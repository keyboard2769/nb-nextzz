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
import nextzz.pppswingui.SubMonitorPane;

public final class SubAnalogScalarManager {
  
  public static final String Q_TYPE_CT = "ct";
  public static final String Q_TYPE_CELL = "cell";
  public static final String Q_TYPE_DEG = "deg";
  public static final String Q_TYPE_TEMP = "temp";
  public static final String Q_TYPE_PRES = "pres";
  public static final String Q_TYPE_MISC = "misc";

  private static final SubAnalogScalarManager SELF
    = new SubAnalogScalarManager();
  public static final
  SubAnalogScalarManager ccRefer(){return SELF;}//+++
  private SubAnalogScalarManager(){}//++!

  //===
  
  private final List<ZcScaledModel>
    cmListOfCurrent = new ArrayList<ZcScaledModel>(SubMonitorPane.C_CTSLOT_MAX);
    
  public final void ccInit(){
    
    //-- create current 
    for(int i=0;i<SubMonitorPane.C_CTSLOT_MAX;i++){
      cmListOfCurrent.add(new ZcScaledModel(0, 5000, 0, 1000));
    }//+++
    
  }//..!
  
  synchronized public final void ccSetCurrentAD(int pxIndex, int pxValue){
    cmListOfCurrent.get(pxIndex&SubMonitorPane.C_CTSLOT_MASK)
      .ccSetInputValue(pxValue);
  }//+++
  
  synchronized public final int ccGetCurrentA(int pxIndex){
    return cmListOfCurrent.get(pxIndex&SubMonitorPane.C_CTSLOT_MASK)
      .ccGetScaledIntegerValue();
  }//+++
  
 }//***eof
