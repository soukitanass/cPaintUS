package cPaintUS.models;

import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.Observable;

public class BoundingBox extends Observable<IObserver> {
	
	private static BoundingBox _instance;
	private Point _cursorPoint;
	
	private BoundingBox() {
		_cursorPoint = new Point();
	}
	
	public static BoundingBox getInstance() {
		if(_instance == null)
			_instance = new BoundingBox();
		return _instance;
	}
	
	public void setCursorPoint(double x, double y) {
		_cursorPoint.setX(x);
		_cursorPoint.setY(y);
		this.notifyAllObservers();
	}
	
	public Point getCursorPoint() {
		return _cursorPoint;
	}

	@Override
	public void notifyAllObservers() {
		for(IObserver obs : this.getObserverList()) {
			obs.update();
		}
	}

}
