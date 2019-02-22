package cpaintus.models.logger;

public enum LogLevel {
	NONE(0),	//  0
	CONSOLE(1), //  1
	FILE(2),	// 10
	ALL(3);		// 11
	
	private final int value;

    private LogLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    public static LogLevel getLevel(int i) {
    	if (i == CONSOLE.getValue()) {
    		return CONSOLE;
    	} else if (i == FILE.getValue()) {
    		return FILE;
    	} else if (i == ALL.getValue()) {
    		return ALL;
    	} else {
    		return NONE;
    	}
    }
}
