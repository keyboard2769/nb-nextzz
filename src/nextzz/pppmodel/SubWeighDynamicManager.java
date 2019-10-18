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
import kosui.pppmodel.McTableAdapter;
import kosui.ppputil.VcArrayUtility;
import kosui.ppputil.VcTranslator;

public final class SubWeighDynamicManager extends McTableAdapter{
  
  private static final SubWeighDynamicManager SELF
    = new SubWeighDynamicManager();
  public static final SubWeighDynamicManager ccRefer(){return SELF;}//+++
  private SubWeighDynamicManager(){}//++!
  
  //===
  
  public static final List<String> O_LIST_OF_TABLE_COLUMN_TITLES
   = Collections.unmodifiableList(Arrays.asList(
    //--
    VcTranslator.tr("_col_ad_3"),//..0
    VcTranslator.tr("_col_ad_2"),//..1
    VcTranslator.tr("_col_ad_1"),//..2
    //--
    VcTranslator.tr("_col_fr_3"),//..3
    VcTranslator.tr("_col_fr_2"),//..4
    VcTranslator.tr("_col_fr_1"),//..5
    //--
    VcTranslator.tr("_col_ag_7"),//..6
    VcTranslator.tr("_col_ag_6"),//..7
    VcTranslator.tr("_col_ag_5"),//..8
    VcTranslator.tr("_col_ag_4"),//..9
    VcTranslator.tr("_col_ag_3"),//..10
    VcTranslator.tr("_col_ag_2"),//..11
    VcTranslator.tr("_col_ag_1"),//..12
    //--
    VcTranslator.tr("_col_as_3"),//..13
    VcTranslator.tr("_col_as_2"),//..14
    VcTranslator.tr("_col_as_1"),//..15
    //--
    VcTranslator.tr("_col_rc_3"),//..16
    VcTranslator.tr("_col_rc_2"),//..17
    VcTranslator.tr("_col_rc_1") //..18
  ));//,,,
  
  private final class McWeighState{
    int[] cmDesAGStateKG = new int[]{0,0,0,0, 0,0,0,0}; 
    int[] cmDesFRStateKG = new int[]{0,0,0,0}; 
    int[] cmDesASStateKG = new int[]{0,0,0,0}; 
    int[] cmDesRCStateKG = new int[]{0,0,0,0}; 
    int[] cmDesADStateKG = new int[]{0,0,0,0};
    final String ccGetColumnContent(int pxCount){
      switch (pxCount) {
        case 0:return Float.toString(((float)cmDesADStateKG[3])/10f);
        case 1:return Float.toString(((float)cmDesADStateKG[2])/10f);
        case 2:return Float.toString(((float)cmDesADStateKG[1])/10f);
        //--
        case 3:return Float.toString(((float)cmDesFRStateKG[3])/10f);
        case 4:return Float.toString(((float)cmDesFRStateKG[2])/10f);
        case 5:return Float.toString(((float)cmDesFRStateKG[1])/10f);
        //--
        case  6:return Integer.toString(cmDesAGStateKG[7]);
        case  7:return Integer.toString(cmDesAGStateKG[6]);
        case  8:return Integer.toString(cmDesAGStateKG[5]);
        case  9:return Integer.toString(cmDesAGStateKG[4]);
        case 10:return Integer.toString(cmDesAGStateKG[3]);
        case 11:return Integer.toString(cmDesAGStateKG[2]);
        case 12:return Integer.toString(cmDesAGStateKG[1]);
        //--
        case 13:return Float.toString(((float)cmDesASStateKG[3])/10f);
        case 14:return Float.toString(((float)cmDesASStateKG[2])/10f);
        case 15:return Float.toString(((float)cmDesASStateKG[1])/10f);
        //--
        case 16:return Float.toString(((float)cmDesRCStateKG[3])/10f);
        case 17:return Float.toString(((float)cmDesRCStateKG[2])/10f);
        case 18:return Float.toString(((float)cmDesRCStateKG[1])/10f);
        default:return "<?>";
      }//...?
    }//++>
  }//+++
  
  private final McWeighState cmTargetWeighKG = new McWeighState();
  private final McWeighState cmCurrentWeighKG = new McWeighState();
  private final McWeighState cmForDumpWeighKG = new McWeighState();
  
  //===
  
  public final void ccSetAGTargetWeighKG(int pxOrder, int pxVal){
    cmTargetWeighKG.cmDesAGStateKG[pxOrder&7]=pxVal;
  }//++<
  
