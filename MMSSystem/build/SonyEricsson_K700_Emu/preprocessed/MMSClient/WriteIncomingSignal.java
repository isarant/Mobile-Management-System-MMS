
package MMSClient;

import javax.microedition.midlet.*;
import java.util.Date;

//Write incoming signals to a file
public class WriteIncomingSignal {
    
    private  String str;
    private String ServerName;
    
    public WriteIncomingSignal(MP mp) {
        str=null;
        try {
            ReadFile rf=new ReadFile("SerFile.mms");
            //Find server name from serfile.mms for incoming signal
            ServersFile sf=new ServersFile();
            for(int x=1;x<=rf.NumOfRecords();x++) {
                sf.Set_ServerFile_Byte(rf.Read(x));
                if(sf.Get_ServerID()==mp.Get_Sender()&& sf.Get_Scenario()==mp.Get_Scenario()) {
                    ServerName=sf.Get_ServerName_No_Space();
                    
                    break;
                }
            }
            //create a string for signal that writen to file
            Date date=new Date(mp.Get_Date_Time());
            str="Sender: " + ServerName +
            " Senario: " + mp.Get_Scenario() +
            " Signal: " + SignalDesc(mp) +
            " Date: " + date.toString() ;
            int mynumofparam=numofparam(mp);
            if(mynumofparam==1) {
                str=str+ " " + param1name(mp) + " : " + mp.Get_Data1_As_String();
            }
            if(mynumofparam==2) {
                str=str + " " + param2name(mp) + " : " + mp.Get_Data2_As_String();
            }
            str=str + " | ";
        }
        catch(Exception e)
        {e.printStackTrace();}
    }
    
    //Write to file a string for each signal
    public void SaveSignal() {
        if(str!=null) {
            WriteFile wf=new WriteFile( ServerName+"PreviewsIncSignal.txt");
            wf.Write(str.getBytes());
            wf.Close();
        }
    }
    
    //Return number of parameters from specification of signals for specified signal
    private synchronized int numofparam(MP mp) {
        int mynum=0;
        try {
            if(mp.Get_Signal_Type()<3) {
                ControlProtocol cp=new ControlProtocol();
                synchronized(cp) {
                    for(int z=0;z<cp.sizeArray();z++) {
                        if(cp.signalsfile(z).Get_Signal_Type()==mp.Get_Signal_Type() && cp.signalsfile(z).Get_Signal_Command()==mp.Get_Signal_Command()) {
                            mynum=cp.signalsfile(z).Get_Parameters();
                            break;
                        }
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
    
    //Return the name of parameter1  from specification of signals for specified signal
    private synchronized String param1name(MP mp) {
        String MyDesc="";
        try {
            if(mp.Get_Signal_Type()<3) {
                ControlProtocol cp=new ControlProtocol();
                synchronized(cp) {
                    for(int z=0;z<cp.sizeArray();z++) {
                        if(cp.signalsfile(z).Get_Signal_Type()==mp.Get_Signal_Type() && cp.signalsfile(z).Get_Signal_Command()==mp.Get_Signal_Command()) {
                            MyDesc=cp.signalsfile(z).Get_Parameter1Name_No_Space();
                            break;
                        }
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
    
    //Return the name of parameter2  from specification of signals for specified signal
    private synchronized String param2name(MP mp) {
        String MyDesc="";
        
        try {
            if(mp.Get_Signal_Type()<3) {
                ControlProtocol cp=new ControlProtocol();
                synchronized(cp) {
                    for(int z=0;z<cp.sizeArray();z++) {
                        if(cp.signalsfile(z).Get_Signal_Type()==mp.Get_Signal_Type() && cp.signalsfile(z).Get_Signal_Command()==mp.Get_Signal_Command()) {
                            MyDesc=cp.signalsfile(z).Get_Parameter2Name_No_Space();
                            break;
                        }
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
    
    //Return the desciptino of signal from specification of signals for specified signal
    private synchronized String SignalDesc(MP mp) {
        String MyDesc="";
        try {
            
            if(mp.Get_Signal_Type()<3) {
                ControlProtocol cp=new ControlProtocol();
                synchronized(cp) {
                    for(int z=0;z<cp.sizeArray();z++) {
                        if(cp.signalsfile(z).Get_Signal_Type()==mp.Get_Signal_Type() && cp.signalsfile(z).Get_Signal_Command()==mp.Get_Signal_Command()) {
                            MyDesc=cp.signalsfile(z).Get_Desc_No_Space();
                            break;
                        }
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
}
