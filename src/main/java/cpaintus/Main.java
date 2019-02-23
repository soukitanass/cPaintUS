package cpaintus;

import java.util.prefs.Preferences;

import cpaintus.controllers.SaveCloseSingleton;
import cpaintus.controllers.command.Invoker;
import cpaintus.models.logger.BaseLogger;
import cpaintus.models.logger.LogLevel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Preferences prefs = Preferences.userNodeForPackage(this.getClass());
		FXMLLoader loader = new FXMLLoader(getClass().getResource("views/root.fxml"));
		Parent root = loader.load();
		primaryStage.setTitle("cPaintUS");
		primaryStage.setScene(new Scene(root, prefs.getDouble("width", 800), prefs.getDouble("height", 600)));
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(650);
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent ev) {
				if (!SaveCloseSingleton.getInstance().triggerClose()) {
					ev.consume();
				}
			}
		});
		primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> prefs.putDouble("width", (double) newVal));
		primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> prefs.putDouble("height", (double) newVal));
		KeyCombination cntrlZ = new KeyCodeCombination(KeyCode.Z, KeyCodeCombination.CONTROL_DOWN);
		KeyCombination cntrlY = new KeyCodeCombination(KeyCode.Y, KeyCodeCombination.CONTROL_DOWN);
		KeyCombination cntrlS = new KeyCodeCombination(KeyCode.S, KeyCodeCombination.CONTROL_DOWN);
		primaryStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (cntrlZ.match(event)) {
					Invoker.getInstance().undo();
				} else if (cntrlY.match(event)) {
					Invoker.getInstance().redo();
				} else if (cntrlS.match(event)) {
					SaveCloseSingleton.getInstance().handleSave();
				}
			}
		});
	}

	public static void main(String[] args) {
		int i = 0;
		if (args.length > 0 && args[0].toLowerCase().contains("verbose")) {
			String[] verbose = args[0].split("=");
			i = Integer.parseInt(verbose[1]);
		}
		LogLevel logLevel = LogLevel.getLevel(i);
		BaseLogger.setLogLevel(logLevel);
		
		launch(args);
	}
}
