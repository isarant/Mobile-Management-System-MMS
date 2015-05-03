
package MMSServer;
import java.lang.ClassNotFoundException;
import java.lang.InstantiationException;
import java.lang.IllegalAccessException;
import java.lang.Class;
import MMSServer.SignalsFile;

public class Send_Command extends javax.swing.JFrame implements Runnable
{
    private static boolean Loading=false;
    private String myFilename;
    private SocConnect sc;
    private int Scenario;
    private int SocConnectID;
    private byte RecieverID;
    private byte SenderID;
    private byte SignalType;
    private int SignalCommand;
    private boolean paronbyte;
    private static Parameters Parameter;
    private static Parameters_hlp Parameter2;
    private byte typeofParameter1,typeofParameter2;
    
    public Send_Command() {
        initComponents();
    }
     public boolean isLoading()
    {
        return Loading;
    }
    
     
     public void init(String Filename,int Scenario,byte RecieverID,byte SenderID,SocConnect sc,int SocConnectID)
    {
        Loading=true;
        this.sc=sc;
        this.SocConnectID=SocConnectID;
        this.Scenario=Scenario;
        this.RecieverID=RecieverID;
        this.SenderID=SenderID;
        myFilename=Filename;
        label_clientid.setText(String.valueOf(RecieverID));
        label_scenario.setText(String.valueOf(Scenario));
        label_par1.setVisible(false);
        txt_par1.setVisible(false);
        label_par2.setVisible(false);
        txt_par2.setVisible(false);
        SignalsFile sf=new SignalsFile();
        ControlProtocol cp=new ControlProtocol();
       for(int z=0;z<cp.sizeArray();z++)
        {
             if(cp.signalsfile[z].Get_Visible()==(byte)1 )
            {
               try
               {
	   	    if(cp.signalsfile[z].Get_UsedCommand_No_Space().compareTo("Client")!=0)
                    {
                        list_commands.add(cp.signalsfile[z].Get_Desc_No_Space());
                    }
               }
               catch(Exception e)
               {}
           }
            
        }
       
        ReadFile rf=new ReadFile("./System/"+SenderID+"_"+label_scenario.getText()+myFilename,sf.Get_SignalsFile_Size()); 
        for(int x=1;x<=rf.NumOfRecords();x++)
        {
            sf.Set_SignalsFile_Bytes(rf.Read(x));
            if(sf.Get_Signal_Type()==3 || sf.Get_Signal_Type()==5)
            {
                if(sf.Get_UsedCommand().compareTo("Client")!=0)
                {
                    list_commands.add(sf.Get_Desc_No_Space());
                    
                }
            }
            
        }
        rf.Close();
    }
   
