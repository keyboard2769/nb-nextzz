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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScList;
import kosui.pppswingui.ScStoker;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcTranslator;
import nextzz.pppmain.MainSketch;
import nextzz.pppmodel.SubErrorListModel;

public final class SubErrorPane implements SiTabbable{

  private static final SubErrorPane SELF = new SubErrorPane();
  public static final SubErrorPane ccRefer(){return SELF;}//+++
  private SubErrorPane(){}//++!

  //===
  
  public static final String C_TAB_NAME = VcTranslator.tr("_error");
  
  public final JPanel cmPane = ScFactory.ccCreateBorderPanel();
  
  public final ScStoker cmLogger
    = new ScStoker("-> "+VcConst.C_V_NEWLINE);
  
  public final JTextArea cmDescriptor = new JTextArea("...", 3, 80);
  
  public final ScList cmErrorList = new ScList(
    SubErrorListModel.ccRefer(),
    MainSketch.ccGetPrefferedW()/4,
    MainSketch.ccGetPrefferedW()/4
  );
  
  //===
  
  public final Runnable cmLoggerRefreshingness = new Runnable() {
    @Override public void run() {
      cmLogger.ccRefresh();
    }//+++
  };//***
  
  //===
  
  @Override public final void ccInit(){
    
    ScFactory.ccSetupInfoArea(cmDescriptor);
    
    cmPane.add(cmErrorList,BorderLayout.LINE_START);
    cmPane.add(cmLogger,BorderLayout.CENTER);
    cmPane.add(cmDescriptor,BorderLayout.PAGE_END);
    
  }//..!
  
  //===
  
  @Override public final String ccGetTitle() {
    return C_TAB_NAME;
  }//+++

  @Override public final JPanel ccGetPane() {
    return cmPane;
  }//+++
  
  //===
  
  public static final void ccWriteln(String pxLine){
    SELF.cmLogger.ccWriteln(pxLine);
    SwingUtilities.invokeLater(SELF.cmLoggerRefreshingness);
  }//+++
  
  public static final void ccWriteln(String pxTag, Object pxValue){
    SELF.cmLogger.ccWriteln(pxTag, pxValue);
    SwingUtilities.invokeLater(SELF.cmLoggerRefreshingness);
  }//+++
  
}//***eof
