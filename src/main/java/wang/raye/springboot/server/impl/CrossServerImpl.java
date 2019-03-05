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
import wang.raye.springboot.bean.*;
import wang.raye.springboot.model.*;
import wang.raye.springboot.server.AnalysisServer;
import wang.raye.springboot.server.CrossServer;
import wang.raye.springboot.server.InfoServer;
import wang.raye.springboot.server.MapperServer;
import wang.raye.springboot.utils.*;
import wang.raye.springboot.utils.send.WxSendMessageUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 指标相关数据库操作实现类
 * @author Raye
 * @since 2016年10月11日19:29:02
 */
@Repository
public class CrossServerImpl implements CrossServer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MapperServer mapperServer;
	@Autowired
	private InfoServer infoServer;
	@Autowired
	private AnalysisServer analysisServer;

	@Autowired
	private WxSendUtils wxSendUtils;
	@Autowired
	private WxSendMessageUtil wxSendMessageUtil;

	@Autowired
	private QuotaUtils quotaUtils;
	@Autowired
	private QuotaCrossUtils quotaCrossUtils;

	@Value("${self.type.macd}")
	private String MACD;
	@Value("${self.type.kdj}")
	private String KDJ;
	@Value("${self.type.doji}")
	private String DOJI;
	private String VOLUME="VOLUME";
	private String RSI="RSI";
	private String BOLL="BOLL";
    private String MA="MA";
	private String VR="VR";
	private String DMI="DMI";
    private String SAR="SAR";
