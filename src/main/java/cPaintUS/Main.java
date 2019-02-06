package cpaintus;

import cpaintus.controllers.SaveCloseSingleton;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
	static SaveCloseSingleton saveCloseSingleton;
	private Preferences prefs;

	@Override
	public void start(Stage primaryStage) throws Exception {
	    prefs = Preferences.userNodeForPackage(this.getClass());
		FXMLLoader loader = new FXMLLoader(getClass().getResource("views/root.fxml"));
		Parent root = loader.load();
		primaryStage.setTitle("cPaintUS");
		primaryStage.setScene(new Scene(root, prefs.getDouble("width",800), prefs.getDouble("height",600)));
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(600);
		primaryStage.show();
		primaryStage.setOnCloseRequest((WindowEvent event) -> {
			saveCloseSingleton = SaveCloseSingleton.getInstance();
			saveCloseSingleton.triggerClose();
		});
		primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
			prefs.putDouble("width", (double) newVal);
		});
		primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
			prefs.putDouble("height", (double) newVal);
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
