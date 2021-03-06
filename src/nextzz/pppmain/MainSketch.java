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

package nextzz.pppmain;

import java.awt.Frame;
import java.awt.Rectangle;
import java.io.File;
import javax.swing.SwingUtilities;
import kosui.ppplocalui.EcConst;
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.EcPoint;
import kosui.ppplocalui.EcRect;
import kosui.pppswingui.ScConst;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcLocalConsole;
import kosui.ppputil.VcLocalCoordinator;
import kosui.ppputil.VcLocalTagger;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcTranslator;
import nextzz.pppdelegate.MainDelegator;
import nextzz.ppplocalui.ConstLocalUI;
import nextzz.ppplocalui.SubIndicativeGroup;
import nextzz.ppplocalui.SubMixerGroup;
import nextzz.ppplocalui.SubVSurgeGroup;
import nextzz.ppplocalui.SubOperativeGroup;
import nextzz.ppplocalui.SubVBondGroup;
import nextzz.ppplocalui.SubVFeederGroup;
import nextzz.ppplocalui.SubWeigherGroup;
import nextzz.pppmodel.MainFileManager;
import nextzz.pppmodel.MainPlantModel;
import nextzz.pppmodel.MainSpecificator;
import nextzz.pppmodel.SubDegreeControlManager;
import nextzz.pppmodel.SubErrorListModel;
import nextzz.pppsetting.MainSettingManager;
import nextzz.pppsimulate.MainSimulator;
import nextzz.pppswingui.ConstSwingUI;
import processing.core.PApplet;
import processing.core.PFont;

public class MainSketch extends PApplet{
  
  static private MainSketch self;
  
  public static final String C_WARE_TITLE = "Next ZZ v19a01";
  
  static public final int C_COLOR_BACKGROUD = 0xFF336633;
  static public final int C_COLOR_PLATE     = 0xFF225522;
  static public final int C_WIDTH_S  =  800;
  static public final int C_HEIGHT_S =  600;
  static public final int C_WIDTH_M  = 1024;
  static public final int C_HEIGHT_M =  768;
  
  private static int pbWindowW=C_WIDTH_S;
  private static int pbWindowH=C_HEIGHT_S;
  private static int pbModifiableKeyCode=0;
  private static int pbRoller=10;
  
  public static volatile boolean pbDebugMode=false;
  
  //=== overidden
  
  @Override public void setup(){
    
    size(pbWindowW,pbWindowH,JAVA2D);
    /* 4 */VcConst.ccPrintln(".setup() $ begin");
    
    //-- pre
    EcConst.ccSetupSketch(this);
    frame.setTitle(C_WARE_TITLE);
    frame.addWindowListener(MainWindow.ccRefer().cmAppClosing);
    self=this;
    ssInitFont();
    
    //-- loadable config
    MainSpecificator.ccRefer().ccVerify();
    
    //-- library
    ConstLocalUI.ccInit();
    ConstSwingUI.ccInit();
    EcElement.ccSetTextAdjust(0, -1);
    VcLocalCoordinator.ccInit(this);
    VcLocalConsole.ccInit(this);
    /* 6 */VcLocalTagger.ccInit(this,false);
    /* 6 */VcLocalTagger.ccSetColor(0xFF111111, 0xFFEEEEEE);
    
    //-- modeling
    MainPlantModel.ccRefer().ccInit();
    MainSettingManager.ccRefer().ccLoadFromFile(null);
    SubErrorListModel.ccRefer().ccInit();
    
    //-- simulator
    MainSimulator.ccInit();
    
    //-- local ui group
    VcLocalCoordinator.ccAddGroup
      (SubIndicativeGroup.ccRefer());//.. should have no dependency
    VcLocalCoordinator.ccAddGroup
      (SubOperativeGroup.ccRefer());//.. should have no dependency
    VcLocalCoordinator.ccAddGroup
      (SubVFeederGroup.ccRefer());//.. might be THE anchor
    VcLocalCoordinator.ccAddGroup
      (SubVBondGroup.ccRefer());//.. might depends on vf group
    VcLocalCoordinator.ccAddGroup
      (SubWeigherGroup.ccRefer());//.. might depands on v-bond group & ope-g
    VcLocalCoordinator.ccAddGroup
      (SubVSurgeGroup.ccRefer());//.. might depands on weigher group
    VcLocalCoordinator.ccAddGroup
      (SubMixerGroup.ccRefer());//.. might depends on weigher group
    
    //-- swing ui group
    ScConst.ccSetOwner(this);
    SwingUtilities.invokeLater
      (MainWindow.ccRefer().cmInitiating);
    SwingUtilities.invokeLater
      (MainActionManager.ccRefer().cmSwingClickableRegiseriness);
    SwingUtilities.invokeLater
      (MainFileManager.ccRefer().cmNotchStateInitiating);
    
    //-- translation
    EcConst.ccTranslateText(SubIndicativeGroup.ccRefer());
    EcConst.ccTranslateText(SubOperativeGroup.ccRefer());
    EcConst.ccTranslateText(SubVFeederGroup.ccRefer());
    EcConst.ccTranslateText(SubVBondGroup.ccRefer());
    EcConst.ccTranslateText(SubVSurgeGroup.ccRefer());
    EcConst.ccTranslateText(SubWeigherGroup.ccRefer());
    
    //-- custom
    MainActionManager.ccRefer().ccInit();
    
    //-- passive
    MainActionManager.ccRefer().cmBackgroundRefreshing.ccTrigger();
    
    //-- post
    frame.setIconImage(ConstSwingUI.O_WINDOW_ICON);
    /* 4 */VcConst.ccPrintln(".setup() $ over");
    
  }//+++
  
