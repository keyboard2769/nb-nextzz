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

import kosui.ppplogic.ZcOffDelayTimer;
import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZcTimer;
import kosui.ppputil.VcStringUtility;

public class ZcFuelExchanger {
  
  private boolean 
    cmIsEnabled,
    cmOilPumpConfirm,cmFeedingStartConfirm,
    cmFuelValveOutput,cmHeavyValveOutput
  ;//,,,
  
  private final ZcTimer cmBeginMixingTM;
  private final ZcTimer cmEndMixingTM;
  
  public ZcFuelExchanger(int pxBeginCount, int pxEndCount){
    cmBeginMixingTM = new ZcOnDelayTimer(pxBeginCount);
    cmEndMixingTM = new ZcOffDelayTimer(pxEndCount);
  }//++!
  
  public ZcFuelExchanger(){
    this(32, 32);
  }//++!
  
  //===
  
  /* 1 */ public void ccRun(boolean pxEnable){
    ccSetEnable(pxEnable);
    ccRun();
  }//++~
  
  /* 1 */public void ccRun(boolean pxEnable, boolean pxPump, boolean pxFeed){
    ccSetEnable(pxEnable);
    ccSetOilPumpConfirm(pxPump);
    ccSetFeedingStartConfirm(pxFeed);
    ccRun();
  }//++~
  
  public final void ccRun(){
    cmBeginMixingTM.ccAct(cmFeedingStartConfirm);
    cmEndMixingTM.ccAct(cmFeedingStartConfirm);
    cmFuelValveOutput = cmOilPumpConfirm
      && (cmIsEnabled?!cmBeginMixingTM.ccIsUp():true);
    cmHeavyValveOutput = cmOilPumpConfirm
      && (cmIsEnabled?cmEndMixingTM.ccIsUp():false);
  }//++~
  
  //===
  
  public final void ccSetEnable(boolean pxInput){
    cmIsEnabled=pxInput;
  }//++<
  
  public final void ccSetOilPumpConfirm(boolean pxInput){
    cmOilPumpConfirm=pxInput;
  }//++<
  
  public final void ccSetFeedingStartConfirm(boolean pxInput){
    cmFeedingStartConfirm=pxInput;
  }//++<
  
  public final void ccSetBeginMixingTimer(int pxFrameCount){
    cmBeginMixingTM.ccSetTime(pxFrameCount);
  }//++<
  
  public final void ccSetEndMixingTM(int pxFrameCount){
    cmEndMixingTM.ccSetTime(pxFrameCount);
  }//++<
  
  //===
  
  public final boolean ccGetFuelValveOutput(){
    return cmFuelValveOutput;
  }//++>
  
  public final boolean ccGetHeavyValveOutput(){
    return cmHeavyValveOutput;
  }//++>
  
  //===
  
  @Override public String toString() {
    StringBuilder lpRes
      = new StringBuilder(ZcFuelExchanger.class.getSimpleName());
    lpRes.append('@');
    lpRes.append(Integer.toHexString(this.hashCode()));
    lpRes.append('$');
    lpRes.append(VcStringUtility.ccPackupBoolTag("M3", cmFuelValveOutput));
    lpRes.append(VcStringUtility.ccPackupBoolTag("M4", cmHeavyValveOutput));
    lpRes.append('|');
    lpRes.append(VcStringUtility
      .ccPackupPairedTag("bT", cmBeginMixingTM.ccGetValue()));
    lpRes.append(VcStringUtility
      .ccPackupPairedTag("eT", cmEndMixingTM.ccGetValue()));
    return lpRes.toString();
  }//+++
  
}//***eof
