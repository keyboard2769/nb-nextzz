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

import java.util.Arrays;
import java.util.HashMap;
import javax.swing.SwingUtilities;
import kosui.ppplocalui.EcComponent;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplocalui.EiTriggerable;
import kosui.ppplogic.ZcImpulsivePulser;
import kosui.ppplogic.ZcImpulsiveTimer;
import kosui.ppplogic.ZcKeepRelay;
import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZcPLC;
import kosui.ppplogic.ZcPulser;
import kosui.ppplogic.ZcRangedModel;
import kosui.ppplogic.ZcRangedValueModel;
import kosui.ppplogic.ZcTimer;
import kosui.pppmodel.MiValue;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcLocalTagger;
import kosui.ppputil.VcNumericUtility;
import nextzz.pppdelegate.SubVProvisionDelegator;
import nextzz.pppdelegate.SubWeighingDelegator;
import nextzz.ppplocalui.SubMixerGroup;
import nextzz.ppplocalui.SubOperativeGroup;
import nextzz.ppplocalui.SubWeigherGroup;
import nextzz.pppmain.MainSketch;
import nextzz.pppsimulate.ZcWeighController;
import nextzz.pppswingui.ConstSwingUI;
import nextzz.pppswingui.SubMonitorPane;

public final class SubWeighControlManager {

  private static final SubWeighControlManager SELF
    = new SubWeighControlManager();
  public static final SubWeighControlManager ccRefer(){return SELF;}//+++
  private SubWeighControlManager(){}//++!

  //===
  
  public volatile int vmRemainBatch=0;
  public volatile int vmDrySetFrame=100;
  public volatile int vmWetSetFrame=200;
  public volatile int vmDryRemainSecond=0;
  public volatile int vmWetRemainSecond=0;
  public volatile int vmAGEmptyKG = 5;
  public volatile int vmFREmptyKG = 50;
  public volatile int vmASEmptyKG = 50;
  //[todo]:: vmRCEmptyKG = ...
  //[todo]:: vmADEmptyKG = ...
  
  public final ZcWeighController
    cmAGWeighCTRL = new ZcWeighController
      (MainPlantModel.C_MATT_AGGR_UI_VALID_MAX),
    cmFRWeighCTRL = new ZcWeighController
      (MainPlantModel.C_MATT_REST_UI_VALID_MAX),
    cmASWeighCTRL = new ZcWeighController
      (MainPlantModel.C_MATT_REST_UI_VALID_MAX)
  ;//,,,
  
  private final ZcImpulsiveTimer cmAutoWeighStartPLS = new ZcImpulsiveTimer(7);
  private final ZcTimer
    cmAllWeighOverConfrimTM = new ZcOnDelayTimer(16),
    cmAGDischargeDelayTM    = new ZcImpulsivePulser(7),
    cmFRDischargeDelayTM    = new ZcImpulsivePulser(11);
  
  private final ZcRangedValueModel
    cmDryTimeManipulator = new ZcRangedValueModel(0, 65535),
    cmWetTimeManipulator = new ZcRangedValueModel(0, 65535);
  
  private final ZcKeepRelay
    cmAGDischargedFlag = new ZcKeepRelay(),
    cmFRDischargedFlag = new ZcKeepRelay(),
    cmASDischargedFlag = new ZcKeepRelay(),
    cmAllDischargeOverFlag = new ZcKeepRelay(),
    cmMixingTimeUpFlag = new ZcKeepRelay();
  
  private final ZcPulser
    cmAllWeighOverPLS = new ZcPulser(),
    cmAllDischargeOverPLS = new ZcPulser(),
    cmMixerDischargeStartPLS = new ZcPulser();
  
  private boolean cmIsAutoWeighReady,cmIsAutoWeighing;
  private boolean cmIsDryCountingDown,cmIsWetCountingDown;
  private boolean cmMixerReadyFlag=true;
  
  private final int[] 
    cmDesRecipeNumber = new int[MainPlantModel.C_BOOK_MODEL_SIZE],
    cmDesKiloGramme   = new int[MainPlantModel.C_BOOK_MODEL_SIZE],
    cmDesBatchCount   = new int[MainPlantModel.C_BOOK_MODEL_SIZE];
  
