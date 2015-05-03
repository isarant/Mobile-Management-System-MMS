
package MMSClient;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.InputStream;
import java.lang.String;

public class SignalsFile {
    
    private byte visible;
    private byte Parameters;
    private byte Signal_Type;
    private int Signal_Command;
    private byte typeofParameter1,typeofParameter2;
    
    private ByteArrayOutputStream BAOSDesc,BAOSClassName,BAOSParameter1Name,BAOSParameter2Name,BAOSUsedCommand,BAOSParameterClass;
    
    public SignalsFile() {
        BAOSDesc=new ByteArrayOutputStream();
        BAOSClassName=new ByteArrayOutputStream();
        BAOSParameter1Name=new ByteArrayOutputStream();
        BAOSParameter2Name=new ByteArrayOutputStream();
        BAOSUsedCommand=new ByteArrayOutputStream();
        BAOSParameterClass=new ByteArrayOutputStream();
    }
    
    public void Set_SignalsFile_Bytes(byte[] data) {
        try{
            InputStream is=new ByteArrayInputStream(data);
            DataInputStream dis=new DataInputStream(is);
            Set_Visible(dis.readByte());
            Set_Parameters(dis.readByte());
            Set_Signal_Type(dis.readByte());
            Set_Signal_Command(dis.readInt());
            Set_TypeOfParameter1(dis.readByte());
            Set_TypeOfParameter2(dis.readByte());
            BAOSDesc.reset();
            for(int x=0; x<20; x++){
                BAOSDesc.write(dis.readByte());
            }
            BAOSClassName.reset();
            for(int x=0; x<50; x++){
                BAOSClassName.write(dis.readByte());
            }
            BAOSParameter1Name.reset();
            for(int x=0; x<10; x++){
                BAOSParameter1Name.write(dis.readByte());
            }
            BAOSParameter2Name.reset();
            for(int x=0; x<10; x++){
                BAOSParameter2Name.write(dis.readByte());
            }
            BAOSUsedCommand.reset();
            for(int x=0; x<6; x++){
                BAOSUsedCommand.write(dis.readByte());
            }
            BAOSParameterClass.reset();
            for(int x=0; x<50; x++){
                BAOSParameterClass.write(dis.readByte());
            }
        }catch(IOException e)
        {}
        
    }
    
    public void Set_Parameters(byte myParameters){
        Parameters=myParameters;
    }
    public void Set_TypeOfParameter1(byte typepar){
        typeofParameter1=typepar;
    }
    public void Set_TypeOfParameter2(byte typepar){
        typeofParameter2=typepar;
    }
    public void Set_Visible(byte myvisible){
        visible=myvisible;
    }
    public void Set_Signal_Type(byte mySignal_Type){
        Signal_Type=mySignal_Type;
    }
    
    public void Set_Signal_Command(int Signal_Command){
        this.Signal_Command=Signal_Command;
    }
    
    public void Set_Desc(String Desc){
        BAOSDesc.reset();
        try{
            int mylength=Desc.length();
            if (mylength<=20){
                for(int x=1;x<=20-mylength;x++)
                {    Desc=Desc + " ";}
                BAOSDesc.write(Desc.getBytes());
            }else{
                BAOSDesc.write(Desc.substring(0,20).getBytes());
            }
            
        }catch(IOException e){}
    }
    
    public void Set_ParameterClass(String Classname){
        BAOSParameterClass.reset();
        try{
            int mylength=Classname.length();
            if (mylength<=50){
                for(int x=1;x<=50-mylength;x++)
                {    Classname=Classname + " ";}
                BAOSParameterClass.write(Classname.getBytes());
            }else{
                BAOSParameterClass.write(Classname.substring(0,50).getBytes());
            }
            
        }catch(IOException e){}
    }
    public void Set_ClassName(String Classname){
        BAOSClassName.reset();
        try{
            int mylength=Classname.length();
            if (mylength<=50){
                for(int x=1;x<=50-mylength;x++)
                {    Classname=Classname + " ";}
                BAOSClassName.write(Classname.getBytes());
            }else{
                BAOSClassName.write(Classname.substring(0,50).getBytes());
            }
            
        }catch(IOException e){}
    }
    public void Set_Parameter1Name(String Parametername){
        BAOSParameter1Name.reset();
        try{
            int mylength=Parametername.length();
            if (mylength<=10){
                for(int x=1;x<=10-mylength;x++)
                {    Parametername=Parametername + " ";}
                BAOSParameter1Name.write(Parametername.getBytes());
            }else{
                BAOSParameter1Name.write(Parametername.substring(0,10).getBytes());
            }
            
        }catch(IOException e){}
    }
    
