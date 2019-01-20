package cPaintUS.models;

import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.Observable;

public class BoundingBox extends Observable<IObserver> {
	
	private static BoundingBox instance;
	private Point cursorPoint;
	
	private BoundingBox() {
		cursorPoint = new Point();
	}
	
	public static BoundingBox getInstance() {
		if(instance == null)
			instance = new BoundingBox();
		return instance;
	}
	
	public void setCursorPoint(double x, double y) {
		cursorPoint.setX(x);
		cursorPoint.setY(y);
		this.notifyAllObservers();
	}
	
	public Point getCursorPoint() {
		return cursorPoint;
	}

	@Override
	public void notifyAllObservers() {
		for(IObserver obs : this.getObserverList()) {
			obs.update();
		}
	}

}
