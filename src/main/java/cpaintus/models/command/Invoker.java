package cpaintus.models.command;

import java.util.ArrayList;
import java.util.List;


public class Invoker {
	
	private List<ICommand> commands;
	private int index;
	
	public Invoker () {
		commands = new ArrayList<ICommand>();
	}
	
	public void execute (ICommand c) {
		if (index < commands.size()-1) {
			commands.subList(index+1, commands.size()).clear();
		}
		commands.add(c);
		index++;
	}
	
	public void undo () {
		ICommand command =  commands.get(index);
		command.undo();
		index--;
	}
	
	public void redo () {
		index++; 
		ICommand command = commands.get(index);
		command.execute();
	}

}
