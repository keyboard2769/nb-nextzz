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
    
    //-- lv
    mnFillerSiloLV,mnCementSiloLV,mnDustSiloLV
    
  ;//...
  
  public static final void ccWire(){
    //[notyet]::
  }//+++
  
  public static final void ccBind(){
    
    //-- misc
    SubVSurgeGroup.ccRefer().cmFillerSiloLV.ccSetPercentage(mnFillerSiloLV);
    SubVSurgeGroup.ccRefer().cmCementSiloLV.ccSetPercentage(mnCementSiloLV);
    SubVSurgeGroup.ccRefer().cmDustSiloLV.ccSetPercentage(mnDustSiloLV);
    
  }//+++
  
}//***eof
