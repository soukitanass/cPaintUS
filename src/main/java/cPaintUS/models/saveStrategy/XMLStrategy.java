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
import cpaintus.models.shapes.ShapesDict;

public class XMLStrategy implements FileManagerStrategy {

	private static final  Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private ShapesDict shapeDict;
	private List<Shape> shapes;

	public XMLStrategy() {
		shapeDict = ShapesDict.getInstance();
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
						System.out.println("Exception! :" + e.toString());
					}
				});
				encoder.writeObject(shapes);
			} catch (FileNotFoundException e) {
				LOGGER.log(Level.INFO, "Error while opening the file ", e);
			} finally {
				if (encoder != null)
					encoder.close();
				try {
					if (fos != null)
						fos.close();
				} catch (IOException ex) {
					LOGGER.log(Level.INFO, "Error while opening the file ", ex);
				}
			}
		} else {
			System.out.println("There is no shapes to save !");
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
			LOGGER.log(Level.INFO, "Error while opening the file ", e);
		} finally {
			if (decoder != null)
				decoder.close();
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				LOGGER.log(Level.INFO, "Error while opening the file ", ex);
			}
		}
	}
}
