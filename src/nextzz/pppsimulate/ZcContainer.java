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

public class ZcContainer extends ZcRangedValueModel{

  public ZcContainer() {
    super(0, 29999);
  }//..!
  
  //===
  
  public final void ccCharge(int pxSpeed){
    ccShift( 1*(pxSpeed&0xFF));
  }//+++
  public final void ccDischarge(int pxSpeed){
    ccShift(-1*(pxSpeed&0xFF));
  }//+++
  
  //===
  
  public final boolean ccIsFull(){
    return ccIsAbove(25555);
  }//+++
  
  public final boolean ccIsMiddle(){
    return ccIsAbove(15555);
  }//+++
  
  public final boolean ccIsLow(){
    return ccIsAbove( 5555);
  }//+++
  
  public final boolean ccIsEmpty(){
    return ccIsBelow( 5555);
  }//+++
  
  //===

  @Override public String toString() {
    StringBuilder lpRes=new StringBuilder(ZcContainer.class.getSimpleName());
    lpRes.append('@');
    lpRes.append(Integer.toHexString(this.hashCode()));
    lpRes.append('$');
    lpRes.append(VcStringUtility.ccPackupParedTag("con", cmValue));
    lpRes.append(VcStringUtility.ccPackupParedTag("E", ccIsEmpty()));
    lpRes.append(VcStringUtility.ccPackupParedTag("L", ccIsLow()));
    lpRes.append(VcStringUtility.ccPackupParedTag("M", ccIsMiddle()));
    lpRes.append(VcStringUtility.ccPackupParedTag("H", ccIsFull()));
    return lpRes.toString();
  }//+++
  
  //===
  
  public static final void ccTransfer(
    ZcContainer pxFrom,
    ZcContainer pxTo,
    boolean pxCondition,
    int pxSpeed
  ){
    if(pxFrom == null){return;}
    if(pxTo == null){return;}
    if(pxCondition){
      if(pxFrom.ccIsBelow(pxSpeed)){return;}
      pxFrom.ccDischarge(pxSpeed);
      pxTo.ccCharge(pxSpeed);
    }//..?
  }//+++
  
}//***eof
