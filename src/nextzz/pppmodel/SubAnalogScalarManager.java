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
import nextzz.pppswingui.SubMonitorPane;

public final class SubAnalogScalarManager {
  
  public static final String Q_TYPE_CT   = "ct";  //.. current
  public static final String Q_TYPE_CELL = "cell";//.. weight
  public static final String Q_TYPE_DEG  = "deg"; //.. percetage
  public static final String Q_TYPE_TEMP = "temp";//.. celcius
  public static final String Q_TYPE_PRES = "pres";//.. kilo paska
  public static final String Q_TYPE_TPH  = "tph"; //.. ton
  public static final String Q_TYPE_DTD  = "dtd"; //.. digital to digial
  public static final String Q_TYPE_MISC = "misc";//.. WTF??
  

  private static final SubAnalogScalarManager SELF
    = new SubAnalogScalarManager();
  public static final
  SubAnalogScalarManager ccRefer(){return SELF;}//+++
  private SubAnalogScalarManager(){}//++!

  //===
  
  private final List<ZcScaledModel> cmListOfCTSlotModel
    = new ArrayList<ZcScaledModel>(32);
    
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
    switch(pxIndex){
      
      case 0:return cmListOfCTSlotModel.get(0)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotZ);
      case 1:return cmListOfCTSlotModel.get(1)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotI);
      case 2:return cmListOfCTSlotModel.get(2)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotII);
      case 3:return cmListOfCTSlotModel.get(3)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotIII);
      
      case 4:return cmListOfCTSlotModel.get(4)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotIV);
      case 5:return cmListOfCTSlotModel.get(5)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotV);
      case 6:return cmListOfCTSlotModel.get(6)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotVI);
      case 7:return cmListOfCTSlotModel.get(7)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotVII);
      
      case  8:return cmListOfCTSlotModel.get( 8)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotVIII);
      case  9:return cmListOfCTSlotModel.get( 9)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotIX);
      case 10:return cmListOfCTSlotModel.get(10)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotX);
      case 11:return cmListOfCTSlotModel.get(11)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotXI);
      
      case 12:return cmListOfCTSlotModel.get(12)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotXII);
      case 13:return cmListOfCTSlotModel.get(13)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotXIII);
      case 14:return cmListOfCTSlotModel.get(14)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotXIV);
      case 15:return cmListOfCTSlotModel.get(15)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotXV);
      
      case 16:return cmListOfCTSlotModel.get(16)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotXVI);
      case 17:return cmListOfCTSlotModel.get(17)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotXVII);
      case 18:return cmListOfCTSlotModel.get(18)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotXVIII);
      case 19:return cmListOfCTSlotModel.get(19)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotXIX);
      
      case 20:return cmListOfCTSlotModel.get(20)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotXX);
      case 21:return cmListOfCTSlotModel.get(21)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotXXI);
      case 22:return cmListOfCTSlotModel.get(22)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotXXII);
      case 23:return cmListOfCTSlotModel.get(23)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotXXIII);
      
      case 24:return cmListOfCTSlotModel.get(24)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotXXIV);
      case 25:return cmListOfCTSlotModel.get(25)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotXXV);
      case 26:return cmListOfCTSlotModel.get(26)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotXXVI);
      case 27:return cmListOfCTSlotModel.get(27)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotXXVII);
      
      case 28:return cmListOfCTSlotModel.get(28)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotXXVIII);
      case 29:return cmListOfCTSlotModel.get(29)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotXXIX);
      case 30:return cmListOfCTSlotModel.get(30)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotXXX);
      case 31:return cmListOfCTSlotModel.get(31)
        .ccToScaledIntegerValue(SubAnalogDelegator.mnCTSlotXXXI);
      
      default:return 0;
    
    }//..?
  }//+++
  
 }//***eof
