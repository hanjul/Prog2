package de.hsa.games.fatsquirrel.console;

import java.util.Scanner;
import java.util.regex.Pattern;

import de.hsa.games.fatsquirrel.MoveCommand;
import de.hsa.games.fatsquirrel.UI;
import de.hsa.games.fatsquirrel.core.BoardView;

public class ConsoleUI implements UI {

	private static final Pattern INPUT_PATTERN = Pattern.compile("[1-8]");
	private final Scanner input = new Scanner(System.in);

	@Override
	public MoveCommand getCommand() {
		String line;
		while (!INPUT_PATTERN.matcher(line = input.nextLine()).matches())
			;
		final int move = Integer.parseInt(line);
		switch (move) {
		case 1:
			return MoveCommand.MOVE_UP;
		case 2:
			return MoveCommand.MOVE_LEFT;
		case 3:
			return MoveCommand.MOVE_RIGHT;
		case 4:
			return MoveCommand.MOVE_DOWN;
		}
		return null;
	}

	@Override
	public void render(BoardView view) {
		for (int x = 0; x < view.getSize().getX(); x++) {
			String sep = "";
			for (int y = 0; y < view.getSize().getY(); y++) {
				System.out.print(sep);
				System.out.print(view.getEntityType(x, y).ordinal());
				sep = ",";
			}
			System.out.println();
		}
	}
}
