package wang.raye.springboot.job.tickers;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import wang.raye.springboot.server.TickersServer;

/**
 * Created by wesley on 2017-03-23.
 * ExamplesJob
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class IdexUpdateTickersJob implements Job {

	private final Logger logger =Logger.getLogger(this.getClass());

	@Autowired
	private TickersServer tickersServer;

	@Value("${self.exchange.idex}")
	private String IDEX;


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("线程 "+ IDEX +" 更新交易对Tickers:"+Thread.currentThread().getName()+"运行开始.....");
		tickersServer.updateExchangeTickers(IDEX);
		logger.info("线程 "+ IDEX +" 更新交易对Tickers:"+Thread.currentThread().getName()+"运行结束.....");
	}

}
