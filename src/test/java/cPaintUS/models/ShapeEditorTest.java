package cPaintUS.models;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cPaintUS.models.shapes.Line;
import cPaintUS.models.shapes.Shape;
import cPaintUS.models.shapes.ShapeEditor;
import cPaintUS.models.shapes.ShapesDict;

public class ShapeEditorTest {
	
	ShapeEditor shapeEditor;
	ShapesDict shapesDict;
	BoundingBox boundingBox;
	Shape shape;
	
	@Before
	public void setup() {
		shapeEditor = ShapeEditor.getInstance();
		shape = new Line();
		shapesDict = ShapesDict.getInstance();
		boundingBox = BoundingBox.getInstance();
	}
	
	@Test
	public void initTest () {
		Assert.assertSame(shapeEditor, ShapeEditor.getInstance());
	}
	
	@Test
	public void editTest () {
		shapeEditor.edit(shape);
		Assert.assertSame(shapeEditor.getShapeToEdit(), shape);
		Assert.assertTrue(shapesDict.getShapesList().contains(shape));
	}
	
	@Test
	public void doneTest () {
		shapeEditor.done();
		Assert.assertSame(shapeEditor.getShapeToEdit(),null);
	}
	
	

}
