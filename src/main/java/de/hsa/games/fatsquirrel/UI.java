package de.hsa.games.fatsquirrel;

import de.hsa.games.fatsquirrel.core.BoardView;

public interface UI {

	MoveCommand getCommand();
	void render(BoardView view);
}
