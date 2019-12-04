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

import kosui.ppputil.VcNumericUtility;
import nextzz.ppplocalui.SubMixerGroup;
import nextzz.ppplocalui.SubVBondGroup;
import nextzz.ppplocalui.SubVSurgeGroup;
import nextzz.ppplocalui.SubWeigherGroup;
import nextzz.pppmodel.MainSpecificator;
import nextzz.pppmodel.SubAnalogScalarManager;

public final class SubAnalogDelegator {
  
  public static volatile int
    
    //-- cell
    mnAGCellAD,mnFRCellAD,mnASCellAD,
    //[todo]::mnRCCellAd,mnADCellAd,
    
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
    mnAGxLVnI,mnAGxLVnII,mnAGxLVnIII,
    mnAGxLVnIV,mnAGxLVnV,mnAGxLVnVI,mnAGxLVnVII,
    mnFillerSiloLV,mnCementSiloLV,mnDustSiloLV
    
  ;//,,,
  
  public static final void ccWire(){
    //[notyet]::
  }//+++
  
  public static final void ccBind(){
    
    //-- cell ** box
    SubWeigherGroup.ccRefer().cmAGCellCB.ccSetValue
      (SubAnalogScalarManager.ccRefer().ccGetAGCellKG());
    SubWeigherGroup.ccRefer().cmFRCellCB.ccSetFloatValueForOneAfter(
      VcNumericUtility.ccToFloatForOneAfter(
        (SubAnalogScalarManager.ccRefer().ccGetFRCellKG())
    ));
    SubWeigherGroup.ccRefer().cmASCellCB.ccSetFloatValueForOneAfter(
      VcNumericUtility.ccToFloatForOneAfter(
        (SubAnalogScalarManager.ccRefer().ccGetASCellKG())
    ));
    
    //-- cell ** gauge
    SubWeigherGroup.ccRefer().cmAGCellLV.ccSetProportion
      (SubAnalogScalarManager.ccRefer().ccGetAGProportion());
    SubWeigherGroup.ccRefer().cmFRCellLV.ccSetProportion
      (SubAnalogScalarManager.ccRefer().ccGetFRProportion());
    SubWeigherGroup.ccRefer().cmASCellLV.ccSetProportion
      (SubAnalogScalarManager.ccRefer().ccGetASProportion());
    
    //-- temperature
    SubVBondGroup.ccRefer().cmEntranceTemperatureCB
      .ccSetValue(SubAnalogScalarManager.ccRefer().cmDesThermoCelcius
        .ccGet(SubAnalogScalarManager.C_I_THII_ENTRANCE));
    SubVBondGroup.ccRefer().cmChuteTemperatureTB
      .ccSetValue(SubAnalogScalarManager.ccRefer().cmDesThermoCelcius
        .ccGet(SubAnalogScalarManager.C_I_THI_CHUTE));
    SubVSurgeGroup.ccRefer().cmSandTemperatureCB
      .ccSetValue(SubAnalogScalarManager.ccRefer().cmDesThermoCelcius
        .ccGet(SubAnalogScalarManager.C_I_THIV_SAND));
    SubVSurgeGroup.ccRefer().cmPipeTemperatureCB
      .ccSetValue(SubAnalogScalarManager.ccRefer().cmDesThermoCelcius
        .ccGet(SubAnalogScalarManager.C_I_THIII_PIPE));
    SubMixerGroup.ccRefer().cmMixerTemperatureCB
      .ccSetValue(SubAnalogScalarManager.ccRefer().cmDesThermoCelcius
        .ccGet(SubAnalogScalarManager.C_I_THVI_MIXER));
    
    //-- vbond
    SubVBondGroup.ccRefer().cmVDPressureCB.ccSetValue
      (SubAnalogScalarManager.ccRefer().ccGetVDryerKPA());
    SubVBondGroup.ccRefer().cmBurnerDegreeCB.ccSetValue
      (SubAnalogScalarManager.ccRefer().ccGetVBurnerPercentage());
    SubVBondGroup.ccRefer().cmExfanDegreeCB.ccSetValue
      (SubAnalogScalarManager.ccRefer().ccGetVExfanPercentage());
    
    //-- content
    for(int i=1;i<=MainSpecificator.ccRefer().vmAGCattegoryCount;i++){
      SubVSurgeGroup.ccRefer().cmLesHotbinLV.get(i)
        .ccSetProportion(ccGetHotBinLevelorAD(i));
    }//..~
    SubVSurgeGroup.ccRefer().cmFillerSiloLV.ccSetProportion(mnFillerSiloLV);
    SubVSurgeGroup.ccRefer().cmCementSiloLV.ccSetProportion(mnCementSiloLV);
    SubVSurgeGroup.ccRefer().cmDustSiloLV.ccSetProportion(mnDustSiloLV);
    
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
  
  public static final void ccSetThermoAD(int pxOrder, int pxVal){
    switch(pxOrder){
      case SubAnalogScalarManager.C_I_THI_CHUTE:mnTHnI=pxVal;break;
      case SubAnalogScalarManager.C_I_THII_ENTRANCE:mnTHnII=pxVal;break;
      case SubAnalogScalarManager.C_I_THIII_PIPE:mnTHnIII=pxVal;break;
      case SubAnalogScalarManager.C_I_THIV_SAND:mnTHnIV=pxVal;break;
      case SubAnalogScalarManager.C_I_THVI_MIXER:mnTHnVI=pxVal;break;
      //[later]::case  0xA:mnRHnVI=pxVal;break;
      //[later]::case  0xB:mnRHnVII=pxVal;break;
      //[later]::case  0xC:mnRHnIVI=pxVal;break;
      //[later]::case  0xD:mnRHnVIII=pxVal;break;
      //[later]::case  0x7:mnTHnVII=pxVal;break;
      //[later]::case  0x8:mnTHnVIII=pxVal;break;
      //--
      default:break;
    }//..?
  }//+++
  
  public static final int ccGetThermoAD(int pxOrder){switch(pxOrder){
      case SubAnalogScalarManager.C_I_THI_CHUTE:return mnTHnI;
      case SubAnalogScalarManager.C_I_THII_ENTRANCE:return mnTHnII;
      case SubAnalogScalarManager.C_I_THIII_PIPE:return mnTHnIII;
      case SubAnalogScalarManager.C_I_THIV_SAND:return mnTHnIV;
      case SubAnalogScalarManager.C_I_THVI_MIXER:return mnTHnVI;
      //[later]::case 0xA:return mnRHnI;
      //[later]::case 0xB:return mnRHnII;
      //[later]::case 0xC:return mnRHnIV;
      //[later]::case 0xD:return mnRHnVIII;
      //[later]::case 0x7:return mnTHnVII;
      //[later]::case 0x8:return mnTHnVIII;
      //--
      default:return 0;
    }//..?
  }//+++
  
  //===
  
  public static final void ccSetHotBinLevelorAD(int pxOrder, int pxVal){
    switch(pxOrder){
      case  1:mnAGxLVnI=pxVal;break;
      case  2:mnAGxLVnII=pxVal;break;
      case  3:mnAGxLVnIII=pxVal;break;
      case  4:mnAGxLVnIV=pxVal;break;
      case  5:mnAGxLVnV=pxVal;break;
      case  6:mnAGxLVnVI=pxVal;break;
      case  7:mnAGxLVnVII=pxVal;break;
      //--
      default:break;
    }//..?
  }//++<
  
  public static final int ccGetHotBinLevelorAD(int pxOrder){
    switch(pxOrder){
      case  1:return mnAGxLVnI;
      case  2:return mnAGxLVnII;
      case  3:return mnAGxLVnIII;
      case  4:return mnAGxLVnIV;
      case  5:return mnAGxLVnV;
      case  6:return mnAGxLVnVI;
      case  7:return mnAGxLVnVII;
      default:return 0;
    }//..?
  }//++>
  
}//***eof
