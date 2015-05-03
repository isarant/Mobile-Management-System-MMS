
package MMSClient;

import javax.microedition.midlet.*;
import java.io.IOException;
import MMSClient.MP;

class SendSoc extends Thread {
    private byte[] data;
    private SocConnect sc;
    private ClientCrypt clientcrypt;
    
    public SendSoc(byte[] data,SocConnect socconnect) {
        try {
            sc=socconnect;
            this.data=data;
        }
        catch(Exception e)
        {}
        start();
    }
    public synchronized void run() {
        
        while(!sc.enambled) {
            try {
                
                this.sleep(1000);
            }
            catch(Exception e)
            {}
            if(sc.promblem)
            {break;}
        }
        if(!sc.promblem) {
            try {
                if(data!=null) {
                    MP mp= new MP();
                    mp.Set_MMP_Bytes(data);
                    clientcrypt=new ClientCrypt(mp.Get_Receiver(),mp.Get_Scenario());
                    if(mp.Get_Data1()!=null) {
                        mp.Set_Data1(clientcrypt.EnCryptData(mp.Get_Data1()));
                    }
                    if(mp.Get_Data2()!=null) {
                        mp.Set_Data2(clientcrypt.EnCryptData(mp.Get_Data2()));
                    }
                    sc.out.write(mp.Get_MMP_Byte());
                    sc.out.flush();
                    //       System.out.println("SEND: " + data.length);
                }
                
            }
            catch(IOException e)
            {}
        }
    }
    public void stop() {
        
    }
    
}

