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

import kosui.ppplogic.ZcHookFlicker;
import kosui.ppplogic.ZiTask;
import kosui.ppputil.VcLocalTagger;
import nextzz.pppdelegate.SubAnalogDelegator;
import nextzz.pppdelegate.SubVCombustDelegator;

public final class SubVCombusTask implements ZiTask{
  
  private static SubVCombusTask self = null;
  public static final SubVCombusTask ccRefer(){
    if(self==null){self=new SubVCombusTask();}
    return self;
  }//+++
  private SubVCombusTask(){}//..!
  
  //===
  
  public final ZcGate dcVBunerDegree = new ZcGate();
  public final ZcGate dcVExfanDegree = new ZcGate();
  
  private final ZcHookFlicker cmVBAutoHOOK = new ZcHookFlicker();
  private final ZcHookFlicker cmVEAutoHOOK = new ZcHookFlicker();

  @Override public void ccScan() {
    
    //-- combust controll
    SubVCombustDelegator.mnVCombustReadyPL
      = SubVProvisionTask.ccRefer().dcVExFan.ccIsContacted()
      &&SubVProvisionTask.ccRefer().dcInclinedBelcon.ccIsContacted()
      &&SubVProvisionTask.ccRefer().dcVBCompressor.ccIsContacted();
    
    //-- output ** vb
    cmVBAutoHOOK.ccHook(SubVCombustDelegator.mnVBurnerAutoSW);
    SubVCombustDelegator.mnVBurnerAutoPL=cmVBAutoHOOK.ccIsHooked();
    dcVBunerDegree.ccSetupAction(
      MainSimulator.ccSelectAutoMode(true, cmVBAutoHOOK.ccIsHooked(),
        SubVCombustDelegator.mnVBurnerCloseSW, false),
      MainSimulator.ccSelectAutoMode(true, cmVBAutoHOOK.ccIsHooked(),
        SubVCombustDelegator.mnVBurnerOpenSW, false)
    );
    SubAnalogDelegator.mnVBDegreeAD=dcVBunerDegree.ccGetValue();
    SubVCombustDelegator.mnVBurnerClosePL=dcVBunerDegree.ccIsClosing();
    SubVCombustDelegator.mnVBurnerOpenPL=dcVBunerDegree.ccIsOpening();
    
    //-- output ** ve
    cmVEAutoHOOK.ccHook(SubVCombustDelegator.mnVExfanAutoSW);
    SubVCombustDelegator.mnVExfanAutoPL=cmVEAutoHOOK.ccIsHooked();
    dcVExfanDegree.ccSetupAction(
      MainSimulator.ccSelectAutoMode(true, cmVEAutoHOOK.ccIsHooked(),
        SubVCombustDelegator.mnVExfanCloseSW, false),
      MainSimulator.ccSelectAutoMode(true, cmVEAutoHOOK.ccIsHooked(),
        SubVCombustDelegator.mnVExfanOpenSW, false)
    );
    SubAnalogDelegator.mnVEDegreeAD=dcVExfanDegree.ccGetValue();
    SubVCombustDelegator.mnVExfanClosePL=dcVExfanDegree.ccIsClosing();
    SubVCombustDelegator.mnVExfanOpenPL=dcVExfanDegree.ccIsOpening();
    
  }//+++

  @Override public void ccSimulate() {
    
    //-- damper
    dcVBunerDegree.ccSimulate();
    dcVExfanDegree.ccSimulate();
  
  }//+++
  
  //===
  
  @Deprecated public final void tstTagg(){
    VcLocalTagger.ccTag("vd?", dcVExfanDegree);
  }//***
  
}//***eof