    public void Set_Parameter2Name(String Parametername){
        BAOSParameter2Name.reset();
        try{
            int mylength=Parametername.length();
            if (mylength<=10){
                for(int x=1;x<=10-mylength;x++)
                {    Parametername=Parametername + " ";}
                BAOSParameter2Name.write(Parametername.getBytes());
            }else{
                BAOSParameter2Name.write(Parametername.substring(0,10).getBytes());
            }
            
        }catch(IOException e){}
    }
    public void Set_UsedCommand(String housincommand){
        BAOSUsedCommand.reset();
        try{
            int mylength=housincommand.length();
            if (mylength<=6){
                for(int x=1;x<=6-mylength;x++)
                {    housincommand=housincommand + " ";}
                BAOSUsedCommand.write(housincommand.getBytes());
            }else{
                BAOSUsedCommand.write(housincommand.substring(0,6).getBytes());
            }
            
        }catch(IOException e){}
    }
    
    public byte Get_Parameters(){
        return Parameters;
    }
    public byte Get_TypeOfParameter1(){
        return typeofParameter1;
    }
    public String Get_TypeOfParameter1_String(){
        String s="";
        if(typeofParameter1==0) {
            s="txt";
        }
        if(typeofParameter1==1) {
            s="int";
        }
        if(typeofParameter1==2) {
            s="float";
        }
        return s;
    }
    public String Get_TypeOfParameter2_String(){
        String s="";
        if(typeofParameter2==0) {
            s="txt";
        }
        if(typeofParameter2==1) {
            s="int";
        }
        if(typeofParameter2==2) {
            s="float";
        }
        return s;
    }
    public byte Get_TypeOfParameter2(){
        return typeofParameter2;
    }
    
    public byte Get_Visible(){
        return visible;
    }
    public byte Get_Signal_Type(){
        return Signal_Type;
    }
    
    public int Get_Signal_Command(){
        return Signal_Command;
    }
    
    private byte[] Get_Signal_Command_Byte(){
        byte b[] = new byte[4];
        int i, shift;
        for(i = 0, shift = 24; i < 4; i++, shift -= 8)
            b[i] = (byte)(0xFF & (Signal_Command >> shift));
        return b;
    }
    
    public String Get_Desc(){
        return BAOSDesc.toString();
    }
    
    
    public String Get_ClassName(){
        return BAOSClassName.toString();
    }
    public String Get_ParameterClass(){
        return BAOSParameterClass.toString();
    }
    public String Get_Parameter1Name(){
        return BAOSParameter1Name.toString();
    }
    public String Get_Parameter2Name(){
        return BAOSParameter2Name.toString();
    }
    public String Get_UsedCommand(){
        return BAOSUsedCommand.toString();
    }
    
    private byte[]  Get_Desc_byte(){
        byte[] zero=null;
        try{
            return BAOSDesc.toByteArray();
        }catch(NullPointerException e)
        { return zero;}
        
    }
    
    private byte[]  Get_ClassName_byte(){
        byte[] zero=null;
        try{
            return BAOSClassName.toByteArray();
        }catch(NullPointerException e)
        { return zero;}
    }
    private byte[]  Get_ParameterClass_byte(){
        byte[] zero=null;
        try{
            return BAOSParameterClass.toByteArray();
        }catch(NullPointerException e)
        { return zero;}
    }
    private byte[]  Get_Parameter1Name_byte(){
        byte[] zero=null;
        try{
            return BAOSParameter1Name.toByteArray();
        }catch(NullPointerException e)
        { return zero;}
    }
    
