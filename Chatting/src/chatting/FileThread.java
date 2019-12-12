/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatting;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.Random;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author SE130585
 */
public class FileThread extends Thread {

    Socket socket = null;
    BufferedReader br = null;
    String sender = null;
    String receiver = "";
    String savePath = "";
    JTextArea txtChat;
    final JFileChooser fc = new JFileChooser();

    public FileThread(Socket socket, String sender, String receiver, JTextArea txt) {
        super();
        this.socket = socket;
        this.sender = sender;
        this.txtChat = txt;
        this.savePath = savePath;
        System.out.println("Sender is: " + sender);
        this.receiver = receiver;
        try {
            br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Network Error");
            System.exit(0);
        }

    }

    public String creatNameFile(File f, String nameFile) {
        String result = f.getName();
        boolean change = false;
        while (true) {

            if (!result.contains(".")) {
                result = result + "_" + nameFile;
                change = true;
            }
            if (new File(f.getParent(), result).exists()) {
                Random rd = new Random();
                int i = rd.nextInt(99);
                result = i + "_" + result;
                change = true;
            } else {
                break;
            }
        }
        if (change) {
            JOptionPane.showMessageDialog(null, "Name file is exist, current name of file is \" " + result + " \" ");
        }
        System.out.println(result);
        return result;
    }

    public void run() {
        String line;
        while (true) {
            try {
                if (socket != null) {
                    if ((line = br.readLine()).equals(MD5.getMD5("sendfile"))) {
                        txtChat.append("\n" + receiver + ": Send a file");
                        if (JOptionPane.showConfirmDialog(null, "Have a file from " + receiver + ", are you want to down file?",
                                "To " + sender, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                            int result = fc.showSaveDialog(null);

                            if (result == 1) {
                                if (JOptionPane.showConfirmDialog(null, "Are you sure to skip the file?",
                                        "To " + sender, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                    txtChat.append("\n" + sender + ": Not receive");
                                    
                                }else{
                                result = fc.showSaveDialog(null);}
                            }
                            if (result != 1) {
                                String filename = br.readLine();

                                File f = new File(fc.getCurrentDirectory(), creatNameFile(fc.getSelectedFile(), filename));

                                PrintWriter pw = new PrintWriter(f);
                                while (!(line = br.readLine()).equals(MD5.getMD5("endfile"))) {
                                    pw.println(line);
                                }
                                pw.close();
                                txtChat.append("\n" + sender + ": got the file");
                            }
                        } else {
                            txtChat.append("\n" + sender + ": Not receive");
                            while (!(line = br.readLine()).equals(MD5.getMD5("endfile"))) {
                            }
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
    }

}
