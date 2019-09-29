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

package nextzz.pppdelegate;

import nextzz.ppplocalui.SubOperativeGroup;
import nextzz.ppplocalui.SubVFeederGroup;

public class SubFeederDelegator {
  
  //.. for any UI part grouping vergin or recycle feeder abstractly
  //..   their max count should get left arbitrary
  //..   as they should feel comfortable when implementing their own stuff.
  //.. as for delegator, witch is THE traffic line interface
  //..   between pc and plc, the valid count hard coded as shown below.
  public static final int C_VF_INIT_ORDER =  1;
  public static final int C_VF_VALID_MAX  = 10;
  public static final int C_RF_INIT_ORDER =  1;
  public static final int C_RF_VALID_MAX  =  5;
  
  public static volatile boolean 
    
    //-- vf ** operate
    mnVFChainMSSW,mnVFChainMSPL,
    
    //-- vf ** ax
    mnVFRunningPLnI,mnVFRunningPLnII,mnVFRunningPLnIII,mnVFRunningPLnIV,
    mnVFRunningPLnV,mnVFRunningPLnVI,mnVFRunningPLnVII,mnVFRunningPLnVIII,
    mnVFRunningPLnIX,mnVFRunningPLnX,
    
    //-- vf ** stuck
    mnVFStuckPLnI,mnVFStuckPLnII,mnVFStuckPLnIII,mnVFStuckPLnIV,
    mnVFStuckPLnV,mnVFStuckPLnVI,mnVFStuckPLnVII,mnVFStuckPLnVIII,
    mnVFStuckPLnIX,mnVFStuckPLnX
    
  ;//...
  
  public static volatile int
    //-- vf speed
    mnVFSpeedADnI,mnVFSpeedADnII,mnVFSpeedADnIII,mnVFSpeedADnIV,
    mnVFSpeedADnV,mnVFSpeedADnVI,mnVFSpeedADnVII,mnVFSpeedADnVIII,
    mnVFSpeedADnIX,mnVFSpeedADnX
  ;//...
  
  //===
  
  public static final void ccWire(){
    /* 6 */throw new RuntimeException("NOT YET!!");
  }//+++
  
  public static final void ccBind(){
    
    //-- vf ** operative
    mnVFChainMSSW=SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(5).ccIsMousePressed();
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(5).ccSetIsActivated(mnVFChainMSPL);
    
    //-- v feeder ** operative
    mnVFChainMSSW=SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(5).ccIsMousePressed();
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(5).ccSetIsActivated(mnVFChainMSPL);
    
    //-- v feeder ** iteraticve
    for(
      int i=C_VF_INIT_ORDER;
      i<=C_VF_VALID_MAX;
      i++
    ){
      //-- ** plc -> pc **stuck
      SubVFeederGroup.ccRefer().cmDesFeederRPMGauge
        .get(i).ccSetIsActivated(ccGetVFeederStuck(i));
      //-- ** plc -> pc ** runnning
      SubVFeederGroup.ccRefer().cmDesFeederRPMBox
        .get(i).ccSetIsActivated(ccGetVFeederRunning(i));
      //-- ** swing -> local
      SubVFeederGroup.ccRefer().ccSetFeederRPM(i, ccGetVFeederSpeed(i));
    }//..~
  
  }//+++
  
  //=== rommantic
  
  public static final void ccSetVFeederSpeed(int pxOrder, int pxVal){
    switch(pxOrder){
      case  1:mnVFSpeedADnI=pxVal;break;
      case  2:mnVFSpeedADnII=pxVal;break;
      case  3:mnVFSpeedADnIII=pxVal;break;
      case  4:mnVFSpeedADnIV=pxVal;break;
      case  5:mnVFSpeedADnV=pxVal;break;
      case  6:mnVFSpeedADnVI=pxVal;break;
      case  7:mnVFSpeedADnVII=pxVal;break;
      case  8:mnVFSpeedADnVIII=pxVal;break;
      case  9:mnVFSpeedADnIX=pxVal;break;
      case 10:mnVFSpeedADnX=pxVal;break;
      default:break;
    }//..?
  }//+++
  
  public static final int ccGetVFeederSpeed(int pxOrder){
    switch (pxOrder) {
      case  1: return mnVFSpeedADnI;
      case  2: return mnVFSpeedADnII;
      case  3: return mnVFSpeedADnIII;
      case  4: return mnVFSpeedADnIV;
      case  5: return mnVFSpeedADnV;
      case  6: return mnVFSpeedADnVI;
      case  7: return mnVFSpeedADnVII;
      case  8: return mnVFSpeedADnVIII;
      case  9: return mnVFSpeedADnIX;
      case 10: return mnVFSpeedADnX;
      default:return 0;
    }//...?
  }//+++
  
  //===
  
  public static final void ccSetVFeederStuck(int pxOrder, boolean pxVal){
    switch(pxOrder){
      case  1:mnVFStuckPLnI=pxVal;break;
      case  2:mnVFStuckPLnII=pxVal;break;
      case  3:mnVFStuckPLnIII=pxVal;break;
      case  4:mnVFStuckPLnIV=pxVal;break;
      case  5:mnVFStuckPLnV=pxVal;break;
      case  6:mnVFStuckPLnVI=pxVal;break;
      case  7:mnVFStuckPLnVII=pxVal;break;
      case  8:mnVFStuckPLnVIII=pxVal;break;
      case  9:mnVFStuckPLnIX=pxVal;break;
      case 10:mnVFStuckPLnX=pxVal;break;
      default:break;
    }//..?
  }//+++
  
  public static final boolean ccGetVFeederStuck(int pxOrder){
    switch (pxOrder) {
      case  1: return mnVFStuckPLnI;
      case  2: return mnVFStuckPLnII;
      case  3: return mnVFStuckPLnIII;
      case  4: return mnVFStuckPLnIV;
      case  5: return mnVFStuckPLnV;
      case  6: return mnVFStuckPLnVI;
      case  7: return mnVFStuckPLnVII;
      case  8: return mnVFStuckPLnVIII;
      case  9: return mnVFStuckPLnIX;
      case 10: return mnVFStuckPLnX;
      default:return false;
    }//...?
  }//+++
  
  //===
  
  public static final void ccSetVFeederRunning(int pxOrder, boolean pxVal){
    switch(pxOrder){
      case  1:mnVFRunningPLnI=pxVal;break;
      case  2:mnVFRunningPLnII=pxVal;break;
      case  3:mnVFRunningPLnIII=pxVal;break;
      case  4:mnVFRunningPLnIV=pxVal;break;
      case  5:mnVFRunningPLnV=pxVal;break;
      case  6:mnVFRunningPLnVI=pxVal;break;
      case  7:mnVFRunningPLnVII=pxVal;break;
      case  8:mnVFRunningPLnVIII=pxVal;break;
      case  9:mnVFRunningPLnIX=pxVal;break;
      case 10:mnVFRunningPLnX=pxVal;break;
      default:break;
    }//..?
  }//+++
  
  public static final boolean ccGetVFeederRunning(int pxOrder){
    switch(pxOrder){
      case  1: return mnVFRunningPLnI;
      case  2: return mnVFRunningPLnII;
      case  3: return mnVFRunningPLnIII;
      case  4: return mnVFRunningPLnIV;
      case  5: return mnVFRunningPLnV;
      case  6: return mnVFRunningPLnVI;
      case  7: return mnVFRunningPLnVII;
      case  8: return mnVFRunningPLnVIII;
      case  9: return mnVFRunningPLnIX;
      case 10: return mnVFRunningPLnX;
      default:return false;
    }//..?
  }//+++
  
}//***eof
