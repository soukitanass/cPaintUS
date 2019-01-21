package cPaintUS.controllers;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cPaintUS.models.saveStrategy.FileContext;
import cPaintUS.models.saveStrategy.FileContext.types;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.MenuBar;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TopPaneController {
	
	@FXML
	private MenuBar menuBar;
	
	private RootController root; 

	public RootController getRoot() {
		return root;
	}

	public void setRoot(RootController root) {
		this.root = root;
	}
	

	@FXML
	private void exit() {
		System.exit(0);
	}

	@FXML
	private void about() {
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
	private void openSaveXml () {
	}
	
	@FXML
	private void openSavePng () {
		
		FileChooser fileChooser = new FileChooser();
		Pane centerPane = this.root.getCenterPaneController().getPane();
	    fileChooser.setTitle("Save Image");
	    File file = fileChooser.showSaveDialog(this.root.getCenterPaneController().getPane().getScene().getWindow());
	    if (file != null && centerPane != null) {
	    	FileContext.save(types.PNG,centerPane,file);
	    }
	}
	
}

