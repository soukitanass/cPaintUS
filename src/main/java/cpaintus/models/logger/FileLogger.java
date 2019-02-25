package cpaintus.models.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileLogger extends BaseLogger {
	private File file;
	private PrintStream stream;
	private Logger logger;

	public FileLogger() {
		super(LogLevel.FILE);
		logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	}

	@Override
	protected void writeMessage(String msg) {
		File directory;
		if (file == null || stream == null) {
			try {
				directory = new File("./events");
				directory.mkdir(); // Will do nothing if already exists
				StringBuilder fileName = new StringBuilder();
				fileName.append("./events/session_");
				fileName.append(session.format(DateTimeFormatter.ofPattern("ddMMuuuu_HHmmss")));
				fileName.append(".txt");
				file = new File(fileName.toString());
				stream = new PrintStream(file);
			} catch (FileNotFoundException e) {
				logger.log(Level.INFO, "Error while creating in file stream", e);
			}

		}
		if (stream != null) {
			stream.println(msg);
		}

	}
}