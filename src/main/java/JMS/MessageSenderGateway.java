package JMS;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Nekkyou on 22-3-2017.
 */
public class MessageSenderGateway {
	private Connection connection;
	private Session session;
	private Destination destination;
	private MessageProducer producer;


	public MessageSenderGateway(String queue) {
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			connection = connectionFactory.createConnection();
			connection.start();

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(queue);
			producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public Message createMessage(String message){
		try {
			System.out.println("@MessageSenderGateway Creating message: " + message);
			return session.createTextMessage(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void send(Message msg) {
		try {
			System.out.println("@MessageSenderGateway Sending message");
			producer.send(msg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
