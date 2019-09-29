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
import kosui.ppplocalui.EcLamp;
import kosui.ppplocalui.EcShape;
import kosui.ppplocalui.EcText;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplocalui.EiGroup;
import kosui.ppplogic.ZcRangedValueModel;
import kosui.ppputil.VcLocalConsole;
import nextzz.pppmain.MainSketch;
import nextzz.pppmodel.MainSpecificator;
import nextzz.pppswingui.ScFeederBlock;

public final class SubVFeederGroup implements EiGroup{
  
  public static final int C_FEEDER_ICON_MASK = 15;
  
  private static final SubVFeederGroup SELF = new SubVFeederGroup();
  public static SubVFeederGroup ccRefer(){return SELF;}//+++

  //===
  
  public final EcShape cmPlate = new EcShape();
  
  //-- belcon
  public final EcShape cmBelconShape = new EcShape();
  public final EcLamp
    cmBelconForwarPL = new EcLamp("<"),
    cmBelconBackwardPL = new EcLamp(">")
  ;//...
  
  //-- feeder
  public final List<EcText> cmDesFeederText
    = Collections.unmodifiableList(Arrays.asList(
      new EcText("VF00"),new EcText("VF01"),
      new EcText("VF02"),new EcText("VF03"),
      //--
      new EcText("VF04"),new EcText("VF05"),
      new EcText("VF06"),new EcText("VF07"),
      //--
      new EcText("VF08"),new EcText("VF09"),
      new EcText("VF10"),new EcText("VF11"),
      //--
      new EcText("VF12"),new EcText("VF13"),
      new EcText("VF14"),new EcText("VF15")
    ));//...
  public final List<EcGraph> cmDesFeederShape
    = Collections.unmodifiableList(Arrays.asList(
      new EcGraph(ConstLocalUI.O_FEEDER),new EcGraph(ConstLocalUI.O_FEEDER),
      new EcGraph(ConstLocalUI.O_FEEDER),new EcGraph(ConstLocalUI.O_FEEDER),
      //--
      new EcGraph(ConstLocalUI.O_FEEDER),new EcGraph(ConstLocalUI.O_FEEDER),
      new EcGraph(ConstLocalUI.O_FEEDER),new EcGraph(ConstLocalUI.O_FEEDER),
      //--
      new EcGraph(ConstLocalUI.O_FEEDER),new EcGraph(ConstLocalUI.O_FEEDER),
      new EcGraph(ConstLocalUI.O_FEEDER),new EcGraph(ConstLocalUI.O_FEEDER),
      //--
      new EcGraph(ConstLocalUI.O_FEEDER),new EcGraph(ConstLocalUI.O_FEEDER),
      new EcGraph(ConstLocalUI.O_FEEDER),new EcGraph(ConstLocalUI.O_FEEDER)
    ));//...
  public final List<EcValueBox> cmDesFeederRPMBox
    = Collections.unmodifiableList(Arrays.asList(
      new EcValueBox("VF00", "0000 r", 0x3510),
      new EcValueBox("VF01", "0000 r", 0x3511),
      new EcValueBox("VF02", "0000 r", 0x3512),
      new EcValueBox("VF03", "0000 r", 0x3513),
      //--
      new EcValueBox("VF04", "0000 r", 0x3514),
      new EcValueBox("VF05", "0000 r", 0x3515),
      new EcValueBox("VF06", "0000 r", 0x3516),
      new EcValueBox("VF07", "0000 r", 0x3517),
      //--
      new EcValueBox("VF08", "0000 r", 0x3518),
      new EcValueBox("VF09", "0000 r", 0x3519),
      new EcValueBox("VF10", "0000 r", 0x351A),
      new EcValueBox("VF11", "0000 r", 0x351B),
      //--
      new EcValueBox("VF12", "0000 r", 0x351C),
      new EcValueBox("VF13", "0000 r", 0x351D),
      new EcValueBox("VF14", "0000 r", 0x351E),
      new EcValueBox("VF15", "0000 r", 0x351F)
    ));//...
  public final List<EcGauge> cmDesFeederRPMGauge
    = Collections.unmodifiableList(Arrays.asList(
      new EcGauge("VF00"),new EcGauge("VF01"),
      new EcGauge("VF02"),new EcGauge("VF03"),
      //--
      new EcGauge("VF04"),new EcGauge("VF05"),
      new EcGauge("VF06"),new EcGauge("VF07"),
      //--
      new EcGauge("VF08"),new EcGauge("VF09"),
      new EcGauge("VF10"),new EcGauge("VF11"),
      //--
      new EcGauge("VF12"),new EcGauge("VF13"),
      new EcGauge("VF14"),new EcGauge("VF15")
    ));//...
  
  public final EcButton
    cmRatioAutoSW  = new EcButton("_ratio", 0x3520),
    cmRatioDownSW  = new EcButton("-",      0x3521),
    cmRatioUpSW    = new EcButton("+",      0x3522)
  ;//..?
  
