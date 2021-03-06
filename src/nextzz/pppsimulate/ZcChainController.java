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

import kosui.ppplocalui.EcConst;
import kosui.ppplogic.ZcCheckedValueModel;
import kosui.ppplogic.ZcLevelComparator;
import kosui.ppplogic.ZcPulser;
import kosui.ppputil.VcStringUtility;

public class ZcChainController extends ZcCheckedValueModel {
  
  private boolean cmRunsUp = false;
  private boolean cmRunsDown = false;
  private final int cmMaxLV;
  private int cmCurrentLV;
  private int cmConfirmedLV=0;
  private final ZcPulser cmEngagePulser = new ZcPulser();
  
  public ZcChainController(int pxIntervalS, int pxMaxLV) {
    super(0, EcConst.C_FPS*(pxIntervalS&0x1F)*ZcLevelComparator.C_LEVEL_MAX);
    cmMaxLV=pxMaxLV;
    cmCurrentLV=ccGetCurrentLevel();
  }//..!
  
  //===
  
  public final void ccRun(){
    
    cmCurrentLV = ccGetCurrentLevel();
    
    //--
    if(cmRunsUp){
      if(cmConfirmedLV>=cmCurrentLV){
        ccShift(1);
      }//..?
      if(ccIsLevelAbove(cmMaxLV)){
        ccSetToLevel(cmMaxLV);
        cmRunsUp=false;
      }//..?
    }//..?
    
    //--
    if(cmRunsDown){
      ccShift(-1);
      if(ccIsLevelAt(0)){
        cmRunsDown=false;
      }//..?
    }//..?
    
  }//+++
  
  public void ccRun(boolean pxRun, boolean pxStop){
    ccSetRun(pxRun, pxStop);
    ccRun();
  }//+++
  
  public void ccRun(boolean pxPulse){
    ccSetRun(pxPulse);
    ccRun();
  }//+++
  
  //===
  
  public final void ccSetRun(boolean pxRun, boolean pxStop){
    if(ccIsLevelAt(0)){
      if(pxRun){cmRunsUp=true;}
    }else{
      if(pxStop){
        cmRunsUp=false;
        cmRunsDown=true;
      }
    }//..?
  }//+++
  
  public final void ccSetRun(boolean pxPulse){
    if(cmEngagePulser.ccUpPulse(pxPulse)){
      if(ccIsLevelAt(0)){
        cmRunsUp=true;
        cmRunsDown=false;
      }else{
        cmRunsUp=false;
        cmRunsDown=true;
      }//..?
    }//..?
  }//+++
  
  public final void ccSetupRunStatus(boolean pxDown, boolean pxUp){
    if(pxDown&&pxUp){return;}
    cmRunsDown=pxDown;
    cmRunsUp=pxUp;
  }//+++
  
  public final void ccSetTrippedAt(int pxLevel, boolean pxStatus){
    if(pxStatus && pxLevel<=cmCurrentLV){
      ccSetToLevel(pxLevel-1);cmRunsUp=false;
    }//..?
  }//+++
  
  public final void ccSetConfirmedAt(int pxLevel, boolean pxStatus){
    if(pxStatus){cmConfirmedLV=pxLevel;}
  }//+++
  
  public final void ccClearConfirmedLevel(){
    cmConfirmedLV=0;
  }//+++
  
  public final void ccForceStop(){
    cmRunsUp=false;
    cmRunsDown=false;
    ccClearConfirmedLevel();
    ccSetValue(0);
  }//+++
  
  //===
  
  public final boolean ccGetOutputFor(int pxLevel){
    return cmCurrentLV>=pxLevel;
  }//+++
  
  public final boolean ccGetOutputAt(int pxLevel){
    return cmCurrentLV==pxLevel;
  }//+++
  
  public final boolean ccGetOutputWith(int pxLowLevel, int pxHighLevel){
    if(pxHighLevel<=pxLowLevel){return false;}
    return cmCurrentLV>=pxLowLevel && cmCurrentLV<=pxHighLevel;
  }//+++
  
  public final boolean ccGetPulseAt(int pxLevel){
    return ccIsValueAt(pxLevel);
  }//+++
  
  //===
  
  public final boolean ccIsAllStopped(){
    return cmValue==0;
  }//+++
  
  public final boolean ccIsTransacting(){
    return 
        cmRunsUp
      ||cmRunsDown
      ||(cmCurrentLV>0 && (cmCurrentLV<cmMaxLV));
  }//+++
  
  public final boolean ccIsAllEngaged(){
    return (cmCurrentLV>=cmMaxLV)
      &&(!cmRunsUp)
      &&(!cmRunsDown);
  }//+++
  
  public final boolean ccGetFlasher(boolean pxClock){
    return
      (!ccIsAllStopped())&&
      (ccIsAllEngaged()||pxClock);
  }//+++
  
  //===

  @Override public String toString() {
    StringBuilder lpRes
      = new StringBuilder(ZcChainController.class.getSimpleName());
    lpRes.append('@');
    lpRes.append(Integer.toHexString(this.hashCode()));
    lpRes.append('$');
    lpRes.append(VcStringUtility.ccPackupPairedTag("V", cmValue));
    lpRes.append(VcStringUtility.ccPackupPairedTag("L", cmCurrentLV));
    lpRes.append(VcStringUtility.ccPackupPairedTag("maxL", cmMaxLV));
    lpRes.append('|');
    lpRes.append(VcStringUtility.ccPackupPairedTag("UP", cmRunsUp));
    lpRes.append(VcStringUtility.ccPackupPairedTag("DN", cmRunsDown));
    return lpRes.toString();
  }//+++
  
}//***eof
