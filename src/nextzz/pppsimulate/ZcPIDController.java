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

package nextzz.pppsimulate;

import kosui.ppputil.VcConst;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcStringUtility;
import processing.core.PApplet;

public class ZcPIDController {
  
  private float
    cmProsessValue,
    cmTarget,cmBaseTarget,cmShiftedTarget,cmAdjustRatio,
    cmDeadZone,cmDeadZoneP,cmDeadZoneN,
    cmProportion,cmProportionP,cmProportionN,
    cmAnalogOutput
  ;//...
  
  private final boolean cmIsRelative;
  
  public ZcPIDController(
    float pxInit, float pxBase,
    float pxProportion, float pxDead,
    boolean pxRelative
  ){
    ccSetTarget(pxInit);
    ccSetBaseTarget(pxBase);
    ccSetProportion(pxProportion, pxDead);
    ssApplyProportion();
    ccResetShiftedTarget();
    cmIsRelative=pxRelative;
    cmProsessValue=0f;
    cmAnalogOutput=0f;
    cmAdjustRatio=4f;
  }//++!
  
  public ZcPIDController(
    float pxBase,
    float pxProportion, float pxDead,
    boolean pxRelative
  ){
    this(pxBase, pxBase, pxProportion, pxDead, pxRelative);
  }//++!
  
  public ZcPIDController(
    float pxBase,
    float pxProportion, float pxDead
  ){
    this(pxBase, pxBase, pxProportion, pxDead, false);
  }//++!
  
  public ZcPIDController(){
    this(50f,100f, 0.2f, 0.02f,false);
  }//++!
  
  //=== 
  
  /* 1 */ public void ccRun(float pxTarget, float pxCurrent){
    ccSetTarget(pxTarget);
    ccResetShiftedTarget();
    ccSetProcessValue(pxCurrent);
    ccRun();
  }//++~
  
  /* 1 */ public void ccRun(float pxCurrent){
    ccSetProcessValue(pxCurrent);
    ccRun();
  }//++~
  
  /* 1 */ public void ccRun(
    float pxCurrent,boolean pxAdjustPLS, boolean pxSamplePLS
  ){
    ccSetProcessValue(pxCurrent, pxSamplePLS);
    ccAdjustTarget(pxAdjustPLS);
    ccRun();
  }//++~
  
  public final void ccRun(){
    
    //--
    ssApplyProportion();
    
    //--
    cmAnalogOutput=0.0f;
    if(cmProsessValue>=cmProportionN && cmProsessValue<=cmDeadZoneN){
      cmAnalogOutput=PApplet
        .map(cmProsessValue,cmProportionN,cmDeadZoneN,0.999f, 0.001f);
    }//..?
    if(cmProsessValue>=cmDeadZoneP && cmProsessValue<=cmProportionP){
      cmAnalogOutput=PApplet
        .map(cmProsessValue,cmDeadZoneP,cmProportionP,0.001f,-0.999f);
    }//..?
    cmAnalogOutput=VcNumericUtility.ccRoundForTwoAfter(cmAnalogOutput);
    if(cmProsessValue<cmProportionN){cmAnalogOutput=1.0f;}
    if(cmProsessValue>cmProportionP){cmAnalogOutput=-1.0f;}
  
  }//++~
  
  private void ssApplyProportion(){
    if(cmIsRelative){ssApplyRelativeProportion();}
    else{ssApplyAbsoluteProportion();}
  }//+++
  
  private void ssApplyAbsoluteProportion(){
    cmDeadZoneN   = cmShiftedTarget-(cmBaseTarget*  cmDeadZone);
    cmDeadZoneP   = cmShiftedTarget+(cmBaseTarget*  cmDeadZone);
    cmProportionN = cmShiftedTarget-(cmBaseTarget*cmProportion);
    cmProportionP = cmShiftedTarget+(cmBaseTarget*cmProportion);
  }//+++
  
  private void ssApplyRelativeProportion(){
    if(cmShiftedTarget==0){
      cmDeadZoneN=-cmDeadZone;
      cmDeadZoneP=cmDeadZone;
      cmProportionN=-cmProportion;
      cmProportionP=cmProportion;
    }else{
      cmDeadZoneN   = cmShiftedTarget*(1f - cmDeadZone);
      cmDeadZoneP   = cmShiftedTarget*(1f + cmDeadZone);
      cmProportionN = cmShiftedTarget*(1f - cmProportion);
      cmProportionP = cmShiftedTarget*(1f + cmProportion);
    }//..?
  }//+++
  
