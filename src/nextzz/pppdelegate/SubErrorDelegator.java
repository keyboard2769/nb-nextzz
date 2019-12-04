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
package nextzz.pppdelegate;

import kosui.ppputil.VcLocalConsole;
import nextzz.ppplocalui.SubIndicativeGroup;
import nextzz.pppmodel.MainPlantModel;
import nextzz.pppmodel.SubErrorListModel;

public final class SubErrorDelegator {
  
  public static volatile int mnMessageCode;
  
  public static volatile boolean
    //-- system
    mnErrorPL,mnErrorClearSW,
    //-- error
    mnErrorBitZ,mnErrorBitI,mnErrorBitII,mnErrorBitIII,
    mnErrorBitIV,mnErrorBitV,mnErrorBitVI,mnErrorBitVII,
    mnErrorBitVIII,mnErrorBitIX,mnErrorBitX,mnErrorBitXI,
    mnErrorBitXII,mnErrorBitXIII,mnErrorBitXIV,mnErrorBitXV,
    //--
    mnErrorBitXVI,mnErrorBitXVII,mnErrorBitXVIII,mnErrorBitXIX,
    mnErrorBitXX,mnErrorBitXXI,mnErrorBitXXII,mnErrorBitXXIII,
    mnErrorBitXXIV,mnErrorBitXXV,mnErrorBitXXVI,mnErrorBitXXVII,
    mnErrorBitXXVIII,mnErrorBitXXIX,mnErrorBitXXX,mnErrorBitXXXI,
    //-- warn
    mnWarnBitZ,mnWarnBitI,mnWarnBitII,mnWarnBitIII,
    mnWarnBitIV,mnWarnBitV,mnWarnBitVI,mnWarnBitVII,
    mnWarnBitVIII,mnWarnBitIX,mnWarnBitX,mnWarnBitXI,
    mnWarnBitXII,mnWarnBitXIII,mnWarnBitXIV,mnWarnBitXV,
    //--
    mnWarnBitXVI,mnWarnBitXVII,mnWarnBitXVIII,mnWarnBitXIX,
    mnWarnBitXX,mnWarnBitXXI,mnWarnBitXXII,mnWarnBitXXIII,
    mnWarnBitXXIV,mnWarnBitXXV,mnWarnBitXXVI,mnWarnBitXXVII,
    mnWarnBitXXVIII,mnWarnBitXXIX,mnWarnBitXXX,mnWarnBitXXXI
  ;//,,,
  
  public static final void ccWire(){
    //[notyet]::
  }//++~
  
  public static final void ccBind(){
    
    //-- pc to plc
    mnErrorClearSW=MainPlantModel.ccRefer().cmErrorClearHoldingFLG;
    
    //-- plc to pc
    SubIndicativeGroup.ccRefer().cmErrorMessagePL.ccSetIsActivated(mnErrorPL);
    VcLocalConsole.ccSetMessageBarText(SubErrorListModel.ccRefer()
      .ccGetMessage(mnMessageCode));
    
  }//++~
  
  //===
  
  public static final void ccSetErrorBit(int pxOrder, boolean pxVal){
    switch(pxOrder){
      case  0:mnErrorBitZ=pxVal;break;
      case  1:mnErrorBitI=pxVal;break;
      case  2:mnErrorBitII=pxVal;break;
      case  3:mnErrorBitIII=pxVal;break;
      case  4:mnErrorBitIV=pxVal;break;
      case  5:mnErrorBitV=pxVal;break;
      case  6:mnErrorBitVI=pxVal;break;
      case  7:mnErrorBitVII=pxVal;break;
      //--
      case  8:mnErrorBitVIII=pxVal;break;
      case  9:mnErrorBitIX=pxVal;break;
      case 10:mnErrorBitX=pxVal;break;
      case 11:mnErrorBitXI=pxVal;break;
      case 12:mnErrorBitXII=pxVal;break;
      case 13:mnErrorBitXIII=pxVal;break;
      case 14:mnErrorBitXIV=pxVal;break;
      case 15:mnErrorBitXV=pxVal;break;
      //-- **
      case 16:mnErrorBitXVI=pxVal;break;
      case 17:mnErrorBitXVII=pxVal;break;
      case 18:mnErrorBitXVIII=pxVal;break;
      case 19:mnErrorBitXIX=pxVal;break;
      case 20:mnErrorBitXX=pxVal;break;
      case 21:mnErrorBitXXI=pxVal;break;
      case 22:mnErrorBitXXII=pxVal;break;
      case 23:mnErrorBitXXIII=pxVal;break;
      //--
      case 24:mnErrorBitXXIV=pxVal;break;
      case 25:mnErrorBitXXV=pxVal;break;
      case 26:mnErrorBitXXVI=pxVal;break;
      case 27:mnErrorBitXXVII=pxVal;break;
      case 28:mnErrorBitXXVIII=pxVal;break;
      case 29:mnErrorBitXXIX=pxVal;break;
      case 30:mnErrorBitXXX=pxVal;break;
      case 31:mnErrorBitXXXI=pxVal;break;
      //--
      default:break;
    }//..?
  }//++<
  
