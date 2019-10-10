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


package nextzz.pppmodel;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import kosui.ppputil.VcLocalConsole;

public final class SubErrorListModel implements ListModel<String>{

  private static final SubErrorListModel SELF = new SubErrorListModel();
  public static final SubErrorListModel ccRefer(){return SELF;}//+++
  private SubErrorListModel(){}//++!

  //===
  
  static private int cmListenerCount=0;
  
  public final void ccInit(){
    
    VcLocalConsole.ccSetMessage("[MSG]:from SubErrorListModel.ccInit()");
    
  }//++!
  
  public final void ccLogic(){
  
  }//++!
  
  //===
  
  synchronized public final String ccGetMessage(int pxID){
    //[todo]::fix this!!
    String lpF = " ";
    if(pxID<0){return lpF;}
    if(pxID==0){lpF="all clear";}
    return lpF;
  }//+++
  
  //===

  @Override public int getSize() {
    //[notyet]::
    return 32;
  }//+++
  
  @Override public String getElementAt(int index) {
    //[notyet]::
    return "<???>";
  }//+++

  @Override public void addListDataListener(ListDataListener l) {
    cmListenerCount++;
  }//+++

  @Override public void removeListDataListener(ListDataListener l) {
    cmListenerCount--;
  }//+++
  
  public int removeListDataListenerCount(){
    return cmListenerCount;
  }//+++
  
 }//***eof
