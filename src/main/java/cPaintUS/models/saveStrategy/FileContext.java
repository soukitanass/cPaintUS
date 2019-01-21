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
		
	public static void save (types type, BufferedImage image, File file) {
		switch(type) {
			case PNG : 
				PNGStrategy pngstrategy = new PNGStrategy ();
				if (file != null && image != null) {
					pngstrategy.setBufferedImage(image);
					pngstrategy.save(file);
				}
				break; 
			case XML : 
				XMLStrategy xmlstrategy = new XMLStrategy ();
				break; 
			default : 
				System.err.println ("Type d'enregistrement non reconnu"); 
				break; 
		}
	}
}
