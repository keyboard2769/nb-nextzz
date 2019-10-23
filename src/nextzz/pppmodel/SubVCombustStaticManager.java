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

public final class SubVCombustStaticManager extends McTableAdapter{
  
  public static final int C_CAPACITY_SIZE = 32;//.. supposedly 512 on the roll
  public static final int C_CAPACITY_MASK = 31;//.. supposedly 511 on the roll

  private static final SubVCombustStaticManager SELF
    = new SubVCombustStaticManager();
  public static final SubVCombustStaticManager ccRefer(){return SELF;}//+++
  private SubVCombustStaticManager(){}//++!
  
  //===
  
  private static final List<String> O_LIST_OF_TABLE_COLUMN_TITLES
   = Collections.unmodifiableList(Arrays.asList(
    //--
    VcTranslator.tr("_col_time"),//..0
    //--
    VcTranslator.tr("_col_chute_temp"),   //..1
    VcTranslator.tr("_col_entrance_temp"),//..2
    VcTranslator.tr("_col_aspipe_temp"),  //..3
    VcTranslator.tr("_col_sand_temp"),    //..4
    //--
    VcTranslator.tr("_col_ve_degree"),//..5
    VcTranslator.tr("_col_vb_degree") //..6
  ));//,,,
  
  //===
  
  private final class McVCombustRecord{
    String cmTimeStamp="-- ? MXV";
    int cmVEDegree = 0;
    int cmVBDegree = 0;
    int[] cmDesVThermo = new int[]{0,0,0,0, 0,0,0,0};
    McVCombustRecord(){}//++!
    McVCombustRecord(
      int pxVE, int pxVB,
      int pxTHI,int pxTHII, int pxTHIII, int pxTHIV
    ){
      this();
      ccSetupRecord(pxVE, pxVB, pxTHI, pxTHII, pxTHIII, pxTHIV);
    }//++!
    McVCombustRecord(int pxVE, int pxVB, int[] pxPacked){
      this();
      ccSetupRecord(pxVE, pxVB, pxPacked);
    }//++!
    final void ccSetupRecord(int pxVE, int pxVB, int[] pxPacked){
      if(pxPacked==null){return;}
      if(pxPacked.length!=4){return;}
      ccSetupRecord(
        pxVE, pxVB,
        pxPacked[0], pxPacked[1], pxPacked[2], pxPacked[3]
      );
    }//++<
    final void ccSetupRecord(
      int pxVE, int pxVB,
      int pxTHI,int pxTHII, int pxTHIII, int pxTHIV
    ){
      cmTimeStamp=VcStampUtility.ccDataLogTypeI();
      cmVEDegree=pxVE;
      cmVBDegree=pxVB;
      cmDesVThermo[1]=pxTHI;
      cmDesVThermo[2]=pxTHII;
      cmDesVThermo[3]=pxTHIII;
      cmDesVThermo[4]=pxTHIV;
    }//++<
    final String ccGetColumnContent(int pxCount){
      switch (pxCount) {
        case 0:return cmTimeStamp;
        //--
        case 1:return Integer.toString(cmDesVThermo[1]);
        case 2:return Integer.toString(cmDesVThermo[2]);
        case 3:return Integer.toString(cmDesVThermo[3]);
        case 4:return Integer.toString(cmDesVThermo[4]);
        //--
        case 5:return Integer.toString(cmVEDegree);
        case 6:return Integer.toString(cmVBDegree);
        default:return "<?>";
      }//...?
    }//++>
  }//***
  
  private final List<McVCombustRecord> cmListOfRecord
    = new ArrayList<McVCombustRecord>(C_CAPACITY_SIZE);
  
  public final void ccLogRecord(
    int pxVE, int pxVB,
    int pxTHI,int pxTHII, int pxTHIII, int pxTHIV
  ){
    if(cmListOfRecord.size()>=C_CAPACITY_MASK){
      ssClearData();
      ssExportData();
    }//..?
    cmListOfRecord.add(new McVCombustRecord(
      pxVE, pxVB,
      pxTHI, pxTHII, pxTHIII, pxTHIV
    ));
  }//+++
  
  private void ssClearData(){
    cmListOfRecord.clear();
  }//+++
  
  private void ssExportData(){
    System.err.println(".SubVCombustStaticManager.ssExportData()::not_yet!");
  }//+++
  
  //===
  
  @Override public int getColumnCount() {
    return O_LIST_OF_TABLE_COLUMN_TITLES.size();
  }//++>
  
  @Override public String getColumnName(int pxColumnIndex) {
    return O_LIST_OF_TABLE_COLUMN_TITLES.get(pxColumnIndex);
  }//++>
  
  @Override public int getRowCount() {
    return cmListOfRecord.size();
  }//++>
  
  @Override public Object getValueAt(int pxRowIndex, int pxColumnIndex) {
    return cmListOfRecord.get(pxRowIndex).ccGetColumnContent(pxColumnIndex);
  }//++>
  
 }//***eof
