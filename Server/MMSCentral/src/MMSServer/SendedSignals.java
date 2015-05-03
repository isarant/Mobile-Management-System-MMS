

package MMSServer;

public class SendedSignals {
    
    public static MP[] mp;
    private static int SendedSignalssize=0;
    public SendedSignals()
    {
        
    }
    
    public MP Get_Signal(int NumOfSignal)
    {
        if(NumOfSignal>=0 && NumOfSignal<=SendedSignalssize)
        {
            return mp[NumOfSignal];
        }
        else
        {
            return null;
        }
    }
    
    public void Add_Signal(MP mp1)
    {
       MP[] helpmp=new MP[SendedSignalssize+1];
        if(SendedSignalssize>0)
       {
            System.arraycopy(mp, 0, helpmp, 0, mp.length);
       } 
       int a=helpmp.length-1;
       helpmp[a]=mp1;
       mp=new MP[helpmp.length];
       System.arraycopy(helpmp , 0, mp, 0, helpmp.length); 
       SendedSignalssize=mp.length;
    }
    
    public void Delete_Signal(int NumOfSignal)
    {
        if(mp.length!=1)
        {
            MP[] helpmp=new MP[mp.length-1];
            if(NumOfSignal>0)
            {
                System.arraycopy(mp, 0, helpmp, 0, NumOfSignal);
            }
            System.arraycopy(mp, NumOfSignal+1, helpmp, NumOfSignal, mp.length-(NumOfSignal+1));
            mp=new MP[helpmp.length];
            System.arraycopy(helpmp , 0, mp, 0, helpmp.length); 
            SendedSignalssize=mp.length;
        }
        else
        {
            SendedSignalssize=0;
        }
    }
    public int Get_SizeOfAnsSignal()
    {
        return SendedSignalssize;
    }
    
}
