package com.wutianyi.threadpattern.threadSpecificStorage;

public class Log {

	private static final ThreadLocal<TSLog> tsLogCollection = new ThreadLocal<TSLog>();

	public static void println(String s) {
		getTSLog().println(s);
	}

	public static void close() {
		getTSLog().close();
	}

	private static TSLog getTSLog() {
		TSLog tsLog = (TSLog) tsLogCollection.get();

		if (null == tsLog) {
			tsLog = new TSLog(Thread.currentThread().getName() + "-log.txt");
			tsLogCollection.set(tsLog);
		}

		return tsLog;
	}
}
