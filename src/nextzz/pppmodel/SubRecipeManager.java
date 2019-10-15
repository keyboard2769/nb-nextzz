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
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import kosui.ppplogic.ZcRangedModel;
import kosui.pppmodel.McTableAdapter;
import kosui.ppputil.VcTranslator;
import kosui.ppputil.VcArrayUtility;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcNumericUtility;
import nextzz.pppswingui.SubRecipePane;
import processing.core.PApplet;

public final class SubRecipeManager extends McTableAdapter{
  
  public static final int C_CAPACITY_HEAD =    0;
  public static final int C_CAPACITY_TAIL =  999;
  public static final int C_CAPACITY_SIZE = 1024;
  public static final int C_CAPACITY_MASK = 1023;
  
  private static final SubRecipeManager SELF = new SubRecipeManager();
  public static final SubRecipeManager ccRefer(){return SELF;}//+++
  private SubRecipeManager(){}//++!
  
  //===
  
  private static final List<String> O_LIST_OF_TABLE_COLUMN_TITLES
   = Collections.unmodifiableList(Arrays.asList(
    //--
    VcTranslator.tr("_col_id"),   //..0
    VcTranslator.tr("_col_name"), //..1
    //--
    VcTranslator.tr("_col_ag_7"),//..2
    VcTranslator.tr("_col_ag_6"),//..3
    VcTranslator.tr("_col_ag_5"),//..4
    VcTranslator.tr("_col_ag_4"),//..5
    VcTranslator.tr("_col_ag_3"),//..6
    VcTranslator.tr("_col_ag_2"),//..7
    VcTranslator.tr("_col_ag_1"),//..8
    //--
    VcTranslator.tr("_col_fr_3"),//.. 9
    VcTranslator.tr("_col_fr_2"),//..10
    VcTranslator.tr("_col_fr_1"),//..11
    //--
    VcTranslator.tr("_col_as_3"),//..12
    VcTranslator.tr("_col_as_2"),//..13
    VcTranslator.tr("_col_as_1"),//..14
    //--
    VcTranslator.tr("_col_rc_3"),//..15
    VcTranslator.tr("_col_rc_2"),//..16
    VcTranslator.tr("_col_rc_1"),//..17
    //--
    VcTranslator.tr("_col_ad_3"),//..18
    VcTranslator.tr("_col_ad_2"),//..19
    VcTranslator.tr("_col_ad_1") //..20
  ));//,,,
  
  //===
  
  private final class McRecipe{
    int cmRecipeID = 0;
    String cmRecipeName="-- ? XXVII";
    float[] cmDesAGPercentage = new float[]{0f,0f,0f,0f, 0f,0f,0f,0f}; 
    float[] cmDesFRPercentage = new float[]{0f,0f,0f,0f}; 
    float[] cmDesASPercentage = new float[]{0f,0f,0f,0f}; 
    float[] cmDesRCPercentage = new float[]{0f,0f,0f,0f}; 
    float[] cmDesADPercentage = new float[]{0f,0f,0f,0f};
    McRecipe(){}//++!
    McRecipe(int pxID, String pxName, float[] pxPacked){
      ccSetupRecipeIdentity(pxID, pxName);
      ccSetContentValue(pxPacked);
    }//++!
    final void ccSetupRecipeIdentity(int pxID, String pxName){
      cmRecipeID=pxID&C_CAPACITY_MASK;
      //.. we are not doing check stuff here
      //..   becuz we need it to get done at somwhere
      //..   a warn box can get called conveniently
      cmRecipeName=pxName;
    }//++<
    final void ccSetContentValue(float[] pxPacked){
      if(pxPacked==null){
        System.err.println("McRecipe.ccSetContentValue()::null passed");
        return;
      }//..?
      if(pxPacked.length!=24){
        System.err.println("McRecipe.ccSetContentValue()::invalid length");
        return;
      }//..?
      for(int i=0;i<8;i++){
        cmDesAGPercentage[i]=pxPacked[0+i];
        if(i<4){
          cmDesFRPercentage[i]=pxPacked[ 8+i];
          cmDesASPercentage[i]=pxPacked[12+i];
          cmDesRCPercentage[i]=pxPacked[16+i];
          cmDesADPercentage[i]=pxPacked[20+i];
        }//..?
      }//..~
    }//++<
    final String ccGetColumnContent(int pxCount){
      switch (pxCount) {
        case 0:return Integer.toString((int)cmRecipeID);
        case 1:return cmRecipeName;
        //--
        case 2:return Float.toString(cmDesAGPercentage[7]);
        case 3:return Float.toString(cmDesAGPercentage[6]);
        case 4:return Float.toString(cmDesAGPercentage[5]);
        case 5:return Float.toString(cmDesAGPercentage[4]);
        case 6:return Float.toString(cmDesAGPercentage[3]);
        case 7:return Float.toString(cmDesAGPercentage[2]);
        case 8:return Float.toString(cmDesAGPercentage[1]);
        //--
        case  9:return Float.toString(cmDesFRPercentage[3]);
        case 10:return Float.toString(cmDesFRPercentage[2]);
        case 11:return Float.toString(cmDesFRPercentage[1]);
        //--
        case 12:return Float.toString(cmDesASPercentage[3]);
        case 13:return Float.toString(cmDesASPercentage[2]);
        case 14:return Float.toString(cmDesASPercentage[1]);
        //--
        case 15:return Float.toString(cmDesRCPercentage[3]);
        case 16:return Float.toString(cmDesRCPercentage[2]);
        case 17:return Float.toString(cmDesRCPercentage[1]);
        //--
        case 18:return Float.toString(cmDesADPercentage[3]);
        case 19:return Float.toString(cmDesADPercentage[2]);
        case 20:return Float.toString(cmDesADPercentage[1]);
        //--
        default:return "<?>";
      }//..?
    }//++>
  }//***
  
