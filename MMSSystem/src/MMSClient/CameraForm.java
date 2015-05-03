

package MMSClient;

import javax.microedition.lcdui.*;
import javax.microedition.media.*;
import javax.microedition.media.control.*;
import java.io.IOException;
import MMSClient.SignalProcedures.ShowImage;

//Form for capture video
class CameraForm extends Form implements CommandListener {
    
    private final Command exitCommand;
    private Command captureCommand=null;
    private Player player;
    private VideoControl videoControl;
    private boolean active=false;
    private StringItem messageItem;
    private Display display;
    private Form prevform;
    
    CameraForm(Form prevform,Display display) {
        super("Camera");
        this.display=display;
        this.prevform=prevform;
        //form components
        messageItem=new StringItem("Message","start");
        append(messageItem);
        exitCommand=new Command("Exit",Command.EXIT,1);
        addCommand(exitCommand);
        setCommandListener(this);
        //initialize camera
        try{
            player=Manager.createPlayer("capture://video");
            player.realize();
            videoControl=(VideoControl)(player.getControl("VideoControl"));
            if(videoControl!=null) {
                append((Item)(videoControl.initDisplayMode(VideoControl.USE_GUI_PRIMITIVE,null)));
                captureCommand=new Command("Capture",Command.SCREEN,1);
                addCommand(captureCommand);
                messageItem.setText("OK");
            } else{messageItem.setText("Novideocontrol");}
        }catch(IOException ioe) {
            messageItem.setText("IOException:"+ioe.getMessage());} catch(MediaException me){messageItem.setText("MediaException"+me.getMessage());} catch(SecurityException se){messageItem.setText("Securityexception:"+se.getMessage());}
        display.setCurrent(this);
        start();
    }
    
    //display video on screen
    synchronized void start() {
        if(!active) {
            try{
                if(player!=null) {
                    player.start();}
                if(videoControl!=null) {
                    videoControl.setVisible(true);
                }
            }catch(MediaException me) {
                messageItem.setText("Mediaexception  start:"+me.getMessage());
                removeCommand(captureCommand);
            } catch(SecurityException se) {
                messageItem.setText("Securityexception:"+se.getMessage());
                removeCommand(captureCommand);
            }
            active=true;
            display.setCurrent(this);
            
        }
    }
    
    // Stop to display video on screen
    synchronized void stop() {
        if(active) {
            try{
                if(videoControl!=null) {
                    videoControl.setVisible(false);}
                if(player!=null) {
                    player.stop();
                    player.close();
                }
            }catch(MediaException me) {
                messageItem.setText("Mediaexception:"+me.getMessage());
            }
            active=false;
        }
    }
    
    public void commandAction(Command c,Displayable d) {
        //Stop video and return to previus form
        if(c==exitCommand) {
            stop();
            String sss=prevform.getClass().getName();
            if(sss.compareTo("MMSPCClient.ShowConnectedServers")==0) {
                Runnable showcon= new Runnable() {
                    public void run() {
                        
                        ShowConnectedServers VarShowConnectedServers=new ShowConnectedServers();
                        while(VarShowConnectedServers.IsWorking) {
                            try {
                                Thread.sleep(50);
                            } catch(InterruptedException e) {
                            }
                        }
                        synchronized(VarShowConnectedServers) {
                            VarShowConnectedServers.init(true);
                        }
                    }
                };
                Thread ShowConnectionThread = new Thread(showcon);
                ShowConnectionThread.start();
            } else {
                display.setCurrent(prevform);
            }
            
        }
        //Capture video
        if(c==captureCommand) {
            //ifwehaveacapturecommand,weknowvideoControlisnotnull
            Runnable save=new Runnable() {
                public void run() {
                    try{
                        byte[]pngImage=videoControl.getSnapshot(null);
                        //midlet.cameraFormCaptured(pngImage);
                        stop();
                        SaveCapture savecapture = new SaveCapture(pngImage,display,prevform,".png");
                        ShowImage showimage = new ShowImage(pngImage,display,savecapture);
                        messageItem.setText("OK");
                    }catch(MediaException me) {
                        messageItem.setText("Mediaexception:"+me.getMessage());}
                }
                
            };
            Thread savethread=new Thread(save);
            savethread.start();
        }
    }
}


