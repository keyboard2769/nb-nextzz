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
import nextzz.pppmodel.MainSpecificator;
import nextzz.pppmodel.SubAnalogScalarManager;

public final class SubFeederFluxSetting extends McAbstractSettingPartition{
  
  public static final String C_KEY_TITLE = "_feeder_flux_setting";

  private static final SubFeederFluxSetting SELF = new SubFeederFluxSetting();
  public static final SubFeederFluxSetting ccRefer(){return SELF;}//++>
  private SubFeederFluxSetting(){}//++!

  //=== inner 
  
  private class McFeederRPMSpanItem implements MiSettingItem{
    private final char cmSystem;
    private final int cmIndex;
    public McFeederRPMSpanItem(char pxSystem_vr, int pxIndex) {
      cmSystem=pxSystem_vr;
      cmIndex=pxIndex;
    }//++!
    @Override public String ccGetName() {
      return "[rpm]:"+VcTranslator
        .tr(String.format("_rpm_span_%cf%02d", cmSystem,cmIndex));
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_feeder_rpm_span");
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
        .tr(String.format("_tph_span_%cf%02d", cmSystem,cmIndex));
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_feeder_tph_span");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[1 ~ 200]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString(SubAnalogScalarManager.ccRefer()
        .ccGetVFeederFluxTPHSpan(cmIndex));
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed=VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedModel.ccLimitInclude(lpFixed, 1, 200);
      SubAnalogScalarManager.ccRefer()
        .ccSetVFeederFluxTPHSpan(cmIndex, lpFixed);
    }//++<
  }//***
  
  //===

  @Override public void ccInit() {
    for(int i=0;i<=MainSpecificator.ccRefer().mnVFeederAmount;i++){
      cmListOfItem.add(new McFeederRPMSpanItem('v', i));
      cmListOfItem.add(new McFeederTPHSpanItem('v', i));
    }//..~
  }//++!
  
  @Override public String ccGetTile() {
    return VcTranslator.tr(C_KEY_TITLE);
  }//++>
  
 }//***eof