  private final McRecipe cmPanedRecipe
    = new McRecipe();
  
  private McRecipe cmOnWeighingRecipeBUF = null;
  
  private float cmPanedTotalSum = 0f;
  private float cmPanedAsphaltSum = 0f;
  
  private final TreeMap<Integer, McRecipe> cmMapOfRecipe
    = new TreeMap<Integer, McRecipe>();
  
  private Object[] cmDesOrderBUFF=null;
  
  //===
  
  public void ccInit(){
    
    //-- dummy for dev
    tstGenerateDummyRecipe();
    ssRefreshOrder();
    
  }//++!
  
  private void ssRefreshOrder(){
    cmDesOrderBUFF=cmMapOfRecipe.keySet().toArray();
  }//+++
  
  private void ssCopyRecipe(McRecipe pxFrom, McRecipe pxTo){
    if(pxFrom==null){return;}
    if(pxTo==null){return;}
    pxTo.cmRecipeID=pxFrom.cmRecipeID;
    pxTo.cmRecipeName=pxFrom.cmRecipeName;
    VcArrayUtility.ccCopy(pxFrom.cmDesAGPercentage, pxTo.cmDesAGPercentage);
    VcArrayUtility.ccCopy(pxFrom.cmDesFRPercentage, pxTo.cmDesFRPercentage);
    VcArrayUtility.ccCopy(pxFrom.cmDesASPercentage, pxTo.cmDesASPercentage);
    VcArrayUtility.ccCopy(pxFrom.cmDesRCPercentage, pxTo.cmDesRCPercentage);
    VcArrayUtility.ccCopy(pxFrom.cmDesADPercentage, pxTo.cmDesADPercentage);
  }//+++
  
