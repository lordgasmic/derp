package lordgasmic.common.atomic;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class LgcProducer {

	private Connection connection;
    private Destination destination;
    private Session session;
    private MessageProducer producer;
    
    private String user = ActiveMQConnection.DEFAULT_USER;
    private String password = ActiveMQConnection.DEFAULT_PASSWORD;
    private String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private String subject = "TOOL.DEFAULT";
    
    public void open() {
    	 try {
             // Create the connection.
         	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
             connection = connectionFactory.createConnection();
             connection.start();

             // Create the session
             session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
             destination = session.createTopic(subject);

             // Create the producer.
             producer = session.createProducer(destination);
             producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
         } catch (Exception e) {
             e.printStackTrace();
         } 
    }

	public void send(String string) throws JMSException {
		Message message = session.createTextMessage(string);
		send(message);
	}
	
	public void send(Serializable object) throws JMSException {
		Message message = session.createObjectMessage(object);
		send(message);
	}
	
	private void send(Message message) throws JMSException {
		producer.send(message);
	}
    
    public void close() throws JMSException{
    	connection.close();
    }

    public void setSubject(String subject) {
    	this.subject = subject;
    }
    
    public void setUrl(String url) {
    	this.url = url;
    }
}
