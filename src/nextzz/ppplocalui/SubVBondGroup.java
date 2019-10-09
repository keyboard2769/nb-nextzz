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
import kosui.ppplocalui.EcButton;
import kosui.ppplocalui.EcConst;
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcGauge;
import kosui.ppplocalui.EcGraph;
import kosui.ppplocalui.EcIcon;
import kosui.ppplocalui.EcLamp;
import kosui.ppplocalui.EcShape;
import kosui.ppplocalui.EcText;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplocalui.EiGroup;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcTranslator;
import nextzz.pppmain.MainSketch;

public final class SubVBondGroup implements EiGroup{
  
  private static final SubVBondGroup SELF = new SubVBondGroup();
  public static SubVBondGroup ccRefer(){return SELF;}//+++

  //===
  
  //-- indicative
  public final EcShape cmPlate = new EcShape();
  
  public final EcValueBox cmTargetTemperatureTB
    = new EcValueBox("_vbtarget", "+000 'C", 0x3610);
  
  public final EcButton
    cmTargetDecrementSW = new EcButton("-", 0x3611),
    cmTargetIncrementSW = new EcButton("+", 0x3612)
  ;//...
  
  public final EcValueBox cmChuteTemperatureTB
    = new EcValueBox("_vbagc", "+000 'C");
  
  public final EcValueBox cmEntranceTemperatureCB
    = new EcValueBox("_bagenc", "+000 'C");
  
  public final EcValueBox cmVDPressureCB
    = new EcValueBox("_vdpb", "+000 kpa");
  
  public final EcValueBox cmBelconFluxCB
    = new EcValueBox("_agsc", "000.0 tph");
  
  public final EcGauge cmVDContentLV
    = new EcGauge();
  
  public final EcLamp
    cmBagHighLV = new EcLamp(12),
    cmBagLowLV  = new EcLamp(12)
  ;//...
  
  public final EcElement
    cmAirPulsePL = new EcElement("- - - -"),
    cmExfanPressurePL = new EcElement(),
    cmBurnerPressurePL = new EcElement(),
    cmBurnerIGPL = new EcElement(),
    cmBurnerPVPL = new EcElement(),
    cmBurnerMVPL = new EcElement()
  ;//...
  public final EcLamp cmFlamingPL = new EcLamp(8);
  
  //-- graphic 
  public final EcShape cmVDryerShape
    = new EcGraph(ConstLocalUI.O_V_DRYER);
  public final EcShape cmBagFilterShape
    = new EcGraph(ConstLocalUI.O_BAGFILTER);
  public final EcShape cmFirstDuctShape
    = new EcGraph(ConstLocalUI.O_FIRST_DUCT);
  public final EcShape cmSecondDuctShape
    = new EcGraph(ConstLocalUI.O_SECOND_DUCT);
  public final EcIcon cmBurnerIcon
    = new EcIcon(ConstLocalUI.O_V_BURNER_ON, ConstLocalUI.O_V_BURNER_OFF);
  public final EcIcon cmExfanIcon
    = new EcIcon(ConstLocalUI.O_V_EXFAN_ON, ConstLocalUI.O_V_EXFAN_OFF);
  
  //-- operative
  public final EcElement cmReadyPL
    = new EcElement("_ready");
  public final EcButton cmRunSW
    = new EcButton("_start", 0x3621);
  public final EcValueBox cmBurnerDegreeCB = new EcValueBox("_vbo", "+000 %");
  public final EcValueBox cmExfanDegreeCB = new EcValueBox("_vdo", "+000 %");
  public final EcButton
    cmExfanCloseSW = new EcButton("-", 0x3631),
    cmExfanOpenSW  = new EcButton("+", 0x3632),
    cmExfanAutoSW  = new EcButton("_auto", 0x3630)
  ;//...
  public final EcButton
    cmBurnerCloseSW = new EcButton("-", 0x3641),
    cmBurnerOpenSW  = new EcButton("+", 0x3642),
    cmBurnerAutoSW  = new EcButton("_auto", 0x3640)
  ;//...
  public final EcElement 
    cmOilPL = new EcElement("_oill"),
    cmGasPL = new EcElement("_gass"),
    cmHeavyPL = new EcElement("_hevo"),
    cmFuelPL = new EcElement("_feuo")
  ;//...
  public final EcText
    cmVExfanText = new EcText(VcTranslator.tr("_ve")),
    cmVBurnerText = new EcText(VcTranslator.tr("_vb"))
  ;//...
  
