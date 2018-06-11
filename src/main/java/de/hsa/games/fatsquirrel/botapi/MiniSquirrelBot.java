package de.hsa.games.fatsquirrel.botapi;

import de.hsa.games.fatsquirrel.core.EntityContext;
import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.core.XYSupport;
import de.hsa.games.fatsquirrel.entity.EntityType;
import de.hsa.games.fatsquirrel.entity.MiniSquirrel;
import de.hsa.games.fatsquirrel.util.Assert;

/**
 * A {@link MiniSquirrel} implementation that represents a bot.
 */
public class MiniSquirrelBot extends MiniSquirrel {

	private static final int VIEW_DISTANCE = 21;
	
	@SuppressWarnings("unused")
	private final BotControllerFactory botControllerFactory;
	private final BotController miniBotController;
	
	public MiniSquirrelBot(final int energy, final XY location, final BotControllerFactory botControllerFactory, final BotController miniBotController) {
		super(energy, location);
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
			return context.getEntityType(xy);
		}

		@Override
		public void move(XY direction) {
			context.tryMove(MiniSquirrelBot.this, direction);
		}

		@Override
		public void spawnMiniBot(XY direction, int energy) {
			throw new UnsupportedOperationException();
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
			final EntityType other = getEntityAt(xy);
			if (other == EntityType.MINI_SQUIRREL) {
				return getMaster().getLocation().equals(xy);
			}
			if (other == EntityType.MASTER_SQUIRREL) {
				return getMaster().getChildren().stream().anyMatch(m -> m.getLocation().equals(xy));
			}
			return false;
		}

		@Override
		public void implode(int impactRadius) {
			if (impactRadius < 2 || impactRadius > 10) {
				throw new IllegalArgumentException();
			}
			context.implode(MiniSquirrelBot.this, impactRadius);
		}

		@Override
		public XY directionOfMaster() {
			return XYSupport.clamp(getMaster().getLocation());
		}

		@Override
		public long getRemainingSteps() {
			return -1;
		}
		
	}

	@Override
	public void onNextStep(EntityContext view) {
		miniBotController.nextStep(new ControllerContextImpl(view));
	}
}