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

import java.util.Arrays;
import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZcPLC;
import kosui.ppplogic.ZcPulser;
import kosui.ppplogic.ZcRoller;
import kosui.ppplogic.ZcTimer;
import kosui.ppplogic.ZiTask;
import kosui.ppputil.VcLocalTagger;
import nextzz.pppdelegate.SubErrorDelegator;

public final class SubErrorTask implements ZiTask{
  
  private static SubErrorTask self = null;
  public static final SubErrorTask ccRefer() {
    if(self==null){self=new SubErrorTask();return self;}
    else{return self;}
  }//+++
  private SubErrorTask(){}//++!
  
  //===
  
  //## .. (message (fatal (error ...)(warn ...)) ...)
  private static final int C_FATAL_HEAD  =   1;
  private static final int C_ERROR_TAIL  =  31;
  private static final int C_FATAL_TAIL  =  63;
  private static final int C_MSG_MASK    = 255;
  private static final int C_MSG_SIZE    = 256;
  
  private final ZcPulser cmClearPLS = new ZcPulser();
  private final ZcRoller cmMessageTester = new ZcRoller(C_MSG_MASK);
  private final boolean[] cmDesMessageBit = new boolean[C_MSG_SIZE];
  private final ZcTimer cmSuppressTM = new ZcOnDelayTimer(20);
  
  public final void ccKeepMessageBit(int pxIndex, boolean pxCondition){
    if(!pxCondition){return;}
    cmDesMessageBit[pxIndex&C_MSG_MASK]=true;
  }//++<
  
  public final void ccSetMessageBit(int pxIndex, boolean pxVal){
    cmDesMessageBit[pxIndex&C_MSG_MASK]=pxVal;
  }//++<
  
  public final boolean ccGetMessageBit(int pxIndex){
    return cmDesMessageBit[pxIndex&C_MSG_MASK];
  }//++>

  @Override public void ccScan() {
    
    //-- specify
    if(cmClearPLS.ccUpPulse(SubErrorDelegator.mnErrorClearSW)){
      Arrays.fill(cmDesMessageBit, false);
      cmMessageTester.ccReset();
    }//..?
    if(!SubErrorDelegator.mnErrorClearSW){
      
      //## ..bit specification code here
      //## ..equalvalent to [ld al28;out k302;]
      
      //-- power?
      ccKeepMessageBit(1, SubVProvisionTask.ccRefer()
        .dcVBCompressor.ccIsTripped());
      ccKeepMessageBit(2, SubVProvisionTask.ccRefer()
        .dcMixer.ccIsTripped());
      
      ccKeepMessageBit(3, SubVProvisionTask.ccRefer()
        .dcVExFan.ccIsTripped());
      ccKeepMessageBit(66, SubVProvisionTask.ccRefer()
        .dcVExFan.ccIsTripped());
      
      //-- control?
      //-- temperature?
      
    }//..?
    
    //-- fatal 
    cmDesMessageBit[0]=true;
    for(int i=C_FATAL_HEAD;i<C_FATAL_TAIL;i++){
      
      //-- sync
      if(i<=C_ERROR_TAIL){
        SubErrorDelegator.ccSetErrorBitAD(i, ccGetMessageBit(i));
      }else{
        SubErrorDelegator.ccSetWarnBitAD(i, ccGetMessageBit(i));
      }//..?
      
      //-- gather
      cmDesMessageBit[0]&=!cmDesMessageBit[i];
      
    }//..~
    
    //-- feedback
    SubErrorDelegator.mnErrorPL=
        (!cmDesMessageBit[0])
      &&(!MainSimulator.ccHalfSecondPLS());
    if(SubErrorDelegator.mnErrorClearSW){
      SubErrorDelegator.mnMessageCode = 1001;
    }else{
      SubErrorDelegator.mnMessageCode=ZcPLC.sel(
        ZcPLC.and(
          ccGetMessageBit(cmMessageTester.ccGetValue()),
          !cmSuppressTM.ccIsUp()
        ),
        cmMessageTester.ccGetValue(), -1
      );
    }//..?
    
  }//+++

  @Override public void ccSimulate() {
    
    //-- rolling
    boolean lpCurrent=ccGetMessageBit(cmMessageTester.ccGetValue());
    cmSuppressTM.ccAct(lpCurrent);
    if(cmSuppressTM.ccIsUp()||!lpCurrent){
      cmMessageTester.ccRoll();
      cmSuppressTM.ccSetValue(0);
    }//..?
    
  }//+++
  
  //===
  
  @Deprecated public final void tstTagg(){
    VcLocalTagger.ccTag("-tm-",cmSuppressTM.ccGetValue());
    VcLocalTagger.ccTag("sw",SubErrorDelegator.mnErrorClearSW);
    VcLocalTagger.ccTag("head",cmMessageTester);
    VcLocalTagger.ccTag("wm39",SubErrorDelegator.mnMessageCode);
  }//+++
  
 }//***eof
