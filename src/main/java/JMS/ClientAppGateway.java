package JMS;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * Created by Nekkyou on 3-5-2017.
 */
public class ClientAppGateway {
	private MessageSenderGateway sender;
	private MessageReceiverGateway receiver;

	public ClientAppGateway() {
		sender = new MessageSenderGateway("authMe.requestTopic", true);
		receiver = new MessageReceiverGateway("authMe.replyQueue", false);

		receiver.setListener(new MessageListener() {
			public void onMessage(Message message) {
				receivedMessage(message);
			}
		});
	}

	private void receivedMessage(Message msg) {
		if (msg instanceof TextMessage) {
			try {
				System.out.println("@ClientAppGateway Text message: " + ((TextMessage) msg).getText());
				String messageText = ((TextMessage) msg).getText();

				//TODO Change later
				int firstOcc = messageText.indexOf(";");

				String input =  messageText.substring(0, firstOcc);
				String response = messageText.substring(firstOcc + 1);

				if (response.startsWith("yes;")) {

					String time = response.substring(response.indexOf(";") + 1);
					int seconds = Integer.parseInt(time);

					Runtime runTime = Runtime.getRuntime();
					try {
						final Process process = runTime.exec(input);
						new java.util.Timer().schedule(
								new java.util.TimerTask() {
									@Override
									public void run() {
										process.destroy();
									}
								},
								seconds * 1000
						);
						System.out.println("Opened program");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendMessage(String text) {
		Message msg = sender.createMessage(text);
		try {
			msg.setBooleanProperty("isResponse", false);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		sender.send(msg);
	}

}
