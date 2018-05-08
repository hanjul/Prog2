package de.hsa.games.fatsquirrel.console;

import de.hsa.games.fatsquirrel.command.CommandTypeInfo;

public enum GameCommandType implements CommandTypeInfo {

	HELP("help", "shows a help dialog"),
	EXIT("exit", "exit program"),
	ALl("all", "list all commands"),
	LEFT("left", "left direction"),
	UP("up", "up direction"),
	DOWN("down", "down direction"),
	RIGHT("right", "right direction"),
	MASTER_ENERGY("master_energy", "master energy"),
	SPAWN_MINI("spawn_mini", "spawn mini");

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

	@Override
	public void execute(Object target, Object... args) {
		// TODO Auto-generated method stub
		
	}
}
