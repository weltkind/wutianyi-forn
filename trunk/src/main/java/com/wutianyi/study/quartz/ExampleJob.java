package com.wutianyi.study.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ExampleJob extends QuartzJobBean{
	
	private int timeout;
	
	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		System.out.println(context.getJobDetail().getName());
		System.out.println(context.getJobDetail().getGroup());
		System.out.println(timeout);
		
		Trigger t = context.getTrigger();
		System.out.println(context.getScheduler());
		
		System.out.println(t.getClass());
	}
	
	public void doInt() {
		
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("com/wutianyi/study/quartz/quartz-context.xml");
		Scheduler scheduler = (Scheduler) context.getBean("schedule");
		
		try {
			scheduler.triggerJob("example2",Scheduler.DEFAULT_GROUP);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
