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
  
  private final ZcStorerController cmMixerDischargeCTRL
    = new ZcStorerController(4,48,4);
  
  @Override public void ccScan(){
    
    //-- mixe discharge auto
    cmMixerDischargeCTRL.ccSetupSuperiorLayer
      (SubWeighingDelegator.mnMixerAutoDischargeRequire);
    cmMixerDischargeCTRL.ccSetupSelfLayer(
      dcMixerGate.ccIsClosed(),
      dcMixerGate.ccIsFullOpened()
    );
    cmMixerDischargeCTRL.ccRun();
    if(cmMixerDischargeCTRL.ccIsOpenConfrimed()){
      SubWeighingDelegator.mnMixerAutoDischargeRequire=false;
    }//..?
    if(cmMixerDischargeCTRL.ccIsAtPost()){
      SubWeighingDelegator.mnMixerDischargedConfirm=true;
    }//..?

    //-- ???
    SubWeighingDelegator.mnAGwPLnVI=SubWeighingDelegator.mnAGCurrentMattLevel==6;
    SubWeighingDelegator.mnAGwPLnV=SubWeighingDelegator.mnAGCurrentMattLevel==5;
    SubWeighingDelegator.mnAGwPLnIV=SubWeighingDelegator.mnAGCurrentMattLevel==4;
    SubWeighingDelegator.mnAGwPLnIII=SubWeighingDelegator.mnAGCurrentMattLevel==3;
    SubWeighingDelegator.mnAGwPLnII=SubWeighingDelegator.mnAGCurrentMattLevel==2;
    SubWeighingDelegator.mnAGwPLnI=SubWeighingDelegator.mnAGCurrentMattLevel==1;
    //-- ???
    SubWeighingDelegator.mnFRwPLnIII=SubWeighingDelegator.mnFRCurrentMattLevel==3;
    SubWeighingDelegator.mnFRwPLnII=SubWeighingDelegator.mnFRCurrentMattLevel==2;
    SubWeighingDelegator.mnFRwPLnI=SubWeighingDelegator.mnFRCurrentMattLevel==1;
    //-- ???
    SubWeighingDelegator.mnASwPLnII=SubWeighingDelegator.mnASCurrentMattLevel==2;
    
    //-- mixer gate 
    dcMixerGate.ccSetupAction(
      MainSimulator.ccSelectModeForce(SubWeighingDelegator.mnMixerGateHoldSW,
        SubWeighingDelegator.mnMixerGateOpenSW,
        cmMixerDischargeCTRL.ccGetGateOpenOutput()
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
    VcLocalTagger.ccTag("m-ctrl?",cmMixerDischargeCTRL);
    VcLocalTagger.ccTag("m-g?",dcMixerGate);
  }//+++
  
}//***eof
