
package MMSServer;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.InputStream;
import java.lang.String;
import java.lang.Long;
import java.lang.Integer;
import java.lang.NullPointerException;
import java.lang.OutOfMemoryError;

public class MP {
    
    private byte Sender;
    
    private byte Receiver;
    
    private int Scenario;
    
    private byte Signal_Type;
    
    private int Signal_Command;
    
    private byte Priority=1;
    
    private long Date_Time;
    
    private int Data_Size1;
    
    private int Data_Size2;
    
    private byte[] OutData1;
    
    private byte[] OutData2;
    
    private ByteArrayOutputStream MMPOutData;
    
    private boolean ok=true;
    
    private boolean OutofMemory = false;
    
    /** Creates a new instance of MP */
    public MP() {
        
    }
    
    public synchronized void Set_MMP_Bytes(byte[] data){
        
        byte[] mydata2=null;
        byte[] mydata=null;
        ok=true;
        try{
            InputStream is=new ByteArrayInputStream(data);
            DataInputStream dis=new DataInputStream(is);
            Set_Sender(dis.readByte());
            Set_Receiver(dis.readByte());
            Set_Scenario(dis.readInt());
            Set_Signal_Type(dis.readByte());
            Set_Signal_Command(dis.readInt());
            Set_Priority(dis.readByte());
            Set_Date_Time(dis.readLong());
            Data_Size1=dis.readInt();
            if(Data_Size1>0) {
                mydata=new byte[Data_Size1];
                dis.read(mydata,0,Data_Size1);
                Set_Data1(mydata);
            }
            Data_Size2=dis.readInt();
            if(Data_Size2>0) {
                mydata2=new byte[Data_Size2];
                dis.read(mydata2,0,Data_Size2);
                Set_Data2(mydata2);
            }
        }
        catch(OutOfMemoryError e)
        {ok=false; OutofMemory=true; e.printStackTrace();}
        catch(IOException e)
        {ok=false;OutofMemory=true;e.printStackTrace();}
        catch(Exception e)
        {ok=false;e.printStackTrace();}
        
    }
    
    public boolean IsItOk() {
        return ok;
    }
    
     public boolean IsOutofMemory() {
        return OutofMemory;
    }
    
    public void Set_Sender(byte Sender){
        this.Sender=Sender;
    }
    
    public void Set_Receiver(byte Receiver){
        this.Receiver=Receiver;
    }
    
    public void Set_Scenario(int Scenario){
        this.Scenario=Scenario;
    }
    
    public void Set_Signal_Type(byte mySignal_Type){
        Signal_Type=mySignal_Type;
    }
    
    public void Set_Signal_Command(int Signal_Command){
        this.Signal_Command=Signal_Command;
    }
    
    public void Set_Priority(byte MP_Priority){
        if(MP_Priority>2){
            Priority=2;
        }else{
            if(MP_Priority<0){
                Priority=0;
            }else{ Priority=MP_Priority;}
        }
    }
    
    public void Set_Date_Time(long MP_Date_Time){
        Date_Time=MP_Date_Time;
    }
    
    public void Set_Data1(byte[] data){
        if(data!=null) {
            OutData1=data;
        }
        
    }
    
    public void Set_Data2(byte[] data){
        if(data!=null) {
            OutData2=data;
        }
    }
    
    public byte Get_Sender(){
        return Sender;
    }
    
    public byte Get_Receiver(){
        return Receiver;
    }
    
