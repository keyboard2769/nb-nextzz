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

public class ZcGate extends ZcRangedValueModel{
  
  private boolean dcOpen,dcClose;
  private boolean dcIsFullOpened,dcIsClosed;
  private boolean dcIsAtFull,dcIsAtMiddle,dcIsAtClosed;
  private int cmSpeed,cmAutoSwitchRange,cmLimitSwitchRange;
  
  public ZcGate() {
    super(400, 3200);
    dcOpen=false;
    dcClose=false;
    cmSpeed=16;
    cmAutoSwitchRange=50;
    cmLimitSwitchRange=200;
  }//..!
  
  public final void ccSimulate(){
    if(dcOpen&&dcClose){
      ccSetupAction(false, false);
      return;
    }//..?
    if(dcClose){ccShift(-1*cmSpeed);}
    if(dcOpen){ccShift(    cmSpeed);}
    //--
    dcIsFullOpened = ccIsAbove(cmMax-cmLimitSwitchRange);
    dcIsClosed     = ccIsBelow(cmMin+cmLimitSwitchRange);
    //--
    dcIsAtFull = ccIsInRangeOf(
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
  
  public final boolean ccGetIsFullOpened(){
    return dcIsFullOpened;
  }//+++
  
  public final boolean ccGetIsClosed(){
    return dcIsClosed;
  }//+++
  
  public final boolean ccGetIsAtFull(){
    return dcIsAtFull;
  }//+++
  
  public final boolean ccGetIsAtMiddle(){
    return dcIsAtMiddle;
  }//+++
  
  public final boolean ccGetIsAtClosed(){
    return dcIsAtClosed;
  }//+++
  
  public final boolean ccGetIsOpening(){
    return dcOpen;
  }//+++
  
  public final boolean ccGetIsClosing(){
    return dcClose;
  }//+++
  
}//***eof
