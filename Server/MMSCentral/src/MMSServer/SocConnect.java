package MMSServer;

import java.io.IOException;
import java.net.SocketException;
import java.lang.InterruptedException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.ByteArrayInputStream;

public class SocConnect implements Runnable
//public class SocConnect 
{
    private int Port;
    private int Numberofconnection=0;
    private  InetAddress LocalInetAddress;
    private static InetAddress[] remoteInetAddress;
    protected static Socket[] client;
    private ServerSocket server;
    protected static OutputStream[] out;
    private static InputStream[] in;
    private ByteArrayOutputStream[] outbuf;
    protected static boolean[] enable;
    protected static boolean[] SendData;
    private boolean[] readflag;
    private static InetAddress MyInetAddress;
    private int MaxConnection=1000;   
    public static final int defaultbufsize=100000;
    private ServerCrypt servercrypt;
    protected long timeoflastsend=0; 
    
    public  SocConnect()
    {
     
    }
    
    
    public  SocConnect(int Port) {
        client=new Socket[MaxConnection];     
        out=new OutputStream[MaxConnection];
        in= new InputStream[MaxConnection];
        outbuf=new ByteArrayOutputStream[MaxConnection];
        remoteInetAddress=new InetAddress[MaxConnection];
        enable=new boolean[MaxConnection];
        SendData=new boolean[MaxConnection];
        readflag=new boolean[MaxConnection];
        for(int x=0;x<MaxConnection;x++)
        {
            client[x]=null;
            out[x]=null;
            in[x]=null;
            remoteInetAddress[x]=null;
            enable[x]=false;
            SendData[x]=false;
            readflag[x]=false;
        }
        this.Port=Port;
         try{
           MyInetAddress= InetAddress.getLocalHost();
         }
         catch(IOException e) 
         {  }
        new Thread(this).start();
    }
    
    public int IntervalWaitReadTime()
    {
        return (defaultbufsize/9600)*1000;
    }
      
    public  void run() {
         try{  
           server=new ServerSocket(Port,1,MyInetAddress);
         }
         catch(IOException e) 
         {
           close();
         }
         while(true) {
             if(Numberofconnection<MaxConnection)
             {
                try{
                    LocalInetAddress=server.getInetAddress();
               }
               catch(Exception e) 
               {}
               try{
                 int newNumberofconnection=Numberofconnection;
                 client[newNumberofconnection]=server.accept();
                 if(newNumberofconnection != Numberofconnection)
                 {
                     client[Numberofconnection]=client[newNumberofconnection];
                     client[newNumberofconnection]=null;
                 }
                 // client[Numberofconnection].setReceiveBufferSize(defaultbufsize);
                 //client[Numberofconnection].setSendBufferSize(defaultbufsize);
                 client[Numberofconnection].setTcpNoDelay(true);
                 enable[Numberofconnection]=true;
                 remoteInetAddress[Numberofconnection]=client[Numberofconnection].getInetAddress();
                 out[Numberofconnection]=client[Numberofconnection].getOutputStream();
                 in[Numberofconnection]=client[Numberofconnection].getInputStream();
                 outbuf[Numberofconnection]= new ByteArrayOutputStream();
                 outbuf[Numberofconnection].reset();
                 Numberofconnection++;
               }
               catch(Exception e) 
               {
                  for(int x=Numberofconnection;x<client.length;x++)
                  {
                    try
                    {
                        if(client[x]!=null)
                        {
                            client[x].close();
                            client[x]=null;   
                        }
                    }
                    catch(IOException ioe)
                    {} 
                    try
                    {
                        if(in[x]!=null)
                        {
                            in[x].close();
                            in[x]=null;   
                        }
                        }
                    catch(IOException ioe)
                    {}
                    try
                    {
                        if(out[x]!=null)
                        {
                            out[x].close();
                            out[x]=null;   
                        }
                   }
                    catch(IOException ioe)
                    {}
                    enable[x]=false;  
                  }
                   
                  //break;
               }
             }
                           
               try{
                Thread.sleep(10);
               }
               catch(InterruptedException e)
               {}
           }
    }
   
 
 
    public boolean Check_Connection(int NumOfConnection)
    {
        boolean mybool=false;
        Socket varclient=client[NumOfConnection];
        try
        {
            mybool=varclient.isConnected() ;
            if(mybool)
            {
                mybool=enable[NumOfConnection];
            }
        }
        catch(Exception e)
        {mybool=false;}
        return mybool;
    }
     public String Get_Server_IP()
     {
        return  MyInetAddress.getHostAddress();
     }
   
    
    public int Get_Max_Number_Of_Connection()
    {
        return Numberofconnection;
    }
    
    public String Get_Client_IP(int NumOfConnection)
    {
        String ipstr="";
        try
        {
            if(remoteInetAddress[NumOfConnection]==null)
            {
                ipstr=client[NumOfConnection].getInetAddress().getHostAddress();
            }
            else
            {
                ipstr=remoteInetAddress[NumOfConnection].getHostAddress();
            }
            
        }
        catch (Exception e)
        {System.out.print("null client ip");}
        return ipstr;   
    }

    public int Get_Connection_Num_From_Client_IP(String Client_IP)
    {
        int connum=-1;
        for(int x=0;x<=Numberofconnection;x++)
        {
           try
           {
                if(Client_IP.compareTo(remoteInetAddress[x].getHostAddress())==0)
                {
                    connum=x;
                    break;
                }
           }
           catch(Exception e)
           {}
        }
        return connum;
    }
    
 
    public synchronized void Send(byte[] data,int NumOfConnection)
    {
        try{
        SendSoc ss;
        ss=new SendSoc(data,this,NumOfConnection,Get_Client_IP(NumOfConnection));
        }
        catch(Exception e)
        {}
    }
  
    
  public synchronized MP Read(int NumOfConnection)
    {
       MP mp=null;
       MP mphelp=null;
       byte[] data=null;
       byte[] Alldata=null;
       int count=0;
       int numdata=0;
       int Alldatcount=0;
       if(enable[NumOfConnection])
       {
            try{
                numdata=in[NumOfConnection].available();
                if (numdata>0 )
                 {
                           data=new byte[numdata];
                           count =in[NumOfConnection].read(data);
                           readflag[NumOfConnection]=true;
                           outbuf[NumOfConnection].write(data);
                           
                   }
                   else
                   {
                            if(readflag[NumOfConnection])
                            {
                               readflag[NumOfConnection]=false;
                               Alldata=outbuf[NumOfConnection].toByteArray();
                               Alldatcount=Alldata.length;
                               outbuf[NumOfConnection].reset();
                            
                               if(Alldatcount>0)
                               {
                                 mp=new MP();
                                 mp.Set_MMP_Bytes(Alldata);
                                 if(mp.IsItOk() && Alldata.length>=mp.Get_MMP_Size())
                                 {
                                    servercrypt=new ServerCrypt(mp.Get_Sender(),mp.Get_Scenario());
                                    if(mp.Get_Data1()!=null)
                                    {
                                        mp.Set_Data1(servercrypt.DeCryptData(mp.Get_Data1()));
                                    }
                                    if(mp.Get_Data2()!=null)
                                    {
                                        mp.Set_Data2(servercrypt.DeCryptData(mp.Get_Data2()));
                                    }
                                    if(mp.Get_MMP_Size()<Alldata.length)
                                    {
                                        byte[] hlpdata=new byte[mp.Get_MMP_Size()];
                                        ByteArrayInputStream hlpstream=new ByteArrayInputStream(Alldata);
                                        hlpstream.read(hlpdata);
                                        byte[] hlpdata2=new byte[hlpstream.available()];
                                        hlpstream.read(hlpdata2);
                                        outbuf[NumOfConnection].write(hlpdata2);
                                        readflag[NumOfConnection]=true;
                                    }
                                 }
                                 else
                                 {
                                    if(mp.IsOutofMemory())
                                    {
                                         
                                       
                                        try
                                         {
                                            for(int x=0;x<in[NumOfConnection].available();x++)
                                            {
                                                byte[] a=new byte[1]; 
                                                count =in[NumOfConnection].read(a);
                                            }
                                            outbuf[NumOfConnection].reset();
                                            readflag[NumOfConnection]=false;
                                            mp=null;
                                        }
                                        catch(IOException ioe)
                                        {
                                            mp=null;
                                            ioe.printStackTrace();
                                        }
                                    }
                                    else
                                    {
                                        mp=null;
                                        outbuf[NumOfConnection].write(Alldata);
                                        readflag[NumOfConnection]=true;
                                    }
                                    
                                 }
                              }
                               
                            }
                        }
                     }
                     catch(OutOfMemoryError e)
                     {
                       System.out.println("OutOfMemoryError");
                        try
                        {
                            for(int x=0;x<in[NumOfConnection].available();x++)
                            {
                                    byte[] a=new byte[1]; 
                                    count =in[NumOfConnection].read(a);
                            }
                           outbuf[NumOfConnection].reset();
                           readflag[NumOfConnection]=false;
                           mp=null;
                          }
                          catch(IOException ioe)
                          {
                              mp=null;
                              ioe.printStackTrace();
                          }
                       }
                       catch(IOException e)
                       {
                           mp=null;
  //                         e.printStackTrace();
                       }                       
                        
       }
        return  mp;
    }
    
   public void  closeConnection(int NumOfConnection)
   {
           try
           {
               out[NumOfConnection].close();
               client[NumOfConnection].close();
               in[NumOfConnection].close();
               out[NumOfConnection]=null;
               in[NumOfConnection]=null;
               client[NumOfConnection]=null;
               remoteInetAddress[NumOfConnection]=null;
               enable[NumOfConnection]=false;
               int outx=0;
               for(int x=NumOfConnection;x<Numberofconnection;x++)
               {
                   out[x]=out[x+1];
                   client[x]=client[x+1];
                   in[x]=in[x+1];
                   enable[x]=enable[x+1];
                   remoteInetAddress[x]=remoteInetAddress[x+1];
                   outx=x+1;
               }
               Numberofconnection--;
               out[outx]=null;
               in[outx]=null;
               client[outx]=null;
               remoteInetAddress[outx]=null;
               enable[outx]=false;
               
           }
           catch(IOException e)
           {}
           catch(Exception e)
           {}
     }
     
     public void  close(){
           try{
                for(int x=0;x<=Numberofconnection;x++)
                {
                   //client[x].close;
                 //  out[x].close;
                }
                Numberofconnection--;
           }
           catch(Exception e)
           {}
           finally
           {
               try
               {
                    server.close();
               }
               catch(IOException e)
               {}
               catch(Exception e)
               {}
               
           }
     }
 }

