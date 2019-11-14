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
import processing.core.PApplet;

public class SubLinearSetting extends McAbstractSettingPartition{
  
  public static final String C_KEY_TITLE = "_linear_setting";

  private static final SubLinearSetting SELF = new SubLinearSetting();
  public static final SubLinearSetting ccRefer(){return SELF;}//++>
  private SubLinearSetting(){}//++!
  
  //===
  
  private class McScalaADOffsetItem implements MiSettingItem{
    private final int cmKey;
    public McScalaADOffsetItem(int pxKey) {
      cmKey=pxKey;
    }//++!
    @Override public String ccGetName() {
      return "[AD]:"+VcTranslator
        .tr(String.format("_linear_ad_offset_%02d", cmKey));
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_linear_ad_offset");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 10000]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString(SubAnalogScalarManager.ccRefer()
        .ccGetScalaADOffset(cmKey));
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed=VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedModel.ccLimitInclude(lpFixed, 0, 10000);
      SubAnalogScalarManager.ccRefer()
        .ccSetScalaADOffset(cmKey, lpFixed);
    }//++<
  }//***
  
  private class McScalaADSpanItem implements MiSettingItem{
    private final int cmKey;
    public McScalaADSpanItem(int pxKey) {
      cmKey=pxKey;
    }//++!
    @Override public String ccGetName() {
      return "[AD]:"+VcTranslator
        .tr(String.format("_linear_ad_span_%02d", cmKey));
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_linear_ad_span");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 10000]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString(SubAnalogScalarManager.ccRefer()
        .ccGetScalaADSpan(cmKey));
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed=VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedModel.ccLimitInclude(lpFixed, 0, 10000);
      SubAnalogScalarManager.ccRefer()
        .ccSetScalaADSpan(cmKey, lpFixed);
    }//++<
  }//***
  
  //[tofix]::can the real span/offset practically got set in general?
  private class McScalaRealOffsetItem implements MiSettingItem{
    private final int cmKey;
    public McScalaRealOffsetItem(int pxKey) {
      cmKey=pxKey;
    }//++!
    @Override public String ccGetName() {
      return VcTranslator.tr(String.format("_linear_ureal_offset_%02d", cmKey));
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_linear_ureal_offset");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 5000]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString(SubAnalogScalarManager.ccRefer()
        .ccGetScalaRealOffset(cmKey));
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed=VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedModel.ccLimitInclude(lpFixed, 0, 5000);
      SubAnalogScalarManager.ccRefer()
        .ccSetScalaRealOffset(cmKey, lpFixed);
    }//++<
  }//***
  
  private class McScalaRealSpanItem implements MiSettingItem{
    private final int cmKey;
    public McScalaRealSpanItem(int pxKey) {
      cmKey=pxKey;
    }//++!
    @Override public String ccGetName() {
      return VcTranslator.tr(String.format("_linear_ureal_span_%02d", cmKey));
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_linear_ureal_span");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 5000]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString(SubAnalogScalarManager.ccRefer()
        .ccGetScalaRealSpan(cmKey));
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed=VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedModel.ccLimitInclude(lpFixed, 0, 5000);
      SubAnalogScalarManager.ccRefer()
        .ccSetScalaRealSpan(cmKey, lpFixed);
    }//++<
  }//***
  
  //===
  
  public final MiSettingItem cmAGTareWeight = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[kg]"+VcTranslator.tr("_ag_tare_kg");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_ag_tare_kg");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[-1000 ~ 1000]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString(SubAnalogScalarManager
        .ccRefer().ccGetAGReviseOffsetKG());
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed = ZcRangedModel.ccLimitInclude(lpFixed, -1000, +1000);
      SubAnalogScalarManager.ccRefer().ccSetAGReviseOffsetKG(lpFixed);
    }//++<
  };//***
  
  public final MiSettingItem cmFRTareWeight = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[kg]"+VcTranslator.tr("_fr_tare_kg");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_fr_tare_kg");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[-1000 ~ 1000]";
    }//++>
    @Override public String ccGetValue() {
      return Float.toString((float)(SubAnalogScalarManager
        .ccRefer().ccGetFRReviseOffsetKG())/10f);
    }//++>
    @Override public void ccSetValue(String pxVal) {
      float lpFixed = VcNumericUtility.ccParseFloatString(pxVal);
      lpFixed = PApplet.constrain(lpFixed, -1000f, 1000f);
      SubAnalogScalarManager.ccRefer()
        .ccSetFRReviseOffsetKG((int)(lpFixed*10f));
    }//++<
  };//***
  
  public final MiSettingItem cmASTareWeight = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[kg]"+VcTranslator.tr("_as_tare_kg");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_as_tare_kg");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[-1000 ~ 1000]";
    }//++>
    @Override public String ccGetValue() {
      return Float.toString((float)(SubAnalogScalarManager
        .ccRefer().ccGetASReviseOffsetKG())/10f);
    }//++>
    @Override public void ccSetValue(String pxVal) {
      float lpFixed = VcNumericUtility.ccParseFloatString(pxVal);
      lpFixed = PApplet.constrain(lpFixed, -1000f, 1000f);
      SubAnalogScalarManager.ccRefer()
        .ccSetASReviseOffsetKG((int)(lpFixed*10f));
    }//++<
  };//***
  
  //===
  
  @Override public void ccInit() {
    
    //[head]::maybe it is just not here??
    
    //-- tare
    cmListOfItem.add(cmAGTareWeight);
    cmListOfItem.add(cmFRTareWeight);
    cmListOfItem.add(cmASTareWeight);
    
    //-- scalas
    //VcConst.ccPrintln("id", Arrays.toString(SubAnalogScalarManager.ccRefer().ccGetScalaKeyList()));
    for(Object it : SubAnalogScalarManager.ccRefer().ccGetScalaKeyList()){
      int lpKey = VcNumericUtility.ccInteger(it);
      cmListOfItem.add(new McScalaADOffsetItem(lpKey));
      cmListOfItem.add(new McScalaADSpanItem(lpKey));
      cmListOfItem.add(new McScalaRealOffsetItem(lpKey));
      cmListOfItem.add(new McScalaRealSpanItem(lpKey));
    }//..~
    
  }//++!
  
  @Override public String ccGetTile() {
    return VcTranslator.tr(C_KEY_TITLE);
  }//++>
  
}//***eof
