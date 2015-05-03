package MMSClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.bouncycastle.crypto.params.KeyParameter;

//This class encrypt and descript the data
public class ClientCrypt {
    private StreamCipher mOutCipher, mInCipher;
    private static  ConnectedServer[]  myConnectedServer;
    private byte ServerID;
    private int keynum=0;
    
    public ClientCrypt() {
        boolean ok=false;
        SelectServer ss=new SelectServer();
        myConnectedServer=ss.Get_ConnectedServer();
        mOutCipher = new RC4Engine();
        mInCipher = new RC4Engine();
    }
    
    //Find Key number for specification server and initialaze class
    public ClientCrypt(byte ServerID , int Scenario) {
        boolean ok=false;
        SelectServer ss=new SelectServer();
        myConnectedServer=ss.Get_ConnectedServer();
        mOutCipher = new RC4Engine();
        mInCipher = new RC4Engine();
        this.ServerID=ServerID;
        if(myConnectedServer!=null) {
            for(int x=0;x<myConnectedServer.length;x++) {
                try {
                    if(myConnectedServer[x].Get_Enable()) {
                        if(myConnectedServer[x].Get_ServerID()==ServerID && myConnectedServer[x].Get_Scenarion()==Scenario) {
                            keynum=myConnectedServer[x].GetKeynum();
                            ok=true;
                            break;
                        }
                    }
                }
                catch(Exception e)
                {}
            }
        }
        if(!ok) {
            ServersFile sf=new ServersFile();
            ReadFile rf=new ReadFile("SerFile.mms");
            for(int x=1;x<=rf.NumOfRecords();x++) {
                sf.Set_ServerFile_Byte(rf.Read(x));
                if(ServerID==sf.Get_ServerID() && Scenario== sf.Get_Scenario()) {
                    keynum=sf.Get_keyNum();
                    break;
                }
            }
            
        }
    }
    
    //Convert string from key number as an array of bytes for descipt
    private synchronized byte[] getInKey() {
        ByteArrayOutputStream key=new ByteArrayOutputStream();
        try {
            key.write(Integer.toString(keynum).getBytes());
        }
        catch(IOException ioe)
        {}
        return key.toByteArray();
    }
    
    //Convert string from key number as an array of bytes for encrypt
    private synchronized byte[] getOutKey() {
        ByteArrayOutputStream key=new ByteArrayOutputStream();
        try {
            key.write(Integer.toString(keynum).getBytes());
        }
        catch(IOException ioe)
        {}
        return key.toByteArray();
    }
    
    //Convert a string as an array of bytes for password file encrypt
    private byte[] getOutKeyLogon() {
        
        return "Outgoing MIDlet key".getBytes();
    }
    
    //Recieve data and return encrypted data
    public synchronized byte[] EnCryptData(byte[] plaindata ) {
        byte[] outKey = getOutKey();
        mOutCipher.init(true, new KeyParameter(outKey));
        byte[] cipherdata = new byte[plaindata.length];
        mOutCipher.processBytes(plaindata, 0, plaindata.length, cipherdata, 0);
        return cipherdata;
    }
    
    //Recieve encrypted data and return descripted data
    public synchronized byte[] DeCryptData(byte[] cipherdata ) {
        byte[] inKey = getInKey();
        mInCipher.init(false, new KeyParameter(inKey));
        byte[] deciphered = new byte[cipherdata.length];
        mInCipher.processBytes(cipherdata, 0, cipherdata.length, deciphered, 0);
        return deciphered;
    }
    
    //Recieve encrypted password and return descripted password
    public byte[] EnCryptLogon(byte[] passwd) {
        byte[] outKey = getOutKeyLogon();
        mOutCipher.init(true, new KeyParameter(outKey));
        byte[] cipherdata = new byte[passwd.length];
        mOutCipher.processBytes(passwd, 0, passwd.length, cipherdata, 0);
        return cipherdata;
    }
    
    //Convert integer from key number as an array of bytes for encrypt
    private byte[] Get_keynum_Byte(int key){
        byte b[] = new byte[4];
        int i, shift;
        for(i = 0, shift = 24; i < 4; i++, shift -= 8)
            b[i] = (byte)(0xFF & (key >> shift));
        return b;
    }
}