    private void initComponents() {//GEN-BEGIN:initComponents
        list_commands = new java.awt.List();
        btn_sendcommand = new java.awt.Button();
        btn_close = new java.awt.Button();
        label1 = new java.awt.Label();
        label_scenario = new java.awt.Label();
        label_par1 = new java.awt.Label();
        txt_par1 = new java.awt.TextField();
        label_par2 = new java.awt.Label();
        txt_par2 = new java.awt.TextField();
        label2 = new java.awt.Label();
        label_clientid = new java.awt.Label();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setTitle("Send Signal");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        list_commands.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                list_commandsActionPerformed(evt);
            }
        });
        list_commands.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                list_commandsMouseClicked(evt);
            }
        });

        getContentPane().add(list_commands, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 210, 130));

        btn_sendcommand.setActionCommand("btn_send command");
        btn_sendcommand.setLabel("Send Command");
        btn_sendcommand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sendcommandActionPerformed(evt);
            }
        });

        getContentPane().add(btn_sendcommand, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 280, 120, -1));

        btn_close.setLabel("Close");
        btn_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_closeActionPerformed(evt);
            }
        });

        getContentPane().add(btn_close, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, 120, -1));

        label1.setText("Scenarion");
        getContentPane().add(label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        getContentPane().add(label_scenario, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 30, -1));

        getContentPane().add(label_par1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 210, -1));

        txt_par1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_par1ActionPerformed(evt);
            }
        });
        txt_par1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_par1KeyTyped(evt);
            }
        });

        getContentPane().add(txt_par1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 210, -1));

        getContentPane().add(label_par2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 210, -1));

        txt_par2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_par2KeyTyped(evt);
            }
        });

        getContentPane().add(txt_par2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 210, -1));

        label2.setText("Client ID :\n");
        getContentPane().add(label2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        getContentPane().add(label_clientid, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 30, -1));

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-265)/2, (screenSize.height-399)/2, 265, 399);
    }//GEN-END:initComponents

    private void txt_par2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_par2KeyTyped
         if(typeofParameter2==2)
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
        if(typeofParameter2==3)
        {
          if(!Character.isDigit(evt.getKeyChar()) || evt.getKeyChar()!=',')
          {
              if( evt.getKeyChar()!='' )
              {
                char Null;
                Null=Character.NON_SPACING_MARK;
                evt.setKeyChar(Null);
              }
          }
    
        }
    }//GEN-LAST:event_txt_par2KeyTyped

    private void txt_par1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_par1KeyTyped
         if(typeofParameter1==2)
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
        if(typeofParameter1==3)
        {
          if(!Character.isDigit(evt.getKeyChar()) || evt.getKeyChar()!=',')
          {
              if( evt.getKeyChar()!='' )
              {
                char Null;
                Null=Character.NON_SPACING_MARK;
                evt.setKeyChar(Null);
              }
          }
    
        }
        
    }//GEN-LAST:event_txt_par1KeyTyped

    private void txt_par1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_par1ActionPerformed
      
    }//GEN-LAST:event_txt_par1ActionPerformed

    private void list_commandsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_list_commandsMouseClicked
    label_par1.setVisible(false);
    txt_par1.setVisible(false);
    label_par2.setVisible(false);
    txt_par2.setVisible(false);
    boolean skip=false;
    paronbyte=false;
    if(list_commands.getSelectedIndex()>-1)
    {
       ControlProtocol cp=new ControlProtocol();
       for(int z=0;z<cp.sizeArray();z++)
        {
            if(cp.signalsfile[z].Get_Desc_No_Space().compareTo(list_commands.getSelectedItem())==0)
            {  
               if(cp.signalsfile[z].Get_ParameterClass_No_Space().compareTo("None")==0 || cp.signalsfile[z].Get_ParameterClass_No_Space().compareTo("")==0)
                {
                    typeofParameter1=cp.signalsfile[z].Get_TypeOfParameter1();
                    typeofParameter2=cp.signalsfile[z].Get_TypeOfParameter2();
                       
                    if(cp.signalsfile[z].Get_Parametrs()==(byte)0)
                    {
                        label_par1.setVisible(false);
                        txt_par1.setVisible(false);
                        label_par2.setVisible(false);
                        txt_par2.setVisible(false);
                        label_par1.setText("");
                        label_par2.setText("");
                        txt_par1.setText("");
                        txt_par2.setText("");
                    }
                    if(cp.signalsfile[z].Get_Parametrs()==(byte)1)
                    {
                        label_par1.setVisible(true);
                        txt_par1.setVisible(true);
                        label_par2.setVisible(false);
                        txt_par2.setVisible(false);
                        label_par1.setText(cp.signalsfile[z].Get_Parameter1Name());
                        label_par2.setText("");
                        txt_par1.setText("");
                        txt_par2.setText("");
                    }
                    if(cp.signalsfile[z].Get_Parametrs()==(byte)2)
                    {
                        label_par1.setVisible(true);
                        txt_par1.setVisible(true);
                        label_par2.setVisible(true);
                        txt_par2.setVisible(true);
                        label_par1.setText(cp.signalsfile[z].Get_Parameter1Name());
                        label_par2.setText(cp.signalsfile[z].Get_Parameter2Name());
                        txt_par1.setText("");
                        txt_par2.setText("");
                    }
                }
                else
                {
                   try
                    {
                        Parameter=new Parameters();
                        Parameter2=new Parameters_hlp(1);
                        String par2="";
                        Parameter2.Set_Parameter(0,cp.signalsfile[z].Get_Desc_No_Space(),par2);
                        Class t = Class.forName("MMSServer.SignalProcedures."+cp.signalsfile[z].Get_ParameterClass_No_Space());
                        Object SignalProcess= t.newInstance();
                    }
                    catch(ClassNotFoundException e)
                    {}
                    catch(InstantiationException e)
                    {}
                    catch(IllegalAccessException e)
                    {}
                    catch(Exception e)
                    {}
                    paronbyte=true;
               }    
               SignalType=cp.signalsfile[z].Get_Signal_Type();
               SignalCommand=cp.signalsfile[z].Get_Signal_Command();
               skip=true;
               break;
              }

       }
       if(!skip)
       {
        SignalsFile sf=new SignalsFile();
        ReadFile rf=new ReadFile("./System/"+SenderID+"_"+label_scenario.getText()+myFilename,sf.Get_SignalsFile_Size()); 
        for(int x=1;x<=rf.NumOfRecords();x++)
        {
            sf.Set_SignalsFile_Bytes(rf.Read(x));
            if(sf.Get_Desc_No_Space().compareTo(list_commands.getSelectedItem())==0)
            {
                if(sf.Get_ParameterClass_No_Space().compareTo("None")==0 || sf.Get_ParameterClass_No_Space().compareTo("")==0)
                {
                   typeofParameter1=sf.Get_TypeOfParameter1();
                   typeofParameter2=sf.Get_TypeOfParameter2();
                     
                    if(sf.Get_Parametrs()==(byte)0)
                    {
                        label_par1.setVisible(false);
                        txt_par1.setVisible(false);
                        label_par2.setVisible(false);
                        txt_par2.setVisible(false);
                        label_par1.setText("");
                        label_par2.setText("");
                        txt_par1.setText("");
                        txt_par2.setText("");
                    }
                    if(sf.Get_Parametrs()==(byte)1)
                    {
                        label_par1.setVisible(true);
                        txt_par1.setVisible(true);
                        label_par2.setVisible(false);
                        txt_par2.setVisible(false);
                        label_par1.setText(sf.Get_Parameter1Name());
                        label_par2.setText("");
                        txt_par1.setText("");
                        txt_par2.setText("");
                    }
                    if(sf.Get_Parametrs()==(byte)2)
                    {
                        label_par1.setVisible(true);
                        txt_par1.setVisible(true);
                        label_par2.setVisible(true);
                        txt_par2.setVisible(true);
                        label_par1.setText(sf.Get_Parameter1Name());
                        label_par2.setText(sf.Get_Parameter2Name());
                        txt_par1.setText("");
                        txt_par2.setText("");
                    }
                }
                 else
                {
                   try
                    {
                        Parameter=new Parameters();
                        Parameter2=new Parameters_hlp(1);
                        String par2="";
                        Parameter2.Set_Parameter(0,sf.Get_Desc_No_Space(),par2);
                        Class t = Class.forName("MMSServer.SignalProcedures."+sf.Get_ParameterClass_No_Space());
                        Object SignalProcess= t.newInstance();
                    }
                    catch(ClassNotFoundException e)
                    {}
                    catch(InstantiationException e)
                    {}
                    catch(IllegalAccessException e)
                    {}
                    catch(Exception e)
                    {}
                    paronbyte=true;
               }     
                SignalType=sf.Get_Signal_Type();
                SignalCommand=sf.Get_Signal_Command();
                break;
              }
          }
            rf.Close();
        }
       }
    
    }//GEN-LAST:event_list_commandsMouseClicked

    private void list_commandsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_list_commandsActionPerformed
    

    }//GEN-LAST:event_list_commandsActionPerformed

    private void btn_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_closeActionPerformed
        Loading=false;
        this.hide();
        this.disable();
    }//GEN-LAST:event_btn_closeActionPerformed
