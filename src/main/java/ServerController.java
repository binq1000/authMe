import JMS.ListDataSingleton;
import JMS.ServerAppGateway;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Nekkyou on 17-5-2017.
 */
public class ServerController implements Initializable{
	@FXML
	private ListView listView;

	private ServerAppGateway serverAppGateway = new ServerAppGateway();

	public void btnYesClick(ActionEvent actionEvent) {
		Object selectedItem = listView.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			String item = selectedItem.toString();
			ListDataSingleton.getInstance().removeItem(item);

			TextInputDialog dialog = new TextInputDialog("5");
			dialog.setTitle("Set time");
			dialog.setHeaderText("How long should this application be available");
			dialog.setContentText("Please enter the desired time:");

			String time = "5";

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
				try {
					Integer.parseInt(result.get());
					time = result.get();
				}
				catch (Exception e) {
					time = "5";
				}
			}

			serverAppGateway.sendResponse(item + ";yes;" + time);
		}
	}

	public void btnNoClick(ActionEvent actionEvent) {
		Object selectedItem = listView.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			String item = selectedItem.toString();
			ListDataSingleton.getInstance().removeItem(item);

			serverAppGateway.sendResponse(item + ";no");
		}

	}

	public void initialize(URL location, ResourceBundle resources) {
		if (listView != null)
			listView.setItems(ListDataSingleton.getInstance().getItems());
		else {
			throw new NullPointerException("listview is null");
		}
	}
}
