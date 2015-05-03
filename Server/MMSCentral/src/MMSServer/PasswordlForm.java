
package MMSServer;

class PasswordlForm extends javax.swing.JFrame {
    private  ReadFile rf;
    private PaswdFile pf;
    private int recindex=0;
    private static  boolean Loading=false;
    
    public PasswordlForm() {
        
    }
    
    public boolean isLoading() {
        return Loading;
    }
    
    public void init() {
        Loading=true;
        initComponents();
        showpass();
    }
    
    protected void showpass() {
        list1.clear();
        
        pf=new PaswdFile();
        rf=new ReadFile("./System/paswdf.mms",pf.Get_Size());
        for(int x=1;x<=rf.NumOfRecords();x++) {
            pf.Set_PaswdFile_Byte(rf.Read(x));
            String s=" Client ID: " + pf.Get_ClientID();
            for(int q=0;q<=16-s.length();q++){s=s+ " ";}
            s=s+" Scenario: ";
            s=s + pf.Get_Scenario();
            for(int q=0;q<=31-s.length();q++){s=s+ " ";}
            s=s+" UserName: ";
            s=s+ pf.Get_UserName_No_Space();
            for(int q=0;q<=52-s.length();q++){s=s+ " ";}
            s=s+"   Password: ";
            s=s+ pf.Get_Passwd();
            s=s+"   KeyNum ";
            s=s+ pf.Get_KeyNum();
            list1.addItem(s);
        }
        rf.Close();
    }
    
    private void initComponents() {//GEN-BEGIN:initComponents
        list1 = new java.awt.List();
        btn_close = new java.awt.Button();
        btn_add = new java.awt.Button();
        btn_delete = new java.awt.Button();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setTitle("Signals");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        list1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                list1ActionPerformed(evt);
            }
        });

        getContentPane().add(list1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 740, 230));

        btn_close.setLabel("Close");
        btn_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_closeActionPerformed(evt);
            }
        });

        getContentPane().add(btn_close, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 310, 60, -1));

        btn_add.setLabel("Add");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        getContentPane().add(btn_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, 60, -1));

        btn_delete.setLabel("Delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        getContentPane().add(btn_delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 280, 60, -1));

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-783)/2, (screenSize.height-410)/2, 783, 410);
    }//GEN-END:initComponents
    
    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        pf=new PaswdFile();
        WriteFile wf=new WriteFile("./System/paswdf.mms");
        wf.Delete(list1.getSelectedIndex()+1,pf.Get_Size());
        wf.Close();
        showpass();
    }//GEN-LAST:event_btn_deleteActionPerformed
    
    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        Password_File_Form password_file_form=  new Password_File_Form();
        if(!password_file_form.isLoading()) {
            password_file_form.init(this,-1);
            password_file_form.show(true);
        }
        else
        {password_file_form.disable();}
        
    }//GEN-LAST:event_btn_addActionPerformed
    
    private void btn_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_closeActionPerformed
        Loading=false;
        this.hide();
        this.disable();
        
    }//GEN-LAST:event_btn_closeActionPerformed
    
    private void list1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_list1ActionPerformed
        Password_File_Form password_file_form=  new Password_File_Form();
        if(!password_file_form.isLoading()) {
            password_file_form.init(this,list1.getSelectedIndex()+1);
            password_file_form.show(true);
        }
        else
        {password_file_form.disable();}
    }//GEN-LAST:event_list1ActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        Loading=false;
        this.hide();
        this.disable();
    }//GEN-LAST:event_exitForm
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button btn_add;
    private java.awt.Button btn_close;
    private java.awt.Button btn_delete;
    private java.awt.List list1;
    // End of variables declaration//GEN-END:variables
    
}
