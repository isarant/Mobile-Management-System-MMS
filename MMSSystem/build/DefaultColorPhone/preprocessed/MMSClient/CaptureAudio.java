
package MMSClient;

import javax.microedition.lcdui.*;
import javax.microedition.media.*;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import MMSClient.SignalProcedures.Mediaplayer;

//Form for capture audio
public class CaptureAudio extends Form implements CommandListener, Runnable {
    //form components
    private final static Command CMD_REC= new Command("Rec", Command.SCREEN, 1);
    private final static Command CMD_PLAY= new Command("Play", Command.SCREEN, 2);
    private final static Command CMD_EXIT = new Command("Exit", Command.EXIT, 1);
    private Display display;
    private Form prevform;
    private String FileName="";
    private TextField tf;
    private TextField tffilename;
    private  MyTimer mytimer;
    private  Player player ;
    private RecordControl rc;
    
    public CaptureAudio(Form prevform,Display display) {
        //initialize form
        super("Audio Recorder");
        tf=new TextField("Rec Time in millisecond","",10,TextField.UNEDITABLE);
        tffilename=new TextField("Type File Name For Recordig Sound","",10,TextField.ANY);
        tffilename.setInitialInputMode("MIDP_LOWERCASE_LATIN");
        this.prevform=prevform;
        this.display=display;
        append(tffilename);
        addCommand(CMD_REC);
        addCommand(CMD_EXIT);
        try {
            setCommandListener(this);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        display.setCurrent(this);
    }
    
    //capture audio for 5000 milisecond
    public void run() {
        boolean problem=false;
        String errorstr="";
        try {
            removeCommand(CMD_REC);
            removeCommand(CMD_PLAY);
            removeCommand(CMD_EXIT);
            display.setCurrent(this);
        }
        catch(Exception e)
        {}
        
        try {
            // Create a Player that captures live audio.
            player = Manager.createPlayer("capture://audio");
            player.realize();
            rc = (RecordControl)player.getControl("RecordControl");
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            rc.setRecordStream(output);
            // rc.setRecordLocation("file:/"+FileName);
            player.prefetch();
            rc.startRecord();
            player.start();
            mytimer=new MyTimer(this);
            mytimer.start();
            Thread.currentThread().sleep(5000);
            rc.stopRecord();
            mytimer.stop();
            rc.commit();
            player.close();
            if(output!=null) {
                WriteFile wf=new WriteFile(FileName);
                wf.Write(output.toByteArray());
                wf.Close();
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            errorstr=ioe.getMessage();
            problem=true;
            mytimer.stop();
        }
        catch (MediaException me) {
            me.printStackTrace();
            problem=true;
            errorstr=me.getMessage();
            mytimer.stop();
        }
        catch (InterruptedException ie) {
            ie.printStackTrace();
            problem=true;
            errorstr=ie.getMessage();
            mytimer.stop();
        }
        catch (Exception ie) {
            ie.printStackTrace();
            problem=true;
            errorstr=ie.getMessage();
            mytimer.stop();
        }
        finally {
            if(problem) {
                deleteAll();
                append(errorstr);
                addCommand(CMD_REC);
                addCommand(CMD_EXIT);
                display.setCurrent(this);
            }
            else {
                deleteAll();
                append(tffilename);
                addCommand(CMD_PLAY);
                addCommand(CMD_REC);
                addCommand(CMD_EXIT);
                display.setCurrent(this);
            }
        }
        
    }
    
    public void commandAction(Command command, Displayable displayable) {
        //command for capure audio
        if(command==CMD_REC) {
            if(rc!=null) {
                try {
                    rc.stopRecord();
                    rc.reset();
                }
                catch(IOException ioe)
                {}
                
            }
            if(player!=null) {
                player.close();
            }
            if(tffilename.getString().compareTo("")!=0) {
                FileName=tffilename.getString() + ".wav";
                new Thread(this).start();
            }
        }
        //stop capture audio end return to previus form
        if(command==CMD_EXIT) {
            if(rc!=null) {
                try {
                    rc.stopRecord();
                    rc.reset();
                }
                catch(IOException ioe)
                {}
            }
            if(player!=null) {
                player.close();
            }
            
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
                display.setCurrent(prevform);
            }
        }
        //play captured audio
        if(command==CMD_PLAY) {
            if(rc!=null) {
                rc.stopRecord();
            }
            if(player!=null) {
                player.close();
            }
            if(FileName.compareTo("")!=0) {
                
                ReadFile rf=new ReadFile(FileName);
                if(rf.NumOfRecords()>0) {
                    InputStream is=new ByteArrayInputStream(rf.Read(1));
                    Mediaplayer mediaplayer=new Mediaplayer(is,"audio/x-wav",display,this);
                }
                rf.Close();
            }
        }
        
    }
    
    //Show record time
    private class MyTimer extends Thread {
        public boolean enable;
        private int mytimer;
        private final int steptimer=100;
        private Form myform;
        public MyTimer(Form myform) {
            this.myform=myform;
            enable=true;
            myform.deleteAll();
            myform.append(tf);
            display.setCurrent(myform);
        }
        public void run() {
            mytimer=0;
            while(enable) {
                tf.setString(String.valueOf(mytimer));
                mytimer=mytimer+steptimer;
                display.setCurrent(myform);
                try {
                    sleep(steptimer);
                }
                catch(InterruptedException e)
                {}
            }
            myform.deleteAll();
            append(tffilename);
            addCommand(CMD_PLAY);
            addCommand(CMD_REC);
            addCommand(CMD_EXIT);
            display.setCurrent(myform);
        }
        public void stop() {
            enable=false;
        }
    }
    
}
