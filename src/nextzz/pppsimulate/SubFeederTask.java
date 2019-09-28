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
import kosui.ppplogic.ZcHookFlicker;
import kosui.ppplogic.ZcRangedModel;
import kosui.ppplogic.ZiTask;
import kosui.ppputil.VcLocalTagger;
import nextzz.pppdelegate.SubVPreparingDelegator;
import nextzz.pppmodel.MainSpecificator;

public final class SubFeederTask implements ZiTask{
  
  private static SubFeederTask self = null;
  public static final SubFeederTask ccRefer(){
    if(self==null){self=new SubFeederTask();}
    return self;
  }//+++
  private SubFeederTask(){}//..!
  
  //===
  
  private static final int C_CONTROLLER_UPBOUND
    = MainSpecificator.ccRefer().mnVFeederAmount+1;
  
  private final ZcChainController cmVFeederChainCTRL
    = new ZcChainController(2, C_CONTROLLER_UPBOUND);
  
  public final List<ZcMotor> dcDesVFedder
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
  
  //===
  
  private boolean dcCAS=false;
  private boolean[] dcDesVFSG = new boolean[dcDesVFedder.size()];
  
  public final boolean ccGetColdAggregateSensor(){
    return dcCAS;
  }//+++
  
  public final boolean ccGetVFeederStuckSensor(int pxOrder){
    return
      dcDesVFSG[ZcRangedModel.ccLimitInclude(pxOrder, 0, dcDesVFSG.length)];
  }//+++

  //===
  
  @Override public void ccScan(){
    
    //-- vf ** pre
    boolean lpVFeederIL
      = SubVPreparingTask.ccRefer().dcHorizontalBelcon.ccIsContacted();
    
    //-- vf ** controller
    if(!lpVFeederIL){
      cmVFeederChainCTRL.ccAllStop();
    }//..?
    cmVFeederChainCTRL.ccSetConfirmedAt(C_CONTROLLER_UPBOUND, true);
    boolean lpVFeederStartFlag
      = SubVPreparingDelegator.mnVFChainMSSW;
    cmVFeederChainCTRL.ccTakePulse(lpVFeederStartFlag);
    cmVFeederChainCTRL.ccRun();
    
    //-- vf ** output
    for(int i=1;i<=8;i++){
      cmDesVFeederHOOK.get(i).ccHook(cmVFeederChainCTRL.ccGetPulseAt(i));
      dcDesVFedder.get(i).ccContact(
        cmDesVFeederHOOK.get(i).ccIsHooked()
      );
    }//+++
    
    //-- vf ** feedback
    SubVPreparingDelegator.mnVFChainMSPL
      =cmVFeederChainCTRL.ccGetFlasher(MainSimulator.ccOneSecondClock());
    SubVPreparingDelegator.mnVFRunningPLnI
      =dcDesVFedder.get(1).ccIsContacted();
    SubVPreparingDelegator.mnVFRunningPLnII
      =dcDesVFedder.get(2).ccIsContacted();
    SubVPreparingDelegator.mnVFRunningPLnIII
      =dcDesVFedder.get(3).ccIsContacted();
    SubVPreparingDelegator.mnVFRunningPLnIV
      =dcDesVFedder.get(4).ccIsContacted();
    SubVPreparingDelegator.mnVFRunningPLnV
      =dcDesVFedder.get(5).ccIsContacted();
    SubVPreparingDelegator.mnVFRunningPLnVI
      =dcDesVFedder.get(6).ccIsContacted();
    SubVPreparingDelegator.mnVFRunningPLnVII
      =dcDesVFedder.get(7).ccIsContacted();
    SubVPreparingDelegator.mnVFRunningPLnVIII
      =dcDesVFedder.get(8).ccIsContacted();
    
  }//+++

  @Override public void ccSimulate() {
    
    for(ZcMotor it:dcDesVFedder){it.ccSimulate(0.66f);}
    
  }//+++
  
  //===
  
  @Deprecated static public final void tstTagVFeederSystem(){
    VcLocalTagger.ccTag("vf-ctrl", self.cmVFeederChainCTRL);
    VcLocalTagger.ccTag("vf-1", self.dcDesVFedder.get(1));
    VcLocalTagger.ccTag("vf1??", SubVPreparingDelegator.mnVFRunningPLnI);
    VcLocalTagger.ccTag("vf-2", self.dcDesVFedder.get(2));
    VcLocalTagger.ccTag("vf-3", self.dcDesVFedder.get(3));
    VcLocalTagger.ccTag("vf-4", self.dcDesVFedder.get(4));
    VcLocalTagger.ccTag("vf-5", self.dcDesVFedder.get(5));
    VcLocalTagger.ccTag("vf-6", self.dcDesVFedder.get(6));
  }//+++
  
}//***eof
