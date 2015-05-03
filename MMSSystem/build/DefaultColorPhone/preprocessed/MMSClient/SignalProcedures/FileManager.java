
package MMSClient.SignalProcedures;

import MMSClient.ReadFile;
import MMSClient.WriteFile;
import MMSClient.Parameters;
import MMSClient.DeleteFile;
import MMSClient.Global_Parameters;
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.lang.InterruptedException;
import javax.microedition.midlet.*;
import java.lang.OutOfMemoryError;
import MMSClient.ShowConnectedServers;

public class FileManager extends Form implements  CommandListener, ItemStateListener {
    
    private int type;
    private  Display mydisplay;
    private Form prevform;
    private final static Command CMD_OPEN = new Command("Open", Command.SCREEN, 1);
    private final static Command CMD_PROPETRIES = new Command("Properites", Command.SCREEN, 5);
    private final static Command CMD_OPEN2 = new Command("Open", Command.SCREEN, 1);
    private final static Command CMD_DELETE = new Command("Delete", Command.SCREEN, 2);
    private final static Command CMD_RENAME = new Command("Rename", Command.SCREEN, 3);
    private final static Command CMD_COPY = new Command("Copy", Command.SCREEN, 4);
    private final static Command CMD_EXIT = new Command("Exit", Command.EXIT, 1);
    private final static Command CMD_EXIT1 = new Command("Exit", Command.EXIT, 1);
    private final static Command CMD_BACK = new Command("Back", Command.BACK, 1);
    private final static Command CMD_BACK1 = new Command("Back", Command.BACK, 1);
    private final static Command CMD_BACKTXT = new Command("Back", Command.BACK, 1);
    private final static Command CMD_SAVETXT= new Command("Save", Command.SCREEN, 1);
    private String[] Files;
    private RecordStore rs;
    private ChoiceGroup cg;
    private Item SelectedItem;
    private String oldname=null;
    private TextBox textBox;
    private String FileName;
    private byte[] FileData;
    private static Global_Parameters gp;
    private FileProperties fileproperties;
    
