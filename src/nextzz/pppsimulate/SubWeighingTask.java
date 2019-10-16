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
  
  
  private final ZcCellGateController
    cmAGCellGateCTRL = new ZcCellGateController(7),
    cmFRCellGateCTRL = new ZcCellGateController(3),
    cmASCellGateCTRL = new ZcCellGateController(3);
  
  private final ZcHookFlicker
    cmAGDischargeManualHOOK = new ZcHookFlicker(),
    cmFRDischargeManualHOOK = new ZcHookFlicker(),
    cmASDischargeManualHOOK = new ZcHookFlicker();
  
  private final ZcStorerController cmMixerDischargeCTRL
    = new ZcStorerController(4,48,4);
  
  @Override public void ccScan(){
    
    //-- ??? 
    cmAGCellGateCTRL.ccSetupControlValues(
      SubAnalogDelegator.mnAGCellAD,
      SubWeighingDelegator.mnAGWeighLevelTargetAD,
      SubWeighingDelegator.mnAGWeighLevel
    );
    cmAGCellGateCTRL.ccRun(SubWeighingDelegator.mnAGDischargeRequest);
    SubWeighingDelegator.mnAGWeighConfirm=cmAGCellGateCTRL
      .ccGetWeightConfirmFlag();
    SubWeighingDelegator.mnAGDischargeConfirm=cmAGCellGateCTRL
      .ccGetDischargeConfirmFlag();
    for(int i=7;i>=1;i--){dcDesAGGate[i]=cmAGCellGateCTRL.ccGetGateOpenFlagAt(i);}
    
    //-- ??? 
    cmFRCellGateCTRL.ccSetupControlValues(
      SubAnalogDelegator.mnFRCellAD,
      SubWeighingDelegator.mnFRWeighLevelTargetAD,
      SubWeighingDelegator.mnFRWeighLevel
    );
    cmFRCellGateCTRL.ccRun(SubWeighingDelegator.mnFRDischargeRequest);
    SubWeighingDelegator.mnFRWeighConfirm=cmFRCellGateCTRL
      .ccGetWeightConfirmFlag();
    SubWeighingDelegator.mnFRDischargeConfirm=cmFRCellGateCTRL
      .ccGetDischargeConfirmFlag();
    for(int i=3;i>=1;i--){dcDesFRGate[i]=cmFRCellGateCTRL.ccGetGateOpenFlagAt(i);}
    
    //-- ??? 
    cmASCellGateCTRL.ccSetupControlValues(
      SubAnalogDelegator.mnASCellAD,
      SubWeighingDelegator.mnASWeighLevelTargetAD,
      SubWeighingDelegator.mnASWeighLevel
    );
    cmASCellGateCTRL.ccRun(SubWeighingDelegator.mnASDischargeRequest);
    SubWeighingDelegator.mnASWeighConfirm=cmASCellGateCTRL
      .ccGetWeightConfirmFlag();
    SubWeighingDelegator.mnASDischargeConfirm=cmASCellGateCTRL
      .ccGetDischargeConfirmFlag();
    for(int i=3;i>=1;i--){dcDesASGate[i]=cmASCellGateCTRL.ccGetGateOpenFlagAt(i);}
    
    //-- ???
    cmAGDischargeManualHOOK.ccHook(SubWeighingDelegator.mnAGCellDischargeSW,
      SubWeighingDelegator.mnAutoWeighing);
    cmFRDischargeManualHOOK.ccHook(SubWeighingDelegator.mnFRCellDischargeSW,
      SubWeighingDelegator.mnAutoWeighing);
    cmASDischargeManualHOOK.ccHook(SubWeighingDelegator.mnASCellDischargeSW,
      SubWeighingDelegator.mnAutoWeighing);
    
    //-- ???
    dcDesAGGate[0]=MainSimulator.ccSelectModeSolo(
      !SubWeighingDelegator.mnAGCellLockSW,
      SubWeighingDelegator.mnAutoWeighing,cmAGCellGateCTRL.ccGetGateOpenFlagAt(0),
      cmAGDischargeManualHOOK.ccIsHooked()
    );
    dcDesFRGate[0]=MainSimulator.ccSelectModeSolo(
      !SubWeighingDelegator.mnFRCellLockSW,
      SubWeighingDelegator.mnAutoWeighing,cmFRCellGateCTRL.ccGetGateOpenFlagAt(0),
      cmFRDischargeManualHOOK.ccIsHooked()
    );
    dcDesASGate[0]=MainSimulator.ccSelectModeSolo(
      !SubWeighingDelegator.mnASCellLockSW,
      SubWeighingDelegator.mnAutoWeighing,cmASCellGateCTRL.ccGetGateOpenFlagAt(0),
      cmASDischargeManualHOOK.ccIsHooked()
    );
    
    //-- ???
    SubWeighingDelegator.mnAGwPLnVI=dcDesAGGate[6];
    SubWeighingDelegator.mnAGwPLnV=dcDesAGGate[5];
    SubWeighingDelegator.mnAGwPLnIV=dcDesAGGate[4];
    SubWeighingDelegator.mnAGwPLnIII=dcDesAGGate[3];
    SubWeighingDelegator.mnAGwPLnII=dcDesAGGate[2];
    SubWeighingDelegator.mnAGwPLnI=dcDesAGGate[1];
    //-- ???
    SubWeighingDelegator.mnFRwPLnIII=dcDesFRGate[3];
    SubWeighingDelegator.mnFRwPLnII=dcDesFRGate[2];
    SubWeighingDelegator.mnFRwPLnI=dcDesFRGate[1];
    //-- ???
    SubWeighingDelegator.mnASwPLnIII=dcDesASGate[3];
    SubWeighingDelegator.mnASwPLnII=dcDesASGate[2];
    SubWeighingDelegator.mnASwPLnI=dcDesASGate[1];
    
    //-- ???
    SubWeighingDelegator.mnAGCellDischargePL=dcDesAGGate[0];
    SubWeighingDelegator.mnFRCellDischargePL=dcDesFRGate[0];
    SubWeighingDelegator.mnASCellDischargePL=dcDesASGate[0];
    
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
    
    //-- mixer content
    if(dcMixerGate.ccIsClosed()
      &&(dcDesAGGate[0]
         ||dcDesFRGate[0]
         ||dcDesASGate[0]
    )){SubWeighingDelegator.mnMixerHasContentPL=true;}
    if(!dcMixerGate.ccIsClosed()){
      SubWeighingDelegator.mnMixerHasContentPL=false;
    }//..?
    
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
    
    //-- cell
    SubAnalogDelegator.mnAGCellAD=dcAGCell.ccGetScaledValue(4000)+397;
    SubAnalogDelegator.mnFRCellAD=dcFRCell.ccGetScaledValue(4000)+388;
    SubAnalogDelegator.mnASCellAD=dcASCell.ccGetScaledValue(4000)+391;
    
  }//+++

  @Override public void ccSimulate(){
    
    //-- ???
    for(int i=6;i>=1;i--){dcAGCell.ccCharge(50, dcDesAGGate[i]);}
    dcAGCell.ccDischarge(100, 
      SubVProvisionDelegator.mnAssistSWnII
      || dcDesAGGate[0]
    );
    
    //-- ???
    for(int i=3;i>=1;i--){dcFRCell.ccCharge(50, dcDesFRGate[i]);}
    dcFRCell.ccDischarge(100, 
      SubVProvisionDelegator.mnAssistSWnIV
      || dcDesFRGate[0]
    );
    
    //-- ???
    for(int i=3;i>=1;i--){dcASCell.ccCharge(50, dcDesASGate[i]);}
    dcASCell.ccDischarge(100, 
      SubVProvisionDelegator.mnAssistSWnVI
      || dcDesASGate[0]
    );
    
    //-- mixer gate
    dcMixerGate.ccSimulate
      (SubVProvisionTask.ccRefer().dcVCompressor.ccIsContacted());
    
  }//+++
  
  //=== 
  
  @Deprecated public final void tstTagg(){
    VcLocalTagger.ccTag("fr-w?",cmFRCellGateCTRL);
    VcLocalTagger.ccTag("as-w?",cmASCellGateCTRL);
  }//+++
  
}//***eof
