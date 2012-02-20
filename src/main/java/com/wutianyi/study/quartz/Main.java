package com.wutianyi.study.quartz;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

public class Main
{
    public static void main(String[] args) throws SchedulerException
    {
//        SchedulerFactory scheFact = new StdSchedulerFactory();
//        Scheduler sched = scheFact.getScheduler();
//        sched.start();
//        JobDetail jobDetail = new JobDetail("myJob", sched.DEFAULT_GROUP, null);
//        SimpleTrigger trigger = new SimpleTrigger("myTrigger", sched.DEFAULT_GROUP, new Date(), null, 0, 0L);
//        sched.scheduleJob(jobDetail, trigger);
    }
}
