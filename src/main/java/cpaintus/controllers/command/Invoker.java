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
		for (int i = 0; i<commands.size(); i++) {
			System.out.println(" Commande " + i + ": " + commands.get(i).hashCode());
		}
	}
	
	public void undo () {
		if (index >= 0) {
			commands.get(index).undo();
			index--;
			System.out.println("Undo");
			for (int i = 0; i<commands.size(); i++) {
				System.out.println(" Commande " + i + ": " + commands.get(i).hashCode());
			}
		}
	}
	
	public void redo () {
		if (index < commands.size()-1) {
			index++; 
			commands.get(index).execute();
			System.out.println("Redo");
			for (int i = 0; i<commands.size(); i++) {
				System.out.println(" Commande " + i + ": " + commands.get(i).hashCode());
			}
		}
	}

}
