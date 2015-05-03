

package MMSClient.SignalProcedures;

import javax.microedition.media.*;
import javax.microedition.media.control.*;
import java.io.*;


/** Acquires the audio content and plays it. The "Model" class in the MVC pattern */
public class AudioPlayer implements Runnable {
    
    private Mediaplayer controller;
    private Player player;
    private VolumeControl volumeControl;
    private String url;
    
    
    public AudioPlayer(Mediaplayer controller){
        this.controller = controller;
    }
    
    
    public void initializeAudio(Player player){
        this.player = player;
        Thread initializer = new Thread(this);
        initializer.start();
    }
    
    
    public void run(){
        try {
            controller.updateProgressGauge();
            player.addPlayerListener(controller);
            player.realize();
            controller.updateProgressGauge();
            player.prefetch();
            controller.updateProgressGauge();
            volumeControl = (VolumeControl)player.getControl("VolumeControl");
            volumeControl.setLevel(50);
            controller.updateProgressGauge();
            player.start();
        } catch (Exception ioe) {
            controller.showAlert("Unable to connect to resource", ioe.getMessage());
        }
    }
    
    
    
    public void replay(){
        try{
            player.start();
        } catch (MediaException me) {
            controller.showAlert("MediaException thrown", me.getMessage());
        }
    }
    
    public void Continue(){
        try{
            player.start();
        } catch (MediaException me) {
            controller.showAlert("MediaException thrown", me.getMessage());
        }
    }
    public void stop(){
        try{
            player.stop();
        } catch (MediaException me) {
            controller.showAlert("MediaException thrown", me.getMessage());
        }
    }
    public void setVolume(int level){
        volumeControl.setLevel(level);
    }
    
    
    public void close(){
        player.close();
        controller = null;
        url = null;
    }
    
    
}
