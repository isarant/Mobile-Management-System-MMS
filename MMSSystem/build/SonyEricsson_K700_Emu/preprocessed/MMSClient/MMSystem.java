
package MMSClient;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class MMSystem extends MIDlet {
    
    private Form myform;
    private Display d ;
    private static  ConnectedServer[]  myConnectedServer;
    private static Global_Parameters gp;
    private static SelectServer ss;
    
    public void startApp() {
        d= Display.getDisplay(this);
        gp=new Global_Parameters();
        gp.Set_Display(d);
        Passwd passwd=new Passwd(d,this);
        //GotoSelectserver();
    }
    
    public void GotoSelectserver() {
        ss=new SelectServer(d,this);
        myConnectedServer=ss.Get_ConnectedServer();
    }
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
        
    }
    void exitRequested() {
        destroyApp(false);
        notifyDestroyed();
    }
}
