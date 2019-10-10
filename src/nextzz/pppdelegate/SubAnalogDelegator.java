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

import nextzz.ppplocalui.SubVBondGroup;
import nextzz.ppplocalui.SubVSurgeGroup;
import nextzz.pppmodel.SubAnalogScalarManager;

public final class SubAnalogDelegator {
  
  public static volatile int
    
    //-- ct
    mnCTSlotZ,mnCTSlotI,mnCTSlotII,mnCTSlotIII,
    mnCTSlotIV,mnCTSlotV,mnCTSlotVI,mnCTSlotVII,
    mnCTSlotVIII,mnCTSlotIX,mnCTSlotX,mnCTSlotXI,
    mnCTSlotXII,mnCTSlotXIII,mnCTSlotXIV,mnCTSlotXV,
    //--
    mnCTSlotXVI,mnCTSlotXVII,mnCTSlotXVIII,mnCTSlotXIX,
    mnCTSlotXX,mnCTSlotXXI,mnCTSlotXXII,mnCTSlotXXIII,
    mnCTSlotXXIV,mnCTSlotXXV,mnCTSlotXXVI,mnCTSlotXXVII,
    mnCTSlotXXVIII,mnCTSlotXXIX,mnCTSlotXXX,mnCTSlotXXXI,
    
    //-- th
    mnTHnI,mnTHnII,mnTHnIII,mnTHnIV,mnTHnV,mnTHnVI,mnTHnVII,mnTHnVIII,
    mnRHnI,mnRHnII,mnRHnIII,mnRHnIV,mnRHnV,mnRHnVI,mnRHnVII,mnRHnVIII,
    
    //-- deg
    mnVBDegreeAD, mnVEDegreeAD,
    
    //-- press
    mnVDPressureAD,
    
    //-- lv
    mnFillerSiloLV,mnCementSiloLV,mnDustSiloLV
    
  ;//...
  
  public static final void ccWire(){
    //[notyet]::
  }//+++
  
  public static final void ccBind(){
    
    //-- temperature
    SubVBondGroup.ccRefer().cmEntranceTemperatureCB.ccSetValue
      (SubAnalogScalarManager.ccRefer().cmDesVThermoCelcius.ccGet(2));
    SubVBondGroup.ccRefer().cmChuteTemperatureTB.ccSetValue
      (SubAnalogScalarManager.ccRefer().cmDesVThermoCelcius.ccGet(1));
    
    //-- content
    SubVSurgeGroup.ccRefer().cmFillerSiloLV.ccSetProportion(mnFillerSiloLV);
    SubVSurgeGroup.ccRefer().cmCementSiloLV.ccSetProportion(mnCementSiloLV);
    SubVSurgeGroup.ccRefer().cmDustSiloLV.ccSetProportion(mnDustSiloLV);
    
    //-- vbond
    SubVBondGroup.ccRefer().cmVDPressureCB.ccSetValue
      (SubAnalogScalarManager.ccRefer().ccGetVDryerKPA());
    SubVBondGroup.ccRefer().cmBurnerDegreeCB.ccSetValue
      (SubAnalogScalarManager.ccRefer().ccGerVBurnerPercentage());
    SubVBondGroup.ccRefer().cmExfanDegreeCB.ccSetValue
      (SubAnalogScalarManager.ccRefer().ccGetVExfanPercentage());
    
  }//+++
  
  //===
   
  public static final void ccSetCTSlotAD(int pxOrder, int pxVal){
    switch(pxOrder){
      case  0:mnCTSlotZ=pxVal;break;
      case  1:mnCTSlotI=pxVal;break;
      case  2:mnCTSlotII=pxVal;break;
      case  3:mnCTSlotIII=pxVal;break;
      case  4:mnCTSlotIV=pxVal;break;
      case  5:mnCTSlotV=pxVal;break;
      case  6:mnCTSlotVI=pxVal;break;
      case  7:mnCTSlotVII=pxVal;break;
      //--
      case  8:mnCTSlotVIII=pxVal;break;
      case  9:mnCTSlotIX=pxVal;break;
      case 10:mnCTSlotX=pxVal;break;
      case 11:mnCTSlotXI=pxVal;break;
      case 12:mnCTSlotXII=pxVal;break;
      case 13:mnCTSlotXIII=pxVal;break;
      case 14:mnCTSlotXIV=pxVal;break;
      case 15:mnCTSlotXV=pxVal;break;
      //-- **
      case 16:mnCTSlotXVI=pxVal;break;
      case 17:mnCTSlotXVII=pxVal;break;
      case 18:mnCTSlotXVIII=pxVal;break;
      case 19:mnCTSlotXIX=pxVal;break;
      case 20:mnCTSlotXX=pxVal;break;
      case 21:mnCTSlotXXI=pxVal;break;
      case 22:mnCTSlotXXII=pxVal;break;
      case 23:mnCTSlotXXIII=pxVal;break;
      //--
      case 24:mnCTSlotXXIV=pxVal;break;
      case 25:mnCTSlotXXV=pxVal;break;
      case 26:mnCTSlotXXVI=pxVal;break;
      case 27:mnCTSlotXXVII=pxVal;break;
      case 28:mnCTSlotXXVIII=pxVal;break;
      case 29:mnCTSlotXXIX=pxVal;break;
      case 30:mnCTSlotXXX=pxVal;break;
      case 31:mnCTSlotXXXI=pxVal;break;
      //--
      default:break;
    }//..?
  }//++<
  
