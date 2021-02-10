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

package nextzz.pppmain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import javax.swing.SwingUtilities;
import kosui.ppplocalui.EcButton;
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplocalui.EiTriggerable;
import kosui.pppmodel.McConst;
import kosui.pppmodel.McFactory;
import kosui.pppswingui.ScConst;
import kosui.ppputil.VcArrayUtility;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcLocalConsole;
import kosui.ppputil.VcLocalCoordinator;
import kosui.ppputil.VcLocalTagger;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcSwingCoordinator;
import kosui.ppputil.VcTranslator;
import nextzz.pppdelegate.SubFeederDelegator;
import nextzz.pppdelegate.SubVCombustDelegator;
import nextzz.pppdelegate.SubVProvisionDelegator;
import nextzz.pppdelegate.SubWeighingDelegator;
import nextzz.ppplocalui.SubIndicativeGroup;
import nextzz.ppplocalui.SubOperativeGroup;
import nextzz.ppplocalui.SubVBondGroup;
import nextzz.ppplocalui.SubVFeederGroup;
import nextzz.ppplocalui.SubWeigherGroup;
import nextzz.pppmodel.MainFileManager;
import nextzz.pppmodel.MainPlantModel;
import nextzz.pppmodel.MainSpecificator;
import nextzz.pppmodel.SubAnalogScalarManager;
import nextzz.pppswingui.SubAssistantPane;
import nextzz.pppsetting.MainSettingManager;
import nextzz.pppsetting.MiSettingItem;
import nextzz.pppmodel.SubDegreeControlManager;
import nextzz.pppmodel.SubRecipeManager;
import nextzz.pppmodel.SubWeighControlManager;
import nextzz.pppmodel.SubWeighStatisticManager;
import nextzz.pppsimulate.MainSimulator;
import nextzz.pppsimulate.SubErrorTask;
import nextzz.pppswingui.ScCSVFilePrinter;
import nextzz.pppswingui.SubFeederPane;
import nextzz.pppswingui.SubMonitorPane;
import nextzz.pppswingui.SubSettingPane;

public final class MainActionManager {
  
  private static final MainActionManager SELF = new MainActionManager();
  public static MainActionManager ccRefer(){return SELF;}//+++
  private MainActionManager(){}//..!

  //=== trigger ** so?
  
  public final EiTriggerable cmBackgroundRefreshing = new EiTriggerable() {
    @Override public void ccTrigger(){
      MainSketch.ccGetPApplet().background(MainSketch.C_COLOR_BACKGROUD);
      VcLocalCoordinator.ccUpdatePassive();
    }//+++
  };//***
  
  public final EiTriggerable cmQuitting = new EiTriggerable() {
    @Override public void ccTrigger(){
      MainWindow.ccRefer().cmAppClosing.windowClosing(null);
    }//+++
  };//***
  
  public final Runnable cmPoppingness = new Runnable() {
    @Override public void run() {
      MainWindow.ccRefer().cmWindow.setVisible(true);
      MainWindow.ccRefer().cmWindow.toFront();
      MainWindow.pbIsVisible=true;
    }//+++
  };//***
  
  public final EiTriggerable cmPopping = new EiTriggerable() {
    @Override public void ccTrigger() {
      SwingUtilities.invokeLater(cmPoppingness);
    }//+++
  };//***
 
