package wang.raye.springboot.job;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import wang.raye.springboot.server.NoticeServer;
import wang.raye.springboot.server.PairsServer;

/**
 * Created by wesley on 2017-03-23.
 * ExamplesJob
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class NoticeJob implements Job {

	private static final Logger logger =Logger.getLogger(NoticeJob.class);

	@Autowired
	private NoticeServer noticeServer;


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("线程 公告信息提醒:"+Thread.currentThread().getName()+"运行开始.....");
		noticeServer.getNoticeInfo();
		logger.info("线程 公告信息提醒:"+Thread.currentThread().getName()+"运行结束.....");
	}

}
