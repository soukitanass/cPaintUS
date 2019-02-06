package cpaintus.models;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.ObservableList;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapeFactory;
import cpaintus.models.shapes.ShapeType;
import cpaintus.models.shapes.ShapesDictionnary;

class ShapeDictTest {

	private ShapesDictionnary shapeDict;
	private ShapeFactory shapeFactory;

	@BeforeEach
	void setUp() throws Exception {
		shapeDict = ShapesDictionnary.getInstance();
		shapeFactory = ShapeFactory.getInstance();
	}

	@Test
	void getInstanceTest() {
		assertSame(ShapesDictionnary.getInstance(), shapeDict);
	}

	@Test
	void addShapeTest() {
		Shape actual = shapeFactory.getShape(ShapeType.RECTANGLE, true, 0, 11, 0, 0, 1, 10, 100, 1, "#fff", "#fff", "", "hh");
		shapeDict.clearShapes();
		shapeDict.addShape(actual);

		assertTrue(shapeDict.getShapesList().contains(actual));
		assertEquals(1, shapeDict.getShapesList().size());

		shapeDict.addShape(null);
		assertEquals(1, shapeDict.getShapesList().size());
	}

	@Test
	void clearShapesTest() {
		shapeDict.addShape(shapeFactory.getShape(ShapeType.RECTANGLE, true, 0, 11, 0, 0, 1, 10, 100, 1, "#fff", "#fff", "", "hh"));
		shapeDict.clearShapes();
		assertEquals(0, shapeDict.getShapesList().size());
	}

	@Test
	void addListShapeTest() {
		List<Shape> actualList = new ArrayList<Shape>();
		Shape actual = shapeFactory.getShape(ShapeType.RECTANGLE, true, 0, 11, 0, 0, 1, 10, 100, 1, "#fff", "#fff", "", "hh");
		actualList.add(actual);
		shapeDict.clearShapes();

		shapeDict.addListShapes(actualList);
		assertEquals(shapeDict.getShapesList().size(), actualList.size());
	}

	@Test
	void observerRegisterUnregisterTest() {
		shapeDict.register(new IObserver() {

			@Override
			public void update(ObservableList obs) {
				// TODO Auto-generated method stub

			}
		});
		assertEquals(1, shapeDict.getObserverList().size());

		IObserver observer = new IObserver() {

			@Override
			public void update(ObservableList obs) {
				// TODO Auto-generated method stub

			}
		};
		shapeDict.register(observer);
		assertEquals(2, shapeDict.getObserverList().size());

		shapeDict.unregister(observer);
		assertEquals(1, shapeDict.getObserverList().size());
		
		shapeDict.unregisterAll();
		assertTrue(shapeDict.getObserverList().isEmpty());
	}

}