  private final int[]
    cmDesAGWeighLevelTargetKG
     = new int [MainPlantModel.C_MATT_AGGR_GENERAL_SIZE],
    cmDesFRWeighLevelTargetKG
     = new int [MainPlantModel.C_MATT_REST_GENERAL_SIZE],
    cmDesASWeighLevelTargetKG
     = new int [MainPlantModel.C_MATT_REST_GENERAL_SIZE];
    //[todo]::cmDesRCCurrentMattTargetKG = ...
    //[todo]::cmDesADCurrentMattTargetKG = ...
  
  private final int[]
    cmDesAGWeighLevelTargetAD
     = new int [MainPlantModel.C_MATT_AGGR_GENERAL_SIZE],
    cmDesFRWeighLevelTargetAD
     = new int [MainPlantModel.C_MATT_REST_GENERAL_SIZE],
    cmDesASWeighLevelTargetAD
     = new int [MainPlantModel.C_MATT_REST_GENERAL_SIZE];
    //[todo]::cmDesRCCurrentMattTargetAD = ...
    //[todo]::cmDesADCurrentMattTargetAD = ...
  
  public final EiTriggerable cmWeighStartClicking = new EiTriggerable() {
    @Override public void ccTrigger(){
      if(cmIsAutoWeighReady){
        ssBunkUp();
        ssVerifyFirstRow();
        ssRefreshUI();
        ssCalculateWeighValues();
        cmIsAutoWeighing=true;
        cmAutoWeighStartPLS.ccImpulse();
      }else{
        SwingUtilities.invokeLater(ConstSwingUI.O_WEIGH_UNREADY_WARNINGNESS);
      }//..?
    }//+++
  };//***
  
  public final EiTriggerable cmWeighCacncelClicking = new EiTriggerable() {
    @Override public void ccTrigger(){
      cmIsAutoWeighing=false;
      ssClearStatusFlag();
      ssVerifyFirstRow();
    }//+++
  };//***
  
  public final EiTriggerable cmCurrentlyBookedCleaning = new EiTriggerable() {
    @Override public void ccTrigger() {
      if(cmIsAutoWeighing){return;}
      ssClearCurrentBooked();
      vmRemainBatch=0;
    }//+++
  };//***
  
  private final HashMap<EcValueBox,MiValue> cmMapOfModelManiputator
    = new HashMap<EcValueBox, MiValue>();
  
  private class McRecipeValue implements MiValue{
    private final int cmIndex;
    public McRecipeValue(int pxIndex){
      cmIndex = pxIndex & MainPlantModel.C_BOOK_MODEL_MASK;
    }//++!
    @Override public void ccSetValue(Object pxVal){
      cmDesRecipeNumber[cmIndex] = ZcRangedModel.ccLimitInclude(
        VcNumericUtility.ccInteger(pxVal), 0, 999
      );
    }//++<
    @Override public Object ccGetValue(){
      return Integer.valueOf(cmDesRecipeNumber[cmIndex]);
    }//++>
  }//***
  
  private class McKilogramValue implements MiValue{
    private final int cmIndex;
    public McKilogramValue(int pxIndex){
      cmIndex = pxIndex & MainPlantModel.C_BOOK_MODEL_MASK;
    }//++!
    @Override public void ccSetValue(Object pxVal){
      cmDesKiloGramme[cmIndex] = ZcRangedModel.ccLimitInclude(
        VcNumericUtility.ccInteger(pxVal), 0, 7999
      );
    }//++<
    @Override public Object ccGetValue(){
      return Integer.valueOf(cmDesKiloGramme[cmIndex]);
    }//++>
  }//***
  
  private class McBatchValue implements MiValue{
    private final int cmIndex;
    public McBatchValue(int pxIndex){
      cmIndex = pxIndex & MainPlantModel.C_BOOK_MODEL_MASK;
    }//++!
    @Override public void ccSetValue(Object pxVal){
      cmDesBatchCount[cmIndex] = ZcRangedModel.ccLimitInclude(
        VcNumericUtility.ccInteger(pxVal), 0, 9999
      );
    }//++<
    @Override public Object ccGetValue(){
      return Integer.valueOf(cmDesBatchCount[cmIndex]);
    }//++>
  }//***
  
