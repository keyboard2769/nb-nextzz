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

import kosui.ppputil.VcConst;

public final class MainSpecificator {
  
  private static final MainSpecificator SELF = new MainSpecificator();
  public static MainSpecificator ccRefer(){return SELF;}//+++

  //===
  
  public final int mnVFeederAmount;
  
  public final int mnAGCattegoryCount;
  public final int mnFRCattegoryCount;
  public final int mnASCattegoryCount;
  public final int mnRCCattegoryCount;
  public final int mnADCattegoryCount;
  
  public final int mnFillerSiloCount;
  public final boolean mnDustBinSeparated;
  public final boolean mnDustSiloExists;
  
  public final int mnMixtureSiloType;
  
  //===
  
  private boolean cmIsVerificated=false;
  
  private MainSpecificator(){
     
    //-- const 
      
    //-- const ** a
    mnAGCattegoryCount=ccGetValue("mnAGCattegoryCount", 6);
    mnFRCattegoryCount=ccGetValue("mnFRCattegoryCount", 2);
    mnASCattegoryCount=ccGetValue("mnASCattegoryCount", 1);
    mnRCCattegoryCount=ccGetValue("mnRCCattegoryCount", 0);
    mnADCattegoryCount=ccGetValue("mnADCattegoryCount", 0);

    //-- const ** v
    mnVFeederAmount=ccGetValue("mnVFeederAmount", 6);

    //-- const ** v ** f
    mnFillerSiloCount=ccGetValue("mnFillerSiloCount", 1);
    mnDustBinSeparated=ccGetValue("mnDustBinSeparated", false);
    mnDustSiloExists=ccGetValue("mnDustSiloExists", true);

    //-- s
    //.. [ 0 ]none
    //.. [ 1 ]beside tower
    //.. [ 2 ]none
    //.. [ 3 ]below mixer
    /* 7 */mnMixtureSiloType = ccGetValue("mnMixtureSiloType", 0);
    
    //-- const ** r
    
    //-- var
    
    //-- vat ** ct
    
    //-- vat ** a 
    
    //-- vat ** v
    
  }//+++!
  
  //===
  
  private int ccGetValue(String pxKey,int pxOrDefault){
    //[notyet]::
    return pxOrDefault;
  }//+++
  
  private boolean ccGetValue(String pxKey, boolean pxOrDeault){
    //[notyet]::
    return pxOrDeault;
  }//+++
  
  //===
  
  public final boolean ccVerify(){
    //[notyet]::
    cmIsVerificated=(false);
    VcConst.ccPrintln("MainSpecification.%file loading%::not yet");
    return cmIsVerificated;
  }//+++
  
  //===
  
  public final boolean ccNeedsExtendsCurrentSlot(){
    return
         mnRCCattegoryCount>0
      || mnMixtureSiloType==1
      || mnMixtureSiloType==3;
  }//+++
  
 }//***eof
