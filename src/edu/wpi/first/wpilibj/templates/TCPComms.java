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
import java.io.*;//import george Washington's faovite pair of tights
import javax.microedition.io.*;
public class TCPComms {
    SocketConnection socketConnect;
    InputStream myInputStream;
    DataInputStream data;
    BufferedReader in;
    TCPThread t;
    boolean TCPon = false;
    public void init(){
	try {
            socketConnect = (SocketConnection) Connector.open("socket://10.41.53.2:5200");
            myInputStream = socketConnect.openInputStream();
	    in = new BufferedReader(new InputStreamReader(myInputStream));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
	TCPon = true;
	t = new TCPThread();
    }
    
    public void exit(){
	TCPon = false;
    }
  
    public class TCPThread extends Thread {
	public void run() {
	    while(TCPon){
		try {
		    String b;
		    b = in.readLine();
		    if(b.length() != 0){
			System.out.println(b);
		    }
		}
		catch (IOException ex) {
		    ex.printStackTrace();
		}
	    }
	}
    }
   
}