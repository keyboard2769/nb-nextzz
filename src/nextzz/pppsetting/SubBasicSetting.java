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

import kosui.ppputil.VcTranslator;

public class SubBasicSetting extends McAbstractSettingPartition{
  
  public static final String C_KEY_TITLE = "_basic_setting";
  
  private static final SubBasicSetting SELF = new SubBasicSetting();
  public static final SubBasicSetting ccRefer(){return SELF;}//++>
  private SubBasicSetting(){}//++!
  
  //===
  
  public final MiSettingItem cmDohItem = new MiSettingItem() {
    //[todo]::..what should i do ??
    @Override public String ccGetName() {
      return "[KG]"+VcTranslator.tr("_doh");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_doh_dont_even_know_what_doh");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "where is your sense of humor??";
    }//++>
    @Override public String ccGetValue() {
      return "<doh>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      System.err.println
        ("you are supoosed to say `doh` in your office by now.");
    }//++<
  };//***
  
  //===

  @Override public void ccInit() {
    cmListOfItem.add(cmDohItem);
  }//++!

  @Override public String ccGetTile() {
    return VcTranslator.tr(C_KEY_TITLE);
  }//++>
  
}//***eof
