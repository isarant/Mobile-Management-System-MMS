

package MMSServer;

import java.lang.Thread;
import java.lang.Runtime;
import java.lang.InterruptedException;
import java.awt.Component;
import java.io.File;
import java.util.Date;
import java.io.IOException;
import org.netbeans.lib.awtextra.AbsoluteConstraints;



public class MMSCentral extends javax.swing.JFrame implements Runnable {
    private MP mp;
    private SocConnect sc;
    private boolean showconnectionenable=true;
    private int countershowconnectionenable=0;
    private byte ServerID;
    private String ServerName,ProcessPath,ImagePath,SoundPath,VideoPath;
    protected static final int defaultbufsize=100000;
    private long waittime=((defaultbufsize/9600)*1000)*4;
    private long checktime=waittime*2;
    private static AnsSignal as;
    private Send_Command formsc;
    
    public MMSCentral() {
        make_folders();
        initComponents();
        as=new AnsSignal();
        ServerIDFile serveridfile=new ServerIDFile();
        ReadFile rf=new ReadFile("./System/serverid.mms",serveridfile.Get_ServerIDFile_Size());
        if(rf.NumOfRecords()>0) {
            serveridfile.Set_ServerIDFile_Bytes(rf.Read(1));
            ServerID=serveridfile.Get_ServerID();
            ServerName=serveridfile.Get_ServerName_No_Space();
            ImagePath=serveridfile.Get_ImagePath();
            SoundPath=serveridfile.Get_SoundPath();
            VideoPath=serveridfile.Get_VideoPath();
            rf.Close();
        }
        else {
            ServerIDForm sidf=new ServerIDForm();
            if(!sidf.isLoading()) {
                sidf.init();
                sidf.show(true);
            }
            else
            {sidf.disable();}
        }
        btn_show_command.setVisible(false);
        new Thread(this).start();
    }
    
