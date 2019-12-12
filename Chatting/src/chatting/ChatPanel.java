/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatting;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author SE130585
 */
public class ChatPanel extends javax.swing.JPanel {

    /**
     * Creates new form ChatPanel
     */
    Socket socket1 = null; // socket cho chat
    BufferedReader br = null;
    DataOutputStream os = null;
    OutputThread t = null;
    String sender;
    String receiver;

    Socket socket2 = null;   // socket cho file
    BufferedReader brF = null;
    DataOutputStream osF = null;
    FileThread ft = null;
    String savePath = "";

    final JFileChooser fc = new JFileChooser();

    public ChatPanel(Socket s, Socket sf, String sender, String receiver) {
        initComponents();
        txtChat.setEditable(false);
        socket1 = s;
        socket2 = sf;
        this.sender = sender;
        this.receiver = receiver;
        try {
            br = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
            os = new DataOutputStream(socket1.getOutputStream());
            t = new OutputThread(socket1, txtChat, this.sender, this.receiver);

            brF = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
            osF = new DataOutputStream(socket2.getOutputStream());
            ft = new FileThread(socket2, this.sender, this.receiver, this.txtChat);

            ft.start();
            t.start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Manager is offline");
        }

    }

    public JTextArea getMessage() {
        return this.txtChat;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtChatBox = new javax.swing.JScrollPane();
        txtChat = new javax.swing.JTextArea();
        pnMessage = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMessage = new javax.swing.JTextArea();
        pnSend = new javax.swing.JPanel();
        btSend = new javax.swing.JButton();
        btSendFile = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        txtChat.setColumns(20);
        txtChat.setRows(5);
        txtChatBox.setViewportView(txtChat);

        add(txtChatBox, java.awt.BorderLayout.CENTER);

        pnMessage.setBorder(javax.swing.BorderFactory.createTitledBorder("Message"));
        pnMessage.setLayout(new java.awt.GridLayout(1, 0));

        txtMessage.setColumns(20);
        txtMessage.setRows(5);
        txtMessage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMessageKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMessageKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(txtMessage);

        pnMessage.add(jScrollPane1);

        pnSend.setLayout(new java.awt.GridLayout(1, 0));

        btSend.setText("Send");
        btSend.setToolTipText("");
        btSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSendActionPerformed(evt);
            }
        });
        pnSend.add(btSend);

        btSendFile.setText("Send File");
        btSendFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSendFileActionPerformed(evt);
            }
        });
        pnSend.add(btSendFile);

        pnMessage.add(pnSend);

        add(pnMessage, java.awt.BorderLayout.PAGE_END);
        add(jPanel1, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void btSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSendActionPerformed
        // TODO add your handling code here:
        if (txtMessage.getText().trim().length() == 0) {
            return;
        }

        try {
            os.writeBytes(txtMessage.getText());
            os.write(13);
            os.write(10);
            os.flush();
            this.txtChat.append("\n" + sender + ": " + txtMessage.getText());
            txtMessage.setText("");
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btSendActionPerformed

    private void txtMessageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMessageKeyPressed

    }//GEN-LAST:event_txtMessageKeyPressed

    private void txtMessageKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMessageKeyTyped
    }//GEN-LAST:event_txtMessageKeyTyped

    private void btSendFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSendFileActionPerformed
        // TODO add your handling code here:
        try {
            int result = fc.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                if (fc.getSelectedFile() != null) {
                    osF.writeBytes(MD5.getMD5("sendfile"));
                    osF.write(13);
                    osF.write(10);
                    osF.flush();
                    File f = fc.getSelectedFile();
                    System.out.println(f.getName());
                    osF.writeBytes(f.getName());
                    osF.write(13);
                    osF.write(10);
                    FileReader fr = new FileReader(f);
                    BufferedReader b = new BufferedReader(fr);
                    String line = null;
                    while ((line = b.readLine()) != null) {
                        osF.writeBytes(line);
                        osF.write(13);
                        osF.write(10);
                        osF.flush();
                    }
                    b.close();
                    fr.close();
                    osF.writeBytes(MD5.getMD5("endfile"));
                    osF.write(13);
                    osF.write(10);
                    osF.flush();

                }
            } else {
                JOptionPane.showMessageDialog(this, "Folder", "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
        }

    }//GEN-LAST:event_btSendFileActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btSend;
    private javax.swing.JButton btSendFile;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnMessage;
    private javax.swing.JPanel pnSend;
    private javax.swing.JTextArea txtChat;
    private javax.swing.JScrollPane txtChatBox;
    private javax.swing.JTextArea txtMessage;
    // End of variables declaration//GEN-END:variables

    private String showDialog() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
