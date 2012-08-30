package com.wutianyi.study.redis.services;

public interface DataStoreService
{

    /**
     * 
     * @param key
     * @param value
     * @return 0表示不成功，1表示成功
     */
    public boolean put(String key, int value);

    public void destroy();
}
