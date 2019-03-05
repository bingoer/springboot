package wang.raye.springboot.server.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import wang.raye.springboot.bean.*;
import wang.raye.springboot.model.AlertSetting;
import wang.raye.springboot.model.MacdCross;
import wang.raye.springboot.server.AnalysisServer;
import wang.raye.springboot.server.InfoServer;
import wang.raye.springboot.server.MapperServer;
import wang.raye.springboot.utils.ConstantUtils;
import wang.raye.springboot.utils.QuotaCrossUtils;
import wang.raye.springboot.utils.QuotaUtils;
import wang.raye.springboot.utils.WxSendUtils;
import wang.raye.springboot.utils.send.WxSendMessageUtil;

import java.util.List;

/**
 * 指标分析操作实现类
 * @author Raye
 * @since 2016年10月11日19:29:02
 */
@Repository
public class AnalysisServerImpl implements AnalysisServer {

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

	@Override
	public VolatileBean getLowByRSI(List<AlertSetting> alertSettingList, CrossQuotaBean crossQuotaBean) {
		VolatileBean volatileBean = new VolatileBean();
		volatileBean.setType(ConstantUtils.ANALYSIS_TYPE_LOW);
		volatileBean.setStatus(ConstantUtils.ANALYSIS_STATUS_LOW);
		double change = crossQuotaBean.getPrice() / crossQuotaBean.getMinLowPrice() - 1;
		volatileBean.setChange(change);
//		boolean result = true;
		volatileBean.setMaxPrice(crossQuotaBean.getMinLowPrice());
		if (null != alertSettingList && alertSettingList.size() > 0) {
			RatioBean ratioBean = this.getRatioBean(alertSettingList);

			if (null == ratioBean) {
				return volatileBean;
			}

			if (ratioBean.isRsiOpen()) {
				if (crossQuotaBean.getQuotaPreBean().getRsiBean().getRsi1() > ratioBean.getRsiLow()
						&& crossQuotaBean.getQuotaThrBean().getRsiBean().getRsi1()+1 < ratioBean.getRsiLow()
//						&& crossQuotaBean.getQuotaBean().getRsiBean().getRsi1() > crossQuotaBean.getQuotaPreBean().getRsiBean().getRsi1()
						) {
//                    isCross = true;
				} else {
//					result = false;
					volatileBean.setStatus(null);
				}
			}
			if (ratioBean.isKdjOpen()) {
				boolean kdjFlg = false;
				if (crossQuotaBean.getQuotaPreBean().getKdjBean().getJ()+1 > crossQuotaBean.getQuotaPreBean().getKdjBean().getK()
						&& crossQuotaBean.getQuotaPreBean().getKdjBean().getK()+1 > crossQuotaBean.getQuotaPreBean().getKdjBean().getD()
						&& crossQuotaBean.getQuotaPreBean().getKdjBean().getJ() < ratioBean.getKdjHigh()
						&& crossQuotaBean.getQuotaThrBean().getKdjBean().getJ() < crossQuotaBean.getQuotaPreBean().getKdjBean().getJ()) {
					kdjFlg = true;
				}

				if ((crossQuotaBean.getQuotaBean().getKdjBean().getJ() > ratioBean.getKdjLow()
						|| crossQuotaBean.getQuotaPreBean().getKdjBean().getJ() > ratioBean.getKdjLow())
						&& crossQuotaBean.getQuotaThrBean().getKdjBean().getJ() < ratioBean.getKdjLow()
						) {
					kdjFlg = true;
				}
				if(!kdjFlg){
//					result = false;
					volatileBean.setStatus(null);
				}
			}

			if (ratioBean.isMaOpen()) {
				if (ratioBean.getMa5() == 1) {
					if (crossQuotaBean.getQuotaPreBean().getMaBean().getMa5() < crossQuotaBean.getQuotaPreBean().getMaBean().getMa10()) {
//                        isCross = true;
					} else {
//						result = false;
						volatileBean.setStatus(null);
					}
				}
				if (ratioBean.getMa10() == 1) {
					if (crossQuotaBean.getPricePre() < crossQuotaBean.getQuotaPreBean().getMaBean().getMa10()) {
//                        isCross = true;
					} else {
//						result = false;
						volatileBean.setStatus(null);
					}
				}
			}
		}
		return volatileBean;
	}

