package de.hsa.games.fatsquirrel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;
import de.hsa.games.fatsquirrel.botapi.MasterSquirrelBot;
import de.hsa.games.fatsquirrel.core.Board;
import de.hsa.games.fatsquirrel.core.BoardConfig;
import de.hsa.games.fatsquirrel.core.BoardCreator;
import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.entity.Entity;
import de.hsa.games.fatsquirrel.entity.EntityType;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

	private static final Logger logger = LoggerFactory.getLogger(Launcher.class);
	private static final Map<String, BotControllerFactory> BOT_FACTORYS = new HashMap<>();

	private static final BoardConfig CONFIG = loadConfig();

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
		FxUI fxUI = FxUI.createInstance(CONFIG.getSize());
		Board b = new BoardCreator(0, CONFIG).generateBoard();
		final Map<Entity, String> bots = new HashMap<>();
		int i = 1;
		for (Map.Entry<String, BotControllerFactory> fac : BOT_FACTORYS.entrySet()) {
			final MasterSquirrelBot e = new MasterSquirrelBot(new XY(i++, i), fac.getValue(),
					fac.getValue().createMasterBotController());
			b.put(e);
			bots.put(e, fac.getKey());
		}
		State s = new State(b);
		final Game game = new FxGame(s, fxUI, bots);
		game.setSteps(CONFIG.getSteps());

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
		for (final String bot : CONFIG.getBotNames()) {
			final String botClassName = bot.substring(0, 1).toUpperCase() + bot.substring(1);
			try {
				final Class<?> clazz = Class.forName(PREFIX + "." + bot + "." + botClassName + "BotControllerFactory");
				if (!BotControllerFactory.class.isAssignableFrom(clazz)) {
					logger.warn("not bot controller factory found in package {}", bot);
					break;
				}

				final BotControllerFactory factory = (BotControllerFactory) clazz.newInstance();
				BOT_FACTORYS.put(bot, factory);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				logger.warn("unable to get controller factory class", e);
			}
		}
	}

	private static BoardConfig loadConfigFromFile() {
		ObjectMapper mapper = new ObjectMapper();
		ClassLoader loader = Launcher.class.getClassLoader();
		final InputStream file = loader.getResourceAsStream("boardconfig.json");
		try {
			final BoardConfig config = mapper.readValue(file, BoardConfig.class);
			return config;
		} catch (IOException e) {
			logger.warn("unable to load board config", e);
			return null;
		}
	}

	private static BoardConfig loadConfig() {
		final BoardConfig fileConfig = loadConfigFromFile();
		if (fileConfig != null) {
			return fileConfig;
		}
		Map<EntityType, Integer> m = new EnumMap<>(EntityType.class);
		m.put(EntityType.WALL, 2);
		m.put(EntityType.GOOD_BEAST, 1);
		m.put(EntityType.BAD_BEAST, 2);
		m.put(EntityType.GOOD_PLANT, 2);
		m.put(EntityType.BAD_PLANT, 2);

		final Set<String> botNames = new HashSet<>();
		botNames.add("team27");
		botNames.add("test");

		BoardConfig boardConfig = new BoardConfig(botNames, m, new XY(20, 20), 10);
		return boardConfig;
	}

	public static void main(String[] args) {
		args = new String[] { "-bots" };
		if (Arrays.stream(args).anyMatch(s -> "-bots".equals(s))) {
			setupBots();
		}
		launch(args);
	}
}
