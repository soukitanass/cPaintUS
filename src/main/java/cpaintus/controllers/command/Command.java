package cpaintus.controllers.command;

public abstract class Command {
	
	private String commandID;

	public String getCommandID() {
		return commandID;
	}

	public void setCommandID(String commandID) {
		this.commandID = commandID;
	}
	
	public abstract void execute();
	public abstract void undo();
	

}
