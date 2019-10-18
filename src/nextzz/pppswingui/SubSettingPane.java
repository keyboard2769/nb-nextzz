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

package nextzz.pppswingui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScList;
import kosui.pppswingui.ScTable;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcStringUtility;
import kosui.ppputil.VcTranslator;
import nextzz.pppmain.MainActionManager;
import nextzz.pppsetting.MainSettingManager;
import nextzz.pppsetting.McAbstractSettingPartition;
import nextzz.pppsetting.MiSettingItem;
import nextzz.pppsetting.SubCombustSetting;

public final class SubSettingPane implements SiTabbable{
  
  public static final int C_DEFAULT_VALUE_COLUMN_W = 120;//..pix
  
  //===
  
  private static final SubSettingPane SELF = new SubSettingPane();
  public static final SubSettingPane ccRefer(){return SELF;}//+++
  private SubSettingPane(){}//++!
  
  //===
  
  public static final String C_TAB_NAME = VcTranslator.tr("_setting");
  
  public final JPanel cmPane = ScFactory.ccCreateBorderPanel();
  
  public final JButton cmImportButton = ScFactory
    .ccCreateCommandButton(VcTranslator.tr("_import"));
  
  public final JButton cmExportButton = ScFactory
    .ccCreateCommandButton(VcTranslator.tr("_export"));
  
  public final JButton cmModifyButton = ScFactory
    .ccCreateCommandButton(VcTranslator.tr("_mod"));
  
  public final ScList cmList
    = new ScList(MainSettingManager.ccRefer(), 160, 160);
  
  public final ScTable cmTable
    = new ScTable(SubCombustSetting.ccRefer(), -1, -1);//..needa fit manually
  
  public final JTextArea cmDescriptor = new JTextArea("...", 3, 80);
  
  //===
  
  private final MouseAdapter cmListPressListener = new MouseAdapter() {
    @Override public void mousePressed(MouseEvent me) {
      int lpIndex=cmList.ccGetCurrentIndex();
      if(lpIndex<0){return;}
      McAbstractSettingPartition lpModel
        = MainSettingManager.ccRefer() .ccGetSelectedModel(lpIndex);
      if(lpModel==null){return;}
      cmTable.ccSetModel(lpModel);
      cmTable.ccSetColumnWidth(0, C_DEFAULT_VALUE_COLUMN_W);
      cmTable.ccRefresh();
    }//+++
  };//***
  
  private final MouseAdapter cmTablePressListener = new MouseAdapter() {
    @Override public void mouseReleased(MouseEvent me) {
      int lpListIndex = cmList.ccGetCurrentIndex();
      int lpTableIndex = cmTable.ccGetSelectedRowIndex();
      if((lpListIndex<0)
       ||(lpTableIndex<0)
      ){return;}//..?
      /* 4 */VcConst.ccLogln(
        VcStringUtility.ccPackupPairedTag("l", lpListIndex),
        VcStringUtility.ccPackupPairedTag("t", lpTableIndex)
      );
      MiSettingItem lpItem = MainSettingManager.ccRefer()
        .ccGetSelectedItem(lpListIndex, lpTableIndex);
      if(lpItem==null){return;}//..?
      cmDescriptor.setText(lpItem.ccGetDescription());
      if (me.getClickCount() == 2 && !me.isConsumed()) {
        me.consume();
        MainActionManager.ccRefer().cmSettingModifying.ccTrigger();
      }//..?
    }//+++
  };//***
  
  //===
  
  @Override public final void ccInit(){
    
    //-- tool bar
    JToolBar lpBar = ScFactory.ccCreateStuckedToolBar();
    lpBar.add(cmImportButton);
    lpBar.add(cmExportButton);
    lpBar.add(new JSeparator(SwingConstants.VERTICAL));
    lpBar.add(cmModifyButton);
    
    //-- list n table
    cmList.ccSetSelectedIndex(0);
    cmList.ccAddMouseListener(cmListPressListener);
    cmTable.ccSetColumnWidth(0, C_DEFAULT_VALUE_COLUMN_W);
    cmTable.ccAddMouseListener(cmTablePressListener);
    
    //-- area
    ScFactory.ccSetupInfoArea(cmDescriptor);
    
    //-- pack
    cmPane.add(lpBar,BorderLayout.PAGE_START);
    cmPane.add(cmList,BorderLayout.LINE_START);
    cmPane.add(cmTable,BorderLayout.CENTER);
    cmPane.add(cmDescriptor,BorderLayout.PAGE_END);
    
  }//..!
  
  @Override public final String ccGetTitle() {
    return C_TAB_NAME;
  }//+++

  @Override public final JPanel ccGetPane() {
    return cmPane;
  }//+++
  
  //===
  
 }//***eof