  private void ssRefreshPanedRecipe(){
    SubRecipePane.ccRefer().cmIdentityBox
      .setText(Integer.toString(cmPanedRecipe.cmRecipeID));
    SubRecipePane.ccRefer().cmNameBox
      .setText(cmPanedRecipe.cmRecipeName);
    cmPanedTotalSum=0f;
    cmPanedAsphaltSum=0f;
    for(int i=0;i<8;i++){
      cmPanedTotalSum+=cmPanedRecipe.cmDesAGPercentage[i];
      SubRecipePane.ccRefer().cmLesAGPercetageBox.get(i)
        .setText(Float.toString(cmPanedRecipe.cmDesAGPercentage[i]));
      if(i<4){
        
        //-- summing
        //[todo]::now how do we hide?
        cmPanedAsphaltSum+=cmPanedRecipe.cmDesASPercentage[i];
        cmPanedTotalSum+=cmPanedRecipe.cmDesFRPercentage[i];
        cmPanedTotalSum+=cmPanedRecipe.cmDesASPercentage[i];
        cmPanedTotalSum+=cmPanedRecipe.cmDesRCPercentage[i];
        cmPanedTotalSum+=cmPanedRecipe.cmDesADPercentage[i];
        
        //-- output
        SubRecipePane.ccRefer().cmLesFRPercetageBox.get(i)
          .setText(Float.toString(cmPanedRecipe.cmDesFRPercentage[i]));
        SubRecipePane.ccRefer().cmLesASPercetageBox.get(i)
          .setText(Float.toString(cmPanedRecipe.cmDesASPercentage[i]));
        //[todo]::SubRecipePane.ccRefer().cmLesRCPercetageBox.get(i)...
        //[todo::]SubRecipePane.ccRefer().cmLesADPercetageBox.get(i)...
        
      }//..?
    }//..~
    cmPanedTotalSum=VcNumericUtility.ccRoundForTwoAfter(cmPanedTotalSum);
    cmPanedAsphaltSum=VcNumericUtility.ccRoundForTwoAfter(cmPanedAsphaltSum);
    SubRecipePane.ccRefer().cmToTalBox
      .setText(Float.toString(cmPanedTotalSum)+"%");
    float lpProportion;
    if(cmPanedTotalSum==cmPanedAsphaltSum){
      lpProportion=0f;
    }else{
      lpProportion=(cmPanedAsphaltSum)/(cmPanedTotalSum-cmPanedAsphaltSum);
    }//..?
    SubRecipePane.ccRefer().cmASProportionBox
      .setText(String.format("%.4f", lpProportion));
  }//+++
    
  //===
  
  public final void ccSetPanedRecipeID(int pxID){
    cmPanedRecipe.cmRecipeID=ZcRangedModel
      .ccLimitInclude(pxID, C_CAPACITY_HEAD, C_CAPACITY_TAIL);  
    ssRefreshPanedRecipe();
  }//++<
  
  public final void ccSetPanedRecipeName(String pxName){
    String lpFix = VcConst.ccIsValidString(pxName)
     ? pxName
     : "<?>";
    lpFix=lpFix.trim();
    lpFix=lpFix.replaceAll(" ", "_");
    lpFix=lpFix.replaceAll(",", "_");
    lpFix=lpFix.replaceAll(";", "_");
    lpFix=lpFix.replaceAll("\\.", "_");
    lpFix=lpFix.replaceAll("\n", "_");
    lpFix=lpFix.replaceAll("\r\n", "_");
    if(lpFix.length()>=64){lpFix=lpFix.substring(0, 63);}
    cmPanedRecipe.cmRecipeName=lpFix; 
    ssRefreshPanedRecipe();
  }//++<
  
  public final
  void ccSetPanePercentage(char pxMatt, int pxOrder, float pxVal){
    float lpFixed = PApplet.constrain(pxVal, 0f, 100f);
    switch (pxMatt) {
      case 'G':cmPanedRecipe.cmDesAGPercentage[pxOrder&7]=lpFixed;break;
      case 'F':cmPanedRecipe.cmDesFRPercentage[pxOrder&3]=lpFixed;break;
      case 'S':cmPanedRecipe.cmDesASPercentage[pxOrder&3]=lpFixed;break;
      case 'R':cmPanedRecipe.cmDesRCPercentage[pxOrder&3]=lpFixed;break;
      case 'D':cmPanedRecipe.cmDesADPercentage[pxOrder&3]=lpFixed;break;
      default:break;
    }//...?
    ssRefreshPanedRecipe();
  }//++<
  
  public final void ccApplyTableSelection(int pxIndex){
    int lpFixed = pxIndex & C_CAPACITY_MASK;
    int lpID = VcNumericUtility
      .ccInteger(VcArrayUtility.ccGet(cmDesOrderBUFF, lpFixed));
    if(!cmMapOfRecipe.containsKey(lpID)){return;}
    McRecipe lpTarget = cmMapOfRecipe.get(lpID);
    ssCopyRecipe(lpTarget, cmPanedRecipe);
    ssRefreshPanedRecipe();
  }//+++
  
  //[todo]::registerPanedRecipe();
  //[todo]::duplicateSelectedRecipe();
  //[todo]::deleteSelectedOne();
  
  public final void ccSetOnWeighingRecipe(int pxID){
    if(cmMapOfRecipe.containsKey(pxID)){
      cmOnWeighingRecipeBUF=new McRecipe();
      ssCopyRecipe(cmMapOfRecipe.get(pxID), cmOnWeighingRecipeBUF);
    }else{
      cmOnWeighingRecipeBUF=null;
    }//..?
  }//+++
  
