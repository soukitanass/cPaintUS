package cPaintUS.models.saveStrategy;

import java.beans.ExceptionListener;
import java.beans.XMLEncoder;
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
			String path2 = path + "\\myXML.xml";
			try {
				fos = new FileOutputStream(path2);
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

	@Override
	public void load(String path) {
		// TODO Auto-generated method stub

	}

}