    private byte[]  Get_Parameter2Name_byte(){
        byte[] zero=null;
        try{
            return BAOSParameter2Name.toByteArray();
        }catch(NullPointerException e)
        { return zero;}
    }
    
    private byte[]  Get_UsedCommand_byte(){
        byte[] zero=null;
        try{
            return BAOSUsedCommand.toByteArray();
        }catch(NullPointerException e)
        { return zero;}
    }
    
    public String Get_Desc_No_Space(){
        int a=BAOSDesc.toString().length();
        for(int x=0;x<BAOSDesc.toString().length();x++) {
            if(BAOSDesc.toString().charAt(x)==' ')
            {a=x;break;}
        }
        return BAOSDesc.toString().substring(0,a);
    }
    
    public String Get_ClassName_No_Space(){
        int a=BAOSClassName.toString().length();
        for(int x=0;x<BAOSClassName.toString().length();x++) {
            if(BAOSClassName.toString().charAt(x)==' ')
            {a=x;break;}
        }
        return BAOSClassName.toString().substring(0,a);
    }
    public String Get_ParameterClass_No_Space(){
        int a=BAOSParameterClass.toString().length();
        for(int x=0;x<BAOSParameterClass.toString().length();x++) {
            if(BAOSParameterClass.toString().charAt(x)==' ')
            {a=x;break;}
        }
        return BAOSParameterClass.toString().substring(0,a);
    }
    public String Get_Parameter1Name_No_Space(){
        int a=BAOSParameter1Name.toString().length();
        for(int x=0;x<BAOSParameter1Name.toString().length();x++) {
            if(BAOSParameter1Name.toString().charAt(x)==' ')
            {a=x;break;}
        }
        return BAOSParameter1Name.toString().substring(0,a);
    }
    
    
    public String Get_Parameter2Name_No_Space(){
        int a=BAOSParameter2Name.toString().length();
        for(int x=0;x<BAOSParameter2Name.toString().length();x++) {
            if(BAOSParameter2Name.toString().charAt(x)==' ')
            {a=x;break;}
        }
        return BAOSParameter2Name.toString().substring(0,a);
    }
    
    public String Get_UsedCommand_No_Space(){
        int a=BAOSUsedCommand.toString().length();
        for(int x=0;x<BAOSUsedCommand.toString().length();x++) {
            if(BAOSUsedCommand.toString().charAt(x)==' ')
            {a=x;break;}
        }
        return BAOSUsedCommand.toString().substring(0,a);
    }
    
    public byte[] Get_SignalsFile_Byte(){
        byte[] data=null;
        ByteArrayOutputStream MMPOutData=new ByteArrayOutputStream();
        try{
            MMPOutData.write(visible);
            MMPOutData.write(Parameters);
            MMPOutData.write(Signal_Type);
            MMPOutData.write(Get_Signal_Command_Byte());
            MMPOutData.write(typeofParameter1);
            MMPOutData.write(typeofParameter2);
            if(BAOSDesc.size()>0)
            {MMPOutData.write(Get_Desc_byte());}
            if(BAOSClassName.size()>0)
            {MMPOutData.write(Get_ClassName_byte());}
            if(BAOSParameter1Name.size()>0)
            {MMPOutData.write(Get_Parameter1Name_byte());}
            if(BAOSParameter2Name.size()>0)
            {MMPOutData.write(Get_Parameter2Name_byte());}
            if(BAOSUsedCommand.size()>0)
            {MMPOutData.write(Get_UsedCommand_byte());}
            if(BAOSParameterClass.size()>0)
            {MMPOutData.write(Get_ParameterClass_byte());}
            data=MMPOutData.toByteArray();
        }catch(IOException e){}
        return data;
    }
    
    public int Get_SignalsFile_Size(){
        int size=155;
        return size;
    }
}