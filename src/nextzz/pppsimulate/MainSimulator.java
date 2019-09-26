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
import kosui.ppputil.VcLocalTagger;

public final class MainSimulator {
  
  private static MainSimulator self = null;
  public static MainSimulator ccRefer() {
    if(self==null){self=new MainSimulator();}
    return self;
  }//+++
  private MainSimulator(){}//..!
  
  //===
  
  private static int cmRoller=11;
  
  private static void ssRoll(){
    cmRoller++;cmRoller&=0xF;
  }//+++
  
  public static boolean ccOneSecondPLS(){
    return cmRoller==7;
  }//+++
  
  public static boolean ccOneSecondClock(){
    return cmRoller>7;
  }//+++
  
  static public final void ccSimulate(){
    ssRoll();
    
    //-- scan
    SubVPreparingTask.ccRefer().ccScan();
    
    //-- simulate
    SubVPreparingTask.ccRefer().ccSimulate();
  
  }//+++
  
  //===
  
  public static final void ccTransferExclusive(
    ZcRangedValueModel pxTarget,
    boolean pxCharge, int pxChargeSpeed,
    boolean pxDischarge, int pxDischargeSpeed
  ){
    if(pxCharge){pxTarget.ccShift(pxChargeSpeed);}
    if(pxDischarge){pxTarget.ccShift(-1*pxDischargeSpeed);}
  }//+++
  
  //[todo]::ccTansferInteractive()
  
 }//***eof
