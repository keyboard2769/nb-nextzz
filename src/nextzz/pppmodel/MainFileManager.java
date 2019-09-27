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
import kosui.pppswingui.ScConst;
import kosui.ppputil.VcConst;

public final class MainFileManager {
  
  private static final String C_LOCAL_FONT_MY_MAC_XI
    = "/Users/keypad/Yard/_temp/nextZZ/font/GB18030Bitmap-12.vlw";
  private static final String C_TEXT_FONT_LOCATION_MY_MAC_XIII
    = "/Users/Keypad/Documents/Processing/CodeP/ResourceFontCreator/data/"
      + "MDSChGothic16-11.vlw";
  private static final String C_LOCAL_FONT_MY_MI_XV
    = "??!!?!!";
  private static final String C_TRUETYPE_FONT_MY_MI_XV
    = "D:\\_installer\\FONTS\\DFHEI5.TTC";
  private static final String C_TRUETYPE_FONT_MY_SONY_XI
    = "C:\\home\\_installer\\FONTS\\DFHEI5.TTC";
  
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
  
  private static final MainFileManager SELF = new MainFileManager();
  public static MainFileManager ccRefer(){return SELF;}//+++
  private MainFileManager(){}//..!
  
  //===
  
  private File cmFontFile=null;
  
  public final void ccInit(){
    
    ccVelidateFontFile();
    
  }//..!
  
  private void ccVelidateFontFile(){
    
    boolean lpOK;
    
    cmFontFile = new File(C_LOCAL_FONT_MY_MAC_XI);
    lpOK=McConst.ccVerifyFileForLoading(cmFontFile,"vlw",9999999l);
    if(lpOK){
      VcConst.ccPrintln("located-font-file", cmFontFile.getName());
      VcConst.ccPrintln("length", cmFontFile.length());
      return;
    }//..?
    
    cmFontFile = new File(C_TEXT_FONT_LOCATION_MY_MAC_XIII);
    lpOK=McConst.ccVerifyFileForLoading(cmFontFile,"vlw",9999999l);
    if(lpOK){
      VcConst.ccPrintln("located-font-file", cmFontFile.getName());
      VcConst.ccPrintln("length", cmFontFile.length());
      return;
    }//..?
    
    cmFontFile = new File(C_LOCAL_FONT_MY_MI_XV);
    lpOK=McConst.ccVerifyFileForLoading(cmFontFile,"vlw",9999999l);
    if(lpOK){
      VcConst.ccPrintln("located-font-file", cmFontFile.getName());
      VcConst.ccPrintln("length", cmFontFile.length());
      return;
    }//..?
    
    cmFontFile = new File(C_TRUETYPE_FONT_MY_MI_XV);
    lpOK=McConst.ccVerifyFileForLoading(cmFontFile);
    if(lpOK){
      VcConst.ccPrintln("located-font-file", cmFontFile.getName());
      VcConst.ccPrintln("length", cmFontFile.length());
      return;
    }//..?
    
    cmFontFile = new File(C_TRUETYPE_FONT_MY_SONY_XI);
    lpOK=McConst.ccVerifyFileForLoading(cmFontFile);
    if(lpOK){
      VcConst.ccPrintln("located-font-file", cmFontFile.getName());
      VcConst.ccPrintln("length", cmFontFile.length());
      return;
    }//..?
    
    cmFontFile=null;
    ScConst.ccMessage("failed to locate font file");
    
  }//+++
  
  public final File ccGetFontFile(){return cmFontFile;}
  
 }//***eof
