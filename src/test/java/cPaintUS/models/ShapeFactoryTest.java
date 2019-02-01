package cPaintUS.models;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cPaintUS.models.shapes.Shape;
import cPaintUS.models.shapes.ShapeFactory;
import cPaintUS.models.shapes.ShapeType;
import javafx.scene.paint.Color;

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
		Shape actual =  shapeFactory.getShape(ShapeType.Rectangle, true, 0, 11, 1, 10, 100, 1, "#fff", "#fff","hh");
		assertTrue(actual instanceof cPaintUS.models.shapes.Rectangle);
		
		actual =  shapeFactory.getShape(ShapeType.Ellipse, true, 0, 11, 1, 10, 100, 1, "#fff", "#fff","hh");
		assertTrue(actual instanceof cPaintUS.models.shapes.Ellipse);
		actual.setFillColor("#F0C");
		Assert.assertEquals("#F0C", actual.getFillColor());
		
		actual =  shapeFactory.getShape(ShapeType.Line, true, 0, 11, 1, 10, 100, 1, "#fff", "#fff","hh");
		assertTrue(actual instanceof cPaintUS.models.shapes.Line);
		
		actual =  shapeFactory.getShape(ShapeType.Heart, true, 0, 11, 1, 10, 100, 1, "#fff", "#fff","hh");
		assertTrue(actual instanceof cPaintUS.models.shapes.Heart);
		
		actual =  shapeFactory.getShape(ShapeType.Pokeball, true, 0, 11, 1, 10, 100, 1, "#fff", "#fff","hh");
		actual.setFillColor("#F0C");
		Assert.assertEquals(actual.getFillColor(),"#F0C");
		assertTrue(actual instanceof cPaintUS.models.shapes.Pokeball);
		
		actual = shapeFactory.getShape(ShapeType.Picture, true, 0, 11, 1, 10, 100, 1, "#fff", "#fff", "hh");
		assertTrue(actual instanceof cPaintUS.models.shapes.Picture);
		
		actual = shapeFactory.getShape(ShapeType.Text, true, 0, 11, 1, 10, 100, 1,"#fff", "#fff", "hh");
		assertTrue(actual instanceof cPaintUS.models.shapes.Text); 
	}

}
