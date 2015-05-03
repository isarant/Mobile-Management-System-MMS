
package MMSServer.SignalProcedures;


import MMSServer.AnsSignal;

public class ResetIAmAlive extends ParentSignalProcess{
    
    /** Creates a new instance of ResetIAmAlive */
    public ResetIAmAlive() {
         super();
         init();
    }
    private synchronized void init()
    {
         AnsSignal as=new AnsSignal();

            for(int x=0;x<as.Get_SizeOfAnsSignal();x++)
            {
                if(as.Get_Signal(x).Get_SenderID()==mp.Get_Sender() && as.Get_Signal(x).Get_Scenario()==mp.Get_Scenario() )
                {
                   if(mp.Get_Date_Time()>as.Get_Signal(x).Get_Date_Time())
                   {
                       as.Delete_Signal(x);
                       x--;
                   }
                }
            }
    }
    
}
