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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import kosui.pppswingui.ScFactory;
import kosui.ppputil.VcStringUtility;

public class ScFeederBlock extends JPanel{
  
  public final JSpinner cmSpinner
    = new JSpinner(new SpinnerNumberModel(900, 0, 1800, 50));
  
  public final JTextField cmField
    = ScFactory.ccCreateTextLamp("000tph",64,22);
  private final JToggleButton cmForceSW
    = ScFactory.ccCreateCommandToggler("_force", 48, 22);
  private final JToggleButton cmDisableSW
    = ScFactory.ccCreateCommandToggler("_disab", 48, 22);
  public final JButton cmConfigSW
    = ScFactory.ccCreateCommandButton("#", 22, 22);
  
  public ScFeederBlock(String pxTitle) {
    super(new FlowLayout(FlowLayout.LEADING));
    setBorder(BorderFactory.createTitledBorder
      (VcStringUtility.ccNulloutString(pxTitle)));
    add(cmForceSW);
    add(cmDisableSW);
    add(cmSpinner);
    add(cmConfigSW);
    add(cmField);
  }//+++
  
}//***eof
