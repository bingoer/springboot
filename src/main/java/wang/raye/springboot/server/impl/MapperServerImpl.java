package wang.raye.springboot.server.impl;

import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.blockcc.ExchangeTickersEntry;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.blockcc.NewPairsList;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.blockcc.NewPairsListEntry;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.idex.Currencies;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.idex.Market;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import wang.raye.springboot.model.*;
import wang.raye.springboot.model.mapper.*;
import wang.raye.springboot.server.MapperServer;
import wang.raye.springboot.utils.DateUtils;
import wang.raye.springboot.utils.ParseUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 涨跌幅相关数据库操作实现类
 * @author Raye
 * @since 2016年10月11日19:29:02
 */
@Repository
public class MapperServerImpl implements MapperServer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SymbolsMapper symbolsMapper;
	@Autowired
	private MacdCrossMapper macdCrossMapper;
	@Autowired
	private MacdCrossHistoryMapper macdCrossHistoryMapper;
	@Autowired
	private AlertMapper alertMapper;
	@Autowired
	private AlertSettingMapper alertSettingMapper;
	@Autowired
	private AlertExchangeMapper alertExchangeMapper;
	@Autowired
	private AlertUserMapper alertUserMapper;
	@Autowired
	private TickersMapper tickersMapper;
	@Autowired
	private AnalysisMapper analysisMapper;
	@Autowired
	private AnalysisHistoryMapper analysisHistoryMapper;
	@Autowired
	private PairsNewMapper pairsNewMapper;
	@Autowired
	private TradePointMapper tradePointMapper;
	@Autowired
	private TradePointExcludeMapper tradePointExcludeMapper;
	@Autowired
	private TradePointQuotaMapper tradePointQuotaMapper;


	@Value("${self.type.volatile}")
	private String VOLATILE;
	private final String TYPE_LIMIT = "LIMIT";


	@Override
	public List<AlertExchange> getAlertExchange(String marketName) {
		AlertExchangeCriteria condAlert = new AlertExchangeCriteria();
		AlertExchangeCriteria.Criteria criteriaAlert = condAlert.createCriteria();
		if(marketName.equals("gate-io")) {
			marketName = "gate";
		}
		criteriaAlert.andExchangeEqualTo(marketName);
		criteriaAlert.andOpenflgEqualTo("1");
		return alertExchangeMapper.selectByExample(condAlert);
	}

	@Override
	public List<AlertSetting> getAlertSettingByPeriod(String period) {
		AlertSettingCriteria condAlert = new AlertSettingCriteria();
		AlertSettingCriteria.Criteria criteriaAlert = condAlert.createCriteria();
		criteriaAlert.andPeriodEqualTo(period);
		criteriaAlert.andOpenflgEqualTo("1");
		return alertSettingMapper.selectByExample(condAlert);
	}

	@Override
	public List<AlertSetting> getAlertSettingByType(String type) {
		AlertSettingCriteria condAlert = new AlertSettingCriteria();
		AlertSettingCriteria.Criteria criteriaAlert = condAlert.createCriteria();
		criteriaAlert.andTypeEqualTo(type);
		criteriaAlert.andOpenflgEqualTo("1");
		return alertSettingMapper.selectByExample(condAlert);
	}

	@Override
	public List<AlertSetting> getAlertSettingByCond(String period, String type) {
		AlertSettingCriteria condAlert = new AlertSettingCriteria();
		AlertSettingCriteria.Criteria criteriaAlert = condAlert.createCriteria();
		if (!"".equals(period)) {
			criteriaAlert.andPeriodEqualTo(period);
		}
		if (!"".equals(type)) {
			criteriaAlert.andTypeEqualTo(type);
		}
		criteriaAlert.andOpenflgEqualTo("1");
		return alertSettingMapper.selectByExample(condAlert);
	}

	@Override
	public List<AlertUser> getAlertUsers() {
		AlertUserCriteria cond = new AlertUserCriteria();
		cond.createCriteria()
				.andOpenflgEqualTo("1");
		return alertUserMapper.selectByExample(cond);
	}

	@Override
	public List<AlertUser> getAlertWorkWxUsers() {
		AlertUserCriteria cond = new AlertUserCriteria();
		cond.createCriteria()
				.andOpenflgWorkWxEqualTo("1");
		return alertUserMapper.selectByExample(cond);
	}

	/**
	 * 调用db获取限制条件成交量
	 * @return 限制条件成交量
	 */
	@Override
	public double getLimitVolume() {
		double volume = 0;
		AlertSettingCriteria condAlert = new AlertSettingCriteria();
		AlertSettingCriteria.Criteria criteriaAlert = condAlert.createCriteria();
		criteriaAlert.andTypeEqualTo(TYPE_LIMIT);
		criteriaAlert.andOpenflgEqualTo("1");
		List<AlertSetting> limitList = alertSettingMapper.selectByExample(condAlert);
		if (null != limitList && limitList.size() > 0) {
			volume = limitList.get(0).getRatio();
		}
		return volume;
	}

	@Override
	public List<Tickers> getTickers(String marketName, String period, List<AlertExchange> alertExchangeList, double limitVolume) {
		List<String> bases = new ArrayList<>();
		for (AlertExchange alertExchange:alertExchangeList) {
			if (!bases.contains(alertExchange.getBase())) {
				bases.add(alertExchange.getBase());
			}
		}
		TickersCriteria cond = new TickersCriteria();
		TickersCriteria.Criteria criteriaTickers = cond.createCriteria();
		criteriaTickers
				.andExchangeNameEqualTo(marketName)
				.andBaseSymbolIn(bases)
				.andEnableklineEqualTo("1")
				.andVolumeGreaterThan(limitVolume);
		if (null != period && !period.equals("")) {
			criteriaTickers.andPeriodLike("%," + period + "%");
		}
		return tickersMapper.selectByExample(cond);
	}

	/**
	 *
	 * @param exchange
	 * @param period
	 * @return
	 */
	@Override
	public List<MacdCross> getMacdCrossAll(String exchange,String period) {
		MacdCrossCriteria condMacdCross = new MacdCrossCriteria();
		MacdCrossCriteria.Criteria criteriaMacdCross = condMacdCross.createCriteria();
		criteriaMacdCross.andExchangeEqualTo(exchange);
		criteriaMacdCross.andPeriodEqualTo(period);
		return macdCrossMapper.selectByExample(condMacdCross);
	}

	/**
	 *
	 * @param exchange
	 * @param period
	 * @return
	 */
	@Override
	public List<MacdCross> getMacdCrossByType(String exchange, String symbol, String type, String period) {
		MacdCrossCriteria condMacdCross = new MacdCrossCriteria();
		MacdCrossCriteria.Criteria criteriaMacdCross = condMacdCross.createCriteria();
		criteriaMacdCross.andExchangeEqualTo(exchange);
		criteriaMacdCross.andSymbolEqualTo(symbol);
		criteriaMacdCross.andTypeEqualTo(type);
		criteriaMacdCross.andPeriodEqualTo(period);
		return macdCrossMapper.selectByExample(condMacdCross);
	}

	/**
	 *
	 * @param exchange
	 * @param period
	 * @return
	 */
	@Override
	public List<Analysis> searchAnalysis(String exchange, String period, String status) {
		AnalysisCriteria condMacdCross = new AnalysisCriteria();
		AnalysisCriteria.Criteria criteria = condMacdCross.createCriteria();
		if (!"-".equals(exchange)) {
			criteria.andExchangeEqualTo(exchange);
		}
		if (!"-".equals(period)) {
			criteria.andPeriodEqualTo(period);
		}
		if (!"-".equals(status)) {
			criteria.andStatusEqualTo(status);
		}
		condMacdCross.setOrderByClause("create_time desc");
		return analysisMapper.selectByExample(condMacdCross);
	}

	/**
	 *
	 * @param exchange
	 * @param symbol
	 * @param period
	 * @param type
	 * @return
	 */
	@Override
	public List<MacdCross> getMacdCross(String exchange, String symbol, String period, String type) {
		MacdCrossCriteria condMacdCross = new MacdCrossCriteria();
		MacdCrossCriteria.Criteria criteriaMacdCross = condMacdCross.createCriteria();
		criteriaMacdCross.andExchangeEqualTo(exchange);
		if (!"".equals(symbol)) {
			criteriaMacdCross.andSymbolEqualTo(symbol);
		}
		criteriaMacdCross.andPeriodEqualTo(period);
		criteriaMacdCross.andTypeEqualTo(type);
		return macdCrossMapper.selectByExample(condMacdCross);
	}

	@Override
	public void modMacdCross(MacdCross macdCross) {
		macdCrossMapper.updateByPrimaryKey(macdCross);
	}


	@Override
	public void addMacdCross (MacdCross macdCross) {
		macdCrossMapper.insert(macdCross);
	}


	/**
	 *
	 * @param exchange
	 * @param period
	 * @return
	 */
	@Override
	public  List<Analysis> getAnalysis(String exchange, String period, String symbol) {
		AnalysisCriteria condMacdCross = new AnalysisCriteria();
		condMacdCross.createCriteria()
				.andExchangeEqualTo(exchange)
				.andPeriodEqualTo(period)
				.andSymbolEqualTo(symbol);
		return analysisMapper.selectByExample(condMacdCross);
	}

	/**
	 *
	 * @param list
	 * @param status
	 * @param price
	 * @param klineTime
	 */
	@Override
	public  void modAnalysis(List<Analysis> list, String status, double price,String klineTime) {
		Analysis analysis = list.get(0);
		analysis.setStatus(status);
		analysis.setPrice(price);
		analysis.setKlineTime(klineTime);
		analysis.setCreateTime(new Date());
		analysisMapper.updateByPrimaryKey(analysis);
	}

	/**
	 *
	 * @param exchange
	 * @param symbol
	 * @param period
	 * @param status
	 * @param price
	 * @param klineTime
	 */
	@Override
	public void addAnalysis(String exchange, String symbol, String period, String status,
							 double price,String klineTime) {
		Analysis analysis = new Analysis();
		analysis.setExchange(exchange);
		analysis.setSymbol(symbol);
		analysis.setPeriod(period);
		analysis.setStatus(status);
		analysis.setPrice(price);
		analysis.setKlineTime(klineTime);
		analysis.setCreateTime(new Date());
		analysisMapper.insert(analysis);
	}

	@Override
	public void addAnalysisHistory(String exchange, String symbol, String period, String status,
									double price,String klineTime){
		AnalysisHistory analysisHistory = new AnalysisHistory();
		analysisHistory.setExchange(exchange);
		analysisHistory.setSymbol(symbol);
		analysisHistory.setPeriod(period);
		analysisHistory.setStatus(status);
		analysisHistory.setPrice(price);
		analysisHistory.setKlineTime(klineTime);
		analysisHistory.setCreateTime(new Date());
		analysisHistoryMapper.insert(analysisHistory);
	}

	@Override
	public List<Tickers> getTickersListByExchange(String exchange) {
		TickersCriteria cond = new TickersCriteria();
		cond.createCriteria()
				.andExchangeNameEqualTo(exchange);
		return tickersMapper.selectByExample(cond);
	}

	/**
	 * 添加新交易对
	 */
	@Override
	public void addTickers(ExchangeTickersEntry exchangeInfoEntry) {
		Tickers tickers = new Tickers();
		tickers.setTickerId(exchangeInfoEntry.get_id());//": "5aa734ccce79d2cf9bbd3809",
		tickers.setDisplayPairName(exchangeInfoEntry.getDisplay_pair_name().replace("/","_"));//display_pair_name;//": "CNN/ETH",
		tickers.setCoinSymbol(exchangeInfoEntry.getCoin_symbol());//coin_symbol;//": "CNN",
		tickers.setCoinName(exchangeInfoEntry.getCoin_name());//coin_name;//": "Content Neutrality Network",
		tickers.setCoinId(exchangeInfoEntry.getCoin_id());//coin_id;//": "content-neutrality-network",
		tickers.setBaseSymbol(exchangeInfoEntry.getBase_symbol());//base_symbol;//": "ETH",
		tickers.setChange1d(exchangeInfoEntry.getChange1d());//change1d;//": 3.13,
		tickers.setType(exchangeInfoEntry.getType());//type;//": 3,
		tickers.setBid(exchangeInfoEntry.getBid());//bid;//": 0,
		tickers.setAsk(exchangeInfoEntry.getAsk());// ask;//": 0,
		tickers.setNativePrice(exchangeInfoEntry.getNative_price());// native_price;//": 0.0000028247,
		tickers.setLow1d(exchangeInfoEntry.getLow1d());// low1d;//": 0.001261590866094,
		tickers.setHigh1d(exchangeInfoEntry.getHigh1d());// high1d;//": 0.001416815065349181,
		tickers.setStatus(exchangeInfoEntry.getStatus());// status;//": "0",
		tickers.setExchangeDisplayName(exchangeInfoEntry.getExchange_display_name());// exchange_display_name;//": "HADAX",
		tickers.setExchangeZhName(exchangeInfoEntry.getExchange_zh_name());// exchange_zh_name;//": "HADAX",
		tickers.setExchangeName(ParseUtils.parseKlineMarket(exchangeInfoEntry.getExchange_name()));// exchange_name;//": "hadax",
		tickers.setUrl(exchangeInfoEntry.getUrl());// url;//": "http://www.hadax.com",
		tickers.setDatacenterPairName(exchangeInfoEntry.getDataCenter_pair_name());// dataCenter_pair_name;//": "CNN/ETH",
		tickers.setTimestamps(DateUtils.format(exchangeInfoEntry.getTimestamps()));// timestamps;//": 1528979309122,
//		tickers.setEnablekline(exchangeInfoEntry.isEnableKline()? "1":"0");// enableKline;//": true,
		tickers.setEnablekline("0");// enableKline;//": true,
		tickers.setVolume(exchangeInfoEntry.getVolume());// volume;//": 911305.6262186101,
		tickers.setPrice(exchangeInfoEntry.getPrice());// price;//": 0.001370621430559893,
		tickers.setPercent(exchangeInfoEntry.getPercent());// percent;//": 0.017289500202242524
		tickersMapper.insert(tickers);
	}

	@Override
	public void updateTickers(List<ExchangeTickersEntry> list, String exchangeName){
		List<Tickers> recordList = new ArrayList<>();
		List<String> displayPairNameList = new ArrayList<>();
		for (ExchangeTickersEntry exchangeInfoEntry:list) {
			Tickers tickers = new Tickers();
			tickers.setTickerId(exchangeInfoEntry.get_id());//": "5aa734ccce79d2cf9bbd3809",
			String displayPairName = exchangeInfoEntry.getDisplay_pair_name().replace("/","_");
			tickers.setDisplayPairName(displayPairName);//display_pair_name;//": "CNN/ETH",
			tickers.setCoinSymbol(exchangeInfoEntry.getCoin_symbol());//coin_symbol;//": "CNN",
			tickers.setCoinName(exchangeInfoEntry.getCoin_name());//coin_name;//": "Content Neutrality Network",
			tickers.setCoinId(exchangeInfoEntry.getCoin_id());//coin_id;//": "content-neutrality-network",
			tickers.setBaseSymbol(exchangeInfoEntry.getBase_symbol());//base_symbol;//": "ETH",
			tickers.setChange1d(exchangeInfoEntry.getChange1d());//change1d;//": 3.13,
			tickers.setType(exchangeInfoEntry.getType());//type;//": 3,
			tickers.setBid(exchangeInfoEntry.getBid());//bid;//": 0,
			tickers.setAsk(exchangeInfoEntry.getAsk());// ask;//": 0,
			tickers.setNativePrice(exchangeInfoEntry.getNative_price());// native_price;//": 0.0000028247,
			tickers.setLow1d(exchangeInfoEntry.getLow1d());// low1d;//": 0.001261590866094,
			tickers.setHigh1d(exchangeInfoEntry.getHigh1d());// high1d;//": 0.001416815065349181,
			tickers.setStatus(exchangeInfoEntry.getStatus());// status;//": "0",
			tickers.setExchangeDisplayName(exchangeInfoEntry.getExchange_display_name());// exchange_display_name;//": "HADAX",
			tickers.setExchangeZhName(exchangeInfoEntry.getExchange_zh_name());// exchange_zh_name;//": "HADAX",
			tickers.setExchangeName(ParseUtils.parseKlineMarket(exchangeInfoEntry.getExchange_name()));// exchange_name;//": "hadax",
			tickers.setUrl(exchangeInfoEntry.getUrl());// url;//": "http://www.hadax.com",
			tickers.setDatacenterPairName(exchangeInfoEntry.getDataCenter_pair_name());// dataCenter_pair_name;//": "CNN/ETH",
			tickers.setTimestamps(DateUtils.format(exchangeInfoEntry.getTimestamps()));// timestamps;//": 1528979309122,
//			tickers.setEnablekline(exchangeInfoEntry.isEnableKline()? "1":"0");// enableKline;//": true,
			tickers.setVolume(exchangeInfoEntry.getVolume());// volume;//": 911305.6262186101,
			tickers.setPrice(exchangeInfoEntry.getPrice());// price;//": 0.001370621430559893,
			tickers.setPercent(exchangeInfoEntry.getPercent());// percent;//": 0.017289500202242524
			recordList.add(tickers);

			// 添加交易对名list
			if (!displayPairNameList.contains(displayPairName)) {
				displayPairNameList.add(displayPairName);
			}
		}
		TickersCriteria cond = new TickersCriteria();
		cond.createCriteria()
				.andDisplayPairNameIn(displayPairNameList)
				.andExchangeNameEqualTo(exchangeName);
		tickersMapper.updateBatchByExampleSelective(recordList, cond);
	}

	@Override
	public void updateTickersByIdexApi(Map<String, Market> list, String exchangeName){
		List<Tickers> recordList = new ArrayList<>();
		List<String> displayPairNameList = new ArrayList<>();
		for(Map.Entry<String, Market> entry : list.entrySet()){
			Tickers tickers = new Tickers();
			tickers.setNativePrice(ParseUtils.parseDoublePrice(entry.getValue().getLast()));// last:0.016747442554110609
			tickers.setLow1d(ParseUtils.parseDoublePrice(entry.getValue().getLow()));// low:0.0130154
			tickers.setHigh1d(ParseUtils.parseDoublePrice(entry.getValue().getHigh()));// high:0.017
			tickers.setBid(ParseUtils.parseDoublePrice(entry.getValue().getHighestBid()));//highestBid:0.016520020000000065
			tickers.setAsk(ParseUtils.parseDoublePrice(entry.getValue().getLowestAsk()));// lowestAsk:0.016889570873997181
			tickers.setChange1d(ParseUtils.parseDoublePrice(entry.getValue().getPercentChange()));//percentChange:25.50513951
			tickers.setBaseVolume(ParseUtils.parseDoublePrice(entry.getValue().getBaseVolume()));//baseVolume:2411.146316338883569655
			tickers.setTimestamps(DateUtils.format(new Date()));// timestamps;//": 1528979309122,

			String displayPairName = entry.getKey();//": "ETH_QNT",
			tickers.setDisplayPairName(displayPairName);//display_pair_name;//": "QNT_ETH",

			recordList.add(tickers);

			// 添加交易对名list
			if (!displayPairNameList.contains(displayPairName)) {
				displayPairNameList.add(displayPairName);
			}
		}
		TickersCriteria cond = new TickersCriteria();
		cond.createCriteria()
				.andDisplayPairNameIn(displayPairNameList)
				.andExchangeNameEqualTo(exchangeName);
		tickersMapper.updateBatchByExampleSelective(recordList, cond);
	}

	@Override
	public void updateTickersContractAddress(Map<String, Currencies> list, String exchangeName){
		List<Tickers> recordList = new ArrayList<>();
		List<String> displayPairNameList = new ArrayList<>();
		for(Map.Entry<String, Currencies> entry : list.entrySet()){
			Tickers tickers = new Tickers();
			tickers.setContractAddress(entry.getValue().getAddress());//address	"0xf660ca1e228e7be1fa8b4f5583145e31147fb577"
			tickers.setDecimals(entry.getValue().getDecimals());
			tickers.setCoinName(entry.getValue().getName());

			tickers.setTimestamps(DateUtils.format(new Date()));// timestamps;//": 1528979309122,

			String displayPairName = entry.getKey();//": "ETH_QNT",
			tickers.setDisplayPairName(displayPairName);//display_pair_name;//": "QNT_ETH",

			recordList.add(tickers);

			// 添加交易对名list
			if (!displayPairNameList.contains(displayPairName)) {
				displayPairNameList.add(displayPairName);
			}
		}
		TickersCriteria cond = new TickersCriteria();
		cond.createCriteria()
				.andDisplayPairNameIn(displayPairNameList)
				.andExchangeNameEqualTo(exchangeName);
		tickersMapper.updateBatchByExampleSelective(recordList, cond);
	}

	@Override
	public void updateTickersTotalSupply(Map<String, Double> list, String exchangeName){
		List<Tickers> recordList = new ArrayList<>();
		List<String> displayPairNameList = new ArrayList<>();
		for(Map.Entry<String, Double> entry : list.entrySet()){
			Tickers tickers = new Tickers();
			tickers.setTotalSupply(entry.getValue());

			tickers.setTimestamps(DateUtils.format(new Date()));// timestamps;//": 1528979309122,

			String displayPairName = entry.getKey();//": "ETH_QNT",
			tickers.setDisplayPairName(displayPairName);//display_pair_name;//": "QNT_ETH",

			recordList.add(tickers);

			// 添加交易对名list
			if (!displayPairNameList.contains(displayPairName)) {
				displayPairNameList.add(displayPairName);
			}
		}
		TickersCriteria cond = new TickersCriteria();
		cond.createCriteria()
				.andDisplayPairNameIn(displayPairNameList)
				.andExchangeNameEqualTo(exchangeName);
		tickersMapper.updateBatchByExampleSelective(recordList, cond);
	}


	public List<PairsNew> getPairsNew(String market, String symbolId) {
		PairsNewCriteria cond = new PairsNewCriteria();
		cond.createCriteria()
				.andMarketEqualTo(market)
				.andSymbolIdEqualTo(symbolId);
		return pairsNewMapper.selectByExample(cond);
	}

	/**
	 * 添加新交易对
	 * @param newPairs
	 */
	public void addPairsNew(NewPairsListEntry newPairs) {
		PairsNew pairs = new PairsNew();
		pairs.setMarket(newPairs.get_id().getMarket_display_name());
		pairs.setSymbol(newPairs.get_id().getExchange_symbol());
		pairs.setSymbolId(newPairs.get_id().getExchange_symbol_id());
		pairs.setCurrency(ParseUtils.listToString(newPairs.getSymbol()));
		pairs.setCoinInfo(ParseUtils.listToString(newPairs.getCoin_info()));
		pairs.setCoinUrl(ParseUtils.listToString(newPairs.getCoin_url()));
		pairs.setChargeAt(String.valueOf(newPairs.getCharge_at()));
		pairs.setWithdrawAt(String.valueOf(newPairs.getWithdraw_at()));
		pairs.setEndAt(String.valueOf(newPairs.getEnd_at()));
		pairs.setTradeAt(String.valueOf(newPairs.getTrade_at()));
		pairs.setPrice(String.valueOf(newPairs.getPrice()));
		pairs.setChange1d(String.valueOf(newPairs.getChange1d()));
		pairsNewMapper.insert(pairs);
	}


	@Override
	public List<TradePoint> getTradePoint(TradePoint tradePoint) {
		TradePointCriteria cond = new TradePointCriteria();
		TradePointCriteria.Criteria criteria = cond.createCriteria();
		if (StringUtils.isNotEmpty(tradePoint.getExchange())) {
			criteria.andExchangeEqualTo(tradePoint.getExchange());
		}
		if (StringUtils.isNotEmpty(tradePoint.getSymbol())) {
			criteria.andSymbolEqualTo(tradePoint.getSymbol());
		}
		if (StringUtils.isNotEmpty(tradePoint.getPeriod())) {
			criteria.andPeriodEqualTo(tradePoint.getPeriod());
		}
		if (StringUtils.isNotEmpty(tradePoint.getQuantType())) {
			criteria.andQuantTypeEqualTo(tradePoint.getQuantType());
		}
		if (StringUtils.isNotEmpty(tradePoint.getStatus())) {
			criteria.andStatusEqualTo(tradePoint.getStatus());
		}
		if (StringUtils.isNotEmpty(tradePoint.getKlineTime())) {
			criteria.andKlineTimeEqualTo(tradePoint.getKlineTime());
		}
		cond.setOrderByClause("exchange desc, symbol desc, base desc, period desc, kline_time asc");
		return tradePointMapper.selectByExample(cond);
	}

	@Override
	public void addTradePoint(TradePoint tradePoint){
		tradePoint.setTime(new Date());
		tradePointMapper.insert(tradePoint);
	}

	@Override
	public List<TradePointExclude> getTradePointExclude(TradePointExclude tradePointExclude) {
		TradePointExcludeCriteria cond = new TradePointExcludeCriteria();
		cond.createCriteria()
				.andExchangeEqualTo(tradePointExclude.getExchange())
				.andSymbolEqualTo(tradePointExclude.getSymbol())
				.andPeriodEqualTo(tradePointExclude.getPeriod())
				.andStatusEqualTo(tradePointExclude.getStatus())
				.andKlineTimeEqualTo(tradePointExclude.getKlineTime());
		return tradePointExcludeMapper.selectByExample(cond);
	}

	@Override
	public void addTradePointExclude(TradePointExclude tradePointExclude){
		tradePointExclude.setTime(new Date());
		tradePointExcludeMapper.insert(tradePointExclude);
	}

	@Override
	public List<TradePointQuota> getTradePointQuota(TradePointQuota tradePointQuota) {
		TradePointQuotaCriteria cond = new TradePointQuotaCriteria();
		cond.createCriteria()
				.andExchangeEqualTo(tradePointQuota.getExchange())
				.andSymbolEqualTo(tradePointQuota.getSymbol())
				.andPeriodEqualTo(tradePointQuota.getPeriod())
				.andQuantTypeEqualTo(tradePointQuota.getQuantType())
				.andQuotaTypeEqualTo(tradePointQuota.getQuotaType())
				.andStatusEqualTo(tradePointQuota.getStatus())
				.andKlineTimeEqualTo(tradePointQuota.getKlineTime());
		return tradePointQuotaMapper.selectByExample(cond);
	}

	@Override
	public void addTradePointQuota(TradePointQuota tradePointQuota){
		tradePointQuota.setTime(new Date());
		tradePointQuotaMapper.insert(tradePointQuota);
	}


}
