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

import java.io.File;
import kosui.pppmodel.McConst;
import kosui.ppputil.VcConst;
import nextzz.pppswingui.SubAssistantPane;

public final class MainFileManager {
  
  //=== font
  
  private static final String C_LOCAL_FONT_MY_MAC_XI
    = "/Users/keypad/Yard/_temp/nextZZ/font/GB18030Bitmap-12.vlw";
  private static final String C_TEXT_FONT_LOCATION_MY_MAC_XIII
    = "/Users/Keypad/Documents/Processing/CodeP/ResourceFontCreator/data/"
      + "GB18030Bitmap-12.vlw";
  private static final String C_TRUETYPE_FONT_MY_MI_XV
    = "D:\\_installer\\FONTS\\DFHEI5.TTC";
  private static final String C_TRUETYPE_FONT_MY_SONY_XI
    = "C:\\home\\_installer\\FONTS\\DFHEI5.TTC";
  
  //=== home
  
  private static final String C_ROOT_MY_MI_XV
    = "C:\\keypadhome\\develop\\nextzz";
  
  private static final String C_ROOT_MY_MAC_XIII
    = "/Users/Keypad/Yard/_temp/nextZZ";
  
  private static final String C_ROOT_MY_SONY_XI
    = "C:\\home\\_develop\\nextzz";
  
  //=== folder
  
  public static final String
    //-- sub folder
    C_SUB_BINARIES_FOLDER   = "bin",
    C_SUB_CONFIGS_FOLDER    = "cfg",
    C_SUB_DATAS_FOLDER      = "dat",
    C_SUB_FONTS_FOLDER      = "font",
    C_SUB_WEIGH_LOGS_FOLDER = "ke",
    C_SUB_TEMP_LOGS_FOLDER  = "trd",
    C_SUB_ERR_LOGS_FOLDER   = "err",
    //-- import file name
    //[todo]::..
    //-- export file name
    C_EXP_WEIGHING = "ke",
    C_EXP_TRD_V    = "vtrd",
    C_EXP_TRD_R    = "rtrd",
    //-- text extension
    C_TYPE_INI = ".ini",
    C_TYPE_CSV = ".csv"
  ;//...
  
  //===
  
  private static final MainFileManager SELF = new MainFileManager();
  public static MainFileManager ccRefer(){return SELF;}//+++
  private MainFileManager(){}//..!
  
  //===
  
  public final Runnable cmNotchStateInitiating = new Runnable() {
    @Override public void run() {
      
      SubAssistantPane.ccRefer().cmVCombustSourceNT.setSelectedIndex(1);
      
    }//+++
  };//***
  
  //===
  
  private boolean cmValidity=true;
  private File cmRootDirectory = null;
  private File cmFontFile = null;
  private File cmTrendExportDirectory = null;
  private File cmWeighExportDirectory = null;
  
  public final void ccInit(){
    
    cmValidity&=ssValidateRootLocation();
    cmValidity&=ssValidateFontFile();
    //[todo]::validate configfile
    //[todo]::validate ...
    
  }//++!
  
  private boolean ssValidateRootLocation(){
    
    //-- bubble
    String lpRes=null;
    String[] lpNeccesity=new String[]{
      C_SUB_BINARIES_FOLDER,
      C_SUB_CONFIGS_FOLDER,
      C_SUB_DATAS_FOLDER,
      C_SUB_FONTS_FOLDER,
      C_SUB_WEIGH_LOGS_FOLDER,
      C_SUB_TEMP_LOGS_FOLDER,
      C_SUB_ERR_LOGS_FOLDER
    };
    boolean lpValidity=false;
    for(String it:new String[]{
      VcConst.C_V_PWD,
      C_ROOT_MY_MI_XV,
      C_ROOT_MY_MAC_XIII,
      C_ROOT_MY_SONY_XI
    }){
      File lpSub=new File(it);
      lpValidity|=McConst.ccVerifyFolder(lpSub, lpNeccesity)==0;
      if(lpValidity){
        lpRes=it;
        break;
      }//..?
    }//..~
    if(!lpValidity){
      System.err.
        println("nextzz.pppmodel.MainFileManager.ssValidateRootLocation()::"
          + "fail_in_bubble");
      return false;
    }//..?
    
    //-- apply
    cmRootDirectory=new File(lpRes);
    cmTrendExportDirectory=new File(cmRootDirectory.getAbsolutePath()
     + VcConst.C_V_PATHSEP+C_SUB_TEMP_LOGS_FOLDER);
    cmWeighExportDirectory=new File(cmRootDirectory.getAbsolutePath()
     + VcConst.C_V_PATHSEP+C_SUB_WEIGH_LOGS_FOLDER);
    return true;
    
  }//+++
  
  private boolean ssValidateFontFile(){
    
    //-- bubble 
    boolean lpOK=false;
    for(String it:new String[]{
      C_LOCAL_FONT_MY_MAC_XI,
      C_TEXT_FONT_LOCATION_MY_MAC_XIII,
      C_TRUETYPE_FONT_MY_MI_XV,
      C_TRUETYPE_FONT_MY_SONY_XI
    }){
      cmFontFile=new File(it);
      lpOK=McConst.ccVerifyFileForLoading(cmFontFile)==0;
      if(lpOK){break;}
    }//..~
    
    //-- report
    if(!lpOK){
      /* 4 */VcConst.ccLogln("MainFileManager.ssValidateFontFile()::"
        +"failed to verify file for loading");
      cmFontFile=null;
      return false;
    }//..?
    /* 4 */VcConst.ccLogln("MainFileManager.ssValidateFontFile()::"
      +"loadted file",cmFontFile.getName());
    
    //-- length
    if(cmFontFile.length()>9999999l){
      /* 4 */VcConst.ccLogln("MainFileManager.ssValidateFontFile()::"
        +"refuse file larger than 9999kB");
      cmFontFile=null;
      return false; 
    }//..?
    
    //-- 
    /* 4 */VcConst.ccLogln("MainFileManager.ssValidateFontFile()::"
      +"file length",cmFontFile.length());
    return true;
    
  }//+++
  
  //===
  
  public final boolean ccIsValid(){
    return cmValidity;
  }//++>
  
  public final File ccGetFontFile(){
    return cmFontFile;
  }//++>
  
  public final File ccGetTrendExportDirectory(){
    return cmTrendExportDirectory;
  }//++>
  
  public final File ccGetWeighExportDirectory(){
    return cmWeighExportDirectory;
  }//+++>
  
}//***eof
