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

import java.util.HashMap;
import kosui.ppplocalui.EcComponent;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplocalui.EiTriggerable;
import kosui.ppplogic.ZcImpulsivePulser;
import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZcRangedModel;
import kosui.ppplogic.ZcRangedValueModel;
import kosui.ppplogic.ZcTimer;
import kosui.pppmodel.MiValue;
import kosui.ppputil.VcLocalTagger;
import kosui.ppputil.VcNumericUtility;
import nextzz.pppdelegate.SubWeighingDelegator;
import nextzz.ppplocalui.SubMixerGroup;
import nextzz.ppplocalui.SubOperativeGroup;
import nextzz.pppmain.MainSketch;
import nextzz.pppsimulate.ZcWeighController;

public final class SubWeighControlManager {

  private static final SubWeighControlManager SELF
    = new SubWeighControlManager();
  public static final SubWeighControlManager ccRefer(){return SELF;}//+++
  private SubWeighControlManager(){}//++!

  //===
  
  //===
  
  public volatile int vmRemainBatch=0;
  public volatile int vmDrySetFrame=90;
  public volatile int vmWetSetFrame=160;
  public volatile int vmDryRemainSecond=0;
  public volatile int vmWetRemainSecond=0;
  
  public final ZcWeighController
    cmAGWeighCTRL = new ZcWeighController(7),
    cmFRWeighCTRL = new ZcWeighController(3),
    cmASWeighCTRL = new ZcWeighController(3)
  ;//,,,
  
  private final ZcTimer
    cmAllWeighOverConfrimTM = new ZcOnDelayTimer(16),
    cmAGDischargeDelayTM    = new ZcImpulsivePulser(7),
    cmFRDischargeDelayTM    = new ZcImpulsivePulser(11);
  
  private final ZcRangedValueModel
    cmDryTimeManipulator = new ZcRangedValueModel(0, 65535),
    cmWetTimeManipulator = new ZcRangedValueModel(0, 65535);
  
  private boolean cmIsAutoWeighReady,cmIsAutoWeighing;
  private boolean cmIsDryCountingDown,cmIsWetCountingDown;
  private boolean cmMixerReadyFlag=true;
  
  private final int[] 
    cmDesRecipeNumber = new int[MainPlantModel.C_BOOK_MODEL_SIZE],
    cmDesKiloGramme   = new int[MainPlantModel.C_BOOK_MODEL_SIZE],
    cmDesBatchCount   = new int[MainPlantModel.C_BOOK_MODEL_SIZE];
  
  public final EiTriggerable cmWeighStartClicking = new EiTriggerable() {
    @Override public void ccTrigger(){
      if(cmIsAutoWeighReady){
        ssBunkUp();
        ssVerifyFirstRow();
        ssRefreshUI();
        cmIsAutoWeighing=true;
      }//..?
    }//+++
  };//***
  
  public final EiTriggerable cmWeighCacncelClicking = new EiTriggerable() {
    @Override public void ccTrigger(){
      cmIsAutoWeighing=false;
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
        SubOperativeGroup.ccRefer().cmDesRecipeTB.get(i),
        new McRecipeValue(i)
      );
      
      //-- init map ** kg
      cmMapOfModelManiputator.put(
        SubOperativeGroup.ccRefer().cmDesKilogramTB.get(i),
        new McKilogramValue(i)
      );
      
      //-- init map ** batch
      cmMapOfModelManiputator.put(
        SubOperativeGroup.ccRefer().cmDesBatchTB.get(i),
        new McBatchValue(i)
      );
      
    }//..~
    
