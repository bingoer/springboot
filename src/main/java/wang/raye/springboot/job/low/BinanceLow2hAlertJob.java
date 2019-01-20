package wang.raye.springboot.job.low;

import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.CommonInterval;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import wang.raye.springboot.server.CrossServer;

/**
 * Created by wesley on 2017-03-23.
 * ExamplesJob
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class BinanceLow2hAlertJob implements Job {

	private final Logger logger =Logger.getLogger(this.getClass());

	@Autowired
	private CrossServer crossServer;

	@Value("${self.exchange.binance}")
	private String BINANCE;


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("线程 "+ BINANCE +" 2h指标高低点提醒:"+Thread.currentThread().getName()+"运行开始.....");
		crossServer.lowMonitor(BINANCE, CommonInterval.TWO_HOURLY);
		logger.info("线程 "+ BINANCE +" 2h指标高低点提醒:"+Thread.currentThread().getName()+"运行结束.....");
	}

}
