package com.wutianyi.study.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ExampleStateFulJob implements StatefulJob{

	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println(context.getJobDetail().getName());
		System.out.println(context.getJobDetail().getGroup());
		
		System.out.println("I am going to sleep!");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("I am up!");
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("com/wutianyi/study/quartz/quartz-state-context.xml");
		
		Scheduler scheduler = (Scheduler) context.getBean("schedule");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(!scheduler.isInStandbyMode()) {
				System.out.println("The schedule is running I ma going to stop!");
				scheduler.standby();
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			if(scheduler.isInStandbyMode()) {
				System.out.println("The schedule is stoping I am make it running");
				scheduler.start();
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
