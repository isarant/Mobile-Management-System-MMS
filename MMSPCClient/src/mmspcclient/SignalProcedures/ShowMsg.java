
package MMSPCClient.SignalProcedures;

import javax.microedition.midlet.*;
import MMSPCClient.Signals;
import MMSPCClient.ClientID;
import javax.microedition.lcdui.Form;
import MMSPCClient.ShowConnectedServers;

public class ShowMsg extends ParentSignalProcess{
    public ShowMsg() {
        Form form=new ShowConnectedServers();
        ClientID cl=new ClientID();
        Signals singnals=new Signals(cl.GetClientID());
        byte[] data=null;
        byte mytype=mp.Get_Signal_Type();
        mytype++;
        sc.Send(singnals.GeneralSignal(mp.Get_Sender(),mp.Get_Scenario(),mytype,mp.Get_Signal_Command(),mp.Get_Priority(),mp.Get_Data1(),data));
        ShowMsgForm showmsg=new ShowMsgForm(mp.Get_Data1(),mp.Get_Data2(),display,form) ;
        
    }
    
}
