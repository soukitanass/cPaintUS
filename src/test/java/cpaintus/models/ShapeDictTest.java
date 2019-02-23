package cpaintus.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.ObservableList;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapeFactory;
import cpaintus.models.shapes.ShapeType;
import cpaintus.models.shapes.ShapesDictionnary;
import cpaintus.models.shapes.Size;
import cpaintus.models.shapes.Stroke;

class ShapeDictTest {

	private ShapesDictionnary shapeDict;

	@BeforeEach
	void setUp() throws Exception {
		shapeDict = ShapesDictionnary.getInstance();
		shapeDict.unregisterAll();
	}

	@Test
	void getInstanceTest() {
		Assertions.assertSame(ShapesDictionnary.getInstance(), shapeDict);
	}

	@Test
	void addShapeTest() {
		Shape actual = ShapeFactory.getShape(ShapeType.RECTANGLE, true, 0, new Point(0, 11), new Point(0, 0), new Size(1, 10), 100, new Stroke(1, "#fff"), "#fff",
				"", "hh");
		shapeDict.clearShapes();
		shapeDict.addShape(actual);

		Assertions.assertEquals(1, shapeDict.getShapesList().size());
		Assertions.assertTrue(shapeDict.getShapesList().contains(actual));

		shapeDict.addShape(null);	
		Assertions.assertEquals(1, shapeDict.getShapesList().size());

		shapeDict.addShape(null, false);
		Assertions.assertEquals(1, shapeDict.getShapesList().size());

		shapeDict.addListShapes(new ArrayList<Shape>());
		Assertions.assertEquals(1, shapeDict.getShapesList().size());

	}

	@Test
	void clearShapesTest() {
		shapeDict.addShape(ShapeFactory.getShape(ShapeType.RECTANGLE, true, 0, new Point(0, 11), new Point(0, 0), new Size(1, 10), 100, new Stroke(1, "#fff"),
				"#fff", "", "hh"));
		shapeDict.clearShapes();
		Assertions.assertEquals(0, shapeDict.getShapesList().size());
	}

	@Test
	void addListShapeTest() {
		List<Shape> actualList = new ArrayList<Shape>();
		Shape actual = ShapeFactory.getShape(ShapeType.RECTANGLE, true, 0, new Point(0, 11), new Point(0, 0),
				new Size(1, 10), 100, new Stroke(1, "#fff"), "#fff",
				"", "hh");
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

	void addShapes() {
		shapeDict.clearShapes();
		shapeDict.addShape(ShapeFactory.getShape(ShapeType.RECTANGLE, true, 3, new Point(6, 11),new Point(0,0), new Size(200, 10), 0, new Stroke(1, "#fff"),
				"#fff56", "", "test"));
		shapeDict.addShape(ShapeFactory.getShape(ShapeType.ELLIPSE, true, 5, new Point(7, 11), new Point(0,0), new Size(220, 100), 30, new Stroke(2, "#fff98"),
				"#ff678", "", "test"));
		shapeDict.addShape(ShapeFactory.getShape(ShapeType.HEART, true, 3, new Point(22, 11), new Point(0,0), new Size(320, 130), 100, new Stroke(6, "#fff45"),
				"#f678f", "", "test"));
		shapeDict.addShape(ShapeFactory.getShape(ShapeType.HEART, true, 3, new Point(22, 11), new Point(0,0), new Size(320, 130), 100,new Stroke(6, "#fff45"),
				"#f678f", "", "test"));
		assertEquals(4, shapeDict.getShapesList().size());
	}

}
