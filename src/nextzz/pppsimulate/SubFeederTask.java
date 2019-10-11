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
  
  public final List<ZcMotor> dcDesVFeeder
    = Collections.unmodifiableList(Arrays.asList(
      new ZcMotor(4),//..0
      new ZcMotor(4),new ZcMotor(4),new ZcMotor(4),//..1-3
      new ZcMotor(4),new ZcMotor(4),new ZcMotor(4),//..2-6
      new ZcMotor(4),new ZcMotor(4),//..7-8
      new ZcMotor(4),new ZcMotor(4) //..9-10..optional!!
    ));
  
  private final List<ZcHookFlicker> cmDesVFeederHOOK
    = Collections.unmodifiableList(Arrays.asList(
      new ZcHookFlicker(),
      new ZcHookFlicker(),new ZcHookFlicker(),new ZcHookFlicker(),
      new ZcHookFlicker(),new ZcHookFlicker(),new ZcHookFlicker(),
      new ZcHookFlicker(),new ZcHookFlicker(),
      new ZcHookFlicker(),new ZcHookFlicker()
    ));
  
  private final ZcTimer simVColdAggregateSensorTM
    = new ZcDelayor(50,50);
  
  private final List<? extends ZcTimer> simDesVFeederSensorTM
    = Collections.unmodifiableList(Arrays.asList(
      new ZcOnDelayTimer(11),
      new ZcOnDelayTimer(11),new ZcOnDelayTimer(11),new ZcOnDelayTimer(11),
      new ZcOnDelayTimer(11),new ZcOnDelayTimer(11),new ZcOnDelayTimer(11),
      new ZcOnDelayTimer(11),new ZcOnDelayTimer(11),
      new ZcOnDelayTimer(11),new ZcOnDelayTimer(11)
    ));
  
  private boolean simVFeederCutout;
  
  private final List<ZcContainer> dcDesHotBin
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
      int i=MainPlantModel.C_VF_INIT_ORDER;
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
  
  //===
  
  @Override public void ccScan(){
    
    //-- vf ** pre
    boolean lpVFeederIL
      = SubVProvisionTask.ccRefer().dcHorizontalBelcon.ccIsContacted();
    
    //-- vf ** controller
    if(!lpVFeederIL){
      cmVFeederChainCTRL.ccForceStop();
    }//..?
    cmVFeederChainCTRL.ccSetConfirmedAt(
      C_CONTROLLER_UPBOUND,lpVFeederIL
    );
    boolean lpVFeederStartFlag
      = SubFeederDelegator.mnVFChainMSSW;
    cmVFeederChainCTRL.ccSetRun(lpVFeederStartFlag);
    cmVFeederChainCTRL.ccRun();
    
    //-- vf ** output
    for(int i=1;i<=MainSpecificator.ccRefer().vmVFeederAmount;i++){
      cmDesVFeederHOOK.get(i)
        .ccHook(cmVFeederChainCTRL.ccGetPulseAt(i),!lpVFeederIL);
      boolean lpPermmision = (!dcDesVFeeder.get(i).ccIsTripped())
        && (!SubFeederDelegator.ccGetVFeederDisable(i));
      boolean lpEngage = 
        cmDesVFeederHOOK.get(i).ccIsHooked()
        ||SubFeederDelegator.ccGetVFeederForce(i);
      dcDesVFeeder.get(i).ccContact(lpPermmision&&lpEngage);
    }//+++
    
    //-- vf ** feedback
    SubFeederDelegator.mnVFChainMSPL
      =cmVFeederChainCTRL.ccGetFlasher(MainSimulator.ccOneSecondClock());
    for(
      int i=MainPlantModel.C_VF_INIT_ORDER;
      i<=MainSpecificator.ccRefer().vmVFeederAmount;
      i++
    ){
      SubFeederDelegator.ccSetVFeederRunning
        (i,dcDesVFeeder.get(i).ccIsContacted());
      SubFeederDelegator.ccSetVFeederStuck
        (i, ccGetVFeederStuckSensor(i));
    }//..~
    SubVCombustDelegator.mnVColdAggreageSensorPL=dcCAS;
    
    //-- hb ** feedback
    for(int i=1;i<=MainSpecificator.ccRefer().vmAGCattegoryCount;i++){
      SubAnalogDelegator.ccSetHotBinLevelorAD
        (i,dcDesHotBin.get(i).ccGetScaledValue(255));
    }//..~
    
    //-- hb ** of-os
    cmOverFlowedGateHOOK.ccHook(SubVProvisionDelegator.mnOverFlowedGateSW);
    dcOFD=cmOverFlowedGateHOOK.ccIsHooked();
    SubVProvisionDelegator.mnOverFlowedGatePL=dcOFD;
    cmOverSizedGateHOOK.ccHook(SubVProvisionDelegator.mnOverSizedGateSW);
    dcOSD=cmOverSizedGateHOOK.ccIsHooked();
    SubVProvisionDelegator.mnOverSizedGatePL=dcOSD;
    //[head]:: the levelor
    
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
      int i=MainPlantModel.C_VF_INIT_ORDER;
      i<=MainSpecificator.ccRefer().vmVFeederAmount;
      i++
    ){
      dcDesVFeeder.get(i).ccSimulate(0.64f);
      simDesVFeederSensorTM.get(i).ccAct(
            dcDesVFeeder.get(i).ccIsContacted()
        && (SubFeederDelegator.ccGetVFeederSpeed(i)>512)
      );
      dcDesVFSG[i]=!simDesVFeederSensorTM.get(i).ccIsUp();
      simVFeederCutout|=simDesVFeederSensorTM.get(i).ccIsUp();
      if(dcCAS && lpHotbinInjectCondition){
        dcDesHotBin.get(i).ccCharge(
          SubFeederDelegator.ccGetVFeederSpeed(i)/120,
          simDesVFeederSensorTM.get(i).ccIsUp()
        );
        if(i>=6){
          dcOverSizedBin.ccCharge(
            SubFeederDelegator.ccGetVFeederSpeed(i)/200,
            dcDesHotBin.get(i).ccIsOverflowing()
          );
        }else{
          dcOverFlowedBin.ccCharge(
            SubFeederDelegator.ccGetVFeederSpeed(i)/300,
            dcDesHotBin.get(i).ccIsOverflowing()
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
    VcLocalTagger.ccTag("ag1", dcDesHotBin.get(1));
    VcLocalTagger.ccTag("ag2", dcDesHotBin.get(2));
    VcLocalTagger.ccTag("ag3", dcDesHotBin.get(3));
    VcLocalTagger.ccTag("ag4", dcDesHotBin.get(4));
    VcLocalTagger.ccTag("ag5", dcDesHotBin.get(5));
    VcLocalTagger.ccTag("ag6", dcDesHotBin.get(6));
    VcLocalTagger.ccTag("of", dcOverFlowedBin);
    VcLocalTagger.ccTag("os", dcOverSizedBin);
  }//+++
  
}//***eof
