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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import kosui.ppplocalui.EcButton;
import kosui.ppplocalui.EcConst;
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcGauge;
import kosui.ppplocalui.EcGraph;
import kosui.ppplocalui.EcShape;
import kosui.ppplocalui.EcText;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplocalui.EiGroup;
import kosui.ppplogic.ZcRangedModel;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcLocalConsole;
import kosui.ppputil.VcTranslator;
import nextzz.pppmain.MainSketch;
import nextzz.pppmodel.MainSpecificator;

public final class SubVSurgeGroup implements EiGroup{
  
  private static final SubVSurgeGroup SELF = new SubVSurgeGroup();
  public static SubVSurgeGroup ccRefer(){return SELF;}//+++

  //===
  
  //-- pane
  public final EcShape cmPlate = new EcShape();
  
  //-- ag
  public final EcGraph cmHotbinShape
    = new EcGraph(ConstLocalUI.O_HOT_BIN);
  public final EcElement
    cmOverFlowedLV = new EcElement(VcTranslator.tr("_ofbin")),
    cmOverSizedLV = new EcElement(VcTranslator.tr("_osbin"))
  ;//...
  public final EcButton
    cmOverFlowedGateSW = new EcButton("_disc", 0x3711),
    cmOverSizedGateSW = new EcButton("_disc", 0x3712)
  ;//..
  public final EcText cmSandTemperatueText
    = new EcText(VcTranslator.tr("_sandt"));
  public final EcValueBox cmSandTemperatureCB
    = new EcValueBox("_sand", "-000 'C");
  public final List<EcGauge> cmDesHotbinLV
    = Collections.unmodifiableList(Arrays.asList(
      new EcGauge("??"),
      new EcGauge("ag1"),new EcGauge("ag2"),new EcGauge("ag3"),
      new EcGauge("ag4"),new EcGauge("ag5"),new EcGauge("ag6"),
      new EcGauge("ag7")
    ));//...
  
  //-- fr
  //-- fr ** silo
  public final EcGauge cmDustSiloLV = new EcGauge("_fds");
    public final EcGraph cmDustSiloShape
      = new EcGraph(ConstLocalUI.O_BIN_CAN_TANK);
    public final EcText cmDustSiloText = new EcText(VcTranslator.tr("_fds"));
  public final EcGauge cmFillerSiloLV = new EcGauge("_ffs");
    public final EcGraph cmFillerSiloShape
      = new EcGraph(ConstLocalUI.O_BIN_CAN_TANK);
    public final EcText cmFillerSiloText = new EcText(VcTranslator.tr("_ffs"));
  public final EcGauge cmCementSiloLV = new EcGauge("_fcs");
    public final EcGraph cmCementSiloShape
      = new EcGraph(ConstLocalUI.O_BIN_CAN_TANK);
    public final EcText cmCementSiloText = new EcText(VcTranslator.tr("_fcs"));
  //-- fr ** bin
  public final EcGauge cmDustBinLV = new EcGauge("_fd");
    public final EcGraph cmDustBinShape
      = new EcGraph(ConstLocalUI.O_BIN_CAN_TANK);
    public final EcText cmDustBinText = new EcText(VcTranslator.tr("_fd"));
  public final EcGauge cmFillerBinLV = new EcGauge("_ff");
    public final EcGraph cmFillerBinShape
      = new EcGraph(ConstLocalUI.O_BIN_CAN_TANK);
    public final EcText cmFillerBinText = new EcText(VcTranslator.tr("_ff"));
  public final EcGauge cmCementBinLV = new EcGauge("_fc");
    public final EcGraph cmCementBinShape
      = new EcGraph(ConstLocalUI.O_BIN_CAN_TANK);
    public final EcText cmCementBinText = new EcText(VcTranslator.tr("_fc"));
  
