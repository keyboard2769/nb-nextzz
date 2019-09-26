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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScGauge;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcTranslator;
import nextzz.pppmodel.MainSpecificator;

public final class SubMonitorPane {
  
  public static final int C_CTSLOT_MAX = 32;
  public static final int C_CTSLOT_MASK = 31;

  private static final SubMonitorPane SELF = new SubMonitorPane();
  public static final SubMonitorPane ccRefer(){return SELF;}//+++
  private SubMonitorPane(){}//++!

  //===
  
  public static final String C_TAB_NAME = VcTranslator.tr("_monitor");
  
  public final JPanel cmPane = ScFactory.ccCreateBorderPanel();
  
  public final List<ScGauge> cmDesCurrentCTSlot
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
  
  public final void ccInit(){
    
    //-- ctslot
    JPanel lpSlotGroupI  = ScFactory.ccCreateGridPanel(16, 1);
    JPanel lpSlotGroupII = ScFactory.ccCreateGridPanel(16, 1);
    for(int i=0;i<16;i++){
      lpSlotGroupI.add(cmDesCurrentCTSlot.get(i));
      lpSlotGroupII.add(cmDesCurrentCTSlot.get(i+16));
    }////~
    JTabbedPane lpLeftWing = new JTabbedPane();
    lpLeftWing.add(VcTranslator.tr("_vact"),lpSlotGroupI);
    if(MainSpecificator.ccRefer().ccNeedsExtendsCurrentSlot())
      {lpLeftWing.add(VcTranslator.tr("_rsct"),lpSlotGroupII);}
    for(ScGauge it : cmDesCurrentCTSlot){
       it.ccSetText(VcTranslator.tr(it.ccGetKey()));
       it.ccSetPercentage(4);
    }//..~
    
    //-- pack
    cmPane.add(lpLeftWing,BorderLayout.LINE_START);
    cmPane.add(new JButton("=Dweigh="),BorderLayout.CENTER);
    
  }//..!
  
 }//***eof
