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

import java.util.Arrays;
import kosui.ppputil.VcConst;
import processing.core.PApplet;

public class ZcPIDController {
  
  //-- control
  private float cmAnalogOutput;
  
  //-- proportional
  private float cmRangeMinimum, cmRangeMaximum;
  private float cmDeadFACT,cmProportionFACT;
  private float cmDeadPositive,cmDeadNegative;
  private float cmProportionPositive,cmProportionNegative;
  
  //-- derivative
  private boolean cmHistoryFilled;
  private int cmHistoryHead;
  private float cmProcessAverage, cmGradientAverage;
  private final float[] cmDesProcessHistory = new float[]{0,0,0,0, 0,0,0,0};
  private final float[] cmDesGradientHistory = new float[]{0,0,0,0, 0,0,0,0};
  private float cmSamplingDead;
  
  //-- integral
  private float cmTarget,cmShiftedTarget,cmAdjustWidth;
  
  //===
  
  public ZcPIDController(
    float pxMin, float pxRange, float pxDeadFACT, float pxProportionFACT
  ){
    cmAnalogOutput = 0f;
    ccSetRange(pxMin, pxRange);
    ccSetDead(pxDeadFACT);
    ccSetProportion(pxProportionFACT);
    ccSetTarget(cmRangeMinimum+ccGetRange()/2);
    ccSetSamplingDead(cmDeadPositive-cmDeadNegative);
    ccSetAdjustWidth(cmDeadPositive-cmDeadNegative);
  }//++!
  
  //=== 
  
  /* 1 */ public void ccRun(float pxCurrent){
    cmAnalogOutput = ssCalculateOutput(pxCurrent);
  }//++~
  
  /* 1 */ public void ccRun(
    float pxCurrent, boolean pxSamplePLS, boolean pxAdjustPLS
  ){
    
    //-- sampling
    if(pxSamplePLS){
      ccOfferProcessValue(pxCurrent);
    }//..?

    //--  adjusting
    boolean lpHistoryCondition = cmHistoryFilled
      && (Math.abs(cmGradientAverage)<=cmSamplingDead);
    if(pxAdjustPLS && lpHistoryCondition){
        ssAdjustTarget();
        ssCalculateDeadZone();
        ssCalculateProportionZone();
    }//..?

    //-- output
    cmAnalogOutput = ssCalculateOutput(cmProcessAverage);
      
  }//++~
  
  public final void ccRun(){
    cmAnalogOutput = ssCalculateOutput(cmProcessAverage);
  }//++~
  
  //===
  
  private float ssCalculateOutput(float pxInput){
      if(pxInput < cmProportionNegative){
        return 1f;
      }else
      if(pxInput > cmProportionPositive){
        return -1f;
      }else
      if(pxInput > cmDeadPositive){
        return -1f * PApplet.map(pxInput-cmDeadPositive,
          0f,cmProportionPositive-cmDeadPositive,
          0.001f,0.999f
        );
      }else
      if(pxInput < cmDeadNegative){
        return PApplet.map(pxInput-cmProportionNegative,
          cmDeadNegative-cmProportionNegative,0f,
          0.001f,0.999f
        );
      }else{
        return 0f;
      }//..?
  }//+++
  
  private void ssCalculateDeadZone(){
    cmDeadNegative = cmShiftedTarget - ccGetRange()*cmDeadFACT;
    cmDeadPositive = cmShiftedTarget + ccGetRange()*cmDeadFACT;
  }//+++
  
  private void ssCalculateProportionZone(){
    cmProportionNegative = cmShiftedTarget - ccGetRange()*cmProportionFACT;
    cmProportionPositive = cmShiftedTarget + ccGetRange()*cmProportionFACT;
  }//+++
  
  private void ssAdjustTarget(){
    if(cmProcessAverage<(cmTarget-cmSamplingDead)){
      cmShiftedTarget+=cmAdjustWidth;
    }//..?
    if(cmProcessAverage>(cmTarget+cmSamplingDead)){
      cmShiftedTarget-=cmAdjustWidth;
    }//..?
    cmShiftedTarget=PApplet.constrain(
      cmShiftedTarget,
      cmRangeMinimum, cmRangeMaximum
    );
  }//+++
  
  //===
  
