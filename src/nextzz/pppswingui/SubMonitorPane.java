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
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import kosui.ppplocalui.EiTriggerable;
import kosui.pppswingui.ScConst;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScGauge;
import kosui.pppswingui.ScTable;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcSwingCoordinator;
import kosui.ppputil.VcTranslator;
import nextzz.pppmodel.MainPlantModel;
import nextzz.pppmodel.MainSpecificator;
import nextzz.pppmodel.SubVCombustStaticManager;
import nextzz.pppmodel.SubWeighStatisticManager;
import nextzz.pppmodel.SubWeighDynamicManager;

public final class SubMonitorPane implements SiTabbable{
  
  private static final SubMonitorPane SELF = new SubMonitorPane();
  public static final SubMonitorPane ccRefer(){return SELF;}//+++
  private SubMonitorPane(){}//++!

  //===
  
  public static final String C_TAB_NAME = VcTranslator.tr("_monitor");
  
  public final JPanel cmPane = ScFactory.ccCreateBorderPanel();
  
  public final List<ScGauge> cmLesCurrentCTSlot
    = Collections.unmodifiableList(Arrays.asList(
      new ScGauge("_ct00", "A"),new ScGauge("_ct01", "A"),
      new ScGauge("_ct02", "A"),new ScGauge("_ct03", "A"),
      //--
      new ScGauge("_ct04", "A"),new ScGauge("_ct05", "A"),
      new ScGauge("_ct06", "A"),new ScGauge("_ct07", "A"),
      //--
      new ScGauge("_ct08", "A"),new ScGauge("_ct09", "A"),
      new ScGauge("_ct10", "A"),new ScGauge("_ct11", "A"),
      //--
      new ScGauge("_ct12", "A"),new ScGauge("_ct13", "A"),
      new ScGauge("_ct14", "A"),new ScGauge("_ct15", "A"),
      //-- **
      new ScGauge("_ct16", "A"),new ScGauge("_ct17", "A"),
      new ScGauge("_ct18", "A"),new ScGauge("_ct19", "A"),
      //-- 
      new ScGauge("_ct20", "A"),new ScGauge("_ct21", "A"),
      new ScGauge("_ct22", "A"),new ScGauge("_ct23", "A"),
      //--
      new ScGauge("_ct24", "A"),new ScGauge("_ct25", "A"),
      new ScGauge("_ct26", "A"),new ScGauge("_ct27", "A"),
      //--
      new ScGauge("_ct28", "A"),new ScGauge("_ct29", "A"),
      new ScGauge("_ct30", "A"),new ScGauge("_ct31", "A")
    ));
  
  public final JButton cmPrintButton
    = new JButton(VcTranslator.tr("_print"));
  
  public final JTextField cmFRWeigherStatePL
    = ScFactory.ccCreateTextLamp("[-:-]");
  public final JTextField cmAGWeigherStatePL
    = ScFactory.ccCreateTextLamp("[-:-]");
  public final JTextField cmASWeigherStatePL
    = ScFactory.ccCreateTextLamp("[-:-]");
  public final JTextField cmMixerStatePL
    = ScFactory.ccCreateTextLamp("[-:-]");
  
  public final ScTable cmDynamicWeighResultTable
    = new ScTable(SubWeighDynamicManager.ccRefer(),80,80);
  
  public final ScTable cmStatisticWeighResultTable
    = new ScTable(SubWeighStatisticManager.ccRefer(),200,200);
  
  public final ScTable cmVCombustResultTable
    = new ScTable(SubVCombustStaticManager.ccRefer(), 180, 180);
  
  public final Runnable cmWeighResultTableRefreshingness
    =new Runnable(){
    @Override public void run() {
      SubMonitorPane.ccRefer().cmStatisticWeighResultTable.ccRefresh();
      ScConst.ccScrollToLast(SubMonitorPane.ccRefer()
        .cmStatisticWeighResultTable);
    }//+++
  };//***
  
  public final Runnable cmVCombustResultTableRefreshingness
    = new Runnable() {
    @Override public void run() {
      SubMonitorPane.ccRefer().cmVCombustResultTable.ccRefresh();
      ScConst.ccScrollToLast(SubMonitorPane.ccRefer().cmVCombustResultTable);
    }//+++
  };//***
  
  //=== *popup ** v combust result
  
  private final JTabbedPane cmCombustResultPane = new JTabbedPane();;
  
  private  final JPopupMenu cmCombustResultMenu = new JPopupMenu();
  
