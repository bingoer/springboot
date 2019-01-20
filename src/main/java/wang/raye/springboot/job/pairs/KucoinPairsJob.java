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
public class KucoinPairsJob implements Job {

	private static final Logger logger =Logger.getLogger(KucoinPairsJob.class);

	@Autowired
	private PairsServer pairsServer;

	@Value("${self.exchange.kucoin}")
	private String KUCOIN;


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("线程 "+ KUCOIN +" 交易对:"+Thread.currentThread().getName()+"运行开始.....");
		pairsServer.marketPairs(KUCOIN);
		logger.info("线程 "+ KUCOIN +" 交易对:"+Thread.currentThread().getName()+"运行结束.....");
	}

}
