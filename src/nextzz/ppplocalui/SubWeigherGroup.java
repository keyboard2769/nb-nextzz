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
import kosui.ppplocalui.EcShape;
import kosui.ppplocalui.EcText;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplocalui.EiGroup;
import kosui.ppputil.VcTranslator;
import nextzz.pppmain.MainSketch;
import nextzz.pppmodel.MainSpecificator;

public final class SubWeigherGroup implements EiGroup{
  
  private static final SubWeigherGroup SELF = new SubWeigherGroup();
  public static SubWeigherGroup ccRefer(){return SELF;}//+++

  //===
  
  public final EcShape
    cmPlateAD = new EcShape(),
    cmPlateFR = new EcShape(),
    cmPlateAG = new EcShape(),
    cmPlateAS = new EcShape(),
    cmPlateRC = new EcShape()
  ;//...
  
  public final EcValueBox
    //-- ag
    cmAGTargetCB=new EcValueBox("_agtgt", "+-0000 kg", 0x3801),
    cmAGCellCB=new EcValueBox("_agcell", "+-0000 kg", 0x3801),
    //-- fr
    cmFRTargetCB=new EcValueBox("_frtgt", "+000.0 kg", 0x3802),
    cmFRCellCB=new EcValueBox("_frcell", "+000.0 kg", 0x3802),
    //-- as
    cmASTargetCB=new EcValueBox("_astgt", "+000.0 kg", 0x3803),
    cmASCellCB=new EcValueBox("_ascell", "+000.0 kg", 0x3803),
    //-- RC
    cmRCTargetCB=new EcValueBox("_rctgt", "+-0000 kg", 0x3804),
    cmRCCellCB=new EcValueBox("_rccell", "+-0000 kg", 0x3804),
    //-- AD
    cmADTargetCB=new EcValueBox("_adtgt", "+-0000 kg", 0x3805),
    cmADCellCB=new EcValueBox("_adcell", "+-0000 kg", 0x3805)
    //--
  ;//...
  
  public final EcGauge
    cmAGCellLV=new EcGauge("_agcell"),
    cmFRCellLV=new EcGauge("_frcell"),
    cmASCellLV=new EcGauge("_ascell"),
    cmRCCellLV=new EcGauge("_rccell"),
    cmADCellLV=new EcGauge("_adcell")
  ;//...
  
