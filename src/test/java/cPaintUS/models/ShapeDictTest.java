package cPaintUS.models;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.ObservableList;
import cPaintUS.models.shapes.Shape;
import cPaintUS.models.shapes.ShapeFactory;
import cPaintUS.models.shapes.ShapeType;
import cPaintUS.models.shapes.ShapesDict;

class ShapeDictTest {

	private ShapesDict shapeDict;
	private ShapeFactory shapeFactory;

	@BeforeEach
	void setUp() throws Exception {
		shapeDict = ShapesDict.getInstance();
		shapeFactory = ShapeFactory.getInstance();
	}

	@Test
	void getInstanceTest() {
		Assertions.assertSame(ShapesDict.getInstance(), shapeDict);
	}

	@Test
	void addShapeTest() {
		Shape actual = shapeFactory.getShape(ShapeType.Rectangle, true, 0, 11, 1, 10, 100, 1, "#fff", "#fff","hh");
		shapeDict.clearShapes();
		shapeDict.addShape(actual);

		Assertions.assertTrue(shapeDict.getShapesList().contains(actual));
		Assertions.assertEquals(1, shapeDict.getShapesList().size());

		shapeDict.addShape(null);
		Assertions.assertEquals(1, shapeDict.getShapesList().size());
		
		shapeDict.addShapeSilent(null);
		Assertions.assertEquals(1, shapeDict.getShapesList().size());
		
		shapeDict.addShapeSilentForList(null);
		Assertions.assertEquals(1, shapeDict.getShapesList().size());

	}

	@Test
	void clearShapesTest() {
		shapeDict.addShape(shapeFactory.getShape(ShapeType.Rectangle, true, 0, 11, 1, 10, 100, 1, "#fff", "#fff","hh"));
		shapeDict.clearShapes();
		Assertions.assertEquals(0, shapeDict.getShapesList().size());
	}

	@Test
	void addListShapeTest() {
		List<Shape> actualList = new ArrayList<Shape>();
		Shape actual = shapeFactory.getShape(ShapeType.Rectangle, true, 0, 11, 1, 10, 100, 1, "#fff", "#fff","hh");
		actualList.add(actual);
		shapeDict.clearShapes();

		shapeDict.addListShapes(actualList);
		Assertions.assertEquals(shapeDict.getShapesList().size(), actualList.size());
	}

	@Test
	void observerRegisterUnregisterTest() {
		shapeDict.register(new IObserver() {

			@Override
			public void update(ObservableList obs) {
				// TODO Auto-generated method stub

			}
		});
		Assertions.assertEquals(1, shapeDict.getObserverList().size());

		IObserver observer = new IObserver() {

			@Override
			public void update(ObservableList obs) {
				// TODO Auto-generated method stub

			}
		};
		shapeDict.register(observer);
		Assertions.assertEquals(2, shapeDict.getObserverList().size());

		shapeDict.unregister(observer);
		Assertions.assertEquals(1, shapeDict.getObserverList().size());
		
		shapeDict.unregisterAll();
		Assertions.assertTrue(shapeDict.getObserverList().isEmpty());
	}

}
