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
    
    //-- tare
    cmListOfItem.add(cmAGTareWeight);
    cmListOfItem.add(cmFRTareWeight);
    cmListOfItem.add(cmASTareWeight);
    
    //-- scalas
    //[head]::
    
  }//++!
  
  @Override public String ccGetTile() {
    return VcTranslator.tr(C_KEY_TITLE);
  }//++>
  
}//***eof
