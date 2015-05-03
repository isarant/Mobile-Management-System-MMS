
package MMSClient.SignalProcedures;

import MMSClient.ReadFile;
import MMSClient.ClientID;
import MMSClient.SignalProcedures.FileManager;
import MMSClient.Signals;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class SendImage extends ParentSignalProcess implements Runnable{
    public SendImage() {
        super();
        new Thread(this).start();
        
    }
    
    public void run() {
        ClientID cl=new ClientID();
        Signals singnals=new Signals(cl.GetClientID());
        ByteArrayOutputStream bous=new ByteArrayOutputStream();
        ByteArrayOutputStream bousFile=new ByteArrayOutputStream();
        try {
            bous.write(mp.Get_Data1());
        }
        catch(IOException e)
        {}
        ReadFile rf=new ReadFile(bous.toString());
        for(int z=1;z<=rf.NumOfRecords();z++) {
            try {
                bousFile.write(rf.Read(z));
            }
            catch(IOException e)
            {}
        }
        rf.Close();
        sc.Send(singnals.GeneralSignal(mp.Get_Sender(),mp.Get_Scenario(),(byte)2,102,mp.Get_Priority(),bous.toByteArray(),bousFile.toByteArray()));
    }
    
}
