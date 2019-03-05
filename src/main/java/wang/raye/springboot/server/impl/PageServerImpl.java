/**
 * model业务实现类
 */
/**
 * @author Raye
 * @since 2016年9月21日20:58:46
 */
package wang.raye.springboot.server.impl;

import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.CommonInterval;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.aicoin.Kline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import wang.raye.springboot.bean.*;
import wang.raye.springboot.model.AlertSetting;
import wang.raye.springboot.model.Tickers;
import wang.raye.springboot.model.TickersCriteria;
import wang.raye.springboot.model.mapper.TickersMapper;
import wang.raye.springboot.server.AnalysisServer;
import wang.raye.springboot.server.CrossServer;
import wang.raye.springboot.server.PageServer;
import wang.raye.springboot.utils.ConstantUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PageServerImpl implements PageServer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TickersMapper tickersMapper;
//    @Autowired
//    private CrossServer crossServer;
    @Autowired
    private AnalysisServer analysisServer;


    @Override
    public List<Tickers> getTickersByExchange(String exchange, String baseSymbol) {
        TickersCriteria cond = new TickersCriteria();
        cond.createCriteria()
                .andExchangeNameEqualTo(exchange)
                .andBaseSymbolEqualTo(baseSymbol);
        return tickersMapper.selectByExample(cond);
    }

    @Override
    public List<Kline> getLowList(List<Kline> candlestickList, QuotaListBean quotaListBean, List<AlertSetting> alertSettingList) {

        List<Kline> lowList = new ArrayList<>();

        boolean isBuy = false;

        for (int i = 0; i < candlestickList.size(); i++) {

            if (i > 10) {


                //===================交叉判断====================
                boolean isCross = false;
                // kdj处于金叉
                // vo处于金叉
                // macd底背离
                // 收盘价>ma5>ma10
                if (candlestickList.get(i).getTime() == 1535299200) {
                    System.out.println(candlestickList.get(i).getClose());
//                    System.out.println(maBeanList.get(i).getMa5());
//                    System.out.println(maBeanList.get(i).getMa10());
                }
                MacdBean macdBean = quotaListBean.getMacdBeanList().get(i);
                macdBean.setDifPre(quotaListBean.getMacdBeanList().get(i-1).getDif());
                KdjBean KdjBean = quotaListBean.getKdjBeanList().get(i);
                KdjBean.setJPre(quotaListBean.getKdjBeanList().get(i-1).getJ());

                BollBean bollBean = quotaListBean.getBollBeanList().get(i);
                bollBean.setLbPre(quotaListBean.getBollBeanList().get(i-1).getLb());

                DmiBean dmiBean = quotaListBean.getDmiBeanList().get(i);
                dmiBean.setAdxPre(quotaListBean.getDmiBeanList().get(i-1).getAdx());

                PriceBean priceBean = new PriceBean();
                priceBean.setClose(candlestickList.get(i).getClose());
                priceBean.setClosePre(candlestickList.get(i-1).getClose());
                priceBean.setClosePre2(candlestickList.get(i-2).getClose());
                priceBean.setClosePre3(candlestickList.get(i-3).getClose());
                priceBean.setOpenPre(candlestickList.get(i-1).getOpen());
                priceBean.setOpenPre2(candlestickList.get(i-2).getOpen());
                priceBean.setOpenPre3(candlestickList.get(i-3).getOpen());
                priceBean.setLow(candlestickList.get(i).getLow());
                priceBean.setLowPre(candlestickList.get(i-1).getLow());

                QuotaBean quotaBean = new QuotaBean();
                quotaBean.setMacdBean(quotaListBean.getMacdBeanList().get(i));
                quotaBean.setKdjBean(quotaListBean.getKdjBeanList().get(i));
                quotaBean.setBollBean(quotaListBean.getBollBeanList().get(i));
                quotaBean.setRsiBean(quotaListBean.getRsiBeanList().get(i));

                quotaBean.setVolumeBean(quotaListBean.getVolBeanList().get(i));

                quotaBean.setVrBean(quotaListBean.getVrBeanList().get(i));

                quotaBean.setSarBean(quotaListBean.getSarBeanList().get(i));
                quotaBean.setPriceBean(priceBean);
//                quotaBean.setDojiPriceBean(dojiPriceBeanList.get(i-1));
                quotaBean.setDmiBean(quotaListBean.getDmiBeanList().get(i));
                quotaBean.setMaBean(quotaListBean.getMaBeanList().get(i));
                quotaBean.setDojiBean(quotaListBean.getDojiBeanList().get(i));
//                quotaBean.setDmiBean(dmiBeanList.get(i-1));
//                quotaBean.setMaBean(maBeanList.get(i-1));
//                quotaBean.setDojiBean(dojiBeanList.get(i-1));

                CrossQuotaBean crossQuotaBean = this.getCrossQuotaBean(i, candlestickList, quotaListBean.getMacdBeanList(),
                        quotaListBean.getBollBeanList(), quotaListBean.getKdjBeanList(),
                        quotaListBean.getVolBeanList(), quotaListBean.getMaBeanList(),
                        quotaListBean.getRsiBeanList(), quotaListBean.getDmiBeanList(),
                        quotaListBean.getVrBeanList(),quotaListBean.getSarBeanList(),
                        quotaListBean.getDojiBeanList());


                if (isBuy) {
                    boolean isCrossSell = false;
//                    boolean isCrossSell = crossServer.getCrossSellNew(alertSettingList, crossQuotaBean);

                    if (isCrossSell) {
                        lowList.add(candlestickList.get(i));
                        isBuy = false;
                        continue;
                    }
                }

//                isCross = crossServer.getCrossNew(alertSettingList, crossQuotaBean);

                //=======================================

                if (isCross && !isBuy) {
                    lowList.add(candlestickList.get(i));
                    quotaListBean.getMacdBeanList().get(i).setTime(candlestickList.get(i).getTime());

                    quotaListBean.getKdjBeanList().get(i).setTime(candlestickList.get(i).getTime());
                    quotaListBean.getKdjBeanList().add(quotaListBean.getKdjBeanList().get(i));
                    quotaListBean.getVolBeanList().get(i).setTime(candlestickList.get(i).getTime());

//                    macdList.add(quotaListBean.getMacdBeanList().get(i));
//                    volList.add(quotaListBean.getVolBeanList().get(i));

//                    System.out.print(DateUtils.format(candlestickList.get(i).getTime())+"  ma5:"+maBeanList.get(i).getMa5());
//                    System.out.print("  ma10:"+maBeanList.get(i).getMa10());
//                    System.out.println("  CLOSE:"+candlestickList.get(i).getClose());


                    isBuy = true;
                    continue;
                }
            }
        }
        return  lowList;
    }

    @Override
    public PointListBean getPointListByRSI(List<Kline> candlestickList, QuotaListBean quotaListBean, List<AlertSetting> alertSettingList) {
        PointListBean pointListBean =new PointListBean();
        List<Kline> lowList = new ArrayList<>();
        List<Kline> highList = new ArrayList<>();
        List<Kline> stopLimitList = new ArrayList<>();
        List<Kline> retraceList = new ArrayList<>();

        List<CrossQuotaBean> lowCrossQuotaList = new ArrayList<>();
        List<CrossQuotaBean> highCrossQuotaList = new ArrayList<>();
        List<CrossQuotaBean> stopLimitCrossQuotaList = new ArrayList<>();
        List<CrossQuotaBean> retraceCrossQuotaList = new ArrayList<>();

        boolean isBuying = false;

        String preStatus = ConstantUtils.RSI_RETRACE_STATUS_NORMAL;

        for (int i = 0; i < candlestickList.size(); i++) {

            if (i > 10) {

                if (candlestickList.get(i).getTime() == 1547510400) {
                    System.out.println(candlestickList.get(i).getClose());
                }

                CrossQuotaBean crossQuotaBean = this.getCrossQuotaBean(i, candlestickList, quotaListBean.getMacdBeanList(),
                        quotaListBean.getBollBeanList(), quotaListBean.getKdjBeanList(),
                        quotaListBean.getVolBeanList(), quotaListBean.getMaBeanList(),
                        quotaListBean.getRsiBeanList(), quotaListBean.getDmiBeanList(),
                        quotaListBean.getVrBeanList(),quotaListBean.getSarBeanList(),
                        quotaListBean.getDojiBeanList());

                //=======================================
//                boolean isLow = analysisServer.getLowByRSI(alertSettingList, crossQuotaBean);
//                if (isLow) {
//                    lowList.add(candlestickList.get(i));
//                    isBuying = true;
//                    continue;
//                }

//                boolean isHigh = analysisServer.getHighByRSI(alertSettingList, crossQuotaBean);
//                if (isHigh) {
//                    highList.add(candlestickList.get(i));
//                    isBuying = false;
//                    continue;
//                }
//
//                if (isBuying) {
//                    boolean isStopLimit = analysisServer.getStopLimitByRSI(alertSettingList, crossQuotaBean);
//                    if (isStopLimit) {
//                        stopLimitList.add(candlestickList.get(i));
//                        isBuying = false;
//                        continue;
//                    }
//                }
                //=======================================

                VolatileBean volatileBean = analysisServer.getRetraceByRSI(alertSettingList, crossQuotaBean, preStatus);
                String newStatus = volatileBean.getStatus();
                if (!newStatus.equals(preStatus)){
                    if (newStatus.equals(ConstantUtils.RSI_RETRACE_STATUS_LOW)) {
                        lowList.add(candlestickList.get(i));
                        lowCrossQuotaList.add(crossQuotaBean);
                        isBuying = true;
                    }
                    if (newStatus.equals(ConstantUtils.RSI_RETRACE_STATUS_RETRACE)) {
                        retraceList.add(candlestickList.get(i));
                        retraceCrossQuotaList.add(crossQuotaBean);
                        isBuying = true;
                    }
                }

                VolatileBean volatileHighBean = analysisServer.getHighByRSI(alertSettingList, crossQuotaBean);
                if (null != volatileHighBean.getStatus()) {
                    highList.add(candlestickList.get(i));
                    highCrossQuotaList.add(crossQuotaBean);
                    isBuying = false;
                }

                if (isBuying) {
                    VolatileBean volatileStopLimitBean = analysisServer.getStopLimitByRSI(alertSettingList, crossQuotaBean);
                    if (null != volatileStopLimitBean.getStatus()) {
                        stopLimitList.add(candlestickList.get(i));
                        stopLimitCrossQuotaList.add(crossQuotaBean);
                        isBuying = false;
                    }
                }
                preStatus = newStatus;
            }
        }


        pointListBean.setLowList(lowList);
        pointListBean.setHighList(highList);
        pointListBean.setStopLimitList(stopLimitList);
        pointListBean.setRetraceList(retraceList);

        pointListBean.setLowCrossQuotaList(lowCrossQuotaList);
        pointListBean.setHighCrossQuotaList(highCrossQuotaList);
        pointListBean.setStopLimitCrossQuotaList(stopLimitCrossQuotaList);
        pointListBean.setRetraceCrossQuotaList(retraceCrossQuotaList);

        return  pointListBean;
    }

    private CrossQuotaBean getCrossQuotaBean(int i, List<Kline> candlestickList, List<MacdBean> macdBeanList,
                                            List<BollBean> bollBeanList, List<KdjBean> kdjBeanList,
                                            List<VolumeBean> volBeanList, List<MaBean> maBeanList,
                                            List<RsiBean> rsiBeanList, List<DmiBean> dmiBeanList,
                                            List<VrBean> vrBeanList,List<SarBean> sarBeanList,
                                            List<DojiBean> dojiBeanList) {

        CrossQuotaBean crossQuotaBean = new CrossQuotaBean();

        QuotaBean quotaBean = new QuotaBean();
        quotaBean.setMacdBean(macdBeanList.get(i));
        quotaBean.setKdjBean(kdjBeanList.get(i));
        quotaBean.setBollBean(bollBeanList.get(i));
        quotaBean.setRsiBean(rsiBeanList.get(i));
        quotaBean.setVolumeBean(volBeanList.get(i));
        quotaBean.setVrBean(vrBeanList.get(i));
        quotaBean.setSarBean(sarBeanList.get(i));
        quotaBean.setDmiBean(dmiBeanList.get(i));
        quotaBean.setMaBean(maBeanList.get(i));
        quotaBean.setDojiBean(dojiBeanList.get(i));

        QuotaBean quotaPreBean = new QuotaBean();
        quotaPreBean.setMacdBean(macdBeanList.get(i-1));
        quotaPreBean.setKdjBean(kdjBeanList.get(i-1));
        quotaPreBean.setBollBean(bollBeanList.get(i-1));
        quotaPreBean.setRsiBean(rsiBeanList.get(i-1));
        quotaPreBean.setVolumeBean(volBeanList.get(i-1));
        quotaPreBean.setVrBean(vrBeanList.get(i-1));
        quotaPreBean.setSarBean(sarBeanList.get(i-1));
        quotaPreBean.setDmiBean(dmiBeanList.get(i-1));
        quotaPreBean.setMaBean(maBeanList.get(i-1));
        quotaPreBean.setDojiBean(dojiBeanList.get(i-1));

        QuotaBean quotaThrBean = new QuotaBean();
        quotaThrBean.setMacdBean(macdBeanList.get(i-2));
        quotaThrBean.setKdjBean(kdjBeanList.get(i-2));
        quotaThrBean.setBollBean(bollBeanList.get(i-2));
        quotaThrBean.setRsiBean(rsiBeanList.get(i-2));
        quotaThrBean.setVolumeBean(volBeanList.get(i-2));
        quotaThrBean.setVrBean(vrBeanList.get(i-2));
        quotaThrBean.setSarBean(sarBeanList.get(i-2));
        quotaThrBean.setDmiBean(dmiBeanList.get(i-2));
        quotaThrBean.setMaBean(maBeanList.get(i-2));
        quotaThrBean.setDojiBean(dojiBeanList.get(i-2));

        crossQuotaBean.setQuotaBean(quotaBean);
        crossQuotaBean.setQuotaPreBean(quotaPreBean);
        crossQuotaBean.setQuotaThrBean(quotaThrBean);

        crossQuotaBean.setPrice(candlestickList.get(i).getClose());
        crossQuotaBean.setPricePre(candlestickList.get(i-1).getClose());
        crossQuotaBean.setPriceThr(candlestickList.get(i-2).getClose());

        return crossQuotaBean;

    }


}