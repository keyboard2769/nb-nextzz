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
import nextzz.pppswingui.SubErrorPane;
import nextzz.pppswingui.SubMonitorPane;

public final class MainWindow {

  private static final MainWindow SELF = new MainWindow();
  public static final MainWindow ccRefer(){return SELF;}//+++
  private MainWindow(){}//++!

  //===
  
  private int cmInitX=100, cmInitY=100;
  private boolean cmDoShowAtFirst=true;
  
  public final ScTitledWindow cmWindow=new ScTitledWindow(null);
  
  public final JButton lpQuitButton = new JButton("_quit");
  
  public final JButton lpHideButton = new JButton("_hide");
  
  public final Runnable cmInitiating = new Runnable() {
    @Override public void run() {
      
      //-- init 
      cmWindow.ccInit(MainSketch.C_WARE_TITLE, ScConst.C_DARK_GREEN);
      SubErrorPane.ccRefer().ccInit();
      SubMonitorPane.ccRefer().ccInit();
      
      //-- content
      final JPanel lpDummyPane=new JPanel();
      lpDummyPane.add(new JButton("=D="));
      
      final JTabbedPane lpCenterPane = new JTabbedPane();
      lpCenterPane.add
        (SubMonitorPane.C_TAB_NAME, SubMonitorPane.ccRefer().cmPane);
      lpCenterPane.add
        (SubErrorPane.C_TAB_NAME, SubErrorPane.ccRefer().cmPane);
      lpCenterPane.add("=D=", lpDummyPane);
      lpCenterPane.setPreferredSize(new Dimension(800, 600));
      
      //-- bar
      final JToolBar lpToolBar = ScFactory.ccCreateStuckedToolBar();
      lpToolBar.add(lpQuitButton);
      lpToolBar.add(lpHideButton);
      
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
