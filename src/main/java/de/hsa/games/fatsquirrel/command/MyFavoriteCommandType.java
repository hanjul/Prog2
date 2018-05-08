package de.hsa.games.fatsquirrel.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public enum MyFavoriteCommandType implements CommandTypeInfo {

	HELP("help", "list all commands"),
	EXIT("exit", "exit program"),
	ADDI("addi", "<param1> <param2> simple integer add ", int.class, int.class),
	ADDF("addf", "<param1> <param2> simple float add ", float.class, float.class),
	ECHO("echo", "<param1> <param2> echos param1 string param2 times", String.class, int.class);

	private final String name;
	private final String help;
	private final Class<?>[] params;
	
	private MyFavoriteCommandType(final String name, final String help, Class<?>... params) {
		this.name = name;
		this.help = help;
		this.params = params;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getHelpText() {
		return help;
	}

	@Override
	public Class<?>[] getParamTypes() {
		return params;
	}
	
	@Override
	public void execute(Object target, Object... args) {
		try {
			final Method targetMethod = target.getClass().getMethod(name, params);
			targetMethod.setAccessible(true);
			targetMethod.invoke(target, args);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
