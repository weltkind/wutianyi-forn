package com.wutianyi.cesu.dataobject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class StatisticDO
{
    private String ip;
    private int flag1;
    private int flag2;
    private int flag3;

    private Map<Integer, Integer> times;

    public StatisticDO()
    {
        times = new HashMap<Integer, Integer>();
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public int getFlag1()
    {
        return flag1;
    }

    public void setFlag1(int flag1)
    {
        this.flag1 = flag1;
    }

    public int getFlag2()
    {
        return flag2;
    }

    public void setFlag2(int flag2)
    {
        this.flag2 = flag2;
    }

    public int getFlag3()
    {
        return flag3;
    }

    public void setFlag3(int flag3)
    {
        this.flag3 = flag3;
    }

    public Map<Integer, Integer> getTimes()
    {
        return times;
    }

    public void setTimes(Map<Integer, Integer> times)
    {
        this.times = times;
    }
    
    public void addTime(Integer pageId, Integer time)
    {
        times.put(pageId, time);
    }
    
    public static void main(String[] args) throws ParseException
    {
        String st = "2011-11-09 00:00:01.374    INFO    61.145.187.42 output=json&flag1=1&flag2=1&flag3=1&1=78";
        String[] ss = st.split("INFO");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for(String s : ss)
        {
            System.out.println(s);
        }
        System.out.println(dateFormat.parse(ss[0]));
    }
}
