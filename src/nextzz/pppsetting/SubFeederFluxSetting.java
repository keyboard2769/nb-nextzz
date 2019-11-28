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
import nextzz.pppmodel.MainPlantModel;
import nextzz.pppmodel.MainSpecificator;
import nextzz.pppmodel.SubAnalogScalarManager;
import processing.core.PApplet;

public final class SubFeederFluxSetting extends McAbstractSettingPartition{
  
  public static final String C_KEY_TITLE = "_feeder_flux_setting";

  private static final SubFeederFluxSetting SELF = new SubFeederFluxSetting();
  public static final SubFeederFluxSetting ccRefer(){return SELF;}//++>
  private SubFeederFluxSetting(){}//++!

  //=== duplicator
  
  private class McFeederRPMOffsetItem implements MiSettingItem{
    private final char cmSystem;
    private final int cmIndex;
    public McFeederRPMOffsetItem(char pxSystem_vr, int pxIndex) {
      cmSystem=pxSystem_vr;
      cmIndex=pxIndex;
    }//++!
    @Override public String ccGetName() {
      return "[rpm]:"+VcTranslator
        .tr(String.format("_rpm_offset_%cf", cmSystem))
        +Integer.toString(cmIndex);
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_feeder_rpm_offset");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[1 ~ 1800]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString(SubAnalogScalarManager.ccRefer()
        .ccGetVFeederFluxRPMOffset(cmIndex));
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed=VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedModel.ccLimitInclude(lpFixed, 1, 1800);
      SubAnalogScalarManager.ccRefer()
        .ccSetVFeederFluxRPMOffset(cmIndex, lpFixed);
    }//++<
  }//***
  
  private class McFeederTPHOffsetItem implements MiSettingItem{
    private final char cmSystem;
    private final int cmIndex;
    public McFeederTPHOffsetItem(char pxSystem_vr, int pxIndex) {
      cmSystem=pxSystem_vr;
      cmIndex=pxIndex;
    }//++!
    @Override public String ccGetName() {
      return "[tph]:"+VcTranslator
        .tr(String.format("_tph_offset_%cf", cmSystem))
        +Integer.toString(cmIndex);
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_feeder_tph_offset");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[1 ~ 200]";
    }//++>
    @Override public String ccGetValue() {
      int lpRaw = SubAnalogScalarManager.ccRefer()
        .ccGetVFeederFluxTPHOffset(cmIndex);
      String lpRes = VcNumericUtility.ccFormatFloatForOneAfter(lpRaw);
      return lpRes;
    }//++>
    @Override public void ccSetValue(String pxVal) {
      float lpFixed = VcNumericUtility.ccParseFloatString(pxVal);
      lpFixed=PApplet.constrain(lpFixed, 1f, 200f);
      SubAnalogScalarManager.ccRefer()
        .ccSetVFeederFluxTPHOffset(
          cmIndex,
          VcNumericUtility.ccInteger(lpFixed,10f)
        );
    }//++<
  }//***
  
  private class McFeederRPMSpanItem implements MiSettingItem{
    private final char cmSystem;
    private final int cmIndex;
    public McFeederRPMSpanItem(char pxSystem_vr, int pxIndex) {
      cmSystem=pxSystem_vr;
      cmIndex=pxIndex;
    }//++!
    @Override public String ccGetName() {
      return "[rpm]:"+VcTranslator
        .tr(String.format("_rpm_span_%cf",cmSystem))
        +Integer.toString(cmIndex);
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_feeder_rpm_span");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[1 ~ 1800]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString(SubAnalogScalarManager.ccRefer()
        .ccGetVFeederFluxRPMSpan(cmIndex));
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed=VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedModel.ccLimitInclude(lpFixed, 1, 1800);
      SubAnalogScalarManager.ccRefer()
        .ccSetVFeederFluxRPMSpan(cmIndex, lpFixed);
    }//++<
  }//***
  
  private class McFeederTPHSpanItem implements MiSettingItem{
    private final char cmSystem;
    private final int cmIndex;
    public McFeederTPHSpanItem(char pxSystem_vr, int pxIndex) {
      cmSystem=pxSystem_vr;
      cmIndex=pxIndex;
    }//++!
    @Override public String ccGetName() {
      return "[tph]:"+VcTranslator
        .tr(String.format("_tph_span_%cf",cmSystem))
        +Integer.toString(cmIndex);
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_feeder_tph_span");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[1 ~ 200]";
    }//++>
    @Override public String ccGetValue() {
      int lpRaw = SubAnalogScalarManager.ccRefer()
        .ccGetVFeederFluxTPHSpan(cmIndex);
      String lpRes = VcNumericUtility.ccFormatFloatForOneAfter(lpRaw);
      return lpRes;
    }//++>
    @Override public void ccSetValue(String pxVal) {
      float lpFixed = VcNumericUtility.ccParseFloatString(pxVal);
      lpFixed=PApplet.constrain(lpFixed, 1f, 200f);
      SubAnalogScalarManager.ccRefer()
        .ccSetVFeederFluxTPHSpan(
          cmIndex, VcNumericUtility.ccInteger(lpFixed,10f)
        );
    }//++<
  }//***
  
  //=== raw
  
  public final MiSettingItem cmVRatioBaseTPHItem = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[tph]"+VcTranslator.tr("_v_ratio_base");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_v_ratio_base");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 400]";
    }//++>
    @Override public String ccGetValue() {
      return Float.toString(MainPlantModel.ccRefer().vmVRatioBaseTPH);
    }//++>
    @Override public void ccSetValue(String pxVal) {
      float lpFixed = VcNumericUtility.ccParseFloatString(pxVal);
      lpFixed = PApplet.constrain(lpFixed, 0f, 400f);
      MainPlantModel.ccRefer().vmVRatioBaseTPH=lpFixed;
    }//++<
  };//***
  
  public final MiSettingItem cmVRatioWidthTPHItem = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[tph]"+VcTranslator.tr("_v_ratio_width");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_v_ratio_width");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 400]";
    }//++>
    @Override public String ccGetValue() {
      return "<not_yet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      float lpFixed = VcNumericUtility.ccParseFloatString(pxVal);
      lpFixed = PApplet.constrain(lpFixed, 0f, 400f);
      System.err.println("cmVRatioWidthTPHItem::not_yet:"
        +Float.toString(lpFixed));
    }//++<
  };//***
  
  //===

  @Override public void ccInit() {
    
    cmListOfItem.add(cmVRatioBaseTPHItem);
    cmListOfItem.add(cmVRatioWidthTPHItem);
    for(
      int i=MainPlantModel.C_VF_UI_VALID_HEAD;
      i<=MainSpecificator.ccRefer().vmVFeederAmount;
      i++
    ){
      cmListOfItem.add(new McFeederRPMOffsetItem('v', i));
      cmListOfItem.add(new McFeederTPHOffsetItem('v', i));
      cmListOfItem.add(new McFeederRPMSpanItem('v', i));
      cmListOfItem.add(new McFeederTPHSpanItem('v', i));
    }//..~
  }//++!
  
  @Override public String ccGetTile() {
    return VcTranslator.tr(C_KEY_TITLE);
  }//++>
  
 }//***eof
