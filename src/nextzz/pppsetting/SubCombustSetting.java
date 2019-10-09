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

import kosui.ppplogic.ZcRangedValueModel;
import kosui.pppswingui.ScConst;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcTranslator;
import nextzz.pppmodel.SubDegreeControlManager;

public final class SubCombustSetting extends McAbstractSettingPartition{
  
  public static final String C_KEY_TITLE = "_combust_setting";

  private static final SubCombustSetting SELF = new SubCombustSetting();
  public static final SubCombustSetting ccRefer(){return SELF;}//+++
  private SubCombustSetting(){}//++!
  
  //=== 
  
  public final MiSettingItem cmVEntranceCoolDownCelciusItem
    = new MiSettingItem() {
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_entrance_cool_down_temp");
    }//++>
    @Override public String ccGetName() {
      return "['C]"+VcTranslator.tr("_entrance_cool_down_temp");
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString
        (SubDegreeControlManager.ccRefer().mnVCoolDownCELC);
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 150, 800);
      if(lpFixed>SubDegreeControlManager.ccRefer().mnVMeltDownCELC){
        ScConst.ccErrorBox(VcTranslator.tr("_m_v_down_temp_setting_error"));
        return;
      }//..?
      SubDegreeControlManager.ccRefer().mnVCoolDownCELC=lpFixed;
    }//++<
  };//***
  
  public final MiSettingItem cmVEntranceMeltDownCelciusItem
    = new MiSettingItem() {
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_entrance_melt_down_temp");
    }//++>
    @Override public String ccGetName() {
      return "['C]"+VcTranslator.tr("_entrance_melt_down_temp");
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString
        (SubDegreeControlManager.ccRefer().mnVMeltDownCELC);
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 150, 800);
      if(lpFixed<SubDegreeControlManager.ccRefer().mnVCoolDownCELC){
        ScConst.ccErrorBox(VcTranslator.tr("_m_v_down_temp_setting_error"));
        return;
      }//..?
      SubDegreeControlManager.ccRefer().mnVMeltDownCELC=lpFixed;
    }//++<
  };//***
  
  public final MiSettingItem cmVTargetAdjustWidthItem
    = new MiSettingItem() {
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_v_target_adjust_width");
    }//++>
    @Override public String ccGetName() {
      return "['C]"+VcTranslator.tr("_v_target_adjust_width");
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString
        (SubDegreeControlManager.ccRefer().mnVTargetAdjustWidth);
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 1, 20);
      SubDegreeControlManager.ccRefer().mnVTargetAdjustWidth=lpFixed;
    }//++<
  };//***
  
  public final MiSettingItem cmVPreHeatingPercentageItem
    = new MiSettingItem() {
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_v_pre_heating_percentage");
    }//++>
    @Override public String ccGetName() {
      return "[%]"+VcTranslator.tr("_v_pre_heating_percentage");
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString
        (SubDegreeControlManager.ccRefer().mnVPreHeatingPT);
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 1, 99);
      SubDegreeControlManager.ccRefer().mnVPreHeatingPT=lpFixed;
    }//++<
  };//***
  
  public final MiSettingItem cmVDryerTargetPressureItem
    = new MiSettingItem() {
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_v_tryer_target_pres");
    }//++>
    @Override public String ccGetName() {
      return "[-kpa]"+VcTranslator.tr("_v_tryer_target_pres");
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString
        (SubDegreeControlManager.ccRefer().mnVDryerTargetMinusKPA);
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 1, 199);
      SubDegreeControlManager.ccRefer().mnVDryerTargetMinusKPA=lpFixed;
    }//++<
  };//***
  
  public final MiSettingItem cmVExfanIgnitionPercentageItem
    = new MiSettingItem() {
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_v_exfan_ignition_percentage");
    }//++>
    @Override public String ccGetName() {
      return "[%]"+VcTranslator.tr("_v_exfan_ignition_percentage");
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString
        (SubDegreeControlManager.ccRefer().mnVExfanIgnitionPT);
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 1, 99);
      SubDegreeControlManager.ccRefer().mnVExfanIgnitionPT=lpFixed;
    }//++<
  };//***
  
  //===
  
  @Override public void ccInit(){
    McAbstractSettingPartition.ccRegisterAll(SELF);
  }//++!
  
  @Override public String ccGetTile() {
    return VcTranslator.tr(C_KEY_TITLE);
  }//++>
  
 }//***eof
