package wang.raye.springboot.job;

import io.goeasy.GoEasy;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import wang.raye.springboot.server.ExamplesService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wesley on 2017-03-23.
 * ExamplesJob
 */
public class ExamplesJob2 implements Job {

	private static final Logger logger =Logger.getLogger(ExamplesJob2.class);

	@Autowired
	private ExamplesService examplesService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			examplesService.testQuartzJob("Quartz Job2 "+context.getScheduler().getSchedulerName());
		} catch (SchedulerException e) {
			logger.error("execute service error",e);
		}
//		2. 实例化GoEasy对象
		GoEasy goEasy = new GoEasy("http://localhost","BS-a3a869f98a0b441e947c971d851eb746");


//		c. 推送消息
		goEasy.publish("coin", "First message");
		System.out.println("examplesJob2:"+Thread.currentThread().getName()+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}

}
