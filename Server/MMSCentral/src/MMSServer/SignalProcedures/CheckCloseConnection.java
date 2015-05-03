
package MMSServer.SignalProcedures;

import java.util.Date;
import MMSServer.*;


public class CheckCloseConnection extends ParentSignalProcess {
  public CheckCloseConnection()
  {
     Session session =new Session();
     for (int x=0;x<session.Get_SizeOfSession();x++)
         {
            if(mp.Get_Sender()==session.Get_Session(x).Get_UserID())
            {
                if(mp.Get_Scenario()==session.Get_Session(x).Get_Scenario())
                {
                     session.Delete_Session(x);
                     sc.closeConnection(Connection_Number);
                     session.refreshSessionFile(sc);
                     deleteAnsSignal(mp.Get_Sender());
                }
            }
         }
   }
  
  private void deleteAnsSignal(byte Sender)
    {
       AnsSignal as=new AnsSignal();
        for(int z=0;z<as.Get_SizeOfAnsSignal();z++)
        {
            if(as.Get_Signal(z).Get_SenderID()==Sender)
            {   
            as.Delete_Signal(z);
            z=z-1;
            }
        }
    } 
}
