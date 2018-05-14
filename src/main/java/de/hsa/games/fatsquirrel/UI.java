package de.hsa.games.fatsquirrel;

import de.hsa.games.fatsquirrel.command.Command;
import de.hsa.games.fatsquirrel.core.BoardView;

public interface UI {

	Command getCommand();
	void render(BoardView view);
	void message(String msg);
}
