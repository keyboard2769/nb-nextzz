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
import kosui.ppplocalui.EcPane;
import kosui.ppplocalui.EcRect;
import kosui.ppplocalui.EcShape;
import kosui.ppplocalui.EcText;
import kosui.ppplocalui.EcTextBox;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplocalui.EiGroup;
import kosui.ppputil.VcLocalConsole;
import kosui.ppputil.VcLocalCoordinator;
import kosui.ppputil.VcTranslator;
import nextzz.pppmain.MainActionManager;
import nextzz.pppmain.MainSketch;
import nextzz.pppmodel.MainPlantModel;

public final class SubOperativeGroup implements EiGroup{
  
  private static final SubOperativeGroup SELF = new SubOperativeGroup();
  public static SubOperativeGroup ccRefer(){return SELF;}//+++
  
  //===
  
  //-- motor pane
  public final EcPane cmMotorSwitchPane
    = new EcPane(VcTranslator.tr("_motor"));
  public final List<EcButton> cmDesMotorSW
    = Collections.unmodifiableList(Arrays.asList(
    //--
    new EcButton("_m00", 0x3100),new EcButton("_m01", 0x3101),
    new EcButton("_m02", 0x3102),new EcButton("_m03", 0x3103),
    new EcButton("_m04", 0x3104),new EcButton("_m05", 0x3105),
    new EcButton("_m06", 0x3106),new EcButton("_m07", 0x3107),
    //--
    new EcButton("_m08", 0x3108),new EcButton("_m09", 0x3109),
    new EcButton("_m10", 0x310A),new EcButton("_m11", 0x310B),
    new EcButton("_m12", 0x310C),new EcButton("_m13", 0x310D),
    new EcButton("_m14", 0x310E),new EcButton("_m15", 0x310F)
  ));//...
  
  //-- assistance pane
  public final EcShape cmAssistPlate = new EcShape();
  public final List<EcButton> cmDesAssistSW
    = Collections.unmodifiableList(Arrays.asList(
    //--
    new EcButton("$a00", 0x3110),new EcButton("$a01", 0x3111),
    new EcButton("$a02", 0x3112),new EcButton("$a03", 0x3113),
    new EcButton("$a04", 0x3114),new EcButton("$a05", 0x3115),
    new EcButton("$a06", 0x3116),new EcButton("$a07", 0x3117),
    //--
    new EcButton("$a08", 0x3118),new EcButton("$a09", 0x3119),
    new EcButton("$a10", 0x311A),new EcButton("$a11", 0x311B),
    new EcButton("$a12", 0x311C),new EcButton("$a13", 0x311D),
    new EcButton("$a14", 0x311E),new EcButton("$a15", 0x311F)
  ));//...
  
  //-- zero pane
  public final EcPane cmCellPane
    = new EcPane(VcTranslator.tr("_cell"));
  public final EcShape cmZeroPlate = new EcShape();
  public final EcButton cmAGZeroSW = new EcButton("_ag", 0x3209);
  public final EcButton cmFRZeroSW = new EcButton("_fr", 0x3209);
  public final EcButton cmASZeroSW = new EcButton("_as", 0x3209);
  public final EcButton cmRCZeroSW = new EcButton("_rc", 0x3209);
  public final EcButton cmApplyZeroSW = new EcButton("_zero", 0x3209);
  
  //-- mixture pane
  public final EcPane cmMixtureControlPane
    = new EcPane(VcTranslator.tr("_mixd"));
  public final EcShape cmMixtureAnnouncementPlate
    = new EcShape();
  public final EcButton cmMixerGateHoldSW = new EcButton("_mhold", 0x3302);
  public final EcButton cmMixerGateAutoSW = new EcButton("_auto", 0x3301);
  public final EcButton cmMixerGateOpenSW = new EcButton("_mopen", 0x3303);
  public final EcButton cmBellSW = new EcButton("_bell", 0x3304);
  public final EcButton cmSirenSW = new EcButton("_siren", 0x3305);
  
