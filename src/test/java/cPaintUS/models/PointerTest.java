package cpaintus.models;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cpaintus.models.Point;
import cpaintus.models.Pointer;
import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.ObservableList;

class PointerTest {

	Pointer pointer;

	@BeforeEach
	void setUp() throws Exception {
		pointer = Pointer.getInstance();
	}

	@Test
	void getInstanceTest() {
		assertSame(Pointer.getInstance(), pointer);
	}
	
	@Test
	void observerRegisterUnregisterTest() {
		pointer.register(new IObserver() {

			@Override
			public void update(ObservableList obs) {
				// TODO Auto-generated method stub

			}
		});
		assertEquals(1, pointer.getObserverList().size());

		IObserver observer = new IObserver() {

			@Override
			public void update(ObservableList obs) {
				// TODO Auto-generated method stub

			}
		};
		pointer.register(observer);
		assertEquals(2, pointer.getObserverList().size());

		pointer.unregister(observer);
		assertEquals(1, pointer.getObserverList().size());
		
		pointer.unregisterAll();
		assertTrue(pointer.getObserverList().isEmpty());
	}
	
	@Test
	public void setCursorPointTest() {
		Point expected = new Point(0.0,1.0);
		pointer.setCursorPoint(expected.getX(), expected.getY());
		assertEquals(expected.getX(),pointer.getCursorPoint().getX());
		assertEquals(expected.getY(),pointer.getCursorPoint().getY());
	}

}
