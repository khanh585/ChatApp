/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatting;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author SE130585
 */
public class OutputThread extends Thread {

    Socket socket;
    JTextArea txt;
    BufferedReader br;
    String sender;
    String receiver;

    public OutputThread(Socket socket, JTextArea txt, String sender, String receiver) {
        super();
        this.socket = socket;
        this.txt = txt;
        this.sender = sender;
        System.out.println("Sender is: " + sender);
        this.receiver = receiver;
        try {
            br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Network Error");
            System.exit(0);
        }

    }

    

    public void run() {
        while (true) {
            try {
                if (socket != null) {
                    String msg = ""; // get data from inputstream
                    while ((msg = br.readLine()) != null) {
                        txt.append("\n" + receiver + ": " + msg);
                    }
                    sleep(700);
                }
            } catch (Exception e) {
            }
        }
    }

}
