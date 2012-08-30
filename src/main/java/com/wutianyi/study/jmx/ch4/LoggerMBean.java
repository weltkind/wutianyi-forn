package com.wutianyi.study.jmx.ch4;

public interface LoggerMBean
{
    public void setLogLevel(int level);

    public int getLogLevel();

    public String retrieveLog(int linesback);

    public void writeLog(String message, int type);
}
