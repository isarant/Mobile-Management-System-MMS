
package MMSServer.SignalProcedures;


import MMSServer.MP;
import MMSServer.SocConnect;

public class ParentSignalProcess  {
    
    /** Creates a new instance of ParentSignalProcess */
    protected static SocConnect sc;
    protected static int Connection_Number;
    protected static MP mp;
    public ParentSignalProcess()
    {}
    public ParentSignalProcess(SocConnect sc, int Connection_Number,MP mp)
    {
        this.sc= sc;
        this.Connection_Number=Connection_Number;
        this.mp=mp;
    }
    
}
