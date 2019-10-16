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

import nextzz.ppplocalui.SubMixerGroup;
import nextzz.ppplocalui.SubOperativeGroup;
import nextzz.ppplocalui.SubWeigherGroup;
import nextzz.pppmodel.MainPlantModel;

public final class SubWeighingDelegator {
  
  public static volatile int
    mnAGWeighLevel,        mnFRWeighLevel,        mnASWeighLevel,
    mnAGWeighLevelTargetAD,mnFRWeighLevelTargetAD,mnASWeighLevelTargetAD,
    mnAGWeighLevelLeadAD,  mnFRWeighLevelLeadAD,  mnASWeighLevelLeadAD
  ;//,,,
  
  public static volatile boolean
    
    //-- weight control
    mnAutoWeighing,
    //[todo]:: ?? manual? auto? ready? run?
    
    //-- AG
    //-- AG ** flag
    mnAGWeighRequest,mnAGDischargeRequest,
    mnAGWeighConfirm,mnAGDischargeConfirm,
    //-- AG ** lock sw
    mnAGCellLockSW,
    mnAGxSWnI,mnAGxSWnII,mnAGxSWnIII,
    mnAGxSWnIV,mnAGxSWnV,mnAGxSWnVI,mnAGxSWnVII,
    //-- AG ** weigh sw
    mnAGCellDischargeSW,
    mnAGwSWnI,mnAGwSWnII,mnAGwSWnIII,
    mnAGwSWnIV,mnAGwSWnV,mnAGwSWnVI,mnAGwSWnVII,
    //-- AG ** weigh pl
    mnAGCellDischargePL,
    mnAGwPLnI,mnAGwPLnII,mnAGwPLnIII,
    mnAGwPLnIV,mnAGwPLnV,mnAGwPLnVI,mnAGwPLnVII,
    
    //-- FR
    //-- FR ** flag
    mnFRWeighRequest,mnFRDischargeRequest,
    mnFRWeighConfirm,mnFRDischargeConfirm,
    //-- FR ** lock sw
    mnFRCellLockSW,
    mnFRxSWnI,mnFRxSWnII,mnFRxSWnIII,
    //-- FR ** weigh sw
    mnFRCellDischargeSW,
    mnFRwSWnI,mnFRwSWnII,mnFRwSWnIII,
    //-- FR ** weigh pl
    mnFRCellDischargePL,
    mnFRwPLnI,mnFRwPLnII,mnFRwPLnIII,
    
    //-- AS
    //-- AS ** flag
    mnASWeighRequest,mnASDischargeRequest,
    mnASWeighConfirm,mnASDischargeConfirm,
    //-- AS ** lock sw
    mnASCellLockSW,
    mnASxSWnI,mnASxSWnII,mnASxSWnIII,
    //-- AS ** weigh sw
    mnASCellDischargeSW,
    mnASwSWnI,mnASwSWnII,mnASwSWnIII,
    //-- AS ** weigh pl
    mnASCellDischargePL,
    mnASwPLnI,mnASwPLnII,mnASwPLnIII,
    
    //[todo]::rc
    //[todo]::ad
    
    //--- Mixer
    mnMixerDischargedConfirm,mnMixerAutoDischargeRequire,
    mnMixerGateHoldSW,mnMixerGateOpenSW,
    mnMixerHasContentPL,
    mnMixerGateFB,mnMixerGateClosedPL
    
  ;//,,,
  
  //===
  
  public static final void ccWire(){
    /* 6 */throw new RuntimeException("NOT YET!!");
  }//++~
  
