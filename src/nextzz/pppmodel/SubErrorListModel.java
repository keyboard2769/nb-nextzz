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
import kosui.ppplogic.ZcPulser;
import kosui.ppplogic.ZcRangedModel;
import kosui.ppputil.VcLocalConsole;
import kosui.ppputil.VcStampUtility;
import kosui.ppputil.VcTranslator;
import nextzz.pppdelegate.SubErrorDelegator;
import nextzz.pppswingui.SubErrorPane;

public final class SubErrorListModel implements ListModel<String>{
  
  static private final int C_MASK = 255;
  static private final int C_SIZE = 256;
  static private final String C_TAG_SET = VcTranslator.tr("_occur");
  static private final String C_TAG_RST = VcTranslator.tr("_clear");
  
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
    final String cmName;
    boolean cmActivity;
    ZcPulser cmPulser;
    public McError(int pxID) {
      cmID=pxID&C_MASK;
      cmActivity=false;
      cmPulser = new ZcPulser();
      cmKey=String.format("_emsg%03d", cmID);
      cmName=VcTranslator.tr(cmKey);
    }//++!
    void ccRun(){
      if(cmPulser.ccPulse(cmActivity)){
        if(cmActivity){
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
      if(ZcRangedModel.ccContains(cmID, 1, 31)){//..yellow print hard code
        return String.format("[ERR%03d]%s", cmID, cmName);
      }//..?
      if(ZcRangedModel.ccContains(cmID, 32, 63)){//..yellow print hard code
        return String.format("[WRN%03d]%s", cmID, cmName);
      }//..?
      return String.format("[MSG%03d]%s", cmID, cmName);
    }//++>
    String ccGetDescription(){
      return VcTranslator.tr(String.format("_dscp_emsg%03d",cmID));
    }//++>
  }//***
  
  private final ArrayList<McError> cmListOfAll
    = new ArrayList<McError>(C_SIZE+1);
  
  private final LinkedList<McError> cmListOfActivated
    = new LinkedList<McError>();
  
  //===
  
  public final void ccInit(){
    
    //-- create
    for(int i=0;i<C_SIZE;i++){
      cmListOfAll.add(new McError(i));
    }//..~
    
    //-- post
    VcLocalConsole.ccSetMessageBarText("[MSG]:from SubErrorListModel.ccInit()");
    
  }//++!
  
  public final void ccLogic(){
    for(McError it : cmListOfAll){
      if(it.cmID==0){continue;}
      if(ZcRangedModel.ccContains(it.cmID, 1, 31)){//..yellow print hard code
        it.ccSetIsActivated(SubErrorDelegator.ccGetErrorBitAD(it.cmID));
      }//..?
      if(ZcRangedModel.ccContains(it.cmID, 32, 63)){//..yellow print hard code
        //[head]:: this range is not working
        it.ccSetIsActivated(SubErrorDelegator.ccGetWarnBitAD(it.cmID-32));
      }//..?
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
  
  public String getDescriptionAt(int index) {
    if(cmListOfActivated.isEmpty()){
      return "...";
    }else{
      return cmListOfActivated.get(index&C_MASK).ccGetDescription();
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
