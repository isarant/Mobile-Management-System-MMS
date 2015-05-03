
package MMSServer.SignalProcedures;

import MMSServer.*;
import java.util.Date;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.String;


public class ChkPasswdSendFile extends ParentSignalProcess{
    private ReadFile rf;
    private PaswdFile pf;
    private String UserName=null;
    //private byte ClientID=(byte)0;
    private boolean enableconnection=false;
    private byte ServerID;
       
    public ChkPasswdSendFile()
    {
     super();
     int a=mp.Get_Scenario();
      ServerIDFile serveridfile=new ServerIDFile();
     ReadFile rf2=new ReadFile("./System/serverid.mms",serveridfile.Get_ServerIDFile_Size());
     synchronized(rf2)
     {
        if(rf2.NumOfRecords()>0)
          {
              serveridfile.Set_ServerIDFile_Bytes(rf2.Read(1));
              ServerID=serveridfile.Get_ServerID();
              rf2.Close();
          }
     }
     pf=new PaswdFile();
     ReadFile rf=new ReadFile("./System/paswdf.mms",pf.Get_Size());
     synchronized(rf)
     {
         for (int x=1;x<=rf.NumOfRecords();x++)
         {
             pf.Set_PaswdFile_Byte(rf.Read(x));
             if (mp.Get_Scenario() == pf.Get_Scenario() && mp.Get_Receiver()==ServerID)
             {
                if(mp.Get_Sender()==pf.Get_ClientID())
                {
                   ByteArrayOutputStream OutData1=new ByteArrayOutputStream();
                   try{
                        OutData1.write(mp.Get_Data1());
                       }catch(IOException e)
                       {}
                    String a2=OutData1.toString();
                    if(OutData1.toString().compareTo(pf.Get_UserName())==0)
                    {
                        ByteArrayOutputStream OutData2=new ByteArrayOutputStream();
                        try{
                        OutData2.write(mp.Get_Data2());
                       }catch(IOException e)
                       {}
                        try{
                        String a1=OutData2.toString();
                        if(OutData2.toString().compareTo(pf.Get_Passwd())==0)
                        {
                            if(ChechDoubleConnection())
                            {
                                Signals singnals=new Signals(ServerID);
                                sc.Send(singnals.AcceptLogOnAndSendSignalFile(pf.Get_ClientID(),pf.Get_Scenario()),Connection_Number);
                                //ClientID=pf.Get_ClientID();
                                UserName=pf.Get_UserName();
                                SessionFile sf=new SessionFile(mp.Get_Receiver(),pf.Get_ClientID(),Connection_Number,pf.Get_Scenario(),sc.Get_Client_IP(Connection_Number),pf.Get_KeyNum());
                                Session session =new Session();
                                session.Add_Session(sf,sc);
                                enableconnection=true;
                                 break;
                            }
                           
                        }
                        }
                       catch(Exception e)
                       {e.printStackTrace();}
                    }
                }
             }
         }
        rf.Close();
     }
     if(!enableconnection)
     {
        if (mp.Get_Scenario() == pf.Get_Scenario() && mp.Get_Receiver()==ServerID)
        {
             Signals singnals=new Signals(ServerID);
            sc.Send(singnals.DenitedLogOnNoSendFile(pf.Get_ClientID(),pf.Get_Scenario()),Connection_Number);
            sc.closeConnection(Connection_Number);
            System.out.print("check password file denide");
        }
     }
 }
 public String Get_UserName()
 {
     return UserName;
 }
 
 /*public byte Get_ClientID()
 {
    return ClientID;
 }*/
 private boolean ChechDoubleConnection()
 {
     boolean mybool=true;
      Session session =new Session();
     for(int x=0;x<session.Get_SizeOfSession();x++)
     {
         if(mp.Get_Scenario()==session.Get_Session(x).Get_Scenario() && mp.Get_Receiver()==ServerID )
         {
             if(mp.Get_Sender()==session.Get_Session(x).Get_UserID())
             {
                 mybool=false;
             }
        }
     }
     return mybool;    
 }


 }
