

package MMSServer.SignalProcedures;


import MMSServer.WriteFile;
import MMSServer.SignalsFile;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import MMSServer.Signals;
import java.io.File;
import MMSServer.MP;
import java.lang.ClassNotFoundException;
import java.lang.InstantiationException;
import java.lang.IllegalAccessException;
import MMSServer.ReadFile;
import MMSServer.ServerIDFile;

public class ShowSendImage extends ParentSignalProcess {
    
    public ShowSendImage() {
        ByteArrayOutputStream bous=new ByteArrayOutputStream();
        try
       {
          bous.write(mp.Get_Data1());
       }
       catch(IOException e)
       {} 
       File file=new File("./Pictures/" + bous.toString());
       if(file.isFile())
       {
           file.delete();
           
       }
       String FilePath="./Pictures/" + bous.toString();
       WriteFile wf=new WriteFile(FilePath);
       wf.Write(mp.Get_Data2());
       wf.Close();
       Signals singnals=new Signals(mp.Get_Receiver());
       byte[] data=null;
       sc.Send(singnals.GeneralSignal(mp.Get_Sender(),mp.Get_Scenario(),(byte)2,102,mp.Get_Priority(),bous.toByteArray(),data),Connection_Number);
       String ImagePath=null;  
        ServerIDFile serveridfile=new ServerIDFile();
         ReadFile rf=new ReadFile("./System/serverid.mms",serveridfile.Get_ServerIDFile_Size());
         if(rf.NumOfRecords()>0)
          {
              serveridfile.Set_ServerIDFile_Bytes(rf.Read(1));
              ImagePath=serveridfile.Get_ImagePath();
     	     // SoundPath=serveridfile.Get_SoundPath();
             // VideoPath=serveridfile.Get_VideoPath();
               rf.Close();
         }
         Loadprogramm(ImagePath,FilePath);
    }
     private synchronized void Loadprogramm(String ClassName,String FilePath)
    {
      
         if(ClassName.compareTo("")!=0 && FilePath.compareTo("")!=0)
          { 
                
              try
                {
                    Process proc = Runtime.getRuntime().exec(ClassName+ " " +FilePath );
                }
                catch(IOException e)
                {}
                catch(Exception e)
                {System.out.print(e.toString());}
          }  
     }
}
