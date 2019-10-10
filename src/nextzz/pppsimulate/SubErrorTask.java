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
import kosui.ppplogic.ZcOffDelayTimer;
import kosui.ppplogic.ZcOnDelayTimer;
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
  
  private static final int C_FATAL_HEAD  = 1;
  private static final int C_FATAL_TAIL  = 63;
  private static final int C_MSG_MAX     = 127;
  private static final int C_MSG_MASK    = 128;
  
  private final ZcPulser cmClearPLS = new ZcPulser();
  private final ZcRoller cmMessageTester = new ZcRoller(C_MSG_MAX);
  private final boolean[] cmDesMessageBit = new boolean[C_MSG_MASK];
  private final ZcTimer cmMessageSuppressTM = new ZcOnDelayTimer(20);
  
  public final void ccKeepErrorBit(int pxIndex, boolean pxCondition){
    if(!pxCondition){return;}
    cmDesMessageBit[pxIndex&127]=true;
  }//+++
  
  public final void ccSetErrorBit(int pxIndex, boolean pxVal){
    cmDesMessageBit[pxIndex&127]=pxVal;
  }//++<
  
  public final boolean ccGetErrorBit(int pxIndex){
    return cmDesMessageBit[pxIndex&127];
  }//++>

  @Override public void ccScan() {
    
    //-- specify
    if(cmClearPLS.ccUpPulse(SubErrorDelegator.mnErrorClearSW)){
      Arrays.fill(cmDesMessageBit, false);
      cmMessageTester.ccReset();
    }//..?
    if(!SubErrorDelegator.mnErrorClearSW){
      ccKeepErrorBit(1, SubVProvisionTask.ccRefer()
        .dcVBCompressor.ccIsTripped());
      ccKeepErrorBit(2, SubVProvisionTask.ccRefer()
        .dcMixer.ccIsTripped());
      ccKeepErrorBit(3, SubVProvisionTask.ccRefer()
        .dcVExFan.ccIsTripped());
    }//..?
    
    //-- fatal
    cmDesMessageBit[0]=true;
    for(int i=C_FATAL_HEAD;i<C_FATAL_TAIL;i++){
      cmDesMessageBit[0]&=!cmDesMessageBit[i];
    }//..~
    
    //-- feedback
    SubErrorDelegator.mnErrorPL=!cmDesMessageBit[0]
      && !MainSimulator.ccHalfSecondPLS();
    if(SubErrorDelegator.mnErrorClearSW){
      SubErrorDelegator.mnMessageCode = 1001;
    }else{
      SubErrorDelegator.mnMessageCode
        = (
           ccGetErrorBit(cmMessageTester.ccGetValue())
             && !cmMessageSuppressTM.ccIsUp()
          ) ? cmMessageTester.ccGetValue()
            : -1;
    }//..?
    
  }//+++

  @Override public void ccSimulate() {
    
    //-- rolling
    boolean lpCurrent=ccGetErrorBit(cmMessageTester.ccGetValue());
    cmMessageSuppressTM.ccAct(lpCurrent);
    if(cmMessageSuppressTM.ccIsUp()||!lpCurrent){
      cmMessageTester.ccRoll();
      cmMessageSuppressTM.ccSetValue(0);
    }//..?
    
  }//+++
  
  //===
  
  @Deprecated public final void tstTagg(){
    VcLocalTagger.ccTag("-tm-",cmMessageSuppressTM.ccGetValue());
    VcLocalTagger.ccTag("sw",SubErrorDelegator.mnErrorClearSW);
    VcLocalTagger.ccTag("head",cmMessageTester.ccGetValue());
    VcLocalTagger.ccTag("wm39",SubErrorDelegator.mnMessageCode);
  }//+++
  
 }//***eof
