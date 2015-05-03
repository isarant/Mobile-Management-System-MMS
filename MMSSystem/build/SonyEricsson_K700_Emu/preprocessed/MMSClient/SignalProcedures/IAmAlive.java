

package MMSClient.SignalProcedures;

import MMSClient.Signals;
import MMSClient.ClientID;

public class IAmAlive extends ParentSignalProcess {
    public IAmAlive() {
        
        super();
        ClientID cl=new ClientID();
        Signals singnals=new Signals(cl.GetClientID());
        sc.Send(singnals.IAmAlive(mp.Get_Sender(),mp.Get_Scenario()));
    }
    
}
