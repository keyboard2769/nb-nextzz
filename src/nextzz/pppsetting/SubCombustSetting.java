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
    @Override public String ccGetName() {
      return "['C]"+VcTranslator.tr("_entrance_cool_down_temp");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_entrance_cool_down_temp");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[150 ~ 800]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString
        (SubDegreeControlManager.ccRefer().vmVCoolDownCELC);
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 150, 800);
      if(lpFixed>SubDegreeControlManager.ccRefer().vmVMeltDownCELC){
        ScConst.ccErrorBox(VcTranslator.tr("_m_v_down_temp_setting_error"));
        return;
      }//..?
      SubDegreeControlManager.ccRefer().vmVCoolDownCELC=lpFixed;
    }//++<
  };//***
  
  public final MiSettingItem cmVEntranceMeltDownCelciusItem
    = new MiSettingItem() {
    @Override public String ccGetName() {
      return "['C]"+VcTranslator.tr("_entrance_melt_down_temp");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_entrance_melt_down_temp");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[150 ~ 800]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString
        (SubDegreeControlManager.ccRefer().vmVMeltDownCELC);
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 150, 800);
      if(lpFixed<SubDegreeControlManager.ccRefer().vmVCoolDownCELC){
        ScConst.ccErrorBox(VcTranslator.tr("_m_v_down_temp_setting_error"));
        return;
      }//..?
      SubDegreeControlManager.ccRefer().vmVMeltDownCELC=lpFixed;
    }//++<
  };//***
  
  public final MiSettingItem cmVTargetAdjustWidthItem
    = new MiSettingItem() {
    @Override public String ccGetName() {
      return "['C]"+VcTranslator.tr("_v_target_adjust_width");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_v_target_adjust_width");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[1 ~ 20]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString
        (SubDegreeControlManager.ccRefer().vmVTargetAdjustWidth);
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 1, 20);
      SubDegreeControlManager.ccRefer().vmVTargetAdjustWidth=lpFixed;
    }//++<
  };//***
  
  public final MiSettingItem cmVPreHeatingPercentageItem
    = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[%]"+VcTranslator.tr("_v_pre_heating_percentage");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_v_pre_heating_percentage");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[1 ~ 99]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString
        (SubDegreeControlManager.ccRefer().vmVPreHeatingPT);
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 1, 99);
      SubDegreeControlManager.ccRefer().vmVPreHeatingPT=lpFixed;
    }//++<
  };//***
  
  public final MiSettingItem cmVDryerTargetPressureItem
    = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[-kpa]"+VcTranslator.tr("_v_tryer_target_pres");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_v_tryer_target_pres");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[1 ~ 199]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString
        (SubDegreeControlManager.ccRefer().vmVDryerTargetMinusKPA);
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 1, 199);
      SubDegreeControlManager.ccRefer().vmVDryerTargetMinusKPA=lpFixed;
    }//++<
  };//***
  
  public final MiSettingItem cmVExfanIgnitionPercentageItem
    = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[%]"+VcTranslator.tr("_v_exfan_ignition_percentage");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_v_exfan_ignition_percentage");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[1 ~ 99]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString
        (SubDegreeControlManager.ccRefer().vmVExfanIgnitionPT);
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 1, 99);
      SubDegreeControlManager.ccRefer().vmVExfanIgnitionPT=lpFixed;
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
