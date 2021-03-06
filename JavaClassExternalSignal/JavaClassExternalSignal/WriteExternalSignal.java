

package JavaClassExternalSignal;

import java.io.RandomAccessFile;
import java.io.IOException;
import java.util.Date;
  
public class WriteExternalSignal
{
	private Date dt;
	private MP mp,mp1;
	
	public WriteExternalSignal()
	{
		dt=new Date();
	}
	
	public void SetSignal(byte Sender,byte Reciever,byte Priority,int Scenario,byte SignalType,int SignalCommand,byte[] Data1,byte[] Data2)
	{
		mp1=new MP();
		mp1.Set_Sender(Sender);
		mp1.Set_Receiver(Reciever);
		mp1.Set_Priority(Priority);
		mp1.Set_Scenario(Scenario);
		mp1.Set_Date_Time(dt.getDate());
		mp1.Set_Signal_Command(SignalCommand);
		mp1.Set_Signal_Type(SignalType);
		mp1.Set_Data1(Data1);
		mp1.Set_Data2(Data2);
	}


	public void WriteToExternalFile()
	{
		mp =new MP();
		mp.Set_MMP_Bytes(mp1.Get_MMP_Byte()); 
		if (mp.IsItOk()==true)
		{
			RandomAccessFile raf=null;
			try 
			{
				raf=new RandomAccessFile("c:\\MobileManageSystem\\System\\ExtSignalFile.mms","rw");
				raf.seek(raf.length());
				raf.write(mp.Get_MMP_Byte());
				raf.close();
			}
			catch (IOException e)
			{e.printStackTrace();}
		}
	}


}
