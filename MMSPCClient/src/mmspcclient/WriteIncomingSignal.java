
package mmspcclient;


import java.util.Date;

public class WriteIncomingSignal {
    
    private  String str;
    private String ClientName;
    
    public WriteIncomingSignal(MP mp)
    {
        PaswdFile pf=new PaswdFile();
        ReadFile rf=new ReadFile("./System/paswdf.mms",pf.Get_Size());
        for(int x=1;x<=rf.NumOfRecords();x++) {
            pf.Set_PaswdFile_Byte(rf.Read(x));
            if(pf.Get_ClientID()==mp.Get_Sender()&& pf.Get_Scenario()==mp.Get_Scenario()) {
                ClientName=pf.Get_UserName_No_Space();
                break;
            }
        }
        Date date=new Date(mp.Get_Date_Time());
        str="Sender: " + ClientName +
          " Senario: " + mp.Get_Scenario() +
          " Signal: " + SignalDesc(mp) +
          " Date: " + date.toString() ;
         int mynumofparam=numofparam(mp);
         if(mynumofparam==1)
         {
                str=str+ " " + param1name(mp) + " : " + mp.Get_Data1_As_String();
         }
         if(mynumofparam==2)
         {
             str=str + " " + param2name(mp) + " : " + mp.Get_Data2_As_String();                
         }         
         str=str + " | ";    

        
    }
    public void SaveSignal()
    {
        WriteFile wf=new WriteFile( "./System/PreviewsIncSignal.txt");
        wf.Write(str.getBytes());
        wf.Close();
    }
    
     private synchronized int numofparam(MP mp)
    {
        int mynum=0;
       try
       {
           if(mp.Get_Signal_Type()<3)
            {    
                ControlProtocol cp=new ControlProtocol();
                for(int z=0;z<cp.sizeArray();z++)
                {
                    if(cp.signalsfile[z].Get_Signal_Type()==mp.Get_Signal_Type() && cp.signalsfile[z].Get_Signal_Command()==mp.Get_Signal_Command())
                    {
                        mynum=cp.signalsfile[z].Get_Parametrs();    
                        break;
                     }
                }
           }
           else
          {
                SignalsFile sf2=new SignalsFile();
                String externalsignalfilename=mp.Get_Sender()+"_"+mp.Get_Scenario()+"externalsignal.mms";
                ReadFile rf1=new ReadFile(externalsignalfilename,sf2.Get_SignalsFile_Size());
                for(int i=1;i<=rf1.NumOfRecords();i++)
                {
                   sf2.Set_SignalsFile_Bytes(rf1.Read(i));
                    if(sf2.Get_Signal_Type()==mp.Get_Signal_Type() && sf2.Get_Signal_Command()==mp.Get_Signal_Command())
                    {
                        mynum=sf2.Get_Parametrs();    
                        break;
                     }
                }
                try{rf1.Close();}
                catch(Exception e){}
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return mynum;
    }  
    
         private synchronized String param1name(MP mp)
    {
         String MyDesc="";
         try
        {
            if(mp.Get_Signal_Type()<3)
            {    
                ControlProtocol cp=new ControlProtocol();
                for(int z=0;z<cp.sizeArray();z++)
                {
                if(cp.signalsfile[z].Get_Signal_Type()==mp.Get_Signal_Type() && cp.signalsfile[z].Get_Signal_Command()==mp.Get_Signal_Command())
                    {
                        MyDesc=cp.signalsfile[z].Get_Parameter1Name_No_Space();    
                        break;
                     }
                }
            }
            else
            {
                SignalsFile sf2=new SignalsFile();
                String externalsignalfilename=mp.Get_Sender()+"_"+mp.Get_Scenario()+"externalsignal.mms";
                ReadFile rf1=new ReadFile(externalsignalfilename,sf2.Get_SignalsFile_Size());
                for(int i=1;i<=rf1.NumOfRecords();i++)
                {
                   sf2.Set_SignalsFile_Bytes(rf1.Read(i));
                    
                    if(sf2.Get_Signal_Type()==mp.Get_Signal_Type() && sf2.Get_Signal_Command()==mp.Get_Signal_Command())
                    {
                        MyDesc=sf2.Get_Parameter1Name_No_Space();    
                        break;
                     }
                }
                try{rf1.Close();}
                catch(Exception e){}
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
            return MyDesc;
    } 
     
    private synchronized String param2name(MP mp)
    {
        String MyDesc="";
       
        try
        {
            if(mp.Get_Signal_Type()<3)
            {    
                ControlProtocol cp=new ControlProtocol();
                for(int z=0;z<cp.sizeArray();z++)
                {
                    if(cp.signalsfile[z].Get_Signal_Type()==mp.Get_Signal_Type() && cp.signalsfile[z].Get_Signal_Command()==mp.Get_Signal_Command())
                    {
                        MyDesc=cp.signalsfile[z].Get_Parameter2Name_No_Space();    
                        break;
                     }
                }
            }
            else
            {
                SignalsFile sf2=new SignalsFile();
                String externalsignalfilename=mp.Get_Sender()+"_"+mp.Get_Scenario()+"externalsignal.mms";
                ReadFile rf1=new ReadFile(externalsignalfilename,sf2.Get_SignalsFile_Size());
                for(int i=1;i<=rf1.NumOfRecords();i++)
                {
                   sf2.Set_SignalsFile_Bytes(rf1.Read(i));
                    if(sf2.Get_Signal_Type()==mp.Get_Signal_Type() && sf2.Get_Signal_Command()==mp.Get_Signal_Command())
                    {
                        MyDesc=sf2.Get_Parameter2Name_No_Space();    
                        break;
                     }
                }
                try{rf1.Close();}
                catch(Exception e){}
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return MyDesc;
    } 
    
      private synchronized String SignalDesc(MP mp)
    {
        String MyDesc="";
        try
        {
           
            if(mp.Get_Signal_Type()<3)
            {    
                ControlProtocol cp=new ControlProtocol();
                for(int z=0;z<cp.sizeArray();z++)
                {
                    if(cp.signalsfile[z].Get_Signal_Type()==mp.Get_Signal_Type() && cp.signalsfile[z].Get_Signal_Command()==mp.Get_Signal_Command())
                    {
                         MyDesc=cp.signalsfile[z].Get_Desc_No_Space();    
                         break;
                    }
                }
            }
            else
            {
                SignalsFile sf2=new SignalsFile();
                String externalsignalfilename=mp.Get_Sender()+"_"+mp.Get_Scenario()+"externalsignal.mms";
                ReadFile rf1=new ReadFile(externalsignalfilename);
                for(int i=1;i<=rf1.NumOfRecords();i++)
                {
                    sf2.Set_SignalsFile_Bytes(rf1.Read(i));
                    if(sf2.Get_Signal_Type()==mp.Get_Signal_Type() && sf2.Get_Signal_Command()==mp.Get_Signal_Command())
                    {
                        MyDesc=sf2.Get_Desc_No_Space();    
                        break;
                    }
                }
                try{rf1.Close();}
               catch(Exception e){}
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return MyDesc;
    }
}
