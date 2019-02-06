package cpaintus.models;

import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.Observable;
import cpaintus.models.observable.ObservableList;

public class Pointer extends Observable<IObserver>{

	private Point cursorPoint;
	
	private Pointer() {
		cursorPoint = new Point();
	}

	private static class SingletonHelper {
		private static final Pointer INSTANCE = new Pointer();
	}

	public static Pointer getInstance() {
		return SingletonHelper.INSTANCE;
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
		for (IObserver obs : this.getObserverList()) {
			obs.update(ObservableList.POINTER);
		}
	}
}
