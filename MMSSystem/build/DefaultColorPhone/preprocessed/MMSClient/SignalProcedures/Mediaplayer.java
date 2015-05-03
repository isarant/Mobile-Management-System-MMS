

package MMSClient.SignalProcedures;

import MMSClient.ReadFile;
import MMSClient.Parameters;
import MMSClient.Global_Parameters;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.OutOfMemoryError;
import java.lang.InterruptedException;
import javax.microedition.lcdui.*;
import javax.microedition.media.*;
import javax.microedition.media.control.*;
import javax.microedition.media.protocol.*;
import MMSClient.ShowConnectedServers;

public class Mediaplayer extends Form implements Runnable, CommandListener, ItemStateListener, PlayerListener  {
    
    private String MediaFile;
    private  Display mydisplay;
    protected Form prevform;
    private static Global_Parameters gp;
    private final static Command CMD_EXIT = new Command("Exit", Command.EXIT, 1);
    private Player player;
    private ChoiceGroup voice;
    private ChoiceGroup loop;
    private TextField duration ;
    private ChoiceGroup curtime;
    private ChoiceGroup ContentType;
    private Gauge gauge;
    private static final int GAUGE_LEVELS = 4;
    private static final int GAUGE_MAX = 12;
    private Command exitCommand,closeCommand,replayCommand,continueCommand,stopCommand;
    private PlayerView playerView;
    private AudioPlayer audioPlayer;
    private TextField tf;
    private   Thread looping;
    private boolean bollloop;
    
    
    
    public Mediaplayer(String MediaFile,Display display,Form PrevForm) {
        super("MediaPlayer");
        gp=new Global_Parameters();
        mydisplay=display;
        prevform=PrevForm;
        this.MediaFile=MediaFile;
        player=CreatePlayer(this.MediaFile);
        if(player.getContentType().equals("video/mpeg")) {
            initVideo();
        }
        else {
            initMedia();
        }
    }
    
    
    
    public Mediaplayer(InputStream is,String MediaFile ,Display display,Form PrevForm) {
        super("MediaPlayer");
        mydisplay=display;
        prevform=PrevForm;
        this.MediaFile=MediaFile;
        String ext=MediaFile.substring(MediaFile.indexOf("."),MediaFile.length());
        String ct=null;
        if (ext.toLowerCase().compareTo(".wmv")==0 || ext.toLowerCase().compareTo(".avi")==0 || ext.toLowerCase().compareTo(".mpeg")==0 || ext.toLowerCase().compareTo(".mpg")==0 || ext.toLowerCase().compareTo(".mp4")==0) {
            ct = "video/mpeg";
        } else if (ext.toLowerCase().compareTo(".3gp")==0) {
            ct = "video/mpeg";
        }   else if (ext.toLowerCase().compareTo(".midi")==0 || ext.toLowerCase().compareTo(".mid")==0 || ext.toLowerCase().compareTo(".kar")==0) {
            ct = "audio/midi";
        } else if (ext.toLowerCase().compareTo(".mp3")==0) {
            ct = "audio/mpeg";
        }else if (ext.toLowerCase().compareTo(".wav")==0) {
            ct = "audio/x-wav";
        } else if (ext.toLowerCase().compareTo(".txt")==0) {
            ct = "audio/x-tone-seq";
        }
        player=CreatePlayer(is,ct);
        if(ct.equals("video/mpeg")) {
            initVideo();
        }
        else {
            initMedia();
        }
    }
    
    public Mediaplayer() {
        super("MediaPlayer");
        gp=new Global_Parameters();
        mydisplay=gp.Get_Display();
        prevform=gp.Get_Previous_Form();
        this.MediaFile=gp.Get_Parameter1_Str();
        player=CreatePlayer(MediaFile);
        if(player.getContentType().equals("video/mpeg")) {
            initVideo();
        }
        else {
            initMedia();
        }
    }
    
    private VolumeControl getVolumeControl() {
        if (player == null) {
            return null;
        }
        return (VolumeControl) player.getControl("VolumeControl");
    }
    
    private void initVideo() {
        
        gauge = new Gauge("Acquiring video", false, GAUGE_MAX, 0);
        gauge.setValue(0);
        append(gauge);
        VideoCanvas videoCanvas = new VideoCanvas(this,mydisplay);
        videoCanvas.initializeVideo(player);
    }
    
    private void initMedia() {
        exitCommand = new Command("Exit", Command.EXIT, 2);
        playerView = new PlayerView(this);
        audioPlayer = new AudioPlayer(this);
        audioPlayer.initializeAudio(player);
    }
    
    
    
