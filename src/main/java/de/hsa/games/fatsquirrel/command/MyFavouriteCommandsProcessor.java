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
				float left = 0;
				float right = 0;
				try {
					left = Float.parseFloat(params[0].toString());
					left = Float.parseFloat(params[1].toString());
				} catch (NumberFormatException e) {
					throw new ScanException(e);
				}
				System.out.println(left + right);
			}
				break;
			case ADDI: {
				int left = 0;
				int right = 0;
				try {
					left = Integer.parseInt(params[0].toString());
					left = Integer.parseInt(params[1].toString());
				} catch (NumberFormatException e) {
					throw new ScanException(e);
				}
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
				help();
				break;
			default:
				break;
			
			}
		}
	}
	
	private void help() {
		System.out.println("Displays a helpful help message");
	}
	
	public static void main(String[] args) {
		
	}
}
