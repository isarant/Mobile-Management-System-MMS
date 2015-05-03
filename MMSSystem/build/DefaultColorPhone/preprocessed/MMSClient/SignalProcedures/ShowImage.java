
package MMSClient.SignalProcedures;

import MMSClient.ReadFile;
import MMSClient.Parameters;
import MMSClient.Global_Parameters;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.OutOfMemoryError;
import javax.microedition.lcdui.*;
import MMSClient.ShowConnectedServers;
public class ShowImage extends Canvas implements CommandListener, ItemStateListener {
    
    private String ImageFile;
    private  Display mydisplay;
    private Form prevform;
    private static Global_Parameters gp;
    private byte[] ImageData;
    int mult=1;
    private final static Command CMD_EXIT = new Command("Exit", Command.EXIT, 1);
    private final static Command CMD_ZOOM_ADD = new Command("Zoom +", Command.SCREEN, 1);
    private final static Command CMD_ZOOM_MINUS = new Command("Zoom -", Command.SCREEN, 2);
    private Image image,helpimage;
    int x,y;
    
    public ShowImage(String ImageFile,Display display,Form PrevForm) {
        //  super("ImageViiewer");
        if(ImageFile.length()>0) {
            gp=new Global_Parameters();
            mydisplay=display;
            prevform=PrevForm;
            this.ImageFile=ImageFile;
            init();
            ViewImage(this.ImageFile);
        }
    }
    public ShowImage(byte[] ImageData,Display display,Form PrevForm) {
        //  super("ImageViiewer");
        if(ImageData.length>0) {
            gp=new Global_Parameters();
            mydisplay=display;
            prevform=PrevForm;
            this.ImageData=ImageData;
            init();
            ViewImage(ImageData);
        }
    }
    
    public ShowImage() {
        // super("ImageViiewer");
        gp=new Global_Parameters();
        mydisplay=gp.Get_Display();
        prevform=gp.Get_Previous_Form();
        this.ImageFile=gp.Get_Parameter1_Str();
        if(ImageFile.length()>0) {
            init();
            ViewImage(this.ImageFile);
        }
    }
    
    private void init() {
        try {
            setCommandListener(this);
            //   setItemStateListener(this);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        // addCommand(CMD_ZOOM_ADD);
        //  addCommand(CMD_ZOOM_MINUS);
        addCommand(CMD_EXIT);
        mydisplay.setCurrent(this);
    }
    
    private void ViewImage(String Filename) {
        
        try {
            Image helpimage=Image.createImage(Filename);
            image=Image.createImage(helpimage);
            mydisplay.setCurrent(this);
        }
        catch(Exception e)
        {  mydisplay.setCurrent(prevform);}
        
    }
    
    private void ViewImage(byte[] imagedata) {
        
        try {
            helpimage=Image.createImage(imagedata,0,imagedata.length);
            image=Image.createImage(helpimage);
            repaint();
            mydisplay.setCurrent(this);
        }
        catch(Exception e)
        {mydisplay.setCurrent(prevform);}
    }
    
    public void commandAction(Command command, Displayable displayable) {
        if(command==CMD_EXIT) {
            if(prevform.getClass().getName().compareTo("MMSClient.ShowConnectedServers")==0) {
                new ShowConnectedServers().init(true);
            }
            else {
                mydisplay.setCurrent(prevform);
            }
        }
        if(command==CMD_ZOOM_ADD) {
            try {
                //Image helpimage=Image.createImage(image);
                mult++;
                //image=Image.createImage(helpimage,0,0,helpimage.getWidth()*mult,helpimage.getHeight()*mult,0);
                repaint();
            }catch(Exception e)
            {}
        }
        if(command==CMD_ZOOM_MINUS) {
            try {
                Image helpimage=Image.createImage(image);
                if(mult>2) {
                    mult--;
                }
                repaint();
            }catch(Exception e)
            {}
            
        }
        
    }
    
    public void itemStateChanged(Item item) {
    }
    
    protected void paint(javax.microedition.lcdui.Graphics graphics) {
        graphics.drawImage(image, 0, 0, Graphics.TOP|Graphics.LEFT);
    }
    
    protected  void keyReleased(int keyCode) {
        
        try {
            if(keyCode==-3) {
                if(x>10) {
                    x=x-10;
                }
            }
            if(keyCode==-1) {
                if(y>10) {
                    y=y-10;
                }
            }
            if(keyCode==-4) {
                if(getWidth()>image.getWidth()-x-10) {
                    x=x+10;
                }
            }
            if(keyCode==-2) {
                if(getHeight()>image.getHeight()-y-10) {
                    y=y+10;
                }
            }
            image=Image.createImage(helpimage,x,y,this.getWidth(),this.getHeight(),0);
            repaint();
        }
        catch(Exception e)
        {}
    }
}
