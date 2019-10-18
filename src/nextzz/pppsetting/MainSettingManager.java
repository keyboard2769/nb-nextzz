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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import kosui.ppplogic.ZcRangedModel;
import nextzz.pppmodel.SubAnalogScalarManager;

public final class MainSettingManager implements ListModel<String>{

  private static final MainSettingManager SELF = new MainSettingManager();
  public static final MainSettingManager ccRefer(){return SELF;}//+++

  //===
  
  private static int cmListDataListenerCounter=0;
  
  private final List<McAbstractSettingPartition> cmListOfPartition 
    = new ArrayList<McAbstractSettingPartition>();
  
  private MainSettingManager(){
    ssAdd(SubCombustSetting.ccRefer());
    ssAdd(SubFeederFluxSetting.ccRefer());
    ssAdd(SubLinearSetting.ccRefer());
    ssAdd(SubTemperatureSetting.ccRefer());
    ssAdd(SubCTSlotSetting.ccRefer());
  }//++!
  
  private void ssAdd(McAbstractSettingPartition pxChild){
    if(pxChild==null){return;}
    pxChild.ccInit();
    cmListOfPartition.add(pxChild);
  }//++!
  
  public final void ccLoadFromFile(File pxFile){
    if(pxFile==null){
    /* 7 */  System.err
        .println("MainSettingManager.ccLoadFromFile()::not yet.");
    }//..?
    /* 7 */SubAnalogScalarManager.ccRefer().ccSetCTSlotREALSpan(0,  750);
    /* 7 */SubAnalogScalarManager.ccRefer().ccSetCTSlotREALSpan(1, 3000);
    /* 7 */SubAnalogScalarManager.ccRefer().ccSetCTSlotREALSpan(2, 2600);
    /* 7 */SubAnalogScalarManager.ccRefer().ccSetCTSlotREALSpan(3,  300);
    //--
    /* 7 */SubAnalogScalarManager.ccRefer().ccSetCTSlotREALSpan(4,  900);
    /* 7 */SubAnalogScalarManager.ccRefer().ccSetCTSlotREALSpan(5,  220);
    /* 7 */SubAnalogScalarManager.ccRefer().ccSetCTSlotREALSpan(6,  750);
    /* 7 */SubAnalogScalarManager.ccRefer().ccSetCTSlotREALSpan(7,  900);
    //--
    /* 7 */SubAnalogScalarManager.ccRefer().ccSetCTSlotREALSpan( 8,  160);
    /* 7 */SubAnalogScalarManager.ccRefer().ccSetCTSlotREALSpan( 9,  150);
    /* 7 */SubAnalogScalarManager.ccRefer().ccSetCTSlotREALSpan(10,  150);
    /* 7 */SubAnalogScalarManager.ccRefer().ccSetCTSlotREALSpan(11,   80);
    //--
    /* 7 */SubAnalogScalarManager.ccRefer().ccSetCTSlotREALSpan(12,  150);
    /* 7 */SubAnalogScalarManager.ccRefer().ccSetCTSlotREALSpan(13,  380);
    /* 7 */SubAnalogScalarManager.ccRefer().ccSetCTSlotREALSpan(14,  450);
    /* 7 */SubAnalogScalarManager.ccRefer().ccSetCTSlotREALSpan(15,  110);
    /* 7 *///return true;
  }//++!
  
  //===
  
  public final
  MiSettingItem ccGetSelectedItem(int pxModelIndex, int pxItemIndex){
    McAbstractSettingPartition lpModel = ccGetSelectedModel(pxModelIndex);
    if(lpModel==null){return null;}
    MiSettingItem lpItem = lpModel.ccGetItem(pxItemIndex);
    return lpItem;
  }//++>
  
  public final McAbstractSettingPartition ccGetSelectedModel(int pxIndex){
    if(cmListOfPartition.isEmpty()){return null;}
    int lpFixed=ZcRangedModel
      .ccLimitInclude(pxIndex, 0,cmListOfPartition.size());
    return cmListOfPartition.get(lpFixed);
  }//++>
  
  //===

  @Override public int getSize() {
    return cmListOfPartition.size();
  }//++>

  @Override public String getElementAt(int pxIndex) {
    return cmListOfPartition.get(pxIndex).ccGetTile();
  }//++>

  @Override public void addListDataListener(ListDataListener pxL) {
    cmListDataListenerCounter++;
  }//+<>

  @Override public void removeListDataListener(ListDataListener pxL) {
    cmListDataListenerCounter--;
  }//+<>
  
  public final int ccGetListDataListenerCounter(){
    return cmListDataListenerCounter;
  }//+<>
  
 }//***eof
