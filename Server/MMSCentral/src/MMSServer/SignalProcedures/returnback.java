
package MMSServer.SignalProcedures;

import MMSServer.Signals;

public class returnback extends ParentSignalProcess {
    public returnback()
    {
        super();
        Signals singnals=new Signals(mp.Get_Receiver());
        byte[] data=null;
        byte mytype=mp.Get_Signal_Type();
        mytype++;
        sc.Send(singnals.GeneralSignal(mp.Get_Sender(),mp.Get_Scenario(),mytype,mp.Get_Signal_Command(),mp.Get_Priority(),data,data),Connection_Number);
    }
    
}
