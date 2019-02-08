package cpaintus.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapeFactory;
import cpaintus.models.shapes.ShapeType;

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
		Shape actual =  shapeFactory.getShape(ShapeType.RECTANGLE, true, 0, 0, 11, 0, 0, 1, 10, 100, 1, "#fff", "#fff", "", "hh");
		Assertions.assertTrue(actual instanceof cpaintus.models.shapes.Rectangle);
		
		actual =  shapeFactory.getShape(ShapeType.ELLIPSE, true, 0, 0, 11, 0, 0, 1, 10, 100, 1, "#fff", "#fff", "", "hh");
		Assertions.assertTrue(actual instanceof cpaintus.models.shapes.Ellipse);
		
		actual =  shapeFactory.getShape(ShapeType.LINE, true, 0, 0, 11, 0, 0, 1, 10, 100, 1, "#fff", "#fff", "", "hh");
		Assertions.assertTrue(actual instanceof cpaintus.models.shapes.Line);
		
		actual =  shapeFactory.getShape(ShapeType.HEART, true, 0, 0, 11, 0, 0, 1, 10, 100, 1, "#fff", "#fff", "", "hh");
		Assertions.assertTrue(actual instanceof cpaintus.models.shapes.Heart);
		
		actual =  shapeFactory.getShape(ShapeType.POKEBALL, true, 0, 0, 11, 0, 0, 1, 10, 100, 1, "#fff", "#fff", "", "hh");
		Assertions.assertTrue(actual instanceof cpaintus.models.shapes.Pokeball);
		
		actual = shapeFactory.getShape(ShapeType.PICTURE, true, 0, 0, 11, 0, 0, 1, 10, 100, 1, "#fff", "#fff", "", "hh");
		Assertions.assertTrue(actual instanceof cpaintus.models.shapes.Picture);
		
		actual = shapeFactory.getShape(ShapeType.TEXT, true, 0, 0, 11, 0, 0, 1, 10, 100, 1, "#fff", "#fff", "", "hh");
		Assertions.assertTrue(actual instanceof cpaintus.models.shapes.Text);
	}

}
