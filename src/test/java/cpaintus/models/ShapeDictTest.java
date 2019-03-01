package cpaintus.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cpaintus.models.composite.ShapesGroup;
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
	
	Shape getShape() {
		return ShapeFactory.getShape(ShapeType.RECTANGLE, true, 0, new Point(0, 11), new Point(0, 0), new Size(1, 10), 100, new Stroke(1, "#fff"), "#fff",
				"", "hh");
	}

	@BeforeEach
	void setUp() throws Exception {
		shapeDict = ShapesDictionnary.getInstance();
		shapeDict.unregisterAll();
	}
	
	@AfterEach
	void tearDown() {
		shapeDict.clearShapes();
	}

	@Test
	void getInstanceTest() {
		Assertions.assertSame(ShapesDictionnary.getInstance(), shapeDict);
	}
	
	@Test
	void getSetShapesDictTest() {
		LinkedHashMap<String, Shape> shapesDict = new LinkedHashMap<>();
		shapesDict.put("test", getShape());
		
		shapeDict.setShapesDict(shapesDict);
		Assertions.assertSame(shapeDict.getShapesDict(), shapesDict);
	}

	@Test
	void addShapeTest() {
		Shape actual = getShape();
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
	void addShapesTest() {
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
	
	@Test
	void addGroupTest() {
		Shape shape1 = getShape();
		Shape shape2 = getShape();
		ShapesGroup group = new ShapesGroup();
		group.add(shape1);
		group.add(shape2);
		
		shapeDict.addShape(group);
		
		List<Shape> shapesList = shapeDict.getShapesList();
		Assertions.assertEquals(1, shapesList.size());
		Assertions.assertSame(group, shapesList.get(0));
		
		List<Shape> fullShapesList = shapeDict.getFullShapesList();
		Assertions.assertEquals(2, fullShapesList.size());
		Assertions.assertSame(shape1, fullShapesList.get(0));
		Assertions.assertSame(shape2, fullShapesList.get(1));
	}
	
	@Test 
	void addShapeWithParentsTest() {
		ShapesGroup group = new ShapesGroup();
		shapeDict.addShape(group);
		
		List<ShapesGroup> parents = new ArrayList<ShapesGroup>();
		parents.add(group);
		Shape shape = getShape();
		shapeDict.addShape(shape, parents);
		
		List<Shape> shapesList = shapeDict.getShapesList();
		Assertions.assertEquals(1, shapesList.size());
		Assertions.assertSame(group, shapesList.get(0));
		
		List<Shape> fullShapesList = shapeDict.getFullShapesList();
		Assertions.assertEquals(1, fullShapesList.size());
		Assertions.assertSame(shape, fullShapesList.get(0));
	}
	
	@Test 
	void addShapeWithEmptyParentsTest() {		
		List<ShapesGroup> parents = new ArrayList<ShapesGroup>();
		Shape shape = getShape();
		shapeDict.addShape(shape, parents);
		
		List<Shape> shapesList = shapeDict.getShapesList();
		Assertions.assertEquals(1, shapesList.size());
		Assertions.assertSame(shape, shapesList.get(0));
	}

	@Test
	void addListShapeTest() {
		List<Shape> actualList = new ArrayList<Shape>();
		Shape actual = getShape();
		actualList.add(actual);
		shapeDict.clearShapes();

		shapeDict.addListShapes(actualList);
		Assertions.assertEquals(shapeDict.getShapesList().size(), actualList.size());
	}
	
	@Test
	void clearShapesTempoTest() {
		shapeDict.addShape(getShape());
		shapeDict.clearShapesTempo();
		Assertions.assertEquals(0, shapeDict.getShapesList().size());
	}

	@Test
	void clearShapesTest() {
		shapeDict.addShape(getShape());
		shapeDict.clearShapes();
		Assertions.assertEquals(0, shapeDict.getShapesList().size());
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
	
	@Test void removeChildShape() {
		ShapesGroup parent = new ShapesGroup();
		Shape child = getShape();
		parent.add(child);
		shapeDict.addShape(parent);
		
		shapeDict.removeShape(child);
		Assertions.assertEquals(0, parent.getShapes().size());
	}
	
	@Test void removeGroup() {
		ShapesGroup parent = new ShapesGroup();
		Shape child = getShape();
		parent.add(child);
		shapeDict.addShape(parent);
		
		shapeDict.removeShape(parent);
		Assertions.assertEquals(1, shapeDict.getShapesList().size());
		Assertions.assertSame(child, shapeDict.getShapesList().get(0));
	}

}
