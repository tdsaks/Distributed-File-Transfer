import java.net.*;
import java.io.*;
public class PeerReceiveThread implements Runnable{
	
	Socket clntSock;
	SocketAddress clntAddress;
	int port;
	String ipString, fileName;
	InetAddress IP;
	
	public PeerReceiveThread(TransferData brokeredData) throws IOException{
		this.port = brokeredData.port;
		this.ipString = brokeredData.getIPString();
		this.IP = InetAddress.getByName(ipString);
		this.fileName = brokeredData.getFile();
	}
	
	
	public void run() {
		try{
			System.out.println(this.port +" "+ this.ipString + " "+this.fileName); //testing
			clntSock = new Socket(IP,port); 
			PeerThreadUtilities PTU = new PeerThreadUtilities(clntSock);
			
			PeerThreadUtilities.setFileName(fileName);

			System.out.println("Connection established."); 
			
			PTU.receiveFile();
			
			System.out.println("Download complete.");
		} catch (Exception e) {
		}
	}
}
