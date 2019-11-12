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

package nextzz.pppsetting;

import kosui.ppplocalui.EcConst;
import kosui.ppplogic.ZcRangedValueModel;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcTranslator;
import nextzz.pppmodel.SubWeighControlManager;
import processing.core.PApplet;

public class SubWeighSetting extends McAbstractSettingPartition{
  
  public static final String C_KEY_TITLE = "_weigh_setting";
  
  private static final SubWeighSetting SELF = new SubWeighSetting();
  public static final SubWeighSetting ccRefer(){return SELF;}//++>
  private SubWeighSetting(){}//++!
  
  //=== dry/wet
  
  public final MiSettingItem cmDryTimeItem= new MiSettingItem() {
    @Override public String ccGetName() {
      return "[S]"+VcTranslator.tr("_dry_time");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_dry_time");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 99]";
    }//++>
    @Override public String ccGetValue() {
      return Float.toString(EcConst
        .ccToSecondCount(SubWeighControlManager.ccRefer().vmDrySetFrame));
    }//++>
    @Override public void ccSetValue(String pxVal) {
      float lpFixed = VcNumericUtility.ccParseFloatString(pxVal);
      lpFixed=PApplet.constrain(lpFixed, 0.0f, 99.0f);
      int lpFrame = EcConst.ccToFrameCount(lpFixed);
      SubWeighControlManager.ccRefer().vmDrySetFrame=lpFrame;
    }//++<
  };//***
  
  public final MiSettingItem cmWetTimeItem= new MiSettingItem() {
    @Override public String ccGetName() {
      return "[S]"+VcTranslator.tr("_wet_time");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_wet_time");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 99]";
    }//++>
    @Override public String ccGetValue() {
      return Float.toString(EcConst
        .ccToSecondCount(SubWeighControlManager.ccRefer().vmWetSetFrame));
    }//++>
    @Override public void ccSetValue(String pxVal) {
      float lpFixed = VcNumericUtility.ccParseFloatString(pxVal);
      lpFixed=PApplet.constrain(lpFixed, 0.0f, 99.0f);
      int lpFrame = EcConst.ccToFrameCount(lpFixed);
      SubWeighControlManager.ccRefer().vmWetSetFrame=lpFrame;
    }//++<
  };//***
  
  //=== emptiness
  
