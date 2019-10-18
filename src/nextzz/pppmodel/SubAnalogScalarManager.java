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

package nextzz.pppmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import kosui.ppplogic.ZcRevisedScaledModel;
import kosui.ppplogic.ZcScaledModel;
import kosui.pppmodel.McPipedChannel;
import kosui.ppputil.VcLocalTagger;
import nextzz.pppdelegate.SubAnalogDelegator;
import nextzz.pppdelegate.SubFeederDelegator;

public final class SubAnalogScalarManager {
  
  public static final int C_I_THI_CHUTE      = 1;
  public static final int C_I_THII_ENTRANCE  = 2;
  public static final int C_I_THIII_PIPE     = 3;
  public static final int C_I_THIV_SAND      = 4;
  public static final int C_I_THVI_MIXER     = 6;
  //[todo]::C_I_RH_SURGE        =0xA
  //[todo]::C_II_RH_RECYCLE     =0xB
  //[todo]::C_IV_RH_GAS         =0xC
  //[todo]::C_VIII_RH_ENTRANCE  =0xD
  //[todo]::C_THVII_SILO_I    = 0x7
  //[todo]::C_THVIII_SILO_II  = 0x8
  
  //===
  
  public static final String Q_TYPE_CT   = "ct";  //.. current
  public static final String Q_TYPE_CELL = "cell";//.. weight
  public static final String Q_TYPE_DEG  = "deg"; //.. percetage
  public static final String Q_TYPE_TEMP = "temp";//.. celcius
  public static final String Q_TYPE_PRES = "pres";//.. kilo paska
  public static final String Q_TYPE_TPH  = "tph"; //.. ton
  public static final String Q_TYPE_MISC = "misc";//.. WTF??
  
  //===
  
  public static final Integer C_KEY_CELL_AG = 0x01;
  public static final Integer C_KEY_CELL_FR = 0x02;
  public static final Integer C_KEY_CELL_AS = 0x03;
  //[todo]::public static final Integer C_KEY_CELL_RC = 0x04;
  //[todo]::public static final Integer C_KEY_CELL_AD = 0x05;
  public static final Integer C_KEY_VB = 0x11;
  public static final Integer C_KEY_VD = 0x12;
  public static final Integer C_KEY_VE = 0x13;
  //[todo]::public static final Integer C_KEY_RB = 0x21;
  //[todo]::public static final Integer C_KEY_RD = 0x22;
  //[todo]::public static final Integer C_KEY_RE = 0x23;
  public static final Integer C_KEY_CA = 0x99;
  
  //===
  
  private static final SubAnalogScalarManager SELF
    = new SubAnalogScalarManager();
  public static final
  SubAnalogScalarManager ccRefer(){return SELF;}//+++
  private SubAnalogScalarManager(){}//++!
  
  private final RuntimeException O_RE_SCALA_NOT_FOUND
    = new RuntimeException
      ("unhandled errer occured while accessing scala object");
  
  private final TreeMap<Integer,ZcScaledModel> cmMapOfScala
    = new TreeMap<Integer, ZcScaledModel>();
  
  //===
  
  private final List<ZcScaledModel> cmListOfCTSlotScalar
    = new ArrayList<ZcScaledModel>(32);
  
  private final List<ZcScaledModel> cmListOfVFeederFluxScalar
    = new ArrayList<ZcScaledModel>(16);
  
  //===
  
  private final ZcRevisedScaledModel cmAGCellScalar
    = new ZcRevisedScaledModel(400, 3600, 0, 4000);
  
  private final ZcRevisedScaledModel cmFRCellScalar
    = new ZcRevisedScaledModel(400, 3600, 0, 5000);
  
  private final ZcRevisedScaledModel cmASCellScalar
    = new ZcRevisedScaledModel(400, 3600, 0, 5000);
  
  //[todo]:: private final ZcScaledModel cmRCCellScalar
  
  //[todo]:: private final ZcScaledModel cmADCellScalar
  
  //=== 
  
  private final ZcRevisedScaledModel cmThermoCouplScalar
    = new ZcRevisedScaledModel(1000, 4680, 0, 1472);
  
  public final McPipedChannel cmDesThermoCoupleBias = new McPipedChannel();
  
  public final McPipedChannel cmDesThermoCoupleOffset = new McPipedChannel();
  
  public final McPipedChannel cmDesThermoCelcius = new McPipedChannel();
  
  //===
  
  private final ZcScaledModel cmVBurnerDegreeScalar
    = new ZcScaledModel(400, 3600, 0, 100);
  
  private final ZcScaledModel cmVDryerPressureScalar
    = new ZcScaledModel(1500, 2500, 0, 200);
  
  private final ZcScaledModel cmVExfanDegreeScalar
    = new ZcScaledModel(400, 3600, 0, 100);
  
  //=== 
  
