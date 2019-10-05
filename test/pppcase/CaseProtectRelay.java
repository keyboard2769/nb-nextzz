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

package pppcase;

import kosui.ppplocalui.EcComponent;
import kosui.ppplocalui.EcConst;
import kosui.ppplogic.ZcRoller;
import kosui.ppputil.VcLocalCoordinator;
import kosui.ppputil.VcLocalTagger;
import kosui.ppputil.VcStringUtility;
import nextzz.pppsimulate.ZcProtectRelay;
import processing.core.PApplet;

public class CaseProtectRelay extends PApplet{
  
  private final ZcRoller cmRoller = new ZcRoller();
  
  private final ZcProtectRelay cmController = new ZcProtectRelay();
  
  boolean cmRunSW,cmResetSW;
  boolean dcExfanPressure,dcBurnerHasPressure,dcBurnerIsOpened,dcBurnerIsClosed;
  boolean dcUltraVision;
  
  @Override public void setup() {
    size(320,240);
    frame.setTitle(CaseProtectRelay.class.getSimpleName());
    EcConst.ccSetupSketch(this);
    VcLocalCoordinator.ccGetInstance().ccInit(this);
    VcLocalTagger.ccGetInstance().ccInit(this, 9);
  }//+++
  
  @Override public void draw() {
    
    //-- pre
    background(0);
    cmRoller.ccRoll();
    
    //-- press
    cmRunSW=EcComponent.ccIsKeyPressed('r');
    cmResetSW=EcComponent.ccIsKeyPressed(' ');
    
    //-- logic
    cmController.ccSetReadyCondition(dcExfanPressure);
    cmController.ccSetDamperCloseConfirm(dcBurnerIsClosed);
    cmController.ccSetDamperOpenConfirm(dcBurnerIsOpened);
    cmController.ccSetFanStartConfirm(dcBurnerHasPressure);
    cmController.ccSetFlameConfirm(dcUltraVision);
    cmController.ccClearLock(cmResetSW);
    cmController.ccRun(cmRunSW);
    
    //-- show
    fill(0xFF);
    text(
      "prt-"+
      VcStringUtility.ccBreakObject(cmController),
      180,120
    );
    
    //-- tag
    //-- tag ** input
    VcLocalTagger.ccTag("[0]l1-2", VcStringUtility
      .ccToString(dcExfanPressure));
    VcLocalTagger.ccTag("[r]RUN", VcStringUtility
      .ccToString(cmRunSW));
    VcLocalTagger.ccTag("[ ]RST", VcStringUtility
      .ccToString(cmResetSW));
    VcLocalTagger.ccTag("[1]bcc", VcStringUtility
      .ccToString(dcBurnerIsClosed));
    VcLocalTagger.ccTag("[2]gp", VcStringUtility
      .ccToString(dcBurnerHasPressure));
    VcLocalTagger.ccTag("[3]boo", VcStringUtility
      .ccToString(dcBurnerIsOpened));
    VcLocalTagger.ccTag("[f]fire!!", VcStringUtility
      .ccToString(dcUltraVision));
    //-- tag ** output
    VcLocalTagger.ccTag("-ready-", VcStringUtility
      .ccToString(cmController.ccGetReadyLamp()));
    VcLocalTagger.ccTag("-run-", VcStringUtility
      .ccToString(cmController.ccGetRunLamp(cmRoller.ccIsAbove(7))));
    VcLocalTagger.ccTag("-rsi-", VcStringUtility
      .ccToString(cmController.ccIsLockedOut()));
    VcLocalTagger.ccTag("-bm-", VcStringUtility
      .ccToString(cmController.ccGetFanStartSignal()));
    VcLocalTagger.ccTag("-buo-", VcStringUtility
      .ccToString(cmController.ccIsAtBUO()));
    VcLocalTagger.ccTag("-buc-", VcStringUtility
      .ccToString(cmController.ccIsAtBUC()));
    VcLocalTagger.ccTag("-ig-", VcStringUtility
      .ccToString(cmController.ccIsAtIG()));
    VcLocalTagger.ccTag("-pv-", VcStringUtility
      .ccToString(cmController.ccIsAtPV()));
    VcLocalTagger.ccTag("-mmv-", VcStringUtility
      .ccToString(cmController.ccIsAtMV()));
    //-- tag ** system
    VcLocalTagger.ccTag("[r]Run", VcStringUtility.ccToString(cmRunSW));
    VcLocalTagger.ccTag("roll", cmRoller.ccGetValue());
    VcLocalTagger.ccStabilize();
    
  }//+++
  
  @Override public void keyPressed() {
    if(key=='q'){
      exit();
    }//..?
    switch(key){
      case '0':dcExfanPressure=!dcExfanPressure;break;
      //case 'r':cmRunFlag=!cmRunFlag;break;
      case '1':dcBurnerIsClosed=!dcBurnerIsClosed;break;
      case '2':dcBurnerHasPressure=!dcBurnerHasPressure;break;
      case '3':dcBurnerIsOpened=!dcBurnerIsOpened;break;
      case 'f':dcUltraVision=!dcUltraVision;break;
      default:break;
    }//..?
  }//+++
  
  //===
  
  public static void main(String[] args) {
    PApplet.main(CaseProtectRelay.class.getCanonicalName());
  }//+++

}//***eof
