package JMS;

import javafx.application.Platform;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Nekkyou on 3-5-2017.
 * This should run on the phone of the parent or something.
 */
public class ServerAppGateway {
	private MessageSenderGateway sender;
	private MessageSenderGateway serverSender;
	private MessageReceiverGateway receiver;

	public ServerAppGateway() {
		sender = new MessageSenderGateway("authMe.replyQueue", false);
		receiver = new MessageReceiverGateway("authMe.requestTopic", true);

		receiver.setListener(new MessageListener() {
			public void onMessage(Message message) {
				receivedMessage(message);
			}
		});

		//Send messages to other receivers of the topic
		serverSender = new MessageSenderGateway("authMe.requestTopic", true);
	}

	private void receivedMessage(Message msg) {
		if (msg instanceof TextMessage) {
			try {
				System.out.println("@ServerAppGateway Received text message: " + ((TextMessage) msg).getText());
				final String item = ((TextMessage) msg).getText();

				//Check if new item, or a response.
				if (item.startsWith("response;")) {
					String response = item.substring(item.indexOf(";") + 1);
					final String appName = response.substring(0, response.indexOf(";"));
					String answer = response.substring(response.indexOf(";") + 1);

					Platform.runLater(new Runnable() {
						public void run() {
							ListDataSingleton.getInstance().removeItem(appName);
						}
					});
				} else {
					Platform.runLater(new Runnable() {
						public void run() {
							ListDataSingleton.getInstance().addItem(item);
						}
					});
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendResponse(String text) {
		Message msg = sender.createMessage(text);
		sender.send(msg);

		Message cancelmsg = serverSender.createMessage("response;" + text);
		serverSender.send(cancelmsg);
	}
}
