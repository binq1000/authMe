import JMS.ClientAppGateway;
import JMS.ServerAppGateway;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


import java.io.IOException;

import static javafx.application.ConditionalFeature.FXML;

/**
 * Created by Nekkyou on 17-5-2017.
 */
public class UIController {
	@javafx.fxml.FXML
	private TextField tfInput;

	ClientAppGateway clientAppGateway = new ClientAppGateway();

	public void btnSendClick(ActionEvent actionEvent) {
		String input = tfInput.getText();
		if (input != null && input.length() != 0) {
			clientAppGateway.sendMessage(input);
		}

	}
}
