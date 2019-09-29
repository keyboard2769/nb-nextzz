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

import nextzz.ppplocalui.ConstLocalUI;
import nextzz.ppplocalui.SubMixerGroup;
import nextzz.ppplocalui.SubOperativeGroup;
import nextzz.ppplocalui.SubVBondGroup;
import nextzz.ppplocalui.SubVFeederGroup;
import nextzz.ppplocalui.SubVSurgeGroup;

public final class SubVProvisionDelegator {
    
  public static volatile boolean 
    
    //-- misc
    mnVCompressorMSSW,mnVCompressorMSPL,
    mnMixerMSSW,mnMixerMSPL,
    mnMixerIconPL,
    mnVExfanMSSW,mnVExfanMSPL,
    mnVExfanIconPL,
    mnTowerBlowerTGSW,
    mnWeighSystemTGSW,mnWeighSystemTGPL,
    
    //-- ag
    
    //-- ag ** tower bond
    mnAGChainMSSW,mnAGChainMSPL,
    mnVDryerPL,mnVInclinedBelconPL,
    mnVHorizontalBelconPL,

    //-- fr
    
    //-- fr ** bag filter
    mnAirPulseDisableTGSW,mnAirPulseForceTGSW,
    mnAirPulseWithFeederTGSW,mnAirPulseWithAirTGSW,
    mnDustExtractionMSSW,mnDustExtractionMSPL,
    mnBagHopperHLV,mnBagHopperLLV,
    
    //-- fr ** dust silo
    mnDustSiloDischargeSW,mnDustSiloAirAutoSW,
    mnDustMixerSW,mnDustMixerScrewSW,mnDustMixerPumpSW,
    
    //-- fr ** filler
    mnFillerSystemSW, mnFillerSystemPL,
    mnFillerUsingAnTGSW,mnFillerUsingCnTGSW,mnFillerSiloAirTGSW,
    mnFillerBinHLVPL,mnFillerBinMLVPL,mnFillerBinLLVPL,
    
    //-- as
    mnASSupplyPumpMSSW,mnASSupplyPumpMSPL
    
  ;//...
  
  public static volatile int
    //-- as tank selection
    mnASTankInWith,mnASTankOutWith
  ;//...
  
  //=== 
  
  public static final void ccWire(){
    /* 6 */throw new RuntimeException("NOT YET!!");
  }//+++
  
  public static final void ccBind(){
    
    //-- misc
    
    //-- misc ** pc -> sim
    mnVCompressorMSSW=SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(1).ccIsMousePressed();
    mnMixerMSSW=SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(2).ccIsMousePressed();
    mnVExfanMSSW=SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(3).ccIsMousePressed();
    mnWeighSystemTGSW=SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(7).ccIsMousePressed();
    
    //-- misc ** sim -> pc
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(1).ccSetIsActivated(mnVCompressorMSPL);
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(2).ccSetIsActivated(mnMixerMSPL);
    SubMixerGroup.ccRefer().cmMixerIcon
      .ccSetIsActivated(mnMixerIconPL);
    SubVBondGroup.ccRefer().cmExfanIcon
      .ccSetIsActivated(mnVExfanIconPL);
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(3).ccSetIsActivated(mnVExfanMSPL);
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(7).ccSetIsActivated(mnWeighSystemTGPL);
    
    //-- AG
    
    //-- AG ** pc -> sim
    mnAGChainMSSW=SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(4).ccIsMousePressed();
    
    //-- AG ** sim -> pc
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(4).ccSetIsActivated(mnAGChainMSPL);
    SubVBondGroup.ccRefer().cmVDryerRollerA
      .ccSetIsActivated(mnVDryerPL);
    SubVBondGroup.ccRefer().cmVDryerRollerC
      .ccSetIsActivated(mnVDryerPL);
    SubVBondGroup.ccRefer().cmBelconForwarPL
      .ccSetIsActivated(mnVInclinedBelconPL);
    SubVFeederGroup.ccRefer().cmBelconForwarPL
      .ccSetIsActivated(mnVHorizontalBelconPL);

    //-- filler supply
    //-- filler supply ** pc -> plc
    mnFillerSystemSW=SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(8).ccIsMousePressed();
    //-- filler supply ** plc -> pc
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(8).ccSetIsActivated(mnFillerSystemPL);
    SubVSurgeGroup.ccRefer().cmFillerBinLV
      .ccSetPercentage(ConstLocalUI.ccLeverage
        (mnFillerBinHLVPL, mnFillerBinMLVPL, mnFillerBinLLVPL));
    
    //-- dust extraction
    //-- dust extraction ** pc -> plc
    mnDustExtractionMSSW=SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(9).ccIsMousePressed();
    //-- dust extraction ** plc -> pc
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(9).ccSetIsActivated(mnDustExtractionMSPL);
    SubVBondGroup.ccRefer().cmBagHighLV
      .ccSetIsActivated(mnBagHopperHLV);
    SubVBondGroup.ccRefer().cmBagLowLV
      .ccSetIsActivated(mnBagHopperLLV);
    
    //-- asphalt supply
    //-- asphalt supply ** pc -> plc
    mnASSupplyPumpMSSW=SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(6).ccIsMousePressed();
    //[todo]:: % mnASTankInWith
    //[todo]::mnASTankOutWith
    //-- asphalt supply **  plc -> pc
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(6).ccSetIsActivated(mnASSupplyPumpMSPL);
    
  }//+++
  
 }//***eof
