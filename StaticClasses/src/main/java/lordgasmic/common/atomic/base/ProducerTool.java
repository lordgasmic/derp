package lordgasmic.common.atomic.base;

import java.io.Serializable;
import java.util.Date;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * A simple tool for publishing messages
 * 
 * @version $Revision: 1.2 $
 */
public class ProducerTool extends Thread {

    private Destination destination;
    private Session session;
    private MessageProducer producer;
    
    private String user = ActiveMQConnection.DEFAULT_USER;
    private String password = ActiveMQConnection.DEFAULT_PASSWORD;
    private String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private String subject = "TOOL.DEFAULT";
    
    private int message = 100;

    public static void main(String[] args) throws InterruptedException {
        ProducerTool producerTool = new ProducerTool();
        producerTool.start();
        producerTool.join();

        System.exit(0);
    }

    public void run() {
        Connection connection = null;
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

            // Start sending messages
            sendLoop(session, producer);

        } catch (Exception e) {
            System.out.println("[" + this.getName() + "] Caught: " + e);
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

    protected void sendLoop(Session session, MessageProducer producer) throws Exception {

        for (int i = 0; i < message; i++) {

            TextMessage message = session.createTextMessage(createMessageText(i));

            String msg = message.getText();
            if (msg.length() > 50) {
                msg = msg.substring(0, 50) + "...";
            }
            System.out.println("[" + this.getName() + "] Sending message: '" + msg + "'");

            producer.send(message);
        }
    }

    private String createMessageText(int index) {
        StringBuffer buffer = new StringBuffer(message);
        buffer.append("Message: " + index + " sent at: " + new Date());
        if (buffer.length() > message) {
            return buffer.substring(0, message);
        }
        for (int i = buffer.length(); i < message; i++) {
            buffer.append(' ');
        }
        return buffer.toString();
    }

}