  //===
  
  public final void ccInit(){
    
    //-- init manipulator map
    for(int i =MainPlantModel.C_BOOK_UI_CAPA_HEAD;
            i<=MainPlantModel.C_BOOK_UI_CAPA_TAIL;i++){
      
      //-- init map ** recipe
      cmMapOfModelManiputator.put(
        SubOperativeGroup.ccRefer().cmLesRecipeTB.get(i),
        new McRecipeValue(i)
      );
      
      //-- init map ** kg
      cmMapOfModelManiputator.put(
        SubOperativeGroup.ccRefer().cmLesKilogramTB.get(i),
        new McKilogramValue(i)
      );
      
      //-- init map ** batch
      cmMapOfModelManiputator.put(
        SubOperativeGroup.ccRefer().cmLesBatchTB.get(i),
        new McBatchValue(i)
      );
      
    }//..~
    
    //-- ???
    //[todo]::delete after dev
    /* 6 */tstRallyUpTestBook();
    /* 6 */ssRefreshUI();
    /* 6 */ssVerifyFirstRow();
    
  }//++!
  
  public final void ccLogic(){
    
    //-- 
    boolean lpAGSkip = //[dev]::EcComponent.ccIsKeyPressed('i') ||
      SubOperativeGroup.ccRefer().cmAGSkipSW.ccIsMousePressed();
    boolean lpFRSkip = //[dev]::EcComponent.ccIsKeyPressed('u') ||
      SubOperativeGroup.ccRefer().cmFRSkipSW.ccIsMousePressed();
    boolean lpASSkip = //[dev]::EcComponent.ccIsKeyPressed('o') ||
      SubOperativeGroup.ccRefer().cmASSkipSW.ccIsMousePressed();
    boolean lpAllWeighOverFlag = //[dev]::EcComponent.ccIsKeyPressed('k') &&
      cmAllWeighOverConfrimTM.ccIsUp() && cmMixerReadyFlag;
    
    //--
    cmAllWeighOverConfrimTM.ccAct(
         cmAGWeighCTRL.ccIsWaitingToDischarge()
      && cmFRWeighCTRL.ccIsWaitingToDischarge()
      && cmASWeighCTRL.ccIsWaitingToDischarge()
    );
    SubWeighingDelegator.mnAutoWeighing=cmIsAutoWeighing;
    
    //-- AG controller 
    cmAGWeighCTRL.ccSetHasNext(vmRemainBatch>1);
    cmAGWeighCTRL
      .ccSetToNext(lpAGSkip || SubWeighingDelegator.mnAGWeighConfirm);
    cmAGWeighCTRL.ccSetDischargeConfirm(cmAllDischargeOverFlag.ccGetBit());
    cmAGWeighCTRL.ccRun(cmAutoWeighStartPLS.ccIsUp(),!cmIsAutoWeighing);
    SubWeighingDelegator.mnAGWeighLevel=cmAGWeighCTRL.ccGetCurrentLevel();
    SubWeighingDelegator.mnAGWeighLevelTargetAD=cmDesAGWeighLevelTargetAD
      [cmAGWeighCTRL.ccGetCurrentLevel()
        &MainPlantModel.C_MATT_AGGR_GENERAL_MASK];
    SubWeighingDelegator.mnAGDischargeRequest
      = cmAGWeighCTRL.ccIsRequiringDischarge();
    /* 6 */SubWeighingDelegator.mnAGWeighLevelLeadAD
      = SubWeighingDelegator.mnAGWeighLevelTargetAD;//[todo]::..
    
    //-- FR controller 
    cmFRWeighCTRL.ccSetHasNext(vmRemainBatch>1);
    cmFRWeighCTRL
      .ccSetToNext(lpFRSkip || SubWeighingDelegator.mnFRWeighConfirm);
    cmFRWeighCTRL.ccSetDischargeConfirm(cmAllDischargeOverFlag.ccGetBit());
    cmFRWeighCTRL.ccRun(cmAutoWeighStartPLS.ccIsUp(),!cmIsAutoWeighing);
    SubWeighingDelegator.mnFRWeighLevel=cmFRWeighCTRL.ccGetCurrentLevel();
    SubWeighingDelegator.mnFRWeighLevelTargetAD=cmDesFRWeighLevelTargetAD
      [cmFRWeighCTRL.ccGetCurrentLevel()
        & MainPlantModel.C_MATT_REST_GENERAL_MASK];
    SubWeighingDelegator.mnFRDischargeRequest
      = cmFRWeighCTRL.ccIsRequiringDischarge();
    /* 6 */SubWeighingDelegator.mnFRWeighLevelLeadAD
      = SubWeighingDelegator.mnFRWeighLevelTargetAD;//[todo]::..
    
    //-- AS controller 
    cmASWeighCTRL.ccSetHasNext(vmRemainBatch>1);
    cmASWeighCTRL
      .ccSetToNext(lpASSkip || SubWeighingDelegator.mnASWeighConfirm);
    cmASWeighCTRL.ccSetDischargeConfirm(cmAllDischargeOverFlag.ccGetBit());
    cmASWeighCTRL.ccRun(cmAutoWeighStartPLS.ccIsUp(),!cmIsAutoWeighing);
    SubWeighingDelegator.mnASWeighLevel=cmASWeighCTRL.ccGetCurrentLevel();
    SubWeighingDelegator.mnASWeighLevelTargetAD=cmDesASWeighLevelTargetAD
      [cmASWeighCTRL.ccGetCurrentLevel()
        & MainPlantModel.C_MATT_REST_GENERAL_MASK];
    SubWeighingDelegator.mnASDischargeRequest
      = cmASWeighCTRL.ccIsRequiringDischarge();
    /* 6 */SubWeighingDelegator.mnASWeighLevelLeadAD
      = SubWeighingDelegator.mnASWeighLevelTargetAD;//[todo]::..
    
    //-- raw delay
    cmAGDischargeDelayTM.ccAct(lpAllWeighOverFlag);
    cmFRDischargeDelayTM.ccAct(lpAllWeighOverFlag);
    
    //-- dry-wet
    if(cmAllWeighOverPLS.ccUpPulse(lpAllWeighOverFlag)){
      SubWeighDynamicManager.ccRefer().ccPopResult();
    }//..?
    if(lpAllWeighOverFlag){
      SubWeighingDelegator.mnMixerDischargedConfirm=false;
      cmIsDryCountingDown=true;
      cmMixerReadyFlag=false;
    }//..?
    if(cmIsDryCountingDown){cmDryTimeManipulator.ccShift(-1);}
    if(cmDryTimeManipulator.ccIsAt(0)){cmIsWetCountingDown=true;}
    if(cmIsWetCountingDown){cmWetTimeManipulator.ccShift(-1);}
    if(cmWetTimeManipulator.ccIsAt(0)){
      cmIsWetCountingDown=false;
      cmIsDryCountingDown=false;
      cmDryTimeManipulator.ccSetValue(vmDrySetFrame);
      cmWetTimeManipulator.ccSetValue(vmWetSetFrame);
    }//..?
    vmDryRemainSecond=cmDryTimeManipulator.ccGetValue()/16;
    vmWetRemainSecond=cmWetTimeManipulator.ccGetValue()/16;
    
    //--
    cmAGWeighCTRL.ccSetToDischarge(cmAGDischargeDelayTM.ccIsUp());
    cmFRWeighCTRL.ccSetToDischarge(cmFRDischargeDelayTM.ccIsUp());
    cmASWeighCTRL.ccSetToDischarge(cmDryTimeManipulator.ccIsAt(1));
    
    //--
    cmAGDischargedFlag.ccSet(SubWeighingDelegator.mnAGDischargeConfirm);
    cmFRDischargedFlag.ccSet(SubWeighingDelegator.mnFRDischargeConfirm);
    cmASDischargedFlag.ccSet(SubWeighingDelegator.mnASDischargeConfirm);
    cmAllDischargeOverFlag.ccSetBit(ZcPLC.ccAND(
      cmAGDischargedFlag, cmFRDischargedFlag, cmASDischargedFlag
    ));
    if(cmAllDischargeOverPLS.ccUpPulse(cmAllDischargeOverFlag.ccGetBit())){
      ssDecrementBatchCounter();
    }//..?
    cmMixingTimeUpFlag.ccSet(cmWetTimeManipulator.ccIsAt(1));
    
    //--
    if(ZcPLC.ccAND(cmMixingTimeUpFlag, cmAllDischargeOverFlag)){
      SubWeighingDelegator.mnMixerAutoDischargeRequire=true;
    }//..?
    if(cmMixerDischargeStartPLS
        .ccUpPulse(SubWeighingDelegator.mnMixerAutoDischargeRequire))
    {
      ssLogWeighResult();
    }//..?
    
    //-- 
    if(SubWeighingDelegator.mnMixerDischargedConfirm){
      cmMixerReadyFlag=true;
      SubWeighingDelegator.mnMixerAutoDischargeRequire=false;
      cmMixingTimeUpFlag.ccReset();
      cmAGDischargedFlag.ccReset();
      cmFRDischargedFlag.ccReset();
      cmASDischargedFlag.ccReset();
    }//..?
    
  }//++~
  
