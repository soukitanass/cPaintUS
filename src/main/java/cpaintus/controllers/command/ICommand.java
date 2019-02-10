package cpaintus.controllers.command;

public interface ICommand {
	public void execute();
	public void undo();
}
