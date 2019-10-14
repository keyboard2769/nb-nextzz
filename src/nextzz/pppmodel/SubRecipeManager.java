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
import java.util.HashMap;
import java.util.List;
import kosui.pppmodel.McTableAdapter;
import kosui.ppputil.VcTranslator;

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
    String cmName="--XXVII";
    int cmRecipeID = 0;
    float[] cmDesAGPercentage = new float[]{0f,0f,0f,0f, 0f,0f,0f,0f}; 
    float[] cmDesFRPercentage = new float[]{0f,0f,0f,0f}; 
    float[] cmDesASPercentage = new float[]{0f,0f,0f,0f}; 
    float[] cmDesRCPercentage = new float[]{0f,0f,0f,0f}; 
    float[] cmDesADPercentage = new float[]{0f,0f,0f,0f};
    final String ccGetColumnContent(int pxCount){
      return "<ny>";
    }//+++
  }//***
  
  private final McRecipe cmTheOneForThePane
    = new McRecipe();
  
  private final HashMap<Integer, McRecipe> cmMapOfRecipe
    = new HashMap<Integer, McRecipe>();
  
  //===
  
  public void ccInit(){
    
    //[head]:: % the sort thing
    
  }//++!
  
  //===
  
  //[todo]::setPanePercentage(char matt, int order, float val)
  //[todo]::setPaneID(int id)
  //[todo]::setPaneName(int id)
  //[todo]::applySelectedOne();
  //[todo]::registerAppliedOne();
  //[todo]::duplicateSelectedOne();
  //[todo]::deleteSelectedOne();
  
  //===
  
  //[todo]::syn getPercentage(int index,char matt,int order, float val)
  
  //===
  
  @Override public int getColumnCount() {
    return O_LIST_OF_TABLE_COLUMN_TITLES.size();
  }//++>
  
  @Override public String getColumnName(int pxColumnIndex) {
    return O_LIST_OF_TABLE_COLUMN_TITLES.get(pxColumnIndex);
  }//++>
  
  @Override public int getRowCount() {
    /* 6 */return 3;
  }//++>
  
  @Override public Object getValueAt(int pxRowIndex, int pxColumnIndex) {
    /* 6 */return "<?>";
  }//++>
  
}//***eof
