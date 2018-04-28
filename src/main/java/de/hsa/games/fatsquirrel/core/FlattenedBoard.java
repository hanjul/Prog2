package de.hsa.games.fatsquirrel.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hsa.games.fatsquirrel.entity.BadBeast;
import de.hsa.games.fatsquirrel.entity.BadPlant;
import de.hsa.games.fatsquirrel.entity.Entity;
import de.hsa.games.fatsquirrel.entity.EntitySet;
import de.hsa.games.fatsquirrel.entity.EntityType;
import de.hsa.games.fatsquirrel.entity.GoodBeast;
import de.hsa.games.fatsquirrel.entity.GoodPlant;
import de.hsa.games.fatsquirrel.entity.MasterSquirrel;
import de.hsa.games.fatsquirrel.entity.MiniSquirrel;
import de.hsa.games.fatsquirrel.entity.PlayerEntity;
import de.hsa.games.fatsquirrel.entity.Wall;
import de.hsa.games.fatsquirrel.util.Assert;

public class FlattenedBoard implements BoardView, EntityContext {

	private final Board board;
	private final Entity[][] cells;

	public FlattenedBoard(final Board board) {
		this.board = Assert.notNull(board, "board must not be null");
		final XY size = board.getConfig().getSize();
		cells = new Entity[size.getX()][size.getY()];
		for (final Entity e : board.getEntities()) {
			final XY location = e.getLocation();
			cells[location.getX()][location.getY()] = e;
		}
	}

	private static EntityType getType(final Entity e) {
		if (e instanceof Wall) {
			return EntityType.WALL;
		}
		if (e instanceof GoodPlant) {
			return EntityType.GOOD_PLANT;
		}
		if (e instanceof BadPlant) {
			return EntityType.BAD_PLANT;
		}
		if (e instanceof GoodBeast) {
			return EntityType.GOOD_BEAST;
		}
		if (e instanceof BadBeast) {
			return EntityType.BAD_BEAST;
		}
		if (e instanceof MiniSquirrel) {
			return EntityType.MINI_SQUIRREL;
		}
		if (e instanceof MasterSquirrel) {
			return EntityType.MASTER_SQUIRREL;
		}
		return null;
	}

	@Override
	public EntityType getEntityType(int x, int y) {
		final XY size = board.getConfig().getSize();
		if (x < 0 || x >= size.getX()) {
			throw new IndexOutOfBoundsException();
		}
		if (y < 0 || y >= size.getY()) {
			throw new IndexOutOfBoundsException();
		}
		return getType(cells[x][y]);
	}

	@Override
	public XY getSize() {
		return board.getConfig().getSize();
	}

	@Override
	public void tryMove(MiniSquirrel squirrel, XY moveDirection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tryMove(GoodBeast beast, XY moveDirection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tryMove(BadBeast beast, XY moveDirection) {
		// TODO Auto-generated method stub

	}

	private void move(final Entity e, final XY direction) {
		XY loc = e.getLocation();
		e.setLocation(loc.add(direction));
		cells[loc.getX()][loc.getY()] = null;
		loc = loc.add(direction);
		cells[loc.getX()][loc.getY()] = e;
	}

	@Override
	public void tryMove(MasterSquirrel master, XY moveDirection) {
		final Entity collide = tryCollide(master, moveDirection);
		if (collide != null) {
			if (collide instanceof Wall) {
				master.updateEnergy(collide.getEnergy());
			}
			if (collide instanceof BadPlant || collide instanceof GoodPlant) {
				killAndReplace(collide);
				move(master, moveDirection);
			}

		}
	}

	/**
	 * Attempts to move the {@code Entity} in the specified direction. If the
	 * desired location is already occupied, the method will return the
	 * {@code Entity} at that location, otherwise {@code null}.
	 * 
	 * @param e
	 *            the Entity to move
	 * @param direction
	 *            the direction in which the Entity is to be moved
	 * @return the Entity at the desired location or null
	 */
	private Entity tryCollide(final Entity e, final XY direction) {
		final XY resultLocation = e.getLocation().add(direction);
		if (cells[resultLocation.getX()][resultLocation.getY()] == null) {
			e.setLocation(e.getLocation().add(direction));
			return cells[resultLocation.getX()][resultLocation.getY()];
		}
		return null;
	}

	@Override
	public PlayerEntity nearestPlayerEntity(XY pos) {
		PlayerEntity result = null;
		double distance = Double.MAX_VALUE;
		for (final Entity e : board.getEntities()) {
			if (e instanceof PlayerEntity) {
				final XY location = e.getLocation();
				final double dist = pos.minus(location).length();
				if (dist < distance) {
					distance = dist;
					result = (PlayerEntity) e;
				}
			}
		}
		return result;
	}

	@Override
	public void kill(Entity entity) {
		Assert.notNull(entity, "entity must not be null");
		final EntitySet set = board.getEntities();
		set.remove(entity);
		final XY location = entity.getLocation();
		cells[location.getX()][location.getY()] = null;
	}

	@Override
	public void killAndReplace(Entity entity) {
		kill(entity);
		final EntityType type = getType(entity);
		final XY rand = randomPosition();
		Entity plant = null;
		if (type == EntityType.GOOD_PLANT) {
			plant = new GoodPlant(-1, rand);
		}
		if (type == EntityType.BAD_PLANT) {
			plant = new BadPlant(-1, rand);
		}
		if (plant != null) {
			board.getEntities().add(plant);
			cells[rand.getX()][rand.getY()] = plant;
		}
	}

	private XY randomPosition() {
		final List<XY> locations = getRemainingLocations();
		if (locations.isEmpty()) {
			throw new IllegalStateException("the board is full");
		}
		Collections.shuffle(locations);
		return locations.get(0);
	}

	private List<XY> getRemainingLocations() {
		final XY size = board.getConfig().getSize();
		final List<XY> result = new ArrayList<>();
		for (int x = 0; x < size.getX(); x++) {
			for (int y = 0; y < size.getY(); y++) {
				if (cells[x][y] == null) {
					result.add(new XY(x, y));
				}
			}
		}
		return result;
	}

	public Board getBoard() {
		return board;
	}

	@Override
	public EntityType getEntityType(XY pos) {
		Assert.notNull(pos, "pos must not be null");
		return getEntityType(pos.getX(), pos.getY());
	}
}
