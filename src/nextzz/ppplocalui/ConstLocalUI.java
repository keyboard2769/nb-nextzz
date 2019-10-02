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

import java.util.List;
import kosui.ppplocalui.EcButton;
import kosui.ppplocalui.EcConst;
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.EcGauge;
import kosui.ppplocalui.EcLamp;
import kosui.ppplocalui.EcRect;
import kosui.ppplocalui.EcShape;
import kosui.ppplocalui.EcText;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplocalui.EiGroup;
import kosui.ppputil.VcNumericUtility;
import nextzz.pppmain.MainSketch;
import processing.core.PApplet;
import processing.core.PGraphics;

public final class ConstLocalUI {
  
  //===
  
  //-- operative ui ** pix ** single
  public static final int C_DEFAULT_SINGLELINE_H = 20;
  public static final int C_MOTOR_SW_SIZE = 48;
  public static final int C_ASSIST_SW_W = 80;//[todo]::re impl this with cfg
  public static final int C_ASSIST_SW_H = 20;
  
  //-- operative ui ** pix ** margin
  public static final int C_SIDE_MARGIN = 5;
  public static final int C_INPANE_MARGIN_S = 5;
  public static final int C_INPANE_MARGIN_U = 22;
  public static final int C_INPANE_GAP = 2;
  
  //-- indicative ui ** pix
  public static final int C_SHAPE_DRYER_ROLLER_W = 4;
  
  //-- indicative ui ** color
  public static final int C_COLOR_POWERDEVICE_OFF
    = 0xFF797979;
  public static final int C_COLOR_POWERDEVICE_ON
    = 0xFFCECE32;
  public static final int C_COLOR_CONTAINER
    = 0xFFA3A3A3;
  public static final int C_COLOR_DUCT
    = 0xFF888888;
  
  //===
  
  //.. for passive issue we have to hand code graphic size here
  
  public static final PGraphics O_FEEDER
    = EcFactory.ccCreatePGraphics(56, 43);
  
  public static final PGraphics O_BIN_CAN_TANK
    = EcFactory.ccCreatePGraphics(20,30);
  
  public static final PGraphics O_HOT_BIN
    = EcFactory.ccCreatePGraphics(142, 40);
  
  public static final PGraphics O_MIXER_ON
    = EcFactory.ccCreatePGraphics(80,60);
  
  public static final PGraphics O_MIXER_OFF
    = EcFactory.ccCreatePGraphics(80,60);
  
  public static final PGraphics O_V_DRYER
    = EcFactory.ccCreatePGraphics(120, 48);
  
  public static final PGraphics O_BAGFILTER
    = EcFactory.ccCreatePGraphics(100, 48);
  
  public static final PGraphics O_FIRST_DUCT
    = EcFactory.ccCreatePGraphics(24, 76);
  
  public static final PGraphics O_SECOND_DUCT
    = EcFactory.ccCreatePGraphics(24, 42);
  
  public static final PGraphics O_V_BURNER_OFF
    = EcFactory.ccCreatePGraphics(60, 40);
  
  public static final PGraphics O_V_BURNER_ON
    = EcFactory.ccCreatePGraphics(60, 40);
  
  public static final PGraphics O_V_EXFAN_OFF
    = EcFactory.ccCreatePGraphics(24, 40);
  
  public static final PGraphics O_V_EXFAN_ON
    = EcFactory.ccCreatePGraphics(24, 40);
  
  //===
  
  private ConstLocalUI(){}//..!
  
  //=== draw
  