  //-- control
  //-- control ** ag
  public final List<EcButton> cmLesAGWeighSW
    = Collections.unmodifiableList(Arrays.asList(
      new EcButton("_ag", 0x3810), 
      new EcButton("+", 0x3811),
      new EcButton("+", 0x3812), new EcButton("+", 0x3813),
	  //--
      new EcButton("+", 0x3814),
      new EcButton("+", 0x3815),
      new EcButton("+", 0x3816), new EcButton("+", 0x3817)
    ));//...
  public final List<EcButton> cmLesAGLockSW
    = Collections.unmodifiableList(Arrays.asList(
      new EcButton("_x_ag", 0x3818),
      new EcButton("x", 0x3819),
      new EcButton("x", 0x381A), new EcButton("x", 0x381B),
	  //--
      new EcButton("x", 0x381C), new EcButton("x", 0x381D),
      new EcButton("x", 0x381E), new EcButton("x", 0x381F)
    ));//...
  public final List<EcText> cmLesAGText
    = Collections.unmodifiableList(Arrays.asList(
      new EcText("_??"),
      new EcText("v1"),new EcText("v2"),new EcText("v3"),
      new EcText("v4"),new EcText("v5"),new EcText("v6"),
      new EcText("v7")
    ));
  //-- control ** fr
  public final List<EcButton> cmLesFRWeighSW
    = Collections.unmodifiableList(Arrays.asList(
      new EcButton("_fr", 0x3820), new EcButton("+", 0x3821),
      new EcButton("+", 0x3822), new EcButton("+", 0x3823)
    ));//...
  public final List<EcButton> cmLesFRLockSW
    = Collections.unmodifiableList(Arrays.asList(
      new EcButton("_x_fr", 0x3824), new EcButton("x", 0x3825),
      new EcButton("x", 0x3826), new EcButton("x", 0x3827)
    ));//...
  public final List<EcText> cmLesFRText
    = Collections.unmodifiableList(Arrays.asList(
      new EcText("_??"),
      new EcText(VcTranslator.tr("_fc")),
      new EcText(VcTranslator.tr("_ff")),
      new EcText(VcTranslator.tr("_fd"))
    ));
  //-- control ** as
  public final List<EcButton> cmLesASWeighSW
    = Collections.unmodifiableList(Arrays.asList(
      new EcButton("_as", 0x3830),new EcButton("+", 0x3831),
      new EcButton("+", 0x3832), new EcButton("+", 0x3833)
    ));//...
  public final List<EcButton> cmLesASLockSW
    = Collections.unmodifiableList(Arrays.asList(
      new EcButton("_x_as", 0x3834), new EcButton("x", 0x3835),
      new EcButton("x", 0x3836), new EcButton("x", 0x3837)
    ));//...
  public final List<EcText> cmLesASText
    = Collections.unmodifiableList(Arrays.asList(
      new EcText("_??"),
      new EcText(VcTranslator.tr("_sv")),
      new EcText(VcTranslator.tr("_st")),
      new EcText(VcTranslator.tr("_sc"))
    ));
  //-- control ** rc
  public final List<EcButton> cmLesRCWeighSW
    = Collections.unmodifiableList(Arrays.asList(
      new EcButton("_rc", 0x3840), new EcButton("+", 0x3841),
      new EcButton("+", 0x3842), new EcButton("+", 0x3843)
    ));//...
  public final List<EcButton> cmLesRCLockSW
    = Collections.unmodifiableList(Arrays.asList(
      new EcButton("_x_rc", 0x3844), new EcButton("x", 0x3845),
      new EcButton("x", 0x3846), new EcButton("x", 0x3847)
    ));//...
  public final List<EcText> cmLesRCText
    = Collections.unmodifiableList(Arrays.asList(
      new EcText("_??"),
      new EcText("_rx"),
      new EcText(VcTranslator.tr("_rc")),
      new EcText("_rx")
    ));
  //-- control ** ad
  public final List<EcButton> cmLesADWeighSW
    = Collections.unmodifiableList(Arrays.asList(
      new EcButton("_ad", 0x3850), new EcButton("+", 0x3851),
      new EcButton("+", 0x3852), new EcButton("+", 0x3853)
    ));//...
  public final List<EcButton> cmLesADLockSW
    = Collections.unmodifiableList(Arrays.asList(
      new EcButton("_x_ad", 0x3854), new EcButton("x", 0x3855),
      new EcButton("x", 0x3856), new EcButton("x", 0x3857)
    ));//...
  public final List<EcText> cmLesADText
    = Collections.unmodifiableList(Arrays.asList(
      new EcText("_??"),
      new EcText("_add"),new EcText("_add"),new EcText("_add")
    ));
  
  //===
  
