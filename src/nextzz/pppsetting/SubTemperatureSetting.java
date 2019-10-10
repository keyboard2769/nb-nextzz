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

import kosui.ppplogic.ZcRangedModel;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcTranslator;
import nextzz.pppmodel.SubAnalogScalarManager;

public final class SubTemperatureSetting extends McAbstractSettingPartition{
  
  public static final String C_KEY_TITLE = "_temperature_setting";
  
  //===

  private static final SubTemperatureSetting SELF = new SubTemperatureSetting();
  public static final SubTemperatureSetting ccRefer(){return SELF;}//++>
  private SubTemperatureSetting(){}//++!

  //=== inner
  
  private class McTemperatureBiasItem implements MiSettingItem{
    private final int cmIndex;
    public McTemperatureBiasItem(int pxIndex){cmIndex=pxIndex;}
    @Override public String ccGetName() {
      return "[%]"
        +VcTranslator.tr(String.format("_ca%02d", cmIndex))
        +VcTranslator.tr("_ca_rvc_bias");
    }//+++
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_ca_rvc_bias");
    }//+++
    @Override public String ccGetLimitationInfo() {
      return "[50 ~ 200]";
    }//+++
    @Override public String ccGetValue() {
      return Integer.toString(SubAnalogScalarManager.ccRefer()
          .cmDesThermoCoupleBias.ccGet(cmIndex));
    }//+++
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedModel.ccLimitInclude(lpFixed, 50, 200);
      SubAnalogScalarManager.ccRefer()
        .cmDesThermoCoupleBias.ccSet(cmIndex, lpFixed);
    }//+++
  }//***
  
  private class McTemperatureOffsetItem implements MiSettingItem{
    private final int cmIndex;
    public McTemperatureOffsetItem(int pxIndex){cmIndex=pxIndex;}
    @Override public String ccGetName() {
      return "['C]"
        +VcTranslator.tr(String.format("_ca%02d", cmIndex))
        +VcTranslator.tr("_ca_rvc_offset");
    }//+++
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_ca_rvc_offset");
    }//+++
    @Override public String ccGetLimitationInfo() {
      return "[-99 ~ 99]";
    }//+++
    @Override public String ccGetValue() {
      return Integer.toString(SubAnalogScalarManager.ccRefer()
          .cmDesThermoCoupleOffset.ccGet(cmIndex));
    }//+++
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedModel.ccLimitInclude(lpFixed, -99, 99);
      SubAnalogScalarManager.ccRefer()
        .cmDesThermoCoupleOffset.ccSet(cmIndex, lpFixed);
    }//+++
  }//***
  
  //=== member
  
  public final MiSettingItem cmThermoCoupleADOffsetItem
    = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[AD]"+VcTranslator.tr("_kca_ad_offset");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_kca_ad_offset");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 5000]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString(SubAnalogScalarManager.ccRefer()
        .ccGetScalaADOffset(SubAnalogScalarManager.C_KEY_CA));
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedModel.ccLimitInclude(lpFixed, 0, 5000);
      SubAnalogScalarManager.ccRefer()
        .ccSetScalaADOffset(SubAnalogScalarManager.C_KEY_CA,lpFixed);
    }//++<
  };//***
  
  public final MiSettingItem cmThermoCoupleADSpanItem
    = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[AD]"+VcTranslator.tr("_kca_ad_span");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_kca_ad_span");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 5000]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString(SubAnalogScalarManager.ccRefer()
        .ccGetScalaADSpan(SubAnalogScalarManager.C_KEY_CA));
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedModel.ccLimitInclude(lpFixed, 0, 5000);
      SubAnalogScalarManager.ccRefer()
        .ccSetScalaADSpan(SubAnalogScalarManager.C_KEY_CA,lpFixed);
    }//++<
  };//***
  
  public final MiSettingItem cmThermoCoupleRealOffsetItem
    = new MiSettingItem() {
    @Override public String ccGetName() {
      return "['C]"+VcTranslator.tr("_kca_real_offset");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_kca_real_offset");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 9999]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString(SubAnalogScalarManager.ccRefer()
        .ccGetScalaRealOffset(SubAnalogScalarManager.C_KEY_CA));
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedModel.ccLimitInclude(lpFixed, 0, 9999);
      SubAnalogScalarManager.ccRefer()
        .ccSetScalaRealOffset(SubAnalogScalarManager.C_KEY_CA,lpFixed);
    }//++<
  };//***
  
  public final MiSettingItem cmThermoCoupleRealSpanItem
    = new MiSettingItem() {
    @Override public String ccGetName() {
      return "['C]"+VcTranslator.tr("_kca_real_span");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_kca_real_span");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 9999]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString(SubAnalogScalarManager.ccRefer()
        .ccGetScalaRealSpan(SubAnalogScalarManager.C_KEY_CA));
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedModel.ccLimitInclude(lpFixed, 0, 9999);
      SubAnalogScalarManager.ccRefer()
        .ccSetScalaRealSpan(SubAnalogScalarManager.C_KEY_CA,lpFixed);
    }//++<
  };//***
  
  //===
  
  @Override public void ccInit() {
    
    //-- general
    cmListOfItem.add(cmThermoCoupleADOffsetItem);
    cmListOfItem.add(cmThermoCoupleADSpanItem);
    cmListOfItem.add(cmThermoCoupleRealOffsetItem);
    cmListOfItem.add(cmThermoCoupleRealSpanItem);
    
    //-- revise ** a
    for(int it:new int[]{
      SubAnalogScalarManager.C_I_THI_CHUTE,
      SubAnalogScalarManager.C_I_THII_ENTRANCE,
      SubAnalogScalarManager.C_I_THIII_PIPE,
      SubAnalogScalarManager.C_I_THIV_SAND,
      SubAnalogScalarManager.C_I_THVI_MIXER
    }){
      cmListOfItem.add(new McTemperatureBiasItem(it));
      cmListOfItem.add(new McTemperatureOffsetItem(it));
    }//..~
    
    //-- revise ** r
    //[todo]::if(MainSpec...){for(int it:new int[]{...){...
    
    //-- revise ** s
    //[todo]::if(MainSpec...){for(int it:new int[]{...){...
    
  }//++!
  
  @Override public String ccGetTile() {
    return VcTranslator.tr(C_KEY_TITLE);
  }//++>
  
  //=== 
  
 }//***eof
