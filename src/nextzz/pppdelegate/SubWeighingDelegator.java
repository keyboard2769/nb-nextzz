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

package nextzz.pppdelegate;

import nextzz.ppplocalui.SubMixerGroup;
import nextzz.ppplocalui.SubOperativeGroup;

public final class SubWeighingDelegator {
  
  public static volatile boolean
    mnMixerGateHoldSW,mnMixerGateOpenSW,
    mnMixerGateOpeningPL,mnMixerGateOpenedPL,mnMixerGateClosedPL
  ;//...
  
  public static final void ccWire(){
    /* 6 */throw new RuntimeException("NOT YET!!");
  }//+++
  
  public static final void ccBind(){
    
    //-- mixer gate
    SubOperativeGroup.ccRefer().cmMixerGateHoldSW
      .ccSetIsActivated(mnMixerGateHoldSW);
    SubOperativeGroup.ccRefer().cmMixerGateAutoSW
      .ccSetIsActivated(!mnMixerGateHoldSW && !mnMixerGateOpenSW);
    SubOperativeGroup.ccRefer().cmMixerGateOpenSW
      .ccSetIsActivated(mnMixerGateOpenSW);
    SubMixerGroup.ccRefer().cmMixerGate.ccSetIsOpening(mnMixerGateOpeningPL);
    SubMixerGroup.ccRefer().cmMixerGate.ccSetIsOpened(mnMixerGateOpenedPL);
    SubMixerGroup.ccRefer().cmMixerGate.ccSetIsClosed(mnMixerGateClosedPL);
    
  }//+++
  
}//***eof