  private SubWeigherGroup(){
    
    final int lpGaugeW=4;
    final int lpBoxH=19;
    final int lpBoxW=cmFRTargetCB.ccGetW();
    final int lpButtonScale=18;
    final int lpSingleCellW=lpBoxW+lpGaugeW+1;
    final int lpSingleCellH=(lpBoxH+1)*3;
    
    int lpPotentialX;
    int lpPotentialY;
    int lpPotentialW;
    int lpPotentialH;
    
    //-- plate 
    //-- plate ** coloring
    final int lpPlateColor=(EcConst.ccAdjustColor(MainSketch.C_COLOR_BACKGROUD, -8));
    cmPlateAD.ccSetBaseColor(lpPlateColor);
    cmPlateFR.ccSetBaseColor(lpPlateColor);
    cmPlateAG.ccSetBaseColor(lpPlateColor);
    cmPlateAS.ccSetBaseColor(lpPlateColor);
    cmPlateRC.ccSetBaseColor(lpPlateColor);
    //-- plate ** sizing
    //-- plate ** sizing ** outsider
    lpPotentialW=lpBoxW+lpGaugeW+ConstLocalUI.C_INPANE_GAP*2;
    lpPotentialH=lpSingleCellH+lpButtonScale*3+ConstLocalUI.C_INPANE_GAP*6;
    cmPlateAD.ccSetSize(lpPotentialW,lpPotentialH);
    cmPlateFR.ccSetSize(lpPotentialW,lpPotentialH);
    cmPlateAS.ccSetSize(lpPotentialW,lpPotentialH);
    cmPlateRC.ccSetSize(lpPotentialW,lpPotentialH);
    //-- plate ** sizing ** aggregater
    lpPotentialW=lpButtonScale*7+ConstLocalUI.C_INPANE_GAP*8;
    cmPlateAG.ccSetSize(lpPotentialW, lpPotentialH);
    //-- plate ** locating
    lpPotentialX=ConstLocalUI.C_SIDE_MARGIN;
    lpPotentialY=SubVBondGroup.ccRefer().cmPlate.ccGetY();
    cmPlateAD.ccSetLocation(lpPotentialX, lpPotentialY);
    cmPlateFR.ccSetLocation(cmPlateAD, ConstLocalUI.C_INPANE_GAP, 0);
    cmPlateAG.ccSetLocation(cmPlateFR, ConstLocalUI.C_INPANE_GAP, 0);
    cmPlateAS.ccSetLocation(cmPlateAG, ConstLocalUI.C_INPANE_GAP, 0);
    cmPlateRC.ccSetLocation(cmPlateAS, ConstLocalUI.C_INPANE_GAP, 0);
    
    //-- ag weigher
    ConstLocalUI.ccAssembleWeighControl(cmPlateAG,
      cmLesAGLockSW,cmLesAGWeighSW, cmLesAGText,
      MainSpecificator.ccRefer().vmAGCattegoryCount
    );
    ConstLocalUI.ccAssenbleCell(cmAGCellLV, cmAGTargetCB, cmAGCellCB,
      cmLesAGWeighSW.get(0),
      cmPlateAG.ccGetX()
        + (cmPlateAG.ccGetW()-lpSingleCellW)/2,
      cmPlateAG.ccEndY()-lpSingleCellH-ConstLocalUI.C_INPANE_GAP
    );
    
    //-- fr weigher
    ConstLocalUI.ccAssembleWeighControl(cmPlateFR,
      cmLesFRLockSW,cmLesFRWeighSW, cmLesFRText,
      3//.. always set by three, hiding is done separately
    );
    ConstLocalUI.ccAssenbleCell(cmFRCellLV, cmFRTargetCB, cmFRCellCB,
      cmLesFRWeighSW.get(0),
      cmPlateFR.ccGetX()+ConstLocalUI.C_INPANE_GAP,
      cmPlateFR.ccEndY()-lpSingleCellH-ConstLocalUI.C_INPANE_GAP
    );
    
    //-- as wweigher
    ConstLocalUI.ccAssembleWeighControl(cmPlateAS, 
      cmLesASLockSW,cmLesASWeighSW, cmLesASText,
      3//.. always set by three, hiding is done separately
    );
    ConstLocalUI.ccAssenbleCell(cmASCellLV, cmASTargetCB, cmASCellCB,
      cmLesASWeighSW.get(0),
      cmPlateAS.ccGetX()+ConstLocalUI.C_INPANE_GAP,
      cmPlateAS.ccEndY()-lpSingleCellH-ConstLocalUI.C_INPANE_GAP
    );
    
    //-- rc wweigher
    ConstLocalUI.ccAssembleWeighControl(cmPlateRC, 
      cmLesRCLockSW,cmLesRCWeighSW, cmLesRCText,
      3//.. always set by three, hiding is done separately
    );
    ConstLocalUI.ccAssenbleCell(cmRCCellLV, cmRCTargetCB, cmRCCellCB,
      cmLesRCWeighSW.get(0),
      cmPlateRC.ccGetX()+ConstLocalUI.C_INPANE_GAP,
      cmPlateRC.ccEndY()-lpSingleCellH-ConstLocalUI.C_INPANE_GAP
    );
    
    //-- AD weigher
    ConstLocalUI.ccAssembleWeighControl(cmPlateAD, 
      cmLesADLockSW,cmLesADWeighSW, cmLesADText,
      3//.. always set by three, hiding is done separately
    );
    ConstLocalUI.ccAssenbleCell(cmADCellLV, cmADTargetCB, cmADCellCB,
      cmLesADWeighSW.get(0),
      cmPlateAD.ccGetX()+ConstLocalUI.C_INPANE_GAP,
      cmPlateAD.ccEndY()-lpSingleCellH-ConstLocalUI.C_INPANE_GAP
    );
    
    //-- cell lock
    //-- cell lock ** anchor
    cmLesAGLockSW.get(0).ccSetSize(SubOperativeGroup.ccRefer().cmAGZeroSW);
    cmLesAGLockSW.get(0).ccSetLocation(
      SubOperativeGroup.ccRefer().cmCellPane,
      ConstLocalUI.C_INPANE_MARGIN_S,
      ConstLocalUI.C_INPANE_MARGIN_U
    );
    //-- cell lock ** rest
    int lpPotentialG = ConstLocalUI.C_INPANE_GAP; 
    cmLesFRLockSW.get(0).ccSetSize(cmLesAGLockSW.get(0));
    cmLesASLockSW.get(0).ccSetSize(cmLesFRLockSW.get(0));
    cmLesRCLockSW.get(0).ccSetSize(cmLesRCLockSW.get(0));
    cmLesADLockSW.get(0).ccSetSize(cmLesADLockSW.get(0));
    cmLesFRLockSW.get(0).ccSetLocation(cmLesAGLockSW.get(0),lpPotentialG,0);
    cmLesASLockSW.get(0).ccSetLocation(cmLesFRLockSW.get(0),lpPotentialG,0);
    cmLesRCLockSW.get(0).ccSetLocation(cmLesAGLockSW.get(0),0,lpPotentialG);
    cmLesADLockSW.get(0).ccSetLocation(cmLesRCLockSW.get(0),lpPotentialG,0);
    
    //-- hiding
    //-- hiding ** ag
    for(int i=MainSpecificator.ccRefer().vmAGCattegoryCount+1;i<=7;i++){
      cmLesAGLockSW.get(i).ccHide();
      cmLesAGWeighSW.get(i).ccHide();
      cmLesAGText.get(i).ccHide();
    }//..~
    //-- hiding ** fr
    if(MainSpecificator.ccRefer().vmFRCattegoryCount<3){
      cmLesFRLockSW.get(1).ccHide();
      cmLesFRWeighSW.get(1).ccHide();
      cmLesFRText.get(1).ccHide();
    }//..?
    //-- hiding ** as
    switch(MainSpecificator.ccRefer().vmASCattegoryCount){
      case 1:
        cmLesASLockSW.get(1).ccHide();
        cmLesASWeighSW.get(1).ccHide();
        cmLesASText.get(1).ccHide();
        cmLesASLockSW.get(3).ccHide();
        cmLesASWeighSW.get(3).ccHide();
        cmLesASText.get(3).ccHide();
      break;
      case 2:
        cmLesASLockSW.get(3).ccHide();
        cmLesASWeighSW.get(3).ccHide();
        cmLesASText.get(3).ccHide();
      break;
      case 3:default:break;
    }//..?
    
    //-- hiding ** rc
    if(MainSpecificator.ccRefer().vmRCCattegoryCount==0){
      int lpDummyPage=99;
      cmPlateRC.ccSetPage(lpDummyPage);
      cmRCCellLV.ccSetPage(lpDummyPage);
      cmRCTargetCB.ccSetPage(lpDummyPage);
      cmRCCellCB.ccSetPage(lpDummyPage);
      for(EcElement it:cmLesRCLockSW){it.ccSetPage(lpDummyPage);}
      for(EcElement it:cmLesRCWeighSW){it.ccSetPage(lpDummyPage);}
      for(EcShape it:cmLesRCText){it.ccSetPage(lpDummyPage);}
    }//..else?
    //-- hiding ** ad
    if(MainSpecificator.ccRefer().vmADCattegoryCount==0){
      int lpDummyPage=99;
      cmPlateAD.ccSetPage(lpDummyPage);
      cmADCellLV.ccSetPage(lpDummyPage);
      cmADTargetCB.ccSetPage(lpDummyPage);
      cmADCellCB.ccSetPage(lpDummyPage);
      for(EcElement it:cmLesADLockSW){it.ccSetPage(lpDummyPage);}
      for(EcElement it:cmLesADWeighSW){it.ccSetPage(lpDummyPage);}
      for(EcShape it:cmLesADText){it.ccSetPage(lpDummyPage);}
    }//..else?
    
  }//..!
  
