package cpaintus.models.logger;

import java.time.LocalDateTime;

public abstract class BaseLogger {
	private static LogLevel logLevel;
	private LogLevel logMask;
	protected BaseLogger next;
	protected LocalDateTime session;

	public BaseLogger(LogLevel logLevel) {
		this.logMask = logLevel;
		session = LocalDateTime.now();
	}

	public static void setLogLevel(LogLevel level) {
		logLevel = level;
	}

	public BaseLogger setNext(BaseLogger next) {
		this.next = next;
		return next;
	}

	public void message(String msg) {
		if (logMask != null && logLevel != null) {
			if ((logMask.getValue() & logLevel.getValue()) != 0) // True if all logMask bits are set in logLevel
			{
				writeMessage(msg);
			}
			if (next != null) {
				next.message(msg);
			}
		}
	}

	protected abstract void writeMessage(String msg);
}