

package MMSClient;

import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.*;
import java.util.Date;
import java.lang.InterruptedException;
import MMSClient.SignalProcedures.FileManager;

public class SelectServer extends  Form implements CommandListener, ItemStateListener {
    private Display  display;
    private Form myPrevForm;
    private Command cmdExit=new Command("Exit", Command.EXIT, 1);
    private Command cmdConnect=new Command("Connect", Command.SCREEN, 1);
    private Command cmdAdd=new Command("Add New Server", Command.SCREEN, 2);
    private Command cmdDel=new Command("Delete Selected Servers", Command.SCREEN, 3);
    private Command cmdFilemanager=new Command("File Manager", Command.SCREEN, 5);
    private Command cmdSetClientID=new Command("Set Client ID", Command.SCREEN, 4);
    private Command cmdchangepaswd=new Command("Change Password", Command.SCREEN, 6);
    private Command cmdCapturePhoto=new Command("Capture Photo", Command.SCREEN, 7);
    private Command cmdCaptureAudio=new Command("Capture Audio", Command.SCREEN, 8);
    private Form myform;
    private ChoiceGroup cg;
    private String[] stringArray;
    private int myintexappend=-1;
    private int port=5001;
    private byte clientid;
    private static ConnectedServer[] myConnectedServer;
    private static SocConnect[] sc;
    private byte ServerID;
    private int myScenario;
    private int KeyNum;
    private static ShowConnectedServers showconnectedservers;
    private static MMSystem midlet;
    
    public SelectServer(Display display,MMSystem midlet) {
        super("Servers to Connect");
        this.display=display;
        this.midlet=midlet;
        showconnectedservers=null;
        display.setCurrent(this);
        myPrevForm=null;
        myConnectedServer=new ConnectedServer[0];
        sc=null;
        init();
    }
    
    public SelectServer(Display display) {
        super("Servers to Connect");
        this.display=display;
        showconnectedservers=null;
        display.setCurrent(this);
        myPrevForm=null;
        myConnectedServer=new ConnectedServer[0];
        sc=null;
        init();
    }
    
    public SelectServer() {
        super("Servers to Connect");
        
    }
    
    
    public SelectServer(Display display,Form PrevForm,MMSystem midlet) {
        super("Servers to Connect");
        this.display=display;
        this.midlet=midlet;
        display.setCurrent(this);
        myPrevForm=PrevForm;
        init();
    }
    
    /*Clear Form*/
    private void ResetForm() {
        stringArray=null;
        try {
            removeCommand(cmdConnect);
            removeCommand(cmdDel);
            removeCommand(cmdAdd);
            removeCommand(cmdExit);
            removeCommand(cmdchangepaswd);
            removeCommand(cmdFilemanager);
            removeCommand(cmdCapturePhoto);
            removeCommand(cmdCaptureAudio);
        }
        catch(Exception e){}
        try {
            cg.deleteAll();
        }
        catch(Exception e)
        {}
        try {
            if(myintexappend!=-1) {
                this.delete(myintexappend);
            }
        }
        catch(Exception e)
        {}
        try {
            SelectServer.showconnectedservers.deleteAll();
        }
        catch(Exception e)
        {}
        try {
            this.deleteAll();
        }
        catch(Exception e)
        {}
    }
    /*Initialize the Form*/
    public ConnectedServer[] Get_ConnectedServer() {
        return myConnectedServer;
    }
    
    protected void init() {
        ClearAnSignal();
        if(myConnectedServer!=null) {
            for(int x=0;x<myConnectedServer.length;x++) {
                try {
                    myConnectedServer[x].LogOff();
                }
                catch(Exception e)
                {}
            }
            myConnectedServer=null;
        }
        if(sc!=null) {
            for(int xi=0;xi<sc.length;xi++) {
                try {
                    sc[xi].close();
                }
                catch(Exception e)
                {   }
            }
            sc=null;
        }
        showconnectedservers=null;
        /* Take throws Client ID*/
        ClientID cl=new ClientID();
        clientid=cl.GetClientID();
        /* ******************************************** */
        /*Build the Servers item  Form*/
        Image[] imageArray = null;
        ReadFile rf=new ReadFile("SerFile.mms");
        ServersFile sf=new ServersFile();
        int numofrec=rf.NumOfRecords();
        try {
            setCommandListener(this);
            setItemStateListener(this);
            ResetForm();
            /* build command button if there is client id*/
            if(clientid!=(byte)0) {
                myintexappend=append("Select Server to Connect");
                /* build command button if there are server in serves  */
                if(numofrec>0){
                    addCommand(cmdConnect);
                    addCommand(cmdDel);
                }
                addCommand(cmdAdd);
                addCommand(cmdFilemanager);
                addCommand(cmdCapturePhoto);
                addCommand(cmdCaptureAudio);
            }
            /* build command button if there is not client id*/
            else {
                myintexappend=append("You must set a Client ID Ask Administrator!!!");
                addCommand(cmdSetClientID);
            }
            addCommand(cmdchangepaswd);
            addCommand(cmdExit);
        }
        catch(Exception e)
        { }
        /* build list of  serves  */
        if(numofrec>0 && clientid!=(byte)0){
            stringArray=null;
            stringArray=new String[numofrec];
            for(int x=1;x<=numofrec;x++) {
                sf.Set_ServerFile_Byte(rf.Read(x));
                stringArray[x-1]=sf.Get_ServerName_No_Space();
            }
            cg=new ChoiceGroup("", ChoiceGroup.MULTIPLE, stringArray,imageArray);
            append(cg);
            rf.Close();
        }
        
    }
    
