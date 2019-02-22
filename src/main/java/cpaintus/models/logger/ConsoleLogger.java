package cpaintus.models.logger;

public class ConsoleLogger extends BaseLogger {
	public ConsoleLogger() {
		super(LogLevel.CONSOLE);
	}

	@Override
	protected void writeMessage(String msg) {
		System.out.println(msg);
	}
}