  //-- motorative
  public final EcShape cmBelconShape = new EcShape();
  public final EcLamp
    cmBelconForwarPL = new EcLamp("<"),
    cmBelconBackwardPL = new EcLamp(">")
  ;//...
  public final EcElement
    cmVDryerRollerA = new EcElement(),//.. A means burner side
    cmVDryerRollerC = new EcElement() //.. C means belcon side
  ;//...
  
  //===
  
  private SubVBondGroup(){
    
    int lpPotentialX;
    int lpPotentialY;
    int lpPotentialW;
    int lpPotentialH;
    int lpPotentialG;
    
    //-- plate
    cmPlate.ccSetLocation(
      SubVFeederGroup.ccRefer().cmPlate,
      0,
      ConstLocalUI.C_SIDE_MARGIN
    );
    cmPlate.ccSetW(SubVFeederGroup.ccRefer().cmPlate.ccGetW());
    cmPlate.ccSetBaseColor(MainSketch.C_COLOR_PLATE);
    
    //-- assert
    final int lpDryerGaugeGap=6;
    final int lpDryerCaseGap=8;
    final int lpDryerHeight=ConstLocalUI.C_DEFAULT_SINGLELINE_H
      + lpDryerGaugeGap*2+lpDryerCaseGap*2;
    final int lpDryerWidth=cmVDPressureCB.ccGetW()
      + lpDryerGaugeGap*2+lpDryerCaseGap*2;
    /* 4 */VcConst.ccLogln("vd-w",lpDryerWidth);
    /* 4 */VcConst.ccLogln("vd-h",lpDryerHeight);
    cmPlate.ccSetH(
      lpDryerHeight*2+ConstLocalUI.C_DEFAULT_SINGLELINE_H*2
        + ConstLocalUI.C_INPANE_GAP*12
    );
    
    //-- burning temperature
    lpPotentialH = ConstLocalUI.C_DEFAULT_SINGLELINE_H;
    lpPotentialG = cmTargetTemperatureTB.ccGetW()-lpPotentialH*2;
    cmTargetTemperatureTB.ccSetH(lpPotentialH);
    cmTargetTemperatureTB.ccSetLocation(
      cmPlate, 
      ConstLocalUI.C_INPANE_GAP, ConstLocalUI.C_INPANE_GAP
    );
    cmTargetDecrementSW.ccSetSize(lpPotentialH, lpPotentialH);
    cmTargetDecrementSW.ccSetLocation
      (cmTargetTemperatureTB, 0,ConstLocalUI.C_INPANE_GAP);
    cmTargetIncrementSW.ccSetSize(cmTargetDecrementSW);
    cmTargetIncrementSW.ccSetLocation(cmTargetDecrementSW, lpPotentialG, 0);
    cmChuteTemperatureTB.ccSetH(lpPotentialH);
    cmChuteTemperatureTB.ccSetLocation(
      cmTargetTemperatureTB.ccGetX(),
      cmPlate.ccCenterY()
    );
    ConstLocalUI.ccSetupClickableBoxColor(cmTargetTemperatureTB);
    ConstLocalUI.ccSetupTemperatureBoxColor(cmChuteTemperatureTB);
    
    //-- BOND!!
    lpPotentialX=cmPlate.ccGetW()/3+cmPlate.ccGetX();
    lpPotentialY=cmPlate.ccGetY()+ConstLocalUI.C_INPANE_GAP*2;
    cmBagFilterShape.ccSetLocation(lpPotentialX, lpPotentialY);
    cmVDryerShape.ccSetLocation(cmBagFilterShape,
      0,
      ConstLocalUI.C_INPANE_GAP*2
    );
    cmVDryerRollerA.ccSetupColor(
      ConstLocalUI.C_COLOR_POWERDEVICE_ON,
      ConstLocalUI.C_COLOR_POWERDEVICE_OFF
    );
    cmVDryerRollerC.ccSetupColor(
      ConstLocalUI.C_COLOR_POWERDEVICE_ON,
      ConstLocalUI.C_COLOR_POWERDEVICE_OFF
    );
    cmVDryerRollerA.ccSetSize
      (ConstLocalUI.C_SHAPE_DRYER_ROLLER_W, cmVDryerShape.ccGetH());
    cmVDryerRollerC.ccSetSize(cmVDryerRollerA);
    cmVDryerRollerA.ccSetLocation(
      cmVDryerShape.ccGetX()+cmVDryerShape.ccGetW()*1/4,
      cmVDryerShape.ccGetY()
    );
    cmVDryerRollerC.ccSetLocation(
      cmVDryerShape.ccGetX()+cmVDryerShape.ccGetW()*3/4,
      cmVDryerShape.ccGetY()
    );
    cmVDContentLV.ccSetLocation(cmVDryerShape, lpDryerCaseGap*2,lpDryerCaseGap);
    cmVDPressureCB.ccSetDigit(3);
    cmVDPressureCB.ccSetW(
      cmVDPressureCB.ccGetW()+lpDryerGaugeGap*4
    );
    cmVDPressureCB.ccSetH(ConstLocalUI.C_DEFAULT_SINGLELINE_H);
    cmVDPressureCB.ccSetLocation(cmVDContentLV,lpDryerGaugeGap,lpDryerGaugeGap);
    cmVDContentLV.ccSetSize(
      cmVDPressureCB.ccGetW()+lpDryerGaugeGap*2,
      cmVDPressureCB.ccGetH()+lpDryerGaugeGap*2
    );
    cmVDContentLV.ccSetColor(EcConst.C_ORANGE);
    cmEntranceTemperatureCB.ccSetH(ConstLocalUI.C_DEFAULT_SINGLELINE_H);
    cmEntranceTemperatureCB.ccSetLocation(
      cmVDryerShape.ccEndX()-cmEntranceTemperatureCB.ccGetW(),
      cmBagFilterShape.ccCenterY()-cmEntranceTemperatureCB.ccGetH()/2
    );
    ConstLocalUI.ccSetupPressureBoxColor(cmVDPressureCB);
    ConstLocalUI.ccSetupTemperatureBoxColor(cmEntranceTemperatureCB);
    
    //-- FAN!!
    cmExfanIcon.ccSetLocation(cmBagFilterShape.ccGetX()
      - cmExfanIcon.ccGetW()-ConstLocalUI.C_INPANE_GAP*8,
      cmBagFilterShape.ccGetY()+ConstLocalUI.C_INPANE_GAP*4
    );
    cmBurnerIcon.ccSetLocation(cmVDryerShape.ccGetX()
      - cmBurnerIcon.ccGetW()-ConstLocalUI.C_INPANE_GAP*2,
      cmVDryerShape.ccGetY()+ConstLocalUI.C_INPANE_GAP*6
    );
    
    //-- bag indicator
    final int lpLevelLampGap=10;
    cmBagHighLV.ccSetLocation(
      cmBagFilterShape.ccGetX()+ConstLocalUI.C_INPANE_GAP*4,
      cmBagFilterShape.ccCenterY()
    );
    cmBagLowLV.ccSetLocation(cmBagHighLV, lpLevelLampGap, lpLevelLampGap);
    cmAirPulsePL.ccSetColor(EcConst.C_LIT_WATER);
    cmAirPulsePL.ccSetH(6);
    cmAirPulsePL.ccSetLocation(cmBagFilterShape,
      ConstLocalUI.C_INPANE_GAP,
      ConstLocalUI.C_INPANE_GAP
    );
    
    //-- duct
    //-- duct ** 1st
    final int lpDuctThick = 4;
    final int lpDuctGap = 2;
    lpPotentialW = (cmVDryerShape.ccEndX()+lpDuctThick+lpDuctGap)
      - (cmBagFilterShape.ccEndX()+lpDuctThick);
    lpPotentialH = cmVDryerShape.ccCenterY()-cmBagFilterShape.ccGetY();
    /* 4 */VcConst.ccLogln("1std-w", lpPotentialW);
    /* 4 */VcConst.ccLogln("1std-h", lpPotentialH);
    cmFirstDuctShape.ccSetLocation(cmBagFilterShape,lpDuctGap, 0);
    //-- duct ** 2nd
    lpPotentialW=cmBagFilterShape.ccGetX()
       - cmExfanIcon.ccCenterX()-lpDuctThick;
    lpPotentialH=cmExfanIcon.ccEndY()-cmBagFilterShape.ccGetY()
       + cmExfanIcon.ccGetW()/2;
    /* 4 */VcConst.ccLogln("2ndd-w", lpPotentialW);
    /* 4 */VcConst.ccLogln("2ndd-h", lpPotentialH);
    cmSecondDuctShape.ccSetLocation(
      cmExfanIcon.ccCenterX(),
      cmBagFilterShape.ccGetY()
    );
    
    //-- burner indicator
    cmExfanPressurePL.ccSetSize(4, 4);
    cmExfanPressurePL.ccSetLocation(cmExfanIcon, 2, 2);
    cmBurnerPressurePL.ccSetSize(4,4);
    cmBurnerPressurePL.ccSetLocation(
      cmBurnerIcon.ccEndX()-6,
      cmBurnerIcon.ccGetY()+2
    );
    lpPotentialW=3;
    lpPotentialH=5;
    cmBurnerIGPL.ccSetSize(lpPotentialW,lpPotentialH);
    cmBurnerPVPL.ccSetSize(cmBurnerIGPL);
    cmBurnerMVPL.ccSetSize(cmBurnerPVPL);
    cmBurnerIGPL.ccSetLocation(
      cmBurnerIcon.ccEndX()-(lpPotentialH+1)*3-2,
      cmBurnerIcon.ccCenterY()-7
    );
    cmBurnerPVPL.ccSetLocation(cmBurnerIGPL, 1, 0);
    cmBurnerMVPL.ccSetLocation(cmBurnerPVPL, 1, 0);
    cmFlamingPL.ccSetLocation(
      cmVDryerShape.ccGetX()+ConstLocalUI.C_INPANE_GAP,
      cmVDryerShape.ccEndY()-ConstLocalUI.C_INPANE_GAP-cmFlamingPL.ccGetH()*2
    );
    cmBurnerIGPL.ccSetColor(EcConst.C_WHITE_PURE);
    cmBurnerPVPL.ccSetColor(EcConst.C_LIT_BLUE);
    cmBurnerMVPL.ccSetColor(EcConst.C_RED);
    cmExfanPressurePL.ccSetColor(EcConst.C_WHITE);
    cmBurnerPressurePL.ccSetColor(EcConst.C_WHITE);
    cmFlamingPL.ccSetColor(EcConst.C_LIT_ORANGE);
    
    //-- operative
    //-- operative ** ready-start
    lpPotentialH=ConstLocalUI.C_DEFAULT_SINGLELINE_H;
    cmReadyPL.ccSetSize(cmChuteTemperatureTB.ccGetW(),lpPotentialH);
    cmReadyPL.ccSetLocation(
      cmTargetTemperatureTB.ccGetX(),
      cmPlate.ccEndY()-lpPotentialH*2-ConstLocalUI.C_INPANE_GAP*2
    );
    cmRunSW.ccSetSize(cmReadyPL);
    cmRunSW.ccSetLocation(cmReadyPL, 0, ConstLocalUI.C_INPANE_GAP);
    //-- operative ** exfan
    cmExfanCloseSW.ccSetSize(lpPotentialH, lpPotentialH);
    cmExfanOpenSW.ccSetSize(cmExfanCloseSW);
    cmExfanAutoSW.ccSetSize(lpPotentialH*2,lpPotentialH);
    cmExfanCloseSW.ccSetLocation(lpPotentialX, cmReadyPL.ccGetY());
    cmExfanOpenSW.ccSetLocation(cmExfanCloseSW,ConstLocalUI.C_INPANE_GAP,0);
    cmExfanAutoSW.ccSetLocation(cmExfanOpenSW,ConstLocalUI.C_INPANE_GAP,0);
    //-- operative ** burner
    cmBurnerCloseSW.ccSetSize(cmExfanCloseSW);
    cmBurnerOpenSW.ccSetSize(cmExfanOpenSW);
    cmBurnerAutoSW.ccSetSize(cmExfanAutoSW);
    cmBurnerCloseSW.ccSetLocation(cmExfanCloseSW,0,ConstLocalUI.C_INPANE_GAP);
    cmBurnerOpenSW.ccSetLocation(cmExfanOpenSW,0,ConstLocalUI.C_INPANE_GAP);
    cmBurnerAutoSW.ccSetLocation(cmExfanAutoSW,0,ConstLocalUI.C_INPANE_GAP);
    //-- operative ** degree box
    cmExfanDegreeCB.ccSetH(ConstLocalUI.C_DEFAULT_SINGLELINE_H);
    cmExfanDegreeCB.ccSetLocation(cmExfanAutoSW,ConstLocalUI.C_INPANE_GAP,0);
    cmBurnerDegreeCB.ccSetH(ConstLocalUI.C_DEFAULT_SINGLELINE_H);
    cmBurnerDegreeCB.ccSetLocation(cmBurnerAutoSW,ConstLocalUI.C_INPANE_GAP,0);
    cmExfanDegreeCB.ccSetDigit(3);
    cmBurnerDegreeCB.ccSetDigit(3);
    ConstLocalUI.ccSetupDegreeBoxColor(cmExfanDegreeCB);
    ConstLocalUI.ccSetupDegreeBoxColor(cmBurnerDegreeCB);
    
    //-- text
    cmVExfanText.ccSetTextColor(EcConst.C_WHITE);
    cmVBurnerText.ccSetTextColor(EcConst.C_WHITE);
    lpPotentialX=cmReadyPL.ccEndX()+
      (cmExfanCloseSW.ccGetX()-cmReadyPL.ccEndX())*3/4;
    cmVExfanText.ccSetLocation(lpPotentialX, cmReadyPL.ccCenterY());
    cmVBurnerText.ccSetLocation(lpPotentialX, cmRunSW.ccCenterY());
    
    //-- combust source lamp
    cmGasPL.ccSetColor(EcConst.C_LIT_GREEN);
    cmOilPL.ccSetColor(EcConst.C_LIT_GREEN);
    cmGasPL.ccSetSize(cmExfanAutoSW);
    cmOilPL.ccSetSize(cmGasPL);
    cmFuelPL.ccSetSize(cmGasPL);
    cmHeavyPL.ccSetSize(cmGasPL);
    cmGasPL.ccSetLocation(
      cmPlate.ccEndX()-cmGasPL.ccGetW()*2-ConstLocalUI.C_INPANE_GAP*2,
      cmReadyPL.ccGetY()
    );
    cmOilPL.ccSetLocation(cmGasPL, ConstLocalUI.C_INPANE_GAP, 0);
    cmFuelPL.ccSetLocation(cmGasPL, 0, ConstLocalUI.C_INPANE_GAP);
    cmHeavyPL.ccSetLocation(cmFuelPL, ConstLocalUI.C_INPANE_GAP, 0);
    
    //-- inclined belcon
    lpPotentialW=
      (cmHeavyPL.ccEndX()-cmVDryerShape.ccEndX())
       * 2/3;
    ConstLocalUI.ccAssembleBelcon(
      cmBelconShape, cmBelconForwarPL, cmBelconBackwardPL,
      cmVDryerShape.ccEndX()+ConstLocalUI.C_INPANE_GAP*8,
      cmVDryerShape.ccEndY()-cmBelconForwarPL.ccGetH(),
      lpPotentialW, cmBelconForwarPL.ccGetH()
    );
    cmBelconFluxCB.ccSetH(ConstLocalUI.C_DEFAULT_SINGLELINE_H);
    cmBelconFluxCB.ccSetLocation(
      cmPlate.ccEndX()-cmBelconFluxCB.ccGetW()-ConstLocalUI.C_INPANE_GAP,
      cmVDryerShape.ccGetY()
    );
    ConstLocalUI.ccSetupFluxBoxColor(cmBelconFluxCB);
    
  }//..!
  
