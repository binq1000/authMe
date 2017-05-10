package JMS;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by Nekkyou on 3-5-2017.
 * This should run on the phone of the parent or something.
 */
public class ServerAppGateway {
	private MessageSenderGateway sender;
	private MessageReceiverGateway receiver;

	public ServerAppGateway() {
		sender = new MessageSenderGateway("authMe.client.receive");
		receiver = new MessageReceiverGateway("authMe.client.send");

		receiver.setListener(new MessageListener() {
			public void onMessage(Message message) {
				System.out.println("@ServerAppGateway Received message");
				receivedMessage(message);
			}
		});
	}

	private void receivedMessage(Message msg) {
		if (msg instanceof TextMessage) {
			try {
				System.out.println("@ServerAppGateway Received text message: " + ((TextMessage) msg).getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
}