  public static final void ccBind(){
    
    //-- aggregate
    mnAGCellDischargeSW=SubWeigherGroup.ccRefer()
      .cmLesAGWeighSW.get(0).ccIsMousePressed();
    SubWeigherGroup.ccRefer().cmLesAGWeighSW.get(0)
      .ccSetIsActivated(mnAGCellDischargePL);
    SubWeigherGroup.ccRefer().cmLesAGLockSW.get(0)
      .ccSetIsActivated(mnAGCellLockSW);
    for(int i=MainPlantModel.C_MATT_AGGR_UI_VALID_HEAD;
      i<=MainPlantModel.C_MATT_AGGR_UI_VALID_MAX;i++
    ){
      ccSetAGGateSW(i, SubWeigherGroup.ccRefer().cmLesAGWeighSW
        .get(i).ccIsMousePressed());
      SubWeigherGroup.ccRefer().cmLesAGWeighSW.get(i)
        .ccSetIsActivated(ccGetAGGatePL(i));
      SubWeigherGroup.ccRefer().cmLesAGLockSW.get(i)
        .ccSetIsActivated(ccGetAGLockSW(i));
    }//++~
    
    //-- rest
    //-- rest ** weigher ** fr
    mnFRCellDischargeSW=SubWeigherGroup.ccRefer()
      .cmLesFRWeighSW.get(0).ccIsMousePressed();
    SubWeigherGroup.ccRefer().cmLesFRWeighSW.get(0)
      .ccSetIsActivated(ccGetFRGatePL(0));
    SubWeigherGroup.ccRefer().cmLesFRLockSW.get(0)
      .ccSetIsActivated(mnFRCellLockSW);
    //-- rest ** weigher ** as
    mnASCellDischargeSW=SubWeigherGroup.ccRefer()
      .cmLesASWeighSW.get(0).ccIsMousePressed();
    SubWeigherGroup.ccRefer().cmLesASWeighSW.get(0)
      .ccSetIsActivated(ccGetASGatePL(0));
    SubWeigherGroup.ccRefer().cmLesASLockSW.get(0)
      .ccSetIsActivated(mnASCellLockSW);
    //[todo]::rc-ad
    for(int i=MainPlantModel.C_MATT_REST_UI_VALID_HEAD;
      i<=MainPlantModel.C_MATT_REST_UI_VALID_MAX;i++
    ){
      //-- rest ** loop * fr
      ccSetFRGateSW(i, SubWeigherGroup.ccRefer().cmLesFRWeighSW
        .get(i).ccIsMousePressed());
      SubWeigherGroup.ccRefer().cmLesFRWeighSW.get(i)
        .ccSetIsActivated(ccGetFRGatePL(i));
      SubWeigherGroup.ccRefer().cmLesFRLockSW.get(i)
        .ccSetIsActivated(ccGetFRLockSW(i));
      //-- rest ** loop * as
      ccSetASGateSW(i, SubWeigherGroup.ccRefer().cmLesASWeighSW
        .get(i).ccIsMousePressed());
      SubWeigherGroup.ccRefer().cmLesASWeighSW.get(i)
        .ccSetIsActivated(ccGetASGatePL(i));
      SubWeigherGroup.ccRefer().cmLesASLockSW.get(i)
        .ccSetIsActivated(ccGetASLockSW(i));
      //[todo]:rc-ad
    }//..~
    
    //-- mixer ** content
    SubMixerGroup.ccRefer().cmHasContentPL
      .ccSetIsActivated(mnMixerHasContentPL);
    
    //-- mixer ** gate
    SubOperativeGroup.ccRefer().cmMixerGateHoldSW
      .ccSetIsActivated(mnMixerGateHoldSW);
    SubOperativeGroup.ccRefer().cmMixerGateAutoSW
      .ccSetIsActivated(!mnMixerGateHoldSW && !mnMixerGateOpenSW);
    SubOperativeGroup.ccRefer().cmMixerGateOpenSW
      .ccSetIsActivated(mnMixerGateOpenSW);
    SubMixerGroup.ccRefer().cmMixerGate.ccSetIsActivated(mnMixerGateFB);
    SubMixerGroup.ccRefer().cmMixerGate.ccSetIsClosed(mnMixerGateClosedPL);
    
  }//++~
  
  //=== AG xwp
  
