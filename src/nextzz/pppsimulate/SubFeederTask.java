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

import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import kosui.ppplogic.ZcDelayor;
import kosui.ppplogic.ZcHookFlicker;
import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZcPLC;
import kosui.ppplogic.ZcPulser;
import kosui.ppplogic.ZcTimer;
import kosui.ppplogic.ZiTask;
import kosui.ppputil.VcLocalTagger;
import nextzz.pppdelegate.SubAnalogDelegator;
import nextzz.pppdelegate.SubFeederDelegator;
import nextzz.pppdelegate.SubVCombustDelegator;
import nextzz.pppdelegate.SubVProvisionDelegator;
import nextzz.pppmodel.MainPlantModel;
import nextzz.pppmodel.MainSpecificator;
import processing.core.PApplet;

public final class SubFeederTask implements ZiTask{
  
  private static SubFeederTask self = null;
  public static final SubFeederTask ccRefer(){
    if(self==null){self=new SubFeederTask();}
    return self;
  }//+++
  private SubFeederTask(){}//..!
  
  //===
  
  private static final int C_CONTROLLER_UPBOUND
    = MainSpecificator.ccRefer().vmVFeederAmount+1;
  
  private final ZcChainController cmVFeederChainCTRL
    = new ZcChainController(2, C_CONTROLLER_UPBOUND);
  
  private final ZcPulser cmVFeederAutoStartPLS
    = new ZcPulser();
  
  public final List<ZcMotor> dcLesVFeeder
    = Collections.unmodifiableList(Arrays.asList(
      new ZcMotor(4),//..0
      new ZcMotor(4),new ZcMotor(4),new ZcMotor(4),//..1-3
      new ZcMotor(4),new ZcMotor(4),new ZcMotor(4),//..2-6
      new ZcMotor(4),new ZcMotor(4),//..7-8
      new ZcMotor(4),new ZcMotor(4) //..9-10..optional!!
    ));
  
  private final List<ZcHookFlicker> cmLesVFeederHOOK
    = Collections.unmodifiableList(Arrays.asList(
      new ZcHookFlicker(),
      new ZcHookFlicker(),new ZcHookFlicker(),new ZcHookFlicker(),
      new ZcHookFlicker(),new ZcHookFlicker(),new ZcHookFlicker(),
      new ZcHookFlicker(),new ZcHookFlicker(),
      new ZcHookFlicker(),new ZcHookFlicker()
    ));
  
  private final ZcTimer simVColdAggregateSensorTM
    = new ZcDelayor(50,50);
  
  private final List<? extends ZcTimer> simLesVFeederSensorTM
    = Collections.unmodifiableList(Arrays.asList(
      new ZcOnDelayTimer(11),
      new ZcOnDelayTimer(11),new ZcOnDelayTimer(11),new ZcOnDelayTimer(11),
      new ZcOnDelayTimer(11),new ZcOnDelayTimer(11),new ZcOnDelayTimer(11),
      new ZcOnDelayTimer(11),new ZcOnDelayTimer(11),
      new ZcOnDelayTimer(11),new ZcOnDelayTimer(11)
    ));
  
  private boolean simVFeederCutout;
  
  private final List<ZcContainer> dcLesHotBin
    = Collections.unmodifiableList(Arrays.asList(
      new ZcContainer(),
      new ZcContainer(),new ZcContainer(),new ZcContainer(),
      new ZcContainer(),new ZcContainer(),new ZcContainer(),
      new ZcContainer()
    ));
  
  private final ZcContainer dcOverFlowedBin = new ZcContainer();
  
  private final ZcContainer dcOverSizedBin = new ZcContainer();
  
  private final ZcHookFlicker cmOverFlowedGateHOOK = new ZcHookFlicker();
  
  private final ZcHookFlicker cmOverSizedGateHOOK = new ZcHookFlicker();
    
  private final boolean[] dcDesVFSG = new boolean[16];
  
