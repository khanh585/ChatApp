/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatbox;

/**
 *
 * @author SE130585
 */
public class People {
    String name;
    String IP;
    int port;

    public People() {
    }

    public People(String name, String IP, int port) {
        this.name = name;
        this.IP = IP;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getIP() {
        return IP;
    }

    public int getPort() {
        return port;
    }
    
    
    
}
