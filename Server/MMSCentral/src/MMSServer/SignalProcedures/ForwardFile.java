

package MMSServer.SignalProcedures;

import java.io.RandomAccessFile;
import java.io.IOException;
import java.util.Date;
import MMSServer.MP;
import MMSServer.ReadFile;
import MMSServer.PaswdFile;
import MMSServer.Signals;
import java.io.ByteArrayOutputStream;

public class ForwardFile extends ParentSignalProcess{
    
    private String [] params;
    public ForwardFile() {
       byte recieverid=0;
       ByteArrayOutputStream bout=new ByteArrayOutputStream();
       try
       {
            bout.write(mp.Get_Data1());
       }
       catch(IOException ioe)
       {}
       params=MyParam(bout.toString());
       PaswdFile pf=new PaswdFile();
       ReadFile rf=rf=new ReadFile("./System/paswdf.mms",pf.Get_Size());
       for(int x=1;x<=rf.NumOfRecords();x++)
       {
            pf.Set_PaswdFile_Byte(rf.Read(x));
            if(params[0].compareTo(pf.Get_UserName_No_Space())==0)
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
       mp1.Set_Signal_Command((int)109);
       mp1.Set_Signal_Type((byte)1);
       mp1.Set_Data1(mp.Get_Data1());
       mp1.Set_Data2(mp.Get_Data2());
       Signals singnals=new Signals(mp.Get_Receiver());
       byte mytype=mp.Get_Signal_Type();
       mytype++;
       sc.Send(singnals.GeneralSignal(mp.Get_Sender(),mp.Get_Scenario(),mytype,mp.Get_Signal_Command(),mp.Get_Priority(),params[0],params[2]),Connection_Number);
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
        
    }
   
    private String[] MyParam(String users)
{
    String[] Str;
    int myint=0;
    int count=0;
    while(myint!=-1)
    {
        myint=users.indexOf(",",myint+1);
        count++;
    }
    Str=new String[count];
    count=0;
    myint=0;
    while(myint!=-1)
    {
        int myitnbigen=myint;
        myint=users.indexOf(",",myint+1);
        int myitnend=myint;
        if(myitnend==-1)
        {
            myitnend=users.length();
        }
        if(myitnbigen!=0)
        {
            myitnbigen=myitnbigen+1;
        }
        Str[count]=users.substring(myitnbigen,myitnend);
        count++;
    }
    return Str;
}
    
}
