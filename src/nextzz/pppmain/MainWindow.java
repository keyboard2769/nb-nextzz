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
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import kosui.pppswingui.ScConst;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScTitledWindow;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcStampUtility;
import kosui.ppputil.VcTranslator;
import nextzz.pppmodel.MainPlantModel;
import nextzz.pppmodel.SubAnalogScalarManager;
import nextzz.pppswingui.SiTabbable;
import nextzz.pppswingui.SubAssistantPane;
import nextzz.pppswingui.SubErrorPane;
import nextzz.pppswingui.SubFeederPane;
import nextzz.pppswingui.SubMonitorPane;
import nextzz.pppswingui.SubSettingPane;

public final class MainWindow {

  private static final MainWindow SELF = new MainWindow();
  public static final MainWindow ccRefer(){return SELF;}//+++
  private MainWindow(){}//++!
  
  //===
  
  public final Runnable cmUpdating = new Runnable() {
    @Override public void run() {
    
      //-- current
      for(int i=0;i<MainPlantModel.C_CTSLOT_CHANNEL_MAX;i++){
        int lpValue=SubAnalogScalarManager.ccRefer().ccGetCTSlotAMPR(i);
        int lpSpan=SubAnalogScalarManager.ccRefer().ccGetCTSlotSpan(i);
        SubMonitorPane.ccRefer().cmDesCurrentCTSlot.get(i)
          .ccSetFloatValueForOneAfter(
            lpValue<10?0f:
            VcNumericUtility.ccToFloatForOneAfter(lpValue)
          );
        SubMonitorPane.ccRefer().cmDesCurrentCTSlot.get(i)
          .ccSetPercentage(VcNumericUtility.ccProportion(lpValue, lpSpan));
      }//..~
      
    }//+++
  };//***

  //===
  
  private int cmInitX=100, cmInitY=100;
  
  private boolean cmDoShowAtFirst=true;
  
  public final ScTitledWindow cmWindow=new ScTitledWindow(null);
  
  public final JButton cmQuitButton
    = new JButton(VcTranslator.tr("_quit"));
  
  public final JButton cmHideButton
    = new JButton(VcTranslator.tr("_hide"));
  
  public final Runnable cmInitiating = new Runnable() {
    @Override public void run() {
      
      //-- style
      ScConst.ccApplyLookAndFeel(0, false);

      //-- init 
      cmWindow.ccInit(MainSketch.C_WARE_TITLE, ScConst.C_DARK_GREEN);
      
      //-- content
      final JTabbedPane lpCenterPane = new JTabbedPane();
      lpCenterPane.setPreferredSize(new Dimension(800, 600));
      ccAddPaneAsTab(lpCenterPane, SubMonitorPane.ccRefer());
      ccAddPaneAsTab(lpCenterPane, SubAssistantPane.ccRefer());
      ccAddPaneAsTab(lpCenterPane, SubFeederPane.ccRefer());
      ccAddPaneAsTab(lpCenterPane, SubSettingPane.ccRefer());
      ccAddPaneAsTab(lpCenterPane, SubErrorPane.ccRefer());
      
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
  
  private void ccAddPaneAsTab(JTabbedPane pxOwner, SiTabbable pxChild){
    if(pxOwner==null){return;}
    if(pxChild==null){return;}
    if(pxChild.ccGetPane()==null){return;}
    if(!VcConst.ccIsValidString(pxChild.ccGetTitle())){return;}
    pxChild.ccInit();
    pxOwner.add(pxChild.ccGetTitle(), pxChild.ccGetPane());
  }//+++
  
  public static final
  void ccSetupInitInformation(int pxX, int pxY, boolean pxVisible){
    SELF.cmInitX=pxX;
    SELF.cmInitY=pxY;
    SELF.cmDoShowAtFirst=pxVisible;
  }//+++
  
 }//***eof
