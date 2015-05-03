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

public class SendMeFile extends Form implements Runnable, CommandListener, ItemStateListener{
    private  Display mydisplay;
    private Form prevform;
    private final static Command CMD_DONE = new Command("Done", Command.SCREEN, 1);
    private final static Command CMD_BACK = new Command("Back", Command.BACK, 1);
    private ChoiceGroup cgfiles;
    private static Global_Parameters gp;
    
    public SendMeFile() {
        super("Send Me File");
        ByteArrayOutputStream bout=new ByteArrayOutputStream();
        String Files="";
        gp=new Global_Parameters();
        mydisplay=gp.Get_Display();
        prevform=gp.Get_Previous_Form();
        try {
            setCommandListener(this);
            setItemStateListener(this);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        addCommand(CMD_BACK);
        ReadFile rf=new ReadFile(gp.Get_ServerID() + "AvaliableFiles.txt");
        if(rf.NumOfRecords()>0) {
            Files=new String();
            for(int x=1;x<=rf.NumOfRecords();x++) {
                try {
                    bout.write(rf.Read(x));
                    Files=Files +bout.toString();
                }
                catch(IOException ioe)
                {}
            }
        }
        else {
            Alert alert = new Alert("Error","I can not find File"+gp.Get_Parameter2_Str()+" AvaliableFiles.txt "  , null, AlertType.ERROR);
            mydisplay.setCurrent(alert);
        }
        rf.Close();
        cgfiles =new ChoiceGroup("Files List", ChoiceGroup.POPUP,MyFiles(Files),null);
        append(cgfiles);
        addCommand(CMD_DONE);
        mydisplay.setCurrent(this);
    }
    
    private String[] MyFiles(String users) {
        String[] Str;
        int myint=0;
        int count=0;
        while(myint!=-1) {
            myint=users.indexOf(",",myint+1);
            count++;
        }
        Str=new String[count];
        count=0;
        myint=0;
        while(myint!=-1) {
            int myitnbigen=myint;
            myint=users.indexOf(",",myint+1);
            int myitnend=myint;
            if(myitnend==-1) {
                myitnend=users.length();
            }
            if(myitnbigen!=0) {
                myitnbigen=myitnbigen+1;
            }
            Str[count]=users.substring(myitnbigen,myitnend);
            count++;
        }
        return Str;
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
            
            if(cgfiles.getString(cgfiles.getSelectedIndex()).compareTo("")!=0) {
                Parameters Parameters=new Parameters();
                Parameters.Clear_Parameter(gp.Get_Parameter2_Int());
                byte[] nullbyte=null;
                Parameters.Set_Parameter(gp.Get_Parameter2_Int(),cgfiles.getString(cgfiles.getSelectedIndex()).getBytes(), nullbyte);
                if(prevform.getClass().getName().compareTo("MMSPCClient.ShowConnectedServers")==0) {
                    new ShowConnectedServers().init(true);
                }
                else {
                    mydisplay.setCurrent(prevform);
                }
            }
        }
    }
    
    public void itemStateChanged(Item item) {
        
    }
    
    public void run() {
    }
    
}