  //-- as
  public final EcGauge cmAsphaultTankLV = new EcGauge("_ast");
  public final EcGraph cmAsphaultTankShape
    = new EcGraph(ConstLocalUI.O_BIN_CAN_TANK);
  public final EcText cmAsphaultTankText
    = new EcText(VcTranslator.tr("_ast"));
  public final EcElement
    cmTankInPL  = new EcElement("< #"),
    cmTankOutPL = new EcElement("> #")
  ;//...
  public final EcText cmPipeTemperatueText
    = new EcText(VcTranslator.tr("_pipet"));
  public final EcValueBox cmPipeTemperatureCB
    = new EcValueBox("_pipe", "-000 'C");
  
  //===
  
  private SubVSurgeGroup(){
    
    int lpPotentialX;
    int lpPotentialY;
    int lpPotentialW;
    int lpPotentialH;
    
    //-- pane
    lpPotentialX
      = SubWeigherGroup.ccRefer().cmPlateFR.ccGetX();
    lpPotentialY
      = VcLocalConsole.ccGetInstance().ccGetBarHeight()
        + ConstLocalUI.C_SIDE_MARGIN;
    lpPotentialW
      = SubWeigherGroup.ccRefer().cmPlateAS.ccEndX()
        - SubWeigherGroup.ccRefer().cmPlateFR.ccGetX();
    lpPotentialH
      = SubVFeederGroup.ccRefer().cmPlate.ccGetH();
    cmPlate.ccSetBound(lpPotentialX, lpPotentialY, lpPotentialW, lpPotentialH);
    cmPlate.ccSetBaseColor
      (EcConst.ccAdjustColor(MainSketch.C_COLOR_BACKGROUD, -8));
    
    //-- ag surge
    //-- ag surge ** high line
    lpPotentialX = SubWeigherGroup.ccRefer().cmPlateAG.ccGetX()
      +ConstLocalUI.C_INPANE_GAP;
    lpPotentialY = cmPlate.ccGetY()+ConstLocalUI.C_INPANE_GAP;
    lpPotentialW = SubWeigherGroup.ccRefer().cmPlateAG.ccGetW()/4;
    cmOverFlowedLV.ccSetW(lpPotentialW);
    cmOverFlowedGateSW.ccSetW(lpPotentialW);
    cmOverSizedLV.ccSetW(lpPotentialW);
    cmOverSizedGateSW.ccSetW(lpPotentialW);
    cmOverFlowedLV.ccSetLocation(lpPotentialX, lpPotentialY);
    cmOverSizedLV.ccSetLocation(cmOverFlowedLV, ConstLocalUI.C_INPANE_GAP,0);
    cmOverFlowedGateSW
      .ccSetLocation(cmOverFlowedLV,0,ConstLocalUI.C_INPANE_GAP);
    cmOverSizedGateSW
      .ccSetLocation(cmOverSizedLV,0,ConstLocalUI.C_INPANE_GAP);
    cmSandTemperatureCB.ccSetLocation(SubWeigherGroup.ccRefer().cmPlateAG.ccEndX()
       - cmSandTemperatureCB.ccGetW(),
      cmOverFlowedLV.ccGetY()
    );
    cmSandTemperatueText.ccSetLocation(cmSandTemperatureCB.ccCenterX(),
      cmOverFlowedGateSW.ccCenterY()
    );
    cmSandTemperatueText.ccSetTextColor(EcConst.C_LIT_GRAY);
    
    //-- ag surge ** dispose
    cmDesHotbinLV.get(0).ccHide();
    //-- ag surge ** lowline
    lpPotentialX = SubWeigherGroup.ccRefer().cmPlateAG.ccGetX();
    lpPotentialY = cmPlate.ccCenterY()+ConstLocalUI.C_INPANE_GAP;
    lpPotentialW = SubWeigherGroup.ccRefer().cmPlateAG.ccGetW();
    lpPotentialH = (cmPlate.ccGetH()-ConstLocalUI.C_INPANE_GAP*3)/2;
    /* 4 */VcConst.ccLogln("hb-w", lpPotentialW);
    /* 4 */VcConst.ccLogln("hb-h", lpPotentialH);
    cmHotbinShape.ccSetLocation(lpPotentialX, lpPotentialY);
    int lpFixedCattCount
      = ZcRangedModel.ccLimitInclude
        (MainSpecificator.ccRefer().mnAGCattegoryCount,1,7);
    lpPotentialW = (
        cmHotbinShape.ccGetW()
        - (ConstLocalUI.C_INPANE_GAP*(lpFixedCattCount+1))
      )/lpFixedCattCount;
    lpPotentialH = cmHotbinShape.ccGetH()*2/3;
    for(int i=lpFixedCattCount;i>=1;i--){
      cmDesHotbinLV.get(i).ccSetLocation(
        cmHotbinShape.ccGetX()+ConstLocalUI.C_INPANE_GAP
          + (
            (ConstLocalUI.C_INPANE_GAP+lpPotentialW)*(lpFixedCattCount-i)
          ),
        cmHotbinShape.ccGetY()+ConstLocalUI.C_INPANE_GAP
      );
      cmDesHotbinLV.get(i).ccSetSize(lpPotentialW,lpPotentialH);
    }//~
    //-- ag surge ** hiding
    for(int i=MainSpecificator.ccRefer().mnAGCattegoryCount+1;i<=7;i++){
      cmDesHotbinLV.get(i).ccHide();
    }//..~
    
    //-- fr surge
    //-- fr surge ** highline
    lpPotentialW = cmFillerBinShape.ccGetW();
    lpPotentialY = cmPlate.ccGetY()+ConstLocalUI.C_INPANE_GAP
     + EcConst.C_DEFAULT_TEXT_HEIGHT;
    ConstLocalUI.ccAssenmbleContentBin(
      cmDustSiloShape, cmDustSiloLV, cmDustSiloText,
      cmPlate.ccGetX()
       + ConstLocalUI.ccTellFlowInterval
        (ConstLocalUI.C_INPANE_GAP,lpPotentialW, 0),
      lpPotentialY, 'a'
    );
    ConstLocalUI.ccAssenmbleContentBin(
      cmFillerSiloShape, cmFillerSiloLV, cmFillerSiloText,
      cmPlate.ccGetX()
       + ConstLocalUI.ccTellFlowInterval
        (ConstLocalUI.C_INPANE_GAP,lpPotentialW, 1),
      lpPotentialY, 'a'
    );
    ConstLocalUI.ccAssenmbleContentBin(
      cmCementSiloShape, cmCementSiloLV, cmCementSiloText,
      cmPlate.ccGetX()
       + ConstLocalUI.ccTellFlowInterval
        (ConstLocalUI.C_INPANE_GAP,lpPotentialW, 2),
      lpPotentialY, 'a'
    );
    //-- fr surge ** lowline
    lpPotentialY = cmPlate.ccCenterY()+ConstLocalUI.C_INPANE_GAP
     + EcConst.C_DEFAULT_TEXT_HEIGHT;
    ConstLocalUI.ccAssenmbleContentBin(
      cmDustBinShape, cmDustBinLV, cmDustBinText,
      cmPlate.ccGetX()
       + ConstLocalUI.ccTellFlowInterval
        (ConstLocalUI.C_INPANE_GAP,lpPotentialW, 0),
      lpPotentialY, 'a'
    );
    ConstLocalUI.ccAssenmbleContentBin(
      cmFillerBinShape, cmFillerBinLV, cmFillerBinText,
      cmPlate.ccGetX()
       + ConstLocalUI.ccTellFlowInterval
        (ConstLocalUI.C_INPANE_GAP,lpPotentialW, 1),
      lpPotentialY, 'a'
    );
    ConstLocalUI.ccAssenmbleContentBin(
      cmCementBinShape, cmCementBinLV, cmCementBinText,
      cmPlate.ccGetX()
       + ConstLocalUI.ccTellFlowInterval
        (ConstLocalUI.C_INPANE_GAP,lpPotentialW, 2),
      lpPotentialY, 'a'
    );
    //-- fr surge ** optional
    if(!MainSpecificator.ccRefer().mnDustSiloExists){
      EcConst.ccHideAll(Arrays.asList
        (cmDustSiloLV,cmDustSiloShape,cmDustSiloText));
    }//..?
    if(!MainSpecificator.ccRefer().mnDustBinSeparated){
      EcConst.ccHideAll(Arrays.asList
        (cmDustBinLV,cmDustBinShape,cmDustBinText));
    }//..?
    if(MainSpecificator.ccRefer().mnFillerSiloCount<2){
      EcConst.ccHideAll(Arrays.asList
        (cmCementSiloLV,cmCementSiloShape,cmCementSiloText));
    }
    if(MainSpecificator.ccRefer().mnFRCattegoryCount<3){
      EcConst.ccHideAll(Arrays.asList
        (cmCementBinLV,cmCementBinShape,cmCementBinText));
    }//..?
    
    //-- as surge 
    //-- as surge ** lowline
    cmPipeTemperatureCB.ccSetLocation(
      SubWeigherGroup.ccRefer().cmPlateAS.ccEndX()
       - cmPipeTemperatureCB.ccGetW(),
      cmHotbinShape.ccGetY()
    );
    cmPipeTemperatueText.ccSetLocation(
      cmPipeTemperatureCB.ccCenterX(),
      cmPipeTemperatureCB.ccEndY()+EcConst.C_DEFAULT_TEXT_HEIGHT/2
    );
    cmPipeTemperatueText.ccSetTextColor(EcConst.C_LIT_GRAY);
    //-- as surge ** hightline
    lpPotentialH = 14;
    ConstLocalUI.ccAssenmbleContentBin(
      cmAsphaultTankShape, cmAsphaultTankLV, cmAsphaultTankText,
      cmPipeTemperatureCB.ccGetX(), cmFillerSiloLV.ccGetY(),
      'a'
    );
    cmTankInPL.ccSetH(lpPotentialH);
    cmTankOutPL.ccSetH(lpPotentialH);
    cmTankInPL.ccSetLocation(cmAsphaultTankShape, ConstLocalUI.C_INPANE_GAP,0);
    cmTankOutPL.ccSetLocation(cmTankInPL,0, ConstLocalUI.C_INPANE_GAP);
    
  }//..!
  
  //===

  @Override public List<? extends EcShape> ccGiveShapeList(){
    return Arrays.asList(cmPlate,
      cmHotbinShape,cmSandTemperatueText,
      cmDustSiloShape,cmDustSiloText,cmDustBinShape,cmDustBinText,
      cmFillerSiloShape,cmFillerSiloText,cmFillerBinShape,cmFillerBinText,
      cmCementSiloShape,cmCementSiloText,cmCementBinShape,cmCementBinText,
      cmAsphaultTankShape,cmAsphaultTankText,
      cmPipeTemperatueText
    );
  }//+++

  @Override public List<? extends EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes = new ArrayList<EcElement>();
    lpRes.addAll(cmDesHotbinLV);
    lpRes.addAll(Arrays.asList(cmOverFlowedLV,cmOverFlowedGateSW,
      cmOverSizedLV,cmOverSizedGateSW,
      cmSandTemperatureCB,
      cmDustSiloLV,cmDustBinLV,
      cmFillerSiloLV,cmFillerBinLV,
      cmCementSiloLV,cmCementBinLV,
      cmTankInPL,cmTankOutPL,cmAsphaultTankLV,cmPipeTemperatureCB
    ));
    return lpRes;
  }//+++
  
 }//***eof