    private void  make_folders() {
      /*  File fileMMS =new File("c:/MobileManageSystem");
        if(!fileMMS.isDirectory())
        {
            try
            {
                fileMMS.mkdir();
            }
            catch(Exception e)
            {}
        }*/
        File filesystem =new File("./System");
        if(!filesystem.isDirectory()) {
            try {
                filesystem.mkdir();
            }
            catch(Exception e)
            {e.printStackTrace();}
        }
        File fileexternalprocess =new File("./ExternalProcess");
        if(!fileexternalprocess.isDirectory()) {
            try {
                fileexternalprocess.mkdir();
            }
            catch(Exception e)
            {e.printStackTrace();}
        }
        File filetemp =new File("./Temp");
        if(!filetemp.isDirectory()) {
            try {
                filetemp.mkdir();
            }
            catch(Exception e)
            {e.printStackTrace();}
        }
        File filepictures =new File("./Pictures");
        if(!filepictures.isDirectory()) {
            try {
                filepictures.mkdir();
            }
            catch(Exception e)
            {e.printStackTrace();}
        }
        File filetransfer =new File("./TransferFiles");
        if(!filetransfer.isDirectory()) {
            try {
                filetransfer.mkdir();
            }
            catch(Exception e)
            {e.printStackTrace();}
        }
        File filevideos =new File("./Media");
        if(!filevideos.isDirectory()) {
            try {
                filevideos.mkdir();
            }
            catch(Exception e)
            {e.printStackTrace();}
        }
    }
    
    
    private void initComponents() {//GEN-BEGIN:initComponents
        label1 = new java.awt.Label();
        numofcon = new java.awt.TextField();
        btn_filepasswd = new java.awt.Button();
        btn_clrlist = new java.awt.Button();
        btn_Close = new java.awt.Button();
        conectedusers = new java.awt.List();
        incomemsg = new java.awt.List();
        list1 = new java.awt.List();
        btn_clientcontrosignal = new java.awt.Button();
        btn_closeconnection = new java.awt.Button();
        txt_serverid = new java.awt.Button();
        button1 = new java.awt.Button();
        btn_show_command = new java.awt.Button();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setTitle("MMS Central Form");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        label1.setText("Number of Connections");
        getContentPane().add(label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        getContentPane().add(numofcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 30, -1));

        btn_filepasswd.setLabel("File Password");
        btn_filepasswd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_filepasswdActionPerformed(evt);
            }
        });

        getContentPane().add(btn_filepasswd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 130, -1));

        btn_clrlist.setLabel("Clear List");
        btn_clrlist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clrlistActionPerformed(evt);
            }
        });

        getContentPane().add(btn_clrlist, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 260, -1, -1));

        btn_Close.setLabel("Close");
        btn_Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CloseActionPerformed(evt);
            }
        });

        getContentPane().add(btn_Close, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 410, 130, -1));

        conectedusers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conectedusersActionPerformed(evt);
            }
        });
        conectedusers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mousepressed(evt);
            }
        });

        getContentPane().add(conectedusers, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 650, 60));

        getContentPane().add(incomemsg, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 650, 110));

        getContentPane().add(list1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 650, 60));

        btn_clientcontrosignal.setLabel("Protocol Signals");
        btn_clientcontrosignal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clientcontrosignalActionPerformed(evt);
            }
        });

        getContentPane().add(btn_clientcontrosignal, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 360, 130, -1));

        btn_closeconnection.setLabel("Close Selected Connection");
        btn_closeconnection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_closeconnectionActionPerformed(evt);
            }
        });

        getContentPane().add(btn_closeconnection, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 110, -1, -1));

        txt_serverid.setLabel("Server ID");
        txt_serverid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_serveridActionPerformed(evt);
            }
        });

        getContentPane().add(txt_serverid, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 360, 130, -1));

        button1.setActionCommand("External Signa ");
        button1.setLabel(" External Signal ");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        getContentPane().add(button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 410, 130, -1));

        btn_show_command.setLabel("Send Command");
        btn_show_command.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_show_commandActionPerformed(evt);
            }
        });

        getContentPane().add(btn_show_command, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 50, 100, -1));

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-778)/2, (screenSize.height-496)/2, 778, 496);
    }//GEN-END:initComponents
    
    private void btn_show_commandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_show_commandActionPerformed
        if(conectedusers.getSelectedIndex()>-1) {
            int a=conectedusers.getSelectedItem().indexOf(":");
            int commandint=Integer.parseInt(conectedusers.getSelectedItem().substring(0,a));
            Session session= new Session();
            for(int x=0;x<session.Get_SizeOfSession();x++) {
                if(session.Get_Session(x).Get_SocConnectID()==commandint) {
                    formsc=new Send_Command();
                    if(!formsc.isLoading()) {
                        formsc.init("externalsignal.mms",session.Get_Session(x).Get_Scenario(),session.Get_Session(x).Get_UserID(),session.Get_Session(x).Get_ServerID(),sc,session.Get_Session(x).Get_SocConnectID());
                        formsc.show(true);
                    }
                    else
                    {formsc.disable();}
                }
            }
        }
    }//GEN-LAST:event_btn_show_commandActionPerformed
    
    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        ShowExteralSignalFile sidf=new ShowExteralSignalFile();
        if(!sidf.isLoading())
            
        {
            sidf.init();
            sidf.show(true);
        }
        else
        {sidf.disable();}
    }//GEN-LAST:event_button1ActionPerformed
    
    private void txt_serveridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_serveridActionPerformed
        ServerIDForm sidf=new ServerIDForm();
        if(!sidf.isLoading())
            
        {
            sidf.init();
            sidf.show(true);
        }
        else
        {sidf.disable();}
    }//GEN-LAST:event_txt_serveridActionPerformed
    
    private void mousepressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mousepressed
        showconnectionenable=false;
    }//GEN-LAST:event_mousepressed
    
    private void conectedusersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conectedusersActionPerformed
        
    }//GEN-LAST:event_conectedusersActionPerformed
    
    private void btn_closeconnectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_closeconnectionActionPerformed
        if(conectedusers.getSelectedIndex()>-1) {
            Session session= new Session();
            for(int x=0;x<session.Get_SizeOfSession();x++) {
                int a=conectedusers.getSelectedItem().indexOf(":");
                if(session.Get_Session(x).Get_SocConnectID()==Integer.parseInt(conectedusers.getSelectedItem().substring(0,a))) {
                    Signals singnals=new Signals(ServerID);
                    sc.Send(singnals.LogOff(session.Get_Session(x).Get_UserID(),session.Get_Session(x).Get_Scenario()),session.Get_Session(x).Get_SocConnectID());
                    deleteAnsSignal(session.Get_Session(x).Get_UserID(),session.Get_Session(x).Get_Scenario());
                    session.Delete_Session(x);
                    session.refreshSessionFile(sc);
                    break;
                }
            }
        }
    }//GEN-LAST:event_btn_closeconnectionActionPerformed
    
    private void btn_clientcontrosignalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clientcontrosignalActionPerformed
        ProtocolForm pf=new ProtocolForm();
        if(!pf.isLoading()) {
            pf.init("externalsignal.mms",1,ServerID);
            pf.show(true);
        }
        else
        {pf.disable();}
    }//GEN-LAST:event_btn_clientcontrosignalActionPerformed
    
    private void btn_filepasswdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_filepasswdActionPerformed
        PasswordlForm pff=new PasswordlForm();
        if(!pff.isLoading())
            
        {
            pff.init();
            pff.show(true);
        }
        else
        {pff.disable();}
        
    }//GEN-LAST:event_btn_filepasswdActionPerformed
    
    private void btn_clrlistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clrlistActionPerformed
        incomemsg.clear();
    }//GEN-LAST:event_btn_clrlistActionPerformed
    
    private void btn_CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CloseActionPerformed
        closeall();
    }//GEN-LAST:event_btn_CloseActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        closeall();
    }//GEN-LAST:event_exitForm
    
    private void closeall() {
        Session session= new Session();
        for(int x=0;x<session.Get_SizeOfSession();x++) {
            try {
                Signals singnals=new Signals(session.Get_Session(x).Get_ServerID());
                sc.Send(singnals.LogOff(session.Get_Session(x).Get_UserID(),session.Get_Session(x).Get_Scenario()),session.Get_Session(x).Get_SocConnectID());
                //sc.closeConnection(session.Get_Session(x).Get_SocConnectID());
                deleteAnsSignal(session.Get_Session(x).Get_UserID(),session.Get_Session(x).Get_Scenario());
                session.Delete_Session(x);
                session.refreshSessionFile(sc);
            }
            catch(Exception e)
            {}
        }
        System.exit(0);
    }
    
    public static void main(String args[]) {
        new MMSCentral().show();
    }
    
    public void run() {
        int showcount=0;
        MP mp=null;
        sc=new SocConnect(5001);
        this.setTitle("MMS Central Form " + sc.Get_Server_IP());
        while(true) {
            for(int clientsocketnum=0;clientsocketnum<sc.Get_Max_Number_Of_Connection();clientsocketnum++) {
                try {
                    mp=sc.Read(clientsocketnum);
                    if(mp!=null) {
                        ShowSignal(mp);
                        CheckIncomingSignals checkincomingsignals=new CheckIncomingSignals();
                        WriteIncomingSignal wis=new WriteIncomingSignal(mp);
                        wis.SaveSignal();
                        synchronized (checkincomingsignals) {
                            checkincomingsignals.init(sc,clientsocketnum,mp,ServerID);
                        }
                        Runtime rt=Runtime.getRuntime();
                        //System.out.println("Memory :" + rt.totalMemory());
                        RefreshSession(mp);
                        
                    }
                    showanssignal();
                    CheckForAliveConnections(sc);
                    CheckConnections(sc);
                    CheckSocketConnectinWithSessions(sc);
                   
                }
                catch(OutOfMemoryError e) {
                    System.out.println("OutOfMemoryError"+e.toString());
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
            ShowConnection();
            try {
                Thread.sleep(100);
            }
            catch(InterruptedException e)
            {}
         /*     if(sc.Get_Max_Number_Of_Connection()==0)
            {
                for(int x=0;x<as.Get_SizeOfAnsSignal();x++)
                 {
                      as.Delete_Signal(x);
                      showanssignal();
                  }
                  
         }
          * */  
      }
    }
    
    public void CheckForAliveConnections(SocConnect sc) {
        Signals signals=new Signals(ServerID);
        Session session=new Session();
        for(int x=0;x<session.Get_SizeOfSession();x++) {
            Date dt=new Date();
            if(session.Get_Session(x).Get_Last_Time()+checktime<dt.getTime()) {
                int a=session.Get_Session(x).Get_SocConnectID();
                sc.Send(signals.AreYouAlive(session.Get_Session(x).Get_UserID(),session.Get_Session(x).Get_Scenario()),a);
                session.Get_Session(x).Set_Last_Time(dt.getTime());
            }
        }
    }
    
    public void RefreshSession(MP mp) {
        Signals signals=new Signals((byte)1);
        Session session=new Session();
        for(int x=0;x<session.Get_SizeOfSession();x++) {
            Date dt=new Date();
            if(session.Get_Session(x).Get_UserID()==mp.Get_Sender()) {
                if(session.Get_Session(x).Get_Scenario()==mp.Get_Scenario()) {
                    session.Get_Session(x).Set_Last_Time(dt.getTime());
                    break;
                }
            }
        }
    }
    
    private synchronized void CheckConnections(SocConnect socconnection) {
       //Check if connection is not alive and delete this session
         Session session1= new Session();
        for(int socketnum=0;socketnum<socconnection.Get_Max_Number_Of_Connection();socketnum++) {
            if(!socconnection.Check_Connection(socketnum)) {
               
                for(int c=0;c<session1.Get_SizeOfSession();c++) {
                    if(session1.Get_Session(c).Get_SocConnectID()==socketnum) {
                         socconnection.closeConnection(session1.Get_Session(c).Get_SocConnectID());
                         deleteAnsSignal(session1.Get_Session(c).Get_UserID(),session1.Get_Session(c).Get_Scenario());
                        session1.Delete_Session(c);
                        session1.refreshSessionFile(socconnection);
                        break;
                    }
                }
                
            }
            
        }
        boolean ok=true;
        int var0k=0;
       //check if answaresignal have session if is have not delete anssignal
        for(int z=0;z<as.Get_SizeOfAnsSignal();z++) {
            var0k=z;
            for(int c=0;c<session1.Get_SizeOfSession();c++) {
                if(as.Get_Signal(z).Get_SenderID()==session1.Get_Session(c).Get_UserID()) {
                    
                    if(as.Get_Signal(z).Get_Scenario()==session1.Get_Session(c).Get_Scenario()) {
                       ok=false;
                        break;
                    }
                    else
                    {
                         if(as.Get_SizeOfAnsSignal()>0) {
                            as.Delete_Signal(z);
                            if(z<0){z=z-1;}
                            }
                    }
                }
            }
            if(!ok){break;}
        }
        //if answaresignal has sesssion
        if(!ok) {
            if(as.Get_Signal(var0k).Get_Signal_Type()==(byte)2 && as.Get_Signal(var0k).Get_Signal_Command()==3) {
                Date dt=new Date();
                if(as.Get_Signal(var0k).Get_Date_Time()+waittime<dt.getTime()) {
                    
                    for(int x=0;x<session1.Get_SizeOfSession();x++) {
                        if(as.Get_Signal(var0k).Get_SenderID()==session1.Get_Session(x).Get_UserID()) {
                            if( as.Get_Signal(var0k).Get_Scenario()==session1.Get_Session(x).Get_Scenario()) {
                                as.Delete_Signal(var0k);
                                Signals singnals=new Signals(ServerID);
                                sc.Send(singnals.LogOff(session1.Get_Session(x).Get_UserID(),session1.Get_Session(x).Get_Scenario()),session1.Get_Session(x).Get_SocConnectID());
                                sc.closeConnection(session1.Get_Session(x).Get_SocConnectID());
                                session1.Delete_Session(x);
                                session1.refreshSessionFile(sc);
                                break;
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    private void CheckSocketConnectinWithSessions(SocConnect socconnection)
    {
        
        Session session1= new Session();
        if(socconnection.Get_Max_Number_Of_Connection()>100)
        {
            for(int socketnum=0;socketnum<socconnection.Get_Max_Number_Of_Connection()-10;socketnum++) {
                boolean ok=false;    
                    for(int c=0;c<session1.Get_SizeOfSession();c++) {
                        if(session1.Get_Session(c).Get_SocConnectID()==socketnum) 
                        {
                          //  System.out.println("let" + socketnum);
                            ok=true;
                            break;
                        }
                    }
                    if(!ok)
                    {
                        try
                        {
                            socconnection.closeConnection(socketnum);
                            session1.refreshSessionFile(socconnection);
                         //   System.out.println("delete" + socketnum);
                        }
                        catch(Exception e)
                        {e.printStackTrace();}
                    }
            }
        }
    }
    
    private void ShowSignal(MP mp1) {
        
        
        Date date=new Date(mp1.Get_Date_Time());
          /*   String str="sender: " + mp1.Get_Sender() +
             " reciever: " + mp1.Get_Receiver() +
             " senario: " + mp1.Get_Scenario() +
             " type: " + mp1.Get_Signal_Type() +
             " command: " + mp1.Get_Signal_Command() +
             " priority: " + mp1.Get_Priority() +
             " date: " + date.toString() +
             " data 1: " + mp1.Get_Data1_As_String() +
             " data 2: " + mp1.Get_Data2_As_String();
           */
        if( mp1.Get_Signal_Type()>2) {
            String par1name=Get_Parameter1Name(mp1);
            String par2name=Get_Parameter2Name(mp1);
            String str="senario: " + mp1.Get_Scenario() +
            " date: " + date.toString() +
            " sender: " +Get_ClientName(mp1) +
            " Command " +Get_SignalDesc(mp1);
            if(par1name!=null) {
                str= str + "  " + par1name +": "+mp1.Get_Data1_As_String() ;
            }
            if(par2name!=null) {
                str= str + "  " + par2name +": "+mp1.Get_Data2_As_String() ;
                
            }
            incomemsg.addItem(str);
        }
        else {
            
            if(mp1.Get_Signal_Command()>99 || mp1.Get_Signal_Command()==2 || mp1.Get_Signal_Command()==1 || mp1.Get_Signal_Command()==0) {
                String par1name=Get_Parameter1Name(mp1);
                String par2name=Get_Parameter2Name(mp1);
                String str="senario: " + mp1.Get_Scenario() +
                " date: " + date.toString() +
                " sender: " +Get_ClientName(mp1) +
                " Command " +Get_SignalDesc(mp1);
                if(par1name!=null) {
                    str= str + "  " + par1name +": "+mp1.Get_Data1_As_String() ;
                }
                if(par2name!=null) {
                    str= str + "  " + par2name +": "+mp1.Get_Data2_As_String() ;
                }
                incomemsg.addItem(str);
            }
        }
        
    }
    
    private String Get_ClientName(MP mp) {
        String name=null;
        PaswdFile pf=new PaswdFile();
        ReadFile rf= new  ReadFile("./System/paswdf.mms",pf.Get_Size());
        for(int x=1;x<=rf.NumOfRecords();x++) {
            pf.Set_PaswdFile_Byte(rf.Read(x));
            if(pf.Get_ClientID()==mp.Get_Sender() && pf.Get_Scenario()==mp.Get_Scenario()) {
                name=pf.Get_UserName();
                break;
            }
        }
        rf.Close();
        return name;
    }
    private String Get_Parameter1Name(MP mp) {
        String param=null;
        boolean skip=false;
        ControlProtocol cp=new ControlProtocol();
        for(int z=0;z<cp.sizeArray();z++) {
            if(cp.signalsfile[z].Get_Signal_Type()==mp.Get_Signal_Type() && cp.signalsfile[z].Get_Signal_Command()==mp.Get_Signal_Command()) {
                if(cp.signalsfile[z].Get_Parametrs()>=1) {
                    param=cp.signalsfile[z].Get_Parameter1Name_No_Space();
                }
                skip=true;
                break;
            }
        }
        if(!skip) {
            SignalsFile sf=new SignalsFile();
            String externalsignalfilename="./System/"+mp.Get_Receiver()+"_"+mp.Get_Scenario()+"externalsignal.mms";
            ReadFile rf=new ReadFile(externalsignalfilename,sf.Get_SignalsFile_Size());
            
            for(int x=1;x<=rf.NumOfRecords();x++) {
                sf.Set_SignalsFile_Bytes(rf.Read(x));
                if(sf.Get_Signal_Type()==mp.Get_Signal_Type() && sf.Get_Signal_Command()==mp.Get_Signal_Command()) {
                    if(sf.Get_Parametrs()>=1) {
                        param=sf.Get_Parameter1Name_No_Space();
                    }
                    break;
                }
            }
            rf.Close();
        }
        return param;
    }
    
    private String Get_Parameter2Name(MP mp) {
        String param=null;
        boolean skip=false;
        ControlProtocol cp=new ControlProtocol();
        for(int z=0;z<cp.sizeArray();z++) {
            if(cp.signalsfile[z].Get_Signal_Type()==mp.Get_Signal_Type() && cp.signalsfile[z].Get_Signal_Command()==mp.Get_Signal_Command()) {
                if(cp.signalsfile[z].Get_Parametrs()>=2) {
                    param=cp.signalsfile[z].Get_Parameter2Name_No_Space();
                }
                skip=true;
                break;
            }
        }
        if(!skip) {
            SignalsFile sf=new SignalsFile();
            String externalsignalfilename="./System/"+mp.Get_Receiver()+"_"+mp.Get_Scenario()+"externalsignal.mms";
            ReadFile rf=new ReadFile(externalsignalfilename,sf.Get_SignalsFile_Size());
            
            for(int x=1;x<=rf.NumOfRecords();x++) {
                sf.Set_SignalsFile_Bytes(rf.Read(x));
                if(sf.Get_Signal_Type()==mp.Get_Signal_Type() && sf.Get_Signal_Command()==mp.Get_Signal_Command()) {
                    if(sf.Get_Parametrs()>=2) {
                        param=sf.Get_Parameter2Name_No_Space();
                    }
                    break;
                }
            }
            rf.Close();
        }
        return param;
    }
    
    private String Get_SignalDesc(MP mp) {
        String signaldesc=null;
        boolean skip=false;
        ControlProtocol cp=new ControlProtocol();
        for(int z=0;z<cp.sizeArray();z++) {
            if(cp.signalsfile[z].Get_Signal_Type()==mp.Get_Signal_Type() && cp.signalsfile[z].Get_Signal_Command()==mp.Get_Signal_Command()) {
                signaldesc=cp.signalsfile[z].Get_Desc_No_Space();
                skip=true;
                break;
            }
        }
        if(!skip) {
            SignalsFile sf=new SignalsFile();
            String externalsignalfilename="./System/"+mp.Get_Receiver()+"_"+mp.Get_Scenario()+"externalsignal.mms";
            ReadFile rf=new ReadFile(externalsignalfilename,sf.Get_SignalsFile_Size());
            for(int x=1;x<=rf.NumOfRecords();x++) {
                sf.Set_SignalsFile_Bytes(rf.Read(x));
                if(sf.Get_Signal_Type()==mp.Get_Signal_Type() && sf.Get_Signal_Command()==mp.Get_Signal_Command()) {
                    signaldesc=sf.Get_Desc_No_Space();
                    break;
                }
            }
            rf.Close();
        }
        return signaldesc;
    }
    private void showanssignal() {
        
        try{
            String s;
            list1.clear();
            for(int z=0;z<as.Get_SizeOfAnsSignal();z++) {
                Date date=new Date(as.Get_Signal(z).Get_Date_Time());
                s=" Type " + as.Get_Signal(z).Get_Signal_Type() + " Sender " + as.Get_Signal(z).Get_SenderID() + " Command " + as.Get_Signal(z).Get_Signal_Command() + " Scenario " + as.Get_Signal(z).Get_Scenario() + " Time " + date.toString() ;
                list1.add(s);
            }
        }
        catch(Exception e)
        {}
        
        
    }
    
    
    private void ShowConnection() {
        
        if(showconnectionenable) {
            Session session=new Session();
            conectedusers.clear();
            if(session.Get_SizeOfSession()==0) {
                numofcon.setText(String.valueOf(session.Get_SizeOfSession()));
                btn_show_command.setVisible(false);
                if(formsc!=null) {
                    if(formsc.isLoading() ) {
                        formsc.CloseForm();
                    }
                }
            }
            else {
                btn_show_command.setVisible(true);
                
            }
            boolean closeSendCommandform=true;
            for(int x=0;x<session.Get_SizeOfSession();x++) {
                numofcon.setText(String.valueOf(session.Get_SizeOfSession()));
                Date date=new Date(session.Get_Session(x).Get_Date_Time());
                String userstr=session.Get_Session(x).Get_SocConnectID()+": User ID: " + session.Get_Session(x).Get_UserID() + " Scenario ID: " + session.Get_Session(x).Get_Scenario() + " Date Time: " + date.toString() + " IP " + session.Get_Session(x).Get_IP();
                conectedusers.add(userstr,session.Get_Session(x).Get_SocConnectID());
                if(formsc!=null) {
                    if(formsc.isLoading() && formsc.Get_ClientId()==session.Get_Session(x).Get_UserID()) {
                        closeSendCommandform=false;
                    }
                }
                if(closeSendCommandform && formsc!=null) {
                    if(formsc.isLoading() ) {
                        formsc.CloseForm();
                        closeSendCommandform=true;
                    }
                }
            }
        }
        else {
            countershowconnectionenable++;
            if(countershowconnectionenable>100) {
                countershowconnectionenable=0;
                showconnectionenable=true;
            }
        }
    }
    
    private void deleteAnsSignal(byte Sender,int Scenario) {
        
        for(int z=0;z<as.Get_SizeOfAnsSignal();z++) {
            if(as.Get_Signal(z).Get_SenderID()==Sender) {
                if(as.Get_Signal(z).Get_Scenario()==Scenario) {
                    as.Delete_Signal(z);
                   break;
                }
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button btn_Close;
    private java.awt.Button btn_clientcontrosignal;
    private java.awt.Button btn_closeconnection;
    private java.awt.Button btn_clrlist;
    private java.awt.Button btn_filepasswd;
    private java.awt.Button btn_show_command;
    private java.awt.Button button1;
    private java.awt.List conectedusers;
    private java.awt.List incomemsg;
    private java.awt.Label label1;
    private java.awt.List list1;
    private java.awt.TextField numofcon;
    private java.awt.Button txt_serverid;
    // End of variables declaration//GEN-END:variables
    
}
