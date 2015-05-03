
package MMSServer.SignalProcedures;
import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import MMSServer.ReadFile;
import MMSServer.Parameters;
import MMSServer.Parameters_hlp;
import MMSServer.SocConnect;
import MMSServer.Alertform;

public class FileManager {

   private byte[] par1=null;
   private byte[] par2=null;
   private static Parameters Parameter;
   private static Parameters_hlp Parameter2;
   public FileManager() 
   {
     open();      
  }
  private void open()
  {
        Frame getimagepath=new Frame();
        FileDialog fd=new FileDialog(getimagepath);
       try
       {
           Parameter2=new Parameters_hlp(); 
           if(Parameter2.Get_Parameter(0).Ge_Parameter1_String().compareTo("SendImage")==0)
            {
                fd.setDirectory("./Pictures");
            }
            if(Parameter2.Get_Parameter(0).Ge_Parameter1_String().compareTo("SendMedia")==0)
            {
                fd.setDirectory("./Media");
            }
            if(Parameter2.Get_Parameter(0).Ge_Parameter1_String().compareTo("SendFile")==0)
            {
                fd.setDirectory("./TransferFiles");
            }
       }
       catch(Exception e)
       {}
        
        FilenameFilter filter=null;
        fd.show();
        if(fd.getDirectory().compareTo("")!=0 && fd.getFile().compareTo("")!=0)
        {
           SocConnect sc=new SocConnect();
           String s=fd.getDirectory()+fd.getFile();
           File file =new File(s) ;
           if(file.length()<=sc.defaultbufsize)
           {
                par1=fd.getFile().getBytes();
                ReadFile rf=null;
                rf=new ReadFile(s);
                par2=rf.Read();
                rf.Close();
           }
           else
           {
               String str="You can not sent file biger than "+ sc.defaultbufsize + " byte";
               Alertform alertform=new Alertform(str);
               alertform.show();
               open();
           }
        }
         Parameter=new Parameters(1);
         Parameter.Set_Parameter(0,par1,par2);
  }
  
   public FileManager(String Filedir,String Filefilter) 
    {
        Frame getimagepath=new Frame();
        FileDialog fd=new FileDialog(getimagepath);
        FilenameFilter filter=fd.getFilenameFilter();
	File dir=new File(Filedir);
        filter.accept(dir, Filefilter);
        fd.setFilenameFilter(filter);
        fd.show();
        if(fd.getDirectory().compareTo("")!=0 && fd.getFile().compareTo("")!=0)
        {
            String s=fd.getDirectory()+fd.getFile();
            par1=fd.getFile().getBytes();
            ReadFile rf=null;
            rf=new ReadFile(s);
            par2=rf.Read();
            rf.Close();
        }
         Parameter=new Parameters(1);
         Parameter.Set_Parameter(0,par1,par2);
  }
  public byte[] Get_Parameter1()
  {
      return par1;
  }
  public String Get_Parameter1_String()
  {
      ByteArrayOutputStream baot=new ByteArrayOutputStream();
      try
      {
        baot.write(par1);
      }
      catch(IOException e)
      {}
      return baot.toString();
  }
  public byte[] Get_Parameter2()
  {
      return par2;
  }
  public String Get_Parameter2_String()
  {
      ByteArrayOutputStream baot=new ByteArrayOutputStream();
      try
      {
        baot.write(par2);
      }
      catch(IOException e)
      {}
      return baot.toString();
  }
}
