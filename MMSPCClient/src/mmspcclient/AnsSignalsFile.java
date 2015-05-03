
package mmspcclient;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.InputStream;
import java.lang.String;

//This class is the structure for answer signal
//Answer signal are the sended signals which wait for answare
public class AnsSignalsFile {
    
    //Answer signal attributes
    private byte Signal_Type;
    private byte SenderID;
    private int Signal_Command;
    private int Scenario;
    private long Date_Time;
    
    
    public AnsSignalsFile() {
        
    }
    
    //Accept an array of byte and fill values of attributes
    public void Set_AnsSignalsFile_Bytes(byte[] data) {
        try{
            InputStream is=new ByteArrayInputStream(data);
            DataInputStream dis=new DataInputStream(is);
            Set_Signal_Type(dis.readByte());
            Set_Signal_Command(dis.readInt());
            Set_SenderID(dis.readByte());
            Set_Scenario(dis.readInt());
            Set_Date_Time(dis.readLong());
            
        }catch(IOException e)
        {}
        
    }
    
    //Set a value in attribute Signal Type
    public void Set_Signal_Type(byte mySignal_Type){
        Signal_Type=mySignal_Type;
    }
    
    //Set a value in attribute Sender ID
    public void Set_SenderID(byte SenderID){
        this.SenderID=SenderID;
    }
    
    //Set a value in attribute Signal Command
    public void Set_Signal_Command(int Signal_Command){
        this.Signal_Command=Signal_Command;
    }
    
    //Set a value in attribute Scenario
    public void Set_Scenario(int Scenario){
        this.Scenario=Scenario;
    }
    
    //Set a value in attribute Date_Time
    public void Set_Date_Time(long MP_Date_Time){
        Date_Time=MP_Date_Time;
    }
    
    //Return value from attribute Signal Type
    public byte Get_Signal_Type(){
        return Signal_Type;
    }
    
    //Return value from attribute Sender ID
    public byte Get_SenderID(){
        return SenderID;
    }
    
    //Return value from attribute Signal Command
    public int Get_Signal_Command(){
        return Signal_Command;
    }
    
    //Return value from attribute Scenario
    public int Get_Scenario(){
        return Scenario;
    }
    
    //Convert integer value of signal command attribute as array of bytes
    private byte[] Get_Signal_Command_Byte(){
        byte b[] = new byte[4];
        int i, shift;
        for(i = 0, shift = 24; i < 4; i++, shift -= 8)
            b[i] = (byte)(0xFF & (Signal_Command >> shift));
        return b;
    }
    
    
    //Convert integer value of scenario attribute as array of bytes
    private byte[] Get_Scenario_Byte(){
        byte b[] = new byte[4];
        int i, shift;
        for(i = 0, shift = 24; i < 4; i++, shift -= 8)
            b[i] = (byte)(0xFF & (Scenario >> shift));
        return b;
    }
    
    //Return value from attribute Date_Time
    public long Get_Date_Time(){
        return Date_Time;
    }
    
    //Convert long number value of date_time attribute as array of bytes
    private byte[] Get_Date_Time_Byte(){
        byte b[] = new byte[8];
        int i, shift;
        for(i = 0, shift = 56; i < 8; i++, shift -= 8)
            b[i] = (byte)(0xFF & (Date_Time >> shift));
        return b;
    }
    
    //Return a array of bytes with values of attributes
    public byte[] Get_AnsSignalsFile_Byte(){
        byte[] data=null;
        ByteArrayOutputStream MMPOutData=new ByteArrayOutputStream();
        try{
            MMPOutData.write(Signal_Type);
            MMPOutData.write(Get_Signal_Command_Byte());
            MMPOutData.write(SenderID);
            MMPOutData.write(Get_Scenario_Byte());
            MMPOutData.write(Get_Date_Time_Byte());
            data=MMPOutData.toByteArray();
        }catch(IOException e){}
        return data;
    }
    
    //Return number of bytes for each answer signal
    public int Get_AnsSignalsFile_Size(){
        return 18;
    }
}