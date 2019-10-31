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

package nextzz.pppswingui;

import java.awt.Font;
import java.awt.print.PrinterException;
import java.util.List;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import kosui.ppplogic.ZcRangedModel;
import kosui.pppmodel.MiExecutable;
import kosui.ppputil.VcConst;

public class ScCSVFilePrinter extends SwingWorker<Integer, Void>{
  
  public static final int C_A4PAPER_W_MM = 210;
  public static final int C_A4PAPER_H_MM = 297;
  
  public static final int
    C_R_EXCEPITON     =-1,
    C_R_SUCCESS       = 0,
    C_R_NULL_LIST     = 1,
    C_R_EMPTY_LIST    = 2
  ;//,,,
  
  private final int cmTextSize;
  private final int cmTabSize;
  private final int cmMariginMM;
  private final List<String> cmTextList;
  private final MiExecutable cmReporting;

  public ScCSVFilePrinter(
    List<String> pxContent,
    int pxTextSize, int pxTabSize, int pxMarginMM,
    MiExecutable pxReporting
  ){
    cmTextList = pxContent;
    cmReporting = pxReporting;
    cmTextSize = ZcRangedModel.ccLimitInclude(pxTextSize, 6, 22);//..arbitrary
    cmTabSize = ZcRangedModel.ccLimitInclude(pxTabSize, 2, 16);//..arbitrary
    cmMariginMM = ZcRangedModel.ccLimitInclude(pxMarginMM, 2, 20);//..arbitrary
  }//!!!
  
  //===

  @Override protected Integer doInBackground() throws Exception {  
    
    //-- checkin 
    if(cmTextList==null){return C_R_NULL_LIST;}
    if(cmTextList.isEmpty()){return C_R_EMPTY_LIST;}
    
    //-- packup
    JTextArea lpArea = new JTextArea(""+VcConst.C_V_NEWLINE+"");
    lpArea.setTabSize(cmTabSize);
    lpArea.setFont(new Font(Font.DIALOG, Font.PLAIN, cmTextSize));
    for(String it : cmTextList){
      lpArea.append(it.replaceAll(",", "\t"));
      lpArea.append(VcConst.C_V_NEWLINE);
    }//..~
    
    //-- sendto
    HashPrintRequestAttributeSet lpSetting = new HashPrintRequestAttributeSet();
    lpSetting.add(new MediaPrintableArea(
      (float)cmMariginMM, (float)cmMariginMM,
      (float)(C_A4PAPER_W_MM-cmMariginMM), (float)(C_A4PAPER_H_MM-cmMariginMM),
      MediaPrintableArea.MM)
    );
    try {
      lpArea.print(null, null, true, null, lpSetting, true);
    } catch (PrinterException e) {
      System.err.println("ScCSVFilePrinter::"+e.getMessage());
      return C_R_EXCEPITON;
    }//..?
    
    //-- post
    return C_R_SUCCESS;
    
  }//+++

  @Override protected void done() {
    int lpCode;
    try {
      lpCode = get();
    } catch (Exception e) {
      lpCode = C_R_EXCEPITON;
    }//..?
    if(cmReporting!=null){
      cmReporting.ccExecute(Integer.toString(lpCode));
    }else{
      System.out.println("ScCSVFilePrinter::done_with:"
        + Integer.toString(lpCode));
    }//..?
  }//+++
  
}//***eof
