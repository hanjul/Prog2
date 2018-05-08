package de.hsa.games.fatsquirrel.command;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class MyFavouriteCommandsProcessor {

	private static final PrintStream outputStream = System.out;
	private static final BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
	
	public void process() {
		CommandScanner commandScanner = new CommandScanner(MyFavoriteCommandType.values(), inputReader, outputStream);
		while (true) {
			final Command command = commandScanner.next();
			final Object[] params = command.getParams();
			
			MyFavoriteCommandType commandType = (MyFavoriteCommandType) command.getCommandType();
			
			switch (commandType) {
			case ADDF: {
				float left = (float) params[0];
				float right = (float) params[1];
				System.out.println(left + right);
			}
				break;
			case ADDI: {
				int left = (int) params[0];
				int right = (int) params[1];
				System.out.println(left + right);
			}
				break;
			case ECHO: {
				final String echo = params[0].toString();
				int times = 0;
				try {
					times = Integer.parseInt(params[1].toString());	
				} catch (NumberFormatException e) {
					throw new ScanException(e);
				}
				for (int i = 0; i < times; i++) {
					System.out.println(echo);
				}
			}
				break;
			case EXIT:
				System.exit(0);
				break;
			case HELP:
				command.getCommandType().execute(this);
//				help();
				break;
			default:
				break;
			
			}
		}
	}
	
	public void help() {
		System.out.println("Displays a helpful help message");
	}
	
	public static void main(String[] args) {
		MyFavouriteCommandsProcessor processor = new MyFavouriteCommandsProcessor();
		processor.process();
	}
}
