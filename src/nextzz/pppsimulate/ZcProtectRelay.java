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

import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZcPulser;
import kosui.ppplogic.ZcTimer;
import kosui.ppputil.VcStringUtility;

public final class ZcProtectRelay{
  
  public static final int C_STAGE_INTERVAL_S = 1;
  public static final int C_STAGE_MAX        = 10;
  //--
  public static final int C_S_Z_STOP    = 0;
  public static final int C_S_I_REMAIN  = 1;
  public static final int C_S_II_FSTART = 2;
  public static final int C_S_III_DOPEN = 3;
  public static final int C_S_IV_DCLOSE = 4;
  public static final int C_S_V_INITION = 5;
  public static final int C_S_VI_PILOTV = 6;
  public static final int C_S_VII_MAINV = 7;
  public static final int C_S_VIII_HOLD = 8;
  
  //===
  
  private final ZcChainController cmDrum
    = new ZcChainController(C_STAGE_INTERVAL_S, C_STAGE_MAX);
  
  private final ZcTimer cmPostPurgeTimer = new ZcOnDelayTimer(40);
  private final ZcPulser cmStartPLS = new ZcPulser();
  private final ZcPulser cmLockoutPLS = new ZcPulser();
  
  private int cmLastDrumStage = 0;
  private boolean
    cmIsReady=false,
    cmDoesStep=false,
    cmIsLockedOut=false,
    cmIsPosting=false
  ;//...
  
  //===
  
  public final void ccRun(){
    
    //-- lock out
    if(cmLockoutPLS.ccUpPulse(cmIsLockedOut)){
      ccStop();
    }//..?
    
    //-- run drum
    cmDrum.ccSetupRunStatus(false, cmDoesStep);
    cmDrum.ccRun();
    
    //-- post purge
    cmPostPurgeTimer.ccAct(cmIsPosting);
    if(cmPostPurgeTimer.ccIsUp()){
      cmIsPosting=false;
    }//+++
    
  }//+++
  
  public void ccRun(boolean pxInput){
    ccSetRun(pxInput);
    ccRun();
  }//+++

  //===
  
  public final void ccSetRun(boolean pxInput){
    if(!cmIsReady){return;}
    if(cmIsLockedOut){return;}
    boolean lpPLS=cmStartPLS.ccUpPulse(pxInput);
    if(cmDrum.ccIsAllStopped()){
      if(lpPLS){
        cmDoesStep=true;
        cmDrum.ccSetConfirmedAt(C_STAGE_MAX, true);
      }//..?
    }else{
      if(lpPLS){
        ccStop();
        cmIsPosting=true;
      }//..?
    }//..?
  }//+++
  
  public final void ccSetReadyCondition(boolean pxInput){
    cmIsReady=pxInput;
    if(!cmDrum.ccIsAllStopped() && !cmIsReady){
      ccForceLock();
    }//..?
  }//+++
  
  public final void ccSetDamperCloseConfirm(boolean pxInput){
    if(cmDrum.ccGetOutputAt(C_S_IV_DCLOSE)){
      cmDoesStep=pxInput;
    }//..?
  }//+++
  
  public final void ccSetDamperOpenConfirm(boolean pxInput){
    if(cmDrum.ccGetOutputAt(C_S_III_DOPEN)){
      cmDoesStep=pxInput;
    }//..?
  }//+++
  
  public final void ccSetFanStartConfirm(boolean pxInput){
    if(cmDrum.ccGetOutputWith(C_S_II_FSTART,C_STAGE_MAX)){
      cmDoesStep&=pxInput;
      if(cmDrum.ccGetOutputWith(C_S_II_FSTART,C_STAGE_MAX) && !pxInput){
        ccForceLock();
      }//..?
    }//..?
  }//+++
  
  public final void ccSetFlameConfirm(boolean pxInput){
    if(cmDrum.ccGetOutputFor(C_S_VIII_HOLD)){
      if(!pxInput){ccForceLock();}
    }else
    if(cmDrum.ccGetOutputWith(C_S_II_FSTART, C_S_IV_DCLOSE)){
      if(pxInput){ccForceLock();}
    }//..?
  }//+++
  
  public final void ccClearLock(boolean pxPullButton){
    if(pxPullButton){
      if(cmIsLockedOut){
        cmIsLockedOut=false;
        cmLastDrumStage=0;
      }//..?
    }//..?
  }//+++
  
  public final void ccForceLock(){
    cmLastDrumStage=cmDrum.ccGetCurrentLevel();
    cmIsLockedOut=true;
    cmIsPosting=true;
  }//+++
  
  public final void ccStop(){
    cmDoesStep=false;
    cmDrum.ccForceStop();
  }//+++
  
  //===
  
  public final boolean ccGetReadyLamp(){
    return ccGetReadyLamp(MainSimulator.ccOneSecondClock());
  }//+++
  
  public final boolean ccGetReadyLamp(boolean pxClock){
    return cmIsPosting?pxClock:(cmIsReady&&cmDrum.ccIsAllStopped());
  }//+++
  
  public final boolean ccGetRunLamp(boolean pxClock){
    return cmDrum.ccIsAllStopped()?false
      :cmDrum.ccIsAllEngaged()?true
      :pxClock;
  }//+++
  
  public final boolean ccGetRunLamp(){
    return ccGetRunLamp(MainSimulator.ccOneSecondClock());
  }//+++
  
  public final boolean ccGetLockedOutLamp(){
    return cmIsLockedOut;
  }//+++
  
  public final boolean ccGetFanStartSignal(){
    return cmDrum.ccGetOutputWith(C_S_I_REMAIN,C_STAGE_MAX)||cmIsPosting;
  }//+++
  
  public final boolean ccGetDamperOpenSignal(){
    return cmDrum.ccGetOutputAt(C_S_III_DOPEN);
  }//+++
  
  public final boolean ccGetDamperCloseSignal(){
    return cmDrum.ccGetOutputAt(C_S_IV_DCLOSE);
  }//+++
  
  public final boolean ccGetIgnitionSignal(){
    return cmDrum.ccGetOutputWith(C_S_V_INITION, C_S_VI_PILOTV);
  }//+++
  
  public final boolean ccGetPilotValveSignal(){
    return cmDrum.ccGetOutputWith(C_S_VI_PILOTV,C_S_VII_MAINV);
  }//+++
  
  public final boolean ccGetMainValveSignal(){
    return cmDrum.ccGetOutputFor(C_S_VII_MAINV);
  }//+++
  
  //===

  @Override public String toString(){
    StringBuilder lpRes = new StringBuilder();
    lpRes.append(ZcProtectRelay.class.getSimpleName());
    lpRes.append('@');
    lpRes.append(Integer.toHexString(hashCode()));
    lpRes.append('$');
    lpRes.append(VcStringUtility.ccPackupBoolTag("ready", cmIsReady));
    lpRes.append(VcStringUtility.ccPackupBoolTag("run", cmDoesStep));
    lpRes.append('|');
    lpRes.append(VcStringUtility.ccPackupPairedTag("lock", cmLastDrumStage));
    lpRes.append('$');
    lpRes.append(cmDrum.toString());
    return lpRes.toString();
  }//+++
  
}//***eof