  public static final int ccGetCTSlotAD(int pxOrder){
    switch(pxOrder){
      case  0:return mnCTSlotZ;
      case  1:return mnCTSlotI;
      case  2:return mnCTSlotII;
      case  3:return mnCTSlotIII;
      case  4:return mnCTSlotIV;
      case  5:return mnCTSlotV;
      case  6:return mnCTSlotVI;
      case  7:return mnCTSlotVII;
      //--
      case  8:return mnCTSlotVIII;
      case  9:return mnCTSlotIX;
      case 10:return mnCTSlotX;
      case 11:return mnCTSlotXI;
      case 12:return mnCTSlotXII;
      case 13:return mnCTSlotXIII;
      case 14:return mnCTSlotXIV;
      case 15:return mnCTSlotXV;
      //-- **
      case 16:return mnCTSlotXVI;
      case 17:return mnCTSlotXVII;
      case 18:return mnCTSlotXVIII;
      case 19:return mnCTSlotXIX;
      case 20:return mnCTSlotXX;
      case 21:return mnCTSlotXXI;
      case 22:return mnCTSlotXXII;
      case 23:return mnCTSlotXXIII;
      //--
      case 24:return mnCTSlotXXIV;
      case 25:return mnCTSlotXXV;
      case 26:return mnCTSlotXXVI;
      case 27:return mnCTSlotXXVII;
      case 28:return mnCTSlotXXVIII;
      case 29:return mnCTSlotXXIX;
      case 30:return mnCTSlotXXX;
      case 31:return mnCTSlotXXXI;
      //--
      default:return 0;
    }//..?
  }//++>
  
   //===
  
  public static final void ccSetVThermoAD(int pxOrder, int pxVal){
    switch(pxOrder){
      case  1:mnTHnI=pxVal;break;
      case  2:mnTHnII=pxVal;break;
      case  3:mnTHnIII=pxVal;break;
      case  4:mnTHnIV=pxVal;break;
      case  5:mnTHnV=pxVal;break;
      case  6:mnTHnVI=pxVal;break;
      case  7:mnTHnVII=pxVal;break;
      case  8:mnTHnVIII=pxVal;break;
      //--
      default:break;
    }//..?
  }//+++
  
  public static final int ccGetVThermoAD(int pxOrder){
    
    //[head]::fix this system to a continiouse indexed one!!
    
    switch(pxOrder){
      case  1:return mnTHnI;
      case  2:return mnTHnII;
      case  3:return mnTHnIII;
      case  4:return mnTHnIV;
      case  5:return mnTHnV;
      case  6:return mnTHnVI;
      case  7:return mnTHnVII;
      case  8:return mnTHnVIII;
      //--
      default:return 0;
    }//..?
  }//+++
  
  //===
  
  public static final void ccSetRThermoAD(int pxOrder, int pxVal){
    switch(pxOrder){
      case  1:mnRHnI=pxVal;break;
      case  2:mnRHnII=pxVal;break;
      case  3:mnRHnIII=pxVal;break;
      case  4:mnRHnIV=pxVal;break;
      case  5:mnRHnV=pxVal;break;
      case  6:mnRHnVI=pxVal;break;
      case  7:mnRHnVII=pxVal;break;
      case  8:mnRHnVIII=pxVal;break;
      //--
      default:break;
    }//..?
  }//+++
  
  public static final int ccGetRThermoAD(int pxOrder){
    switch(pxOrder){
      case  1:return mnRHnI;
      case  2:return mnRHnII;
      case  3:return mnRHnIII;
      case  4:return mnRHnIV;
      case  5:return mnRHnV;
      case  6:return mnRHnVI;
      case  7:return mnRHnVII;
      case  8:return mnRHnVIII;
      //--
      default:return 0;
    }//..?
  }//+++
  
}//***eof
