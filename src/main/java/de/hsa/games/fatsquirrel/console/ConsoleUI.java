package de.hsa.games.fatsquirrel.console;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import de.hsa.games.fatsquirrel.UI;
import de.hsa.games.fatsquirrel.command.Command;
import de.hsa.games.fatsquirrel.command.CommandScanner;
import de.hsa.games.fatsquirrel.core.BoardView;
import de.hsa.games.fatsquirrel.entity.EntityType;

public class ConsoleUI implements UI {

	private static final boolean SLOW_MOTION = false;
	
	@Override
	public Command getCommand() {
		final CommandScanner scanner = new CommandScanner(GameCommandType.values(), new BufferedReader(new InputStreamReader(System.in)), System.out);
		return scanner.next();
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

	@Override
	public void message(String msg) {
		System.out.println(msg);
	}
}