  //-- booking pane
  public final EcPane cmBookingPane
    = new EcPane(VcTranslator.tr("_book"));
  public final EcText cmRecipeText = new EcText(VcTranslator.tr("_recipe"));
  public final EcText cmNameText = new EcText(VcTranslator.tr("_name"));
  public final EcText cmKilogramText = new EcText("kg");
  public final EcText cmBatchText = new EcText(VcTranslator.tr("_batc"));
  public final EcText cmWeighSkipText = new EcText(VcTranslator.tr("_wskip"));
  public final EcShape cmBookingPlate = new EcShape();
  public final EcElement cmWeighReadyPL = new EcElement("_ready");
  public final EcButton cmWeighStartSW = new EcButton("_start", 0x34A1);
  public final EcButton cmWeighStopSW = new EcButton("_stop", 0x34A2);
  public final EcButton cmWeighCancelSW = new EcButton("_cancel", 0x34A3);
  public final EcButton cmAGSkipSW = new EcButton("_ik_ag", 0x34B1);
  public final EcButton cmFRSkipSW = new EcButton("_ik_fr", 0x34B2);
  public final EcButton cmASSkipSW = new EcButton("_ik_as", 0x34B3);
  public final EcButton cmRCSkipSW = new EcButton("_ik_rc", 0x34B4);
  public final EcButton cmADSkipSW = new EcButton("_ik_ad", 0x34B5);
  
  public final List<EcValueBox> cmDesRecipeTB
    = Collections.unmodifiableList(Arrays.asList(
      new EcValueBox("_recipe", "000  ",0x3400),
      new EcValueBox("_recipe", "000  ",0x3401),
      new EcValueBox("_recipe", "000  ",0x3402),
      new EcValueBox("_recipe", "000  ",0x3403),
      new EcValueBox("_recipe", "000  ",0x3404)
  ));//...
  public final List<EcTextBox> cmDesNameCB
    = Collections.unmodifiableList(Arrays.asList(
      new EcTextBox("_name", "--Z"),
      new EcTextBox("_name", "--I"),
      new EcTextBox("_name", "--II"),
      new EcTextBox("_name", "--III"),
      new EcTextBox("_name", "--IV")
  ));//...
  public final List<EcValueBox> cmDesKilogramTB
    = Collections.unmodifiableList(Arrays.asList(
      new EcValueBox("_per", "0000 kg",0x3410),
      new EcValueBox("_per", "0000 kg",0x3411),
      new EcValueBox("_per", "0000 kg",0x3412),
      new EcValueBox("_per", "0000 kg",0x3413),
      new EcValueBox("_per", "0000 kg",0x3414)
  ));//...
  public final List<EcValueBox> cmDesBatchTB
    = Collections.unmodifiableList(Arrays.asList(
      new EcValueBox("_batch", "0000 b",0x3420),
      new EcValueBox("_batch", "0000 b",0x3421),
      new EcValueBox("_batch", "0000 b",0x3422),
      new EcValueBox("_batch", "0000 b",0x3423),
      new EcValueBox("_batch", "0000 b",0x3424)
  ));//...
  
  //===
  
