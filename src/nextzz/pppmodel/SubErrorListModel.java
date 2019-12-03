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

import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import kosui.ppplocalui.EcElement;
import kosui.ppplogic.ZcPulser;
import kosui.ppputil.VcLocalConsole;
import kosui.ppputil.VcTranslator;
import nextzz.pppswingui.SubErrorPane;
import processing.core.PApplet;

public final class SubErrorListModel implements ListModel<String>{
  
  static public final int C_SIZE = 256;
  static public final int C_MASK = 255;
  
  //===

  private static final SubErrorListModel SELF = new SubErrorListModel();
  public static final SubErrorListModel ccRefer(){return SELF;}//+++
  private SubErrorListModel(){}//++!

  //===
  
  static private int cmListenerCount=0;
  
  //===
  
  private class McError{
    final int cmID;
    final String cmKey;
    boolean cmActivity;
    ZcPulser cmPulser;
    public McError(int pxID) {
      cmID=pxID&C_MASK;
      cmActivity=false;
      cmPulser = new ZcPulser();
      cmKey=String.format("_merr%03d", cmID);
    }//++!
    void ccRun(){
      if(cmPulser.ccPulse(cmActivity)){
        if(cmActivity){
          //[head]::
          //[tood]:: % add to active list 
          SubErrorPane.ccWriteln("McError.ccRun:UP ->"+cmID);
        }else{
          
          //[tood]:: % remove from active list 
          SubErrorPane.ccWriteln("McError.ccRun:DN ->"+cmID);
        }//..?
        //[tood]:: % refresh list
      }//..?
    }//++~
    void ccSetIsActivated(boolean pxVal){
      cmActivity=pxVal;
    }//++<
    
  }//***
  
  private final ArrayList<McError> cmListOfError
    = new ArrayList<McError>(C_SIZE+1);
  
  private final LinkedList<McError> cmListOfActiveError
    = new LinkedList<McError>();
  
  //===
  
  public final void ccInit(){
    
    //--
    for(int i=0;i<C_SIZE;i++){
      cmListOfError.add(new McError(i));
    }//..~
    
    //--
    VcLocalConsole.ccSetMessageBarText("[MSG]:from SubErrorListModel.ccInit()");
    
  }//++!
  
  public final void ccLogic(){
    
    
    cmListOfError.get(1).ccSetIsActivated(EcElement.ccIsKeyPressed('1'));
    cmListOfError.get(2).ccSetIsActivated(EcElement.ccIsKeyPressed('2'));
    cmListOfError.get(3).ccSetIsActivated(EcElement.ccIsKeyPressed('3'));
    cmListOfError.get(4).ccSetIsActivated(EcElement.ccIsKeyPressed('4'));
    
    for(McError it : cmListOfError){
      it.ccRun();
    }//..~
    
    //[head]:: now what? 
    //[head]:: wait a f*+ minute! -> do you remember we already had the wm39 stuff ???
    
  }//++!
  
  //===
  
  synchronized public final String ccGetMessage(int pxID){
    if(pxID<0){return " ";}
    if(pxID>0){return VcTranslator.tr("_m_errmsg_"+PApplet.nf(pxID, 3));}
    return VcTranslator.tr("_m_no_fatal");
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
