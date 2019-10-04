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

public final class ConstFBHolder {

  public static final
  boolean ccMoterFeedBackLamp(ZcHookFlicker pxHolder, ZcMotor pxMotor){
    return
      pxHolder.ccIsHooked()
       && (MainSimulator.ccOneSecondClock()||pxMotor.ccIsContacted());
  }//+++
  
  public static final
  boolean ccSelectAutoMode(
    boolean pxPermmision, boolean pxAuto,
    boolean pxSwitch, boolean pxFlag
  ){
    if(!pxPermmision){return false;}
    return pxAuto?pxFlag:pxSwitch;
  }//+++
  
 }//***eof
