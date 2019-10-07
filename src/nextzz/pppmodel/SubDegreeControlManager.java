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

import kosui.ppplogic.ZcRangedValueModel;
import kosui.ppputil.VcLocalTagger;
import nextzz.pppdelegate.SubAnalogDelegator;
import nextzz.pppdelegate.SubVCombustDelegator;
import nextzz.pppdelegate.SubVProvisionDelegator;
import nextzz.pppsimulate.ZcPIDController;

public final class SubDegreeControlManager {
  
  private static final SubDegreeControlManager SELF
    = new SubDegreeControlManager();
  public static SubDegreeControlManager ccRefer(){return SELF;}//++>
  private SubDegreeControlManager(){}//++!
  
  //===
  
  public volatile float mnVExfanDegreeForIgnition = 30f;
  
  //-- v
  
  private final ZcPIDController 
    cmVTemperatureCTRL   = new ZcPIDController( 160f, 0.8f, 0.1f ),
    cmVBurnerDegreeCTRL  = new ZcPIDController(   1f, 0.5f, 0.05f),
    cmVPressureCTRL      = new ZcPIDController(1111f, 0.95f,0.2f ),
    cmVExfanDegreeCTRL   = new ZcPIDController(   1f, 0.5f, 0.05f)
  ;//,,,
  
  private final ZcRangedValueModel
    cmVTemperatureAdjustTM = new ZcRangedValueModel(0, 63),
    cmVTemperatureSamplingTM = new ZcRangedValueModel(0, 15),
    cmVPressureAdjustTM = new ZcRangedValueModel(0, 63),
    cmVPressureSamplingTM = new ZcRangedValueModel(0, 15)
  ;//,,,
  
  //-- r
  
  public final void ccLogic(){
    
    //-- v
    
    //-- vb
    //-- vb ** timing
    //[head]::
    //-- vb ** controller
    //-- vb ** to plc
    
    //-- ve
    //-- ve ** timing
    cmVPressureAdjustTM.ccRoll(1);
    cmVPressureSamplingTM.ccRoll(1);
    //-- ve ** controller
    cmVPressureCTRL.ccRun(
      SubAnalogDelegator.mnVDPressureAD,
      cmVPressureAdjustTM.ccIsAt(1) && SubVCombustDelegator.mnVBFlamingPL,
      cmVPressureSamplingTM.ccIsAt(1)
    );
    cmVExfanDegreeCTRL.ccRun(
      !SubVProvisionDelegator.mnVExfanIconPL?0f
         : (!SubVCombustDelegator.mnVBFlamingPL?mnVExfanDegreeForIgnition
           : cmVPressureCTRL.ccGetReverselyTrimmed()*100f),
      (float)SubAnalogScalarManager.ccRefer().ccGetScaledVEDegreeValue()
    );
    
    //-- ve ** to plc
    SubVCombustDelegator.mnVExfanCloseFLG
      = cmVExfanDegreeCTRL.ccGetNegativeOutput();
    SubVCombustDelegator.mnVExfanOpenFLG
      = cmVExfanDegreeCTRL.ccGetPositiveOutput();
    
    //-- r
    //-- re
    //-- rb
    
  }//++~
  
  //===
  
  //[todo]:: ccSetProportion(float pxProportion, flat pxDead){}//++<
  //[todo]:: ccSetAdjustInterval(float pxSecond){}//++<
  //[todo]:: ccSetSamplingInerval(float pxSecond){}//++<
  
  //===

  @Deprecated public static final void ccTagg(){
    VcLocalTagger.ccTag("vp-ctrl", SELF.cmVPressureCTRL);
    VcLocalTagger.ccTag("vp-rvs", SELF.cmVPressureCTRL.ccGetReverselyTrimmed());
    VcLocalTagger.ccTag("ve-ctrl", SELF.cmVExfanDegreeCTRL);
  }//+++
  
 }//***eof
