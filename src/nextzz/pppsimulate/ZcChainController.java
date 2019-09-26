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

public class ZcChainController extends ZcCheckedValueModel {
  
  /*
    rule #1 : if some stage tripped, he may stop immeatly
    rule #2 : if some stage tripped, it chain it seld is stopped
    rule #3 : it some stage tripped, previouse stage should not stopp immediatly
  */
  
  private boolean cmIsEngaged = false;

  public ZcChainController(int pxIntervalS) {
    super(0, EcConst.C_FPS*(pxIntervalS&0x1F)*ZcLevelComparator.C_LEVEL_MAX);
  }//..!
  
  //===
  
  public final void ccRun(){
    if(cmIsEngaged){
      ccShift(1);
    }else{
      ccShift(-1);
    }//..?
  }//+++
  
  //===
  
  public final void ccSetEngaged(boolean pxStatus){
    cmIsEngaged=pxStatus;
  }//+++
  
  public final void ccSetTrippedAt(int pxIndex, boolean pxStatus){
    //[head]::
  }//+++
  
  public final void ccSetConfirmedAt(int pxIndex, boolean pxStatus){
    
  }//+++
  
  public final boolean ccGetOutputAt(int pxIndex){
    return false;
  }//+++
  
  //===
  
  public final void ccAllStop(){
  
  }//+++
  
}//***eof