  public final void ccBind(){
    
    //-- weigh model ui
    SubWeigherGroup.ccRefer().cmAGTargetCB
      .ccSetIsActivated(cmAGWeighCTRL.ccIsWeighing());
    SubWeigherGroup.ccRefer().cmAGTargetCB
      .ccSetValue(cmDesAGWeighLevelTargetKG[cmAGWeighCTRL
        .ccGetCurrentLevel() & MainPlantModel.C_MATT_AGGR_GENERAL_MASK]);
    
    SubWeigherGroup.ccRefer().cmFRTargetCB
      .ccSetIsActivated(cmFRWeighCTRL.ccIsWeighing());
    SubWeigherGroup.ccRefer().cmFRTargetCB
      .ccSetFloatValueForOneAfter(VcNumericUtility
        .ccToFloatForOneAfter(cmDesFRWeighLevelTargetKG[cmFRWeighCTRL
          .ccGetCurrentLevel() & MainPlantModel.C_MATT_REST_GENERAL_MASK]));
    
    SubWeigherGroup.ccRefer().cmASTargetCB
      .ccSetIsActivated(cmASWeighCTRL.ccIsWeighing());
    SubWeigherGroup.ccRefer().cmASTargetCB
      .ccSetFloatValueForOneAfter(VcNumericUtility
        .ccToFloatForOneAfter(cmDesASWeighLevelTargetKG[cmASWeighCTRL
          .ccGetCurrentLevel() & MainPlantModel.C_MATT_REST_GENERAL_MASK]));
    
    //-- weigh dynamic result
    SubWeighDynamicManager.ccRefer().ccSetAGCurrentWeighKG(
      cmAGWeighCTRL.ccGetCurrentLevel(),
      SubAnalogScalarManager.ccRefer().ccGetAGCellKG()
    );
    SubWeighDynamicManager.ccRefer().ccSetFRCurrentWeighKG(
      cmFRWeighCTRL.ccGetCurrentLevel(),
      SubAnalogScalarManager.ccRefer().ccGetFRCellKG()
    );
    SubWeighDynamicManager.ccRefer().ccSetASCurrentWeighKG(
      cmASWeighCTRL.ccGetCurrentLevel(),
      SubAnalogScalarManager.ccRefer().ccGetASCellKG()
    );
    
    //-- weigh control ui
    
    //-- weigh control ui ** THE key
    SubOperativeGroup.ccRefer().cmLesBatchTB.get(0)
      .ccSetValue(vmRemainBatch);
    
    //-- weigh control ui ** rest
    SubOperativeGroup.ccRefer().cmLesRecipeTB.get(0)
      .ccSetValue(cmDesRecipeNumber[0]);
    SubOperativeGroup.ccRefer().cmLesKilogramTB.get(0)
      .ccSetValue(cmDesKiloGramme[0]);
    SubOperativeGroup.ccRefer().cmWeighReadyPL
      .ccSetIsActivated(cmIsAutoWeighReady);
    SubOperativeGroup.ccRefer().cmWeighStartSW
      .ccSetIsActivated(cmIsAutoWeighing);
    SubOperativeGroup.ccRefer().cmWeighStopSW
      .ccSetIsActivated(!cmIsAutoWeighing);
    
    //-- mixer ui
    SubMixerGroup.ccRefer().cmDryCountCB.ccSetValue(vmDryRemainSecond);
    SubMixerGroup.ccRefer().cmWetCountCB.ccSetValue(vmWetRemainSecond);
    SubMixerGroup.ccRefer().cmDryCountCB
      .ccSetIsActivated(cmIsDryCountingDown
        && MainSketch.ccIsRollingAccrose(5, 2));
    SubMixerGroup.ccRefer().cmWetCountCB
      .ccSetIsActivated(cmIsWetCountingDown
        && MainSketch.ccIsRollingAccrose(5, 2));
    
  }//++~
  
