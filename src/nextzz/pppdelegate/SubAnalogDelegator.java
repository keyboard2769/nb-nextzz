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
    
    //-- misc
    mnFillerSiloLV,mnCementSiloLV
    
  ;//...
  
  public static final void ccWire(){
    //[notyet]::
  }//+++
  
  public static final void ccBind(){
    
    //-- ct
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD( 0, mnCTSlotZ);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD( 1, mnCTSlotI);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD( 2, mnCTSlotII);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD( 3, mnCTSlotIII);
    //--
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD( 4, mnCTSlotIV);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD( 5, mnCTSlotV);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD( 6, mnCTSlotVI);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD( 7, mnCTSlotVII);
    //--
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD( 8, mnCTSlotVIII);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD( 9, mnCTSlotIX);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD(10, mnCTSlotX);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD(11, mnCTSlotXI);
    //-- 
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD(12, mnCTSlotXII);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD(13, mnCTSlotXIII);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD(14, mnCTSlotXIV);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD(15, mnCTSlotXV);
    //-- **
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD(16, mnCTSlotXVI);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD(17, mnCTSlotXVII);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD(18, mnCTSlotXIII);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD(19, mnCTSlotXIX);
    //-- 
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD(20, mnCTSlotXX);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD(21, mnCTSlotXXI);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD(22, mnCTSlotXXII);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD(23, mnCTSlotXXIII);
    //-- 
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD(24, mnCTSlotXXIV);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD(25, mnCTSlotXXV);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD(26, mnCTSlotXXVI);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD(27, mnCTSlotXXVII);
    //-- 
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD(28, mnCTSlotXXVIII);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD(29, mnCTSlotXXIX);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD(30, mnCTSlotXXX);
    SubAnalogScalarManager.ccRefer().ccSetCurrentAD(31, mnCTSlotXXXI);
    
    //-- misc
    SubVSurgeGroup.ccRefer().cmFillerSiloLV.ccSetPercentage(mnFillerSiloLV);
    SubVSurgeGroup.ccRefer().cmCementSiloLV.ccSetPercentage(mnCementSiloLV);
    
  }//+++
  
}//***eof
