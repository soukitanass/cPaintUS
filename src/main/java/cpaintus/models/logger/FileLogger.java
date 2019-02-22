package cpaintus.models.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileLogger extends BaseLogger {
	private File file;
	private File directory;
	private PrintStream stream;
	private Logger LOGGER;

	public FileLogger() {
		super(LogLevel.FILE);
		LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	}

	@Override
	protected void writeMessage(String msg) {
		if (file == null || stream == null) {
			try {
				directory = new File("./logs");
				directory.mkdir(); // Will do nothing if already exists
				StringBuilder fileName = new StringBuilder();
				fileName.append("./logs/session_");
				fileName.append(session.format(DateTimeFormatter.ofPattern("ddMMuuuu_HHmmss")));
				fileName.append(".txt");
				file = new File(fileName.toString());
				stream = new PrintStream(file);
			} catch (FileNotFoundException e) {
				LOGGER.log(Level.INFO, "Error while creating in file stream", e);
			}
		}
		stream.println(msg);
	}
}