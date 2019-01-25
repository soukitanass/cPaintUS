package cPaintUS.models;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.ObservableList;

class BoundingBoxTest {

	BoundingBox boundingBox;

	@BeforeEach
	void setUp() throws Exception {
		boundingBox = BoundingBox.getInstance();
	}

	@Test
	void getInstanceTest() {
		assertSame(BoundingBox.getInstance(), boundingBox);
	}
	
	@Test
	void setOriginTest() {
		Point expected = new Point(0.0,1.0);
		boundingBox.setOrigin(expected);
		assertEquals(expected.getX(),boundingBox.getOrigin().getX());
		assertEquals(expected.getY(),boundingBox.getOrigin().getY());
		
		assertEquals(expected.getX(),boundingBox.getOppositeCorner().getX());
		assertEquals(expected.getY(),boundingBox.getOppositeCorner().getY());
	}
	
	@Test
	void updateBoundingBoxTest() {
		Point expected = new Point(0.0,0.0);
		boundingBox.updateBoundingBox(expected);
		
		assertEquals(expected.getX(),boundingBox.getOppositeCorner().getX());
		assertEquals(expected.getY(),boundingBox.getOppositeCorner().getY());
	}

	@Test
	void getUpLeftCornerTest() {
		Point expected = new Point(0.3,0.4);
		boundingBox.setOrigin(new Point(1.0,1.0));
		boundingBox.updateBoundingBox(new Point(0.3,0.4));
		
		assertEquals(expected.getX(),boundingBox.getUpLeftCorner().getX());
		assertEquals(expected.getY(),boundingBox.getUpLeftCorner().getY());
	}
	
	@Test
	void getWidthTest() {
		double expected = 3;
		boundingBox.setOrigin(new Point(6.0,0.0));
		boundingBox.updateBoundingBox(new Point(3.0,0.4));
		
		assertEquals(expected, boundingBox.getWidth());
	}
	
	@Test
	void getHeightTest() {
		double expected = 3;
		boundingBox.setOrigin(0.0,6.0);
		boundingBox.updateBoundingBox(new Point(3.0,3.0));
		
		assertEquals(expected, boundingBox.getHeight());
	}
	
	@Test
	void setVisibleTest() {
		boundingBox.setVisible(true);
		assertTrue(boundingBox.isVisible());
	}
	
	@Test
	void observerRegisterUnregisterTest() {
		boundingBox.register(new IObserver() {

			@Override
			public void update(ObservableList obs) {
				// TODO Auto-generated method stub

			}
		});
		assertEquals(1, boundingBox.getObserverList().size());

		IObserver observer = new IObserver() {

			@Override
			public void update(ObservableList obs) {
				// TODO Auto-generated method stub

			}
		};
		boundingBox.register(observer);
		assertEquals(2, boundingBox.getObserverList().size());

		boundingBox.unregister(observer);
		assertEquals(1, boundingBox.getObserverList().size());
		
		boundingBox.unregisterAll();
		assertTrue(boundingBox.getObserverList().isEmpty());
	}
}
