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
import nextzz.pppswingui.SubAssistantPane;

public final class SubVPreparingDelegator {
  
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
    mnVDryerPL,mnVInclinedBelconPL,mnVColdAggreageSensorPL,
    
    //-- ag ** feeder ** operate
    mnVFChainMSSW,mnVFChainMSPL,
    mnVHorizontalBelconPL,
    
    //-- ag ** feeder ** ax
    mnVFRunningPLnI,mnVFRunningPLnII,mnVFRunningPLnIII,mnVFRunningPLnIV,
    mnVFRunningPLnV,mnVFRunningPLnVI,mnVFRunningPLnVII,mnVFRunningPLnVIII,
    mnVFRunningPLnIX,mnVFRunningPLnX,
    
    //-- ag ** feeder ** stuck
    mnVFStuckPLnI,mnVFStuckPLnII,mnVFStuckPLnIII,mnVFStuckPLnIV,
    mnVFStuckPLnV,mnVFStuckPLnVI,mnVFStuckPLnVII,mnVFStuckPLnVIII,
    mnVFStuckPLnIX,mnVFStuckPLnX,
    
    //-- fr
    
    //-- fr ** bag filter
    mnAirPulseDisableTGSW,mnAirPulseForceTGSW,
    mnAirPulseWithFeederTGSW,mnAirPulseWithAirTGSW,
    mnDustExtractionMSSW,mnDustExtractionMSPL,
    
    //-- fr ** dust silo
    mnDustSiloDischargeSW,mnDustSiloAirTGSW,
    mnDustSiloFullLV,mnDustSiloMidLV,mnDustSiloLowLV,
    mnDustMixerSW,mnDustMixerScrewSW,mnDustMixerPumpSW,
    
    //-- fr ** filler
    mnFillerSystemSW, mnFillerSystemPL,
    mnFillerUsingAnTGSW,mnFillerUsingCnTGSW,mnFillerSiloAirTGSW,
    mnFillerBinHLVPL,mnFillerBinMLVPL,mnFillerBinLLVPL,
    
    //-- as
    mnASSupplyPumpMSSW,mnASSupplyPumpMSPL
    
  ;//...
  
  public static volatile int
    
    //-- tph
    mnAGTonPerHourAD,
    
    //-- vf speed
    mnVFSpeedADnI,mnVFSpeedADnII,mnVFSpeedADnIII,mnVFSpeedADnIV,
    mnVFSpeedADnV,mnVFSpeedADnVI,mnVFSpeedADnVII,mnVFSpeedADnVIII,
    mnVFSpeedADnIX,mnVFSpeedADnX,
    
    //-- thermo
    mnSandTemperatureAD,mnPipeTemperatureAD,mnMixerTemperatureAD,
    
    //-- as tank selection
    mnASTankInWith,mnASTankOutWith
    
  ;//...
  
  //=== 
  
  public static final void ccWiring(){
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
    mnVFChainMSSW=SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(5).ccIsMousePressed();
    //[todo]:: %vf speed??% 
    
    //-- AG ** sim -> pc
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(4).ccSetIsActivated(mnAGChainMSPL);
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(5).ccSetIsActivated(mnVFChainMSPL);
    ssBindVFeederPL();
    
    
    //-- FR ** ui to simulator
    mnFillerSystemSW=SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(8).ccIsMousePressed();
    //-- FR ** simulator to ui
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(8).ccSetIsActivated(mnFillerSystemPL);
    SubVSurgeGroup.ccRefer().cmFillerBinLV
      .ccSetPercentage(ConstLocalUI.ccLeverage
        (mnFillerBinHLVPL, mnFillerBinMLVPL, mnFillerBinLLVPL));
    
    //-- AS
    
  }//+++
  
  private static void ssBindVFeederPL(){
    
    //-- runnning
    SubVFeederGroup.ccRefer().cmDesFeederRPMBox
      .get(1).ccSetIsActivated(mnVFRunningPLnI);
    SubVFeederGroup.ccRefer().cmDesFeederRPMBox
      .get(2).ccSetIsActivated(mnVFRunningPLnII);
    SubVFeederGroup.ccRefer().cmDesFeederRPMBox
      .get(3).ccSetIsActivated(mnVFRunningPLnIII);
    SubVFeederGroup.ccRefer().cmDesFeederRPMBox
      .get(4).ccSetIsActivated(mnVFRunningPLnIV);
    SubVFeederGroup.ccRefer().cmDesFeederRPMBox
      .get(5).ccSetIsActivated(mnVFRunningPLnV);
    SubVFeederGroup.ccRefer().cmDesFeederRPMBox
      .get(6).ccSetIsActivated(mnVFRunningPLnVI);
    //--
    SubVFeederGroup.ccRefer().cmDesFeederRPMBox
      .get(7).ccSetIsActivated(mnVFRunningPLnVII);
    SubVFeederGroup.ccRefer().cmDesFeederRPMBox
      .get(8).ccSetIsActivated(mnVFRunningPLnVIII);
    //--
    SubVFeederGroup.ccRefer().cmDesFeederRPMBox
      .get(9).ccSetIsActivated(mnVFRunningPLnIX);
    SubVFeederGroup.ccRefer().cmDesFeederRPMBox
      .get(10).ccSetIsActivated(mnVFRunningPLnX);
    
    //-- stiking
    SubVFeederGroup.ccRefer().cmDesFeederRPMGauge
      .get(1).ccSetIsActivated(mnVFStuckPLnI);
    SubVFeederGroup.ccRefer().cmDesFeederRPMGauge
      .get(2).ccSetIsActivated(mnVFStuckPLnII);
    SubVFeederGroup.ccRefer().cmDesFeederRPMGauge
      .get(3).ccSetIsActivated(mnVFStuckPLnIII);
    SubVFeederGroup.ccRefer().cmDesFeederRPMGauge
      .get(4).ccSetIsActivated(mnVFStuckPLnIV);
    SubVFeederGroup.ccRefer().cmDesFeederRPMGauge
      .get(5).ccSetIsActivated(mnVFStuckPLnV);
    SubVFeederGroup.ccRefer().cmDesFeederRPMGauge
      .get(6).ccSetIsActivated(mnVFStuckPLnVI);
    //--
    SubVFeederGroup.ccRefer().cmDesFeederRPMGauge
      .get(7).ccSetIsActivated(mnVFStuckPLnVII);
    SubVFeederGroup.ccRefer().cmDesFeederRPMGauge
      .get(8).ccSetIsActivated(mnVFStuckPLnVIII);
    //--
    SubVFeederGroup.ccRefer().cmDesFeederRPMGauge
      .get(9).ccSetIsActivated(mnVFStuckPLnVI);
    SubVFeederGroup.ccRefer().cmDesFeederRPMGauge
      .get(10).ccSetIsActivated(mnVFStuckPLnX);
    
  }//+++
  
  private static void ssBindAsphaultSupply(){
     
    //-- pc >> plc
    mnASSupplyPumpMSSW=SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(6).ccIsMousePressed();
    //[todo]:: % mnASTankInWith
    //[todo]::mnASTankOutWith
    
    //-- plc >> pc
    SubOperativeGroup.ccRefer().cmDesMotorSW
      .get(6).ccSetIsActivated(mnASSupplyPumpMSPL);
    
  }//+++
  
 }//***eof
