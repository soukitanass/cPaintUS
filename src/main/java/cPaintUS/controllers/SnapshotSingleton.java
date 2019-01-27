package cPaintUS.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.Observable;
import cPaintUS.models.observable.ObservableList;
import cPaintUS.models.shapes.Picture;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class SnapshotSingleton extends Observable<IObserver> {

	private AnchorPane snapshotPane;
	private Image image;
	private Picture picture;
	
	public AnchorPane getSnapshotPane() {
		return snapshotPane;
	}

	public void setSnapshotPane(AnchorPane snapshotPane) {
		this.snapshotPane = snapshotPane;
	}

	private static class SingletonHelper {
		private static final SnapshotSingleton INSTANCE = new SnapshotSingleton();
	}
	
	public static SnapshotSingleton getInstance() {
		return SingletonHelper.INSTANCE;
	}
	
	public Image getImage() {
		return this.image;
	}
	
	public void setImage(Picture im) {
		setPicture(im);
		BufferedImage image = null;
        byte[] imageByte;
        try {
            imageByte = Base64.getDecoder().decode(im.getBase64());
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.image = SwingFXUtils.toFXImage(image, null);
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

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}
}
