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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import kosui.pppmodel.McTableAdapter;
import kosui.ppputil.VcStampUtility;
import kosui.ppputil.VcTranslator;

public final class SubWeighStatisticManager extends McTableAdapter{
  
  public static final int C_CAPACITY_SIZE = 512;
  public static final int C_CAPACITY_MASK = 511;
  
  private static final SubWeighStatisticManager SELF
    = new SubWeighStatisticManager();
  public static final SubWeighStatisticManager ccRefer(){return SELF;}//+++
  private SubWeighStatisticManager(){}//++!
  
  //===
  
  private static final List<String> O_LIST_OF_TABLE_COLUMN_TITLES
   = Collections.unmodifiableList(Arrays.asList(
    //--
    VcTranslator.tr("_col_time"),  //..0
    VcTranslator.tr("_col_recipe"),//..1
    VcTranslator.tr("_col_mtemp"), //..2
    VcTranslator.tr("_col_total"), //..3
    //--
    VcTranslator.tr("_col_ad_3"),//..4
    VcTranslator.tr("_col_ad_2"),//..5
    VcTranslator.tr("_col_ad_1"),//..6
    //--
    VcTranslator.tr("_col_fr_3"),//..7
    VcTranslator.tr("_col_fr_2"),//..8
    VcTranslator.tr("_col_fr_1"),//..9
    //--
    VcTranslator.tr("_col_ag_7"),//..10
    VcTranslator.tr("_col_ag_6"),//..11
    VcTranslator.tr("_col_ag_5"),//..12
    VcTranslator.tr("_col_ag_4"),//..13
    VcTranslator.tr("_col_ag_3"),//..14
    VcTranslator.tr("_col_ag_2"),//..15
    VcTranslator.tr("_col_ag_1"),//..16
    //--
    VcTranslator.tr("_col_as_3"),//..17
    VcTranslator.tr("_col_as_2"),//..18
    VcTranslator.tr("_col_as_1"),//..19
    //--
    VcTranslator.tr("_col_rc_3"),//..20
    VcTranslator.tr("_col_rc_2"),//..21
    VcTranslator.tr("_col_rc_1") //..22
  ));//,,,
  
  //===
  
  private final class McWeighRecord {
    String cmTimeStamp="-- ? MXV";
    int cmRecipeID = 0;
    float cmMixtureTemp = 0f;
    float cmTotalKG = 0f;
    float[] cmDesAGResultKG = new float[]{0f,0f,0f,0f, 0f,0f,0f,0f}; 
    float[] cmDesFRResultKG = new float[]{0f,0f,0f,0f}; 
    float[] cmDesASResultKG = new float[]{0f,0f,0f,0f}; 
    float[] cmDesRCResultKG = new float[]{0f,0f,0f,0f}; 
    float[] cmDesADResultKG = new float[]{0f,0f,0f,0f};
    McWeighRecord(){}//++!
    McWeighRecord(
      int pxRecipe, float pxMixtrueTemp, float pxTotalKG,
      float[] pxPacked
    ){
      this();
      ccSetupRecordIdentity(pxRecipe, pxMixtrueTemp, pxTotalKG);
      ccSetContentValue(pxPacked);
    }//++!
    final void ccSetupRecordIdentity(
      int pxRecipe, float pxMixtrueTemp, float pxTotalKG
    ){
      cmRecipeID=pxRecipe;
      cmMixtureTemp=pxMixtrueTemp;
      cmTotalKG=pxTotalKG;
    }//++<
    final void ccSetContentValue(float[] pxPacked){
      if(pxPacked==null){
        System.err.println("McWeighRecord.ccSetContentValue()::null passed");
        return;
      }//..?
      if(pxPacked.length!=24){
        System.err.println("McWeighRecord.ccSetContentValue()::invalid length");
        return;
      }//..?
      cmTimeStamp=VcStampUtility.ccDataLogTypeI();
      for(int i=0;i<8;i++){
        cmDesAGResultKG[i]=pxPacked[ 0+i];
      }//..~
      for(int i=0;i<4;i++){
        cmDesFRResultKG[i]=pxPacked[ 8+i];
        cmDesASResultKG[i]=pxPacked[12+i];
        cmDesRCResultKG[i]=pxPacked[16+i];
        cmDesADResultKG[i]=pxPacked[20+i];
      }//..~
    }//++<
    final String ccGetColumnContent(int pxCount){
      switch (pxCount) {
        case 0:return cmTimeStamp;
        case 1:return Integer.toString((int)cmRecipeID);
        case 2:return Float.toString(cmMixtureTemp);
        case 3:return Float.toString(cmTotalKG);
        //--
        case 4:return Float.toString(cmDesADResultKG[3]);
        case 5:return Float.toString(cmDesADResultKG[2]);
        case 6:return Float.toString(cmDesADResultKG[1]);
        //--
        case 7:return Float.toString(cmDesFRResultKG[3]);
        case 8:return Float.toString(cmDesFRResultKG[2]);
        case 9:return Float.toString(cmDesFRResultKG[1]);
        //--
        case 10:return Float.toString(cmDesAGResultKG[7]);
        case 11:return Float.toString(cmDesAGResultKG[6]);
        case 12:return Float.toString(cmDesAGResultKG[5]);
        case 13:return Float.toString(cmDesAGResultKG[4]);
        case 14:return Float.toString(cmDesAGResultKG[3]);
        case 15:return Float.toString(cmDesAGResultKG[2]);
        case 16:return Float.toString(cmDesAGResultKG[1]);
        //--
        case 17:return Float.toString(cmDesASResultKG[3]);
        case 18:return Float.toString(cmDesASResultKG[2]);
        case 19:return Float.toString(cmDesASResultKG[1]);
        //--
        case 20:return Float.toString(cmDesRCResultKG[3]);
        case 21:return Float.toString(cmDesRCResultKG[2]);
        case 22:return Float.toString(cmDesRCResultKG[1]);
        default:return "<?>";
      }//...?
    }//++>
  }//***
  
  private final List<McWeighRecord> cmLesRecord 
    = new ArrayList<McWeighRecord>(C_CAPACITY_SIZE);
  
  public final void ccLogRecord(
      int pxRecipe, float pxMixtrueTemp,
      float[] pxPacked
  ){
    float lpSum
      = pxPacked[ 0]+pxPacked[ 8]+pxPacked[ 12]
      + pxPacked[16]+pxPacked[20];
    cmLesRecord
      .add(new McWeighRecord(pxRecipe, pxMixtrueTemp, lpSum, pxPacked));
  }//+++
  
  //[todo]:: % cleardata
  //[todo]:: % exportdata
  
  //===
  
  @Override public int getColumnCount() {
    return O_LIST_OF_TABLE_COLUMN_TITLES.size();
  }//++>
  
  @Override public String getColumnName(int pxColumnIndex) {
    return O_LIST_OF_TABLE_COLUMN_TITLES.get(pxColumnIndex);
  }//++>
  
  @Override public int getRowCount() {
    return cmLesRecord.size();
  }//++>
  
  @Override public Object getValueAt(int pxRowIndex, int pxColumnIndex) {
    return cmLesRecord.get(pxRowIndex).ccGetColumnContent(pxColumnIndex);
  }//++>

}//***eof
