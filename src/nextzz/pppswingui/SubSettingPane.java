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
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import kosui.pppswingui.ScFactory;
import kosui.ppputil.VcTranslator;

public final class SubSettingPane implements SiTabbable{

  private static final SubSettingPane SELF = new SubSettingPane();
  public static final SubSettingPane ccRefer(){return SELF;}//+++
  private SubSettingPane(){}//++!

  //===
  
  public static final String C_TAB_NAME = VcTranslator.tr("_setting");
  
  public final JPanel cmPane = ScFactory.ccCreateBorderPanel();
  
  //===
  
  @Override public final void ccInit(){
    
    //-- tool sw
    JButton lpImportButton = ScFactory
      .ccCreateCommandButton(VcTranslator.tr("_import"));
    JButton lpExportButton = ScFactory
      .ccCreateCommandButton(VcTranslator.tr("_export"));
    JButton lpModifyButton = ScFactory
      .ccCreateCommandButton(VcTranslator.tr("_mod"));
    
    //-- tool bar
    JToolBar lpBar = ScFactory.ccCreateStuckedToolBar();
    lpBar.add(lpImportButton);
    lpBar.add(lpExportButton);
    lpBar.add(new JSeparator(SwingConstants.VERTICAL));
    lpBar.add(lpModifyButton);
    
    //-- list
    
    //[head]::.. well, now , what??
    
    //-- pack
    cmPane.add(lpBar,BorderLayout.PAGE_START);
    cmPane.add(new JButton("=d-list="),BorderLayout.LINE_START);
    cmPane.add(new JButton("=d-table="),BorderLayout.CENTER);
    cmPane.add(new JButton("=d-area="),BorderLayout.PAGE_END);
    
  }//..!
  
  @Override public final String ccGetTitle() {
    return C_TAB_NAME;
  }//+++

  @Override public final JPanel ccGetPane() {
    return cmPane;
  }//+++
  
  //=== 
  
 }//***eof
