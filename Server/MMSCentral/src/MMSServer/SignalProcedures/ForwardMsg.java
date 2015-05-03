

package MMSServer.SignalProcedures;

import java.io.RandomAccessFile;
import java.io.IOException;
import java.util.Date;
import MMSServer.MP;
import MMSServer.ReadFile;
import MMSServer.PaswdFile;
import MMSServer.Signals;
import java.io.ByteArrayOutputStream;

public class ForwardMsg extends ParentSignalProcess{
    

    public ForwardMsg() {
       byte recieverid=0;
       ByteArrayOutputStream bout=new ByteArrayOutputStream();
       try
       {
            bout.write(mp.Get_Data1());
       }
       catch(IOException ioe)
       {}
       PaswdFile pf=new PaswdFile();
       ReadFile rf=rf=new ReadFile("./System/paswdf.mms",pf.Get_Size());
       for(int x=1;x<=rf.NumOfRecords();x++)
       {
            pf.Set_PaswdFile_Byte(rf.Read(x));
            if(bout.toString().compareTo(pf.Get_UserName_No_Space())==0)
            {
                recieverid=pf.Get_ClientID();
                break;
            }
       }
       rf.Close();
       Date dt=new Date();
       MP mp1 =new MP();
       mp1.Set_Sender(mp.Get_Receiver());
       mp1.Set_Receiver(recieverid);
       mp1.Set_Priority(mp.Get_Priority());
       mp1.Set_Scenario(mp.Get_Scenario());
       mp1.Set_Date_Time(dt.getDate());
       mp1.Set_Signal_Command((int)107);
       mp1.Set_Signal_Type((byte)1);
       mp1.Set_Data1(mp.Get_Data1());
       mp1.Set_Data2(mp.Get_Data2());
       RandomAccessFile raf=null;
       try 
       {
           raf=new RandomAccessFile("./System/ExtSignalFile.mms","rw");
           raf.seek(raf.length());
           raf.write(mp1.Get_MMP_Byte());
           raf.close();
       }
       catch (IOException e)
       {e.printStackTrace();}
        Signals singnals=new Signals(mp.Get_Receiver());
        byte[] data=null;
        byte mytype=mp.Get_Signal_Type();
        mytype++;
        sc.Send(singnals.GeneralSignal(mp.Get_Sender(),mp.Get_Scenario(),mytype,mp.Get_Signal_Command(),mp.Get_Priority(),mp.Get_Data1(),data),Connection_Number);
    }
    
}
