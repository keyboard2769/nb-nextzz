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
  
  //-- ct slot
  public static final int C_CTSLOT_CHANNEL_SINGLE = 16;
  public static final int C_CTSLOT_CHANNEL_MAX    = 32;
  public static final int C_CTSLOT_CHANNEL_MASK   = 31;
  
  //-- temperature
  public static final int C_THERMO_CHANNEL_HEAD = 1;
  public static final int C_THERMO_CHANNEL_TAIL = 8;
  
  //-- combust
  public static final float C_PRESSURE_CONTOL_OFFSET = 500f;
  
  //-- feeder
  //   .. for any UI part grouping vergin or recycle feeder abstractly
  //   ..   their max count should get left arbitrary
  //   ..   as they should feel comfortable when implementing their own stuff.
  //   .. as for delegator, witch is THE traffic line interface
  //   ..   between pc and plc, the valid count hard coded as shown below.
  public static final int C_FEEDER_RPM_MIN =    0;
  public static final int C_FEEDER_RPM_MAX = 1800;
  public static final int C_VF_INIT_ORDER  =    1;
  public static final int C_VF_VALID_MAX   =   10;
  public static final int C_RF_INIT_ORDER  =    1;
  public static final int C_RF_VALID_MAX   =    5;
  
  //===

  private static final MainPlantModel SELF = new MainPlantModel();
  public static final MainPlantModel ccRefer(){return SELF;}//+++
  private MainPlantModel(){}//++!

  //===
  
  //-- v combust
  public final McPipedChannel cmDesVFeederTPH = new McPipedChannel();
  public volatile int cmVSupplyTPH = 0;
  
  //===
  
  public final void ccInit(){
    
    //-- manager
    SubAnalogScalarManager.ccRefer().ccInit();
    
  }//++!
  
  public final void ccLogic(){
    
    //-- manager
    SubDegreeControlManager.ccRefer().ccLogic();
    SubAnalogScalarManager.ccRefer().ccLogic();
    
    //-- v feeder tph
    cmVSupplyTPH=0;
    for(
      int i=C_VF_INIT_ORDER;
      i<=MainSpecificator.ccRefer().mnVFeederAmount;
      i++
    ){
      if(SubFeederDelegator.ccGetVFeederRunning(i)){
        cmVSupplyTPH+=cmDesVFeederTPH.ccGet(i);
      }//..? 
    }//..~
  
  }//++~
  
  //===
  
 }//***eof
