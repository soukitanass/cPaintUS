package cPaintUS.models.saveStrategy;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

public class PNGStrategy implements FileManagerStrategy {

	@Override
	public void save(Pane pane, File file) {
	    WritableImage image = pane.snapshot(new SnapshotParameters(), null);
	    if (image != null && pane != null && file !=null) {
		    try {
		        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
		    } catch (IOException e) {
	            System.out.println(e.getMessage());
	        }	
	    }
	}

	@Override
	public void load(String path) {
		// TODO Auto-generated method stub
		
	}
	

}
