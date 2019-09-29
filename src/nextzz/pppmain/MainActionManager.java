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
import nextzz.pppdelegate.SubVProvisionDelegator;
import nextzz.ppplocalui.SubIndicativeGroup;
import nextzz.ppplocalui.SubVFeederGroup;
import nextzz.pppswingui.SubAssistantPane;
import static nextzz.pppmain.MainSketch.C_COLOR_BACKGROUD;
import nextzz.pppmodel.MainSettingManager;
import nextzz.pppmodel.MiSettingItem;
import nextzz.pppswingui.SubFeederPane;
import nextzz.pppswingui.SubSettingPane;

public final class MainActionManager {
  
  private static final MainActionManager SELF = new MainActionManager();
  public static MainActionManager ccRefer(){return SELF;}//+++
  private MainActionManager(){}//..!

  //=== trigger ** so?
  
  public final EiTriggerable cmBackgroundRefreshing = new EiTriggerable() {
    @Override public void ccTrigger(){
      MainSketch.ccGetPApplet().background(C_COLOR_BACKGROUD);
      VcLocalCoordinator.ccUpdatePassive();
    }//+++
  };//***
  
  public final EiTriggerable cmQuitting = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcConst.ccPrintln(".cmQuitting()::call PApplet::exit()");
      MainSketch.ccGetPApplet().exit();
    }//+++
  };//***
  
  public final EiTriggerable cmPopping = new EiTriggerable() {
    @Override public void ccTrigger() {
      MainWindow.ccRefer().cmWindow.setVisible(true);
      MainWindow.ccRefer().cmWindow.toFront();
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
  
  //=== trigger ** swing
   
  public final EiTriggerable cmHiding = new EiTriggerable() {
    @Override public void ccTrigger() {
      MainWindow.ccRefer().cmWindow.setVisible(false);
    }//+++
  };//***
  
  public final EiTriggerable cmSettingModifing = new EiTriggerable() {
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
        lpItem.ccGetName(),
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
      
      //-- tower
      SubVProvisionDelegator.mnTowerBlowerTGSW=
        SubAssistantPane.ccRefer().cmTowerBlowerNT.getSelectedIndex()==0;
      
      //-- feeder
      for(
        int i=SubFeederDelegator.C_VF_INIT_ORDER;
        i<=SubFeederDelegator.C_VF_VALID_MAX;
        i++
      ){
        SubFeederDelegator.ccSetVFeederForce(i,
          SubFeederPane.ccRefer().cmDesVFeederBlock.get(i).ccGetIsForced());
        SubFeederDelegator.ccSetVFeederDisable(i,
          SubFeederPane.ccRefer().cmDesVFeederBlock.get(i).ccGetIsDisabled());
      }//..~
      
      //-- aggregate
      
      //-- filler
      
      //-- dust
      SubVProvisionDelegator.mnDustSiloAirAutoSW
        = SubAssistantPane.ccRefer().cmDustSiloAirNT.getSelectedIndex()==0;
      SubVProvisionDelegator.mnDustSiloDischargeSW
        = SubAssistantPane.ccRefer().cmDustSiloDischargeSW.isSelected();
      
      //-- asphalt
      
      //-- recycle
      
      //-- additive
      
      //-- misc
      
    }//+++
  };//***
  
  
  public final Runnable cmSwingClickableRegisering = new Runnable() {
    @Override public void run() {
    
      VcSwingCoordinator.ccRegisterAction
        (MainWindow.ccRefer().cmQuitButton, cmQuitting);
      VcSwingCoordinator.ccRegisterAction
        (MainWindow.ccRefer().cmHideButton, cmHiding);
      VcSwingCoordinator.ccRegisterAction
        (SubSettingPane.ccRefer().cmModifyButton, cmSettingModifing);

    }//+++
  };//***
  
  //=== local
  
  public final void ccInit(){
    
    //-- sketch ** indicative
    VcLocalCoordinator.ccRegisterMouseTrigger
      (SubIndicativeGroup.ccRefer().cmSystemPopSW, cmPopping);
    
    //-- sketch ** operative
    
    //-- sketch ** floatting
    
    //-- sketch ** floatting ** v feeder group
    VcLocalCoordinator.ccRegisterMouseTrigger
      (SubVFeederGroup.ccRefer().cmRatioAutoSW, cmVFeederRatioAutoFitting);
    VcLocalCoordinator.ccRegisterMouseTrigger
      (SubVFeederGroup.ccRefer().cmRatioDownSW, cmVFeederRatioDecrementing);
    VcLocalCoordinator.ccRegisterMouseTrigger
      (SubVFeederGroup.ccRefer().cmRatioUpSW, cmVFeederRatioIncrementing);
    
    
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
  
  }//..!
  
}//***eof
