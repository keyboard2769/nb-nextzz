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
      new EcText("&vf00"),new EcText("&vf01"),
      new EcText("&vf02"),new EcText("&vf03"),
      //--
      new EcText("&vf04"),new EcText("&vf05"),
      new EcText("&vf06"),new EcText("&vf07"),
      //--
      new EcText("&vf08"),new EcText("&vf09"),
      new EcText("&vf10"),new EcText("&vf11"),
      //--
      new EcText("&vf12"),new EcText("&vf13"),
      new EcText("&vf14"),new EcText("&vf15")
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
      new EcValueBox("&vf00", "0000 r", 0x3510),
      new EcValueBox("&vf01", "0000 r", 0x3511),
      new EcValueBox("&vf02", "0000 r", 0x3512),
      new EcValueBox("&vf03", "0000 r", 0x3513),
      //--
      new EcValueBox("&vf04", "0000 r", 0x3514),
      new EcValueBox("&vf05", "0000 r", 0x3515),
      new EcValueBox("&vf06", "0000 r", 0x3516),
      new EcValueBox("&vf07", "0000 r", 0x3517),
      //--
      new EcValueBox("&vf08", "0000 r", 0x3518),
      new EcValueBox("&vf09", "0000 r", 0x3519),
      new EcValueBox("&vf10", "0000 r", 0x351A),
      new EcValueBox("&vf11", "0000 r", 0x351B),
      //--
      new EcValueBox("&vf12", "0000 r", 0x351C),
      new EcValueBox("&vf13", "0000 r", 0x351D),
      new EcValueBox("&vf14", "0000 r", 0x351E),
      new EcValueBox("&vf15", "0000 r", 0x351F)
    ));//...
  public final List<EcGauge> cmDesFeederRPMGauge
    = Collections.unmodifiableList(Arrays.asList(
      new EcGauge("&vf00"),new EcGauge("&vf01"),
      new EcGauge("&vf02"),new EcGauge("&vf03"),
      //--
      new EcGauge("&vf04"),new EcGauge("&vf05"),
      new EcGauge("&vf06"),new EcGauge("&vf07"),
      //--
      new EcGauge("&vf08"),new EcGauge("&vf09"),
      new EcGauge("&vf10"),new EcGauge("&vf11"),
      //--
      new EcGauge("&vf12"),new EcGauge("&vf13"),
      new EcGauge("&vf14"),new EcGauge("&vf15")
    ));//...
  public final EcButton cmSpeedAutoAdjustSW = new EcButton("&ratio", 0x3599);
  
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
    final int lpAdditionalMargin = 100;
    lpPotentialW = lpSingleHopperWidth*6
      + lpSingleHopperWidth/2
      + ConstLocalUI.C_INPANE_GAP*6
      + lpAdditionalMargin;
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
      it.ccSetupColor(EcConst.C_YELLOW, EcConst.C_DARK_GRAY);
      it.ccSetTextColor(EcConst.C_DIM_GRAY);
    }//..~
    for(EcGauge it:cmDesFeederRPMGauge){
      it.ccSetSize(cmDesFeederRPMBox.get(0).ccGetW(),lpSingleGaugeH);
      it.ccSetupColor(EcConst.C_GRAY, EcConst.C_YELLOW);
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
    cmSpeedAutoAdjustSW.ccSetH(ConstLocalUI.C_DEFAULT_SINGLELINE_H);
    cmSpeedAutoAdjustSW.ccSetLocation
      (cmPlate,ConstLocalUI.C_INPANE_GAP, ConstLocalUI.C_INPANE_GAP);
    
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
    lpRes.addAll(Arrays.asList(cmBelconBackwardPL,cmBelconForwarPL));
    lpRes.add(cmSpeedAutoAdjustSW);
    return lpRes;
  }//+++
  
 }//***eof
