package cpaintus.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cpaintus.models.shapes.Text;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapeFactory;
import cpaintus.models.shapes.ShapeType;
import cpaintus.models.shapes.Size;
import cpaintus.models.shapes.Stroke;

class ShapesTest {
	@Test
	void textTest() {
		Shape shape = ShapeFactory.getShape(ShapeType.TEXT, true, 0, new Point(0, 11), new Point(0, 0), new Size(1, 10), 100, new Stroke(1, "#fff"), "#fff",
				"", "hh");
		
		String msg = "test";
		((Text) shape).setText(msg);
		assertEquals(msg, ((Text) shape).getText());
		
		Shape copy = shape.makeCopy();
		assertEquals(copy.getShapeId(), shape.getShapeId());
	}
	
	@Test
	void pictureTest() {
		Shape shape = ShapeFactory.getShape(ShapeType.PICTURE, true, 0, new Point(0, 11), new Point(0, 0), new Size(1, 10), 100, new Stroke(1, "#fff"), "#fff",
				"", "hh");
		
		Shape copy = shape.makeCopy();
		assertEquals(copy.getShapeId(), shape.getShapeId());
	}
	
	@Test
	void ellipseTest() {
		Shape shape = ShapeFactory.getShape(ShapeType.ELLIPSE, true, 0, new Point(0, 11), new Point(0, 0), new Size(1, 10), 100, new Stroke(1, "#fff"), "#fff",
				"", "hh");
		
		Shape copy = shape.makeCopy();
		assertEquals(copy.getShapeId(), shape.getShapeId());
	}
	
	@Test
	void pokeballTest() {
		Shape shape = ShapeFactory.getShape(ShapeType.POKEBALL, true, 0, new Point(0, 11), new Point(0, 0), new Size(1, 10), 100, new Stroke(1, "#fff"), "#fff",
				"", "hh");
		
		Shape copy = shape.makeCopy();
		assertEquals(copy.getShapeId(), shape.getShapeId());
	}
	
	@Test
	void heartTest() {
		Shape shape = ShapeFactory.getShape(ShapeType.HEART, true, 0, new Point(0, 11), new Point(0, 0), new Size(1, 10), 100, new Stroke(1, "#fff"), "#fff",
				"", "hh");
		
		Shape copy = shape.makeCopy();
		assertEquals(copy.getShapeId(), shape.getShapeId());
	}
	
	@Test
	void lineTest() {
		Shape shape = ShapeFactory.getShape(ShapeType.LINE, true, 0, new Point(0, 11), new Point(0, 0), new Size(1, 10), 100, new Stroke(1, "#fff"), "#fff",
				"", "hh");
		
		Shape copy = shape.makeCopy();
		assertEquals(copy.getShapeId(), shape.getShapeId());
	}
}