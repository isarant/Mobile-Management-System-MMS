

package MMSClient;

//This class operate a list of Answer Signals
//Answer signal are the sended signals which wait for answare
public class AnsSignal {
    
    public static AnsSignalsFile[] Anssignal;
    private static int AnsSignalsize=0;
    public AnsSignal() {
        
    }
    
    //Return a specified  answer signal from list
    public AnsSignalsFile Get_Signal(int NumOfSignal) {
        if(NumOfSignal>=0 && NumOfSignal<=AnsSignalsize) {
            return Anssignal[NumOfSignal];
        }
        else {
            return null;
        }
    }
    
    //Add an answer signal in list
    public void Add_Signal(AnsSignalsFile AnsSignalsfile) {
        AnsSignalsFile[] helpAnssignal=new AnsSignalsFile[AnsSignalsize+1];
        if(AnsSignalsize>0) {
            System.arraycopy(Anssignal, 0, helpAnssignal, 0, Anssignal.length);
        }
        int a=helpAnssignal.length-1;
        helpAnssignal[a]=AnsSignalsfile;
        Anssignal=new AnsSignalsFile[helpAnssignal.length];
        System.arraycopy(helpAnssignal , 0, Anssignal, 0, helpAnssignal.length);
        AnsSignalsize=Anssignal.length;
    }
    
    //Delete an answer signal from list
    public void Delete_Signal(int NumOfSignal) {
        if(Anssignal.length!=1) {
            AnsSignalsFile[] helpAnssignal=new AnsSignalsFile[Anssignal.length-1];
            if(NumOfSignal>0) {
                System.arraycopy(Anssignal, 0, helpAnssignal, 0, NumOfSignal);
            }
            System.arraycopy(Anssignal, NumOfSignal+1, helpAnssignal, NumOfSignal, Anssignal.length-(NumOfSignal+1));
            Anssignal=new AnsSignalsFile[helpAnssignal.length];
            System.arraycopy(helpAnssignal , 0, Anssignal, 0, helpAnssignal.length);
            AnsSignalsize=Anssignal.length;
        }
        else {
            AnsSignalsize=0;
        }
    }
    
    //Return the number of answer signal in list
    public int Get_SizeOfAnsSignal() {
        return AnsSignalsize;
    }
}