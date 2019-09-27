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
import kosui.ppplocalui.EcRect;
import kosui.ppplogic.ZcRoller;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcLocalCoordinator;
import kosui.ppputil.VcLocalTagger;
import kosui.ppputil.VcStringUtility;
import nextzz.pppsimulate.ZcContainer;
import processing.core.PApplet;

public class CaseContainer extends PApplet{
  
  private final ZcRoller cmRoller = new ZcRoller();
  
  private final ZcContainer cmSource = new ZcContainer(1.1f);
  private final ZcContainer cmTarget = new ZcContainer(2.3f);
  
  private final EcElement cmChargeKEY = new EcElement("W", 0xAA1);
  private final EcElement cmTransferKEY = new EcElement("S", 0xAA2);
  private final EcElement cmResetKEY = new EcElement("A", 0xAA3);
  private final EcElement cmDischargeKEY = new EcElement("D", 0xAA4);
  
  @Override public void setup() {
    size(320,240);
    EcConst.ccSetupSketch(this);
    VcLocalCoordinator.ccGetInstance().ccInit(this);
    VcLocalTagger.ccGetInstance().ccInit(this, 9);
    EcRect lpG = new EcRect(5,120,27, 27);
    cmChargeKEY.ccSetLocation(   lpG.ccGridX(2), lpG.ccGridY(1));
    cmResetKEY.ccSetLocation(    lpG.ccGridX(1), lpG.ccGridY(2));
    cmTransferKEY.ccSetLocation( lpG.ccGridX(2), lpG.ccGridY(2));
    cmDischargeKEY.ccSetLocation(lpG.ccGridX(3), lpG.ccGridY(2));
    VcLocalCoordinator.ccAddElement(cmChargeKEY);
    VcLocalCoordinator.ccAddElement(cmTransferKEY);
    VcLocalCoordinator.ccAddElement(cmResetKEY);
    VcLocalCoordinator.ccAddElement(cmDischargeKEY);
  }//+++

  @Override public void draw() {
    
    background(0);
    cmRoller.ccRoll();
    
    //--
    cmChargeKEY.ccSetIsActivated(EcComponent.ccIsKeyPressed('w'));
    cmTransferKEY.ccSetIsActivated(EcComponent.ccIsKeyPressed('s'));
    cmResetKEY.ccSetIsActivated(EcComponent.ccIsKeyPressed('a'));
    cmDischargeKEY.ccSetIsActivated(EcComponent.ccIsKeyPressed('d'));
    
    //--
    if(cmResetKEY.ccIsActivated()){
      cmSource.ccSetValue(0);
      cmTarget.ccSetValue(0);
    }//..?
    if(cmChargeKEY.ccIsActivated()){cmSource.ccCharge(0x32);}
    if(cmDischargeKEY.ccIsActivated()){cmTarget.ccDischarge(0x64);}
    ZcContainer.ccTransfer
      (cmSource, cmTarget, cmTransferKEY.ccIsActivated(), 0x16);
    
    //--
    VcLocalCoordinator.ccUpdate();
    
    //--
    fill(0xFF);
    text(
      "src"+VcConst.C_V_NEWLINE+VcStringUtility.ccBreakObject(cmSource),
      160,5
    );
    text(
      "tgt"+VcConst.C_V_NEWLINE+VcStringUtility.ccBreakObject(cmTarget)
       + VcConst.C_V_NEWLINE+PApplet.nf(cmTarget.ccGetScaledValue(3600),4),
      160, 120
    );
    
    //--
    VcLocalTagger.ccTag("roll", cmRoller.ccGetValue());
    VcLocalTagger.ccStabilize();
    
  }//+++

  @Override public void keyPressed() {
    if(key=='q'){
      exit();
    }//..?
  }//+++
  
  public static void main(String[] args) {
    PApplet.main(CaseContainer.class.getCanonicalName());
  }//+++
  
}//**eof
