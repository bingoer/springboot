package wang.raye.springboot.server.impl;

import de.elbatya.cryptocoins.bittrexclient.BlockccClient;
import de.elbatya.cryptocoins.bittrexclient.BlockccInfoClient;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.blockcc.CoinInfo;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.blockcc.ExchangeRate;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.blockcc.ExchangeRateEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import wang.raye.springboot.server.InfoServer;

/**
 * 用户相关数据库操作实现类
 * @author Raye
 * @since 2016年10月11日19:29:02
 */
@Repository
public class InfoServerImpl implements InfoServer {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public double getUsdRate(){
		BlockccClient blockccClient = new BlockccClient();
		// 调用api获取汇率
		ExchangeRate exchangeRate = blockccClient.getPublicApi().getExchangeRate().unwrap();
		double usdRate = 6.3;
		if (null != exchangeRate) {
			ExchangeRateEntry exchangeRateEntry = exchangeRate.getRates();
			if (null != exchangeRateEntry) {
				usdRate = exchangeRateEntry.getCNY();
			}
		}
		return  usdRate;
	}


	@Override
	public CoinInfo getCoinInfo(BlockccInfoClient blockccInfoClient, String coinId) {
		// 通过api获取币种信息
		CoinInfo coinInfo = new CoinInfo();
		try{
			coinInfo = blockccInfoClient.getInfoApi().getCoinInfo(coinId).unwrap();
		}
		catch (Exception e){
			log.error(e.getMessage(),e);
		}
		return  coinInfo;
	}

}
