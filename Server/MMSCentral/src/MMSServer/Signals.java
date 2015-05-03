
package MMSServer;


import java.util.Date;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Signals {
    private byte SenderID;
        
    public Signals(byte SenderID) {
        this.SenderID=SenderID;
    }
    
     public byte[] LogOnAndSendMeSignalFile(byte RecieverID ,int ScenarioID,String Data1,String Data2)
     {
          MP mp=new MP();
          mp.Set_Sender(SenderID);
          mp.Set_Receiver(RecieverID);
          mp.Set_Priority((byte)1);
          mp.Set_Signal_Type((byte)1);
          mp.Set_Scenario(ScenarioID);
          Date dt=new Date();
          mp.Set_Date_Time(dt.getTime());
          mp.Set_Signal_Command(0);
          mp.Set_Data1(Data1.getBytes());
          mp.Set_Data2(Data2.getBytes());
          //writeAnsSignal(mp.Get_Signal_Type(),mp.Get_Signal_Command(),mp.Get_Sender(),mp.Get_Scenario());
          writeAnsSignal(mp.Get_Signal_Type(),mp.Get_Signal_Command(),mp.Get_Receiver(),mp.Get_Scenario());
          return mp.Get_MMP_Byte();
     }
     
    public byte[] LogOn(byte RecieverID ,int ScenarioID,String Data1,String Data2)
     {
          MP mp=new MP();
          mp.Set_Sender(SenderID);
          mp.Set_Receiver(RecieverID);
          mp.Set_Priority((byte)1);
          mp.Set_Signal_Type((byte)1);
          mp.Set_Scenario(ScenarioID);
          Date dt=new Date();
          mp.Set_Date_Time(dt.getTime());
          mp.Set_Signal_Command(1);
          mp.Set_Data1(Data1.getBytes());
          mp.Set_Data2(Data2.getBytes());
          //writeAnsSignal(mp.Get_Signal_Type(),mp.Get_Signal_Command(),mp.Get_Sender(),mp.Get_Scenario());
          writeAnsSignal(mp.Get_Signal_Type(),mp.Get_Signal_Command(),mp.Get_Receiver(),mp.Get_Scenario());
          return mp.Get_MMP_Byte();
     }
    
     public byte[] AcceptLogOn(byte RecieverID ,int ScenarioID)
    {
          MP mp2=new MP();
          mp2.Set_Sender(SenderID);
          mp2.Set_Receiver(RecieverID);
          mp2.Set_Priority((byte)1);
          mp2.Set_Scenario(ScenarioID);
          Date dt=new Date();
          mp2.Set_Date_Time(dt.getTime());
          mp2.Set_Signal_Command(1);
          mp2.Set_Signal_Type((byte)2);
          mp2.Set_Data1("ok".getBytes());
          return mp2.Get_MMP_Byte();
     }
     
    public byte[] AcceptLogOnAndSendSignalFile(byte RecieverID ,int ScenarioID)
    {
          MP mp2=new MP();
          mp2.Set_Sender(SenderID);
          mp2.Set_Receiver(RecieverID);
          mp2.Set_Priority((byte)1);
          mp2.Set_Scenario(ScenarioID);
          Date dt=new Date();
          mp2.Set_Date_Time(dt.getTime());
          mp2.Set_Signal_Command(0);
          mp2.Set_Signal_Type((byte)2);
          mp2.Set_Data1("ok".getBytes());
          SignalsFile sf=new SignalsFile();
          ByteArrayOutputStream OutData=new ByteArrayOutputStream();
          String externalsignalfilename="./System/"+SenderID+"_"+ScenarioID+"externalsignal.mms";
          ReadFile rf=new ReadFile(externalsignalfilename,sf.Get_SignalsFile_Size());
          for(int x=1;x<=rf.NumOfRecords();x++)
          {
              try
              {
                OutData.write(rf.Read(x));
              }
              catch(IOException e)
              {}
          }
          rf.Close();
          mp2.Set_Data2(OutData.toByteArray());
          return mp2.Get_MMP_Byte();
     }
      
     public byte[] DenitedLogOn(byte RecieverID ,int ScenarioID)
     {
          MP mp2=new MP();
          mp2.Set_Sender(SenderID);
          mp2.Set_Receiver(RecieverID);
          mp2.Set_Priority((byte)1);
          mp2.Set_Scenario(ScenarioID);
          Date dt=new Date();
          mp2.Set_Date_Time(dt.getTime());
          mp2.Set_Signal_Command(1);
          mp2.Set_Signal_Type((byte)2);
          mp2.Set_Data1("no".getBytes());
          return mp2.Get_MMP_Byte();
     }
     public byte[] DenitedLogOnNoSendFile(byte RecieverID ,int ScenarioID)
     {
          MP mp2=new MP();
          mp2.Set_Sender(SenderID);
          mp2.Set_Receiver(RecieverID);
          mp2.Set_Priority((byte)1);
          mp2.Set_Scenario(ScenarioID);
          Date dt=new Date();
          mp2.Set_Date_Time(dt.getTime());
          mp2.Set_Signal_Command(0);
          mp2.Set_Signal_Type((byte)2);
          mp2.Set_Data1("no".getBytes());
          return mp2.Get_MMP_Byte();
     }
     public byte[] LogOff(byte RecieverID ,int ScenarioID)
     {
          MP mp=new MP();
          mp.Set_Sender(SenderID);
          mp.Set_Receiver(RecieverID);
          mp.Set_Priority((byte)1);
          mp.Set_Signal_Type((byte)2);
          mp.Set_Scenario(ScenarioID);
          Date dt=new Date();
          mp.Set_Date_Time(dt.getTime());
          mp.Set_Signal_Command(2);
          return mp.Get_MMP_Byte();
     } 
     
      public byte[] AreYouAlive(byte RecieverID ,int ScenarioID)
     {
          MP mp=new MP();
          mp.Set_Sender(SenderID);
          mp.Set_Receiver(RecieverID);
          mp.Set_Priority((byte)1);
          mp.Set_Signal_Type((byte)1);
          mp.Set_Scenario(ScenarioID);
          Date dt=new Date();
          mp.Set_Date_Time(dt.getTime());
          mp.Set_Signal_Command(3);
         // writeAnsSignal(mp.Get_Signal_Type(),mp.Get_Signal_Command(),mp.Get_Sender(),mp.Get_Scenario());
          writeAnsSignal(mp.Get_Signal_Type(),mp.Get_Signal_Command(),mp.Get_Receiver(),mp.Get_Scenario());
          return mp.Get_MMP_Byte();
     } 
      
     public byte[] IAmAlive(byte RecieverID ,int ScenarioID)
     {
          MP mp=new MP();
          mp.Set_Sender(SenderID);
          mp.Set_Receiver(RecieverID);
          mp.Set_Priority((byte)1);
          mp.Set_Signal_Type((byte)2);
          mp.Set_Scenario(ScenarioID);
          Date dt=new Date();
          mp.Set_Date_Time(dt.getTime());
          mp.Set_Signal_Command(3);
          return mp.Get_MMP_Byte();
          
     }  
      
      public byte[] GeneralSignal(byte RecieverID ,int ScenarioID,byte SignalType,int SignalCommand,byte Priority,String Data1,String Data2)
     {
          MP mp=new MP();
          mp.Set_Sender(SenderID);
          mp.Set_Receiver(RecieverID);
          mp.Set_Priority(Priority);
          mp.Set_Signal_Type(SignalType);
          mp.Set_Scenario(ScenarioID);
          Date dt=new Date();
          mp.Set_Date_Time(dt.getTime());
          mp.Set_Signal_Command(SignalCommand);
          if(Data1!=null)
          {
            mp.Set_Data1(Data1.getBytes());
          }
          if(Data2!=null)
          {
            mp.Set_Data2(Data2.getBytes());
          }
          if(SignalType==1 || SignalType==3)
          {
              writeAnsSignal(mp.Get_Signal_Type(),mp.Get_Signal_Command(),mp.Get_Receiver(),mp.Get_Scenario());
          }
          return mp.Get_MMP_Byte();
     }
     public byte[] GeneralSignal(byte RecieverID ,int ScenarioID,byte SignalType,int SignalCommand,byte Priority,byte[] Data1,byte[] Data2)
     {
          MP mp=new MP();
          mp.Set_Sender(SenderID);
          mp.Set_Receiver(RecieverID);
          mp.Set_Priority(Priority);
          mp.Set_Signal_Type(SignalType);
          mp.Set_Scenario(ScenarioID);
          Date dt=new Date();
          mp.Set_Date_Time(dt.getTime());
          mp.Set_Signal_Command(SignalCommand);
          if(Data1!=null)
          {
              mp.Set_Data1(Data1);
          }
          if(Data2!=null)
          {
            mp.Set_Data2(Data2);
          }
          if(SignalType==1 || SignalType==3)
          {
              writeAnsSignal(mp.Get_Signal_Type(),mp.Get_Signal_Command(),mp.Get_Receiver(),mp.Get_Scenario());
          }
          return mp.Get_MMP_Byte();
     } 
     
     private void writeAnsSignal(byte Signal_Type,int Signal_Command,byte SenderID,int Scenario)
     {
       AnsSignalsFile sf=new AnsSignalsFile();
       sf.Set_Signal_Command(Signal_Command);
       sf.Set_Scenario(Scenario);
       sf.Set_SenderID(SenderID);
       Date dt=new Date();
       sf.Set_Date_Time(dt.getTime());
       if(Signal_Type==1)
       {
        sf.Set_Signal_Type((byte)2);
       }
       if(Signal_Type==3)
       {
        sf.Set_Signal_Type((byte)4);
       }
       AnsSignal as=new AnsSignal();
       as.Add_Signal(sf);
     }
}