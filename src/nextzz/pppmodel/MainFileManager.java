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
  
  private String cmRootLocation=null;
  
  private static final String C_ROOT_MY_MI_XV
    = "C:\\keypadhome\\develop\\nextzz";
  
  //=== folder
  
  public static final String
    //-- sub folder
    C_SUB_BINARIES_FOLDER = "bin",
    C_SUB_CONFIGS_FOLDER = "cfg",
    C_SUB_DATAS_FOLDER = "cfg",
    C_SUB_FONTS_FOLDER = "font",
    C_SUB_WEIGH_LOGS_FOLDER = "ke",
    C_SUB_TEMP_LOGS_FOLDER = "trd",
    C_SUB_ERR_LOGS_FOLDER = "err"
    //-- file name
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
  
  private File cmFontFile=null;
  
  public final void ccInit(){
    
    //[todo]::validate root folder
    ssValidateFontFile();
    //[todo]::validate configfile
    //[todo]::validate ...
    
  }//++!
  
  private void ssValidateRootLocation(){
    
    //[head]:: now what??
  
  }//+++
  
  private void ssValidateFontFile(){
    
    //--
    boolean lpOK=false;
    for(String it:new String[]{
      C_LOCAL_FONT_MY_MAC_XI,
      C_TEXT_FONT_LOCATION_MY_MAC_XIII,
      C_TRUETYPE_FONT_MY_MI_XV,
      C_TRUETYPE_FONT_MY_SONY_XI
    }){
      cmFontFile=new File(it);
      lpOK=McConst.ccVerifyFileForLoading(cmFontFile);
      if(lpOK){break;}
    }//..~
    
    //--
    if(lpOK){
      /* 4 */VcConst.ccPrintln("MainFileManager.ssValidateFontFile()::"
        +"loadted file",cmFontFile.getName());
    }else{
      /* 4 */VcConst.ccPrintln("MainFileManager.ssValidateFontFile()::"
        +"failed to verify file for loading");
      cmFontFile=null;
      return; 
    }//..!
    
    //--
    if(cmFontFile.length()<=9999999l){
      /* 4 */VcConst.ccPrintln("MainFileManager.ssValidateFontFile()::"
        +"file length",cmFontFile.length());
    }else{
      /* 4 */VcConst.ccPrintln("MainFileManager.ssValidateFontFile()::"
        +"refuse file larger than 9999kB");
      cmFontFile=null;
      return; 
    }//..?
    
  }//+++
  
  //===
  
  public final File ccGetFontFile(){return cmFontFile;}//++>
  
 }//***eof
