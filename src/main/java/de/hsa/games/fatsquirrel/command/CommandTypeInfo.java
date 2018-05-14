package de.hsa.games.fatsquirrel.command;

public interface CommandTypeInfo {

	String getName();
	String getHelpText();
	Class<?>[] getParamTypes();
}
