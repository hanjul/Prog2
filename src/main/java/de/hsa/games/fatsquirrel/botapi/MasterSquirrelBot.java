package de.hsa.games.fatsquirrel.botapi;

import de.hsa.games.fatsquirrel.core.EntityContext;
import de.hsa.games.fatsquirrel.core.OutOfViewException;
import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.entity.EntityType;
import de.hsa.games.fatsquirrel.entity.MasterSquirrel;
import de.hsa.games.fatsquirrel.entity.MiniSquirrel;
import de.hsa.games.fatsquirrel.util.Assert;

/**
 * A {@link MasterSquirrel} implementation that represents a bot.
 */
public class MasterSquirrelBot extends MasterSquirrel {

	private final BotControllerFactory botControllerFactory;
	private final BotController masterBotController;

	public MasterSquirrelBot(final XY location, final BotControllerFactory botControllerFactory,
			final BotController masterBotController) {
		super(location);
		this.botControllerFactory = Assert.notNull(botControllerFactory, "botControllerFactory must not be null");
		this.masterBotController = Assert.notNull(masterBotController, "masterBotController must not be null");
	}

	public class ControllerContextImpl implements ControllerContext {

		private static final int VIEW_DISTANCE = 31;

		private final EntityContext context;

		public ControllerContextImpl(final EntityContext context) {
			this.context = Assert.notNull(context, "context must not be null");
		}

		@Override
		public XY getViewLowerLeft() {
			final XY size = context.getSize();
			final int width = Math.max(size.x, getLocation().x - VIEW_DISTANCE);
			final int height = Math.max(size.y, getLocation().y - VIEW_DISTANCE);
			return new XY(width, height);
		}

		@Override
		public XY getViewUpperRight() {
			final XY size = context.getSize();
			final int width = Math.min(size.x, getLocation().x + VIEW_DISTANCE);
			final int height = Math.min(size.y, getLocation().y + VIEW_DISTANCE);
			return new XY(width, height);
		}

		@Override
		public EntityType getEntityAt(XY xy) {
			if (xy.x < getViewLowerLeft().x || xy.y > getViewLowerLeft().y) {
				throw new OutOfViewException();
			}
			if (xy.x > getViewLowerLeft().x || xy.y < getViewLowerLeft().y) {
				throw new OutOfViewException();
			}
			return context.getEntityType(xy);
		}

		@Override
		public void move(XY direction) {
			context.tryMove(MasterSquirrelBot.this, direction);
		}

		@Override
		public void spawnMiniBot(XY direction, int energy) {
			final MiniSquirrel mini = new MiniSquirrelBot(energy, getLocation().plus(direction), botControllerFactory,
					botControllerFactory.createMiniBotController());
			context.spawnMiniSquirrel(mini);
		}

		@Override
		public int getEnergy() {
			return getEnergy();
		}

		@Override
		public XY locate() {
			return getLocation();
		}

		@Override
		public boolean isMine(XY xy) {
			final EntityType otherType = getEntityAt(xy);
			if (otherType == EntityType.MINI_SQUIRREL) {
				return getChildren().stream().anyMatch(m -> m.getLocation().equals(xy));
			}
			return false;
		}

		@Override
		public void implode(int impactRadius) {
			throw new UnsupportedOperationException();
		}

		@Override
		public XY directionOfMaster() {
			throw new UnsupportedOperationException();
		}

		@Override
		public long getRemainingSteps() {
			return -1;
		}
	}

	@Override
	public void onNextStep(EntityContext context) {
		masterBotController.nextStep(BotProxy.createProxy(new ControllerContextImpl(context)));
	}
}
