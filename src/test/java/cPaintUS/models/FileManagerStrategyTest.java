package cPaintUS.models;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import cPaintUS.models.saveStrategy.FileManagerStrategy;
import cPaintUS.models.saveStrategy.XMLStrategy;
import cPaintUS.models.shapes.ShapeFactory;
import cPaintUS.models.shapes.ShapeType;
import cPaintUS.models.shapes.ShapesDict;

class FileManagerStrategyTest {

	private FileManagerStrategy fileManagerStrategy;
	private final String XML_FILE_TO_LOAD_PATH = "xmlFileLoadTest.xml";
	private final String XML_FILE_TO_SAVE_PATH = "xmlFileSaveTest.xml";
	private ShapesDict shapesDict;
	private ShapeFactory shapeFactory;
	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@BeforeEach
	void setUp() throws Exception {
		shapesDict = ShapesDict.getInstance();
		shapeFactory = ShapeFactory.getInstance();
		temporaryFolder.create();
	}

	@Test
	void testLoadXML() {
		fileManagerStrategy = new XMLStrategy();
		fileManagerStrategy.load(getClass().getClassLoader().getResource(XML_FILE_TO_LOAD_PATH).getPath());
		assertFalse(shapesDict.getShapesList().isEmpty());
	}

	@Test
	void testSaveXML() {
		fileManagerStrategy = new XMLStrategy();
		shapesDict.clearShapes();
		shapesDict.addShape(shapeFactory.getShape(ShapeType.Rectangle, true, 3333, 11, 1, 10, 100, 1, "#00000", "#00000"));
		fileManagerStrategy.save(resolvePath(XML_FILE_TO_SAVE_PATH));
		shapesDict.clearShapes();
		fileManagerStrategy.load(resolvePath(XML_FILE_TO_SAVE_PATH));
		assertFalse(shapesDict.getShapesList().isEmpty());

	}

	private String resolvePath(String folder) {
		return temporaryFolder.getRoot().toPath().resolve(folder).toString();
	}

}
