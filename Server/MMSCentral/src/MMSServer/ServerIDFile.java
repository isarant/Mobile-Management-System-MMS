
package MMSServer;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.InputStream;
import java.lang.String;

 public class ServerIDFile {
    
   private byte ServerID;
   private ByteArrayOutputStream BAOSServerName,BAOSImagePath,BAOSSoundPath,BAOSVideoPath;
   public ServerIDFile() 
   {
      BAOSServerName=new ByteArrayOutputStream();
      BAOSImagePath=new ByteArrayOutputStream();
      BAOSSoundPath=new ByteArrayOutputStream();
      BAOSVideoPath=new ByteArrayOutputStream();
   }
    
    public void Set_ServerID(byte serverid){
        ServerID=serverid;
    }
    public void Set_ServerName(String servername){
     BAOSServerName.reset();
     try{
       int mylength=servername.length();
       if (mylength<=200){
           for(int x=1;x<=200-mylength;x++)
           {    servername=servername + " ";}
           BAOSServerName.write(servername.getBytes()); 
       }
       else
       {
           BAOSServerName.write(servername.substring(0,200).getBytes());
       }
    }
     catch(IOException e){}   
 }
    
  public void Set_ImageApp(String externalprocesspath){
     BAOSImagePath.reset();
     try{
       int mylength=externalprocesspath.length();
       if (mylength<=500){
           for(int x=1;x<=500-mylength;x++)
           {    externalprocesspath=externalprocesspath + " ";}
           BAOSImagePath.write(externalprocesspath.getBytes()); 
       }
       else
       {
           BAOSImagePath.write(externalprocesspath.substring(0,500).getBytes());
       }   
            
     }
     catch(IOException e){}   
 }
  
   public void Set_SoundApp(String externalprocesspath){
     BAOSSoundPath.reset();
     try{
       int mylength=externalprocesspath.length();
       if (mylength<=500){
           for(int x=1;x<=500-mylength;x++)
           {    externalprocesspath=externalprocesspath + " ";}
           BAOSSoundPath.write(externalprocesspath.getBytes()); 
       }
       else
       {
           BAOSSoundPath.write(externalprocesspath.substring(0,500).getBytes());
       }   
            
     }
     catch(IOException e){}   
 }
   
  public void Set_VideoApp(String externalprocesspath){
     BAOSVideoPath.reset();
     try{
       int mylength=externalprocesspath.length();
       if (mylength<=500){
           for(int x=1;x<=500-mylength;x++)
           {    externalprocesspath=externalprocesspath + " ";}
           BAOSVideoPath.write(externalprocesspath.getBytes()); 
       }
       else
       {
           BAOSVideoPath.write(externalprocesspath.substring(0,500).getBytes());
       }   
            
     }
     catch(IOException e){}   
 }
     
  public void Set_ServerIDFile_Bytes(byte[] data)
    {
        try{
            InputStream is=new ByteArrayInputStream(data);
            DataInputStream dis=new DataInputStream(is);
            Set_ServerID(dis.readByte());
            BAOSServerName.reset();
            for(int x=0; x<200; x++){
                 BAOSServerName.write(dis.readByte());
            }
            BAOSImagePath.reset();
            for(int x=0; x<500; x++){
                 BAOSImagePath.write(dis.readByte());
            }
             BAOSSoundPath.reset();
            for(int x=0; x<500; x++){
                 BAOSSoundPath.write(dis.readByte());
            }
              BAOSVideoPath.reset();
            for(int x=0; x<500; x++){
                 BAOSVideoPath.write(dis.readByte());
            }
        }catch(IOException e)
        {}
    }      
   public byte Get_ServerID(){
        return ServerID;
    } 
    public String Get_ServerName(){
     return BAOSServerName.toString();
  }
   public String Get_ImagePath(){
     return BAOSImagePath.toString();
  }
   
   public String Get_SoundPath(){
     return BAOSSoundPath.toString();
  }
   
   public String Get_VideoPath(){
     return BAOSVideoPath.toString();
  }
   
  private byte[]  Get_ServerName_byte(){
   byte[] zero=null;
        try{
            return BAOSServerName.toByteArray();
        }catch(NullPointerException e)
        { return zero;}
 }
  
  private byte[]  Get_ImageApp_byte(){
   byte[] zero=null;
        try{
            return BAOSImagePath.toByteArray();
        }catch(NullPointerException e)
        { return zero;}
 }
 private byte[]  Get_SoundApp_byte(){
   byte[] zero=null;
        try{
            return BAOSSoundPath.toByteArray();
        }catch(NullPointerException e)
        { return zero;}
 } 
 private byte[]  Get_VideoApp_byte(){
   byte[] zero=null;
        try{
            return BAOSVideoPath.toByteArray();
        }catch(NullPointerException e)
        { return zero;}
 }
 
 public String Get_ServerName_No_Space(){
  int a=BAOSServerName.toString().length();
        for(int x=0;x<BAOSServerName.toString().length();x++)
        {
            if(BAOSServerName.toString().charAt(x)==' ')
            {a=x;break;}
        }
        return BAOSServerName.toString().substring(0,a);    
    } 
 
  public String Get_ImageApp_No_Space(){
  int a=BAOSImagePath.toString().length();
        for(int x=0;x<BAOSImagePath.toString().length();x++)
        {
            if(BAOSImagePath.toString().charAt(x)==' ')
            {a=x;break;}
        }
        return BAOSImagePath.toString().substring(0,a);    
    } 
 public String Get_SoundApp_No_Space(){
  int a=BAOSSoundPath.toString().length();
        for(int x=0;x<BAOSSoundPath.toString().length();x++)
        {
            if(BAOSSoundPath.toString().charAt(x)==' ')
            {a=x;break;}
        }
        return BAOSSoundPath.toString().substring(0,a);    
    } 
  public String Get_VideoApp_No_Space(){
  int a=BAOSVideoPath.toString().length();
        for(int x=0;x<BAOSVideoPath.toString().length();x++)
        {
            if(BAOSVideoPath.toString().charAt(x)==' ')
            {a=x;break;}
        }
        return BAOSVideoPath.toString().substring(0,a);    
    } 
  
   public byte[] Get_ServerIDFile_Bytes(){
         byte[] data=null;
        ByteArrayOutputStream MMPOutData=new ByteArrayOutputStream();
        try{
            MMPOutData.write(Get_ServerID());
            if(BAOSServerName.size()>0)
           {MMPOutData.write(Get_ServerName_byte());}
            if(BAOSImagePath.size()>0)
           {MMPOutData.write(Get_ImageApp_byte());}
           if(BAOSSoundPath.size()>0)
           {MMPOutData.write(Get_SoundApp_byte());}
             if(BAOSVideoPath.size()>0)
           {MMPOutData.write(Get_VideoApp_byte());}
           
            data=MMPOutData.toByteArray();
        }catch(IOException e){}
        return data;
    }
    
    public int Get_ServerIDFile_Size(){
       int size=1701;
       return size;
    }
}
