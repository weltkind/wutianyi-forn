package com.wutianyi.study.mysqlslap;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public class testWeakReference {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws InterruptedException {
		ReferenceQueue<myWeakObject> queues = new ReferenceQueue<myWeakObject>();
		System.out
				.println("=======================�����ö��������ռ����================");

		myWeakObject mwo = new myWeakObject("myweakobject1");
		A a = new A("test");
		mwo.setA(a);
		a = null;
		WeakReference wr = new WeakReference(mwo, queues);

		mwo = null;

		((myWeakObject) wr.get()).show();

		System.out.println("��һ�������ռ�������");
		
		Thread thread = new Thread(new myRunnable(queues));
		thread.start();
		
		System.gc();
		
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (wr.get() != null) {
			((myWeakObject) wr.get()).show();
		}

		System.out
				.println("==========================������MAP=========================");
		WeakHashMap whm = new WeakHashMap();
		myWeakObject mwo2 = new myWeakObject("myweakobject2");
		whm.put(mwo2, "XXXXX");
		mwo2 = null;
		((myWeakObject) whm.keySet().iterator().next()).show();
		System.out.println("�ڶ���������գ�����");
		System.gc();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		((myWeakObject) whm.keySet().iterator().next()).show();

	}

}

class myWeakObject {

	String mwname;
	A a;

	public myWeakObject(String mwname) {
		super();
		this.mwname = mwname;
	}

	public void finalize() {
		System.out.println(mwname + "�������������ռ����������ռ�������");
	}

	public void show() {
		System.out.println(mwname + "���󻹿��Ա����ã�����");
	}

	public void setA(A a) {
		this.a = a;
	}
}

class A {
	String name;

	public A(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void println() {
		System.out.println(name);
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println("a Ҳ����������ˣ�");
	}

}

class myRunnable implements Runnable {
	
	ReferenceQueue<myWeakObject> queue;
	
	public myRunnable(ReferenceQueue<myWeakObject> _queue) {
		this.queue = _queue;
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			WeakReference<myWeakObject>reUsed = (WeakReference<myWeakObject>) queue.remove();
			((myWeakObject) reUsed.get()).show();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}