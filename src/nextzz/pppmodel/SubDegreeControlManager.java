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
import kosui.ppplogic.ZcPulser;
import kosui.ppplogic.ZcRangedValueModel;
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
    cmVTemperatureCTRL   = new ZcPIDController(100f,0.8f,0.10f,true),
    cmVBurnerDegreeCTRL  = new ZcPIDController(100f,0.5f,0.02f),
    cmVPressureCTRL      = new ZcPIDController(100f,0.8f,0.10f),
    cmVExfanDegreeCTRL   = new ZcPIDController(100f,0.5f,0.02f)
  ;//,,,
  
  private final ZcPulser cmVShiftResetPLS = new ZcPulser();
  
  private final ZcRangedValueModel
    cmVTemperatureAdjustTM = new ZcRangedValueModel(0, 63),
    cmVTemperatureSamplingTM = new ZcRangedValueModel(0, 15),
    cmVPressureAdjustTM = new ZcRangedValueModel(0, 63),
    cmVPressureSamplingTM = new ZcRangedValueModel(0, 15)
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
  
  public final EiTriggerable cmControllerRetargetting = new EiTriggerable() {
    @Override public void ccTrigger() {
      System.out.println("cmControllerRetargetting()::invoked!!");
      cmVTemperatureCTRL.ccResetShiftedTarget();
      cmVPressureCTRL.ccResetShiftedTarget();
    }//+++
  };//***
  
  //===
  
  public final void ccLogic(){
    
    //-- v
    
    //-- v ** shift reset
    if(cmVShiftResetPLS.ccUpPulse(SubVCombustDelegator.mnVBFlamingPL)){
      cmControllerRetargetting.ccTrigger();
    }//..?
    
    //-- vb
    //-- vb ** timing
    cmVTemperatureAdjustTM.ccRoll(1);
    cmVTemperatureSamplingTM.ccRoll(1);
    //-- vb ** controller
    cmVTemperatureCTRL.ccSetTarget(vmVTargetCELC);
    cmVTemperatureCTRL.ccRun(
      SubAnalogScalarManager.ccRefer().cmDesThermoCelcius
        .ccGet(SubAnalogScalarManager.C_I_THI_CHUTE),
      cmVTemperatureAdjustTM.ccIsAt(1)
        && SubVCombustDelegator.mnVColdAggreageSensorPL,
      cmVTemperatureSamplingTM.ccIsAt(1)
    );
    cmVBurnerDegreeCTRL.ccRun(!SubVCombustDelegator.mnVBFlamingPL
        ? 0f
        : (SubVCombustDelegator.mnVColdAggreageSensorPL
            ? cmVTemperatureCTRL.ccGetMinusTrimmed()*100f
            : ((float)vmVPreHeatingPT)),
      (float)SubAnalogScalarManager.ccRefer().ccGerVBurnerPercentage()
    );
    //-- vb ** to plc
    SubVCombustDelegator.mnVBurnerCloseFLG
      = cmVBurnerDegreeCTRL.ccGetNegativeOutput();
    SubVCombustDelegator.mnVBurnerOpenFLG
      = cmVBurnerDegreeCTRL.ccGetPositiveOutput();
    
    //-- ve
    //-- ve ** timing
    cmVPressureAdjustTM.ccRoll(1);
    cmVPressureSamplingTM.ccRoll(1);
    //-- ve ** controller
    cmVPressureCTRL.ccSetTarget(MainPlantModel.C_PRESSURE_CONTOL_OFFSET
      - ((float)vmVDryerTargetMinusKPA));
    cmVPressureCTRL.ccRun(
      MainPlantModel.C_PRESSURE_CONTOL_OFFSET
        + ((float)SubAnalogScalarManager.ccRefer().ccGetVDryerKPA()),
      cmVPressureAdjustTM.ccIsAt(1) && SubVCombustDelegator.mnVBFlamingPL,
      cmVPressureSamplingTM.ccIsAt(1)
    );
    cmVExfanDegreeCTRL.ccRun(!SubVProvisionDelegator.mnVExfanIconPL
        ? 0f
        : (SubVCombustDelegator.mnVBFlamingPL
            ? cmVPressureCTRL.ccGetReverselyTrimmed()*100f
            : ((float)vmVExfanIgnitionPT)),
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
    VcLocalTagger.ccTag("vb-ctrl", cmVBurnerDegreeCTRL);
    VcLocalTagger.ccTag("vp-ctrl", cmVPressureCTRL);
    VcLocalTagger.ccTag("ve-ctrl", cmVExfanDegreeCTRL);
  }//+++
  
 }//***eof
