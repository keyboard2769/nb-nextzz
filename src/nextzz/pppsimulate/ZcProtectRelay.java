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
  
  private final ZcChainController cmDrum = new ZcChainController(1, 10);
  private final ZcTimer cmPostPurgeTimer = new ZcOnDelayTimer(40);
  private final ZcPulser cmStartPLS = new ZcPulser();
  private final ZcPulser cmLockoutPLS = new ZcPulser();
  
  private boolean
    cmIsRunning=false,
    cmIsReady=false,
    cmDoesStep=false,
    cmIsLockedOut=false,
    cmIsPosting=false
  ;//...
  
  //===
  
  public final void ccRun(){
    
    //-- **
    
    if(cmLockoutPLS.ccUpPulse(cmIsLockedOut)){
      ccStop();
    }//..?
    
    cmDrum.ccSetupRunStatus(false, cmDoesStep);
    cmDrum.ccRun();
    
    //-- 0..stop
    
    //-- 1..remain
    
    //-- 2..fan start 
    
    //-- 3..fan open
    
    //-- 4..fan close
    
    //-- 5..ignition
    
    //-- 6..pilot
    
    //-- 7..valve
    
    //-- 8..holding
    
    //-- post purge
    //[head]:: now what...and how?
    
    
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
        cmIsRunning=true;
        cmDoesStep=true;
        cmDrum.ccSetConfirmedAt(10, true);
      }//..?
    }else{
      if(lpPLS){
        cmIsRunning=false;
        cmDoesStep=false;
        cmDrum.ccForceStop();
        cmIsPosting=true;
      }//..?
    }//..?
  }//+++
  
  public final void ccSetReadyCondition(boolean pxInput){
    cmIsReady=pxInput;
  }//+++
  
  public final void ccSetDamperCloseConfirm(boolean pxInput){
    if(ccIsAtBUC()){
      cmDoesStep=pxInput;
    }//..?
  }//+++
  
  public final void ccSetDamperOpenConfirm(boolean pxInput){
    if(ccIsAtBUO()){
      cmDoesStep=pxInput;
    }//..?
  }//+++
  
  public final void ccSetFanStartConfirm(boolean pxInput){
    if(cmDrum.ccIsInRangeOf(2,9)){
      cmDoesStep=pxInput;
      cmIsLockedOut=!pxInput;
    }//..?
  }//+++
  
  public final void ccSetFlameConfirm(boolean pxInput){
    if(cmDrum.ccGetOutputFor(8)){
      if(!pxInput){cmIsLockedOut=true;}
    }else
    if(cmDrum.ccGetOutputWith(2, 4)){
      if(pxInput){cmIsLockedOut=true;}
    }//..?
  }//+++
  
  public final void ccClearLock(boolean pxPullButton){
    if(pxPullButton){
      if(cmIsLockedOut){cmIsLockedOut=false;}//..?
    }//..?
  }//+++
  
  public final void ccForceLock(){
    cmIsLockedOut=true;
  }//+++
  
  public final void ccStop(){
    cmIsRunning=false;
    cmDoesStep=false;
    cmDrum.ccForceStop();
  }//+++
  
  //===
  
  public final boolean ccGetReadyLamp(){
    return false;
  }//+++
  
  public final boolean ccGetRunLamp(boolean pxClock){
    return false;
  }//+++
  
  public final boolean ccGetRunLamp(){
    return ccGetRunLamp(MainSimulator.ccOneSecondClock());
  }//+++
  
  public final boolean ccGetFanStartSignal(){
    return cmDrum.ccGetOutputWith(1,9);
  }//+++
  
  public final boolean ccIsAtBUO(){
    return cmDrum.ccGetOutputAt(3);
  }//+++
  
  public final boolean ccIsAtBUC(){
    return cmDrum.ccGetOutputAt(4);
  }//+++
  
  public final boolean ccIsAtIG(){
    return cmDrum.ccGetOutputWith(5, 6);
  }//+++
  
  public final boolean ccIsAtPV(){
    return cmDrum.ccGetOutputAt(6);
  }//+++
  
  public final boolean ccIsAtMV(){
    return cmDrum.ccGetOutputAt(7);
  }//+++
  
  public final boolean ccIsLockedOut(){
    return cmIsLockedOut;
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
    lpRes.append('$');
    lpRes.append(cmDrum.toString());
    return lpRes.toString();
  }//+++
  
}//***eof
