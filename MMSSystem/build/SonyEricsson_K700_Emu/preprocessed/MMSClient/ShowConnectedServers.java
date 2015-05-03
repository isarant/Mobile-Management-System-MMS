
package MMSClient;

import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.*;
import java.lang.InterruptedException;
import MMSClient.SignalProcedures.FileManager;

public class ShowConnectedServers extends Form implements Runnable, CommandListener, ItemStateListener, ItemCommandListener {
    private static Display mydisplay;
    private static ConnectedServer[]  ThreadConnectedServer;
    private static String[] CommandDesk;
    private final static Command CMD_EXIT = new Command("Exit", Command.EXIT,
    1);
    private final static Command CMD_EXIT1 = new Command("Exit", Command.EXIT,
    1);
    
    private final static Command CMD_Send = new Command("Send Signals to Servers", Command.SCREEN,
    1);
    private Command cmdFilemanager=new Command("File Manager", Command.SCREEN, 2);
    private Command cmdCapturePhoto=new Command("Capture Photo", Command.SCREEN, 3);
    private Command cmdCaptureAudio=new Command("Capture Audio", Command.SCREEN, 4);
    private static Command[] CMD_PRESS;
    private static ChoiceGroup[] ConnectedServer;
    private static int[] selectedcommand;
    private static Item[] item;
    private static int[] intitem;
    private SignalsFile[] commandsignalfile;
    private static Parameters Parameters;
    private static Thread mythread;
    private boolean looping;
    private  boolean ok;
    private static Global_Parameters gp;
    private static SelectServer ss;
    public static boolean IsWorking;
    
    public ShowConnectedServers() {
        super("Connected Servers");
        gp=new Global_Parameters();
        try {
            setCommandListener(this);
            setItemStateListener(this);
        }
        catch(Exception e)
        {}
    }
    
    public ShowConnectedServers(Display display,ConnectedServer[]  myConnectedServer) {
        super("Connected Servers");
        this.mydisplay=display;
        gp=new Global_Parameters();
        gp.Set_Display(this.mydisplay);
        gp.Set_Previous_Form(this);
        ThreadConnectedServer=myConnectedServer;
        try {
            setCommandListener(this);
            setItemStateListener(this);
        }
        catch(Exception e)
        {}
        selectedcommand=new int[ThreadConnectedServer.length];
        Parameters=new Parameters(ThreadConnectedServer.length);
        mydisplay.setCurrent(this);
        if(mythread==null) {
            mythread=new Thread(this);
            mythread.start();
        }
        IsWorking=false;
    }
    
    private synchronized String SignalDesc(MP mp) {
        boolean skip=false;
        String MyDesc="";
        if (mp!=null) {
            ControlProtocol cp =new ControlProtocol();
            for(int z=0;z<cp.sizeArray();z++) {
                if(cp.signalsfile(z).Get_Signal_Type()==mp.Get_Signal_Type() && cp.signalsfile(z).Get_Signal_Command()==mp.Get_Signal_Command()) {
                    MyDesc=cp.signalsfile(z).Get_Desc_No_Space();
                    skip=true;
                    break;
                }
            }
            if(!skip) {
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
            }
        }
        return MyDesc;
    }
    
