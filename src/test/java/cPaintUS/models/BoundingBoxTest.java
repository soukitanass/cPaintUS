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
	DrawSettings drawSettings;

	@BeforeEach
	void setUp() throws Exception {
		boundingBox = BoundingBox.getInstance();
		drawSettings = DrawSettings.getInstance();
		drawSettings.setLineWidth(1);
	}

	@Test
	void getInstanceTest() {
		assertSame(BoundingBox.getInstance(), boundingBox);
	}
	
	@Test
	void setOriginTest() {
		Point expected = new Point(2.0,1.0);
		boundingBox.setOrigin(expected);
		assertEquals(expected.getX(),boundingBox.getOrigin().getX());
		assertEquals(expected.getY(),boundingBox.getOrigin().getY());
		
		assertEquals(expected.getX(),boundingBox.getOppositeCorner().getX());
		assertEquals(expected.getY(),boundingBox.getOppositeCorner().getY());
	}
	
	@Test
	void updateBoundingBoxTest() {
		Point expected = new Point(8.4,7.1);
		boundingBox.updateBoundingBox(expected);
		
		assertEquals(expected.getX(),boundingBox.getOppositeCorner().getX());
		assertEquals(expected.getY(),boundingBox.getOppositeCorner().getY());
	}

	@Test
	void getUpLeftCornerTest() {
		Point expected = new Point(4.7,5.8);
		boundingBox.setOrigin(new Point(15.3,5.8));
		boundingBox.updateBoundingBox(new Point(4.7,11.3));
		
		assertEquals(expected.getX(),boundingBox.getUpLeftCorner().getX());
		assertEquals(expected.getY(),boundingBox.getUpLeftCorner().getY());
	}
	
	@Test
	void getWidthTest() {
		double expected = 6;
		boundingBox.setOrigin(new Point(6.0,9.0));
		boundingBox.updateBoundingBox(new Point(12.0,5.4));
		
		assertEquals(expected, boundingBox.getWidth());
	}
	
	@Test
	void getHeightTest() {
		double expected = 2;
		boundingBox.setOrigin(8.0,6.0);
		boundingBox.updateBoundingBox(new Point(12.0,8.0));
		
		assertEquals(expected, boundingBox.getHeight());
	}
	
	@Test
	void setVisibleTest() {
		boundingBox.setVisible(true);
		assertTrue(boundingBox.isVisible());
	}
	
	@Test
	void getRotationTest() {
		double expected = 2.0;
		boundingBox.setRotation(2.0);
		assertEquals(expected, boundingBox.getRotation());
		
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
