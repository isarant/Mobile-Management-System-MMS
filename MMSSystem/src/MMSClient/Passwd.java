
package MMSClient;

import javax.microedition.lcdui.*;
import java.io.IOException;

public class Passwd extends Form implements CommandListener, ItemStateListener {
    
    private SelectServer myPrevForm;
    private Display  display;
    private TextField tf,tf2;
    private Command cmdDone=new Command("Done", Command.SCREEN, 1);
    private Command cmdSave=new Command("Save", Command.SCREEN, 1);
    private Command cmdSave2=new Command("Save", Command.SCREEN, 1);
    private Command cmdCancel=new Command("Cancel",Command.CANCEL, 1);
    private Command cmdCancel2=new Command("Cancel",Command.CANCEL, 1);
    private boolean Passok=false;
    private  MMSystem midlet;
    
    public Passwd(Display display,MMSystem midlet) {
        super("Password");
        this.display=display;
        this.midlet=midlet;
        byte[] mypass=new byte[4];
        ReadFile rf=new ReadFile("passwd.mms");
        if (rf.NumOfRecords()>0) {
            InsertPasswd();
        }
        else {
            SetPasswd();
        }
        rf.Close();
        
    }
    
    public Passwd(Display display,SelectServer myPrevForm) {
        super("Set Password");
        this.display=display;
        this.myPrevForm=myPrevForm;
        
        SetPasswd2();
        
    }
    
    public Passwd() {
        super("Set Password");
    }
    
    public byte[] GetPasswd() {
        byte[] mypass;
        ReadFile rf=new ReadFile("passwd.mms");
        if (rf.NumOfRecords()>0) {
            mypass=rf.Read();
            rf.Close();
        }
        else {
            mypass=null;
        }
        return mypass;
    }
    
    private void SetPasswd() {
        try {
            setCommandListener(this);
            setItemStateListener(this);
            addCommand(cmdSave);
            addCommand(cmdCancel);
            tf=new TextField("Enter your Password","",4,TextField.PASSWORD);
            tf2=new TextField("Confirm your Password","",4,TextField.PASSWORD);
            append(tf);
            append(tf2);
            display.setCurrent(this);
        }
        catch(Exception e)
        { }
    }
    private void SetPasswd2() {
        try {
            setCommandListener(this);
            setItemStateListener(this);
            addCommand(cmdSave2);
            addCommand(cmdCancel2);
            tf=new TextField("Enter your Password","",4,TextField.PASSWORD);
            tf2=new TextField("Confirm your Password","",4,TextField.PASSWORD);
            append(tf);
            append(tf2);
            display.setCurrent(this);
        }
        catch(Exception e)
        { }
    }
    
    private void InsertPasswd() {
        try {
            setCommandListener(this);
            setItemStateListener(this);
            addCommand(cmdDone);
            addCommand(cmdCancel);
            tf=new TextField("Please Type your Password","",4,TextField.PASSWORD);
            append(tf);
            display.setCurrent(this);
        }
        catch(Exception e)
        { }
    }
    public void commandAction(Command command, Displayable displayable) {
        if(command==cmdSave) {
            if(tf.getString().compareTo(tf2.getString())==0) {
                byte[] mycid=new byte[4];
                try {
                    mycid=tf.getString().getBytes();
                }
                catch(Exception e) {
                    Alert errorAlert = new Alert("Alert",
                    "Problem with Password",
                    null, AlertType.ERROR);
                    errorAlert.setTimeout(Alert.FOREVER);
                    display.setCurrent(errorAlert);
                }
                if(mycid.length>0) {
                    new DeleteFile("passwd.mms");
                    ClientCrypt clientcrypt=new ClientCrypt();
                    WriteFile wf1=new WriteFile("passwd.mms");
                    wf1.Write(clientcrypt.EnCryptLogon(mycid));
                    wf1.Close();
                    midlet.GotoSelectserver();
                }
            }
            else {
                tf.setString("");
                tf2.setString("");
                Alert errorAlert = new Alert("Alert",
                "Try Again",
                null, AlertType.ERROR);
                errorAlert.setTimeout(Alert.FOREVER);
                display.setCurrent(errorAlert);
            }
        }
        if(command==cmdSave2) {
            if(tf.getString().compareTo(tf2.getString())==0) {
                byte[] mycid=new byte[4];
                try {
                    mycid=tf.getString().getBytes();
                }
                catch(Exception e) {
                    Alert errorAlert = new Alert("Alert",
                    "Problem with Password",
                    null, AlertType.ERROR);
                    errorAlert.setTimeout(Alert.FOREVER);
                    display.setCurrent(errorAlert);
                }
                if(mycid.length>0) {
                    new DeleteFile("passwd.mms");
                    ClientCrypt clientcrypt=new ClientCrypt();
                    WriteFile wf1=new WriteFile("passwd.mms");
                    wf1.Write(clientcrypt.EnCryptLogon(mycid));
                    wf1.Close();
                    myPrevForm.init();
                    display.setCurrent(myPrevForm);
                }
            }
            else {
                tf.setString("");
                tf2.setString("");
                Alert errorAlert = new Alert("Alert",
                "Try Again",
                null, AlertType.ERROR);
                errorAlert.setTimeout(Alert.FOREVER);
                display.setCurrent(errorAlert);
            }
        }
        if(command==cmdCancel2) {
            display.setCurrent(myPrevForm);
        }
        if(command==cmdCancel) {
            try {
                midlet.destroyApp(false);
                midlet.notifyDestroyed();
            }
            catch(Exception e)
            {}
        }
        if(command==cmdDone) {
            byte[] mycid;
            try {
                ClientCrypt clientcrypt=new ClientCrypt();
                mycid=clientcrypt.EnCryptLogon(tf.getString().getBytes());
                byte[] mypass=GetPasswd();
                boolean ok=true;
                try{
                    
                    for(int x=0;x<mypass.length;x++) {
                        if(mycid[x]!=mypass[x]) {
                            ok=false;
                            break;
                        }
                    }
                }
                catch(Exception e) {
                    ok=false;
                }
                if(ok) {
                    Passok=true;
                    midlet.GotoSelectserver();
                }
                else {
                    Passok=false;
                    try {
                        midlet.destroyApp(false);
                        midlet.notifyDestroyed();
                    }
                    catch(Exception e)
                    {}
                }
            }
            catch(Exception e) {
                Alert errorAlert = new Alert("Alert",
                "Problem with Password",
                null, AlertType.ERROR);
                errorAlert.setTimeout(Alert.FOREVER);
                display.setCurrent(errorAlert);
            }
            
        }
    }
    
    
    public boolean PasswordIsOK() {
        return Passok;
    }
    public void itemStateChanged(Item item) {
    }
    
}
