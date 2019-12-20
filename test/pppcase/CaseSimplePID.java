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
import kosui.ppplogic.ZcFlicker;
import kosui.ppputil.VcLocalCoordinator;
import kosui.ppputil.VcStringUtility;
import nextzz.pppsimulate.ZcPIDController;
import processing.core.PApplet;

public final class CaseSimplePID extends PApplet{
  
  private static final int C_VIS_GAP = 8;
  
  private final ZcFlicker cmSamplingTM = new ZcFlicker(16, 0.5f);
  private final ZcFlicker cmAdjustingTM = new ZcFlicker(80, 0.5f);
  
  private final ZcPIDController cmController
    = new ZcPIDController(0f,240f,0.02f,0.33f);
  
  private boolean cmAdjustFlag,cmSampleFlag;

  @Override public void setup(){
    
    //-- pre
    size(320,240);
    EcConst.ccSetupSketch(this);
    VcLocalCoordinator.ccGetInstance().ccInit(this);
    
  }//+++

  @Override public void draw(){
    
    //-- pre
    background(0);
   
    //-- logic ** clock
    cmSamplingTM.ccAct(true);
    cmAdjustingTM.ccAct(true);
    cmSampleFlag=cmSamplingTM.ccAtEdge();
    cmAdjustFlag=cmAdjustingTM.ccAtEdge();
    
    //-- logic ** controller
    final int lpConstCurrent = height - mouseY;
    cmController.ccRun(lpConstCurrent,cmSampleFlag,cmAdjustFlag);
    
    //-- visualize ** push
    final int lpConstTarget = height
     - ceil(cmController.tstGetTarget());
    final int lpConstShifted = height
     - ceil(cmController.tstGetShiftedTarget());
    final int lpConstAverage = height
     - ceil(cmController.tstGetProcessAverage());
    final int lpConstDeadZ = ceil(
      cmController.tstGetDeadP() - cmController.tstGetDeadN()
    );
    final int lpConstProportionZ = ceil(
      cmController.tstGetProportionP()- cmController.tstGetProportionN()
    );
    
    //-- visualize ** controller ** target
    stroke(0xFFEEEE33);ccDrawLineH(lpConstTarget);
    
    //-- visualize ** controller ** shifted target 
    stroke(0xFF44FF44);ccDrawLineH(lpConstShifted);
    
    //-- visualize ** controller ** shifted target 
    stroke(0xFF3399CC);ccDrawLineH(lpConstAverage);
    
    //-- visualize ** controller ** proportion zone
    stroke(0xFF33CC33);
    //[head]:: the fill flash
    fill(0x66,0x66);
    rect(
      160+C_VIS_GAP,lpConstShifted-lpConstProportionZ/2,
      width/2-C_VIS_GAP*2,lpConstProportionZ
    );
    
    //-- visualize ** controller ** dead zone
    stroke(0xFF119911);
    fill(0x66,0x66);
    rect(
      160+C_VIS_GAP*2,lpConstShifted-lpConstDeadZ/2,
      width/2-C_VIS_GAP*4,lpConstDeadZ
    );
    
    //-- visualize ** controller ** current
    stroke(0xFF,0xAA);ccDrawLineH(mouseY);
    fill(0xFF);
    text(String.format("[pv:%3d]", lpConstCurrent),mouseX,mouseY-20);
    
    //[head]::now what ??
    
    //-- visualize ** pop
    noStroke();
    
    //-- inspect
    fill(0x55,0xAA);
    rect(10,10,125,105);
    fill(0xEE);
    text(
      "ctrl-"+VcStringUtility.ccBreakObject(cmController),
      12, 12
    );
    
  }//+++
  
  @Override public void keyPressed(){
    
    //[head]:: let config parameter via console
    
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
