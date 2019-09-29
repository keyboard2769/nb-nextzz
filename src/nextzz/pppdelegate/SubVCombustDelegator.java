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
import nextzz.ppplocalui.SubVBondGroup;
import nextzz.pppmodel.MainPlantModel;

public class SubVCombustDelegator {
  
  public static volatile boolean
    mnVColdAggreageSensorPL
  ;//...
  
  //===
  
  public static final void ccWire(){
    /* 6 */throw new RuntimeException("NOT YET!!");
  }//+++
  
  public static final void ccBind(){
    
    //-- temperature
    SubVBondGroup.ccRefer().cmTargetTemperatureTB
      .ccSetValue(MainPlantModel.ccRefer().cmVTargetTemperature);
    
    //-- cas
    SubVBondGroup.ccRefer().cmBelconFluxCB
      .ccSetFloatValueForOneAfter(VcNumericUtility
        .ccToFloatForOneAfter(MainPlantModel.ccRefer().cmVSupplyTPH)
      );
    SubVBondGroup.ccRefer().cmBelconFluxCB
      .ccSetIsActivated(mnVColdAggreageSensorPL);
    SubVBondGroup.ccRefer().cmVDContentLV
      .ccSetPercentage(MainPlantModel.ccRefer().cmVSupplyTPH, 4000);
  
  }//+++
  
}//***eof
