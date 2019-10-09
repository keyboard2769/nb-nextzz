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

/**
 * aka 'NennSyouSouChi'
 */
public class ZcCombustor extends ZcMotor{
  
  private boolean cmIsOnFire;
  private boolean cmIgniter,cmPilot;
  
  public ZcCombustor(){
    super(4);
  }//+++
  
  //===

  @Override public void ccSimulate(float pxLoad){
    ccSetLoad(/* 6 */pxLoad);
    ccSimulate();
    cmIsOnFire=(ccIsContacted()||cmPilot)&&(cmIsOnFire||cmIgniter);
  }//+++
  
  //===
  
  public final void ccSetIgniter(boolean pxInput){
    cmIgniter=pxInput;
  }//+++
  
  public final void ccSetPilot(boolean pxInput){
    cmPilot=pxInput;
  }//+++
  
  //[todo]::ccSetDoesExchange
  //[todo]::ccSetUseGas
  //[todo]::ccExchange
  
  //===
  
  public final boolean ccIsOnFire(){
    return cmIsOnFire;
  }//+++
  
  //[todo]::ccGetUsingFuel
  //[todo]::ccGetUsingHeavy
  
}//***eof
