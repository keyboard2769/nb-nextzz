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
  private boolean dcIsOpened,dcIsClosed;
  private boolean dcIsAtOpened,dcIsAtMiddle,dcIsAtClosed;
  private int cmSpeed,cmAutoSwitchRange,cmLimitSwitchRange;
  
  public ZcGate(int pxSpeed, int pxAuto, int pxLimit) {
    super(400, 3200);
    dcOpen=false;
    dcClose=false;
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
  
  public final void ccSimulate(boolean pxEnpowered){
    if(dcOpen&&dcClose){
      ccSetupAction(false, false);
      return;
    }//..?
    if(pxEnpowered){
      if(dcClose){ccShift(-1*cmSpeed);}
      if(dcOpen){ccShift(    cmSpeed);}
    }//..?
    //--
    dcIsOpened = ccIsAbove(cmMax-cmLimitSwitchRange);
    dcIsClosed     = ccIsBelow(cmMin+cmLimitSwitchRange);
    //--
    dcIsAtOpened = ccIsInRangeOf(
      cmMax-cmLimitSwitchRange-cmAutoSwitchRange,
      cmMax-cmLimitSwitchRange+cmAutoSwitchRange
    );
    dcIsAtMiddle = ccIsInRangeOf(
      (cmMax+cmMin)/2-cmAutoSwitchRange,
      (cmMax+cmMin)/2+cmAutoSwitchRange
    );
    dcIsAtClosed = ccIsInRangeOf(
      cmMin+cmLimitSwitchRange-cmAutoSwitchRange,
      cmMin+cmLimitSwitchRange+cmAutoSwitchRange
    );
  }//+++
  
  public final void ccSimulate(){
    ccSimulate(true);
  }//+++
  
  //===
  
  public final void ccSetSpeed(int pxSpeed){
    cmSpeed=pxSpeed&0xFF;
  }//+++
  
  public final void ccSetAutoSwitchRange(int pxRange){
    cmAutoSwitchRange=pxRange&0xFF;
  }//+++
  
  public final void ccSetLimitSwitchRange(int pxRange){
    cmLimitSwitchRange=pxRange&0xFF;
  }//+++
  
  public final void ccSetupRange(int pxAuto, int pxLimit){
    ccSetAutoSwitchRange(pxAuto);
    ccSetLimitSwitchRange(pxLimit);
  }//+++
  
  public final void ccSetOpening(boolean pxStatus){
    dcOpen=pxStatus;
  }//+++
  
  public final void ccSetClosing(boolean pxStatus){
    dcClose=pxStatus;
  }//+++
  
  public final void ccSetupAction(boolean pxClose, boolean pxOpen){
    if(pxOpen&&pxClose){return;}
    ccSetClosing(pxClose);
    ccSetOpening(pxOpen);
  }//+++
  
  public final void ccSetupAction(boolean pxOpen){
    ccSetupAction(!pxOpen, pxOpen);
  }//+++
  
  //===
  
  public final boolean ccIsFullOpened(){
    return dcIsOpened;
  }//+++
  
  public final boolean ccIsClosed(){
    return dcIsClosed;
  }//+++
  
  public final boolean ccIsAtFull(){
    return dcIsAtOpened;
  }//+++
  
  public final boolean ccIsAtMiddle(){
    return dcIsAtMiddle;
  }//+++
  
  public final boolean ccIsAtClosed(){
    return dcIsAtClosed;
  }//+++
  
  public final boolean ccIsOpening(){
    return dcOpen;
  }//+++
  
  public final boolean ccIsClosing(){
    return dcClose;
  }//+++
  
  public final boolean ccIsMissing(){
    return !dcIsOpened && !dcIsClosed;
  }//+++
  
  //===

  @Override public String toString(){
    StringBuilder lpRes = new StringBuilder(ZcGate.class.getSimpleName());
    lpRes.append('@');
    lpRes.append(Integer.toHexString(this.hashCode()));
    lpRes.append('$');
    lpRes.append(VcStringUtility.ccPackupBoolTag("DN", dcClose));
    lpRes.append(VcStringUtility.ccPackupBoolTag("UP", dcOpen));
    lpRes.append('|');
    lpRes.append(VcStringUtility.ccPackupBoolTag("CL", dcIsClosed));
    lpRes.append(VcStringUtility.ccPackupBoolTag("OL", dcIsOpened));
    lpRes.append('|');
    lpRes.append(VcStringUtility.ccPackupParedTag("v", cmValue));
    lpRes.append(VcStringUtility.ccPackupParedTag("SPD", cmSpeed));
    return lpRes.toString();
  }//+++
  
}//***eof
