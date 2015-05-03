
package MMSPCClient.SignalProcedures;

import javax.microedition.midlet.*;
import MMSPCClient.Signals;
import MMSPCClient.ClientID;
import javax.microedition.lcdui.Form;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.microedition.rms.*;
import MMSPCClient.WriteFile;
import MMSPCClient.ShowConnectedServers;

public class ShowFileMsg extends ParentSignalProcess{
    private String[] param;
    private RecordStore rs;
    public ShowFileMsg() {
        Form form=new ShowConnectedServers();
        ClientID cl=new ClientID();
        Signals singnals=new Signals(cl.GetClientID());
        ByteArrayOutputStream bout=new ByteArrayOutputStream();
        try {
            bout.write(mp.Get_Data1());
        }
        catch(IOException ioe)
        {}
        param=MyParam(bout.toString());
        byte mytype=mp.Get_Signal_Type();
        mytype++;
        try {
            rs = RecordStore.openRecordStore(param[2],false);
            if(rs.getNumRecords()>0) {
                rs.closeRecordStore();
                rs.deleteRecordStore(param[2]);
            }
        }
        catch(RecordStoreException e) {
        }
        try {
            rs.closeRecordStore();
        }
        catch (RecordStoreException e)
        {}
        catch(Exception e)
        {}
        WriteFile wf=new WriteFile(param[2]);
        wf.Write(mp.Get_Data2());
        wf.Close();
        sc.Send(singnals.GeneralSignal(mp.Get_Sender(),mp.Get_Scenario(),mytype,mp.Get_Signal_Command(),mp.Get_Priority(),param[0],param[2]));
        ShowFileMsgForm showmsg=new ShowFileMsgForm(param[0],param[1],param[2],mp.Get_Data2(),display,form) ;
        
    }
    
    private String[] MyParam(String users) {
        String[] Str;
        int myint=0;
        int count=0;
        while(myint!=-1) {
            myint=users.indexOf(",",myint+1);
            count++;
        }
        Str=new String[count];
        count=0;
        myint=0;
        while(myint!=-1) {
            int myitnbigen=myint;
            myint=users.indexOf(",",myint+1);
            int myitnend=myint;
            if(myitnend==-1) {
                myitnend=users.length();
            }
            if(myitnbigen!=0) {
                myitnbigen=myitnbigen+1;
            }
            Str[count]=users.substring(myitnbigen,myitnend);
            count++;
        }
        return Str;
    }
    
}
