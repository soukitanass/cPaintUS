package cpaintus.controllers.command;

import java.util.ArrayList;
import java.util.List;

public class Invoker {
	
	private List<ICommand> commands = new ArrayList<ICommand>();
	private int index = -1;
	
	
	private static class InvokerHelper {
		private static final Invoker INSTANCE = new Invoker();
	}

	public static Invoker getInstance() {
		return InvokerHelper.INSTANCE;
	}
	
	public void execute (ICommand c) {
		c.execute();
		if (index < commands.size()-1) {
			commands.subList(index+1, commands.size()).clear();
		}
		commands.add(c);
		index++;
	}
	
	public void undo () {
		if (index >= 0) {
			commands.get(index).undo();
			for (ICommand command : commands) {
				System.out.println(command);
				if (command instanceof EditZCommand) {
					System.out.println(((EditZCommand) command).getShapeAttr());
				}
			}
			System.out.println("Current : " + commands.get(index));
			index--;
		}
	}
	
	public void redo () {
		if (index < commands.size()-1) {
			index++; 
			for (ICommand command : commands) {
				System.out.println(command);
			}
			System.out.println("Current : " + commands.get(index));
			commands.get(index).execute();
		}
	}

}
