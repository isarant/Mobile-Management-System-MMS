package MMSClient.SignalProcedures;

import javax.microedition.lcdui.*;
import MMSClient.ShowConnectedServers;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import MMSClient.Parameters;
import MMSClient.Global_Parameters;

public class ShowFileMsgForm extends Form implements CommandListener, ItemStateListener {
    
    private final static Command CMD_EXIT = new Command("Exit", Command.EXIT, 1);
    private Form prevform;
    private Display display;
    private static Global_Parameters gp;
    private Image image,helpimage;
    
    public ShowFileMsgForm(String name,String msg,String FileName,byte[] filedata, Display display, Form prevform) {
        super("Recieve a Message");
        this.prevform=prevform;
        this.display=display;
        TextField tfname=new TextField("SenderName: ",name,200,TextField.UNEDITABLE);
        TextField tfmsg=new TextField("Message: ",msg,200,TextField.UNEDITABLE);
        TextField tffilename=new TextField("File Name: ",FileName,200,TextField.UNEDITABLE);
        append(tfname);
        append(tfmsg);
        append(tffilename);
        String ext=FileName.substring(FileName.indexOf("."),FileName.length());
        if( ext.toLowerCase().compareTo(".png")==0 || ext.toLowerCase().compareTo(".jpg")==0|| ext.toLowerCase().compareTo(".jpeg")==0) {
            try {
                helpimage=Image.createImage(filedata,0,filedata.length);
                image=Image.createImage(helpimage);
                append(image);
            }
            catch(Exception e)
            {}
        }
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
    
    public void itemStateChanged(Item item) {
    }
    
}