  //[todo]::cmRDryerPressureScalar
  //[todo]::cmRBurnerDegreeScalar
  //[todo]::cmRExfanDegreeScalar
  
  //===
    
  public final void ccInit(){
    
    //-- WORDY indexed
    for(int i=0;i<16;i++){
      
      //-- feeder flux
      //-- feeder flux ** V
      cmListOfVFeederFluxScalar.add(new ZcScaledModel(0, 900, 0, 500));
      //.. 500d -> 50.0f [ton]
      
      //-- thermocouple bias
      cmDesThermoCoupleBias.ccSet(i, 100);
      cmDesThermoCoupleOffset.ccSet(i, 0);
      
    }//+++
    
    //-- DOUBLY indexed
    for(int i=0;i<32;i++){
      //-- ct slot ..1000d -> 100.0f [A]
      cmListOfCTSlotScalar.add(new ZcScaledModel(0, 5000, 0, 1000));
    }//+++
    
    //-- file inii
    //[notyet]::cmAGCellScalar.ccSetupSpan..
    //[notyet]::cmFRCellScalar.ccSetupSpan..
    //[notyet]::cmASCellScalar.ccSetupSpan..
    //[notyet]::cmRCCellScalar.ccSetupSpan..
    //[notyet]::cmADCellScalar.ccSetupSpan..
    
    //-- general register
    cmMapOfScala.put(C_KEY_CELL_AG, cmAGCellScalar);
    cmMapOfScala.put(C_KEY_CELL_FR, cmFRCellScalar);
    cmMapOfScala.put(C_KEY_CELL_AS, cmASCellScalar);
    cmMapOfScala.put(C_KEY_VB, cmVBurnerDegreeScalar);
    cmMapOfScala.put(C_KEY_VD, cmVDryerPressureScalar);
    cmMapOfScala.put(C_KEY_VE, cmVExfanDegreeScalar);
    cmMapOfScala.put(C_KEY_CA, cmThermoCouplScalar);
    
  }//++!
  
  public final void ccLogic(){
    
    //-- cell
    cmAGCellScalar.ccRun(SubAnalogDelegator.mnAGCellAD);
    cmFRCellScalar.ccRun(SubAnalogDelegator.mnFRCellAD);
    cmASCellScalar.ccRun(SubAnalogDelegator.mnASCellAD);
    //[todo]::RC
    //[todo]::AD
    
    //-- ct
    for(int i=0;i<MainPlantModel.C_CTSLOT_CHANNEL_MAX;i++){
      cmListOfCTSlotScalar.get(i&31)
        .ccRun(SubAnalogDelegator.ccGetCTSlotAD(i));
    }//..~
    
    //-- th ** v
    for(int i=0;i<MainPlantModel.C_THERMO_VALID_MAX;i++){
      cmThermoCouplScalar.ccSetupReviser(
        cmDesThermoCoupleBias.ccGet(i),
        cmDesThermoCoupleOffset.ccGet(i)
      );
      cmThermoCouplScalar.ccRun(SubAnalogDelegator.ccGetThermoAD(i));
      cmDesThermoCelcius
        .ccSet(i,cmThermoCouplScalar.ccGetRevisedIntegerValue());
    }//..~
    
    //-- v combust
    cmVBurnerDegreeScalar.ccRun(SubAnalogDelegator.mnVBDegreeAD);
    cmVExfanDegreeScalar.ccRun(SubAnalogDelegator.mnVEDegreeAD);
    cmVDryerPressureScalar.ccRun(SubAnalogDelegator.mnVDPressureAD);
    
    //-- r combust
    
  }//++~
  
  //[todo]::..or public final void ccCalculateFeeder(){}//++~
  
  //=== 
  
  //=== feeder flux ** V
  
  synchronized public final
  void ccSetVFeederFluxRPMSpan(int pxIndex, int pxVal){
    cmListOfVFeederFluxScalar.get(pxIndex&15).ccSetInputSpan(pxVal&0xFFFF);
  }//++<
  
  synchronized public final
  int ccGetVFeederFluxRPMSpan(int pxIndex){
   return cmListOfVFeederFluxScalar.get(pxIndex&15).ccGetInputSpan();
  }//++>
  
  synchronized public final
  void ccSetVFeederFluxTPHSpan(int pxIndex, int pxVal){
    cmListOfVFeederFluxScalar.get(pxIndex&15).ccSetOutputSpan(pxVal&0xFFFF);
  }//++<
  
  synchronized public final
  int ccGetVFeederFluxTPHSpan(int pxIndex){
   return cmListOfVFeederFluxScalar.get(pxIndex&15).ccGetOutputSpan();
  }//++>
  