  private final MouseAdapter cmCombustResultClickListener = new MouseAdapter(){
    @Override public void mouseReleased(MouseEvent me) {
      if(me.isPopupTrigger() || (me.getButton()!=MouseEvent.BUTTON1)){
        cmCombustResultMenu.show(me.getComponent(), me.getX(), me.getY());
      }//..?
    }//+++
  };//***
  
  public final EiTriggerable cmCombustResultExporting = new EiTriggerable(){
    @Override public void ccTrigger() {
      int lpSelectedIndex = cmCombustResultPane.getSelectedIndex();
      /* 4 */VcConst.ccLogln("cmCombustResultExporting::tab:", lpSelectedIndex);
      switch (lpSelectedIndex) {
        case 0:
          SubVCombustStaticManager.ccRefer().ccExportAndClear();
        break;
        case 1:
          System.err.println("cmCombustResultExporting::RC_not_yet");
        break;
        default:
          System.err.println("cmCombustResultExporting::unreachable_error");
        break;
      }//...?
    }//+++
  };//***
  
  //=== *popup ** w statistic result
  
  private final JPopupMenu cmWeighResultMenu = new JPopupMenu();
  
  private final MouseAdapter cmWeighResultClickListener = new MouseAdapter() {
    @Override public void mouseReleased(MouseEvent me){
      if(me.isPopupTrigger() || (me.getButton()!=MouseEvent.BUTTON1)){
        cmWeighResultMenu.show(me.getComponent(), me.getX(), me.getY());
      }//..?
    }//+++
  };//***
  
  public final EiTriggerable cmWeighResultExporting = new EiTriggerable() {
    @Override public void ccTrigger(){
      SubWeighStatisticManager.ccRefer().ccExportAndClear();
    }//+++
  };//***
  
  //===
  
