package com.wutianyi.study.performance;

import java.util.Random;

/**
 * @author hanjiewu 测试上下文切换
 */
public class SwitchTest
{

    private Object[] locks;
    private Random random = new Random();
    private int threadCount = 500;

    public static void main(String[] args)
    {
        SwitchTest test = new SwitchTest();
        test.runTest();
    }

    private void runTest()
    {
        locks = new Object[threadCount];
        for (int i = 0; i < threadCount; i++)
        {
            locks[i] = new Object();
        }
        for (int i = 0; i < threadCount; i++)
        {
            new Thread(new ATask(i)).start();
            new Thread(new BTask(i)).start();
        }
    }

    class ATask implements Runnable
    {

        private Object lockObject = null;

        public ATask(int i)
        {
            lockObject = locks[i];
        }

        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    synchronized (lockObject)
                    {
                        lockObject.wait(random.nextInt(10));
                    }
                }
                catch (Exception e)
                {
                }
            }
        }

    }

    class BTask implements Runnable
    {
        private Object lockObject = null;

        public BTask(int i)
        {
            lockObject = locks[i];
        }

        @Override
        public void run()
        {
            while (true)
            {
                synchronized (lockObject)
                {
                    lockObject.notifyAll();
                }
                try
                {
                    Thread.sleep(random.nextInt(5));
                }
                catch (Exception e)
                {
                    // TODO: handle exception
                }
            }
        }

    }
}