  private SubOperativeGroup(){
    
    final int lpPlateColor = EcConst.ccAdjustColor(EcConst.C_BLACK, 23);
    
    int lpPotentialX;
    int lpPotentialY;
    int lpPotentialW;
    int lpPotentialH;
    
    //-- motor pane
    //-- motor pane ** allocate
    lpPotentialW=ConstLocalUI.C_MOTOR_SW_SIZE*5
      +ConstLocalUI.C_INPANE_GAP*4
      +ConstLocalUI.C_INPANE_MARGIN_S*2;
    lpPotentialH=ConstLocalUI.C_MOTOR_SW_SIZE*3
      +ConstLocalUI.C_INPANE_MARGIN_S*2
      +ConstLocalUI.C_INPANE_MARGIN_S+ConstLocalUI.C_INPANE_MARGIN_U;
    //-- motor pane ** anchor
    cmMotorSwitchPane.ccSetLocation(
      MainSketch.ccGetPrefferedW()-lpPotentialW-ConstLocalUI.C_SIDE_MARGIN,
      MainSketch.ccGetPrefferedH()-lpPotentialH//-ConstLocalUI.C_SIDE_MARGIN
        - VcLocalConsole.ccGetInstance().ccGetBarHeight()
    );
    cmDesMotorSW.get(1).ccSetLocation(cmMotorSwitchPane,
      ConstLocalUI.C_INPANE_MARGIN_S,
      ConstLocalUI.C_INPANE_MARGIN_U
    );
    cmDesMotorSW.get(1).ccSetSize(
      ConstLocalUI.C_MOTOR_SW_SIZE,
      ConstLocalUI.C_MOTOR_SW_SIZE
    );
    /* 6 */cmDesMotorSW.get(0).ccHide();//..intended disposed
    //-- motor pane **  relate
    cmDesMotorSW.get(6).ccSetLocation
      (cmDesMotorSW.get(1), 0, ConstLocalUI.C_INPANE_GAP);
    cmDesMotorSW.get(6).ccSetSize(cmDesMotorSW.get(1));
    cmDesMotorSW.get(11).ccSetLocation
      (cmDesMotorSW.get(6), 0, ConstLocalUI.C_INPANE_GAP);
    cmDesMotorSW.get(11).ccSetSize(cmDesMotorSW.get(1));
    for(int i=0;i<=3;i++){
      cmDesMotorSW.get(i+2).ccSetLocation
        (cmDesMotorSW.get(i+1), ConstLocalUI.C_INPANE_GAP, 0);
      cmDesMotorSW.get(i+2).ccSetSize(cmDesMotorSW.get(1));
      cmDesMotorSW.get(i+7).ccSetLocation
        (cmDesMotorSW.get(i+6), ConstLocalUI.C_INPANE_GAP, 0);
      cmDesMotorSW.get(i+7).ccSetSize(cmDesMotorSW.get(1));
      cmDesMotorSW.get(i+12).ccSetLocation
        (cmDesMotorSW.get(i+11), ConstLocalUI.C_INPANE_GAP, 0);
      cmDesMotorSW.get(i+12).ccSetSize(cmDesMotorSW.get(1));
    }//..~
    //-- motor pane **  pack
    cmMotorSwitchPane.ccSetEndPoint(
      cmDesMotorSW.get(15).ccEndX()+ConstLocalUI.C_INPANE_MARGIN_S,
      cmDesMotorSW.get(15).ccEndY()+ConstLocalUI.C_INPANE_MARGIN_S
    );//..~
    
    //-- assistance
    //-- assistance ** anchor
    cmAssistPlate.ccSetX(ConstLocalUI.C_SIDE_MARGIN);
    cmAssistPlate.ccSetY(
      cmMotorSwitchPane.ccGetY()
     -ConstLocalUI.C_ASSIST_SW_H
     -ConstLocalUI.C_SIDE_MARGIN*2
    );
    cmAssistPlate.ccSetBaseColor(
      EcConst.ccAdjustColor(MainSketch.C_COLOR_BACKGROUD, -16)
    );
    //-- assistance
    cmDesAssistSW.get(1).ccSetLocation
      (cmAssistPlate, ConstLocalUI.C_INPANE_GAP, ConstLocalUI.C_INPANE_GAP);
    cmDesAssistSW.get(1).ccSetSize
      (ConstLocalUI.C_ASSIST_SW_W,ConstLocalUI.C_ASSIST_SW_H);
    for(int i=2;i<=8;i++){
      cmDesAssistSW.get(i).ccSetSize(cmDesAssistSW.get(i-1));
      cmDesAssistSW.get(i).ccSetLocation
        (cmDesAssistSW.get(i-1), ConstLocalUI.C_INPANE_GAP, 0);
    }//..~
    for(int it:new int[]{0,9,10,11,12,13,14,15}){
      cmDesAssistSW.get(it).ccHide();//..intended disposed
    }//..~
    //-- assistance ** pack
    cmAssistPlate.ccSetW
      (MainSketch.ccGetPrefferedW()-ConstLocalUI.C_SIDE_MARGIN*2);
    cmAssistPlate.ccSetH
      (ConstLocalUI.C_ASSIST_SW_H+ConstLocalUI.C_INPANE_GAP*2);
    
    //-- cell ** pane
    lpPotentialX=ConstLocalUI.C_SIDE_MARGIN;
    lpPotentialY=cmMotorSwitchPane.ccGetY();
    lpPotentialW=160;//[todo]::.. make this a constant value
    lpPotentialH=cmMotorSwitchPane.ccGetH();
    cmCellPane.ccSetLocation(lpPotentialX, lpPotentialY);
    cmCellPane.ccSetSize(lpPotentialW, lpPotentialH);
    //-- cell ** zero plate
    cmZeroPlate.ccSetBaseColor(lpPlateColor);
    cmZeroPlate.ccSetLocation(
      cmCellPane.ccGetX()+ConstLocalUI.C_INPANE_MARGIN_S,
      cmCellPane.ccCenterY()
    );
    cmZeroPlate.ccSetEndPoint(
      cmCellPane.ccEndX()-ConstLocalUI.C_INPANE_MARGIN_S,
      cmCellPane.ccEndY()-ConstLocalUI.C_INPANE_MARGIN_S
    );
    //-- zero ** la switch
    cmApplyZeroSW.ccSetX(cmZeroPlate.ccGetX()+ConstLocalUI.C_INPANE_GAP);
    cmApplyZeroSW.ccSetH(ConstLocalUI.C_DEFAULT_SINGLELINE_H);
    cmApplyZeroSW.ccSetW(cmZeroPlate.ccGetW()-ConstLocalUI.C_INPANE_GAP*2);
    cmApplyZeroSW.ccSetY(
      cmZeroPlate.ccEndY()
      -cmApplyZeroSW.ccGetH()-ConstLocalUI.C_INPANE_GAP
    );
    //-- zero ** des switch
    final int lpCellElementWrap=3;
    lpPotentialW
      = (cmZeroPlate.ccGetW()-ConstLocalUI.C_INPANE_GAP*(lpCellElementWrap+1))
        / lpCellElementWrap;
    lpPotentialH=ConstLocalUI.C_DEFAULT_SINGLELINE_H;
    cmAGZeroSW.ccSetSize(
      lpPotentialW,lpPotentialH
    );
    cmFRZeroSW.ccSetSize(cmAGZeroSW);
    cmASZeroSW.ccSetSize(cmAGZeroSW);
    cmRCZeroSW.ccSetSize(cmAGZeroSW);
    //[todo]::.. AD!!
    cmAGZeroSW.ccSetLocation
      (cmZeroPlate,ConstLocalUI.C_INPANE_GAP, ConstLocalUI.C_INPANE_GAP);
    cmFRZeroSW.ccSetLocation(cmAGZeroSW, ConstLocalUI.C_INPANE_GAP, 0);
    cmASZeroSW.ccSetLocation(cmFRZeroSW, ConstLocalUI.C_INPANE_GAP, 0);
    cmRCZeroSW.ccSetLocation(cmAGZeroSW, 0, ConstLocalUI.C_INPANE_GAP);
    //[todo]::.. AD!!
    //-- zero ** hide optional
    cmRCZeroSW.ccHide();//..optionally disposed
    
    //-- mixture
    lpPotentialW = 70;//[later]::.. move to constant holder 
    lpPotentialH = ConstLocalUI.C_DEFAULT_SINGLELINE_H;
    //-- mixture ** anchor
    cmMixtureControlPane.ccSetLocation
      (cmCellPane, ConstLocalUI.C_SIDE_MARGIN, 0);
    cmMixtureControlPane.ccSetH(cmMotorSwitchPane.ccGetH());
    //-- mixture ** resize
    cmMixerGateHoldSW.ccSetSize(lpPotentialW,lpPotentialH);
    cmMixerGateAutoSW.ccSetSize(cmMixerGateHoldSW);
    cmMixerGateOpenSW.ccSetSize(cmMixerGateAutoSW);
    //-- mixture ** reallocate
    cmMixerGateHoldSW.ccSetLocation(cmMixtureControlPane,
      ConstLocalUI.C_INPANE_MARGIN_S,
      ConstLocalUI.C_INPANE_MARGIN_U
    );
    cmMixerGateAutoSW.ccSetLocation
      (cmMixerGateHoldSW, 0, ConstLocalUI.C_INPANE_GAP);
    cmMixerGateOpenSW.ccSetLocation
      (cmMixerGateAutoSW, 0, ConstLocalUI.C_INPANE_GAP);
    //-- mixture ** pack
    cmMixtureControlPane.ccSetEndPoint(cmMixerGateOpenSW, 5, 65536);
    //-- mixture ** announcement ** plate
    cmMixtureAnnouncementPlate.ccSetBaseColor(lpPlateColor);
    cmMixtureAnnouncementPlate.ccSetW(
      cmMixtureControlPane.ccGetW()-ConstLocalUI.C_INPANE_MARGIN_S*2
    );
    cmMixtureAnnouncementPlate.ccSetH(
      ConstLocalUI.C_DEFAULT_SINGLELINE_H*2
       + ConstLocalUI.C_INPANE_GAP*3
    );
    cmMixtureAnnouncementPlate.ccSetLocation(
      cmMixtureControlPane.ccGetX()+ConstLocalUI.C_INPANE_MARGIN_S,
      cmMixtureControlPane.ccEndY()-ConstLocalUI.C_INPANE_MARGIN_S
       - cmMixtureAnnouncementPlate.ccGetH()
    );
    //-- mixture ** announcement ** switch
    cmBellSW.ccSetSize(
      cmMixtureAnnouncementPlate.ccGetW()-(ConstLocalUI.C_INPANE_GAP*2),
      ConstLocalUI.C_DEFAULT_SINGLELINE_H
    );
    cmSirenSW.ccSetSize(cmBellSW);
    cmBellSW.ccSetLocation(cmMixtureAnnouncementPlate,
      ConstLocalUI.C_INPANE_GAP, ConstLocalUI.C_INPANE_GAP
    );
    cmSirenSW.ccSetLocation(cmBellSW, 0, ConstLocalUI.C_INPANE_GAP);
    
    //-- booking
    lpPotentialH=16;//..arbitrary
    //-- booking ** pack
    cmBookingPane.ccSetLocation
      (cmMixtureControlPane, ConstLocalUI.C_SIDE_MARGIN, 0);
    cmBookingPane.ccSetEndPoint(
      cmMotorSwitchPane.ccGetX()-ConstLocalUI.C_SIDE_MARGIN,
      cmMotorSwitchPane.ccEndY()
    );
    //-- booking ** title
    //-- booking ** title ** shrink
    cmDesRecipeTB.get(0).ccSetH(lpPotentialH);
    cmDesNameCB.get(0).ccSetH(lpPotentialH);
    cmDesKilogramTB.get(0).ccSetH(lpPotentialH);
    cmDesBatchTB.get(0).ccSetH(lpPotentialH);
    //-- booking ** title ** color
    cmDesRecipeTB.get(0).ccSetupColor(EcConst.C_RED, EcConst.C_DARK_YELLOW);
    cmDesNameCB.get(0).ccSetupColor(EcConst.C_RED, EcConst.C_DARK_YELLOW);
    cmDesKilogramTB.get(0).ccSetupColor(EcConst.C_RED, EcConst.C_DARK_YELLOW);
    cmDesBatchTB.get(0).ccSetupColor(EcConst.C_RED, EcConst.C_DARK_YELLOW);
    //-- booking ** title ** span size
    cmDesNameCB.get(0).ccSetW(
      cmBookingPane.ccGetW()
     -ConstLocalUI.C_INPANE_GAP*3-ConstLocalUI.C_INPANE_MARGIN_S*2
     -cmDesRecipeTB.get(0).ccGetW()
     -cmDesKilogramTB.get(0).ccGetW()
     -cmDesBatchTB.get(0).ccGetW()
    );
    //-- booking ** title ** allocate
    cmDesRecipeTB.get(0).ccSetLocation(cmBookingPane,
      ConstLocalUI.C_INPANE_MARGIN_S,
      ConstLocalUI.C_INPANE_MARGIN_U+lpPotentialH
    );
    cmDesNameCB.get(0).ccSetLocation
      (cmDesRecipeTB.get(0), ConstLocalUI.C_INPANE_GAP, 0);
    cmDesKilogramTB.get(0).ccSetLocation
      (cmDesNameCB.get(0), ConstLocalUI.C_INPANE_GAP, 0);
    cmDesBatchTB.get(0).ccSetLocation
      (cmDesKilogramTB.get(0), ConstLocalUI.C_INPANE_GAP, 0);
    //-- booking ** table
    for(
      int i=MainPlantModel.C_BOOK_UI_CAPA_HEAD;
      i<=MainPlantModel.C_BOOK_UI_CAPA_TAIL;
      i++
    ){
      //--
      cmDesRecipeTB.get(i).ccSetLocation
        (cmDesRecipeTB.get(i-1), 0,ConstLocalUI.C_INPANE_GAP);
      cmDesRecipeTB.get(i).ccSetSize(cmDesRecipeTB.get(i-1));
      //--
      cmDesNameCB.get(i).ccSetLocation
        (cmDesNameCB.get(i-1), 0,ConstLocalUI.C_INPANE_GAP);
      cmDesNameCB.get(i).ccSetSize(cmDesNameCB.get(i-1));
      //--
      cmDesKilogramTB.get(i).ccSetLocation
        (cmDesKilogramTB.get(i-1), 0,ConstLocalUI.C_INPANE_GAP);
      cmDesKilogramTB.get(i).ccSetSize(cmDesKilogramTB.get(i-1));
      //--
      cmDesBatchTB.get(i).ccSetLocation
        (cmDesBatchTB.get(i-1), 0,ConstLocalUI.C_INPANE_GAP);
      cmDesBatchTB.get(i).ccSetSize(cmDesBatchTB.get(i-1));
    }//..~
    //-- booking ** title ** label
    lpPotentialY=cmBookingPane.ccGetY()
      +ConstLocalUI.C_INPANE_MARGIN_U
      +ConstLocalUI.C_INPANE_MARGIN_S;
    cmRecipeText.ccSetTextColor(EcConst.C_LIT_GRAY);
    cmNameText.ccSetTextColor(EcConst.C_LIT_GRAY);
    cmKilogramText.ccSetTextColor(EcConst.C_LIT_GRAY);
    cmBatchText.ccSetTextColor(EcConst.C_LIT_GRAY);
    cmRecipeText.ccSetLocation
      (cmDesRecipeTB.get(0).ccCenterX(), lpPotentialY);
    cmNameText.ccSetLocation
      (cmDesNameCB.get(0).ccCenterX(), lpPotentialY);
    cmKilogramText.ccSetLocation
      (cmDesKilogramTB.get(0).ccCenterX(), lpPotentialY);
    cmBatchText.ccSetLocation
      (cmDesBatchTB.get(0).ccCenterX(), lpPotentialY);
    //-- booking ** control ** plate
    lpPotentialH=22;
    cmBookingPlate.ccSetBaseColor(lpPlateColor);
    cmBookingPlate.ccSetLocation(
      cmDesRecipeTB.get(0).ccGetX(),
      cmBookingPane.ccEndY()-lpPotentialH-ConstLocalUI.C_INPANE_MARGIN_S*2
    );
    cmBookingPlate.ccSetEndPoint(cmBookingPane,
      -1*ConstLocalUI.C_INPANE_MARGIN_S,
      -1*ConstLocalUI.C_INPANE_MARGIN_S
    );
    //-- booking ** control ** sequencer
    lpPotentialW=55;
    cmWeighReadyPL.ccSetSize(lpPotentialW, lpPotentialH);
    cmWeighStartSW.ccSetSize(lpPotentialW,lpPotentialH);
    cmWeighCancelSW.ccSetSize(cmWeighReadyPL);
    cmWeighStopSW.ccSetSize(lpPotentialW,lpPotentialH);
    cmWeighReadyPL.ccSetLocation(cmBookingPlate,
      ConstLocalUI.C_INPANE_GAP,
      ConstLocalUI.C_INPANE_GAP
    );
    cmWeighStartSW.ccSetLocation(cmWeighReadyPL, ConstLocalUI.C_INPANE_GAP, 0);
    cmWeighStopSW.ccSetLocation(cmWeighStartSW, ConstLocalUI.C_INPANE_GAP, 0);
    cmWeighCancelSW.ccSetLocation(cmWeighStopSW, ConstLocalUI.C_INPANE_GAP, 0);
    //-- booking ** control ** skip
    lpPotentialH=18;
    cmAGSkipSW.ccSetSize(lpPotentialH);
    cmFRSkipSW.ccSetSize(lpPotentialH);
    cmASSkipSW.ccSetSize(lpPotentialH);
    cmRCSkipSW.ccSetSize(lpPotentialH);
    cmADSkipSW.ccSetSize(lpPotentialH);
    cmRCSkipSW.ccSetLocation(
      cmBookingPlate.ccEndX()-lpPotentialH-ConstLocalUI.C_INPANE_GAP,
      cmBookingPlate.ccCenterY()-(lpPotentialH/2)+ConstLocalUI.C_INPANE_GAP
    );
    EcRect.ccFlowLayout(
      Arrays.asList(cmRCSkipSW,cmASSkipSW,cmAGSkipSW,cmFRSkipSW,cmADSkipSW),
      2, false, true
    );
    cmWeighSkipText.ccSetTextColor(EcConst.C_LIT_GRAY);
    cmWeighSkipText.ccSetLocation(
      cmADSkipSW.ccGetX()-lpPotentialH,
      cmBookingPlate.ccCenterY()
    );
    
    //-- inputtable ** register
    for(int it:new int[]{1,2,3,4}){
      VcLocalCoordinator.ccAddInputtable(cmDesRecipeTB.get(it));
      VcLocalCoordinator.ccAddInputtable(cmDesKilogramTB.get(it));
      VcLocalCoordinator.ccAddInputtable(cmDesBatchTB.get(it));
      VcLocalCoordinator.ccRegisterMouseTrigger(cmDesRecipeTB.get(it),
        MainActionManager.ccRefer().cmInputtableClicking);
      VcLocalCoordinator.ccRegisterMouseTrigger(cmDesKilogramTB.get(it),
        MainActionManager.ccRefer().cmInputtableClicking);
      VcLocalCoordinator.ccRegisterMouseTrigger(cmDesBatchTB.get(it),
        MainActionManager.ccRefer().cmInputtableClicking);
    }//..~
    
  }//..!
  
