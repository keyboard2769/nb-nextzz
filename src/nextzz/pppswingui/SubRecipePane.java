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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import kosui.pppswingui.ScConst;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScTable;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcTranslator;
import nextzz.pppmodel.MainPlantModel;
import nextzz.pppmodel.SubRecipeManager;

public final class SubRecipePane implements SiTabbable{
  
  private static final SubRecipePane SELF = new SubRecipePane();
  public static final SubRecipePane ccRefer(){return SELF;}//+++
  private SubRecipePane(){}//..!
  
  //===
  
  public static final String C_TAB_NAME = VcTranslator.tr("_recipe");
  
  public final JPanel cmPane = ScFactory.ccCreateBorderPanel();
  
  public final List<JTextField> cmLesAGPercetageBox
   = Collections.unmodifiableList(Arrays.asList(
    new JTextField("--"),
    new JTextField("--"),new JTextField("--"),new JTextField("--"),
    new JTextField("--"),new JTextField("--"),new JTextField("--"),
    new JTextField("--")
  ));//,,,
  
  public final List<JTextField> cmLesFRPercetageBox
   = Collections.unmodifiableList(Arrays.asList(
    new JTextField("--"),
    new JTextField("--"),new JTextField("--"),new JTextField("--")
  ));//,,,
  
  public final List<JTextField> cmLesASPercetageBox
   = Collections.unmodifiableList(Arrays.asList(
    new JTextField("--"),
    new JTextField("--"),new JTextField("--"),new JTextField("--")
  ));//,,,
  
  //[todo]::cmLesRCPercetageBox
  //[todo]::cmLesADPercetageBox
  
  public final JButton cmImportButton = ConstSwingUI
    .ccCreateTranslatedButton("_import");
  
  public final JButton cmExportButton = ConstSwingUI
    .ccCreateTranslatedButton("_export");
  
  public final JButton cmRegisterButton = ConstSwingUI
    .ccCreateTranslatedButton("_register");
  
  public final JButton cmDuplicateButton = ConstSwingUI
    .ccCreateTranslatedButton("_duplicate");
  
  public final JButton cmDeleteButton = ConstSwingUI
    .ccCreateTranslatedButton("_delete");
  
  public final JTextField cmIdentityBox = new JTextField("--");
  
  public final JTextField cmNameBox = new JTextField("--");
  
  public final JTextField cmToTalBox
    = ScFactory.ccCreateTextLamp("--",66,22);
  
  public final JTextField cmASProportionBox
    = ScFactory.ccCreateTextLamp("--",66,22);
  
  public final ScTable cmRecipeTable
    = new ScTable(SubRecipeManager.ccRefer(), -1, -1);
    
