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
    mnVCombustReadyPL,mnVCombustRunSW,
    mnVExfanCloseSW, mnVExfanOpenSW,mnVExfanAutoSW,
    mnVExfanClosePL, mnVExfanOpenPL,mnVExfanAutoPL,
    mnVBurnerCloseSW, mnVBurnerOpenSW,mnVBurnerAutoSW,
    mnVBurnerClosePL, mnVBurnerOpenPL,mnVBurnerAutoPL,
    mnVColdAggreageSensorPL,
    mnVCombustSourceSW,mnVOilExchangeSW,
    mnVCombustUsingGasPL,mnVCombustUsingOilPL,
    mnVCombustUsingFuelPL,mnVCombustUsingHeavyPL
  ;//...
  
  //===
  
  public static final void ccWire(){
    /* 6 */throw new RuntimeException("NOT YET!!");
  }//+++
  
  public static final void ccBind(){
    
    //-- temperature
    SubVBondGroup.ccRefer().cmTargetTemperatureTB
      .ccSetValue(MainPlantModel.ccRefer().cmVTargetTemperature);
    
    //-- v combust operate
    mnVCombustRunSW=SubVBondGroup.ccRefer().cmRunSW.ccIsMousePressed();
    SubVBondGroup.ccRefer().cmReadyPL.ccSetIsActivated(mnVCombustReadyPL);
    
    //-- v burner operate
    mnVBurnerCloseSW=SubVBondGroup.ccRefer().cmBurnerCloseSW.ccIsMousePressed();
    mnVBurnerOpenSW=SubVBondGroup.ccRefer().cmBurnerOpenSW.ccIsMousePressed();
    mnVBurnerAutoSW=SubVBondGroup.ccRefer().cmBurnerAutoSW.ccIsMousePressed();
    SubVBondGroup.ccRefer().cmBurnerCloseSW.ccSetIsActivated(mnVBurnerClosePL);
    SubVBondGroup.ccRefer().cmBurnerOpenSW.ccSetIsActivated(mnVBurnerOpenPL);
    SubVBondGroup.ccRefer().cmBurnerAutoSW.ccSetIsActivated(mnVBurnerAutoPL);
    
    //-- v exfan operate
    mnVExfanCloseSW=SubVBondGroup.ccRefer().cmExfanCloseSW.ccIsMousePressed();
    mnVExfanOpenSW=SubVBondGroup.ccRefer().cmExfanOpenSW.ccIsMousePressed();
    mnVExfanAutoSW=SubVBondGroup.ccRefer().cmExfanAutoSW.ccIsMousePressed();
    SubVBondGroup.ccRefer().cmExfanCloseSW.ccSetIsActivated(mnVExfanClosePL);
    SubVBondGroup.ccRefer().cmExfanOpenSW.ccSetIsActivated(mnVExfanOpenPL);
    SubVBondGroup.ccRefer().cmExfanAutoSW.ccSetIsActivated(mnVExfanAutoPL);
    
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
