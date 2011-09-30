package com.wutianyi.anagram;

import java.util.concurrent.Callable;

import com.wutianyi.utils.CountTime;

public class TimeAnalysis
{
    public static void main(String[] args)
    {
        CountTime createTask = new CountTime(new Runnable()
        {
            
            @Override
            public void run()
            {
               for(int i = 0; i < 100000; i ++) {
                   Callable<String> c = new Callable<String>()
                           {
                               
                               @Override
                               public String call() throws Exception
                               {
                                   // TODO Auto-generated method stub
                                   return null;
                               }
                           };
               }
            }
        }, 100);
    }
}
