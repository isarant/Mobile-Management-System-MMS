

package MMSServer;

import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.Date;

public class ShowExteralSignalFile extends javax.swing.JFrame {
    private RandomAccessFile raf;
    private  MP mp;
    private static boolean Loading=false;
    public ShowExteralSignalFile() {
        
    }
    
     public boolean isLoading()
    {
        return Loading;
    }
     public void init()
    {
        Loading=true; 
        initComponents();
    }
    private void initComponents() {//GEN-BEGIN:initComponents
        list_extsignal = new java.awt.List();
        btn_close = new java.awt.Button();
        btn_refresh = new java.awt.Button();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        getContentPane().add(list_extsignal, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 610, 180));

        btn_close.setLabel("Close");
        btn_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_closeActionPerformed(evt);
            }
        });

        getContentPane().add(btn_close, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 260, -1, -1));

        btn_refresh.setLabel("Refresh");
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        getContentPane().add(btn_refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 220, 70, -1));

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-642)/2, (screenSize.height-334)/2, 642, 334);
    }//GEN-END:initComponents

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
      boolean ReadIncomigExternalSignalEnabled=true;
      int index=0;
      int raflen=0;
      list_extsignal.clear();
      
      try 
        {
         raf=new RandomAccessFile("./System/ExtSignalFile.mms","r");
         index=0;
        
         raflen=(int)raf.length();
        }
      catch(IOException e)
       { raflen=0;}
      //while(index<raflen)
      try
      {
           raflen=(int)raf.length();
      }
      catch(Exception e)
      {}
      while(raflen>index)
      {
       try 
          {
               mp=new MP();
              raf.seek(index);
              mp.Set_Sender(raf.readByte());
              byte varReciever=raf.readByte();
              mp.Set_Receiver(varReciever);
              mp.Set_Scenario(raf.readInt());
              mp.Set_Signal_Type(raf.readByte());
              mp.Set_Signal_Command(raf.readInt());
              mp.Set_Priority(raf.readByte());
              mp.Set_Date_Time(raf.readLong());
              int data1size=0;
              data1size=raf.readInt();
              if(data1size>0)
               {
                   byte[] data1=new byte[data1size]; 
                   raf.read(data1);
                   mp.Set_Data1(data1);
                }
               int data2size=0;
               data2size=raf.readInt();
               if(data2size>0)
                {
                    byte[] data2=new byte[data2size]; 
		     raf.read(data2);
                     mp.Set_Data2(data2);
                 }
               index=index+mp.Get_MMP_Size();
               raflen=(int)raf.length();
               Date date=new Date(mp.Get_Date_Time());
               String str="sender: " + mp.Get_Sender() +
               " reciever: " + mp.Get_Receiver() +
               " senario: " + mp.Get_Scenario() +
               " type: " + mp.Get_Signal_Type() +
               " command: " + mp.Get_Signal_Command() +
               " priority: " + mp.Get_Priority() +
               " date: " + date.toString() +
               " data 1: " + mp.Get_Data1_As_String() +
               " data 2: " + mp.Get_Data2_As_String() ;
               list_extsignal.addItem(str);
           }
           catch(IOException e)
           {}
           catch(Exception e)
           {}
      }
        
      try 
        {
         raf.close();
        }
      catch(IOException e)
       { }
      
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_closeActionPerformed
        Loading=false;
        this.hide();
        this.disable();
    }//GEN-LAST:event_btn_closeActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
       Loading=false;
        this.hide();
        this.disable();
    }//GEN-LAST:event_exitForm
    
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button btn_close;
    private java.awt.Button btn_refresh;
    private java.awt.List list_extsignal;
    // End of variables declaration//GEN-END:variables
    
}
