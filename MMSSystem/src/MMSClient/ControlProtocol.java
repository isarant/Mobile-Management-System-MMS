

package MMSClient;

class ControlProtocol {
    private SignalsFile[] varSignalsFile;
    private int mysizeArray=30;
    
    public  ControlProtocol()
    {
       
        try {
            varSignalsFile=new SignalsFile[mysizeArray];
         synchronized(varSignalsFile)
            {
            
            /************************************/
            varSignalsFile[0]=new SignalsFile();
            varSignalsFile[0].Set_Visible((byte)0);
            varSignalsFile[0].Set_Signal_Type((byte)1);
            varSignalsFile[0].Set_Signal_Command(0);
            varSignalsFile[0].Set_UsedCommand("Both");
            varSignalsFile[0].Set_Desc("LogOnSgnFile");
            varSignalsFile[0].Set_ClassName("ChkPasswdSendFile");
            /************************************/
            /************************************/
            varSignalsFile[1]=new SignalsFile();
            varSignalsFile[1].Set_Visible((byte)0);
            varSignalsFile[1].Set_Signal_Type((byte)2);
            varSignalsFile[1].Set_Signal_Command(0);
            varSignalsFile[1].Set_UsedCommand("Both");
            varSignalsFile[1].Set_Desc("AnsLogonandSentFile");
            varSignalsFile[1].Set_ClassName("AnsPasswdSendFile");
            /************************************/
            /************************************/
            varSignalsFile[2]=new SignalsFile();
            varSignalsFile[2].Set_Visible((byte)0);
            varSignalsFile[2].Set_UsedCommand("Both");
            varSignalsFile[2].Set_Signal_Type((byte)1);
            varSignalsFile[2].Set_Signal_Command(1);
            varSignalsFile[2].Set_Desc("LogonSignal");
            varSignalsFile[2].Set_ClassName("CheckPasswd");
            /************************************/
            /************************************/
            varSignalsFile[3]=new SignalsFile();
            varSignalsFile[3].Set_Visible((byte)0);
            varSignalsFile[3].Set_Signal_Type((byte)2);
            varSignalsFile[3].Set_Signal_Command(1);
            varSignalsFile[3].Set_UsedCommand("Both");
            varSignalsFile[3].Set_Desc("AnsLogonSignal");
            varSignalsFile[3].Set_ClassName("AnsCheckPasswd");
            /************************************/
            /************************************/
            varSignalsFile[4]=new SignalsFile();
            varSignalsFile[4].Set_Visible((byte)1);
            varSignalsFile[4].Set_Signal_Type((byte)2);
            varSignalsFile[4].Set_UsedCommand("Both");
            varSignalsFile[4].Set_Signal_Command(2);
            varSignalsFile[4].Set_Desc("Logoff");
            varSignalsFile[4].Set_ClassName("CheckCloseConnection");
            /************************************/
            /************************************/
            varSignalsFile[5]=new SignalsFile();
            varSignalsFile[5].Set_Visible((byte)0);
            varSignalsFile[5].Set_Signal_Type((byte)1);
            varSignalsFile[5].Set_Signal_Command(3);
            varSignalsFile[5].Set_UsedCommand("Both");
            varSignalsFile[5].Set_Desc("AreYouAlive");
            varSignalsFile[5].Set_ClassName("IAmAlive");
            /************************************/
            /************************************/
            varSignalsFile[6]=new SignalsFile();
            varSignalsFile[6].Set_Visible((byte)0);
            varSignalsFile[6].Set_Signal_Type((byte)2);
            varSignalsFile[6].Set_Signal_Command(3);
            varSignalsFile[6].Set_UsedCommand("Both");
            varSignalsFile[6].Set_Desc("IamAlive");
            varSignalsFile[6].Set_ClassName("ResetIAmAlive");
            /************************************/
            /************************************/
            varSignalsFile[7]=new SignalsFile();
            varSignalsFile[7].Set_UsedCommand("Both");
            varSignalsFile[7].Set_Visible((byte)0);
            varSignalsFile[7].Set_Parameters((byte)1);
            varSignalsFile[7].Set_Signal_Type((byte)2);
            varSignalsFile[7].Set_Signal_Command(4);
            varSignalsFile[7].Set_Desc("ChangeBufferSize");
            varSignalsFile[7].Set_ClassName("ChangeBufferSize");
            /************************************/
            /************************************/
            varSignalsFile[8]=new SignalsFile();
            varSignalsFile[8].Set_ParameterClass("FileManager");
            varSignalsFile[8].Set_Parameters((byte)1);
            varSignalsFile[8].Set_Parameter1Name("FileName");
            varSignalsFile[8].Set_Visible((byte)1);
            varSignalsFile[8].Set_Signal_Type((byte)1);
            varSignalsFile[8].Set_Signal_Command(100);
            varSignalsFile[8].Set_Desc("SendFile");
            varSignalsFile[8].Set_UsedCommand("Both");
            varSignalsFile[8].Set_ClassName("GetFile");
            /************************************/
            /************************************/
            varSignalsFile[9]=new SignalsFile();
            varSignalsFile[9].Set_ParameterClass("FileManager");
            varSignalsFile[9].Set_Parameters((byte)1);
            varSignalsFile[9].Set_Parameter1Name("FileName");
            varSignalsFile[9].Set_Visible((byte)0);
            varSignalsFile[9].Set_Signal_Type((byte)2);
            varSignalsFile[9].Set_Signal_Command(100);
            varSignalsFile[9].Set_Desc("RecievedSendedFile");
            varSignalsFile[9].Set_UsedCommand("Both");
            varSignalsFile[9].Set_ClassName("None");
            /************************************/
            /************************************/
            varSignalsFile[10]=new SignalsFile();
            varSignalsFile[10].Set_Visible((byte)1);
            varSignalsFile[10].Set_Parameters((byte)1);
            varSignalsFile[10].Set_Parameter1Name("FileName");
            varSignalsFile[10].Set_ParameterClass("SendMeFile");
            varSignalsFile[10].Set_Signal_Type((byte)1);
            varSignalsFile[10].Set_UsedCommand("Both");
            varSignalsFile[10].Set_Signal_Command(101);
            varSignalsFile[10].Set_Desc("SendMeFile");
            varSignalsFile[10].Set_ClassName("SendFile");
            /************************************/
            /************************************/
            varSignalsFile[11]=new SignalsFile();
            varSignalsFile[11].Set_Visible((byte)0);
            varSignalsFile[11].Set_Parameters((byte)1);
            varSignalsFile[11].Set_Parameter1Name("FileName");
            varSignalsFile[11].Set_Signal_Type((byte)2);
            varSignalsFile[11].Set_Signal_Command(101);
            varSignalsFile[11].Set_Desc("RecieveFile");
            varSignalsFile[11].Set_ClassName("GetFile");
            varSignalsFile[11].Set_UsedCommand("Both");
            /************************************/
            /************************************/
            varSignalsFile[12]=new SignalsFile();
            varSignalsFile[12].Set_ParameterClass("FileManager");
            varSignalsFile[12].Set_Parameters((byte)1);
            varSignalsFile[12].Set_Parameter1Name("ImageName");
            varSignalsFile[12].Set_Visible((byte)1);
            varSignalsFile[12].Set_Signal_Type((byte)1);
            varSignalsFile[12].Set_Signal_Command(102);
            varSignalsFile[12].Set_Desc("SendImage");
            varSignalsFile[12].Set_UsedCommand("Both");
            varSignalsFile[12].Set_ClassName("ShowSendImage");
            /************************************/
            /************************************/
            varSignalsFile[13]=new SignalsFile();
            varSignalsFile[13].Set_ParameterClass("FileManager");
            varSignalsFile[13].Set_Parameters((byte)1);
            varSignalsFile[13].Set_Parameter1Name("ImageName");
            varSignalsFile[13].Set_Visible((byte)0);
            varSignalsFile[13].Set_Signal_Type((byte)2);
            varSignalsFile[13].Set_Signal_Command(102);
            varSignalsFile[13].Set_Desc("RecievedImageFile");
            varSignalsFile[13].Set_UsedCommand("Both");
            varSignalsFile[13].Set_ClassName("None");
            /************************************/
            /************************************/
            varSignalsFile[14]=new SignalsFile();
            varSignalsFile[14].Set_ParameterClass("FileManager");
            varSignalsFile[14].Set_Parameters((byte)1);
            varSignalsFile[14].Set_Parameter1Name("MediaName");
            varSignalsFile[14].Set_Visible((byte)1);
            varSignalsFile[14].Set_Signal_Type((byte)1);
            varSignalsFile[14].Set_Signal_Command(103);
            varSignalsFile[14].Set_Desc("SendMedia");
            varSignalsFile[14].Set_UsedCommand("Both");
            varSignalsFile[14].Set_ClassName("PlayMedia");
            /************************************/
            /************************************/
            varSignalsFile[15]=new SignalsFile();
            varSignalsFile[15].Set_ParameterClass("FileManager");
            varSignalsFile[15].Set_Parameters((byte)1);
            varSignalsFile[15].Set_Parameter1Name("ImageName");
            varSignalsFile[15].Set_Visible((byte)0);
            varSignalsFile[15].Set_Signal_Type((byte)2);
            varSignalsFile[15].Set_Signal_Command(103);
            varSignalsFile[15].Set_Desc("RecievedMediadFile");
            varSignalsFile[15].Set_UsedCommand("Both");
            varSignalsFile[15].Set_ClassName("None");
            /************************************/
            /************************************/
            varSignalsFile[16]=new SignalsFile();
            varSignalsFile[16].Set_Visible((byte)1);
            varSignalsFile[16].Set_Signal_Type((byte)1);
            varSignalsFile[16].Set_Signal_Command(104);
            varSignalsFile[16].Set_Desc("GetActiveUsers");
            varSignalsFile[16].Set_UsedCommand("Client");
            varSignalsFile[16].Set_ClassName("SendUsersFile");
            /************************************/
            /************************************/
            varSignalsFile[17]=new SignalsFile();
            varSignalsFile[17].Set_Parameters((byte)1);
            varSignalsFile[17].Set_Parameter1Name("AcitveUsersFile");
            varSignalsFile[17].Set_Visible((byte)0);
            varSignalsFile[17].Set_Signal_Type((byte)2);
            varSignalsFile[17].Set_Signal_Command(104);
            varSignalsFile[17].Set_Desc("RecievedActiveUsersList");
            varSignalsFile[17].Set_UsedCommand("Server");
            varSignalsFile[17].Set_ClassName("GetFile");
            /************************************/
            /************************************/
            varSignalsFile[18]=new SignalsFile();
            varSignalsFile[18].Set_Visible((byte)1);
            varSignalsFile[18].Set_Signal_Type((byte)1);
            varSignalsFile[18].Set_Signal_Command(105);
            varSignalsFile[18].Set_Desc("GetAllUsers");
            varSignalsFile[18].Set_UsedCommand("Client");
            varSignalsFile[18].Set_ClassName("SendUsersFile");
            /************************************/
            /************************************/
            varSignalsFile[19]=new SignalsFile();
            varSignalsFile[19].Set_Parameters((byte)1);
            varSignalsFile[19].Set_Parameter1Name("AllUsersFile");
            varSignalsFile[19].Set_Visible((byte)0);
            varSignalsFile[19].Set_Signal_Type((byte)2);
            varSignalsFile[19].Set_Signal_Command(105);
            varSignalsFile[19].Set_Desc("RecievedAllUsersList");
            varSignalsFile[19].Set_UsedCommand("Server");
            varSignalsFile[19].Set_ClassName("GetFile");
            /************************************/
            /************************************/
            varSignalsFile[20]=new SignalsFile();
            varSignalsFile[20].Set_Parameters((byte)2);
            varSignalsFile[20].Set_Parameter1Name("UserName");
            varSignalsFile[20].Set_Parameter2Name("Message");
            varSignalsFile[20].Set_Visible((byte)1);
            varSignalsFile[20].Set_Signal_Type((byte)1);
            varSignalsFile[20].Set_Signal_Command(106);
            varSignalsFile[20].Set_ParameterClass("SendMsgtoUser");
            varSignalsFile[20].Set_Desc("SendMsgToUser");
            varSignalsFile[20].Set_UsedCommand("Client");
            varSignalsFile[20].Set_ClassName("ForwardMsg");
            /************************************/
            /************************************/
            varSignalsFile[21]=new SignalsFile();
            varSignalsFile[21].Set_Parameters((byte)1);
            varSignalsFile[21].Set_Parameter1Name("UserName");
            varSignalsFile[21].Set_Visible((byte)0);
            varSignalsFile[21].Set_Signal_Type((byte)2);
            varSignalsFile[21].Set_Signal_Command(106);
            varSignalsFile[21].Set_Desc("SendingMsg");
            varSignalsFile[21].Set_UsedCommand("Both");
            varSignalsFile[21].Set_ClassName("None");
            /************************************/
            /************************************/
            varSignalsFile[22]=new SignalsFile();
            varSignalsFile[22].Set_Parameters((byte)2);
            varSignalsFile[22].Set_Parameter1Name("UserName");
            varSignalsFile[22].Set_Parameter2Name("Message");
            varSignalsFile[22].Set_Visible((byte)0);
            varSignalsFile[22].Set_Signal_Type((byte)1);
            varSignalsFile[22].Set_Signal_Command(107);
            varSignalsFile[22].Set_Desc("RecieveAMsg");
            varSignalsFile[22].Set_UsedCommand("Server");
            varSignalsFile[22].Set_ClassName("ShowMsg");
            /************************************/
            /************************************/
            varSignalsFile[23]=new SignalsFile();
            varSignalsFile[23].Set_Parameters((byte)1);
            varSignalsFile[23].Set_Parameter1Name("UserName");
            varSignalsFile[23].Set_Visible((byte)0);
            varSignalsFile[23].Set_Signal_Type((byte)2);
            varSignalsFile[23].Set_Signal_Command(107);
            varSignalsFile[23].Set_Desc("MsgRecievedFromUser");
            varSignalsFile[23].Set_UsedCommand("Both");
            varSignalsFile[23].Set_ClassName("None");
            /************************************/
            /************************************/
            varSignalsFile[24]=new SignalsFile();
            varSignalsFile[24].Set_Parameters((byte)1);
            varSignalsFile[24].Set_Parameter1Name("par1");
            varSignalsFile[24].Set_Visible((byte)1);
            varSignalsFile[24].Set_Signal_Type((byte)1);
            varSignalsFile[24].Set_Signal_Command(108);
            varSignalsFile[24].Set_ParameterClass("SendFiletoUser");
            varSignalsFile[24].Set_Desc("SendFiletoUser");
            varSignalsFile[24].Set_UsedCommand("Client");
            varSignalsFile[24].Set_ClassName("ForwardFile");
            /************************************/
            /************************************/
            varSignalsFile[25]=new SignalsFile();
            varSignalsFile[25].Set_Parameters((byte)2);
            varSignalsFile[25].Set_Parameter1Name("UserName");
            varSignalsFile[25].Set_Parameter1Name("FileName");
            varSignalsFile[25].Set_Visible((byte)0);
            varSignalsFile[25].Set_Signal_Type((byte)2);
            varSignalsFile[25].Set_Signal_Command(108);
            varSignalsFile[25].Set_Desc("SendingFileMsg");
            varSignalsFile[25].Set_UsedCommand("Both");
            varSignalsFile[25].Set_ClassName("None");
            /************************************/
            /************************************/
            varSignalsFile[26]=new SignalsFile();
            varSignalsFile[26].Set_Parameters((byte)1);
            varSignalsFile[26].Set_Parameter1Name("UserName");
            varSignalsFile[26].Set_Visible((byte)0);
            varSignalsFile[26].Set_Signal_Type((byte)1);
            varSignalsFile[26].Set_Signal_Command(109);
            varSignalsFile[26].Set_Desc("RecieveAFileMsg");
            varSignalsFile[26].Set_UsedCommand("Server");
            varSignalsFile[26].Set_ClassName("ShowFileMsg");
            /************************************/
            /************************************/
            varSignalsFile[27]=new SignalsFile();
            varSignalsFile[27].Set_Parameters((byte)2);
            varSignalsFile[27].Set_Parameter1Name("UserName");
            varSignalsFile[27].Set_Parameter1Name("FileName");
            varSignalsFile[27].Set_Visible((byte)0);
            varSignalsFile[27].Set_Signal_Type((byte)2);
            varSignalsFile[27].Set_Signal_Command(109);
            varSignalsFile[27].Set_Desc("FileMsgRecievedFromUser");
            varSignalsFile[27].Set_UsedCommand("Both");
            varSignalsFile[27].Set_ClassName("None");
            /************************************/
            /************************************/
            varSignalsFile[28]=new SignalsFile();
            varSignalsFile[28].Set_Visible((byte)1);
            varSignalsFile[28].Set_Signal_Type((byte)1);
            varSignalsFile[28].Set_Signal_Command(110);
            varSignalsFile[28].Set_Desc("GetAvaliableFiles");
            varSignalsFile[28].Set_UsedCommand("Client");
            varSignalsFile[28].Set_ClassName("SendAvaliableFiles");
            /************************************/
            /************************************/
            varSignalsFile[29]=new SignalsFile();
            varSignalsFile[29].Set_Parameters((byte)1);
            varSignalsFile[29].Set_Parameter1Name("AvaliableFiles");
            varSignalsFile[29].Set_Visible((byte)0);
            varSignalsFile[29].Set_Signal_Type((byte)2);
            varSignalsFile[29].Set_Signal_Command(110);
            varSignalsFile[29].Set_Desc("RecievedAvaliableFileList");
            varSignalsFile[29].Set_UsedCommand("Server");
            varSignalsFile[29].Set_ClassName("GetFile");
            /************************************/
         }
        }
        catch(Exception e)
        {e.printStackTrace();}
       
    }
    protected SignalsFile signalsfile(int x)
    {
        return varSignalsFile[x];
    }
    protected int sizeArray() {
        return mysizeArray;
    }
    
}
