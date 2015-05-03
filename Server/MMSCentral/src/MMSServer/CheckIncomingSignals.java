

package MMSServer;

import java.lang.ClassNotFoundException;
import java.lang.InstantiationException;
import java.lang.IllegalAccessException;
import MMSServer.SignalProcedures.ParentSignalProcess;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import MMSServer.WriteFile;

//This class receives every incoming signal 
//recognize them from signals specification and execute specified procdure
class CheckIncomingSignals {
    private SocConnect sc;
    private  ControlProtocol cp;
    public CheckIncomingSignals()
    {
    }
    
    
    public synchronized void init(SocConnect mysc, int Connection_Number, MP mp, byte ServerID)
    {
        
        int  delindexrec=-1;
        sc=mysc;
        cp=new ControlProtocol();
        boolean ok=true;
        boolean SameSenderAndScenariobutNotTypeAndCommand=false;
        
        //Check if incoming signal is an answer for an answer signal
        AnsSignal as=new AnsSignal();
        for(int x=0;x<as.Get_SizeOfAnsSignal();x++)
        {
            if(mp.Get_Sender()==as.Get_Signal(x).Get_SenderID() && mp.Get_Scenario()==as.Get_Signal(x).Get_Scenario())
            {
                if(mp.Get_Signal_Type()==as.Get_Signal(x).Get_Signal_Type()&& mp.Get_Signal_Command()==as.Get_Signal(x).Get_Signal_Command())
                    {
                            ok=true;
                            delindexrec=x;
                            break;
                    }
                    else
                    {
                        if(mp.Get_Signal_Type()==2 && mp.Get_Signal_Command()==3)
                        {
                             ok=true;
                             delindexrec=x;
                             break;
                        }
                        else
                        {
                            SameSenderAndScenariobutNotTypeAndCommand=true;
                        }
                    }
            }
            else
            {
                if(SameSenderAndScenariobutNotTypeAndCommand)
                {ok=false;delindexrec=0;}
                else
                {ok=true;delindexrec=x;}    
            }
        }
        /* Chech if signal have the same ip with the session*/
        Session session=new Session();
        for(int sessioncount=0;sessioncount<session.Get_SizeOfSession();sessioncount++)
        {
            if(mp.Get_Sender()==session.Get_Session(sessioncount).Get_UserID())
            {
                if(session.Get_Session(sessioncount).Get_IP().compareTo(sc.Get_Client_IP(Connection_Number))!=0)
                {
                    ok=false;
                    break;
                }
            }
        }
        
        /********************************************/
        if(ok)
        {
          //Set current time in Last _time attribute in session  
          Session session1=new Session();
          for(int sessioncount=0;sessioncount<session1.Get_SizeOfSession();sessioncount++)
          {
             if(mp.Get_Sender()==session1.Get_Session(sessioncount).Get_UserID())
            {
                Date dt=new Date();
                session1.Get_Session(sessioncount).Set_Last_Time(dt.getTime());
                break;
            }
          }
            DeleteRecFromAnsSignal(delindexrec);
            SignalsFile sf2=new SignalsFile();
            ReadFile rf1;
            if(mp.Get_Signal_Type() >2)
            {
              //Serviced signal form external protocol 
               rf1=new ReadFile("./System/"+mp.Get_Receiver()+"_"+mp.Get_Scenario()+"externalsignal.mms",sf2.Get_SignalsFile_Size());
               for(int i=1;i<=rf1.NumOfRecords();i++)
                {
                   sf2.Set_SignalsFile_Bytes(rf1.Read(i));
                    if(sf2.Get_Signal_Type()==mp.Get_Signal_Type() && sf2.Get_Signal_Command()==mp.Get_Signal_Command())
                    {
                        if(sf2.Get_UsedCommand_No_Space().compareTo("Server")!=0)
                        {
                            if(sf2.Get_ClassName()!=null)
                            {
                                Loadprogramm(sf2.Get_ClassName(), mysc,Connection_Number,mp,sf2);
                            }
                        }
                           break;
                     }
                }
                try{rf1.Close();}
                catch(Exception e){}
            }
            else
            {
               //Serviced signal form control protocol 
                for(int i=0;i<cp.sizeArray();i++)
                {
                   
                    if(cp.signalsfile[i].Get_Signal_Type()==mp.Get_Signal_Type() && cp.signalsfile[i].Get_Signal_Command()==mp.Get_Signal_Command())
                    {
                         if(cp.signalsfile[i].Get_ClassName_No_Space()!=null)
                          {
                            LoadClass(cp.signalsfile[i].Get_ClassName_No_Space(), mysc,Connection_Number,mp);
                           }
                           break;
                     }
                }
            }
            
        }
        else
        {
            sc.close();
        }
    }
    
