package cpaintus.models.saveStrategy;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import cpaintus.controllers.SnapshotSingleton;
import cpaintus.models.shapes.Picture;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapeFactory;
import cpaintus.models.shapes.ShapeType;
import cpaintus.models.shapes.ShapesDict;

public class PNGStrategy implements FileManagerStrategy {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
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
		if (this.bufferedImage != null && path != null) {
			try {
				File file = new File(path);
				ImageIO.write(this.bufferedImage, "png", file);
			} catch (IOException e) {
				LOGGER.log(Level.INFO, "Error while opening the file ", e);
			}
		}
	}

	@Override
	public void load(String path) {

		byte[] bytes;
		try {
			bytes = Files.readAllBytes(Paths.get(path));
			String img = Base64.getEncoder().encodeToString(bytes);
			Shape pic = shapeFactory.getShape(ShapeType.Picture, true, 0, 0, 0, 0, 0, 0, 0, 1, "#000000", "#000000",
					img, "");
			snapshotSingleton.setImage((Picture) pic);
			pic.setHeight(snapshotSingleton.getImage().getHeight());
			pic.setWidth(snapshotSingleton.getImage().getWidth());
			shapeDict.addShape(pic);
		} catch (IOException e) {
			LOGGER.log(Level.INFO, "Error while opening the file ", e);
		}

	}
}
