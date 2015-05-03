
package MMSClient;

import javax.microedition.lcdui.*;
import java.lang.Byte;
import java.lang.Integer;

//This class is a form for add a server
class AddServerForm extends Form implements CommandListener, ItemStateListener {
    private Display display;
    private SelectServer myPrevForm;
    private Command AddCommand =new Command("Add", Command.SCREEN, 1);
    private Command cmdCancel=new Command("Cancel",Command.CANCEL, 1);
    private TextField tfServerID;
    private TextField tfScenario;
    private TextField tfServerName;
    private TextField tfServerURL;
    private TextField tfUserName;
    private TextField tfPasswd;
    private TextField tfKeyNum;
    
    public AddServerForm(Display display,SelectServer PrevForm) {
        super("Add Server Details");
        append("Server Details ");
        append("Ask your Administrator");
        this.display=display;
        myPrevForm=PrevForm;
        //Create components of form
        try {
            setCommandListener(this);
            setItemStateListener(this);
            addCommand(AddCommand);
            addCommand(cmdCancel);
            tfServerID=new TextField("Server ID","",3,TextField.NUMERIC);
            tfScenario=new TextField("Scenario ID","",10,TextField.NUMERIC);
            tfServerName=new TextField("Server Name","",10,0);
            tfServerName.setInitialInputMode("MIDP_LOWERCASE_LATIN");
            tfServerURL=new TextField("Server URL or IP","",50,0);
            tfServerURL.setInitialInputMode("MIDP_LOWERCASE_LATIN");
            tfUserName=new TextField("User Name","",10,0);
            tfUserName.setInitialInputMode("MIDP_LOWERCASE_LATIN");
            tfPasswd=new TextField("PassWord","",10,0);
            tfPasswd.setInitialInputMode("MIDP_LOWERCASE_LATIN");
            tfKeyNum=new TextField("Key Number","",10,TextField.NUMERIC);
            append(tfServerID);
            append(tfScenario);
            append(tfServerName);
            append(tfServerURL);
            append(tfUserName);
            append(tfPasswd);
            append(tfKeyNum);
        }
        catch(Exception e)
        {}
    }
    
    public void commandAction(Command command, Displayable displayable) {
        boolean ok=true;
        //Add Command procedure
        //Save server details
        if (command==AddCommand) {
            ServersFile sf=new ServersFile();
            try {
                //Check if Server ID is a number between 1 and 255
                if(Byte.parseByte(tfServerID.getString())>0 && Byte.parseByte(tfServerID.getString())<256)
                {sf.Set_ServerID(Byte.parseByte(tfServerID.getString()));
                }
                else {
                    Alert errorAlert = new Alert("Alert",
                    "Server ID is a number betwwen 1-255",
                    null, AlertType.ERROR);
                    errorAlert.setTimeout(Alert.FOREVER);
                    display.setCurrent(errorAlert);
                    ok=false;
                }
                //Check if Scenario is number  great than 0
                if(Integer.parseInt(tfScenario.getString())>0) {
                    sf.Set_Scenario(Integer.parseInt(tfScenario.getString()));
                }
                else {
                    Alert errorAlert = new Alert("Alert",
                    "Scenario ID is a number betwwen 1 - 4.294.967.295",
                    null, AlertType.ERROR);
                    errorAlert.setTimeout(Alert.FOREVER);
                    display.setCurrent(errorAlert);
                    ok=false;
                }
                //Check if Server name is null
                if(tfServerName.getString()!=null) {
                    sf.Set_ServerName(tfServerName.getString());
                }
                else {
                    Alert errorAlert = new Alert("Alert",
                    "Type Server Name",
                    null, AlertType.ERROR);
                    errorAlert.setTimeout(Alert.FOREVER);
                    display.setCurrent(errorAlert);
                    ok=false;
                }
                //Check if Server URL is null
                if(tfServerURL.getString()!=null) {
                    sf.Set_ServerURL(tfServerURL.getString());
                }
                else {
                    Alert errorAlert = new Alert("Alert",
                    "Type Server URL or IP",
                    null, AlertType.ERROR);
                    errorAlert.setTimeout(Alert.FOREVER);
                    display.setCurrent(errorAlert);
                    ok=false;
                }
                //Check if user name is null
                if(tfUserName.getString()!=null) {
                    sf.Set_UserName(tfUserName.getString());
                }
                else {
                    Alert errorAlert = new Alert("Alert",
                    "Type User Name",
                    null, AlertType.ERROR);
                    errorAlert.setTimeout(Alert.FOREVER);
                    display.setCurrent(errorAlert);
                    ok=false;
                }
                //Check if user password is null
                if(tfPasswd.getString()!=null) {
                    sf.Set_Passwd(tfPasswd.getString());
                }
                else {
                    Alert errorAlert = new Alert("Alert",
                    "Type Password",
                    null, AlertType.ERROR);
                    errorAlert.setTimeout(Alert.FOREVER);
                    display.setCurrent(errorAlert);
                    ok=false;
                }
                //Check if Key number is great than 0
                if(Integer.parseInt(tfKeyNum.getString())>0) {
                    sf.Set_KeyNum(Integer.parseInt(tfKeyNum.getString()));
                }
                else {
                    Alert errorAlert = new Alert("Alert",
                    "Key Number is a number betwwen 1 - 4.294.967.295",
                    null, AlertType.ERROR);
                    errorAlert.setTimeout(Alert.FOREVER);
                    display.setCurrent(errorAlert);
                    ok=false;
                }
            }
            catch(Exception e) {
                Alert errorAlert = new Alert("Alert",
                "Server ID is a number betwwen 1-255" + "Scenario ID is a number betwwen 1 - 4.294.967.295",
                null, AlertType.ERROR);
                errorAlert.setTimeout(Alert.FOREVER);
                display.setCurrent(errorAlert);
                ok=false;
            }
            //If pass all check write server detail in SerFile.mms file
            if(ok) {
                WriteFile wf=new WriteFile("SerFile.mms");
                wf.Write(sf.Get_ServerFile_Byte());
                wf.Close();
                myPrevForm.init();
                display.setCurrent(myPrevForm);
            }
            
        }
        // Cancel Command procedure
        //Return to Previous form
        if(command==cmdCancel) {
            display.setCurrent(myPrevForm);
        }
    }
    
    public void itemStateChanged(Item item) {
    }
    
}
