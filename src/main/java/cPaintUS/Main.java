package cPaintUS;

import cPaintUS.controllers.CentralCloseController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
	static CentralCloseController centralCloseController; 

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader (getClass().getResource("views/root.fxml"));
		Parent root = loader.load();
		primaryStage.setTitle("cPaintUS");
		primaryStage.setScene(new Scene(root, 800, 600));
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(600);
		primaryStage.show();	
		primaryStage.setOnCloseRequest((WindowEvent event) -> {
			centralCloseController = CentralCloseController.getInstance();
			centralCloseController.triggerClose();
	    });
	}

	public static void main(String[] args) {
		launch(args);
	}
}
