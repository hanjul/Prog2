package de.hsa.games.fatsquirrel;

import de.hsa.games.fatsquirrel.command.Command;
import de.hsa.games.fatsquirrel.console.GameCommandType;
import de.hsa.games.fatsquirrel.core.BoardView;
import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.entity.EntityType;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class FxUI extends Scene implements UI {

	private static final int CELL_SIZE = 32;

	private static Command currentCommand = new Command(GameCommandType.NONE);
	
	private final Canvas boardCanvas;
	private final Label msgLabel;

	public FxUI(Parent parent, Canvas boardCanvas, Label msgLabel) {
		super(parent);
		this.boardCanvas = boardCanvas;
		this.msgLabel = msgLabel;
	}

	public static FxUI createInstance(XY boardSize) {
		Canvas boardCanvas = new Canvas(boardSize.x * CELL_SIZE, boardSize.y * CELL_SIZE);
		Label statusLabel = new Label();
		VBox top = new VBox();
		top.getChildren().add(boardCanvas);
		top.getChildren().add(statusLabel);
		statusLabel.setText("Hallo Welt");
		final FxUI fxUI = new FxUI(top, boardCanvas, statusLabel);
		fxUI.setOnKeyPressed(keyEvent -> {
			switch(keyEvent.getCode()) {
			case W: currentCommand = new Command(GameCommandType.UP);
			break;
			case A: currentCommand = new Command(GameCommandType.LEFT);
			break;
			case D: currentCommand = new Command(GameCommandType.RIGHT);
			break;
			case S: currentCommand = new Command(GameCommandType.DOWN);
			break;
			default: currentCommand = new Command(GameCommandType.NONE);
			}
		});
		return fxUI;
	}

	@Override
	public void render(final BoardView view) {
		Platform.runLater(() -> repaintBoardCanvas(view));
	}

	private void repaintBoardCanvas(BoardView view) {
		GraphicsContext gc = boardCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, boardCanvas.getWidth(), boardCanvas.getHeight());
//		XY viewSize = view.getSize();

		// dummy for rendering a board snapshot, TODO: change it!
//        gc.fillText("Where are the beasts?", 500, 500);
//        gc.setFill(Color.RED);
//        gc.fillOval(150, 150, 50, 50);

		for (int x = 0; x < view.getSize().y; x++) {
			for (int y = 0; y < view.getSize().x; y++) {
				final EntityType e = view.getEntityType(y, x);
				render(gc, e, y, x);
			}
		}
	}

	private static void render(GraphicsContext gc, EntityType type, final int x, final int y) {
		Color color = Color.WHITE;
		boolean round = false;
		if (type != null) {
			switch (type) {
			case BAD_BEAST:
				color = Color.RED;
				round = true;
				break;
			case BAD_PLANT:
				color = Color.RED;
				round = false;
				break;
			case GOOD_BEAST:
				color = Color.GREEN;
				break;
			case GOOD_PLANT:
				color = Color.GREEN;
				round = true;
				break;
			case MASTER_SQUIRREL:
				color = Color.BLUE;
				break;
			case MINI_SQUIRREL:
				color = Color.BLUE;
				round = true;
				break;
			case WALL:
				color = Color.ORANGE;
				break;
			default:
				break;
			}
		}
		gc.setFill(color);
		if (round) {
			gc.fillOval(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
		} else {
			gc.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
		}
	}

	@Override
	public void message(final String msg) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				msgLabel.setText(msg);
			}
		});
	}

	@Override
	public Command getCommand() {
		final Command lastCommand = currentCommand;
		currentCommand = new Command(GameCommandType.NONE);
		return lastCommand;
	}
}
