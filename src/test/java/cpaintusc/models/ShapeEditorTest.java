package cpaintusc.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cpaintusc.models.BoundingBox;
import cpaintusc.models.shapes.Line;
import cpaintusc.models.shapes.Shape;
import cpaintusc.models.shapes.ShapeEditor;
import cpaintusc.models.shapes.ShapesDictionnary;

public class ShapeEditorTest {

	ShapeEditor shapeEditor;
	ShapesDictionnary shapesDict;
	BoundingBox boundingBox;
	Shape shape;

	@BeforeEach
	public void setup() {
		shapeEditor = ShapeEditor.getInstance();
		shape = new Line();
		shapesDict = ShapesDictionnary.getInstance();
		boundingBox = BoundingBox.getInstance();
	}

	@Test
	public void initTest() {
		Assertions.assertSame(shapeEditor, ShapeEditor.getInstance());
	}

	@Test
	public void editTest() {
		shapeEditor.edit(shape);
		Assertions.assertSame(shapeEditor.getShapeToEdit(), shape);
		Assertions.assertTrue(shapesDict.getShapesList().contains(shape));
	}

	@Test
	public void doneTest() {
		shapeEditor.done();
		Assertions.assertSame(shapeEditor.getShapeToEdit(), null);
	}

}
