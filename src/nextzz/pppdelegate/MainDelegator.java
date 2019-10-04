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

import nextzz.ppplocalui.SubIndicativeGroup;
import nextzz.pppmain.MainSketch;

/**
 *
 * @author Key Parker from K.I.C
 */
public final class MainDelegator {
  
  private static final MainDelegator SELF = new MainDelegator();
  public static MainDelegator ccGetReference(){return SELF;}//+++
  private MainDelegator(){}//..!

  //===
  
  public static final void ccWire(){
    
    //[NOTYET]::
  
  }//+++
  
  public static final void ccBind(){
    
    //-- general
    SubWeighingDelegator.ccBind();
    SubFeederDelegator.ccBind();
    SubAnalogDelegator.ccBind();
    
    //-- virgin
    SubVProvisionDelegator.ccBind();
    SubVCombustDelegator.ccBind();
    
    //-- recycle
    
    //-- silo
    
    //-- post
    ssBindSystemElements();
    
  }//+++
  
  private static void ssBindSystemElements(){
    SubIndicativeGroup.ccRefer().cmRunningPL
      .ccSetIsActivated(MainSketch.ccIsRollingAbove(7));
    SubIndicativeGroup.ccRefer().cmSystemPopSW.ccSetIsActivated
      (SubIndicativeGroup.ccRefer().cmSystemPopSW.ccIsMouseHovered());
  }//+++
  
 }//***eof
