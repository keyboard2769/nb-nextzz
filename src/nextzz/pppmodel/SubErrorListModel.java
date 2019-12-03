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
import kosui.ppputil.VcStampUtility;
import kosui.ppputil.VcTranslator;
import nextzz.pppswingui.SubErrorPane;
import processing.core.PApplet;

public final class SubErrorListModel implements ListModel<String>{
  
  static public final int C_SIZE = 256;
  static public final int C_MASK = 255;
  static private int cmListenerCount=0;
  static private final String C_TAG_SET = VcTranslator.tr("_occur");
  static private final String C_TAG_RST = VcTranslator.tr("_clear");
  
  //===

  private static final SubErrorListModel SELF = new SubErrorListModel();
  public static final SubErrorListModel ccRefer(){return SELF;}//+++
  private SubErrorListModel(){}//++!

  //===
  
  private class McError{
    final int cmID;
    final String cmKey;
    final String cmName;
    boolean cmActivity;
    ZcPulser cmPulser;
    public McError(int pxID) {
      cmID=pxID&C_MASK;
      cmActivity=false;
      cmPulser = new ZcPulser();
      cmKey=String.format("_merr%03d", cmID);
      cmName=VcTranslator.tr(cmKey);
    }//++!
    void ccRun(){
      if(cmPulser.ccPulse(cmActivity)){
        if(cmActivity){
          //[head]:: d
          ssAddToActivated(cmID);
          SubErrorPane.ccWriteln(String.format("[%s]%s|%s",
            C_TAG_SET,cmName, VcStampUtility.ccDataLogTypeI()
          ));
        }else{
          ssRemoveFromActivated(cmID);
          SubErrorPane.ccWriteln(String.format("[%s]%s|%s",
            C_TAG_RST,cmName, VcStampUtility.ccDataLogTypeI()
          ));
        }//..?
      }//..?
    }//++~
    void ccSetIsActivated(boolean pxVal){
      cmActivity=pxVal;
    }//++<
    String ccToListRepresentation(){
      return String.format("[ERR%03d]%s",cmID,cmName);
    }//++>
  }//***
  
  private final ArrayList<McError> cmListOfAll
    = new ArrayList<McError>(C_SIZE+1);
  
  private final LinkedList<McError> cmListOfActivated
    = new LinkedList<McError>();
  
  //===
  
  public final void ccInit(){
    
    //--
    for(int i=0;i<C_SIZE;i++){
      cmListOfAll.add(new McError(i));
    }//..~
    
    //--
    VcLocalConsole.ccSetMessageBarText("[MSG]:from SubErrorListModel.ccInit()");
    
  }//++!
  
  public final void ccLogic(){
    
    tstDummyToggler();//..[later]::deletethis
    //[head]:: now what? 
    //[head]:: wait a f*+ minute! -> do you remember we already had the wm39 stuff ???
    
    for(McError it : cmListOfAll){
      it.ccRun();
    }//..~
    
  }//++!
  
  //===
  
  synchronized public final String ccGetMessage(int pxID){
    if(pxID<0){return " ";}
    if(pxID>0){return cmListOfAll.get(pxID&C_MASK).ccToListRepresentation();}
    return VcTranslator.tr("_m_no_fatal");
  }//+++
  
  //===
  
  @Deprecated private void tstDummyToggler(){
    if(EcElement.ccIsKeyPressed('1')){cmListOfAll.get(1).ccSetIsActivated(true);}
    if(EcElement.ccIsKeyPressed('2')){cmListOfAll.get(2).ccSetIsActivated(true);}
    if(EcElement.ccIsKeyPressed('3')){cmListOfAll.get(3).ccSetIsActivated(true);}
    if(EcElement.ccIsKeyPressed('4')){cmListOfAll.get(4).ccSetIsActivated(true);}
    if(EcElement.ccIsKeyPressed('5')){
      cmListOfAll.get(1).ccSetIsActivated(false);
      cmListOfAll.get(2).ccSetIsActivated(false);
      cmListOfAll.get(3).ccSetIsActivated(false);
      cmListOfAll.get(4).ccSetIsActivated(false);
    }
  }//+++
  
  private static void ssAddToActivated(int pxID){
    McError lpTarget = SELF.cmListOfAll.get(pxID&C_MASK);
    if(SELF.cmListOfActivated.contains(lpTarget)){return;}
    SELF.cmListOfActivated.add(lpTarget);
  }//+++
  
  private static void ssRemoveFromActivated(int pxID){
    McError lpTarget = SELF.cmListOfAll.get(pxID&C_MASK);
    if(!SELF.cmListOfActivated.contains(lpTarget)){return;}
    SELF.cmListOfActivated.remove(lpTarget);
  }//+++
  
  //===

  @Override public int getSize() {
    if(cmListOfActivated.isEmpty()){
      return 1;
    }else{
      return cmListOfActivated.size();
    }//..?
  }//+++
  
  @Override public String getElementAt(int index) {
    if(cmListOfActivated.isEmpty()){
      return VcTranslator.tr("_m_error_all_clear");
    }else{
      return cmListOfActivated.get(index&C_MASK).ccToListRepresentation();
    }//..?
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
