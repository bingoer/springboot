/**
 * model业务实现类
 */
/**
 * @author Raye
 * @since 2016年9月21日20:58:46
 */
package wang.raye.springboot.server.impl;

import de.elbatya.cryptocoins.bittrexclient.AicoinClient;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.CommonInterval;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.CommonIntervalMap;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.aicoin.Kline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import wang.raye.springboot.bean.DmiBean;
import wang.raye.springboot.bean.KdjBean;
import wang.raye.springboot.bean.RsiBean;
import wang.raye.springboot.model.TradePoint;
import wang.raye.springboot.model.TradePointQuota;
import wang.raye.springboot.server.MapperServer;
import wang.raye.springboot.server.QuotaInfoServer;
import wang.raye.springboot.utils.DateUtils;
import wang.raye.springboot.utils.QuotaUtils;

import java.util.List;

@Repository
public class QuotaInfoServerImpl implements QuotaInfoServer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuotaUtils quotaUtils;
    @Autowired
    private MapperServer mapperServer;

    @Override
    public boolean updateTradePointQuota(String exchange, String symbol, String base, String period, String status,String quantType){

        TradePoint point = new TradePoint();
        point.setExchange(exchange);
        point.setSymbol(symbol);
        point.setBase(base);
        point.setPeriod(CommonInterval.valueOf(period).getIntervalId());
        point.setStatus(status);
        point.setQuantType(quantType);

        List<TradePoint> tradePointList =  mapperServer.getTradePoint(point);

        String exchangeTmp = "";
        String symbolTmp = "";
        String baseTmp = "";
        String periodTmp = "";

        List<Kline> candlestickList = null;

//        List<MacdBean> macdBeanList =  null;
//        List<BollBean> bollBeanList =  null;
        List<KdjBean> kdjBeanList = null;
//        List<VolumeBean> volBeanList = null;
//        List<MaBean> maBeanList = null;
        List<RsiBean> rsiBeanList = null;
        List<DmiBean> dmiBeanList = null;
//        List<VrBean> vrBeanList = null;
//        List<SarBean> sarBeanList = null;
//        List<DojiBean> dojiBeanList = null;

        for (TradePoint tradePoint: tradePointList) {


            if (!(exchangeTmp+symbolTmp+baseTmp+periodTmp).equals(
                    tradePoint.getExchange()+tradePoint.getSymbol()+tradePoint.getBase()+tradePoint.getPeriod())) {

                CommonInterval stepCommonInterval = null;
                CommonInterval[] commonIntervals = CommonInterval.values();
                for (CommonInterval commonInterval: commonIntervals) {
                    if (commonInterval.getIntervalId().equals(tradePoint.getPeriod())) {
                        stepCommonInterval = commonInterval;
                        break;
                    }
                }

                String aicoin_symbol = (tradePoint.getExchange() + tradePoint.getSymbol() + tradePoint.getBase()).toLowerCase();
                // aicoin的周线
                int step = CommonIntervalMap.ENUMMAP.get(stepCommonInterval);
                AicoinClient aicoinClient = new AicoinClient();
                candlestickList = aicoinClient.getPublicApi().getKline(aicoin_symbol, step).unwrap();

//                macdBeanList =  quotaUtils.getMACD(12,26,9,candlestickList);
//                bollBeanList =  quotaUtils.getBoll(20,2,candlestickList);
                kdjBeanList = quotaUtils.getKDJ(9,3,3,candlestickList);
//                volBeanList = quotaUtils.getVolume(5,10,candlestickList);
//                maBeanList = quotaUtils.getMa(5,10,30,120,candlestickList);
                rsiBeanList = quotaUtils.getRsi(6, 12, 24, candlestickList);
                dmiBeanList = quotaUtils.getDmi(14, 6, candlestickList);
//                vrBeanList = quotaUtils.getVr(26, 6, candlestickList);
//                sarBeanList = quotaUtils.getSar(candlestickList);
//                dojiBeanList = quotaUtils.getDoji(candlestickList,bollBeanList);
            }

            for (int i = 0; i < candlestickList.size(); i++) {
                if (tradePoint.getKlineTime().equals(DateUtils.format(candlestickList.get(i).getTime()))) {
                    TradePointQuota tradePointquota = new TradePointQuota();
                    tradePointquota.setExchange(tradePoint.getExchange());
                    tradePointquota.setSymbol(tradePoint.getSymbol());
                    tradePointquota.setPeriod(tradePoint.getPeriod());
                    tradePointquota.setQuantType(tradePoint.getQuantType());
                    tradePointquota.setStatus(tradePoint.getStatus());
                    tradePointquota.setKlineTime(tradePoint.getKlineTime());

                    tradePointquota.setQuotaType("KDJ");
                    List<TradePointQuota> quotaKdjList = mapperServer.getTradePointQuota(tradePointquota);
                    if (null == quotaKdjList || quotaKdjList.size() == 0) {
                        tradePointquota.setQuota1(kdjBeanList.get(i).getK());
                        tradePointquota.setQuota2(kdjBeanList.get(i).getD());
                        tradePointquota.setQuota3(kdjBeanList.get(i).getJ());
                        mapperServer.addTradePointQuota(tradePointquota);
                    }

                    tradePointquota.setQuotaType("RSI");
                    List<TradePointQuota> quotaRsiList = mapperServer.getTradePointQuota(tradePointquota);
                    if (null == quotaRsiList || quotaRsiList.size() == 0) {
                        tradePointquota.setQuota1(rsiBeanList.get(i).getRsi1());
                        tradePointquota.setQuota2(rsiBeanList.get(i).getRsi2());
                        tradePointquota.setQuota3(rsiBeanList.get(i).getRsi3());
                        mapperServer.addTradePointQuota(tradePointquota);
                    }

                    tradePointquota.setQuotaType("DMI");
                    List<TradePointQuota> quotaDmiList = mapperServer.getTradePointQuota(tradePointquota);
                    if (null == quotaDmiList || quotaDmiList.size() == 0) {
                        tradePointquota.setQuota1(dmiBeanList.get(i).getPdi());
                        tradePointquota.setQuota2(dmiBeanList.get(i).getMdi());
                        tradePointquota.setQuota3(dmiBeanList.get(i).getAdx());
                        tradePointquota.setQuota4(dmiBeanList.get(i).getAdxr());
                        mapperServer.addTradePointQuota(tradePointquota);
                    }
                }
            }

            exchangeTmp = tradePoint.getExchange();
            symbolTmp = tradePoint.getSymbol();
            baseTmp = tradePoint.getBase();
            periodTmp = tradePoint.getPeriod();

        }

        return false;
    }



}