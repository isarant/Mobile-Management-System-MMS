
package MMSClient.SignalProcedures;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.media.*;
import javax.microedition.media.control.*;
import java.io.*;
import javax.microedition.media.protocol.*;
import java.lang.*;
import MMSClient.ShowConnectedServers;

/** Acquires the video content and renders it */
public class VideoCanvas extends Canvas implements CommandListener, PlayerListener, Runnable {
    
    private Mediaplayer parent;
    private Display display;
    
    private Player player;
    private VideoControl videoControl;
    private Form Prevform;
    
    private String url;
    private Thread initializer;
    
    private Command close;
    private Command rePlay;
    
    
    public VideoCanvas(Mediaplayer parent,Display display){
        super();
        this.parent = parent;
        this.display=display ;
        this.Prevform=Prevform;
        close = new Command("close", Command.EXIT, 1);
        addCommand(close);
        display.setCurrent(this);
        setCommandListener(this);
        
    }
    
    
    public void initializeVideo(Player player){
        this.player = player;
        initializer = new Thread(this);
        initializer.start();
    }
    
    
    public void run(){
        
        try {
            parent.updateGauge();
            player.addPlayerListener(this);
            player.realize();
            parent.updateGauge();
            player.prefetch();
            parent.updateGauge();
            playVideo();
        }
        catch(IllegalStateException e) {
            Alert alert = new Alert("IOException thrown", e.getMessage(), null, AlertType.ERROR);
            display.setCurrent(alert);
        }
        catch(MediaException e) {
            Alert alert = new Alert("Media Exception", e.getMessage(), null, AlertType.ERROR);
            display.setCurrent(alert);
        }
        catch (Exception ioe) {
            Alert alert = new Alert("Exception thrown", ioe.getMessage(), null, AlertType.ERROR);
            display.setCurrent(alert);
        }
        catch(VirtualMachineError e) {
            Alert alert = new Alert("Error", e.getMessage(), null, AlertType.ERROR);
            display.setCurrent(alert);
        }
        
        
    }
    
    
    public void playVideo(){
        try {
            
            // Get the video control and set it to the current display.
            videoControl = (VideoControl)player.getControl("VideoControl");
            if (videoControl != null) {
                videoControl.initDisplayMode(videoControl.USE_DIRECT_VIDEO, this);
            }
            
            parent.updateGauge();
            
            int cHeight = this.getHeight();
            int cWidth = this.getWidth();
            videoControl.setDisplaySize(cWidth, cHeight);
            
            display.setCurrent(this);
            videoControl.setVisible(true);
            player.start();
            
            
        } catch (MediaException me) {
            Alert alert = new Alert("MediaException thrown", me.getMessage(), null, AlertType.ERROR);
            display.setCurrent(alert);
        }
        catch(Exception e)
        { Alert alert = new Alert("MediaException thrown", e.getMessage(), null, AlertType.ERROR);
          display.setCurrent(alert); }
    }
    
    
    /** Paints background color */
    public void paint(Graphics g){
        g.setColor(128, 128, 128);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
    
    
    public void playerUpdate(Player p, String event, Object eventData) {
        //add "Replay" option when video is finished
        if (event == PlayerListener.END_OF_MEDIA) {
            if (rePlay == null) {
                rePlay = new Command("re-play", Command.SCREEN, 1);
                addCommand(rePlay);
            }
        }
        
    }
    
    
    public void commandAction(Command c, Displayable s) {
        if(c == rePlay){
            try{
                player.start();
            } catch (MediaException me) {
                Alert alert = new Alert("MediaException thrown", me.getMessage(), null, AlertType.ERROR);
                display.setCurrent(alert);
            }
        }
        
        else if(c == close){
            player.close();
            if(parent.prevform.getClass().getName().compareTo("MMSPCClient.ShowConnectedServers")==0) {
                new ShowConnectedServers().init(true);
            }
            else {
                display.setCurrent(parent.prevform);
            }
            
            
        }
        
    }
    
    
}
