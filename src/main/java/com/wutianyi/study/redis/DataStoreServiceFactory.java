package com.wutianyi.study.redis;

import com.wutianyi.study.redis.services.DataStoreService;
import com.wutianyi.study.redis.services.impl.JedisDataStoreService;
import com.wutianyi.utils.Pair;

public class DataStoreServiceFactory
{

    private static DataStoreService dataStoreService;
    static
    {
        Pair<String, Integer> master = new Pair<String, Integer>("localhost", 6379);
        Pair<String, Integer>[] slaves = new Pair[]
        { new Pair<String, Integer>("localhost", 6380) };
        dataStoreService = new JedisDataStoreService(master, slaves);
    }

    public static DataStoreService getDataStoreService()
    {
        return dataStoreService;
    }

    public static void main(String[] args) throws InterruptedException
    {
        getDataStoreService().put("t2", 1);
        getDataStoreService().put("t3", 1);
        System.out.println("sleep");
        Thread.sleep(10000);
        getDataStoreService().put("t4", 1);
        getDataStoreService().put("t5", 1);

        getDataStoreService().destroy();
    }
}
