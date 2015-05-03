
package MMSClient;


import javax.microedition.lcdui.*;
import java.io.IOException;

//Form for input user the client id
public class ClientID extends Form implements CommandListener, ItemStateListener {
    
    private SelectServer myPrevForm;
    private Display  display;
    private TextField tf;
    private Command cmdSave=new Command("Save", Command.SCREEN, 1);
    private Command cmdCancel=new Command("Cancel",Command.CANCEL, 1);
    
    public ClientID(Display display, SelectServer PrevForm) {
        super("Set Client ID");
        this.display=display;
        myPrevForm=PrevForm;
        SetClientID();
    }
    
    public ClientID() {
        super("Set Client ID");
    }
    
    public byte GetClientID() {
        byte[] myclientid=new byte[1];
        ReadFile rf=new ReadFile("clientid.mms");
        if (rf.NumOfRecords()>0) {
            myclientid=rf.Read();
            rf.Close();
        }
        else {
            myclientid[0]=(byte)0;
        }
        return (byte) myclientid[0];
    }
    
    private void SetClientID() {
        try {
            setCommandListener(this);
            setItemStateListener(this);
            addCommand(cmdSave);
            addCommand(cmdCancel);
            tf=new TextField("Client Id","",3,TextField.NUMERIC);
            append(tf);
            
        }
        catch(Exception e)
        { }
    }
    
    public void commandAction(Command command, Displayable displayable) {
        if(command==cmdSave) {
            byte[] mycid=new byte[1];
            try {
                mycid[0]=(Byte.parseByte(tf.getString()));
            }
            catch(Exception e) {
                Alert errorAlert = new Alert("Alert",
                "Client ID is a number betwwen 1-255",
                null, AlertType.ERROR);
                errorAlert.setTimeout(Alert.FOREVER);
                display.setCurrent(errorAlert);
            }
            if(mycid[0]>0) {
                new DeleteFile("clientid.mms");
                WriteFile wf1=new WriteFile("clientid.mms");
                wf1.Write(mycid);
                wf1.Close();
                myPrevForm.init();
                display.setCurrent(myPrevForm);
            }
        }
        if(command==cmdCancel) {
            Command cmdSetClientID=new Command("Set Client ID", Command.SCREEN, 4);
            myPrevForm.addCommand(cmdSetClientID);
            display.setCurrent(myPrevForm);
        }
    }
    
    /**
     * Called when internal state of any item changed
     */
    public void itemStateChanged(Item item) {
    }
    
}
