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
import kosui.ppplogic.ZcScaledModel;
import kosui.pppmodel.McPipedChannel;
import kosui.ppputil.VcLocalTagger;
import nextzz.pppdelegate.SubAnalogDelegator;
import nextzz.pppdelegate.SubFeederDelegator;

public final class SubAnalogScalarManager {
  
  public static final int C_I_TH_CHUTE    = 1;
  public static final int C_I_TH_ENTRANCE = 2;
  public static final int C_I_TH_PIPE     = 3;
  public static final int C_I_TH_SAND     = 4;
  public static final int C_I_TH_MIXER    = 6;
  
  //[todo]::C_I_RH_ENTRANCE
  //[todo]::C_I_RH_GAS
  //[todo]::C_I_RH_SURGE
  //[todo]::C_I_RH_RECYCLE
  
  //[todo]::C_I_TH_SILO_I
  //[todo]::C_I_TH_SILO_II
  
  //===
  
  public static final String Q_TYPE_CT   = "ct";  //.. current
  public static final String Q_TYPE_CELL = "cell";//.. weight
  public static final String Q_TYPE_DEG  = "deg"; //.. percetage
  public static final String Q_TYPE_TEMP = "temp";//.. celcius
  public static final String Q_TYPE_PRES = "pres";//.. kilo paska
  public static final String Q_TYPE_TPH  = "tph"; //.. ton
  public static final String Q_TYPE_MISC = "misc";//.. WTF??
  
  //===
  
  private static final SubAnalogScalarManager SELF
    = new SubAnalogScalarManager();
  public static final
  SubAnalogScalarManager ccRefer(){return SELF;}//+++
  private SubAnalogScalarManager(){}//++!

  //===
  
  private final List<ZcScaledModel> cmListOfCTSlotScalar
    = new ArrayList<ZcScaledModel>(32);

  private final List<ZcScaledModel> cmListOfVFeederFluxScalar
    = new ArrayList<ZcScaledModel>(16);
  
  private final ZcScaledModel cmVDryerPressureScalar
    = new ZcScaledModel(1500, 2500, 0, 200);
  
  private final ZcScaledModel cmVBurnerDegreeScalar
    = new ZcScaledModel(400, 3600, 0, 100);
  
  private final ZcScaledModel cmVExfanDegreeScalar
    = new ZcScaledModel(400, 3600, 0, 100);
  
  //===
  
  private final ZcScaledModel cmThermoCouplScalar
    = new ZcScaledModel(1000, 4680, 0, 1472);
  
  public final McPipedChannel cmDesVThermoCelcius = new McPipedChannel();
  //[todo]::cmDesRThermoCelcius
  
  //===
    
  public final void ccInit(){
    
    //-- create current 
    for(int i=0;i<32;i++){
      cmListOfCTSlotScalar.add(new ZcScaledModel(0, 5000, 0, 1000));
      //.. 1000d -> 100.0f [A]
    }//+++
    
    //-- feeder flux
    
    //-- feeder flux ** V
    for(int i=0;i<16;i++){
      cmListOfVFeederFluxScalar.add(new ZcScaledModel(0, 900, 0, 500));
      //.. 500d -> 50.0f [ton]
    }//+++
    
  }//++!
  
  public final void ccLogic(){
    
    //-- ct
    for(int i=0;i<MainPlantModel.C_CTSLOT_CHANNEL_MAX;i++){
      cmListOfCTSlotScalar.get(i&31)
        .ccRun(SubAnalogDelegator.ccGetCTSlotAD(i));
    }//..~
    
    //-- th ** v
    for(int i=1;i<=8;i++){
      cmDesVThermoCelcius.ccSet(
        i,
        cmThermoCouplScalar
          .ccToScaledIntegerValue(SubAnalogDelegator.ccGetVThermoAD(i))
      );
      //[todo]:: % cmDesRThermoCelcius.ccSet(...
    }//..~
    
    //-- v
    cmVBurnerDegreeScalar.ccRun(SubAnalogDelegator.mnVBDegreeAD);
    cmVExfanDegreeScalar.ccRun(SubAnalogDelegator.mnVEDegreeAD);
    cmVDryerPressureScalar.ccRun(SubAnalogDelegator.mnVDPressureAD);
    
    //-- r
    
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
      .ccToScaledIntegerValue(
        SubFeederDelegator.ccGetVFeederSpeed(pxIndex)
      );
  }//++>
  
  //=== feeder flux ** R
  //[todo]::ccSetRFeederFluxRPMSpan
  //[todo]::ccGetRFeederFluxRPMSpan
  //[todo]::ccSetRFeederFluxTPHSpan
  //[todo]::ccGetRFeederFluxTPHSpan
  //[todo]::ccGetRFeederFluxTPH
  
  //=== CT Slot
  
  synchronized public final void ccSetCTSlotSpan(int pxIndex, int pxVal){
    cmListOfCTSlotScalar.get(pxIndex&31).ccSetOutputSpan(pxVal&0xFFFF);
  }//++<
  
  synchronized public final int ccGetCTSlotSpan(int pxIndex){
    return cmListOfCTSlotScalar.get(pxIndex&31).ccGetOutputSpan();
  }//++>
  
  synchronized public final int ccGetCTSlotAMPR(int pxIndex){
    return cmListOfCTSlotScalar.get(pxIndex&31).ccGetScaledIntegerValue();
  }//++>
  
  //=== pressure
  //=== pressure ** v
  
  synchronized public final int ccGetVDryerKPA(){
    return cmVDryerPressureScalar.ccGetScaledIntegerValue();
  }//++>
  
  //=== pressure ** r
  
  //=== degree
  //=== degree ** v 
  //=== degree ** v ** vb
  
  //[todo]::.. % set vb span
  //[todo]::.. % set vb offset
  
  synchronized public final int ccGerVBurnerPercentage(){
    return cmVBurnerDegreeScalar.ccGetScaledIntegerValue();
  }//++>
  
  //=== degree ** v ** ve
  
  synchronized public final int ccGetVExfanPercentage(){
    return cmVExfanDegreeScalar.ccGetScaledIntegerValue();
  }//++>
  
  //=== utility
  
  //[todo]::.. static % set ad offset (k,v)
  //[todo]::.. static % set ad span (k,v)
  //[todo]::.. static % set real offset (k,v)
  //[todo]::.. static % set real span (k,v)
  
  //[todo]::.. static % get ad offset (k)
  //[todo]::.. static % get ad span (k)
  //[todo]::.. static % get real offset (k)
  //[todo]::.. static % get real span (k)
  
  //===
  
  @Deprecated public final void tstTagg(){
    VcLocalTagger.ccTag("vdpp", cmVDryerPressureScalar);
    VcLocalTagger.ccTag("vbpp", cmVBurnerDegreeScalar);
  }//+++
  
 }//***eof
