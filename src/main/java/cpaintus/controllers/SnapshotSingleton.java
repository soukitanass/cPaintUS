package cpaintus.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.Observable;
import cpaintus.models.observable.ObservableList;
import cpaintus.models.shapes.Picture;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapesDictionnary;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class SnapshotSingleton extends Observable<IObserver> {

	private static final  Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private AnchorPane snapshotPane;
	private Image image;
	private Picture picture;
	private ShapesDictionnary shapesDict = ShapesDictionnary.getInstance();

	public AnchorPane getSnapshotPane() {
		return snapshotPane;
	}

	public void setSnapshotPane(AnchorPane snapshotPane) {
		this.snapshotPane = snapshotPane;
	}

	private static class SnapSingletonHelper {
		private static final SnapshotSingleton INSTANCE = new SnapshotSingleton();
	}

	public static SnapshotSingleton getInstance() {
		return SnapSingletonHelper.INSTANCE;
	}

	public Image getImage() {
		return this.image;
	}

	public void setImage(Picture im) {
		setPicture(im);
		BufferedImage imageBuffered = null;
		byte[] imageByte;
		try {
			imageByte = Base64.getDecoder().decode(im.getBase64());
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
			imageBuffered = ImageIO.read(bis);
			bis.close();
		} catch (Exception e) {
			LOGGER.log(Level.INFO, "Error while opening the file ", e);
		}
		this.image = SwingFXUtils.toFXImage(imageBuffered, null);
		this.picture.setWidth(this.image.getWidth());
		this.picture.setHeight(this.image.getHeight());
		notifyAllLoadImage();
	}

	public void eraseAll() {
		notifyAllObservers();
	}

	@Override
	public void notifyAllObservers() {
		for (IObserver obs : getObserverList()) {
			obs.update(ObservableList.MENU_ERASE);
		}
	}

	private void notifyAllLoadImage() {
		for (IObserver obs : getObserverList()) {
			obs.update(ObservableList.LOAD_IMAGE);
		}
	}
	
	private void notifyAllZEdit() {
		for (IObserver obs : getObserverList()) {
			obs.update(ObservableList.Z_EDIT);
		}
	}
	
	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}
	
	public void updateShapesZ() {
		List<Node> nodes = snapshotPane.getChildren();

		for (int i = 0; i < nodes.size(); i++) {
			int hash = nodes.get(i).hashCode();
			Shape shape = shapesDict.getFullShapesList().stream()
				.filter(s -> hash == s.getCanvasHash())
				.findAny()
				.orElse(null);
			if (shape != null) shape.setZ(i);
		}
		notifyAllZEdit();
	}
}
