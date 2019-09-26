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

package nextzz.pppsimulate;

import kosui.ppplogic.ZcCheckedValueModel;
import kosui.ppplogic.ZcDelayor;
import kosui.ppplogic.ZcHookFlicker;
import kosui.ppplogic.ZcOffDelayTimer;
import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZcTimer;
import kosui.ppplogic.ZiTask;
import kosui.ppputil.VcLocalTagger;
import nextzz.pppdelegate.SubCurrentSlotDelefator;
import nextzz.pppdelegate.SubVPreparingDelegator;

public final class SubVPreparingTask implements ZiTask{
  
  private static SubVPreparingTask self = null;
  public static SubVPreparingTask ccRefer() {
    if(self==null){self=new SubVPreparingTask();}
    return self;
  }//+++
  private SubVPreparingTask(){}//..!
  
  //===
  
  //-- misc ** motor
  
  private final ZcMotor dcVCompressor = new ZcMotor(32);
  private final ZcHookFlicker cmVCompressorHOOK = new ZcHookFlicker();
  
  private final ZcMotor dcMixer = new ZcMotor(48);
  private final ZcHookFlicker cmMixerHOOK = new ZcHookFlicker();
  
  private final ZcMotor dcVExFan = new ZcMotor(36);
  private final ZcHookFlicker cmVExFanHooker = new ZcHookFlicker();
  
  //-- filler supply
  
  private boolean
    
    //-- filler 
    dcFillerEelevatorMC,
    dcFillerScrewMC,
    dcFillerBinLV
    
  ;//...
  
  
  private final ZcHookFlicker  cmFillerSupplyHOOK = new ZcHookFlicker();
  
  
  private final ZcTimer cmFillerEVStopTM = new ZcOffDelayTimer(32);
  private final ZcTimer cmFillerSCStartTM = new ZcOnDelayTimer(32);
  private final ZcTimer cmFillerLevelDelay = new ZcDelayor(7, 999);

  @Override public void ccScan(){
    
    //-- misc ** v comprssor
    cmVCompressorHOOK.ccHook
      (SubVPreparingDelegator.mnVCompressorMSSW,dcVCompressor.ccIsTripped());
    dcVCompressor.ccContact(cmVCompressorHOOK.ccIsHooked());
    SubVPreparingDelegator.mnVCompressorMSPL
      = ConstFunctionBlockHolder.ccMoterFeedBackLamp
          (cmVCompressorHOOK, dcVCompressor);
    SubCurrentSlotDelefator.mnCTSlotZ=dcVCompressor.ccGetCT();
    
    //-- misc ** mixer
    cmMixerHOOK.ccHook
      (SubVPreparingDelegator.mnMixerMSSW,dcMixer.ccIsTripped());
    dcMixer.ccContact(cmMixerHOOK.ccIsHooked());
    SubVPreparingDelegator.mnMixerMSPL
      = ConstFunctionBlockHolder.ccMoterFeedBackLamp
          (cmMixerHOOK, dcMixer);
    //[notsure]::SubCurrentSlotDelefator.mnCTSlotVI= ??? ;
    
    //-- misc ** exfan
    cmVExFanHooker.ccHook
      (SubVPreparingDelegator.mnVExfanMSSW,dcVExFan.ccIsTripped());
    dcVExFan.ccContact(cmVExFanHooker.ccIsHooked());
    SubVPreparingDelegator.mnVExfanMSPL
      = ConstFunctionBlockHolder.ccMoterFeedBackLamp
        (cmVExFanHooker, dcVExFan);
    //[notsure]:: how s the ct slot??
    
    
    //-- filler supply 
    //-- filler supply ** software input
    cmFillerSupplyHOOK.ccHook(SubVPreparingDelegator.mnFillerSystemSW);
    cmFillerEVStopTM.ccAct(cmFillerSupplyHOOK.ccIsHooked());
    cmFillerSCStartTM.ccAct(cmFillerSupplyHOOK.ccIsHooked());
    //-- filler supply ** hardware io
    cmFillerLevelDelay.ccAct(dcFillerBinLV);
    dcFillerEelevatorMC=cmFillerEVStopTM.ccIsUp();
    dcFillerScrewMC=!cmFillerLevelDelay.ccIsUp()
      &&cmFillerSCStartTM.ccIsUp();
    //-- filler supply ** software output
    SubVPreparingDelegator.mnFillerBinMLVPL
      =SubVPreparingDelegator.mnFillerBinLLVPL
      =dcFillerBinLV;
    SubVPreparingDelegator.mnFillerSystemPL=cmFillerEVStopTM.ccIsUp()&&
      (MainSimulator.ccOneSecondClock()||dcFillerScrewMC);
    
  }//+++
  
  //===
  
  private final ZcCheckedValueModel simFillerBin
    = new ZcCheckedValueModel(0, 29999);
  
  @Override public void ccSimulate(){
    
    //-- misc
    dcVCompressor.ccSimulate(0.76f);
    dcMixer.ccSimulate(0.65f);
    dcVExFan.ccSimulate(0.55f);
    
    //-- transfer
    MainSimulator.ccTransferExclusive(simFillerBin,
      dcFillerScrewMC&&dcFillerEelevatorMC, 64,
      true, 16
    );
    dcFillerBinLV=simFillerBin.ccIsAbove(22222);
    /* 4 *///VcLocalTagger.ccTag("ffcbin", simFillerBin.ccGetValue());
    
  }//+++
  
}//***eof