  @Override public void draw(){
    
    //-- self
    ssRoll();
    
    //-- wire/simulate
    /* alternate:
     * if(isSimulating){
     *   MainSimulator.ccSimulate();
     * }else{
     *   MainDelegator.ccWire();
     * }//..?
     */
    MainSimulator.ccSimulate();
    
    //-- modeling
    MainPlantModel.ccRefer().ccLogic();
    
    //-- local ui
    MainPlantModel.ccRefer().ccBind();
    MainDelegator.ccBind();
    VcLocalCoordinator.ccUpdateQueue();
    VcLocalCoordinator.ccUpdateActive();
    VcLocalConsole.ccUpdate();
    
    //-- swing ui
    if(ccIsRollingAt(7)){
      SwingUtilities.invokeLater(MainWindow.ccRefer().cmUpdating);
    }//..?
    
    //-- debug
    /* 4 */ SubDegreeControlManager.ccRefer().tstTagg();
    VcLocalTagger.ccTag("roll",nf(pbRoller,2));
    VcLocalTagger.ccTag
      ("latency",VcNumericUtility.ccFormatPointTwoFloat(17f-frameRate));
    VcLocalTagger.ccTag
      ("clickID",nf(VcLocalCoordinator.ccGetMouseOverID(),6));
    VcLocalTagger.ccTag
      ("inputID",nf(VcLocalCoordinator.ccGetInputFocusID(),6));
    VcLocalTagger.ccTag("mX",nf(mouseX,4));
    VcLocalTagger.ccTag("mY",nf(mouseY,4));
    VcLocalTagger.ccStabilize();
    
  }//+++

  @Override public void keyPressed(){
    
    //-- for console
    VcLocalCoordinator.ccGuardEscKey(this);
    if(VcLocalConsole.ccKeyTyped(key, keyCode)){
      MainPlantModel.ccRefer().cmMessageBarBlockingFLG=true;
      return;
    }//..?
    
    //-- for input shift
    boolean lpMasked=false;
    if(keyCode==java.awt.event.KeyEvent.VK_TAB){
      if(pbModifiableKeyCode==java.awt.event.KeyEvent.VK_SHIFT){
        VcLocalCoordinator.ccToPreviousInputIndex();
        lpMasked=true;
      }else{
        VcLocalCoordinator.ccToNextInputIndex();
      }//..?
    }//..?
    if(keyCode==java.awt.event.KeyEvent.VK_SHIFT){pbModifiableKeyCode=keyCode;}
    else{pbModifiableKeyCode=0;}
    if(lpMasked){return;}
    
    //-- norm
    VcLocalCoordinator.ccKeyPressed(keyCode);
    
  }//+++
  
  @Override public void mousePressed(){
    if(mouseButton == LEFT) {
      VcLocalCoordinator.ccMousePressed();
    }else
    if(mouseButton == RIGHT) {
      if(MainWindow.pbIsVisible){
        SwingUtilities.invokeLater(MainActionManager.ccRefer().cmHidingness);
      }else{
        MainActionManager.ccRefer().cmPopping.ccTrigger();
      }//..?
    }//..?
  }//+++
  
  //=== utility
  
  private void ssInitFont(){
    File lpFont=MainFileManager.ccRefer().ccGetFontFile();
    if(lpFont==null){return;}
    if(lpFont.getName().endsWith(".vlw")){
      textFont(loadFont(lpFont.getAbsolutePath()));
      textSize(12);
    }else{
      PFont lpPFont=EcFactory.ccLoadTrueTypeFont(lpFont);
      if(lpPFont!=null){
        textFont(lpPFont);
        textSize(12);
      }//..?
    }//..?
  }//+++
  
  //=== timing
  
  private static void ssRoll(){
    pbRoller++;pbRoller&=0xF;
  }//+++
  
  static public boolean ccIsRollingAbove(int pxZeroToFifteen){
    return pbRoller>pxZeroToFifteen;
  }//+++
  
  static public boolean ccIsRollingAt(int pxZeroToFifteen){
    return pbRoller==pxZeroToFifteen;
  }//+++
  
