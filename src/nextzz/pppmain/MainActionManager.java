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

import java.awt.event.KeyEvent;
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplocalui.EiTriggerable;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcLocalConsole;
import kosui.ppputil.VcLocalCoordinator;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcSwingCoordinator;
import nextzz.ppplocalui.SubIndicativeGroup;
import nextzz.ppplocalui.SubOperativeGroup;
import static nextzz.pppmain.MainSketch.C_BACKGROUD;

public final class MainActionManager {
  
  private static final MainActionManager SELF = new MainActionManager();
  public static MainActionManager ccRefer(){return SELF;}//+++
  private MainActionManager(){}//..!

  //=== trigger ** so?
  
  public final EiTriggerable cmBackgroundRefreshing = new EiTriggerable() {
    @Override public void ccTrigger(){
      MainSketch.ccGetPApplet().background(C_BACKGROUD);
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
      System.out.println("cmPopping.ccTrigger()");
      MainWindow.ccRefer().cmWindow.setVisible(true);
      MainWindow.ccRefer().cmWindow.toFront();
    }//+++
  };//***
  
  public final EiTriggerable cmHiding = new EiTriggerable() {
    @Override public void ccTrigger() {
      System.out.println("cmHiding.ccTrigger()");
      MainWindow.ccRefer().cmWindow.setVisible(false);
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
  
  //=== trigger ** what?
  
  //=== swing 
  
  public final Runnable cmSwingClickableRegisering = new Runnable() {
    @Override public void run() {
    
      VcSwingCoordinator.ccRegisterAction
        (MainWindow.ccRefer().lpQuitButton, cmQuitting);
      VcSwingCoordinator.ccRegisterAction
        (MainWindow.ccRefer().lpHideButton, cmHiding);
    
    }//+++
  };
  
  //=== local
  
  public final void ccInit(){
    
    //-- sketch ** operative buttons 
    VcLocalCoordinator.ccRegisterMouseTrigger
      (SubIndicativeGroup.ccRefer().cmSystemPopSW, cmPopping);
    
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
        MainSketch.pbDoesTag=!MainSketch.pbDoesTag;
      }//+++
    });
  
  }//..!
  
  public final void ccLogic(){
    
    
    
    
  }//..!
  
 }//***eof