  private final ActionListener cmCommandButtonListener
    = new ActionListener() {
    @Override public void actionPerformed(ActionEvent ae) {
      
      //-- 
      String lpCommand = ae.getActionCommand();
      
      //--
      if(lpCommand.equals("_register")){
        
        //-- check id box
        String lpIDText = cmIdentityBox.getText();
        if(!VcNumericUtility.ccIsIntegerString(lpIDText)){
          ScConst.ccErrorBox(VcTranslator.tr("_err_of_invalid_id"));
          return;
        }//..?
        int lpID = VcNumericUtility.ccParseIntegerString(lpIDText);
        
        //-- check name box
        String lpName = cmNameBox.getText();
        boolean lpIsNameOK
          = VcConst.ccIsValidString(lpName)
          & VcConst.ccIsAllNoneSpace(lpName);
        VcConst.ccPrintln("before for", lpIsNameOK);
        if(lpName.length()<=128){//..[todo]::make this const??
          for(int i=0;i<lpName.length();i++){
            //.. how the hack could it possible we make it generic??
            lpIsNameOK&=(lpName.charAt(i)!=':');
            lpIsNameOK&=(lpName.charAt(i)!=';');
            lpIsNameOK&=(lpName.charAt(i)!=',');
            lpIsNameOK&=(lpName.charAt(i)!='.');
            lpIsNameOK&=(lpName.charAt(i)!='\\');
            lpIsNameOK&=(lpName.charAt(i)!='/');
            lpIsNameOK&=(lpName.charAt(i)!='|');
            lpIsNameOK&=(lpName.charAt(i)!='~');
            lpIsNameOK&=(lpName.charAt(i)!='*');
            if(!lpIsNameOK){
              System.err.println(String.format(
                "[at:%d][with:%s]", i, lpName
              ));
              break;
            }//..?
          }//..~
        }else{
          lpIsNameOK=false;
        }//..?
        if(!lpIsNameOK){
          ScConst.ccErrorBox(VcTranslator.tr("_err_of_invalid_name"));
          return;
        }//..?
        
        //-- retrieve value
        float[] lpVal = {
          0f,0f,0f,0f,0f,0f,0f,0f,
          0f,0f,0f,0f,
          0f,0f,0f,0f,
          0f,0f,0f,0f,
          0f,0f,0f,0f,
        };
        for(
          int i=MainPlantModel.C_MATT_AGGR_GENERAL_MASK;
          i>=MainPlantModel.C_MATT_AGGR_UI_VALID_HEAD;i--
        ){
          lpVal[i]=VcNumericUtility
            .ccParseFloatString(cmLesAGPercetageBox.get(i).getText());
          if(i<=MainPlantModel.C_MATT_REST_GENERAL_MASK){
            lpVal[8+i]=VcNumericUtility//..[todo]::make this const??
              .ccParseFloatString(cmLesFRPercetageBox.get(i).getText());
            lpVal[12+i]=VcNumericUtility//..[todo]::make this const??
              .ccParseFloatString(cmLesASPercetageBox.get(i).getText());
            //[todo]::.ccParseFloatString(cmLesRCPercetageBox.get(i).getText());
            //[todo]::.ccParseFloatString(cmLesADPercetageBox.get(i).getText());
          }//..?
        }//..~
        
        //-- call
        System.out.println("lets print those values!!");
        String lpRead = String.format(
          "[id:%d][recipe:%s]\n>>>%s",
          lpID,lpName,Arrays.toString(lpVal)
        );
        System.out.println(lpRead);
        
        //[head]::
        SubRecipeManager.ccRefer().ccRegisterPanedRecipe();
        
        return;
      }//..?
      
      System.err.println("SubrecipePane.cmCommandButtonListener::"
        + "unhandled:"+lpCommand);
      
    }//+++
  };//***
  
  private final MouseAdapter cmClickableBoxListener = new MouseAdapter() {
    @Override public void mouseReleased(MouseEvent me) {
      Object lpSource =  me.getSource();
      if(lpSource.equals(cmIdentityBox)){
        String lpInput = ScConst.ccGetStringByInputBox(
          VcTranslator.tr("_mpipt_rid"),
          cmIdentityBox.getText(),cmPane
        );
        if(lpInput.equals(ScConst.C_M_CANCEL)){return;}
        int lpID = VcNumericUtility.ccParseIntegerString(lpInput);
        SubRecipeManager.ccRefer().ccSetPanedRecipeID(lpID);
      }else
      if(lpSource.equals(cmNameBox)){
        String lpInput = ScConst.ccGetStringByInputBox(
          VcTranslator.tr("_mpipt_rname"),
          cmNameBox.getText(),cmPane
        );
        if(lpInput.equals(ScConst.C_M_CANCEL)){return;}
        if(!VcConst.ccIsValidString(lpInput)){return;}
        SubRecipeManager.ccRefer().ccSetPanedRecipeName(lpInput);
      }else
      if(lpSource instanceof JTextField){
        JTextField lpTarget = ((JTextField)lpSource);
        String lpName = lpTarget.getName();
        if(lpName==null){return;}
        if(lpName.length()!=2){return;}
        String lpInput = ScConst.ccGetStringByInputBox(
          VcTranslator.tr("_mpipt_"+lpName),
          lpTarget.getText(),cmPane
        );
        if(lpInput.equals(ScConst.C_M_CANCEL)){return;}
        float lpParsed = VcNumericUtility.ccParseFloatString(lpInput);
        SubRecipeManager.ccRefer()
          .ccSetPanePercentage(lpName.charAt(0),lpName.charAt(1),lpParsed);
      }//..?
    }//+++
  };//***
  
