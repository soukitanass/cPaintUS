package cpaintus.models;

import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.Observable;
import cpaintus.models.observable.ObservableList;

public class ZoomSingleton extends Observable<IObserver> {

	private double zoom;
	private double oldZoom;
	
	private static class SingletonHelper {
		private static final ZoomSingleton INSTANCE = new ZoomSingleton();
	}

	private ZoomSingleton() {
		oldZoom = 100;
		zoom = 100;
	}
	
	public static ZoomSingleton getInstance() {
		return SingletonHelper.INSTANCE;
	}

	@Override
	public void notifyAllObservers() {
		for (IObserver obs : getObserverList()) {
			obs.update(ObservableList.ZOOM);
		}
	}

	public double getZoom() {
		return zoom;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom > 50 ? (zoom < 150 ? zoom : 150) : 50;
		notifyAllObservers();
	}
	
	public double getZoomRatio() {
		return zoom/100;
	}

	public void setOldZoom(double oldZoom) {
		this.oldZoom = oldZoom;
	}
	
	public double getDynamicZoom() {
		return zoom/oldZoom;
	}
}
