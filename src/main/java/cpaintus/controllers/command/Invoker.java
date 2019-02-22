package cpaintus.controllers.command;

import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Invoker extends Observable<IObserver> {

	private ObservableList<Command> commands = FXCollections.observableArrayList();
	private int index = -1;

	public ObservableList<Command> getCommands() {
		return commands;
	}

	public void setCommands(ObservableList<Command> commands) {
		this.commands = commands;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	private static class InvokerHelper {
		private static final Invoker INSTANCE = new Invoker();
	}

	public static Invoker getInstance() {
		return InvokerHelper.INSTANCE;
	}

	public void execute(Command c) {
		c.execute();
		if (index < commands.size() - 1) {
			commands.subList(index + 1, commands.size()).clear();
		}
		commands.add(c);
		index++;
	}

	public void undo() {
		if (index >= 0) {
			commands.get(index).undo();
			index--;
			notifyAllObservers();
		}
	}

	public void redo() {
		if (index < commands.size() - 1) {
			index++;
			commands.get(index).execute();
			notifyAllObservers();
		}
	}

	@Override
	public void notifyAllObservers() {
		for (IObserver obs : getObserverList()) {
			obs.update(cpaintus.models.observable.ObservableList.CHANGEDCOMMAND);
		}
	}

}
