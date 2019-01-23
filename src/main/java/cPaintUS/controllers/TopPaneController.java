package cPaintUS.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cPaintUS.models.saveStrategy.FileContext;
import cPaintUS.models.saveStrategy.FileContext.types;
import javafx.embed.swing.SwingFXUtils;
import cPaintUS.controllers.popup.NewController;
import cPaintUS.models.saveStrategy.FileManagerStrategy;
import cPaintUS.models.saveStrategy.PNGStrategy;
import cPaintUS.models.saveStrategy.XMLStrategy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TopPaneController {
	
	private FileManagerStrategy fileManager;
	private SnapshotSingleton snapshotSingleton;
	@FXML
	private MenuBar menuBar;
	
	public TopPaneController() {
		snapshotSingleton = SnapshotSingleton.getInstance();
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
				snapshotSingleton.eraseAll();
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
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Save As");
		Stage stage = (Stage) snapshotSingleton.getSnapshotPane().getScene().getWindow();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		chooser.getExtensionFilters().add(extFilter);
		File selectedDirectory = chooser.showSaveDialog(stage);
		if (selectedDirectory != null)
			fileManager.save(selectedDirectory.getAbsolutePath());

	}

	@FXML
	private void handleLoadXMLClick() {
		fileManager = new XMLStrategy();

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("XML Files", "*.xml"));
		Stage stage = (Stage) snapshotSingleton.getSnapshotPane().getScene().getWindow();
		File selectedFile = fileChooser.showOpenDialog(stage);
		if (selectedFile != null) {
			fileManager.load(selectedFile.getAbsolutePath());
		}
	}
	@FXML
	private void openSavePng () {
		
		FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Save Image");
	    File file = fileChooser.showSaveDialog(snapshotSingleton.getSnapshotPane().getScene().getWindow());
	    BufferedImage image = SwingFXUtils.fromFXImage(snapshotSingleton.getSnapshotPane().snapshot(new SnapshotParameters(), null), null);
	    if (file != null) {
	    	FileContext.save(types.PNG,image,file.getPath());
	    }
	}
	
	@FXML 
	private void openLoadPng () {
		fileManager = new PNGStrategy();
		
		FileChooser fileChooser = new FileChooser (); 
		fileChooser.setTitle("Select Image");
		File file = fileChooser.showOpenDialog(snapshotSingleton.getSnapshotPane().getScene().getWindow());
		
		if (file != null) {
			fileManager.load(file.toURI().toString());
        }
	}
	
}

