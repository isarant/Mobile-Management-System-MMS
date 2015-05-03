
package mmspcclient;

import javax.microedition.lcdui.Display;
import java.lang.ClassNotFoundException;
import java.lang.InstantiationException;
import java.lang.IllegalAccessException;
import MMSPCClient.SignalProcedures.ParentSignalProcess;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;

//This class receives every incoming signal
//recognize them from signals specification and execute specified procdure
public class CheckIncomingSignals extends Thread {
    private SocConnect sc;
    private Display display;
    private int Connection_Number;
    private byte[] data;
    private byte SenderID;
    
    public CheckIncomingSignals() {
        super();
    }
    
    
    public  synchronized void init(Display display,SocConnect mysc,int Connection_Number,MP mp,byte SenderID) {
        
        sc=mysc;
        this.display=display;
        this.Connection_Number=Connection_Number;
        this.data=data;
        this.SenderID=SenderID;
        int  delindexrec=-1;
        boolean ok=true;
        boolean SameSenderAndScenariobutNotTypeAndCommand=false;
        
        //Check if incoming signal is an answer for an answer signal
        AnsSignal as=new AnsSignal();
        synchronized(as) {
            for(int x=0;x<as.Get_SizeOfAnsSignal();x++) {
                if(mp.Get_Sender()==as.Get_Signal(x).Get_SenderID() && mp.Get_Scenario()==as.Get_Signal(x).Get_Scenario()) {
                    if(mp.Get_Signal_Type()==as.Get_Signal(x).Get_Signal_Type()&& mp.Get_Signal_Command()==as.Get_Signal(x).Get_Signal_Command()) {
                        ok=true;
                        delindexrec=x;
                        break;
                    }
                    else {
                        if(mp.Get_Signal_Type()==2 && mp.Get_Signal_Command()==3) {
                            ok=true;
                            delindexrec=x;
                            break;
                        }
                        else {
                            SameSenderAndScenariobutNotTypeAndCommand=true;
                        }
                    }
                }
                else {
                    if(SameSenderAndScenariobutNotTypeAndCommand)
                    {ok=false;delindexrec=0;}
                    else
                    {ok=true;delindexrec=x;}
                }
            }
        }
        if(ok) {
            DeleteRecFromAnsSignal(delindexrec);
            SignalsFile sf2=new SignalsFile();
            ReadFile rf1;
            if(mp.Get_Signal_Type() >2) {
                //Serviced signal form external protocol
                synchronized(sf2) {
                    String externalsignalfilename=mp.Get_Sender()+"_"+mp.Get_Scenario()+"externalsignal.mms";
                    rf1=new ReadFile(externalsignalfilename);
                    for(int i=1;i<=rf1.NumOfRecords();i++) {
                        sf2.Set_SignalsFile_Bytes(rf1.Read(i));
                        if(sf2.Get_Signal_Type()==mp.Get_Signal_Type() && sf2.Get_Signal_Command()==mp.Get_Signal_Command()) {
                            if(sf2.Get_UsedCommand_No_Space().compareTo("Client")!=0) {
                                if(sf2.Get_ClassName_No_Space()!="") {
                                    LoadClass(sf2.Get_ClassName_No_Space(), sc,Connection_Number,mp);
                                }
                            }
                            break;
                        }
                    }
                    try{rf1.Close();}
                    catch(Exception e){}
                }
            }
            else {
                //Serviced signal form control protocol
                ControlProtocol cp=new ControlProtocol();
                synchronized(cp) {
                    for(int i=0;i<cp.sizeArray();i++) {
                        if(cp.signalsfile(i).Get_Signal_Type()==mp.Get_Signal_Type() && cp.signalsfile(i).Get_Signal_Command()==mp.Get_Signal_Command()) {
                            if(cp.signalsfile(i).Get_ClassName_No_Space()!="") {
                                LoadClass(cp.signalsfile(i).Get_ClassName_No_Space(), sc,Connection_Number,mp);
                            }
                            break;
                        }
                    }
                }
            }
        }
        else {
            sc.close();
        }
    }
    
    //If receive an signal which is the answer in an answer signal delete answer signal from answer signal list
    private void DeleteRecFromAnsSignal(int indexRec) {
        if(indexRec>=0) {
            AnsSignal as=new AnsSignal();
            as.Delete_Signal(indexRec);
            
        }
    }
    //Load a class which service a specified signal
    private synchronized void LoadClass(String ClassName,SocConnect mysc,int Connection_Number,MP mp) {
        ParentSignalProcess parentpignalprocess = new ParentSignalProcess(display,mysc,Connection_Number,mp);
        try {
            if(ClassName.compareTo("")!=0 || ClassName!="None") {
                Class t = Class.forName("MMSPCClient.SignalProcedures."+ClassName);
                Object SignalProcess= t.newInstance();
            }
        }
        catch(ClassNotFoundException e)
        { }
        catch(InstantiationException e)
        { }
        catch(IllegalAccessException e)
        { }
        catch(Exception e) {
            Alert alert = new Alert("Error", e.getMessage(), null, AlertType.ERROR);
            display.setCurrent(alert);
        }
    }
}