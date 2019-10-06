/*
 * Copyright (C) 2019 Key Parker from K.I.C
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nextzz.pppsimulate;

import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZcRangedValueModel;
import kosui.ppplogic.ZcTimer;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcStringUtility;
import processing.core.PApplet;

public class ZcMotor extends ZcRangedValueModel{
  
  private boolean cmMC,cmAN, cmAL;
  private final ZcTimer cmContactDelay;
  private float cmLoad;
  
  public ZcMotor(int pxContactDelay){
    super(0, 5000);
    cmMC=cmAN=cmAL=false;
    cmContactDelay=new ZcOnDelayTimer(pxContactDelay);
    cmLoad=0.66f;
  }//..!
  
  //===
  
  public final void ccSimulate(){
    
    //--
    cmContactDelay.ccAct(cmMC);
    cmAN=cmContactDelay.ccIsUp();
    
    //--
    if(!cmAN){
      ccSetValue(1);
    }else{
      ccSetValue(PApplet.ceil(
        cmLoad*5000f+VcNumericUtility.ccRandom(200f)
      ));
    }//..?
    
    //--
    if(cmValue>4765){cmAL=true;}
    if(cmAL){
      cmAN=false;
      ccSetValue(1);
    }//..?
    
  }//+++
  
  public void ccSimulate(float pxLoad){
    ccSetLoad(pxLoad);
    ccSimulate();
  }//+++
  
  //===
  
  public final void ccContact(boolean pxInput){
    cmMC=pxInput;
  }//+++
  
  public final void ccTestTrip(boolean pxInput){
    cmAL=pxInput;
  }//+++
  
  public final void ccForceTrip(){
    cmAL=true;
  }//+++
  
  public final void ccResetTrip(){
    cmAL=false;
  }//+++
  
  public final void ccSetLoad(float pxLoad){
    cmLoad=pxLoad<0f?0.1f:pxLoad;
  }//+++
  
  //===
  
  public final boolean ccIsTripped(){
    return cmAL;
  }//+++
  
  public final boolean ccIsContacted(){
    return cmAN;
  }//+++
  
  public final int ccGetCT(){
    return cmValue;
  }//+++
  
  //===

  @Override public String toString() {
    StringBuilder lpRes = new StringBuilder(ZcMotor.class.getSimpleName());
    lpRes.append('@');
    lpRes.append(Integer.toHexString(this.hashCode()));
    lpRes.append('$');
    lpRes.append(VcStringUtility.ccPackupPairedTag("AL", cmAL));
    lpRes.append(VcStringUtility.ccPackupPairedTag("AN", cmAN));
    lpRes.append(VcStringUtility.ccPackupPairedTag("MC", cmMC));
    lpRes.append(VcStringUtility.ccPackupPairedTag("CT", cmValue));
    lpRes.append(VcStringUtility.ccPackupPairedTag
      ("tm", cmContactDelay.ccGetValue()));
    return lpRes.toString();
  }//+++
  
}//***eof
