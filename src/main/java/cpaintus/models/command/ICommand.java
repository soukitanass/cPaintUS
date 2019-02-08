package cpaintus.models.command;

public interface ICommand {
	public void execute();
	public void undo();
}
