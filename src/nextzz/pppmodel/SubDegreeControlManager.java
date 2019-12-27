/*
 * Copyright (C) 2019 Key Parker from K.I.C.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */

package nextzz.pppmodel;

import kosui.ppplocalui.EiTriggerable;
import kosui.ppplogic.ZcFlicker;
import kosui.ppplogic.ZcPLC;
import kosui.ppplogic.ZcPulser;
import kosui.ppputil.VcLocalTagger;
import nextzz.pppdelegate.SubVCombustDelegator;
import nextzz.pppdelegate.SubVProvisionDelegator;
import nextzz.pppsimulate.ZcPIDController;

public final class SubDegreeControlManager {
  
  private static final SubDegreeControlManager SELF
    = new SubDegreeControlManager();
  public static SubDegreeControlManager ccRefer(){return SELF;}//++>
  private SubDegreeControlManager(){}//++!
  
  //===
  
  //-- v
  
  public volatile int vmVTargetCELC = 160;//.. aka "MoKuHyouOnnDo"
  
  public volatile int vmVCoolDownCELC = 200;//.. aka "JyouGenn"
  
  public volatile int vmVMeltDownCELC = 240;//.. aka "JyouJyouGenn"
  
  public volatile int vmVTargetAdjustWidth = 5;//.. aka "TyouSeiKannDo"
  
  public volatile int vmVPreHeatingPT = 15;//.. aka "YoNetsuOnnDo"
  
  public volatile int vmVDryerTargetMinusKPA = 25;//.. aka "DoRaIiYaSeiAaTsu"
  
  public volatile int vmVExfanIgnitionPT = 15;//.. aka "TyakKaKaiDo"
    
  private final ZcPIDController
    //[head]::so what is the best init value??
    cmVTemperatureCTRL   = new ZcPIDController(0f,400f,0.01f,0.55f),
    cmVBurnerDegreeCTRL  = new ZcPIDController(0f,100f,0.03f,0.30f),
    cmVPressureCTRL      = new ZcPIDController(0f,200f,0.05f,0.75f),
    cmVExfanDegreeCTRL   = new ZcPIDController(0f,100f,0.05f,0.30f)
  ;//,,,
  
  private final ZcPulser cmVShiftResetPLS = new ZcPulser();
  
  private final ZcFlicker
    cmVTemperatureAdjustTM   = new ZcFlicker(80),
    cmVTemperatureSamplingTM = new ZcFlicker(16),
    cmVPressureAdjustTM      = new ZcFlicker(32),
    cmVPressureSamplingTM    = new ZcFlicker(16)
  ;//,,,
  
  //-- r
  //[todo]::cmRTemperatureCTRL
  //[todo]::cmRBurnerDegreeCTRL
  //[todo]::cmRPressureCTRL
  //[todo]::cmRExfanDegreeCTRL
  //[todo]::cmRTemperatureAdjustTM
  //[todo]::cmRTemperatureSamplingTM
  //[todo]::cmRPressureAdjustTM
  //[todo]::cmRPressureSamplingTM
  
  //===
  
  public final EiTriggerable cmVTemperatureTargetSettling
    = new EiTriggerable() {
    @Override public void ccTrigger() {
      cmVTemperatureCTRL.ccSetTarget(vmVTargetCELC);
    }//+++
  };//***
  
  public final EiTriggerable cmVPressureTargetSettling
    = new EiTriggerable() {
    @Override public void ccTrigger() {
      cmVPressureCTRL.ccSetTarget(
        MainPlantModel.C_PRESSURE_CONTOL_OFFSET 
         - ((float)vmVDryerTargetMinusKPA)
      );
    }//+++
  };//***
  
  //===
  
