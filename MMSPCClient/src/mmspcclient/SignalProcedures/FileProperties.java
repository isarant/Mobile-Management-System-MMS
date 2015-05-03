
package MMSPCClient.SignalProcedures;

import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import java.util.Date;

public class FileProperties extends Form implements CommandListener, ItemStateListener {
    private RecordStore rs;
    private final static Command CMD_EXIT = new Command("Exit", Command.EXIT, 1);
    private Display display;
    private FileManager parent;
    
    public FileProperties(FileManager parent,Display display,String Filename) {
        super(Filename + " Properties");
        this.display=display;
        this.parent=parent;
        try {
            setCommandListener(this);
            setItemStateListener(this);
            addCommand(CMD_EXIT);
        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
            rs = RecordStore.openRecordStore(Filename,false);
            
        }
        catch(RecordStoreException e)
        {}
        catch(Exception e)
        {}
        try {
            Date date=new Date();
            date.setTime(rs.getLastModified());
            append("Size : "+rs.getSize());
            append("Records : "+rs.getNumRecords());
            append("Version : " + rs.getVersion());
            append("Last Modified : "+date.toString());
            append("Available Size: "+rs.getSizeAvailable());
            
            display.setCurrent(this);
        }
        catch(Exception e)
        {}
        try {
            rs.closeRecordStore();
        }
        catch(Exception e)
        {}
        
        
    }
    
    
    public void commandAction(Command command, Displayable displayable) {
        if(command==CMD_EXIT) {
            display.setCurrent(parent);
        }
    }
    
    public void itemStateChanged(Item item) {
    }
    
}
