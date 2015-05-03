
package MMSPCClient.SignalProcedures;

import MMSPCClient.SelectServer;
import MMSPCClient.ConnectedServer;

public class CheckCloseConnection extends ParentSignalProcess {
    
    public CheckCloseConnection() {
        SelectServer ss=new SelectServer();
        ConnectedServer[]  myConnectedServer=ss.Get_ConnectedServer();
        ConnectedServer cs=myConnectedServer[Connection_Number];
        cs.CloseConnectedServer(2);
    }
    
}
