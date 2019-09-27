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
package ppptest;

import kosui.ppplocalui.EcConst;
import kosui.ppplogic.ZcRoller;
import kosui.ppputil.VcLocalTagger;
import processing.core.PApplet;

public class TestSketch extends PApplet{
  
  ZcRoller cmRoller = new ZcRoller();
  
  @Override public void setup() {
    size(320,240);
    EcConst.ccSetupSketch(this);
    VcLocalTagger.ccGetInstance().ccInit(this);
  }//+++

  @Override public void draw() {
    background(0);
    cmRoller.ccRoll();
    VcLocalTagger.ccTag("roll", cmRoller.ccGetValue());
    VcLocalTagger.ccStabilize();
  }//+++

  @Override public void keyPressed() {
    exit();
  }//+++
  
  public static void main(String[] args) {
    PApplet.main(TestSketch.class.getCanonicalName());
  }//+++
  
}//***eof