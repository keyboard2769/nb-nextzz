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

import javax.swing.SwingUtilities;
import kosui.ppplocalui.EcConst;
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EiTriggerable;
import kosui.ppplogic.ZcFlicker;
import kosui.ppplogic.ZcRangedValueModel;
import kosui.pppswingui.ScConst;
import kosui.ppputil.VcLocalCoordinator;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcStringUtility;
import nextzz.pppsimulate.ZcPIDController;
import processing.core.PApplet;
import processing.event.MouseEvent;

public final class CaseSimplePID extends PApplet{
  
  private static CaseSimplePID self = null;
  
  private static final int C_VIS_GAP = 8;
  
  private static volatile int mnSamplingFPS = 16;
  private static volatile int mnAdjustingFPS = 80;
  
  public final EcElement cmSamplingTimerPL
    = new EcElement("[s]:01s");
  
  public final EcElement cmAdjustingTimerPL
    = new EcElement("[a]:05s");
  
  private final ZcFlicker cmSamplingTM
    = new ZcFlicker(mnSamplingFPS);
  
  private final ZcFlicker cmAdjustingTM
    = new ZcFlicker(mnAdjustingFPS);
  
  private final ZcRangedValueModel cmSamplingFlash
    = new ZcRangedValueModel(0, 10);
  
  private final  EiTriggerable cmSamplingTimerSetting = new EiTriggerable() {
    @Override public void ccTrigger() {
      cmSamplingTimerPL.ccSetText(String.format(
        "[s]:%2ds", (int)EcConst.ccToSecondCount(mnSamplingFPS)
      ));
      cmSamplingTM.ccSetTime(mnSamplingFPS);
    }//+++
  };//***
  
  private static final Runnable O_SAMP_TM_IPT = new Runnable() {
    @Override public void run() {
      String lpEntered = ScConst.ccGetStringByInputBox(
        "[1 ~ 99] as second for sampling",
        Integer.toString((int)EcConst.ccToSecondCount(mnSamplingFPS))
      );
      int lpFixed = VcNumericUtility.ccParseIntegerString(lpEntered);
      mnSamplingFPS = EcConst.ccToFrameCount(
        (float)constrain(lpFixed, 1, 99)
      );
      VcLocalCoordinator.ccInvokeLater(self.cmSamplingTimerSetting);
    }//+++
  };//***
  
  private final ZcRangedValueModel cmAdjustingFlash
    = new ZcRangedValueModel(0, 10);
  
  private final EiTriggerable cmAdjustingTimerSetting = new EiTriggerable() {
    @Override public void ccTrigger() {
      cmAdjustingTimerPL.ccSetText(String.format(
        "[a]:%2ds", (int)EcConst.ccToSecondCount(mnAdjustingFPS)
      ));
      cmAdjustingTM.ccSetTime(mnAdjustingFPS);
    }//+++
  };//***
  
  private static final Runnable O_ADJ_TM_IPT = new Runnable() {
    @Override public void run() {
      String lpEntered = ScConst.ccGetStringByInputBox(
        "[1 ~ 99] as second for adjusting",
        Integer.toString((int)EcConst.ccToSecondCount(mnAdjustingFPS))
      );
      int lpFixed = VcNumericUtility.ccParseIntegerString(lpEntered);
      mnAdjustingFPS = EcConst.ccToFrameCount(
        (float)constrain(lpFixed, 1, 99)
      );
      VcLocalCoordinator.ccInvokeLater(self.cmAdjustingTimerSetting);
    }//+++
  };//***
  
  private final ZcPIDController cmController
    = new ZcPIDController(0f,240f,0.02f,0.33f);
  
  public final EcElement cmModeTargetPL
    = new EcElement("[t]Target");
  
  public final EcElement cmModeDeadPL
    = new EcElement("[d]Dead");
  
  public final EcElement cmModeProportionPL
    = new EcElement("[p]Prop");
  
  public final EcElement cmRunPL
    = new EcElement("[r]RUN");
  
  private boolean cmAdjustingFlag,cmSamplingFlag,cmRunningFlag;
  
  private char cmMouseWheelMode = 't';

  @Override public void setup(){
    
    //-- pre
    size(320,240);
    EcConst.ccSetupSketch(this);
    VcLocalCoordinator.ccGetInstance().ccInit(this);
    self = this;
    
    //-- local ui ** range
    cmModeTargetPL.ccSetLocation(10, 135);
    cmModeDeadPL.ccSetLocation(cmModeTargetPL, 'b', 5);
    cmModeProportionPL.ccSetLocation(cmModeDeadPL, 'b', 5);
    cmModeTargetPL.ccSetSize(cmModeProportionPL);
    cmModeDeadPL.ccSetSize(cmModeProportionPL);
    cmModeTargetPL.ccSetIsActivated(true);
    
    //-- local ui ** clock
    cmRunPL.ccSetLocation(cmModeTargetPL, 'r', 5);
    cmSamplingTimerPL.ccSetLocation(cmRunPL, 'b', 5);
    cmAdjustingTimerPL.ccSetLocation(cmSamplingTimerPL, 'b', 5);
    cmSamplingTimerPL.ccSetSize(cmAdjustingTimerPL);
    cmRunPL.ccSetSize(cmSamplingTimerPL);
    
    //--- local ui ** pack
    VcLocalCoordinator.ccAddAll(this);
    
  }//+++

