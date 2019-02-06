package cpaintus.controllers.drawers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import cpaintus.models.shapes.Picture;
import cpaintus.models.shapes.Shape;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PictureDrawerStrategy implements IDrawerStrategy {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Override
	public void draw(GraphicsContext gc, Shape shape) {
		Picture picture = (Picture) shape;
		gc.drawImage(decode(picture.getBase64()), 0, 0);
	}

	private Image decode(String base64) {
		BufferedImage image = null;
		byte[] imageByte;
		try {
			imageByte = Base64.getDecoder().decode(base64);
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
			image = ImageIO.read(bis);
			bis.close();
		} catch (Exception e) {
			LOGGER.log(Level.INFO, "Error while opening the file ", e);
		}
		return SwingFXUtils.toFXImage(image, null);
	}

}
