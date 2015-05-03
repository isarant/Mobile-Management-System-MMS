
package MMSServer.SignalProcedures;

import MMSServer.ReadFile;
import MMSServer.Signals;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import MMSServer.ReadFile;
import MMSServer.Session;
import MMSServer.SessionFile;
import MMSServer.PaswdFile;

public class SendUsersFile extends ParentSignalProcess {
    
    public SendUsersFile()
    {
        super();
        if(mp.Get_Signal_Type()==1 && mp.Get_Signal_Command()==105)
        {
            Signals singnals=new Signals(mp.Get_Receiver());
            ByteArrayOutputStream bous=new ByteArrayOutputStream();
              PaswdFile pf=new PaswdFile();
            ReadFile rf=new ReadFile("./System/paswdf.mms",pf.Get_Size());
          
            try
            {
                for(int x=1;x<=rf.NumOfRecords();x++)
                {       
                    pf.Set_PaswdFile_Byte(rf.Read(x));
                    if(pf.Get_Scenario()==mp.Get_Scenario())
                    {
                        String s=pf.Get_UserName_No_Space();
                        if(x<rf.NumOfRecords())
                            {s=s+",";} 
                        bous.write(s.getBytes());
                    }
                }
            }
            catch(IOException ioe)
            {}
             String filename;
            filename=mp.Get_Receiver() + "_" + mp.Get_Scenario()+"AllUsers.txt";
            sc.Send(singnals.GeneralSignal(mp.Get_Sender(),mp.Get_Scenario(),(byte)2,105,mp.Get_Priority(),filename.getBytes(),bous.toByteArray()),Connection_Number);
          }
         if(mp.Get_Signal_Type()==1 && mp.Get_Signal_Command()==104)
         {
            Signals singnals=new Signals(mp.Get_Receiver());
            ByteArrayOutputStream bous=new ByteArrayOutputStream();
            PaswdFile pf=new PaswdFile();
            ReadFile rf=new ReadFile("./System/paswdf.mms",pf.Get_Size());
            
            Session session=new Session();
            try
            {
               for(int z=0;z<session.Get_SizeOfSession();z++)
               {
                    for(int x=1;x<=rf.NumOfRecords();x++)
                    {       
                        pf.Set_PaswdFile_Byte(rf.Read(x));
                        if(session.Get_Session(z).Get_UserID()==pf.Get_ClientID())
                        {
                            if(pf.Get_Scenario()==mp.Get_Scenario())
                            {
                                String s=pf.Get_UserName_No_Space();
                                if(z<session.Get_SizeOfSession()-1)
                                {s=s+",";} 
                                bous.write(s.getBytes());
                                break;
                            }
                        }
                    }
                }
            }
            catch(IOException ioe)
            {}
            String filename;
            filename=mp.Get_Receiver()+ "_" + mp.Get_Scenario()+"ActUsers.txt";
            sc.Send(singnals.GeneralSignal(mp.Get_Sender(),mp.Get_Scenario(),(byte)2,104,mp.Get_Priority(),filename.getBytes(),bous.toByteArray()),Connection_Number);
          }
        }
    }
    

