package de.hsa.games.fatsquirrel.botimpls.test;

import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;
import de.hsa.games.fatsquirrel.botimpls.team27.Team27MasterSquirrelBotController;
import de.hsa.games.fatsquirrel.botimpls.team27.Team27MiniSquirrelBotController;

public class TestBotControllerFactory implements BotControllerFactory {

	@Override
	public BotController createMasterBotController() {
		return new Team27MasterSquirrelBotController();
	}

	@Override
	public BotController createMiniBotController() {
		return new Team27MiniSquirrelBotController();
	}
}
