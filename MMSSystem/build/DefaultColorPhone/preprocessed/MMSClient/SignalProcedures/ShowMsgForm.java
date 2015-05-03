package MMSClient.SignalProcedures;

import javax.microedition.lcdui.*;
import MMSClient.ShowConnectedServers;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import MMSClient.Parameters;
import MMSClient.Global_Parameters;

public class ShowMsgForm extends Form implements CommandListener, ItemStateListener {
    
    private final static Command CMD_EXIT = new Command("Exit", Command.EXIT, 1);
    private Form prevform;
    private Display display;
    private static Global_Parameters gp;
    
    public ShowMsgForm(byte[] sendername,byte[] msg,Display display,Form prevform) {
        super("Recieve a Message");
        this.prevform=prevform;
        this.display=display;
        ByteArrayOutputStream boutname=new ByteArrayOutputStream();
        ByteArrayOutputStream boutmsg=new ByteArrayOutputStream();
        try {
            boutname.write(sendername);
            boutmsg.write(msg);
        }
        catch(IOException ioe)
        {}
        TextField tfname=new TextField("SenderName: ",boutname.toString(),200,TextField.UNEDITABLE);
        TextField tfmsg=new TextField("Message: ",boutmsg.toString(),200,TextField.UNEDITABLE);
        append(tfname);
        append(tfmsg);
        addCommand(CMD_EXIT);
        try {
            setCommandListener(this);
            setItemStateListener(this);
        } catch(Exception e) {
            e.printStackTrace();
        }
        display.setCurrent(this);
    }
    
    public void commandAction(Command command, Displayable displayable) {
        if(command==CMD_EXIT) {
            if(prevform.getClass().getName().compareTo("MMSClient.ShowConnectedServers")==0) {
                new ShowConnectedServers().init(true);
            }
            else {
                display.setCurrent(prevform);
            }
        }
    }
    
    /**
     * Called when internal state of any item changed
     */
    public void itemStateChanged(Item item) {
    }
    
}
