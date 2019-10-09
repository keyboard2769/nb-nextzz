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
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import kosui.ppplogic.ZcRangedModel;

public final class MainSettingManager implements ListModel<String>{

  private static final MainSettingManager SELF = new MainSettingManager();
  public static final MainSettingManager ccRefer(){return SELF;}//+++

  //===
  
  private static int cmListDataListenerCounter=0;
  
  private final List<McAbstractSettingPartition> cmListOfPartition 
    = new ArrayList<McAbstractSettingPartition>();
  
  private MainSettingManager(){
    
    cmListOfPartition.add(SubFeederFluxSetting.ccRefer());
    cmListOfPartition.add(SubCTSlotSetting.ccRefer());
  
  }//..!
  
  //===
  
  public final
  MiSettingItem ccGetSelectedItem(int pxModelIndex, int pxItemIndex){
    McAbstractSettingPartition lpModel = ccGetSelectedModel(pxModelIndex);
    if(lpModel==null){return null;}
    MiSettingItem lpItem = lpModel.ccGetItem(pxItemIndex);
    return lpItem;
  }//+++
  
  public final McAbstractSettingPartition ccGetSelectedModel(int pxIndex){
    if(cmListOfPartition.isEmpty()){return null;}
    int lpFixed=ZcRangedModel
      .ccLimitInclude(pxIndex, 0,cmListOfPartition.size());
    return cmListOfPartition.get(lpFixed);
  }//+++
  
  //===

  @Override public int getSize() {
    return cmListOfPartition.size();
  }//+++

  @Override public String getElementAt(int pxIndex) {
    return cmListOfPartition.get(pxIndex).ccGetTile();
  }//+++

  @Override public void addListDataListener(ListDataListener pxL) {
    cmListDataListenerCounter++;
  }//+++

  @Override public void removeListDataListener(ListDataListener pxL) {
    cmListDataListenerCounter--;
  }//+++
  
  public final int ccGetListDataListenerCounter(){
    return cmListDataListenerCounter;
  }//+++
  
 }//***eof
