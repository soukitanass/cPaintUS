package cPaintUS.models.saveStrategy;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import javax.imageio.ImageIO;

import cPaintUS.controllers.SnapshotSingleton;
import cPaintUS.models.shapes.Picture;
import cPaintUS.models.shapes.Shape;
import cPaintUS.models.shapes.ShapeFactory;
import cPaintUS.models.shapes.ShapeType;
import cPaintUS.models.shapes.ShapesDict;


public class PNGStrategy implements FileManagerStrategy {

	private ShapesDict shapeDict;
	private ShapeFactory shapeFactory;
	private BufferedImage bufferedImage; 
	private SnapshotSingleton snapshotSingleton;
	
	public PNGStrategy() {
		shapeDict = ShapesDict.getInstance();
		shapeFactory = ShapeFactory.getInstance();
		snapshotSingleton = SnapshotSingleton.getInstance();
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	@Override
	public void save(String path) {
	    if (this.bufferedImage != null && path !=null) {
		    try {
		    	File file = new File(path);
		        ImageIO.write(this.bufferedImage, "png", file);
		    } catch (IOException e) {
	            System.out.println(e.getMessage());
	        }	
	    }
	}

	@Override
	public void load(String path) {
		
			byte[] bytes;
			try {
				bytes = Files.readAllBytes(Paths.get(path));
				String img = Base64.getEncoder().encodeToString(bytes);
				Shape pic = shapeFactory.getShape(ShapeType.Picture, true, 0, 0, 0, 10, 10, 1, "a", img);
				shapeDict.addShape(pic);
				snapshotSingleton.setImage((Picture)pic);
				pic.setHeight(snapshotSingleton.getImage().getHeight());
				pic.setWidth(snapshotSingleton.getImage().getWidth());
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
}
