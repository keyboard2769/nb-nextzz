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
import kosui.ppplogic.ZcStepper;
import kosui.ppplogic.ZcTimer;

public class ZcStorerController extends ZcStepper{
  
  private static final int
    //--
    S_READY  = 0,
    S_STANDY = 1,
    //--
    S_STORED         = 2,
    S_PRE_DISCHARGE  = 3,
    S_OPEN           = 4,
    S_OPENED_CONFIRM = 5,
    S_CLOSE          = 6,
    S_CLOSED_CONFIRM = 7,
    S_POST_DISCHARGE = 8,
    //--
    S_ABEND = 99
  ;//,,,
  
  private final ZcTimer cmDischargeWaitTM;
  private final ZcTimer cmDischargeTM;
  private final ZcTimer cmRecoverTM;
  
  private boolean 
    cmIsStored, cmDischargePermission=true, 
    cmOpenedLS, cmClosedLS,cmReturnPermission=true
  ;//,,,
  
  //===

  public ZcStorerController() {
    super();
    cmDischargeWaitTM = new ZcOnDelayTimer(32);
    cmDischargeTM = new ZcOnDelayTimer(32);
    cmRecoverTM = new ZcOnDelayTimer(32);
  }//+++
  
  public ZcStorerController(
    int pxDischargeWait, int pxDischargeTime, int pxRecoverTimer
  ){
    super();
    cmDischargeWaitTM = new ZcOnDelayTimer(pxDischargeWait);
    cmDischargeTM = new ZcOnDelayTimer(pxDischargeTime);
    cmRecoverTM = new ZcOnDelayTimer(pxRecoverTimer);
  }//+++
  
  //===
  
  /* 1 *///public ccRun(...)
  
  public final void ccRun(){
    
    ccStep(
      S_ABEND, S_READY,
      false// % reset
    );
    
    ccStep(
      S_READY, S_STANDY,
      true
    );
    
    ccStep(
      S_STANDY, S_STORED,
      cmIsStored
    );
    
    ccStep(
      S_STORED, S_PRE_DISCHARGE,
      true// % remain
    );
    cmDischargeWaitTM.ccAct(ccIsAt(S_PRE_DISCHARGE) && cmDischargePermission);
    
    ccStep(
      S_PRE_DISCHARGE, S_OPEN,
      cmDischargeWaitTM.ccIsUp()
    );
    
    ccStep(
      S_OPEN, S_OPENED_CONFIRM,
      cmOpenedLS
    );
    cmDischargeTM.ccAct(ccIsAt(S_OPENED_CONFIRM));
    
    ccStep(
      S_OPENED_CONFIRM, S_CLOSE,
      cmDischargeTM.ccIsUp()
    );
    if(ccIsAt(S_OPENED_CONFIRM)){cmIsStored=false;}
    
    ccStep(
      S_CLOSE, S_CLOSED_CONFIRM,
      cmClosedLS
    );
    
    ccStep(
      S_CLOSED_CONFIRM, S_POST_DISCHARGE,
      cmReturnPermission
    );
    cmRecoverTM.ccAct(ccIsAt(S_POST_DISCHARGE));
    
    ccStep(
      S_POST_DISCHARGE, S_STANDY,
      cmRecoverTM.ccIsUp()
    );
    
  }//++~
  
  //===
  
  public final void ccSetupSelfLayer(boolean pxCL, boolean pxOL){
    cmClosedLS=pxCL;
    cmOpenedLS=pxOL;
  }//++<
  
  public final void ccSetupSuperiorLayer(boolean pxMV, boolean pxOL){
    if(cmClosedLS && pxMV && pxOL){
      cmIsStored=true;
    }//+++
  }//++<
  
  public final void ccSetupSuperiorLayer(boolean pxMV){
    ccSetupSuperiorLayer(pxMV,pxMV);
  }//++<
  
  public final void ccSetDischargePermission(boolean pxSubordinate){
    cmDischargePermission=pxSubordinate;
  }//++<
  
  public final void ccSetReturnPermission(boolean pxOutsider){
    cmReturnPermission=pxOutsider;
  }//+++<
  
  //===
  
  public final boolean ccGetGateOpenOutput(){
    return ccIsAt(S_OPEN) || ccIsAt(S_OPENED_CONFIRM);
  }//++>
  
  public final boolean ccIsOpenConfrimed(){
    return ccIsAt(S_OPENED_CONFIRM);
  }//++>
  
  public final boolean ccIsAtPost(){
    return ccIsAt(S_POST_DISCHARGE);
  }//++>
  
  //===
  
  @Override public String toString() {
    return super.toString();
  }//+++
  
}//***eof
