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
import kosui.ppplogic.ZcReal;
import kosui.ppplogic.ZiTask;
import kosui.ppputil.VcLocalTagger;
import kosui.ppputil.VcNumericUtility;
import nextzz.pppdelegate.SubAnalogDelegator;
import nextzz.pppdelegate.SubVCombustDelegator;

public final class SubVCombusTask implements ZiTask{
  
  private static SubVCombusTask self = null;
  public static final SubVCombusTask ccRefer(){
    if(self==null){self=new SubVCombusTask();}
    return self;
  }//+++
  private SubVCombusTask(){}//++!
  
  //===
  
  //-- motor
  public final ZcMotor dcVBurnerFan = new ZcMotor(4);
  public final ZcCombustor dcVOilPump = new ZcCombustor();
  
  //-- damper
  private boolean dcCoolingDamperMV;
  public final ZcGate dcVBunerDegree = new ZcGate();
  public final ZcGate dcVExfanDegree = new ZcGate();
  private final ZcHookFlicker cmVBAutoHOOK = new ZcHookFlicker(true);
  private final ZcHookFlicker cmVEAutoHOOK = new ZcHookFlicker(true);
  
  //-- controller
  private final ZcProtectRelay cmLFL = new ZcProtectRelay();
  
  //-- real 
  //-- real ** pressure
  private boolean dcVBurnerPressureLS,dcVExfanPressureLS;
  private final ZcReal 
    simAtomsphereKPA = new ZcReal( 0.6f,true),
    simVBurnerKPA    = new ZcReal( 0.75f,true),
    simVDryerKPA     = new ZcReal( 1.2f),
    simVExfanKPA     = new ZcReal(-0.75f,true)
  ;//,,,
  //-- real ** temperature
  private final ZcReal
    simAtomsphereCELC  = new ZcReal(27f, true),
    simBurnerBlastCELC = new ZcReal(32f, true),
    simDryerBodyCELC   = new ZcReal(34f),
    //--
    simDryerChuteCELC  = new ZcReal(16f),
    simBagEntranceCELC = new ZcReal(12f),
    simSandBinCELC     = new ZcReal(14f)
  ;//,,,
  
  //===

  @Override public void ccScan() {
    
    //-- THE LFL
    cmLFL.ccSetReadyCondition(
        SubVProvisionTask.ccRefer().dcInclinedBelcon.ccIsContacted()
      &&SubVProvisionTask.ccRefer().dcVBCompressor.ccIsContacted()//[todo]::gas
      &&dcVExfanPressureLS
    );
    cmLFL.ccSetFanStartConfirm(dcVBurnerPressureLS);
    cmLFL.ccSetDamperOpenConfirm(dcVBunerDegree.ccIsFullOpened());
    cmLFL.ccSetDamperCloseConfirm(dcVBunerDegree.ccIsClosed());
    cmLFL.ccSetFlameConfirm(dcVOilPump.ccIsOnFire());
    //[todo]::..ccClearLock
    cmLFL.ccRun(SubVCombustDelegator.mnVCombustRunSW);
    SubVCombustDelegator.mnVCombustReadyPL
      = cmLFL.ccGetReadyLamp();
    SubVCombustDelegator.mnVCombustRunPL
      = cmLFL.ccGetRunLamp();
    SubVCombustDelegator.mnVBIgnitionPL=cmLFL.ccGetIgnitionSignal();
    SubVCombustDelegator.mnVBPilotValvePL=cmLFL.ccGetPilotValveSignal();
    SubVCombustDelegator.mnVBMainValvePL=cmLFL.ccGetMainValveSignal();
    dcVBurnerFan.ccContact(cmLFL.ccGetFanStartSignal());
    dcVOilPump.ccSetPilot(cmLFL.ccGetPilotValveSignal());
    dcVOilPump.ccSetIgniter(cmLFL.ccGetIgnitionSignal());
    dcVOilPump.ccContact(SubVCombustDelegator.mnVBMainValvePL);
    //[todo]::..%err_xxx% = ccGetLockedout
    
    //-- feedback ** vb
    SubVCombustDelegator.mnVBFlamingPL=dcVOilPump.ccIsOnFire();
    SubVCombustDelegator.mnVBunnerFanPL=dcVBurnerFan.ccIsContacted();
    //[todo]::..CT?? = % pump
    //[todo]::..CT?? = % fan
    
    //-- output ** vb
    cmVBAutoHOOK.ccHook(SubVCombustDelegator.mnVBurnerAutoSW);
    SubVCombustDelegator.mnVBurnerAutoPL=cmVBAutoHOOK.ccIsHooked();
    dcVBunerDegree.ccSetupAction(
      MainSimulator.ccSelectModeSolo(true,
        !cmVBAutoHOOK.ccIsHooked(),SubVCombustDelegator.mnVBurnerCloseSW,
        MainSimulator.ccSelectModeSolo(true,
          !dcVOilPump.ccIsOnFire(),cmLFL.ccGetDamperCloseSignal(),
          SubVCombustDelegator.mnVBurnerCloseFLG)),
      MainSimulator.ccSelectModeSolo(true,
        !cmVBAutoHOOK.ccIsHooked(),SubVCombustDelegator.mnVBurnerOpenSW,
        MainSimulator.ccSelectModeSolo(true,
          !dcVOilPump.ccIsOnFire(),cmLFL.ccGetDamperOpenSignal(), 
          SubVCombustDelegator.mnVBurnerOpenFLG))
    );
    SubAnalogDelegator.mnVBDegreeAD=dcVBunerDegree.ccGetValue();
    SubVCombustDelegator.mnVBurnerClosePL=dcVBunerDegree.ccIsClosing();
    SubVCombustDelegator.mnVBurnerOpenPL=dcVBunerDegree.ccIsOpening();
    
    //-- output ** ve
    cmVEAutoHOOK.ccHook(SubVCombustDelegator.mnVExfanAutoSW);
    SubVCombustDelegator.mnVExfanAutoPL=cmVEAutoHOOK.ccIsHooked();
    dcVExfanDegree.ccSetupAction(
      MainSimulator.ccSelectModeSolo(true,
        cmVEAutoHOOK.ccIsHooked(),SubVCombustDelegator.mnVExfanCloseFLG,
        SubVCombustDelegator.mnVExfanCloseSW),
      MainSimulator.ccSelectModeSolo(true, 
        cmVEAutoHOOK.ccIsHooked(),SubVCombustDelegator.mnVExfanOpenFLG,
        SubVCombustDelegator.mnVExfanOpenSW)
    );
    SubAnalogDelegator.mnVEDegreeAD=dcVExfanDegree.ccGetValue();
    SubVCombustDelegator.mnVExfanClosePL=dcVExfanDegree.ccIsClosing();
    SubVCombustDelegator.mnVExfanOpenPL=dcVExfanDegree.ccIsOpening();
    
    //-- pressure ** output
    SubVCombustDelegator.mnVBFanHasPressurePL=dcVBurnerPressureLS;
    SubVCombustDelegator.mnVEFanHasPressurePL=dcVExfanPressureLS;
    SubAnalogDelegator.mnVDPressureAD
      = MainSimulator.ccDecodePressure(simVDryerKPA.ccGet());
    
    //-- temperature ** output
    SubAnalogDelegator.mnTHnII
      = MainSimulator.ccDecodeTemperature(simBagEntranceCELC.ccGet());
    SubAnalogDelegator.mnTHnI
      = MainSimulator.ccDecodeTemperature(simDryerChuteCELC.ccGet());
    
  }//+++