  public final MiSettingItem cmAGEmptyKGItem = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[KG]"+VcTranslator.tr("_ag_empty_kg");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_ag_empty_kg");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 400]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString
        (SubWeighControlManager.ccRefer().vmAGEmptyKG);
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 0, 400);
      SubWeighControlManager.ccRefer().vmAGEmptyKG=lpFixed;
    }//++<
  };//***
  
  public final MiSettingItem cmFREmptyKGItem = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[KG]"+VcTranslator.tr("_fr_empty_kg");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_fr_empty_kg");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 50]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString
        (SubWeighControlManager.ccRefer().vmFREmptyKG);
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 0, 400);
      SubWeighControlManager.ccRefer().vmFREmptyKG=lpFixed;
    }//++<
  };//***
  
  public final MiSettingItem cmASEmptyKGItem = new MiSettingItem() {
    @Override public String ccGetName() {
      return "[KG]"+VcTranslator.tr("_as_empty_kg");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_as_empty_kg");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 50]";
    }//++>
    @Override public String ccGetValue() {
      return Integer.toString
        (SubWeighControlManager.ccRefer().vmASEmptyKG);
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 0, 400);
      SubWeighControlManager.ccRefer().vmASEmptyKG=lpFixed;
    }//++<
  };//***
  
  //[todo]RCEmpty
  //[todo]ADEmpty
  
  //=== graduation
  
  public final MiSettingItem cmAGGraduationItem= new MiSettingItem() {
    @Override public String ccGetName() {
      return "[KG]"+VcTranslator.tr("_ag_graduation");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_ag_graduation");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[1 ~ 50]";
    }//++>
    @Override public String ccGetValue() {
      return "<noteyet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 1, 50);
      System.out.println(".ccSetValue()::"+Integer.toString(lpFixed));
    }//++<
  };//***
  
  public final MiSettingItem cmFRGraduationItem= new MiSettingItem() {
    @Override public String ccGetName() {
      return "[KG]"+VcTranslator.tr("_fr_graduation");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_fr_graduation");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0.1 ~ 5.0]";
    }//++>
    @Override public String ccGetValue() {
      return "<noteyet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      float lpFixed = VcNumericUtility.ccParseFloatString(pxVal);
      lpFixed=PApplet.constrain(lpFixed, 0.1f, 5.0f);
      System.out.println(".ccSetValue()::"+Float.toString(lpFixed));
    }//++<
  };//***
  
  public final MiSettingItem cmASGraduationItem= new MiSettingItem() {
    @Override public String ccGetName() {
      return "[KG]"+VcTranslator.tr("_as_graduation");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_as_graduation");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0.1 ~ 5.0]";
    }//++>
    @Override public String ccGetValue() {
      return "<noteyet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      float lpFixed = VcNumericUtility.ccParseFloatString(pxVal);
      lpFixed=PApplet.constrain(lpFixed, 0.5f, 5.0f);
      System.out.println(".ccSetValue()::"+Float.toString(lpFixed));
    }//++<
  };//***
  
  //[todo]RCGraduation
  //[todo]ADGraduation
  
  //=== dropping
  
  public final MiSettingItem cmAGDroppingItem= new MiSettingItem() {
    @Override public String ccGetName() {
      return "[KG]"+VcTranslator.tr("_ag_dropping");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_ag_dropping");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 500]";
    }//++>
    @Override public String ccGetValue() {
      return "<noteyet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 0, 500);
      System.out.println(".ccSetValue()::"+Integer.toString(lpFixed));
    }//++<
  };//***
  
  public final MiSettingItem cmFRDroppingItem= new MiSettingItem() {
    @Override public String ccGetName() {
      return "[KG]"+VcTranslator.tr("_fr_dropping");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_fr_dropping");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 50]";
    }//++>
    @Override public String ccGetValue() {
      return "<noteyet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 0, 50);
      System.out.println(".ccSetValue()::"+Integer.toString(lpFixed));
    }//++<
  };//***
  
  public final MiSettingItem cmASDroppingItem= new MiSettingItem() {
    @Override public String ccGetName() {
      return "[KG]"+VcTranslator.tr("_as_dropping");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_as_dropping");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 50]";
    }//++>
    @Override public String ccGetValue() {
      return "<noteyet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 0, 50);
      System.out.println(".ccSetValue()::"+Integer.toString(lpFixed));
    }//++<
  };//***
  
  //[todo]RCDropping
  //[todo]ADDropping
  
  //=== constraining
  
  public final MiSettingItem cmAGConstrainingItem= new MiSettingItem() {
    @Override public String ccGetName() {
      return "[KG]"+VcTranslator.tr("_ag_constraining");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_ag_constraining");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 9999]";
    }//++>
    @Override public String ccGetValue() {
      return "<noteyet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 0, 9999);
      System.out.println(".ccSetValue()::"+Integer.toString(lpFixed));
    }//++<
  };//***
  
  public final MiSettingItem cmFRConstrainingItem= new MiSettingItem() {
    @Override public String ccGetName() {
      return "[KG]"+VcTranslator.tr("_fr_constraining");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_fr_constraining");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 999]";
    }//++>
    @Override public String ccGetValue() {
      return "<noteyet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 0, 999);
      System.out.println(".ccSetValue()::"+Integer.toString(lpFixed));
    }//++<
  };//***
  
  public final MiSettingItem cmASConstrainingItem= new MiSettingItem() {
    @Override public String ccGetName() {
      return "[KG]"+VcTranslator.tr("_as_constraining");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_as_constraining");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 999]";
    }//++>
    @Override public String ccGetValue() {
      return "<noteyet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 0, 999);
      System.out.println(".ccSetValue()::"+Integer.toString(lpFixed));
    }//++<
  };//***
  
  //[todo]RCConstraining
  //[todo]ADConstraining
  
  //=== ErrorMargin
  
  public final MiSettingItem cmAGErrorMarginItem= new MiSettingItem() {
    @Override public String ccGetName() {
      return "[%]"+VcTranslator.tr("_ag_error_margin");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_ag_error_margin");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[1 ~ 99]";
    }//++>
    @Override public String ccGetValue() {
      return "<noteyet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 1, 99);
      System.out.println(".ccSetValue()::not_yet:"+Integer.toString(lpFixed));
    }//++<
  };//***
  
  public final MiSettingItem cmFRErrorMarginItem= new MiSettingItem() {
    @Override public String ccGetName() {
      return "[%]"+VcTranslator.tr("_fr_error_margin");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_fr_error_margin");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[1 ~ 99]";
    }//++>
    @Override public String ccGetValue() {
      return "<noteyet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 1, 99);
      System.out.println(".ccSetValue()::"+Integer.toString(lpFixed));
    }//++<
  };//***
  
  public final MiSettingItem cmASErrorMarginItem= new MiSettingItem() {
    @Override public String ccGetName() {
      return "[%]"+VcTranslator.tr("_as_error_margin");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_as_error_margin");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[1 ~ 99]";
    }//++>
    @Override public String ccGetValue() {
      return "<noteyet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 1, 99);
      System.out.println(".ccSetValue()::"+Integer.toString(lpFixed));
    }//++<
  };//***
  
  //[todo]RCErrorMargin
  //[todo]ADErrorMargin
  
  //=== discharge constrainning ** rc
  
  public final MiSettingItem cmRCDischargeConstrainingItem= new MiSettingItem() {
    @Override public String ccGetName() {
      return "[KG]"+VcTranslator.tr("_rc_discharge_constraining");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_rc_discharge_constraining");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 9999]";
    }//++>
    @Override public String ccGetValue() {
      return "<noteyet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 0, 9999);
      System.out.println(".ccSetValue()::not_yet:"+Integer.toString(lpFixed));
    }//++<
  };//***
  
  //=== over scale ** as
  
  public final MiSettingItem cmASOverScaleItem= new MiSettingItem() {
    @Override public String ccGetName() {
      return "[AD]"+VcTranslator.tr("_as_over_scale");
    }//++>
    @Override public String ccGetDescription() {
      return VcTranslator.tr("_dscp_as_over_scale");
    }//++>
    @Override public String ccGetLimitationInfo() {
      return "[0 ~ 7999]";
    }//++>
    @Override public String ccGetValue() {
      return "<noteyet>";
    }//++>
    @Override public void ccSetValue(String pxVal) {
      int lpFixed = VcNumericUtility.ccParseIntegerString(pxVal);
      lpFixed=ZcRangedValueModel.ccLimitInclude(lpFixed, 0, 7999);
      System.out.println(".ccSetValue()::not_yet:"+Integer.toString(lpFixed));
    }//++<
  };//***
  
  //===

  @Override public void ccInit() {
    
    //-- dry/wet
    cmListOfItem.add(cmDryTimeItem);
    cmListOfItem.add(cmWetTimeItem);
    
    //-- emptiness
    cmListOfItem.add(cmAGEmptyKGItem);
    cmListOfItem.add(cmFREmptyKGItem);
    cmListOfItem.add(cmASEmptyKGItem);
    //[todo]::r/a
    
    //-- graduation
    cmListOfItem.add(cmAGGraduationItem);
    cmListOfItem.add(cmFRGraduationItem);
    cmListOfItem.add(cmASGraduationItem);
    //[todo]::r/a
    
    //-- dropping
    cmListOfItem.add(cmAGDroppingItem);
    cmListOfItem.add(cmFRDroppingItem);
    cmListOfItem.add(cmASDroppingItem);
    //[todo]::r/a
    
    //-- constraining
    cmListOfItem.add(cmAGConstrainingItem);
    cmListOfItem.add(cmFRConstrainingItem);
    cmListOfItem.add(cmASConstrainingItem);
    //[todo]::r/a
    
    //-- error margin
    cmListOfItem.add(cmAGErrorMarginItem);
    cmListOfItem.add(cmFRErrorMarginItem);
    cmListOfItem.add(cmASErrorMarginItem);
    //[todo]::r/a
    
    //-- misc
    cmListOfItem.add(cmRCDischargeConstrainingItem);
    cmListOfItem.add(cmASOverScaleItem);
    
  }//++!

  @Override public String ccGetTile() {
    return VcTranslator.tr(C_KEY_TITLE);
  }//++>
  
}//***eof
