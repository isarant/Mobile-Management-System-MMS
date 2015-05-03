
package mmspcclient;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Parameters {
    private static Parameterstype[] partype;
    private static int parsize=0;
    public Parameters(int parsize) {
        this.parsize=parsize;
        partype=null;
        partype=new Parameterstype[parsize];
    }
    public Parameters() {
        
    }
    public Parameterstype Get_Parameter(int NumOfpar) {
        if(NumOfpar>=0 && NumOfpar<=parsize) {
            return partype[NumOfpar];
        }
        else {
            return null;
        }
    }
    public void Set_Parameter(int NumOfpar,String par1,String par2) {
        if(NumOfpar>=0 && NumOfpar<=parsize) {
            
            partype[NumOfpar]=new Parameterstype();
            if(par1.compareTo("")!=0) {
                partype[NumOfpar].Set_Parameter1(par1);
            }
            if(par2.compareTo("")!=0) {
                partype[NumOfpar].Set_Parameter2(par2);
            }
        }
    }
    
    public void Set_Parameter(int NumOfpar,byte[] par1,byte[] par2) {
        if(NumOfpar>=0 && NumOfpar<=parsize) {
            partype[NumOfpar]=new Parameterstype();
            try {
                if(par1.length>0) {
                    partype[NumOfpar].Set_Parameter1(par1);
                }
            }
            catch(Exception e)
            {}
            try {
                if(par2.length>0) {
                    partype[NumOfpar].Set_Parameter2(par2);
                }
            }
            catch(Exception e)
            {}
        }
        
        
    }
    public void Clear_Parameter(int NumOfpar) {
        if(NumOfpar>=0 && NumOfpar<=parsize) {
            partype[NumOfpar]=null;
        }
    }
    public void Clear_Parameter_All() {
        for(int x=0;x<partype.length;x++) {
            partype[x]=null;
        }
    }
    
    public class Parameterstype {
        private  byte[] Parameter1;
        private  byte[] Parameter2;
        public Parameterstype() {
            
        }
        public void Set_Parameter1(String Parameter) {
            Parameter1=Parameter.getBytes();
        }
        public void Set_Parameter2(String Parameter) {
            Parameter2=Parameter.getBytes();
        }
        public void Set_Parameter1(byte[] Parameter) {
            Parameter1=Parameter;
        }
        public void Set_Parameter2(byte[] Parameter) {
            Parameter2=Parameter;
        }
        
        public byte[] Ge_Parameter1() {
            return Parameter1;
        }
        public byte[] Ge_Parameter2() {
            return Parameter2;
        }
        public String Ge_Parameter1_String(){
            ByteArrayOutputStream BAOS=new ByteArrayOutputStream();
            try {
                BAOS.write(Parameter1);
            }
            catch(IOException e)
            {}
            return BAOS.toString();
        }
        public String Ge_Parameter2_String(){
            ByteArrayOutputStream BAOS=new ByteArrayOutputStream();
            try {
                BAOS.write(Parameter2);
            }
            catch(IOException e)
            {}
            return BAOS.toString();
        }
    }
}
