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

import java.util.ArrayList;
import java.util.List;
import kosui.ppplogic.ZcRangedModel;
import kosui.pppmodel.McTableAdapter;
import kosui.ppputil.VcTranslator;

public abstract class McAbstractSettingPartition extends McTableAdapter{
  
  protected final List<MiSettingItem> cmListOfItem 
    = new ArrayList<MiSettingItem>();
  
  //===
  
  public abstract String ccGetTile();
  
  public final MiSettingItem ccGetItem(int pxIndex){
    if(cmListOfItem.isEmpty()){return null;}
    else{
      return cmListOfItem
        .get(ZcRangedModel.ccLimitInclude(pxIndex, 0,cmListOfItem.size()));
    }//..?
  }//+++
  
  //===
  
  @Override public int getColumnCount() {
    return 2;
  }//+++

  @Override public String getColumnName(int pxColumnIndex) {
    switch (pxColumnIndex) {
      case 0:return VcTranslator.tr("_value");
      case 1:return VcTranslator.tr("_item");
      default:return "<?>";
    }//...?
  }//+++

  @Override public int getRowCount() {
    return cmListOfItem.size();
  }//+++

  @Override public Object getValueAt(int pxRowIndex, int pxColumnIndex) {
    MiSettingItem lpTarget = cmListOfItem.get(pxRowIndex);
    if(lpTarget==null){return "<?>";}
    switch (pxColumnIndex) {
      case 0:return lpTarget.ccGetValue();
      case 1:return lpTarget.ccGetName();
      default:return "<?>";
    }//...?
  }//+++
  
}//***eof