  public final EiTriggerable cmInputFocusCleaning = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcLocalCoordinator.ccClearCurrentInputFocus();
    }//+++
  };//***
  
  public final EiTriggerable cmInputtableClicking = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcLocalCoordinator.ccSetCurrentInputFocus();
    }//+++
  };//***
  
  public final EiTriggerable cmInputSettling = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcLocalCoordinator.ccInvokeLater(cmBackgroundRefreshing);
      if(VcLocalCoordinator.ccHasInputtableFocused()){
        VcLocalCoordinator.ccToNextInputIndex();
      }//..?
    }//+++
  };//***
  
  public final EiTriggerable cmNumericInputting = new EiTriggerable() {
    @Override public void ccTrigger(){
      EcElement lpSource
       = VcLocalCoordinator.ccGetCurrentFocusedBox();
      if(lpSource instanceof EcValueBox){
        int lpInput = VcNumericUtility
          .ccParseIntegerString(VcLocalConsole.ccGetLastAccepted());
        SubWeighControlManager.ccRefer()
          .ccSetBookModelValue((EcValueBox)lpSource, lpInput);
      }//..?
    }//+++
  };//***
  
  //=== trigger ** operative
  
  //=== trigger ** operative ** weigher locker
  
  public final EiTriggerable cmAGLockFlipping
    = new EiTriggerable() {
    @Override public void ccTrigger() {
      int lpID=VcLocalCoordinator.ccGetMouseOverID();
      lpID-=SubWeigherGroup.ccRefer().cmLesAGLockSW.get(0).ccGetID();
      boolean lpFlip = SubWeighingDelegator.ccGetAGLockSW(lpID);
      lpFlip=!lpFlip;
      SubWeighingDelegator.ccSetAGLockSW(lpID, lpFlip);
    }//+++
  };//***
  
  public final EiTriggerable cmFRLockFlipping
    = new EiTriggerable() {
    @Override public void ccTrigger() {
      int lpID=VcLocalCoordinator.ccGetMouseOverID();
      lpID-=SubWeigherGroup.ccRefer().cmLesFRLockSW.get(0).ccGetID();
      boolean lpFlip = SubWeighingDelegator.ccGetFRLockSW(lpID);
      lpFlip=!lpFlip;
      SubWeighingDelegator.ccSetFRLockSW(lpID, lpFlip);
    }//+++
  };//***
  
  public final EiTriggerable cmASLockFlipping
    = new EiTriggerable() {
    @Override public void ccTrigger() {
      int lpID=VcLocalCoordinator.ccGetMouseOverID();
      lpID-=SubWeigherGroup.ccRefer().cmLesASLockSW.get(0).ccGetID();
      boolean lpFlip = SubWeighingDelegator.ccGetASLockSW(lpID);
      lpFlip=!lpFlip;
      SubWeighingDelegator.ccSetASLockSW(lpID, lpFlip);
    }//+++
  };//***
  
  //[todo]::cmRCLockFlipping
  //[todo]::cmADLockFlipping
  
  //=== trigger ** operative ** zero adjust
  
  public final EiTriggerable cmAGZeroAdjustFlipping = new EiTriggerable() {
    @Override public void ccTrigger() {
      MainPlantModel.ccRefer().vmAGZeroAdjustSelector = 
        ! MainPlantModel.ccRefer().vmAGZeroAdjustSelector;
    }//+++
  };//***
  
  public final EiTriggerable cmFRZeroAdjustFlipping = new EiTriggerable() {
    @Override public void ccTrigger() {
      MainPlantModel.ccRefer().vmFRZeroAdjustSelector = 
        ! MainPlantModel.ccRefer().vmFRZeroAdjustSelector;
    }//+++
  };//***
  
  public final EiTriggerable cmASZeroAdjustFlipping = new EiTriggerable() {
    @Override public void ccTrigger() {
      MainPlantModel.ccRefer().vmASZeroAdjustSelector = 
        ! MainPlantModel.ccRefer().vmASZeroAdjustSelector;
    }//+++
  };//***
  
  //[todo]::cmRCZeroAdjustFlipping
  //[todo]::cmADZeroAdjustFlipping
  
  public final EiTriggerable cmZeroAdjustApplying = new EiTriggerable() {
    @Override public void ccTrigger() {
      if(MainPlantModel.ccRefer().vmAGZeroAdjustSelector){
        int lpKG = SubAnalogScalarManager.ccRefer().ccGetAGCellKG();
        SubAnalogScalarManager.ccRefer().ccSetAGReviseOffsetKG(-1*lpKG);
      }//..?
      if(MainPlantModel.ccRefer().vmFRZeroAdjustSelector){
        int lpKG = SubAnalogScalarManager.ccRefer().ccGetFRCellKG();
        SubAnalogScalarManager.ccRefer().ccSetFRReviseOffsetKG(-1*lpKG);
      }//..?
      if(MainPlantModel.ccRefer().vmASZeroAdjustSelector){
        int lpKG = SubAnalogScalarManager.ccRefer().ccGetASCellKG();
        SubAnalogScalarManager.ccRefer().ccSetASReviseOffsetKG(-1*lpKG);
      }//..?
      MainPlantModel.ccRefer().vmAGZeroAdjustSelector=false;
      MainPlantModel.ccRefer().vmFRZeroAdjustSelector=false;
      MainPlantModel.ccRefer().vmASZeroAdjustSelector=false;
    }//+++
  };//***
  
  //=== trigger ** operative ** mixer gate
  
  public final EiTriggerable cmMixerGateHoldClicking = new EiTriggerable() {
    @Override public void ccTrigger() {
      SubWeighingDelegator.mnMixerGateHoldSW=true;
      SubWeighingDelegator.mnMixerGateOpenSW=false;
    }//+++
  };//***
  
  public final EiTriggerable cmMixerGateAutoClicking = new EiTriggerable() {
    @Override public void ccTrigger() {
      SubWeighingDelegator.mnMixerGateHoldSW=false;
      SubWeighingDelegator.mnMixerGateOpenSW=false;
    }//+++
  };//***
  
  public final EiTriggerable cmMixerGateOpenClicking = new EiTriggerable() {
    @Override public void ccTrigger() {
      SubWeighingDelegator.mnMixerGateHoldSW=false;
      SubWeighingDelegator.mnMixerGateOpenSW=true;
    }//+++
  };//***
  
  //=== trigger ** operative ** v feeder group
  
  /**
   * if there is a accepted recipe, rate with the recipe.
   * if there is no accepted recipe, rate with first recipe if valid.
   * if there is no accepted recipe and the first recipe is not valid,
   *   make them all equal.
   */
  public final EiTriggerable cmVFeederRatioAutoFitting
    = new EiTriggerable() {
    @Override public void ccTrigger() {
      char lpFlag = 'n';
      int lpAccepted = SubWeighControlManager.ccRefer().ccGetAcceptedRecipeID();
      int lpFirst = SubWeighControlManager.ccRefer().ccGetFirsetRecipeID();
      if(SubRecipeManager.ccRefer().ccHasRecipe(lpFirst)){lpFlag='f';}
      if(SubRecipeManager.ccRefer().ccHasRecipe(lpAccepted)){lpFlag='a';}
      switch(lpFlag){
        
        //-- case of accepted raw
        case 'a':
        {
          for(int i=MainPlantModel.C_VF_UI_VALID_HEAD;
                  i<=MainSpecificator.ccRefer().vmVFeederAmount;
                  i++)
          {
            float lpPercentage = SubRecipeManager.ccRefer()
              .ccGetOnWeighingPercentage('G', i);
            float lpTon
              = lpPercentage*(MainPlantModel.ccRefer().vmVRatioBaseTPH)/10f;
            int lpRoll = SubAnalogScalarManager.ccRefer()
              .ccToVFeederFluxRPM(i, (int)lpTon);//[todo]::do you remember somthing called `sieving`?
            SubFeederPane.ccRefer().cmLesVFeederBlock.get(i).ccSetValue(lpRoll);
          }
        }
        break;
        
        //-- case of first raw
        case 'f':
        {
          for(int i=MainPlantModel.C_VF_UI_VALID_HEAD;
                  i<=MainSpecificator.ccRefer().vmVFeederAmount;
                  i++)
          {
            float lpPercentage = SubRecipeManager.ccRefer()
              .ccGetPercentage(lpFirst, 'G', i);
            float lpTon
              = lpPercentage*(MainPlantModel.ccRefer().vmVRatioBaseTPH)/10f;
            int lpRoll = SubAnalogScalarManager.ccRefer()
              .ccToVFeederFluxRPM(i, (int)lpTon);//[todo]::do you remember somthing called `sieving`?
            SubFeederPane.ccRefer().cmLesVFeederBlock.get(i).ccSetValue(lpRoll);
          }
        }
        break;
        
        //-- case of equalizer
        default:
        {
          float lpPercentage = 100f/MainSpecificator.ccRefer().vmVFeederAmount;
          float lpTon
            = lpPercentage*(MainPlantModel.ccRefer().vmVRatioBaseTPH)/10f;
          for(int i=MainPlantModel.C_VF_UI_VALID_HEAD;
                  i<=MainSpecificator.ccRefer().vmVFeederAmount;
                  i++)
          {
            int lpRoll = SubAnalogScalarManager.ccRefer()
              .ccToVFeederFluxRPM(i, (int)lpTon);//[todo]::do you remember somthing called `sieving`?
            SubFeederPane.ccRefer().cmLesVFeederBlock.get(i).ccSetValue(lpRoll);
          }
        }
        break;
        
      }//..?
    }//+++
  };//***
  
  public final EiTriggerable cmVFeederRatioDecrementing
    = new EiTriggerable() {
    @Override public void ccTrigger() {
      SubFeederPane.ccRefer().ccVRatioShift(-2f);
    }//+++
  };//***
  
  public final EiTriggerable cmVFeederRatioIncrementing
    = new EiTriggerable() {
    @Override public void ccTrigger() {
      SubFeederPane.ccRefer().ccVRatioShift(2f);
    }//+++
  };//***
  
  //=== trigger ** operative ** v bond group
  
  public final EiTriggerable cmVTargetTemperatureAdjusting
    = new EiTriggerable() {
    @Override public void ccTrigger() {
      SwingUtilities.invokeLater(new Runnable() {
        @Override public void run() {
          String lpInput = ScConst.ccGetStringByInputBox(
            VcTranslator.tr("_m_adjust_vb_target_temperature"),
            Integer.toString(SubDegreeControlManager.ccRefer().vmVTargetCELC)
          );
          if(!VcConst.ccIsValidString(lpInput)){return;}
          if(lpInput.equals(ScConst.C_M_CANCEL)){return;}
          if(!VcNumericUtility.ccIsIntegerString(lpInput)){
            ScConst.ccErrorBox(VcTranslator.tr("_m_general_format_error"));
            return;
          }//..!
          SubDegreeControlManager.ccRefer().vmVTargetCELC
            = VcNumericUtility.ccParseIntegerString(lpInput)&0xFF;
          VcLocalCoordinator.ccInvokeLater
            (SubDegreeControlManager.ccRefer().cmVTemperatureTargetSettling);
        }//+++
      });//***
    }//+++
  };//***
  
  public final EiTriggerable cmVTargetTemperatureDecrementing
    = new EiTriggerable() {
    @Override public void ccTrigger() {
      SubDegreeControlManager.ccRefer().vmVTargetCELC
        -= SubDegreeControlManager.ccRefer().vmVTargetStepCELC;
      SubDegreeControlManager.ccRefer().vmVTargetCELC &= 0xFF;
      VcLocalCoordinator.ccInvokeLater
        (SubDegreeControlManager.ccRefer().cmVTemperatureTargetSettling);
    }//+++
  };//***
  
  public final EiTriggerable cmVTargetTemperatureIncrementing
    = new EiTriggerable() {
    @Override public void ccTrigger() {
      SubDegreeControlManager.ccRefer().vmVTargetCELC
        += SubDegreeControlManager.ccRefer().vmVTargetStepCELC;
      SubDegreeControlManager.ccRefer().vmVTargetCELC &= 0xFF;
      VcLocalCoordinator.ccInvokeLater
        (SubDegreeControlManager.ccRefer().cmVTemperatureTargetSettling);
    }//+++
  };//***
  
  //=== trigger ** swing
   
  public final EiTriggerable cmHiding = new EiTriggerable() {
    @Override public void ccTrigger() {
      MainWindow.ccRefer().cmWindow.setVisible(false);
      MainWindow.pbIsVisible=false;
    }//+++
  };//***
  
  public final Runnable cmHidingness = new Runnable() {
    @Override public void run() {
      cmHiding.ccTrigger();
    }//+++
  };//***
  
  public final EiTriggerable cmErrorCleaning = new EiTriggerable() {
    @Override public void ccTrigger() {
      MainPlantModel.ccRefer().cmErrorClearHoldingFLG=true;
    }//+++
  };//***
  
  public final EiTriggerable cmSettingModifying = new EiTriggerable() {
    @Override public void ccTrigger() {
      int lpListIndex = SubSettingPane.ccRefer()
        .cmList.ccGetCurrentIndex();
      int lpTableIndex = SubSettingPane.ccRefer()
        .cmTable.ccGetSelectedRowIndex();
      if(
          (lpListIndex<0)
        ||(lpTableIndex<0)
      ){
        ScConst.ccErrorBox(VcTranslator.tr("_m_warn_of_none_selection"));
        return;
      }//..?
      MiSettingItem lpItem = MainSettingManager.ccRefer()
        .ccGetSelectedItem(lpListIndex, lpTableIndex);
      if(lpItem==null){
        ScConst.ccErrorBox(VcTranslator.tr("_m_warn_of_unkoun_error"));
        return;
      }//..?
      String lpInput = ScConst.ccGetStringByInputBox(
        SubSettingPane.ccRefer().cmPane,
        lpItem.ccGetLimitationInfo(),
        lpItem.ccGetValue()
      );
      if(!VcConst.ccIsValidString(lpInput)){return;}
      lpItem.ccSetValue(lpInput);
      SubSettingPane.ccRefer().cmTable.ccRefresh();
    }//+++
  };//***
  
  public final EiTriggerable cmPrinting = new EiTriggerable() {
    @Override public void ccTrigger() {
      
      //-- check in
      ScConst.ccSetFileChooserCurrentDirectoryLocation
        (MainFileManager.ccRefer()
          .ccGetWeighExportDirectory().getAbsolutePath());
      File lpFile = ScConst.ccGetFileByFileChooser('f');
      boolean lpPreCkeck
        = McConst.ccVerifyFileForLoading(lpFile, "csv",999999l)==0;
      if(!lpPreCkeck){
        ScConst.ccErrorBox("_m_invalid_file");
        return;
      }//..?
      
      //-- read in
      List<String> lpDesLine = McFactory.ccLoadTextFromFile(lpFile);
      if(!VcArrayUtility.ccIsValidList(lpDesLine, 1)){
        ScConst.ccErrorBox("_m_unknown_error");
      }//..?
      
      //-- work
      ScCSVFilePrinter lpWorker
          //[todo]:: let s make some notch
        = new ScCSVFilePrinter(lpDesLine, 12, 8, 10,null);
      lpWorker.execute();
      
    }//+++
  };//***
  
  //=== trigger ** what?
  
  public final EiTriggerable cmDummyWeighRecordGenerating = new EiTriggerable(){
    @Override public void ccTrigger() {
      for(int i=0;i<16;i++){
        SubWeighStatisticManager.ccRefer().ccLogRecord(
          990,99.9f,
          new float[]{
            10f,11f,12f,13f, 14f,15f,16f,17f,
            20f,21f,22f,23f,
            30f,31f,32f,33f,
            40f,41f,42f,43f,
            50f,51f,52f,53f
          }
        );
      }//..~
      SwingUtilities.invokeLater(SubMonitorPane.ccRefer()
        .cmWeighResultTableRefreshingness);
      VcLocalConsole.ccSetMessage("[echo]dmwsr::generated");
    }//+++
  };//***
  
  public final EiTriggerable cmDebugging = new EiTriggerable() {
    @Override public void ccTrigger(){
      MainSketch.pbDebugMode=!MainSketch.pbDebugMode;
      VcLocalTagger.ccSetIsVisible(MainSketch.pbDebugMode);
      VcConst.ccSetDoseLog(MainSketch.pbDebugMode);
      VcLocalConsole.ccSetMessage("[echo]debug:"
        + (MainSketch.pbDebugMode?"on":"off")
      );
    }//+++
  };//***
  
  public final EiTriggerable cmMotorThmalResetting = new EiTriggerable() {
    @Override public void ccTrigger(){
      MainSimulator.tstResetAllMotorTrip();
      VcLocalConsole.ccSetMessage("[echo]mrst::resetting motor thermal status");
    }//+++
  };//***
  
  public final EiTriggerable cmErrorBitSetting = new EiTriggerable() {
    @Override public void ccTrigger(){
      int lpErrorIndex = VcNumericUtility
        .ccParseIntegerString(VcLocalConsole.ccGetLastAccepted(1));
      SubErrorTask.ccRefer().ccSetMessageBit(lpErrorIndex, true);
      VcLocalConsole.ccSetMessage("[echo]terr:"+lpErrorIndex);
    }//+++
  };//***
  
  //=== swing 
  
  public final ActionListener cmNotchListener = new ActionListener() {
    @Override public void actionPerformed(ActionEvent e) {
      
      //[tofigure]::VcConst.ccPrintln("aha!!");
      //            .. how many time will this thing get called??
      
      //-- tower
      SubVProvisionDelegator.mnTowerBlowerTGSW=SubAssistantPane
        .ccRefer().cmTowerBlowerNT.getSelectedIndex()==0;
      
      //-- aggregate
      
      //-- aggregate **filter ** air pulse
      SubVProvisionDelegator.mnAirPulseDisableTGSW=SubAssistantPane
        .ccRefer().cmAirPulseOperateNT.getSelectedIndex()==1;
      SubVProvisionDelegator.mnAirPulseWithAirTGSW=SubAssistantPane
        .ccRefer().cmAirPulseModeNT.getSelectedIndex()==0;
      SubVProvisionDelegator.mnAirPulseWithFeederTGSW=SubAssistantPane
        .ccRefer().cmAirPulseModeNT.getSelectedIndex()==1;
      SubVProvisionDelegator.mnAirPulseForceTGSW=SubAssistantPane
        .ccRefer().cmAirPulseModeNT.getSelectedIndex()==2;
      
      //-- aggregate **filter ** cooling damper
      SubVCombustDelegator.mnCoolingDamperCloseSW=SubAssistantPane
        .ccRefer().cmCoolingDamperNT.getSelectedIndex()==1;
      SubVCombustDelegator.mnCoolingDamperOpenSW=SubAssistantPane
        .ccRefer().cmCoolingDamperNT.getSelectedIndex()==2;
      
      //-- aggregate ** v combust
      SubVCombustDelegator.mnVCombustSourceSW=SubAssistantPane
        .ccRefer().cmVCombustSourceNT.getSelectedIndex()==0;
      SubVCombustDelegator.mnVFuelExchangeSW=SubAssistantPane
        .ccRefer().cmVFuelExchangeNT.getSelectedIndex()==0;
      
      //-- dust
      SubVProvisionDelegator.mnDustSiloAirAutoSW
        = SubAssistantPane.ccRefer().cmDustSiloAirNT.getSelectedIndex()==0;
      SubVProvisionDelegator.mnDustSiloDischargeSW
        = SubAssistantPane.ccRefer().cmDustSiloDischargeSW.isSelected();
      
      //-- asphalt
      
      //-- recycle
      
      //-- additive
      
      //-- misc
      
      //-- feeder
      for(
        int i=MainPlantModel.C_VF_UI_VALID_HEAD;
        i<=MainPlantModel.C_VF_UI_VALID_MAX;
        i++
      ){
        SubFeederDelegator.ccSetVFeederForce(i,
          SubFeederPane.ccRefer().cmLesVFeederBlock.get(i).ccGetIsForced());
        SubFeederDelegator.ccSetVFeederDisable(i,
          SubFeederPane.ccRefer().cmLesVFeederBlock.get(i).ccGetIsDisabled());
      }//..~
      
    }//+++
  };//***
  
  public final Runnable cmSwingClickableRegiseriness = new Runnable() {
    @Override public void run() {
    
      //-- accessibility
      VcSwingCoordinator.ccRegisterAction
        (MainWindow.ccRefer().cmQuitButton, cmQuitting);
      VcSwingCoordinator.ccRegisterAction
        (MainWindow.ccRefer().cmHideButton, cmHiding);
      
      //-- sub
      VcSwingCoordinator.ccRegisterAction
        (MainWindow.ccRefer().cmErrorClearButton, cmErrorCleaning);
      VcSwingCoordinator.ccRegisterAction
        (SubMonitorPane.ccRefer().cmPrintButton, cmPrinting);
      VcSwingCoordinator.ccRegisterAction
        (SubSettingPane.ccRefer().cmModifyButton, cmSettingModifying);

    }//+++
  };//***
  
  //=== local
  
  public final void ccInit(){
    
    //-- sketch ** indicative
    VcLocalCoordinator.ccRegisterMouseTrigger
      (SubIndicativeGroup.ccRefer().cmSystemPopSW, cmPopping);
    
    //-- sketch ** operative
    
    //-- sketch ** floatting
    
    //-- sketch ** floatting ** weigher locker
    for(EcButton it : SubWeigherGroup.ccRefer().cmLesAGLockSW){
      VcLocalCoordinator.ccRegisterMouseTrigger(it, cmAGLockFlipping);
    }//..~
    for(EcButton it : SubWeigherGroup.ccRefer().cmLesFRLockSW){
      VcLocalCoordinator.ccRegisterMouseTrigger(it, cmFRLockFlipping);
    }//..~
    for(EcButton it : SubWeigherGroup.ccRefer().cmLesASLockSW){
      VcLocalCoordinator.ccRegisterMouseTrigger(it, cmASLockFlipping);
    }//..~
    //[todo]::for(EcButton it : SubWeigherGroup.ccRefer().cmDesRC...
    //[todo]::for(EcButton it : SubWeigherGroup.ccRefer().cmDesAD...
    
    //-- sketch ** floatting ** zero adjust
    VcLocalCoordinator.ccRegisterMouseTrigger
      (SubOperativeGroup.ccRefer().cmAGZeroSW,cmAGZeroAdjustFlipping);
    VcLocalCoordinator.ccRegisterMouseTrigger
      (SubOperativeGroup.ccRefer().cmFRZeroSW,cmFRZeroAdjustFlipping);
    VcLocalCoordinator.ccRegisterMouseTrigger
      (SubOperativeGroup.ccRefer().cmASZeroSW,cmASZeroAdjustFlipping);
    //[todo]:: % rc ...
    //[todo]:: % ad ...
    VcLocalCoordinator.ccRegisterMouseTrigger
      (SubOperativeGroup.ccRefer().cmApplyZeroSW,cmZeroAdjustApplying);
    
    //-- sketch ** floatting ** mixer gate control
    VcLocalCoordinator.ccRegisterMouseTrigger
      (SubOperativeGroup.ccRefer().cmMixerGateHoldSW, cmMixerGateHoldClicking);
    VcLocalCoordinator.ccRegisterMouseTrigger
      (SubOperativeGroup.ccRefer().cmMixerGateAutoSW, cmMixerGateAutoClicking);
    VcLocalCoordinator.ccRegisterMouseTrigger
      (SubOperativeGroup.ccRefer().cmMixerGateOpenSW, cmMixerGateOpenClicking);
    
    //-- sketch ** floatting ** v feeder group
    VcLocalCoordinator.ccRegisterMouseTrigger
      (SubVFeederGroup.ccRefer().cmRatioAutoSW, cmVFeederRatioAutoFitting);
    VcLocalCoordinator.ccRegisterMouseTrigger
      (SubVFeederGroup.ccRefer().cmRatioDownSW, cmVFeederRatioDecrementing);
    VcLocalCoordinator.ccRegisterMouseTrigger
      (SubVFeederGroup.ccRefer().cmRatioUpSW, cmVFeederRatioIncrementing);
    
    //-- sketch ** floatting ** v bond group
    VcLocalCoordinator.ccRegisterMouseTrigger
      (SubVBondGroup.ccRefer().cmTargetTemperatureTB
        ,cmVTargetTemperatureAdjusting);
    VcLocalCoordinator.ccRegisterMouseTrigger
      (SubVBondGroup.ccRefer().cmTargetDecrementSW
        ,cmVTargetTemperatureDecrementing);
    VcLocalCoordinator.ccRegisterMouseTrigger
      (SubVBondGroup.ccRefer().cmTargetIncrementSW
        ,cmVTargetTemperatureIncrementing);
    
    //-- 
    VcLocalCoordinator.ccRegisterMouseTrigger(
      SubOperativeGroup.ccRefer().cmWeighStartSW,
      SubWeighControlManager.ccRefer().cmWeighStartClicking
    );
    VcLocalCoordinator.ccRegisterMouseTrigger(
      SubOperativeGroup.ccRefer().cmWeighStopSW,
      SubWeighControlManager.ccRefer().cmWeighCacncelClicking
    );
    VcLocalCoordinator.ccRegisterMouseTrigger(
      SubOperativeGroup.ccRefer().cmWeighCancelSW,
      SubWeighControlManager.ccRefer().cmCurrentlyBookedCleaning
    );
    
    //-- skectch ** key pressing ** test
    VcLocalCoordinator.ccRegisterKeyTrigger
      (KeyEvent.VK_F, new EiTriggerable() {
      @Override public void ccTrigger() {
        VcConst.ccPrintln(
          "this is a public service announcement",
          "this is only a Test 'which ya not suppsed to use"
        );
        /* 7 */ //[givemesomerthing]::..
        // ...
      }//+++
    });
    
    //-- skectch ** key pressing
    VcLocalCoordinator.ccRegisterKeyTrigger
      (KeyEvent.VK_Q, cmQuitting);
    VcLocalCoordinator.ccRegisterKeyTrigger
      (KeyEvent.VK_SPACE, cmInputFocusCleaning);
    //[todo]::%mask esc to clear input focus%
    
    //-- skech ** command ** default
    VcLocalConsole.ccRegisterSettlement(cmInputSettling);
    VcLocalConsole.ccRegisterNumeric(cmNumericInputting);
    
    //-- skech ** command ** command
    VcLocalConsole.ccRegisterCommand
      ("quit", cmQuitting);
    VcLocalConsole.ccRegisterCommand
      ("run", SubWeighControlManager.ccRefer().cmWeighStartClicking);
    VcLocalConsole.ccRegisterCommand
      ("debug", cmDebugging);
    VcLocalConsole.ccRegisterCommand
      ("mrst", cmMotorThmalResetting);
    VcLocalConsole.ccRegisterCommand
      ("dmwsr", cmDummyWeighRecordGenerating);
    VcLocalConsole.ccRegisterCommand
      ("terr", cmErrorBitSetting);
  
  }//++!
  
}//***eof
