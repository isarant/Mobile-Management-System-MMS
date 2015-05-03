

package mmspcclient;

import java.util.Date;
import java.lang.Runtime;

//This class is a structure for instance for every server is conected
public class ConnectedServer extends Thread {
    private SocConnect socconnect;
    private Display display;
    private byte ServerID;
    private int ScenarioID;
    private byte UserID;
    private int ServerKeynum;
    private int NumConnectedServer;
    private long lasttimeofacceptsignal=0;
    private String ServerName,ServerURL;
    private  boolean ConnectedServerIsEnabled;
    private boolean IsConnected;
    protected boolean crypton=false;
    private SignalsFile[] commandsignalfile;
    private IncomingMP incomingmp;
    private boolean outofmemoryerrortryagain;
    
    public ConnectedServer() {
        
    }
    
    public ConnectedServer(SocConnect socconnect,int NumConnectedServer,Display display,byte ServerID,byte UserID,int ScenarioID) {
        this.socconnect=socconnect;
        this.display=display;
        this.ServerID=ServerID;
        this.UserID=UserID;
        this.ScenarioID=ScenarioID;
        this.NumConnectedServer=NumConnectedServer;
        incomingmp=new  IncomingMP();
        incomingmp.Clear_All();
        init();
    }
    
    private synchronized void init() {
        ReadFile rf=new ReadFile("SerFile.mms");
        ServersFile sf=new ServersFile();
        for(int x=1;x<=rf.NumOfRecords();x++) {
            sf.Set_ServerFile_Byte(rf.Read(x));
            if(sf.Get_ServerID()==ServerID && sf.Get_Scenario()==ScenarioID) {
                ServerName=sf.Get_ServerName_No_Space();
                ServerURL=sf.Get_ServerURL_No_Space();
                ServerKeynum=sf.Get_keyNum();
                break;
            }
        }
        
        ConnectedServerIsEnabled=true;
        IsConnected=false;
        
        /*******************************************/
        //this.setPriority(Thread.MAX_PRIORITY);
        this.start();
    }
    
    //This sub procedure used for every signal sent to specified server if we want to sent text
    public synchronized void Send_Signal(SignalsFile SendSingnal,byte Priority,String Parameter1,String Parameter2) {
        try {
            Signals signals=new Signals(UserID);
            byte[] par1data=null;
            byte[] par2data=null;
            if(Parameter1!=null) {
                if(Parameter1.compareTo("")!=0) {
                    par1data=Parameter1.getBytes();
                }
            }
            if(Parameter2!=null) {
                if(Parameter2.compareTo("")!=0) {
                    par2data=Parameter2.getBytes();
                }
            }
            socconnect.Send(signals.GeneralSignal(ServerID ,ScenarioID,SendSingnal.Get_Signal_Type(),SendSingnal.Get_Signal_Command(),Priority,par1data,par2data));
        }
        catch(NullPointerException e)
        {}
        catch(Exception e)
        {CloseConnectedServer(1);}
    }
    
    //This sub procedure used for every signal sent to specified server if we want to sent bytes
    public synchronized void Send_Signal(SignalsFile SendSingnal,byte Priority,byte[] Parameter1,byte[] Parameter2) {
        try {
            Signals signals=new Signals(UserID);
            byte[] par1data=null;
            byte[] par2data=null;
            if(Parameter1!=null) {
                if(Parameter1.length>0) {
                    par1data=Parameter1;
                }
            }
            if(Parameter2!=null) {
                if(Parameter2.length>0) {
                    par2data=Parameter2;
                }
            }
            socconnect.Send(signals.GeneralSignal(ServerID ,ScenarioID,SendSingnal.Get_Signal_Type(),SendSingnal.Get_Signal_Command(),Priority,par1data,par2data));
        }
        catch(NullPointerException e)
        {}
        catch(Exception e)
        {CloseConnectedServer(1);}
    }
    
    //This sub procedure called for logon with server
    public synchronized void LogOn(String UserName,String Password) {
        try {
            Signals signals=new Signals(UserID);
            socconnect.Send(signals.LogOn(ServerID ,ScenarioID,UserName,Password));
        }
        catch(Exception e)
        {CloseConnectedServer(1);}
    }
    
    //This sub procedure called for logon with server and server sent external file
    public synchronized void LogOnSendExteranlFile(String UserName,String Password) {
        try {
            Signals signals=new Signals(UserID);
            socconnect.Send(signals.LogOnAndSendMeSignalFile(ServerID ,ScenarioID,UserName,Password));
        }
        catch(Exception e)
        {CloseConnectedServer(1);}
    }
    
