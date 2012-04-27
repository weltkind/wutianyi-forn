package com.wutianyi.study.performance;

import java.util.ArrayList;

/**
 * @author hanjiewu 查看cpu高消耗的情况
 */
public class TestCPU
{

    public static void main(String[] args)
    {
        TestCPU cpu = new TestCPU();
        cpu.runTest();
    }

    private void runTest()
    {
        int count = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < count; i++)
        {
            new Thread(new ConsumeCPUTask()).start();
        }
        for (int i = 0; i < 200; i++)
        {
            new Thread(new NotConsumeCPUTask()).start();
        }
    }

    class ConsumeCPUTask implements Runnable
    {

        @Override
        public void run()
        {
            String str = "vnaogqwjpqjf0puascnasopfqwcas'kf'asfmnpoqwhfaofqc'sacasfja[pnvcpherpoughbnaoshja'ps ma' asj fasp mapsf asvas v jasvpoja spvmapsodgianva s";
            float i = 0.002f;
            float j = 232.13243f;

            while (true)
            {
                j = i * j;
                str.indexOf("'");
                ArrayList<String> list = new ArrayList<String>();
                for (int k = 0; k < 1000; k++)
                {
                    list.add(str + String.valueOf(k));
                }
                list.contains("iii");
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    class NotConsumeCPUTask implements Runnable
    {

        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    Thread.sleep(10000000);
                }
                catch (InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }
}
