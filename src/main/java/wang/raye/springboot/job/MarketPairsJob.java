package wang.raye.springboot.job;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import wang.raye.springboot.server.PairsServer;

/**
 * Created by wesley on 2017-03-23.
 * ExamplesJob
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class MarketPairsJob implements Job {

	private static final Logger logger =Logger.getLogger(MarketPairsJob.class);

	@Autowired
	private PairsServer pairsServer;


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("线程 交易对:"+Thread.currentThread().getName()+"运行开始.....");
		pairsServer.marketPairs();
		logger.info("线程 交易对:"+Thread.currentThread().getName()+"运行结束.....");
	}

}
