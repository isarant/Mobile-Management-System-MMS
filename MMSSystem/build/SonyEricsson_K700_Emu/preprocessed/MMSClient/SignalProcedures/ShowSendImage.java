
package MMSClient.SignalProcedures;

import javax.microedition.lcdui.Form;
import MMSClient.ShowConnectedServers;
import MMSClient.SignalsFile;
import MMSClient.Signals;
import MMSClient.ClientID;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import javax.microedition.rms.*;
import MMSClient.WriteFile;

public class ShowSendImage extends ParentSignalProcess {
    private RecordStore rs;
    
    public ShowSendImage() {
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
        Form form=new ShowConnectedServers();
        ShowImage showimage=new ShowImage(mp.Get_Data2(),display,form) ;
        ClientID cl=new ClientID();
        Signals singnals=new Signals(cl.GetClientID());
        byte[] data=null;
        if(mp.Get_Signal_Command()==102) {
            sc.Send(singnals.GeneralSignal(mp.Get_Sender(),mp.Get_Scenario(),(byte)2,102,mp.Get_Priority(),mp.Get_Data1(),data));
        }
    }
    
}
