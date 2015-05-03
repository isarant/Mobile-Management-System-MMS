

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

public class PlayMedia extends ParentSignalProcess{
    
    /** Creates a new instance of PlayMedia */
    public PlayMedia() {
     ByteArrayOutputStream bous=new ByteArrayOutputStream();
        try
       {
          bous.write(mp.Get_Data1());
       }
       catch(IOException e)
       {} 
        String MediaFile= bous.toString();
        String ext=MediaFile.substring(MediaFile.indexOf("."),MediaFile.length());
        String ct=null;
        String FilePath="";
        if (ext.toLowerCase().compareTo(".wmv")==0 || ext.toLowerCase().compareTo(".avi")==0 || ext.toLowerCase().compareTo(".mpeg")==0 || ext.toLowerCase().compareTo(".mpg")==0) {
             File file=new File("./Media/" + bous.toString());
            if(file.isFile())
            {
                file.delete();
            }
            FilePath="./Media/" + bous.toString();
         } else if (ext.toLowerCase().compareTo(".wav")==0 || ext.toLowerCase().compareTo(".midi")==0 || ext.toLowerCase().compareTo(".mid")==0 || ext.toLowerCase().compareTo(".kar")==0) {
               File file=new File("./Media/" + bous.toString());
            if(file.isFile())
            {
                file.delete();
            }
            FilePath="./Media/" + bous.toString();
        }
     
       if(FilePath.compareTo("")!=0)
       {    
            WriteFile wf=new WriteFile(FilePath);
            wf.Write(mp.Get_Data2());
            wf.Close();
            Signals singnals=new Signals(mp.Get_Receiver());
            byte[] data=null;
            sc.Send(singnals.GeneralSignal(mp.Get_Sender(),mp.Get_Scenario(),(byte)2,103,mp.Get_Priority(),bous.toByteArray(),data),Connection_Number);
            String SoundPath=null;  
            String VideoPath=null;  
            ServerIDFile serveridfile=new ServerIDFile();
            ReadFile rf=new ReadFile("./System/serverid.mms",serveridfile.Get_ServerIDFile_Size());
            if(rf.NumOfRecords()>0)
            {
              serveridfile.Set_ServerIDFile_Bytes(rf.Read(1));
              SoundPath=serveridfile.Get_SoundPath();
              VideoPath=serveridfile.Get_VideoPath();
              rf.Close();
            }
            if (ext.toLowerCase().compareTo(".wmv")==0 || ext.toLowerCase().compareTo(".avi")==0 || ext.toLowerCase().compareTo(".mpeg")==0 || ext.toLowerCase().compareTo(".mpg")==0) {
                Loadprogramm(SoundPath,FilePath);
            } else if (ext.toLowerCase().compareTo(".wav")==0 || ext.toLowerCase().compareTo(".midi")==0 || ext.toLowerCase().compareTo(".mid")==0 || ext.toLowerCase().compareTo(".kar")==0) {
                Loadprogramm(VideoPath,FilePath);
            }
       }
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
