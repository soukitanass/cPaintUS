package cpaintus.models;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cpaintus.models.savestrategy.FileManagerStrategy;
import cpaintus.models.savestrategy.XMLStrategy;
import cpaintus.models.shapes.ShapeFactory;
import cpaintus.models.shapes.ShapeType;
import cpaintus.models.shapes.ShapesDictionnary;

class FileManagerStrategyTest {

	private FileManagerStrategy fileManagerStrategy;
	private final String XML_FILE_TO_LOAD_PATH = "xmlFileLoadTest.xml";
	private final String XML_FILE_TO_SAVE_PATH = "xmlFileSaveTest.xml";
	private ShapesDictionnary shapesDict;
	private ShapeFactory shapeFactory;

	public TemporaryFolderExtension temporaryFolder = new TemporaryFolderExtension();

	@BeforeEach
	void setUp() throws Exception {
		shapesDict = ShapesDictionnary.getInstance();
		shapeFactory = ShapeFactory.getInstance();
		temporaryFolder.beforeEach(null);
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
		shapesDict.addShape(shapeFactory.getShape(ShapeType.RECTANGLE, true, 0, new Point(3333, 11), new Point(1, 10), 100, 1, 2, 7, "#00000",
				"#00000", null, "test"));
		fileManagerStrategy.save(resolvePath(XML_FILE_TO_SAVE_PATH));
		shapesDict.clearShapes();
		fileManagerStrategy.load(resolvePath(XML_FILE_TO_SAVE_PATH));
		assertFalse(shapesDict.getShapesList().isEmpty());

	}

	private String resolvePath(String folder) {
		return temporaryFolder.getRoot().toPath().resolve(folder).toString();
	}

}
