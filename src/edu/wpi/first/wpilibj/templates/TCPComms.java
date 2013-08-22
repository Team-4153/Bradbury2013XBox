package edu.wpi.first.wpilibj.templates;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 4153student
 */
import com.sun.squawk.io.BufferedReader;
import java.io.*;
import javax.microedition.io.*;
public class TCPComms {
    SocketConnection socketConnect;
    InputStream myInputStream;
    DataInputStream data;
    BufferedReader in;
    public void init(){
	try {
            socketConnect = (SocketConnection) Connector.open("socket://10.41.53.2:5200");
            myInputStream = socketConnect.openInputStream();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void updateTCP() {
        try {
            int b;
            while ((b = myInputStream.read()) != -1) {
                System.out.println(b);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
