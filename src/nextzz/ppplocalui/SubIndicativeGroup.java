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

package nextzz.ppplocalui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kosui.ppplocalui.EcConst;
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcShape;
import kosui.ppplocalui.EiGroup;
import kosui.ppputil.VcLocalConsole;
import nextzz.pppmain.MainSketch;

public final class SubIndicativeGroup implements EiGroup{
  
  private static final SubIndicativeGroup SELF = new SubIndicativeGroup();
  public static SubIndicativeGroup ccRefer(){return SELF;}//+++

  //===
  
  public final EcElement cmSystemPopSW = new EcElement("&pop", 0x9999);
  public final EcElement cmRunningPL = new EcElement("Z");
  public final EcElement cmLinkageOKayPL = new EcElement("L");
  public final EcElement cmLinkageSendingPL = new EcElement("S");
  public final EcElement cmLinkageRecievingPL = new EcElement("R");
  
  private SubIndicativeGroup(){
    
    //-- switch
    cmSystemPopSW.ccSetW(64);
    cmSystemPopSW.ccSetH(VcLocalConsole.ccGetInstance().ccGetBarHeight());
    cmSystemPopSW.ccSetupColor(EcConst.C_LIT_GRAY, EcConst.C_GRAY);
    
    //-- led
    //-- led ** size
    cmRunningPL.ccSetH(cmSystemPopSW.ccGetH());
    cmLinkageOKayPL.ccSetH(cmSystemPopSW.ccGetH());
    cmLinkageSendingPL.ccSetH(cmSystemPopSW.ccGetH());
    cmLinkageRecievingPL.ccSetH(cmSystemPopSW.ccGetH());
    //-- led ** location
    cmSystemPopSW.ccSetLocation(0, 0);
    cmRunningPL.ccSetLocation(cmSystemPopSW, 1, 0);
    cmLinkageOKayPL.ccSetLocation(cmRunningPL, 1, 0);
    cmLinkageSendingPL.ccSetLocation(cmLinkageOKayPL, 1, 0);
    cmLinkageRecievingPL.ccSetLocation(cmLinkageSendingPL, 1, 0);
    
    //-- console
    VcLocalConsole.ccGetInstance().ccSetMessageBarColor
      (EcConst.ccAdjustColor(MainSketch.C_COLOR_BACKGROUD,0x20));
    VcLocalConsole.ccGetInstance().ccSetMessageBarAnchor
      (cmLinkageRecievingPL.ccEndX()+1, 0);
    VcLocalConsole.ccGetInstance().ccSetFieldBarAnchor(
      0,
      MainSketch.ccGetPApplet().height
        - VcLocalConsole.ccGetInstance().ccGetBarHeight()
    );
    
  }//..!

  @Override public List<? extends EcShape> ccGiveShapeList(){
    return new ArrayList<EcShape>();
  }//+++

  @Override public List<? extends EcElement> ccGiveElementList(){
    return Arrays.asList(
      cmSystemPopSW,
      cmRunningPL,cmLinkageOKayPL,cmLinkageSendingPL,cmLinkageRecievingPL
    );
  }//+++
  
 }//***eof
