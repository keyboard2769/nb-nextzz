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
import nextzz.pppdelegate.SubAnalogDelegator;
import nextzz.pppdelegate.SubVCombustDelegator;
import processing.core.PApplet;

public final class SubVCombusTask implements ZiTask{
  
  private static SubVCombusTask self = null;
  public static final SubVCombusTask ccRefer(){
    if(self==null){self=new SubVCombusTask();}
    return self;
  }//+++
  private SubVCombusTask(){}//..!
  
  //===
  
  //-- motor
  public final ZcMotor dcVBurnerFan = new ZcMotor(4);
  public final ZcCombustor dcVOilPump = new ZcCombustor();
  
  //-- damper
  public final ZcGate dcVBunerDegree = new ZcGate();
  public final ZcGate dcVExfanDegree = new ZcGate();
  private final ZcHookFlicker cmVBAutoHOOK = new ZcHookFlicker();
  private final ZcHookFlicker cmVEAutoHOOK = new ZcHookFlicker();
  
  //-- pressure
  private boolean dcVBurnerPressureLS,dcVExfanPressureLS;
  private final ZcReal 
    simAtomsphere      = new ZcReal(0.6f,true),
    simVBurnerPressure = new ZcReal(0.75f,true),
    simVDryerPressure  = new ZcReal(1.2f),
    simVExfanPressure  = new ZcReal(-0.75f,true)
  ;//...
  
  //-- controller
  private final ZcProtectRelay cmLFL = new ZcProtectRelay();
  
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
          /* 4 */ false)),
      MainSimulator.ccSelectModeSolo(true,
        !cmVBAutoHOOK.ccIsHooked(),SubVCombustDelegator.mnVBurnerOpenSW,
        MainSimulator.ccSelectModeSolo(true,
          !dcVOilPump.ccIsOnFire(),cmLFL.ccGetDamperOpenSignal(), 
          /* 4 */ false))
    );
    SubAnalogDelegator.mnVBDegreeAD=dcVBunerDegree.ccGetValue();
    SubVCombustDelegator.mnVBurnerClosePL=dcVBunerDegree.ccIsClosing();
    SubVCombustDelegator.mnVBurnerOpenPL=dcVBunerDegree.ccIsOpening();
    
    //-- output ** ve
    cmVEAutoHOOK.ccHook(SubVCombustDelegator.mnVExfanAutoSW);
    SubVCombustDelegator.mnVExfanAutoPL=cmVEAutoHOOK.ccIsHooked();
    dcVExfanDegree.ccSetupAction(
      MainSimulator.ccSelectModeSolo(true, cmVEAutoHOOK.ccIsHooked(),
        false, SubVCombustDelegator.mnVExfanCloseSW),
      MainSimulator.ccSelectModeSolo(true, cmVEAutoHOOK.ccIsHooked(),
        false, SubVCombustDelegator.mnVExfanOpenSW)
    );
    SubAnalogDelegator.mnVEDegreeAD=dcVExfanDegree.ccGetValue();
    SubVCombustDelegator.mnVExfanClosePL=dcVExfanDegree.ccIsClosing();
    SubVCombustDelegator.mnVExfanOpenPL=dcVExfanDegree.ccIsOpening();
    
    //-- pressure ** output
    SubVCombustDelegator.mnVBFanHasPressurePL=dcVBurnerPressureLS;
    SubVCombustDelegator.mnVEFanHasPressurePL=dcVExfanPressureLS;
    SubAnalogDelegator.mnVDPressureAD
      = (int)(PApplet.map(simVDryerPressure.ccGet(), 0f, 200f, 1500f, 2500f));
    
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
    simVBurnerPressure.ccEffect(
      dcVBurnerFan.ccIsContacted()?
        ( 300f*dcVBunerDegree.ccGetProportion()+16f)
        :simAtomsphere.ccGet()
    );
    simVExfanPressure.ccEffect(
      SubVProvisionTask.ccRefer().dcVExFan.ccIsContacted()?
        (-320f*dcVExfanDegree.ccGetProportion()-10f)
        :simAtomsphere.ccGet()
    );
    ZcReal.ccTransfer(simVDryerPressure, simAtomsphere);
    ZcReal.ccTransfer(simVDryerPressure, simVBurnerPressure);
    ZcReal.ccTransfer(simVDryerPressure, simVExfanPressure);
    dcVExfanPressureLS=simVExfanPressure.ccGet() < (-7f);
    dcVBurnerPressureLS=simVBurnerPressure.ccGet() > 3f;
    
  }//+++
  
  //===
  
  @Deprecated public final void tstTagg(){
    VcLocalTagger.ccTag("LFL?", cmLFL);
    VcLocalTagger.ccTag("vb-p?", simVBurnerPressure.ccGet());
    VcLocalTagger.ccTag("ve-p?", simVExfanPressure.ccGet());
    VcLocalTagger.ccTag("vb-m", dcVBurnerFan);
    VcLocalTagger.ccTag("fire?", dcVOilPump.ccIsOnFire());
  }//***
  
}//***eof
