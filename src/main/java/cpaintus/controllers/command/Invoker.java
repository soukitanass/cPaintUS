package cpaintus.controllers.command;

import cpaintus.models.logger.BaseLogger;
import cpaintus.models.logger.ConsoleLogger;
import cpaintus.models.logger.FileLogger;
import cpaintus.models.logger.LogLevel;
import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Invoker extends Observable<IObserver> {

	private ObservableList<Command> commands = FXCollections.observableArrayList();
	private int index = -1;
	private BaseLogger logger;
	
	public Invoker() {		
		logger = new ConsoleLogger();
		logger.setNext(new FileLogger());
	}
	
	private static class InvokerHelper {
		private static final Invoker INSTANCE = new Invoker();
	}

	public static Invoker getInstance() {
		return InvokerHelper.INSTANCE;
	}
	
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

	public void execute(Command c) {
		c.execute();
		if (index < commands.size() - 1) {
			commands.subList(index + 1, commands.size()).clear();
		}
		commands.add(c);
		index++;
		
		logger.message("DO " + c.getCommandID());
	}

	public void undo() {
		if (index >= 0) {
			Command c = commands.get(index);
			c.undo();
			index--;
			notifyAllObservers();
			
			logger.message("UNDO " + c.getCommandID());
		}
	}

	public void redo() {
		if (index < commands.size() - 1) {
			index++;
			Command c = commands.get(index);
			c.execute();
			notifyAllObservers();
			
			logger.message("REDO " + c.getCommandID());
		}
	}

	@Override
	public void notifyAllObservers() {
		for (IObserver obs : getObserverList()) {
			obs.update(cpaintus.models.observable.ObservableList.CHANGEDCOMMAND);
		}
	}

}
