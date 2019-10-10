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

import kosui.ppplogic.ZiTask;
import kosui.ppputil.VcLocalTagger;
import nextzz.pppdelegate.SubErrorDelegator;

public final class SubErrorTask implements ZiTask{
  
  private static SubErrorTask self = null;
  public static final SubErrorTask ccRefer() {
    if(self==null){self=new SubErrorTask();return self;}
    else{return self;}
  }//+++
  private SubErrorTask(){}//++!
  
  //===

  @Override public void ccScan() {
    
    SubErrorDelegator.mnMessageCode=MainSimulator.ccHalfSecondClock()?
      0:-1;
    
  }//+++

  @Override public void ccSimulate() {
    /* 0 */
  }//+++
  
  //===
  
  @Deprecated public final void tstTagg(){
    VcLocalTagger.ccTag("wm39",SubErrorDelegator.mnMessageCode);
  }//+++
  
 }//***eof
