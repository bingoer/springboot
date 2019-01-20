package wang.raye.springboot.job.cross;

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
public class HadaxCrossJob implements Job {

	private final Logger logger =Logger.getLogger(this.getClass());

	@Autowired
	private CrossServer crossServer;

	@Value("${self.exchange.hadax}")
	private String HADAX;


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("线程 "+ HADAX +" 2h指标数据保存:"+Thread.currentThread().getName()+"运行开始.....");
		crossServer.saveCross(HADAX, CommonInterval.TWO_HOURLY);
		logger.info("线程 "+ HADAX +" 2h指标数据保存:"+Thread.currentThread().getName()+"运行结束.....");
	}

}
