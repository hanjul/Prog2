package de.hsa.games.fatsquirrel.botapi;

import de.hsa.games.fatsquirrel.core.EntityContext;
import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.entity.EntityType;
import de.hsa.games.fatsquirrel.entity.MasterSquirrel;
import de.hsa.games.fatsquirrel.entity.MiniSquirrel;
import de.hsa.games.fatsquirrel.util.Assert;

public class MiniSquirrelBot implements BotController {

	private final BotControllerFactory botControllerFactory;
	private final BotController miniBotController;
	
	private MiniSquirrel controlled;
	
	public MiniSquirrelBot(final BotControllerFactory botControllerFactory, final BotController miniBotController) {
		this.botControllerFactory = Assert.notNull(botControllerFactory, "botControllerFactory must not be null");
		this.miniBotController = Assert.notNull(miniBotController, "masterBotController must not be null");
	}
	
	public class ControllerContextImpl implements ControllerContext {

		private final EntityContext context;
		
		public ControllerContextImpl(final EntityContext context) {
			this.context = Assert.notNull(context, "context must not be null");
		}
		
		@Override
		public XY getViewLowerLeft() {
			final int height = context.getSize().getY();
			return new XY(0, height);
		}

		@Override
		public XY getViewUpperRight() {
			final int width = context.getSize().getY();
			return new XY(width, 0);
		}

		@Override
		public EntityType getEntityAt(XY xy) {
			return context.getEntityType(xy);
		}

		@Override
		public void move(XY direction) {
			context.tryMove(controlled, direction);
		}

		@Override
		public void spawnMiniBot(XY direction, int energy) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int getEnergy() {
			return controlled.getEnergy();
		}
		
	}

	@Override
	public void nextStep(ControllerContext view) {
		
	}
}