  @Override public void draw(){
    
    //-- pre
    background(0);
   
    //-- logic ** clock ** sampling
    cmSamplingTM.ccAct(true);
    cmSamplingFlash.ccShift(-1);
    cmSamplingFlag=cmSamplingTM.ccAtEdge() & cmRunningFlag;
    if(cmSamplingFlag){cmSamplingFlash.ccSetToMaximum();}//..?
    
    //-- logic ** clock ** adjusting
    cmAdjustingTM.ccAct(true);
    cmAdjustingFlash.ccShift(-1);
    cmAdjustingFlag=cmAdjustingTM.ccAtEdge() & cmRunningFlag;
    if(cmAdjustingFlag){cmAdjustingFlash.ccSetToMaximum();}//..?
    
    //-- logic ** controller
    final int lpConstCurrent = height - mouseY;
    if(cmRunningFlag){
      cmController.ccRun(lpConstCurrent,cmSamplingFlag,cmAdjustingFlag);
    }else{
      cmController.ccRun(lpConstCurrent);
    }//..?
    
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
    fill((0x66+cmAdjustingFlash.ccGetValue()*3)&0xFF,0x66);
    rect(
      160+C_VIS_GAP,lpConstShifted-lpConstProportionZ/2,
      width/2-C_VIS_GAP*2,lpConstProportionZ
    );
    
    //-- visualize ** controller ** dead zone
    stroke(0xFF119911);
    fill((0x66+cmSamplingFlash.ccGetValue()*3)&0xFF,0x66);
    rect(
      160+C_VIS_GAP*2,lpConstShifted-lpConstDeadZ/2,
      width/2-C_VIS_GAP*4,lpConstDeadZ
    );
    
    //-- visualize ** controller ** current
    stroke(0xFF,0xAA);ccDrawLineH(mouseY);
    fill(0xFF);
    text(String.format("[pv:%3d]", lpConstCurrent),mouseX,mouseY-20);
    
    //-- visualize ** pop
    noStroke();
    
    //-- local ui
    cmSamplingTimerPL.ccSetIsActivated(EcElement.ccIsKeyPressed('s'));
    cmAdjustingTimerPL.ccSetIsActivated(EcElement.ccIsKeyPressed('a'));
    VcLocalCoordinator.ccUpdate();
    
    //-- inspect
    fill(0x55,0xAA);
    rect(10,10,125,105);
    fill(0xEE);
    text(
      "ctrl-"+VcStringUtility.ccBreakObject(cmController),
      12, 12
    );
    
  }//++~
  
  @Override public void keyPressed(){
    if(key=='q'){exit();}
    switch(key){
      case 'a':SwingUtilities.invokeLater(O_ADJ_TM_IPT);break;
      case 's':SwingUtilities.invokeLater(O_SAMP_TM_IPT);break;
      case 'r':
        cmRunningFlag = !cmRunningFlag;
        cmRunPL.ccSetIsActivated(cmRunningFlag);
      break;
      case 't':case 'd':case 'p':
        cmMouseWheelMode=key;
        cmModeTargetPL.ccSetIsActivated(cmMouseWheelMode=='t');
        cmModeDeadPL.ccSetIsActivated(cmMouseWheelMode=='d');
        cmModeProportionPL.ccSetIsActivated(cmMouseWheelMode=='p');
      break;
      default:break;
    }//..?
  }//+++

  @Override public void mouseWheel(MouseEvent me) {
     int lpCount=-1*(int)(me.getAmount());
     switch(cmMouseWheelMode){
       case 't':
       {
         float lpTarget = cmController.tstGetTarget();
         lpTarget += ((float)(lpCount));
         cmController.ccSetTarget(lpTarget);
       }
       break;
       case 'd':
       {
         float lpDead = cmController.tstGetDeadF();
         lpDead += ((float)(lpCount))/100f;
         cmController.ccSetDead(constrain(
           lpDead,
           0.02f, cmController.tstGetProportionF()
         ));
       }
       break;
       case 'p':
       {
         float lpProp = cmController.tstGetProportionF();
         lpProp += ((float)(lpCount))/100f;
         cmController.ccSetProportion(constrain(lpProp,
           cmController.tstGetDeadF(), 0.98f
         ));
       }  
       break;
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