  public final void ccSetFRTargetWeighKG(int pxOrder, int pxVal){
    cmTargetWeighKG.cmDesFRStateKG[pxOrder&3]=pxVal;
  }//++<
  
  public final void ccSetASTargetWeighKG(int pxOrder, int pxVal){
    cmTargetWeighKG.cmDesASStateKG[pxOrder&3]=pxVal;
  }//++<
  
  //===
  
  public final void ccSetAGCurrentWeighKG(int pxOrder, int pxVal){
    cmCurrentWeighKG.cmDesAGStateKG[pxOrder&7]=pxVal;
  }//++<
  
  public final void ccSetFRCurrentWeighKG(int pxOrder, int pxVal){
    cmCurrentWeighKG.cmDesFRStateKG[pxOrder&3]=pxVal;
  }//++<
  
  public final void ccSetASCurrentWeighKG(int pxOrder, int pxVal){
    cmCurrentWeighKG.cmDesASStateKG[pxOrder&3]=pxVal;
  }//++<
  
  //===
  
  public final void ccPopResult(){
    ssCopy(cmCurrentWeighKG, cmForDumpWeighKG);
    ssClear(cmCurrentWeighKG);
  }//+++
  
  public final float[] ccDumpResult(){
    float[] lpRes = new float[24];
    for(int i=0;i<8;i++){
      float lpAG=(float)cmForDumpWeighKG.cmDesAGStateKG[i];
      lpRes[ 0+i]=lpAG<0f?0f:lpAG;
    }//..~
    for(int i=0;i<4;i++){
      float lpFR=((float)cmForDumpWeighKG.cmDesFRStateKG[i])/10f;
      lpRes[ 8+i]=lpFR<0f?0f:lpFR;
      float lpAS=((float)cmForDumpWeighKG.cmDesASStateKG[i])/10f;
      lpRes[12+i]=lpAS<0f?0f:lpAS;
      float lpRC=((float)cmForDumpWeighKG.cmDesRCStateKG[i])/10f;
      lpRes[16+i]=lpRC<0f?0f:lpRC;
      float lpAD=((float)cmForDumpWeighKG.cmDesADStateKG[i])/10f;
      lpRes[20+i]=lpAD<0f?0f:lpAD;
    }//..~
    ssClear(cmForDumpWeighKG);
    return lpRes;
  }//+++
  
  public final void ccClearTarget(){
    ssClear(cmTargetWeighKG);
  }//+++
  
  private void ssCopy(McWeighState pxFrom, McWeighState pxTo){
    VcArrayUtility.ccCopy(pxFrom.cmDesAGStateKG, pxTo.cmDesAGStateKG);
    VcArrayUtility.ccCopy(pxFrom.cmDesFRStateKG, pxTo.cmDesFRStateKG);
    VcArrayUtility.ccCopy(pxFrom.cmDesASStateKG, pxTo.cmDesASStateKG);
    VcArrayUtility.ccCopy(pxFrom.cmDesRCStateKG, pxTo.cmDesRCStateKG);
    VcArrayUtility.ccCopy(pxFrom.cmDesADStateKG, pxTo.cmDesADStateKG);
  }//+++
  
  private void ssClear(McWeighState pxTarget){
    Arrays.fill(pxTarget.cmDesAGStateKG, 0);
    Arrays.fill(pxTarget.cmDesFRStateKG, 0);
    Arrays.fill(pxTarget.cmDesASStateKG, 0);
    Arrays.fill(pxTarget.cmDesRCStateKG, 0);
    Arrays.fill(pxTarget.cmDesADStateKG, 0);
  }//+++
  
  //===
  
  @Override public int getColumnCount() {
    return O_LIST_OF_TABLE_COLUMN_TITLES.size();
  }//++>
  @Override public String getColumnName(int pxColumnIndex) {
    return O_LIST_OF_TABLE_COLUMN_TITLES.get(pxColumnIndex);
  }//++>
  @Override public int getRowCount() {
    return 3;
  }//++>
  @Override public Object getValueAt(int pxRowIndex, int pxColumnIndex) {
    switch (pxRowIndex) {
      case 0:return cmTargetWeighKG.ccGetColumnContent(pxColumnIndex);
      case 1:return cmCurrentWeighKG.ccGetColumnContent(pxColumnIndex);
      case 2:return cmForDumpWeighKG.ccGetColumnContent(pxColumnIndex);
      default:return "<>>";
    }//...?
  }//++>
  
}//***eof