  @Override public List<? extends EcShape> ccGiveShapeList(){
    return Arrays.asList(cmMotorSwitchPane,cmAssistPlate,
      cmCellPane,cmZeroPlate,
      cmMixtureControlPane,cmMixtureAnnouncementPlate,
      cmBookingPane,cmBookingPlate,
      cmRecipeText,cmNameText,cmKilogramText,cmBatchText,
      cmWeighSkipText
    );//...
  }//+++

  @Override public List<? extends EcElement> ccGiveElementList(){
    ArrayList<EcElement> lpRes = new ArrayList<EcElement>();
    lpRes.addAll(cmDesMotorSW);
    lpRes.addAll(cmDesAssistSW);
    lpRes.addAll(cmDesRecipeTB);
    lpRes.addAll(cmDesNameCB);
    lpRes.addAll(cmDesKilogramTB);
    lpRes.addAll(cmDesBatchTB);
    lpRes.addAll(Arrays.asList(cmAGZeroSW,cmFRZeroSW,cmASZeroSW,cmRCZeroSW,
      cmApplyZeroSW,
      cmMixerGateHoldSW,cmMixerGateAutoSW,cmMixerGateOpenSW,
      cmBellSW,cmSirenSW,
      cmWeighReadyPL,cmWeighStartSW,cmWeighStopSW,cmWeighCancelSW,
      cmAGSkipSW,cmFRSkipSW,cmASSkipSW//[todo]rc-ad?
    ));//...
    return lpRes;
  }//+++
  
 }//***eof
