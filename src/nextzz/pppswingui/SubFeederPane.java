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
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import kosui.ppplogic.ZcRangedModel;
import kosui.pppswingui.ScConst;
import kosui.pppswingui.ScFactory;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcTranslator;
import nextzz.pppdelegate.SubFeederDelegator;
import nextzz.pppmain.MainActionManager;
import nextzz.pppmodel.MainSpecificator;

public final class SubFeederPane implements SiTabbable{
  
  private static final SubFeederPane SELF = new SubFeederPane();
  public static final SubFeederPane ccRefer(){return SELF;}//+++
  private SubFeederPane(){}//..!
  
  //===
  
  public static final String C_TAB_NAME = VcTranslator.tr("_feeder");
  
  public final JPanel cmPane = ScFactory.ccCreateBorderPanel();
  
  private final HashMap<JButton,JSpinner> cmMapOfConfigSW
    = new HashMap<JButton, JSpinner>();
  
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
      if(lpSource instanceof JButton){
        JButton lpCurrentButton = (JButton)lpSource;  
        JSpinner lpCurrentSpinner = cmMapOfConfigSW.get(lpCurrentButton);
        String lpCurrentValue = Integer
          .toString(VcNumericUtility.ccInteger(lpCurrentSpinner.getValue()));
        String lpNewValueString = ScConst.ccGetStringByInputBox(
          VcTranslator.tr("_feeder_speed"),
          lpCurrentValue
        );
        if(!VcConst.ccIsValidString(lpNewValueString)){return;}
        if(lpNewValueString.equals(ScConst.C_M_CANCEL)){return;}
        int lpNewValue=VcNumericUtility
          .ccParseIntegerString(lpNewValueString);
        lpCurrentSpinner.setValue(
          ZcRangedModel.ccLimitInclude(
            lpNewValue,
            ScFeederBlock.C_SPEED_MIN, ScFeederBlock.C_SPEED_MAX
          )
        );
      }//..?
    }//+++
  };//***
  
  private final ChangeListener cmFeederSpeedChangeListener
    = new ChangeListener() {
    @Override public void stateChanged(ChangeEvent ce) {
      for(
        int i=SubFeederDelegator.C_VF_INIT_ORDER;
        i<=SubFeederDelegator.C_VF_VALID_MAX;
        i++
      ){
        SubFeederDelegator
          .ccSetVFeederSpeed(i, cmDesVFeederBlock.get(i).ccGetValue());
      }//..~
    }//+++
  };//***
  
  //===
  
  @Override public final void ccInit(){
    
    JPanel lpVFeederPanel = ScFactory.ccCreateGridPanel(10,1);
    JPanel lpRFeederPanel = ScFactory.ccCreateGridPanel(10,1);
    for(int i=1;i<=10;i++){
      
      //-- V
      
      //-- V ** register
      lpVFeederPanel.add(cmDesVFeederBlock.get(i));
      cmMapOfConfigSW.put(
        cmDesVFeederBlock.get(i) .cmConfigSW,
        cmDesVFeederBlock.get(i) .cmSpinner
      );
      //-- V ** event
      cmDesVFeederBlock.get(i).cmForceSW
        .addActionListener(MainActionManager.ccRefer().cmNotchListener);
      cmDesVFeederBlock.get(i).cmDisableSW
        .addActionListener(MainActionManager.ccRefer().cmNotchListener);
      cmDesVFeederBlock.get(i).cmConfigSW
        .addActionListener(cmFeederSpeedConfigListener);
      cmDesVFeederBlock.get(i).cmSpinner
        .addChangeListener(cmFeederSpeedChangeListener);
      
      //-- V ** hide
      if(i>MainSpecificator.ccRefer().mnVFeederAmount){
        ScConst.ccHideComponent(cmDesVFeederBlock.get(i));
      }//..?
      
      //-- R 
      //-- R ** register
      lpRFeederPanel.add(cmDesRFeederBlock.get(i));
      //-- R ** event
      //-- R ** hide
      ScConst.ccHideComponent(cmDesRFeederBlock.get(i));
      
    }//..~
    
    //-- pack
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
