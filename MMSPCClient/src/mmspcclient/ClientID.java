/*
 * ClientID.java
 *
 * Created on 23 Ã‹˙ÔÚ 2006, 9:40 ÏÏ
 */

package mmspcclient;

/**
 *
 * @author  giannisdemo
 */
public class ClientID extends javax.swing.JFrame {
    
    /** Creates new form ClientID */
    public ClientID() {
        initComponents();
        textID.setText(String.valueOf(GetClientID()));
       rootPane.setDefaultButton(null);
       
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        textID = new java.awt.TextField();
        buttonSave = new java.awt.Button();
        label1 = new java.awt.Label();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Client ID");
        setName("ClientIDForm");
        setResizable(false);
        getAccessibleContext().setAccessibleParent(this);

        buttonSave.setLabel("Save");
        buttonSave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                buttonSaveKeyTyped(evt);
            }
        });
        buttonSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonSaveMouseClicked(evt);
            }
        });

        label1.setText("Set Client ID");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(label1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(77, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(textID, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                        .addContainerGap())
                    .add(layout.createSequentialGroup()
                        .add(buttonSave, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(label1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(textID, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(1, 1, 1)
                .add(buttonSave, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonSaveKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buttonSaveKeyTyped
     
    }//GEN-LAST:event_buttonSaveKeyTyped

    private void buttonSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonSaveMouseClicked
             byte[] mycid=new byte[1];
            try {
                mycid[0]=(Byte.parseByte(textID.getText()));
            }
            catch(Exception e) {
               
            }
            if(mycid[0]>0) {
                try{
                    java.io.File f=new java.io.File("clientid.mms");
                    f.delete();
                }
                catch(Exception e)
                {}
                WriteFile wf1=new WriteFile("clientid.mms");
                wf1.Write(mycid);
                wf1.Close();
            }
    }//GEN-LAST:event_buttonSaveMouseClicked
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button buttonSave;
    private java.awt.Label label1;
    private java.awt.TextField textID;
    // End of variables declaration//GEN-END:variables
    
    public byte GetClientID() {
        byte[] myclientid=new byte[1];
        ReadFile rf=new ReadFile("clientid.mms");
        if (rf.NumOfRecords()>0) {
            myclientid=rf.Read();
            rf.Close();
        }
        else {
            myclientid[0]=(byte)0;
        }
        return (byte) myclientid[0];
    }
    
  
}
