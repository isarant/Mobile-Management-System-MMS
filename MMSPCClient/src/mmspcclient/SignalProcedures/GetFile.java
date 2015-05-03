
package MMSPCClient.SignalProcedures;

import MMSPCClient.WriteFile;
import MMSPCClient.SignalsFile;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import javax.microedition.rms.*;
import MMSPCClient.Signals;
import MMSPCClient.ClientID;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;

public class GetFile extends ParentSignalProcess implements Runnable {
    
    private RecordStore rs;
    
    public GetFile() {
        super();
        new Thread(this).start();
    }
    
    public synchronized void run() {
        
        
        ByteArrayOutputStream bous=new ByteArrayOutputStream();
        
        try {
            bous.write(mp.Get_Data1());
        }
        catch(IOException e) {
            Alert alert = new Alert("Error", e.getMessage(), null, AlertType.ERROR);
            display.setCurrent(alert);
        }
        try {
            rs = RecordStore.openRecordStore(bous.toString(),false);
            if(rs.getNumRecords()>0) {
                rs.closeRecordStore();
                rs.deleteRecordStore(bous.toString());
            }
        }
        catch(RecordStoreException e) {
        }
        catch(Exception e) {
            Alert alert = new Alert("Error", e.getMessage(), null, AlertType.ERROR);
            display.setCurrent(alert);
        }
        try {
            rs.closeRecordStore();
        }
        catch (RecordStoreException e)
        {}
        catch(Exception e)
        {}
        WriteFile wf=new WriteFile(bous.toString());
        wf.Write(mp.Get_Data2());
        wf.Close();
        ClientID cl=new ClientID();
        Signals singnals=new Signals(cl.GetClientID());
        byte[] data=null;
        if(mp.Get_Signal_Command()==100) {
            sc.Send(singnals.GeneralSignal(mp.Get_Sender(),mp.Get_Scenario(),(byte)2,100,mp.Get_Priority(),bous.toByteArray(),data));
        }
    }
    
}