  synchronized public final
  int ccGetVFeederFluxTPH(int pxIndex){
    //[tofix]::why have we leave this??
    return cmListOfVFeederFluxScalar.get(pxIndex&15)
      .ccToScaledIntegerValue(SubFeederDelegator.ccGetVFeederSpeed(pxIndex));
  }//++>
  
  //=== feeder flux ** R
  //[todo]::ccSetRFeederFluxRPMSpan
  //[todo]::ccGetRFeederFluxRPMSpan
  //[todo]::ccSetRFeederFluxTPHSpan
  //[todo]::ccGetRFeederFluxTPHSpan
  //[todo]::ccGetRFeederFluxTPH
  
  //=== CT Slot
  
  synchronized public final void ccSetCTSlotREALSpan(int pxIndex, int pxVal){
    cmListOfCTSlotScalar.get(pxIndex&31).ccSetOutputSpan(pxVal&0xFFFF);
  }//++<
  
  synchronized public final int ccGetCTSlotREALSpan(int pxIndex){
    return cmListOfCTSlotScalar.get(pxIndex&31).ccGetOutputSpan();
  }//++>
  
  synchronized public final int ccGetCTSlotAMPR(int pxIndex){
    return cmListOfCTSlotScalar.get(pxIndex&31).ccGetScaledIntegerValue();
  }//++>
  
  //=== Cell ** proportion
  
  public final float ccGetAGProportion(){
    return ((float)cmAGCellScalar.ccGetRevisedIntegerValue())
      / ((float)cmAGCellScalar.ccGetOutputSpan());
  }//++>
  
  public final float ccGetFRProportion(){
    return ((float)cmFRCellScalar.ccGetRevisedIntegerValue())
      / ((float)cmFRCellScalar.ccGetOutputSpan());
  }//++>
  
  public final float ccGetASProportion(){
    return ((float)cmASCellScalar.ccGetRevisedIntegerValue())
      / ((float)cmASCellScalar.ccGetOutputSpan());
  }//++>
  
  //=== Cell ** KG
  
  synchronized public final int ccGetAGCellKG(){
    return cmAGCellScalar.ccGetRevisedIntegerValue();
  }//++>
  
  synchronized public final int ccGetFRCellKG(){
    return cmFRCellScalar.ccGetRevisedIntegerValue();
  }//++>
  
  synchronized public final int ccGetASCellKG(){
    return cmASCellScalar.ccGetRevisedIntegerValue();
  }//++>
  
  //[todo]:: ccGetRCCellKG
  //[todo]:: ccGetADCellKG
  
  //=== cell ** AD ** AG
  
  synchronized public final int ccToAGCellAD(int pxKG){
    return cmAGCellScalar.ccToUnrevisedInputValue(pxKG);//.. what if we have to count the tare?!
  }//++>
  
  synchronized public final int ccToAGCellAD(int pxKG, int pxEmptyKG){
    if(pxKG<=pxEmptyKG){return 0;}
    else{return ccToAGCellAD(pxKG);}//.. what if we have to count the tare?!
  }//++>
  
  //=== cell ** AD ** FR
  
  synchronized public final int ccToFRCellAD(int pxKG){
    return cmFRCellScalar.ccToUnrevisedInputValue(pxKG);//.. what if we have to count the tare?!
  }//++>
  
  synchronized public final int ccToFRCellAD(int pxKG, int pxEmptyKG){
    if(pxKG<=pxEmptyKG){return 0;}
    else{return ccToFRCellAD(pxKG);}//.. what if we have to count the tare?!
  }//++>
  
  //=== cell ** AD ** AS
  
  synchronized public final int ccToASCellAD(int pxKG){
    return cmASCellScalar.ccToUnrevisedInputValue(pxKG);//.. what if we have to count the tare?!
  }//++>
  
  synchronized public final int ccToASCellAD(int pxKG, int pxEmptyKG){
    if(pxKG<=pxEmptyKG){return 0;}
    else{return ccToASCellAD(pxKG);}//.. what if we have to count the tare?!
  }//++>
  
  //=== Cell ** tare ** AG
  
  synchronized public final void ccSetAGReviseOffsetKG(int pxOffset){
    cmAGCellScalar.ccSetReviseOffset(pxOffset);
  }//++<
  
  synchronized public final int ccGetAGReviseOffsetKG(){
    return cmAGCellScalar.ccGetReviseOffset();
  }//++>
  
  //=== Cell ** tare ** FR
  
  synchronized public final void ccSetFRReviseOffsetKG(int pxOffset){
    cmFRCellScalar.ccSetReviseOffset(pxOffset);
  }//++<
  
  synchronized public final int ccGetFRReviseOffsetKG(){
    return cmFRCellScalar.ccGetReviseOffset();
  }//++>
  
  //=== Cell ** tare ** AS
  
  synchronized public final void ccSetASReviseOffsetKG(int pxOffset){
    cmASCellScalar.ccSetReviseOffset(pxOffset);
  }//++<
  