  public static final void ccSetAGLockSW(int pxOrder, boolean pxVal){
    switch(pxOrder){
      case  0:mnAGCellLockSW=pxVal;break;
      case  1:mnAGxSWnI=pxVal;break;
      case  2:mnAGxSWnII=pxVal;break;
      case  3:mnAGxSWnIII=pxVal;break;
      case  4:mnAGxSWnIV=pxVal;break;
      case  5:mnAGxSWnV=pxVal;break;
      case  6:mnAGxSWnVI=pxVal;break;
      case  7:mnAGxSWnVII=pxVal;break;
      //--
      default:break;
    }//..?
  }//++<
  
  public static final boolean ccGetAGLockSW(int pxOrder){
    switch(pxOrder){
      case  0:return mnAGCellLockSW;
      case  1:return mnAGxSWnI;
      case  2:return mnAGxSWnII;
      case  3:return mnAGxSWnIII;
      case  4:return mnAGxSWnIV;
      case  5:return mnAGxSWnV;
      case  6:return mnAGxSWnVI;
      case  7:return mnAGxSWnVII;
      default:return false;
    }//..?
  }//++>
  
  public static final void ccSetAGGateSW(int pxOrder, boolean pxVal){
    switch(pxOrder){
      case  0:mnAGCellDischargeSW=pxVal;break;
      case  1:mnAGwSWnI=pxVal;break;
      case  2:mnAGwSWnII=pxVal;break;
      case  3:mnAGwSWnIII=pxVal;break;
      case  4:mnAGwSWnIV=pxVal;break;
      case  5:mnAGwSWnV=pxVal;break;
      case  6:mnAGwSWnVI=pxVal;break;
      case  7:mnAGwSWnVII=pxVal;break;
      //--
      default:break;
    }//..?
  }//++<
  
  public static final boolean ccGetAGGateSW(int pxOrder){
    switch(pxOrder){
      case  0:return mnAGCellDischargeSW;
      case  1:return mnAGwSWnI;
      case  2:return mnAGwSWnII;
      case  3:return mnAGwSWnIII;
      case  4:return mnAGwSWnIV;
      case  5:return mnAGwSWnV;
      case  6:return mnAGwSWnVI;
      case  7:return mnAGwSWnVII;
      default:return false;
    }//..?
  }//++>
  
  public static final void ccSetAGGatePL(int pxOrder, boolean pxVal){
    switch(pxOrder){
      case  0:mnAGCellDischargePL=pxVal;break;
      case  1:mnAGwPLnI=pxVal;break;
      case  2:mnAGwPLnII=pxVal;break;
      case  3:mnAGwPLnIII=pxVal;break;
      case  4:mnAGwPLnIV=pxVal;break;
      case  5:mnAGwPLnV=pxVal;break;
      case  6:mnAGwPLnVI=pxVal;break;
      case  7:mnAGwPLnVII=pxVal;break;
      //--
      default:break;
    }//..?
  }//++<
  
  public static final boolean ccGetAGGatePL(int pxOrder){
    switch(pxOrder){
      case  0:return mnAGCellDischargePL;
      case  1:return mnAGwPLnI;
      case  2:return mnAGwPLnII;
      case  3:return mnAGwPLnIII;
      case  4:return mnAGwPLnIV;
      case  5:return mnAGwPLnV;
      case  6:return mnAGwPLnVI;
      case  7:return mnAGwPLnVII;
      default:return false;
    }//..?
  }//++>
  
  //=== FR xwp
  
  public static final void ccSetFRLockSW(int pxOrder, boolean pxVal){
    switch(pxOrder){
      case  0:mnFRCellLockSW=pxVal;break;
      case  1:mnFRxSWnI=pxVal;break;
      case  2:mnFRxSWnII=pxVal;break;
      case  3:mnFRxSWnIII=pxVal;break;
      //--
      default:break;
    }//..?
  }//++<
  
