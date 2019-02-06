package cpaintusc.models.savestrategy;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileContext {

	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public enum types {
		PNG, XML
	}

	public static void save(types type, BufferedImage image, String path) {
		switch (type) {
		case PNG:
			PNGStrategy pngstrategy = new PNGStrategy();
			if (path != null && path != "" && image != null) {
				pngstrategy.setBufferedImage(image);
				pngstrategy.save(path);
			}
			break;
		case XML:
			XMLStrategy xmlStrategy = new XMLStrategy();
			xmlStrategy.save(path);
			break;
		default:
			LOGGER.log(Level.INFO, "Type d'enregistrement non reconnu");
			break;
		}
	}

	public static void load(types type, String path) {
		switch (type) {
		case PNG:
			PNGStrategy pngstrategy = new PNGStrategy();
			if (path != null && path != "") {
				pngstrategy.load(path);
			}
			break;
		case XML:
			XMLStrategy xmlStrategy = new XMLStrategy();
			xmlStrategy.load(path);
			break;
		default:
			LOGGER.log(Level.INFO, "Type d'enregistrement non reconnu");
			break;
		}
	}
}