    public FileManager(int Type,Display display,Form PrevForm) {
        super("File Manager");
        gp=new Global_Parameters();
        mydisplay=display;
        prevform=PrevForm;
        type=Type;
        try {
            setCommandListener(this);
            setItemStateListener(this);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        init();
        
    }
    
    
    public FileManager() {
        super("File Manager");
        gp=new Global_Parameters();
        mydisplay=gp.Get_Display();
        prevform=gp.Get_Previous_Form();
        type=gp.Get_Parameter1_Int();
        try {
            setCommandListener(this);
            setItemStateListener(this);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        init();
        
    }
    public void Set_DIsplay(Display display) {
        mydisplay=display;
    }
    public void Set_PrevForm(Form PrevForm) {
        prevform=PrevForm;
    }
    public void Set_Type(int Type) {
        type=Type;
    }
    public void init() {
        deleteAll();
        removeCommand(CMD_OPEN);
        removeCommand(CMD_OPEN2);
        removeCommand(CMD_DELETE);
        removeCommand(CMD_RENAME);
        removeCommand(CMD_EXIT);
        removeCommand(CMD_EXIT1);
        removeCommand(CMD_COPY);
        removeCommand(CMD_PROPETRIES);
        try {
            
            Files=rs.listRecordStores();
            if(Files.length>0) {
                Image[] imageArray = null;
                cg=new ChoiceGroup("",ChoiceGroup.EXCLUSIVE,Files,imageArray);
                append(cg);
                if(type==1) {
                    addCommand(CMD_OPEN);
                    addCommand(CMD_EXIT);
                }
                if(type==2) {
                    addCommand(CMD_OPEN2);
                    addCommand(CMD_EXIT1);
                }
                else {
                    addCommand(CMD_OPEN);
                    addCommand(CMD_DELETE);
                    addCommand(CMD_RENAME);
                    addCommand(CMD_COPY);
                    addCommand(CMD_PROPETRIES);
                    addCommand(CMD_EXIT);
                }
            }
        }
        catch(Exception e)
        {}
        
        mydisplay.setCurrent(this);
    }
    public void commandAction(Command command, Displayable displayable) {
        if(command==CMD_EXIT) {
            String sss=prevform.getClass().getName();
            if(sss.compareTo("MMSClient.ShowConnectedServers")==0) {
                Runnable showcon= new Runnable() {
                    public void run() {
                        
                        ShowConnectedServers VarShowConnectedServers=new ShowConnectedServers();
                        while(VarShowConnectedServers.IsWorking) {
                            try {
                                Thread.sleep(50);
                            }
                            catch(InterruptedException e)
                            { }
                        }
                        synchronized(VarShowConnectedServers) {
                            VarShowConnectedServers.init(true);
                        }
                    }
                };
                Thread ShowConnectionThread = new Thread(showcon);
                ShowConnectionThread.start();
            }
            else {
                mydisplay.setCurrent(prevform);
            }
        }
        if(command==CMD_EXIT1) {
            String sss=prevform.getClass().getName();
            if(sss.compareTo("MMSClient.ShowConnectedServers")==0) {
                Runnable showcon= new Runnable() {
                    public void run() {
                        
                        ShowConnectedServers VarShowConnectedServers=new ShowConnectedServers();
                        while(VarShowConnectedServers.IsWorking) {
                            try {
                                Thread.sleep(50);
                            }
                            catch(InterruptedException e)
                            { }
                        }
                        synchronized(VarShowConnectedServers) {
                            VarShowConnectedServers.init(true);
                        }
                    }
                };
                Thread ShowConnectionThread = new Thread(showcon);
                ShowConnectionThread.start();
            }
            else {
                mydisplay.setCurrent(prevform);
            }
        }
        if(command==CMD_PROPETRIES) {
            fileproperties=new FileProperties(this,mydisplay,cg.getString(cg.getSelectedIndex()));
        }
        if(command==CMD_OPEN) {
            ReadFile rf=null;
            try {
                FileName=cg.getString(cg.getSelectedIndex());
                ByteArrayOutputStream b=new ByteArrayOutputStream();
                rf=new ReadFile(FileName);
                if(rf.NumOfRecords()==1) {
                    FileData=rf.Read(1);
                }
                else {
                    for(int looprec=1;looprec<=rf.NumOfRecords();looprec++) {
                        b.write(rf.Read(looprec));
                    }
                    FileData=b.toByteArray();
                    b.close();
                }
                rf.Close();
                String ext=FileName.substring(FileName.indexOf("."),FileName.length());
                if( ext.toLowerCase().compareTo(".png")==0 || ext.toLowerCase().compareTo(".jpg")==0|| ext.toLowerCase().compareTo(".jpeg")==0) {
                    if(FileData.length>0) {
                        ShowImage showimage=new ShowImage(FileData,mydisplay,this) ;
                    }
                }
                if(FileData.length>0 && ext.toLowerCase().compareTo(".txt")==0) {
                    ByteArrayOutputStream btxt=new ByteArrayOutputStream();
                    btxt.write(FileData);
                    int nuofchar=30000;
                    String docstr=btxt.toString();
                    int docstrlength=docstr.length();
                    if(docstrlength>nuofchar) {
                        nuofchar=docstrlength+3000;
                    }
                    textBox=new TextBox(FileName, docstr, nuofchar, 0);
                    textBox.addCommand(CMD_BACKTXT);
                    textBox.addCommand(CMD_SAVETXT);
                    textBox.setCommandListener(this);
                    mydisplay.setCurrent(textBox);
                }
                
                if(FileData.length>0 && (ext.toLowerCase().compareTo(".mp4")==0 || ext.toLowerCase().compareTo(".3gp")==0 || ext.toLowerCase().compareTo(".wmv")==0 || ext.toLowerCase().compareTo(".avi")==0 || ext.toLowerCase().compareTo(".mpeg")==0 || ext.toLowerCase().compareTo(".mpg")==0 || ext.toLowerCase().compareTo(".wav")==0 || ext.toLowerCase().compareTo(".midi")==0 || ext.toLowerCase().compareTo(".mid")==0  ||ext.toLowerCase().compareTo(".mp3")==0 )) {
                    try {
                        InputStream is = new ByteArrayInputStream(FileData);
                        Mediaplayer mediaplayer=new Mediaplayer(is,FileName,mydisplay,this) ;
                    }
                    catch(Exception e)
                    {}
                }
            }
            catch(OutOfMemoryError e) {
                rf.Close();
                Alert alert = new Alert("OutOfMemoryError FileManager", e.getMessage(), null, AlertType.ERROR);
                mydisplay.setCurrent(alert);
            }
            catch(IOException e) {
                rf.Close();
                //mydisplay.setCurrent(prevform);
                Alert alert = new Alert("IO Error", e.getMessage(), null, AlertType.ERROR);
                mydisplay.setCurrent(alert);
            }
            catch(Exception e) {
                rf.Close();
                //mydisplay.setCurrent(prevform);
                Alert alert = new Alert("Error", e.getMessage(), null, AlertType.ERROR);
                mydisplay.setCurrent(alert);
            }
            
            
        }
        if(command==CMD_OPEN2) {
            System.gc();
            Parameters Parameters=new Parameters();
            Parameters.Clear_Parameter(gp.Get_Parameter2_Int());
            FileName=cg.getString(cg.getSelectedIndex());
            ByteArrayOutputStream b=new ByteArrayOutputStream();
            ReadFile rf=new ReadFile(FileName);
            try {
                
                byte[] mydata=null;
                if(rf.NumOfRecords()==1) {
                    mydata=rf.Read(1);
                }
                else {
                    for(int looprec=1;looprec<=rf.NumOfRecords();looprec++) {
                        b.write(rf.Read(looprec));
                    }
                    mydata=b.toByteArray();
                }
                
                Parameters.Set_Parameter(gp.Get_Parameter2_Int(),FileName.getBytes(), mydata);
                b.close();
                mydisplay.setCurrent(prevform);
            }
            catch(OutOfMemoryError e)
            {}
            catch(Exception e)
            {}
            rf.Close();
        }
        if(command==CMD_RENAME) {
            oldname=cg.getString(cg.getSelectedIndex());
            textBox = new TextBox("Type New Name", "", 50,
            TextField.ANY);
            textBox.setInitialInputMode("MIDP_LOWERCASE_LATIN");
            textBox.addCommand(CMD_BACK);
            textBox.setCommandListener(this);
            mydisplay.setCurrent(textBox);
        }
        if(command==CMD_COPY) {
            oldname=cg.getString(cg.getSelectedIndex());
            textBox = new TextBox("Type New Name", "", 50,
            TextField.ANY);
            textBox.setInitialInputMode("MIDP_LOWERCASE_LATIN");
            textBox.addCommand(CMD_BACK1);
            textBox.setCommandListener(this);
            mydisplay.setCurrent(textBox);
        }
        if(command==CMD_DELETE) {
            String s=cg.getString(cg.getSelectedIndex());
            new DeleteFile(s);
            init();
        }
        if (command == CMD_BACK) {
            try {
                if(textBox.getString().length()>0) {
                    byte[] mydata;
                    ReadFile rf=new ReadFile(oldname);
                    WriteFile wf=new WriteFile(textBox.getString());
                    for(int looprec=1;looprec<=rf.NumOfRecords();looprec++) {
                        wf.Write(rf.Read(looprec));
                    }
                    rf.Close();
                    wf.Close();
                    new DeleteFile(oldname);
                }
            }
            catch(OutOfMemoryError e)
            {}
            catch(Exception e)
            {}
            mydisplay.setCurrent(this);
            init();
        }
        if (command == CMD_BACK1) {
            try {
                if(textBox.getString().length()>0) {
                    byte[] mydata;
                    ReadFile rf=new ReadFile(oldname);
                    WriteFile wf=new WriteFile(textBox.getString());
                    for(int looprec=1;looprec<=rf.NumOfRecords();looprec++) {
                        wf.Write(rf.Read(looprec));
                    }
                    rf.Close();
                    wf.Close();
                }
            }
            catch(OutOfMemoryError e)
            {}
            catch(Exception e)
            {}
            mydisplay.setCurrent(this);
            init();
        }
        if (command == CMD_BACKTXT) {
            mydisplay.setCurrent(this);
            init();
        }
        if (command == CMD_SAVETXT) {
            
            try {
                if(textBox.getString().length()>0) {
                    new DeleteFile(FileName);
                    WriteFile wf=new WriteFile(FileName);
                    wf.Write(textBox.getString().getBytes());
                    wf.Close();
                }
            }
            catch(OutOfMemoryError e)
            {}
            catch(Exception e)
            {}
            mydisplay.setCurrent(this);
            init();
        }
        
        
    }
    
    public void itemStateChanged(Item item) {
        
    }
    
    public String Get_FileName() {
        return FileName;
    }
    public byte[] Get_FileData() {
        return FileData;
    }
    
    
    
}
