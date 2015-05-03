

package MMSClient;

import java.lang.String;
import javax.microedition.rms.*;

//Delete a file
public class DeleteFile {
    private RecordStore rs;
    public  DeleteFile(String Filename) {
        
        try {
            rs.deleteRecordStore(Filename);
        }
        catch(RecordStoreException e)
        {}
        catch(Exception e)
        {}
    }
    
}
