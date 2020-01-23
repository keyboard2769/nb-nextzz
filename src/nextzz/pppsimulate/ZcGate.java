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

import kosui.ppplogic.ZcRangedValueModel;
import kosui.ppputil.VcStringUtility;

public class ZcGate extends ZcRangedValueModel{
  
  private boolean dcOpen,dcClose;
  private boolean dcOpened,dcClosed;
  private boolean dcAtOpened,dcAtMiddle,dcAtClosed;
  private boolean cmEnpowered;
  private int cmSpeed,cmAutoSwitchRange,cmLimitSwitchRange;
  
  public ZcGate(int pxSpeed, int pxAuto, int pxLimit) {
    super(400, 3200);
    dcOpen=false;
    dcClose=false;
    cmEnpowered=true;
    ccSetSpeed(pxSpeed);
    ccSetupRange(pxAuto, pxLimit);
  }//..!
  
  public ZcGate(int pxSpeed){
    this(pxSpeed,50,200);
  }//..!
  
  public ZcGate(){
    this(16);
  }//..!
  
  //===
  
  /* 1 */ public void ccRun(boolean pxEnpowered){
    ccSetEnpowered(pxEnpowered);
    ccRun();
  }//++~
  
  public final void ccRun(){
    if(dcOpen&&dcClose){
      ccSetupAction(false, false);
      return;
    }//..?
    if(cmEnpowered){
      if(dcClose){ccShift(-1*cmSpeed);}
      if(dcOpen){ ccShift(   cmSpeed);}
    }//..?
    //--
    dcOpened = ccIsAbove(cmMax-cmLimitSwitchRange);
    dcClosed = ccIsBelow(cmMin+cmLimitSwitchRange);
    //--
    dcAtOpened = ccIsWith(
      cmMax-cmLimitSwitchRange-cmAutoSwitchRange,
      cmMax-cmLimitSwitchRange+cmAutoSwitchRange
    );
    dcAtMiddle = ccIsWith(
      (cmMax+cmMin)/2-cmAutoSwitchRange,
      (cmMax+cmMin)/2+cmAutoSwitchRange
    );
    dcAtClosed = ccIsWith(
      cmMin+cmLimitSwitchRange-cmAutoSwitchRange,
      cmMin+cmLimitSwitchRange+cmAutoSwitchRange
    );
  }//++~
  
  //===
  
  public final void ccSetEnpowered(boolean pxEnpowered){
    cmEnpowered = pxEnpowered;
  }//++<
  
  public final void ccSetSpeed(int pxSpeed){
    cmSpeed=pxSpeed&0xFF;
  }//++<
  
  public final void ccSetAutoSwitchRange(int pxRange){
    cmAutoSwitchRange=pxRange&0xFF;
  }//++<
  
  public final void ccSetLimitSwitchRange(int pxRange){
    cmLimitSwitchRange=pxRange&0xFF;
  }//++<
  
  public final void ccSetupRange(int pxAuto, int pxLimit){
    ccSetAutoSwitchRange(pxAuto);
    ccSetLimitSwitchRange(pxLimit);
  }//++<
  
  public final void ccSetOpening(boolean pxStatus){
    dcOpen=pxStatus;
  }//++<
  
  public final void ccSetClosing(boolean pxStatus){
    dcClose=pxStatus;
  }//++<
  
  public final void ccSetupAction(boolean pxClose, boolean pxOpen){
    if(pxOpen&&pxClose){return;}
    ccSetClosing(pxClose);
    ccSetOpening(pxOpen);
  }//++<
  
  public final void ccSetupAction(boolean pxOpen){
    ccSetupAction(!pxOpen, pxOpen);
  }//++<
  
  //===
  
  public final boolean ccIsFullOpened(){
    return dcOpened;
  }//++>
  
  public final boolean ccIsClosed(){
    return dcClosed;
  }//++>
  
  public final boolean ccIsAtFull(){
    return dcAtOpened;
  }//++>
  
  public final boolean ccIsAtMiddle(){
    return dcAtMiddle;
  }//++>
  
  public final boolean ccIsAtClosed(){
    return dcAtClosed;
  }//++>
  
  public final boolean ccIsOpening(){
    return dcOpen;
  }//++>
  
  public final boolean ccIsClosing(){
    return dcClose;
  }//++>
  
  public final boolean ccIsMissing(){
    return !dcOpened && !dcClosed;
  }//++>
  
  //===

  @Override public String toString(){
    StringBuilder lpRes = new StringBuilder(ZcGate.class.getSimpleName());
    lpRes.append('@');
    lpRes.append(Integer.toHexString(this.hashCode()));
    lpRes.append('$');
    lpRes.append(VcStringUtility.ccPackupBoolTag("DN", dcClose));
    lpRes.append(VcStringUtility.ccPackupBoolTag("UP", dcOpen));
    lpRes.append('|');
    lpRes.append(VcStringUtility.ccPackupBoolTag("CL", dcClosed));
    lpRes.append(VcStringUtility.ccPackupBoolTag("OL", dcOpened));
    lpRes.append('|');
    lpRes.append(VcStringUtility.ccPackupPairedTag("v", cmValue));
    lpRes.append(VcStringUtility.ccPackupPairedTag("SPD", cmSpeed));
    return lpRes.toString();
  }//+++
  
}//***eof
