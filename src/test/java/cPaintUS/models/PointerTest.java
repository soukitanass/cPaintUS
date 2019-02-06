package cPaintUS.models;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.ObservableList;

class PointerTest {

	Pointer pointer;

	@BeforeEach
	void setUp() throws Exception {
		pointer = Pointer.getInstance();
	}

	@Test
	void getInstanceTest() {
		Assertions.assertSame(Pointer.getInstance(), pointer);
	}
	
	@Test
	void observerRegisterUnregisterTest() {
		pointer.register(new IObserver() {

			@Override
			public void update(ObservableList obs) {
				// TODO Auto-generated method stub

			}
		});
		Assertions.assertEquals(1, pointer.getObserverList().size());

		IObserver observer = new IObserver() {

			@Override
			public void update(ObservableList obs) {
				// TODO Auto-generated method stub

			}
		};
		pointer.register(observer);
		Assertions.assertEquals(2, pointer.getObserverList().size());

		pointer.unregister(observer);
		Assertions.assertEquals(1, pointer.getObserverList().size());
		
		pointer.unregisterAll();
		Assertions.assertTrue(pointer.getObserverList().isEmpty());
	}
	
	@Test
	public void setCursorPointTest() {
		Point expected = new Point(0.0,1.0);
		pointer.setCursorPoint(expected.getX(), expected.getY());
		Assertions.assertEquals(expected.getX(),pointer.getCursorPoint().getX());
		Assertions.assertEquals(expected.getY(),pointer.getCursorPoint().getY());
	}

}