  //===
  
  private SubVFeederGroup(){
    
    //-- const
    final int lpSingleInnerGap = 1;
    final int lpSingleBoxH = 19;
    final int lpSingleGaugeH = 5;
    final int lpSingleHopperWidth = cmDesFeederShape.get(1).ccGetW();
    final int lpSingleHopperHeight = cmDesFeederShape.get(1).ccGetH();
    final int lpBelconHeight = cmBelconBackwardPL.ccGetH();
    
    //-- buffer
    int lpPotentialX;
    int lpPotentialY;
    int lpPotentialW;
    int lpPotentialH;
    
    //-- pane
    lpPotentialW = lpSingleHopperWidth*6
      + lpSingleHopperWidth/2
      + ConstLocalUI.C_INPANE_GAP*6;
    lpPotentialH = lpSingleHopperHeight*2
      + lpBelconHeight+ConstLocalUI.C_INPANE_GAP*4;
    lpPotentialX = MainSketch.ccGetPrefferedW()
      - lpPotentialW
      - ConstLocalUI.C_SIDE_MARGIN;
    lpPotentialY = VcLocalConsole.ccGetInstance().ccGetBarHeight()
      + ConstLocalUI.C_SIDE_MARGIN;
    
    //-- pane
    cmPlate.ccSetBound(lpPotentialX, lpPotentialY, lpPotentialW, lpPotentialH);
    cmPlate.ccSetBaseColor
      (EcConst.ccAdjustColor(MainSketch.C_COLOR_BACKGROUD, -8));
    
    //-- re styling
    for(EcValueBox it:cmDesFeederRPMBox){
      it.ccSetW(lpSingleHopperWidth-lpSingleInnerGap*2);
      it.ccSetH(lpSingleBoxH);
      ConstLocalUI.ccSetupFluxBoxColor(it);
    }//..~
    for(EcGauge it:cmDesFeederRPMGauge){
      it.ccSetSize(cmDesFeederRPMBox.get(0).ccGetW(),lpSingleGaugeH);
      it.ccSetupColor(EcConst.C_DARK_GRAY, EcConst.C_YELLOW);
      it.ccSetIsVertical(false);
    }//..~
    for(EcText it:cmDesFeederText){
      it.ccSetTextColor(EcConst.C_BLACK);
    }//..~
    
    //-- disposing
    for(int it:new int[]{0,11,12,13,14,15}){
      cmDesFeederRPMBox.get(it).ccHide();
      cmDesFeederRPMGauge.get(it).ccHide();
      cmDesFeederShape.get(it).ccHide();
      cmDesFeederText.get(it).ccHide();
    }//..~
    for(int i=MainSpecificator.ccRefer().mnVFeederAmount+1;i<=10;i++){
      cmDesFeederRPMBox.get(i).ccHide();
      cmDesFeederRPMGauge.get(i).ccHide();
      cmDesFeederShape.get(i).ccHide();
      cmDesFeederText.get(i).ccHide();
    }//..~
    
    //-- line dividing
    int[] lpDesUnderLine;
    int[] lpDesUpperLine;
    switch(MainSpecificator.ccRefer().mnVFeederAmount){
      case 4:
        lpDesUpperLine=new int[]{0};
        lpDesUnderLine=new int[]{1,2,3,4};
      break;
      case 5:
        lpDesUpperLine=new int[]{4,5};
        lpDesUnderLine=new int[]{1,2,3};
      break;
      case 6:default:
        lpDesUpperLine=new int[]{4,5,6};
        lpDesUnderLine=new int[]{1,2,3};
      break;
      case 7:
        lpDesUpperLine=new int[]{5,6,7};
        lpDesUnderLine=new int[]{1,2,3,4};
      break;
      case 8:
        lpDesUpperLine=new int[]{5,6,7,8};
        lpDesUnderLine=new int[]{1,2,3,4};
      break;
      case 9:
        lpDesUpperLine=new int[]{6,7,8,9};
        lpDesUnderLine=new int[]{1,2,3,4,5};
      break;
      case 10:
        lpDesUpperLine=new int[]{6,7,8,9,10};
        lpDesUnderLine=new int[]{1,2,3,4,5};
      break;
    }//..?
    
    //-- allocation
    //-- allocation ** upper
    lpPotentialW = lpSingleHopperWidth + ConstLocalUI.C_INPANE_GAP;
    lpPotentialX = cmPlate.ccEndX()-lpPotentialW*lpDesUpperLine.length;
    for(int it:lpDesUpperLine){
      cmDesFeederShape.get(it).ccSetLocation(lpPotentialX, 
        cmPlate.ccGetY()+ConstLocalUI.C_INPANE_GAP
      );
      cmDesFeederRPMBox.get(it).ccSetLocation
        (cmDesFeederShape.get(it), lpSingleInnerGap, lpSingleInnerGap);
      cmDesFeederRPMGauge.get(it).ccSetLocation(
        cmDesFeederRPMBox.get(it).ccGetX(),
        cmDesFeederRPMBox.get(it).ccEndY()
      );
      cmDesFeederText.get(it).ccSetLocation(
        cmDesFeederRPMGauge.get(it).ccCenterX(),
        cmDesFeederRPMGauge.get(it).ccEndY()
          + EcConst.C_DEFAULT_AUTOSIZE_HEIGHT/3
      );
      lpPotentialX+=lpPotentialW;
    }//..~
    //-- allocation ** under
    lpPotentialX = cmPlate.ccEndX()
      - lpPotentialW*lpDesUnderLine.length - lpPotentialW/2;
    for(int it:lpDesUnderLine){
      cmDesFeederShape.get(it).ccSetLocation(lpPotentialX, 
        cmPlate.ccGetY()+lpSingleHopperHeight+ConstLocalUI.C_INPANE_GAP*2
      );
      cmDesFeederRPMBox.get(it).ccSetLocation
        (cmDesFeederShape.get(it), lpSingleInnerGap, lpSingleInnerGap);
      cmDesFeederRPMGauge.get(it).ccSetLocation(
        cmDesFeederRPMBox.get(it).ccGetX(),
        cmDesFeederRPMBox.get(it).ccEndY()
      );
      cmDesFeederText.get(it).ccSetLocation(
        cmDesFeederRPMGauge.get(it).ccCenterX(),
        cmDesFeederRPMGauge.get(it).ccEndY()
          + EcConst.C_DEFAULT_AUTOSIZE_HEIGHT/3
      );
      lpPotentialX+=lpPotentialW;
    }//..~
    
    //-- belcon
    lpPotentialX=cmPlate.ccGetX()+ConstLocalUI.C_INPANE_GAP+lpBelconHeight;
    lpPotentialY=cmPlate.ccEndY()-lpBelconHeight-ConstLocalUI.C_INPANE_GAP;
    lpPotentialW=cmPlate.ccGetW()-lpBelconHeight*2-ConstLocalUI.C_INPANE_GAP*2;
    ConstLocalUI.ccAssembleBelcon(
      cmBelconShape, cmBelconForwarPL, cmBelconBackwardPL,
      lpPotentialX, lpPotentialY, lpPotentialW, lpBelconHeight
    );
    
    //-- the switch
    lpPotentialH = ConstLocalUI.C_DEFAULT_SINGLELINE_H;
    lpPotentialW = lpPotentialH*2+ConstLocalUI.C_INPANE_GAP;
    cmRatioAutoSW.ccSetSize(lpPotentialW,lpPotentialH);
    cmRatioAutoSW.ccSetLocation
      (cmPlate,ConstLocalUI.C_INPANE_GAP, ConstLocalUI.C_INPANE_GAP);
    cmRatioDownSW.ccSetSize(lpPotentialH,lpPotentialH);
    cmRatioDownSW.ccSetLocation(cmRatioAutoSW,0,ConstLocalUI.C_INPANE_GAP);
    cmRatioUpSW.ccSetSize(lpPotentialH,lpPotentialH);
    cmRatioUpSW.ccSetLocation(cmRatioDownSW, ConstLocalUI.C_INPANE_GAP, 0);
    
  }//..!
  
