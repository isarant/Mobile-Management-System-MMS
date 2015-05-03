
package MMSPCClient.SignalProcedures;


import MMSPCClient.SocConnect;
import MMSPCClient.MP;
import javax.microedition.lcdui.Display;

public class ParentSignalProcess {
    
    /** Creates a new instance of ParentSignalProcess */
    protected  static Display display;
    protected  static SocConnect sc;
    protected  static int Connection_Number;
    protected  static MP mp;
    
    public ParentSignalProcess()
    {}
    public ParentSignalProcess(Display display,SocConnect sc,int Connection_Number,MP mp) {
        this.sc= sc;
        this.Connection_Number=Connection_Number;
        this.display=display;
        this.mp=mp;
    }
    
}
