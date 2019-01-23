package cPaintUS.controllers;

import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.Observable;
import cPaintUS.models.observable.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class SnapshotSingleton extends Observable<IObserver> {

	private AnchorPane snapshotPane;
	private Image image;
	
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
	
	public void setImage(Image im) {
		this.image = im;
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
}