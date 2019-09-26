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

import java.awt.ItemSelectable;
import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import kosui.pppswingui.ScConst;
import kosui.pppswingui.ScFactory;
import kosui.ppputil.VcTranslator;
import nextzz.pppmain.MainActionManager;
import nextzz.pppmodel.MainSpecificator;

/**
 *
 * @author Key Parker from K.I.C
 */
public final class SubAssistantPane {
  
  public static final int C_ROW_MAX = 24;

  private static final SubAssistantPane SELF = new SubAssistantPane();
  public static final SubAssistantPane ccRefer(){return SELF;}//+++
  private SubAssistantPane(){}//++!

  //===
  
  public static final String C_TAB_NAME = VcTranslator.tr("_assistant");
  
  public final JPanel cmPane = ScFactory.ccCreateGridPanel(1, 6);
  
  public final JComboBox<String> cmTowerBlowerNT
    = ScFactory.ccCreateNotch(
      VcTranslator.tr("_tblower:a"),//..auto
      null,
      VcTranslator.tr("_tblower:d") //..disable
    );
  
  public final JComboBox<String> cmAirPulseModeNT
    = ScFactory.ccCreateNotch(
      VcTranslator.tr("_apulsem:c"),//..compressor
      VcTranslator.tr("_apulsem:rf"),//..rf
      VcTranslator.tr("_apulsem:f") //..force
    );
  
  public final JComboBox<String> cmAirPulseOperateNT
    = ScFactory.ccCreateNotch(
      VcTranslator.tr("_apulseo:u"),//..use
      null,
      VcTranslator.tr("_apulseo:d") //..disable
    );
  
  public final JComboBox<String> cmCoolingDamperNT
    = ScFactory.ccCreateNotch(
      VcTranslator.tr("_cdamper:auto"),
      VcTranslator.tr("_cdamper:d"),
      VcTranslator.tr("_cdamper:m")
    );
  
  public final JComboBox<String> cmVFeederIxVibratorNT
    = ScFactory.ccCreateNotch(
      VcTranslator.tr("_vf1vib:auto"),
      VcTranslator.tr("_vf1vib:d"),
      VcTranslator.tr("_vf1vib:m")
    );
  
  public final JComboBox<String> cmVFeederIIxVibratorNT
    = ScFactory.ccCreateNotch(
      VcTranslator.tr("_vf2vib:auto"),
      VcTranslator.tr("_vf2vib:d"),
      VcTranslator.tr("_vf2vib:m")
    );
  
  public final JComboBox<String> cmVCombustSourceNT
    = ScFactory.ccCreateNotch(
      VcTranslator.tr("_vcombs:g"),//..gas
      null,
      VcTranslator.tr("_vcombs:o") //..oil
    );
  
  public final JComboBox<String> cmVFuelExchangeNT
    = ScFactory.ccCreateNotch(
      VcTranslator.tr("_vfuele:c"),//..continuouse
      null,
      VcTranslator.tr("_vfuele:x") //..exchange
    );
  
  //--
  
  public final JComboBox<String> cmFillerSiloAirNT
    = ScFactory.ccCreateNotch(
      VcTranslator.tr("_fsair:a"),//..auto
      null,
      VcTranslator.tr("_fsair:d") //..disable
    );
  
  public final JComboBox<String> cmFillerSiloSelectNT
    = ScFactory.ccCreateNotch(
      VcTranslator.tr("_fssel:u"),//..number one 
      VcTranslator.tr("_fssel:m"),//..mix
      VcTranslator.tr("_fssel:d") //..number two
    );
  
  public final JComboBox<String> cmCementSiloAirNT
    = ScFactory.ccCreateNotch(
      VcTranslator.tr("_csair:a"),//..auto
      null,
      VcTranslator.tr("_csair:d") //..disable
    );
  
  public final JComboBox<String> cmDustSiloAirNT
    = ScFactory.ccCreateNotch(
      VcTranslator.tr("_dsair:a"),//..auto
      null,
      VcTranslator.tr("_dsair:d") //..disable
    );
  
