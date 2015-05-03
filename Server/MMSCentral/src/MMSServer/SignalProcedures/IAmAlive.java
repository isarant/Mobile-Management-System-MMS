
package MMSServer.SignalProcedures;

import MMSServer.Signals;


public class IAmAlive extends ParentSignalProcess {
    public IAmAlive() {
        super();
        init();
    }
    private synchronized void init()
    {
        Signals singnals=new Signals(mp.Get_Receiver());
        sc.Send(singnals.IAmAlive(mp.Get_Sender(),mp.Get_Scenario()),Connection_Number);
    }
    
}
