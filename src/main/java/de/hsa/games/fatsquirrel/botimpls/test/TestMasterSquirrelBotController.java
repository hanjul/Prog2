package de.hsa.games.fatsquirrel.botimpls.test;

import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.ControllerContext;
import de.hsa.games.fatsquirrel.core.XYSupport;

public class TestMasterSquirrelBotController implements BotController {

	@Override
	public void nextStep(ControllerContext view) {
		view.move(XYSupport.randomDirection());
	}
}