  public static final
  void ccDrawDuctShape(
    PGraphics pxTarget, char pxMode_abcdx,
    int pxCut, int pxThick, int pxColor
  ){
    if(pxTarget==null){return;}
    int lpW=pxTarget.width;
    int lpH=pxTarget.height;
    int lpCut=pxCut>=0?pxCut:0;
    int lpDuctThick=pxThick>=1?pxThick:4;
    pxTarget.beginDraw();
    pxTarget.fill(EcConst.C_TRANSPARENT);
    pxTarget.noStroke();
    {
      pxTarget.fill(pxColor);
      switch(pxMode_abcdx){
        //-- point left top 
        case 'a':
          pxTarget.rect(lpCut, 0, lpW-lpCut, lpDuctThick);
          pxTarget.rect(lpCut, 0, lpDuctThick, lpH);
          if(lpCut>0){pxTarget.rect(0,lpH,
            lpCut+lpDuctThick,lpDuctThick
          );}
        break;
        //-- point right top
        case 'b':
          pxTarget.rect(0, 0, lpW-lpCut, lpDuctThick);
          pxTarget.rect(lpW-lpCut, 0, lpDuctThick, lpH);
          if(lpCut>0){pxTarget.rect(lpW-lpCut,lpH-lpDuctThick,
            lpCut,lpDuctThick
          );}
        break;
        //-- point right bottom
        case 'c':
          pxTarget.rect(lpW-lpDuctThick, 0, lpDuctThick, lpH-lpCut);
          pxTarget.rect(0, lpH-lpCut-lpDuctThick, lpW, lpDuctThick);
          if(lpCut>0){pxTarget.rect(0, lpH-lpCut,
            lpDuctThick, lpCut
          );}
        break;
        //-- point left bottom
        case 'd':
          pxTarget.rect(0, 0, lpDuctThick, lpH-lpCut);
          pxTarget.rect(0, lpH-lpCut, lpW, lpDuctThick);
          if(lpCut>0){pxTarget.rect(lpW-lpDuctThick, lpH-lpCut,
            lpDuctThick, lpCut
          );}
        break;
        //--
        default:
        break;
      }//..?
    }
    pxTarget.endDraw();
  }//+++
  
  public static final
  void ccDrawBlowerShape(PGraphics pxTarget, char pxMode_udlr, int pxColor){
    if(pxTarget==null){return;}
    int lpW=pxTarget.width;
    int lpH=pxTarget.height;
    int lpHalfW=lpW/2;
    int lpHalfH=lpH/2;
    pxTarget.beginDraw();
    pxTarget.fill(EcConst.C_TRANSPARENT);
    pxTarget.noStroke();
    pxTarget.ellipseMode(PApplet.CENTER);
    {
      pxTarget.fill(pxColor);
      switch(pxMode_udlr){
        case 'u':
          pxTarget.ellipse(lpHalfW, lpH-lpHalfW, lpW, lpW);
          pxTarget.rect(0, 0, lpHalfW, lpH-lpHalfW);
        break;
        //--
        case 'd':
          pxTarget.ellipse(lpHalfW,lpHalfW,lpW,lpW);
          pxTarget.rect(lpHalfW, lpHalfW, lpHalfW, lpH-lpHalfW);
        break;
        //--
        case 'l':
          pxTarget.ellipse(lpW-lpHalfH,lpHalfH,lpH,lpH);
          pxTarget.rect(0,0,lpW-lpHalfH,lpHalfH);
        break;
        //--
        case 'r':
          pxTarget.ellipse(lpHalfH,lpHalfH,lpH,lpH);
          pxTarget.rect(lpHalfH,0,lpW-lpHalfH,lpHalfH);
        break;
        //--
        default:break;
        //--
      }//..?
    }
    pxTarget.endDraw();
  }//+++
  
  public static final
  void ccDrawHopperShape(PGraphics pxTarget, int pxCut, int pxColor){
    if(pxTarget==null){return;}
    int lpW=pxTarget.width;
    int lpH=pxTarget.height;
    int lpCut=pxCut>=0?pxCut:((lpW/2)-1);
    int lpHoldLength=lpH-lpCut;
    pxTarget.beginDraw();
    pxTarget.fill(EcConst.C_TRANSPARENT);
    pxTarget.noStroke();
    {
      pxTarget.fill(pxColor);
      pxTarget.rect(0, 0, lpW, lpH-lpCut);
      pxTarget.quad(0, lpHoldLength,
        lpW, lpHoldLength,
        lpW-lpCut, lpH,
        lpCut, lpH
      );
    }
    pxTarget.endDraw();
  }//+++
  
  public static final
  void ccDrawMixerShape(PGraphics pxTarget, int pxCut, int pxColor){
    if(pxTarget==null){return;}
    ccDrawHopperShape(pxTarget, pxCut, pxColor);
    pxTarget.beginDraw();
    pxTarget.ellipseMode(PApplet.CENTER);
    pxTarget.clip(0, 0, pxTarget.width, pxTarget.height);
    pxTarget.fill(MainSketch.C_COLOR_BACKGROUD);
    pxTarget.ellipse(pxTarget.width/2,pxTarget.height,pxCut*2,pxCut*2);
    pxTarget.noClip();
    pxTarget.endDraw();
  }//+++
  
