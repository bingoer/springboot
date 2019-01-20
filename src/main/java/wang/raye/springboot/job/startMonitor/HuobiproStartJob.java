package wang.raye.springboot.job.startMonitor;

import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.CommonInterval;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import wang.raye.springboot.server.StartMonitorServer;

/**
 * Created by wesley on 2017-03-23.
 * ExamplesJob
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class HuobiproStartJob implements Job {

	private final Logger logger =Logger.getLogger(this.getClass());

	@Autowired
	private StartMonitorServer startMonitorServer;

	@Value("${self.exchange.huobipro}")
	private String HUOBIPRO;


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("线程 "+ HUOBIPRO +" 开启上涨提醒:"+Thread.currentThread().getName()+"运行开始.....");
		startMonitorServer.startPoint(HUOBIPRO, CommonInterval.FIVE_MINUTES);
		logger.info("线程 "+ HUOBIPRO +" 开启上涨提醒:"+Thread.currentThread().getName()+"运行结束.....");
	}

}
