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

import kosui.pppmodel.McPipedChannel;
import nextzz.pppdelegate.SubFeederDelegator;

public final class MainPlantModel {

  private static final MainPlantModel SELF = new MainPlantModel();
  public static final MainPlantModel ccRefer(){return SELF;}//+++
  private MainPlantModel(){}//++!

  //===
  
  //-- v combust
  public final McPipedChannel cmDesVFeederTPH = new McPipedChannel();
  public volatile int cmVSupplyTPH = 0;
  public volatile int cmVTargetTemperature = 160;
  public volatile int cmVTargetTempAdjustWidth = 5;
  
  //===
  
  public final void ccInit(){
    
  }//..!
  
  public final void ccLogic(){
    
    cmVSupplyTPH=0;
    for(
      int i=SubFeederDelegator.C_VF_INIT_ORDER;
      i<=MainSpecificator.ccRefer().mnVFeederAmount;
      i++
    ){
      if(SubFeederDelegator.ccGetVFeederRunning(i)){
        cmVSupplyTPH+=cmDesVFeederTPH.ccGet(i);
      }//..? 
    }//..~
    
    //cmDesVFeederTPH.ccSum();
  
  }//+++
  
  //===
  
 }//***eof