	@Override
	public VolatileBean getHighByRSI(List<AlertSetting> alertSettingList, CrossQuotaBean crossQuotaBean) {
		VolatileBean volatileBean = new VolatileBean();
		volatileBean.setType(ConstantUtils.ANALYSIS_TYPE_HIGH);
		volatileBean.setStatus(ConstantUtils.ANALYSIS_STATUS_HIGH);
		double change = crossQuotaBean.getPrice() / crossQuotaBean.getMaxHighPrice() - 1;
		volatileBean.setChange(change);
//		boolean result = true;
		volatileBean.setMaxPrice(crossQuotaBean.getMaxHighPrice());
		if (null != alertSettingList && alertSettingList.size() > 0) {
			RatioBean ratioBean = this.getRatioBean(alertSettingList);

			if (null == ratioBean) {
				volatileBean.setStatus(null);
				return volatileBean;
			}

			if (ratioBean.isRsiOpen()) {
				if (crossQuotaBean.getQuotaBean().getRsiBean().getRsi1() < ratioBean.getRsiHigh()
//						&& crossQuotaBean.getQuotaBean().getRsiBean().getRsi2() > ratioBean.getRsiLow()
						&& crossQuotaBean.getQuotaPreBean().getRsiBean().getRsi1() > ratioBean.getRsiHigh()
//						&& crossQuotaBean.getQuotaPreBean().getRsiBean().getRsi2() < ratioBean.getRsiLow()
						) {
//                    isCross = true;
				} else {
//					result = false;
					volatileBean.setStatus(null);
				}
			}

			boolean macdFlg = true;
			if (ratioBean.isMacdOpen()) {
				if (crossQuotaBean.getQuotaBean().getMacdBean().getDif() > crossQuotaBean.getQuotaBean().getMacdBean().getDea()
						&& crossQuotaBean.getQuotaPreBean().getMacdBean().getDif() > crossQuotaBean.getQuotaBean().getMacdBean().getDif()
						&& crossQuotaBean.getQuotaPreBean().getMacdBean().getBar() > crossQuotaBean.getQuotaBean().getMacdBean().getBar()) {
//                    isCross = true;
				} else {
					macdFlg = false;
				}
			}

			boolean kdjFlg = true;
			if (ratioBean.isKdjOpen()) {
				if ((crossQuotaBean.getQuotaBean().getKdjBean().getD() > crossQuotaBean.getQuotaBean().getKdjBean().getK()
						&& crossQuotaBean.getQuotaPreBean().getKdjBean().getJ() > crossQuotaBean.getQuotaBean().getKdjBean().getJ())
						||
						(crossQuotaBean.getQuotaBean().getKdjBean().getJ() < ratioBean.getKdjHigh()
								&& crossQuotaBean.getQuotaPreBean().getKdjBean().getJ() > ratioBean.getKdjHigh())
						) {
//                    isCross = true;
				} else {
					kdjFlg = false;
				}
			}

			if (null != volatileBean.getStatus()) {
				if (macdFlg || kdjFlg) {
//					result = true;
				} else {
//					result = false;
					volatileBean.setStatus(null);
				}
			}

//			if (ratioBean.isDmiOpen()) {
//				if (quotaBean.getDmiBean().getPdi() < 20
//						&& quotaBean.getDmiBean().getAdxPre() > 40
//						&& quotaBean.getDmiBean().getAdxPre() > quotaBean.getDmiBean().getAdx()
//						&& quotaBean.getDmiBean().getAdxr()-1 > quotaBean.getDmiBean().getAdx()) {
////                    isCross = true;
//				} else {
//					result = false;
//				}
//			}
		}
		return volatileBean;
	}

	@Override
	public VolatileBean getStopLimitByRSI(List<AlertSetting> alertSettingList, CrossQuotaBean crossQuotaBean) {
		VolatileBean volatileBean = new VolatileBean();
		volatileBean.setType(ConstantUtils.ANALYSIS_TYPE_STOP);
//		volatileBean.setStatus(ConstantUtils.ANALYSIS_STATUS_STOP);
		double change = crossQuotaBean.getPrice() / crossQuotaBean.getMaxHighPrice() - 1;
		volatileBean.setChange(change);
//		boolean result = false;
		volatileBean.setMaxPrice(crossQuotaBean.getMaxHighPrice());
		if (null != alertSettingList && alertSettingList.size() > 0) {
			RatioBean ratioBean = this.getRatioBean(alertSettingList);

			if (null == ratioBean) {
				volatileBean.setStatus(null);
				return volatileBean;
			}

			// rsi重新回到低位区间，也就是低于30
			if (ratioBean.isRsiOpen()) {
				if (crossQuotaBean.getQuotaBean().getRsiBean().getRsi1() < ratioBean.getRsiLow()
						&& crossQuotaBean.getQuotaPreBean().getRsiBean().getRsi1() < ratioBean.getRsiLow()
						&& crossQuotaBean.getQuotaThrBean().getRsiBean().getRsi1() > ratioBean.getRsiLow()
						) {
//					result = true;
					volatileBean.setStatus(ConstantUtils.ANALYSIS_STATUS_STOP);
				}
			}

//			if (ratioBean.isRsiOpen() && ratioBean.isKdjOpen()) {
//				if ((crossQuotaBean.getQuotaBean().getRsiBean().getRsi1() < ratioBean.getRsiMid()
//						&& crossQuotaBean.getQuotaPreBean().getRsiBean().getRsi1() < ratioBean.getRsiMid()
//						&& crossQuotaBean.getQuotaThrBean().getRsiBean().getRsi1() > ratioBean.getRsiMid())
//						&&
//						(crossQuotaBean.getQuotaBean().getKdjBean().getJ() < ratioBean.getKdjMid()
//								&& crossQuotaBean.getQuotaPreBean().getKdjBean().getJ() < ratioBean.getKdjMid()
//								&& crossQuotaBean.getQuotaThrBean().getKdjBean().getJ() > ratioBean.getKdjMid())
//						) {
//					result = true;
//				}
//			}

			if (ratioBean.isRsiOpen() && ratioBean.isKdjOpen()) {
				if (crossQuotaBean.getQuotaPreBean().getKdjBean().getJ() > ratioBean.getKdjMid()
						&& crossQuotaBean.getQuotaPreBean().getRsiBean().getRsi1() > ratioBean.getRsiMid()) {
					if (crossQuotaBean.getQuotaPreBean().getRsiBean().getRsi1() > crossQuotaBean.getQuotaBean().getRsiBean().getRsi1()
							&& crossQuotaBean.getQuotaPreBean().getKdjBean().getJ() > crossQuotaBean.getQuotaBean().getKdjBean().getJ()
							) {
//						result = true;
						volatileBean.setStatus(ConstantUtils.ANALYSIS_STATUS_STOP);
					}
				}
			}

		}
		return volatileBean;
	}

