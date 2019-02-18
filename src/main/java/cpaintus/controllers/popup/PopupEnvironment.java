package cpaintus.controllers.popup;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class PopupEnvironment {
	
	private Stage stage;
	private FXMLLoader fxmlLoader;
	
	public PopupEnvironment(Stage stage, FXMLLoader fxmlLoader) {
		this.stage = stage;
		this.fxmlLoader = fxmlLoader;
	}
	
	public Stage getStage() {
		return stage;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public FXMLLoader getFxmlLoader() {
		return fxmlLoader;
	}
	public void setFxmlLoader(FXMLLoader fxmlLoader) {
		this.fxmlLoader = fxmlLoader;
	}
}
