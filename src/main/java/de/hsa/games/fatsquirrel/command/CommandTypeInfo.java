package de.hsa.games.fatsquirrel.command;

import de.hsa.games.fatsquirrel.Game;
import de.hsa.games.fatsquirrel.entity.MasterSquirrel;

public interface CommandTypeInfo {

	String getName();
	String getHelpText();
	Class<?>[] getParamTypes();
	boolean execute(Game game, MasterSquirrel master);
}
