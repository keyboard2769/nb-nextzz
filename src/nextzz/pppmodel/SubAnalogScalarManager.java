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
  
  private final List<ZcScaledModel> cmListOfCTSlotModel
    = new ArrayList<ZcScaledModel>(32);
  
  //===
    
  public final void ccInit(){
    
    //-- create current 
    for(int i=0;i<32;i++){
      cmListOfCTSlotModel.add(new ZcScaledModel(0, 5000, 0, 1000));
    }//+++
    
  }//..!
  
  //=== Feeder Speed
  
  //=== CT Slot
  
  synchronized public final int ccGetCTSlotSpan(int pxIndex){
    return cmListOfCTSlotModel.get(pxIndex&31).ccGetOutputSpan();
  }//+++
  
  synchronized public final int ccGetScaledCTSlotValue(int pxIndex){
    int lpFixedIndex=pxIndex&0x1F;
    return cmListOfCTSlotModel.get(lpFixedIndex)
      .ccToScaledIntegerValue(
        SubAnalogDelegator.ccGetCTSlotAD(lpFixedIndex)
    );
  }//+++
  
 }//***eof
