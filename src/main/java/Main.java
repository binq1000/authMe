import JMS.ClientAppGateway;
import JMS.ServerAppGateway;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by Nekkyou on 3-5-2017.
 */
public class Main extends Application {
	public static void main(String [ ] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

		primaryStage.setTitle("Authme Client");
		primaryStage.setScene(new Scene(root, 640, 480));
		primaryStage.show();
	}
}
