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

public final class SubTemperatureSetting extends McAbstractSettingPartition{
  
  public static final String C_KEY_TITLE = "_temperature_setting";
  
  //===

  private static final SubTemperatureSetting SELF = new SubTemperatureSetting();
  public static final SubTemperatureSetting ccGetInstance(){return SELF;}//++>
  private SubTemperatureSetting(){}//++!

  //===
  
  private class McTemperatureBiasItem implements MiSettingItem{
    private final int cmIndex;
    public McTemperatureBiasItem(int pxIndex){cmIndex=pxIndex;}
    @Override public String ccGetName() {
      return "[%]"
        +VcTranslator.tr(String.format("_ca%02d", cmIndex))
        +VcTranslator.tr("_ca_bias");
    }//+++
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_ca_bias");
    }//+++
    @Override public String ccGetLimitationInfo() {
      return "[50 ~ 200]";
    }//+++
    @Override public String ccGetValue() {
      return "<not_yet>";
    }//+++
    @Override public void ccSetValue(String pxVal) {
      ;
    }//+++
  }//***
  
  private class McTemperatureOffsetItem implements MiSettingItem{
    private final int cmIndex;
    public McTemperatureOffsetItem(int pxIndex){cmIndex=pxIndex;}
    @Override public String ccGetName() {
      return "['C]"
        +VcTranslator.tr(String.format("_ca%02d", cmIndex))
        +VcTranslator.tr("_ca_offset");
    }//+++
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_ca_offset");
    }//+++
    @Override public String ccGetLimitationInfo() {
      return "[50 ~ 200]";
    }//+++
    @Override public String ccGetValue() {
      return "<not_yet>";
    }//+++
    @Override public void ccSetValue(String pxVal) {
      ;
    }//+++
  }//***
  
  //===
  
  @Override public void ccInit() {
    
  }//++!
  
  @Override public String ccGetTile() {
    return VcTranslator.tr(C_KEY_TITLE);
  }//++>
  
  //=== 
  
 }//***eof