//	private String TYPE_BUY="BUY";
//	private String TYPE_SELL="SELL";
//    private String TYPE_LOW="LOW";
//    private String TYPE_HIGH="HIGH";
//    private String TYPE_STOP="STOP";
//    private String STATUS_LOW="11";
//    private String STATUS_HIGH="21";
//    private String STATUS_STOP="30";
	private String GOLDEN_CROSS="2";
	private String Negative_Divergence="9";
	private String DEATH_CROSS="4";

	private String DOJI_UP="32";
	private String DOJI_DOWN="34";

	private final String TYPE_LIMIT = "LIMIT";

	@Override
	public boolean saveCross(String exchange, CommonInterval period) {

		List<AlertExchange> alertExchangeList = mapperServer.getAlertExchange(exchange);

		if (null != alertExchangeList && alertExchangeList.size() > 0) {
			double limitVol = mapperServer.getLimitVolume();
			List<Tickers> tickersList = mapperServer.getTickers(exchange,period.getIntervalId(), alertExchangeList, limitVol);
			if (null != tickersList && tickersList.size() > 0) {

				AicoinClient aicoinClient = new AicoinClient();

				for (Tickers tickers: tickersList) {
					// aicoin的symbol
//					String symbol = pairs.getMarketAlias() + pairs.getSymbolAlias();
                    String symbol = tickers.getExchangeName() + tickers.getDisplayPairName().replace("_","").toLowerCase();
					// aicoin的周线
					int step = CommonIntervalMap.ENUMMAP.get(period);
					List<Kline> candlestickList = null;
					try {
						// 调用aicoin的k线api
						candlestickList = aicoinClient.getPublicApi().getKline(symbol, step).unwrap();

						Thread.sleep(300);

					} catch (Exception e) {
						log.error("线程 指标监测调K线API出错 :["+Thread.currentThread().getName()+"]marketName：" + exchange + ",symbol:" +symbol+",period:"+period.getIntervalId());
                        log.error(e.getMessage(),e);
					}

//					if (tickers.getDisplayPairName().equals("CHAT_BTC")) {
//						System.out.println("KDJ:");
//					}

					this.saveCrossData(candlestickList,exchange, tickers.getDisplayPairName(), period.getIntervalId());
				}
			}
		}

		return true;
	}

	@Override
	public boolean crossMonitor(String exchange, CommonInterval period) {
		List<AlertExchange> alertExchangeList = mapperServer.getAlertExchange(exchange);

		if (null != alertExchangeList && alertExchangeList.size() > 0) {
			double limitVol = mapperServer.getLimitVolume();
			List<Tickers> tickersList = mapperServer.getTickers(exchange,period.getIntervalId(), alertExchangeList, limitVol);
			List<MacdCross> crossList= mapperServer.getMacdCrossAll(exchange, period.getIntervalId());
			if (null != tickersList && tickersList.size() > 0) {

				List<AlertUser> alertUsers= mapperServer.getAlertUsers();
				List<AlertUser> alertWorkWxUsers= mapperServer.getAlertWorkWxUsers();
				BlockccInfoClient blockccInfoClient = new BlockccInfoClient();

				List<AlertSetting> alertSettingList = mapperServer.getAlertSettingByPeriod(period.getIntervalId());

				for (Tickers tickers:tickersList) {

					KdjBean kdjBean = new KdjBean();
					MacdBean macdBean = new MacdBean();
					BollBean bollBean = new BollBean();
					RsiBean rsiBean = new RsiBean();
					VolumeBean volBean = new VolumeBean();
					MaBean maBean = new MaBean();
					VrBean vrBean = new VrBean();
					DmiBean dmiBean = new DmiBean();
                    SarBean sarBean = new SarBean();
					double price = tickers.getNativePrice();
					double pricePre = tickers.getNativePrice();
                    KdjBean kdjPreBean = new KdjBean();
                    MacdBean macdPreBean = new MacdBean();
                    BollBean bollPreBean = new BollBean();
                    RsiBean rsiPreBean = new RsiBean();
                    VolumeBean volPreBean = new VolumeBean();
                    MaBean maPreBean = new MaBean();
					VrBean vrPreBean = new VrBean();
					DmiBean dmiPreBean = new DmiBean();
                    SarBean sarPreBean = new SarBean();

					String klineTime = "";

					String symbol = tickers.getDisplayPairName();

					for (MacdCross macdCross:crossList) {
						if (macdCross.getSymbol().equals(symbol)) {
							if (macdCross.getType().equals(KDJ)) {
								kdjBean.setK(macdCross.getQuota1());
								kdjBean.setD(macdCross.getQuota2());
								kdjBean.setJ(macdCross.getQuota3());
								kdjBean.setJPre(macdCross.getPreQuota1());

                                kdjPreBean.setK(macdCross.getPreQuota1());
                                kdjPreBean.setD(macdCross.getPreQuota2());
                                kdjPreBean.setJ(macdCross.getPreQuota3());
                                kdjPreBean.setJPre(macdCross.getThrQuota1());
								continue;
							}
							if (macdCross.getType().equals(MACD)) {
								macdBean.setDif(macdCross.getQuota1());
								macdBean.setDea(macdCross.getQuota2());
								macdBean.setBar(macdCross.getQuota3());
								macdBean.setDifPre(macdCross.getPreQuota1());

                                macdPreBean.setDif(macdCross.getPreQuota1());
                                macdPreBean.setDea(macdCross.getPreQuota2());
                                macdPreBean.setBar(macdCross.getPreQuota3());
                                macdPreBean.setDifPre(macdCross.getThrQuota1());

								price = macdCross.getPrice();
								pricePre = macdCross.getPrePrice();
								klineTime = macdCross.getKlineTime();
								continue;
							}
							if (macdCross.getType().equals(BOLL)) {
								bollBean.setBoll(macdCross.getQuota1());
								bollBean.setUb(macdCross.getQuota2());
								bollBean.setLb(macdCross.getQuota3());

                                bollPreBean.setBoll(macdCross.getPreQuota1());
                                bollPreBean.setUb(macdCross.getPreQuota2());
                                bollPreBean.setLb(macdCross.getPreQuota3());
								continue;
							}
							if (macdCross.getType().equals(RSI)) {
								rsiBean.setRsi1(macdCross.getQuota1());
								rsiBean.setRsi2(macdCross.getQuota2());
								rsiBean.setRsi3(macdCross.getQuota3());

                                rsiPreBean.setRsi1(macdCross.getPreQuota1());
                                rsiPreBean.setRsi2(macdCross.getPreQuota2());
                                rsiPreBean.setRsi3(macdCross.getPreQuota3());
								continue;
							}
							if (macdCross.getType().equals(VOLUME)) {
								volBean.setFast(macdCross.getQuota1());
								volBean.setSlow(macdCross.getQuota2());

                                volPreBean.setFast(macdCross.getPreQuota1());
                                volPreBean.setSlow(macdCross.getPreQuota2());
								continue;
							}
							if (macdCross.getType().equals(MA)) {
								maBean.setMa5(macdCross.getQuota1());
								maBean.setMa10(macdCross.getQuota2());
								maBean.setMa120(macdCross.getQuota3());

                                maPreBean.setMa5(macdCross.getPreQuota1());
                                maPreBean.setMa10(macdCross.getPreQuota2());
                                maPreBean.setMa120(macdCross.getPreQuota3());
								continue;
							}
							if (macdCross.getType().equals(VR)) {
								vrBean.setVr(macdCross.getQuota1());
								vrBean.setMavr(macdCross.getQuota2());

								vrPreBean.setVr(macdCross.getPreQuota1());
								vrPreBean.setMavr(macdCross.getPreQuota2());

								continue;
							}
							if (macdCross.getType().equals(DMI)) {
								dmiBean.setPdi(macdCross.getQuota1());
								dmiBean.setMdi(macdCross.getQuota2());
								dmiBean.setAdx(macdCross.getQuota3());
								dmiBean.setAdxr(macdCross.getQuota4());

								dmiPreBean.setPdi(macdCross.getPreQuota1());
								dmiPreBean.setMdi(macdCross.getPreQuota2());
								dmiPreBean.setAdx(macdCross.getPreQuota3());
								dmiPreBean.setAdxr(macdCross.getPreQuota4());
								continue;
							}
                            if (macdCross.getType().equals(SAR)) {
                                sarBean.setSar(macdCross.getQuota1());

                                sarPreBean.setSar(macdCross.getPreQuota1());
                                continue;
                            }
						}
					}

//					if (symbol.equals("CHAT_BTC")) {
//						System.out.println("K:"+kdjBean.getK());
//						System.out.println("D:"+kdjBean.getD());
//						System.out.println("J:"+kdjBean.getJ());
//					}

					List<Analysis> analysisExistList = mapperServer.getAnalysis(exchange, period.getIntervalId(), symbol);
//                    if(tickers.getDisplayPairName().equals("ADX_BTC")) {
//                        System.out.println(tickers.getDisplayPairName());
//                    }
					if (null != analysisExistList && analysisExistList.size() > 0) {
						Analysis analysis = analysisExistList.get(0);
						if (analysis.getStatus().equals(GOLDEN_CROSS)) {
							String status = null;
							if (macdBean.getDifPre() > macdBean.getDif() && macdBean.getDifThr() > macdBean.getDifPre()) {
								status = Negative_Divergence;

							} else if ((price < maBean.getMa5() && price < maBean.getMa10())
									&& (pricePre < maBean.getMa5() && pricePre < maBean.getMa10())) {
								status = DEATH_CROSS;
							}
							if (null != status) {
								mapperServer.modAnalysis(analysisExistList, DEATH_CROSS, price,klineTime);
								mapperServer.addAnalysisHistory(exchange, symbol, period.getIntervalId(), DEATH_CROSS, price,klineTime);

								CoinInfo coinInfo = infoServer.getCoinInfo(blockccInfoClient,tickers.getCoinId());
								VolatileBean volatileBean = new VolatileBean();
								volatileBean.setType(ConstantUtils.ANALYSIS_TYPE_SELL);
								volatileBean.setStatus(status);
								volatileBean.setChange(0.0);
								this.sendCross(alertExchangeList,alertWorkWxUsers, tickers, coinInfo, period,price, volatileBean);
								this.sendCross2(alertUsers, tickers, coinInfo, period,price,ConstantUtils.ANALYSIS_TYPE_SELL,status);
							}
						} else {
							QuotaBean quotaBean = new QuotaBean();
							quotaBean.setMacdBean(macdBean);
							quotaBean.setKdjBean(kdjBean);
							quotaBean.setBollBean(bollBean);
							quotaBean.setRsiBean(rsiBean);
//							quotaBean.setDojiBean();
							quotaBean.setVolumeBean(volBean);
							quotaBean.setMaBean(maBean);
							quotaBean.setVrBean(vrBean);
							quotaBean.setDmiBean(dmiBean);
                            quotaBean.setSarBean(sarBean);
							boolean isCross = analysisServer.getCross(alertSettingList, quotaBean, price);

							QuotaBean quotaPreBean = new QuotaBean();
							quotaPreBean.setMacdBean(macdPreBean);
							quotaPreBean.setKdjBean(kdjPreBean);
							quotaPreBean.setBollBean(bollPreBean);
							quotaPreBean.setRsiBean(rsiPreBean);
//							quotaPreBean.setDojiBean();
							quotaPreBean.setVolumeBean(volPreBean);
							quotaPreBean.setMaBean(maPreBean);
							quotaPreBean.setVrBean(vrPreBean);
							quotaPreBean.setDmiBean(dmiPreBean);
                            quotaPreBean.setSarBean(sarPreBean);
//                         	 boolean isCrossPre = this.getCross(alertSettingList, quotaPreBean, pricePre);
//							if (isCross && isCrossPre) {
							if (isCross) {
								mapperServer.modAnalysis(analysisExistList, GOLDEN_CROSS, price,klineTime);
								mapperServer.addAnalysisHistory(exchange, symbol, period.getIntervalId(), GOLDEN_CROSS, price,klineTime);


								CoinInfo coinInfo = infoServer.getCoinInfo(blockccInfoClient,tickers.getCoinId());
								VolatileBean volatileBean = new VolatileBean();
								volatileBean.setType(ConstantUtils.ANALYSIS_TYPE_BUY);
								volatileBean.setStatus(GOLDEN_CROSS);
								volatileBean.setChange(0.0);
								this.sendCross(alertExchangeList,alertWorkWxUsers, tickers, coinInfo, period,price, volatileBean);
								this.sendCross2(alertUsers, tickers, coinInfo, period,price,ConstantUtils.ANALYSIS_TYPE_BUY,GOLDEN_CROSS);
							}
						}
					} else {
						mapperServer.addAnalysis(exchange, symbol, period.getIntervalId(), DEATH_CROSS, price,klineTime);
					}
				}
			}
        }

		return true;
	}

	@Override
	public boolean dojiMonitor(String exchange, CommonInterval period) {
		List<AlertExchange> alertExchangeList = mapperServer.getAlertExchange(exchange);

		if (null != alertExchangeList && alertExchangeList.size() > 0) {
			double limitVol = mapperServer.getLimitVolume();
			List<Tickers> tickersList = mapperServer.getTickers(exchange,period.getIntervalId(), alertExchangeList, limitVol);
			List<MacdCross> crossList= mapperServer.getMacdCrossAll(exchange, period.getIntervalId());
			if (null != tickersList && tickersList.size() > 0) {

				List<AlertUser> alertUsers= mapperServer.getAlertUsers();
				List<AlertUser> alertWorkWxUsers= mapperServer.getAlertWorkWxUsers();
				BlockccInfoClient blockccInfoClient = new BlockccInfoClient();

				List<AlertSetting> alertSettingList = mapperServer.getAlertSettingByPeriod(period.getIntervalId());

				for (Tickers tickers:tickersList) {

					String symbol = tickers.getDisplayPairName();

					CrossQuotaBean crossQuotaBean = this.getCrossQuotaBean(crossList, symbol);

//					if (symbol.equals("AION_BTC")) {
//						System.out.println("TUSD_USDT");
//					}

					List<Analysis> analysisExistList = mapperServer.getAnalysis(exchange, period.getIntervalId(), symbol);

					if (null != analysisExistList && analysisExistList.size() > 0) {
						Analysis analysis = analysisExistList.get(0);
						if (analysis.getStatus().equals(DOJI_UP)) {
							String status = DOJI_DOWN;
							boolean isCrossSell = analysisServer.getDojiSell(alertSettingList, crossQuotaBean.getQuotaBean(), analysis.getPrice(),crossQuotaBean.getPrice(),crossQuotaBean.getMaxHighPrice());
							if (isCrossSell) {
								mapperServer.modAnalysis(analysisExistList, status, crossQuotaBean.getPrice(),crossQuotaBean.getKlineTime());
								mapperServer.addAnalysisHistory(exchange, symbol, period.getIntervalId(), status, crossQuotaBean.getPrice(),crossQuotaBean.getKlineTime());


								CoinInfo coinInfo = infoServer.getCoinInfo(blockccInfoClient,tickers.getCoinId());
								VolatileBean volatileBean = new VolatileBean();
								volatileBean.setType(ConstantUtils.ANALYSIS_TYPE_SELL);
								volatileBean.setStatus(status);
								volatileBean.setChange(0.0);
								this.sendCross(alertExchangeList,alertWorkWxUsers, tickers, coinInfo, period,crossQuotaBean.getPrice(), volatileBean);
								this.sendCross2(alertUsers, tickers, coinInfo, period,crossQuotaBean.getPrice(),ConstantUtils.ANALYSIS_TYPE_SELL,status);
							}
						} else {

							crossQuotaBean.getQuotaPreBean().setMaBean(crossQuotaBean.getQuotaThrBean().getMaBean());
							crossQuotaBean.getQuotaPreBean().setDmiBean(crossQuotaBean.getQuotaThrBean().getDmiBean());
							crossQuotaBean.getQuotaPreBean().setDojiBean(crossQuotaBean.getQuotaThrBean().getDojiBean());

							String status = DOJI_UP;

							boolean isCross = analysisServer.getDoji(alertSettingList, crossQuotaBean.getQuotaPreBean(), crossQuotaBean.getPriceThr(), crossQuotaBean.getPricePre());
							if (isCross) {
								mapperServer.modAnalysis(analysisExistList, status, crossQuotaBean.getMinLowPrice(),crossQuotaBean.getKlineTime());
								mapperServer.addAnalysisHistory(exchange, symbol, period.getIntervalId(), status, crossQuotaBean.getMinLowPrice(),crossQuotaBean.getKlineTime());


								CoinInfo coinInfo = infoServer.getCoinInfo(blockccInfoClient,tickers.getCoinId());
								VolatileBean volatileBean = new VolatileBean();
								volatileBean.setType(ConstantUtils.ANALYSIS_TYPE_BUY);
								volatileBean.setStatus(status);
								volatileBean.setChange(0.0);
								this.sendCross(alertExchangeList,alertWorkWxUsers, tickers, coinInfo, period,crossQuotaBean.getMinLowPrice(), volatileBean);
								this.sendCross2(alertUsers, tickers, coinInfo, period,crossQuotaBean.getMinLowPrice(),ConstantUtils.ANALYSIS_TYPE_BUY,status);
							}
						}
					} else {
						mapperServer.addAnalysis(exchange, symbol, period.getIntervalId(), DOJI_DOWN, crossQuotaBean.getPrice(),crossQuotaBean.getKlineTime());
					}
				}
			}
		}

		return true;
	}

    @Override
    public boolean lowMonitor(String exchange, CommonInterval period) {
        List<AlertExchange> alertExchangeList = mapperServer.getAlertExchange(exchange);

        if (null != alertExchangeList && alertExchangeList.size() > 0) {
            double limitVol = mapperServer.getLimitVolume();
            List<Tickers> tickersList = mapperServer.getTickers(exchange,period.getIntervalId(), alertExchangeList, limitVol);
            List<MacdCross> crossList= mapperServer.getMacdCrossAll(exchange, period.getIntervalId());
            if (null != tickersList && tickersList.size() > 0) {

//                List<AlertUser> alertUsers= mapperServer.getAlertUsers();
                List<AlertUser> alertWorkWxUsers= mapperServer.getAlertWorkWxUsers();
                BlockccInfoClient blockccInfoClient = new BlockccInfoClient();

                List<AlertSetting> alertSettingList = mapperServer.getAlertSettingByPeriod(period.getIntervalId());

                for (Tickers tickers:tickersList) {

                    String symbol = tickers.getDisplayPairName();

                    List<Analysis> analysisExistList = mapperServer.getAnalysis(exchange, period.getIntervalId(), symbol);

                    CrossQuotaBean crossQuotaBean = this.getCrossQuotaBean(crossList, symbol);

                    double price = crossQuotaBean.getPrice();
                    String klineTime = crossQuotaBean.getKlineTime();

                    if (null != analysisExistList && analysisExistList.size() > 0) {
                        Analysis analysis = analysisExistList.get(0);

//                        String status = null;
//                        String type = null;

						VolatileBean volatileBean = analysisServer.getLowByRSI(alertSettingList, crossQuotaBean);
                        if (null == volatileBean.getStatus()) {
							volatileBean = analysisServer.getHighByRSI(alertSettingList, crossQuotaBean);
                            if (null == volatileBean.getStatus()) {
                                if (analysis.getStatus().equals(ConstantUtils.ANALYSIS_STATUS_LOW)) {
									volatileBean = analysisServer.getStopLimitByRSI(alertSettingList, crossQuotaBean);
                                }
                            }
                        }

                        if (null != volatileBean.getStatus() && !analysis.getStatus().equals(volatileBean.getStatus())) {
                            mapperServer.modAnalysis(analysisExistList, volatileBean.getStatus(), price,klineTime);
                            mapperServer.addAnalysisHistory(exchange, symbol, period.getIntervalId(), volatileBean.getStatus(), price,klineTime);

                            CoinInfo coinInfo = infoServer.getCoinInfo(blockccInfoClient,tickers.getCoinId());
                            this.sendCross(alertExchangeList,alertWorkWxUsers, tickers, coinInfo, period, price, volatileBean);
//                            this.sendCross2(alertUsers, tickers, coinInfo, period,price,TYPE_SELL,status);
                        }
                    } else {
                        mapperServer.addAnalysis(exchange, symbol, period.getIntervalId(), ConstantUtils.ANALYSIS_STATUS_STOP, price,klineTime);
                    }
                }
            }
        }

        return true;
    }

	@Override
	public boolean retraceMonitor(String exchange, CommonInterval period) {
		List<AlertExchange> alertExchangeList = mapperServer.getAlertExchange(exchange);

		if (null != alertExchangeList && alertExchangeList.size() > 0) {
			double limitVol = mapperServer.getLimitVolume();
			List<Tickers> tickersList = mapperServer.getTickers(exchange,period.getIntervalId(), alertExchangeList, limitVol);
			List<MacdCross> crossList= mapperServer.getMacdCrossAll(exchange, period.getIntervalId());
			if (null != tickersList && tickersList.size() > 0) {

//                List<AlertUser> alertUsers= mapperServer.getAlertUsers();
				List<AlertUser> alertWorkWxUsers= mapperServer.getAlertWorkWxUsers();
				BlockccInfoClient blockccInfoClient = new BlockccInfoClient();

				List<AlertSetting> alertSettingList = mapperServer.getAlertSettingByPeriod(period.getIntervalId());

				for (Tickers tickers:tickersList) {

					String symbol = tickers.getDisplayPairName();

					List<Analysis> analysisExistList = mapperServer.getAnalysis(exchange, period.getIntervalId(), symbol);

					CrossQuotaBean crossQuotaBean = this.getCrossQuotaBean(crossList, symbol);

					double price = crossQuotaBean.getPrice();
					String klineTime = crossQuotaBean.getKlineTime();

					if (null != analysisExistList && analysisExistList.size() > 0) {
						Analysis analysis = analysisExistList.get(0);

						String status = null;
						String type = null;

						//取得前一个rsi的状态
						String preStatus = ConstantUtils.RSI_RETRACE_STATUS_NORMAL;
						List<MacdCross> crossRsiList= mapperServer.getMacdCrossByType(exchange, symbol, RSI, period.getIntervalId());
						if (null != crossRsiList && crossRsiList.size()> 0) {
							preStatus = crossRsiList.get(0).getStatus();
						}

						VolatileBean volatileBean = analysisServer.getRetraceByRSI(alertSettingList, crossQuotaBean, preStatus);
						String newStatus = volatileBean.getStatus();
						if (newStatus.equals(ConstantUtils.RSI_RETRACE_STATUS_LOW)
								|| newStatus.equals(ConstantUtils.RSI_RETRACE_STATUS_RETRACE)) {
							if (!volatileBean.getStatus().equals(preStatus)){
//								type = TYPE_LOW;
//								status = STATUS_LOW;
								volatileBean.setStatus(ConstantUtils.ANALYSIS_STATUS_LOW);
							}
						} else {
							volatileBean = analysisServer.getHighByRSI(alertSettingList, crossQuotaBean);
							if (null == volatileBean.getStatus()) {
								if (analysis.getStatus().equals(ConstantUtils.ANALYSIS_STATUS_LOW)) {
									volatileBean = analysisServer.getStopLimitByRSI(alertSettingList, crossQuotaBean);
//									if (isStopLimit) {
//										type = TYPE_STOP;
//										status = STATUS_STOP;
//									}
								}
							}
						}

						//更新rsi状态
						if (!newStatus.equals(preStatus)){
							if (null != crossRsiList && crossRsiList.size()> 0) {
								MacdCross rsiMacdCross = crossRsiList.get(0);
								rsiMacdCross.setStatus(newStatus);
								mapperServer.modMacdCross(rsiMacdCross);
							}
						}


						if (null != status && !analysis.getStatus().equals(status)) {
							mapperServer.modAnalysis(analysisExistList, status, price,klineTime);
							mapperServer.addAnalysisHistory(exchange, symbol, period.getIntervalId(), status, price,klineTime);

							CoinInfo coinInfo = infoServer.getCoinInfo(blockccInfoClient,tickers.getCoinId());
							this.sendCross(alertExchangeList,alertWorkWxUsers, tickers, coinInfo, period, price, volatileBean);
//                            this.sendCross2(alertUsers, tickers, coinInfo, period,price,TYPE_SELL,status);
						}
					} else {
						mapperServer.addAnalysis(exchange, symbol, period.getIntervalId(), ConstantUtils.ANALYSIS_STATUS_STOP, price,klineTime);
					}
				}
			}
		}

		return true;
	}

	private CrossQuotaBean getCrossQuotaBean(List<MacdCross> crossList, String symbol) {
		KdjBean kdjBean = new KdjBean();
		MacdBean macdBean = new MacdBean();
		BollBean bollBean = new BollBean();
		RsiBean rsiBean = new RsiBean();
		VolumeBean volBean = new VolumeBean();
		MaBean maBean = new MaBean();
		VrBean vrBean = new VrBean();
		DmiBean dmiBean = new DmiBean();
		SarBean sarBean = new SarBean();
		DojiBean dojiBean = new DojiBean();

		PriceBean priceBean = new PriceBean();

		KdjBean kdjPreBean = new KdjBean();
		MacdBean macdPreBean = new MacdBean();
		BollBean bollPreBean = new BollBean();
		RsiBean rsiPreBean = new RsiBean();
		VolumeBean volPreBean = new VolumeBean();
		MaBean maPreBean = new MaBean();
		VrBean vrPreBean = new VrBean();
		DmiBean dmiPreBean = new DmiBean();
		SarBean sarPreBean = new SarBean();
		DojiBean dojiPreBean = new DojiBean();

		KdjBean kdjThrBean = new KdjBean();
		MacdBean macdThrBean = new MacdBean();
		BollBean bollThrBean = new BollBean();
		RsiBean rsiThrBean = new RsiBean();
		VolumeBean volThrBean = new VolumeBean();
		MaBean maThrBean = new MaBean();
		VrBean vrThrBean = new VrBean();
		DmiBean dmiThrBean = new DmiBean();
		SarBean sarThrBean = new SarBean();
		DojiBean dojiThrBean = new DojiBean();

		String klineTime = "";

		double price = 0;
		double pricePre = 0;
		double priceThr = 0;
		double maxHighPrice = 0;
		double minLowPrice = 0;

		for (MacdCross macdCross:crossList) {
			if (macdCross.getSymbol().equals(symbol)) {
				if (macdCross.getType().equals(KDJ)) {
					kdjBean.setK(macdCross.getQuota1());
					kdjBean.setD(macdCross.getQuota2());
					kdjBean.setJ(macdCross.getQuota3());
					kdjBean.setJPre(macdCross.getPreQuota1());

					kdjPreBean.setK(macdCross.getPreQuota1());
					kdjPreBean.setD(macdCross.getPreQuota2());
					kdjPreBean.setJ(macdCross.getPreQuota3());
					kdjPreBean.setJPre(macdCross.getThrQuota1());

					kdjThrBean.setK(macdCross.getThrQuota1());
					kdjThrBean.setD(macdCross.getThrQuota2());
					kdjThrBean.setJ(macdCross.getThrQuota3());
					kdjThrBean.setJPre(macdCross.getThrQuota1());
					continue;
				}
				if (macdCross.getType().equals(MACD)) {
					macdBean.setDif(macdCross.getQuota1());
					macdBean.setDea(macdCross.getQuota2());
					macdBean.setBar(macdCross.getQuota3());
					macdBean.setDifPre(macdCross.getPreQuota1());

					macdPreBean.setDif(macdCross.getPreQuota1());
					macdPreBean.setDea(macdCross.getPreQuota2());
					macdPreBean.setBar(macdCross.getPreQuota3());
					macdPreBean.setDifPre(macdCross.getThrQuota1());

					macdThrBean.setDif(macdCross.getThrQuota1());
					macdThrBean.setDea(macdCross.getThrQuota2());
					macdThrBean.setBar(macdCross.getThrQuota3());
					macdThrBean.setDifPre(macdCross.getThrQuota1());

					klineTime = macdCross.getKlineTime();
					price = macdCross.getPrice();
					pricePre = macdCross.getPrePrice();
					priceThr = macdCross.getThrPrice();
					maxHighPrice = macdCross.getMaxHigh();
					minLowPrice = macdCross.getMinLow();

					priceBean.setClosePre(macdCross.getPrePrice());
					continue;
				}
				if (macdCross.getType().equals(BOLL)) {
					bollBean.setBoll(macdCross.getQuota1());
					bollBean.setUb(macdCross.getQuota2());
					bollBean.setLb(macdCross.getQuota3());

					bollPreBean.setBoll(macdCross.getPreQuota1());
					bollPreBean.setUb(macdCross.getPreQuota2());
					bollPreBean.setLb(macdCross.getPreQuota3());

					bollThrBean.setBoll(macdCross.getThrQuota1());
					bollThrBean.setUb(macdCross.getThrQuota2());
					bollThrBean.setLb(macdCross.getThrQuota3());
					continue;
				}
				if (macdCross.getType().equals(RSI)) {
					rsiBean.setRsi1(macdCross.getQuota1());
					rsiBean.setRsi2(macdCross.getQuota2());
					rsiBean.setRsi3(macdCross.getQuota3());

					rsiPreBean.setRsi1(macdCross.getPreQuota1());
					rsiPreBean.setRsi2(macdCross.getPreQuota2());
					rsiPreBean.setRsi3(macdCross.getPreQuota3());

					rsiThrBean.setRsi1(macdCross.getThrQuota1());
					rsiThrBean.setRsi2(macdCross.getThrQuota2());
					rsiThrBean.setRsi3(macdCross.getThrQuota3());
					continue;
				}
				if (macdCross.getType().equals(VOLUME)) {
					volBean.setFast(macdCross.getQuota1());
					volBean.setSlow(macdCross.getQuota2());

					volPreBean.setFast(macdCross.getPreQuota1());
					volPreBean.setSlow(macdCross.getPreQuota2());

					volThrBean.setFast(macdCross.getThrQuota1());
					volThrBean.setSlow(macdCross.getThrQuota2());
					continue;
				}
				if (macdCross.getType().equals(MA)) {
					maBean.setMa5(macdCross.getQuota1());
					maBean.setMa10(macdCross.getQuota2());
					maBean.setMa120(macdCross.getQuota3());

					maPreBean.setMa5(macdCross.getPreQuota1());
					maPreBean.setMa10(macdCross.getPreQuota2());
					maPreBean.setMa120(macdCross.getPreQuota3());

					maThrBean.setMa5(macdCross.getThrQuota1());
					maThrBean.setMa10(macdCross.getThrQuota2());
					maThrBean.setMa120(macdCross.getThrQuota3());
					continue;
				}
				if (macdCross.getType().equals(VR)) {
					vrBean.setVr(macdCross.getQuota1());
					vrBean.setMavr(macdCross.getQuota2());

					vrPreBean.setVr(macdCross.getPreQuota1());
					vrPreBean.setMavr(macdCross.getPreQuota2());

					vrThrBean.setVr(macdCross.getThrQuota1());
					vrThrBean.setMavr(macdCross.getThrQuota2());

					continue;
				}
				if (macdCross.getType().equals(DMI)) {
					dmiBean.setPdi(macdCross.getQuota1());
					dmiBean.setMdi(macdCross.getQuota2());
					dmiBean.setAdx(macdCross.getQuota3());
					dmiBean.setAdxr(macdCross.getQuota4());

					dmiPreBean.setPdi(macdCross.getPreQuota1());
					dmiPreBean.setMdi(macdCross.getPreQuota2());
					dmiPreBean.setAdx(macdCross.getPreQuota3());
					dmiPreBean.setAdxr(macdCross.getPreQuota4());

					dmiThrBean.setPdi(macdCross.getThrQuota1());
					dmiThrBean.setMdi(macdCross.getThrQuota2());
					dmiThrBean.setAdx(macdCross.getThrQuota3());
					dmiThrBean.setAdxr(macdCross.getThrQuota4());
					continue;
				}
				if (macdCross.getType().equals(SAR)) {
					sarBean.setSar(macdCross.getQuota1());

					sarPreBean.setSar(macdCross.getPreQuota1());

					sarThrBean.setSar(macdCross.getThrQuota1());
					continue;
				}

				if (macdCross.getType().equals(DOJI)) {
					dojiBean.setDoji(macdCross.getQuota1());
					dojiBean.setDownCount(macdCross.getQuota2());
					dojiBean.setDownChange(macdCross.getQuota3());
					dojiBean.setHhv(macdCross.getQuota4());

					dojiPreBean.setDoji(macdCross.getPreQuota1());
					dojiPreBean.setDownCount(macdCross.getPreQuota2());
					dojiPreBean.setDownChange(macdCross.getPreQuota3());
					dojiPreBean.setHhv(macdCross.getPreQuota4());

					dojiThrBean.setDoji(macdCross.getThrQuota1());
					dojiThrBean.setDownCount(macdCross.getThrQuota2());
					dojiThrBean.setDownChange(macdCross.getThrQuota3());
					dojiThrBean.setHhv(macdCross.getThrQuota4());

					continue;
				}
			}
		}

		QuotaBean quotaBean = new QuotaBean();
		quotaBean.setMacdBean(macdBean);
		quotaBean.setKdjBean(kdjBean);
		quotaBean.setBollBean(bollBean);
		quotaBean.setRsiBean(rsiBean);
		quotaBean.setDojiBean(dojiBean);
		quotaBean.setVolumeBean(volBean);
		quotaBean.setMaBean(maBean);
		quotaBean.setVrBean(vrBean);
		quotaBean.setDmiBean(dmiBean);
		quotaBean.setSarBean(sarBean);

		quotaBean.setPriceBean(priceBean);

		QuotaBean quotaPreBean = new QuotaBean();
		quotaPreBean.setMacdBean(macdPreBean);
		quotaPreBean.setKdjBean(kdjPreBean);
		quotaPreBean.setBollBean(bollPreBean);
		quotaPreBean.setRsiBean(rsiPreBean);
		quotaPreBean.setDojiBean(dojiPreBean);
		quotaPreBean.setVolumeBean(volPreBean);
		quotaPreBean.setMaBean(maPreBean);
		quotaPreBean.setVrBean(vrPreBean);
		quotaPreBean.setDmiBean(dmiPreBean);
		quotaPreBean.setSarBean(sarPreBean);

		QuotaBean quotaThrBean = new QuotaBean();
		quotaThrBean.setMacdBean(macdThrBean);
		quotaThrBean.setKdjBean(kdjThrBean);
		quotaThrBean.setBollBean(bollThrBean);
		quotaThrBean.setRsiBean(rsiThrBean);
		quotaThrBean.setDojiBean(dojiThrBean);
		quotaThrBean.setVolumeBean(volThrBean);
		quotaThrBean.setMaBean(maThrBean);
		quotaThrBean.setVrBean(vrThrBean);
		quotaThrBean.setDmiBean(dmiThrBean);
		quotaThrBean.setSarBean(sarThrBean);

		CrossQuotaBean crossQuotaBean = new CrossQuotaBean();

		crossQuotaBean.setQuotaBean(quotaBean);
		crossQuotaBean.setQuotaPreBean(quotaPreBean);
		crossQuotaBean.setQuotaThrBean(quotaThrBean);

		crossQuotaBean.setKlineTime(klineTime);
		crossQuotaBean.setPrice(price);
		crossQuotaBean.setPricePre(pricePre);
		crossQuotaBean.setPriceThr(priceThr);
		crossQuotaBean.setMaxHighPrice(maxHighPrice);
		crossQuotaBean.setMinLowPrice(minLowPrice);

		return crossQuotaBean;
	}

    private void saveCrossData(List<Kline> candlestickList, String exchange, String symbol, String period) {

		try {
			if(null == candlestickList || candlestickList.size() < 10) {
				return;
			}

//			List<Double> priceHighList = new ArrayList<>();
//			List<Double> priceLowList = new ArrayList<>();
//
//			for (Kline candlestick: candlestickList) {
//				priceHighList.add(Double.valueOf(candlestick.getHigh()));
//				priceLowList.add(Double.valueOf(candlestick.getLow()));
//			}
//			double maxPrice = Collections.max(priceHighList);
//			double minPrice =Collections.min(priceLowList);
//
//			Kline lastCandle = candlestickList.get(candlestickList.size()-1);
//			Kline secondCandle = candlestickList.get(candlestickList.size()-2);
//
//			// 收盘价
//			double close = Double.valueOf(lastCandle.getClose());

			//MACD
			List<MacdBean> macdBeanList =  quotaUtils.getMACD(12,26,9,candlestickList);
			String statusMacd = quotaCrossUtils.getMACDCross(macdBeanList, candlestickList.size());
			MacdBean lastMacdBean = macdBeanList.get(macdBeanList.size() - 1);
			MacdBean secMacdBean = macdBeanList.get(macdBeanList.size() - 2);
			MacdBean thrMacdBean = macdBeanList.get(macdBeanList.size() - 3);
			// 保存MACD数据
			this.saveCross(exchange, symbol, period, MACD, statusMacd, candlestickList,
					lastMacdBean.getDif(),lastMacdBean.getDea(),lastMacdBean.getBar(),0,
					secMacdBean.getDif(),secMacdBean.getDea(),secMacdBean.getBar(),0,
					thrMacdBean.getDif(),thrMacdBean.getDea(),thrMacdBean.getBar(),0);

			//KDJ
			List<KdjBean> kdjBeanList = quotaUtils.getKDJ(9,3,3,candlestickList);
			String statusKdj = quotaCrossUtils.getKdjCross(kdjBeanList, candlestickList.size());
			KdjBean lastKdjBean = kdjBeanList.get(kdjBeanList.size() - 1);
			KdjBean secKdjBean = kdjBeanList.get(kdjBeanList.size() - 2);
			KdjBean thrKdjBean = kdjBeanList.get(kdjBeanList.size() - 3);
			// 保存KDJ数据
			this.saveCross(exchange, symbol, period, KDJ, statusKdj, candlestickList,
					lastKdjBean.getK(),lastKdjBean.getD(),lastKdjBean.getJ(),0,
					secKdjBean.getK(),secKdjBean.getD(),secKdjBean.getJ(),0,
					thrKdjBean.getK(),thrKdjBean.getD(),thrKdjBean.getJ(),0);

			//BOLL
			List<BollBean> bollBeanList = quotaUtils.getBoll(20,2,candlestickList);
			//TODO 布林线状态 开口 收口
			String statusBoll = "0";
			BollBean lastBollBean = bollBeanList.get(bollBeanList.size() - 1);
			BollBean secBollBean = bollBeanList.get(bollBeanList.size() - 2);
			BollBean thrBollBean = bollBeanList.get(bollBeanList.size() - 3);
			// 保存BOLL数据
			this.saveCross(exchange, symbol, period, BOLL, statusBoll, candlestickList,
					lastBollBean.getBoll(), lastBollBean.getUb(), lastBollBean.getLb(),0,
					secBollBean.getBoll(), secBollBean.getUb(), secBollBean.getLb(),0,
					thrBollBean.getBoll(), thrBollBean.getUb(), thrBollBean.getLb(),0);

			//RSI
			List<RsiBean> rsiBeanList= quotaUtils.getRsi(6,12,24,candlestickList);
//			String statusRsi = quotaCrossUtils.getRsiCross(rsiBeanList);
			String statusRsi = ConstantUtils.RSI_RETRACE_STATUS_NORMAL;
			RsiBean lastRsiBean = rsiBeanList.get(rsiBeanList.size() - 1);
			RsiBean secRsiBean = rsiBeanList.get(rsiBeanList.size() - 2);
			RsiBean thrRsiBean = rsiBeanList.get(rsiBeanList.size() - 3);
			// 保存RSI数据
			this.saveCross(exchange, symbol, period, RSI, statusRsi, candlestickList,
					lastRsiBean.getRsi1(), lastRsiBean.getRsi2(), lastRsiBean.getRsi3(),0,
					secRsiBean.getRsi1(), secRsiBean.getRsi2(), secRsiBean.getRsi3(),0,
					thrRsiBean.getRsi1(), thrRsiBean.getRsi2(), thrRsiBean.getRsi3(),0);

            //MA
            List<MaBean> maBeanList= quotaUtils.getMa(5,10,30,120,candlestickList);
            String statusMa = quotaCrossUtils.getMaCross(maBeanList);
            MaBean lastMaBean = maBeanList.get(maBeanList.size() - 1);
			MaBean secMaBean = maBeanList.get(maBeanList.size() - 2);
			MaBean thrMaBean = maBeanList.get(maBeanList.size() - 3);

            // 保存MA数据
            this.saveCross(exchange, symbol, period, MA, statusMa, candlestickList,
					lastMaBean.getMa5(), lastMaBean.getMa10(), lastMaBean.getMa120(),0,
					secMaBean.getMa5(), secMaBean.getMa10(), secMaBean.getMa120(),0,
					thrMaBean.getMa5(), thrMaBean.getMa10(), thrMaBean.getMa120(),0);

			//VR
			List<VrBean> vrBeanList= quotaUtils.getVr(26,6 ,candlestickList);
			String statusVr = "0";
			VrBean lastVrBean = vrBeanList.get(vrBeanList.size() - 1);
			VrBean secVrBean = vrBeanList.get(vrBeanList.size() - 2);
			VrBean thrVrBean = vrBeanList.get(vrBeanList.size() - 3);

			// 保存VR数据
			this.saveCross(exchange, symbol, period, VR, statusVr, candlestickList,
					lastVrBean.getVr(), lastVrBean.getMavr(), 0,0,
					secVrBean.getVr(), secVrBean.getMavr(), 0,0,
					thrVrBean.getVr(), thrVrBean.getMavr(), 0,0);

			//DMI
			List<DmiBean> dmiBeanList= quotaUtils.getDmi(14,6 ,candlestickList);
			String statusDmi = "0";
			DmiBean lastDmiBean = dmiBeanList.get(dmiBeanList.size() - 1);
			DmiBean secDmiBean = dmiBeanList.get(dmiBeanList.size() - 2);
			DmiBean thrDmiBean = dmiBeanList.get(dmiBeanList.size() - 3);

			// 保存DMI数据
			this.saveCross(exchange, symbol, period, DMI, statusDmi, candlestickList,
					lastDmiBean.getPdi(), lastDmiBean.getMdi(), lastDmiBean.getAdx(),lastDmiBean.getAdxr(),
					secDmiBean.getPdi(), secDmiBean.getMdi(), secDmiBean.getAdx(),secDmiBean.getAdxr(),
					thrDmiBean.getPdi(), thrDmiBean.getMdi(), thrDmiBean.getAdx(),thrDmiBean.getAdxr());

            //SAR
            List<SarBean> sarBeanList= quotaUtils.getSar(candlestickList);
            String statusSar = "0";
            SarBean lastSarBean = sarBeanList.get(sarBeanList.size() - 1);
            SarBean secSarBean = sarBeanList.get(sarBeanList.size() - 2);
            SarBean thrSarBean = sarBeanList.get(sarBeanList.size() - 3);

            // 保存SAR数据
            this.saveCross(exchange, symbol, period, SAR, statusSar, candlestickList,
                    lastSarBean.getSar(), 0, 0,0,
                    secSarBean.getSar(), 0, 0,0,
                    thrSarBean.getSar(), 0, 0,0);

			//DOJI十字星
			List<DojiBean> dojiBeanList = quotaUtils.getDoji(candlestickList,bollBeanList);
			String statusDoji = quotaCrossUtils.getDojiCross(dojiBeanList, candlestickList);
			DojiBean lastDojiBean = dojiBeanList.get(kdjBeanList.size() - 1);
			DojiBean secDojiBean = dojiBeanList.get(kdjBeanList.size() - 2);
			DojiBean thrDojiBean = dojiBeanList.get(kdjBeanList.size() - 3);
			// 保存DOJI数据
			this.saveCross(exchange, symbol, period, DOJI, statusDoji, candlestickList,
					lastDojiBean.getDoji(), lastDojiBean.getDownCount(),lastDojiBean.getDownChange(), lastDojiBean.getHhv(),
					secDojiBean.getDoji(), secDojiBean.getDownCount(),secDojiBean.getDownChange(), secDojiBean.getHhv(),
					thrDojiBean.getDoji(), thrDojiBean.getDownCount(),thrDojiBean.getDownChange(), thrDojiBean.getHhv());

			//成交量
			List<VolumeBean> volBeanList = quotaUtils.getVolume(5,10,candlestickList);
			String statusVol = quotaCrossUtils.getVolumeCross(volBeanList, candlestickList.size());
			VolumeBean lastVolBean = volBeanList.get(volBeanList.size() - 1);
			VolumeBean secVolBean = volBeanList.get(volBeanList.size() - 2);
			VolumeBean thrVolBean = volBeanList.get(volBeanList.size() - 3);
			// 保存VOLUME数据
			this.saveCross(exchange, symbol, period, VOLUME, statusVol, candlestickList,
					lastVolBean.getFast(), lastVolBean.getSlow(), lastVolBean.getVol(),0,
					secVolBean.getFast(), secVolBean.getSlow(), secVolBean.getVol(),0,
					thrVolBean.getFast(), thrVolBean.getSlow(), thrVolBean.getVol(),0);
		}
		catch (Exception e) {
			log.error("线程 "+exchange+" ["+symbol+"] 指标交叉 "+period+":"+Thread.currentThread().getName()+"异常终止....." + e.getMessage());
			log.error(e.getMessage(),e);
		}
	}

	/**
	 * 保存交叉指标数据
	 * @param exchange 交易所
	 * @param symbol 币种
	 * @param period k线周期
	 * @param type 指标类型
	 * @param status 指标状态
	 * @param candlestickList k线数据
	 */
	private void saveCross (String exchange, String symbol, String period, String type, String status,
							List<Kline> candlestickList, double quota1, double quota2, double quota3,double quota4,
							double preQuota1, double preQuota2, double preQuota3,double preQuota4,
							double thrQuota1, double thrQuota2, double thrQuota3, double thrQuota4) {
			List<MacdCross> existList = mapperServer.getMacdCross(exchange, symbol, period, type);
			if (existList.size() > 0) {
				MacdCross macdCross = existList.get(0);
//				macdCross.setStatus(status);

				macdCross.setQuota1(quota1);
				macdCross.setQuota2(quota2);
				macdCross.setQuota3(quota3);
				macdCross.setQuota4(quota4);
				macdCross.setPreQuota1(preQuota1);
				macdCross.setPreQuota2(preQuota2);
				macdCross.setPreQuota3(preQuota3);
				macdCross.setPreQuota4(preQuota4);
				macdCross.setThrQuota1(thrQuota1);
				macdCross.setThrQuota2(thrQuota2);
				macdCross.setThrQuota3(thrQuota3);
				macdCross.setThrQuota4(thrQuota4);
				macdCross.setPrice(candlestickList.get(candlestickList.size()-1).getClose());
				macdCross.setPrePrice(candlestickList.get(candlestickList.size()-2).getClose());
				macdCross.setThrPrice(candlestickList.get(candlestickList.size()-3).getClose());
//				macdCross.setThrLow(candlestickList.get(candlestickList.size()-3).getLow());
//				macdCross.setHigh(candlestickList.get(candlestickList.size()-1).getHigh());
				macdCross.setMinLow(ParseUtils.getMinLowPriceByLastCandles(candlestickList,3));
				macdCross.setMaxHigh(ParseUtils.getMaxHighPriceByLastCandles(candlestickList, 3));
				macdCross.setKlineTime(DateUtils.format(candlestickList.get(candlestickList.size()-1).getTime()));
				macdCross.setUpdateTime(new Date());
				if(!((existList.get(0).getStatus().equals("2") && status.equals("2"))
						||(existList.get(0).getStatus().equals("4") && status.equals("4")))) {
					mapperServer.modMacdCross(macdCross);
				}
			} else {
				MacdCross macdCross = new MacdCross();
				macdCross.setExchange(exchange);
				macdCross.setSymbol(symbol);
				macdCross.setPeriod(period);
				macdCross.setType(type);
				macdCross.setStatus(status);

				macdCross.setQuota1(quota1);
				macdCross.setQuota2(quota2);
				macdCross.setQuota3(quota3);
				macdCross.setQuota4(quota4);
				macdCross.setPreQuota1(preQuota1);
				macdCross.setPreQuota2(preQuota2);
				macdCross.setPreQuota3(preQuota3);
				macdCross.setPreQuota4(preQuota4);
				macdCross.setThrQuota1(thrQuota1);
				macdCross.setThrQuota2(thrQuota2);
				macdCross.setThrQuota3(thrQuota3);
				macdCross.setThrQuota4(thrQuota4);
				macdCross.setPrice(candlestickList.get(candlestickList.size()-1).getClose());
				macdCross.setPrePrice(candlestickList.get(candlestickList.size()-2).getClose());
				macdCross.setThrPrice(candlestickList.get(candlestickList.size()-3).getClose());
//				macdCross.setThrLow(candlestickList.get(candlestickList.size()-3).getLow());
//				macdCross.setHigh(candlestickList.get(candlestickList.size()-1).getHigh());
				macdCross.setMinLow(ParseUtils.getMinLowPriceByLastCandles(candlestickList,3));
				macdCross.setMaxHigh(ParseUtils.getMaxHighPriceByLastCandles(candlestickList, 3));
				macdCross.setKlineTime(DateUtils.format(candlestickList.get(candlestickList.size()-1).getTime()));
				macdCross.setUpdateTime(new Date());
				mapperServer.addMacdCross(macdCross);
			}
	}

	/**
	 * 指标交叉微信提醒
	 */
	private void sendCross2(List<AlertUser> alertUsers, Tickers tickers, CoinInfo coinInfo, CommonInterval period,double price,String type,String status){
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
	 * 指标交叉微信提醒
	 */
	private void sendCross(List<AlertExchange> alertExchangeList,List<AlertUser> alertUsers, Tickers tickers, CoinInfo coinInfo, CommonInterval period,double price, VolatileBean volatileBean){

		try {
		String mobile = "";
		for (int i = 0; i < alertUsers.size(); i++) {
			if (i == 0) {
				mobile = alertUsers.get(i).getMobile();
			} else {
				mobile += "/" + alertUsers.get(i).getMobile();
			}
		}

		String now = DateUtils.getToday();
		String title = "【"+tickers.getDisplayPairName()+"】价格["+price+"]｜"+ParseUtils.parseCrossStatus(volatileBean.getStatus())+"｜最值:"+volatileBean.getMaxPrice()+ "｜"+ParseUtils.normalDecimalFormat(volatileBean.getChange()) + "%｜"+period.getIntervalId()+"｜"+ParseUtils.parseCrossType(volatileBean.getType());

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
		String secretId = WxSendMessageUtil.SECRETID_BINANCE_BUY;
		String agentId = WxSendMessageUtil.AGENTID_BINANCE_BUY;
		if (ConstantUtils.ANALYSIS_TYPE_BUY.equals(volatileBean.getType())) {
			if ("huobipro".equals(tickers.getExchangeName()) || "hadax".equals(tickers.getExchangeName())) {
				secretId = WxSendMessageUtil.SECRETID_HUOBI_BUY;
				agentId = WxSendMessageUtil.AGENTID_HUOBI_BUY;
			}else if ("gate".equals(tickers.getExchangeName())) {
				secretId = WxSendMessageUtil.SECRETID_GATE_BUY;
				agentId = WxSendMessageUtil.AGENTID_GATE_BUY;
			} else if ("bittrex".equals(tickers.getExchangeName())) {
				secretId = WxSendMessageUtil.SECRETID_BITTREX_BUY;
				agentId = WxSendMessageUtil.AGENTID_BITTREX_BUY;
			} else if ("okex".equals(tickers.getExchangeName())) {
				secretId = WxSendMessageUtil.SECRETID_OKEX_BUY;
				agentId = WxSendMessageUtil.AGENTID_OKEX_BUY;
			}
		} else if (ConstantUtils.ANALYSIS_TYPE_SELL.equals(volatileBean.getType())){
			if ("binance".equals(tickers.getExchangeName())) {
				secretId = WxSendMessageUtil.SECRETID_BINANCE_SELL;
				agentId = WxSendMessageUtil.AGENTID_BINANCE_SELL;
			} else if ("huobipro".equals(tickers.getExchangeName()) || "hadax".equals(tickers.getExchangeName())) {
				secretId = WxSendMessageUtil.SECRETID_HUOBI_SELL;
				agentId = WxSendMessageUtil.AGENTID_HUOBI_SELL;
			} else if ("gate".equals(tickers.getExchangeName())) {
				secretId = WxSendMessageUtil.SECRETID_GATE_SELL;
				agentId = WxSendMessageUtil.AGENTID_GATE_SELL;
			} else if ("bittrex".equals(tickers.getExchangeName())) {
				secretId = WxSendMessageUtil.SECRETID_BITTREX_SELL;
				agentId = WxSendMessageUtil.AGENTID_BITTREX_SELL;
			} else if ("okex".equals(tickers.getExchangeName())) {
				secretId = WxSendMessageUtil.SECRETID_OKEX_SELL;
				agentId = WxSendMessageUtil.AGENTID_OKEX_SELL;
			}
		} else if (ConstantUtils.ANALYSIS_TYPE_LOW.equals(volatileBean.getType())){
            if ("binance".equals(tickers.getExchangeName())) {
                secretId = WxSendMessageUtil.SECRETID_BINANCE_LOW;
                agentId = WxSendMessageUtil.AGENTID_BINANCE_LOW;
            } else if ("huobipro".equals(tickers.getExchangeName()) || "hadax".equals(tickers.getExchangeName())) {
                secretId = WxSendMessageUtil.SECRETID_HUOBI_BUY;
                agentId = WxSendMessageUtil.AGENTID_HUOBI_BUY;
            } else if ("gate".equals(tickers.getExchangeName())) {
                secretId = WxSendMessageUtil.SECRETID_GATE_BUY;
                agentId = WxSendMessageUtil.AGENTID_GATE_BUY;
            } else if ("bittrex".equals(tickers.getExchangeName())) {
                secretId = WxSendMessageUtil.SECRETID_BITTREX_BUY;
                agentId = WxSendMessageUtil.AGENTID_BITTREX_BUY;
            } else if ("okex".equals(tickers.getExchangeName())) {
                secretId = WxSendMessageUtil.SECRETID_OKEX_BUY;
                agentId = WxSendMessageUtil.AGENTID_OKEX_BUY;
            }
        } else if (ConstantUtils.ANALYSIS_TYPE_HIGH.equals(volatileBean.getType())){
            if ("binance".equals(tickers.getExchangeName())) {
                secretId = WxSendMessageUtil.SECRETID_BINANCE_HIGH;
                agentId = WxSendMessageUtil.AGENTID_BINANCE_HIGH;
            } else if ("huobipro".equals(tickers.getExchangeName()) || "hadax".equals(tickers.getExchangeName())) {
                secretId = WxSendMessageUtil.SECRETID_HUOBI_SELL;
                agentId = WxSendMessageUtil.AGENTID_HUOBI_SELL;
            } else if ("gate".equals(tickers.getExchangeName())) {
                secretId = WxSendMessageUtil.SECRETID_GATE_SELL;
                agentId = WxSendMessageUtil.AGENTID_GATE_SELL;
            } else if ("bittrex".equals(tickers.getExchangeName())) {
                secretId = WxSendMessageUtil.SECRETID_BITTREX_SELL;
                agentId = WxSendMessageUtil.AGENTID_BITTREX_SELL;
            } else if ("okex".equals(tickers.getExchangeName())) {
                secretId = WxSendMessageUtil.SECRETID_OKEX_SELL;
                agentId = WxSendMessageUtil.AGENTID_OKEX_SELL;
            }
        } else if (ConstantUtils.ANALYSIS_TYPE_STOP.equals(volatileBean.getType())){
            if ("binance".equals(tickers.getExchangeName())) {
                secretId = WxSendMessageUtil.SECRETID_BINANCE_STOP;
                agentId = WxSendMessageUtil.AGENTID_BINANCE_STOP;
            } else if ("huobipro".equals(tickers.getExchangeName()) || "hadax".equals(tickers.getExchangeName())) {
                secretId = WxSendMessageUtil.SECRETID_HUOBI_SELL;
                agentId = WxSendMessageUtil.AGENTID_HUOBI_SELL;
            } else if ("gate".equals(tickers.getExchangeName())) {
                secretId = WxSendMessageUtil.SECRETID_GATE_SELL;
                agentId = WxSendMessageUtil.AGENTID_GATE_SELL;
            } else if ("bittrex".equals(tickers.getExchangeName())) {
                secretId = WxSendMessageUtil.SECRETID_BITTREX_SELL;
                agentId = WxSendMessageUtil.AGENTID_BITTREX_SELL;
            } else if ("okex".equals(tickers.getExchangeName())) {
                secretId = WxSendMessageUtil.SECRETID_OKEX_SELL;
                agentId = WxSendMessageUtil.AGENTID_OKEX_SELL;
            }
        }

            wxSendMessageUtil.sendMessageCard(title, desp.toString(), url, secretId, agentId, mobile);
		} catch (Exception e) {
			log.error("线程 发送提醒信息出错 :["+Thread.currentThread().getName()+"]marketName：" +tickers.getExchangeName()+ ",symbol:" +tickers.getDisplayPairName());
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
	}






}
