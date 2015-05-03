
package MMSServer;

 class ProtocolForm extends javax.swing.JFrame {
    private String myFilename;
    private static boolean Loading=false;
    private byte ServerID;
    
    public ProtocolForm() {
      
    }
    
    public boolean isLoading()
    {
        return Loading;
    }
    
    public void init(String Filename,int Scenarion,byte ServerID)
    {
        Loading=true;
        myFilename=Filename;
        this.ServerID=ServerID;
        initComponents();
        txt_scenario.setText(String.valueOf(Scenarion));
        showsignals();
    }
    protected void showsignals()
    {
        this.setTitle("Signals " +txt_scenario.getText()+myFilename );
        list1.clear();
      
        SignalsFile sf=new SignalsFile();
        ReadFile rf=new ReadFile("./System/"+ServerID+"_"+txt_scenario.getText()+myFilename,sf.Get_SignalsFile_Size()); 
        for(int x=1;x<=rf.NumOfRecords();x++)
        {
            sf.Set_SignalsFile_Bytes(rf.Read(x));
            String s=" " +sf.Get_Visible()+" ST " + sf.Get_Signal_Type();
            for(int q=0;q<=7-s.length();q++){s=s+ " ";}
            s=s+" CT ";
            s=s + sf.Get_Signal_Command();
            for(int q=0;q<=21-s.length();q++){s=s+ " ";}
            s=s+" Desc ";
            s=s+ sf.Get_Desc();
            for(int q=0;q<=28-s.length();q++){s=s+ " ";}
            s=s+"   CN ";
            s=s+ sf.Get_ClassName();
            s=s+"   NP ";
            s=s+ sf.Get_Parametrs();
            s=s+"   P1N ";
            s=s+ sf.Get_Parameter1Name();
            s=s+"   ToP1 ";
            s=s+ sf.Get_TypeOfParameter1_String();
            s=s+"   P2N ";
            s=s+ sf.Get_Parameter2Name();
            s=s+"   ToP2 ";
            s=s+ sf.Get_TypeOfParameter2_String();
            s=s+"   US ";
            s=s+ sf.Get_UsedCommand();
            s=s+"   PC ";
            s=s+ sf.Get_ParameterClass();
            
            list1.addItem(s);
        }
        rf.Close();
         ControlProtocol cp=new ControlProtocol();
       for(int z=0;z<cp.sizeArray();z++)
        {
            String s=" " +cp.signalsfile[z].Get_Visible()+" ST " + cp.signalsfile[z].Get_Signal_Type();
            for(int q=0;q<=7-s.length();q++){s=s+ " ";}
            s=s+" CT ";
            s=s + cp.signalsfile[z].Get_Signal_Command();
            for(int q=0;q<=21-s.length();q++){s=s+ " ";}
            s=s+" Desc ";
            s=s+ cp.signalsfile[z].Get_Desc();
            for(int q=0;q<=28-s.length();q++){s=s+ " ";}
            s=s+"   CN ";
            s=s+ cp.signalsfile[z].Get_ClassName();
            s=s+"   NP ";
            s=s+ cp.signalsfile[z].Get_Parametrs();
            s=s+"   P1N ";
            s=s+ cp.signalsfile[z].Get_Parameter1Name();
            s=s+"   P2N ";
            s=s+ cp.signalsfile[z].Get_Parameter2Name();
            s=s+"   US ";
            s=s+ cp.signalsfile[z].Get_UsedCommand();
            s=s+"   PC ";
            s=s+ cp.signalsfile[z].Get_ParameterClass();
            
            list1.addItem(s);
        }
    }

    private void initComponents() {//GEN-BEGIN:initComponents
        list1 = new java.awt.List();
        btn_close = new java.awt.Button();
        btn_add = new java.awt.Button();
        btn_delete = new java.awt.Button();
        txt_scenario = new java.awt.TextField();
        label1 = new java.awt.Label();
        btn_changescenario = new java.awt.Button();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();
        label4 = new java.awt.Label();
        label5 = new java.awt.Label();
        label6 = new java.awt.Label();
        label7 = new java.awt.Label();
        label8 = new java.awt.Label();
        label9 = new java.awt.Label();
        label10 = new java.awt.Label();
        label11 = new java.awt.Label();
        label12 = new java.awt.Label();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setTitle("Signals");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        list1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                list1ActionPerformed(evt);
            }
        });

        getContentPane().add(list1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 740, 230));

        btn_close.setLabel("Close");
        btn_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_closeActionPerformed(evt);
            }
        });

        getContentPane().add(btn_close, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 310, 60, -1));

        btn_add.setLabel("Add");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        getContentPane().add(btn_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, 60, -1));

        btn_delete.setLabel("Delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        getContentPane().add(btn_delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 280, 60, -1));

        txt_scenario.setText("1");
        txt_scenario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_scenarioActionPerformed(evt);
            }
        });
        txt_scenario.addTextListener(new java.awt.event.TextListener() {
            public void textValueChanged(java.awt.event.TextEvent evt) {
                changetxt(evt);
            }
        });

        getContentPane().add(txt_scenario, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 40, -1));

        label1.setText("Scenarion");
        getContentPane().add(label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, -1, -1));

        btn_changescenario.setLabel("Change");
        btn_changescenario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_changescenarioActionPerformed(evt);
            }
        });

        getContentPane().add(btn_changescenario, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, -1, -1));

        label2.setFont(new java.awt.Font("Dialog", 0, 10));
        label2.setText("ST=Signal Type\n");
        getContentPane().add(label2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));

        label3.setFont(new java.awt.Font("Dialog", 0, 10));
        label3.setText("CT=Command Type\n");
        getContentPane().add(label3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, -1, -1));

        label4.setFont(new java.awt.Font("Dialog", 0, 10));
        label4.setText("Desc=Command Description");
        getContentPane().add(label4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, -1, -1));

        label5.setFont(new java.awt.Font("Dialog", 0, 10));
        label5.setText("CN=Class Name\n");
        getContentPane().add(label5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, -1, -1));

        label6.setFont(new java.awt.Font("Dialog", 0, 10));
        label6.setText("NP=Number Of Parametre");
        getContentPane().add(label6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 280, -1, -1));

        label7.setFont(new java.awt.Font("Dialog", 0, 10));
        label7.setText("P1N=Parametre 1 Name");
        getContentPane().add(label7, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, -1, -1));

        label8.setFont(new java.awt.Font("Dialog", 0, 10));
        label8.setText("P2N=Parametre 2 Name\n");
        getContentPane().add(label8, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, -1, -1));

        label9.setFont(new java.awt.Font("Dialog", 0, 10));
        label9.setText("US=User ho Used Command");
        getContentPane().add(label9, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 340, -1, -1));

        label10.setFont(new java.awt.Font("Dialog", 0, 10));
        label10.setText("Signal type =3 (Send a signal and wait for request) ");
        getContentPane().add(label10, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 280, -1, -1));

        label11.setFont(new java.awt.Font("Dialog", 0, 10));
        label11.setText("Signal Type =4 (Requested signal from a signal type 3)");
        getContentPane().add(label11, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 300, -1, -1));

        label12.setFont(new java.awt.Font("Dialog", 0, 10));
        label12.setText("Signal type =5 (Send a signal without wait for request) ");
        getContentPane().add(label12, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 320, -1, -1));

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-783)/2, (screenSize.height-410)/2, 783, 410);
    }//GEN-END:initComponents

    private void btn_changescenarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_changescenarioActionPerformed
        showsignals();
    }//GEN-LAST:event_btn_changescenarioActionPerformed

    private void txt_scenarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_scenarioActionPerformed
            
    }//GEN-LAST:event_txt_scenarioActionPerformed

    private void changetxt(java.awt.event.TextEvent evt) {//GEN-FIRST:event_changetxt
        // Add your handling code here:
    }//GEN-LAST:event_changetxt

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
      SignalsFile sf=new SignalsFile();
      WriteFile wf=new WriteFile("./System/"+ServerID+"_"+txt_scenario.getText()+myFilename);
      wf.Delete(list1.getSelectedIndex()+1,sf.Get_SignalsFile_Size());
      wf.Close();
      showsignals();
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
         AddProtocol myAddProtocol=  new AddProtocol();
         if(!myAddProtocol.isLoading())
          {
           myAddProtocol.init("./System/"+ServerID+"_"+txt_scenario.getText()+myFilename,-1,this);
           myAddProtocol.show(true);
        }
         else
        {myAddProtocol.disable();}
     
    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_closeActionPerformed
        Loading=false;
        this.hide();
        this.disable();
        
    }//GEN-LAST:event_btn_closeActionPerformed

    private void list1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_list1ActionPerformed
        AddProtocol myAddProtocol=  new AddProtocol();
         if(!myAddProtocol.isLoading())
          {
           myAddProtocol.init("./System/"+ServerID+"_"+txt_scenario.getText()+myFilename,list1.getSelectedIndex()+1,this);
           myAddProtocol.show(true);
        }
         else
        {myAddProtocol.disable();} 
    }//GEN-LAST:event_list1ActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        Loading=false;
        this.hide();
        this.disable();
    }//GEN-LAST:event_exitForm
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button btn_add;
    private java.awt.Button btn_changescenario;
    private java.awt.Button btn_close;
    private java.awt.Button btn_delete;
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
    private java.awt.List list1;
    private java.awt.TextField txt_scenario;
    // End of variables declaration//GEN-END:variables
    
}
