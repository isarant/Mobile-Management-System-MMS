
package MMSClient;

import java.lang.String;
import javax.microedition.rms.*;



public  class ReadFile {
    private RecordStore rs;
    private int varNumOfRecords;
    private RecordEnumeration re;
    
    public ReadFile(String Filename) {
        try {
            rs = RecordStore.openRecordStore(Filename,false);
            re=rs.enumerateRecords(null,null,true);
            varNumOfRecords=re.numRecords();
        }
        catch(RecordStoreException e)
        {varNumOfRecords=0;}
        catch(Exception e)
        {e.printStackTrace();}
    }
    
    public synchronized byte[] Read(int reccount) {
        byte[] data=null;
        try {
            re.rebuild();
            if(reccount<=re.numRecords()) {
                int recid=0;
                for(int x=1;x<=reccount;x++) {
                    recid=re.nextRecordId();
                }
                if(recid>0) {
                    data=rs.getRecord(recid);
                    varNumOfRecords=re.numRecords();
                }
            }
        }
        catch(RecordStoreException e)
        {}
        catch(Exception e)
        {}
        return data;
    }
    
    public synchronized byte[] Read() {
        byte[] data=null;
        try {
            data=re.nextRecord();
            varNumOfRecords=re.numRecords();
        }
        catch(RecordStoreException e)
        {}
        catch(Exception e)
        {}
        return data;
    }
    
    public int NumOfRecords(){
        return varNumOfRecords;
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
