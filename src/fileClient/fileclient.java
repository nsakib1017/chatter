/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileClient;

import java.io.*;
import java.net.*;
import java.util.Random;

public class fileclient{
    
static protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
public static void main(String[] args) throws Exception {

byte []b=new byte[20002];
Socket sr=new Socket("localhost",80);
InputStream is=sr.getInputStream();
String string=getSaltString();
FileOutputStream fr=new FileOutputStream("F:\\"+string+".txt");
is.read(b,0,b.length);
fr.write(b, 0, b.length);
}
}