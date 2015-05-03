

package mmspcclient;

import java.lang.String;
import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.RandomAccessFile;
import java.io.IOException;




public class WriteFile {
   //private FileOutputStream fos;
     private RandomAccessFile fos;
    public WriteFile(String Filename)
    {
	boolean existfile=false;
        try 
          {
              fos =new RandomAccessFile(Filename,"rw");
          }
         catch(IOException e)
         {}
    }
     
    public void Write (byte[] data)
    {
      
        try 
          {
            fos.seek(fos.length());
              fos.write(data);
          }
         catch(IOException e)
         {}
         
     
    }
    public void Edit(byte[] data,int reccount,int sizeofdata)
    {
         byte[] mydata=new byte[sizeofdata];  
         ByteArrayOutputStream bos=null;
         try 
          {
             int varNumOfRecords=(int) fos.length()/sizeofdata;
              if (varNumOfRecords>=reccount)
            {
                 fos.seek((reccount-1)*sizeofdata);
                 fos.write(data,0,sizeofdata);
            }
            else{data=null;}
          }
         catch(IOException e) 
         {}
   }
 
    public void Delete(int reccount,int sizeofdata)
    {
        // byte[] data=new byte[sizeofdata];  
         ByteArrayOutputStream bos=new ByteArrayOutputStream();
         try 
          {
              int varNumOfRecords=(int) fos.length()/sizeofdata;
              if (varNumOfRecords>=reccount)
            {
                if((reccount-1)>0)
                {
                    byte[] data=new byte[sizeofdata*(reccount-1)];  
                    fos.read(data,0,sizeofdata*(reccount-1));
                    bos.write(data);
                }
                 if(reccount+1<=varNumOfRecords)
                 {
                   byte[] data=new byte[(int)fos.length()-(sizeofdata*reccount)];  
                   fos.seek(reccount*sizeofdata);
                   fos.read(data);
                   bos.write(data);
                 }
                if(bos.size()>0)
                {
                    fos.setLength(0);
                    fos.write(bos.toByteArray(),0,bos.size());
                }
                else
                {
                   fos.setLength(0);
                }
          }
         }  catch(IOException e) 
         {}
   }
 
    public void Close(){
      try 
        {
           fos.close();
         }
        catch (IOException e)
        {}
        catch(Exception e)
        {}
    }
}
