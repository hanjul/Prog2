package de.hsa.games.fatsquirrel.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

public class CommandScanner {

	private final CommandTypeInfo[] commandTypeInfos;
	private final BufferedReader inputReader;
	private final PrintStream outputStream;
	
	public CommandScanner(CommandTypeInfo[] commandTypes, BufferedReader inputReader, PrintStream outputStream) {
		this.commandTypeInfos = commandTypes;
		this.inputReader = inputReader;
		this.outputStream = outputStream;
	}
	
	public Command next() {
		String line;
		try {
			while((line = inputReader.readLine()).isEmpty());
		} catch (IOException e) {
			throw new ScanException(e);
		}
		String[] split = line.split(" ");
		final String name = split[0];
		CommandTypeInfo info = null;
		for (CommandTypeInfo t : commandTypeInfos) {
			if (t.getName().equals(name)) {
				info = t;
			}
		}
		if (info == null) {
			throw new ScanException();
		}
		int remainingParams = split.length - 1;
		if (info.getParamTypes().length != remainingParams) {
			throw new ScanException();
		}
		
//		final Object[] params = parseParams(Arrays.copyOf(split, remainingParams));
		final String[] rawParams = new String[remainingParams];
		for (int i = 1; i < split.length; i++) {
			rawParams[i - 1] = split[i];
		}
		final Object[] params = parseParams(rawParams);
		final Class<?>[] paramClasses = getClasses(params);
		if (!Arrays.equals(info.getParamTypes(), paramClasses)) {
			throw new ScanException("incompatible class types");
		}
		return new Command(info, params);
	}
	
	private static Class<?>[] getClasses(final Object[] objs) {
		final Class<?>[] result = new Class<?>[objs.length];
		for (int i = 0; i < objs.length; i++) {
			if (objs[i].getClass() == Integer.class) {
				result[i] = int.class;
			} else if (objs[i].getClass() == Float.class) {
				result[i] = float.class;
			} else {
				result[i] = objs[i].getClass();
			}
		}
		return result;
	}
	
	private static Object[] parseParams(final String[] rawParams) {
		final Object[] result = new Object[rawParams.length];
		for (int i = 0; i < rawParams.length; i++) {
			String param = rawParams[i];
			try {
				int parsedInt = Integer.parseInt(param);
				result[i] = parsedInt;
			} catch (NumberFormatException e1) {
				try {
					float parsedFloat = Float.parseFloat(param);
					result[i] = parsedFloat;
				} catch (NumberFormatException e2) {
					result[i] = param;
				}
			}
		}
		return result;
	}
}
