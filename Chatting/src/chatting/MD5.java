/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatting;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author SE130585
 */
public class MD5 {

    private static String convertByteToHex(byte[] data) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            String hex = Integer.toHexString(0xff & data[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    public static String getMD5(String input) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-1");
      byte[] messageDigest = md.digest(input.getBytes());
      return convertByteToHex(messageDigest);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
    
}
