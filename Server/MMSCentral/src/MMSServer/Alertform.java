
package MMSServer;

public class Alertform extends javax.swing.JFrame {
    
    public Alertform(String str) {
        initComponents();
        jLabel_alert.setText(str);
    }
    
   
   
    private void initComponents() {//GEN-BEGIN:initComponents
        jButton_close = new javax.swing.JButton();
        jLabel_alert = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setTitle("Alert");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jButton_close.setText("Close");
        jButton_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_closeActionPerformed(evt);
            }
        });

        getContentPane().add(jButton_close, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, -1, -1));

        jLabel_alert.setFont(new java.awt.Font("MS Sans Serif", 0, 12));
        jLabel_alert.setText("jLabel1");
        getContentPane().add(jLabel_alert, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 290, 50));

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-315)/2, (screenSize.height-161)/2, 315, 161);
    }//GEN-END:initComponents

    private void jButton_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_closeActionPerformed
         this.hide();
        this.disable();
    }//GEN-LAST:event_jButton_closeActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        this.hide();
        this.disable();
    }//GEN-LAST:event_exitForm
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_close;
    private javax.swing.JLabel jLabel_alert;
    // End of variables declaration//GEN-END:variables
    
}
