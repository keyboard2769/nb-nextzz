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

package nextzz.ppplocalui;

import java.util.Arrays;
import java.util.List;
import kosui.ppplocalui.EcConst;
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcIcon;
import kosui.ppplocalui.EcShape;
import kosui.ppplocalui.EcText;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplocalui.EiGroup;
import kosui.ppputil.VcTranslator;
import nextzz.pppmain.MainSketch;

public final class SubMixerGroup implements EiGroup{
  
  private static final SubMixerGroup SELF = new SubMixerGroup();
  public static SubMixerGroup ccRefer(){return SELF;}//+++
  
  //===
  
  //-- pane
  public final EcShape cmPlate = new EcShape();
  public final int cmPlateColor
    = EcConst.ccAdjustColor(MainSketch.C_COLOR_BACKGROUD, -8);
  
  //-- icon
  public final EcIcon cmMixerIcon =
    new EcIcon(ConstLocalUI.O_MIXER_ON, ConstLocalUI.O_MIXER_OFF);
  public final EcMixerGate cmMixerGate =
    new EcMixerGate();
  public final EcElement cmHasContentPL
    = new EcElement(" ");
  
  //-- box
  public final EcValueBox
    cmWetCountBox = new EcValueBox("_wet", "00 S"),
    cmDryCountBox = new EcValueBox("_dry", "00 S"),
    cmMixerTemperatureBox = new EcValueBox("_mixer", "-000 'C")
  ;//...
  public final EcText
    cmWetText = new EcText(VcTranslator.tr("_wet")),
    cmDryText = new EcText(VcTranslator.tr("_dry"))
  ;//...
  
  //===
  
  private SubMixerGroup(){
    
    //-- pane
    cmPlate.ccSetW(SubWeigherGroup.ccRefer().cmPlateAG.ccGetW());
    cmPlate.ccSetH(80);
    cmPlate.ccSetLocation(
      SubWeigherGroup.ccRefer().cmPlateAG,
      0,
      ConstLocalUI.C_SIDE_MARGIN
    );
    cmPlate.ccSetBaseColor(cmPlateColor);
    
    //-- icon
    cmMixerIcon.ccSetLocation(cmPlate, 'v');
    cmMixerIcon.ccSetY(cmPlate.ccGetY()+5);
    cmMixerGate.ccSetW(cmMixerIcon.ccGetW()/2);
    cmMixerGate.ccSetH(EcConst.C_DEFAULT_TEXT_HEIGHT/2);
    cmMixerGate.ccSetLocation(cmMixerIcon, 'b');
    cmMixerGate.ccSetLocation(cmMixerIcon, 'v');
    cmMixerGate.ccShiftLocation(0, ConstLocalUI.C_INPANE_GAP*2);
    cmHasContentPL.ccSetLocation(cmMixerIcon, 2,2);
    cmHasContentPL.ccSetSize(cmMixerIcon.ccGetW()-4,4);
    
    //-- box
    cmDryCountBox.ccSetLocation(
      cmMixerIcon.ccGetX()-5,
      cmHasContentPL.ccEndY()+5
    );
    cmWetCountBox.ccSetLocation(cmDryCountBox, 0,2);
    cmMixerTemperatureBox.ccSetLocation(
      cmPlate.ccEndX()-cmMixerTemperatureBox.ccGetW()-5,
      cmDryCountBox.ccGetY()
    );
    cmDryText.ccSetLocation(cmDryCountBox,'l');
    cmDryText.ccSetTextColor(EcConst.C_LIT_GRAY);
    cmWetText.ccSetLocation(cmWetCountBox,'l');
    cmWetText.ccSetTextColor(EcConst.C_LIT_GRAY);
    ConstLocalUI.ccSetupClickableBoxColor(cmDryCountBox);
    ConstLocalUI.ccSetupClickableBoxColor(cmWetCountBox);
    ConstLocalUI.ccSetupTemperatureBoxColor(cmMixerTemperatureBox);
    
  }//..!

  @Override public List<? extends EcShape> ccGiveShapeList(){
    return Arrays.asList(
      cmPlate,
      cmDryText,cmWetText
    );
  }//+++
  
  @Override public List<? extends EcElement> ccGiveElementList(){
    return Arrays.asList(
      cmMixerIcon,cmMixerGate,cmHasContentPL,
      cmDryCountBox,cmWetCountBox,cmMixerTemperatureBox
    );
  }//+++
  
 }//***eof
