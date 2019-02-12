package cpaintus.models;

import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.Observable;
import cpaintus.models.observable.ObservableList;

public class Pointer extends Observable<IObserver>{

	private Point cursorPoint;
	private Boolean isGridActivated;
	private double gridStep;
	
	private Pointer() {
		cursorPoint = new Point();
		isGridActivated = true;
		gridStep = 50;
	}

	private static class SingletonHelper {
		private static final Pointer INSTANCE = new Pointer();
	}

	public static Pointer getInstance() {
		return SingletonHelper.INSTANCE;
	}
	
	public void setCursorPoint(double x, double y) {
		if(isGridActivated) {
			cursorPoint.setX(Math.round((x/gridStep)+ 0.5) * gridStep);
			cursorPoint.setY(Math.round((y/gridStep)+ 0.5) * gridStep);
			System.out.println(cursorPoint.getX() + " - " + x);
		} else {
			cursorPoint.setX(x);
			cursorPoint.setY(y);			
		}
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
