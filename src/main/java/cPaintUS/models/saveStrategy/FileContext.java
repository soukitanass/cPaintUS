package cPaintUS.models.saveStrategy;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

public class FileContext {
	
	public enum types {
		PNG, XML
	}
	
	public FileContext() {

	}
		
	public static void save (types type, BufferedImage image, String path) {
		switch(type) {
			case PNG : 
				PNGStrategy pngstrategy = new PNGStrategy ();
				if (path != null && path != "" && image != null) {
					pngstrategy.setBufferedImage(image);
					pngstrategy.save(path);
				}
				break; 
			case XML : 
				XMLStrategy xmlStrategy = new XMLStrategy ();
				xmlStrategy.save(path);
				break; 
			default : 
				System.err.println ("Type d'enregistrement non reconnu"); 
				break; 
		}
	}
	
	public static void load (types type, String path) {
		switch(type) {
			case PNG : 
				PNGStrategy pngstrategy = new PNGStrategy ();
				if (path != null && path != "") {
					pngstrategy.load(path);
				}
				break; 
			case XML : 
				XMLStrategy xmlStrategy = new XMLStrategy ();
				xmlStrategy.load(path);
				break; 
			default : 
				System.err.println ("Type d'enregistrement non reconnu"); 
				break; 
		}
	}
}
