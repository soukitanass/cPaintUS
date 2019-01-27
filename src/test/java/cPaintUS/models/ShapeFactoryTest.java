package cPaintUS.models;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cPaintUS.models.shapes.Shape;
import cPaintUS.models.shapes.ShapeFactory;
import cPaintUS.models.shapes.ShapeType;

class ShapeFactoryTest {

	ShapeFactory shapeFactory;

	@BeforeEach
	void setUp() throws Exception {
		shapeFactory = ShapeFactory.getInstance();
	}

	@Test
	void getInstanceTest() {
		assertSame(ShapeFactory.getInstance(), shapeFactory);
	}

	@Test
	void getShapeTest() {
		Shape actual =  shapeFactory.getShape(ShapeType.Rectangle, 0, 11, 1, 10, 100, 1, "#fff", "#fff");
		assertTrue(actual instanceof cPaintUS.models.shapes.Rectangle);
		
		actual =  shapeFactory.getShape(ShapeType.Ellipse, 0, 11, 1, 10, 100, 1, "#fff", "#fff");
		assertTrue(actual instanceof cPaintUS.models.shapes.Ellipse);
		
		actual =  shapeFactory.getShape(ShapeType.Line, 0, 11, 1, 10, 100, 1, "#fff", "#fff");
		assertTrue(actual instanceof cPaintUS.models.shapes.Line);
		
		actual =  shapeFactory.getShape(ShapeType.Heart, 0, 11, 1, 10, 100, 1, "#fff", "#fff");
		assertTrue(actual instanceof cPaintUS.models.shapes.Heart);
		
		actual =  shapeFactory.getShape(ShapeType.Pokeball, 0, 11, 1, 10, 100, 1, "#fff", "#fff");
		assertTrue(actual instanceof cPaintUS.models.shapes.Pokeball);
		
	}

}
