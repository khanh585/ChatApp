/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatting;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author SE130585
 */
public class ManagerChatter extends javax.swing.JFrame implements Runnable {

    ServerSocket srvSocket = null;
    ServerSocket srvSocketF = null;
    BufferedReader br = null;
    Thread t; //thread to connect to staff
  
    public ManagerChatter() {
        initComponents();
        this.setSize(600, 550);
        this.txtServerPort.setText("13058");
        int serverPort = Integer.parseInt(this.txtServerPort.getText());
        try {
            srvSocket = new ServerSocket(serverPort);
            srvSocketF = new ServerSocket(serverPort+1);
            this.lbMessage.setText("Mng. Server is running at the port:" + srvSocket.getLocalPort());
        } catch (Exception e) {
        }
        t = new Thread(this);
        t.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lbMessage = new javax.swing.JLabel();
        txtServerPort = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel1.setLayout(new java.awt.GridLayout());

        lbMessage.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbMessage.setText("Manager Port:");
        lbMessage.setCursor(new java.awt.Cursor(java.awt.Cursor.N_RESIZE_CURSOR));
        lbMessage.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel1.add(lbMessage);
        jPanel1.add(txtServerPort);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);
        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ManagerChatter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManagerChatter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManagerChatter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManagerChatter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManagerChatter().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbMessage;
    private javax.swing.JTextField txtServerPort;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {

        while (true) {
            try {
                Socket aStaffSocket = srvSocket.accept();
                Socket fileSocket = srvSocketF.accept();
                if (aStaffSocket != null) {
                    br = new BufferedReader(new InputStreamReader(aStaffSocket.getInputStream()));
                    String S = br.readLine();
                    int pos = S.indexOf(':'); //format Hoa: sdfasdf
                    String staffName = S.substring(pos + 1); //getname
                    ChatPanel p = new ChatPanel(aStaffSocket, fileSocket,"Manager", staffName);
                    System.out.println("Iam sender: Manager");
                    this.jTabbedPane1.add(staffName, p);
                    p.updateUI();

                }
                Thread.sleep(1000);

            } catch (Exception e) {

            }

        }

    }
}