    public int  Get_Scenario(){
        return Scenario;
    }
    private byte[]  Get_Scenario_Byte(){
        byte b[] = new byte[4];
        int i, shift;
        for(i = 0, shift = 24; i < 4; i++, shift -= 8)
            b[i] = (byte)(0xFF & (Scenario >> shift));
        return b;
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
    
    public byte Get_Priority(){
        return Priority;
    }
    
    public long Get_Date_Time(){
        return Date_Time;
    }
    
    private byte[] Get_Date_Time_Byte(){
        byte b[] = new byte[8];
        int i, shift;
        for(i = 0, shift = 56; i < 8; i++, shift -= 8)
            b[i] = (byte)(0xFF & (Date_Time >> shift));
        return b;
    }
    
    public byte[] Get_Data1(){
        
        return OutData1;
        
    }
    
    public byte[] Get_Data2(){
        return OutData2;
    }
    
    public synchronized String Get_Data1_As_String(){
        ByteArrayOutputStream bous=new ByteArrayOutputStream();
        String s="";
        try{
            bous.write(OutData1);
            s= bous.toString();
        }catch(Exception e){s="";}
        return s;
    }
    public synchronized String Get_Data2_As_String(){
        ByteArrayOutputStream bous=new ByteArrayOutputStream();
        String s="";
        try{
            bous.write(OutData2);
            s= bous.toString();
            
        }catch(Exception e){s="";}
        return s;
    }
    
    public int Get_Data1_Size(){
        
        
        int zero=0;
        try {
            zero=OutData1.length;
        }
        catch(Exception e)
        {}
        return zero;
        
    }
    
    private synchronized byte[] Get_Data1_Size_Byte(){
        int zero=0;
        try{
            
            zero= OutData1.length;
        }catch (Exception e){}
        byte b[] = new byte[4];
        int i, shift;
        for(i = 0, shift = 24; i < 4; i++, shift -= 8)
            b[i] = (byte)(0xFF & (zero >> shift));
        return b;
    }
    
    public synchronized int Get_Data2_Size(){
        int zero=0;
        try {
            zero=OutData2.length;
        }
        catch(Exception e)
        {}
        return zero;
        
    }
    
    private synchronized byte[] Get_Data2_Size_Byte(){
        int zero=0;
        try{
            
            zero= OutData2.length;
        }catch (Exception e){}
        byte b[] = new byte[4];
        int i, shift;
        for(i = 0, shift = 24; i < 4; i++, shift -= 8)
            b[i] = (byte)(0xFF & (zero >> shift));
        return b;
    }
    public synchronized byte[] Get_MMP_Byte(){
        byte[] data =new byte[Get_MMP_Size()];
        int i;
        ByteArrayOutputStream MMPOutData=new ByteArrayOutputStream();
        try{
            
            MMPOutData.write(Sender);
            MMPOutData.write(Receiver);
            MMPOutData.write(Get_Scenario_Byte());
            MMPOutData.write(Signal_Type);
            MMPOutData.write(Get_Signal_Command_Byte());
            MMPOutData.write(Priority);
            MMPOutData.write(Get_Date_Time_Byte());
            MMPOutData.write(Get_Data1_Size_Byte());
            i=MMPOutData.size();
            System.arraycopy(MMPOutData.toByteArray() , 0, data, 0,MMPOutData.toByteArray().length);
            if(Get_Data1_Size()>0) {
                System.arraycopy(OutData1 , 0, data, i, OutData1.length);
                i=i+OutData1.length;
            }
            System.arraycopy(Get_Data2_Size_Byte() , 0, data, i,Get_Data2_Size_Byte().length);
            i=i+Get_Data2_Size_Byte().length;
            if(Get_Data2_Size()>0)
            {System.arraycopy(OutData2 , 0, data, i,OutData2.length);}
        }
        catch(Exception e)
        {data=null;}
        return data;
    }
    
    public synchronized int Get_MMP_Size(){
        int i;
        ByteArrayOutputStream MMPOutData1=new ByteArrayOutputStream();
        try{
            
            MMPOutData1.write(Sender);
            MMPOutData1.write(Receiver);
            MMPOutData1.write(Get_Scenario_Byte());
            MMPOutData1.write(Signal_Type);
            MMPOutData1.write(Get_Signal_Command_Byte());
            MMPOutData1.write(Priority);
            MMPOutData1.write(Get_Date_Time_Byte());
            MMPOutData1.write(Get_Data1_Size_Byte());
            i=MMPOutData1.size();
            if(Get_Data1_Size()>0)
            {i=i+OutData1.length;}
            i=i+4;
            if(Get_Data2_Size()>0)
            {i=i+OutData2.length;}
        }
        catch(Exception e)
        {i=0;}
        return i;
    }
    
    public String Get_MMP_String(){
        String s;
        ByteArrayOutputStream MMPOutData1=new ByteArrayOutputStream();
        try{
            
            MMPOutData1.write(Get_MMP_Byte());
            s=MMPOutData1.toString();
        }
        catch(Exception e)
        {s="";}
        return s;
    }
}