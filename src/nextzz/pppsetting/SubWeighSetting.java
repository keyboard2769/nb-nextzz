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
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcTranslator;
import nextzz.pppmodel.SubWeighControlManager;

public class SubWeighSetting extends McAbstractSettingPartition{
  
  public static final String C_KEY_TITLE = "_weigh_setting";
  
  private static final SubWeighSetting SELF = new SubWeighSetting();
  public static final SubWeighSetting ccRefer(){return SELF;}//++>
  private SubWeighSetting(){}//++!
  
  //===
  
  public final MiSettingItem cmAGEmtyKGItem = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[KG]"+VcTranslator.tr("_ag_emty_kg");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_ag_emty_kg");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 400]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString
        (SubWeighControlManager.ccRefer().vmAGEmptyKG);
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 0, 400);
      SubWeighControlManager.ccRefer().vmAGEmptyKG=lpFixed;
    }//++<
  };//***
  
  public final MiSettingItem cmFREmtyKGItem = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[KG]"+VcTranslator.tr("_fr_emty_kg");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_fr_emty_kg");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 50]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString
        (SubWeighControlManager.ccRefer().vmFREmptyKG);
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 0, 400);
      SubWeighControlManager.ccRefer().vmFREmptyKG=lpFixed;
    }//++<
  };//***
  
  public final MiSettingItem cmASEmtyKGItem = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[KG]"+VcTranslator.tr("_as_emty_kg");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_as_emty_kg");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 50]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString
        (SubWeighControlManager.ccRefer().vmASEmptyKG);
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 0, 400);
      SubWeighControlManager.ccRefer().vmASEmptyKG=lpFixed;
    }//++<
  };//***
  
  //===

  @Override public void ccInit() {
    cmListOfItem.add(cmAGEmtyKGItem);
    cmListOfItem.add(cmFREmtyKGItem);
    cmListOfItem.add(cmASEmtyKGItem);
  }//++!

  @Override public String ccGetTile() {
    return VcTranslator.tr(C_KEY_TITLE);
  }//++>
  
}//***eof
