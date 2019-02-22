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
    	switch(i) {
    	case 1:
    		return CONSOLE;
    	case 2:
    		return FILE;
    	case 3:
    		return ALL;
    	default:
    		return NONE;
    	}
    }
}
