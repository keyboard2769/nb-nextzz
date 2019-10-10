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
import javax.swing.SwingUtilities;
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplocalui.EiTriggerable;
import kosui.pppswingui.ScConst;
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
import nextzz.pppmodel.MainPlantModel;
import nextzz.pppswingui.SubAssistantPane;
import nextzz.pppsetting.MainSettingManager;
import nextzz.pppsetting.MiSettingItem;
import nextzz.pppmodel.SubDegreeControlManager;
import nextzz.pppswingui.SubFeederPane;
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
      VcConst.ccPrintln(".cmQuitting()::call PApplet::exit()");
      MainSketch.ccGetPApplet().exit();
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
 
  public final EiTriggerable cmInputFocusShifting = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcLocalCoordinator.ccGetInstance().ccToNextInputIndex();
    }//+++
  };//***
  
  public final EiTriggerable cmInputFocusCleaning = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcLocalCoordinator.ccGetInstance().ccClearCurrentInputFocus();
    }//+++
  };//***
  
  public final EiTriggerable cmInputSettling = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcLocalCoordinator.ccInvokeLater(cmBackgroundRefreshing);
      if(VcLocalCoordinator.ccHasInputtableFocused()){
        VcLocalCoordinator.ccGetInstance().ccToNextInputIndex();
      }//..?
    }//+++
  };//***
  
  public final EiTriggerable cmInputtableClicking = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcLocalCoordinator.ccGetInstance().ccSetCurrentInputFocus();
    }//+++
  };//***
  
  public final EiTriggerable cmNumericInputting = new EiTriggerable() {
    @Override public void ccTrigger(){
      EcElement lpSource
       = VcLocalCoordinator.ccGetInstance().ccGetCurrentFocusedBox();
      /* 6 *///[later]::
      if(lpSource instanceof EcValueBox){
        ((EcValueBox)lpSource).ccSetValue(
          VcNumericUtility.ccParseIntegerString
            (VcLocalConsole.ccGetLastAccepted())
        );
      }//..?
    }//+++
  };//***
  
  //=== trigger ** operative
  
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
  
  public final EiTriggerable cmVFeederRatioAutoFitting
    = new EiTriggerable() {
    @Override public void ccTrigger() {
      System.err.println(".cmVFeederRatioAutoFitting::not_yet");
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
          VcLocalCoordinator.ccInvokeLater(SubDegreeControlManager
            .ccRefer().cmControllerRetargetting);
        }//+++
      });//***
    }//+++
  };//***
  
  public final EiTriggerable cmVTargetTemperatureDecrementing
    = new EiTriggerable() {
    @Override public void ccTrigger() {
      SubDegreeControlManager.ccRefer().vmVTargetCELC
        -= SubDegreeControlManager.ccRefer().vmVTargetAdjustWidth;
      SubDegreeControlManager.ccRefer().vmVTargetCELC &= 0xFF;
      SubDegreeControlManager.ccRefer().cmControllerRetargetting.ccTrigger();
    }//+++
  };//***
  
  public final EiTriggerable cmVTargetTemperatureIncrementing
    = new EiTriggerable() {
    @Override public void ccTrigger() {
      SubDegreeControlManager.ccRefer().vmVTargetCELC
        += SubDegreeControlManager.ccRefer().vmVTargetAdjustWidth;
      SubDegreeControlManager.ccRefer().vmVTargetCELC &= 0xFF;
      SubDegreeControlManager.ccRefer().cmControllerRetargetting.ccTrigger();
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
  
  public final EiTriggerable ccSettingModifying = new EiTriggerable() {
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
        lpItem.ccGetLimitationInfo(),
        lpItem.ccGetValue()
      );
      if(!VcConst.ccIsValidString(lpInput)){return;}
      if(lpInput.equals(ScConst.C_M_CANCEL)){return;}
      lpItem.ccSetValue(lpInput);
      SubSettingPane.ccRefer().cmTable.ccRefresh();
    }//+++
  };//***
  
  //=== trigger ** what?
  
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
        int i=MainPlantModel.C_VF_INIT_ORDER;
        i<=MainPlantModel.C_VF_VALID_MAX;
        i++
      ){
        SubFeederDelegator.ccSetVFeederForce(i,
          SubFeederPane.ccRefer().cmDesVFeederBlock.get(i).ccGetIsForced());
        SubFeederDelegator.ccSetVFeederDisable(i,
          SubFeederPane.ccRefer().cmDesVFeederBlock.get(i).ccGetIsDisabled());
      }//..~
      
    }//+++
  };//***
  
  public final Runnable cmSwingClickableRegiseriness = new Runnable() {
    @Override public void run() {
    
      VcSwingCoordinator.ccRegisterAction
        (MainWindow.ccRefer().cmQuitButton, cmQuitting);
      VcSwingCoordinator.ccRegisterAction
        (MainWindow.ccRefer().cmHideButton, cmHiding);
      VcSwingCoordinator.ccRegisterAction
        (SubSettingPane.ccRefer().cmModifyButton, ccSettingModifying);

    }//+++
  };//***
  
  //=== local
  
  public final void ccInit(){
    
    //-- sketch ** indicative
    VcLocalCoordinator.ccRegisterMouseTrigger
      (SubIndicativeGroup.ccRefer().cmSystemPopSW, cmPopping);
    
    //-- sketch ** operative
    
    //-- sketch ** floatting
    
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
    
    //-- skectch ** key pressing
    VcLocalCoordinator.ccRegisterKeyTrigger
      (KeyEvent.VK_Q, cmQuitting);
    VcLocalCoordinator.ccRegisterKeyTrigger
      (KeyEvent.VK_TAB, cmInputFocusShifting);
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
      ("debug", new EiTriggerable() {
      @Override public void ccTrigger(){
        MainSketch.pbDebugMode=!MainSketch.pbDebugMode;
        VcLocalTagger.ccGetInstance().ccSetIsVisible(MainSketch.pbDebugMode);
        VcConst.ccSetDoseLog(MainSketch.pbDebugMode);
      }//+++
    });
  
  }//++!
  
}//***eof