  public final void ccAdjustTarget(boolean pxPulse){
    if(!pxPulse){return;}
    float lpDiff =cmTarget-cmProsessValue;
    float lpWidth=cmTarget*(cmDeadZone/cmAdjustRatio);
    if(lpDiff> lpWidth){cmShiftedTarget+=lpWidth;}
    if(lpDiff<-lpWidth){cmShiftedTarget-=lpWidth;}
  }//+++
  
  //===
  
  public final void ccSetProcessValue(float pxValue){
    cmProsessValue=pxValue;
  }//++<
  
  public final void ccSetProcessValue(float pxValue, boolean pxPulse){
    if(!pxPulse){return;}
    cmProsessValue=pxValue;
  }//++<
  
  public final void ccSetTarget(float pxTarget){
    cmTarget=pxTarget>=0f?pxTarget:1f;
  }//++<
  
  public final void ccSetBaseTarget(float pxBase){
    cmBaseTarget=pxBase>=0f?pxBase:1f;
  }//++<
  
  public final void ccSetProportion(
    float pxProportion, float pxDead
  ){
    cmProportion=PApplet.constrain(pxProportion, 0f, 1f);
    cmDeadZone=PApplet.constrain(pxDead, 0f, 1f);
    if(cmDeadZone>cmProportion){cmDeadZone=cmProportion;}
  }//+++
  
  public final void ccSetAdjustRatio(int pxRatio){
    int lpFixed=pxRatio&0xFF;
    cmAdjustRatio=lpFixed==0?1f:((float)lpFixed);
  }//++<
  
  public final void ccResetShiftedTarget(){
    cmShiftedTarget=cmTarget;
  }//++<
  
  //===
  
  public final float ccGetShiftedTarget(){
    return cmShiftedTarget;
  }//++<
  
  public final float ccGetAnalogOutput(){
    return cmAnalogOutput;
  }//++<
  
  public final float ccGetMinusTrimmed(){
    if(cmAnalogOutput<=0.0f){return 0.0f;}
    else{return cmAnalogOutput;}
  }//++<
  
  public final float ccGetReverselyTrimmed(){
    if(cmAnalogOutput>=0.0f){return 0.0f;}
    else{return PApplet.abs(cmAnalogOutput);}
  }//++<
  
  public final boolean ccGetPositiveOutput(){
    return cmAnalogOutput>0f;
  }//++<
  
  public final boolean ccGetNegativeOutput(){
    return cmAnalogOutput<0f;
  }//++<
  
  //=== 

  @Override public String toString(){
    StringBuilder lpRes
      = new StringBuilder(ZcPIDController.class.getSimpleName());
    lpRes.append('@');
    lpRes.append(Integer.toHexString(hashCode()));
    lpRes.append('$');
    lpRes.append(VcStringUtility.ccPackupPairedTag("O",
      cmAnalogOutput));
    lpRes.append('|');
    lpRes.append(VcStringUtility.ccPackupPairedTag("pv",
      VcNumericUtility.ccFormatPointTwoFloat(cmProsessValue)));
    lpRes.append(VcStringUtility.ccPackupPairedTag("sv",
      VcNumericUtility.ccFormatPointTwoFloat(cmTarget)));
    lpRes.append(VcStringUtility.ccPackupPairedTag("svi", 
      VcNumericUtility.ccFormatPointTwoFloat(cmShiftedTarget)));
    //[fortest]::delete them later
    //lpRes.append('|');
    //lpRes.append(VcStringUtility.ccPackupPairedTag("d", 
    //  VcNumericUtility.ccFormatPointTwoFloat(cmDeadZone)));
    //lpRes.append(VcStringUtility.ccPackupPairedTag("p", 
    //  VcNumericUtility.ccFormatPointTwoFloat(cmProportion)));
    lpRes.append('|');
    lpRes.append(VcStringUtility.ccPackupPairedTag("dN", 
      VcNumericUtility.ccFormatPointTwoFloat(cmDeadZoneN)));
    lpRes.append(VcStringUtility.ccPackupPairedTag("dP",
      VcNumericUtility.ccFormatPointTwoFloat(cmDeadZoneP)));
    lpRes.append(VcStringUtility.ccPackupPairedTag("pN",
      VcNumericUtility.ccFormatPointTwoFloat(cmProportionN)));
    lpRes.append(VcStringUtility.ccPackupPairedTag("pP",
      VcNumericUtility.ccFormatPointTwoFloat(cmProportionP)));
    return lpRes.toString();
  }//++>
  
}//***eof