  private void ssLogWeighResult(){
    SubWeighStatisticManager.ccRefer().ccOfferLog(
      /* 6 */
      99,999f,9999f,
      SubWeighDynamicManager.ccRefer().ccDumpResult()
    );
    SwingUtilities.invokeLater(SubMonitorPane.ccRefer()
      .cmStatisticWeighResultTableRefreshing);
  }//+++
  
  private void ssDecrementBatchCounter(){
    vmRemainBatch--;
    if(vmRemainBatch<=0){
      ssClearCurrentBooked();
      vmRemainBatch=0;
      cmIsAutoWeighing=false;
    }//..?
  }//+++
  
  //===
  
  private void ssClearStatusFlag(){
    cmAGDischargedFlag.ccReset();
    cmFRDischargedFlag.ccReset();
    cmASDischargedFlag.ccReset();
    cmAllDischargeOverFlag.ccReset();
    cmMixingTimeUpFlag.ccReset();
    cmIsWetCountingDown=false;
    cmIsDryCountingDown=false;
    cmDryTimeManipulator.ccSetValue(vmDrySetFrame);
    cmWetTimeManipulator.ccSetValue(vmWetSetFrame);
    //[so]:: how do we deal with this? : cmMixerReadyFlag=false;
  }//+++
  
  private void ssRefreshUI(){
    for(int i =MainPlantModel.C_BOOK_UI_CAPA_HEAD;
            i<=MainPlantModel.C_BOOK_UI_CAPA_TAIL;i++){
      SubOperativeGroup.ccRefer().cmLesRecipeTB.get(i)
        .ccSetValue(cmDesRecipeNumber[i]);
      SubOperativeGroup.ccRefer().cmLesNameCB.get(i)
        .ccSetText(SubRecipeManager.ccRefer()
          .ccGetRecipeName(cmDesRecipeNumber[i]));
      SubOperativeGroup.ccRefer().cmLesKilogramTB.get(i)
        .ccSetValue(cmDesKiloGramme[i]);
      SubOperativeGroup.ccRefer().cmLesBatchTB.get(i)
        .ccSetValue(cmDesBatchCount[i]);
    }//..~
  }//+++
  