  private boolean
    dcCAS=false,
    dcOFD=false,
    dcOSD=false
  ;//,,,
  
  //===
  
  public final boolean ccGetColdAggregateSensor(){
    return dcCAS;
  }//++>
  
  public final boolean ccGetVFeederStuckSensor(int pxOrder){
    return dcDesVFSG[pxOrder&0xF];
  }//++>
  
  public final int ccGetVFeederConveyorScaleBYTE(){
    int lpSum=0;
    for(
      int i=MainPlantModel.C_VF_UI_VALID_HEAD;
      i<=MainSpecificator.ccRefer().vmVFeederAmount;
      i++
    ){
      lpSum+=SubFeederDelegator.ccGetVFeederSpeed(i);
    }//..~
    if(lpSum<=100){return 0;}
    return PApplet.ceil(PApplet.map((float)lpSum,
      0f,
      (float)(MainSpecificator.ccRefer().vmVFeederAmount
        * MainPlantModel.C_FEEDER_RPM_MAX), 
      0f, 255f
    ));
  }//++>
  
  public final boolean ccGetVFeederStartFlag(){
    return simVFeederCutout;
  }//++>
  
  public final boolean ccIsSandBinNotEmpty(){
    return dcLesHotBin.get(1).ccHasContent();
  }//++>
  
  //===
  
  @Override public void ccScan(){
    
    //-- vf ** pre
    boolean lpVFeederIL
      = SubVProvisionTask.ccRefer().dcHorizontalBelcon.ccIsContacted();
    
    //-- vf ** controller
    if(!lpVFeederIL){
      cmVFeederChainCTRL.ccForceStop();
    }//..?
    cmVFeederChainCTRL.ccSetConfirmedAt(C_CONTROLLER_UPBOUND,lpVFeederIL);
    boolean lpVFeederStartFlag = ZcPLC.or(
      SubFeederDelegator.mnVFChainMSSW,
      ZcPLC.and(
        cmVFeederChainCTRL.ccIsAllStopped(),
        cmVFeederAutoStartPLS.ccUpPulse(SubFeederDelegator
          .mnVEntranceTemperatureOverbaseFLG)
      )
    );
    
    cmVFeederChainCTRL.ccSetRun(lpVFeederStartFlag);
    cmVFeederChainCTRL.ccRun();
    
    //-- vf ** output
    for(int i=1;i<=MainSpecificator.ccRefer().vmVFeederAmount;i++){
      cmLesVFeederHOOK.get(i)
        .ccHook(cmVFeederChainCTRL.ccGetPulseAt(i),!lpVFeederIL);
      boolean lpPermmision = (!dcLesVFeeder.get(i).ccIsTripped())
        && (!SubFeederDelegator.ccGetVFeederDisable(i));
      boolean lpEngage = 
        cmLesVFeederHOOK.get(i).ccIsHooked()
        ||SubFeederDelegator.ccGetVFeederForce(i);
      dcLesVFeeder.get(i).ccContact(lpPermmision&&lpEngage);
    }//+++
    
    //-- vf ** feedback
    SubFeederDelegator.mnVFChainMSPL
      =cmVFeederChainCTRL.ccGetFlasher(MainSimulator.ccOneSecondClock());
    for(
      int i=MainPlantModel.C_VF_UI_VALID_HEAD;
      i<=MainSpecificator.ccRefer().vmVFeederAmount;
      i++
    ){
      SubFeederDelegator.ccSetVFeederRunning
        (i,dcLesVFeeder.get(i).ccIsContacted());
      SubFeederDelegator.ccSetVFeederStuck
        (i, ccGetVFeederStuckSensor(i));
    }//..~
    SubVCombustDelegator.mnVColdAggreageSensorPL=dcCAS;
    
    //-- hb ** feedback
    for(int i=1;i<=MainSpecificator.ccRefer().vmAGCattegoryCount;i++){
      SubAnalogDelegator.ccSetHotBinLevelorAD
        (i,dcLesHotBin.get(i).ccGetScaledValue(255));
    }//..~
    
    //-- hb ** of-os
    cmOverFlowedGateHOOK.ccHook(SubVProvisionDelegator.mnOverFlowedGateSW);
    dcOFD=cmOverFlowedGateHOOK.ccIsHooked();
    SubVProvisionDelegator.mnOverFlowedGatePL=dcOFD;
    SubVProvisionDelegator.mnOverFlowedLVPL=dcOverFlowedBin.ccIsFull();
    cmOverSizedGateHOOK.ccHook(SubVProvisionDelegator.mnOverSizedGateSW);
    dcOSD=cmOverSizedGateHOOK.ccIsHooked();
    SubVProvisionDelegator.mnOverSizedGatePL=dcOSD;
    SubVProvisionDelegator.mnOverSizedLVPL=dcOverSizedBin.ccIsFull();
    
  }//+++

