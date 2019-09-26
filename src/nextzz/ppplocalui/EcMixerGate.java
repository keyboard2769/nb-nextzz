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
  
  private boolean cmMV=false,cmMOL=false,cmMCL=false;
  
  public EcMixerGate(){
    super();
  }//+++ 
  
  @Override public void ccUpdate(){
    
    drawRect(SubMixerGroup.ccRefer().cmPlateColor);
    
    //-- setup
    cmOnColor=EcConst.C_GRAY;
    int lpOffset=cmMCL?0:(cmW/8);
    if(cmMOL&&cmMV){
      cmOnColor=EcConst.C_YELLOW;  
    }else{
      if(cmMOL){cmOnColor=EcConst.C_LIT_ORANGE;}
      if(cmMV){cmOnColor=EcConst.C_RED;}
    }//..?
    
    //-- draw
    pbOwner.fill(cmOnColor);
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
  
  public final void ccSetIsOpening(boolean pxStatus){
    cmMV=pxStatus;
  }//+++
  
  public final void ccSetIsOpened(boolean pxStatus){
    cmMOL=pxStatus;
  }//+++
  
  public final void ccSetIsClosed(boolean pxStatus){
    cmMCL=pxStatus;
  }//+++
  
  public final void ccSetupStatus(boolean pxMV, boolean pxMOL, boolean pxMCL){
    ccSetIsOpening(pxMV);
    ccSetIsOpened(pxMOL);
    ccSetIsClosed(pxMCL);
  }//+++
 
}//***eof
