
package MMSServer.SignalProcedures;


import MMSServer.WriteFile;
import MMSServer.SignalsFile;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import MMSServer.Signals;
import java.io.File;

public class GetFile extends ParentSignalProcess{
   
    public GetFile()
    {
       ByteArrayOutputStream bous=new ByteArrayOutputStream();
       
        try
       {
          bous.write(mp.Get_Data1());
       }
       catch(IOException e)
       {} 
       File file=new File("./TransferFiles/" + bous.toString());
       if(file.isFile())
       {
           file.delete();
           
       }
       WriteFile wf=new WriteFile("./TransferFiles/" + bous.toString());
       wf.Write(mp.Get_Data2());
       wf.Close();
       Signals singnals=new Signals(mp.Get_Receiver());
        byte[] data=null;
        sc.Send(singnals.GeneralSignal(mp.Get_Sender(),mp.Get_Scenario(),(byte)2,100,mp.Get_Priority(),bous.toByteArray(),data),Connection_Number);
    }
    
}
