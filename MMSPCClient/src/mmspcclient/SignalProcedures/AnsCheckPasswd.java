

package MMSPCClient.SignalProcedures;

import MMSPCClient.SelectServer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import MMSPCClient.ConnectedServer;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;

public class AnsCheckPasswd extends ParentSignalProcess  {
    
    private  ConnectedServer[]  myConnectedServer;
    
    public AnsCheckPasswd() {
        ByteArrayOutputStream bous=new ByteArrayOutputStream();
        try {
            bous.write(mp.Get_Data1());
        }
        catch(IOException e)
        {}
        catch (Exception e)
        {}
        SelectServer ss=new SelectServer();
        ConnectedServer[]  myConnectedServer=ss.Get_ConnectedServer();
        ConnectedServer cs=myConnectedServer[Connection_Number];
        if(bous.toString().compareTo("ok")==0) {
            cs.AlertConnectedServer();
            
        }
        else {
            cs.CloseConnectedServer(1);
        }
        
    }
    
}
