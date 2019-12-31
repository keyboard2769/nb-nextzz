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

import kosui.ppplocalui.EcConst;
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
  
  public volatile int vmVEntranceBaseCELC = 60;//.. aka "KiridashiOnndo"
  
  public volatile int vmVTargetCELC = 160;//.. aka "MoKuHyouOnnDo"
  
  public volatile int vmVTargetStepCELC = 5;//.. aka "TyouSeiKannDo"
  
  public volatile int vmVCoolDownCELC = 200;//.. aka "JyouGenn"
  
  public volatile int vmVMeltDownCELC = 240;//.. aka "JyouJyouGenn"
  
  public volatile int vmVPreHeatingPT = 15;//.. aka "YoNetsuOnnDo"
  
  public volatile int vmVBurnerDegreeLimitPT = 100;
  //.. aka "BahnaKaidoJyougenn"
  
  public volatile int vmVDryerTargetMinusKPA = 25;//.. aka "DoRaIiYaSeiAaTsu"
  
  public volatile int vmVExfanIgnitionPT = 15;//.. aka "TyakKaKaiDo"
  
  public volatile int vmVExfanDegreeLimitPT = 100;
  //.. aka "HaifuhKiKaidoJyougenn"
  
  public volatile float vmVCombustCTRLDeadFACT = 0.01f;//.. aka "ShiKu"
  
  public volatile float vmVCombustCTRLProportionFACT = 0.45f;
  //.. aka "HireiTai"
  
  public volatile float vmVCombustCTRLSamplingSEC = 1f;
  //.. aka "toriIreKannKaku"
  
  public volatile float vmVCombustCTRLAdjustingSEC = 5f;
  //.. aka "tyouseiKannKaku"
    
  private final ZcPIDController
    cmVCombustCTRL = new ZcPIDController(
      0f,400f,
      vmVCombustCTRLDeadFACT,
      vmVCombustCTRLProportionFACT
    ),
    cmVBurnerDegreeCTRL = new ZcPIDController(0f,100f,0.03f,0.30f),
    cmVPressureCTRL = new ZcPIDController(
      0f,800f,//.. offset counted
      0.02f,0.40f//.. hard coded 
    ),
    cmVExfanDegreeCTRL = new ZcPIDController(0f,100f,0.05f,0.30f)
  ;//,,,
  
  private final ZcPulser cmVShiftResetPLS = new ZcPulser();
  
  private final ZcFlicker
    //.. saying "combust sampling timer" is pretty awkward,
    //     leave them anyway. same as that trigger.
    cmVTemperatureSamplingTM = new ZcFlicker(EcConst
      .ccToFrameCount(vmVCombustCTRLSamplingSEC)),
    cmVTemperatureAdjustTM   = new ZcFlicker(EcConst
      .ccToFrameCount(vmVCombustCTRLAdjustingSEC)),
    cmVPressureSamplingTM    = new ZcFlicker(16),
    cmVPressureAdjustTM      = new ZcFlicker(32)
  ;//,,,
  
  //-- r
  //[todo]::cmRCombustCTRL
  //[todo]::cmRBurnerDegreeCTRL
  //[todo]::cmRPressureCTRL
  //[todo]::cmRExfanDegreeCTRL
  //[todo]::cmRCombustAdjustTM
  //[todo]::cmRCombustSamplingTM
  //[todo]::cmRPressureAdjustTM
  //[todo]::cmRPressureSamplingTM
  
  //===
  
  public final EiTriggerable cmVTemperatureTargetSettling
    = new EiTriggerable() {
    @Override public void ccTrigger() {
      cmVCombustCTRL.ccSetTarget(vmVTargetCELC);
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
  
  public final EiTriggerable cmVCombustCTRLApplying
    = new EiTriggerable() {
    @Override public void ccTrigger(){
      cmVCombustCTRL.ccSetDead(vmVCombustCTRLDeadFACT);
      cmVCombustCTRL.ccSetProportion(vmVCombustCTRLProportionFACT);
      cmVTemperatureSamplingTM.ccSetTime(EcConst
        .ccToFrameCount(vmVCombustCTRLSamplingSEC));
      cmVTemperatureAdjustTM.ccSetTime(EcConst
        .ccToFrameCount(vmVCombustCTRLAdjustingSEC));
    }//+++
  };//***
  
  //===
  
  public final void ccInit(){
    
    //-- init controller
    cmVTemperatureTargetSettling.ccTrigger();
    cmVPressureTargetSettling.ccTrigger();
  
  }//++~
  
  public final void ccLogic(){
    
    //-- v
    
    //-- v ** shift reset
    if(cmVShiftResetPLS.ccUpPulse(SubVCombustDelegator.mnVBFlamingPL)){
      cmVCombustCTRL.ccReset();
      cmVPressureCTRL.ccReset();
    }//..?
    
    //-- vb
    //-- vb ** timing
    cmVTemperatureAdjustTM.ccAct(SubVCombustDelegator.mnVBFlamingPL);
    cmVTemperatureSamplingTM.ccAct(SubVCombustDelegator.mnVBFlamingPL);
    //-- vb ** controller
    cmVCombustCTRL.ccRun(
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
          cmVCombustCTRL.ccGetMinusTrimmed()
            *((float)vmVBurnerDegreeLimitPT),
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
    cmVPressureSamplingTM.ccAct(SubVCombustDelegator.mnVBFlamingPL);
    cmVPressureAdjustTM.ccAct(SubVCombustDelegator.mnVBFlamingPL);
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
          -1f*cmVPressureCTRL.ccGetReverselyTrimmed()
            *((float)vmVExfanDegreeLimitPT),
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

  @Deprecated public final void tstTagg(){
    VcLocalTagger.ccTag("vt-ctrl", cmVCombustCTRL);
    VcLocalTagger.ccTag("vt-stm", cmVTemperatureSamplingTM);
    VcLocalTagger.ccTag("vt-stm", cmVTemperatureAdjustTM);
    VcLocalTagger.ccTag("vb-ctrl", cmVBurnerDegreeCTRL);
    VcLocalTagger.ccTag("vp-ctrl", cmVPressureCTRL);
    VcLocalTagger.ccTag("ve-ctrl", cmVExfanDegreeCTRL);
  }//+++
  
 }//***eof
