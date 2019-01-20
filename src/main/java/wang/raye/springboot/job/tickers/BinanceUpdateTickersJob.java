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
public class BinanceUpdateTickersJob implements Job {

	private final Logger logger =Logger.getLogger(this.getClass());

	@Autowired
	private TickersServer tickersServer;

	@Value("${self.exchange.binance}")
	private String BINANCE;


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("线程 "+ BINANCE +" 更新交易对Tickers:"+Thread.currentThread().getName()+"运行开始.....");
		tickersServer.updateExchangeTickers(BINANCE);
		logger.info("线程 "+ BINANCE +" 更新交易对Tickers:"+Thread.currentThread().getName()+"运行结束.....");
	}

}
