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

import javax.swing.SwingUtilities;
import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZcRangedValueModel;
import kosui.ppplogic.ZcTimer;
import kosui.pppmodel.McPipedChannel;
import kosui.ppputil.VcLocalConsole;
import kosui.ppputil.VcLocalTagger;
import nextzz.pppdelegate.SubFeederDelegator;
import nextzz.pppdelegate.SubVCombustDelegator;
import nextzz.ppplocalui.SubOperativeGroup;
import nextzz.pppswingui.SubMonitorPane;

public final class MainPlantModel {
  
  //-- general
  public static final int C_ASSIST_SW_SIZE  = 16;
  public static final int C_ASSIST_SW_MASK  = 15;
  
  //-- book
  public static final int C_BOOK_MODEL_SIZE     =  8;
  public static final int C_BOOK_MODEL_MASK     =  7;
  public static final int C_BOOK_UI_CAPA_HEAD  =  1;//..need manually fit
  public static final int C_BOOK_UI_CAPA_TAIL  =  4;//..need manually fit
  public static final int C_BOOK_UI_ROW_SIZE   =  5;//..need manually fit
  
  //-- weighing
  public static final int C_MATT_AGGR_GENERAL_SIZE  = 8;
  public static final int C_MATT_AGGR_GENERAL_MASK  = 7;
  public static final int C_MATT_AGGR_UI_VALID_HEAD = 1;
  public static final int C_MATT_AGGR_UI_VALID_MAX  = 7;//.. the BIG
  public static final int C_MATT_REST_GENERAL_SIZE  = 4;
  public static final int C_MATT_REST_GENERAL_MASK  = 3;
  public static final int C_MATT_REST_UI_VALID_HEAD = 1;
  public static final int C_MATT_REST_UI_VALID_MAX  = 3;//.. the THIG
  
  //-- ct slot
  public static final int C_CTSLOT_CHANNEL_SINGLE = 16;
  public static final int C_CTSLOT_CHANNEL_MAX    = 32;
  public static final int C_CTSLOT_CHANNEL_MASK   = 31;
  
  //-- temperature
  public static final int C_THERMO_VALID_MAX = 16;
  
  //-- combust
  public static final float C_PRESSURE_CONTOL_OFFSET = 400f;
  
  //-- feeder
  //   .. for any UI part grouping vergin or recycle feeder abstractly
  //   ..   their max count should get left arbitrary
  //   ..   as they should feel comfortable when implementing their own stuff.
  //   .. as for delegator, witch is THE traffic line interface
  //   ..   between pc and plc, the valid count hard coded as shown below.
  public static final int C_FEEDER_RPM_MIN   =    0;
  public static final int C_FEEDER_RPM_MAX   = 1800;
  public static final int C_VF_UI_VALID_HEAD =    1;
  public static final int C_VF_UI_VALID_MAX  =   10;
  public static final int C_RF_UI_VALID_HEAD =    1;
  public static final int C_RF_UI_VALID_MAX  =    5;
  
  //===

  private static final MainPlantModel SELF = new MainPlantModel();
  public static final MainPlantModel ccRefer(){return SELF;}//+++
  private MainPlantModel(){}//++!

  //===
  
  //-- error message
  public volatile boolean cmMessageBarBlockingFLG = false;
  private final ZcTimer cmMessageBarBlockingTM = new ZcOnDelayTimer(32);
  
  public volatile boolean cmErrorClearHoldingFLG = false;
  private final ZcTimer cmErrorClearHoldingTM = new ZcOnDelayTimer(16);
  
  //-- v supply
  public final McPipedChannel cmDesVFeederTPH = new McPipedChannel();
  public volatile float vmVRatioBaseTPH = 320;
  public volatile int vmVSupplyTPH = 0;
  
  //-- v combust
  public final ZcRangedValueModel cmVCombustLogINTV
    = new ZcRangedValueModel(0, 16*15);
  
  //-- zero
  public volatile boolean vmAGZeroAdjustSelector =false;
  public volatile boolean vmFRZeroAdjustSelector =false;
  public volatile boolean vmASZeroAdjustSelector =false;
  
