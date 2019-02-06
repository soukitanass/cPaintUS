package cPaintUS.models;


import org.junit.jupiter.api.Assertions;
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
		Assertions.assertSame(BoundingBox.getInstance(), boundingBox);
	}
	
	@Test
	void setOriginTest() {
		Point expected = new Point(2.0,1.0);
		boundingBox.setOrigin(expected);
		Assertions.assertEquals(expected.getX(),boundingBox.getOrigin().getX());
		Assertions.assertEquals(expected.getY(),boundingBox.getOrigin().getY());
		
		Assertions.assertEquals(expected.getX(),boundingBox.getOppositeCorner().getX());
		Assertions.assertEquals(expected.getY(),boundingBox.getOppositeCorner().getY());
	}
	
	@Test
	void updateBoundingBoxTest() {
		Point expected = new Point(8.4,7.1);
		boundingBox.updateBoundingBox(expected);
		
		Assertions.assertEquals(expected.getX(),boundingBox.getOppositeCorner().getX());
		Assertions.assertEquals(expected.getY(),boundingBox.getOppositeCorner().getY());
	}

	@Test
	void getUpLeftCornerTest() {
		Point expected = new Point(4.7,5.8);
		boundingBox.setOrigin(new Point(15.3,5.8));
		boundingBox.updateBoundingBox(new Point(4.7,11.3));
		
		Assertions.assertEquals(expected.getX(),boundingBox.getUpLeftCorner().getX());
		Assertions.assertEquals(expected.getY(),boundingBox.getUpLeftCorner().getY());
	}
	
	@Test
	void getWidthTest() {
		double expected = 6;
		boundingBox.setOrigin(new Point(6.0,9.0));
		boundingBox.updateBoundingBox(new Point(12.0,5.4));
		
		Assertions.assertEquals(expected, boundingBox.getWidth());
	}
	
	@Test
	void getHeightTest() {
		double expected = 2;
		boundingBox.setOrigin(8.0,6.0);
		boundingBox.updateBoundingBox(new Point(12.0,8.0));
		
		Assertions.assertEquals(expected, boundingBox.getHeight());
	}
	
	@Test
	void setVisibleTest() {
		boundingBox.setVisible(true);
		Assertions.assertTrue(boundingBox.isVisible());
	}
	
	@Test
	void getRotationTest() {
		double expected = 2.0;
		boundingBox.setRotation(2.0);
		Assertions.assertEquals(expected, boundingBox.getRotation());
		
	}
	
	@Test
	void observerRegisterUnregisterTest() {
		boundingBox.register(new IObserver() {

			@Override
			public void update(ObservableList obs) {
				// TODO Auto-generated method stub

			}
		});
		Assertions.assertEquals(1, boundingBox.getObserverList().size());

		IObserver observer = new IObserver() {

			@Override
			public void update(ObservableList obs) {
				// TODO Auto-generated method stub

			}
		};
		boundingBox.register(observer);
		Assertions.assertEquals(2, boundingBox.getObserverList().size());

		boundingBox.unregister(observer);
		Assertions.assertEquals(1, boundingBox.getObserverList().size());
		
		boundingBox.unregisterAll();
		Assertions.assertTrue(boundingBox.getObserverList().isEmpty());
	}
}