  @Override public final void ccInit(){
    
    //-- ctslot
    JPanel lpSlotGroupI  = ScFactory
      .ccCreateGridPanel(MainPlantModel.C_CTSLOT_CHANNEL_SINGLE, 1);
    JPanel lpSlotGroupII = ScFactory
      .ccCreateGridPanel(MainPlantModel.C_CTSLOT_CHANNEL_SINGLE, 1);
    for(int i=0;i<MainPlantModel.C_CTSLOT_CHANNEL_SINGLE;i++){
      lpSlotGroupI.add(cmLesCurrentCTSlot
        .get(i));
      lpSlotGroupII.add(cmLesCurrentCTSlot
        .get(i+MainPlantModel.C_CTSLOT_CHANNEL_SINGLE));
    }////~
    JTabbedPane lpLeftWing = new JTabbedPane();
    lpLeftWing.add(VcTranslator.tr("_va_ct"),lpSlotGroupI);
    if(MainSpecificator.ccRefer().ccNeedsExtendsCurrentSlot())
      {lpLeftWing.add(VcTranslator.tr("_rs_ct"),lpSlotGroupII);}
    for(ScGauge it : cmLesCurrentCTSlot){
      it.ccSetText(VcTranslator.tr(it.ccGetKey()));
      it.ccSetPercentage(4);//..arbitrary
      it.ccSetAlertColor(ScConst.C_DIM_RED);
    }//..~
    
    //-- table config
    cmDynamicWeighResultTable.ccSetEnabled(false);
    cmDynamicWeighResultTable
      .ccSetColor(ScConst.C_LIT_GREEN, Color.DARK_GRAY, Color.GRAY);
    //-- table config ** hide ** dynamic
    //[todo]:: clean this!!
    /* 6 */cmDynamicWeighResultTable.ccHideColumn(0);//..how do we make it specific?
    /* 6 */cmDynamicWeighResultTable.ccHideColumn(1);//..how do we make it specific?
    /* 6 */cmDynamicWeighResultTable.ccHideColumn(2);//..how do we make it specific?
    //--
    /* 6 */cmDynamicWeighResultTable.ccHideColumn(5);//..how do we make it specific?
    //--
    /* 6 */cmDynamicWeighResultTable.ccHideColumn(6);//..how do we make it specific?
    //--
    /* 6 */cmDynamicWeighResultTable.ccHideColumn(13);//..how do we make it specific?
    /* 6 */cmDynamicWeighResultTable.ccHideColumn(15);//..how do we make it specific?
    //--
    /* 6 */cmDynamicWeighResultTable.ccHideColumn(16);//..how do we make it specific?
    /* 6 */cmDynamicWeighResultTable.ccHideColumn(17);//..how do we make it specific?
    /* 6 */cmDynamicWeighResultTable.ccHideColumn(18);//..how do we make it specific?
    //-- table config ** hide ** statistic
    //[todo]:: clean this!!
    cmStatisticWeighResultTable.ccSetColumnWidth(0, 147);//..how do we make it constant?
    cmStatisticWeighResultTable.ccSetColumnWidth(1, 63);//..how do we make it constant?
    cmStatisticWeighResultTable.ccSetColumnWidth(2, 99);//..how do we make it constant?
    cmStatisticWeighResultTable.ccSetColumnWidth(3, 99);//..how do we make it constant?
    cmStatisticWeighResultTable.ccHideColumn(4);//..how do we make it specific?
    cmStatisticWeighResultTable.ccHideColumn(5);//..how do we make it specific?
    cmStatisticWeighResultTable.ccHideColumn(6);//..how do we make it specific?
    /* 6 */cmStatisticWeighResultTable.ccHideColumn(9);//..how do we make it specific?
    /* 6 */cmStatisticWeighResultTable.ccHideColumn(10);//..how do we make it specific?
    /* 6 */cmStatisticWeighResultTable.ccHideColumn(17);//..how do we make it specific?
    /* 6 */cmStatisticWeighResultTable.ccHideColumn(19);//..how do we make it specific?
    /* 6 */cmStatisticWeighResultTable.ccHideColumn(20);//..how do we make it specific?
    /* 6 */cmStatisticWeighResultTable.ccHideColumn(21);//..how do we make it specific?
    /* 6 */cmStatisticWeighResultTable.ccHideColumn(22);//..how do we make it specific?
    
    //-- center pane 
    
    //-- center pane ** weigh
    
    //-- center pane ** weigh ** lamp
    cmFRWeigherStatePL.setDisabledTextColor(ScConst.C_WHITE);
    cmAGWeigherStatePL.setDisabledTextColor(ScConst.C_WHITE);
    cmASWeigherStatePL.setDisabledTextColor(ScConst.C_WHITE);
    cmMixerStatePL.setDisabledTextColor(ScConst.C_WHITE);
    
    //-- center pane ** weigh ** bar
    JToolBar lpWeighToolBar = ScFactory.ccCreateStuckedToolBar();
    lpWeighToolBar.add(cmPrintButton);
    lpWeighToolBar.add(new JSeparator(SwingConstants.VERTICAL));
    lpWeighToolBar.add(cmFRWeigherStatePL);
    lpWeighToolBar.add(cmAGWeigherStatePL);
    lpWeighToolBar.add(cmASWeigherStatePL);
    lpWeighToolBar.add(cmMixerStatePL);
    
    //-- center pane ** weigh result ** popup
    JMenuItem lpWWRExportMI=new JMenuItem(VcTranslator.tr("_export_n_clear"));
    cmWeighResultMenu.add(lpWWRExportMI);
    VcSwingCoordinator
      .ccRegisterAction(lpWWRExportMI, cmWeighResultExporting);
    cmStatisticWeighResultTable.ccAddMouseListener(cmWeighResultClickListener);
    
    //-- center pane ** weigh ** pack
    JPanel lpWeighPartPane = ScFactory.ccCreateBorderPanel();
    lpWeighPartPane.add(lpWeighToolBar,BorderLayout.PAGE_START);
    lpWeighPartPane.add(cmDynamicWeighResultTable,BorderLayout.CENTER);
    lpWeighPartPane.add(cmStatisticWeighResultTable,BorderLayout.PAGE_END);
    
    //-- center pane ** combust ** popup
    JMenuItem lpCCRExportMI=new JMenuItem(VcTranslator.tr("_export_n_clear"));
    cmCombustResultMenu.add(lpCCRExportMI);
    VcSwingCoordinator
      .ccRegisterAction(lpCCRExportMI, cmCombustResultExporting);
    cmCombustResultPane.addMouseListener(cmCombustResultClickListener);
    
    //-- center pane ** combust
    cmCombustResultPane.add(cmVCombustResultTable,
      VcTranslator.tr("_v_combust"));
    cmCombustResultPane.add(new JButton("=R.C="),
      VcTranslator.tr("_r_combust"));
    
    //-- center pane ** center
    JPanel lpCenterPane = ScFactory.ccCreateBorderPanel();
    lpCenterPane.add(lpWeighPartPane, BorderLayout.CENTER);
    lpCenterPane.add(cmCombustResultPane, BorderLayout.PAGE_END);
    
    //-- pack
    cmPane.add(lpLeftWing,BorderLayout.LINE_START);
    cmPane.add(lpCenterPane,BorderLayout.CENTER);
    
  }//++!
  
  @Override public final String ccGetTitle() {
    return C_TAB_NAME;
  }//++>
  
  @Override public final JPanel ccGetPane() {
    return cmPane;
  }//++>
  
 }//***eof