    public synchronized void init(boolean firsttime ) {
        int layout=-1;
        boolean ThereisConnectedServer=false;
        IsWorking=true;
        try {
            deleteAll();
          //  for(int ConnectedServercount=0;ConnectedServercount<ConnectedServer.length;ConnectedServercount++) {
          //      ConnectedServer[ConnectedServercount].deleteAll();
          //  }
        }
        catch(Exception e)
        { e.printStackTrace();}
        ConnectedServer=new ChoiceGroup[ThreadConnectedServer.length];
        item = new StringItem[ThreadConnectedServer.length];
        intitem = new int[ThreadConnectedServer.length];
        CMD_PRESS=new Command[ThreadConnectedServer.length];
        for(int z=0 ;z<ThreadConnectedServer.length;z++) {
            ConnectedServer[z]=null;
            item[z]=null;
            CMD_PRESS[z]=null;
            intitem[z]=-1;
        }
        for(int x=0;x<ThreadConnectedServer.length;x++) {
            try {
                
                if(ThreadConnectedServer[x]!=null) {
                    if(ThreadConnectedServer[x].Get_ServerName().compareTo("")!=0 && ThreadConnectedServer[x].Get_Enable()) {
                        ThereisConnectedServer=true;
                        commandsignalfile=ThreadConnectedServer[x].Get_Commands();
                        CommandDesk=new String[commandsignalfile.length];
                        for(int CommandDeskcount=0;CommandDeskcount<commandsignalfile.length;CommandDeskcount++) {
                            CommandDesk[CommandDeskcount]=commandsignalfile[CommandDeskcount].Get_Desc_No_Space();
                        }
                        
                        String GroupStr=ThreadConnectedServer[x].Get_ServerName();
                        ConnectedServer[x] = new ChoiceGroup(GroupStr, ChoiceGroup.POPUP,
                        CommandDesk, null);
                        ConnectedServer[x].setSelectedIndex(selectedcommand[x], true);
                        append(ConnectedServer[x]);
                        layout++;
                        String s=SignalDesc(ThreadConnectedServer[x].Get_last_IncomingMP());
                        if(s.compareTo("")!=0) {
                            item[x] = new StringItem("", s, Item.BUTTON);
                            CMD_PRESS[x] = new Command("Show Incoming Signal", Command.ITEM, 1);
                            item[x].setDefaultCommand(CMD_PRESS[x]);
                            item[x].setItemCommandListener(this);
                            append(item[x]);
                            layout++;
                            item[x].setLayout(layout);
                            intitem[x]=item[x].getLayout();
                        }
                    }
                }
            }
            catch(Exception e)
            { e.printStackTrace();}
        }
        
        try {
            
            if(ThereisConnectedServer) {
                looping=false;
                ok=false;
                this.removeCommand(CMD_EXIT1);
                addCommand(CMD_Send);
                addCommand(cmdFilemanager);
                addCommand(cmdCapturePhoto);
                addCommand(cmdCaptureAudio);
                addCommand(CMD_EXIT);
                String sss=mydisplay.getCurrent().getClass().getName();
                if(sss.compareTo("MMSClient.ShowConnectedServers")==0 || firsttime) {
                    mydisplay.setCurrent(this);
                }
            }
            else {
                make_myConnectedServer_null();
                SelectServer ss=new SelectServer(mydisplay);
            }
            
        }
        catch(Exception e) {
            e.printStackTrace();
            Alert alert = new Alert("Error", e.getMessage(), null, AlertType.ERROR);
            alert.setTimeout(500);
            mydisplay.setCurrent(alert);
        }
        IsWorking=false;
    }
    
    public synchronized void commandAction(Command command, Displayable displayable) {
        if(command==CMD_Send) {
            for(int x=0;x<ConnectedServer.length;x++) {
                try {
                    if(ConnectedServer[x].getSelectedIndex()>0) {
                        if(commandsignalfile[ConnectedServer[x].getSelectedIndex()].Get_Signal_Type()==2 && commandsignalfile[ConnectedServer[x].getSelectedIndex()].Get_Signal_Command()==2) {
                            ThreadConnectedServer[x].LogOff();
                        }
                        else {
                            if(Parameters.Get_Parameter(x)==null) {
                                ThreadConnectedServer[x].Send_Signal(commandsignalfile[ConnectedServer[x].getSelectedIndex()], (byte)1,"","");
                            }
                            else {
                                ThreadConnectedServer[x].Send_Signal(commandsignalfile[ConnectedServer[x].getSelectedIndex()], (byte)1,Parameters.Get_Parameter(x).Ge_Parameter1(),Parameters.Get_Parameter(x).Ge_Parameter2());
                                ConnectedServer[x].setSelectedIndex(0, true);
                                Parameters.Clear_Parameter(x);
                            }
                        }
                        ConnectedServer[x].setSelectedIndex(0, true);
                        selectedcommand[x]=0;
                    }
                }
                catch(Exception e)
                {}
            }
        }
        if(command==CMD_EXIT) {
            for(int x=0;x<ThreadConnectedServer.length;x++) {
                try {
                    ThreadConnectedServer[x].LogOff();
                }
                catch(Exception e)
                {}
            }
            make_myConnectedServer_null();
            ss=new SelectServer(mydisplay);
        }
        if(command==CMD_EXIT1) {
            for(int x=0;x<ThreadConnectedServer.length;x++) {
                try {
                    ThreadConnectedServer[x].LogOff();
                }
                catch(Exception e)
                {}
            }
            make_myConnectedServer_null();
            SelectServer ss=new SelectServer(mydisplay);
        }
        if(command==cmdFilemanager) {
            FileManager fm=new FileManager(0,mydisplay,this);
        }
        if(command==cmdCapturePhoto) {
            CameraForm cf=new CameraForm(this,mydisplay);
        }
        if(command==cmdCaptureAudio) {
            CaptureAudio ca=new CaptureAudio(this,mydisplay);
        }
    }
    
