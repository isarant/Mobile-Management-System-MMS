
package MMSClient;

import java.lang.String;
import javax.microedition.rms.*;


public class WriteFile {
    private RecordStore rs;
    private RecordEnumeration re;
    
    public WriteFile(String Filename) {
        try {
            rs = RecordStore.openRecordStore(Filename,true);
            re=rs.enumerateRecords(null,null,true);
        }
        catch(RecordStoreException e)
        {e.printStackTrace();}
        catch(Exception e)
        {}
    }
    
    public WriteFile() {
        
    }
    
    public synchronized void Write(byte[] data) {
        try {
            rs.addRecord(data,0,data.length);
            re.rebuild();
        }
        catch(RecordStoreException e)
        {e.printStackTrace(); }
        catch(Exception e)
        {e.printStackTrace();}
    }
    
    public synchronized void Edit(byte[] data,int reccount) {
        try {
            
            re.rebuild();
            if(reccount<=re.numRecords()) {
                int recid=0;
                for(int x=1;x<=reccount;x++) {
                    recid=re.nextRecordId();
                }
                if(recid>0) {
                    rs.setRecord(recid,data,0,data.length);
                    re.rebuild();
                }
            }
        }
        catch(RecordStoreException e)
        {e.printStackTrace(); }
        catch(Exception e)
        {e.printStackTrace();}
    }
    
    
    
    public synchronized void Delete(int reccount) {
        byte[] data=null;
        try {
            re.rebuild();
            if(reccount<=re.numRecords()) {
                int recid=0;
                for(int x=1;x<=reccount;x++) {
                    recid=re.nextRecordId();
                }
                if(recid>0) {
                    rs.deleteRecord(recid);
                    re.rebuild();
                }
            }
        }
        catch(RecordStoreException e)
        {e.printStackTrace();}
        catch(Exception e)
        {}
    }
    
    public synchronized void Close(){
        try {
            re.destroy();
            rs.closeRecordStore();
        }
        catch (RecordStoreException e)
        {}
        catch(Exception e)
        {}
    }
}