  @Override public void ccSimulate() {
    
    //-- cold aggregate sensor
    simVColdAggregateSensorTM.ccAct(
      simVFeederCutout
      && SubVProvisionTask.ccRefer().dcHorizontalBelcon.ccIsContacted()
    );
    dcCAS=simVColdAggregateSensorTM.ccIsUp()
      && SubVProvisionTask.ccRefer().dcInclinedBelcon.ccIsContacted();
    
    //-- aggregate supply
    simVFeederCutout=false;
    boolean lpHotbinInjectCondition
      = SubVProvisionTask.ccRefer().dcScreen.ccIsContacted()
        && SubVProvisionTask.ccRefer().dcHotElevator.ccIsContacted();
    for(
      int i=MainPlantModel.C_VF_UI_VALID_HEAD;
      i<=MainSpecificator.ccRefer().vmVFeederAmount;
      i++
    ){
      dcLesVFeeder.get(i).ccRun(0.64f);
      simLesVFeederSensorTM.get(i).ccAct(dcLesVFeeder.get(i).ccIsContacted()
        && (SubFeederDelegator.ccGetVFeederSpeed(i)>512)
      );
      dcDesVFSG[i]=!simLesVFeederSensorTM.get(i).ccIsUp();
      simVFeederCutout|=simLesVFeederSensorTM.get(i).ccIsUp();
      if(dcCAS && lpHotbinInjectCondition){
        dcLesHotBin.get(i).ccCharge(SubFeederDelegator.ccGetVFeederSpeed(i)/120,
          simLesVFeederSensorTM.get(i).ccIsUp()
        );
        if(i>=6){
          dcOverSizedBin.ccCharge(SubFeederDelegator.ccGetVFeederSpeed(i)/200,
            dcLesHotBin.get(i).ccIsOverflowing()
          );
        }else{
          dcOverFlowedBin.ccCharge(SubFeederDelegator.ccGetVFeederSpeed(i)/300,
            dcLesHotBin.get(i).ccIsOverflowing()
          );
        }//..?
      }//..?
    }//..~
    
    //-- of-os
    dcOverFlowedBin.ccDischarge(99,dcOFD);
    dcOverSizedBin.ccDischarge(99, dcOSD);
    
  }//+++
  
  //===
  
  @Deprecated public final void tstTagg(){
    VcLocalTagger.ccTag("vf-ctrl", cmVFeederChainCTRL);
    VcLocalTagger.ccTag("ag1", dcLesHotBin.get(1));
    VcLocalTagger.ccTag("ag2", dcLesHotBin.get(2));
    VcLocalTagger.ccTag("ag3", dcLesHotBin.get(3));
    VcLocalTagger.ccTag("ag4", dcLesHotBin.get(4));
    VcLocalTagger.ccTag("ag5", dcLesHotBin.get(5));
    VcLocalTagger.ccTag("ag6", dcLesHotBin.get(6));
    VcLocalTagger.ccTag("of", dcOverFlowedBin);
    VcLocalTagger.ccTag("os", dcOverSizedBin);
  }//+++
  
}//***eof
