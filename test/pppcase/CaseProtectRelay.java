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

import java.util.Arrays;
import kosui.ppplocalui.EcComponent;
import kosui.ppplocalui.EcConst;
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcRect;
import kosui.ppplogic.ZcRoller;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcLocalCoordinator;
import kosui.ppputil.VcLocalTagger;
import kosui.ppputil.VcStringUtility;
import nextzz.pppsimulate.ZcProtectRelay;
import processing.core.PApplet;

public class CaseProtectRelay extends PApplet{
  
  private final ZcRoller cmRoller = new ZcRoller();
  
  private final ZcProtectRelay cmController = new ZcProtectRelay();
  
  public final EcElement
    cmReadyPL = new EcElement("ready"),
    cmRunPL = new EcElement("run"),
    cmLockedOutPL = new EcElement("RSI"),
    cmFanStartPL = new EcElement("BM"),
    cmDamperOpenPL = new EcElement("BUO"),
    cmDamperClosePL = new EcElement("BUC"),
    cmIngitionPL = new EcElement("IG"),
    cmPilotPL = new EcElement("PV"),
    cmMainValvePL = new EcElement("MMV")
  ;//...
  
  boolean cmRunSW,cmResetSW;
  boolean dcExfanPressure,dcBurnerHasPressure,dcBurnerIsOpened,dcBurnerIsClosed;
  boolean dcUltraVision;
  
  @Override public void setup() {
    
    //--
    size(320,240);
    frame.setTitle(CaseProtectRelay.class.getSimpleName());
    EcConst.ccSetupSketch(this);
    VcLocalCoordinator.ccInit(this);
    VcLocalTagger.ccInit(this, 9);
    
    //--
    cmReadyPL.ccSetLocation(5, 170);
    EcRect.ccFlowLayout(Arrays.asList(
      cmReadyPL,cmRunPL,cmLockedOutPL
    ));
    cmFanStartPL.ccSetLocation(cmReadyPL, 0, 5);
    EcRect.ccFlowLayout(Arrays.asList(
      cmFanStartPL,cmDamperOpenPL,cmDamperClosePL,
      cmIngitionPL,cmPilotPL,cmMainValvePL
    ));
    
    //--
    VcConst.ccSetDoseLog(true);
    VcLocalCoordinator.ccAddAll(this);
    
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
    
    //-- local
    cmReadyPL.ccSetIsActivated(cmController
      .ccGetReadyLamp(cmRoller.ccIsAbove(7)));
    cmRunPL.ccSetIsActivated(cmController
      .ccGetRunLamp(cmRoller.ccIsAbove(7)));
    cmLockedOutPL.ccSetIsActivated(cmController.ccGetLockedOutLamp());
    cmFanStartPL.ccSetIsActivated(cmController.ccGetFanStartSignal());
    cmDamperClosePL.ccSetIsActivated(cmController.ccGetDamperCloseSignal());
    cmDamperOpenPL.ccSetIsActivated(cmController.ccGetDamperOpenSignal());
    cmIngitionPL.ccSetIsActivated(cmController.ccGetIgnitionSignal());
    cmPilotPL.ccSetIsActivated(cmController.ccGetPilotValveSignal());
    cmMainValvePL.ccSetIsActivated(cmController.ccGetMainValveSignal());
    VcLocalCoordinator.ccUpdate();
    
    //-- inspect
    fill(0xFF);
    text(
      "prt-"+
      VcStringUtility.ccBreakObject(cmController),
      160,5
    );
    
    //-- tag
    //-- tag ** input
    VcLocalTagger.ccTag("[0]l1-2", dcExfanPressure);
    VcLocalTagger.ccTag("[r]RUN", cmRunSW);
    VcLocalTagger.ccTag("[ ]RST", cmResetSW);
    VcLocalTagger.ccTag("[1]bcc", dcBurnerIsClosed);
    VcLocalTagger.ccTag("[2]gp", dcBurnerHasPressure);
    VcLocalTagger.ccTag("[3]boo", dcBurnerIsOpened);
    VcLocalTagger.ccTag("[f]fire!!", dcUltraVision);
    //-- tag ** system
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
