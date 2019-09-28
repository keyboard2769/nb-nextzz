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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import kosui.pppswingui.ScConst;
import kosui.pppswingui.ScFactory;
import kosui.ppputil.VcTranslator;
import nextzz.pppmodel.MainSpecificator;

public final class SubFeederPane implements SiTabbable{
  
  private static final SubFeederPane SELF = new SubFeederPane();
  public static final SubFeederPane ccRefer(){return SELF;}//+++
  private SubFeederPane(){}//..!
  
  //===
  
  public static final String C_TAB_NAME = VcTranslator.tr("_feeder");
  
  public final JPanel cmPane = ScFactory.ccCreateBorderPanel();
  
  private final HashMap<JButton,String> cmMapOfConfigSW
    = new HashMap<JButton, String>();
  
  public final List<ScFeederBlock> cmDesVFeederBlock
    = Collections.unmodifiableList(Arrays.asList(
      new ScFeederBlock("X"),
      new ScFeederBlock("VF1"),
      new ScFeederBlock("VF2"),
      new ScFeederBlock("VF3"),
      new ScFeederBlock("VF4"),
      new ScFeederBlock("VF5"),
      new ScFeederBlock("VF6"),
      new ScFeederBlock("VF7"),
      new ScFeederBlock("VF8"),
      new ScFeederBlock("VF9"),
      new ScFeederBlock("VF10")
    ));
  
  public final List<ScFeederBlock> cmDesRFeederBlock
    = Collections.unmodifiableList(Arrays.asList(
      new ScFeederBlock("X"),
      new ScFeederBlock("RF1"),
      new ScFeederBlock("RF2"),
      new ScFeederBlock("RF3"),
      new ScFeederBlock("RF4"),
      new ScFeederBlock("RF5"),
      new ScFeederBlock("RF6"),
      new ScFeederBlock("RF7"),
      new ScFeederBlock("RF8"),
      new ScFeederBlock("RF9"),
      new ScFeederBlock("RF10")
    ));
  
  private final ActionListener cmFeederSpeedConfigListener
    = new ActionListener() {
    @Override public void actionPerformed(ActionEvent e) {
      Object lpSource = e.getSource();
      
      //[head]::try to make this stuff work
      
      if(SwingUtilities.isEventDispatchThread()){
        System.out.println("yes");
      }
      
      if(lpSource instanceof JButton){
        System.out.println(".cmFeederSpeedConfigListener::"
          +cmMapOfConfigSW.get((JButton)lpSource));
      }
    }//+++
  };//***
  
  //===
  
  @Override public final void ccInit(){
    
    JPanel lpVFeederPanel = ScFactory.ccCreateGridPanel(10,1);
    JPanel lpRFeederPanel = ScFactory.ccCreateGridPanel(10,1);
    
    for(int i=1;i<=10;i++){
      lpVFeederPanel.add(cmDesVFeederBlock.get(i));
      cmMapOfConfigSW.put(cmDesVFeederBlock.get(i)
        .cmConfigSW, "VF"+Integer.toString(i));
      cmDesVFeederBlock.get(i).cmConfigSW
        .addActionListener(cmFeederSpeedConfigListener);
      if(i>=MainSpecificator.ccRefer().mnVFeederAmount){
        ScConst.ccHideComponent(cmDesVFeederBlock.get(i));
      }//..?
      lpRFeederPanel.add(cmDesRFeederBlock.get(i));
      ScConst.ccHideComponent(cmDesRFeederBlock.get(i));
    }//+++
    
    cmPane.add(lpVFeederPanel,BorderLayout.LINE_START);
    cmPane.add(lpRFeederPanel,BorderLayout.LINE_END);
    
  }//..!

  @Override public final String ccGetTitle() {
    return C_TAB_NAME;
  }//+++

  @Override public final JPanel ccGetPane() {
    return cmPane;
  }//+++
  
}//***eof