  public final void ccLogic(){
    
    //-- v
    
    //-- v ** shift reset
    if(cmVShiftResetPLS.ccUpPulse(SubVCombustDelegator.mnVBFlamingPL)){
      cmVTemperatureCTRL.ccReset();
      cmVPressureCTRL.ccReset();
    }//..?
    
    //-- vb
    //-- vb ** timing
    cmVTemperatureAdjustTM.ccAct(SubVCombustDelegator.mnVBFlamingPL);
    cmVTemperatureSamplingTM.ccAct(SubVCombustDelegator.mnVBFlamingPL);
    //-- vb ** controller
    cmVTemperatureCTRL.ccRun(
      SubAnalogScalarManager.ccRefer().cmDesThermoCelcius
        .ccGet(SubAnalogScalarManager.C_I_THI_CHUTE),
      cmVTemperatureSamplingTM.ccAtEdge(),
      cmVTemperatureAdjustTM.ccAtEdge()
        && SubVCombustDelegator.mnVColdAggreageSensorPL
    );
    cmVBurnerDegreeCTRL.ccSetTarget(
      ZcPLC.sel(!SubVCombustDelegator.mnVBFlamingPL,
        0f,
        ZcPLC.sel(SubVCombustDelegator.mnVColdAggreageSensorPL,
          cmVTemperatureCTRL.ccGetMinusTrimmed()*100f,
          (float)vmVPreHeatingPT
        )
      )
    );
    cmVBurnerDegreeCTRL.ccRun(
      (float)SubAnalogScalarManager.ccRefer().ccGetVBurnerPercentage()
    );
    
    //-- vb ** to plc
    SubVCombustDelegator.mnVBurnerCloseFLG
      = cmVBurnerDegreeCTRL.ccGetNegativeOutput();
    SubVCombustDelegator.mnVBurnerOpenFLG
      = cmVBurnerDegreeCTRL.ccGetPositiveOutput();
    
    //-- ve
    //-- ve ** timing
    cmVPressureAdjustTM.ccAct(SubVCombustDelegator.mnVBFlamingPL);
    cmVPressureSamplingTM.ccAct(SubVCombustDelegator.mnVBFlamingPL);
    //-- ve ** controller
    cmVPressureCTRL.ccRun(
      MainPlantModel.C_PRESSURE_CONTOL_OFFSET
        + ((float)SubAnalogScalarManager.ccRefer().ccGetVDryerKPA()),
      cmVPressureSamplingTM.ccAtEdge(),
      cmVPressureAdjustTM.ccAtEdge() && SubVCombustDelegator.mnVBFlamingPL
    );
    cmVExfanDegreeCTRL.ccSetTarget(
      ZcPLC.sel(!SubVProvisionDelegator.mnVExfanIconPL,
        0f,
        ZcPLC.sel(SubVCombustDelegator.mnVBFlamingPL,
          cmVPressureCTRL.ccGetReverselyTrimmed()*100f,
          (float)vmVExfanIgnitionPT
        )
      )
    );
    cmVExfanDegreeCTRL.ccRun(
      (float)SubAnalogScalarManager.ccRefer().ccGetVExfanPercentage()
    );
    //-- ve ** to plc
    SubVCombustDelegator.mnVExfanCloseFLG
      = cmVExfanDegreeCTRL.ccGetNegativeOutput();
    SubVCombustDelegator.mnVExfanOpenFLG
      = cmVExfanDegreeCTRL.ccGetPositiveOutput();
    
    //-- r
    //-- re
    //-- rb
    
  }//++~
  
  //===
  
  //[todo]:: ccSetProportion(float pxProportion, flat pxDead){}//++<
  //[todo]:: ccSetAdjustInterval(float pxSecond){}//++<
  //[todo]:: ccSetSamplingInerval(float pxSecond){}//++<
  
  //===

  @Deprecated public final void tstTagg(){
    VcLocalTagger.ccTag("vt-ctrl", cmVTemperatureCTRL);
    VcLocalTagger.ccTag("vt-stm", cmVTemperatureSamplingTM);
    VcLocalTagger.ccTag("vt-stm", cmVTemperatureAdjustTM);
    VcLocalTagger.ccTag("vb-ctrl", cmVBurnerDegreeCTRL);
    VcLocalTagger.ccTag("vp-ctrl", cmVPressureCTRL);
    VcLocalTagger.ccTag("ve-ctrl", cmVExfanDegreeCTRL);
  }//+++
  
 }//***eof
