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

public class SubServientTimerSetting extends McAbstractSettingPartition{
  
  public static final String C_KEY_TITLE = "_servient_timer_setting";
  
  private static final SubServientTimerSetting SELF
    = new SubServientTimerSetting();
  public static final SubServientTimerSetting ccRefer(){return SELF;}//++>
  private SubServientTimerSetting(){}//++!
  
  //=== inner
  
  private class McServientTimerItem implements MiSettingItem{
    private final int cmIndex;
    public McServientTimerItem(int pxIndex){cmIndex=pxIndex;}//++!
    @Override public String ccGetName() {
      return "[S]"+VcTranslator.tr(String.format("_svtimer%03d",cmIndex));
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_svtimer");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 9999]";
    }//++>
    @Override public String ccGetValue() {
      return "<noteyet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 0, 400);
      System.out.println(this.ccGetName()
        +".ccSetValue()::not_yet:"+Integer.toString(lpFixed));
    }//++<
  }//***
  
  //=== 
  
  @Override public void ccInit() {
    
    //[head]::maybe it is just not here??
    for(
      int i=1,s=32;//..[todo]::how do we const this
      i<s;i++
    ){
      cmListOfItem.add(new McServientTimerItem(i));
    }//..~
    
  }//++!

  @Override public String ccGetTile() {
    return VcTranslator.tr(C_KEY_TITLE);
  }//++>
  
}//***eof
