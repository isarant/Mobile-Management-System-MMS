
package MMSServer;

import java.lang.Byte;
import java.lang.Integer;

class AddProtocol extends javax.swing.JFrame {
    
    private String myFilename;
    private int myinexrec;
    private ProtocolForm mypf;
    private static boolean Loading=false;
    
    public AddProtocol() {
        
    }
    
    public boolean isLoading() {
        return Loading;
    }
    
    public void init(String Filename,int inexrec,ProtocolForm pf) {
        Loading=true;
        myFilename=Filename;
        myinexrec=inexrec;
        mypf=pf;
        initComponents();
        choice_visible.addItem("0");
        choice_visible.addItem("1");
        choice_visible.select("1");
        choice_signaltype.addItem("3");
        choice_signaltype.addItem("4");
        choice_signaltype.addItem("5");
        choice_signaltype.select("3");
        choice_parameters.addItem("0");
        choice_parameters.addItem("1");
        choice_parameters.addItem("2");
        choice_parameters.select("0");
        txt_classname.setText("returnback");
        choice_us.addItem("Both");
        choice_us.addItem("Server");
        choice_us.addItem("Client");
        choice_us.select("Both");
        choice_top1.addItem("txt");
        choice_top1.addItem("int");
        choice_top1.addItem("float");
        //choice_top1.addItem("byte");
        choice_top1.select("txt");
        choice_top2.addItem("txt");
        choice_top2.addItem("int");
        choice_top2.addItem("float");
        //choice_top2.addItem("byte");
        choice_top2.select("txt");
        
        txt_parameter1name.setVisible(false);
        txt_parameter2name.setVisible(false);
        choice_top1.setVisible(false);
        choice_top2.setVisible(false);
        txt_parameter1name.setText("None");
        txt_parameter2name.setText("None");
        txt_parameterclass.setText("None");
        if(myinexrec>-1) {
            SignalsFile sf=new SignalsFile();
            ReadFile rf=new ReadFile(myFilename,sf.Get_SignalsFile_Size());
            if(rf.NumOfRecords()>= myinexrec) {
                sf.Set_SignalsFile_Bytes(rf.Read(inexrec));
                choice_visible.select(String.valueOf(sf.Get_Visible()));
                choice_signaltype.select(String.valueOf(sf.Get_Signal_Type()));
                txt_signalcommand.setText(String.valueOf(sf.Get_Signal_Command()));
                txt_description.setText(sf.Get_Desc_No_Space());
                txt_classname.setText(sf.Get_ClassName());
                choice_parameters.select(String.valueOf(sf.Get_Parametrs()));
                txt_parameter1name.setText(sf.Get_Parameter1Name_No_Space());
                txt_parameter2name.setText(sf.Get_Parameter2Name_No_Space());
                txt_parameterclass.setText(sf.Get_ParameterClass_No_Space());
                choice_us.select(sf.Get_UsedCommand_No_Space());
                choice_top1.select(sf.Get_TypeOfParameter1_String());
                choice_top2.select(sf.Get_TypeOfParameter2_String());
                
                switch (Integer.parseInt(choice_parameters.getSelectedItem())) {
                    case 0:{txt_parameter1name.setVisible(false);
                    txt_parameter2name.setVisible(false);
                    choice_top1.setVisible(false);
                    choice_top2.setVisible(false);}
                    case 1:{txt_parameter1name.setVisible(true);
                    txt_parameter2name.setVisible(false);
                    choice_top1.setVisible(true);
                    choice_top2.setVisible(false);}
                    case 2:{txt_parameter1name.setVisible(true);
                    txt_parameter2name.setVisible(true);
                    choice_top1.setVisible(true);
                    choice_top2.setVisible(true);}
                }
            }
            rf.Close();
        }
        
    }
    
    private void initComponents() {//GEN-BEGIN:initComponents
        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        txt_signalcommand = new java.awt.TextField();
        label3 = new java.awt.Label();
        label4 = new java.awt.Label();
        txt_classname = new java.awt.TextField();
        btn_add = new java.awt.Button();
        btn_cancel = new java.awt.Button();
        txt_description = new java.awt.TextField();
        label_alert = new java.awt.Label();
        choice_parameters = new java.awt.Choice();
        choice_signaltype = new java.awt.Choice();
        label5 = new java.awt.Label();
        label_alert2 = new java.awt.Label();
        label6 = new java.awt.Label();
        label7 = new java.awt.Label();
        label8 = new java.awt.Label();
        txt_parameter1name = new java.awt.TextField();
        txt_parameter2name = new java.awt.TextField();
        choice_us = new java.awt.Choice();
        choice_visible = new java.awt.Choice();
        txt_parameterclass = new java.awt.TextField();
        label9 = new java.awt.Label();
        label10 = new java.awt.Label();
        choice_top1 = new java.awt.Choice();
        label11 = new java.awt.Label();
        choice_top2 = new java.awt.Choice();
        label12 = new java.awt.Label();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setTitle("Add Signal ");
        setBackground(new java.awt.Color(255, 51, 0));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        label1.setText("Signal Type");
        getContentPane().add(label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        label2.setText("Signal Command");
        getContentPane().add(label2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        txt_signalcommand.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_signalcommandKeyTyped(evt);
            }
        });