  public static final boolean ccGetFRLockSW(int pxOrder){
    switch(pxOrder){
      case  0:return mnFRCellLockSW;
      case  1:return mnFRxSWnI;
      case  2:return mnFRxSWnII;
      case  3:return mnFRxSWnIII;
      default:return false;
    }//..?
  }//++>
  
  public static final void ccSetFRGateSW(int pxOrder, boolean pxVal){
    switch(pxOrder){
      case  0:mnFRCellDischargeSW=pxVal;break;
      case  1:mnFRwSWnI=pxVal;break;
      case  2:mnFRwSWnII=pxVal;break;
      case  3:mnFRwSWnIII=pxVal;break;
      //--
      default:break;
    }//..?
  }//++<
  
  public static final boolean ccGetFRGateSW(int pxOrder){
    switch(pxOrder){
      case  0:return mnFRCellDischargeSW;
      case  1:return mnFRwSWnI;
      case  2:return mnFRwSWnII;
      case  3:return mnFRwSWnIII;
      default:return false;
    }//..?
  }//++>
  
  public static final void ccSetFRGatePL(int pxOrder, boolean pxVal){
    switch(pxOrder){
      case  0:mnFRCellDischargePL=pxVal;break;
      case  1:mnFRwPLnI=pxVal;break;
      case  2:mnFRwPLnII=pxVal;break;
      case  3:mnFRwPLnIII=pxVal;break;
      //--
      default:break;
    }//..?
  }//++<
  
  public static final boolean ccGetFRGatePL(int pxOrder){
    switch(pxOrder){
      case  0:return mnFRCellDischargePL;
      case  1:return mnFRwPLnI;
      case  2:return mnFRwPLnII;
      case  3:return mnFRwPLnIII;
      default:return false;
    }//..?
  }//++>
  
  //=== AS xwp
  
  public static final void ccSetASLockSW(int pxOrder, boolean pxVal){
    switch(pxOrder){
      case  0:mnASCellLockSW=pxVal;break;
      case  1:mnASxSWnI=pxVal;break;
      case  2:mnASxSWnII=pxVal;break;
      case  3:mnASxSWnIII=pxVal;break;
      //--
      default:break;
    }//..?
  }//++<
  
  public static final boolean ccGetASLockSW(int pxOrder){
    switch(pxOrder){
      case  0:return mnASCellLockSW;
      case  1:return mnASxSWnI;
      case  2:return mnASxSWnII;
      case  3:return mnASxSWnIII;
      default:return false;
    }//..?
  }//++>
  
  public static final void ccSetASGateSW(int pxOrder, boolean pxVal){
    switch(pxOrder){
      case  0:mnASCellDischargeSW=pxVal;break;
      case  1:mnASwSWnI=pxVal;break;
      case  2:mnASwSWnII=pxVal;break;
      case  3:mnASwSWnIII=pxVal;break;
      //--
      default:break;
    }//..?
  }//++<
  
  public static final boolean ccGetASGateSW(int pxOrder){
    switch(pxOrder){
      case  0:return mnASCellDischargeSW;
      case  1:return mnASwSWnI;
      case  2:return mnASwSWnII;
      case  3:return mnASwSWnIII;
      default:return false;
    }//..?
  }//++>
  
  public static final void ccSetASGatePL(int pxOrder, boolean pxVal){
    switch(pxOrder){
      case  0:mnASCellDischargePL=pxVal;break;
      case  1:mnASwPLnI=pxVal;break;
      case  2:mnASwPLnII=pxVal;break;
      case  3:mnASwPLnIII=pxVal;break;
      //--
      default:break;
    }//..?
  }//++<
  
  public static final boolean ccGetASGatePL(int pxOrder){
    switch(pxOrder){
      case  0:return mnASCellDischargePL;
      case  1:return mnASwPLnI;
      case  2:return mnASwPLnII;
      case  3:return mnASwPLnIII;
      default:return false;
    }//..?
  }//++>
  
  //=== RC xwp
  //[todo]::
  
  //=== AD xwp
  //[todo]::
  
}//***eof
