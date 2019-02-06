package cPaintUS.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cPaintUS.models.shapes.Line;
import cPaintUS.models.shapes.Shape;
import cPaintUS.models.shapes.ShapeEditor;
import cPaintUS.models.shapes.ShapesDict;

public class ShapeEditorTest {
	
	ShapeEditor shapeEditor;
	ShapesDict shapesDict;
	BoundingBox boundingBox;
	Shape shape;
	
	@BeforeEach
	public void setup() {
		shapeEditor = ShapeEditor.getInstance();
		shape = new Line();
		shapesDict = ShapesDict.getInstance();
		boundingBox = BoundingBox.getInstance();
	}
	
	@Test
	public void initTest () {
		Assertions.assertSame(shapeEditor, ShapeEditor.getInstance());
	}
	
	@Test
	public void editTest () {
		shapeEditor.edit(shape);
		Assertions.assertSame(shapeEditor.getShapeToEdit(), shape);
		Assertions.assertTrue(shapesDict.getShapesList().contains(shape));
	}
	
	@Test
	public void doneTest () {
		shapeEditor.done();
		Assertions.assertSame(shapeEditor.getShapeToEdit(),null);
	}
	
	

}