    //This sub procedure called if not success the logon with server
    public synchronized void LogOff() {
        try {
            Signals signals=new Signals(UserID);
            socconnect.Send(signals.LogOff(ServerID,ScenarioID));
            ConnectedServerIsEnabled=false;
            IsConnected=false;
            new ShowConnectedServers().init(false);
        }
        catch(Exception e)
        {CloseConnectedServer(1);}
        
    }
    
    //Return Server ID attribute for specified server
    public byte Get_ServerID() {
        return ServerID;
    }
    
    //Return Client ID attribute
    public byte Get_ClientID() {
        return UserID;
    }
    
    //Return scenario attribute for specified server nad connection
    public int Get_Scenarion() {
        return ScenarioID;
    }
    
    //Return Server name attribute for specified server
    public String Get_ServerName() {
        return ServerName;
    }
    
    //Return Server URL attribute for specified server
    public String Get_ServerURL() {
        return ServerURL;
    }
    
    //Return Local IP attribute for specified connection
    public String Get_Local_IP() {
        return socconnect.Get_Local_IP();
    }
    
    //Return Server IP attribute for specified server
    public String Get_Remote_IP() {
        return socconnect.Get_Remote_IP();
    }
    
    //Return Key number attribute for specified server
    public int GetKeynum() {
        return ServerKeynum;
    }
    
    //This sub procedure close the connection with specified server
    public synchronized void CloseConnectedServer(int command) {
        if(command==1) {
            Alert myAlert = new Alert("Alert",
            " Can not Connect with "+ServerName,
            null, AlertType.CONFIRMATION);
            myAlert.setTimeout(Alert.FOREVER);
            display.setCurrent(myAlert);
        }
        if(command==2) {
            Alert myAlert = new Alert("Alert",
            " Accept Disconect Signal Disonnected with "+ServerName,
            null, AlertType.CONFIRMATION);
            myAlert.setTimeout(Alert.FOREVER);
            display.setCurrent(myAlert);
        }
        if(command==3) {
            lasttimeofacceptsignal=0;
            //socconnect.close();
            ConnectedServerIsEnabled=false;
            IsConnected=false;
            
        }
        else {
            lasttimeofacceptsignal=0;
            socconnect.close();
            ConnectedServerIsEnabled=false;
            IsConnected=false;
            crypton=false;
            new ShowConnectedServers().init(true);
        }
    }
    
    //This sub procedure called if success the logon with server
    public void AlertConnectedServer() {
        IsConnected=true;
        crypton=true;
        try {
            new DeleteFile(ServerID + "_" + ScenarioID + "AvaliableFiles.txt");
            new DeleteFile(ServerID + "_" + ScenarioID + "AllUsers.txt");
            new DeleteFile(ServerID + "_" + ScenarioID + "ActUsers.txt");
            new DeleteFile(ServerName+ ScenarioID + "PreviewsIncSignal.txt");
        }
        catch (Exception e)
        {e.printStackTrace();}
        Runnable showcon= new Runnable() {
            public void run() {
                ShowConnectedServers VarShowConnectedServers=new ShowConnectedServers();
                while(VarShowConnectedServers.IsWorking) {
                    try {
                        Thread.sleep(50);
                    }
                    catch(InterruptedException e)
                    {e.printStackTrace(); }
                }
                try{
                    synchronized(VarShowConnectedServers) {
                        VarShowConnectedServers.init(true);
                    }
                }
                catch(Exception e)
                {e.printStackTrace();}
            }
        };
        
        Thread ShowConnectionThread = new Thread(showcon);
        ShowConnectionThread.start();
    }
    
