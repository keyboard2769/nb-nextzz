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

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import kosui.ppplocalui.EcConst;
import kosui.pppmodel.MiPixillatable;
import kosui.pppswingui.ScConst;
import kosui.pppswingui.ScIcon;
import kosui.ppputil.VcTranslator;
import nextzz.pppmain.MainActionManager;

public final class ConstSwingUI {
  
  public static final ScIcon O_WINDOW_ICON = new ScIcon();
  
  public static final Runnable O_IMPOSSIBLE_WARNINGNESS = new Runnable() {
    @Override public void run() {
      ScConst.ccErrorBox(VcTranslator.tr("_m_unreachable_error"));
    }//+++
  };//***
  
  public static final Runnable O_WEIGH_UNREADY_WARNINGNESS = new Runnable() {
    @Override public void run() {
      ScConst.ccErrorBox(VcTranslator.tr("_m_auto_weigh_not_ready"));
    }//+++
  };//***
  
  public static final Runnable O_TREND_EMPTY_WARNINGNESS = new Runnable() {
    @Override public void run() {
      ScConst.ccErrorBox(VcTranslator.tr("_m_no_trend_data"));
    }//+++
  };//***
  
  //===
  
  public static void ccAddAssistant(JPanel pxPane, JComponent pxSwitch){
    assert pxPane!=null;
    assert pxSwitch!=null;
    pxPane.add(pxSwitch);
    if(pxSwitch instanceof JComboBox){
      ((JComboBox)pxSwitch).addActionListener
        (MainActionManager.ccRefer().cmNotchListener);
    }else 
    if(pxSwitch instanceof JToggleButton){
      ((JToggleButton)pxSwitch).addActionListener
        (MainActionManager.ccRefer().cmNotchListener);
    }else{
      throw new RuntimeException
        (".ssAddAssistant():you are not suppsed to do this");
    }//...?
  }//+++
  
  //===
  
  static public final void ccInit(){
    
    O_WINDOW_ICON.ccFillPixel(EcConst.C_LIT_GRAY, new MiPixillatable() {
      @Override public boolean ccPixillate(int pxX, int pxY) {
        return true;
      }//+++
    });
    O_WINDOW_ICON.ccFillPixel(EcConst.C_DIM_GREEN, new MiPixillatable() {
      @Override public boolean ccPixillate(int pxX, int pxY) {
        return pxX<pxY;
      }//+++
    });
    O_WINDOW_ICON.ccFillPixel(EcConst.C_BLACK, new MiPixillatable() {
      private final int cmBorderThick=3;
      @Override public boolean ccPixillate(int pxX, int pxY) {
        return pxX<cmBorderThick || pxX>(ScIcon.C_SCALE-cmBorderThick)
             ||pxY<cmBorderThick || pxY>(ScIcon.C_SCALE-cmBorderThick);
      }//+++
    });
    
  }//..!
  
 }//***eof
