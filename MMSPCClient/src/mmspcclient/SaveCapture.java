
package MMSPCClient;

import javax.microedition.lcdui.*;

public class SaveCapture extends Form implements CommandListener, ItemStateListener {
    private final static Command CMD_SAVE_CAPTURE_PHOTO= new Command("Save", Command.SCREEN, 1);
    private final static Command CMD_BACK_CAPTURE_PHOTO = new Command("Back", Command.BACK, 1);
    private String prefix;
    private Display display;
    private byte[] data;
    private TextField tf;
    private Form prevform;
    
    public SaveCapture(byte[] data,Display display,Form prevform,String prefix) {
        super("Save Capture");
        this.prevform=prevform;
        this.data=data;
        this.display=display;
        tf=new TextField("Type File Name","",10,TextField.ANY)  ;
        tf.setInitialInputMode("MIDP_LOWERCASE_LATIN");
        append(tf);
        this.prefix=prefix;
        if (prefix.compareTo(".png")==0) {
            addCommand(CMD_SAVE_CAPTURE_PHOTO);
            addCommand(CMD_BACK_CAPTURE_PHOTO);
        }
        try {
            setCommandListener(this);
            setItemStateListener(this);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void commandAction(Command command, Displayable displayable) {
        if(command==CMD_SAVE_CAPTURE_PHOTO) {
            if(tf.getString().compareTo("")!=0) {
                WriteFile wf=new WriteFile(tf.getString() + prefix);
                wf.Write(data);
                wf.Close();
                CameraForm capturephoto=new CameraForm(prevform,display);
            }
        }
        if(command==CMD_BACK_CAPTURE_PHOTO) {
            CameraForm capturephoto=new CameraForm(prevform,display);
        }
    }
    
    /**
     * Called when internal state of any item changed
     */
    public void itemStateChanged(Item item) {
    }
    
}
