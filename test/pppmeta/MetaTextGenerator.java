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

package pppmeta;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import javax.swing.SwingUtilities;
import kosui.pppmodel.McConst;
import kosui.pppswingui.ScConst;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcStampUtility;
import nextzz.pppmodel.SubAnalogScalarManager;
import nextzz.pppsetting.McAbstractSettingPartition;
import nextzz.pppsetting.SubTemperatureSetting;
import processing.core.PApplet;

public final class MetaTextGenerator {
  
  private static String ssProcess(String pxTranslationKey){
    
    //-- check in
    if(pxTranslationKey==null){return "<null>";}
    if(pxTranslationKey.isEmpty()){return "</>";}
    String lpKey,lpEn;
    
    //-- replace ** key
    lpKey=pxTranslationKey.replaceAll("\\[.+\\]", "");
    
    //-- replace ** en
    lpEn=lpKey.replaceAll("^_", "");
    lpEn=lpEn.replaceAll("_", "<bk>");
    StringBuilder lpEnBuf = new StringBuilder(lpEn);
    lpEnBuf.replace(0, 1, lpEn.substring(0, 1).toUpperCase());
    
    //-- post
    return lpKey+","+lpEnBuf.toString();
    
  }//+++
  
  private static final Runnable O_SW_SETTING_KEYCONTENT_G = new Runnable() {
    @Override public void run() {
      
      //-- let select
      ScConst.ccApplyLookAndFeel(4, false);
      ScConst.ccSetFileChooserSelectedFile(
        VcConst.C_V_PWD+VcConst.C_V_PATHSEP
        +"so"+VcStampUtility.ccFileNameTypeVI()+".txt"
      );
      ScConst.ccSetFileChooserButtonText("Export");
      File lpOutputFile = ScConst.ccGetFileByFileChooser('f');
      boolean lpVerify = McConst.ccVerifyFileForSaving(lpOutputFile)==0;
      if(!lpVerify){ssExit(-1);}
      VcConst.ccPrintln("accepted", lpOutputFile.getAbsolutePath());
      
      //-- re-direct
      SubAnalogScalarManager.ccRefer().ccInit();//..[note]::delete later
      McAbstractSettingPartition lpBUF = SubTemperatureSetting.ccRefer();
      lpBUF.ccInit();
      
      //-- pack
      int lpRowMax = lpBUF.getRowCount();
      VcConst.ccPrintln("RowCount", lpRowMax);
      List<String> lpLesContent = new LinkedList<String>();
      for(int i=0;i<lpRowMax;i++){
        lpLesContent.add(
          ssProcess(lpBUF.getValueAt(i, 1).toString())
        );
      }//..~
      lpLesContent.add("<eof>");
      
      //-- write
      PApplet.saveStrings(lpOutputFile, lpLesContent.toArray(new String[]{}));
      
      //-- exit
      ssExit(0);
      
    }//+++
  };//***
  
  private static void ssExit(int lpCode){
    System.err.println("pppmeta.MetaTextGenerator.ssExit::with:"
      +Integer.toString(lpCode));
    System.exit(lpCode);
  }//+++
  
  public static void main(String[] args) {
    
    //-- enter
    System.out.println("pppmeta.MetaTextGenerator.main()::enter");
    
    //-- loop
    SwingUtilities.invokeLater(O_SW_SETTING_KEYCONTENT_G);
    
    //-- exit
    System.out.println("pppmeta.MetaTextGenerator.main()::exit");
    
  }//!!!
  
}//**eof
