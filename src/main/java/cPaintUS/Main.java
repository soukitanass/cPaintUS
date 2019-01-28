package cPaintUS;

import cPaintUS.controllers.RootController;
import cPaintUS.controllers.SaveCloseSingleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
	static SaveCloseSingleton saveCloseSingleton; 

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader (getClass().getResource("views/root.fxml"));
		Parent root = loader.load();
		RootController rootController = loader.getController();
		rootController.getLeftPaneController().setRoot(rootController);
		primaryStage.setTitle("cPaintUS");
		primaryStage.setScene(new Scene(root, 800, 600));
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(600);
		primaryStage.show();	
		primaryStage.setOnCloseRequest((WindowEvent event) -> {
			saveCloseSingleton = SaveCloseSingleton.getInstance();
			saveCloseSingleton.triggerClose();
	    });
	}

	public static void main(String[] args) {
		launch(args);
	}
}
