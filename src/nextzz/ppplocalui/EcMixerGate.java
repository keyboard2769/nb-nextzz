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

package nextzz.ppplocalui;

import kosui.ppplocalui.EcConst;
import kosui.ppplocalui.EcElement;
import processing.core.PConstants;

public class EcMixerGate extends EcElement{
  
  private boolean cmIsClosed=false;
  
  public EcMixerGate(){
    super();
    ccSetColor(EcConst.C_YELLOW);
  }//+++ 
  
  @Override public void ccUpdate(){
    
    //-- pre
    drawRect(SubMixerGroup.ccRefer().cmPlateColor);
    
    //-- draw
    ccActFill();
    int lpOffset=cmIsClosed?0:(cmW/8);
    pbOwner.arc(
      ccCenterX()-lpOffset, ccCenterY(), cmW*3/4, cmH,
      PConstants.PI/2+0.1f, PConstants.PI*3/2-0.1f,
      PConstants.PIE
    );
    pbOwner.arc(
      ccCenterX()+lpOffset, ccCenterY(), cmW*3/4, cmH,
      -PConstants.PI/2+0.1f, PConstants.PI/2-0.1f,
      PConstants.PIE
    );
    
  }//+++
  
  public final void ccSetIsClosed(boolean pxStatus){
    cmIsClosed=pxStatus;
  }//+++
 
}//***eof