  synchronized public final int ccGetASReviseOffsetKG(){
    return cmASCellScalar.ccGetReviseOffset();
  }//++>
  
  //=== V combust
  
  synchronized public final int ccGerVBurnerPercentage(){
    return cmVBurnerDegreeScalar.ccGetScaledIntegerValue();
  }//++>
  
  synchronized public final int ccGetVDryerKPA(){
    return cmVDryerPressureScalar.ccGetScaledIntegerValue();
  }//++>
  
  synchronized public final int ccGetVExfanPercentage(){
    return cmVExfanDegreeScalar.ccGetScaledIntegerValue();
  }//++>
  
  //=== R combust
  //[todo]::ccGerRBurnerPercentage
  //[todo]::ccGetRDryerKPA
  //[todo]::ccGetRExfanPercentage
  
  //=== general interface 
  
  synchronized public final
  Object[] ccGetScalaKeyList(){
    return cmMapOfScala.keySet().toArray();
  }//+++
  
  synchronized public final
  void ccSetScalaADOffset(Integer pxKey, int pxVal){
    if(pxKey==null){throw O_RE_SCALA_NOT_FOUND;}
    if(!cmMapOfScala.containsKey(pxKey)){throw O_RE_SCALA_NOT_FOUND;}
    ZcScaledModel lpModle=cmMapOfScala.get(pxKey);
    lpModle.ccSetInputOffset(pxVal);
  }//+++<
  
  synchronized public final
  void ccSetScalaADSpan(Integer pxKey, int pxVal){
    if(pxKey==null){throw O_RE_SCALA_NOT_FOUND;}
    if(!cmMapOfScala.containsKey(pxKey)){throw O_RE_SCALA_NOT_FOUND;}
    ZcScaledModel lpModle=cmMapOfScala.get(pxKey);
    lpModle.ccSetInputSpan(pxVal);
  }//+++<
  
  synchronized public final
  void ccSetScalaRealOffset(Integer pxKey, int pxVal){
    if(pxKey==null){throw O_RE_SCALA_NOT_FOUND;}
    if(!cmMapOfScala.containsKey(pxKey)){throw O_RE_SCALA_NOT_FOUND;}
    ZcScaledModel lpModle=cmMapOfScala.get(pxKey);
    lpModle.ccSetOutputSpan(pxVal);
  }//+++<
  
  synchronized public final
  void ccSetScalaRealSpan(Integer pxKey, int pxVal){
    if(pxKey==null){throw O_RE_SCALA_NOT_FOUND;}
    if(!cmMapOfScala.containsKey(pxKey)){throw O_RE_SCALA_NOT_FOUND;}
    ZcScaledModel lpModle=cmMapOfScala.get(pxKey);
    lpModle.ccSetOutputSpan(pxVal);
  }//+++<
  
  synchronized public final
  int ccGetScalaADOffset(Integer pxKey){
    if(pxKey==null){throw O_RE_SCALA_NOT_FOUND;}
    if(!cmMapOfScala.containsKey(pxKey)){throw O_RE_SCALA_NOT_FOUND;}
    ZcScaledModel lpModle=cmMapOfScala.get(pxKey);
    return lpModle.ccGetInputOffset();
  }//+++<
  
  synchronized public final
  int ccGetScalaADSpan(Integer pxKey){
    if(pxKey==null){throw O_RE_SCALA_NOT_FOUND;}
    if(!cmMapOfScala.containsKey(pxKey)){throw O_RE_SCALA_NOT_FOUND;}
    ZcScaledModel lpModle=cmMapOfScala.get(pxKey);
    return lpModle.ccGetInputSpan();
  }//+++<
  
  synchronized public final
  int ccGetScalaRealOffset(Integer pxKey){
    if(pxKey==null){throw O_RE_SCALA_NOT_FOUND;}
    if(!cmMapOfScala.containsKey(pxKey)){throw O_RE_SCALA_NOT_FOUND;}
    ZcScaledModel lpModle=cmMapOfScala.get(pxKey);
    return lpModle.ccGetOutputOffset();
  }//+++<
  
  synchronized public final
  int ccGetScalaRealSpan(Integer pxKey){
    if(pxKey==null){throw O_RE_SCALA_NOT_FOUND;}
    if(!cmMapOfScala.containsKey(pxKey)){throw O_RE_SCALA_NOT_FOUND;}
    ZcScaledModel lpModle=cmMapOfScala.get(pxKey);
    return lpModle.ccGetOutputSpan();
  }//+++<
  
  //===
  
  @Deprecated public final void tstTagg(){
    VcLocalTagger.ccTag("vdpp", cmVDryerPressureScalar);
    VcLocalTagger.ccTag("vbpp", cmVBurnerDegreeScalar);
  }//+++
  
 }//***eof