    public void commandAction(Command c, Displayable displayable) {
        if(c==CMD_EXIT) {
            if(player!=null) {
                try {
                    player.stop();
                }
                catch(Exception e)
                {}
                
            }
            if(prevform.getClass().getName().compareTo("MMSClient.ShowConnectedServers")==0) {
                new ShowConnectedServers().init(true);
            }
            else {
                mydisplay.setCurrent(prevform);
            }
            
        }
        if(c==replayCommand) {
            audioPlayer.replay();
        }
        if(c==continueCommand) {
            audioPlayer.Continue();
        }
        if(c==stopCommand) {
            audioPlayer.stop();
        }
        
        if(c == closeCommand){
            bollloop=false;
            audioPlayer.close();
            if(prevform.getClass().getName().compareTo("MMSClient.ShowConnectedServers")==0) {
                new ShowConnectedServers().init(true);
            }
            else {
                mydisplay.setCurrent(prevform);
            }
        }
        
    }
    
    
    private Player CreatePlayer(String Filepath) {
        Player myplayer=null;
        try {
            myplayer=Manager.createPlayer(Filepath);
        }
        catch (MediaException pe)
        {addCommand(CMD_EXIT);showAlert("Error", pe.getMessage());}
        catch (IOException ioe)
        {addCommand(CMD_EXIT);showAlert("Error", ioe.getMessage());}
        return myplayer;
        
    }
    
    private Player CreatePlayer(DataSource ds) {
        
        Player myplayer=null;
        try {
            myplayer=Manager.createPlayer(ds);
        }
        catch (MediaException pe)
        {addCommand(CMD_EXIT);showAlert("Error", pe.getMessage());}
        catch (IOException ioe)
        {addCommand(CMD_EXIT);showAlert("Error", ioe.getMessage());}
        return myplayer;
        
    }
    
    private Player CreatePlayer(InputStream is,String type) {
        Player myplayer=null;
        try {
            myplayer=Manager.createPlayer(is,type);
        }
        catch (MediaException pe)
        {addCommand(CMD_EXIT);showAlert("Error", pe.getMessage());}
        catch (IOException ioe)
        {addCommand(CMD_EXIT);showAlert("Error", ioe.getMessage());}
        return myplayer;
        
    }
    
    
    public void updateGauge(){
        int current = gauge.getValue();
        current = (current + GAUGE_MAX/GAUGE_LEVELS);
        gauge.setValue(current);
    }
    
    
    
    
    public void playerUpdate(Player p, String event, Object eventData) {
        
        if (event == PlayerListener.STARTED) {
            if(closeCommand == null){
                closeCommand = new Command("close", Command.EXIT, 1);
                playerView.addCommand(closeCommand);
            }
            if (stopCommand == null){
                stopCommand = new Command("stop", Command.SCREEN, 3);
                playerView.addCommand(stopCommand);
                if(continueCommand!=null) {
                    playerView.removeCommand(continueCommand);
                    continueCommand=null;
                }
            }
            if(replayCommand!=null) {
                playerView.removeCommand(replayCommand);
                replayCommand=null;
            }
            if(looping==null) {
                looping = new Thread(this);
            }
            bollloop=true;
            looping.start();
        }
        else if (event == PlayerListener.VOLUME_CHANGED) {
            VolumeControl volumeControl = (VolumeControl)eventData;
            int currentLevel = volumeControl.getLevel();
            if(playerView.getVolumeIndicator() != currentLevel)
                playerView.setVolumeIndicator(currentLevel);
        }
        else if (event == PlayerListener.END_OF_MEDIA){
            bollloop=false;
            if (replayCommand == null){
                replayCommand = new Command("re-play", Command.SCREEN, 1);
                playerView.addCommand(replayCommand);
            }
            if(stopCommand!=null) {
                playerView.removeCommand(stopCommand);
                stopCommand=null;
            }
            if(continueCommand!=null) {
                playerView.removeCommand(continueCommand);
                continueCommand=null;
            }
        }
        else if (event == PlayerListener.STOPPED){
            bollloop=false;
            if (continueCommand == null){
                continueCommand = new Command("continue", Command.SCREEN, 2);
                playerView.addCommand(continueCommand);
                if(stopCommand!=null) {
                    playerView.removeCommand(stopCommand);
                    stopCommand=null;
                }
            }
            
        }
        if (event != PlayerListener.CLOSED) {
            mydisplay.setCurrent(playerView);
        }
    }
    
    
    public void itemStateChanged(Item item){
        if (item instanceof Gauge){
            Gauge volumeIndicator = (Gauge)item;
            int level = volumeIndicator.getValue();
            audioPlayer.setVolume(level);
        }
    }
    
    
    public void updateProgressGauge(){
        
    }
    
    
    public void showAlert(String title, String message){
        Alert alert = new Alert(title, message, null, AlertType.ERROR);
        mydisplay.setCurrent(alert);
    }
    
    
    public void run() {
        while(bollloop) {
            if(tf==null) {
                tf=new TextField("Duration : ",Long.toString(player.getMediaTime()/1000)+ " sec",15,TextField.UNEDITABLE);
                playerView.append(tf);
            }
            else {
                tf.setString(Long.toString(player.getMediaTime()/1000)+ " sec");
            }
            mydisplay.setCurrent(playerView);
            try {
                
                looping.sleep(500);
            }
            catch(InterruptedException e)
            {}
        }
        looping=null;
    }
    
    
}


