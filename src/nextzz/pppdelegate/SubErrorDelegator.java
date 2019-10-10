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
import nextzz.pppmodel.SubErrorListModel;

public final class SubErrorDelegator {
  
  public static volatile int mnMessageCode;
  
  public static volatile int dddd;
  
  public static final void ccWire(){
    //[notyet]::
  }//++~
  
  public static final void ccBind(){
    
    //[head]:: well, now what?!
    
    dddd++;
    
    VcLocalConsole.ccSetMessage(
      SubErrorListModel.ccRefer().ccGetMessage(mnMessageCode)
    );
    
  }//++~
  
}//***eof
