package cPaintUS.models;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sun.javafx.tools.packager.Main;

import cPaintUS.models.saveStrategy.FileContext;
import cPaintUS.models.saveStrategy.FileContext.types;
import cPaintUS.models.saveStrategy.FileManagerStrategy;
import cPaintUS.models.saveStrategy.XMLStrategy;
import cPaintUS.models.shapes.ShapeFactory;
import cPaintUS.models.shapes.ShapeType;
import cPaintUS.models.shapes.ShapesDict;

class FileManagerStrategyTest {

	private FileManagerStrategy fileManagerStrategy;
	private FileContext fileContext;
	private final String XML_FILE_TO_LOAD_PATH = "xmlFileLoadTest.xml";
	private final String XML_FILE_TO_SAVE_PATH = "xmlFileSaveTest.xml";
	private ShapesDict shapesDict;
	private ShapeFactory shapeFactory;

	@BeforeEach
	void setUp() throws Exception {
		fileContext = new FileContext();
		shapesDict = ShapesDict.getInstance();
		shapeFactory = ShapeFactory.getInstance();
	}

	@Test
	void testLoadXML() throws URISyntaxException {
		fileManagerStrategy = new XMLStrategy();
		fileManagerStrategy.load(getClass().getClassLoader().getResource(XML_FILE_TO_LOAD_PATH).getPath());
		assertFalse(shapesDict.getShapesList().isEmpty());
	}

	@Test
	void testSaveXML() {
		shapesDict.clearShapes();
		shapesDict.addShape(shapeFactory.getShape(ShapeType.Rectangle, 0, 11, 1, 10, 100, 1, "#00000", "#00000"));
		fileManagerStrategy.save(getClass().getClassLoader().getResource(XML_FILE_TO_SAVE_PATH).getPath());
		//creation du fichier
		File file = new File(getClass().getClassLoader().getResource(XML_FILE_TO_SAVE_PATH).getFile());
		assertNotNull(file);
		//load bien les shapes
	}

}
