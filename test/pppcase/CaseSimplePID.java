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
import kosui.ppputil.VcConst;
import kosui.ppputil.VcLocalCoordinator;
import kosui.ppputil.VcLocalTagger;
import kosui.ppputil.VcStringUtility;
import nextzz.pppsimulate.ZcPIDController;
import processing.core.PApplet;

public final class CaseSimplePID extends PApplet{
  
  private static final int C_VIS_GAP = 5;
  
  private final ZcRoller cmRoller=new ZcRoller();
  
  private final ZcPIDController cmController
    = new ZcPIDController(0f,240f,0.02f,0.75f);
  
  public final EcElement cmAdjusttingPL  = new EcElement("-I-");
  public final EcElement cmSamplingPL    = new EcElement("-D-");
  public final EcElement cmUpRequestPL   = new EcElement("UP");
  public final EcElement cmDownRequestPL = new EcElement("DN");
  
  private boolean cmAdjustFlag,cmSampleFlag;
  private float cmCurrentValue;

  @Override public void setup(){
    
    //-- pre
    size(320,240);
    EcConst.ccSetupSketch(this);
    VcLocalCoordinator.ccGetInstance().ccInit(this);
    VcLocalTagger.ccGetInstance().ccInit(this);
    
    //-- setup
    cmAdjusttingPL.ccSetLocation(160, 80);
    cmDownRequestPL.ccSetLocation(cmAdjusttingPL, 2, 0);
    cmSamplingPL.ccSetLocation(cmDownRequestPL, 2, 0);
    cmUpRequestPL.ccSetLocation(
      cmDownRequestPL.ccGetX(),
      cmDownRequestPL.ccGetY()-2-cmUpRequestPL.ccGetH()
    );
    
    //-- post
    VcConst.ccSetDoseLog(true);
    VcLocalCoordinator.ccAddAll(this);
    
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
    
    //-- visualize ** push
    final int lpConstTarget = height
     - ceil(cmController.tstGetTarget());
    final int lpConstShifted = height
     - ceil(cmController.tstGetShiftedTarget());
    
    //-- visualize ** controller ** target
    stroke(0xFFEEEE33);ccDrawLineH(lpConstTarget);
    
    //-- visualize ** controller ** shifted target 
    stroke(0xFF44FF44);ccDrawLineH(lpConstShifted);
    
    //-- visualize ** controller ** proportion zone
    //[head]::fill(0x66666666);
    //[head]::stroke(0xFF33CC33);
    //[head]::now what ??
    
    //-- visualize ** controller ** dead zone
    
    //-- visualize ** pop
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
  
  void ccDrawLineH(int pxY){
    line(0, pxY, width, pxY);
  }//+++
  
  //===
  
  public static void main(String[] args) {
    PApplet.main(CaseSimplePID.class.getCanonicalName());
  }//!!!

}//***eof
