package MMSClient.SignalProcedures;

import MMSClient.ReadFile;
import MMSClient.Parameters;
import MMSClient.Global_Parameters;
import javax.microedition.lcdui.*;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.InterruptedException;
import javax.microedition.midlet.*;
import java.lang.OutOfMemoryError;
import java.lang.String;
import MMSClient.ShowConnectedServers;

public class SendMsgtoUser extends Form implements  Runnable,CommandListener, ItemStateListener{
    private  Display mydisplay;
    private Form prevform;
    private final static Command CMD_DONE = new Command("Done", Command.SCREEN, 1);
    private final static Command CMD_BACK = new Command("Back", Command.BACK, 1);
    private ChoiceGroup cguserstype,cguser;
    private TextField tf;
    private Item SelectedItem;
    private static Global_Parameters gp;
    private String cguserstypestr="";
    private int cguserstypeint=0;
    public SendMsgtoUser() {
        super("Send Message");
        gp=new Global_Parameters();
        mydisplay=gp.Get_Display();
        prevform=gp.Get_Previous_Form();
        try {
            setCommandListener(this);
            setItemStateListener(this);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        String[] s=new String[3];
        s[0]="";
        s[1]="All users";
        s[2]="Active Users";
        cguserstype=new ChoiceGroup("Choise User Group", ChoiceGroup.POPUP,s,null);
        append(cguserstype);
        addCommand(CMD_BACK);
        mydisplay.setCurrent(this);
    }
    private void init() {
        if(cguserstypestr.compareTo("")!=0) {
            deleteAll();
            removeCommand(CMD_DONE);
            String Filename=null;
            String Users=null;
            String[] s=new String[3];
            s[0]="";
            s[1]="All users";
            s[2]="Active Users";
            cguserstype=new ChoiceGroup("Choise User Group",ChoiceGroup.POPUP,s,null);
            cguserstype.setSelectedIndex(cguserstypeint, true);
            append(cguserstype);
            ByteArrayOutputStream bout=new ByteArrayOutputStream();
            int a=cguserstype.getSelectedIndex();
            
            if(cguserstype.getSelectedIndex()==1) {
                Filename=gp.Get_ServerID() + "_" + gp.Get_Scenario() + "AllUsers.txt";
            }
            if(cguserstype.getSelectedIndex()==2) {
                Filename=gp.Get_ServerID() + "_" + gp.Get_Scenario() + "ActUsers.txt";
            }
            ReadFile rf=new ReadFile(Filename);
            if(rf.NumOfRecords()>0) {
                Users=new String();
                for(int x=1;x<=rf.NumOfRecords();x++) {
                    try {
                        bout.write(rf.Read(x));
                        Users=Users +bout.toString();
                    }
                    catch(IOException ioe)
                    {}
                }
            }
            else {
                Alert alert = new Alert("Error","I can not find File " +Filename , null, AlertType.ERROR);
                mydisplay.setCurrent(alert);
            }
            rf.Close();
            cguser=new ChoiceGroup("Users List", ChoiceGroup.POPUP,MyUsers(Users),null);
            append(cguser);
            tf=new TextField("Write Msg","",200,TextField.ANY);
            tf.setInitialInputMode("MIDP_LOWERCASE_LATIN");
            append(tf);
            addCommand(CMD_DONE);
            mydisplay.setCurrent(this);
        }
        
    }
    private String[] MyUsers(String users) {
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
            if(prevform.getClass().getName().compareTo("MMSClient.ShowConnectedServers")==0) {
                new ShowConnectedServers().init(true);
            }
            else {
                mydisplay.setCurrent(prevform);
            }
        }
        if(command==CMD_DONE) {
            
            Parameters Parameters=new Parameters();
            Parameters.Clear_Parameter(gp.Get_Parameter2_Int());
            Parameters.Set_Parameter(gp.Get_Parameter2_Int(),cguser.getString(cguser.getSelectedIndex()).getBytes(), tf.getString().getBytes());
            if(prevform.getClass().getName().compareTo("MMSClient.ShowConnectedServers")==0) {
                new ShowConnectedServers().init(true);
            }
            else {
                mydisplay.setCurrent(prevform);
            }
        }
    }
    public void itemStateChanged(Item item) {
        if(item.getLabel().compareTo("Choise User Group")==0) {
            cguserstypestr=cguserstype.getString(cguserstype.getSelectedIndex());
            cguserstypeint=cguserstype.getSelectedIndex();
            init();
        }
    }
    
    public void run() {
    }
    
}
