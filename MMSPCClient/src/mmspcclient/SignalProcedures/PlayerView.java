
package MMSPCClient.SignalProcedures;

import javax.microedition.lcdui.*;


public class PlayerView extends Form {
    private Gauge volumeIndicator;
    
    public PlayerView(Mediaplayer controller) {
        super("Volume control");
        volumeIndicator = new Gauge("Volume control", true, 100, 50);
        append(volumeIndicator);
        setItemStateListener(controller);
        setCommandListener(controller);
    }
    
    
    public void setVolumeIndicator(int level) {
        volumeIndicator.setValue(level);
    }
    
    
    public int getVolumeIndicator() {
        return volumeIndicator.getValue();
    }
}
