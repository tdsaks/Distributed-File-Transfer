import java.net.*;
import java.util.Scanner;
public class Peer {
	static final int DEFAULT_PORT = 5001;
	
	public static void main(String[] args) {
		Scanner fromKeyboard = new Scanner(System.in);
		String ipString = getMyIPAddress();
		String name, userInput, fileName;
		TransferUtilities TU;
		TransferData toSend, toReceive;
		int servPort = 5000;
		
		System.out.println("Listening on port: " + DEFAULT_PORT);
		System.out.println("Welcome. Please enter your name.");
		name = fromKeyboard.nextLine();
		
		while(true) {
			System.out.println("Welcome, " + name + " What would you like to do? Enter r to register a file, f to find a file, l to log out");
			userInput = fromKeyboard.nextLine();
			
			if(userInput.equals("r")) {
				String requestType = "r";
				try {
					System.out.println("You have chosen to register a file.");
					
					PeerSendThread PST = new PeerSendThread(DEFAULT_PORT,ipString); 
					
					TU = new TransferUtilities(ipString,servPort);
					TU.setUpSocket();

					System.out.println("Please enter the name of the file you would like to register, including the .txt extension if applicable.");
					fileName = fromKeyboard.nextLine();
					TU.setFile(fileName);
					
					toSend = new TransferData(ipString,DEFAULT_PORT,requestType);
					toSend.setFile(fileName);
					TransferUtilities.sendDataToServer(toSend);
					
					System.out.println("Your title has been registered.");
					
					new Thread(PST).start();
				} catch(Exception e) {
					
				} 
			
			} else if(userInput.equals("f")) {
				String requestType = "f";
				
				try {
					System.out.println("You have chosen to find a file.");
					
					PeerReceiveThread PRT;
					
					TU = new TransferUtilities(ipString,servPort);
					TU.setUpSocket();
					
					System.out.println("Please enter the name of the file you would like to find, including the .txt extension if applicable.");
					fileName = fromKeyboard.nextLine();
					TU.setFile(fileName);
					
					toSend = new TransferData(ipString,servPort,requestType);
					toSend.setFile(fileName);
					TransferUtilities.sendDataToServer(toSend);
					
					toReceive = TransferUtilities.receiveDataFromServer();
					toReceive.printFileName(); //testing
					
					if(!toReceive.fileExists) {
						System.out.println("File does not exist in the database. Pleaes try again.");
					} else {
						System.out.println("Your file has been found. Initiating connection...");
						PRT = new PeerReceiveThread(toReceive);
						new Thread(PRT).start();
					}
					
					
				} catch(Exception e) {}

				
			} else if(userInput.equals("l")) {
				System.out.println("Logging out.");
				break;
			} else {
				System.out.println("Invalid input. Please try again.");
			}
		}
		
		fromKeyboard.close();
		

	}

	
	
	

	public static String getMyIPAddress() {
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.out.println("Could not find local address!");
		}
		return address.getHostAddress();
}
}


