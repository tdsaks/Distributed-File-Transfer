import java.net.*;
import java.io.*;
import java.util.Scanner;
public class PeerThreadUtilities implements Serializable{
	private static final long serialVersionUID = 1L;

	static Socket socket;
	static ServerSocket servSocket;
	static String fileName;
	
	
	public PeerThreadUtilities(Socket s) {
		socket = s;
	}
	
	public PeerThreadUtilities(Socket s, String fn) {
		socket = s;
		fileName = fn;
	}
	
	
	public static String getFileName() {
		return fileName;
	}
	
	public static void setFileName(String name) {
		fileName = name;
	}
	
	public void sendToTerminal(String prompt) {
		System.out.println(prompt);
	}
	
	
	
	public void sendFile() throws IOException{
		try {
			File infile = new File(fileName);
			System.out.println(infile.exists());
			Scanner fromInputFile = new Scanner(infile).useDelimiter("\\n|\\r");

			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			
			while(fromInputFile.hasNextLine()) {
				String lineFromInputFile = fromInputFile.nextLine();
				oos.writeObject(lineFromInputFile);
			}
			oos.writeObject("finished");
			
			fromInputFile.close();
			
			
		} catch (IOException e) {
			System.out.println("Spotted IOException");
		} 
	}
	
	public void receiveFile() throws IOException{
		try {
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(fw);
			
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			
			String message = "";
			while(true) {
				message = (String) ois.readObject();
				if(message.equals("finished")) {
					break;
				}
				bw.write(message + "\n");
			}
			
			bw.close();
			
		} catch (IOException e) {
			System.out.println("Spotted IOException");
		} catch (ClassNotFoundException e) {
			System.out.println("Spotted ClassNotFoundExceptoin e");
		}
	}
	
	
}
