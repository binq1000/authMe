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

				boolean isReponse = msg.getBooleanProperty("isResponse");
				//Check if new item, or a response.
				if (isReponse) {
					final String appName = item.substring(0, item.indexOf(";"));
					String answer = item.substring(item.indexOf(";") + 1);

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

	public void sendYesResponse(String text, String time) {
		String response = text + ";yes;" + time;
		Message msg = sender.createMessage(response);
		sender.send(msg);

		Message cancelmsg = serverSender.createMessage(response);
		setMessageProperties(cancelmsg, true);
		serverSender.send(cancelmsg);
	}

	public void sendNoResponse(String text) {
		String response = text + ";no";
		Message msg = sender.createMessage(response);
		sender.send(msg);

		Message cancelmsg = serverSender.createMessage(response);
		setMessageProperties(cancelmsg, true);
		serverSender.send(cancelmsg);
	}

	private void setMessageProperties(Message msg, boolean isResponse) {
		try {
			msg.setBooleanProperty("isResponse", isResponse);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
