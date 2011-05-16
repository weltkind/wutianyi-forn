package com.wutianyi.threadpattern.workerThread;

/**
 * Worker Thread--等到工作来了，来了就工作
 * @author hanjie.wuhj
 *
 */
public class Main {
	public static void main(String[] args) {
		Channel channel = new Channel(5);
		channel.startWorks();
		
		new ClientThread("Alice", channel).start();
		new ClientThread("Bobby", channel).start();
		new ClientThread("Chris", channel).start();
	}
}
