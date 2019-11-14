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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
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
import nextzz.pppmodel.MainPlantModel;
import nextzz.pppmodel.MainSpecificator;
import nextzz.pppmodel.SubAnalogScalarManager;

public final class SubFeederPane implements SiTabbable{
  
  private static final SubFeederPane SELF = new SubFeederPane();
  public static final SubFeederPane ccRefer(){return SELF;}//+++
  private SubFeederPane(){}//..!
  
  //===
  
  public static final String C_TAB_NAME = VcTranslator.tr("_feeder");
  
  public final JComboBox<String> cmVFeederIxVibratorNT
    = ScFactory.ccCreateNotch(
      VcTranslator.tr("_vf1vib:a"),
      VcTranslator.tr("_vf1vib:d"),
      VcTranslator.tr("_vf1vib:m")
    );
  
  public final JComboBox<String> cmVFeederIIxVibratorNT
    = ScFactory.ccCreateNotch(
      VcTranslator.tr("_vf2vib:a"),
      VcTranslator.tr("_vf2vib:d"),
      VcTranslator.tr("_vf2vib:m")
    );
  
  //[todo]::cmRFeederIxVibratorNT
  
  //[todo]::cmRFeederIIxVibratorNT
  
  public final JPanel cmPane = ScFactory.ccCreateBorderPanel();
  
  private final HashMap<JButton,JSpinner> cmMapOfConfigSW
    = new HashMap<JButton, JSpinner>();
  
  public final List<ScFeederBlock> cmLesVFeederBlock
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
  
  public final List<ScFeederBlock> cmLesRFeederBlock
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
          lpCurrentValue,
          cmPane
        );
        if(!VcConst.ccIsValidString(lpNewValueString)){return;}
        if(lpNewValueString.equals(ScConst.C_M_CANCEL)){return;}
        int lpNewValue=VcNumericUtility
          .ccParseIntegerString(lpNewValueString);
        lpCurrentSpinner.setValue(
          ZcRangedModel.ccLimitInclude(
            lpNewValue,
            MainPlantModel.C_FEEDER_RPM_MIN,MainPlantModel.C_FEEDER_RPM_MAX
          )
        );
      }//..?
    }//+++
  };//***
  
  private final ChangeListener cmFeederSpeedChangeListener
    = new ChangeListener() {
    @Override public void stateChanged(ChangeEvent ce) {
      /* 4 */VcConst.ccLogln(".cmFeederSpeedChangeListener.stateChanged() >>>");
      for(
        int i=MainPlantModel.C_VF_UI_VALID_HEAD;
            i<=MainPlantModel.C_VF_UI_VALID_MAX;
            i++
      ){
        SubFeederDelegator.ccSetVFeederSpeed
          (i, cmLesVFeederBlock.get(i).ccGetValue());
        int lpTPH=SubAnalogScalarManager.ccRefer().ccGetVFeederFluxTPH(i);
        MainPlantModel.ccRefer().cmDesVFeederTPH.ccSet(i, lpTPH);
        cmLesVFeederBlock.get(i).cmTPHField
          .setText(VcNumericUtility.ccFormatFloatForOneAfter(lpTPH)+"tph");
      }//..~
    }//+++
  };//***
  
  //===
  
  @Override public final void ccInit(){
    
    //-- misc pane
    JPanel lpMiscPane = ScFactory
      .ccCreateGridPanel(SubAssistantPane.C_ROW_MAX, 1);
    lpMiscPane.setPreferredSize(new Dimension(149, 149));
    lpMiscPane.add(new JLabel(VcTranslator.tr("_vergin:")));
    lpMiscPane.add(new JSeparator(SwingConstants.HORIZONTAL));
    ConstSwingUI.ccAddAssistant(lpMiscPane,cmVFeederIxVibratorNT);
    ConstSwingUI.ccAddAssistant(lpMiscPane,cmVFeederIIxVibratorNT);
    lpMiscPane.add(new JLabel(VcTranslator.tr("_recycle:")));
    lpMiscPane.add(new JSeparator(SwingConstants.HORIZONTAL));
    
    //-- block pane
    JPanel lpVFeederPanel = ScFactory.ccCreateGridPanel(10,1);
    JPanel lpRFeederPanel = ScFactory.ccCreateGridPanel(10,1);
    for(int i=1;i<=10;i++){
      
      //-- V
      
      //-- V ** register
      lpVFeederPanel.add(cmLesVFeederBlock.get(i));
      cmMapOfConfigSW.put(cmLesVFeederBlock.get(i) .cmConfigSW,
        cmLesVFeederBlock.get(i) .cmRPMSpinner
      );
      //-- V ** event
      cmLesVFeederBlock.get(i).cmForceSW
        .addActionListener(MainActionManager.ccRefer().cmNotchListener);
      cmLesVFeederBlock.get(i).cmDisableSW
        .addActionListener(MainActionManager.ccRefer().cmNotchListener);
      cmLesVFeederBlock.get(i).cmConfigSW
        .addActionListener(cmFeederSpeedConfigListener);
      cmLesVFeederBlock.get(i).cmRPMSpinner
        .addChangeListener(cmFeederSpeedChangeListener);
      
      //-- V ** hide
      if(i>MainSpecificator.ccRefer().vmVFeederAmount){
        ScConst.ccHideComponent(cmLesVFeederBlock.get(i));
      }//..?
      
      //-- R 
      //-- R ** register
      lpRFeederPanel.add(cmLesRFeederBlock.get(i));
      //-- R ** event
      //-- R ** hide
      ScConst.ccHideComponent(cmLesRFeederBlock.get(i));
      
    }//..~
    
    //-- pack
    cmPane.add(lpMiscPane,BorderLayout.LINE_START);
    cmPane.add(lpVFeederPanel,BorderLayout.CENTER);
    cmPane.add(lpRFeederPanel,BorderLayout.LINE_END);
    
  }//..!
  
  //===
  
  public final void ccVRatioShift(float pxMagnitude){
    //[todo]:fix this
    int lpStep=VcNumericUtility.ccInteger(50, pxMagnitude);
    int lpBuf;
    for(ScFeederBlock it : cmLesVFeederBlock){
      lpBuf = it.ccGetValue();
      lpBuf+=lpStep;
      it.ccSetValue(lpBuf);
    }//..~
  }//+++
  
  //===

  @Override public final String ccGetTitle() {
    return C_TAB_NAME;
  }//++>

  @Override public final JPanel ccGetPane() {
    return cmPane;
  }//++>
  
}//***eof
