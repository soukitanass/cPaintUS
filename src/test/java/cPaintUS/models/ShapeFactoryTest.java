package cPaintUS.models;


import org.junit.jupiter.api.Assertions;
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
		Assertions.assertSame(ShapeFactory.getInstance(), shapeFactory);
	}

	@Test
	void getShapeTest() {
		Shape actual =  shapeFactory.getShape(ShapeType.Rectangle, true, 0, 11, 1, 10, 100, 1, "#fff", "#fff","hh");
		Assertions.assertTrue(actual instanceof cPaintUS.models.shapes.Rectangle);
		
		actual =  shapeFactory.getShape(ShapeType.Ellipse, true, 0, 11, 1, 10, 100, 1, "#fff", "#fff","hh");
		Assertions.assertTrue(actual instanceof cPaintUS.models.shapes.Ellipse);
		actual.setFillColor("#F0C");
		Assertions.assertEquals("#F0C", actual.getFillColor());
		
		actual =  shapeFactory.getShape(ShapeType.Line, true, 0, 11, 1, 10, 100, 1, "#fff", "#fff","hh");
		Assertions.assertTrue(actual instanceof cPaintUS.models.shapes.Line);
		
		actual =  shapeFactory.getShape(ShapeType.Heart, true, 0, 11, 1, 10, 100, 1, "#fff", "#fff","hh");
		Assertions.assertTrue(actual instanceof cPaintUS.models.shapes.Heart);
		
		actual =  shapeFactory.getShape(ShapeType.Pokeball, true, 0, 11, 1, 10, 100, 1, "#fff", "#fff","hh");
		actual.setFillColor("#F0C");
		Assertions.assertEquals(actual.getFillColor(),"#F0C");
		Assertions.assertTrue(actual instanceof cPaintUS.models.shapes.Pokeball);
		
		actual = shapeFactory.getShape(ShapeType.Picture, true, 0, 11, 1, 10, 100, 1, "#fff", "#fff", "hh");
		Assertions.assertTrue(actual instanceof cPaintUS.models.shapes.Picture);
		
		actual = shapeFactory.getShape(ShapeType.Text, true, 0, 11, 1, 10, 100, 1,"#fff", "#fff", "hh");
		Assertions.assertTrue(actual instanceof cPaintUS.models.shapes.Text); 
	}

}
