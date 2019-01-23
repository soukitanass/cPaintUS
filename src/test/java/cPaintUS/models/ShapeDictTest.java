package cPaintUS.models;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
		assertSame(ShapesDict.getInstance(), shapeDict);
	}

	@Test
	void addShapeTest() {
		Shape actual = shapeFactory.getShape(ShapeType.Rectangle, 0, 11, 1, 10, 100, 1, "#fff", "#fff");
		shapeDict.addShape(actual);

		assertTrue(shapeDict.getShapesList().contains(actual));
		assertEquals(1, shapeDict.getShapesList().size());

		shapeDict.addShape(null);
		assertEquals(1, shapeDict.getShapesList().size());
	}

	@Test
	void clearShapesTest() {
		shapeDict.addShape(shapeFactory.getShape(ShapeType.Rectangle, 0, 11, 1, 10, 100, 1, "#fff", "#fff"));
		shapeDict.clearShapes();
		assertEquals(0, shapeDict.getShapesList().size());
	}

	@Test
	void addListShapeTest() {
		List<Shape> actualList = new ArrayList<Shape>();
		Shape actual = shapeFactory.getShape(ShapeType.Rectangle, 0, 11, 1, 10, 100, 1, "#fff", "#fff");
		actualList.add(actual);
		shapeDict.clearShapes();

		shapeDict.addListShapes(actualList);
		assertEquals(shapeDict.getShapesList().size(), actualList.size());
	}

}