  private void ssVerifyFirstRow(){
    cmIsAutoWeighReady = ZcPLC.ccAnd(
      ZcPLC.ccAnd(
        SubRecipeManager.ccRefer().ccHasRecipe(cmDesRecipeNumber[1]),
        cmDesKiloGramme[1]>999,
        cmDesBatchCount[1]>0
      ),
      SubVProvisionDelegator.mnMixerIconPL,
      ZcPLC.ccAnd(
        SubAnalogScalarManager.ccRefer().ccGetAGCellKG()<=vmAGEmptyKG,
        SubAnalogScalarManager.ccRefer().ccGetFRCellKG()<=vmFREmptyKG,
        SubAnalogScalarManager.ccRefer().ccGetASCellKG()<=vmASEmptyKG
      )
    );
  }//+++
  
  private void ssBunkUp(){
    if(vmRemainBatch!=0){return;}
    cmDesRecipeNumber[0]=cmDesRecipeNumber[MainPlantModel.C_BOOK_UI_CAPA_HEAD];
    cmDesKiloGramme[0]=cmDesKiloGramme[MainPlantModel.C_BOOK_UI_CAPA_HEAD];
    cmDesBatchCount[0]=cmDesBatchCount[MainPlantModel.C_BOOK_UI_CAPA_HEAD];
    vmRemainBatch=cmDesBatchCount[0];
    for(int i=MainPlantModel.C_BOOK_UI_CAPA_HEAD;
            i<MainPlantModel.C_BOOK_UI_CAPA_TAIL;i++){
      cmDesRecipeNumber[i]=cmDesRecipeNumber[i+1];
      cmDesKiloGramme[i]=cmDesKiloGramme[i+1];
      cmDesBatchCount[i]=cmDesBatchCount[i+1];
    }//..~
    cmDesRecipeNumber[MainPlantModel.C_BOOK_UI_CAPA_TAIL]=0;
    cmDesKiloGramme[MainPlantModel.C_BOOK_UI_CAPA_TAIL]=0;
    cmDesBatchCount[MainPlantModel.C_BOOK_UI_CAPA_TAIL]=0;
    SubOperativeGroup.ccRefer().cmLesNameCB.get(0)
      .ccSetText(SubRecipeManager.ccRefer()
        .ccGetRecipeName(cmDesRecipeNumber[0]));
  }//+++
  
