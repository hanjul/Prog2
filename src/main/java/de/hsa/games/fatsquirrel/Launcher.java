package de.hsa.games.fatsquirrel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;
import de.hsa.games.fatsquirrel.botapi.MasterSquirrelBot;
import de.hsa.games.fatsquirrel.core.Board;
import de.hsa.games.fatsquirrel.core.BoardConfig;
import de.hsa.games.fatsquirrel.core.BoardCreator;
import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.entity.Entity;
import de.hsa.games.fatsquirrel.entity.EntityType;
import de.hsa.games.fatsquirrel.entity.MasterSquirrel;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

	private static final Logger logger = LoggerFactory.getLogger(Launcher.class);
	private static final List<BotControllerFactory> BOT_FACTORYS = new ArrayList<>();
	private static final Set<String> BOT_NAMES = new HashSet<>();

	private static void startGame(final Game game) {
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				game.run();
			}
		}, 0L);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Map<EntityType, Integer> m = new EnumMap<>(EntityType.class);
		m.put(EntityType.WALL, 2);
		m.put(EntityType.GOOD_BEAST, 1);
		m.put(EntityType.BAD_BEAST, 2);
		m.put(EntityType.GOOD_PLANT, 2);
		m.put(EntityType.BAD_PLANT, 2);
		m.put(EntityType.MASTER_SQUIRREL, 0);
//		BoardConfig boardConfig = new BoardConfig(m, new XY(10, 8), 5);
		BoardConfig boardConfig = new BoardConfig(m, new XY(20, 20), BOT_NAMES, 100);
		FxUI fxUI = FxUI.createInstance(boardConfig.getSize());
		Board b = new BoardCreator(0, boardConfig).generateBoard();
		final Map<Entity, String> bots = new HashMap<>();
		for (BotControllerFactory fac : BOT_FACTORYS) {
			final MasterSquirrelBot e = new MasterSquirrelBot(new XY(1, 1), fac, fac.createMasterBotController());
			b.put(e);
			bots.put(e, fac.getClass().getName());
		}
		State s = new State(b, bots);
		final Game game = new FxGame(s, fxUI);

		primaryStage.setScene(fxUI);
		primaryStage.setTitle("Diligent Squirrel");
		fxUI.getWindow().setOnCloseRequest(evt -> {
			System.exit(-1);
		});
		primaryStage.show();

		startGame(game);
	}

	private static final String PREFIX = "de.hsa.games.fatsquirrel.botimpls";

	private static void setupBots() {
		for (Package p : Package.getPackages()) {
			System.out.println(p.getName());
			if (p.getName().startsWith(PREFIX)) {
				final String packageName = p.getName().substring(0, PREFIX.length() - 1);
				final String className = packageName + "BotControllerFactory";
				try {
					final Class<?> clazz = Class.forName(p.getName() + className);
					if (!clazz.isAssignableFrom(BotControllerFactory.class)) {
						logger.warn("not bot controller factory found in package {}", p);
						break;
					}
					
					final BotControllerFactory factory = (BotControllerFactory) clazz.newInstance();
					BOT_FACTORYS.add(factory);
					BOT_NAMES.add(packageName);
					
					System.out.println(packageName);
					
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
					logger.warn("unable to get controller factory class", e);
				}
			}
		}
	}

	public static void main(String[] args) {
		args = new String[] { "-bots" };
		if (Arrays.stream(args).anyMatch(s -> "-bots".equals(s))) {
			setupBots();
		}
		launch(args);
	}
}