  //-- ct slot alarm
  public final McPipedChannel cmDesCTSlotAlarmPartIxAMPR
    = new McPipedChannel();
  public final McPipedChannel cmDesCTSlotAlarmPartIIxAMPR
    = new McPipedChannel();
  
  //===
  
  public final void ccInit(){
    
    //-- manager
    SubAnalogScalarManager.ccRefer().ccInit();
    SubRecipeManager.ccRefer().ccInit();
    SubDegreeControlManager.ccRefer().ccInit();
    SubWeighControlManager.ccRefer().ccInit();
    
    //-- value
    
  }//++!
  
  public final void ccLogic(){
    
    //-- one shot ** message bar blocking
    cmMessageBarBlockingTM.ccAct(cmMessageBarBlockingFLG);
    if(cmMessageBarBlockingTM.ccIsUp()){
      VcLocalConsole.ccClearMessageBarText();
      cmMessageBarBlockingFLG=false;
    }//..?
    
    //-- one shot ** error reset
    cmErrorClearHoldingTM.ccAct(cmErrorClearHoldingFLG);
    if(cmErrorClearHoldingTM.ccIsUp()){cmErrorClearHoldingFLG=false;}
    
    //-- manager
    SubAnalogScalarManager.ccRefer().ccLogic();
    SubWeighControlManager.ccRefer().ccLogic();
    SubDegreeControlManager.ccRefer().ccLogic();
    SubErrorListModel.ccRefer().ccLogic();
    
    //-- v feeder tph
    vmVSupplyTPH=0;
    for(
      int i=C_VF_UI_VALID_HEAD;
      i<=MainSpecificator.ccRefer().vmVFeederAmount;
      i++
    ){
      if(SubFeederDelegator.ccGetVFeederRunning(i)){
        vmVSupplyTPH+=cmDesVFeederTPH.ccGet(i);
      }//..? 
    }//..~
    
    //-- v comboset logging
    if(SubVCombustDelegator.mnVBFlamingPL){
      cmVCombustLogINTV.ccRoll(1);
      if(cmVCombustLogINTV.ccIsAt(cmVCombustLogINTV.ccGetMax()-1)){
        SubVCombustStaticManager.ccRefer().ccLogRecord(
          SubAnalogScalarManager.ccRefer().ccGetVExfanPercentage(),
          SubAnalogScalarManager.ccRefer().ccGetVBurnerPercentage(),
          SubAnalogScalarManager.ccRefer().cmDesThermoCelcius
            .ccGet(SubAnalogScalarManager.C_I_THI_CHUTE),
          SubAnalogScalarManager.ccRefer().cmDesThermoCelcius
            .ccGet(SubAnalogScalarManager.C_I_THII_ENTRANCE),
          SubAnalogScalarManager.ccRefer().cmDesThermoCelcius
            .ccGet(SubAnalogScalarManager.C_I_THIII_PIPE),
          SubAnalogScalarManager.ccRefer().cmDesThermoCelcius
            .ccGet(SubAnalogScalarManager.C_I_THIV_SAND)
        );
        SwingUtilities.invokeLater(SubMonitorPane
          .ccRefer().cmVCombustResultTableRefreshingness);
      }//..?
    }//..?
  
  }//++~
  
  public final void ccBind(){
    
    //-- misc
    SubOperativeGroup.ccRefer().cmAGZeroSW
      .ccSetIsActivated(vmAGZeroAdjustSelector);
    SubOperativeGroup.ccRefer().cmFRZeroSW
      .ccSetIsActivated(vmFRZeroAdjustSelector);
    SubOperativeGroup.ccRefer().cmASZeroSW
      .ccSetIsActivated(vmASZeroAdjustSelector);
    
    //-- manager
    SubWeighControlManager.ccRefer().ccBind();
  
  }//..~
  
  //===
  
  @Deprecated public final void tstTagg(){
    VcLocalTagger.ccTag("-blk-", cmMessageBarBlockingTM.ccGetValue());
    VcLocalTagger.ccTag("-clr-", cmErrorClearHoldingTM.ccGetValue());
  }//+++
  
 }//***eof
