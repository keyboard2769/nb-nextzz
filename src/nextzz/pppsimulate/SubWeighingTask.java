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

import kosui.ppplogic.ZiTask;
import kosui.ppputil.VcLocalTagger;
import nextzz.pppdelegate.SubWeighingDelegator;

public class SubWeighingTask implements ZiTask{
  
  private static SubWeighingTask self = null;
  public static final SubWeighingTask ccRefer(){
    if(self==null){self = new SubWeighingTask();}
    return self;
  }//+++
  private SubWeighingTask(){}//..!
  
  //===
  
  public final ZcGate dcMixerGate = new ZcGate(64);

  @Override public void ccScan() {
    
    dcMixerGate.ccSetupAction(
      ConstFBHolder.ccSelectForceOpen(
        SubWeighingDelegator.mnMixerGateHoldSW,
        SubWeighingDelegator.mnMixerGateOpenSW,
        false
      )
    );
    SubWeighingDelegator.mnMixerGateOpeningPL=dcMixerGate.ccGetIsOpening();
    SubWeighingDelegator.mnMixerGateOpenedPL=dcMixerGate.ccGetIsFullOpened();
    SubWeighingDelegator.mnMixerGateClosedPL=dcMixerGate.ccGetIsClosed();
    
  }//+++

  @Override public void ccSimulate() {
    
    dcMixerGate.ccSimulate
      (SubVProvisionTask.ccRefer().dcVCompressor.ccIsContacted());
    
  }//+++
  
  //=== 
  
  @Deprecated public final void tstTagg(){
    VcLocalTagger.ccTag("mg?", dcMixerGate.ccGetValue());
    VcLocalTagger.ccTag("mg-h-sw?", SubWeighingDelegator.mnMixerGateHoldSW);
    VcLocalTagger.ccTag("mg-o-sw?", SubWeighingDelegator.mnMixerGateOpenSW);
    //[todo]::
    VcLocalTagger.ccTag("mg-o?", dcMixerGate.ccGetIsOpening());
  }//+++
  
}//***eof
