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
  
  private boolean
    
    //-- misc
    dcVCompressorMC,dcVCompressorAN,dcVCompressorAL,
    
    //-- filler 
    dcFillerEelevatorMC,
    dcFillerScrewMC,
    dcFillerBinLV
    
    //== 
    
  ;//...
  
  private int 
    dcVComporessorCT  
  ;//...
  
  private final ZcHookFlicker
    cmVCompressorHOOK = new ZcHookFlicker(),
    cmFillerSupplyHOOK = new ZcHookFlicker()
  ;//...
  
  
  private final ZcTimer cmFillerEVStopTM = new ZcOffDelayTimer(32);
  private final ZcTimer cmFillerSCStartTM = new ZcOnDelayTimer(32);
  private final ZcTimer cmFillerLevelDelay = new ZcDelayor(7, 999);

  @Override public void ccScan(){
    
    //-- misc ** v comprssor
    cmVCompressorHOOK.ccHook
      (SubVPreparingDelegator.mnVCompressorMSSW,dcVCompressorAL);
    dcVCompressorMC=cmVCompressorHOOK.ccIsHooked();
    SubVPreparingDelegator.mnVCompressorMSPL=dcVCompressorMC&&
      (MainSimulator.ccOneSecondClock()||dcVCompressorAN);
    SubCurrentSlotDelefator.mnCTSlotZ=dcVComporessorCT;
    
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
  
  private final ZcTimer simVCompressorStartWaitTM
    = new ZcOnDelayTimer(30);
  
  private final ZcCheckedValueModel simFillerBin
    = new ZcCheckedValueModel(0, 29999);
  
  private final ZcMotor simVCompressor = new ZcMotor();
  
  @Override public void ccSimulate(){
    
    //-- misc ** v compressor
    simVCompressorStartWaitTM.ccAct(dcVCompressorMC);
    dcVCompressorAN=simVCompressorStartWaitTM.ccIsUp();
    dcVComporessorCT=simVCompressor.ccContact(dcVCompressorAN, 0.76f);
    dcVCompressorAL=simVCompressor.ccGetIsTripped();
    
    
    MainSimulator.ccTransferExclusive(simFillerBin,
      dcFillerScrewMC&&dcFillerEelevatorMC, 64,
      true, 16
    );
    dcFillerBinLV=simFillerBin.ccIsAbove(22222);
    /* 4 *///VcLocalTagger.ccTag("ffcbin", simFillerBin.ccGetValue());
    
    
    
  }//+++
  
}//***eof