    //If receive an signal which is the answer in an answer signal delete answer signal from answer signal list
    private void DeleteRecFromAnsSignal(int indexRec)
    {
        if(indexRec>=0)
        {    
            AnsSignal as=new AnsSignal();
            as.Delete_Signal(indexRec);
            
        }
    }
    
    //Load a program which service a specified signal
    private synchronized void Loadprogramm(String ClassName,SocConnect mysc,int Connection_Number,MP mp,SignalsFile sf)
    {
         String ProcessPath="";
         ByteArrayOutputStream baosdata1 =new ByteArrayOutputStream();
         ByteArrayOutputStream baosdata2 =new ByteArrayOutputStream();
         try
         {
             baosdata1.write(mp.Get_Data1());
             baosdata2.write(mp.Get_Data2());
         }    
         catch (IOException e)
         {}
         catch(Exception e)
         {}
     
         if(ClassName.compareTo("")!=0 || ClassName!="None")
          { 
                
               ProcessPath="./ExternalProcess/";
               int x ;
               String newClassName="";
               for (x=ClassName.length()-1;x>0;x--)
               {
                    if(ClassName.charAt(x)!=' ')
                    {
                        newClassName=ClassName.substring(0,x+1);
                        break;
                    }
                    newClassName=ClassName;
               }
               ProcessPath=ProcessPath + newClassName  + " " + String.valueOf(mp.Get_Sender())+ " " + String.valueOf(mp.Get_Signal_Type()) + " " + String.valueOf(mp.Get_Signal_Command()) + " " + String.valueOf(mp.Get_Scenario());
               if(baosdata1.toString().length()>0)
                {
                    if(sf.Get_Parametrs()==1)
                    {
                        ProcessPath=ProcessPath + " "  + baosdata1.toString();
                    }
                     File file=new File("./Temp/temp1" + mp.Get_Sender()+".tmp");
                     if(file.isFile())
                        {
                        file.delete();
           
                        }
                    WriteFile wf=new WriteFile("./Temp/temp1" + mp.Get_Sender()+".tmp");
                    wf.Write(baosdata1.toByteArray());
                    wf.Close();
                }
                if(baosdata2.toString().length()>0)
                {
                    if(sf.Get_Parametrs()==2)
                    {
                        ProcessPath=ProcessPath + " "  + baosdata2.toString();
                    }   
                     File file=new File("./Temp/temp2" + mp.Get_Sender()+".tmp");
                     if(file.isFile())
                        {
                        file.delete();
           
                        }
                    WriteFile wf=new WriteFile("./Temp/temp2" + mp.Get_Sender()+".tmp");
                    wf.Write(baosdata2.toByteArray());
                    wf.Close();
                }
                try
                {
                    System.out.println(ProcessPath);
                    Process proc = Runtime.getRuntime().exec(ProcessPath);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
          }

    }
    
    //Load a class which service a specified signal
    private synchronized void LoadClass(String ClassName,SocConnect mysc,int Connection_Number,MP mp)
    {
            ParentSignalProcess parentpignalprocess = new ParentSignalProcess(mysc,Connection_Number,mp);
          try
          {
                if(ClassName.compareTo("")!=0 || ClassName!="None")
                { 
                    Class t = Class.forName("MMSServer.SignalProcedures."+ClassName);
                    Object  SignalProcess= t.newInstance();
                }
          }
        catch(ClassNotFoundException e)
        {}
        catch(InstantiationException e)
        {}
        catch(IllegalAccessException e)
        {}
        catch(Exception e)
        {}
    }
}