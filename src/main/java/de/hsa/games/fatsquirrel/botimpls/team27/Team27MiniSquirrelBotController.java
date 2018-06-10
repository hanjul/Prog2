package de.hsa.games.fatsquirrel.botimpls.team27;

import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.ControllerContext;
import de.hsa.games.fatsquirrel.core.XYSupport;

public class Team27MiniSquirrelBotController implements BotController {

	@Override
	public void nextStep(ControllerContext view) {
		view.move(XYSupport.randomDirection());
	}
}