  private void ssClearCurrentBooked(){
    cmDesRecipeNumber[0]=0;
    cmDesKiloGramme[0]=0;
    cmDesBatchCount[0]=0;
    SubOperativeGroup.ccRefer().cmLesNameCB.get(0)
      .ccSetText(SubRecipeManager.C_INVALID_MARK);
  }//+++
  
  private void ssCalculateWeighValues(){
    
    VcConst.ccPrintln("total-kg", cmDesKiloGramme[0]);
    VcConst.ccPrintln("recp", cmDesRecipeNumber[0]);
    SubRecipeManager.ccRefer().ccSetOnWeighingRecipe(cmDesRecipeNumber[0]);
    
    cmDesAGWeighLevelTargetKG[0]=0;
    cmDesFRWeighLevelTargetKG[0]=0;
    cmDesASWeighLevelTargetKG[0]=0;
    
    for(int i=MainPlantModel.C_MATT_AGGR_UI_VALID_MAX;i>=1;i--){
      
      //-- ag ** kg
      cmDesAGWeighLevelTargetKG[i]=cmDesAGWeighLevelTargetKG[(i+1)
        &MainPlantModel.C_MATT_AGGR_GENERAL_MASK]
        +ssTranslatePercentage(SubRecipeManager.ccRefer()
          .ccGetPercentage('G', i));//..how do we fix index from level to actual?
      //-- ag ** ad
      cmDesAGWeighLevelTargetAD[i]=SubAnalogScalarManager.ccRefer()
        .ccToAGCellAD(cmDesAGWeighLevelTargetKG[i],vmAGEmptyKG);//..what if the scala needs revice?!
      
      if(i<MainPlantModel.C_MATT_REST_GENERAL_SIZE){
        
        //-- fr ** kg
        cmDesFRWeighLevelTargetKG[i]=cmDesFRWeighLevelTargetKG[(i+1)
          &MainPlantModel.C_MATT_REST_GENERAL_MASK]
          +ssTranslatePercentage(SubRecipeManager.ccRefer()
            .ccGetPercentage('F', i));//..how do we fix index from level to actual?
        //-- fr ** ad
        cmDesFRWeighLevelTargetAD[i]=SubAnalogScalarManager.ccRefer()
          .ccToAGCellAD(cmDesFRWeighLevelTargetKG[i],vmFREmptyKG);//..what if the scala needs revice?!
        
        //-- as ** kg
        cmDesASWeighLevelTargetKG[i]=cmDesASWeighLevelTargetKG[(i+1)
          &MainPlantModel.C_MATT_REST_GENERAL_MASK]
          +ssTranslatePercentage(SubRecipeManager.ccRefer()
            .ccGetPercentage('S', i));//..how do we fix index from level to actual?
        //-- as ** ad
        cmDesASWeighLevelTargetAD[i]=SubAnalogScalarManager.ccRefer()
          .ccToAGCellAD(cmDesASWeighLevelTargetKG[i],vmASEmptyKG);//..what if the scala needs revice?!
        
        //[todo]::cmDesRCWeighLevelTargetKG[i]=ssTranslatePercentage..
        //[todo]::cmDesADWeighLevelTargetKG[i]=ssTranslatePercentage..
        
      }//..?
    }//..~
    
    cmDesAGWeighLevelTargetAD[0]=SubAnalogScalarManager.ccRefer()
      .ccToAGCellAD(vmAGEmptyKG);
    cmDesFRWeighLevelTargetAD[0]=SubAnalogScalarManager.ccRefer()
      .ccToFRCellAD(vmFREmptyKG);
    cmDesASWeighLevelTargetAD[0]=SubAnalogScalarManager.ccRefer()
      .ccToASCellAD(vmASEmptyKG);
    
    VcConst.ccPrintln("ag-kg", Arrays.toString(cmDesAGWeighLevelTargetKG));
    VcConst.ccPrintln("ag-ad", Arrays.toString(cmDesAGWeighLevelTargetAD));
    VcConst.ccPrintln("fr-kg", Arrays.toString(cmDesFRWeighLevelTargetKG));
    VcConst.ccPrintln("fr-ad", Arrays.toString(cmDesFRWeighLevelTargetAD));
    VcConst.ccPrintln("as-kg", Arrays.toString(cmDesASWeighLevelTargetKG));
    VcConst.ccPrintln("as-ad", Arrays.toString(cmDesASWeighLevelTargetAD));
    
  }//+++
  