  //===
  
  @Override public List<? extends EcShape> ccGiveShapeList(){
    return Arrays.asList(
      cmPlate,
      cmFirstDuctShape,cmSecondDuctShape,
      cmBagFilterShape,cmVDryerShape,
      cmBelconShape,
      cmVExfanText,cmVBurnerText
    );
  }//+++

  @Override public List<? extends EcElement> ccGiveElementList(){
    return Arrays.asList(cmTargetTemperatureTB,cmChuteTemperatureTB,
      cmTargetDecrementSW,cmTargetIncrementSW,
      cmVDryerRollerA,cmVDryerRollerC,
      cmVDContentLV,cmVDPressureCB,cmEntranceTemperatureCB,
      cmBagHighLV,cmBagLowLV,cmAirPulsePL,
      cmExfanIcon,cmBurnerIcon,
      cmExfanPressurePL,cmBurnerPressurePL,
      cmBurnerIGPL,cmBurnerPVPL,cmBurnerMVPL,cmFlamingPL,
      cmBelconForwarPL,cmBelconBackwardPL,cmBelconFluxCB,
      cmReadyPL,cmRunSW,
      cmExfanCloseSW,cmExfanOpenSW,cmExfanAutoSW,cmExfanDegreeCB,
      cmBurnerCloseSW,cmBurnerOpenSW,cmBurnerAutoSW,cmBurnerDegreeCB,
      cmOilPL,cmGasPL,cmFuelPL,cmHeavyPL
    );
  }//+++
  
 }//***eof