    //return a list of command as signals  that supported from specified server
    //This list contain control protocol signals and external protocol signals
    public synchronized SignalsFile[] Get_Commands() {
        /******************************************/
        try{
            SignalsFile sf2=new SignalsFile();
            ReadFile rf1;
            String externalsignalfilename=ServerID+"_"+ScenarioID+"externalsignal.mms";
            rf1=new ReadFile(externalsignalfilename);
            ControlProtocol cp=new ControlProtocol();
            int index=0;
            SignalsFile[] helpcommandsignalfile;
            int countcommands;
            synchronized(cp) {
                //Add to list Command signal from control protocol
                helpcommandsignalfile=new SignalsFile[rf1.NumOfRecords()+cp.sizeArray()+1];
                helpcommandsignalfile[0]=new SignalsFile();
                helpcommandsignalfile[0].Set_Desc("None");
                for(int z=0;z<cp.sizeArray();z++) {
                    if(cp.signalsfile(z).Get_Visible()==(byte)1) {
                        index++;
                        helpcommandsignalfile[index]=cp.signalsfile(z);
                    }
                }
                countcommands=index+1;
            }
            
            synchronized(rf1) {
                //Add to list Command signal from external protocol
                for(int i=1;i<=rf1.NumOfRecords();i++) {
                    sf2.Set_SignalsFile_Bytes(rf1.Read(i));
                    if(sf2.Get_Signal_Type()==(byte)3 || sf2.Get_Signal_Type()==(byte)5) {
                        if(sf2.Get_UsedCommand_No_Space().compareTo("Server")!=0) {
                            helpcommandsignalfile[countcommands]=new SignalsFile();
                            helpcommandsignalfile[countcommands].Set_SignalsFile_Bytes(sf2.Get_SignalsFile_Byte());
                            countcommands++;
                        }
                    }
                }
                commandsignalfile= new SignalsFile[countcommands];
                System.arraycopy(helpcommandsignalfile , 0, commandsignalfile, 0, countcommands);
                try{rf1.Close();}
                catch(Exception e){e.printStackTrace();}
            }
            /*******************************************/
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return commandsignalfile;
    }
    
    //Return a boolean if connection with specified server is enable
    public boolean Get_Enable() {
        return IsConnected;
    }
    
    //Check connection with server if it is alive
    private synchronized void checkconnection() {
        long waittime=socconnect.IntervalWaitReadTime()*4;
        long checktime=waittime*3;
        Date dt=new Date();
        long mycurrenttitme=dt.getTime();
        //if the time from last incoming signal is great maximum wait time then sent a "AreyouAlive" signal
        if(lasttimeofacceptsignal>0) {
            if((lasttimeofacceptsignal+checktime)<mycurrenttitme) {
                Signals singnals=new Signals(UserID);
                socconnect.Send(singnals.AreYouAlive(ServerID,ScenarioID));
                lasttimeofacceptsignal=dt.getTime() ;
            }
        }
        //If "IamAlive" signal do not arive in a specified time then close connection with server
        AnsSignal as=new AnsSignal();
        for(int z=0;z<as.Get_SizeOfAnsSignal();z++) {
            if(as.Get_Signal(z).Get_SenderID()==ServerID) {
                if(as.Get_Signal(z).Get_Signal_Type()==(byte)2 && as.Get_Signal(z).Get_Signal_Command()==3) {
                    if(as.Get_Signal(z).Get_Date_Time()+waittime<dt.getTime()) {
                        as.Delete_Signal(z);
                        lasttimeofacceptsignal=0;
                        socconnect.close();
                        ConnectedServerIsEnabled=false;
                        IsConnected=false;
                        new ShowConnectedServers().init(false);
                        break;
                    }
                }
            }
        }
    }
    
    //Return Signal description from specification of signals for a external signal
    private synchronized String SignalDesc(MP mp) {
        String MyDesc="";
        SignalsFile sf2=new SignalsFile();
        ReadFile rf1=new ReadFile(mp.Get_Sender()+"_"+mp.Get_Scenario()+"externalsignal.mms");
        for(int i=1;i<=rf1.NumOfRecords();i++) {
            sf2.Set_SignalsFile_Bytes(rf1.Read(i));
            
            if(sf2.Get_Signal_Type()==mp.Get_Signal_Type() && sf2.Get_Signal_Command()==mp.Get_Signal_Command()) {
                MyDesc=sf2.Get_Desc_No_Space();
                break;
            }
        }
        try{rf1.Close();}
        catch(Exception e){}
        return MyDesc;
    }
    
    //Return the last incoming signal from incoming signals list
    public MP Get_last_IncomingMP() {
        MP helpmp=null;
        int helpint=incomingmp.Get_SizeOfIncomingMP();
        if(helpint>0) {
            helpmp=incomingmp.Get_IncomingMP(helpint-1);
        }
        return helpmp;
    }
    
    //Return incoming signals list
    public IncomingMP Get_IncomingMP() {
        return incomingmp;
    }
    
    //Return true if socket connection with specified server is enable else return false
    public boolean Get_Enabled_Socket() {
        return socconnect.enambledfirst;
    }
    
    //Return number of parameters for a signal
    private int Get_num_Of_Paramaters(MP mp) {
        int NumOfParam=0;
        boolean ok=true;
        SignalsFile sf2=new SignalsFile();
        ControlProtocol cp=new ControlProtocol();
        //Check number of parameters from external signals specificatin for a external signal
        for(int i=0;i<cp.sizeArray();i++) {
            sf2.Set_SignalsFile_Bytes(cp.signalsfile(i).Get_SignalsFile_Byte());
            if(sf2.Get_Signal_Type()==mp.Get_Signal_Type() && sf2.Get_Signal_Command()==mp.Get_Signal_Command()) {
                NumOfParam=sf2.Get_Parameters();
                ok=false;
                break;
            }
        }
        //Check number of parameters from control signals specificatin for a control signal
        if(ok) {
            ReadFile rf1=new ReadFile(mp.Get_Sender()+"_"+mp.Get_Scenario()+"externalsignal.mms");
            for(int i=1;i<=rf1.NumOfRecords();i++) {
                sf2.Set_SignalsFile_Bytes(rf1.Read(i));
                if(sf2.Get_Signal_Type()==mp.Get_Signal_Type() && sf2.Get_Signal_Command()==mp.Get_Signal_Command()) {
                    NumOfParam=sf2.Get_Parameters();
                    break;
                }
            }
            try{rf1.Close();}
            catch(Exception e)
            {}
        }
        return NumOfParam;
        
    }
    //Return a signal that has data in parameters only if specified in specification of signal
    private MP ClearincomingSignal(MP mp) {
        MP mp1 =new MP();
        mp1.Set_Date_Time(mp.Get_Date_Time());
        mp1.Set_Priority(mp.Get_Priority());
        mp1.Set_Receiver(mp.Get_Receiver());
        mp1.Set_Scenario(mp.Get_Scenario());
        mp1.Set_Sender(mp.Get_Sender());
        mp1.Set_Signal_Command(mp.Get_Signal_Command());
        mp1.Set_Signal_Type(mp.Get_Signal_Type());
        try {
            if(Get_num_Of_Paramaters(mp)==1) {
                mp1.Set_Data1(mp.Get_Data1());
            }
            if(Get_num_Of_Paramaters(mp)==2) {
                mp1.Set_Data1(mp.Get_Data1());
                mp1.Set_Data2(mp.Get_Data2());
            }
        }
        catch(Exception e)
        {}
        return mp1;
    }
    //run every 300 milisecond
    //Recieve and control all incoming signals
    public  void run() {
        outofmemoryerrortryagain=false;
        MP mp=null;
        while(ConnectedServerIsEnabled) {
            try {
                byte[] ttdata;
                //Check for memory out error
                if(!outofmemoryerrortryagain) {
                    outofmemoryerrortryagain=false;
                }
                //Read incoming signal data and put them in a mp instant
                mp =socconnect.Read();
                //check for null signal
                if(mp!=null) {
                    if(mp.Get_Sender()==ServerID && mp.Get_Scenario()==ScenarioID) {
                        Date dt=new Date();
                        //set the time for last incomig signal
                        lasttimeofacceptsignal=dt.getTime();
                        //Give the signal to CheckIncomingSignals for controling the signal
                        CheckIncomingSignals a=new  CheckIncomingSignals();
                        a.init(display,socconnect,NumConnectedServer,mp,UserID);
                        //Write incoming signal in a file "PreviewsIncSignal.txt"
                        try {
                            WriteIncomingSignal wis=new WriteIncomingSignal(mp);
                            wis.SaveSignal();
                        }
                        catch(Exception e)
                        {e.printStackTrace();}
                        //If incoming signal is external signal or control signal with with signal command great 99 then
                        //add signal to incoming signals list and refresh ShowConnectedServers form
                        if(mp.Get_Signal_Type()>2) {
                            incomingmp.Add_IncomingMP(ClearincomingSignal(mp));
                            new ShowConnectedServers().init(false);
                        }
                        else {
                            if(mp.Get_Signal_Command()>99) {
                                incomingmp.Add_IncomingMP(ClearincomingSignal(mp));
                                new ShowConnectedServers().init(false);
                            }
                        }
                    }
                }
                checkconnection();
            }
            catch(OutOfMemoryError e) {
                Alert alert = new Alert("OutOfMemoryError control", e.getMessage(), null, AlertType.ERROR);
                display.setCurrent(alert);
            }
            catch(Exception e)
            { }
            try {
                Thread.sleep(300);
            }
            catch(InterruptedException e)
            {}
        }
    }
}
