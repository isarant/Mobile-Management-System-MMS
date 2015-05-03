

package mmspcclient;

import java.net.Socket;
import java.net.SocketException;
import java.net.SocketAddress;
import java.io.OutputStream;
import java.io.IOException;
import java.lang.String;
import java.lang.Runtime;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

public class SocConnect implements Runnable {
    private Socket socketConnection=null;
    protected OutputStream out;
    private InputStream in=null;
    private String myServer;
    private String Port;
    private ByteArrayOutputStream outbuf;
    private boolean readflag;
    protected boolean enambled=false;
    protected boolean enambledfirst=false;
    protected boolean promblem=false;
    protected boolean ReadData=false;
    protected  byte[] readdata=null;
    protected static final int defaultbufsize=100000;
    private  String myip="";
    private  String remip="";
    private ClientCrypt clientcrypt;
    
    public SocConnect(String Server, String Port) {
        myServer=Server;
        this.Port=Port;
        new Thread(this).start();
    }
    
    public int IntervalWaitReadTime() {
        return ((defaultbufsize/9600)*1000);
    }
    
    public void run(){
        try {
            String myconnection="socket://" + myServer + ":" + Port;
            socketConnection =(Socket) (Connector.open(myconnection));
            socketConnection.setSocketOption(SocketConnection.DELAY, 15);
            //socketConnection.setSocketOption(SocketConnection.RCVBUF,defaultbufsize);
            //socketConnection.setSocketOption(SocketConnection.SNDBUF,defaultbufsize);
            myip=socketConnection.getLocalAddress();
            remip=socketConnection.getAddress();
            out=socketConnection.openOutputStream();
            in=socketConnection.openInputStream();
            outbuf= new ByteArrayOutputStream();
            readflag=false;
            enambled=true;
            enambledfirst=true;
        }catch(IOException e) {
            promblem=true;
            enambled=false;
            enambledfirst=true;
            close();
        }
        
    }
    
    
    
    public synchronized void Send(byte[] data) {
        
        SendSoc ss=new SendSoc(data,this);
    }
    
    
    
    public synchronized MP Read() {
        MP mp=null;
        MP mphelp=null;
        byte[] data=null;
        byte[] Alldata=null;
        int count=0;
        int numdata=0;
        int Alldatcount=0;
        try{
            numdata=in.available();
            if (numdata>0 ) {
                data=new byte[numdata];
                count =in.read(data);
                readflag=true;
                outbuf.write(data);
                System.out.println("read data size:" + data.length);
                System.out.println("read data buf size:" + outbuf.size());
            }
            else {
                if(readflag) {
                    readflag=false;
                    Alldata=outbuf.toByteArray();
                    Alldatcount=Alldata.length;
                    outbuf.reset();
                    if(Alldatcount>0) {
                        mp=new MP();
                        mp.Set_MMP_Bytes(Alldata);
                        if(mp.IsItOk() && Alldata.length>=mp.Get_MMP_Size()) {
                            clientcrypt=new ClientCrypt(mp.Get_Sender(),mp.Get_Scenario());
                            if(mp.Get_Data1()!=null) {
                                mp.Set_Data1(clientcrypt.DeCryptData(mp.Get_Data1()));
                            }
                            if(mp.Get_Data2()!=null) {
                                mp.Set_Data2(clientcrypt.DeCryptData(mp.Get_Data2()));
                            }
                            System.out.println("mp size:" +mp.Get_MMP_Size());
                            if(mp.Get_MMP_Size()<Alldata.length) {
                                byte[] hlpdata=new byte[mp.Get_MMP_Size()];
                                ByteArrayInputStream hlpstream=new ByteArrayInputStream(Alldata);
                                hlpstream.read(hlpdata);
                                byte[] hlpdata2=new byte[hlpstream.available()];
                                hlpstream.read(hlpdata2);
                                outbuf.write(hlpdata2);
                                readflag=true;
                            }
                        }
                        else {
                            mp=null;
                            outbuf.write(Alldata);
                            readflag=true;
                        }
                    }
                }
            }
        }
        catch(OutOfMemoryError e) {
            System.out.println("out of memory read");
            try {
                for(int x=0;x<in.available();x++) {
                    byte[] a=new byte[1];
                    count =in.read(a);
                }
                outbuf.reset();
                readflag=false;
                mp=null;
            }
            catch(IOException ioe) {
                mp=null;
                ioe.printStackTrace();
            }
        }
        catch(IOException e) {
            mp=null;
            e.printStackTrace();
        }
        return mp;
    }
    
    public String Get_Local_IP() {
        
        //return myip;
        String ipstr="";
        try {
            ipstr=socketConnection.getLocalAddress();
        }
        catch(Exception e) {
            ipstr=myip;
        }
        return ipstr;
    }
    
    public String Get_Remote_IP() {
        
        return remip;
    }
    
    public void close() {
        try {
            socketConnection.close();
            enambled=false;
            out.close();
            in.close();
        }
        catch(Exception e)
        { }
    }
}
