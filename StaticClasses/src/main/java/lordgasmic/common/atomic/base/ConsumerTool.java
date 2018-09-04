package lordgasmic.common.atomic.base;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;

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

/**
 * A simple tool for consuming messages
 * 
 * @version $Revision: 1.1.1.1 $
 */
public class ConsumerTool extends Thread implements MessageListener, ExceptionListener {

	private boolean running;

	private Session session;
	private Destination destination;

	private String subject = "TOOL.DEFAULT";
	private String user = ActiveMQConnection.DEFAULT_USER;
	private String password = ActiveMQConnection.DEFAULT_PASSWORD;
	private String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private int ackMode = Session.AUTO_ACKNOWLEDGE;

	public static void main(String[] args) {
		ConsumerTool consumerTool = new ConsumerTool();

		consumerTool.start();
	}

	public void run() {
		try {
			running = true;

			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
			Connection connection = connectionFactory.createConnection();

			connection.setExceptionListener(this);
			connection.start();

			session = connection.createSession(false, ackMode);
			destination = session.createTopic(subject);

			MessageConsumer consumer = null;
			consumer = session.createConsumer(destination);

			consumeMessagesAndClose(connection, session, consumer);

		} catch (Exception e) {
			System.out.println("[" + this.getName() + "] Caught: " + e);
			e.printStackTrace();
		}
	}

	public void onMessage(Message message) {
		try {

			if (message instanceof TextMessage) {
				TextMessage txtMsg = (TextMessage) message;
				String msg = txtMsg.getText();
				int length = msg.length();
				if (length > 50) {
					msg = msg.substring(0, 50) + "...";
				}
				System.out.println("[" + this.getName() + "] Received: '" + msg + "' (length " + length + ")");
			} 
			else {

			}

		} catch (JMSException e) {
			System.out.println("[" + this.getName() + "] Caught: " + e);
			e.printStackTrace();
		} 
	}

	public synchronized void onException(JMSException ex) {
		System.out.println("[" + this.getName() + "] JMS Exception occured.  Shutting down client.");
		running = false;
	}

	synchronized boolean isRunning() {
		return running;
	}

	protected void consumeMessagesAndClose(Connection connection, Session session, MessageConsumer consumer) throws JMSException, IOException {
		Message message;
		while ((message = consumer.receive(0)) != null) {
			onMessage(message);
		}

		System.out.println("[" + this.getName() + "] Closing connection");
		consumer.close();
		session.close();
		connection.close();

		System.out.println("[" + this.getName() + "] Press return to shut down");
		System.in.read();

	}

}
