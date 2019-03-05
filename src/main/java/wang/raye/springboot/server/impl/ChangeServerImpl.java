package wang.raye.springboot.server.impl;

import de.elbatya.cryptocoins.bittrexclient.AicoinClient;
import de.elbatya.cryptocoins.bittrexclient.BlockccInfoClient;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.CommonInterval;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.CommonIntervalMap;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.aicoin.Kline;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.blockcc.CoinInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import wang.raye.springboot.bean.VolatileBean;
import wang.raye.springboot.bean.WxSendBean;
import wang.raye.springboot.model.AlertExchange;
import wang.raye.springboot.model.AlertSetting;
import wang.raye.springboot.model.AlertUser;
import wang.raye.springboot.model.Tickers;
import wang.raye.springboot.server.ChangeServer;
import wang.raye.springboot.server.InfoServer;
import wang.raye.springboot.server.MapperServer;
import wang.raye.springboot.utils.*;
import wang.raye.springboot.utils.send.WxSendMessageUtil;

import java.util.List;

/**
 * 涨跌幅相关数据库操作实现类
 * @author Raye
 * @since 2016年10月11日19:29:02
 */
@Repository
public class ChangeServerImpl implements ChangeServer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MapperServer mapperServer;
	@Autowired
	private InfoServer infoServer;
	@Autowired
	private WxSendUtils wxSendUtils;
	@Autowired
	private WxSendMessageUtil wxSendMessageUtil;

	@Autowired
	private QuotaUtils quotaUtils;
	@Autowired
	private QuotaCrossUtils quotaCrossUtils;

	@Value("${self.type.volatile}")
	private String VOLATILE;

	@Override
	public boolean netChange(String marketName, CommonInterval period) {

		List<AlertExchange> alertExchangeList = mapperServer.getAlertExchange(marketName);

		if (null != alertExchangeList && alertExchangeList.size() > 0) {
			double limitVol = mapperServer.getLimitVolume();
			List<Tickers> tickersList = mapperServer.getTickers(marketName,period.getIntervalId(), alertExchangeList, limitVol);
			if (null != tickersList && tickersList.size() > 0) {

				List<AlertUser> alertUsers= mapperServer.getAlertUsers();
				List<AlertUser> alertWorkWxUsers= mapperServer.getAlertWorkWxUsers();

				List<AlertSetting> alertSettingList = mapperServer.getAlertSettingByType(VOLATILE);

				AicoinClient aicoinClient = new AicoinClient();
				BlockccInfoClient blockccInfoClient = new BlockccInfoClient();

				for (Tickers tickers: tickersList) {
					// aicoin的symbol
//                    String symbol = pairs.getMarketAlias() + pairs.getSymbolAlias();
					String symbol = tickers.getExchangeName() + tickers.getDisplayPairName().replace("_","").toLowerCase();
					// aicoin的周线
					int step = CommonIntervalMap.ENUMMAP.get(period);
					List<Kline> listKline = null;
					try {
						// 调用aicoin的k线api
						listKline = aicoinClient.getPublicApi().getKline(symbol, step).unwrap();
						this.changeAnalysis(alertExchangeList,alertUsers,alertWorkWxUsers,tickers,blockccInfoClient,listKline, period, alertSettingList);

						Thread.sleep(300);

					} catch (Exception e) {
						log.error("线程 涨跌幅提醒运行出错 :["+Thread.currentThread().getName()+"]marketName：" + marketName + ",symbol:" +symbol+",period:"+period.getIntervalId());
						log.error(e.getMessage(),e);
					}
				}
			}
		}

		return true;
	}

	@Override
	public boolean idexNetChange() {

//		List<AlertExchange> alertExchangeList = mapperServer.getAlertExchange(marketName);
//
//		if (null != alertExchangeList && alertExchangeList.size() > 0) {
//			double limitVol = mapperServer.getLimitVolume();
//			List<Tickers> tickersList = mapperServer.getTickers(marketName, alertExchangeList, limitVol);
//			if (null != tickersList && tickersList.size() > 0) {
//
//				List<AlertUser> alertUsers= mapperServer.getAlertUsers();
//
//				List<AlertSetting> alertSettingList = mapperServer.getAlertSettingByType(VOLATILE);
//
//				AicoinClient aicoinClient = new AicoinClient();
//				BlockccInfoClient blockccInfoClient = new BlockccInfoClient();
//
//				for (Tickers tickers: tickersList) {
//					// aicoin的symbol
////                    String symbol = pairs.getMarketAlias() + pairs.getSymbolAlias();
//					String symbol = tickers.getExchangeName() + tickers.getDisplayPairName().replace("_","").toLowerCase();
//					// aicoin的周线
//					int step = CommonIntervalMap.ENUMMAP.get(period);
//					List<Kline> listKline = null;
//					try {
//						// 调用aicoin的k线api
//						listKline = aicoinClient.getPublicApi().getKline(symbol, step).unwrap();
//						this.changeAnalysis(alertUsers,tickers,blockccInfoClient,listKline, period, alertSettingList);
//
//						Thread.sleep(300);
//
//					} catch (Exception e) {
//						log.error("线程 涨跌幅提醒运行出错 :["+Thread.currentThread().getName()+"]marketName：" + marketName + ",symbol:" +symbol+",period:"+period.getIntervalId());
//						log.error(e.getMessage(),e);
//					}
//				}
//			}
//		}

		return true;
	}


	private void changeAnalysis(List<AlertExchange> alertExchangeList,List<AlertUser> alertUsers,List<AlertUser> alertWorkWxUsers,Tickers tickers,BlockccInfoClient blockccInfoClient,List<Kline> candlestickList, CommonInterval period, List<AlertSetting> alertSettingList) {

		if (null == candlestickList || candlestickList.size() < 10) {
			return;
		}

		Kline lastCandle = null;
		Kline secondCandle = null;
		try {
			lastCandle = candlestickList.get(candlestickList.size()-1);
			secondCandle = candlestickList.get(candlestickList.size()-2);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		if (lastCandle ==null || secondCandle == null) {
			return;
		}

		//判断是否爆涨还是暴跌
		VolatileBean volatileBean = quotaCrossUtils.getVolatile(candlestickList,alertSettingList);

		if (!"".equals(volatileBean.getStatus())) {
			try {
				// 涨跌幅微信提醒
				this.sendAlert(alertExchangeList,alertWorkWxUsers, tickers, infoServer.getCoinInfo(blockccInfoClient,tickers.getCoinId()), period,lastCandle.getClose(), VOLATILE, volatileBean);
				//this.sendAlert2(alertUsers, tickers, infoServer.getCoinInfo(blockccInfoClient,tickers.getCoinId()), period,lastCandle.getClose(), VOLATILE, status);
			} catch (Exception e) {
				log.error("线程 发送涨跌幅提醒信息出错 :["+Thread.currentThread().getName()+"]marketName：" +tickers.getExchangeName()+ ",symbol:" +tickers.getDisplayPairName());
				log.error(e.getMessage(),e);
				e.printStackTrace();
			}

		}

		try {
			Thread.sleep(300);
		}
		catch (InterruptedException e) {
			log.error(e.getMessage()+" 线程异常终止...",e);
		}
	}

	/**
	 * 涨跌幅微信提醒
	 */
	private void sendAlert2(List<AlertUser> alertUsers, Tickers tickers, CoinInfo coinInfo, CommonInterval period,double price, String type, String status){
		WxSendBean wxSendBean = new WxSendBean();
		wxSendBean.setExchange(tickers.getExchangeName());
		wxSendBean.setSymbol(tickers.getDisplayPairName());
		wxSendBean.setPeriod(period.getIntervalId());
		wxSendBean.setPrice(price);
		wxSendBean.setType(type);
		wxSendBean.setStatus(status);
		for(AlertUser alertUser : alertUsers) {
			wxSendBean.setSckey(alertUser.getSckey());
			wxSendUtils.sendCross(wxSendBean, coinInfo);
		}
	}

	/**
	 * 涨跌幅微信提醒
	 */
	private void sendAlert(List<AlertExchange> alertExchangeList,List<AlertUser> alertUsers, Tickers tickers, CoinInfo coinInfo, CommonInterval period,double price, String type, VolatileBean volatileBean){
		String mobile = "";
		for (int i = 0; i < alertUsers.size(); i++) {
			if (i == 0) {
				mobile = alertUsers.get(i).getMobile();
			} else {
				mobile += "/" + alertUsers.get(i).getMobile();
			}
		}

		String now = DateUtils.getToday();
        String title = "【"+tickers.getDisplayPairName()+"】价格["+price+"]｜"+ParseUtils.parseCrossStatus(volatileBean.getStatus())+ "｜"+ParseUtils.normalDecimalFormat(volatileBean.getChange()*100) + "%｜"+period.getIntervalId()+"｜"+ParseUtils.parseCrossType(type);

		StringBuffer desp = new StringBuffer();
		desp.append("<div class=\\\"gray\\\">"+now+"</div> <div class=\\\"normal\\\">");
		desp.append("成交额:" + "[¥" + ParseUtils.normalDecimalFormat(coinInfo.getVolume_ex() * coinInfo.getCNY_RATE() / 10000) + "万]｜");
		desp.append("市值:" + "[¥" + ParseUtils.normalDecimalFormat(coinInfo.getMarketCap() * coinInfo.getCNY_RATE() / 100000000) + "亿]｜<br>");
		desp.append("市值排名:" + "[" + coinInfo.getLevel() + "]");
		desp.append("</div>");
		desp.append("<div class=\\\"highlight\\\">");
		desp.append("时涨幅:" + "[" + ParseUtils.normalDecimalFormat(coinInfo.getChange1h()) + "%]｜");
		desp.append("日涨幅:" + "[" + ParseUtils.normalDecimalFormat(coinInfo.getChange1d()) + "%]｜<br>");
		desp.append("周涨幅:" + "[" + ParseUtils.normalDecimalFormat(coinInfo.getChange7d()) + "%]｜");
		desp.append("月涨幅:" + "[" + ParseUtils.normalDecimalFormat(coinInfo.getChange30d()) + "%]");
		desp.append("</div>");

		String url=ParseUtils.parseAlertUrl(alertExchangeList,tickers.getExchangeName(),tickers.getDisplayPairName());
		String secretId = WxSendMessageUtil.SECRETID_BINANCE_CHANGE;
		String agentId = WxSendMessageUtil.AGENTID_BINANCE_CHANGE;
		if ("huobipro".equals(tickers.getExchangeName()) || "hadax".equals(tickers.getExchangeName())) {
			secretId = WxSendMessageUtil.SECRETID_HUOBI_CHANGE;
			agentId = WxSendMessageUtil.AGENTID_HUOBI_CHANGE;
		} else if ("gate".equals(tickers.getExchangeName())) {
			secretId = WxSendMessageUtil.SECRETID_GATE_CHANGE;
			agentId = WxSendMessageUtil.AGENTID_GATE_CHANGE;
		} else if ("bittrex".equals(tickers.getExchangeName())) {
			secretId = WxSendMessageUtil.SECRETID_BITTREX_CHANGE;
			agentId = WxSendMessageUtil.AGENTID_BITTREX_CHANGE;
		} else if ("okex".equals(tickers.getExchangeName())) {
			secretId = WxSendMessageUtil.SECRETID_OKEX_CHANGE;
			agentId = WxSendMessageUtil.AGENTID_OKEX_CHANGE;
		}

        wxSendMessageUtil.sendMessageCard(title, desp.toString(), url, secretId, agentId, mobile);
	}


}
