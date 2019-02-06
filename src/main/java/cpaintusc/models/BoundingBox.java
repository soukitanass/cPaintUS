package cpaintusc.models;

import cpaintusc.models.observable.IObserver;
import cpaintusc.models.observable.Observable;
import cpaintusc.models.observable.ObservableList;

public class BoundingBox extends Observable<IObserver>{

	private boolean visible;
	private Point origin;
	private Point oppositeCorner;
	private DrawSettings drawSettings;
	private double rotation;

	private static class SingletonHelper {
		private static final BoundingBox INSTANCE = new BoundingBox();
	}
	
	private BoundingBox() {
		visible = false;
		origin = new Point();
		oppositeCorner = new Point();
		drawSettings = DrawSettings.getInstance();
		rotation = 0;
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
		this.oppositeCorner.setPosition(cursor.getX() < 4 + drawSettings.getLineWidth()/2 ? 4 + drawSettings.getLineWidth()/2 : cursor.getX(), cursor.getY() < 4 + drawSettings.getLineWidth()/2 ? 4 + drawSettings.getLineWidth()/2 : cursor.getY());
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
		this.visible = visible;
		notifyAllObservers();
	}
	
	public boolean isVisible() {
		return visible;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
}
