package cpaintus.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cpaintus.models.shapes.ShapeFactory;

class ShapeFactoryTest {

	ShapeFactory shapeFactory;

	@BeforeEach
	void setUp() throws Exception {
		shapeFactory = ShapeFactory.getInstance();
	}

	@Test
	void getInstanceTest() {
		Assertions.assertSame(ShapeFactory.getInstance(), shapeFactory);
	}

	@Test
	void getShapeTest() {
		/*
		 * Shape actual = shapeFactory.getShape(ShapeType.RECTANGLE, true, 0, 11, 0, 0,
		 * 1, 10, 100, 1, "#fff", "#fff", "", "hh"); assertTrue(actual instanceof
		 * cpaintus.models.shapes.Rectangle);
		 * 
		 * actual = shapeFactory.getShape(ShapeType.ELLIPSE, true, 0, 11, 0, 0, 1, 10,
		 * 100, 1, "#fff", "#fff", "", "hh"); assertTrue(actual instanceof
		 * cpaintus.models.shapes.Ellipse); actual.setFillColor("#F0C");
		 * Assertions.assertEquals("#F0C", actual.getFillColor());
		 * 
		 * actual = shapeFactory.getShape(ShapeType.LINE, true, 0, 11, 0, 0, 1, 10, 100,
		 * 1, "#fff", "#fff", "", "hh"); assertTrue(actual instanceof
		 * cpaintus.models.shapes.Line);
		 * 
		 * actual = shapeFactory.getShape(ShapeType.HEART, true, 0, 11, 0, 0, 1, 10,
		 * 100, 1, "#fff", "#fff", "", "hh"); assertTrue(actual instanceof
		 * cpaintus.models.shapes.Heart);
		 * 
		 * actual = shapeFactory.getShape(ShapeType.POKEBALL, true, 0, 11, 0, 0, 1, 10,
		 * 100, 1, "#fff", "#fff", "", "hh"); actual.setFillColor("#F0C");
		 * Assertions.assertEquals(actual.getFillColor(),"#F0C"); assertTrue(actual
		 * instanceof cpaintus.models.shapes.Pokeball);
		 * 
		 * actual = shapeFactory.getShape(ShapeType.Picture, true, 0, 11, 1, 10, 100, 1,
		 * "#fff", "#fff", "hh"); Assertions.assertTrue(actual instanceof
		 * cPaintUS.models.shapes.Picture);
		 * 
		 * actual = shapeFactory.getShape(ShapeType.Text, true, 0, 11, 1, 10, 100,
		 * 1,"#fff", "#fff", "hh"); Assertions.assertTrue(actual instanceof
		 * cPaintUS.models.shapes.Text);
		 */
	}

}