  private final MouseAdapter cmTablePressListener = new MouseAdapter() {
    @Override public void mousePressed(MouseEvent me) {
      int lpTableIndex = cmRecipeTable.ccGetSelectedRowIndex();
      /* 4 */VcConst.ccLogln(".cmTablePressListener:", lpTableIndex);
      SubRecipeManager.ccRefer().ccApplyTableSelection(lpTableIndex);
    }//+++
  };//***
  
  @Override public final void ccInit(){
    
    //-- bar
    cmImportButton.addActionListener(cmCommandButtonListener);
    cmExportButton.addActionListener(cmCommandButtonListener);
    JToolBar lpBar = ScFactory.ccCreateStuckedToolBar();
    lpBar.add(cmImportButton);
    lpBar.add(cmExportButton);
    
    //-- modificaive pane ** index and name
    ccSetupInputtiveBox(cmIdentityBox, 44, 22);
    ccSetupInputtiveBox(cmNameBox, 320, 22);
    cmIdentityBox.setHorizontalAlignment(JTextField.LEFT);
    cmNameBox.setHorizontalAlignment(JTextField.LEFT);
    JPanel lpIndexAndNamePane = ScFactory.ccCreateFlowPanel(1, false);
    lpIndexAndNamePane.add(new JSeparator(SwingConstants.VERTICAL));
    lpIndexAndNamePane.add(new JLabel(VcTranslator.tr("_l_rid:")));
    lpIndexAndNamePane.add(cmIdentityBox);
    lpIndexAndNamePane.add(new JSeparator(SwingConstants.VERTICAL));
    lpIndexAndNamePane.add(new JLabel(VcTranslator.tr("_l_rname:")));
    lpIndexAndNamePane.add(cmNameBox);
    lpIndexAndNamePane.add(new JSeparator(SwingConstants.VERTICAL));
    lpIndexAndNamePane.add(new JSeparator(SwingConstants.VERTICAL));
    lpIndexAndNamePane.add(new JLabel(VcTranslator.tr("_l_rtotal:")));
    lpIndexAndNamePane.add(cmToTalBox);
    lpIndexAndNamePane.add(new JSeparator(SwingConstants.VERTICAL));
    lpIndexAndNamePane.add(new JLabel(VcTranslator.tr("_l_raspa:")));
    lpIndexAndNamePane.add(cmASProportionBox);
    
    //-- modificaive pane ** operative
    cmRegisterButton.addActionListener(cmCommandButtonListener);
    cmDuplicateButton.addActionListener(cmCommandButtonListener);
    cmDeleteButton.addActionListener(cmCommandButtonListener);
    JPanel lpOperativePane = ScFactory.ccCreateGridPanel(3, 1);
    lpOperativePane.add(cmRegisterButton);
    lpOperativePane.add(cmDuplicateButton);
    lpOperativePane.add(cmDeleteButton);
    
    //-- modificaive pane ** inputtive
    JPanel lpInputtivePane = ScFactory.ccCreateGridPanel(3, 2);
    lpInputtivePane.add(ccCreateInputtiveMattPane
      (VcTranslator.tr("_l_agpt:"),cmLesAGPercetageBox,"G"));
    /* 6 */lpInputtivePane.add(ccCreateInputtiveMattPane("_l_rcpt",null,"R"));
    lpInputtivePane.add(ccCreateInputtiveMattPane
      (VcTranslator.tr("_l_frpt:"),cmLesFRPercetageBox,"F"));
    /* 6 */lpInputtivePane.add(ccCreateInputtiveMattPane("_ad_agpt",null,"D"));
    lpInputtivePane.add(ccCreateInputtiveMattPane
      (VcTranslator.tr("_l_aspt:"),cmLesASPercetageBox,"S"));
    /* 6 */lpInputtivePane.getComponent(1).setVisible(false);
    /* 6 */lpInputtivePane.getComponent(3).setVisible(false);
    /* 6 */cmLesAGPercetageBox.get(7).setVisible(false);
    /* 6 */cmLesFRPercetageBox.get(1).setVisible(false);
    /* 6 */cmLesASPercetageBox.get(3).setVisible(false);
    /* 6 */cmLesASPercetageBox.get(1).setVisible(false);
    
    //-- modificaive pane ** indicative
    
    //-- modificaive pane ** base 
    JPanel lpTailPane = ScFactory.ccCreateBorderPanel();
    lpTailPane.add(lpIndexAndNamePane, BorderLayout.PAGE_START);
    lpTailPane.add(lpOperativePane, BorderLayout.LINE_END);
    lpTailPane.add(lpInputtivePane, BorderLayout.CENTER);
    
    //-- table config
    cmRecipeTable.ccAddMouseListener(cmTablePressListener);
    //[todo]:: clean this!!
    cmRecipeTable.ccSetColumnWidth(0, 48);//..how do we make it constant?
    cmRecipeTable.ccSetColumnWidth(1, 147);//..how do we make it constant?
    //--
    cmRecipeTable.ccHideColumn(2);//..how do we make it specific?
    //--
    cmRecipeTable.ccHideColumn(11);//..how do we make it specific?
    //--
    cmRecipeTable.ccHideColumn(12);//..how do we make it specific?
    cmRecipeTable.ccHideColumn(14);//..how do we make it specific?
    //--
    cmRecipeTable.ccHideColumn(15);//..how do we make it specific?
    cmRecipeTable.ccHideColumn(16);//..how do we make it specific?
    cmRecipeTable.ccHideColumn(17);//..how do we make it specific?
    //--
    cmRecipeTable.ccHideColumn(18);//..how do we make it specific?
    cmRecipeTable.ccHideColumn(19);//..how do we make it specific?
    cmRecipeTable.ccHideColumn(20);//..how do we make it specific?
    
    
    //-- pack
    cmPane.add(lpBar,BorderLayout.PAGE_START);
    cmPane.add(cmRecipeTable,BorderLayout.CENTER);
    cmPane.add(lpTailPane,BorderLayout.PAGE_END);
    
  }//++!
  
