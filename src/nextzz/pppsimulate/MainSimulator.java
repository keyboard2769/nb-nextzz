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

import kosui.ppplogic.ZcHookFlicker;
import kosui.ppplogic.ZcRoller;

public final class MainSimulator {
  
  private static MainSimulator self = null;
  public static MainSimulator ccRefer() {
    if(self==null){self=new MainSimulator();}
    return self;
  }//+++
  private MainSimulator(){}//..!

  //===
  
  //=== clock
  
  //=== clock ** 
  
  private static final ZcRoller O_HALFSEC_ROLLER
    = new ZcRoller(7, 5);
  
  private static final ZcRoller O_ONESEC_ROLLER
    = new ZcRoller(15, 11);
  
  private static final ZcRoller O_TWOSEC_ROLLER
    = new ZcRoller(31, 27);
  
  //=== clock ** half
  
  public static boolean ccHalfSecondPLS(){
    return O_HALFSEC_ROLLER.ccIsAt(3);
  }//+++
  
  public static boolean ccHalfSecondClock(){
    return O_HALFSEC_ROLLER.ccIsAbove(3);
  }//+++
  
  //=== clock ** one
  
  public static boolean ccOneSecondPLS(){
    return O_ONESEC_ROLLER.ccIsAt(7);
  }//+++
  
  public static boolean ccOneSecondClock(){
    return O_ONESEC_ROLLER.ccIsAbove(7);
  }//+++
  
  //=== clock ** two
  
  public static boolean ccTwoSecondPLS(){
    return O_TWOSEC_ROLLER.ccIsAt(15);
  }//+++
  
  public static boolean ccTwoSecondClock(){
    return O_TWOSEC_ROLLER.ccIsAbove(15);
  }//+++
  
  //=== loop
  
  static public final void ccSimulate(){
    
    //-- clock
    O_HALFSEC_ROLLER.ccRoll();
    O_ONESEC_ROLLER.ccRoll();
    O_TWOSEC_ROLLER.ccRoll();
    
    //-- scan
    SubWeighingTask.ccRefer().ccScan();
    SubFeederTask.ccRefer().ccScan();
    SubVProvisionTask.ccRefer().ccScan();
    SubVCombusTask.ccRefer().ccScan();
    
    //-- simulate
    SubWeighingTask.ccRefer().ccSimulate();
    SubFeederTask.ccRefer().ccSimulate();
    SubVProvisionTask.ccRefer().ccSimulate();
    SubVCombusTask.ccRefer().ccSimulate();
  
  }//+++
  
  //=== function block
  
  public static final boolean ccMoterFeedBackLamp(
    ZcHookFlicker pxHolder, ZcMotor pxMotor
  ){
    return pxHolder
       .ccIsHooked()&&(MainSimulator.ccOneSecondClock()
      ||pxMotor.ccIsContacted());
  }//+++

  public static final boolean ccSelectModeForce(
    boolean pxForceClose, boolean pxForceOpen, boolean pxAutoFlag
  ){
    return pxForceClose?false:pxForceOpen?true:pxAutoFlag;
  }//+++

  public static final boolean ccSelectModeSolo(
    boolean pxPermmision,
    boolean pxModeA, boolean pxInputA, boolean pxInputB
  ){
    if(!pxPermmision){return false;}
    return pxModeA?pxInputA:pxInputB;
  }//+++
  
  public static final boolean ccSelectModeDuo(
    boolean pxPermmision,
    boolean pxModeA, boolean pxInputA,
    boolean pxModeB, boolean pxInputB
  ){
    if(!pxPermmision){return false;}
    return
      pxModeA?pxInputA:
      pxModeB?pxInputB:
      false;
  }//+++
  
 }//***eof
