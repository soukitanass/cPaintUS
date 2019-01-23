package cPaintUS.models.saveStrategy;

import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import cPaintUS.models.shapes.Shape;
import cPaintUS.models.shapes.ShapesDict;

public class XMLStrategy implements FileManagerStrategy {

	private ShapesDict shapeDict;
	private List<Shape> shapes;

	public XMLStrategy() {
		shapeDict = ShapesDict.getInstance();
		shapes = shapeDict.getShapesList();
	}

	@Override
	public void save(String path) {
		if (!shapes.isEmpty()) {
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(path);
				XMLEncoder encoder = new XMLEncoder(fos);
				encoder.setExceptionListener(new ExceptionListener() {
					public void exceptionThrown(Exception e) {
						System.out.println("Exception! :" + e.toString());
					}
				});
				encoder.writeObject(shapes);
				encoder.close();
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		} else {
			System.out.println("There is no shapes to save !");
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void load(String path) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(path);
			XMLDecoder decoder = new XMLDecoder(fis);
			List<Shape> readObject = (List<Shape>) decoder.readObject();
			shapes = readObject;
			shapeDict.clearShapes();
			shapeDict.addListShapes(shapes);
			shapeDict.notifyAllObservers();
			decoder.close();
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	

}
