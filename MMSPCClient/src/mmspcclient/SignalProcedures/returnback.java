
package MMSPCClient.SignalProcedures;

import MMSPCClient.Signals;
import MMSPCClient.ClientID;

public class returnback extends ParentSignalProcess {
    
    public returnback() {
        super();
        ClientID cl=new ClientID();
        Signals singnals=new Signals(cl.GetClientID());
        byte[] data=null;
        byte mytype=mp.Get_Signal_Type();
        mytype++;
        sc.Send(singnals.GeneralSignal(mp.Get_Sender(),mp.Get_Scenario(),mytype,mp.Get_Signal_Command(),mp.Get_Priority(),data,data));
    }
    
}
