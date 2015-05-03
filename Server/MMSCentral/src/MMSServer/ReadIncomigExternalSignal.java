
package MMSServer;

import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.lang.InterruptedException;
import java.io.EOFException;


public class ReadIncomigExternalSignal extends Thread {
    
    private boolean ReadIncomigExternalSignalEnabled=true;
    private RandomAccessFile raf;
    private MP mp;
    private Signals signals;
    private SocConnect sc;
    private SessionFile session;
    
    public ReadIncomigExternalSignal(SocConnect sc,SessionFile session) {
        this.sc=sc;
        this.session=session;
        this.start();
    }
    
    public void Change_Connection_Number(SessionFile session) {
        this.session=session;
    }
    
    public void ReadIncomigExternalSignal_Close() {
        ReadIncomigExternalSignalEnabled=false;
    }
    
    public synchronized void run() {
        try {
            sleep(300);
        }
        catch(InterruptedException e)
        {}
        
        while(ReadIncomigExternalSignalEnabled) {
            /*check if user id session is enabled */
            if(session.Get_Enable()==0) {
                ReadIncomigExternalSignalEnabled=true;
                try {
                    
                    ReadAndSendSignal();
                }
                catch(Exception e) {
                    e.printStackTrace();
                    // ReadIncomigExternalSignalEnabled=false;
                }
            }
            try {
                sleep(20000);
            }
            catch(InterruptedException e)
            {e.printStackTrace();}
        }
        
    }
    
    
    private synchronized void ReadAndSendSignal() {
        
        int maxlen=0;
        int index=0;
        
        try {
            raf=new RandomAccessFile("./System/ExtSignalFile.mms","rw");
            maxlen=(int)raf.length();
        }
        catch(IOException e) {
            ReadIncomigExternalSignalEnabled=false;
            maxlen=0;
        }
        synchronized(raf) {
            while(index<maxlen) {
                try {
                    mp=new MP();
                    raf.seek(index);
                    mp.Set_Sender(raf.readByte());
                    byte varReciever=raf.readByte();
                    mp.Set_Receiver(varReciever);
                    int varScenario=raf.readInt();
                    mp.Set_Scenario(varScenario);
                    mp.Set_Signal_Type(raf.readByte());
                    mp.Set_Signal_Command(raf.readInt());
                    mp.Set_Priority(raf.readByte());
                    mp.Set_Date_Time(raf.readLong());
                    int data1size=0;
                    data1size=raf.readInt();
                    if(data1size>0) {
                        byte[] data1=new byte[data1size];
                        raf.read(data1);
                        mp.Set_Data1(data1);
                    }
                    int data2size=0;
                    data2size=raf.readInt();
                    if(data2size>0) {
                        byte[] data2=new byte[data2size];
                        raf.read(data2);
                        mp.Set_Data2(data2);
                    }
                    /* check if there is and signal for this user id*/
                    boolean ok=true;
                    boolean ok2=false;
                    AnsSignal as=new AnsSignal();
                    for(int x=0;x<as.Get_SizeOfAnsSignal();x++) {
                        if(as.Get_Signal(x).Get_SenderID()==mp.Get_Receiver() && as.Get_Signal(x).Get_Scenario()==mp.Get_Scenario() ) {
                            ok=false;
                            break;
                        }
                    }
                    /* check if there active user id*/
                    if(mp.Get_Receiver()==session.Get_UserID()) {
                        ok2=true;
                    }
                    if(ok && ok2) {
                        if(varReciever==session.Get_UserID() && varScenario==session.Get_Scenario()) {
                            signals=new Signals(mp.Get_Sender());
                            sc.Send(signals.GeneralSignal(mp.Get_Receiver() ,mp.Get_Scenario(),mp.Get_Signal_Type(),mp.Get_Signal_Command(),mp.Get_Priority(),mp.Get_Data1(),mp.Get_Data2()),session.Get_SocConnectID());
                            ByteArrayOutputStream baos=new ByteArrayOutputStream();
                            int mpsize=mp.Get_MMP_Size();
                            raf.seek(0);
                            if(index>0) {
                                byte[]  vardata1=new byte[index];
                                raf.read(vardata1);
                                baos.write(vardata1);
                            }
                            byte[]  vardata2=new byte[(int)raf.length()-(mpsize+index)];
                            raf.seek(index+mpsize);
                            raf.read(vardata2);
                            baos.write(vardata2);
                            raf.setLength(0);
                            raf.write(baos.toByteArray());
                            //index=index+mpsize;
                            index=0;
                        }
                        else {
                            index=index+mp.Get_MMP_Size();
                        }
                    }
                    else {
                        index=index+mp.Get_MMP_Size();
                    }
                }
                catch(EOFException eof)
                {break;}
                catch(IOException ioe)
                {break;}
            }
            try {
                raf.close();
            }
            catch (IOException e) {//e.printStackTrace();
            }
            catch(Exception e)
            {e.printStackTrace();}
        }
    }
}
