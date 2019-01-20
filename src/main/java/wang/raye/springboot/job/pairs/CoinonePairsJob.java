package wang.raye.springboot.job.pairs;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import wang.raye.springboot.server.PairsServer;

/**
 * Created by wesley on 2017-03-23.
 * ExamplesJob
 */
public class CoinonePairsJob implements Job {

	private static final Logger logger =Logger.getLogger(CoinonePairsJob.class);

	@Autowired
	private PairsServer pairsServer;

	@Value("${self.exchange.coinone}")
	private String COINONE;


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("线程 "+ COINONE +" 交易对:"+Thread.currentThread().getName()+"运行开始.....");
		pairsServer.marketPairs(COINONE);
		logger.info("线程 "+ COINONE +" 交易对:"+Thread.currentThread().getName()+"运行结束.....");
	}

}
