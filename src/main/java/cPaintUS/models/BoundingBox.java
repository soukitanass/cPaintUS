package cPaintUS.models;

import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.Observable;
import cPaintUS.models.observable.ObservableList;

public class BoundingBox extends Observable<IObserver>{

	private boolean visible;
	private Point origin;
	private Point oppositeCorner;

	private static class SingletonHelper {
		private static final BoundingBox INSTANCE = new BoundingBox();
	}
	
	private BoundingBox() {
		visible = false;
		origin = new Point();
		oppositeCorner = new Point();
	}

	public static BoundingBox getInstance() {
		return SingletonHelper.INSTANCE;
	}
	
	@Override
	public void notifyAllObservers() {
		for(IObserver obs : getObserverList()) {
			obs.update(ObservableList.BOUNDING_BOX);
		}
	}
	

	public void setOrigin(Point origin) {
		this.origin.setPosition(origin.getX(), origin.getY());
		this.oppositeCorner.setPosition(origin.getX(), origin.getY());
	}

	public void setOrigin(double x, double y) {
		this.origin.setPosition(x, y);
		this.oppositeCorner.setPosition(x, y);
	}
	
	public Point getOrigin() {
		return this.origin;
	}
	
	public Point getOppositeCorner() {
		return this.oppositeCorner;
	}

	public void updateBoundingBox(Point cursor) {
		this.oppositeCorner.setPosition(cursor.getX(), cursor.getY());
		notifyAllObservers();
	}

	public Point getUpLeftCorner() {
		return new Point(Math.min(this.origin.getX(), oppositeCorner.getX()), Math.min(this.origin.getY(), oppositeCorner.getY()));
	}

	public double getWidth() {
		return Math.max(this.origin.getX(), oppositeCorner.getX()) - Math.min(this.origin.getX(), oppositeCorner.getX());
	}

	public double getHeight() {
		return Math.max(this.origin.getY(), oppositeCorner.getY()) - Math.min(this.origin.getY(), oppositeCorner.getY());
	}
	
	public void setVisible(boolean visible) {
		notifyAllObservers();
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return visible;
	}
}
