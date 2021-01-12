//broker side
import java.io.Serializable;

public class TransferData implements Serializable{
	private static final long serialVersionUID = 1L;
	
	String message, name, fileName, ipString, requestType;
	int port;
	boolean fileExists = false;
	
	public TransferData(String address, int port, String requestType) {
		this.ipString = address;
		this.port = port;
		this.fileName = "";
		this.requestType = requestType;
	}
	
	public TransferData() {
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setIPString(String ip) {
		this.ipString = ip;
	}
	
	public String getIPString() {
		return this.ipString;
	}
	
	public void setFile(String file) {
		this.fileName = file;
	}
	
	public String getFile() {
		return this.fileName;
	}
	
	public void printFileName() {
		System.out.println(this.fileName);
	}
	
	public void setMessage(String m) {
		this.message = m;
	} 
	
	public String getMessage() {
		return this.message;
	}
}