  public static final
  void ccDrawDryerShape(PGraphics pxTarget, int pxCut, int pxColor){
    if(pxTarget==null){return;}
    int lpW=pxTarget.width;
    int lpH=pxTarget.height;
    int lpBarrelD=lpH-pxCut*2;
    int lpRollerA=lpW*1/4;
    int lpRollerC=lpW*3/4;
    pxTarget.beginDraw();
    pxTarget.fill(EcConst.C_TRANSPARENT);
    pxTarget.noStroke();
    {
      pxTarget.fill(pxColor);
      pxTarget.rect(0, pxCut, lpW, lpBarrelD);
      pxTarget.rect(lpRollerA,0,C_SHAPE_DRYER_ROLLER_W,lpH);
      pxTarget.rect(lpRollerC,0,C_SHAPE_DRYER_ROLLER_W,lpH);
    }
    pxTarget.endDraw();
  }//+++
  
  //=== assemble
  
  public static final
  void ccAssembleWeighControl(
    EcRect pxPlate,
    List<EcButton> pxDesLocker,List<EcButton> pxDesWeigher,
    List<EcText>pxDesLabel,
    int pxCategoryCount
  ){
    
    //-- checkin ** valid check
    if(pxPlate==null){return;}
    if(pxDesLocker==null){return;}
    if(pxDesWeigher==null){return;}
    if(pxDesLabel==null){return;}
    
    //-- checkin ** size check
    if(pxCategoryCount<1 || pxCategoryCount>7){return;}
    final int lpSize=pxCategoryCount+1;
    if(pxDesLocker.size()<lpSize){return;}
    if(pxDesWeigher.size()<lpSize){return;}
    if(pxDesLabel.size()<lpSize){return;}
    if(!VcNumericUtility.ccEquals(
      pxDesLocker.size(), 
      pxDesWeigher.size(), 
      pxDesLabel.size()
    )){return;}
    
    //-- restyling
    final int lpButtonScale=18;
    for(EcButton it : pxDesLocker){it.ccSetSize(lpButtonScale);}
    for(EcButton it : pxDesWeigher){it.ccSetSize(lpButtonScale);}
    for(EcText it : pxDesLabel){it.ccSetTextColor(EcConst.C_LIT_GRAY);}
    
    //-- relocating ** anchor
    int lpPotentialG=(pxPlate.ccGetW()-lpButtonScale*pxCategoryCount)/lpSize;
    pxDesLocker.get(pxCategoryCount).ccSetLocation
      (pxPlate, lpPotentialG, lpButtonScale+ConstLocalUI.C_INPANE_GAP*2);
    pxDesWeigher.get(pxCategoryCount).ccSetLocation
      (pxDesLocker.get(pxCategoryCount),'b');
    
    //-- relocating ** follower
    for(int i=pxCategoryCount-1;i>=1;i--){
      pxDesLocker.get(i).ccSetLocation
        (pxDesLocker.get(i+1), lpPotentialG,0);
      pxDesWeigher.get(i).ccSetLocation
        (pxDesLocker.get(i),'b');
    }//..~
    for(int i=pxCategoryCount;i>=1;i--){
      pxDesLabel.get(i).ccSetLocation(
        pxDesLocker.get(i).ccCenterX(),
        pxPlate.ccGetY()+ConstLocalUI.C_INPANE_GAP+lpButtonScale/2
      );
    }//..~
    
  }//+++
  