  @Override public void ccSimulate() {
    
    //-- damper
    dcVBunerDegree.ccSimulate();
    dcVExfanDegree.ccSimulate();
    
    //-- motor
    dcVOilPump.ccSimulate(dcVBunerDegree.ccGetProportion()/4f+0.35f);
    dcVBurnerFan.ccSimulate(
      dcVBunerDegree.ccGetProportion()/(dcVOilPump.ccIsOnFire()?2f:4f)
       + 0.45f
    );
    
    //-- pressure
    simAtomsphereKPA.ccEffect(VcNumericUtility.ccRandom(5f));
    simVBurnerKPA.ccEffect(dcVBurnerFan.ccIsContacted()?
        ( 300f*dcVBunerDegree.ccGetProportion()+16f)
        :simAtomsphereKPA.ccGet()
    );
    simVExfanKPA.ccEffect(SubVProvisionTask.ccRefer().dcVExFan.ccIsContacted()?
        (-320f*dcVExfanDegree.ccGetProportion()-10f)
        :simAtomsphereKPA.ccGet()
    );
    ZcReal.ccTransfer(simVDryerKPA, simAtomsphereKPA, 128);
    ZcReal.ccTransfer(simVDryerKPA, simVBurnerKPA);
    ZcReal.ccTransfer(simVDryerKPA, simVExfanKPA);
    dcVExfanPressureLS=simVExfanKPA.ccGet() < (-7f);
    dcVBurnerPressureLS=simVBurnerKPA.ccGet() > 3f;
    
    //-- temperature
    simAtomsphereCELC.ccEffect(VcNumericUtility.ccRandom(-26f, 3f));
    simBurnerBlastCELC.ccEffect(
      dcVOilPump.ccIsOnFire()
       ? dcVBunerDegree.ccGetProportion()*2900f
       : simAtomsphereKPA.ccGet()
    );
    if(!dcVOilPump.ccIsOnFire()){
      ZcReal.ccTransfer(simBurnerBlastCELC, simAtomsphereCELC);
    }//..?
    if(!dcVOilPump.ccIsOnFire() || dcCoolingDamperMV){
      ZcReal.ccTransfer(simDryerBodyCELC, simAtomsphereCELC);
    }//..?
    ZcReal.ccTransfer(simDryerChuteCELC, simAtomsphereCELC);
    ZcReal.ccTransfer(simBagEntranceCELC, simAtomsphereCELC);
    ZcReal.ccTransfer(simSandBinCELC, simAtomsphereCELC);
    ZcReal.ccTransfer(simBurnerBlastCELC, simDryerBodyCELC, 64);
    ZcReal.ccTransfer(
      simDryerBodyCELC,
      simBagEntranceCELC,
      SubFeederTask.ccRefer().ccGetVFeederConveyorScaleBYTE()/4+8
    );
    if(SubFeederTask.ccRefer().ccGetColdAggregateSensor()){
      ZcReal.ccTransfer(simDryerBodyCELC, simDryerChuteCELC);
    }//..?
    //[todo]::ZcReal.ccTransfer(simDryerChuteCELC, simSandBinCELC);
        
  }//+++
  
  //===
  
  @Deprecated public final void tstTagg(){
    VcLocalTagger.ccTag("@vse", simVDryerKPA.ccGet());
    VcLocalTagger.ccTag("@vse-ad", SubAnalogDelegator.mnVDPressureAD);
    VcLocalTagger.ccTag("@th1", simDryerChuteCELC.ccGet());
    VcLocalTagger.ccTag("@th2", simBagEntranceCELC.ccGet());
    VcLocalTagger.ccTag("@th4", simSandBinCELC.ccGet());
  }//***
  
}//***eof
