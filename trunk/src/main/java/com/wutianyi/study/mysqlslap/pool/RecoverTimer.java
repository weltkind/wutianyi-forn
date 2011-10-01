package com.wutianyi.study.mysqlslap.pool;

import java.util.Timer;
import java.util.TimerTask;

public class RecoverTimer {

	private static Timer timer;

	private RecoverTimer() {

	}

	public static void startTimerTask(TimerTask task) {
		if (null == timer) {
			timer = new Timer(true);
		}
		timer.schedule(task, 0, 1000);
	}

	public static void cancelTimerTask(TimerTask task) {
		if (null != task) {
			task.cancel();
		}

		if (null != timer) {
			timer.cancel();
		}
	}

}
