package cpaintus.controllers;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import cpaintus.controllers.popup.AboutController;
import cpaintus.controllers.popup.NewController;
import cpaintus.models.savestrategy.FileContext;
import cpaintus.models.shapes.ShapesDictionnary;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TopPaneController {

	private static final  Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private SnapshotSingleton snapshotSingleton;
	private SaveCloseSingleton saveCloseSingleton;
	@FXML
	private MenuBar menuBar;

	private ShapesDictionnary shapesDict;

	public TopPaneController() {
		snapshotSingleton = SnapshotSingleton.getInstance();
		shapesDict = ShapesDictionnary.getInstance();
		saveCloseSingleton = SaveCloseSingleton.getInstance();
	}

	@FXML
	private void handleNewClick() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cpaintus/views/popup/New.fxml"));
		Parent parent;
		try {
			parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("New");
			stage.setScene(scene);
			stage.setResizable(false);

			NewController controller = fxmlLoader.getController();
			controller.setNewDialog(stage);
			if (!shapesDict.getShapesList().isEmpty()) {
				stage.showAndWait();
				if (controller.isYesClicked()) {
					this.handleSaveClick();
				}
				snapshotSingleton.eraseAll();
			}

		} catch (IOException e) {
			LOGGER.log(Level.INFO, "Error while opening the file ", e);
		}
	}

	@FXML
	private void handleExitClick() {
		saveCloseSingleton.triggerClose();
	}

	@FXML
	private void handleAboutClick() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cpaintus/views/popup/About.fxml"));
		Parent parent;
		try {
			parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("About");
			stage.setScene(scene);
			stage.setResizable(false);

			AboutController controller = fxmlLoader.getController();
			controller.setNewDialog(stage);
			stage.showAndWait();
		} catch (IOException e) {
			LOGGER.log(Level.INFO, "Error while opening the file ", e);
		}
	}

	@FXML
	private void handleLoadLClick() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("XML Files", "*.xml"),
				new ExtensionFilter("PNG Files", "*.png"));
		Stage stage = (Stage) snapshotSingleton.getSnapshotPane().getScene().getWindow();
		File selectedFile = fileChooser.showOpenDialog(stage);
		if (selectedFile != null) {
			String fileName = selectedFile.getName();
			String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1, selectedFile.getName().length());
			if (fileExtension.equalsIgnoreCase("xml")) {
				FileContext.load(FileContext.types.XML, selectedFile.getAbsolutePath());
			} else {
				FileContext.load(FileContext.types.PNG, selectedFile.getAbsolutePath());
			}
		}

	}

	@FXML
	private void handleSaveClick() {
		saveCloseSingleton.handleSave();
	}

}