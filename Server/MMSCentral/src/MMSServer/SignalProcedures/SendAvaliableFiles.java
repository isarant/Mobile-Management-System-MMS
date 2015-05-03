
package MMSServer.SignalProcedures;

import MMSServer.ReadFile;
import MMSServer.Signals;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import MMSServer.Session;
import MMSServer.SessionFile;
import MMSServer.ReadFile;
import MMSServer.Signals;
import java.io.File;

public class SendAvaliableFiles extends ParentSignalProcess {
    
    private File file;
    private String[] TransferFiles,Pictures,Media;
    
    public SendAvaliableFiles()
    {
        super();
            Signals singnals=new Signals(mp.Get_Receiver());
            ByteArrayOutputStream bous=new ByteArrayOutputStream();
            TransferFiles= new File("./TransferFiles/").list();
            Pictures= new File("./Pictures/").list();
            Media= new File("./Media/").list();
            for(int x=0;x<TransferFiles.length;x++)
            {
                String s=TransferFiles[x];
                {s=s+",";} 
                try
                {
                    bous.write(s.getBytes());
                }
                catch(IOException ioe)
                {}
            }
            for(int x=0;x<Pictures.length;x++)
            {
                String s=Pictures[x];
                {s=s+",";} 
                try
                {
                    bous.write(s.getBytes());
                }
                catch(IOException ioe)
                {}
            }
             for(int x=0;x<Media.length;x++)
            {
                String s=Media[x];
                {s=s+",";} 
                try
                {
                    bous.write(s.getBytes());
                }
                catch(IOException ioe)
                {}
            }
            String filename;
            filename=mp.Get_Receiver() +"AvaliableFiles.txt";
            sc.Send(singnals.GeneralSignal(mp.Get_Sender(),mp.Get_Scenario(),(byte)2,110,mp.Get_Priority(),filename.getBytes(),bous.toByteArray()),Connection_Number);
        }
    }
    

