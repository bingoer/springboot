package wang.raye.springboot.job.change;

import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.CommonInterval;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import wang.raye.springboot.server.ChangeServer;

/**
 * Created by wesley on 2017-03-23.
 * ExamplesJob
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class OkexChange30mJob implements Job {

	private final Logger logger =Logger.getLogger(this.getClass());

	@Autowired
	private ChangeServer changeServer;

	@Value("${self.exchange.okex}")
	private String OKEX;


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("线程 "+ OKEX +" 涨跌幅提醒:"+Thread.currentThread().getName()+"运行开始.....");
		changeServer.netChange(OKEX, CommonInterval.HALF_HOURLY);
		logger.info("线程 "+ OKEX +" 涨跌幅提醒:"+Thread.currentThread().getName()+"运行结束.....");
	}

}
