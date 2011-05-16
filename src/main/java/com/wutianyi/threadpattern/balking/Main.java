package com.wutianyi.threadpattern.balking;

/**
 * Balking--不需要的话，就算了吧
 * 当不满足警戒条件的时候，就直接退出
 * @author hanjie.wuhj
 *
 */
public class Main {

	public static void main(String[] args) {
		Data data = new Data("data.txt", "(empty)");
		new ChangerThread("ChangerThread", data).start();
		new SaverThread("SaverThread", data).start();
	}
}
