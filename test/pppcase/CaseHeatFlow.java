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
import kosui.ppplocalui.EcSlider;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplogic.ZcImpulsiveTimer;
import kosui.ppplogic.ZcReal;
import kosui.ppplogic.ZcRoller;
import kosui.ppplogic.ZcTimer;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcLocalCoordinator;
import kosui.ppputil.VcLocalTagger;
import kosui.ppputil.VcNumericUtility;
import processing.core.PApplet;

public class CaseHeatFlow extends PApplet{
  
  private final ZcRoller cmRoller=new ZcRoller();
  
  private final ZcReal
    cmAtmosphere  = new ZcReal(27f, true),
    cmBurnerBlast = new ZcReal(32f, true),
    cmDryerBody = new ZcReal(34f),
    //--
    cmTH1 = new ZcReal(16f),
    cmTH2 = new ZcReal(12f),
    cmTH4 = new ZcReal(14f)
  ;//,,,
  
  public final EcElement
    cmCoolingDamperPL = new EcElement("-mvo-"),
    cmAggregateSupplyTG = new EcElement("aG"),
    cmSandBinContentTG = new EcElement("sAND"),
    cmFireTG = new EcElement("fIRE")
  ;//,,,
  
  public final EcSlider
    cmBurnerDegreeSLD = new EcSlider(),
    cmTonPerHourSLD = new EcSlider()
  ;//,,,
  
  public final EcValueBox
    cmBurnerDegreeCB = new EcValueBox("vb", "000 %"),
    cmTonPerHourCB = new EcValueBox("vd", "000 tph")
  ;//,,,
  
  private final ZcTimer cmCoolingDamperTM = new ZcImpulsiveTimer(32);
  private boolean dcCoolingDamperMV;
  
  @Override public void setup() {
    
    //-- pre
    size(320,240);
    VcConst.ccSetDoseLog(true);
    EcConst.ccSetupSketch(this);
    VcLocalCoordinator.ccInit(this);
    VcLocalTagger.ccInit(this);
    
    //-- relocation
    cmCoolingDamperPL.ccSetLocation(160, 5);
    cmAggregateSupplyTG.ccSetLocation(cmCoolingDamperPL, 0,2);
    cmSandBinContentTG.ccSetLocation(cmAggregateSupplyTG, 2, 0);
    cmFireTG.ccSetLocation(cmSandBinContentTG, 2,0);
    cmCoolingDamperPL.ccSetEndX(cmFireTG.ccEndX());
    
    //-- pack
    cmBurnerDegreeSLD.ccSetSize(8, 80);
    cmTonPerHourSLD.ccSetSize(8, 80);
    cmBurnerDegreeCB.ccSetLocation(160, 120);
    cmTonPerHourCB.ccSetLocation(cmBurnerDegreeCB, 8, 0);
    cmBurnerDegreeSLD.ccSetLocation(
      cmBurnerDegreeCB.ccCenterX()-cmBurnerDegreeSLD.ccGetW()/2,
      cmBurnerDegreeCB.ccEndY()+8
    );
    cmTonPerHourSLD.ccSetLocation(
      cmTonPerHourCB.ccCenterX()-cmTonPerHourSLD.ccGetW()/2,
      cmTonPerHourCB.ccEndY()+8
    );
    
    //-- register
    VcLocalCoordinator.ccAddAll(this);
    
  }//++!

  @Override public void draw() {
    
    //-- pre
    background(0);
    cmRoller.ccRoll();
    
    //-- preload
    float cmBurnerDegreeRT=VcNumericUtility
      .ccProportion(cmBurnerDegreeSLD.ccGetContentValue());
    int cmTonPerHourBYTE=cmTonPerHourSLD.ccGetContentValue();
    int lpFixedEntranceQUAD = cmTonPerHourBYTE/4+8;
    int lpFixedChuteQUAD = (256-cmTonPerHourBYTE)/4+8;
    
    //-- heat flow 
    cmAtmosphere.ccEffect(random(24f, 29f));
    cmBurnerBlast.ccEffect(
      cmFireTG.ccIsActivated()
       ? cmBurnerDegreeRT*2900f
       : cmAtmosphere.ccGet()
    );
    if(!cmFireTG.ccIsActivated()){
      ZcReal.ccTransfer(cmBurnerBlast, cmAtmosphere);
    }//..?
    if(!cmFireTG.ccIsActivated() || dcCoolingDamperMV){
      ZcReal.ccTransfer(cmDryerBody, cmAtmosphere);
    }//..?
    ZcReal.ccTransfer(cmTH1, cmAtmosphere);
    ZcReal.ccTransfer(cmTH2, cmAtmosphere);
    ZcReal.ccTransfer(cmTH4, cmAtmosphere);
    //-- heat flow **
    ZcReal.ccTransfer(cmBurnerBlast, cmDryerBody,64);
    ZcReal.ccTransfer(cmDryerBody, cmTH2,lpFixedEntranceQUAD);
    if(cmAggregateSupplyTG.ccIsActivated()){
     ZcReal.ccTransfer(cmDryerBody, cmTH1);
    }
    if(cmSandBinContentTG.ccIsActivated()){
      ZcReal.ccTransfer(cmTH1, cmTH4);
    }//..?
    
    //-- output
    cmCoolingDamperTM.ccAct(cmTH2.ccGet()>190f);
    dcCoolingDamperMV=cmCoolingDamperTM.ccIsUp();
    
    //-- local
    cmCoolingDamperPL.ccSetIsActivated(dcCoolingDamperMV);
    cmBurnerDegreeCB.ccSetValue((int)(cmBurnerDegreeRT*100f));
    cmTonPerHourCB.ccSetValue((int)cmTonPerHourBYTE);
    VcLocalCoordinator.ccUpdate();
    
    //-- tag
    VcLocalTagger.ccTag(".d->2", lpFixedEntranceQUAD);
    VcLocalTagger.ccTag(".d->1", lpFixedChuteQUAD);
    VcLocalTagger.ccTag(".burner", cmBurnerBlast.ccGet());
    VcLocalTagger.ccTag(".dryer", cmDryerBody.ccGet());
    VcLocalTagger.ccTag("@TH1", cmTH1.ccGet());
    VcLocalTagger.ccTag("@TH2", cmTH2.ccGet());
    VcLocalTagger.ccTag("@TH4", cmTH4.ccGet()*1.8f);
    //-- tag **
    VcLocalTagger.ccTag("$latency", 17f-frameRate);
    VcLocalTagger.ccTag("$roll", cmRoller.ccGetValue());
    VcLocalTagger.ccStabilize();
    
  }//++~

  @Override public void keyPressed() {
    if(key=='q'){exit();}
    switch(key){
      case 'a':cmAggregateSupplyTG.ccSetIsActivated();break;
      case 's':cmSandBinContentTG.ccSetIsActivated();break;
      case 'f':cmFireTG.ccSetIsActivated();break;
      default:break;
    }//..?
  }//+++
  
  public static void main(String[] args) {
    PApplet.main(CaseHeatFlow.class.getCanonicalName());
  }//..!
  
}//**eof
