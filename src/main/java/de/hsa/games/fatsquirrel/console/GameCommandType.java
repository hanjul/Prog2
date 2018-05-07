package de.hsa.games.fatsquirrel.console;

import de.hsa.games.fatsquirrel.command.CommandTypeInfo;

public enum GameCommandType implements CommandTypeInfo {

	HELP("help", "list all commands"),
	EXIT("exit", "exit program");

	private final String name;
	private final String help;
	private final Class<?>[] params;
	
	private GameCommandType(final String name, final String help, Class<?>... params) {
		this.name = name;
		this.help = help;
		this.params = params;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getHelpText() {
		return help;
	}

	@Override
	public Class<?>[] getParamTypes() {
		return params;
	}
}
