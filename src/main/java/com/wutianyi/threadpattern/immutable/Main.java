package com.wutianyi.threadpattern.immutable;

/**
 * immutable-想破坏它也没有办法
 * 这种模式中，有着保证实例状态不会改变的类
 * @author hanjie.wuhj
 *
 */
public class Main {
	public static void main(String[] args) {
		Person alice = new Person("Alice", "Alaska");
		new PrintPersonThread(alice).start();
		new PrintPersonThread(alice).start();
		new PrintPersonThread(alice).start();
	}
}