  public final void ccOfferProcessValue(float pxValue){
      
    //-- offering
    int lpLogicalPrev = (cmHistoryHead-1)&0x07;
    cmDesProcessHistory[cmHistoryHead]=pxValue;
    cmDesGradientHistory[cmHistoryHead]=
      cmDesProcessHistory[cmHistoryHead]
        - cmDesProcessHistory[lpLogicalPrev];
    cmHistoryHead++;cmHistoryHead&=0x07;

    //-- fulfilling
    if(cmHistoryHead>=7){cmHistoryFilled=true;}

    //-- average calculation
    if(!cmHistoryFilled){
      cmProcessAverage = cmDesProcessHistory[lpLogicalPrev];
      cmGradientAverage = cmDesGradientHistory[lpLogicalPrev];
    }else{

      //-- ** process
      cmProcessAverage = 0f;
      for(int i=0;i<8;i++){cmProcessAverage += cmDesProcessHistory[i];}
      cmProcessAverage/=8f;

      //-- ** gradient
      cmGradientAverage = 0f;
      for(int i=0;i<8;i++){cmGradientAverage += cmDesGradientHistory[i];}
      cmGradientAverage/=8f;

    }//..?
      
  }//++<
  
  public final void ccSetRange(float pxMin, float pxRange){
    cmRangeMinimum = pxMin;
    cmRangeMaximum = cmRangeMinimum + Math.abs(pxRange);
  }//++<
  
  public final void ccSetTarget(float pxReal){
    cmTarget = PApplet.constrain(pxReal, cmRangeMinimum, cmRangeMaximum);
    ccReset();
    ssCalculateDeadZone();
    ssCalculateProportionZone();
  }//++<
      
  public final void ccSetDead(float pxFactor){
    cmDeadFACT = PApplet.constrain(pxFactor, 0.01f, 0.99f);
    ssCalculateDeadZone();
  }//++<
  
  public final void ccSetProportion(float pxFactor){
    cmProportionFACT = PApplet.constrain(pxFactor, 0.01f, 0.99f);
    ssCalculateProportionZone();
  }//++<
  
  public final void ccSetSamplingDead(float pxFactor){
    cmSamplingDead = Math.abs(pxFactor);
  }//++<
  
  public final void ccSetAdjustWidth(float pxFactor){
    cmAdjustWidth = Math.abs(pxFactor);
  }//++<
  
  public final void ccReset(){
    cmShiftedTarget = cmTarget;
    cmHistoryFilled=false;
    cmHistoryHead=0;
    cmProcessAverage=0f;
    Arrays.fill(cmDesProcessHistory, 0f);
    cmGradientAverage=0f;
    Arrays.fill(cmDesGradientHistory, 0f);
  }//++<
  
  //===
  
  public final float ccGetAnalogOutput(){
    return cmAnalogOutput;
  }//++>
  
  public final float ccGetMinusTrimmed(){
    return cmAnalogOutput>=0f?cmAnalogOutput:0f;
  }//++>
  
  public final float ccGetReverselyTrimmed(){
    return cmAnalogOutput<=0f?cmAnalogOutput:0f;
  }//++>
  
  public final boolean ccGetPositiveOutput(){
    return cmAnalogOutput>0f;
  }//++>
  
  public final boolean ccGetNegativeOutput(){
    return cmAnalogOutput<0f;
  }//++>
  
  public final float ccGetRange(){
    return cmRangeMaximum - cmRangeMinimum;
  }//++>
  
  //=== 
  
  @Deprecated public final float tstGetProcessAverage(){
    return cmProcessAverage;
  }//++>
  
  @Deprecated public final float tstGetShiftedTarget(){
    return cmShiftedTarget;
  }//++>
  
  @Deprecated public final float tstGetTarget(){
    return cmTarget;
  }//++>
  
  @Deprecated public final float tstGetDeadF(){
    return cmDeadFACT;
  }//++>
  
  @Deprecated public final float tstGetDeadP(){
    return cmDeadPositive;
  }//++>
  
  @Deprecated public final float tstGetDeadN(){
    return cmDeadNegative;
  }//++>
  
  @Deprecated public final float tstGetProportionF(){
    return cmProportionFACT;
  }//++>
  
  @Deprecated public final float tstGetProportionP(){
    return cmProportionPositive;
  }//++>
  
  @Deprecated public final float tstGetProportionN(){
    return cmProportionNegative;
  }//++>
  
  //===
  
  @Override public String toString(){
    StringBuilder lpRes
      = new StringBuilder(ZcPIDController.class.getSimpleName());
    lpRes.append('@');
    lpRes.append(Integer.toHexString(hashCode()));
    lpRes.append('$');
    lpRes.append(String.format(
      "[tgt:%3.1f][stt:%3.1f]|[d:(%3.1f,%3.1f)]|[p:(%3.1f,%3.1f)]|",
      cmTarget,cmShiftedTarget,
      cmDeadNegative,cmDeadPositive,
      cmProportionNegative,cmProportionPositive
    ));
    lpRes.append(String.format(
      "[avp:%3.1f][avg:%3.1f]|[opt:%.2f]|",
      cmProcessAverage,cmGradientAverage,cmAnalogOutput
    ));
    lpRes.append(VcConst.C_V_NEWLINE);
    return lpRes.toString();
  }//++>
  
}//***eof
