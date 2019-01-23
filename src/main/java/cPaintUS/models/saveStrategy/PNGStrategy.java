package cPaintUS.models.saveStrategy;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cPaintUS.controllers.SnapshotSingleton;
import javafx.scene.image.Image;


public class PNGStrategy implements FileManagerStrategy {
	
	private BufferedImage bufferedImage; 

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
		Image image = new Image(path);
		if (!image.isError()) {
			SnapshotSingleton.getInstance().setImage(image);			
		} else {
			System.out.println("Image cannot be created");
		}
	}
	

}