  public static final boolean ccGetErrorBit(int pxOrder){
    switch(pxOrder){
      case  0:return mnErrorBitZ;
      case  1:return mnErrorBitI;
      case  2:return mnErrorBitII;
      case  3:return mnErrorBitIII;
      case  4:return mnErrorBitIV;
      case  5:return mnErrorBitV;
      case  6:return mnErrorBitVI;
      case  7:return mnErrorBitVII;
      //--
      case  8:return mnErrorBitVIII;
      case  9:return mnErrorBitIX;
      case 10:return mnErrorBitX;
      case 11:return mnErrorBitXI;
      case 12:return mnErrorBitXII;
      case 13:return mnErrorBitXIII;
      case 14:return mnErrorBitXIV;
      case 15:return mnErrorBitXV;
      //-- **
      case 16:return mnErrorBitXVI;
      case 17:return mnErrorBitXVII;
      case 18:return mnErrorBitXVIII;
      case 19:return mnErrorBitXIX;
      case 20:return mnErrorBitXX;
      case 21:return mnErrorBitXXI;
      case 22:return mnErrorBitXXII;
      case 23:return mnErrorBitXXIII;
      //--
      case 24:return mnErrorBitXXIV;
      case 25:return mnErrorBitXXV;
      case 26:return mnErrorBitXXVI;
      case 27:return mnErrorBitXXVII;
      case 28:return mnErrorBitXXVIII;
      case 29:return mnErrorBitXXIX;
      case 30:return mnErrorBitXXX;
      case 31:return mnErrorBitXXXI;
      //--
      default:return false;
    }//..?
  }//++>
  
  //===
  
  public static final void ccSetWarnBit(int pxOrder, boolean pxVal){
    switch(pxOrder){
      case  0:mnWarnBitZ=pxVal;break;
      case  1:mnWarnBitI=pxVal;break;
      case  2:mnWarnBitII=pxVal;break;
      case  3:mnWarnBitIII=pxVal;break;
      case  4:mnWarnBitIV=pxVal;break;
      case  5:mnWarnBitV=pxVal;break;
      case  6:mnWarnBitVI=pxVal;break;
      case  7:mnWarnBitVII=pxVal;break;
      //--
      case  8:mnWarnBitVIII=pxVal;break;
      case  9:mnWarnBitIX=pxVal;break;
      case 10:mnWarnBitX=pxVal;break;
      case 11:mnWarnBitXI=pxVal;break;
      case 12:mnWarnBitXII=pxVal;break;
      case 13:mnWarnBitXIII=pxVal;break;
      case 14:mnWarnBitXIV=pxVal;break;
      case 15:mnWarnBitXV=pxVal;break;
      //-- **
      case 16:mnWarnBitXVI=pxVal;break;
      case 17:mnWarnBitXVII=pxVal;break;
      case 18:mnWarnBitXVIII=pxVal;break;
      case 19:mnWarnBitXIX=pxVal;break;
      case 20:mnWarnBitXX=pxVal;break;
      case 21:mnWarnBitXXI=pxVal;break;
      case 22:mnWarnBitXXII=pxVal;break;
      case 23:mnWarnBitXXIII=pxVal;break;
      //--
      case 24:mnWarnBitXXIV=pxVal;break;
      case 25:mnWarnBitXXV=pxVal;break;
      case 26:mnWarnBitXXVI=pxVal;break;
      case 27:mnWarnBitXXVII=pxVal;break;
      case 28:mnWarnBitXXVIII=pxVal;break;
      case 29:mnWarnBitXXIX=pxVal;break;
      case 30:mnWarnBitXXX=pxVal;break;
      case 31:mnWarnBitXXXI=pxVal;break;
      //--
      default:break;
    }//..?
  }//++<
  
  public static final boolean ccGetWarnBit(int pxOrder){
    switch(pxOrder){
      case  0:return mnWarnBitZ;
      case  1:return mnWarnBitI;
      case  2:return mnWarnBitII;
      case  3:return mnWarnBitIII;
      case  4:return mnWarnBitIV;
      case  5:return mnWarnBitV;
      case  6:return mnWarnBitVI;
      case  7:return mnWarnBitVII;
      //--
      case  8:return mnWarnBitVIII;
      case  9:return mnWarnBitIX;
      case 10:return mnWarnBitX;
      case 11:return mnWarnBitXI;
      case 12:return mnWarnBitXII;
      case 13:return mnWarnBitXIII;
      case 14:return mnWarnBitXIV;
      case 15:return mnWarnBitXV;
      //-- **
      case 16:return mnWarnBitXVI;
      case 17:return mnWarnBitXVII;
      case 18:return mnWarnBitXVIII;
      case 19:return mnWarnBitXIX;
      case 20:return mnWarnBitXX;
      case 21:return mnWarnBitXXI;
      case 22:return mnWarnBitXXII;
      case 23:return mnWarnBitXXIII;
      //--
      case 24:return mnWarnBitXXIV;
      case 25:return mnWarnBitXXV;
      case 26:return mnWarnBitXXVI;
      case 27:return mnWarnBitXXVII;
      case 28:return mnWarnBitXXVIII;
      case 29:return mnWarnBitXXIX;
      case 30:return mnWarnBitXXX;
      case 31:return mnWarnBitXXXI;
      //--
      default:return false;
    }//..?
  }//++>
  
}//***eof