  //===
  
  public final void ccSetFeederRPM(int pxIndex,int pxValue){
    int lpFixedValue = ZcRangedValueModel.ccLimitInclude
      (pxValue, ScFeederBlock.C_SPEED_MIN, ScFeederBlock.C_SPEED_MAX);
    cmDesFeederRPMBox.get(pxIndex&0xF).ccSetValue(lpFixedValue);
    cmDesFeederRPMGauge.get(pxIndex&0xF).ccSetPercentage
      (lpFixedValue, ScFeederBlock.C_SPEED_MAX);
  }//+++
  
  //===

  @Override public List<? extends EcShape> ccGiveShapeList(){
    List<EcShape> lpRes = new ArrayList<EcShape>();
    lpRes.addAll(Arrays.asList(cmPlate,cmBelconShape));
    lpRes.addAll(cmDesFeederShape);
    lpRes.addAll(cmDesFeederText);
    return lpRes;
  }//+++

  @Override public List<? extends EcElement> ccGiveElementList(){
    List<EcElement> lpRes = new ArrayList<EcElement>();
    lpRes.addAll(cmDesFeederRPMBox);
    lpRes.addAll(cmDesFeederRPMGauge);
    lpRes.addAll(Arrays.asList(
      cmBelconBackwardPL,cmBelconForwarPL,
      cmRatioAutoSW,cmRatioDownSW,cmRatioUpSW
    ));
    return lpRes;
  }//+++
  
 }//***eof
