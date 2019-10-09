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

package nextzz.pppsimulate;

import kosui.ppplogic.ZcDelayor;
import kosui.ppplogic.ZcHookFlicker;
import kosui.ppplogic.ZcOffDelayTimer;
import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZcTimer;
import kosui.ppplogic.ZiTask;
import kosui.ppputil.VcLocalTagger;
import nextzz.pppdelegate.SubAnalogDelegator;
import nextzz.pppdelegate.SubVProvisionDelegator;

public final class SubVProvisionTask implements ZiTask{

  private static SubVProvisionTask self = null;
  public static SubVProvisionTask ccRefer() {
    if(self==null){self=new SubVProvisionTask();}
    return self;
  }//+++
  private SubVProvisionTask(){}//..!

  //===

  //-- misc ** motor
  //-- misc ** motor ** v comporessor
  public final ZcMotor dcVCompressor = new ZcMotor(32);
  private final ZcHookFlicker cmVCompressorHOOK = new ZcHookFlicker();
  //-- misc ** motor ** mixer
  public final ZcMotor dcMixer = new ZcMotor(48);
  private final ZcHookFlicker cmMixerHOOK = new ZcHookFlicker();
  //-- misc ** motor ** v exfan
  public final ZcMotor dcVExFan = new ZcMotor(36);
  private final ZcHookFlicker cmVExFanHooker = new ZcHookFlicker();
  //-- misc ** motor ** v b comp
  public final ZcMotor dcVBCompressor = new ZcMotor(17);
  private final ZcHookFlicker cmVBCompressorHooker = new ZcHookFlicker();

  //-- ag chain ** motor
  private final ZcChainController cmAGChainCTRL = new ZcChainController(3, 6);
  public final ZcMotor dcScreen = new ZcMotor(4);
  public final ZcMotor dcHotElevator = new ZcMotor(4);
  public final ZcMotor dcDryer = new ZcMotor(4);
  public final ZcMotor dcInclinedBelcon = new ZcMotor(4);
  public final ZcMotor dcHorizontalBelcon = new ZcMotor(4);

  //-- filler supply
  private final ZcHookFlicker  cmFillerSupplyHOOK = new ZcHookFlicker();
  private final ZcTimer cmFillerBinInputStopTM = new ZcOffDelayTimer(80);
  private final ZcTimer cmFillerBinInpuStartTM = new ZcOnDelayTimer(32);
  private final ZcTimer cmFillerBinLevelDelayor = new ZcDelayor(7, 888);
  public final ZcMotor dcFillerElevator = new ZcMotor(4);
  public final ZcMotor dcFillerScrew = new ZcMotor(4);
  //[todo]::fillerScrewB
  public final ZcContainer dcFillerSilo = new ZcContainer(0.8f);
  //[todo]::fillerSiloB
  public final ZcContainer dcFillerBin = new ZcContainer(2.2f);

  //-- dust extraction
  private final ZcHookFlicker cmDustExtractionHOOK = new ZcHookFlicker();
  private final ZcTimer cmDustSiloInputStopTM = new ZcOffDelayTimer(80);
  private final ZcTimer cmDustSiloInputStartTM = new ZcOnDelayTimer(32);
  private final ZcTimer cmDustSiloLevelDelayor = new ZcDelayor(16, 16);
  private final ZcChainController cmDustExtractionCTRL = new ZcChainController(2, 3);
  public boolean dcDustSiloDischargeGateMV=false;
  public boolean dcDustSiloAirationMV=false;
  public final ZcMotor dcDustSiloElevator = new ZcMotor(4);
  public final ZcMotor dcDustExtractionScrew = new ZcMotor(4);
  public final ZcMotor dcBagDustScrew = new ZcMotor(4);
  public final ZcContainer dcBagHopper = new ZcContainer(1.8f);
  public final ZcContainer dcDustSilo = new ZcContainer(1.2f);

  //===