	@Override
	public boolean getDoubleByRSI(List<AlertSetting> alertSettingList, CrossQuotaBean crossQuotaBean) {
		boolean result = false;
		if (null != alertSettingList && alertSettingList.size() > 0) {
			RatioBean ratioBean = this.getRatioBean(alertSettingList);

			if (null == ratioBean) {
				return false;
			}

			// rsi重新回到低位区间，也就是低于30
			if (ratioBean.isRsiOpen()) {
				if (crossQuotaBean.getQuotaBean().getRsiBean().getRsi1() < ratioBean.getRsiLow()
						&& crossQuotaBean.getQuotaPreBean().getRsiBean().getRsi1() < ratioBean.getRsiLow()
						&& crossQuotaBean.getQuotaThrBean().getRsiBean().getRsi1() > ratioBean.getRsiLow()
						) {
					result = true;
				}
			}

//			if (ratioBean.isRsiOpen() && ratioBean.isKdjOpen()) {
//				if ((crossQuotaBean.getQuotaBean().getRsiBean().getRsi1() < ratioBean.getRsiMid()
//						&& crossQuotaBean.getQuotaPreBean().getRsiBean().getRsi1() < ratioBean.getRsiMid()
//						&& crossQuotaBean.getQuotaThrBean().getRsiBean().getRsi1() > ratioBean.getRsiMid())
//						&&
//						(crossQuotaBean.getQuotaBean().getKdjBean().getJ() < ratioBean.getKdjMid()
//								&& crossQuotaBean.getQuotaPreBean().getKdjBean().getJ() < ratioBean.getKdjMid()
//								&& crossQuotaBean.getQuotaThrBean().getKdjBean().getJ() > ratioBean.getKdjMid())
//						) {
//					result = true;
//				}
//			}

			if (ratioBean.isRsiOpen() && ratioBean.isKdjOpen()) {
				if (crossQuotaBean.getQuotaPreBean().getKdjBean().getJ() > ratioBean.getKdjMid()
						&& crossQuotaBean.getQuotaPreBean().getRsiBean().getRsi1() > ratioBean.getRsiMid()) {
					if (crossQuotaBean.getQuotaPreBean().getRsiBean().getRsi1() > crossQuotaBean.getQuotaBean().getRsiBean().getRsi1()
							&& crossQuotaBean.getQuotaPreBean().getKdjBean().getJ() > crossQuotaBean.getQuotaBean().getKdjBean().getJ()
							) {
						result = true;
					}
				}
			}

		}
		return result;
	}

	@Override
	public VolatileBean getRetraceByRSI(List<AlertSetting> alertSettingList, CrossQuotaBean crossQuotaBean, String preStatus) {

		VolatileBean volatileBean = new VolatileBean();
		volatileBean.setType(ConstantUtils.ANALYSIS_TYPE_LOW);
		volatileBean.setStatus(preStatus);
		double change = crossQuotaBean.getPrice() / crossQuotaBean.getMinLowPrice() - 1;
		volatileBean.setChange(change);
		volatileBean.setMaxPrice(crossQuotaBean.getMinLowPrice());
//		String result = preStatus;

		if (null != alertSettingList && alertSettingList.size() > 0) {
			RatioBean ratioBean = this.getRatioBean(alertSettingList);

			if (null == ratioBean) {
				return volatileBean;
			}

			if (ratioBean.isRsiOpen()) {
				if (ConstantUtils.RSI_RETRACE_STATUS_NORMAL.equals(preStatus)) {
					boolean rsiFlg = false;
					boolean kdjFlg = false;
					if (crossQuotaBean.getQuotaBean().getRsiBean().getRsi1() > ratioBean.getRsiLow()
							&& crossQuotaBean.getQuotaPreBean().getRsiBean().getRsi1() > ratioBean.getRsiLow()
							&& crossQuotaBean.getQuotaThrBean().getRsiBean().getRsi1() < ratioBean.getRsiLow()
							) {
						rsiFlg = true;

					}
					if (ratioBean.isKdjOpen()) {
						if (crossQuotaBean.getQuotaBean().getKdjBean().getJ()+1 > crossQuotaBean.getQuotaBean().getKdjBean().getK()
								&& crossQuotaBean.getQuotaBean().getKdjBean().getK()+1 > crossQuotaBean.getQuotaBean().getKdjBean().getD()
//								&& crossQuotaBean.getQuotaBean().getKdjBean().getJ() < ratioBean.getKdjHigh()
//								&& crossQuotaBean.getQuotaPreBean().getKdjBean().getJ() < crossQuotaBean.getQuotaBean().getKdjBean().getJ()
								) {
							kdjFlg = true;
						}
						if (crossQuotaBean.getQuotaPreBean().getKdjBean().getJ() > crossQuotaBean.getQuotaThrBean().getKdjBean().getJ()
								) {
							kdjFlg = true;
						}
					}
					if(rsiFlg && kdjFlg){
//						result = ConstantUtils.RSI_RETRACE_STATUS_LOW;
						volatileBean.setStatus(ConstantUtils.RSI_RETRACE_STATUS_LOW);
					}
				} else {
					if (ConstantUtils.RSI_RETRACE_STATUS_LOW.equals(preStatus)) {
						if (crossQuotaBean.getQuotaPreBean().getRsiBean().getRsi1() > ratioBean.getRsiMid()
								&& crossQuotaBean.getQuotaThrBean().getRsiBean().getRsi1() < ratioBean.getRsiMid()
								) {
//							result = ConstantUtils.RSI_RETRACE_STATUS_UP;
							volatileBean.setStatus(ConstantUtils.RSI_RETRACE_STATUS_UP);
						}
					} else {
						if (ConstantUtils.RSI_RETRACE_STATUS_UP.equals(preStatus)
								|| ConstantUtils.RSI_RETRACE_STATUS_RETRACE.equals(preStatus)) {
							if (ratioBean.isKdjOpen()) {
								if ((crossQuotaBean.getQuotaPreBean().getRsiBean().getRsi1() < ratioBean.getRsiMid()
										&& crossQuotaBean.getQuotaThrBean().getRsiBean().getRsi1() > ratioBean.getRsiMid())
									&& crossQuotaBean.getQuotaPreBean().getKdjBean().getJ() < crossQuotaBean.getQuotaThrBean().getKdjBean().getJ()+1
										) {
//									result = ConstantUtils.RSI_RETRACE_STATUS_BACK;
									volatileBean.setStatus(ConstantUtils.RSI_RETRACE_STATUS_BACK);
								}
							}
						} else {
					//	if (!retraceBean.isRetrace()) {
							if ((crossQuotaBean.getQuotaBean().getRsiBean().getRsi1() > ratioBean.getRsiMid()
									&& crossQuotaBean.getQuotaPreBean().getRsiBean().getRsi1() > ratioBean.getRsiMid()
									&& crossQuotaBean.getQuotaThrBean().getRsiBean().getRsi1() < ratioBean.getRsiMid())
									&&
									(crossQuotaBean.getQuotaBean().getKdjBean().getJ() > crossQuotaBean.getQuotaBean().getKdjBean().getK()
											&& crossQuotaBean.getQuotaBean().getKdjBean().getK() > crossQuotaBean.getQuotaBean().getKdjBean().getD()
											)
									) {
//								result = ConstantUtils.RSI_RETRACE_STATUS_RETRACE;
								volatileBean.setStatus(ConstantUtils.RSI_RETRACE_STATUS_RETRACE);
							}
//						}
						}
					}
				}
			}

			if (crossQuotaBean.getQuotaPreBean().getRsiBean().getRsi1() < ratioBean.getRsiLow()) {
				if (crossQuotaBean.getQuotaPreBean().getRsiBean().getRsi1() < ratioBean.getRsiLow()-5
						|| crossQuotaBean.getQuotaBean().getRsiBean().getRsi1() < ratioBean.getRsiLow()) {
//					result = ConstantUtils.RSI_RETRACE_STATUS_NORMAL;
					volatileBean.setStatus(ConstantUtils.RSI_RETRACE_STATUS_NORMAL);
				}
			}
		}
		return volatileBean;
	}