    public synchronized void itemStateChanged(Item item) {
        int ConnectedServernum=-1;
       try{
        for(int xi=0;xi<ConnectedServer.length;xi++) {
            if(ConnectedServer[xi]!=null) {
                if(item.getLabel().compareTo(ConnectedServer[xi].getLabel())==0) {
                    ConnectedServernum=xi;
                    break;
                }
                ConnectedServernum=-1;
            }
        }
       }
       catch(Exception e)
       {e.printStackTrace();}
        if(ConnectedServernum>-1) {
            selectedcommand[ConnectedServernum]=ConnectedServer[ConnectedServernum].getSelectedIndex();
            if(commandsignalfile[ConnectedServer[ConnectedServernum].getSelectedIndex()].Get_Parameters()>(byte)0) {
                if(commandsignalfile[ConnectedServer[ConnectedServernum].getSelectedIndex()].Get_ParameterClass_No_Space().compareTo("None")==0 || commandsignalfile[ConnectedServer[ConnectedServernum].getSelectedIndex()].Get_ParameterClass_No_Space().compareTo("")==0) {
                    Parametersform pf=new Parametersform(mydisplay,this,commandsignalfile[ConnectedServer[ConnectedServernum].getSelectedIndex()],ConnectedServernum);
                }
                else {
                    try {
                        gp.Set_Parameter1(2);
                        gp.Set_Parameter2(ConnectedServernum);
                        gp.Set_Previous_Form(this);
                        gp.Set_ServerID( ThreadConnectedServer[ConnectedServernum].Get_ServerID());
                        gp.Set_Scenario( ThreadConnectedServer[ConnectedServernum].Get_Scenarion());
                        Class t = Class.forName("MMSClient.SignalProcedures."+commandsignalfile[ConnectedServer[ConnectedServernum].getSelectedIndex()].Get_ParameterClass_No_Space());
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
                }
            }
        }
      
        
        
    }
    
    public void run() {
        boolean showlocalip=false;
        boolean showremoteip=false;
        looping=true;
        int loopcount=0;
        ok=false;
        append(
        new Gauge("Connecting.....", false,
        Gauge.INDEFINITE, Gauge.CONTINUOUS_RUNNING));
        addCommand(CMD_EXIT1);
        while(loopcount<100) {
            loopcount++;
            for(int x=0;x<ThreadConnectedServer.length;x++) {
                try {
                    if(ThreadConnectedServer[x].Get_Enable()&& ThreadConnectedServer[x].Get_Enabled_Socket()) {
                        ok=true;
                        loopcount=400;
                        // break;
                    }
                }
                catch(Exception e)
                { }
            }
            try {
                mythread.sleep(1000);
            }
            catch(InterruptedException e)
            {}
        }
        if(!ok) {
            deleteAll();
            for(int x=0;x<ThreadConnectedServer.length;x++) {
                try {
                    ThreadConnectedServer[x].LogOff();
                }
                catch(Exception e)
                {}
            }
            make_myConnectedServer_null();
            SelectServer ss=new SelectServer(mydisplay);
        }
        else {
            //init(true);
        }
    }
    
    private void  make_myConnectedServer_null() {
        for(int x=0;x<ThreadConnectedServer.length;x++) {
            try {
                ThreadConnectedServer[x].CloseConnectedServer(3);
            }
            catch(Exception e)
            {}
        }
        mythread=null;
    }
    
    public void commandAction(javax.microedition.lcdui.Command command, javax.microedition.lcdui.Item item) {
        int myindex=0;
        for(int x=0;x<intitem.length;x++) {
            if(intitem[x]==item.getLayout()) {
                myindex=x;
                break;
            }
        }
        new ViewIncomingMP(mydisplay,ThreadConnectedServer,myindex);
    }
    
}
