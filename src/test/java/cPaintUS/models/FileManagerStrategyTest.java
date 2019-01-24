package cPaintUS.models;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import cPaintUS.controllers.SnapshotSingleton;
import cPaintUS.models.saveStrategy.FileContext;
import cPaintUS.models.saveStrategy.FileContext.types;
import cPaintUS.models.saveStrategy.FileManagerStrategy;
import cPaintUS.models.saveStrategy.PNGStrategy;
import cPaintUS.models.saveStrategy.XMLStrategy;
import cPaintUS.models.shapes.ShapeFactory;
import cPaintUS.models.shapes.ShapeType;
import cPaintUS.models.shapes.ShapesDict;

class FileManagerStrategyTest {

	private FileManagerStrategy fileManagerStrategy;
	private final String XML_FILE_TO_LOAD_PATH = "xmlFileLoadTest.xml";
	private final String XML_FILE_TO_SAVE_PATH = "xmlFileSaveTest.xml";
	private final String PNG_FILE_TO_LOAD_PATH = "pngFileLoadTest.png";
	private ShapesDict shapesDict;
	private ShapeFactory shapeFactory;
	private FileContext fileContext;
	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@BeforeEach
	void setUp() throws Exception {
		shapesDict = ShapesDict.getInstance();
		shapeFactory = ShapeFactory.getInstance();
		temporaryFolder.create();
		fileContext = new FileContext();
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
		shapesDict.addShape(shapeFactory.getShape(ShapeType.Rectangle, 0, 11, 1, 10, 100, 1, "#00000", "#00000"));
		fileManagerStrategy.save(resolvePath(XML_FILE_TO_SAVE_PATH));
		shapesDict.clearShapes();
		fileManagerStrategy.load(resolvePath(XML_FILE_TO_SAVE_PATH));
		assertFalse(shapesDict.getShapesList().isEmpty());

	}

	private String resolvePath(String folder) {
		return temporaryFolder.getRoot().toPath().resolve(folder).toString();
	}

	@Test
	void testLoadPNG() {
		System.out.println(getClass().getClassLoader().getResource(PNG_FILE_TO_LOAD_PATH).getPath());
		FileContext.load(types.PNG, getClass().getClassLoader().getResource(PNG_FILE_TO_LOAD_PATH).getPath());
		assertFalse(SnapshotSingleton.getInstance().getImage().isError());
	}

}
