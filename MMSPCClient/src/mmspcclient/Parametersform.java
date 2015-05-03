
package MMSPCClient;

import javax.microedition.lcdui.*;

public class Parametersform extends Form implements CommandListener, ItemStateListener {
    
    private Display mydisplay;
    private Form form;
    private final static Command CMD_EXIT = new Command("Exit", Command.EXIT,
    1);
    private final static Command CMD_Ok = new Command("Ok", Command.SCREEN,
    1);
    private TextField parname1;
    private TextField parname2;
    private int numofParameter;
    
    public Parametersform(Display display,Form form,SignalsFile signalfile,int numofParameter) {
        super("Set Parameters");
        this.form=form;
        this.mydisplay=display;
        this.numofParameter=numofParameter;
        try {
            // Set up this form to listen to command events
            setCommandListener(this);
            // Set up this form to listen to changes in the internal state of its interactive items
            setItemStateListener(this);
            // Add the Exit command
            addCommand(CMD_EXIT);
        }
        catch(Exception e)
        { }
        Parameters Parameters=new Parameters();
        Parameters.Clear_Parameter(numofParameter);
        if(signalfile.Get_Parameters()==1) {
            if(Parameters.Get_Parameter(numofParameter)!=null) {
                parname1=new TextField(signalfile.Get_Parameter1Name(),Parameters.Get_Parameter(numofParameter).Ge_Parameter1_String(),20,inputtype(signalfile.Get_TypeOfParameter1_String()));
                parname1.setInitialInputMode("MIDP_LOWERCASE_LATIN");
            }
            else {
                parname1=new TextField(signalfile.Get_Parameter1Name(),"",20,inputtype(signalfile.Get_TypeOfParameter1_String()));
                parname1.setInitialInputMode("MIDP_LOWERCASE_LATIN");
                
            }
            append(parname1);
            addCommand(CMD_Ok);
        }
        if(signalfile.Get_Parameters()==2) {
            if(Parameters.Get_Parameter(numofParameter)!=null) {
                
                parname1=new TextField(signalfile.Get_Parameter1Name(),Parameters.Get_Parameter(numofParameter).Ge_Parameter1_String(),20,inputtype(signalfile.Get_TypeOfParameter1_String()));
                parname1.setInitialInputMode("MIDP_LOWERCASE_LATIN");
                parname2=new TextField(signalfile.Get_Parameter2Name(),Parameters.Get_Parameter(numofParameter).Ge_Parameter2_String(),20,inputtype(signalfile.Get_TypeOfParameter2_String()));
                parname2.setInitialInputMode("MIDP_LOWERCASE_LATIN");
            }
            else {
                parname1=new TextField(signalfile.Get_Parameter1Name(),"",20,inputtype(signalfile.Get_TypeOfParameter1_String()));
                parname1.setInitialInputMode("MIDP_LOWERCASE_LATIN");
                parname2=new TextField(signalfile.Get_Parameter2Name(),"",20,inputtype(signalfile.Get_TypeOfParameter2_String()));
                parname2.setInitialInputMode("MIDP_LOWERCASE_LATIN");
            }
            append(parname1);
            append(parname2);
            addCommand(CMD_Ok);
        }
        try {
            this.mydisplay.setCurrent(this);
        }
        catch(Exception e)
        {}
    }
    
    
    public void commandAction(Command command, Displayable displayable) {
        if(command==CMD_Ok) {
            
            Parameters Parameters=new Parameters();
            String par1="";
            String par2="";
            try {
                if(parname1.getString().length()>0) {
                    par1=parname1.getString();
                }
                if(parname2.getString().length()>0) {
                    par2=parname2.getString();
                }
            }
            catch(Exception e)
            {}
            Parameters.Set_Parameter(numofParameter,par1,par2);
            // this.mydisplay.setCurrent(form);
            new ShowConnectedServers().init(true);
        }
        if(command==CMD_EXIT) {
            parname2.setString("");
            parname1.setString("");
            Parameters Parameters=new Parameters();
            Parameters.Set_Parameter(numofParameter,parname1.getString(),parname2.getString());
            //mydisplay.setCurrent(form);
            new ShowConnectedServers().init(true);
        }
    }
    
    public void itemStateChanged(Item item) {
        
    }
    private int inputtype(String type) {
        int intype=0;
        if(type.compareTo("txt")==0) {
            intype=TextField.ANY;
        }
        if(type.compareTo("int")==0) {
            intype=TextField.NUMERIC;
        }
        if(type.compareTo("float")==0) {
            intype=TextField.DECIMAL;
        }
        return intype;
    }
}
