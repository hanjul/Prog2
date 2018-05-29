package de.hsa.games.fatsquirrel.command;

import de.hsa.games.fatsquirrel.Game;
import de.hsa.games.fatsquirrel.entity.MasterSquirrel;

public enum MyFavoriteCommandType implements CommandTypeInfo {

	HELP("help", "list all commands"),
	EXIT("exit", "exit program"),
	ADDI("addi", "<param1> <param2> simple integer add ", int.class, int.class),
	ADDF("addf", "<param1> <param2> simple float add ", float.class, float.class),
	ECHO("echo", "<param1> <param2> echos param1 string param2 times", String.class, int.class);

	private final String name;
	private final String help;
	private final Class<?>[] params;
	
	private MyFavoriteCommandType(final String name, final String help, Class<?>... params) {
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
	public boolean execute(Game game, MasterSquirrel master) {
		// TODO Auto-generated method stub
		return false;
	}
}
