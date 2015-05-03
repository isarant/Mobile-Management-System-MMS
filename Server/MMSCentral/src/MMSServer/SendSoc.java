

package MMSServer;


import java.io.IOException;
import java.util.Date;
import MMSServer.MP;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.lang.InterruptedException;
import java.io.InputStream;
import MMSServer.SocConnect;

class SendSoc extends Thread
{
    private byte[] data;
    private static SocConnect sc;
    private ServerCrypt servercrypt;
    private int numofconnection;
    private Date dt1;
    private long mytime;
    private final int packsize=500;
    private final int sendtime=1000;
    private String remote;
     
    
    public SendSoc(byte[] data,SocConnect soc,int numofconnection,String remote)
    {
      
      try
      {
        this.sc=soc;
        this.data=data;
        this.remote=remote;
        this.numofconnection=numofconnection;
        start();
      }
      catch(Exception e)
      {}
       
    }
     
    public synchronized void run()
    {
        
        while(sc.SendData[numofconnection])
        {
            try
            {
                this.sleep(sendtime/4);
            }
            catch(InterruptedException e)
            {}
        }
        sc.SendData[numofconnection]=true;
        MP mp= new MP();
        mp.Set_MMP_Bytes(data);
        servercrypt=new ServerCrypt(mp.Get_Receiver(),mp.Get_Scenario());
        if(mp.Get_Data1()!=null)
        {
           mp.Set_Data1(servercrypt.EnCryptData(mp.Get_Data1()));
        }
        if(mp.Get_Data2()!=null)
        {
           mp.Set_Data2(servercrypt.EnCryptData(mp.Get_Data2()));
        }
        byte[] hlpdata=mp.Get_MMP_Byte();
        if(mp.IsItOk())
        {
            if(sc.enable[numofconnection])
            {
                try
                {
                    if(hlpdata.length<=sc.defaultbufsize)  
                    {
                        if(hlpdata.length>packsize)
                        {
                           InputStream bin=new ByteArrayInputStream(hlpdata);
                            while(bin.available()>0)
                            {
                               byte[] datapack=null;
                               if(bin.available()>=packsize)
                               {
                                  datapack= new byte[packsize];
                               }
                                else
                               {
                                  datapack= new byte[bin.available()];
                               }
                               bin.read(datapack);
                               sc.out[numofconnection].write(datapack);
                               sc.out[numofconnection].flush();
                             // System.out.println("SEND data : " + mp.Get_Data1_As_String() + "  " + datapack.length);
                               try
                               {
                
                                    this.sleep(sendtime);
                                }
                                catch(InterruptedException e)
                                {}
                            }
                        }
                        else
                        {
                            sc.out[numofconnection].write(hlpdata);
                            sc.out[numofconnection].flush();
                            //System.out.println("send data");
                            if (mp.Get_Signal_Type()==(byte)2)
                            {
                               if (mp.Get_Signal_Command()==2)
                               {
                                   sc.closeConnection(numofconnection);
                               }
                            }
                        }
                    }
                    else
                    {
                        String s="You can not sent file biger than " + sc.defaultbufsize + " byte";
                        Alertform alertform=new Alertform(s);
                        alertform.show();
                    }
               
                }
                catch(IOException ioe) 
                {
                    ioe.printStackTrace();
                    sc.enable[numofconnection]=false;
                }
                catch(Exception e) 
                {
                    e.printStackTrace();
                     sc.enable[numofconnection]=false;
                }
                sc.SendData[numofconnection]=false;
            }
        }
    }
   
    
}