  private int ssTranslatePercentage(float pxPercentage){
    int r=(int)(
      ((float)cmDesKiloGramme[0])*pxPercentage/100f  
    );
    return r;
  }//+++
  
  //===
  
  public final void ccSetBookModelValue(EcValueBox pxKey, int pxVal){
    
    //-- checkin
    if(pxKey==null){return;}
    if(!cmMapOfModelManiputator.containsKey(pxKey)){return;}
    
    //-- set
    MiValue lpManipulator = cmMapOfModelManiputator.get(pxKey);
    lpManipulator.ccSetValue(Integer.valueOf(
      pxVal & 0xFFFF //.. i am a cracked stone head who box on purpose
    ));
    
    //-- post
    ssVerifyFirstRow();
    ssRefreshUI();
    
  }//++<
  
  //===
  
  synchronized public final String ccGetAGCTRLStatus(){
    return "AG"+cmAGWeighCTRL.ccToTag();//..maybe a translate?
  }//++>
  
  synchronized public final String ccGetFRCTRLStatus(){
    return "FR"+cmFRWeighCTRL.ccToTag();//..maybe a translate?
  }//++>
  
  synchronized public final String ccGetASCTRLStatus(){
    return "AS"+cmASWeighCTRL.ccToTag();//..maybe a translate?
  }//++>
  
  //===
  
  @Deprecated public final void tstRallyUpTestBook(){
    cmDesRecipeNumber[1]=11;cmDesKiloGramme[1]=3100;cmDesBatchCount[1]=3;
    cmDesRecipeNumber[2]=12;cmDesKiloGramme[2]=3200;cmDesBatchCount[2]=4;
    cmDesRecipeNumber[3]=13;cmDesKiloGramme[3]=3300;cmDesBatchCount[3]=5;
    cmDesRecipeNumber[4]=14;cmDesKiloGramme[4]=3400;cmDesBatchCount[4]=6;
  }//+++
  
  @Deprecated public final void tstTagg(){
    VcLocalTagger.ccTag("-w-ctrl?", cmAGWeighCTRL);
  }//+++
  
 }//***eof