        getContentPane().add(txt_signalcommand, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 100, -1));

        label3.setText("Description");
        getContentPane().add(label3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        label4.setText("Class Or Programme Name");
        getContentPane().add(label4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 170, 20));

        txt_classname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_classnameKeyTyped(evt);
            }
        });

        getContentPane().add(txt_classname, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 170, 100, -1));

        btn_add.setLabel("Save");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        getContentPane().add(btn_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 450, 60, -1));

        btn_cancel.setLabel("Cancel");
        btn_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelActionPerformed(evt);
            }
        });

        getContentPane().add(btn_cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 450, 60, -1));

        txt_description.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_descriptionKeyTyped(evt);
            }
        });

        getContentPane().add(txt_description, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, 100, -1));

        label_alert.setForeground(new java.awt.Color(255, 51, 0));
        getContentPane().add(label_alert, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 20));

        choice_parameters.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                choice_parametersItemStateChanged(evt);
            }
        });

        getContentPane().add(choice_parameters, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 50, -1));

        choice_signaltype.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                signatype_change(evt);
            }
        });

        getContentPane().add(choice_signaltype, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, 50, -1));

        label5.setText("Number Of Parameters");
        getContentPane().add(label5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));
        label5.getAccessibleContext().setAccessibleName("Number Of Parameters");

        label_alert2.setForeground(new java.awt.Color(255, 51, 0));
        getContentPane().add(label_alert2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 320, 20));

        label6.setText("Parameter 1 Name");
        getContentPane().add(label6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));
        label6.getAccessibleContext().setAccessibleName("Parameter 1 Name");

        label7.setText("Parameter 2 Name");
        getContentPane().add(label7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, -1, -1));
        label7.getAccessibleContext().setAccessibleName("Parameter 2 Name");

        label8.setText("How used Command");
        getContentPane().add(label8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, -1));

        txt_parameter1name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_parameter1nameKeyTyped(evt);
            }
        });

        getContentPane().add(txt_parameter1name, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 270, 100, -1));

        txt_parameter2name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_parameter2nameKeyTyped(evt);
            }
        });

        getContentPane().add(txt_parameter2name, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 340, 100, -1));

        getContentPane().add(choice_us, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, 100, 30));

        getContentPane().add(choice_visible, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 50, -1));

        getContentPane().add(txt_parameterclass, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 410, 100, 20));

        label9.setText("Parameter Class Name");
        getContentPane().add(label9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, -1, 20));
        label9.getAccessibleContext().setAccessibleName("Parameter Class Name");

        label10.setText("Visible Signal");
        getContentPane().add(label10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        getContentPane().add(choice_top1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 300, 100, 30));

        label11.setText("Type Of Parameter 1");
        getContentPane().add(label11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));
        label11.getAccessibleContext().setAccessibleName("Type Of Parameter 1");

        getContentPane().add(choice_top2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 370, 100, 30));

        label12.setText("Type Of Parameter 2");
        getContentPane().add(label12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, -1, -1));
        label12.getAccessibleContext().setAccessibleName("Type Of Parameter 2");

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-334)/2, (screenSize.height-542)/2, 334, 542);
    }//GEN-END:initComponents
    
    private void choice_parametersItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_choice_parametersItemStateChanged
        switch (Integer.parseInt(choice_parameters.getSelectedItem())) {
            case 0:{txt_parameter1name.setVisible(false);
            txt_parameter2name.setVisible(false);
            choice_top1.setVisible(false);
            choice_top2.setVisible(false);}
            case 1:{txt_parameter1name.setVisible(true);
            txt_parameter2name.setVisible(false);
            choice_top1.setVisible(true);
            choice_top2.setVisible(false);}
            case 2:{txt_parameter1name.setVisible(true);
            txt_parameter2name.setVisible(true);
            choice_top1.setVisible(true);
            choice_top2.setVisible(true);}
        }
    }//GEN-LAST:event_choice_parametersItemStateChanged
    
    private void txt_parameter2nameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_parameter2nameKeyTyped
        checkchar(evt,true,false,txt_parameter2name,10);
    }//GEN-LAST:event_txt_parameter2nameKeyTyped
    
    private void txt_parameter1nameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_parameter1nameKeyTyped
        checkchar(evt,true,false,txt_parameter1name,10);
    }//GEN-LAST:event_txt_parameter1nameKeyTyped
    
    private void signatype_change(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_signatype_change
        if(Byte.parseByte(choice_signaltype.getSelectedItem())==3) {
            txt_classname.setText("returnback");
        }
        if(Byte.parseByte(choice_signaltype.getSelectedItem())==5) {
            txt_classname.setText("None");
        }
        if(Byte.parseByte(choice_signaltype.getSelectedItem())==4) {
            txt_classname.setText("None");
        }
    }//GEN-LAST:event_signatype_change
    
    private void txt_classnameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_classnameKeyTyped
        checkchar(evt,false,false,txt_classname,50);
    }//GEN-LAST:event_txt_classnameKeyTyped
    
    private void txt_descriptionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_descriptionKeyTyped
        checkchar(evt,true,false,txt_description,20);
    }//GEN-LAST:event_txt_descriptionKeyTyped
    
    private void txt_signalcommandKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_signalcommandKeyTyped
        checkchar(evt,true,true,txt_signalcommand,10);
    }//GEN-LAST:event_txt_signalcommandKeyTyped
    
    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed
        mypf.showsignals();
        Loading=false;
        this.hide();
        this.disable();
    }//GEN-LAST:event_btn_cancelActionPerformed
    
    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        try {
            if(Byte.parseByte(choice_signaltype.getSelectedItem())>2 && Byte.parseByte(choice_signaltype.getSelectedItem())<6 && Integer.parseInt(txt_signalcommand.getText())>0) {
                boolean ok=true;
                if(myinexrec==-1) {
                    SignalsFile rsf=new SignalsFile();
                    ReadFile rf=new ReadFile(myFilename,rsf.Get_SignalsFile_Size());
                    for(int x=1;x<=rf.NumOfRecords();x++) {
                        rsf.Set_SignalsFile_Bytes(rf.Read(x));
                        if(Byte.parseByte(choice_signaltype.getSelectedItem())==rsf.Get_Signal_Type() && Integer.parseInt(txt_signalcommand.getText())==rsf.Get_Signal_Command()) {
                            ok=false;
                            label_alert.setText("Signal Type and Command Signal is allready used");
                        }
                        if(txt_description.getText().compareTo(rsf.Get_Desc_No_Space())==0 || txt_description.getText().compareToIgnoreCase("")==0) {
                            ok=false;
                            label_alert.setText("You can not used null Description or same Description with other command");
                        }
                    }
                    rf.Close();
                }
                if(ok) {
                    SignalsFile sf=new SignalsFile();
                    WriteFile wf=new WriteFile(myFilename);
                    sf.Set_Signal_Type(Byte.parseByte(choice_signaltype.getSelectedItem()));
                    sf.Set_Signal_Command(Integer.parseInt(txt_signalcommand.getText()));
                    sf.Set_Desc(txt_description.getText());
                    if(txt_classname.getText().compareToIgnoreCase("")==0) {
                        sf.Set_ClassName("None");
                    }
                    else {
                        sf.Set_ClassName(txt_classname.getText());
                    }
                    sf.Set_Parameters(Byte.parseByte(choice_parameters.getSelectedItem()));
                    if(txt_parameter1name.getText().compareTo("")==0) {
                        txt_parameter1name.setText("None");
                    }
                    if(txt_parameter2name.getText().compareTo("")==0) {
                        txt_parameter2name.setText("None");
                    }
                    sf.Set_Parameter1Name(txt_parameter1name.getText());
                    sf.Set_Parameter2Name(txt_parameter2name.getText());
                    sf.Set_TypeOfParameter1((byte)choice_top1.getSelectedIndex());
                    sf.Set_TypeOfParameter2((byte)choice_top2.getSelectedIndex());
                    sf.Set_UsedCommand(choice_us.getSelectedItem());
                    sf.Set_Visible(Byte.parseByte(choice_visible.getSelectedItem()));
                    sf.Set_ParameterClass(txt_parameterclass.getText());
                    if(myinexrec==-1) {
                        wf.Write(sf.Get_SignalsFile_Byte());
                        if(Byte.parseByte(choice_signaltype.getSelectedItem())==3) {
                            SignalsFile sf1=new SignalsFile();
                            sf1.Set_Visible((byte)0);
                            sf1.Set_Signal_Type((byte)4);
                            sf1.Set_Signal_Command(Integer.parseInt(txt_signalcommand.getText()));
                            sf1.Set_Desc("Ret_" + txt_description.getText());
                            sf1.Set_ClassName("None");
                            sf1.Set_Parameters((byte)0);
                            sf1.Set_Parameter1Name("None");
                            sf1.Set_Parameter2Name("None");
                            sf1.Set_TypeOfParameter1((byte)choice_top1.getSelectedIndex());
                            sf1.Set_TypeOfParameter2((byte)choice_top2.getSelectedIndex());
                            
                            sf1.Set_ParameterClass("None");
                            if (choice_us.getSelectedItem().compareTo("Both")==0) {
                                sf1.Set_UsedCommand(choice_us.getSelectedItem());
                            }
                            if (choice_us.getSelectedItem().compareTo("Client")==0) {
                                sf1.Set_UsedCommand("Server");
                            }
                            if (choice_us.getSelectedItem().compareTo("Server")==0) {
                                sf1.Set_UsedCommand("Client");
                            }
                            
                            wf.Write(sf1.Get_SignalsFile_Byte());
                        }
                        
                    }
                    else {
                        wf.Edit(sf.Get_SignalsFile_Byte(),myinexrec,sf.Get_SignalsFile_Size());
                    }
                    wf.Close();
                    mypf.showsignals();
                    Loading=false;
                    this.hide();
                    this.disable();
                }
            }
            else {
                label_alert.setText("Signal Type is a number between 3-5 and ");
                label_alert2.setText("Command Signal is a number between 1-2.147.483.647");
            }
        }
        catch(Exception e) {
            label_alert.setText("Signal Type is a number between 3-5 and ");
            label_alert2.setText("Command Signal is a number between 1-2.147.483.647");
        }
        
    }//GEN-LAST:event_btn_addActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        mypf.showsignals();
        Loading=false;
        this.hide();
        this.disable();
    }//GEN-LAST:event_exitForm
    
    
    private void checkchar(java.awt.event.KeyEvent evt,boolean NoSpace,boolean OnlyNumber,java.awt.TextField textfield,int MaxNumOfChar) {
        if(textfield.getText().length()>=MaxNumOfChar) {
            if( evt.getKeyChar()!='') {
                char Null;
                Null=Character.NON_SPACING_MARK;
                evt.setKeyChar(Null);
            }
        }
        if(NoSpace) {
            if( evt.getKeyCode()==evt.VK_SPACE) {
                char Null;
                Null=Character.NON_SPACING_MARK;
                evt.setKeyChar(Null);
            }
        }
        if(OnlyNumber) {
            
            if(!Character.isDigit(evt.getKeyChar())) {
                if( evt.getKeyChar()!='') {
                    char Null;
                    Null=Character.NON_SPACING_MARK;
                    evt.setKeyChar(Null);
                }
            }
        }
        else {
            if(!Character.isLetter(evt.getKeyChar()) && !Character.isDigit(evt.getKeyChar())) {
                boolean mybool=false;
                if(evt.getKeyChar()=='' || evt.getKeyChar()==' ')
                {mybool=true;}
                else{mybool=false;}
                if( NoSpace && mybool ) {
                    char Null;
                    Null=Character.NON_SPACING_MARK;
                    evt.setKeyChar(Null);
                }
            }
        }
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button btn_add;
    private java.awt.Button btn_cancel;
    private java.awt.Choice choice_parameters;
    private java.awt.Choice choice_signaltype;
    private java.awt.Choice choice_top1;
    private java.awt.Choice choice_top2;
    private java.awt.Choice choice_us;
    private java.awt.Choice choice_visible;
    private java.awt.Label label1;
    private java.awt.Label label10;
    private java.awt.Label label11;
    private java.awt.Label label12;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.Label label5;
    private java.awt.Label label6;
    private java.awt.Label label7;
    private java.awt.Label label8;
    private java.awt.Label label9;
    private java.awt.Label label_alert;
    private java.awt.Label label_alert2;
    private java.awt.TextField txt_classname;
    private java.awt.TextField txt_description;
    private java.awt.TextField txt_parameter1name;
    private java.awt.TextField txt_parameter2name;
    private java.awt.TextField txt_parameterclass;
    private java.awt.TextField txt_signalcommand;
    // End of variables declaration//GEN-END:variables
    
}
