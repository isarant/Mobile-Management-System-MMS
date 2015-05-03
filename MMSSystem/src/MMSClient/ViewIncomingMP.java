
package MMSClient;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
import java.util.Date;

public class ViewIncomingMP extends Form implements CommandListener, ItemStateListener {
    
    private static Display mydisplay;
    private final static Command CMD_EXIT = new Command("Exit", Command.EXIT, 1);
    //private static ConnectedServer[]  ThreadConnectedServer;
    
    private  StringItem[] item0=null ;
    private  StringItem[] item1=null ;
    private  StringItem[] item2=null ;
    private  StringItem[] item3=null ;
    private int index;
    private IncomingMP incomingmp;
    
    public ViewIncomingMP(Display display,ConnectedServer[]  myConnectedServer,int index) {
        super("Incoming Signal");
        append("Server Name: "+ myConnectedServer[index].Get_ServerName());
        mydisplay=display;
        this.index=index;
        // ThreadConnectedServer=myConnectedServer;
        incomingmp=myConnectedServer[index].Get_IncomingMP();
        
        init();
    }
    
    private synchronized void init() {
        try {
            
            setCommandListener(this);
            setItemStateListener(this);
            addCommand(CMD_EXIT);
            int numofincomingmp= incomingmp.Get_SizeOfIncomingMP();
            item1=new StringItem[numofincomingmp];
            item0=new StringItem[numofincomingmp];
            item2=new StringItem[numofincomingmp];
            item3=new StringItem[numofincomingmp];
            
            for(int x=0;x<numofincomingmp;x++) {
                MP mp=new MP();
                item1[x]=new StringItem(x+1 +" Signal: ",SignalDesc(incomingmp.Get_IncomingMP(x)),0);
                append(item1[x]);
                long longdate=incomingmp.Get_IncomingMP(x).Get_Date_Time()+3600000;
                Date mydate=new Date(longdate);
                String vardate=mydate.toString();
                item0[x]=new StringItem(x+1 +" Date: ",vardate,0);
                append(item0[x]);
                int mynumofparam=0;
                mynumofparam=numofparam(incomingmp.Get_IncomingMP(x));
                if(mynumofparam==1) {
                    mp=incomingmp.Get_IncomingMP(x);
                    item2[x]=new StringItem(param1name(mp),mp.Get_Data1_As_String(),0);
                    append(item2[x]);
                }
                if(mynumofparam==2) {
                    mp=incomingmp.Get_IncomingMP(x);
                    item2[x]=new StringItem(param1name(mp),mp.Get_Data1_As_String(),0);
                    append(item2[x]);
                    item3[x]=new StringItem(param2name(mp),mp.Get_Data2_As_String(),0);
                    append(item3[x]);
                    
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        mydisplay.setCurrent(this);
    }
    
    public void commandAction(Command command, Displayable displayable) {
        if(command==CMD_EXIT) {
            new ShowConnectedServers().init(true);
        }
    }
    
    
    public void itemStateChanged(Item item) {
    }
    
    private synchronized String SignalDesc(MP mp) {
        String MyDesc="";
        try {
            
            if(mp.Get_Signal_Type()<3) {
                ControlProtocol cp=new ControlProtocol();
                for(int z=0;z<cp.sizeArray();z++) {
                    if(cp.signalsfile(z).Get_Signal_Type()==mp.Get_Signal_Type() && cp.signalsfile(z).Get_Signal_Command()==mp.Get_Signal_Command()) {
                        MyDesc=cp.signalsfile(z).Get_Desc_No_Space();
                        break;
                    }
                }
            }
            else {
                SignalsFile sf2=new SignalsFile();
                String externalsignalfilename=mp.Get_Sender()+"_"+mp.Get_Scenario()+"externalsignal.mms";
                ReadFile rf1=new ReadFile(externalsignalfilename);
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
        catch(Exception e) {
            e.printStackTrace();
        }
        return MyDesc;
    }
    private synchronized String param1name(MP mp) {
        String MyDesc="";
        try {
            if(mp.Get_Signal_Type()<3) {
                ControlProtocol cp=new ControlProtocol();
                for(int z=0;z<cp.sizeArray();z++) {
                    if(cp.signalsfile(z).Get_Signal_Type()==mp.Get_Signal_Type() && cp.signalsfile(z).Get_Signal_Command()==mp.Get_Signal_Command()) {
                        MyDesc=cp.signalsfile(z).Get_Parameter1Name_No_Space();
                        break;
                    }
                }
            }
            else {
                SignalsFile sf2=new SignalsFile();
                String externalsignalfilename=mp.Get_Sender()+"_"+mp.Get_Scenario()+"externalsignal.mms";
                ReadFile rf1=new ReadFile(externalsignalfilename);
                for(int i=1;i<=rf1.NumOfRecords();i++) {
                    sf2.Set_SignalsFile_Bytes(rf1.Read(i));
                    
                    if(sf2.Get_Signal_Type()==mp.Get_Signal_Type() && sf2.Get_Signal_Command()==mp.Get_Signal_Command()) {
                        MyDesc=sf2.Get_Parameter1Name_No_Space();
                        break;
                    }
                }
                try{rf1.Close();}
                catch(Exception e){}
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return MyDesc;
    }
    
    private synchronized String param2name(MP mp) {
        String MyDesc="";
        
        try {
            if(mp.Get_Signal_Type()<3) {
                ControlProtocol cp=new ControlProtocol();
                for(int z=0;z<cp.sizeArray();z++) {
                    if(cp.signalsfile(z).Get_Signal_Type()==mp.Get_Signal_Type() && cp.signalsfile(z).Get_Signal_Command()==mp.Get_Signal_Command()) {
                        MyDesc=cp.signalsfile(z).Get_Parameter2Name_No_Space();
                        break;
                    }
                }
            }
            else {
                SignalsFile sf2=new SignalsFile();
                String externalsignalfilename=mp.Get_Sender()+"_"+mp.Get_Scenario()+"externalsignal.mms";
                ReadFile rf1=new ReadFile(externalsignalfilename);
                for(int i=1;i<=rf1.NumOfRecords();i++) {
                    sf2.Set_SignalsFile_Bytes(rf1.Read(i));
                    if(sf2.Get_Signal_Type()==mp.Get_Signal_Type() && sf2.Get_Signal_Command()==mp.Get_Signal_Command()) {
                        MyDesc=sf2.Get_Parameter2Name_No_Space();
                        break;
                    }
                }
                try{rf1.Close();}
                catch(Exception e){}
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return MyDesc;
    }
    
    private synchronized int numofparam(MP mp) {
        int mynum=0;
        try {
            if(mp.Get_Signal_Type()<3) {
                ControlProtocol cp=new ControlProtocol();
                for(int z=0;z<cp.sizeArray();z++) {
                    if(cp.signalsfile(z).Get_Signal_Type()==mp.Get_Signal_Type() && cp.signalsfile(z).Get_Signal_Command()==mp.Get_Signal_Command()) {
                        mynum=cp.signalsfile(z).Get_Parameters();
                        break;
                    }
                }
            }
            else {
                SignalsFile sf2=new SignalsFile();
                String externalsignalfilename=mp.Get_Sender()+"_"+mp.Get_Scenario()+"externalsignal.mms";
                ReadFile rf1=new ReadFile(externalsignalfilename);
                for(int i=1;i<=rf1.NumOfRecords();i++) {
                    sf2.Set_SignalsFile_Bytes(rf1.Read(i));
                    if(sf2.Get_Signal_Type()==mp.Get_Signal_Type() && sf2.Get_Signal_Command()==mp.Get_Signal_Command()) {
                        mynum=sf2.Get_Parameters();
                        break;
                    }
                }
                try{rf1.Close();}
                catch(Exception e){}
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return mynum;
    }
}
