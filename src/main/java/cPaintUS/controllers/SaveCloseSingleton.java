package cPaintUS.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import cPaintUS.controllers.popup.CloseController;
import cPaintUS.models.saveStrategy.FileContext;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SaveCloseSingleton {
	private SnapshotSingleton snapshotSingleton;
	
	private SaveCloseSingleton () {
		snapshotSingleton = SnapshotSingleton.getInstance();
	}
	
	private static class SaveCloseSingletonHelper {
		private static final SaveCloseSingleton INSTANCE = new SaveCloseSingleton();
	}
	
	public static SaveCloseSingleton getInstance () {
		return SaveCloseSingletonHelper.INSTANCE;
	}
	
	public void triggerClose() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cPaintUS/views/popup/Close.fxml"));
		Parent parent;
		try {
			parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Close");
			stage.setScene(scene);
			stage.setResizable(false);

			CloseController controller = fxmlLoader.getController();
			controller.setNewDialog(stage);
			stage.showAndWait();
			if (controller.isYesClicked()) {
				this.handleSave();
			}
			System.exit(0);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void handleSave () {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Save As");
		Stage stage = (Stage) snapshotSingleton.getSnapshotPane().getScene().getWindow();
		chooser.getExtensionFilters().addAll(new ExtensionFilter("XML Files", "*.xml"),
				new ExtensionFilter("PNG Files", "*.png"));
		File selectedFile = chooser.showSaveDialog(stage);
		if (selectedFile != null) {
			String fileName = selectedFile.getName();
			String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, selectedFile.getName().length());
			if (fileExtension.equalsIgnoreCase("xml")) {
				FileContext.save(FileContext.types.XML, null, selectedFile.getAbsolutePath());
			} else {
				BufferedImage image = SwingFXUtils.fromFXImage(
						snapshotSingleton.getSnapshotPane().snapshot(new SnapshotParameters(), null), null);
				FileContext.save(FileContext.types.PNG, image, selectedFile.getAbsolutePath());
			}
		}
	}

}
