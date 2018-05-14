package de.hsa.games.fatsquirrel;

import java.util.EnumMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hsa.games.fatsquirrel.core.Board;
import de.hsa.games.fatsquirrel.core.BoardConfig;
import de.hsa.games.fatsquirrel.core.BoardCreator;
import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.entity.EntityType;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

	private static void startGame(final Game game) {
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				game.run();
			}
		}, 1000L);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Map<EntityType, Integer> m = new EnumMap<>(EntityType.class);
		m.put(EntityType.WALL, 2);
		m.put(EntityType.GOOD_BEAST, 1);
		m.put(EntityType.BAD_BEAST, 2);
		m.put(EntityType.GOOD_PLANT, 2);
		m.put(EntityType.BAD_PLANT, 2);
		m.put(EntityType.MASTER_SQUIRREL, 1);
		BoardConfig boardConfig = new BoardConfig(m, new XY(10, 8), 5);
		FxUI fxUI = FxUI.createInstance(boardConfig.getSize());
		Board b = new BoardCreator(0, boardConfig).generateBoard();
		State s = new State(b);
		final Game game = new FxGame(s, fxUI);

		primaryStage.setScene(fxUI);
		primaryStage.setTitle("Diligent Squirrel");
		fxUI.getWindow().setOnCloseRequest(evt -> {
			System.exit(-1);
		});
		primaryStage.show();

		startGame(game);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