  public final JToggleButton cmDustSiloDischargeSW
    = ScFactory.ccCreateCommandToggler("_dsdis");
  
  //--
  
  public final JComboBox<String> cmAsphaltSupplySW
    = ScFactory.ccCreateNotch(
      VcTranslator.tr("_assupp:f"),//..forward
      null,
      VcTranslator.tr("_assupp:r") //..reversed
    );
  
  //===
  
  public final void ccInit(){
    
    //-- layout ** tower
    JPanel lpLeftI=ScFactory.ccCreateGridPanel(C_ROW_MAX, 1);
    lpLeftI.add(new JLabel(VcTranslator.tr("_aptower:")));
    ssAddAssistant(lpLeftI, cmTowerBlowerNT);
    
    //-- layout ** ag
    JPanel lpLeftII=ScFactory.ccCreateGridPanel(C_ROW_MAX, 1);
    lpLeftII.add(new JLabel(VcTranslator.tr("_vergin:")));
    lpLeftII.add(cmAirPulseOperateNT);
    lpLeftII.add(cmAirPulseModeNT);
    lpLeftII.add(cmCoolingDamperNT);
    lpLeftII.add(new JSeparator(SwingConstants.HORIZONTAL));
    lpLeftII.add(cmVFeederIxVibratorNT);
    lpLeftII.add(cmVFeederIIxVibratorNT);
    lpLeftII.add(new JSeparator(SwingConstants.HORIZONTAL));
    lpLeftII.add(cmVCombustSourceNT);
    lpLeftII.add(cmVFuelExchangeNT);
    
    //-- layout ** filler/asphalt
    JPanel lpLeftIII=ScFactory.ccCreateGridPanel(C_ROW_MAX, 1);
    lpLeftIII.add(new JLabel(VcTranslator.tr("_powder:")));
    lpLeftIII.add(cmFillerSiloAirNT);
    ssAddAssistant(lpLeftIII, cmFillerSiloSelectNT);
    lpLeftIII.add(cmCementSiloAirNT);
    lpLeftIII.add(cmDustSiloAirNT);
    lpLeftIII.add(cmDustSiloDischargeSW);
    lpLeftIII.add(new JSeparator(SwingConstants.HORIZONTAL));
    lpLeftIII.add(new JLabel(VcTranslator.tr("_asphalt:")));
    ssAddAssistant(lpLeftIII, cmAsphaltSupplySW);
    
    //-- layout ** recycle/add
    JPanel lpLeftIV=ScFactory.ccCreateGridPanel(C_ROW_MAX, 1);
    lpLeftIV.add(new JLabel(VcTranslator.tr("_recycle:")));
    lpLeftIV.add(new JSeparator(SwingConstants.HORIZONTAL));
    lpLeftIV.add(new JLabel(VcTranslator.tr("_additive:")));
    
    //-- layout ** misc
    JPanel lpLeftV=ScFactory.ccCreateGridPanel(C_ROW_MAX, 1);
    lpLeftV.add(new JLabel(VcTranslator.tr("_misc:")));
    
    //-- optional
    if(MainSpecificator.ccRefer().mnFRCattegoryCount<3)
      {ScConst.ccHideComponent(cmCementSiloAirNT);}
    
    //-- pack
    cmPane.add(lpLeftI);
    cmPane.add(lpLeftII);
    cmPane.add(lpLeftIII);
    cmPane.add(lpLeftIV);
    cmPane.add(lpLeftV);
    
  }//..!
  
  private void ssAddAssistant(JPanel pxPane, JComponent pxSwitch){
    assert pxPane!=null;
    assert pxSwitch!=null;
    pxPane.add(pxSwitch);
    if(pxSwitch instanceof JComboBox){
      ((JComboBox)pxSwitch).addActionListener
        (MainActionManager.ccRefer().cmNotchListener);
    }else 
    if(pxSwitch instanceof JToggleButton){
      ((JToggleButton)pxSwitch).addActionListener
        (MainActionManager.ccRefer().cmNotchListener);
    }else{
      throw new RuntimeException
        (".ssAddAssistant():you are not suppsed to do this");
    }//...?
  }//+++
  
 }//***eof
