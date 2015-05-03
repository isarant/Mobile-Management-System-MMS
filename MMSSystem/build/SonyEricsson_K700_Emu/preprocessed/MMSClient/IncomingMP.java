

package MMSClient;

import javax.microedition.midlet.*;

//Control the list of incoming signals
public class IncomingMP {
    
    private  MP[] mp ;
    private  int IncomingMPsize=0;
    public IncomingMP() {
        
    }
    
    //Clear list
    public synchronized void Clear_All() {
        if(mp!=null) {
            for(int z=0;z<mp.length;z++) {
                mp[z]=null;
            }
            mp=null;
            IncomingMPsize=0;
        }
        
    }
    
    //Return a specified signal from incoming list
    public synchronized MP Get_IncomingMP(int NumOfIncomingMP) {
        if(NumOfIncomingMP>=0 && NumOfIncomingMP<=IncomingMPsize) {
            return mp[NumOfIncomingMP];
        }
        else {
            return null;
        }
    }
    
    //Add a signal to incoming list
    public synchronized void Add_IncomingMP(MP mpsignal) {
        if(IncomingMPsize>9)
        {Delete_IncomingMP(0);}
        MP[] helpmp=new MP[IncomingMPsize+1];
        if(IncomingMPsize>0) {
            System.arraycopy(mp, 0, helpmp, 0, mp.length);
        }
        int a=helpmp.length-1;
        helpmp[a]=mpsignal;
        mp=new MP[helpmp.length];
        System.arraycopy(helpmp , 0, mp, 0, helpmp.length);
        IncomingMPsize=mp.length;
    }
    
    //Delete a specified signal from incming list
    public synchronized void Delete_IncomingMP(int NumOfIncomingMP) {
        if(mp.length!=1) {
            MP[] helpmp=new MP[mp.length-1];
            if(NumOfIncomingMP>0) {
                System.arraycopy(mp, 0, helpmp, 0, NumOfIncomingMP);
            }
            else {
                System.arraycopy(mp, NumOfIncomingMP+1, helpmp, NumOfIncomingMP, mp.length-1);
            }
            mp=new MP[helpmp.length];
            System.arraycopy(helpmp , 0, mp, 0, helpmp.length);
            IncomingMPsize=mp.length;
        }
        else {
            IncomingMPsize=0;
        }
    }
    
    //Return the number of signals in list
    public int Get_SizeOfIncomingMP() {
        return IncomingMPsize;
    }
    
}
