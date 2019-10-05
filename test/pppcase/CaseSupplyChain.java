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
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EiTriggerable;
import kosui.ppplogic.ZcRoller;
import kosui.ppputil.VcLocalCoordinator;
import kosui.ppputil.VcLocalTagger;
import kosui.ppputil.VcStringUtility;
import nextzz.pppsimulate.ZcChainController;
import nextzz.pppsimulate.ZcMotor;
import processing.core.PApplet;

public class CaseSupplyChain extends PApplet{
  
  static private CaseSupplyChain self=null;
  
  //===
  
  private final ZcRoller cmRoller = new ZcRoller(15, 2);
  
  private final ZcChainController dcTheController
    = new ZcChainController(2,6);
  
  private boolean dcAlartI  = false;
  private boolean dcAlartII  = false;
  private boolean dcAlartIII  = false;
  private boolean dcAlartIV  = false;
  private boolean dcAlartV  = false;
  
  private ZcMotor dcMotorI = new ZcMotor(10);
  private ZcMotor dcMotorII = new ZcMotor(10);
  private ZcMotor dcMotorIII = new ZcMotor(10);
  private ZcMotor dcMotorIV = new ZcMotor(10);
  private ZcMotor dcMotorV = new ZcMotor(10);
  
  private final EcElement cmRunPLB = new EcElement("run", 0xAA0E);
  private final EcElement cmAlartIxPL   = new EcElement("1", 0xAAA1);
  private final EcElement cmAlartIIxPL  = new EcElement("2", 0xAAA2);
  private final EcElement cmAlartIIIxPL = new EcElement("3", 0xAAA3);
  private final EcElement cmAlartIVxPL  = new EcElement("4", 0xAAA4);
  private final EcElement cmAlartVxPL   = new EcElement("5", 0xAAA5);
  
  private final EiTriggerable cmQuitting = new EiTriggerable() {
    @Override public void ccTrigger() {
      if(self==null){
        System.out.println(".cmQuitting::this should never happen!");
        return;
      }//..?
      PApplet.println(".cmQuitting::call exit");
      self.exit();
    }//+++
  };//***
  
  //===
  
  @Override public void setup() {
   
    size(320,240);
    frame.setTitle(CaseSupplyChain.class.getSimpleName());
    self=this;
    
    //--
    EcConst.ccSetupSketch(this);
    VcLocalTagger.ccGetInstance().ccInit(this);
    VcLocalCoordinator.ccGetInstance().ccInit(this);
    EcElement.ccSetTextAdjust(0, -2);
    
    //--
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_Q, cmQuitting);
    