  public static final void ccAssenbleCell(
    EcGauge pxGauge, EcValueBox pxTarget, EcValueBox pxCell,
    EcButton pxLocker,EcButton pxDischarger,
    int pxX, int pxY
  ){
    
    //-- checkin
    if(pxGauge==null){return;}
    if(pxTarget==null){return;}
    if(pxCell==null){return;}
    if(pxDischarger==null){return;}
    
    //-- resizing
    //   .. i dont know how to make this thing clean 
    final int lpGaugeWidth=4;
    pxGauge.ccSetW(lpGaugeWidth);
    pxGauge.ccSetH(pxTarget.ccGetH()+pxCell.ccGetH()-1);
    pxDischarger.ccSetW(pxGauge.ccGetW()+pxCell.ccGetW());
    pxLocker.ccSetW(pxDischarger.ccGetW());
    pxLocker.ccSetH(C_DEFAULT_SINGLELINE_H*2/3);
    pxDischarger.ccSetH(C_DEFAULT_SINGLELINE_H);
    
    //-- restyling
    ccSetupClickableBoxColor(pxTarget);
    ccSetupPressureBoxColor(pxCell);
    
    //-- assemble
    int lpX=pxX&0x7FF;
    int lpY=pxY&0x7FF;
    pxLocker.ccSetLocation(lpX, lpY);
    pxGauge.ccSetLocation(pxLocker,'b');
    pxTarget.ccSetLocation(
      pxGauge.ccEndX(),
      pxGauge.ccGetY()
    );
    pxCell.ccSetLocation(
      pxTarget.ccGetX(),
      pxTarget.ccEndY()
    );
    pxDischarger.ccSetLocation(
      pxGauge.ccGetX(), 
      pxCell.ccEndY()
    );
  
  }//+++
  
  public static final void ccAssembleBelcon(
    EcShape pxBody, EcLamp pxPullyL, EcLamp pxPullyR,
    int pxX, int pxY, int pxW, int pxH
  ){
    
    //-- checkin
    if(pxBody==null){return;}
    if(pxPullyL==null){return;}
    if(pxPullyR==null){return;}
    
    //-- styling
    pxBody.ccSetBaseColor(C_COLOR_POWERDEVICE_OFF);
    pxPullyL.ccSetupColor(C_COLOR_POWERDEVICE_ON, C_COLOR_POWERDEVICE_OFF);
    pxPullyR.ccSetupColor(C_COLOR_POWERDEVICE_ON, C_COLOR_POWERDEVICE_OFF);
    
    //-- sizing
    int lpW=pxW>18?pxW:18;
    int lpH=pxH>18?pxH:18;
    pxBody.ccSetSize(lpW, lpH);
    pxPullyL.ccSetSize(lpH,lpH);
    pxPullyR.ccSetSize(lpH,lpH);
    
    //-- locating
    int lpX=pxX&0x7FF;
    int lpY=pxY&0x7FF;
    pxBody.ccSetLocation(lpX, lpY);
    pxPullyL.ccSetLocation(lpX-lpH/2,lpY);
    pxPullyR.ccSetLocation(pxBody.ccEndX()-lpH/2,lpY);
    
  }//+++
  
  public static final void ccAssenmbleContentBin(
    EcShape pxBody, EcGauge pxLevelor, EcText pxLabel,
    int pxX, int pxY, char pxTextAlign_aix
  ){
    
    //-- checkin
    if(pxBody==null){return;}
    if(pxLevelor==null){return;}
    if(pxLabel==null){return;}
    
    //-- styling
    pxLevelor.ccSetSize(pxBody.ccGetW()/4, pxBody.ccGetH()*2/3);
    pxLevelor.ccSetHasStroke(true);
    pxLevelor.ccSetGaugeColor(EcConst.C_DARK_GRAY, EcConst.C_WHITE);
    pxLevelor.ccSetupColor(EcConst.C_LIT_RED, EcConst.C_LIT_YELLOW);
    pxLabel.ccSetTextColor(EcConst.C_LIT_GRAY);
    
    //-- locating
    int lpX=pxX&0x7FF;
    int lpY=pxY&0x7FF;
    pxBody.ccSetLocation(lpX, lpY);
    pxLevelor.ccSetLocation(pxBody, 2, 2);
    switch(pxTextAlign_aix){
      
      case 'a':
        pxLabel.ccSetLocation(
          pxBody.ccCenterX(), 
          pxBody.ccGetY()-EcConst.C_DEFAULT_TEXT_HEIGHT/2
        );
      break;
      
      case 'c':
        pxLabel.ccSetLocation(
          pxBody.ccGetX()+pxBody.ccGetW()*3/4, 
          pxBody.ccGetY()+EcConst.C_DEFAULT_TEXT_HEIGHT/2
        );
      break;
      
      default:
        pxLabel.ccSetIsVisible(false);
        pxLabel.ccSetIsEnabled(false);
      break;
      
    }//..?
  
  }//+++
  
  //=== setup
  
