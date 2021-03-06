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

package nextzz.pppsetting;

import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcTranslator;
import nextzz.pppmodel.MainPlantModel;
import nextzz.pppmodel.SubAnalogScalarManager;
import processing.core.PApplet;

public final class SubCTSlotSetting extends McAbstractSettingPartition{
  
  public static final String C_KEY_TITLE = "_ct_slot_setting";

  private static final SubCTSlotSetting SELF = new SubCTSlotSetting();
  public static final SubCTSlotSetting ccRefer(){return SELF;}//++>
  private SubCTSlotSetting(){}//++!

  //=== inner
  
  private class McCTSlotSpanItem implements MiSettingItem{
    private final int cmIndex;
    public McCTSlotSpanItem(int pxIndex) {
      cmIndex=pxIndex;
    }//++!
    @Override public String ccGetName() {
      return "[A]:"
        +VcTranslator.tr(String.format("_ct%02d", cmIndex))
        +VcTranslator.tr("_ct_span");
    }//+++
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_ct_span");
    }//+++
    @Override public String ccGetLimitationInfo() {
      return "[1 ~ 999]";
    }//+++
    @Override public String ccGetValue() {
      return VcNumericUtility
        .ccFormatFloatForOneAfter(
          SubAnalogScalarManager.ccRefer().ccGetCTSlotREALSpan(cmIndex)
        );
    }//+++
    @Override public void ccSetValue(String pxVal) {
      float lpFixed = VcNumericUtility.ccParseFloatString(pxVal);
      lpFixed=PApplet.constrain(lpFixed, 1f, 999f);
      SubAnalogScalarManager.ccRefer()
        .ccSetCTSlotREALSpan(cmIndex,VcNumericUtility.ccInteger(lpFixed,10f));
    }//+++
  }//***
  
  private class McCTSlotAlarmItem implements MiSettingItem{
    private final int cmIndex;
    public McCTSlotAlarmItem(int pxIndex) {
      cmIndex=pxIndex;
    }//..!
    @Override public String ccGetName() {
      return "[A]:"
        +VcTranslator.tr(String.format("_ct%02d", cmIndex))
        +VcTranslator.tr("_ct_alarm");
    }//+++
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_ct_alarm");
    }//+++
    @Override public String ccGetLimitationInfo() {
      return "[1 ~ 999]";
    }//+++
    @Override public String ccGetValue() {
      int lpRaw;
      if(cmIndex>=0 && cmIndex<16){//..[todo]::how do we constantize this ??
        lpRaw = MainPlantModel.ccRefer().cmDesCTSlotAlarmPartIxAMPR
          .ccGet(cmIndex);
      }else if(cmIndex>=16 && cmIndex<32){//..[todo]::how do we constantize this ??
        lpRaw = MainPlantModel.ccRefer().cmDesCTSlotAlarmPartIxAMPR
          .ccGet(cmIndex-16);//..[todo]::how do we constantize this ??
      }else{
        System.err.println(
          ".McCTSlotAlarmItem.ccSetValue() $ invalid index"
        );
        return "<x>";
      }//..?
      return VcNumericUtility
        .ccFormatFloatForOneAfter(lpRaw);
    }//+++
    @Override public void ccSetValue(String pxVal) {
      float lpFixed = VcNumericUtility.ccParseFloatString(pxVal);
      lpFixed=PApplet.constrain(lpFixed, 1f, 999f);
      int lpCasted = VcNumericUtility.ccInteger(lpFixed,10f);
      if(cmIndex>=0 && cmIndex<16){//..[todo]::how do we constantize this ??
        MainPlantModel.ccRefer().cmDesCTSlotAlarmPartIxAMPR
          .ccSet(cmIndex, lpCasted);
      }else if(cmIndex>=16 && cmIndex<32){//..[todo]::how do we constantize this ??
        MainPlantModel.ccRefer().cmDesCTSlotAlarmPartIxAMPR
          .ccSet(cmIndex-16, lpCasted);//..[todo]::how do we constantize this ??
      }else{
        System.err.println(
          ".McCTSlotAlarmItem.ccSetValue() $ invalid index"
        );
      }//..?
    }//+++
  }//***
  
  //===
  
  @Override public void ccInit() {
        
    for(int i=0;i<32;i++){//..[todo]::how do we constant this ??
      cmListOfItem.add(new McCTSlotSpanItem(i));
      cmListOfItem.add(new McCTSlotAlarmItem(i));
    }//..~
    
  }//++!
  
  @Override public String ccGetTile() {
    return VcTranslator.tr(C_KEY_TITLE);
  }//++>
  
 }//***eof
