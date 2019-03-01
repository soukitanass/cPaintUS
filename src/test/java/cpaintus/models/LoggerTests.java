package cpaintus.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cpaintus.models.logger.BaseLogger;
import cpaintus.models.logger.ConsoleLogger;
import cpaintus.models.logger.FileLogger;
import cpaintus.models.logger.LogLevel;

class LoggerTests {	
	private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private static final PrintStream originalOut = System.out;

	@BeforeAll
	public static void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	}

	@AfterAll
	public static void restoreStreams() {
	    System.setOut(originalOut);
	}
	
	@Test
	void LogLevelGetValueTest() {
		assertEquals(0, LogLevel.NONE.getValue());
		assertEquals(1, LogLevel.CONSOLE.getValue());
		assertEquals(2, LogLevel.FILE.getValue());
		assertEquals(3, LogLevel.ALL.getValue());
	}
	
	@Test
	void LogLevelGetLevelTest() {
		assertEquals(LogLevel.NONE, LogLevel.getLevel(0));
		assertEquals(LogLevel.CONSOLE, LogLevel.getLevel(1));
		assertEquals(LogLevel.FILE, LogLevel.getLevel(2));
		assertEquals(LogLevel.ALL, LogLevel.getLevel(3));
	}
	
	@Test 
	void LoggersTest() {
		String msg = "test";
		BaseLogger.setLogLevel(LogLevel.ALL);
		ConsoleLogger consoleLogger = new ConsoleLogger();
		FileLogger fileLogger = new FileLogger();
		
		consoleLogger.setNext(fileLogger);
		consoleLogger.message(msg);
		assertTrue(outContent.toString().contains(msg));
		
		File directory = new File("./events");
		assertTrue(directory.isDirectory());
	}
}

