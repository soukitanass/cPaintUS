package cpaintus.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cpaintus.models.BoundingBox;
import cpaintus.models.shapes.Line;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapeEditor;
import cpaintus.models.shapes.ShapesDictionnary;

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
