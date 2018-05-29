package de.hsa.games.fatsquirrel.console;

import de.hsa.games.fatsquirrel.Game;
import de.hsa.games.fatsquirrel.command.CommandTypeInfo;
import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.core.XYSupport;
import de.hsa.games.fatsquirrel.entity.MasterSquirrel;
import de.hsa.games.fatsquirrel.entity.MiniSquirrel;

public enum GameCommandType implements CommandTypeInfo {

	NONE("none", "none"),
	HELP("help", "list all commands"),
	EXIT("exit", "exit program"),
	ALL("all", "?"),
	LEFT("left", "move left"),
	UP("up", "move up"),
	DOWN("down", "move down"),
	RIGHT("right", "move right"),
	MASTER_ENERGY("master_energy", "master energy"),
	SPAWN_MINI("spawn_mini", "spawn mini");

	private final String name;
	private final String help;
	private final Class<?>[] params;
	
	private GameCommandType(final String name, final String help, Class<?>... params) {
		this.name = name;
		this.help = help;
		this.params = params;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getHelpText() {
		return help;
	}

	@Override
	public Class<?>[] getParamTypes() {
		return params;
	}

	@Override
	public boolean execute(Game game, MasterSquirrel master) {
		switch (this) {
		 case HELP:
             for (GameCommandType type : GameCommandType.values())
                 game.getUI().message(type.getName() + " " + type.getHelpText());
             return true;
         case EXIT:
             game.getUI().message("Exiting");
             System.exit(0);
             return false;
         case ALL:
             throw new UnsupportedOperationException();
         case LEFT:
             master.setDirection(XY.LEFT);
             return false;
         case UP:
             master.setDirection(XY.UP);
             return false;
         case DOWN:
             master.setDirection(XY.DOWN);
             return false;
         case RIGHT:
             master.setDirection(XY.RIGHT);
             return false;
         case MASTER_ENERGY:
             game.getUI().message(String.valueOf(master.getEnergy()));
             return true;
         case SPAWN_MINI:
             game.getState().flattenedBoard().spawnMiniSquirrel(new MiniSquirrel(100, master.getLocation().plus(XYSupport.randomDirection())));
             return true;
		default:
			throw new InternalError();
		}
	}
}
