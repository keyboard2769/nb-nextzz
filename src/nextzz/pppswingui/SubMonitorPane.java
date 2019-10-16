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
import java.awt.Dimension;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import kosui.pppswingui.ScConst;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScGauge;
import kosui.pppswingui.ScTable;
import kosui.ppputil.VcTranslator;
import nextzz.pppmodel.MainPlantModel;
import nextzz.pppmodel.MainSpecificator;
import nextzz.pppmodel.SubStatisticWeighManager;
import nextzz.pppmodel.SubWeighControlManager;

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
  
  public final JButton cmExportButton
    = new JButton(VcTranslator.tr("_export"));
  
  public final JButton cmPrintButton
    = new JButton(VcTranslator.tr("_print"));
  
  public final JTextField cmFRWeigherStatePL = ScFactory.ccCreateTextLamp("[-:-]");
  public final JTextField cmAGWeigherStatePL = ScFactory.ccCreateTextLamp("[-:-]");
  public final JTextField cmASWeigherStatePL = ScFactory.ccCreateTextLamp("[-:-]");
  public final JTextField cmMixerStatePL = ScFactory.ccCreateTextLamp("[-:-]");
  
  public final ScTable cmDynamicWeighResultTable
    = new ScTable(SubWeighControlManager.ccRefer().cmDynamicResultModel,80,80);
  
  public final ScTable cmStatisticWeighResultTable
    = new ScTable(SubStatisticWeighManager.ccRefer(),200,200);
  
  public final Runnable cmDynamicWeighResultTableRefreshing=new Runnable(){
    @Override public void run() {
      SubMonitorPane.ccRefer().cmStatisticWeighResultTable.ccRefresh();
      ScConst.ccScrollToLast(SubMonitorPane.ccRefer().cmStatisticWeighResultTable);
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
    lpLeftWing.add(VcTranslator.tr("_vact"),lpSlotGroupI);
    if(MainSpecificator.ccRefer().ccNeedsExtendsCurrentSlot())
      {lpLeftWing.add(VcTranslator.tr("_rsct"),lpSlotGroupII);}
    for(ScGauge it : cmLesCurrentCTSlot){
       it.ccSetText(VcTranslator.tr(it.ccGetKey()));
       it.ccSetPercentage(4);//..arbitrary
    }//..~
    
    //-- table config
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
    //-- center pane ** weigh ** bar
    JToolBar lpWeighToolBar = ScFactory.ccCreateStuckedToolBar();
    lpWeighToolBar.add(cmExportButton);
    lpWeighToolBar.add(cmPrintButton);
    lpWeighToolBar.add(new JSeparator(SwingConstants.VERTICAL));
    lpWeighToolBar.add(cmFRWeigherStatePL);
    lpWeighToolBar.add(cmAGWeigherStatePL);
    lpWeighToolBar.add(cmASWeigherStatePL);
    lpWeighToolBar.add(cmMixerStatePL);
    //-- center pane ** weigh ** pack
    JPanel lpWeighPartPane = ScFactory.ccCreateBorderPanel();
    lpWeighPartPane.add(lpWeighToolBar,BorderLayout.PAGE_START);
    lpWeighPartPane.add(cmDynamicWeighResultTable,BorderLayout.CENTER);
    lpWeighPartPane.add(cmStatisticWeighResultTable,BorderLayout.PAGE_END);
    
    //-- center pane ** combust
    JPanel lpCombustPartPane = ScFactory.ccCreateBorderPanel();
    /* 6 */JButton dtfmDummy = new JButton("=Dcombust=");
    dtfmDummy.setPreferredSize(new Dimension(200, 200));
    lpCombustPartPane.add(dtfmDummy,BorderLayout.CENTER);
    
    //-- center pane ** center
    JPanel lpCenterPane = ScFactory.ccCreateBorderPanel();
    lpCenterPane.add(lpWeighPartPane, BorderLayout.CENTER);
    lpCenterPane.add(lpCombustPartPane, BorderLayout.PAGE_END);
    
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
