package cPaintUS.controllers.drawers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import cPaintUS.models.shapes.Picture;
import cPaintUS.models.shapes.Shape;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PictureDrawerStrategy implements IDrawerStrategy {

	@Override
	public void draw(GraphicsContext gc, Shape shape) {
		Picture picture = (Picture) shape;
		gc.drawImage(decode(picture.getBase64()), picture.getX(), picture.getY());
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
            e.printStackTrace();
        }
        return SwingFXUtils.toFXImage(image, null);
	}

}
