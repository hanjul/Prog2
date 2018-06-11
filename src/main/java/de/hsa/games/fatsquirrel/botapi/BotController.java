package de.hsa.games.fatsquirrel.botapi;

/**
 * Represents the Controller of a bot/a computer controlled Entity.
 */
public interface BotController {

	/**
	 * Is called every time this bot is allowed to move.
	 * 
	 * @param view the Context of this move
	 */
	void nextStep(ControllerContext view);
}
