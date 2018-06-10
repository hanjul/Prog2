package de.hsa.games.fatsquirrel.botimpls.team27;

import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;

public class Team27BotControllerFactory implements BotControllerFactory {

	@Override
	public BotController createMasterBotController() {
		return new Team27MasterSquirrelBotController();
	}

	@Override
	public BotController createMiniBotController() {
		return new Team27MiniSquirrelBotController();
	}
}
