
package MMSServer;

 class Password_File_Form extends javax.swing.JFrame {
    
    private ReadFile rf;
    private PaswdFile pf;
    private int recindex=0;
    private static boolean Loading=false;
    private PasswordlForm prevform;
    
    public Password_File_Form() {
         
    }
    public boolean isLoading()
    {
        return Loading;
    }
    public void init(PasswordlForm PrevForm,int index)
    {
         Loading=true;
         prevform=PrevForm;
         initComponents();
         txt_clientid.setText(""); 
         txt_scenarioid.setText(""); 
         txt_username.setText("");
         txtpassword.setText("");
         txt_keynum.setText("");
         recindex=index;
         if(recindex>0)
         {showdata(recindex);}
       
    }
    
    private void showdata(int recnum)
    {
         pf=new PaswdFile();
         rf=new ReadFile("./System/paswdf.mms",pf.Get_Size());
         if(rf.NumOfRecords()>=recnum && recnum>0 )
        {
            pf.Set_PaswdFile_Byte(rf.Read(recnum));
            byte s=pf.Get_ClientID();
            String ss=String.valueOf(s);
            txt_clientid.setText(ss); 
            txt_scenarioid.setText(String.valueOf(pf.Get_Scenario())); 
            txt_username.setText(pf.Get_UserName_No_Space());
            txtpassword.setText(pf.Get_Passwd_No_Space());
            txt_keynum.setText(String.valueOf(pf.Get_KeyNum()));
            recindex=recnum;
            label_alert.setText("");
            rf.Close();
        }
   }
    
    private void initclose()
    {
        prevform.showpass();
        Loading=false;
        this.hide();
        this.disable();
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();
        label4 = new java.awt.Label();
        txt_clientid = new java.awt.TextField();
        txt_scenarioid = new java.awt.TextField();
        txt_username = new java.awt.TextField();
        txtpassword = new java.awt.TextField();
        btn_close = new java.awt.Button();
        btn_savenewrec = new java.awt.Button();
        txt_keynum = new java.awt.TextField();
        label5 = new java.awt.Label();
        label_alert = new java.awt.Label();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setTitle("PassWords");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        label1.setText("Cliend ID");
        getContentPane().add(label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 70, -1));

        label2.setText("Scenario ID");
        getContentPane().add(label2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        label3.setText("User Name");
        getContentPane().add(label3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        label4.setText("PassWord");
        getContentPane().add(label4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        txt_clientid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_clientidKeyTyped(evt);
            }
        });

        getContentPane().add(txt_clientid, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 80, 20));

        txt_scenarioid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_scenarioidKeyTyped(evt);
            }
        });

        getContentPane().add(txt_scenarioid, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 80, -1));

        txt_username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_usernameKeyTyped(evt);
            }
        });

        getContentPane().add(txt_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 80, -1));

        txtpassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtpasswordKeyTyped(evt);
            }
        });

        getContentPane().add(txtpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 80, -1));

        btn_close.setLabel("Close");
        btn_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_closeActionPerformed(evt);
            }
        });

        getContentPane().add(btn_close, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, 90, -1));

        btn_savenewrec.setLabel("Save ");
        btn_savenewrec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_savenewrecActionPerformed(evt);
            }
        });

        getContentPane().add(btn_savenewrec, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 80, -1));

        txt_keynum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_keynumKeyTyped(evt);
            }
        });

        getContentPane().add(txt_keynum, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 170, 80, -1));

        label5.setText("Key Number");
        getContentPane().add(label5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 70, -1));

        label_alert.setForeground(new java.awt.Color(255, 51, 51));
        getContentPane().add(label_alert, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 30));

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-266)/2, (screenSize.height-279)/2, 266, 279);
    }//GEN-END:initComponents

    private void txt_keynumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_keynumKeyTyped
       checkchar(evt,true,true,txt_keynum,10);
    }//GEN-LAST:event_txt_keynumKeyTyped

    private void txtpasswordKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpasswordKeyTyped
       checkchar(evt,true,false,txtpassword,10);
    }//GEN-LAST:event_txtpasswordKeyTyped

    private void txt_usernameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_usernameKeyTyped
        checkchar(evt,true,false,txt_username,10);
    }//GEN-LAST:event_txt_usernameKeyTyped

    private void txt_clientidKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_clientidKeyTyped
        checkchar(evt,true,true,txt_clientid,3);
    }//GEN-LAST:event_txt_clientidKeyTyped

    private void txt_scenarioidKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_scenarioidKeyTyped
        checkchar(evt,true,true,txt_scenarioid,10);
    }//GEN-LAST:event_txt_scenarioidKeyTyped

    private void btn_savenewrecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_savenewrecActionPerformed
     if(doublerec())
     {
       boolean ok=true;
       PaswdFile pf2=new PaswdFile();
       ReadFile rf2=new ReadFile("./System/paswdf.mms",pf2.Get_Size());
   
       try{
         if(Byte.parseByte(txt_clientid.getText())>0 && Byte.parseByte(txt_clientid.getText())<=255)
         {
               pf2.Set_ClientID(Byte.parseByte(txt_clientid.getText()));
         }
         else
         {
             ok=false;
             label_alert.setText("Client ID is a number between 1-255");
         }
       }
       catch(Exception e)
       {
           ok=false;
             label_alert.setText("Client ID is a number between 1-255");
       }
      try
      {
         if(Integer.parseInt(txt_scenarioid.getText())>0 && Integer.parseInt(txt_scenarioid.getText())<=2147483647)
         {
              pf2.Set_Scenario(Integer.parseInt(txt_scenarioid.getText()));
         }
          else
         {
            ok=false;
            label_alert.setText("Scenario ID is a number between 1-2.147.483.647");
         }
      }
      catch(Exception e)
      {
            ok=false;
            label_alert.setText("Scenario ID is a number between 1-2.147.483.647");
      }
      if(txt_username.getText().compareTo("")!=0)
       {
           pf2.Set_UserName(txt_username.getText().toLowerCase());
       }
       else
       {
           ok=false;
           label_alert.setText("UserName can not be Null, 10 Characters");
       }
       if(txtpassword.getText().compareTo("")!=0)
       {
           pf2.Set_Passwd(txtpassword.getText().toLowerCase());
       }
        else
       {
           ok=false;
           label_alert.setText("PassWord can not be Null, 10 Characters");
       }
     try
     {
         if(Integer.parseInt(txt_keynum.getText())>0 && Integer.parseInt(txt_keynum.getText())<=2147483647)
        {
              pf2.Set_KeyNum(Integer.parseInt(txt_keynum.getText()));
        }
        else
        {
              ok=false;
              label_alert.setText("Key Number is a number between 1-2.147.483.647");
       }
     }
     catch(Exception e)
     {
              ok=false;
              label_alert.setText("Key Number is a number between 1-2.147.483.647");
     }

       if(ok)
       {
        WriteFile wf=new WriteFile("./System/paswdf.mms");
        if(recindex>0)
        {
                  wf.Edit(pf2.Get_PaswdFile_Byte(),recindex,pf2.Get_Size());
        
        }
        else
        {
                wf.Write(pf2.Get_PaswdFile_Byte());
        }
        wf. Close();
        
       }
       initclose();
     }
       
    }//GEN-LAST:event_btn_savenewrecActionPerformed

    private void btn_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_closeActionPerformed
      initclose();
    
      
    }//GEN-LAST:event_btn_closeActionPerformed

   
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        initclose();
    }//GEN-LAST:event_exitForm
   
     private boolean doublerec()
     {
         boolean ok=true;
         PaswdFile rpf=new PaswdFile();
         ReadFile rf2=new ReadFile("./System/paswdf.mms",rpf.Get_Size());
         for(int x=1;x<=rf2.NumOfRecords();x++)
         {
            rpf.Set_PaswdFile_Byte(rf2.Read(x));
            try
            {
                if(rpf.Get_ClientID()==Byte.parseByte(txt_clientid.getText()) && rpf.Get_Scenario()==Integer.parseInt(txt_scenarioid.getText()))
                {
                     if(recindex!=x)
                     {
                        ok=false;
                        label_alert.setText("Cliend ID is allready used");
                        break;
                     }
                }
                if(rpf.Get_UserName_No_Space().compareTo(txt_username.getText())==0)
                {
                     if(recindex!=x)
                     {
                        ok=false;
                        label_alert.setText("User Name is allready used");
                        break;
                      }
                }
            }
            catch(Exception e)
            {}
         }
         rf2.Close();
        return ok;
              
     }
    
    private void checkchar(java.awt.event.KeyEvent evt,boolean NoSpace,boolean OnlyNumber,java.awt.TextField textfield,int MaxNumOfChar)
    {
      if(textfield.getText().length()>=MaxNumOfChar)
      {     
         if( evt.getKeyChar()!='')
         {
            char Null;
            Null=Character.NON_SPACING_MARK;
            evt.setKeyChar(Null);
         }
      }
      if(NoSpace)
      {
          if( evt.getKeyCode()==evt.VK_SPACE)
          {
            char Null;
            Null=Character.NON_SPACING_MARK;
            evt.setKeyChar(Null);
           }
      }
      if(OnlyNumber)
      {
           
          if(!Character.isDigit(evt.getKeyChar()))
          {
              if( evt.getKeyChar()!='')
              {
                char Null;
                Null=Character.NON_SPACING_MARK;
                evt.setKeyChar(Null);
              }
          }
      }
      else
      {
         if(!Character.isLetter(evt.getKeyChar()) && !Character.isDigit(evt.getKeyChar()))
          {
              if( evt.getKeyChar()!='')
              {
                char Null;
                Null=Character.NON_SPACING_MARK;
                evt.setKeyChar(Null);
              }
          }
      }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button btn_close;
    private java.awt.Button btn_savenewrec;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.Label label5;
    private java.awt.Label label_alert;
    private java.awt.TextField txt_clientid;
    private java.awt.TextField txt_keynum;
    private java.awt.TextField txt_scenarioid;
    private java.awt.TextField txt_username;
    private java.awt.TextField txtpassword;
    // End of variables declaration//GEN-END:variables
    
}
