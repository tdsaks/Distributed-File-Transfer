import java.io.*;
import java.net.*;

public class PeerSendThread implements Runnable{
	PeerThreadUtilities PTU;
	Socket socket;
	ServerSocket servSocket;
	InputStream inStream;
	OutputStream outStream;
	
	int port;
	String ipString;
	InetAddress IP;
	
	String fileName;
	
	public PeerSendThread(int port, String ip) throws Exception{
		this.port = port;
		this.ipString = ip;
		this.IP = InetAddress.getByName(ipString);
		servSocket = new ServerSocket(port);
		
	}
	
	public void run() {
		while(true) {
			try {
				PTU = new PeerThreadUtilities(servSocket.accept());
				System.out.println("Connection established."); //testing
				
				System.out.println("Responding to peer request.");
				PTU.sendFile();
				
				
			} catch (IOException e) {
				System.out.println("Spotted IOException");
				e.printStackTrace();
			} catch(Exception e) {
				System.out.println("Spotted Exception");
			}
		}
	}
}
