package JMS;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by Nekkyou on 3-5-2017.
 */
public class ClientAppGateway {
	private MessageSenderGateway sender;
	private MessageReceiverGateway receiver;

	public ClientAppGateway() {
		sender = new MessageSenderGateway("authMe.client.send");
		receiver = new MessageReceiverGateway("authMe.client.receive");

		receiver.setListener(new MessageListener() {
			public void onMessage(Message message) {
				System.out.println("@ClientAppGateway Received message");
				receivedMessage(message);
			}
		});
	}

	private void receivedMessage(Message msg) {
		if (msg instanceof TextMessage) {
			try {
				System.out.println("@ClientAppGateway Text message: " + ((TextMessage) msg).getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendMessage(String text) {
		Message msg = sender.createMessage(text);
		sender.send(msg);
	}

}
