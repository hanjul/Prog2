package de.hsa.games.fatsquirrel.botapi;

public interface BotControllerFactory {

	/**
	 * Returns a new {@link MasterSquirrelBot}.
	 * 
	 * @return a new MasterSquirrelBot
	 */
	BotController createMasterBotController();
	
	/**
	 * Returns a new {@link MiniSquirrelBot}.
	 * 
	 * @return a new MiniSquirrelBot
	 */
	BotController createMiniBotController();
}
