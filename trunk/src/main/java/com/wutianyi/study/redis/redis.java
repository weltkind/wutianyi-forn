package com.wutianyi.study.redis;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class redis
{
    private static JedisPool pool;

    public static void main(String[] args) throws InterruptedException
    {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMinIdle(3);
        pool = new JedisPool(config, "localhost",6379);
        Jedis jedis = pool.getResource();
        jedis.ping();
        try
        {
            System.out.println(jedis.setnx("n", "bar"));
            String foobar = jedis.get("foo");
            jedis.zadd("sose", 0, "car");
            jedis.zadd("sose", 0, "bike");
            Set<String> sose = jedis.zrange("sose", 0, -1);
        }
        finally
        {
            pool.returnResource(jedis);
        }
        pool.destroy();
    }
}