  public final void ccClearOnWeighingRecipe(){
    cmOnWeighingRecipeBUF=null;
  }//+++
  
  //===
  
  public final boolean ccHasRecipe(int pxID){
    return cmMapOfRecipe.containsKey(pxID);
  }//+++
  
  public final String ccGetRecipeName(int pxID){
    if((pxID <= 0) || (pxID >999)){return "--";}
    if(!cmMapOfRecipe.containsKey(pxID)){
      return VcTranslator.tr("_opm_not_found");
    }//..?
    return cmMapOfRecipe.get(pxID).cmRecipeName;
  }//+++
  
  public final
  float ccGetPercentage(char matt,int order){
    if(cmOnWeighingRecipeBUF==null){return 0f;}
    switch (matt) {
      case 'G':return cmOnWeighingRecipeBUF.cmDesAGPercentage[order&7];
      case 'F':return cmOnWeighingRecipeBUF.cmDesFRPercentage[order&3];
      case 'S':return cmOnWeighingRecipeBUF.cmDesASPercentage[order&3];
      case 'R':return cmOnWeighingRecipeBUF.cmDesRCPercentage[order&3];
      case 'D':return cmOnWeighingRecipeBUF.cmDesADPercentage[order&3];
      default:return 0f;
    }//..?
  }//+++
  
  //[todo]::syn getKG(int index,char matt,int order, float val)
  //[todo]::syn getAD(int index,char matt,int order, float val)
  
  //===
  
  @Override public int getColumnCount() {
    return O_LIST_OF_TABLE_COLUMN_TITLES.size();
  }//++>
  
  @Override public String getColumnName(int pxColumnIndex) {
    return O_LIST_OF_TABLE_COLUMN_TITLES.get(pxColumnIndex);
  }//++>
  
  @Override public int getRowCount() {
    return cmMapOfRecipe.size();
  }//++>
  
  @Override public Object getValueAt(int pxRowIndex, int pxColumnIndex) {
    if(cmDesOrderBUFF==null){return "<!>";}
    int lpOrderedIndex=VcNumericUtility
      .ccInteger(VcArrayUtility.ccGet(cmDesOrderBUFF, pxRowIndex));
    return cmMapOfRecipe.get(lpOrderedIndex).ccGetColumnContent(pxColumnIndex);
  }//++>
  
  //===
  
  @Deprecated private void tstGenerateDummyRecipe(){
    cmMapOfRecipe.put(1, new McRecipe(1, "T-N320", new float[]{
      0f,11f,12f,13f,14f,15f,16f,0f,
      0f,0f,2f,3f,
      0f,0f,3f,0f,
      0f,0f,0f,0f,
      0f,0f,0f,0f,
    }));
    cmMapOfRecipe.put(2, new McRecipe(2, "T-N240", new float[]{
      0f,11f,12f,13f,14f,15f,0f,0f,
      0f,0f,2f,3f,
      0f,0f,3f,0f,
      0f,0f,0f,0f,
      0f,0f,0f,0f,
    }));
    cmMapOfRecipe.put(3, new McRecipe(3, "C-N240", new float[]{
      0f,11f,12f,13f,14f,15f,0f,0f,
      0f,0f,2f,0f,
      0f,0f,3f,0f,
      0f,0f,0f,0f,
      0f,0f,0f,0f,
    }));
    cmMapOfRecipe.put(101, new McRecipe(101, "A-AG", new float[]{
      10f,11f,12f,13f,14f,15f,16f,17f,
      0f,0f,0f,0f,
      0f,0f,0f,0f,
      0f,0f,0f,0f,
      0f,0f,0f,0f,
    }));
    cmMapOfRecipe.put(102, new McRecipe(102, "N-RC", new float[]{
      10f,11f,12f,13f,14f,15f,16f,17f,
      0f,1f,2f,3f,
      0f,1f,2f,3f,
      0f,0f,0f,0f,
      0f,1f,2f,3f,
    }));
    cmMapOfRecipe.put(999, new McRecipe(999, "A-TEST", new float[]{
      10.0f,11.0f,12.0f,13.0f, 14.0f,15.0f,16.0f,17.0f,
       3.0f, 3.1f, 3.2f, 3.3f,
       4.0f, 4.1f, 4.2f, 4.3f,
      11.0f,11.1f,11.2f,11.2f,
       5.0f, 5.1f, 5.2f, 5.3f
    }));
  }//+++
  
}//***eof