  private JPanel ccCreateInputtiveMattPane(
    String pxHead, List<JTextField> pxPacked, String pxPrefix
  ){
    JPanel lpRes = ScFactory
      .ccCreateFlowPanel(1,false,VcTranslator.tr(pxHead));
    if(pxPacked!=null){
      if(pxPacked.size()<4){return lpRes;}
      for(int i=pxPacked.size()-1;i>=1;i--){
        JTextField lpTarget = pxPacked.get(i);
        ccSetupInputtiveBox(lpTarget, 44, 22);
        if(VcConst.ccIsValidString(pxPrefix)){
          lpTarget.setName(pxPrefix+Integer.toString(i));
        }//..?
        lpRes.add(lpTarget);
      }//..~
    }else{
      lpRes.add(new JButton("=3="));
      lpRes.add(new JButton("=2="));
      lpRes.add(new JButton("=1="));
    }//..?
    return lpRes;
  }//+++
  
  private void ccSetupInputtiveBox(JTextField pxTarget, int pxW, int pxH){
    pxTarget.setEditable(false);
    pxTarget.setEnabled(false);
    pxTarget.setBackground(ScConst.C_LIT_GRAY);
    pxTarget.setForeground(ScConst.C_DARK_BLUE);
    pxTarget.setDisabledTextColor(ScConst.C_BLUE);
    pxTarget.setHorizontalAlignment(JTextField.RIGHT);
    pxTarget.setPreferredSize(new Dimension(pxW, pxH));
    pxTarget.addMouseListener(cmClickableBoxListener);
  }//+++
  
  //===
  
  @Override public final String ccGetTitle() {
    return C_TAB_NAME;
  }//++>

  @Override public final JPanel ccGetPane() {
    return cmPane;
  }//++>
  
}//***eof
