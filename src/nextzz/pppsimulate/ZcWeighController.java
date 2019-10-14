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

import kosui.ppplogic.ZcPulser;
import kosui.ppplogic.ZcStepper;
import kosui.ppputil.VcStringUtility;

public final class ZcWeighController extends ZcStepper{
  
  private static final int
    //--
    S_READY = 0,
    S_PRE  = 1,
    S_WEIGH  = 2,
    S_POST = 3,
    S_ALL_OVER = 4,
    S_DISCHARGE = 5,
    S_DISCHARGE_CONFIRM = 6,
    S_ZERO_REQUIRE = 7,
    S_ABEND = 99
  ;//,,,
  
  private final ZcPulser cmToNextPLS = new ZcPulser();
  
  private int cmCurrentLevel = 0;
  
  private boolean 
    cmToStart,
    cmHasNext,cmToNext,cmToDischarge,cmIsDicharged;
  
  private final int C_LV_MAX;
  
  public ZcWeighController(int pxMaxMAT){
    C_LV_MAX=pxMaxMAT&0x7;
  }//++!
  
  //===
  
  /* 1 *///run(...)
  
  /* 1 */public
  void ccRun(boolean pxStart, boolean pxAbend){
    ccStart(pxStart);
    ccAbend(pxAbend);
    ccRun();
  }//++~
  
  public final void ccRun(){
    
    //[head]:: i know you have absolutely no idea about what to do next
    
    ccStep(
      S_READY, S_PRE,
      cmToStart
    );
    
    if(ccIsAt(S_PRE)){
      cmCurrentLevel=C_LV_MAX;
    }//..?
    
    
    ccStep(
      S_PRE, S_WEIGH,
      true
    );
    
    if(cmToNextPLS.ccDownPulse(cmToNext) && ccIsAt(S_WEIGH)){
      cmCurrentLevel--;
    }//..?
    
    
    ccStep(S_WEIGH, S_POST,
      cmCurrentLevel<=0
    );
    
    ccStep(
      S_POST, S_ALL_OVER,
      true
    );
    
    ccStep(
      S_ALL_OVER, S_DISCHARGE,
      cmToDischarge
    );
    
    ccStep(
      S_DISCHARGE, S_DISCHARGE_CONFIRM,
      cmToDischarge
    );
    
    ccStep(
      S_DISCHARGE_CONFIRM,
      cmHasNext
        ? S_PRE
        : S_READY,
      true
    );
    
    
    ccStep(
      S_ABEND, S_READY,
      false// % reset
    );
    
  }//++~
  
  //===
  
  
  public final void ccSetHasNext(boolean pxCondition){
    cmHasNext=pxCondition;
  }//++<
  
  public final void ccSetToNext(boolean pxCondition){
    cmToNext=pxCondition;
  }//++<
  
  public final void ccSetToDischarge(boolean pxCondition){
    cmToDischarge=pxCondition;
  }//++<
  
  public final void ccSetIsDischarged(boolean pxCondition){
    cmIsDicharged=pxCondition;
  }//++<
  
  public final void ccStart(boolean pxCondition){
    cmToStart=pxCondition;
  }//+++
  
  public final void ccReady(boolean pxCondition){
    ccSetTo(S_READY, pxCondition);
  }//++<
  
  public final void ccAbend(boolean pxCondition){
    if(pxCondition){
      ccSetTo(S_ABEND, true);
    }else{
      ccStep(S_ABEND, S_READY, true);
    }//..?
  }//++<
  
  //===
  
  public final boolean ccGetNewStartFlag(){return false;}//++>
  
  public final int ccGetCurrentLevel(){
    return cmCurrentLevel;
  }//++>
  
  public final boolean ccIsWeighing(){
    return ccIsAt(S_WEIGH);
  }//++>
  
  public final boolean ccIsWaitingToDischarge(){
    return ccIsAt(S_ALL_OVER);
  }//++>
  
  
  public final int ccGetCurrentTarget(){return 0;}//++>
  public final int ccGetCurrentCut(){return 0;}//++>
  public final int ccGetCurrentMAT(){return 0;}//++>
  
  public final int ccToIndicativeNumber(){
    return cmStage*100+cmCurrentLevel;
  }//++>
  
  //===
  
  @Deprecated public final String ccToTag(){
    return VcStringUtility.ccPackupPairedTag(
      Integer.toString(cmStage),
      Integer.toString(cmCurrentLevel)
    );
  }//+++
  
  @Override public String toString() {
    return super.toString()
      + VcStringUtility.ccPackupPairedTag("LV", cmCurrentLevel);
  }//+++
  
}//***eof