  //===

  @Override public List<? extends EcShape> ccGiveShapeList(){
    ArrayList<EcShape> lpRes=new ArrayList<EcShape>();
    lpRes.addAll(Arrays.asList(
      cmPlateAD,cmPlateFR,cmPlateAG,cmPlateAS,cmPlateRC
    ));
    lpRes.addAll(cmLesAGText);
    lpRes.addAll(cmLesFRText);
    lpRes.addAll(cmLesASText);
    lpRes.addAll(cmLesRCText);
    lpRes.addAll(cmLesADText);
    return lpRes;
  }//+++

  @Override public List<? extends EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes=new ArrayList<EcElement>();
    lpRes.addAll(cmLesAGLockSW);lpRes.addAll(cmLesAGWeighSW);
    lpRes.addAll(cmLesFRLockSW);lpRes.addAll(cmLesFRWeighSW);
    lpRes.addAll(cmLesASLockSW);lpRes.addAll(cmLesASWeighSW);
    lpRes.addAll(cmLesRCLockSW);lpRes.addAll(cmLesRCWeighSW);
    lpRes.addAll(cmLesADLockSW);lpRes.addAll(cmLesADWeighSW);
    lpRes.addAll(Arrays.asList(
      cmAGCellLV,cmAGTargetCB,cmAGCellCB,
      cmFRCellLV,cmFRTargetCB,cmFRCellCB,
      cmASCellLV,cmASTargetCB,cmASCellCB,
      cmRCCellLV,cmRCTargetCB,cmRCCellCB,
      cmADCellLV,cmADTargetCB,cmADCellCB
    ));
    return lpRes;
  }//+++
  
 }//***eof
