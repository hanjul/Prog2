package de.hsa.games.fatsquirrel.botapi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BotProxy {

	public static ControllerContext createProxy(final ControllerContext context) {
		InvocationHandler handler = new ProxyLogger(context);
		ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
		Class<?>[] proxyInterfaces = { ControllerContext.class };

		return (ControllerContext) Proxy.newProxyInstance(sysClassLoader, proxyInterfaces, handler);
	}

	private static final class ProxyLogger implements InvocationHandler {

		private static final Logger logger = LoggerFactory.getLogger(ProxyLogger.class.getName());

		private ControllerContext context;
		
		public ProxyLogger(final ControllerContext context) {
			this.context = context;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			final String[] argsAsString = new String[args.length];
			for (int i = 0; i < args.length; i++) {
				argsAsString[i] = args[i].toString();
			}
			logger.debug("{} called the {}() method with the following arguments {}", context, method,
					String.join(", ", argsAsString));
			method.invoke(context, args);
			return null;
		}
	}
}
