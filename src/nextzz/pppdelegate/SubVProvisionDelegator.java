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
import nextzz.pppmodel.MainPlantModel;

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
    mnASSupplyPumpMSSW,mnASSupplyPumpMSPL,
    
    //-- assist
    //-- assist ** SW
    mnAssistSWnZ,mnAssistSWnI,mnAssistSWnII,mnAssistSWnIII,
    mnAssistSWnIV,mnAssistSWnV,mnAssistSWnVI,mnAssistSWnVII,
    mnAssistSWnVIII,mnAssistSWnIX,mnAssistSWnX,mnAssistSWnXI,
    mnAssistSWnXII,mnAssistSWnXIII,mnAssistSWnXIV,mnAssistSWnXV,
    //-- assist ** PL
    mnAssistPLnZ,mnAssistPLnI,mnAssistPLnII,mnAssistPLnIII,
    mnAssistPLnIV,mnAssistPLnV,mnAssistPLnVI,mnAssistPLnVII,
    mnAssistPLnVIII,mnAssistPLnIX,mnAssistPLnX,mnAssistPLnXI,
    mnAssistPLnXII,mnAssistPLnXIII,mnAssistPLnXIV,mnAssistPLnXV
    
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
    mnVCompressorMSSW=SubOperativeGroup.ccRefer().cmLesMotorSW
      .get(1).ccIsMousePressed();
    SubOperativeGroup.ccRefer().cmLesMotorSW
      .get(1).ccSetIsActivated(mnVCompressorMSPL);
    
    //-- misc ** motor ** mixer
    mnMixerMSSW=SubOperativeGroup.ccRefer().cmLesMotorSW
      .get(2).ccIsMousePressed();
    SubOperativeGroup.ccRefer().cmLesMotorSW
      .get(2).ccSetIsActivated(mnMixerMSPL);
    
    //-- misc ** motor ** v exf
    mnVExfanMSSW=SubOperativeGroup.ccRefer().cmLesMotorSW
      .get(3).ccIsMousePressed();
    SubOperativeGroup.ccRefer().cmLesMotorSW
      .get(3).ccSetIsActivated(mnVExfanMSPL);
    SubMixerGroup.ccRefer().cmMixerIcon
      .ccSetIsActivated(mnMixerIconPL);
    SubVBondGroup.ccRefer().cmExfanIcon
      .ccSetIsActivated(mnVExfanIconPL);
    
    //-- misc ** motor ** weigh sys
    mnWeighSystemMSSW=SubOperativeGroup.ccRefer().cmLesMotorSW
      .get(7).ccIsMousePressed();
    SubOperativeGroup.ccRefer().cmLesMotorSW
      .get(7).ccSetIsActivated(mnWeighSystemMSPL);
    
    //-- misc ** motor ** vb comp
    mnVBCompressorMSSW=SubOperativeGroup.ccRefer().cmLesMotorSW
      .get(10).ccIsMousePressed();
    SubOperativeGroup.ccRefer().cmLesMotorSW
      .get(10).ccSetIsActivated(mnVBCompressorMSPL);
    
    //-- AG
    //-- AG ** chain
    //-- AG ** chain ** motoer switch
    mnAGChainMSSW=SubOperativeGroup.ccRefer().cmLesMotorSW
      .get(4).ccIsMousePressed();
    SubOperativeGroup.ccRefer().cmLesMotorSW
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
    mnDustExtractionMSSW=SubOperativeGroup.ccRefer().cmLesMotorSW
      .get(9).ccIsMousePressed();
    //-- FR ** dust extraction ** plc -> pc
    SubOperativeGroup.ccRefer().cmLesMotorSW
      .get(9).ccSetIsActivated(mnDustExtractionMSPL);
    SubVBondGroup.ccRefer().cmBagHighLV
      .ccSetIsActivated(mnBagHopperHLV);
    SubVBondGroup.ccRefer().cmBagLowLV
      .ccSetIsActivated(mnBagHopperLLV);
    
    //-- FR ** filler supply ** plc -> pc
    mnFillerSystemSW=SubOperativeGroup.ccRefer().cmLesMotorSW
      .get(8).ccIsMousePressed();
    SubOperativeGroup.ccRefer().cmLesMotorSW
      .get(8).ccSetIsActivated(mnFillerSystemPL);
    SubVSurgeGroup.ccRefer().cmFillerBinLV
      .ccSetProportion(ConstLocalUI.ccLeverage
        (mnFillerBinHLVPL, mnFillerBinMLVPL, mnFillerBinLLVPL));
    
    //-- AS ** supply
    mnASSupplyPumpMSSW=SubOperativeGroup.ccRefer().cmLesMotorSW
      .get(6).ccIsMousePressed();
    SubOperativeGroup.ccRefer().cmLesMotorSW
      .get(6).ccSetIsActivated(mnASSupplyPumpMSPL);
    //[todo]:: % mnASTankInWith
    //[todo]:: % mnASTankOutWith
    
    //-- assist
    for(int i=0;i<=MainPlantModel.C_ASSIST_SW_MASK;i++){
      ccSetAssistSW(i, SubOperativeGroup.ccRefer()
        .cmLesAssistSW.get(i).ccIsMousePressed());
      SubOperativeGroup.ccRefer()
        .cmLesAssistSW.get(i).ccSetIsActivated(ccGetAssistPL(i));
    }//..~
    
  }//+++
  
  //===
  
  public static final void ccSetAssistSW(int pxOrder, boolean pxVal){
    switch(pxOrder){
      case  0:mnAssistSWnZ=pxVal;break;
      case  1:mnAssistSWnI=pxVal;break;
      case  2:mnAssistSWnII=pxVal;break;
      case  3:mnAssistSWnIII=pxVal;break;
      case  4:mnAssistSWnIV=pxVal;break;
      case  5:mnAssistSWnV=pxVal;break;
      case  6:mnAssistSWnVI=pxVal;break;
      case  7:mnAssistSWnVII=pxVal;break;
      //--
      case  8:mnAssistSWnVIII=pxVal;break;
      case  9:mnAssistSWnIX=pxVal;break;
      case 10:mnAssistSWnX=pxVal;break;
      case 11:mnAssistSWnXI=pxVal;break;
      case 12:mnAssistSWnXII=pxVal;break;
      case 13:mnAssistSWnXIII=pxVal;break;
      case 14:mnAssistSWnXIV=pxVal;break;
      case 15:mnAssistSWnXV=pxVal;break;
      //--
      default:break;
    }//..?
  }//++<
  
  public static final boolean ccGetAssistSW(int pxOrder){
    switch(pxOrder){
      case  0:return mnAssistSWnZ;
      case  1:return mnAssistSWnI;
      case  2:return mnAssistSWnII;
      case  3:return mnAssistSWnIII;
      case  4:return mnAssistSWnIV;
      case  5:return mnAssistSWnV;
      case  6:return mnAssistSWnVI;
      case  7:return mnAssistSWnVII;
      //--
      case  8:return mnAssistSWnVIII;
      case  9:return mnAssistSWnIX;
      case 10:return mnAssistSWnX;
      case 11:return mnAssistSWnXI;
      case 12:return mnAssistSWnXII;
      case 13:return mnAssistSWnXIII;
      case 14:return mnAssistSWnXIV;
      case 15:return mnAssistSWnXV;
      //--
      default:return false;
    }//..?
  }//++>
  
  public static final void ccSetAssistPL(int pxOrder, boolean pxVal){
    switch(pxOrder){
      case  0:mnAssistPLnZ=pxVal;break;
      case  1:mnAssistPLnI=pxVal;break;
      case  2:mnAssistPLnII=pxVal;break;
      case  3:mnAssistPLnIII=pxVal;break;
      case  4:mnAssistPLnIV=pxVal;break;
      case  5:mnAssistPLnV=pxVal;break;
      case  6:mnAssistPLnVI=pxVal;break;
      case  7:mnAssistPLnVII=pxVal;break;
      //--
      case  8:mnAssistPLnVIII=pxVal;break;
      case  9:mnAssistPLnIX=pxVal;break;
      case 10:mnAssistPLnX=pxVal;break;
      case 11:mnAssistPLnXI=pxVal;break;
      case 12:mnAssistPLnXII=pxVal;break;
      case 13:mnAssistPLnXIII=pxVal;break;
      case 14:mnAssistPLnXIV=pxVal;break;
      case 15:mnAssistPLnXV=pxVal;break;
      //--
      default:break;
    }//..?
  }//++<
  
  public static final boolean ccGetAssistPL(int pxOrder){
    switch(pxOrder){
      case  0:return mnAssistPLnZ;
      case  1:return mnAssistPLnI;
      case  2:return mnAssistPLnII;
      case  3:return mnAssistPLnIII;
      case  4:return mnAssistPLnIV;
      case  5:return mnAssistPLnV;
      case  6:return mnAssistPLnVI;
      case  7:return mnAssistPLnVII;
      //--
      case  8:return mnAssistPLnVIII;
      case  9:return mnAssistPLnIX;
      case 10:return mnAssistPLnX;
      case 11:return mnAssistPLnXI;
      case 12:return mnAssistPLnXII;
      case 13:return mnAssistPLnXIII;
      case 14:return mnAssistPLnXIV;
      case 15:return mnAssistPLnXV;
      //--
      default:return false;
    }//..?
  }//++>
  
}//***eof
