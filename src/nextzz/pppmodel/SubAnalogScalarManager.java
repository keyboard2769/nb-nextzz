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
import nextzz.pppdelegate.SubAnalogDelegator;
import nextzz.pppdelegate.SubFeederDelegator;

public final class SubAnalogScalarManager {
  
  public static final String Q_TYPE_CT   = "ct";  //.. current
  public static final String Q_TYPE_CELL = "cell";//.. weight
  public static final String Q_TYPE_DEG  = "deg"; //.. percetage
  public static final String Q_TYPE_TEMP = "temp";//.. celcius
  public static final String Q_TYPE_PRES = "pres";//.. kilo paska
  public static final String Q_TYPE_TPH  = "tph"; //.. ton
  public static final String Q_TYPE_MISC = "misc";//.. WTF??
  
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
    
  }//..!
  
  //=== 
  
  //=== feeder flux ** V
  
  synchronized public final
  void ccSetVFeederFluxRPMSpan(int pxIndex, int pxVal){
    cmListOfVFeederFluxScalar.get(pxIndex&15).ccSetInputSpan(pxVal&0xFFFF);
  }//+++
  
  synchronized public final
  int ccGetVFeederFluxRPMSpan(int pxIndex){
   return cmListOfVFeederFluxScalar.get(pxIndex&15).ccGetInputSpan();
  }//+++
  
  synchronized public final
  void ccSetVFeederFluxTPHSpan(int pxIndex, int pxVal){
    cmListOfVFeederFluxScalar.get(pxIndex&15).ccSetOutputSpan(pxVal&0xFFFF);
  }//+++
  
  synchronized public final
  int ccGetVFeederFluxTPHSpan(int pxIndex){
   return cmListOfVFeederFluxScalar.get(pxIndex&15).ccGetOutputSpan();
  }//+++
  
  synchronized public final
  int ccGetVFeederFluxTPHValue(int pxIndex){
    return cmListOfVFeederFluxScalar.get(pxIndex&15)
      .ccToScaledIntegerValue(
        SubFeederDelegator.ccGetVFeederSpeed(pxIndex)
      );
  }//+++
  
  //=== feeder flux ** R
  
  //=== CT Slot
  
  synchronized public final void ccSetCTSlotSpan(int pxIndex, int pxVal){
    cmListOfCTSlotScalar.get(pxIndex&31).ccSetOutputSpan(pxVal&0xFFFF);
  }//+++
  
  synchronized public final int ccGetCTSlotSpan(int pxIndex){
    return cmListOfCTSlotScalar.get(pxIndex&31).ccGetOutputSpan();
  }//+++
  
  synchronized public final int ccGetScaledCTSlotValue(int pxIndex){
    return cmListOfCTSlotScalar.get(pxIndex&31)
      .ccToScaledIntegerValue(
        SubAnalogDelegator.ccGetCTSlotAD(pxIndex)
      );
  }//+++
  
  //=== pressure
  //=== pressure ** v
  
  synchronized public final int ccGetScaledVDPressureValue(){
    return cmVDryerPressureScalar.ccToScaledIntegerValue(
      SubAnalogDelegator.mnVDPressureAD
    );
  }//+++
  
  //=== pressure ** r
  
  //=== degree
  //=== degree ** v 
  //=== degree ** v ** vb
  
  //[todo]::.. % set vb span
  //[todo]::.. % set vb offset
  
  synchronized public final int ccGetScaledVBDegreeValue(){
    return cmVBurnerDegreeScalar.ccToScaledIntegerValue(
      SubAnalogDelegator.mnVBDegreeAD
    );
  }//+++
  
  //=== degree ** v ** ve
  
  synchronized public final int ccGetScaledVEDegreeValue(){
    return cmVExfanDegreeScalar.ccToScaledIntegerValue(
      SubAnalogDelegator.mnVEDegreeAD
    );
  }//+++
  
 }//***eof
