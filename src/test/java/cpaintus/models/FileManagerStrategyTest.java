package cpaintus.models;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cpaintus.models.savestrategy.FileManagerStrategy;
import cpaintus.models.savestrategy.XMLStrategy;
import cpaintus.models.shapes.ShapeFactory;
import cpaintus.models.shapes.ShapeType;
import cpaintus.models.shapes.ShapesDictionnary;
import cpaintus.models.shapes.Size;
import cpaintus.models.shapes.Stroke;

class FileManagerStrategyTest {

	private FileManagerStrategy fileManagerStrategy;
	private final String XML_FILE_TO_LOAD_PATH = "xmlFileLoadTest.xml";
	private final String XML_FILE_TO_SAVE_PATH = "xmlFileSaveTest.xml";
	private ShapesDictionnary shapesDict;

	public TemporaryFolderExtension temporaryFolder = new TemporaryFolderExtension();

	@BeforeEach
	void setUp() throws Exception {
		shapesDict = ShapesDictionnary.getInstance();
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
		shapesDict.addShape(ShapeFactory.getShape(ShapeType.RECTANGLE, true, 0, new Point(3333, 11), new Point(1, 10),
				new Size(100, 1), 2,new Stroke( 7, "#000000"),
				"#000000", null, "test"));
		fileManagerStrategy.save(resolvePath(XML_FILE_TO_SAVE_PATH));
		shapesDict.clearShapes();
		fileManagerStrategy.load(resolvePath(XML_FILE_TO_SAVE_PATH));
		assertFalse(shapesDict.getShapesList().isEmpty());

	}

	private String resolvePath(String folder) {
		return temporaryFolder.getRoot().toPath().resolve(folder).toString();
	}

}
