import java.net.*;
import java.io.*;
//broker side
public class TransferUtilities {
	int servPort;
	static ServerSocket servSocket;
	static Socket clntSock;
	SocketAddress clntAddress;
	
	public TransferUtilities(int port) {
		this.servPort = port;
	}
	
	public void setUpSocket() throws IOException{
		try {
			servSocket = new ServerSocket(servPort);
			System.out.println("Broker is up and running.");
		} catch(IOException e) {}	
	}
	
	public void handlePeer() throws IOException {
		try {
			clntSock = servSocket.accept();
			clntAddress = clntSock.getRemoteSocketAddress();
			System.out.println("Handling Peer at: " + clntAddress);
		} catch(IOException e) {}
	}
	

	public static TransferData receiveDataFromClient() throws IOException{
		SocketAddress clientAddress = clntSock.getRemoteSocketAddress();
		int port = clntSock.getPort();
		
		TransferData fromClient = null;
		
		try {
			InputStream is = clntSock.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			fromClient = (TransferData) ois.readObject();
			
		}  catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (EOFException e) { 
			System.out.println("in receive EOF: goodbye client at " + clientAddress + " with port# " + port);
			clntSock.close(); // Close the socket. We are done with this client!
		}
		catch (IOException e) {
			System.out.println("in receive IO: goodbye client at " + clientAddress + " with port# " + port);
			clntSock.close(); //
		}
		return fromClient;
		
	}
	
	public static void sendDataBackToClient(TransferData fromClient) throws IOException{
		TransferData toClient = null;
		try {
			OutputStream os = clntSock.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			toClient = fromClient;
			oos.writeObject(toClient);

		}  catch (EOFException e) { // needed to catch when client is done
			System.out.println("in Send EOFException: goodbye client at " + clntSock.getRemoteSocketAddress() + " with port# " + clntSock.getPort());
			clntSock.close(); // Close the socket. We are done with this client!
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("in Send IOException: goodbye client at " + clntSock.getRemoteSocketAddress() + " with port# " + clntSock.getPort());
			clntSock.close(); //
		}
	}
}