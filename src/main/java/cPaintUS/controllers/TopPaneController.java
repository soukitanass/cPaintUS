package cPaintUS.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import cPaintUS.controllers.popup.AboutController;
import cPaintUS.controllers.popup.NewController;
import cPaintUS.models.saveStrategy.FileContext;
import cPaintUS.models.shapes.ShapesDict;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TopPaneController {

	private SnapshotSingleton snapshotSingleton;
	private SaveCloseSingleton saveCloseSingleton;
	@FXML
	private MenuBar menuBar;

	private ShapesDict shapesDict;
	
	public TopPaneController() {
		snapshotSingleton = SnapshotSingleton.getInstance();
		shapesDict = ShapesDict.getInstance();
		saveCloseSingleton = SaveCloseSingleton.getInstance();
	}

	@FXML
	private void handleNewClick() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cPaintUS/views/popup/New.fxml"));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void handleExitClick() {
		saveCloseSingleton.triggerClose();
	}

	@FXML
	private void handleAboutClick() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cPaintUS/views/popup/About.fxml"));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, selectedFile.getName().length());
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
