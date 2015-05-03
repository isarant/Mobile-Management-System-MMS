package MMSPCClient.SignalProcedures;

import MMSPCClient.ReadFile;
import MMSPCClient.Parameters;
import MMSPCClient.Global_Parameters;
import javax.microedition.lcdui.*;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.InterruptedException;
import javax.microedition.midlet.*;
import java.lang.OutOfMemoryError;
import java.lang.String;
import MMSPCClient.ShowConnectedServers;
import javax.microedition.rms.*;

public class SendFileandText extends Form implements Runnable, CommandListener, ItemStateListener{
    private  Display mydisplay;
    private Form prevform;
    private final static Command CMD_DONE = new Command("Done", Command.SCREEN, 1);
    private final static Command CMD_BACK = new Command("Back", Command.BACK, 1);
    private ChoiceGroup cguserstype,cguser,cgfilename;
    private TextField tf;
    private Item SelectedItem;
    private static Global_Parameters gp;
    private int cguserstypeint=0;
    private String[] Files;
    private RecordStore rs;
    
    public SendFileandText() {
        super("Send File and Message");
        gp=new Global_Parameters();
        mydisplay=gp.Get_Display();
        prevform=gp.Get_Previous_Form();
        try {
            setCommandListener(this);
            setItemStateListener(this);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        Files=rs.listRecordStores();
        if(Files.length>0) {
            Image[] imageArray = null;
            cgfilename=new ChoiceGroup("Choise File ",ChoiceGroup.POPUP,Files,imageArray);
            
        }
        addCommand(CMD_BACK);
        mydisplay.setCurrent(this);
        deleteAll();
            removeCommand(CMD_DONE);
            String Filename=null;
            ByteArrayOutputStream bout=new ByteArrayOutputStream();
            tf=new TextField("Write Msg","",200,TextField.ANY);
            tf.setInitialInputMode("MIDP_LOWERCASE_LATIN");
            append(tf);
            append(cgfilename);
            addCommand(CMD_DONE);
            mydisplay.setCurrent(this);
    }
   
  
    public void commandAction(javax.microedition.lcdui.Command command, javax.microedition.lcdui.Displayable displayable) {
        if(command==CMD_BACK) {
            if(prevform.getClass().getName().compareTo("MMSPCClient.ShowConnectedServers")==0) {
                new ShowConnectedServers().init(true);
            }
            else {
                mydisplay.setCurrent(prevform);
            }
        }
        if(command==CMD_DONE) {
            try {
                byte[] filedata = null;
                ReadFile rf=new ReadFile(cgfilename.getString(cgfilename.getSelectedIndex()));
                if (rf.NumOfRecords()>0) {
                    filedata=rf.Read(1);
                    Parameters Parameters=new Parameters();
                    Parameters.Clear_Parameter(gp.Get_Parameter2_Int());
                    ByteArrayOutputStream param1=new ByteArrayOutputStream();
                    param1.write(tf.getString().getBytes());
                    Parameters.Set_Parameter(gp.Get_Parameter2_Int(),param1.toByteArray(), filedata );
                    if(prevform.getClass().getName().compareTo("MMSPCClient.ShowConnectedServers")==0) {
                        new ShowConnectedServers().init(true);
                    }
                    else {
                        mydisplay.setCurrent(prevform);
                    }
                }
                rf.Close();
            }
            catch(IOException ioe)
            {}
        }
    }
    public void itemStateChanged(Item item) {
       
    }
    
    public void run() {
    }
    
}
