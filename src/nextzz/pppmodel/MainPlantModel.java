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

import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZcTimer;
import kosui.pppmodel.McPipedChannel;
import kosui.ppputil.VcLocalConsole;
import kosui.ppputil.VcLocalTagger;
import nextzz.pppdelegate.SubFeederDelegator;

public final class MainPlantModel {
  
  //-- general
  public static final int C_ASSIST_SW_MASK = 15;
  
  //-- weighing
  public static final int C_MATT_AGGR_UI_VALID_HEAD = 1;
  public static final int C_MATT_AGGR_UI_VALID_MAX  = 7;//.. the BIG
  public static final int C_MATT_REST_UI_VALID_HEAD = 1;
  public static final int C_MATT_REST_UI_VALID_MAX  = 3;//.. the THIG
  
  //-- ct slot
  public static final int C_CTSLOT_CHANNEL_SINGLE = 16;
  public static final int C_CTSLOT_CHANNEL_MAX    = 32;
  public static final int C_CTSLOT_CHANNEL_MASK   = 31;
  
  //-- temperature
  public static final int C_THERMO_VALID_MAX = 16;
  
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
  public static final int C_VF_UI_VALID_HEAD  =    1;
  public static final int C_VF_UI_VALID_MAX   =   10;
  public static final int C_RF_UI_VALID_HEAD  =    1;
  public static final int C_RF_UI_VALID_MAX   =    5;
  
  //===

  private static final MainPlantModel SELF = new MainPlantModel();
  public static final MainPlantModel ccRefer(){return SELF;}//+++
  private MainPlantModel(){}//++!

  //===
  
  public volatile boolean vmMessageBarBlockingFLG = false;
  private final ZcTimer cmMessageBarBlockingTM = new ZcOnDelayTimer(32);
  
  
  public volatile boolean vmErrorClearHoldingFLG = false;
  private final ZcTimer cmErrorClearHoldingTM = new ZcOnDelayTimer(16);
  
  //-- v combust
  public final McPipedChannel cmDesVFeederTPH = new McPipedChannel();
  public volatile int cmVSupplyTPH = 0;
  
  //===
  
  public final void ccInit(){
    
    //-- manager
    SubAnalogScalarManager.ccRefer().ccInit();
    
  }//++!
  
  public final void ccLogic(){
    
    //-- one shot ** message bar blocking
    cmMessageBarBlockingTM.ccAct(vmMessageBarBlockingFLG);
    if(cmMessageBarBlockingTM.ccIsUp()){
      VcLocalConsole.ccClearMessageBarText();
      vmMessageBarBlockingFLG=false;
    }//..?
    
    //-- one shot ** error reset
    cmErrorClearHoldingTM.ccAct(vmErrorClearHoldingFLG);
    if(cmErrorClearHoldingTM.ccIsUp()){vmErrorClearHoldingFLG=false;}
    
    //-- manager
    SubAnalogScalarManager.ccRefer().ccLogic();
    SubWeighControlManager.ccRefer().ccLogic();
    SubDegreeControlManager.ccRefer().ccLogic();
    
    //-- v feeder tph
    cmVSupplyTPH=0;
    for(
      int i=C_VF_UI_VALID_HEAD;
      i<=MainSpecificator.ccRefer().vmVFeederAmount;
      i++
    ){
      if(SubFeederDelegator.ccGetVFeederRunning(i)){
        cmVSupplyTPH+=cmDesVFeederTPH.ccGet(i);
      }//..? 
    }//..~
  
  }//++~
  
  //===
  
  @Deprecated public final void tstTagg(){
    VcLocalTagger.ccTag("-blk-", cmMessageBarBlockingTM.ccGetValue());
    VcLocalTagger.ccTag("-clr-", cmErrorClearHoldingTM.ccGetValue());
  }//+++
  
 }//***eof
