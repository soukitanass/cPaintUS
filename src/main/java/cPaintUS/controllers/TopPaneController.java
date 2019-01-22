package cPaintUS.controllers;

import java.io.File;
import java.io.IOException;

import cPaintUS.controllers.popup.NewController;
import cPaintUS.models.saveStrategy.FileManagerStrategy;
import cPaintUS.models.saveStrategy.XMLStrategy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TopPaneController {
	private RootController root;
	private FileManagerStrategy fileManager;

	@FXML
	private MenuBar menuBar;

	public void setRoot(RootController r) {
		this.root = r;
	}

	@FXML
	private void handleNewClick() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cPaintUS/views/popup/New.fxml"));
		Parent parent;
		try {
			parent = fxmlLoader.load();
			Scene scene = new Scene(parent, 220, 100);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("New");
			stage.setScene(scene);

			NewController controller = fxmlLoader.getController();
			controller.setNewDialog(stage);

			stage.showAndWait();

			if (controller.isYesClicked()) {
				root.getCenterPaneController().eraseAll();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void handleExitClick() {
		System.exit(0);
	}

	@FXML
	private void handleAboutClick() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cPaintUS/views/popup/About.fxml"));
		Parent parent;
		try {
			parent = fxmlLoader.load();
			Scene scene = new Scene(parent, 300, 200);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("About");
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	private void handleSaveXMLClick() {
		// get the path
		fileManager = new XMLStrategy();
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("JavaFX Projects");
		Stage stage = (Stage) root.getCenterPaneController().getPane().getScene().getWindow();
		File selectedDirectory = chooser.showDialog(stage);
		if (selectedDirectory != null)
			fileManager.save(selectedDirectory.getAbsolutePath());

	}
}
