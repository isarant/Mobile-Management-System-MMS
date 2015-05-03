package MMSServer;


import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.InputStream;
import java.lang.String;

 public class PaswdFile {
 private byte ClientID=0;
 private int Scenario=0;
 private int keynum=0;
 private ByteArrayOutputStream BAOSUserName,BAOSPasswd;
 
 public PaswdFile(){
     BAOSUserName= new ByteArrayOutputStream();
     BAOSPasswd= new ByteArrayOutputStream();
  }
 
 public int Get_Size(){
     int size=29;
   /*  byte[] data=null;
     try{
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        baos.write(Get_ClientID());
        baos.write(Get_Scenario_Byte());
        baos.write(Get_UserName_byte());
        baos.write(Get_Passwd_byte());
        size=baos.size();
    }catch(IOException e)
    {size=25;}*/
    return size;
 }
 
 public byte Get_ClientID(){
     return ClientID;
 }
 
 public int Get_Scenario(){
     return Scenario;
 }
 
 public int Get_KeyNum(){
     return keynum;
 }
 
  private byte[] Get_Scenario_Byte(){
        byte b[] = new byte[4];
        int i, shift;
        for(i = 0, shift = 56; i < 4; i++, shift -= 8)
        b[i] = (byte)(0xFF & (Scenario >> shift));
        return b;
  }
 private byte[] Get_KeyNum_Byte(){
        byte b[] = new byte[4];
        int i, shift;
        for(i = 0, shift = 56; i < 4; i++, shift -= 8)
        b[i] = (byte)(0xFF & (keynum >> shift));
        return b;
  }
 public String Get_UserName(){
     return BAOSUserName.toString();
 }
 
  private byte[]  Get_UserName_byte(){
     return BAOSUserName.toByteArray();
 }
  
  public String Get_UserName_No_Space(){
  int a=BAOSUserName.toString().length();
        for(int x=0;x<BAOSUserName.toString().length();x++)
        {
            if(BAOSUserName.toString().charAt(x)==' ')
            {a=x;break;}
        }
        return BAOSUserName.toString().substring(0,a);    
  }
  
  public String Get_Passwd(){
     return BAOSPasswd.toString();
 }
 
 private byte[]  Get_Passwd_byte(){
     return BAOSPasswd.toByteArray();
 }
 
  public String Get_Passwd_No_Space(){
  int a=BAOSPasswd.toString().length();
        for(int x=0;x<BAOSPasswd.toString().length();x++)
        {
            if(BAOSPasswd.toString().charAt(x)==' ')
            {a=x;break;}
        }
        return BAOSPasswd.toString().substring(0,a);    
  }
  
 public byte[] Get_PaswdFile_Byte(){
    byte[] data=null;
     try{
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        baos.write(Get_ClientID());
        baos.write(Get_Scenario_Byte());
        baos.write(Get_UserName_byte());
        baos.write(Get_Passwd_byte());
        baos.write(Get_KeyNum_Byte());
        data=baos.toByteArray();
    }catch(IOException e){}
    return data;
 }
 
 public void Set_ClientID(byte ClientID){
     this.ClientID=ClientID;
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
     }catch(IOException e){}   
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
       
     }catch(IOException e){}
 }
 
 public void Set_PaswdFile_Byte(byte[] data){
     try{
        if(data!=null)
        {
            InputStream is=new ByteArrayInputStream(data);
            DataInputStream dis=new DataInputStream(is);
            Set_ClientID(dis.readByte());
            Set_Scenario(dis.readInt());
            BAOSUserName.reset();
            BAOSPasswd.reset();
            for(int x=0; x<10; x++){
                 BAOSUserName.write(dis.readByte());
            }
            for(int x=0; x<10; x++){
                 BAOSPasswd.write(dis.readByte());
            }
            Set_KeyNum(dis.readInt());
        }
     }catch(IOException e)
     {}
 }
 
}
