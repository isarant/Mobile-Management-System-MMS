
package MMSServer.SignalProcedures;

import MMSServer.ReadFile;
import MMSServer.Signals;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class SendFile extends ParentSignalProcess {
    private File file;
    private String[] filepath;
    
    public SendFile()
    {
        super();
        Signals singnals=new Signals(mp.Get_Receiver());
        ByteArrayOutputStream bous=new ByteArrayOutputStream();
        ByteArrayOutputStream bousFile=new ByteArrayOutputStream();
       try
       {
          bous.write(mp.Get_Data1());
       }
       catch(IOException e)
       {} 
        filepath=new String[3];
        filepath[0]="./TransferFiles/"+bous.toString();
        filepath[1]="./Pictures/"+bous.toString();
        filepath[2]="./Media/"+bous.toString();
        for(int x=0;x<filepath.length;x++)
        {
            file=new File(filepath[x]);
            if(file.isFile())
            {
                	ReadFile  rf=new ReadFile(filepath[x]);
                	try
                	{
                 		bousFile.write(rf.Read());
                	}
                	catch(IOException e)
                	{}
                	rf.Close();
                	sc.Send(singnals.GeneralSignal(mp.Get_Sender(),mp.Get_Scenario(),(byte)2,101,mp.Get_Priority(),bous.toByteArray(),bousFile.toByteArray()),Connection_Number);
                        break;
            }
        }
    }
    
}
