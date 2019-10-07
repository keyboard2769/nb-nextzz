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

import kosui.ppplocalui.EcConst;
import kosui.ppplocalui.EcElement;
import kosui.ppplogic.ZcRoller;
import kosui.ppputil.VcLocalCoordinator;
import kosui.ppputil.VcLocalTagger;
import kosui.ppputil.VcStringUtility;
import nextzz.pppsimulate.ZcPIDController;
import processing.core.PApplet;

public final class CaseSimplePID extends PApplet{
  
  private final float C_DEAD_ZONE = 0.1f;
  private final float C_PROPORTION = 0.5f;
  
  private final ZcRoller cmRoller=new ZcRoller();
  
  private final ZcPIDController cmController
    = new ZcPIDController(150f, C_PROPORTION, C_DEAD_ZONE);
  
  private final EcElement cmAdjusttingPL = new EcElement("-I-");
  private final EcElement cmSamplingPL = new EcElement("-D-");
  private final EcElement cmUpRequestPL = new EcElement("UP");
  private final EcElement cmDownRequestPL = new EcElement("DN");
  
  private boolean cmAdjustFlag,cmSampleFlag;
  private float cmCurrentValue;

  @Override public void setup(){
    
    //--
    size(320,240);
    EcConst.ccSetupSketch(this);
    VcLocalCoordinator.ccGetInstance().ccInit(this);
    VcLocalTagger.ccGetInstance().ccInit(this);
    
    //--
    cmAdjusttingPL.ccSetLocation(160, 80);
    cmDownRequestPL.ccSetLocation(cmAdjusttingPL, 2, 0);
    cmSamplingPL.ccSetLocation(cmDownRequestPL, 2, 0);
    cmUpRequestPL.ccSetLocation(
      cmDownRequestPL.ccGetX(),
      cmDownRequestPL.ccGetY()-2-cmUpRequestPL.ccGetH()
    );
    VcLocalCoordinator.ccAddElement(cmAdjusttingPL);
    VcLocalCoordinator.ccAddElement(cmDownRequestPL);
    VcLocalCoordinator.ccAddElement(cmSamplingPL);
    VcLocalCoordinator.ccAddElement(cmUpRequestPL);
    
  }//+++

  @Override public void draw(){
    
    //-- pre
    background(0);
    cmRoller.ccRoll();
   
    //-- logic
    cmCurrentValue=height-mouseY;
    cmAdjustFlag=cmRoller.ccIsAt(11);
    cmSampleFlag=cmRoller.ccIsAcrossAt(6, 1);
    cmController.ccRun(cmCurrentValue,cmAdjustFlag,cmSampleFlag);
    
    //-- visualze
    stroke(EcConst.C_GREEN);fill(EcConst.C_GREEN);
    line(0,mouseY,width,mouseY);
    text(nfc(cmCurrentValue,2),mouseX,mouseY-18);
    float lpShiftedY = height - cmController.ccGetShiftedTarget();
    stroke(0xFF);
    line(0,lpShiftedY,width,lpShiftedY);
    stroke(0x55);
    float lpDeadPY
      = height - cmController.ccGetShiftedTarget() * (1f+C_DEAD_ZONE);
    float lpDeadNY
      = height - cmController.ccGetShiftedTarget() * (1f-C_DEAD_ZONE);
    line(0,lpDeadPY,width,lpDeadPY);
    line(0,lpDeadNY,width,lpDeadNY);
    float lpProportionPY
      = height - cmController.ccGetShiftedTarget() * (1f+C_PROPORTION);
    float lpProportionNY
      = height - cmController.ccGetShiftedTarget() * (1f-C_PROPORTION);
    line(0,lpProportionPY,width,lpProportionPY);
    line(0,lpProportionNY,width,lpProportionNY);
    noStroke();
    
    //-- local
    cmAdjusttingPL.ccSetIsActivated(cmAdjustFlag);
    cmDownRequestPL.ccSetIsActivated(cmController.ccGetNegativeOutput());
    cmSamplingPL.ccSetIsActivated(cmSampleFlag);
    cmUpRequestPL.ccSetIsActivated(cmController.ccGetPositiveOutput());
    VcLocalCoordinator.ccUpdate();
    
    //-- inspect
    fill(0xEE);
    text(
      "ctrl-"+VcStringUtility.ccBreakObject(cmController),
      5,120
    );
    
    //-- tag
    VcLocalTagger.ccTag("roller", cmRoller.ccGetValue());
    VcLocalTagger.ccStabilize();
    
  }//+++
  
  @Override public void keyPressed(){
    if(key=='q'){exit();}
    switch(key){
      default:break;
    }//..?
  }//+++
  
  //===
  
  public static void main(String[] args) {
    PApplet.main(CaseSimplePID.class.getCanonicalName());
  }//!!!

}//***eof
