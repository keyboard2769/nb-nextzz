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

package nextzz.pppsimulate;

import kosui.ppplogic.ZcImpulsivePulser;
import kosui.ppplogic.ZcTimer;
import kosui.ppputil.VcStringUtility;

public final class ZcCellGateController {
  
  private final int cmGateTail;
  
  private boolean cmWeighOverFlag;
  private final ZcTimer cmWeighConfrimTM = new ZcImpulsivePulser(32);
  private final ZcTimer cmDischargeConfirmTM = new ZcImpulsivePulser(16);
  
  private boolean zxDischargeRequest;
  private int zxCurrentCellAD;
  private int zxTargetAD;
  private int zxWeighLevel;
  
  private boolean zyWeighConfirmFlag,zyDischargeConfirm;
  private final boolean[] zyDesGateAutoFlag = new boolean[8];

  public ZcCellGateController(int pxGateTail) {
    cmGateTail = pxGateTail;
  }//++!
  
  //===
  
  /* 1 */public
  void ccRun(boolean pxDischargeRequest){
    ccSetDischargeRequest(pxDischargeRequest);
    ccRun();
  }//++~
  
  public final void ccRun(){
    
    //-- gate output
    for(int i=cmGateTail;i>=1;i--){
      zyDesGateAutoFlag[i]= (zxWeighLevel==i) && (zxCurrentCellAD<zxTargetAD);
    }//..~
    
    //-- weigh over confirm
    cmWeighOverFlag=
      zxWeighLevel>0
      && (zxCurrentCellAD >= zxTargetAD)
      && !cmWeighConfrimTM.ccIsUp();
    cmWeighConfrimTM.ccAct(cmWeighOverFlag);
    zyWeighConfirmFlag=cmWeighConfrimTM.ccIsUp();
    
    //-- discharge
    cmDischargeConfirmTM.ccAct(
      zxDischargeRequest
      && (zxCurrentCellAD<zxTargetAD)
    );
    zyDischargeConfirm=cmDischargeConfirmTM.ccIsUp();
    zyDesGateAutoFlag[0]=zxDischargeRequest
      && !cmDischargeConfirmTM.ccIsCounting();
    
  }//++~
  
  //===
  
  public final void ccSetDischargeRequest(boolean pxCondition){
    zxDischargeRequest=pxCondition;
  }//++<
  
  public final void ccSetupControlValues(
    int pxCurrentAD, int pxTargetAD, int pxLevel
  ){
    zxCurrentCellAD=pxCurrentAD;
    zxTargetAD=pxTargetAD;
    zxWeighLevel=pxLevel;
  }//++<
  
  //===
  
  public final boolean ccGetWeightConfirmFlag(){
    return zyWeighConfirmFlag;
  }//++>
  
  public final boolean ccGetDischargeConfirmFlag(){
    return zyDischargeConfirm;
  }//++>
  
  public final boolean ccGetGateOpenFlagAt(int pxIndex){
    return zyDesGateAutoFlag[pxIndex&0x07];
  }//++>
  
  //===

  @Override public String toString() {
    StringBuilder lpRes
      = new StringBuilder(ZcCellGateController.class.getSimpleName());
    lpRes.append('@');
    lpRes.append(Integer.toHexString(hashCode()));
    lpRes.append('$');
    lpRes.append(VcStringUtility.ccPackupPairedTag("C",zxCurrentCellAD));
    lpRes.append(VcStringUtility.ccPackupPairedTag("T",zxTargetAD));
    lpRes.append(VcStringUtility.ccPackupPairedTag("LV",zxWeighLevel));
    lpRes.append('|');
    lpRes.append("[w-TM:");
    lpRes.append(Integer.toString(cmWeighConfrimTM.ccGetValue()));
    lpRes.append(cmWeighConfrimTM.ccIsCounting()?"o":"=");
    lpRes.append(cmWeighConfrimTM.ccIsUp()?"o":"=");
    lpRes.append("|[d-TM:");
    lpRes.append(Integer.toString(cmDischargeConfirmTM.ccGetValue()));
    lpRes.append(cmDischargeConfirmTM.ccIsCounting()?"o":"=");
    lpRes.append(cmDischargeConfirmTM.ccIsUp()?"o":"=");
    lpRes.append(']');
    return lpRes.toString();
  }//+++
  
}//***
