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

import kosui.ppplocalui.EcComponent;
import kosui.ppplogic.ZcImpulsivePulser;
import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZcRangedValueModel;
import kosui.ppplogic.ZcStepper;
import kosui.ppplogic.ZcTimer;
import kosui.ppputil.VcLocalTagger;
import kosui.ppputil.VcStringUtility;
import nextzz.pppdelegate.SubWeighingDelegator;
import nextzz.ppplocalui.SubMixerGroup;
import nextzz.ppplocalui.SubOperativeGroup;
import nextzz.pppmain.MainSketch;
import nextzz.pppsimulate.ZcWeighController;

public final class SubWeighControlManager {

  private static final SubWeighControlManager SELF
    = new SubWeighControlManager();
  public static final SubWeighControlManager ccRefer(){return SELF;}//+++
  private SubWeighControlManager(){}//++!

  //===
  
  //===
  
  public volatile int vmRemainBatch=6;
  public volatile int vmDrySetFrame=90;
  public volatile int vmWetSetFrame=160;
  public volatile int vmDryRemainSecond=0;
  public volatile int vmWetRemainSecond=0;
  
  public final ZcWeighController
    cmAGWeighCTRL = new ZcWeighController(7),
    cmFRWeighCTRL = new ZcWeighController(3),
    cmASWeighCTRL = new ZcWeighController(3)
  ;//,,,
  
  private final ZcTimer
    cmAGDischargeDelayTM = new ZcImpulsivePulser(7),
    cmFRDischargeDelayTM = new ZcImpulsivePulser(11);
  
  private final ZcRangedValueModel
    cmDryTimeManipulator = new ZcRangedValueModel(0, 65535),
    cmWetTimeManipulator = new ZcRangedValueModel(0, 65535);
  
  private boolean cmIsDryCountingDown,cmIsWetCountingDown;
  private boolean cmMixerReadyFlag=true;
  
  public final void ccResetMixerOnMixingFlag(){
    cmMixerReadyFlag=true;
  }//+++
  
  //===
  
  public final void ccInit(){
    
  }//++!
  
  public final void ccLogic(){
    
    /* 6 */
    boolean lpAGSkip = EcComponent.ccIsKeyPressed('i');
    boolean lpFRSkip = EcComponent.ccIsKeyPressed('u');
    boolean lpASSkip = EcComponent.ccIsKeyPressed('o');
    
    boolean lpDiss = EcComponent.ccIsKeyPressed('k')
      && cmAGWeighCTRL.ccIsWaitingToDischarge()
      && cmFRWeighCTRL.ccIsWaitingToDischarge()
      && cmASWeighCTRL.ccIsWaitingToDischarge()
      && cmMixerReadyFlag;
    
    cmAGWeighCTRL.ccSetHasNext(vmRemainBatch>1);
    cmAGWeighCTRL.ccSetToNext(lpAGSkip);
    cmAGWeighCTRL.ccRun();
    
    cmFRWeighCTRL.ccSetHasNext(vmRemainBatch>1);
    cmFRWeighCTRL.ccSetToNext(lpFRSkip);
    cmFRWeighCTRL.ccRun();
    
    cmASWeighCTRL.ccSetHasNext(vmRemainBatch>1);
    cmASWeighCTRL.ccSetToNext(lpASSkip);
    cmASWeighCTRL.ccRun();
    
    //-- raw delay
    cmAGDischargeDelayTM.ccAct(lpDiss);
    cmFRDischargeDelayTM.ccAct(lpDiss);
    
    //-- dry-wet
    if(lpDiss){
      SubWeighingDelegator.mnMixerDischargeConfirmFlag=false;
      cmIsDryCountingDown=true;
      cmMixerReadyFlag=false;
    }//..?
    if(cmIsDryCountingDown){cmDryTimeManipulator.ccShift(-1);}
    if(cmDryTimeManipulator.ccIsAt(0)){cmIsWetCountingDown=true;}
    if(cmIsWetCountingDown){cmWetTimeManipulator.ccShift(-1);}
    if(cmWetTimeManipulator.ccIsAt(0)){
      cmIsWetCountingDown=false;
      cmIsDryCountingDown=false;
      cmDryTimeManipulator.ccSetValue(vmDrySetFrame);
      cmWetTimeManipulator.ccSetValue(vmWetSetFrame);
    }//..?
    vmDryRemainSecond=cmDryTimeManipulator.ccGetValue()/16;
    vmWetRemainSecond=cmWetTimeManipulator.ccGetValue()/16;
    
    //--
    cmAGWeighCTRL.ccSetToDischarge(cmAGDischargeDelayTM.ccIsUp());
    cmFRWeighCTRL.ccSetToDischarge(cmFRDischargeDelayTM.ccIsUp());
    cmASWeighCTRL.ccSetToDischarge(cmDryTimeManipulator.ccIsAt(1));
    
    //--
    if(cmWetTimeManipulator.ccIsAt(1)){
      SubWeighingDelegator.mnMixerAutoDischargeFlag=true;
      vmRemainBatch--;if(vmRemainBatch<0){vmRemainBatch=0;}
    }//..?
    if(SubWeighingDelegator.mnMixerDischargeConfirmFlag){
      cmMixerReadyFlag=true;
      SubWeighingDelegator.mnMixerAutoDischargeFlag=false;
    }//..?
    
    //[head]:: i know you have absolutely no idea about what to do next
    
  }//++~
  
  public final void ccBind(){
    
    SubOperativeGroup.ccRefer().cmDesBatchTB.get(0).ccSetValue(vmRemainBatch);
    SubMixerGroup.ccRefer().cmDryCountCB.ccSetValue(vmDryRemainSecond);
    SubMixerGroup.ccRefer().cmWetCountCB.ccSetValue(vmWetRemainSecond);
    
    SubMixerGroup.ccRefer().cmDryCountCB
      .ccSetIsActivated(cmIsDryCountingDown
        && MainSketch.ccIsRollingAccrose(5, 2));
    SubMixerGroup.ccRefer().cmWetCountCB
      .ccSetIsActivated(cmIsWetCountingDown
        && MainSketch.ccIsRollingAccrose(5, 2));
    
  }//++~
  
  //===
  
  @Deprecated public final void tstTagg(){
    VcLocalTagger.ccTag("-w-ctrl?", cmAGWeighCTRL);
  }//+++
  
 }//***eof
