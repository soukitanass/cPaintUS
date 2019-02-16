package cpaintus.controllers.popup;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopupBuilder {

	private String windowName;
	private String fxmlResource;
	
	public PopupBuilder() {
		
	}
	
	public PopupBuilder(String windowName, String fxmlResource) {
		this.windowName = windowName;
		this.fxmlResource = fxmlResource;
	}
	
	public PopupEnvironment build() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlResource));
		Parent parent;
		parent = fxmlLoader.load();
		Scene scene = new Scene(parent);
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(windowName);
		stage.setScene(scene);
		stage.setResizable(false);
		PopupEnvironment popupEnvironment = new PopupEnvironment(stage, fxmlLoader);
		return popupEnvironment;
	}

	public String getWindowName() {
		return windowName;
	}

	public void setWindowName(String windowName) {
		this.windowName = windowName;
	}

	public String getFxmlResource() {
		return fxmlResource;
	}

	public void setFxmlResource(String fxmlResource) {
		this.fxmlResource = fxmlResource;
	}
	
	
}
