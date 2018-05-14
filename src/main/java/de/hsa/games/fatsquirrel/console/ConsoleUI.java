package de.hsa.games.fatsquirrel.console;

import java.util.Scanner;
import java.util.regex.Pattern;

import de.hsa.games.fatsquirrel.MoveCommand;
import de.hsa.games.fatsquirrel.UI;
import de.hsa.games.fatsquirrel.core.BoardView;
import de.hsa.games.fatsquirrel.entity.EntityType;

public class ConsoleUI implements UI {

	private static final Pattern INPUT_PATTERN = Pattern.compile("[1-8]");
	private final Scanner input = new Scanner(System.in);
	private static final boolean SLOW_MOTION = false;
	
	@Override
	public MoveCommand getCommand() {
		System.out.println("Please enter the next direction (1=right,2=up,3=down,4=left)");
		String line;
		while (!INPUT_PATTERN.matcher(line = input.nextLine()).matches())
			;
//		1 -> right
//		2 -> up
//		3 -> down
//		4 -> left
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

	private static void render(final EntityType type) {
		switch (type) {
		case BAD_BEAST:
			System.out.print('B');
			break;
		case BAD_PLANT:
			System.out.print('b');
			break;
		case GOOD_BEAST:
			System.out.print('G');
			break;
		case GOOD_PLANT:
			System.out.print('g');
			break;
		case MASTER_SQUIRREL:
			System.out.print('M');
			break;
		case MINI_SQUIRREL:
			System.out.print('m');
			break;
		case WALL:
			System.out.print('W');
			break;
		default:
			break;

		}
	}

	@Override
	public void render(BoardView view) {
		if (SLOW_MOTION) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (int x = 0; x < view.getSize().getY(); x++) {
			String sep = "";
			for (int y = 0; y < view.getSize().getX(); y++) {
				System.out.print(sep);
				final EntityType e = view.getEntityType(y, x);
				if (e == null) {
					System.out.print(' ');
				} else {
					render(e);
				}
				sep = ",";
			}
			System.out.println();
		}
		System.out.println();
	}
}
