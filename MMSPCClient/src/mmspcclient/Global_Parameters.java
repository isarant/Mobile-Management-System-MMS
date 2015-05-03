package mmspcclient;


public class Global_Parameters {
    
    private static Display display;
    private static Form PrevForm;
    private static int parint1;
    private static String parstr1;
    private static int parint2;
    private static String parstr2;
    private static byte ServerID;
    private static int Scenario;
    
    public Global_Parameters() {
        
    }
    public void Set_Parameter1(int Parameter) {
        parint1=Parameter;
    }
    public void Set_Parameter1(String Parameter) {
        parstr1=Parameter;
    }
    public String Get_Parameter1_Str() {
        return parstr1;
    }
    public int Get_Parameter1_Int() {
        return parint1;
    }
    public void Set_Parameter2(int Parameter) {
        parint2=Parameter;
    }
    public void Set_Parameter2(String Parameter) {
        parstr2=Parameter;
    }
    public String Get_Parameter2_Str() {
        return parstr2;
    }
    public int Get_Parameter2_Int() {
        return parint2;
    }
    public void Set_Display(Display display) {
        this.display=display;
    }
    public void Set_Previous_Form(Form Previous_form) {
        PrevForm=Previous_form;
    }
    public Display Get_Display() {
        return display;
    }
    public Form Get_Previous_Form() {
        return PrevForm;
    }
    public void Set_ServerID(byte serverid ) {
        ServerID=serverid;
    }
    public byte Get_ServerID() {
        return ServerID;
    }
     public void Set_Scenario(int scenario) {
        Scenario=scenario;
    }
    public int Get_Scenario() {
        return Scenario;
    }
}