    public void commandAction(Command command, Displayable displayable) {
        if(command==cmdConnect){
            /* Servers Connect */
            sc=null;
            myConnectedServer=null;
            ReadFile rf2=new ReadFile("SerFile.mms");
            ServersFile sf2=new ServersFile();
            myConnectedServer=new ConnectedServer[cg.size()];
            sc=new SocConnect[cg.size()];
            //showconnectedservers=new ShowConnectedServers(display, midlet,myConnectedServer);
            for( int x=0;x<=cg.size()-1;x++) {
                if(cg.isSelected(x)) {
                    sf2.Set_ServerFile_Byte(rf2.Read(x+1));
                    KeyNum=sf2.Get_keyNum();
                    try {
                        /*Make SocConnection and create ConnectedServer Object */
                        sc[x]=new SocConnect(sf2.Get_ServerURL_No_Space(),String.valueOf(port));
                        myConnectedServer[x]=new ConnectedServer(sc[x],x,display,sf2.Get_ServerID(),clientid,sf2.Get_Scenario());
                        /*SendLogin Data*/
                        ReadFile rf=new ReadFile(sf2.Get_ServerID()+"_"+ sf2.Get_Scenario()+"externalsignal.mms");
                        if(rf.NumOfRecords()>0) {
                            myConnectedServer[x].LogOn(sf2.Get_UserName(),sf2.Get_Passwd());
                        }
                        else {
                            myConnectedServer[x].LogOnSendExteranlFile(sf2.Get_UserName(),sf2.Get_Passwd());
                        }
                        rf.Close();
                        // showconnectedservers=new ShowConnectedServers(display, midlet,myConnectedServer);
                    }
                    catch(Exception e)
                    {}
                    
                }
            }
            showconnectedservers=new ShowConnectedServers(display,myConnectedServer);
            rf2.Close();
        }
        
        if(command==cmdAdd){
            /* Add new Server */
            myform=new AddServerForm(display,(SelectServer)this);
            display.setCurrent(myform);
        }
        
        if(command==cmdDel) {
            /*Delete Selected Server*/
            WriteFile wf=new WriteFile("SerFile.mms");
            for(int x=0;x<=cg.size()-1;x++) {
                if(cg.isSelected(x)) {
                    wf.Delete(x+1);
                    cg.delete(x);
                    x=-1;
                }
            }
            wf.Close();
            init();
        }
        
        if(command==cmdSetClientID) {
            removeCommand(cmdSetClientID);
            Form clientid=new ClientID(display,(SelectServer)this);
            display.setCurrent(clientid);
        }
        if(command==cmdchangepaswd) {
            Passwd passwd=new Passwd(display,(SelectServer)this);
            display.setCurrent(passwd);
        }
        
        
        if(command==cmdExit) {
            if(myPrevForm!=null) {
                display.setCurrent(myPrevForm);
            }
            else {
                try {
                    midlet.destroyApp(true);
                    midlet.notifyDestroyed();
                }
                catch(Exception e)
                {e.printStackTrace();}
            }
        }
        if(command==cmdFilemanager) {
            FileManager fm=new FileManager(0,display,this);
        }
        if(command==cmdCapturePhoto) {
            CameraForm cf=new CameraForm(this,display);
        }
        if(command==cmdCaptureAudio) {
            CaptureAudio ca=new CaptureAudio(this,display);
        }
        
    }
    
    public void itemStateChanged(Item item) {
        
    }
    
    public Display Get_Display() {
        return display;
    }
    
    
    private void ClearAnSignal() {
        AnsSignal as=new AnsSignal();
        for(int z=0;z<as.Get_SizeOfAnsSignal();z++) {
            as.Delete_Signal(z);
        }
    }
    
}
