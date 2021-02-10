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
import kosui.ppplogic.ZcRoller;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcLocalCoordinator;
import kosui.ppputil.VcLocalTagger;
import kosui.ppputil.VcStringUtility;
import nextzz.pppsimulate.ZcChainController;
import nextzz.pppsimulate.ZcMotor;
import processing.core.PApplet;

public class CaseSupplyChain extends PApplet{
  
  private final ZcRoller cmRoller = new ZcRoller(15, 2);
  
  private final ZcChainController dcTheController
    = new ZcChainController(2,6);
  
  private boolean dcAlartI  = false;
  private boolean dcAlartII  = false;
  private boolean dcAlartIII  = false;
  private boolean dcAlartIV  = false;
  private boolean dcAlartV  = false;
  
  private final ZcMotor dcMotorI = new ZcMotor(10);
  private final ZcMotor dcMotorII = new ZcMotor(10);
  private final ZcMotor dcMotorIII = new ZcMotor(10);
  private final ZcMotor dcMotorIV = new ZcMotor(10);
  private final ZcMotor dcMotorV = new ZcMotor(10);
  
  public final EcElement cmRunPLB = new EcElement("run", 0xAA0E);
  public final EcElement cmAlartIxPL   = new EcElement("1", 0xAAA1);
  public final EcElement cmAlartIIxPL  = new EcElement("2", 0xAAA2);
  public final EcElement cmAlartIIIxPL = new EcElement("3", 0xAAA3);
  public final EcElement cmAlartIVxPL  = new EcElement("4", 0xAAA4);
  public final EcElement cmAlartVxPL   = new EcElement("5", 0xAAA5);
  
  //===
  
  @Override public void setup() {
   
    size(320,240);
    frame.setTitle(CaseSupplyChain.class.getSimpleName());
    
    //--
    EcConst.ccSetupSketch(this);
    VcLocalTagger.ccInit(this);
    VcLocalCoordinator.ccInit(this);
    EcElement.ccSetTextAdjust(0, -2);
    
    //--
    cmAlartIxPL.ccSetLocation(160, 120);
    cmAlartIIxPL.ccSetLocation(cmAlartIxPL, 5, 0);
    cmAlartIIIxPL.ccSetLocation(cmAlartIIxPL, 5, 0);
    cmAlartIVxPL.ccSetLocation(cmAlartIIIxPL, 5, 0);
    cmAlartVxPL.ccSetLocation(cmAlartIVxPL, 5, 0);
    cmRunPLB.ccSetLocation(cmAlartIVxPL, 0, 5);
    cmRunPLB.ccSetEndX(cmAlartVxPL.ccEndX());
    
    //--
    VcConst.ccSetDoseLog(true);
    VcLocalCoordinator.ccAddAll(this);
    
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
    dcMotorI.ccSetTrip(dcAlartI);
    dcMotorII.ccSetTrip(dcAlartII);
    dcMotorIII.ccSetTrip(dcAlartIII);
    dcMotorIV.ccSetTrip(dcAlartIV);
    dcMotorV.ccSetTrip(dcAlartV);
    
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
    dcMotorI.ccRun(0.5f);
    dcMotorII.ccRun(0.5f);
    dcMotorIII.ccRun(0.5f);
    dcMotorIV.ccRun(0.5f);
    dcMotorV.ccRun(0.5f);
    
    //--
    cmAlartIxPL.ccSetIsActivated(dcAlartI);
    cmAlartIIxPL.ccSetIsActivated(dcAlartII);
    cmAlartIIIxPL.ccSetIsActivated(dcAlartIII);
    cmAlartIVxPL.ccSetIsActivated(dcAlartIV);
    cmAlartVxPL.ccSetIsActivated(dcAlartV);
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
    if(key=='q'){exit();}
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
