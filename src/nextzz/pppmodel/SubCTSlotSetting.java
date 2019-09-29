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

import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcTranslator;

public final class SubCTSlotSetting extends McAbstractSettingPartition{
  
  public static final String C_KEY_TITLE = "_ct_slot";

  private static final SubCTSlotSetting SELF = new SubCTSlotSetting();
  public static final SubCTSlotSetting ccRefer(){return SELF;}//+++

  //=== inner
  
  private class McCTSlotSpanItem implements MiSettingItem{
    private final int cmIndex;
    public McCTSlotSpanItem(int pxIndex) {
      cmIndex=pxIndex;
    }//..!
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_m_ct_span");
    }//+++
    @Override public String ccGetName() {
      return "[A]:"
        +VcTranslator.tr(String.format("_ct%02d", cmIndex))
        +VcTranslator.tr("_ct_span");
    }//+++
    @Override public String ccGetValue() {
      //[todo]::fix this!
      String lpD=Integer.toString
        (SubAnalogScalarManager.ccRefer().ccGetCTSlotSpan(cmIndex));
      return lpD;
    }//+++
    @Override public void ccSetValue(String pxVal) {
      //[todo]::fix this!
      int lpD=VcNumericUtility.ccParseIntegerString(pxVal);
      SubAnalogScalarManager.ccRefer().ccSetCTSlotSpan(cmIndex, lpD);
    }//+++
  }//***
  
  //===
  
  private SubCTSlotSetting(){
    
    for(int i=0;i<32;i++){
      cmListOfItem.add(new McCTSlotSpanItem(i));
    }//..~
    
  }//++!
  
  //===

  @Override public String ccGetTile() {
    return VcTranslator.tr(C_KEY_TITLE);
  }//+++
  
 }//***eof