  @Override public void ccScan(){

    //-- misc ** motor
    //-- misc ** motor ** v comprssor
    cmVCompressorHOOK.ccHook
      (SubVProvisionDelegator.mnVCompressorMSSW,dcVCompressor.ccIsTripped());
    dcVCompressor.ccContact(cmVCompressorHOOK.ccIsHooked());
    SubVProvisionDelegator.mnVCompressorMSPL
      = MainSimulator.ccMoterFeedBackLamp(cmVCompressorHOOK, dcVCompressor);
    SubAnalogDelegator.mnCTSlotZ=dcVCompressor.ccGetCT();

    //-- misc ** motor ** mixer
    cmMixerHOOK.ccHook
      (SubVProvisionDelegator.mnMixerMSSW,dcMixer.ccIsTripped());
    dcMixer.ccContact(cmMixerHOOK.ccIsHooked());
    SubVProvisionDelegator.mnMixerMSPL
      = MainSimulator.ccMoterFeedBackLamp(cmMixerHOOK, dcMixer);
    SubAnalogDelegator.mnCTSlotI=dcMixer.ccGetCT();
    SubVProvisionDelegator.mnMixerIconPL=dcMixer.ccIsContacted();

    //-- misc ** motor ** exfan
    cmVExFanHooker.ccHook
      (SubVProvisionDelegator.mnVExfanMSSW,dcVExFan.ccIsTripped());
    dcVExFan.ccContact(cmVExFanHooker.ccIsHooked());
    SubVProvisionDelegator.mnVExfanMSPL
      = MainSimulator.ccMoterFeedBackLamp(cmVExFanHooker, dcVExFan);
    SubAnalogDelegator.mnCTSlotII=dcVExFan.ccGetCT();
    SubVProvisionDelegator.mnVExfanIconPL=dcVExFan.ccIsContacted();

    //-- misc ** motor ** v burner compressor
    cmVBCompressorHooker.ccHook
      (SubVProvisionDelegator.mnVBCompressorMSSW,dcVBCompressor.ccIsTripped());
    dcVBCompressor.ccContact(cmVBCompressorHooker.ccIsHooked());
    SubVProvisionDelegator.mnVBCompressorMSPL
      = MainSimulator.ccMoterFeedBackLamp(cmVBCompressorHooker, dcVBCompressor);
    SubAnalogDelegator.mnCTSlotIII=dcVBCompressor.ccGetCT();

    //-- ag supply chain
    //-- ag supply chain ** takewith
    cmAGChainCTRL.ccSetTrippedAt(1, dcScreen.ccIsTripped());
    cmAGChainCTRL.ccSetTrippedAt(2, dcHotElevator.ccIsTripped());
    cmAGChainCTRL.ccSetTrippedAt(3, dcDryer.ccIsTripped());
    cmAGChainCTRL.ccSetTrippedAt(4, dcInclinedBelcon.ccIsTripped());
    cmAGChainCTRL.ccSetTrippedAt(5, dcHorizontalBelcon.ccIsTripped());
    cmAGChainCTRL.ccSetConfirmedAt(1, dcScreen.ccIsContacted());
    cmAGChainCTRL.ccSetConfirmedAt(2, dcHotElevator.ccIsContacted());
    cmAGChainCTRL.ccSetConfirmedAt(3, dcDryer.ccIsContacted());
    cmAGChainCTRL.ccSetConfirmedAt(4, dcInclinedBelcon.ccIsContacted());
    cmAGChainCTRL.ccSetConfirmedAt(5, dcHorizontalBelcon.ccIsContacted());
    //-- ag supply chain ** run
    cmAGChainCTRL.ccSetRun(SubVProvisionDelegator.mnAGChainMSSW);
    cmAGChainCTRL.ccRun();
    //-- ag supply chain ** give ** mc
    dcScreen.ccContact(cmAGChainCTRL.ccGetOutputFor(1));
    dcHotElevator.ccContact(cmAGChainCTRL.ccGetOutputFor(2));
    dcDryer.ccContact(cmAGChainCTRL.ccGetOutputFor(3));
    dcInclinedBelcon.ccContact(cmAGChainCTRL.ccGetOutputFor(4));
    dcHorizontalBelcon.ccContact(cmAGChainCTRL.ccGetOutputFor(5));
    //-- ag supply chain ** give ** pc ** pl
    SubVProvisionDelegator.mnAGChainMSPL
      = cmAGChainCTRL.ccGetFlasher(MainSimulator.ccOneSecondClock());
    SubVProvisionDelegator.mnVDryerPL
      = dcDryer.ccIsContacted();
    SubVProvisionDelegator.mnVInclinedBelconPL
      = dcInclinedBelcon.ccIsContacted();
    SubVProvisionDelegator.mnVHorizontalBelconPL
      = dcHorizontalBelcon.ccIsContacted();
    //-- ag supply chain ** give ** pc ** ct
    SubAnalogDelegator.mnCTSlotVI=dcScreen.ccGetCT();
    SubAnalogDelegator.mnCTSlotVII=dcHotElevator.ccGetCT();
    SubAnalogDelegator.mnCTSlotVIII=dcDryer.ccGetCT();
    SubAnalogDelegator.mnCTSlotIX=dcInclinedBelcon.ccGetCT();
    SubAnalogDelegator.mnCTSlotX=dcHorizontalBelcon.ccGetCT();
    
    //-- bag filter air pulse
    /* 7 */SubVProvisionDelegator.mnAirPulsingPL
      = MainSimulator.ccSelectModeDuo(
        !SubVProvisionDelegator.mnAirPulseDisableTGSW
          && MainSimulator.ccHalfSecondPLS(),
        SubVProvisionDelegator.mnAirPulseWithAirTGSW,
          (dcVCompressor.ccIsContacted()
            ||SubVProvisionDelegator.mnAirPulseForceTGSW),
        SubVProvisionDelegator.mnAirPulseWithFeederTGSW,
          ((dcVCompressor.ccIsContacted()
            ||SubFeederTask.ccRefer().ccGetVFeederStartFlag())
              ||SubVProvisionDelegator.mnAirPulseForceTGSW)
      );
    
    //-- filler supply
    //-- filler supply ** takewith
    cmFillerSupplyHOOK.ccHook(SubVProvisionDelegator.mnFillerSystemSW);
    cmFillerBinInputStopTM.ccAct(cmFillerSupplyHOOK.ccIsHooked());
    cmFillerBinInpuStartTM.ccAct(cmFillerSupplyHOOK.ccIsHooked());
    //-- filler supply ** hardware io
    cmFillerBinLevelDelayor.ccAct(dcFillerBin.ccIsMiddle());
    dcFillerElevator.ccContact(cmFillerBinInputStopTM.ccIsUp());
    dcFillerScrew.ccContact(!cmFillerBinLevelDelayor.ccIsUp()
      &&cmFillerBinInpuStartTM.ccIsUp()
    );
    //-- filler supply ** feedback
    SubVProvisionDelegator.mnFillerBinMLVPL
      =SubVProvisionDelegator.mnFillerBinLLVPL
      =dcFillerBin.ccIsMiddle();
    SubVProvisionDelegator.mnFillerSystemPL=cmFillerBinInputStopTM.ccIsUp()&&
      (MainSimulator.ccOneSecondClock()||dcFillerScrew.ccIsContacted());
    SubAnalogDelegator.mnFillerSiloLV = dcFillerSilo.ccGetScaledValue(255);
    SubAnalogDelegator.mnCTSlotXV=dcFillerElevator.ccGetCT();

    //-- dust extraction
    //-- dust extraction ** takewith
    cmDustExtractionHOOK.ccHook(SubVProvisionDelegator.mnDustExtractionMSSW);
    cmDustSiloInputStopTM.ccAct(cmDustExtractionHOOK.ccIsHooked());
    cmDustSiloInputStartTM.ccAct(cmDustExtractionHOOK.ccIsHooked());
    //-- dust extraction ** controller
    cmDustExtractionCTRL.ccSetTrippedAt
      (1,dcDustExtractionScrew.ccIsTripped());
    cmDustExtractionCTRL.ccSetTrippedAt
      (2,dcBagDustScrew.ccIsTripped());
    cmDustExtractionCTRL.ccSetConfirmedAt
      (1,dcDustExtractionScrew.ccIsContacted());
    cmDustExtractionCTRL.ccSetConfirmedAt
      (2,dcBagDustScrew.ccIsContacted());
    boolean lpDustExtractionStartHLD
      = dcDustSiloElevator.ccIsContacted()
       && cmDustExtractionHOOK.ccIsHooked()
       && (!cmDustSiloLevelDelayor.ccIsUp());
    cmDustExtractionCTRL.ccSetRun
      (lpDustExtractionStartHLD, !lpDustExtractionStartHLD);
    cmDustExtractionCTRL.ccRun();
    //-- dust extraction ** output
    dcDustSiloElevator.ccContact(cmDustSiloInputStopTM.ccIsUp());
    dcDustExtractionScrew.ccContact(cmDustExtractionCTRL.ccGetOutputFor(1));
    dcBagDustScrew.ccContact(cmDustExtractionCTRL.ccGetOutputFor(2));
    SubVProvisionDelegator.mnDustExtractionMSPL=
      cmDustSiloInputStopTM.ccIsUp()
        &&
      (cmDustExtractionCTRL.ccIsAllEngaged()||MainSimulator.ccOneSecondClock());
    dcDustSiloDischargeGateMV=SubVProvisionDelegator.mnDustSiloDischargeSW;
    //-- dust extraction ** feedback
    SubVProvisionDelegator.mnBagHopperLLV=dcBagHopper.ccIsMiddle();
    SubVProvisionDelegator.mnBagHopperHLV=dcBagHopper.ccIsFull();
    SubAnalogDelegator.mnDustSiloLV=dcDustSilo.ccGetScaledValue(255);
    SubAnalogDelegator.mnCTSlotXII=dcBagDustScrew.ccGetCT();

    //--???


  }//+++

