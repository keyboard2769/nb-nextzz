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

import java.util.LinkedList;
import java.util.List;
import kosui.ppplocalui.EcComponent;
import kosui.ppplocalui.EcConst;
import kosui.ppplocalui.EcElement;
import kosui.ppplogic.ZcHookFlicker;
import kosui.ppplogic.ZcRoller;
import kosui.ppputil.VcLocalCoordinator;
import kosui.ppputil.VcLocalTagger;
import kosui.ppputil.VcStringUtility;
import nextzz.pppsimulate.ZcChainController;
import processing.core.PApplet;

public class CaseFeederChain extends PApplet{

  private final ZcRoller cmRoller = new ZcRoller();
  
  private final ZcChainController cmController = new ZcChainController(2, 10);
  
  private final EcElement cmRunPL = new EcElement("run");
  
  private final List<EcElement> cmListOfAutoPulse
    = new LinkedList<EcElement>();
  private final List<EcElement> cmListOfFeeder
    = new LinkedList<EcElement>();
  private final List<ZcHookFlicker> cmListOfHook
    = new LinkedList<ZcHookFlicker>();
  
  @Override public void setup() {
    
    //--
    size(320,240);
    EcConst.ccSetupSketch(this);
    VcLocalCoordinator.ccInit(this);
    
    //--
    for(int i=0;i<10;i++){
      cmListOfAutoPulse.add(new EcElement("ap"+nf(i,1)));
      cmListOfFeeder.add(new EcElement("vf"+nf(i,1)));
      cmListOfHook.add(new ZcHookFlicker());
    }///..~
    cmListOfAutoPulse.get(0).ccHide();
    cmListOfFeeder.get(0).ccHide();
    cmListOfAutoPulse.get(9).ccHide();
    cmListOfFeeder.get(9).ccHide();
    cmListOfAutoPulse.get(1).ccSetLocation(5, 120);
    for(int i=2;i<=8;i++){
      cmListOfAutoPulse.get(i).ccSetLocation(cmListOfAutoPulse.get(i-1), 5, 0);
    }//..~
    for(int i=1;i<=8;i++){
      cmListOfFeeder.get(i).ccSetSize(cmListOfAutoPulse.get(i));
      cmListOfFeeder.get(i).ccSetLocation(cmListOfAutoPulse.get(i), 0,3);
    }//..~
    for(EcElement it : cmListOfAutoPulse){VcLocalCoordinator.ccAddElement(it);}
    for(EcElement it : cmListOfFeeder){VcLocalCoordinator.ccAddElement(it);}
    cmRunPL.ccSetLocation(cmListOfFeeder.get(1),0, 5);
    cmRunPL.ccSetEndX(cmListOfFeeder.get(8).ccEndX());
    VcLocalCoordinator.ccAddElement(cmRunPL);
    
    //--
    VcLocalTagger.ccInit(this);
  
  }//+++

  @Override public void draw() {
    
    background(0);
    cmRoller.ccRoll();
    
    //--
    cmController.ccSetConfirmedAt(10, true);
    cmController.ccSetRun(EcComponent.ccIsKeyPressed('r'));
    cmController.ccRun();
    
    //--
    for(int i=1;i<=8;i++){
      boolean lpPulse=cmController.ccGetPulseAt(i);
      cmListOfAutoPulse.get(i).ccSetIsActivated(lpPulse);
      cmListOfHook.get(i).ccHook(lpPulse);
      cmListOfFeeder.get(i).ccSetIsActivated(cmListOfHook.get(i).ccIsHooked());
    }//~
    
    //--
    cmRunPL.ccSetIsActivated(cmController.ccGetFlasher(cmRoller.ccIsAbove(7)));
    VcLocalCoordinator.ccUpdate();
    
    //--
    fill(0xFF);
    text(
      VcStringUtility.ccBreakObject(cmController),
      160,5
    );
    
    //--
    VcLocalTagger.ccTag("[W]", EcComponent.ccIsKeyPressed('w'));
    VcLocalTagger.ccTag("[S]", EcComponent.ccIsKeyPressed('s'));
    VcLocalTagger.ccTag("[A]", EcComponent.ccIsKeyPressed('a'));
    VcLocalTagger.ccTag("[D]", EcComponent.ccIsKeyPressed('d'));
    VcLocalTagger.ccTag("roll", cmRoller.ccGetValue());
    VcLocalTagger.ccStabilize();
    
  }//+++

  @Override public void keyPressed() {
    if(key=='q'){exit();}
  }//+++

  //===
  
  public static void main(String[] args) {
    PApplet.main(CaseFeederChain.class.getCanonicalName());
  }//!!!
  
}//***eof
