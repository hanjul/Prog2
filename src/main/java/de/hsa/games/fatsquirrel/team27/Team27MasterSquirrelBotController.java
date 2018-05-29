package de.hsa.games.fatsquirrel.team27;

import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.ControllerContext;
import de.hsa.games.fatsquirrel.core.XYSupport;

public class Team27MasterSquirrelBotController implements BotController {

	@Override
	public void nextStep(ControllerContext view) {
		view.move(XYSupport.randomDirection());
	}
}