	private RatioBean getRatioBean(List<AlertSetting> alertSettingList) {

		RatioBean ratioBean = new RatioBean();

		double kdjHigh = 95;
		double kdjLow = 20;
		double kdjMid = 50;
		double macdZero = 0;
		double rsiChaomai = 80;
		double rsiLow = 30;
		double rsiHigh = 70;
		double rsiMid = 50;
		double volChange = 0.05;
		double ma5 = 1;
		double ma10 = 1;
		double ma30 = 1;
		double ma120 = 1;

        double pdiUpRatio = 0;
        double adxUpRatio = 0;

		boolean kdjOpen = false;
		boolean macdOpen = false;
		boolean bollOpen = false;
		boolean rsiOpen = false;
		boolean dojiOpen = false;
		boolean volumeOpen = false;
		boolean maOpen = false;
		boolean vrOpen = false;
		boolean dmiOpen = false;
        boolean sarOpen = false;

		for (AlertSetting alertSetting:alertSettingList) {
			if(alertSetting.getType().equals("KDJ")) {
				kdjHigh = alertSetting.getRatio();
				kdjLow = alertSetting.getRatio2();
				kdjMid = alertSetting.getRatio3();
				if (alertSetting.getOpenflg().equals("1")) {
					kdjOpen = true;
				}
				continue;
			}
			if(alertSetting.getType().equals("MACD")) {
				macdZero = alertSetting.getRatio();
				if (alertSetting.getOpenflg().equals("1")) {
					macdOpen = true;
				}
				continue;
			}
			if(alertSetting.getType().equals("BOLL")) {
				if (alertSetting.getOpenflg().equals("1")) {
					bollOpen = true;
				}
				continue;
			}
			if(alertSetting.getType().equals("RSI")) {
				rsiChaomai = alertSetting.getRatio();
				rsiLow = alertSetting.getRatio2();
				rsiHigh = alertSetting.getRatio3();
				rsiMid = alertSetting.getRatio4();
				if (alertSetting.getOpenflg().equals("1")) {
					rsiOpen = true;
				}
				continue;
			}
			if(alertSetting.getType().equals("DOJI")) {
				if (alertSetting.getOpenflg().equals("1")) {
					dojiOpen = true;
				}
				ratioBean.setDojiDifRatio(alertSetting.getRatio());
				ratioBean.setDojiDownCountRatio(alertSetting.getRatio2());
				ratioBean.setDojiDownChangeRatio(alertSetting.getRatio3());
				ratioBean.setDojiHhvRatio(alertSetting.getRatio4());
				continue;
			}
			if(alertSetting.getType().equals("VOLUME")) {
				volChange = alertSetting.getRatio();
				if (alertSetting.getOpenflg().equals("1")) {
					volumeOpen = true;
				}
				continue;
			}
			if(alertSetting.getType().equals("MA")) {
				ma5 = alertSetting.getRatio();
				ma10 = alertSetting.getRatio2();
				ma30 = alertSetting.getRatio3();
				ma120 = alertSetting.getRatio4();
				if (alertSetting.getOpenflg().equals("1")) {
					maOpen = true;
				}
				continue;
			}
			if(alertSetting.getType().equals("VR")) {
				if (alertSetting.getOpenflg().equals("1")) {
					vrOpen = true;
				}
				continue;
			}
			if(alertSetting.getType().equals("DMI")) {
                pdiUpRatio = alertSetting.getRatio();
                adxUpRatio = alertSetting.getRatio3();
				if (alertSetting.getOpenflg().equals("1")) {
					dmiOpen = true;
				}
				continue;
			}
            if(alertSetting.getType().equals("SAR")) {
                if (alertSetting.getOpenflg().equals("1")) {
                    sarOpen = true;
                }
                continue;
            }
		}

		//都不打开那就是不分析
		if (!(kdjOpen || macdOpen || rsiOpen || dojiOpen || volumeOpen || maOpen || vrOpen)) {
			return null;
		}

		ratioBean.setKdjHigh(kdjHigh);
		ratioBean.setKdjLow(kdjLow);
		ratioBean.setKdjMid(kdjMid);

		ratioBean.setMacdZero(macdZero);
		ratioBean.setRsiChaomai(rsiChaomai);
		ratioBean.setRsiLow(rsiLow);
		ratioBean.setRsiHigh(rsiHigh);
		ratioBean.setRsiMid(rsiMid);

		ratioBean.setVolChange(volChange);
		ratioBean.setMa5(ma5);
		ratioBean.setMa10(ma10);
		ratioBean.setMa30(ma30);
		ratioBean.setMa120(ma120);

        ratioBean.setPdiUpRatio(pdiUpRatio);
        ratioBean.setAdxUpRatio(adxUpRatio);

		ratioBean.setKdjOpen(kdjOpen);
		ratioBean.setMacdOpen(macdOpen);
		ratioBean.setBollOpen(bollOpen);
		ratioBean.setRsiOpen(rsiOpen);
		ratioBean.setDojiOpen(dojiOpen);
		ratioBean.setVolumeOpen(volumeOpen);
		ratioBean.setMaOpen(maOpen);
		ratioBean.setVrOpen(vrOpen);
		ratioBean.setDmiOpen(dmiOpen);
        ratioBean.setSarOpen(sarOpen);

		return ratioBean;
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


	@Override
	public boolean getCross(List<AlertSetting> alertSettingList, QuotaBean quotaBean, double price) {
		boolean isCross = true;
		if (null != alertSettingList && alertSettingList.size() > 0) {
			RatioBean ratioBean = this.getRatioBean(alertSettingList);

			if (null == ratioBean) {
				return false;
			}

			if (ratioBean.isKdjOpen()) {
				if (quotaBean.getKdjBean().getJ()+1 > quotaBean.getKdjBean().getK()
						&& quotaBean.getKdjBean().getK()+1 > quotaBean.getKdjBean().getD()
						&& quotaBean.getKdjBean().getJ() < ratioBean.getKdjHigh()
						&& quotaBean.getKdjBean().getJPre() < quotaBean.getKdjBean().getJ()) {
//                    isCross = true;
				} else {
					isCross = false;
				}
			}
			if (ratioBean.isMacdOpen()) {
				if (quotaBean.getMacdBean().getDif() > quotaBean.getMacdBean().getDifPre()) {
//  TODO  MACD 零轴                     && macdBean.getDifPre() < macdZero) {
//                    isCross = true;
				} else {
					isCross = false;
				}
			}
			if (ratioBean.isBollOpen()) {
				if (price > quotaBean.getBollBean().getBoll()) {
//                    isCross = true;
				} else {
					isCross = false;
				}
			}
			if (ratioBean.isRsiOpen()) {
				if (quotaBean.getRsiBean().getRsi1() > quotaBean.getRsiBean().getRsi3()
						&& quotaBean.getRsiBean().getRsi1() < ratioBean.getRsiChaomai()) {
//                    isCross = true;
				} else {
					isCross = false;
				}
			}
			if (ratioBean.isVolumeOpen()) {
				if (quotaBean.getVolumeBean().getFast() > quotaBean.getVolumeBean().getSlow()) {
//                    isCross = true;
				} else {
					double volChajia = Math.abs(quotaBean.getVolumeBean().getFast() / quotaBean.getVolumeBean().getSlow() - 1);
					if (volChajia < ratioBean.getVolChange()) {
						//                    isCross = true;
					} else {
						isCross = false;
					}

				}
			}
			if (ratioBean.isMaOpen()) {
				if (ratioBean.getMa5() == 1) {
					if (price > quotaBean.getMaBean().getMa5()) {
//                        isCross = true;
					} else {
						isCross = false;
					}
//					if (quotaBean.getMaBean().getMa5() > quotaBean.getBollBean().getBoll()) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
				}
				if (ratioBean.getMa10() == 1) {
					if (price > quotaBean.getMaBean().getMa10()) {
//                        isCross = true;
					} else {
						isCross = false;
					}
//					if (quotaBean.getMaBean().getMa10() > quotaBean.getBollBean().getBoll()) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
					double maChange = quotaBean.getMaBean().getMa10()/quotaBean.getMaBean().getMa5()-1;
					if (quotaBean.getMaBean().getMa5() > quotaBean.getMaBean().getMa10()
							|| maChange < 0.01) {
//                        isCross = true;
					} else {
						isCross = false;
					}
				}
//				if (ratioBean.getMa30() == 1) {
//					if (price > quotaBean.getMaBean().getMa30()) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
//				}
//				if (ratioBean.getMa120() == 1) {
//					if (price > quotaBean.getMaBean().getMa120()) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
//				}
			}
			if (ratioBean.isVrOpen()) {
				if (quotaBean.getVrBean().getVr() > quotaBean.getVrBean().getMavr()
						) {
//                    isCross = true;
				} else {
					isCross = false;
				}
			}
			if (ratioBean.isDmiOpen()) {
				if (quotaBean.getDmiBean().getPdi() > quotaBean.getDmiBean().getMdi()
						&& quotaBean.getDmiBean().getAdxr() > quotaBean.getDmiBean().getMdi()
						&& quotaBean.getDmiBean().getAdx()-1 > quotaBean.getDmiBean().getAdxr()
						&& quotaBean.getDmiBean().getPdi() > ratioBean.getPdiUpRatio()
						&& quotaBean.getDmiBean().getAdx() > ratioBean.getAdxUpRatio()) {
//                    isCross = true;
				} else {
					isCross = false;
				}
			}
			if (ratioBean.isSarOpen()) {
				if (price > quotaBean.getSarBean().getSar()) {
//                    isCross = true;
				} else {
					isCross = false;
				}
			}
		}
		return isCross;
	}

	@Override
	public boolean getCrossNew(List<AlertSetting> alertSettingList, CrossQuotaBean crossBean) {
		boolean isCross = true;
		if (null != alertSettingList && alertSettingList.size() > 0) {
			RatioBean ratioBean = this.getRatioBean(alertSettingList);

			if (null == ratioBean) {
				return false;
			}

//			if (ratioBean.isKdjOpen()) {
//				if (crossBean.getQuotaBean().getKdjBean().getJ()+1 > crossBean.getQuotaBean().getKdjBean().getK()
//						&& crossBean.getQuotaBean().getKdjBean().getK()+1 > crossBean.getQuotaBean().getKdjBean().getD()
//						&& crossBean.getQuotaBean().getKdjBean().getJ() < ratioBean.getKdjChaomai()
//						&& crossBean.getQuotaBean().getKdjBean().getJPre() < crossBean.getQuotaBean().getKdjBean().getJ()) {
////                    isCross = true;
//				} else {
//					isCross = false;
//				}
//			}
//			if (ratioBean.isMacdOpen()) {
//				if (crossBean.getQuotaBean().getMacdBean().getDif() > crossBean.getQuotaBean().getMacdBean().getDifPre()) {
////  TODO  MACD 零轴                     && macdBean.getDifPre() < macdZero) {
////                    isCross = true;
//				} else {
//					isCross = false;
//				}
//			}
//			if (ratioBean.isBollOpen()) {
//				if (crossBean.getPrice() > crossBean.getQuotaBean().getBollBean().getBoll()) {
////                    isCross = true;
//				} else {
//					isCross = false;
//				}
//			}
//			if (ratioBean.isRsiOpen()) {
//				if (crossBean.getQuotaBean().getRsiBean().getRsi1() > crossBean.getQuotaBean().getRsiBean().getRsi3()
//						&& crossBean.getQuotaBean().getRsiBean().getRsi1() < ratioBean.getRsiChaomai()) {
////                    isCross = true;
//				} else {
//					isCross = false;
//				}
//			}
//			if (ratioBean.isVolumeOpen()) {
//				if (crossBean.getQuotaBean().getVolumeBean().getFast() > crossBean.getQuotaBean().getVolumeBean().getSlow()) {
////                    isCross = true;
//				} else {
//					double volChajia = Math.abs(crossBean.getQuotaBean().getVolumeBean().getFast() / crossBean.getQuotaBean().getVolumeBean().getSlow() - 1);
//					if (volChajia < ratioBean.getVolChange()) {
//						//                    isCross = true;
//					} else {
//						isCross = false;
//					}
//
//				}
//			}
			if (ratioBean.isMaOpen()) {
//				if (ratioBean.getMa5() == 1) {
//					if (crossBean.getPrice() > crossBean.getQuotaBean().getMaBean().getMa5()) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
////					if (crossBean.getQuotaBean().getMaBean().getMa5() > crossBean.getQuotaBean().getBollBean().getBoll()) {
//////                        isCross = true;
////					} else {
////						isCross = false;
////					}
//				}
//				if (ratioBean.getMa10() == 1) {
//					if (crossBean.getPrice() > crossBean.getQuotaBean().getMaBean().getMa10()) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
////					if (crossBean.getQuotaBean().getMaBean().getMa10() > crossBean.getQuotaBean().getBollBean().getBoll()) {
//////                        isCross = true;
////					} else {
////						isCross = false;
////					}
//					double maChange = crossBean.getQuotaBean().getMaBean().getMa10()/crossBean.getQuotaBean().getMaBean().getMa5()-1;
//					if (crossBean.getQuotaBean().getMaBean().getMa5() > crossBean.getQuotaBean().getMaBean().getMa10()
//							|| maChange < 0.01) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
//				}
//				if (ratioBean.getMa30() == 1) {
//					if (price > crossBean.getQuotaBean().getMaBean().getMa30()) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
//				}
//				if (ratioBean.getMa120() == 1) {
//					if (price > crossBean.getQuotaBean().getMaBean().getMa120()) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
//				}
			}
//			if (ratioBean.isVrOpen()) {
//				if (crossBean.getQuotaBean().getVrBean().getVr() > crossBean.getQuotaBean().getVrBean().getMavr()
//						) {
////                    isCross = true;
//				} else {
//					isCross = false;
//				}
//			}
			if (ratioBean.isDmiOpen()) {
				if (crossBean.getQuotaBean().getDmiBean().getPdi() > crossBean.getQuotaBean().getDmiBean().getMdi()
						&& crossBean.getQuotaBean().getDmiBean().getAdx()-1 > crossBean.getQuotaPreBean().getDmiBean().getAdx()) {
//                    isCross = true;
				} else {
					isCross = false;
				}
			}
//			if (ratioBean.isSarOpen()) {
//				if (crossBean.getPrice() > crossBean.getQuotaBean().getSarBean().getSar()) {
////                    isCross = true;
//				} else {
//					isCross = false;
//				}
//			}
		}
		return isCross;
	}

	@Override
	public boolean getCrossSellNew(List<AlertSetting> alertSettingList, CrossQuotaBean crossBean) {
		boolean isCross = true;
		if (null != alertSettingList && alertSettingList.size() > 0) {
			RatioBean ratioBean = this.getRatioBean(alertSettingList);

			if (null == ratioBean) {
				return false;
			}

//			if (ratioBean.isKdjOpen()) {
//				if (crossBean.getQuotaBean().getKdjBean().getJ()+1 > crossBean.getQuotaBean().getKdjBean().getK()
//						&& crossBean.getQuotaBean().getKdjBean().getK()+1 > crossBean.getQuotaBean().getKdjBean().getD()
//						&& crossBean.getQuotaBean().getKdjBean().getJ() < ratioBean.getKdjChaomai()
//						&& crossBean.getQuotaBean().getKdjBean().getJPre() < crossBean.getQuotaBean().getKdjBean().getJ()) {
////                    isCross = true;
//				} else {
//					isCross = false;
//				}
//			}
//			if (ratioBean.isMacdOpen()) {
//				if (crossBean.getQuotaBean().getMacdBean().getDif() > crossBean.getQuotaBean().getMacdBean().getDifPre()) {
////  TODO  MACD 零轴                     && macdBean.getDifPre() < macdZero) {
////                    isCross = true;
//				} else {
//					isCross = false;
//				}
//			}
//			if (ratioBean.isBollOpen()) {
//				if (crossBean.getPrice() > crossBean.getQuotaBean().getBollBean().getBoll()) {
////                    isCross = true;
//				} else {
//					isCross = false;
//				}
//			}
//			if (ratioBean.isRsiOpen()) {
//				if (crossBean.getQuotaBean().getRsiBean().getRsi1() > crossBean.getQuotaBean().getRsiBean().getRsi3()
//						&& crossBean.getQuotaBean().getRsiBean().getRsi1() < ratioBean.getRsiChaomai()) {
////                    isCross = true;
//				} else {
//					isCross = false;
//				}
//			}
//			if (ratioBean.isVolumeOpen()) {
//				if (crossBean.getQuotaBean().getVolumeBean().getFast() > crossBean.getQuotaBean().getVolumeBean().getSlow()) {
////                    isCross = true;
//				} else {
//					double volChajia = Math.abs(crossBean.getQuotaBean().getVolumeBean().getFast() / crossBean.getQuotaBean().getVolumeBean().getSlow() - 1);
//					if (volChajia < ratioBean.getVolChange()) {
//						//                    isCross = true;
//					} else {
//						isCross = false;
//					}
//
//				}
//			}
			if (ratioBean.isMaOpen()) {
//				if (ratioBean.getMa5() == 1) {
//					if (crossBean.getPrice() > crossBean.getQuotaBean().getMaBean().getMa5()) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
////					if (crossBean.getQuotaBean().getMaBean().getMa5() > crossBean.getQuotaBean().getBollBean().getBoll()) {
//////                        isCross = true;
////					} else {
////						isCross = false;
////					}
//				}
//				if (ratioBean.getMa10() == 1) {
//					if (crossBean.getPrice() > crossBean.getQuotaBean().getMaBean().getMa10()) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
////					if (crossBean.getQuotaBean().getMaBean().getMa10() > crossBean.getQuotaBean().getBollBean().getBoll()) {
//////                        isCross = true;
////					} else {
////						isCross = false;
////					}
//					double maChange = crossBean.getQuotaBean().getMaBean().getMa10()/crossBean.getQuotaBean().getMaBean().getMa5()-1;
//					if (crossBean.getQuotaBean().getMaBean().getMa5() > crossBean.getQuotaBean().getMaBean().getMa10()
//							|| maChange < 0.01) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
//				}
//				if (ratioBean.getMa30() == 1) {
//					if (price > crossBean.getQuotaBean().getMaBean().getMa30()) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
//				}
//				if (ratioBean.getMa120() == 1) {
//					if (price > crossBean.getQuotaBean().getMaBean().getMa120()) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
//				}
			}
//			if (ratioBean.isVrOpen()) {
//				if (crossBean.getQuotaBean().getVrBean().getVr() > crossBean.getQuotaBean().getVrBean().getMavr()
//						) {
////                    isCross = true;
//				} else {
//					isCross = false;
//				}
//			}
			if (ratioBean.isDmiOpen()) {
				if (crossBean.getQuotaBean().getDmiBean().getAdx()-1 < crossBean.getQuotaPreBean().getDmiBean().getAdx()
						|| crossBean.getQuotaBean().getDmiBean().getAdx() > 50) {
//                    isCross = true;
				} else {
					isCross = false;
				}
			}
//			if (ratioBean.isSarOpen()) {
//				if (crossBean.getPrice() > crossBean.getQuotaBean().getSarBean().getSar()) {
////                    isCross = true;
//				} else {
//					isCross = false;
//				}
//			}
		}
		return isCross;
	}

	@Override
	public boolean getDoji(List<AlertSetting> alertSettingList, QuotaBean quotaBean, double dojiPrice, double price) {
		boolean isCross = true;
		if (null != alertSettingList && alertSettingList.size() > 0) {

			RatioBean ratioBean = this.getRatioBean(alertSettingList);

			if (null == ratioBean) {
				return false;
			}

			if (ratioBean.isDojiOpen()) {
				if (quotaBean.getDojiBean().getDoji()  < ratioBean.getDojiDifRatio()
						&& quotaBean.getDojiBean().getDownCount() > ratioBean.getDojiDownCountRatio()
						&& quotaBean.getDojiBean().getDownChange() > ratioBean.getDojiDownChangeRatio()
						&& dojiPrice < price) {
//                    isCross = true;
				} else {
					isCross = false;
				}
			}

			if (ratioBean.isKdjOpen()) {
				if (quotaBean.getKdjBean().getJ() > quotaBean.getKdjBean().getJPre()
						&& quotaBean.getKdjBean().getJ() > quotaBean.getKdjBean().getK()
						&& quotaBean.getKdjBean().getK() > quotaBean.getKdjBean().getD()) {
//                    isCross = true;
				} else {
					isCross = false;
				}
			}
//			if (macdOpen) {
//				if (quotaBean.getMacdBean().getDif() > quotaBean.getMacdBean().getDifPre()) {
////  TODO  MACD 零轴                     && macdBean.getDifPre() < macdZero) {
////                    isCross = true;
//				} else {
//					isCross = false;
//				}
//			}
//			if (ratioBean.isBollOpen()) {
//				if (price < quotaBean.getBollBean().getBoll()) {
////                    isCross = true;
//				} else {
//					isCross = false;
//				}
//			}
			if (ratioBean.isRsiOpen()) {
				if (quotaBean.getRsiBean().getRsi1() > quotaBean.getRsiBean().getRsi3()
						&& quotaBean.getRsiBean().getRsi1() < ratioBean.getRsiChaomai()) {
//                    isCross = true;
				} else {
					isCross = false;
				}
			}
//			if (volumeOpen) {
//				if (quotaBean.getVolumeBean().getFast() > quotaBean.getVolumeBean().getSlow()) {
////                    isCross = true;
//				} else {
//					double volChajia = Math.abs(quotaBean.getVolumeBean().getFast() / quotaBean.getVolumeBean().getSlow() - 1);
//					if (volChajia < volChange) {
//						//                    isCross = true;
//					} else {
//						isCross = false;
//					}
//
//				}
//			}
			if (ratioBean.isMaOpen()) {
				if (ratioBean.getMa5() == 1) {
					if (quotaBean.getMaBean().getMa5() > dojiPrice) {
//                        isCross = true;
					} else {
						isCross = false;
					}
//					if (quotaBean.getMaBean().getMa5() > quotaBean.getMaBean().getMa10()) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
				}
				if (ratioBean.getMa10() == 1) {
					if (quotaBean.getMaBean().getMa10() > dojiPrice) {
//                        isCross = true;
					} else {
						isCross = false;
					}
//					if (maBean.getMa10() > bollBean.getBoll()) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
				}
//				if (ma30 == 1) {
//					if (price > quotaBean.getMaBean().getMa30()) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
//				}
//				if (ratioBean.getMa120() == 1) {
//					if (price < quotaBean.getMaBean().getMa120()) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
//					double change = quotaBean.getMaBean().getMa120()/price-1;
//					if (change > 0.05) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
//				}
			}
//			if (ratioBean.isVrOpen()) {
//				if (quotaBean.getVrBean().getVr() < 160) {
////                    isCross = true;
//				} else {
//					isCross = false;
//				}
//			}
			if (ratioBean.isDmiOpen()) {
				if (quotaBean.getDmiBean().getMdi() > quotaBean.getDmiBean().getPdi()
						&& ratioBean.getPdiUpRatio() > quotaBean.getDmiBean().getPdi()) {
//                    isCross = true;
				} else {
					isCross = false;
				}
			}
		} else {
			isCross = false;
		}
		return isCross;
	}

	@Override
	public boolean getDojiSell(List<AlertSetting> alertSettingList, QuotaBean quotaBean, double dojiPrice, double close,double high) {
		boolean isCross = false;
		if (null != alertSettingList && alertSettingList.size() > 0) {

			RatioBean ratioBean = this.getRatioBean(alertSettingList);

			if (null == ratioBean) {
				return false;
			}

			if (ratioBean.isDojiOpen()) {
				if (dojiPrice > close) {
					isCross = true;
				}
			}

			if (ratioBean.isKdjOpen()) {
				if (quotaBean.getKdjBean().getJ() < quotaBean.getKdjBean().getK()
						&& quotaBean.getKdjBean().getK() < quotaBean.getKdjBean().getD()) {
					isCross = true;
				}
			}

//			if (ratioBean.isBollOpen()) {
//				if (close > quotaBean.getBollBean().getUb()) {
//                    isCross = true;
//				}
//			}
//			if (ratioBean.isRsiOpen()) {
//				if (quotaBean.getRsiBean().getRsi1() > quotaBean.getRsiBean().getRsi3()
//						&& quotaBean.getRsiBean().getRsi1() < ratioBean.getRsiChaomai()) {
////                    isCross = true;
//				} else {
//					isCross = false;
//				}
//			}
//			if (volumeOpen) {
//				if (quotaBean.getVolumeBean().getFast() > quotaBean.getVolumeBean().getSlow()) {
////                    isCross = true;
//				} else {
//					double volChajia = Math.abs(quotaBean.getVolumeBean().getFast() / quotaBean.getVolumeBean().getSlow() - 1);
//					if (volChajia < volChange) {
//						//                    isCross = true;
//					} else {
//						isCross = false;
//					}
//
//				}
//			}
			if (ratioBean.isMaOpen()) {
//				if (ratioBean.getMa5() == 1) {
//					if (quotaBean.getMaBean().getMa5() > quotaBean.getPriceBean().getClosePre()) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
////					if (quotaBean.getMaBean().getMa5() > quotaBean.getMaBean().getMa10()) {
//////                        isCross = true;
////					} else {
////						isCross = false;
////					}
//				}
//				if (ratioBean.getMa10() == 1) {
//					if (quotaBean.getMaBean().getMa10() > quotaBean.getPriceBean().getClosePre()) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
////					if (maBean.getMa10() > bollBean.getBoll()) {
//////                        isCross = true;
////					} else {
////						isCross = false;
////					}
//				}
//				if (ma30 == 1) {
//					if (price > quotaBean.getMaBean().getMa30()) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
//				}
//				if (ratioBean.getMa120() == 1) {
//					if (price < quotaBean.getMaBean().getMa120()) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
//					double change = quotaBean.getMaBean().getMa120()/price-1;
//					if (change > 0.05) {
////                        isCross = true;
//					} else {
//						isCross = false;
//					}
//				}
			}
//			if (ratioBean.isVrOpen()) {
//				if (quotaBean.getVrBean().getVr() < 160) {
////                    isCross = true;
//				} else {
//					isCross = false;
//				}
//			}
//			if (ratioBean.isDmiOpen()) {
//				if (quotaBean.getDmiBean().getMdi() > quotaBean.getDmiBean().getPdi()
//						&& ratioBean.getPdiUpRatio() > quotaBean.getDmiBean().getPdi()) {
////                    isCross = true;
//				} else {
//					isCross = false;
//				}
//			}
		}
		return isCross;
	}










}
