package cPaintUS.controllers;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cPaintUS.controllers.FileContext.types;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.MenuBar;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TopPaneController {
	
	@FXML
	private MenuBar menuBar;
	
	private CenterPaneController centerPaneController; 

	public CenterPaneController getCenterPaneController() {
		return centerPaneController;
	}

	public void setCenterPaneController(CenterPaneController centerPaneController) {
		this.centerPaneController = centerPaneController;
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
	    fileChooser.setTitle("Save Image");
	    File file = fileChooser.showSaveDialog(this.centerPaneController.getPane().getScene().getWindow());
	    if (file != null) {
	    	savePng(file);
	    }
	}
	
	
		
	private void savePng (File file) {
		    WritableImage image = this.centerPaneController.getPane().snapshot(new SnapshotParameters(), null);
		    try {
		        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
		    } catch (IOException e) {
	            System.out.println(e.getMessage());
	        }
	}
	
	/*
	@FXML
	private void openSaveXml () {
		FileContext fileContext = new FileContext (); 
		fileContext.openSaveModal(types.XML);
	}*/
}

