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

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import kosui.pppswingui.ScConst;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScTitledWindow;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcStampUtility;
import nextzz.pppmodel.SubAnalogScalarManager;
import nextzz.pppswingui.SubAssistantPane;
import nextzz.pppswingui.SubErrorPane;
import nextzz.pppswingui.SubMonitorPane;

public final class MainWindow {

  private static final MainWindow SELF = new MainWindow();
  public static final MainWindow ccRefer(){return SELF;}//+++
  private MainWindow(){}//++!
  
  //===
  
  public final Runnable cmUpdating = new Runnable() {
    @Override public void run() {
    
      //-- current
      for(int i=0;i<SubMonitorPane.C_CTSLOT_MAX;i++){
        SubMonitorPane.ccRefer().cmDesCurrentCTSlot.get(i)
          .ccSetValue(SubAnalogScalarManager.ccRefer().ccGetCurrentA(i));
      }//..~
      
    }//+++
  };//***

  //===
  
  private int cmInitX=100, cmInitY=100;
  private boolean cmDoShowAtFirst=true;
  
  public final ScTitledWindow cmWindow=new ScTitledWindow(null);
  
  public final JButton cmQuitButton = new JButton("_quit");
  
  public final JButton cmHideButton = new JButton("_hide");
  
  public final Runnable cmInitiating = new Runnable() {
    @Override public void run() {
      
      //-- style
      ScConst.ccApplyLookAndFeel(0, true);

      //-- init 
      cmWindow.ccInit(MainSketch.C_WARE_TITLE, ScConst.C_DARK_GREEN);
      SubMonitorPane.ccRefer().ccInit();
      SubAssistantPane.ccRefer().ccInit();
      SubErrorPane.ccRefer().ccInit();
      
      //-- content
      final JTabbedPane lpCenterPane = new JTabbedPane();
      lpCenterPane.setPreferredSize(new Dimension(800, 600));
      lpCenterPane.add
        (SubMonitorPane.C_TAB_NAME, SubMonitorPane.ccRefer().cmPane);
      lpCenterPane.add
        (SubAssistantPane.C_TAB_NAME, SubAssistantPane.ccRefer().cmPane);
      lpCenterPane.add
        (SubErrorPane.C_TAB_NAME, SubErrorPane.ccRefer().cmPane);
      
      //-- bar
      final JToolBar lpToolBar = ScFactory.ccCreateStuckedToolBar();
      lpToolBar.add(cmQuitButton);
      lpToolBar.add(cmHideButton);
      
      //-- pack
      cmWindow.ccAddCenter(lpCenterPane);
      cmWindow.ccAddPageEnd(lpToolBar);
      cmWindow.ccFinish(cmDoShowAtFirst, cmInitX, cmInitY);
      
      //-- post
      SubErrorPane.ccRefer().cmErrorList.ccRefresh();
      SubErrorPane.ccRefer().cmLogger
        .ccWriteln("hellow, have a nice day!");
      SubErrorPane.ccRefer().cmLogger
        .ccWriteln("it is",VcStampUtility.ccDataLogTypeI());
      
    }//+++
  };//***
  
  public static final
  void ccSetupInitInformation(int pxX, int pxY, boolean pxVisible){
    SELF.cmInitX=pxX;
    SELF.cmInitY=pxY;
    SELF.cmDoShowAtFirst=pxVisible;
  }//+++
  
 }//***eof
