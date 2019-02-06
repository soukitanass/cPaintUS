package cpaintus.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import cpaintus.controllers.popup.CloseController;
import cpaintus.models.savestrategy.FileContext;
import cpaintus.models.shapes.ShapesDict;
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

	private static final  Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private SnapshotSingleton snapshotSingleton;
	private ShapesDict shapesDict;

	private SaveCloseSingleton() {
		snapshotSingleton = SnapshotSingleton.getInstance();
		shapesDict = ShapesDict.getInstance();
	}

	private static class SaveCloseSingletonHelper {
		private static final SaveCloseSingleton INSTANCE = new SaveCloseSingleton();
	}

	public static SaveCloseSingleton getInstance() {
		return SaveCloseSingletonHelper.INSTANCE;
	}

	public void triggerClose() {
		if (shapesDict.getShapesList().size() != 0) {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cpaintus/views/popup/Close.fxml"));
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

			} catch (IOException e) {
				LOGGER.log(Level.INFO, "Error while opening the file ", e);
			}
		}
		System.exit(0);
	}

	public void handleSave() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Save As");
		Stage stage = (Stage) snapshotSingleton.getSnapshotPane().getScene().getWindow();
		chooser.getExtensionFilters().addAll(new ExtensionFilter("XML Files", "*.xml"),
				new ExtensionFilter("PNG Files", "*.png"));
		File selectedFile = chooser.showSaveDialog(stage);
		if (selectedFile != null) {
			String fileName = selectedFile.getName();
			String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1, selectedFile.getName().length());
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
