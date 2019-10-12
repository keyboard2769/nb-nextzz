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

import kosui.ppplogic.ZiTask;
import kosui.ppputil.VcLocalTagger;
import nextzz.pppdelegate.SubAnalogDelegator;
import nextzz.pppdelegate.SubVProvisionDelegator;
import nextzz.pppdelegate.SubWeighingDelegator;

public class SubWeighingTask implements ZiTask{
  
  private static SubWeighingTask self = null;
  public static final SubWeighingTask ccRefer(){
    if(self==null){self = new SubWeighingTask();}
    return self;
  }//+++
  private SubWeighingTask(){}//..!
  
  //===
  
  public final ZcGate dcMixerGate = new ZcGate(96);
  
  private final boolean[] dcDesAGGate = new boolean[8];
  final ZcContainer dcAGCell = new ZcContainer();
  
  private final boolean[] dcDesFRGate = new boolean[4];
  final ZcContainer dcFRCell = new ZcContainer();
  
  private final boolean[] dcDesASGate = new boolean[4];
  final ZcContainer dcASCell = new ZcContainer();
  
  
  /* 6 */private int dtfmMixerContoller = 0;
  
  @Override public void ccScan(){
    
    //-- ???
    if(SubWeighingDelegator.mnMixerAutoDischargeFlag){
      if(dtfmMixerContoller==0){dtfmMixerContoller=1;}
    }//..?
    if(dcMixerGate.ccIsFullOpened()){
      if(dtfmMixerContoller==1){
        dtfmMixerContoller=2;
        SubWeighingDelegator.mnMixerDischargeConfirmFlag=true;
      }
    }//..?
    if(dcMixerGate.ccIsClosed()){
      if(dtfmMixerContoller==2){
        dtfmMixerContoller=0;
      }
    }//..?
    
    
    //-- mixer gate 
    dcMixerGate.ccSetupAction(
      MainSimulator.ccSelectModeForce(
        SubWeighingDelegator.mnMixerGateHoldSW,
        SubWeighingDelegator.mnMixerGateOpenSW,
        SubWeighingDelegator.mnMixerAutoDischargeFlag
      )
    );
    SubWeighingDelegator.mnMixerGateFB
      = (dcMixerGate.ccIsOpening()&&dcMixerGate.ccIsFullOpened())?true
       :(!dcMixerGate.ccIsOpening()&&dcMixerGate.ccIsClosed())?true
       :MainSimulator.ccHalfSecondClock();
    SubWeighingDelegator.mnMixerGateClosedPL
      = dcMixerGate.ccIsClosed() && !dcMixerGate.ccIsFullOpened();
    
    //-- feedback
    SubAnalogDelegator.mnAGCellAD=dcAGCell.ccGetScaledValue(4000);
    SubAnalogDelegator.mnFRCellAD=dcFRCell.ccGetScaledValue(4000);
    SubAnalogDelegator.mnASCellAD=dcASCell.ccGetScaledValue(4000);
    
  }//+++

  @Override public void ccSimulate(){
    
    //-- ???
    dcAGCell.ccCharge(50, SubVProvisionDelegator.mnAssistSWnI);
    dcAGCell.ccDischarge(100, SubVProvisionDelegator.mnAssistSWnII);
    dcFRCell.ccCharge(50, SubVProvisionDelegator.mnAssistSWnIII);
    dcFRCell.ccDischarge(100, SubVProvisionDelegator.mnAssistSWnIV);
    dcASCell.ccCharge(50, SubVProvisionDelegator.mnAssistSWnV);
    dcASCell.ccDischarge(100, SubVProvisionDelegator.mnAssistSWnVI);
    
    //-- mixer gate
    dcMixerGate.ccSimulate
      (SubVProvisionTask.ccRefer().dcVCompressor.ccIsContacted());
    
  }//+++
  
  //=== 
  
  @Deprecated public final void tstTagg(){
    VcLocalTagger.ccTag("ag-cell?",dcAGCell);
    VcLocalTagger.ccTag("m-g?",dcMixerGate);
  }//+++
  
}//***eof
