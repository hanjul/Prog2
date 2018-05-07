package de.hsa.games.fatsquirrel.command;

public class Command {

	private final CommandTypeInfo commandType;
	private final Object[] params;
	
	public Command(final CommandTypeInfo commandType, final Object... params) {
		this.commandType = commandType;
		this.params = params;
	}

	public CommandTypeInfo getCommandType() {
		return commandType;
	}

	public Object[] getParams() {
		return params;
	}
}
