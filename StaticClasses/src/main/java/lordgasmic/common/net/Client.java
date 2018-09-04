package lordgasmic.common.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket connectionSocket;
	private ObjectOutputStream oos;
		
	public Client(String ip, int port) throws UnknownHostException, IOException{
		connectionSocket = new Socket(ip, port);
		oos = new ObjectOutputStream(connectionSocket.getOutputStream());
	}

	public void send(Object message) throws IOException {
		oos.writeObject(message);
		oos.flush();
	}
	
	public void close() throws IOException{
		oos.close();
		connectionSocket.close();
	}
}
