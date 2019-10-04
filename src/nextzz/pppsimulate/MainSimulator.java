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

package nextzz.pppsimulate;

public final class MainSimulator {
  
  private static MainSimulator self = null;
  public static MainSimulator ccRefer() {
    if(self==null){self=new MainSimulator();}
    return self;
  }//+++
  private MainSimulator(){}//..!
  
  //===
  
  private static int cmRoller=11;
  
  private static void ssRoll(){
    cmRoller++;cmRoller&=0xF;
  }//+++
  
  public static boolean ccOneSecondPLS(){
    return cmRoller==7;
  }//+++
  
  public static boolean ccOneSecondClock(){
    return cmRoller>7;
  }//+++
  
  static public final void ccSimulate(){
    
    ssRoll();
    
    //-- scan
    SubWeighingTask.ccRefer().ccScan();
    SubFeederTask.ccRefer().ccScan();
    SubVProvisionTask.ccRefer().ccScan();
    SubVCombusTask.ccRefer().ccScan();
    
    //-- simulate
    SubWeighingTask.ccRefer().ccScan();
    SubFeederTask.ccRefer().ccSimulate();
    SubVProvisionTask.ccRefer().ccSimulate();
    SubVCombusTask.ccRefer().ccSimulate();
  
  }//+++
  
 }//***eof
