
package MMSServer;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Date;

public  class SessionFile {
    private byte ServerID=0;
    private byte enable=0;
    private int SocConnectID=0;
    private byte UserID=0;
    private int Scenario=0;
    private long Date_Time;
    private long last_Time=0;
    private int keynum;
    private ByteArrayOutputStream BAOSIP;  
    public SessionFile()
    {
       
    }
    
    public SessionFile(byte ServerID,byte UserID,int SocConnectID,int Scenario,long Date_Time,String IP,int keynum)
    {
     BAOSIP=new ByteArrayOutputStream();
     try
     {
         BAOSIP.write(IP.getBytes()); 
     }
     catch(IOException e)
     {}
     this.ServerID=ServerID;
     this.UserID=UserID;
     this.SocConnectID=SocConnectID;
     this.Scenario=Scenario;
     this.Date_Time=Date_Time;
     this.last_Time=Date_Time;
     this.keynum=keynum;
     enable=0;
    }
    
    public SessionFile(byte ServerID,byte UserID,int SocConnectID,int Scenario,String IP,int keynum)
    {
     BAOSIP=new ByteArrayOutputStream();
    try
     {
        BAOSIP.write(IP.getBytes()); 
     }
     catch(IOException e)
     {}
     this.ServerID=ServerID;
     this.UserID=UserID;
     this.SocConnectID=SocConnectID;
     this.Scenario=Scenario;
     Date dt=new Date();
     this.Date_Time=dt.getTime();
     this.last_Time=dt.getTime();
     this.keynum=keynum;
     enable=0;
    }
    
    public void Set_IP(String IP)
    {
        BAOSIP=new ByteArrayOutputStream();
       try
       {
          BAOSIP.write(IP.getBytes()); 
        }
        catch(IOException e)
       {}
    }
    public void Set_Date_Time(long MP_Date_Time){
        Date_Time=MP_Date_Time;
    }
     
     public void Set_Last_Time(long last_Time){
       this.last_Time=last_Time;
    } 
     
     public void Set_ServerID(byte ServerID){
         this.ServerID=ServerID;
    }
      public void Set_Enable(byte enable){
         this.enable=enable;
    } 
 
     public void Set_UserID(byte UserID){
        this.UserID=UserID;
    }
    
   public void Set_SocConnectID(int SocConnectID){
         this.SocConnectID=SocConnectID;
    }

     public void Set_Scenario(int Scenario){
         this.Scenario=Scenario;
     }   
     public void Set_Keynum(int keynum){
         this.keynum=keynum;
     }
     public void Set_SessionrFile_Byte(byte[] data){
     try{
        if(data!=null)
        {
            InputStream is=new ByteArrayInputStream(data);
            DataInputStream dis=new DataInputStream(is);
            Set_ServerID(dis.readByte());
            Set_UserID(dis.readByte());
            Set_SocConnectID(dis.readInt());
            Set_Scenario(dis.readInt());
            Set_Enable(dis.readByte());
            Set_Keynum(dis.readInt());
            Set_Date_Time(dis.readLong());
            Set_Last_Time(dis.readLong());
            BAOSIP.reset();
            byte[] dataip=new byte[15];
            dis.read(dataip);
            BAOSIP.write(dataip);
        }
     }
     catch(IOException e)
     {e.printStackTrace();}
 }
 
     
     public int Get_Size(){
        int size=0;
        byte[] data=null;
        try{
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            baos.write(Get_ServerID());
            baos.write(Get_UserID());
            baos.write(Get_SocConnectID_byte());
            baos.write(Get_Scenario_byte());
            baos.write(Get_Enable());
            baos.write(Get_Keynum_byte());
            baos.write(Get_Date_Time_byte());
            baos.write(Get_Last_Time_byte());
            baos.write(BAOSIP.toByteArray());
            size=baos.size();
        }
        catch(IOException e){}
        return size;
    }
     
    public byte[] Get_SessionFile_Byte(){
     byte[] data=null;
     try{
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        baos.write(Get_ServerID());
        baos.write(Get_UserID());
        baos.write(Get_SocConnectID_byte());
        baos.write(Get_Scenario_byte());
        baos.write(Get_Enable());
        baos.write(Get_Keynum_byte());
        baos.write(Get_Date_Time_byte());
        baos.write(Get_Last_Time_byte());
        baos.write(BAOSIP.toByteArray());
        data=baos.toByteArray();
     }
     catch(IOException e)
     {}
     return data;
    } 
     
    public String Get_IP()
    {
        return BAOSIP.toString();
    }
    public byte Get_ServerID(){
         return ServerID;
    }
    public byte Get_Enable(){
         return enable;
    }
     public int Get_Scenario(){
         return Scenario;
    }
      public int Get_Keynum(){
         return keynum;
    }
     public byte Get_UserID(){
         return UserID;
    }
    
    public int Get_SocConnectID(){
         return SocConnectID;
    }
    
     private byte[] Get_Scenario_byte(){
        byte b[] = new byte[4];
        int i, shift;
        for(i = 0, shift = 56; i < 4; i++, shift -= 8)
        b[i] = (byte)(0xFF & (Scenario >> shift));
        return b;
    }
     private byte[] Get_Keynum_byte(){
        byte b[] = new byte[4];
        int i, shift;
        for(i = 0, shift = 56; i < 4; i++, shift -= 8)
        b[i] = (byte)(0xFF & (keynum >> shift));
        return b;
    }
     private byte[] Get_SocConnectID_byte(){
        byte b[] = new byte[4];
        int i, shift;
        for(i = 0, shift = 56; i < 4; i++, shift -= 8)
        b[i] = (byte)(0xFF & (SocConnectID >> shift));
        return b;
    } 
    
    public long Get_Date_Time(){
        return Date_Time;
    }
    
     private byte[] Get_Date_Time_byte(){
        byte b[] = new byte[8];
        int i, shift;
        for(i = 0, shift = 56; i < 8; i++, shift -= 8)
        b[i] = (byte)(0xFF & (Date_Time >> shift));
        return b;
     }
     public long Get_Last_Time(){
        return last_Time;
    }
    
     private byte[] Get_Last_Time_byte(){
        byte b[] = new byte[8];
        int i, shift;
        for(i = 0, shift = 56; i < 8; i++, shift -= 8)
        b[i] = (byte)(0xFF & (last_Time >> shift));
        return b;
     }
}
