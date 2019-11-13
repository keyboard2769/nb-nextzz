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
  
  //=== v safe guard
  
  public final MiSettingItem cmEntranceBaseCelciusItem= new MiSettingItem() {
    @Override public String ccGetName() {
      return "[`C]"+VcTranslator.tr("_entrance_base_celcius");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_entrance_base_celcius");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[1 ~ 999]";
    }//++>
    @Override public String ccGetValue() {
      return "<noteyet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 1, 999);
      System.out.println(".ccSetValue()::not_yet:"+Integer.toString(lpFixed));
    }//++<
  };//***
  
  public final MiSettingItem cmEntranceCoolDownCelciusItem
    = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[`C]"+VcTranslator.tr("_entrance_cool_down_temp");
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
  
  public final MiSettingItem cmEntranceMeltDownCelciusItem
    = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[`C]"+VcTranslator.tr("_entrance_melt_down_temp");
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
  
  //=== v burner degree
  
  public final MiSettingItem cmVTargetTemperatureItem= new MiSettingItem() {
    @Override public String ccGetName() {
      return "[`C]"+VcTranslator.tr("_v_target_temperature");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_v_target_temperature");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[1 ~ 255]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString(SubDegreeControlManager.ccRefer().vmVTargetCELC);
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 1, 255);
      SubDegreeControlManager.ccRefer().vmVTargetCELC=lpFixed;
    }//++<
  };//***
  
  public final MiSettingItem cmVTargetAdjustWidthItem
    = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[`C]"+VcTranslator.tr("_v_target_adjust_width");
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
  
  public final MiSettingItem cmVPreHeatingDegreeItem
    = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[%]"+VcTranslator.tr("_v_pre_heating_degree");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_v_pre_heating_degree");
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
  
  public final MiSettingItem cmVBurnerLimitDegreeItem= new MiSettingItem() {
    @Override public String ccGetName() {
      return "[%]"+VcTranslator.tr("_v_burner_limit_degree");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_v_burner_limit_degree");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[1 ~ 99]";
    }//++>
    @Override public String ccGetValue() {
      return "<noteyet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 1, 99);
      System.out.println(".ccSetValue()::not_yet:"+Integer.toString(lpFixed));
    }//++<
  };//***
  
  //=== v exf degree
  
  public final MiSettingItem cmVDryerTargetPressureItem
    = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[-kpa]"+VcTranslator.tr("_v_dryer_target_pres");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_v_dryer_target_pres");
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
  
  public final MiSettingItem cmVExfanLimitDegreeItem= new MiSettingItem() {
    @Override public String ccGetName() {
      return "[%]"+VcTranslator.tr("_v_exfan_limit_degree");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp__v_exfan_limit_degree");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[1 ~ 99]";
    }//++>
    @Override public String ccGetValue() {
      return "<noteyet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 1, 99);
      System.out.println(".ccSetValue()::not_yet:"+Integer.toString(lpFixed));
    }//++<
  };//***
  
  //=== v combust control
  
  public final MiSettingItem cmVCombustControlProprtionZoneItem
    = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[%]"+VcTranslator.tr("_v_combust_control_proprtion_zone");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp__v_combust_control_proprtion_zone");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[1 ~ 99]";
    }//++>
    @Override public String ccGetValue() {
      return "<noteyet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 1, 99);
      System.out.println(".ccSetValue()::not_yet:"+Integer.toString(lpFixed));
    }//++<
  };//***
  
  public final MiSettingItem cmVCombustControlDeadZoneItem
    = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[%]"+VcTranslator.tr("_v_combust_control_dead_zone");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp__v_combust_control_dead_zone");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[1 ~ 99]";
    }//++>
    @Override public String ccGetValue() {
      return "<noteyet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 1, 99);
      System.out.println(".ccSetValue()::not_yet:"+Integer.toString(lpFixed));
    }//++<
  };//***
  
  public final MiSettingItem cmVCombustControlSampleIntervalItem
    = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[S]"+VcTranslator.tr("_v_combust_control_sample_interval");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp__v_combust_control_sample_interval");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0.5 ~ 3600.0]";
    }//++>
    @Override public String ccGetValue() {
      return "<noteyet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 960, 962);
      System.out.println(".ccSetValue()::not_yet:"+Integer.toString(lpFixed));
    }//++<
  };//***
  
  public final MiSettingItem cmVCombustControlAdjustIntervalItem
    = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[S]"+VcTranslator.tr("_v_combust_control_adjust_interval");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_v_combust_control_adjust_interval");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0.5 ~ 3600.0]";
    }//++>
    @Override public String ccGetValue() {
      return "<noteyet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 960, 962);
      System.out.println(".ccSetValue()::not_yet:"+Integer.toString(lpFixed));
    }//++<
  };//***
  
  //[todo]::..
  //=== r safe guard
  //=== r burner degree
  //=== r exf degree
  //=== r combust control
  
  //===
  
  @Override public void ccInit(){
    
    //-- v safe guard
    cmListOfItem.add(cmEntranceBaseCelciusItem);
    cmListOfItem.add(cmEntranceCoolDownCelciusItem);
    cmListOfItem.add(cmEntranceMeltDownCelciusItem);
    
    //-- v burner degree
    cmListOfItem.add(cmVTargetTemperatureItem);
    cmListOfItem.add(cmVTargetAdjustWidthItem);
    cmListOfItem.add(cmVPreHeatingDegreeItem);
    cmListOfItem.add(cmVBurnerLimitDegreeItem);
    
    //-- v exf degree
    cmListOfItem.add(cmVDryerTargetPressureItem);
    cmListOfItem.add(cmVExfanIgnitionPercentageItem);
    cmListOfItem.add(cmVExfanLimitDegreeItem);
    
    //-- v combust control
    cmListOfItem.add(cmVCombustControlProprtionZoneItem);
    cmListOfItem.add(cmVCombustControlDeadZoneItem);
    cmListOfItem.add(cmVCombustControlSampleIntervalItem);
    cmListOfItem.add(cmVCombustControlAdjustIntervalItem);
    
    //[todo]::..
    //-- r safe guard
    //-- r burner degree
    //-- r exf degree
    //-- r combust control
    
  }//++!
  
  @Override public String ccGetTile() {
    return VcTranslator.tr(C_KEY_TITLE);
  }//++>
  
 }//***eof
