package com.wutianyi.threadpattern.readWriterLock;

/**Read-Write Lock ---大家想看就看吧，不过看的时候不能写喔
 * @author hanjie.wuhj
 *
 */
public class Main {
	public static void main(String[] args) {
		Data data = new Data(10);
		
		new ReaderThread(data).start();
		new ReaderThread(data).start();
		new ReaderThread(data).start();
		new ReaderThread(data).start();
		new ReaderThread(data).start();
		new ReaderThread(data).start();
		new WriterThread(data, "ABCDEFGHIJKLMNOPQRSTUVWXYZ").start();
		new WriterThread(data, "abcdefghijklmnopqrstuvwxyz").start();
	}
}
