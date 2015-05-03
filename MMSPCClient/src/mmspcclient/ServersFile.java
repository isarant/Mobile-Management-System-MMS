package mmspcclient;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.InputStream;
import java.lang.String;

class ServersFile {
    
    private byte ServerID=0;
    private int Scenario=0;
    private ByteArrayOutputStream BAOSUserName,BAOSPasswd;
    private ByteArrayOutputStream BAOSServerName,BAOSServerURL;
    private int URLLength=50;
    private int keynum;
    
    public ServersFile(){
        BAOSUserName= new ByteArrayOutputStream();
        BAOSPasswd= new ByteArrayOutputStream();
        BAOSServerName= new ByteArrayOutputStream();
        BAOSServerURL= new ByteArrayOutputStream();
        
    }
    
    public int Get_Size(){
        int size=0;
        byte[] data=null;
        try{
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            baos.write(Get_ServerID());
            baos.write(Get_Scenario_byte());
            baos.write(Get_ServerName_byte());
            baos.write(Get_ServerURL_byte());
            baos.write(Get_UserName_byte());
            baos.write(Get_Passwd_byte());
            baos.write(Get_KeyNum_byte());
            size=baos.size();
        }
        catch(IOException e){}
        return size;
    }
    
    public byte Get_ServerID(){
        return ServerID;
    }
    
    public int Get_Scenario(){
        return Scenario;
    }
    public int Get_keyNum(){
        return keynum;
    }
    private byte[] Get_Scenario_byte(){
        byte b[] = new byte[4];
        int i, shift;
        for(i = 0, shift = 56; i < 4; i++, shift -= 8)
            b[i] = (byte)(0xFF & (Scenario >> shift));
        return b;
    }
    private byte[] Get_KeyNum_byte(){
        byte b[] = new byte[4];
        int i, shift;
        for(i = 0, shift = 56; i < 4; i++, shift -= 8)
            b[i] = (byte)(0xFF & (keynum >> shift));
        return b;
    }
    public String Get_UserName(){
        String s=BAOSUserName.toString();
        return s;
    }
    
    private byte[]  Get_UserName_byte(){
        
        return BAOSUserName.toByteArray();
    }
    public String Get_UserName_No_Space(){
        int a=BAOSUserName.toString().length();
        for(int x=0;x<BAOSUserName.toString().length();x++) {
            if(BAOSUserName.toString().charAt(x)==' ')
            {a=x;break;}
        }
        return BAOSUserName.toString().substring(0,a);
    }
    
    public String Get_Passwd(){
        String s=BAOSPasswd.toString();
        return s;
    }
    
    private byte[]  Get_Passwd_byte(){
        return BAOSPasswd.toByteArray();
    }
    
    public String Get_Passwd_No_Space(){
        int a=BAOSPasswd.toString().length();
        for(int x=0;x<BAOSPasswd.toString().length();x++) {
            if(BAOSPasswd.toString().charAt(x)==' ')
            {a=x;break;}
        }
        return BAOSPasswd.toString().substring(0,a);
    }
    
    public String Get_ServerName(){
        return BAOSServerName.toString();
    }
    
    private byte[]  Get_ServerName_byte(){
        return BAOSServerName.toByteArray();
    }
    
    public String Get_ServerName_No_Space(){
        int a=BAOSServerName.toString().length();
        for(int x=0;x<BAOSServerName.toString().length();x++) {
            if(BAOSServerName.toString().charAt(x)==' ')
            {a=x;break;}
        }
        return BAOSServerName.toString().substring(0,a);
    }
    
    public String Get_ServerURL(){
        return BAOSServerURL.toString();
    }
    
    private byte[]  Get_ServerURL_byte(){
        return BAOSServerURL.toByteArray();
    }
    public String Get_ServerURL_No_Space(){
        int a=BAOSServerURL.toString().length();
        for(int x=0;x<BAOSServerURL.toString().length();x++) {
            if(BAOSServerURL.toString().charAt(x)==' ')
            {a=x;break;}
        }
        String s= BAOSServerURL.toString().substring(0,a);
        return s;
    }
    
    public byte[] Get_ServerFile_Byte(){
        byte[] data=null;
        try{
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            baos.write(Get_ServerID());
            baos.write(Get_Scenario_byte());
            baos.write(Get_ServerName_byte());
            baos.write(Get_ServerURL_byte());
            baos.write(Get_UserName_byte());
            baos.write(Get_Passwd_byte());
            baos.write(Get_KeyNum_byte());
            data=baos.toByteArray();
        }
        catch(IOException e){}
        return data;
    }
    
    public void Set_ServerID(byte ServerID){
        this.ServerID=ServerID;
    }
    
    public void Set_Scenario(int Scenario){
        this.Scenario=Scenario;
    }
    public void Set_KeyNum(int keynum){
        this.keynum=keynum;
    }
    public void Set_UserName(String UserName){
        BAOSUserName.reset();
        try{
            int mylength=UserName.length();
            if (mylength<=10){
                for(int x=1;x<=10-mylength;x++)
                {    UserName=UserName + " ";}
                BAOSUserName.write(UserName.getBytes());
            }else{
                BAOSUserName.write(UserName.substring(0,10).getBytes());
            }
            
        }
        catch(IOException e)
        {}
    }
    
    public void Set_Passwd(String Passwd){
        try{
            BAOSPasswd.reset();
            int mylength=Passwd.length();
            if (mylength<=10){
                for(int x=1;x<=10-mylength;x++)
                {    Passwd=Passwd + " ";}
                BAOSPasswd.write(Passwd.getBytes());
            }else{
                BAOSPasswd.write(Passwd.substring(1,10).getBytes());
            }
            
        }
        catch(IOException e)
        {}
    }
    
    public void Set_ServerName(String ServerName){
        BAOSServerName.reset();
        try{
            int mylength=ServerName.length();
            if (mylength<=10){
                for(int x=1;x<=10-mylength;x++)
                {    ServerName=ServerName + " ";}
                BAOSServerName.write(ServerName.getBytes());
            }else{BAOSServerName.write(ServerName.substring(0,10).getBytes());}
        }
        catch(IOException e)
        {}
    }
    
    public void Set_ServerURL(String ServerURL){
        BAOSServerURL.reset();
        try{
            int mylength=ServerURL.length();
            if (mylength<=URLLength){
                for(int x=1;x<=URLLength-mylength;x++)
                {    ServerURL=ServerURL + " ";}
                BAOSServerURL.write(ServerURL.getBytes());
            }else{
                BAOSServerURL.write(ServerURL.substring(0,URLLength).getBytes());
            }
        }
        catch(IOException e)
        {}
    }
    
    public void Set_ServerFile_Byte(byte[] data){
        try{
            if(data!=null) {
                InputStream is=new ByteArrayInputStream(data);
                DataInputStream dis=new DataInputStream(is);
                Set_ServerID(dis.readByte());
                Set_Scenario(dis.readInt());
                BAOSUserName.reset();
                BAOSPasswd.reset();
                BAOSServerName.reset();
                BAOSServerURL.reset();
                for(int x=0; x<10; x++){
                    BAOSServerName.write(dis.readByte());
                }
                for(int x=0; x<URLLength; x++){
                    BAOSServerURL.write(dis.readByte());
                }
                for(int x=0; x<10; x++){
                    BAOSUserName.write(dis.readByte());
                }
                for(int x=0; x<10; x++){
                    BAOSPasswd.write(dis.readByte());
                }
                Set_KeyNum(dis.readInt());
            }
        }
        catch(IOException e)
        {}
    }
    
    
}
