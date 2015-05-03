

package MMSServer;


class ControlProtocol {
protected static SignalsFile[] signalsfile;   
private int mysizeArray=30;
    public ControlProtocol()
    {
        signalsfile=new SignalsFile[mysizeArray];
        /************************************/
        signalsfile[0]=new SignalsFile();
        signalsfile[0].Set_Visible((byte)0);
        signalsfile[0].Set_Signal_Type((byte)1);
        signalsfile[0].Set_Signal_Command(0);
        signalsfile[0].Set_UsedCommand("Both");
        signalsfile[0].Set_Desc("LogOnSgnFile");
        signalsfile[0].Set_ClassName("ChkPasswdSendFile");
        /************************************/
        /************************************/
        signalsfile[1]=new SignalsFile();
        signalsfile[1].Set_Visible((byte)0);
        signalsfile[1].Set_Signal_Type((byte)2);
        signalsfile[1].Set_Signal_Command(0);
        signalsfile[1].Set_UsedCommand("Both");
        signalsfile[1].Set_Desc("AnsLogonandSentFile");
        signalsfile[1].Set_ClassName("AnsPasswdSendFile");
        /************************************/
        /************************************/
        signalsfile[2]=new SignalsFile();
        signalsfile[2].Set_Visible((byte)0);
        signalsfile[2].Set_UsedCommand("Both");
        signalsfile[2].Set_Signal_Type((byte)1);
        signalsfile[2].Set_Signal_Command(1);
        signalsfile[2].Set_Desc("LogonSignal");
        signalsfile[2].Set_ClassName("CheckPasswd");
        /************************************/
        /************************************/
        signalsfile[3]=new SignalsFile();
        signalsfile[3].Set_Visible((byte)0);
        signalsfile[3].Set_Signal_Type((byte)2);
        signalsfile[3].Set_Signal_Command(1);
        signalsfile[3].Set_UsedCommand("Both");
        signalsfile[3].Set_Desc("AnsLogonSignal");
        signalsfile[3].Set_ClassName("AnsCheckPasswd");
        /************************************/
        /************************************/
        signalsfile[4]=new SignalsFile();
        signalsfile[4].Set_Visible((byte)1);
        signalsfile[4].Set_Signal_Type((byte)2);
        signalsfile[4].Set_UsedCommand("Both");
        signalsfile[4].Set_Signal_Command(2);
        signalsfile[4].Set_Desc("Logoff");
        signalsfile[4].Set_ClassName("CheckCloseConnection");
        /************************************/
        /************************************/
        signalsfile[5]=new SignalsFile();
        signalsfile[5].Set_Visible((byte)0);
        signalsfile[5].Set_Signal_Type((byte)1);
        signalsfile[5].Set_Signal_Command(3);
        signalsfile[5].Set_UsedCommand("Both");
        signalsfile[5].Set_Desc("AreYouAlive");
        signalsfile[5].Set_ClassName("IAmAlive");
        /************************************/
        /************************************/
        signalsfile[6]=new SignalsFile();
        signalsfile[6].Set_Visible((byte)0);
        signalsfile[6].Set_Signal_Type((byte)2);
        signalsfile[6].Set_Signal_Command(3);
        signalsfile[6].Set_UsedCommand("Both");
        signalsfile[6].Set_Desc("IamAlive");
        signalsfile[6].Set_ClassName("ResetIAmAlive");
        /************************************/
        /************************************/
        signalsfile[7]=new SignalsFile();
        signalsfile[7].Set_UsedCommand("Both");
        signalsfile[7].Set_Visible((byte)0);
        signalsfile[7].Set_Parameters((byte)1);
        signalsfile[7].Set_Signal_Type((byte)2);
        signalsfile[7].Set_Signal_Command(4);
        signalsfile[7].Set_Desc("ChangeBufferSize");
        signalsfile[7].Set_ClassName("ChangeBufferSize");
        /************************************/
        /************************************/
        signalsfile[8]=new SignalsFile();
        signalsfile[8].Set_ParameterClass("FileManager");
        signalsfile[8].Set_Parameters((byte)1);
        signalsfile[8].Set_Parameter1Name("FileName");
        signalsfile[8].Set_Visible((byte)1);
        signalsfile[8].Set_Signal_Type((byte)1);
        signalsfile[8].Set_Signal_Command(100);
        signalsfile[8].Set_Desc("SendFile");
        signalsfile[8].Set_UsedCommand("Both");
        signalsfile[8].Set_ClassName("GetFile");
        /************************************/
         /************************************/
        signalsfile[9]=new SignalsFile();
        signalsfile[9].Set_ParameterClass("FileManager");
        signalsfile[9].Set_Parameters((byte)1);
        signalsfile[9].Set_Parameter1Name("FileName");
        signalsfile[9].Set_Visible((byte)0);
        signalsfile[9].Set_Signal_Type((byte)2);
        signalsfile[9].Set_Signal_Command(100);
        signalsfile[9].Set_Desc("RecievedSendedFile");
        signalsfile[9].Set_UsedCommand("Both");
        signalsfile[9].Set_ClassName("None");
        /************************************/
        /************************************/
        signalsfile[10]=new SignalsFile();
        signalsfile[10].Set_Visible((byte)1);
        signalsfile[10].Set_Parameters((byte)1);
        signalsfile[10].Set_Parameter1Name("FileName");
        signalsfile[10].Set_ParameterClass("SendMeFile");
        signalsfile[10].Set_Signal_Type((byte)1);
        signalsfile[10].Set_UsedCommand("Both");
        signalsfile[10].Set_Signal_Command(101);
        signalsfile[10].Set_Desc("SendMeFile");
        signalsfile[10].Set_ClassName("SendFile");
        /************************************/
        /************************************/
        signalsfile[11]=new SignalsFile();
        signalsfile[11].Set_Visible((byte)0);
        signalsfile[11].Set_Parameters((byte)1);
        signalsfile[11].Set_Parameter1Name("FileName");
        signalsfile[11].Set_Signal_Type((byte)2);
        signalsfile[11].Set_Signal_Command(101);
        signalsfile[11].Set_Desc("RecieveFile");
        signalsfile[11].Set_ClassName("GetFile");
        signalsfile[11].Set_UsedCommand("Both");
        /************************************/
          /************************************/
        signalsfile[12]=new SignalsFile();
        signalsfile[12].Set_ParameterClass("FileManager");
        signalsfile[12].Set_Parameters((byte)1);
        signalsfile[12].Set_Parameter1Name("ImageName");
        signalsfile[12].Set_Visible((byte)1);
        signalsfile[12].Set_Signal_Type((byte)1);
        signalsfile[12].Set_Signal_Command(102);
        signalsfile[12].Set_Desc("SendImage");
        signalsfile[12].Set_UsedCommand("Both");
        signalsfile[12].Set_ClassName("ShowSendImage");
        /************************************/
         /************************************/
        signalsfile[13]=new SignalsFile();
        signalsfile[13].Set_ParameterClass("FileManager");
        signalsfile[13].Set_Parameters((byte)1);
        signalsfile[13].Set_Parameter1Name("ImageName");
        signalsfile[13].Set_Visible((byte)0);
        signalsfile[13].Set_Signal_Type((byte)2);
        signalsfile[13].Set_Signal_Command(102);
        signalsfile[13].Set_Desc("RecievedImageFile");
        signalsfile[13].Set_UsedCommand("Both");
        signalsfile[13].Set_ClassName("None");
        /************************************/
         /************************************/
        signalsfile[14]=new SignalsFile();
        signalsfile[14].Set_ParameterClass("FileManager");
        signalsfile[14].Set_Parameters((byte)1);
        signalsfile[14].Set_Parameter1Name("MediaName");
        signalsfile[14].Set_Visible((byte)1);
        signalsfile[14].Set_Signal_Type((byte)1);
        signalsfile[14].Set_Signal_Command(103);
        signalsfile[14].Set_Desc("SendMedia");
        signalsfile[14].Set_UsedCommand("Both");
        signalsfile[14].Set_ClassName("PlayMedia");
        /************************************/
         /************************************/
        signalsfile[15]=new SignalsFile();
        signalsfile[15].Set_ParameterClass("FileManager");
        signalsfile[15].Set_Parameters((byte)1);
        signalsfile[15].Set_Parameter1Name("ImageName");
        signalsfile[15].Set_Visible((byte)0);
        signalsfile[15].Set_Signal_Type((byte)2);
        signalsfile[15].Set_Signal_Command(103);
        signalsfile[15].Set_Desc("RecievedMediadFile");
        signalsfile[15].Set_UsedCommand("Both");
        signalsfile[15].Set_ClassName("None");
        /************************************/
        /************************************/
        signalsfile[16]=new SignalsFile();
        signalsfile[16].Set_Visible((byte)1);
         signalsfile[16].Set_Signal_Type((byte)1);
        signalsfile[16].Set_Signal_Command(104);
        signalsfile[16].Set_Desc("GetActiveUsers");
        signalsfile[16].Set_UsedCommand("Client");
        signalsfile[16].Set_ClassName("SendUsersFile");
        /************************************/
         /************************************/
        signalsfile[17]=new SignalsFile();
        signalsfile[17].Set_Parameters((byte)1);
        signalsfile[17].Set_Parameter1Name("AcitveUsersFile");
        signalsfile[17].Set_Visible((byte)0);
        signalsfile[17].Set_Signal_Type((byte)2);
        signalsfile[17].Set_Signal_Command(104);
        signalsfile[17].Set_Desc("RecievedActiveUsersList");
        signalsfile[17].Set_UsedCommand("Server");
        signalsfile[17].Set_ClassName("GetFile");
        /************************************/
        /************************************/
        signalsfile[18]=new SignalsFile();
        signalsfile[18].Set_Visible((byte)1);
        signalsfile[18].Set_Signal_Type((byte)1);
        signalsfile[18].Set_Signal_Command(105);
        signalsfile[18].Set_Desc("GetAllUsers");
        signalsfile[18].Set_UsedCommand("Client");
        signalsfile[18].Set_ClassName("SendUsersFile");
        /************************************/
         /************************************/
        signalsfile[19]=new SignalsFile();
        signalsfile[19].Set_Parameters((byte)1);
        signalsfile[19].Set_Parameter1Name("AllUsersFile");
        signalsfile[19].Set_Visible((byte)0);
        signalsfile[19].Set_Signal_Type((byte)2);
        signalsfile[19].Set_Signal_Command(105);
        signalsfile[19].Set_Desc("RecievedAllUsersList");
        signalsfile[19].Set_UsedCommand("Server");
        signalsfile[19].Set_ClassName("GetFile");
        /************************************/
      /************************************/
        signalsfile[20]=new SignalsFile();
        signalsfile[20].Set_Parameters((byte)2);
        signalsfile[20].Set_Parameter1Name("UserName");
        signalsfile[20].Set_Parameter2Name("Message");
        signalsfile[20].Set_Visible((byte)1);
        signalsfile[20].Set_Signal_Type((byte)1);
        signalsfile[20].Set_Signal_Command(106);
        signalsfile[20].Set_ParameterClass("SendMsgtoUser");
        signalsfile[20].Set_Desc("SendMsgToUser");
        signalsfile[20].Set_UsedCommand("Client");
        signalsfile[20].Set_ClassName("ForwardMsg");
        /************************************/
       /************************************/
        signalsfile[21]=new SignalsFile();
        signalsfile[21].Set_Parameters((byte)1);
        signalsfile[21].Set_Parameter1Name("UserName");
        signalsfile[21].Set_Visible((byte)0);
        signalsfile[21].Set_Signal_Type((byte)2);
        signalsfile[21].Set_Signal_Command(106);
        signalsfile[21].Set_Desc("SendingMsg");
        signalsfile[21].Set_UsedCommand("Both");
        signalsfile[21].Set_ClassName("None");
        /************************************/
         /************************************/
        signalsfile[22]=new SignalsFile();
        signalsfile[22].Set_Parameters((byte)2);
        signalsfile[22].Set_Parameter1Name("UserName");
        signalsfile[22].Set_Parameter2Name("Message");
        signalsfile[22].Set_Visible((byte)0);
        signalsfile[22].Set_Signal_Type((byte)1);
        signalsfile[22].Set_Signal_Command(107);
        signalsfile[22].Set_Desc("RecieveAMsg");
        signalsfile[22].Set_UsedCommand("Server");
        signalsfile[22].Set_ClassName("ShowMsg");
        /************************************/  
        /************************************/
        signalsfile[23]=new SignalsFile();
        signalsfile[23].Set_Parameters((byte)1);
        signalsfile[23].Set_Parameter1Name("UserName");
        signalsfile[23].Set_Visible((byte)0);
        signalsfile[23].Set_Signal_Type((byte)2);
        signalsfile[23].Set_Signal_Command(107);
        signalsfile[23].Set_Desc("MsgRecievedFromUser");
        signalsfile[23].Set_UsedCommand("Both");
        signalsfile[23].Set_ClassName("None");
        /************************************/  
         /************************************/
        signalsfile[24]=new SignalsFile();
        signalsfile[24].Set_Parameters((byte)1);
        signalsfile[24].Set_Parameter1Name("par1");
        signalsfile[24].Set_Visible((byte)1);
        signalsfile[24].Set_Signal_Type((byte)1);
        signalsfile[24].Set_Signal_Command(108);
        signalsfile[24].Set_ParameterClass("SendFiletoUser");
        signalsfile[24].Set_Desc("SendFiletoUser");
        signalsfile[24].Set_UsedCommand("Client");
        signalsfile[24].Set_ClassName("ForwardFile");
        /************************************/
       /************************************/
        signalsfile[25]=new SignalsFile();
        signalsfile[25].Set_Parameters((byte)2);
        signalsfile[25].Set_Parameter1Name("UserName");
        signalsfile[25].Set_Parameter1Name("FileName");
        signalsfile[25].Set_Visible((byte)0);
        signalsfile[25].Set_Signal_Type((byte)2);
        signalsfile[25].Set_Signal_Command(108);
        signalsfile[25].Set_Desc("SendingFileMsg");
        signalsfile[25].Set_UsedCommand("Both");
        signalsfile[25].Set_ClassName("None");
        /************************************/
         /************************************/
        signalsfile[26]=new SignalsFile();
        signalsfile[26].Set_Parameters((byte)1);
        signalsfile[26].Set_Parameter1Name("UserName");
        signalsfile[26].Set_Visible((byte)0);
        signalsfile[26].Set_Signal_Type((byte)1);
        signalsfile[26].Set_Signal_Command(109);
        signalsfile[26].Set_Desc("RecieveAFileMsg");
        signalsfile[26].Set_UsedCommand("Server");
        signalsfile[26].Set_ClassName("ShowFileMsg");
        /************************************/  
        /************************************/
        signalsfile[27]=new SignalsFile();
        signalsfile[27].Set_Parameters((byte)2);
        signalsfile[27].Set_Parameter1Name("UserName");
        signalsfile[27].Set_Parameter1Name("FileName");
        signalsfile[27].Set_Visible((byte)0);
        signalsfile[27].Set_Signal_Type((byte)2);
        signalsfile[27].Set_Signal_Command(109);
        signalsfile[27].Set_Desc("FileMsgRecievedFromUser");
        signalsfile[27].Set_UsedCommand("Both");
        signalsfile[27].Set_ClassName("None");
        /************************************/ 
         /************************************/  
        signalsfile[28]=new SignalsFile();
        signalsfile[28].Set_Visible((byte)1);
        signalsfile[28].Set_Signal_Type((byte)1);
        signalsfile[28].Set_Signal_Command(110);
        signalsfile[28].Set_Desc("GetAvaliableFiles");
        signalsfile[28].Set_UsedCommand("Client");
        signalsfile[28].Set_ClassName("SendAvaliableFiles");
        /************************************/
         /************************************/
        signalsfile[29]=new SignalsFile();
        signalsfile[29].Set_Parameters((byte)1);
        signalsfile[29].Set_Parameter1Name("AvaliableFiles");
        signalsfile[29].Set_Visible((byte)0);
        signalsfile[29].Set_Signal_Type((byte)2);
        signalsfile[29].Set_Signal_Command(110);
        signalsfile[29].Set_Desc("RecievedAvaliableFileList");
        signalsfile[29].Set_UsedCommand("Server");
        signalsfile[29].Set_ClassName("GetFile");
        /************************************/
        
        
    }
    protected int sizeArray()
    {
        return mysizeArray;
    }
    
}
