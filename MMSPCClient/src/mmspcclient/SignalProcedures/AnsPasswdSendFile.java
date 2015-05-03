

package MMSPCClient.SignalProcedures;


import MMSPCClient.SelectServer;
import MMSPCClient.WriteFile;
import MMSPCClient.SignalsFile;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import MMSPCClient.ConnectedServer;

public class AnsPasswdSendFile extends ParentSignalProcess {
    
    private DataInputStream dis;
    public AnsPasswdSendFile() {
        ByteArrayOutputStream bous=new ByteArrayOutputStream();
        SelectServer ss=new SelectServer();
        ConnectedServer[]  myConnectedServer=ss.Get_ConnectedServer();
        ConnectedServer cs=myConnectedServer[Connection_Number];
        try {
            bous.write(mp.Get_Data1());
        }
        catch(IOException e)
        {}
        
        if(bous.toString().compareTo("ok")==0) {
            SignalsFile sf=new SignalsFile();
            boolean mybool=true;
            try {
                
                InputStream is=new ByteArrayInputStream(mp.Get_Data2());
                dis=new DataInputStream(is);
                mybool=true;
            }catch(Exception e)
            {mybool=false;}
            if(mybool) {
                WriteFile wf=new WriteFile(mp.Get_Sender()+"_"+mp.Get_Scenario()+"externalsignal.mms");
                int numrec=0;
                try {
                    numrec=dis.available()/sf.Get_SignalsFile_Size();
                }
                catch(IOException e)
                {}
                byte[] mydata=new byte[sf.Get_SignalsFile_Size()];
                for(int x=1;x<=numrec;x++) {
                    try {
                        dis.read(mydata,0,sf.Get_SignalsFile_Size());
                        sf.Set_SignalsFile_Bytes(mydata);
                    }
                    catch(IOException e)
                    {}
                    wf.Write(sf.Get_SignalsFile_Byte());
                }
                wf.Close();
                cs.AlertConnectedServer();
            }
            else {
                cs.CloseConnectedServer(1);
            }
        }
        else {
            cs.CloseConnectedServer(1);
        }
        
    }
}
