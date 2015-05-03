
package mmspcclient;

import java.lang.String;

import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.DataInputStream;


public class ReadFile {
   
    private RandomAccessFile fis;
    private int varNumOfRecords=0;;
    private int varsizeofdata;
         
   
    
    public ReadFile(String Filename,int sizeofdata)
    {
	varsizeofdata=sizeofdata;
        try 
          {
            fis =new RandomAccessFile(Filename,"r");
            varNumOfRecords=(int) fis.length()/varsizeofdata;
          }
          catch(IOException e)
         {varNumOfRecords=0;}
         catch(Exception e)
         {varNumOfRecords=0;}
     }
    
    public ReadFile(String Filename)
    {
        try 
          {
            fis =new RandomAccessFile(Filename,"r");
          }
          catch(IOException e)
         {varNumOfRecords=0;}
         catch(Exception e)
         {varNumOfRecords=0;}
     }
    public byte[] Read (int reccount,int sizeofdata)
    {
       byte[] data=new byte[sizeofdata];  
       if(varNumOfRecords>0)
       {
            try 
            {
              varNumOfRecords=(int) fis.length()/sizeofdata;
              if (varNumOfRecords>=reccount)
                {
                     fis.read(data,sizeofdata*(reccount-1),sizeofdata);
                
                }
                else{data=null;}
            }
            catch(IOException e) 
            {}
            catch(Exception e)
            {}
       }
      return data; 
    }
    
    public byte[] Read (int reccount)
    {
        byte[] data=new byte[varsizeofdata];  
         if (varNumOfRecords>=reccount)
         {
            try 
            {
                if (fis.length()/varsizeofdata>=reccount)
                {
                    fis.seek((reccount-1)*varsizeofdata);
                    fis.read(data);
                }
                else{data=null;}
            }
            catch(IOException e) 
            {}
            catch(Exception e)
            {}
         }
      return data; 
    }
    
   public byte[] Read()
    {
        byte[] data=null; 
        try 
        {
           data=new byte[(int)fis.length()];          
           if (fis.length()>0)
           {
              fis.read(data);
          }
          else{data=null;}
       }
       catch(IOException e) 
       {}
       return data; 
    }  
   
   public int NumOfRecords(){
        return varNumOfRecords;
   }
   
   public void Close(){
      try 
        {
           fis.close();
         }
    catch (IOException e)
    { }
    catch(Exception e)
    {}
   }

}
