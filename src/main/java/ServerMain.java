import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Nekkyou on 17-5-2017.
 */
public class ServerMain extends Application {
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("serverMain.fxml"));

		primaryStage.setTitle("Authme Server");
		primaryStage.setScene(new Scene(root, 640, 480));
		primaryStage.show();
	}

	public static void main(String [ ] args)
	{
		launch(args);
	}
}
