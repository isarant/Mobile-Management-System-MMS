
package MMSServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.bouncycastle.crypto.StreamCipher;
//import org.bouncycastle.crypto.*;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.bouncycastle.crypto.params.KeyParameter;

//This class encrypt and descript the data
public class ServerCrypt {
    private StreamCipher mOutCipher, mInCipher;
    private static  Session session;
    private byte ClientID;
    private int keynum=0; 
     
   //Find Key number for specification cliend and initialaze class
    public ServerCrypt(byte ClientID,int Scenario)
    {
        boolean ok=false;
        session =new Session();
        mOutCipher = new RC4Engine();
        mInCipher = new RC4Engine();
        this.ClientID=ClientID;
        session =new Session();
        for(int x=0;x<session.Get_SizeOfSession();x++)
        {
           if(session.Get_Session(x).Get_UserID()==ClientID && session.Get_Session(x).Get_Scenario()==Scenario)
           {
              keynum=session.Get_Session(x).Get_Keynum();
               ok=true;
                break;
            }
        }
        if(!ok)
        {
           PaswdFile pf=new PaswdFile();
           ReadFile rf=new ReadFile("./System/paswdf.mms",pf.Get_Size());
           for(int x=1;x<=rf.NumOfRecords();x++)
           {
               pf.Set_PaswdFile_Byte(rf.Read(x));
               if(pf.Get_ClientID()==ClientID && pf.Get_Scenario()==Scenario )
               {
                   keynum=pf.Get_KeyNum();
                   break;
               }
           }
            
        }
     }
   
    //Convert string from key number as an array of bytes for descipt
    private synchronized byte[] getInKey() {
       ByteArrayOutputStream key=new ByteArrayOutputStream();
       session =new Session();
       try
       {
            key.write(Integer.toString(keynum).getBytes());
       }
       catch(IOException ioe)
       {}
       return key.toByteArray();
   }

   //Convert string from key number as an array of bytes for encrypt
    private synchronized  byte[] getOutKey() {
       ByteArrayOutputStream key=new ByteArrayOutputStream();
      
       try
       {
             key.write(Integer.toString(keynum).getBytes());
       }
       catch(IOException ioe)
      {System.out.println("error");}
      // System.out.println("encrypt key" + key.toString());
       return key.toByteArray();
    }  
    
    //Convert a string as an array of bytes for password file encrypt
    private byte[] getOutKeyLogon() {
         
        return "Outgoing MIDlet key".getBytes();
    }  
    
    //Recieve data and return encrypted data 
    public synchronized byte[] EnCryptData(byte[] plaindata )
    {
        byte[] outKey = getOutKey();
        mOutCipher.init(true, new KeyParameter(outKey));
        byte[] cipherdata = new byte[plaindata.length];
        mOutCipher.processBytes(plaindata, 0, plaindata.length, cipherdata, 0);
        return cipherdata;
    }

    //Recieve encrypted data and return descripted data
    public synchronized byte[] DeCryptData(byte[] cipherdata )
    {
        byte[] inKey = getInKey();
        mInCipher.init(false, new KeyParameter(inKey));
        byte[] deciphered = new byte[cipherdata.length];
        mInCipher.processBytes(cipherdata, 0, cipherdata.length, deciphered, 0);
        return deciphered;
    }
    
    //Recieve encrypted password and return descripted password
    public byte[] EnCryptLogon(byte[] passwd)
    {
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