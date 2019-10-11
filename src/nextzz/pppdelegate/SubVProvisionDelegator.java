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
    mnMixerMSSW,mnMixerMSPL,mnMixerIconPL,
    mnVExfanMSSW,mnVExfanMSPL,mnVExfanIconPL,
    mnVBCompressorMSSW,mnVBCompressorMSPL,
    mnTowerBlowerTGSW,
    mnWeighSystemMSSW,mnWeighSystemMSPL,
    
    //-- ag
    
    //-- ag ** bond->tower
    mnAGChainMSSW,mnAGChainMSPL,
    mnVDryerPL,mnVInclinedBelconPL,
    mnVHorizontalBelconPL,
    
    //-- ag ** of-os
    mnOverFlowedLVPL,mnOverSizedLVPL,
    mnOverFlowedGatePL,mnOverSizedGatePL,
    mnOverFlowedGateSW,mnOverSizedGateSW,

    //-- fr
    
    //-- fr ** bag filter
    mnAirPulseDisableTGSW,mnAirPulseForceTGSW,
    mnAirPulseWithFeederTGSW,mnAirPulseWithAirTGSW,
    mnAirPulsingPL,
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
    //-- misc ** motor ** v comp
    mnVCompressorMSSW=SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(1).ccIsMousePressed();
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(1).ccSetIsActivated(mnVCompressorMSPL);
    
    //-- misc ** motor ** mixer
    mnMixerMSSW=SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(2).ccIsMousePressed();
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(2).ccSetIsActivated(mnMixerMSPL);
    
    //-- misc ** motor ** v exf
    mnVExfanMSSW=SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(3).ccIsMousePressed();
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(3).ccSetIsActivated(mnVExfanMSPL);
    SubMixerGroup.ccRefer().cmMixerIcon
      .ccSetIsActivated(mnMixerIconPL);
    SubVBondGroup.ccRefer().cmExfanIcon
      .ccSetIsActivated(mnVExfanIconPL);
    
    //-- misc ** motor ** weigh sys
    mnWeighSystemMSSW=SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(7).ccIsMousePressed();
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(7).ccSetIsActivated(mnWeighSystemMSPL);
    
    //-- misc ** motor ** vb comp
    mnVBCompressorMSSW=SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(10).ccIsMousePressed();
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(10).ccSetIsActivated(mnVBCompressorMSPL);
    
    //-- AG
    //-- AG ** chain
    //-- AG ** chain ** motoer switch
    mnAGChainMSSW=SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(4).ccIsMousePressed();
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(4).ccSetIsActivated(mnAGChainMSPL);
    //-- AG ** chain ** pl
    SubVBondGroup.ccRefer().cmVDryerRollerA
      .ccSetIsActivated(mnVDryerPL);
    SubVBondGroup.ccRefer().cmVDryerRollerC
      .ccSetIsActivated(mnVDryerPL);
    SubVBondGroup.ccRefer().cmBelconForwarPL
      .ccSetIsActivated(mnVInclinedBelconPL);
    SubVFeederGroup.ccRefer().cmBelconForwarPL
      .ccSetIsActivated(mnVHorizontalBelconPL);
    
    //-- AG ** of-os
    mnOverFlowedGateSW=SubVSurgeGroup.ccRefer()
      .cmOverFlowedGateSW.ccIsMousePressed();
    mnOverSizedGateSW=SubVSurgeGroup.ccRefer()
      .cmOverSizedGateSW.ccIsMousePressed();
    SubVSurgeGroup.ccRefer().cmOverFlowedLV
      .ccSetIsActivated(mnOverFlowedLVPL);
    SubVSurgeGroup.ccRefer().cmOverSizedLV
      .ccSetIsActivated(mnOverSizedLVPL);
    SubVSurgeGroup.ccRefer().cmOverFlowedGateSW
      .ccSetIsActivated(mnOverFlowedGatePL);
    SubVSurgeGroup.ccRefer().cmOverSizedGateSW
      .ccSetIsActivated(mnOverSizedGatePL);

    //-- FR ** dust extraction
    SubVBondGroup.ccRefer().cmAirPulsePL.ccSetIsActivated(mnAirPulsingPL);
    //-- FR ** dust extraction ** pc -> plc
    mnDustExtractionMSSW=SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(9).ccIsMousePressed();
    //-- FR ** dust extraction ** plc -> pc
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(9).ccSetIsActivated(mnDustExtractionMSPL);
    SubVBondGroup.ccRefer().cmBagHighLV
      .ccSetIsActivated(mnBagHopperHLV);
    SubVBondGroup.ccRefer().cmBagLowLV
      .ccSetIsActivated(mnBagHopperLLV);
    
    //-- FR ** filler supply ** plc -> pc
    mnFillerSystemSW=SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(8).ccIsMousePressed();
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(8).ccSetIsActivated(mnFillerSystemPL);
    SubVSurgeGroup.ccRefer().cmFillerBinLV
      .ccSetProportion(ConstLocalUI.ccLeverage
        (mnFillerBinHLVPL, mnFillerBinMLVPL, mnFillerBinLLVPL));
    
    //-- AS ** supply
    mnASSupplyPumpMSSW=SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(6).ccIsMousePressed();
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(6).ccSetIsActivated(mnASSupplyPumpMSPL);
    //[todo]:: % mnASTankInWith
    //[todo]:: % mnASTankOutWith
    
  }//+++
  
}//***eof
