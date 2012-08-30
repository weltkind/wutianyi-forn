package com.wutianyi.study.redis.services.impl;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.wutianyi.study.redis.services.DataStoreService;
import com.wutianyi.utils.Pair;

public class JedisDataStoreService implements DataStoreService
{

    private JedisPool pool;

    private final int DEFAULT_PORT = 6379;

    /**
     * 主
     */
    private Pair<String, Integer> master;
    /**
     * 备用
     */
    private Pair<String, Integer>[] slaves;

    JedisPoolConfig config;

    private boolean isMaster;

    Thread thread;

    private boolean running = true;

    @SuppressWarnings("unchecked")
    public JedisDataStoreService(String master, String[] slaves)
    {
        if (StringUtils.isBlank(master))
        {
            throw new IllegalArgumentException("master must not null!");
        }
        this.master = new Pair<String, Integer>(master, DEFAULT_PORT);
        if (null != slaves && slaves.length > 0)
        {
            this.slaves = new Pair[slaves.length];
            for (int i = 0; i < slaves.length; i++)
            {
                this.slaves[i] = new Pair<String, Integer>(slaves[i], DEFAULT_PORT);
            }
        }
        initPool();
    }

    public JedisDataStoreService(Pair<String, Integer> master, Pair<String, Integer>[] slaves)
    {
        if (null == master)
        {
            throw new IllegalArgumentException("master must not null!");
        }
        this.master = master;
        if (null != slaves)
        {
            this.slaves = slaves;
        }
        initPool();
    }

    public JedisDataStoreService(String master)
    {
        this(master, null);
    }

    private void initPool()
    {
        config = new JedisPoolConfig();
        config.setMinIdle(config.getMaxActive());
        pool = new JedisPool(config, master.getFirst(), master.getSecond());
        isMaster = true;
        thread = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                while (running)
                {
                    Jedis jedis = null;
                    try
                    {
                        jedis = pool.getResource();
                        // 说明断了
                        if (!(jedis.isConnected() && jedis.ping().equals("PONG")))
                        {
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println("master dead!");
                        try
                        {
                            try
                            {
                                pool.destroy();
                            }
                            catch (Exception e2)
                            {
                                e.printStackTrace();
                            }
                            System.out.println("reinit pool!");
                            jedis = null;
                            if (null != slaves && isMaster)
                            {
                                System.out.println("init slave!");
                                Pair<String, Integer> s = slaves[(int) (Math.random() * slaves.length)];
                                pool = new JedisPool(config, s.getFirst(), s.getSecond());
                                isMaster = false;
                            }
                            else
                            {
                                System.out.println("reinit master!");
                                pool = new JedisPool(config, master.getFirst(), master.getSecond());
                                isMaster = true;
                            }
                        }
                        catch (Exception e1)
                        {
                            e1.printStackTrace();
                        }
                    }
                    finally
                    {
                        if (null != jedis)
                        {
                            pool.returnResource(jedis);
                        }
                    }

                    try
                    {
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public boolean put(String key, int value)
    {
        Jedis jedis = pool.getResource();

        try
        {
            jedis.set(key, String.valueOf(value));
            return true;
        }
        finally
        {
            pool.returnResource(jedis);
        }
    }

    @Override
    public void destroy()
    {
        running = false;
        if (null != thread && thread.isAlive())
        {
            thread.interrupt();
        }
        try
        {
            thread.join();
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        pool.destroy();
    }

}
