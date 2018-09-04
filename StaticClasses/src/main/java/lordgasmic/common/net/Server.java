package lordgasmic.common.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private ServerSocket serverSocket;
	private Socket connectionSocket;
	private ObjectInputStream ois;

	public Server(int port) throws IOException{
		serverSocket = new ServerSocket(port);
		connectionSocket = serverSocket.accept();
		ois = new ObjectInputStream(connectionSocket.getInputStream());
	}
	
	public Object read() throws IOException, ClassNotFoundException {
		return ois.readObject();
	}

	public void close() throws IOException {
		ois.close();
		connectionSocket.close();
		serverSocket.close();
	}
	
}
