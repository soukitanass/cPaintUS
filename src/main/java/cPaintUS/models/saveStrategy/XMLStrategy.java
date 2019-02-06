package cpaintus.models.savestrategy;

import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapesDictionnary;

public class XMLStrategy implements FileManagerStrategy {

	private static final  Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static final String ERROR_MESSAGE = "Error while opening the file ";
	private ShapesDictionnary shapeDict;
	private List<Shape> shapes;

	public XMLStrategy() {
		shapeDict = ShapesDictionnary.getInstance();
		shapes = shapeDict.getShapesList();
	}

	@Override
	public void save(String path) {
		if (!shapes.isEmpty()) {
			FileOutputStream fos = null;
			XMLEncoder encoder = null;
			try {
				fos = new FileOutputStream(path);
				encoder = new XMLEncoder(fos);
				encoder.setExceptionListener(new ExceptionListener() {
					public void exceptionThrown(Exception e) {
						LOGGER.log(Level.INFO,ERROR_MESSAGE , e);
					}
				});
				encoder.writeObject(shapes);
			} catch (FileNotFoundException e) {
				LOGGER.log(Level.INFO, ERROR_MESSAGE, e);
			} finally {
				if (encoder != null)
					encoder.close();
				try {
					if (fos != null)
						fos.close();
				} catch (IOException ex) {
					LOGGER.log(Level.INFO, ERROR_MESSAGE, ex);
				}
			}
		} else {
			LOGGER.log(Level.INFO, "There is no shapes to save !");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void load(String path) {
		FileInputStream fis = null;
		XMLDecoder decoder = null;
		try {
			fis = new FileInputStream(path);
			decoder = new XMLDecoder(fis);
			List<Shape> readObject = (List<Shape>) decoder.readObject();
			shapes = readObject;
			shapeDict.clearShapes();
			shapeDict.addListShapes(shapes);
			shapeDict.notifyAllObservers();

		} catch (FileNotFoundException e) {
			LOGGER.log(Level.INFO, ERROR_MESSAGE, e);
		} finally {
			if (decoder != null)
				decoder.close();
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				LOGGER.log(Level.INFO,ERROR_MESSAGE, ex);
			}
		}
	}
}