    /* 6 */tstRallyUpTestBook();
    
  }//++!
  
  public final void ccLogic(){
    
    /* 6 */
    boolean lpAGSkip = EcComponent.ccIsKeyPressed('i');
    boolean lpFRSkip = EcComponent.ccIsKeyPressed('u');
    boolean lpASSkip = EcComponent.ccIsKeyPressed('o');
    
    cmAllWeighOverConfrimTM.ccAct(
         cmAGWeighCTRL.ccIsWaitingToDischarge()
      && cmFRWeighCTRL.ccIsWaitingToDischarge()
      && cmASWeighCTRL.ccIsWaitingToDischarge()
    );
    
    boolean lpStart = SubOperativeGroup.ccRefer()
      .cmWeighStartSW.ccIsMousePressed();
    
    boolean lpDiss = //EcComponent.ccIsKeyPressed('k')
      //&&
      cmAllWeighOverConfrimTM.ccIsUp()
      && cmMixerReadyFlag;
    
    cmAGWeighCTRL.ccStart(lpStart);
    cmAGWeighCTRL.ccSetHasNext(vmRemainBatch>1);
    cmAGWeighCTRL.ccSetToNext(lpAGSkip);
    cmAGWeighCTRL.ccRun();
    
    cmFRWeighCTRL.ccStart(lpStart);
    cmFRWeighCTRL.ccSetHasNext(vmRemainBatch>1);
    cmFRWeighCTRL.ccSetToNext(lpFRSkip);
    cmFRWeighCTRL.ccRun();
    
    cmASWeighCTRL.ccStart(lpStart);
    cmASWeighCTRL.ccSetHasNext(vmRemainBatch>1);
    cmASWeighCTRL.ccSetToNext(lpASSkip);
    cmASWeighCTRL.ccRun();
    
    //-- raw delay
    cmAGDischargeDelayTM.ccAct(lpDiss);
    cmFRDischargeDelayTM.ccAct(lpDiss);
    
    //-- dry-wet
    if(lpDiss){
      SubWeighingDelegator.mnMixerDischargeConfirmFlag=false;
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
    if(cmWetTimeManipulator.ccIsAt(1)){
      SubWeighingDelegator.mnMixerAutoDischargeFlag=true;
      vmRemainBatch--;if(vmRemainBatch<=0){
        ssClearCurrentlyBooked();
        vmRemainBatch=0;
        cmIsAutoWeighing=false;
      }//..?
    }//..?
    if(SubWeighingDelegator.mnMixerDischargeConfirmFlag){
      cmMixerReadyFlag=true;
      SubWeighingDelegator.mnMixerAutoDischargeFlag=false;
    }//..?
    
    //[head]:: i know you have absolutely no idea about what to do next
    
  }//++~
  
  public final void ccBind(){
    
    //-- weigh control ui
    
    //-- weigh control ui ** THE key
    SubOperativeGroup.ccRefer().cmDesBatchTB.get(0)
      .ccSetValue(vmRemainBatch);
    
    //-- weigh control ui ** rest
    SubOperativeGroup.ccRefer().cmDesRecipeTB.get(0)
      .ccSetValue(cmDesRecipeNumber[0]);
    SubOperativeGroup.ccRefer().cmDesKilogramTB.get(0)
      .ccSetValue(cmDesKiloGramme[0]);
    SubOperativeGroup.ccRefer().cmWeighReadyPL
      .ccSetIsActivated(cmIsAutoWeighReady);
    SubOperativeGroup.ccRefer().cmWeighStartSW
      .ccSetIsActivated(cmIsAutoWeighing);
    SubOperativeGroup.ccRefer().cmWeighCancelSW
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
  
  //===
  
  private void ssRefreshUI(){
    for(int i =MainPlantModel.C_BOOK_UI_CAPA_HEAD;
            i<=MainPlantModel.C_BOOK_UI_CAPA_TAIL;i++){
      SubOperativeGroup.ccRefer().cmDesRecipeTB.get(i)
        .ccSetValue(cmDesRecipeNumber[i]);
      SubOperativeGroup.ccRefer().cmDesKilogramTB.get(i)
        .ccSetValue(cmDesKiloGramme[i]);
      SubOperativeGroup.ccRefer().cmDesBatchTB.get(i)
        .ccSetValue(cmDesBatchCount[i]);
    }//..~
  }//+++
  
  private void ssVerifyFirstRow(){
    cmIsAutoWeighReady=(cmDesRecipeNumber[1]!=0)//..?.ccVerifyRecipeNumber?
     && (cmDesKiloGramme[1]>999)
     && (cmDesBatchCount[1]>0);
  }//+++
  
  private void ssClearCurrentlyBooked(){
    cmDesRecipeNumber[0]=0;
    cmDesKiloGramme[0]=0;
    cmDesBatchCount[0]=0;
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
  }//+++
  
  //===
  
  public final void ccSetMixerReady(){
    cmMixerReadyFlag=true;
  }//++<
  
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
