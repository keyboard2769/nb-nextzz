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

import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import kosui.pppswingui.ScConst;
import kosui.pppswingui.ScFactory;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcStringUtility;

public class ScFeederBlock extends JPanel{
  
  public static final int C_SPEED_MIN =    0;
  public static final int C_SPEED_MAX = 1800;
  public static final int C_STEP_MIN  =   50;
  public static final int C_STEP_MAX  =  200;
  
  //===
  
  public final JSpinner cmSpinner
    = new JSpinner(new SpinnerNumberModel(900, 0, 1800, 50));
  
  public final JTextField cmField
    = ScFactory.ccCreateValueBox("000tph",64,22);
  private final JToggleButton cmForceSW
    = ScFactory.ccCreateCommandToggler("_force", 48, 22);
  private final JToggleButton cmDisableSW
    = ScFactory.ccCreateCommandToggler("_disab", 48, 22);
  public final JButton cmConfigSW
    = ScFactory.ccCreateCommandButton("#", 22, 22);
  
  //===
  
  public ScFeederBlock(String pxTitle) {
    
    super(new FlowLayout(FlowLayout.LEADING));
    setBorder(BorderFactory.createTitledBorder
      (VcStringUtility.ccNulloutString(pxTitle)));
    
    //--
    cmSpinner.setBackground(ScConst.C_LIT_GRAY);
    cmSpinner.setForeground(ScConst.C_DARK_GRAY);
    
    //--
    add(cmForceSW);
    add(cmDisableSW);
    add(cmSpinner);
    add(cmConfigSW);
    add(cmField);
    
  }//+++
  
  //===
  
  public final int ccGetValue(){
    return VcNumericUtility.ccInteger(cmSpinner.getValue());
  }//+++
  
  public final void ccSetStep(int pxStep){
    Object lpModel = cmSpinner.getModel();
    if(lpModel instanceof SpinnerNumberModel){
      SpinnerNumberModel lpNumberModel = (SpinnerNumberModel)lpModel;
      lpNumberModel.setStepSize(pxStep);
    }//..?
  }//+++
  
}//***eof
