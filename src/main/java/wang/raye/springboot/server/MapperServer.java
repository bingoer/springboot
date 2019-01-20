package wang.raye.springboot.server;

import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.CommonInterval;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.blockcc.ExchangeTickersEntry;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.blockcc.NewPairsList;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.blockcc.NewPairsListEntry;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.idex.Currencies;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.idex.Market;
import wang.raye.springboot.model.*;
import wang.raye.springboot.utils.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 涨跌幅监测提醒
 * @author Raye
 * @since 2016年9月21日20:57:39
 */
public interface MapperServer {

	public List<AlertExchange> getAlertExchange(String marketName);

	public List<AlertSetting> getAlertSettingByPeriod(String period);

	public List<AlertSetting> getAlertSettingByType(String type);

	public List<AlertSetting> getAlertSettingByCond(String period, String type);

	public List<AlertUser> getAlertUsers();

	public List<AlertUser> getAlertWorkWxUsers();

	/**
	 * 调用db获取限制条件成交量
	 * @return 限制条件成交量
	 */
	public double getLimitVolume();

	public List<Tickers> getTickers(String marketName, String period, List<AlertExchange> alertExchangeList, double limitVolume);

	public List<MacdCross> getMacdCrossAll(String exchange, String period);

	public List<MacdCross> getMacdCrossByType(String exchange, String symbol, String type, String period);

	public List<Analysis> searchAnalysis(String exchange, String period, String status);

	public List<MacdCross> getMacdCross(String exchange, String symbol, String period, String type);

	public void modMacdCross(MacdCross macdCross);

	public void addMacdCross (MacdCross macdCross);


	/**
	 *
	 * @param exchange
	 * @param period
	 * @return
	 */
	public List<Analysis> getAnalysis(String exchange, String period, String symbol);

	public void modAnalysis(List<Analysis> list, String status, double price,String klineTime);

	public void addAnalysis(String exchange, String symbol, String period, String status,
							 double price,String klineTime) ;

	public void addAnalysisHistory(String exchange, String symbol, String period, String status,
									double price,String klineTime);

	public List<Tickers> getTickersListByExchange(String exchange);

	public void addTickers(ExchangeTickersEntry exchangeInfoEntry);

	public void updateTickers(List<ExchangeTickersEntry> list,String exchangeName);

	public void updateTickersByIdexApi(Map<String, Market> list, String exchangeName);

	public void updateTickersContractAddress(Map<String, Currencies> list, String exchangeName);

	public void updateTickersTotalSupply(Map<String, Double> list, String exchangeName);

	public List<PairsNew> getPairsNew(String market, String symbolId);

	/**
	 * 添加新交易对
	 * @param newPairs
	 */
	public void addPairsNew(NewPairsListEntry newPairs);

	public List<TradePoint> getTradePoint(TradePoint tradePoint);

	public void addTradePoint(TradePoint tradePoint);

	public List<TradePointExclude> getTradePointExclude(TradePointExclude tradePointexclude);

	public void addTradePointExclude(TradePointExclude tradePointexclude);

	public List<TradePointQuota> getTradePointQuota(TradePointQuota tradePointquota);

	public void addTradePointQuota(TradePointQuota tradePointquota);



}
