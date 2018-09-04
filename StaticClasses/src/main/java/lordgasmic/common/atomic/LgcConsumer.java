package lordgasmic.common.atomic;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class LgcConsumer implements MessageListener, ExceptionListener {

	private boolean running;

	private Session session;
	private Destination destination;
	private Connection connection;
	private MessageConsumer consumer;

	private String subject = "TOOL.DEFAULT";
	private String user = ActiveMQConnection.DEFAULT_USER;
	private String password = ActiveMQConnection.DEFAULT_PASSWORD;
	private String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private int ackMode = Session.AUTO_ACKNOWLEDGE;
	
	private volatile List<Message> messages;

	public void open() {
		try {
			running = true;
			messages = new Vector<Message>();

			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
			connection = connectionFactory.createConnection();

			connection.setExceptionListener(this);
			connection.start();

			session = connection.createSession(false, ackMode);
			destination = session.createTopic(subject);

			consumer = session.createConsumer(destination);

			consumer.setMessageListener(this);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onMessage(Message message) {
		try {
			messages.add(message);
			
			if (message instanceof TextMessage) {
				TextMessage txtMsg = (TextMessage) message;
				String msg = txtMsg.getText();
				int length = msg.length();
				if (length > 50) {
					msg = msg.substring(0, 50) + "...";
				}
				System.out.println("[" + "] Received: '" + msg + "' (length " + length + ")");
			} 
			else {

			}
		} 
		catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public synchronized void onException(JMSException ex) {
		System.out.println("[" + "] JMS Exception occured.  Shutting down client.");
		running = false;
	}

	synchronized boolean isRunning() {
		return running;
	}
	
	public List<Message> getMessages() {
		List<Message> tempList = new Vector<Message>();
		
		synchronized (messages) {
			tempList.addAll(messages);
			messages.clear();
		}
		
		return tempList;
	}

	public void close() throws JMSException, IOException {
		System.out.println("[" + "] Closing connection");
		consumer.close();
		session.close();
		connection.close();

		System.out.println("[" + "] Press return to shut down");
		System.in.read();
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