  //===

  @Override public void ccSimulate(){

    //-- misc ** motor
    dcVCompressor.ccSimulate(0.76f);
    dcMixer.ccSimulate(0.65f);
    dcVExFan.ccSimulate(SubVCombusTask.ccRefer()
      .dcVExfanDegree.ccGetProportion()/2f+0.44f);
    dcVBCompressor.ccSimulate(0.45f);

    //-- ag chain
    dcScreen.ccSimulate(0.66f);
    dcHotElevator.ccSimulate(0.65f);
    dcDryer.ccSimulate(0.64f);
    dcInclinedBelcon.ccSimulate(0.63f);
    dcHorizontalBelcon.ccSimulate(0.62f);

    //-- fr chain
    dcFillerScrew.ccSimulate(0.66f);
    dcFillerElevator.ccSimulate(0.64f);
    dcFillerSilo.ccCharge(127);
    ZcContainer.ccTransfer(
      dcFillerSilo, dcFillerBin,
      dcFillerScrew.ccIsContacted()&&dcFillerElevator.ccIsContacted(),
      64
    );

    //--
    if(dcVExFan.ccIsContacted()){
      dcBagHopper.ccCharge(2);
      if(SubFeederTask.ccRefer().ccGetColdAggregateSensor()){
        dcBagHopper.ccCharge(8);
      }//..?
    }//..?
    if(dcDustSiloDischargeGateMV){dcDustSilo.ccDischarge(64);}
    dcDustSiloElevator.ccSimulate(0.45f);
    dcDustExtractionScrew.ccSimulate(0.47f);
    dcBagDustScrew.ccSimulate(0.55f);
    ZcContainer.ccTransfer(
      dcBagHopper, dcDustSilo,
      dcDustSiloElevator.ccIsContacted()
        && dcDustExtractionScrew.ccIsContacted()
        && dcBagDustScrew.ccIsContacted(),
      32
    );

  }//+++

  //===

  @Deprecated public static final void tstTagDustExtractionSIMS(){
    VcLocalTagger.ccTag("d-e-offtm",self.cmDustSiloInputStopTM);
    VcLocalTagger.ccTag("d-e-ctrl",self.cmDustExtractionCTRL);
    VcLocalTagger.ccTag("#37B", self.dcDustSiloElevator);
    VcLocalTagger.ccTag("#37", self.dcDustExtractionScrew);
    VcLocalTagger.ccTag("#33", self.dcBagDustScrew);
    VcLocalTagger.ccTag("c-c-bagh", self.dcBagHopper);
    VcLocalTagger.ccTag("c-c-dustsilo", self.dcDustSilo);
  }//+++

}//***eof