    //--
    cmAlartIxPL.ccSetLocation(160, 120);
    cmAlartIIxPL.ccSetLocation(cmAlartIxPL, 5, 0);
    cmAlartIIIxPL.ccSetLocation(cmAlartIIxPL, 5, 0);
    cmAlartIVxPL.ccSetLocation(cmAlartIIIxPL, 5, 0);
    cmAlartVxPL.ccSetLocation(cmAlartIVxPL, 5, 0);
    cmRunPLB.ccSetLocation(cmAlartIVxPL, 0, 5);
    cmRunPLB.ccSetEndX(cmAlartVxPL.ccEndX());
    VcLocalCoordinator.ccAddElement(cmAlartIxPL);
    VcLocalCoordinator.ccAddElement(cmAlartIIxPL);
    VcLocalCoordinator.ccAddElement(cmAlartIIIxPL);
    VcLocalCoordinator.ccAddElement(cmAlartIVxPL);
    VcLocalCoordinator.ccAddElement(cmAlartVxPL);
    VcLocalCoordinator.ccAddElement(cmRunPLB);
    
  }//+++

  @Override public void draw() {
    
    background(0);
    cmRoller.ccRoll();
    
    //--
    dcAlartI=EcComponent.ccIsKeyPressed('1');
    dcAlartII=EcComponent.ccIsKeyPressed('2');
    dcAlartIII=EcComponent.ccIsKeyPressed('3');
    dcAlartIV=EcComponent.ccIsKeyPressed('4');
    dcAlartV=EcComponent.ccIsKeyPressed('5');
    
    //--
    cmAlartIxPL.ccSetIsActivated(dcAlartI);
    cmAlartIIxPL.ccSetIsActivated(dcAlartII);
    cmAlartIIIxPL.ccSetIsActivated(dcAlartIII);
    cmAlartIVxPL.ccSetIsActivated(dcAlartIV);
    cmAlartVxPL.ccSetIsActivated(dcAlartV);
    
    //--
    dcMotorI.ccTestTrip(dcAlartI);
    dcMotorII.ccTestTrip(dcAlartII);
    dcMotorIII.ccTestTrip(dcAlartIII);
    dcMotorIV.ccTestTrip(dcAlartIV);
    dcMotorV.ccTestTrip(dcAlartV);
    
    //--
    dcTheController.ccSetTrippedAt(1, dcAlartI);
    dcTheController.ccSetTrippedAt(2, dcAlartII);
    dcTheController.ccSetTrippedAt(3, dcAlartIII);
    dcTheController.ccSetTrippedAt(4, dcAlartIV);
    dcTheController.ccSetTrippedAt(5, dcAlartV);
    dcTheController.ccSetConfirmedAt(1, dcMotorI.ccIsContacted());
    dcTheController.ccSetConfirmedAt(2, dcMotorII.ccIsContacted());
    dcTheController.ccSetConfirmedAt(3, dcMotorIII.ccIsContacted());
    dcTheController.ccSetConfirmedAt(4, dcMotorIV.ccIsContacted());
    dcTheController.ccSetConfirmedAt(5, dcMotorV.ccIsContacted());
    
    //--
    boolean lpSW=EcComponent.ccIsKeyPressed('r');
    dcTheController.ccSetRun(lpSW);
    dcTheController.ccRun();
    dcMotorI.ccContact(  dcTheController.ccGetOutputFor(1));
    dcMotorII.ccContact( dcTheController.ccGetOutputFor(2));
    dcMotorIII.ccContact(dcTheController.ccGetOutputFor(3));
    dcMotorIV.ccContact( dcTheController.ccGetOutputFor(4));
    dcMotorV.ccContact(  dcTheController.ccGetOutputFor(5));
    cmRunPLB.ccSetIsActivated
      (dcTheController.ccGetFlasher(cmRoller.ccIsAbove(7)));
    
    //--
    dcMotorI.ccSimulate(0.5f);
    dcMotorII.ccSimulate(0.5f);
    dcMotorIII.ccSimulate(0.5f);
    dcMotorIV.ccSimulate(0.5f);
    dcMotorV.ccSimulate(0.5f);
    
    //--
    VcLocalCoordinator.ccUpdate();
    
    //--
    fill(0xFF);
    text(
      VcStringUtility.ccBreakObject(dcTheController),
      160,5
    );
    
    //--
    VcLocalTagger.ccTag("roller", cmRoller.ccGetValue());
    VcLocalTagger.ccTag(ccPackupMotorTag(1,dcMotorI));
    VcLocalTagger.ccTag(ccPackupMotorTag(2,dcMotorII));
    VcLocalTagger.ccTag(ccPackupMotorTag(3,dcMotorIII));
    VcLocalTagger.ccTag(ccPackupMotorTag(4,dcMotorIV));
    VcLocalTagger.ccTag(ccPackupMotorTag(5,dcMotorV));
    VcLocalTagger.ccStabilize();
    
  }//+++

  @Override public void keyPressed() {
    VcLocalCoordinator.ccKeyPressed(keyCode);
  }//+++
  
  //===
  
  private String ccPackupMotorTag(int pxID,ZcMotor pxMotor){
    if(pxMotor==null){return "<null>";}
    return String.format("m%d > AL:%b | AN:%b ",
      pxID,
      pxMotor.ccIsTripped(),pxMotor.ccIsContacted()
    );
  }//+++
  
  //===
  
  public static void main(String[] args) {
    PApplet.main(CaseSupplyChain.class.getCanonicalName());
  }//+++ 
  
}//***eof
