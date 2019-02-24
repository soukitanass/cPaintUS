package cpaintus.models;

import java.util.prefs.Preferences;

import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.Observable;
import cpaintus.models.observable.ObservableList;

public class BoundingBox extends Observable<IObserver> {

	private final String GRIDMOD = "gridmod";
	private final String GRIDSTEP = "gridstep";
	private boolean visible;
	private Point origin;
	private Point oppositeCorner;
	private double rotation;
	private Preferences prefs;

	private boolean gridMod;
	private double gridStep;
	private boolean followGrid;

	private static class SingletonHelper {
		private static final BoundingBox INSTANCE = new BoundingBox();
	}

	private BoundingBox() {
		prefs = Preferences.userNodeForPackage(this.getClass());
		visible = false;
		origin = new Point();
		oppositeCorner = new Point();
		rotation = 0;

		gridStep = prefs.getDouble(GRIDSTEP, 25);
		gridMod = prefs.getBoolean(GRIDMOD, false);
	}

	public static BoundingBox getInstance() {
		return SingletonHelper.INSTANCE;
	}

	@Override
	public void notifyAllObservers() {
		for (IObserver obs : getObserverList()) {
			obs.update(ObservableList.BOUNDING_BOX);
		}
	}

	public void notifyGridObservers() {
		for (IObserver obs : getObserverList()) {
			obs.update(ObservableList.GRID);
		}
	}

	private Point gridRound(Point p) {
		if (gridMod && followGrid) {
			p.setX(roundForGrid(p.getX()));
			p.setY(roundForGrid(p.getY()));
		}
		return p;
	}

	private double roundForGrid(double input) {
		return Math.round((input / gridStep)) * gridStep;
	}

	public void setOrigin(Point origin) {
		origin = gridRound(origin);
		origin.setX(Math.max(4, origin.getX()));
		origin.setY(Math.max(4, origin.getY()));
		this.origin.setPosition(origin.getX(), origin.getY());
		this.oppositeCorner.setPosition(origin.getX(), origin.getY());
	}

	public void setOrigin(double x, double y) {
		if (gridMod && followGrid) {
			x = roundForGrid(x);
			y = roundForGrid(y);
		}
		if (x < 4)
			x = gridStep;
		if (y < 4)
			y = gridStep;
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
		cursor = gridRound(cursor);
		if (gridMod && gridStep >= 4) {
			this.oppositeCorner.setPosition(cursor.getX() < 4 ? gridStep : cursor.getX(),
					cursor.getY() < 4 ? gridStep : cursor.getY());
		} else {
			this.oppositeCorner.setPosition(cursor.getX() < 4 ? 4 : cursor.getX(),
					cursor.getY() < 4 ? 4 : cursor.getY());
		}
		notifyAllObservers();
	}

	public Point getUpLeftCorner() {
		return new Point(Math.min(this.origin.getX(), oppositeCorner.getX()),
				Math.min(this.origin.getY(), oppositeCorner.getY()));
	}

	public double getWidth() {
		return Math.max(this.origin.getX(), oppositeCorner.getX())
				- Math.min(this.origin.getX(), oppositeCorner.getX());
	}

	public double getHeight() {
		return Math.max(this.origin.getY(), oppositeCorner.getY())
				- Math.min(this.origin.getY(), oppositeCorner.getY());
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

		notifyAllObservers();
	}

	public Boolean getGridMod() {
		return gridMod;
	}

	public void setGridMod(Boolean gridMod) {
		this.gridMod = gridMod;
		prefs.putBoolean(GRIDMOD, gridMod);
		notifyGridObservers();
	}

	public double getGridStep() {
		return gridStep;
	}

	public void setGridStep(double gridStep) {
		this.gridStep = gridStep;
		prefs.putDouble(GRIDSTEP, gridStep);
		notifyGridObservers();
	}

	public void setFollowGrid(Boolean followGrid) {
		this.followGrid = followGrid;
	}
}