public void run()
{
      synchronized(sc)
      {
        Signals singnals=new Signals(SenderID);
        try
        {
             if(paronbyte)
            {
                sc.Send(singnals.GeneralSignal(RecieverID,Scenario,SignalType,SignalCommand,(byte)1,Parameter.Get_Parameter(0).Ge_Parameter1(),Parameter.Get_Parameter(0).Ge_Parameter2()),SocConnectID);
            }
            else
            {
                sc.Send(singnals.GeneralSignal(RecieverID,Scenario,SignalType,SignalCommand,(byte)1,txt_par1.getText().getBytes(),txt_par2.getText().getBytes()),SocConnectID);
            }
       }
       catch(Exception e)
       {}
     }
}
    private void btn_sendcommandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sendcommandActionPerformed
        Thread thread=new Thread(this);
        thread.start();
    
    }//GEN-LAST:event_btn_sendcommandActionPerformed
    
   
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        Loading=false;
        this.hide();
        this.disable();
    }//GEN-LAST:event_exitForm
   public void CloseForm()
   {
        Loading=false;
        this.hide();
        this.disable();
   }
   public byte Get_ClientId()
   {
       return RecieverID;
   }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button btn_close;
    private java.awt.Button btn_sendcommand;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label_clientid;
    private java.awt.Label label_par1;
    private java.awt.Label label_par2;
    private java.awt.Label label_scenario;
    private java.awt.List list_commands;
    private java.awt.TextField txt_par1;
    private java.awt.TextField txt_par2;
    // End of variables declaration//GEN-END:variables

}