  static public boolean ccIsRollingAccrose(int pxMod,int pxInt){
    return (pbRoller % pxMod) == pxInt;
  }//+++
  
  //=== selfie
  
  public static final int ccGetPrefferedW(){return pbWindowW;}//+++
  
  public static final int ccGetPrefferedH(){return pbWindowH;}//+++
  
  public static final MainSketch ccGetSketch(){return self;}//+++
  
  public static final PApplet ccGetPApplet(){return self;}//+++
  
  public static final Frame ccGetFrame(){return self.frame;}//+++
  
  //=== entry
  
  public static void main(String[] args) {
    
    //-- check in
    /* 4 */VcConst.ccSetDoseLog(false);
    VcConst.ccPrintln(".main() $ with", VcConst.C_V_OS);
    VcConst.ccPrintln(".main() $ at", VcConst.C_V_PWD);
    /* ? */VcConst.ccPrintln
      (".main() $ kosui -b", EcConst.ccGetLastLeavingStamp());
    /* ? */VcConst.ccPrintln
      (".main() $ nzz -b", MainSketch.ccGetLastLeavingStamp());
    
    //-- translation
    VcTranslator.ccGetInstance().ccInit();
    boolean lpIsCSVxOK = VcTranslator.ccGetInstance().ccParseCSV
      (MainSketch.class.getResourceAsStream("/nextzz/pppresource/tr.csv"));
    boolean lpIsXMLxOK = VcTranslator.ccGetInstance().ccParseXML
      (MainSketch.class.getResourceAsStream("/nextzz/pppresource/tr.xml"));
    /* 7 */VcTranslator.ccGetInstance().ccSetMode('c');//..later!
    if(!(lpIsCSVxOK&&lpIsXMLxOK)){
      ScConst.ccMessage("faild to laod text resource from archiver!");
    }//..?
    
    //-- file finding
    MainFileManager.ccRefer().ccInit();
    if(!MainFileManager.ccRefer().ccIsValid()){
      ScConst.ccMessage("faild to validate home directory!");
      System.exit(-1);
    }//..?
    
    //-- locale applying
    if(MainFileManager.ccRefer().ccGetFontFile()==null){
      VcTranslator.ccGetInstance().ccSetMode('e');
    }//..?
    
    //-- screen stuff
    ScConst.ccInitMonitorInformation();
    int lpMonitorHeight = ScConst.ccGetMinimalBound().height;
    if(lpMonitorHeight<=800){
      pbWindowW=C_WIDTH_S;
      pbWindowH=C_HEIGHT_S;
    }else{
      pbWindowW=C_WIDTH_M;
      pbWindowH=C_HEIGHT_M;
    }//..?
    EcRect lpPotentialWindow = new EcRect(pbWindowW, pbWindowH);
    if(ScConst.ccHasSubMonior()){
      /* 4 */VcConst.ccPrintln(".main() $ detected sub monitor");
      Rectangle lpSubBound = ScConst.ccGetSubMoniorBound();
      int lpPotentialInitX=0;
      int lpPotentialInitY=0;
      if(lpSubBound.y>=0){
        EcRect lpSubMonitor=new EcRect(lpSubBound);
        EcPoint lpPotentialInitPoint
          = EcRect.ccGetOffsetForCenterAlign(lpSubMonitor, lpPotentialWindow);
        lpPotentialInitX=lpSubMonitor.ccGetX()+lpPotentialInitPoint.ccGetX();
        lpPotentialInitY=lpSubMonitor.ccGetY()+lpPotentialInitPoint.ccGetY();
      }else{
        //.. this case would only get to happen on my sony 13 laptop.
        //   forgive this thing, any way.
        lpPotentialInitX=lpSubBound.x;
        lpPotentialInitY=lpSubBound.y;
        lpPotentialInitX+=100;
        lpPotentialInitY+=100;
      }//..?
      MainWindow.ccSetupInitInformation
        (lpPotentialInitX,lpPotentialInitY,true);
    }else{
      EcRect lpMainMonitor = new EcRect(ScConst.ccGetMainMonitorBound());
      EcPoint lpPotentialInitPoint
        = EcRect.ccGetOffsetForCenterAlign(lpMainMonitor, lpPotentialWindow);
      MainWindow.ccSetupInitInformation(
        lpPotentialInitPoint.ccGetX(),
        lpPotentialInitPoint.ccGetY(),
        false
      );
    }//..?
    
    //-- run sketch
    /* 4 */VcConst.ccLogln(".main() $ commited-w", pbWindowW);
    /* 4 */VcConst.ccLogln(".main() $ commited-h", pbWindowH);
    PApplet.main(MainSketch.class.getCanonicalName());
    
  }//+++
  
  public static final String ccGetLastLeavingStamp(){
    return "_2102101109";
  }//+++

}//***eof
