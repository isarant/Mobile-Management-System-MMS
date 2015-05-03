

package MMSServer;

 public class Session {
     public static SessionFile[] session;
     private static int sessionsize=0;
     private static ReadIncomigExternalSignal[] readrncomigexternalsignal;
    
    public Session()
    {}
    public SessionFile Get_Session(int NumOfSession)
    {
        if(NumOfSession>=0 && NumOfSession<=sessionsize)
        {
            return session[NumOfSession];
        }
        else
        {
            return null;
        }
    }
    public void Add_Session(SessionFile sessionfile,SocConnect sc)
    {
       SessionFile[] helpsession=new SessionFile[sessionsize+1];
       ReadIncomigExternalSignal[] helpreadrncomigexternalsignal=new ReadIncomigExternalSignal[sessionsize+1];
       if(sessionsize>0)
       {
            System.arraycopy(session, 0, helpsession, 0, session.length);
            System.arraycopy(readrncomigexternalsignal, 0, helpreadrncomigexternalsignal, 0, readrncomigexternalsignal.length);
       } 
       int a=helpsession.length-1;
       helpsession[a]=sessionfile;
       helpreadrncomigexternalsignal[a]=new ReadIncomigExternalSignal(sc,sessionfile);
       session=new SessionFile[helpsession.length];
       readrncomigexternalsignal=new ReadIncomigExternalSignal[helpsession.length];
       System.arraycopy(helpsession , 0, session, 0, helpsession.length); 
       System.arraycopy(helpreadrncomigexternalsignal , 0, readrncomigexternalsignal, 0, helpreadrncomigexternalsignal.length); 
       sessionsize=session.length;
    }

    public void Delete_Session(int NumOfSession)
    {
        readrncomigexternalsignal[NumOfSession].ReadIncomigExternalSignal_Close();
        if(session.length!=1)
        {
            SessionFile[] helpsession=new SessionFile[session.length-1];
            ReadIncomigExternalSignal[] helpreadrncomigexternalsignal=new ReadIncomigExternalSignal[session.length-1];
            if(NumOfSession>0)
            {
                System.arraycopy(session, 0, helpsession, 0, NumOfSession);
                System.arraycopy(readrncomigexternalsignal, 0, helpreadrncomigexternalsignal, 0, NumOfSession);
            }
            System.arraycopy(session, NumOfSession+1, helpsession, NumOfSession, session.length-(NumOfSession+1));
            System.arraycopy(readrncomigexternalsignal, NumOfSession+1, helpreadrncomigexternalsignal, NumOfSession, readrncomigexternalsignal.length-(NumOfSession+1));
            session=new SessionFile[helpsession.length];
            readrncomigexternalsignal=new ReadIncomigExternalSignal[helpsession.length];
            System.arraycopy(helpsession , 0, session, 0, helpsession.length); 
            System.arraycopy(helpreadrncomigexternalsignal , 0, readrncomigexternalsignal, 0, helpreadrncomigexternalsignal.length); 
            sessionsize=session.length;
        }
        else
        {
           session=null;
            sessionsize=0;
        }
    
    }
    public int Get_SizeOfSession()
    {
        return sessionsize;
    }
    
    public void refreshSessionFile(SocConnect sc)
    {
        try
        {
          	for(int x=0;x<session.length;x++)
            	{
              	// session[x].Set_SocConnectID(x);
                	int mynewConnectionid=-1;
                	try
                	{
                    		mynewConnectionid= sc.Get_Connection_Num_From_Client_IP(session[x].Get_IP());
                   	}
                        catch(Exception e)
                        {}
                        if(mynewConnectionid!=-1)
                        {
                            session[x].Set_SocConnectID(mynewConnectionid);
                            readrncomigexternalsignal[x].Change_Connection_Number(session[x]);
                        }
                }
        }
        catch(Exception e)
        {}
    }
}
