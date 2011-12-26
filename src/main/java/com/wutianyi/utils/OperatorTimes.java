package com.wutianyi.utils;

import java.util.ArrayDeque;
import java.util.Deque;

public class OperatorTimes {
	
	private Deque<Long> deque;
	
	public OperatorTimes()
	{
		deque = new ArrayDeque<Long>();
	}
	
	public void start()
	{
		long start = System.currentTimeMillis();
		deque.add(start);
	}
	
	public void end()
	{
		long end = System.currentTimeMillis();
		long start = deque.pop();
		System.out.println("Use time: " + (end - start));
	}
}