  public static final void ccSetupFluxBoxColor(EcValueBox pxBox){
    if(pxBox==null){return;}
    pxBox.ccSetupColor(EcConst.C_YELLOW, EcConst.C_DARK_GRAY);
    pxBox.ccSetTextColor(EcConst.C_DIM_GRAY);
  }//+++
  
  public static final void ccSetupTemperatureBoxColor(EcValueBox pxBox){
    if(pxBox==null){return;}
    pxBox.ccSetupColor(EcConst.C_ORANGE, EcConst.C_DARK_RED);
    pxBox.ccSetTextColor(EcConst.C_LIT_GRAY);
  }//+++
  
  public static final void ccSetupPressureBoxColor(EcValueBox pxBox){
    if(pxBox==null){return;}
    pxBox.ccSetupColor(EcConst.C_ORANGE, EcConst.C_DARK_WATER);
    pxBox.ccSetTextColor(EcConst.C_LIT_GRAY);
  }//+++
  
  public static final void ccSetupDegreeBoxColor(EcValueBox pxBox){
    if(pxBox==null){return;}
    pxBox.ccSetupColor(EcConst.C_ORANGE, EcConst.C_BLACK);
    pxBox.ccSetTextColor(EcConst.C_LIT_GRAY);
  }//+++
  
  public static final void ccSetupClickableBoxColor(EcValueBox pxBox){
    if(pxBox==null){return;}
    pxBox.ccSetupColor(EcConst.C_DARK_YELLOW, EcConst.C_DARK_GREEN);
    pxBox.ccSetTextColor(EcConst.C_LIT_GRAY);
  }//+++
  
  //=== interval
  
  public static final
  int ccTellFlowInterval(int pxGap, int pxLength, int pxOrder){
    return pxGap+(pxLength+pxGap)*pxOrder;
  }//+++
  
  //===
  
  public static final void ccInit(){
    
    //-- feeder
    ccDrawHopperShape(O_FEEDER, 14, C_COLOR_CONTAINER);
    
    //-- v surge
    ccDrawHopperShape(O_BIN_CAN_TANK, 6, C_COLOR_CONTAINER);
    ccDrawHopperShape(O_HOT_BIN, 8, C_COLOR_CONTAINER);
    
    //-- mixer
    ccDrawMixerShape(O_MIXER_ON, 12, C_COLOR_POWERDEVICE_ON);
    ccDrawMixerShape(O_MIXER_OFF, 12, C_COLOR_POWERDEVICE_OFF);
    
    //-- bond
    ccDrawDuctShape(O_FIRST_DUCT, 'b', 4, 4, C_COLOR_DUCT);
    ccDrawDuctShape(O_SECOND_DUCT, 'c', 4, 4, C_COLOR_DUCT);
    ccDrawDryerShape(O_V_DRYER, 3, C_COLOR_POWERDEVICE_OFF);
    ccDrawHopperShape(O_BAGFILTER, 20, C_COLOR_CONTAINER);
    ccDrawBlowerShape(O_V_BURNER_OFF, 'r', C_COLOR_POWERDEVICE_OFF);
    ccDrawBlowerShape(O_V_BURNER_ON, 'r', C_COLOR_POWERDEVICE_ON);
    ccDrawBlowerShape(O_V_EXFAN_OFF, 'u', C_COLOR_POWERDEVICE_OFF);
    ccDrawBlowerShape(O_V_EXFAN_ON, 'u', C_COLOR_POWERDEVICE_ON);
    
  }//..!
  
  public static final
  int ccLeverage(boolean pxHI, boolean pxMID, boolean pxLOW){
    if(pxHI&&pxMID&&pxLOW){return 254;}else
    if(!pxHI&&pxMID&&pxLOW){return 126;}else
    if(!pxHI&&!pxMID&&pxLOW){return 62;}else
    if(!pxHI&&!pxMID&&!pxLOW){return 0;}else
    {return 15;}
  }//+++
  
  public static final
  void ccRepage(EiGroup pxGroup, int pxPage){
    if(pxGroup==null){return;}
    for(EcShape it:pxGroup.ccGiveShapeList()){it.ccSetPage(pxPage);}
    for(EcElement it:pxGroup.ccGiveElementList()){it.ccSetPage(pxPage);}
  }//+++
  
 }//***eof
