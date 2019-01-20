/**
 * model业务实现类
 */
/**
 * @author Raye
 * @since 2016年9月21日20:58:46
 */
package wang.raye.springboot.server.impl;

import de.elbatya.cryptocoins.bittrexclient.AicoinClient;
import de.elbatya.cryptocoins.bittrexclient.BlockccClient;
import de.elbatya.cryptocoins.bittrexclient.BlockccInfoClient;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.CommonInterval;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.CommonIntervalMap;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.aicoin.Kline;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.blockcc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import wang.raye.springboot.bean.WxSendBean;
import wang.raye.springboot.model.*;
import wang.raye.springboot.model.Tickers;
import wang.raye.springboot.model.mapper.AlertSettingMapper;
import wang.raye.springboot.model.mapper.AlertUserMapper;
import wang.raye.springboot.model.mapper.PairsMapper;
import wang.raye.springboot.model.mapper.TickersMapper;
import wang.raye.springboot.server.MapperServer;
import wang.raye.springboot.server.StartMonitorServer;
import wang.raye.springboot.utils.DateUtils;
import wang.raye.springboot.utils.QuotaCrossUtils;
import wang.raye.springboot.utils.WxSendUtils;

import java.util.List;

@Repository
public class StartMonitorImpl implements StartMonitorServer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final String TYPE_START = "START";
    private final String START_UP = "2";
    private final String START_DOWN = "1";

    @Autowired
    private MapperServer mapperServer;

    @Autowired
    private QuotaCrossUtils quotaCrossUtils;
    @Autowired
    private WxSendUtils wxSendUtils;

    @Override
    public boolean startPoint(String marketName, CommonInterval period) {


        List<AlertExchange> alertExchangeList = mapperServer.getAlertExchange(marketName);

        if (null != alertExchangeList && alertExchangeList.size() > 0) {

            AicoinClient aicoinClient = new AicoinClient();
            BlockccClient blockccClient = new BlockccClient();
            BlockccInfoClient blockccInfoClient = new BlockccInfoClient();

            double limitVol = mapperServer.getLimitVolume();
            List<Tickers> tickersList = mapperServer.getTickers(marketName,period.getIntervalId(), alertExchangeList, limitVol);

            if (null != tickersList && tickersList.size() > 0) {

                List<AlertUser> alertUsers= mapperServer.getAlertUsers();

                List<AlertSetting> alertSettingList = mapperServer.getAlertSettingByCond(period.getIntervalId(),TYPE_START);

                // 调用api获取汇率
//                double usdRate=this.getUsdRate(blockccClient);

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
                        if (null != listKline && listKline.size() > 0) {
                            //
                            for (AlertSetting alertSetting: alertSettingList) {
                                //  上涨时
                                if (START_UP.equals(alertSetting.getStatus())) {
                                    String status = quotaCrossUtils.getStart(listKline, alertSetting);
                                    if (START_UP.equals(status)) {
                                        this.sendStart(alertUsers, tickers, this.getCoinInfo(blockccInfoClient,tickers.getCoinId()), period, listKline.get(listKline.size()-1).getClose());
                                        for (int i = listKline.size() - 6; i < listKline.size() - 1; i++) {
                                            log.info("上涨k线  "+ tickers.getDisplayPairName()+" close:["+listKline.get(i).getClose() + "] 时间:["+ listKline.get(i).getTime()+"]"+ DateUtils.format(listKline.get(i).getTime()));
                                        }
                                    }
                                } else if (START_DOWN.equals(alertSetting.getStatus())) {
                                    // 下跌时

                                }
                            }
                        }

                        Thread.sleep(300);

                    } catch (Exception e) {
                        log.error("线程 启动监测运行出错 :["+Thread.currentThread().getName()+"]marketName：" + marketName + ",symbol:" +symbol+",period:"+period.getIntervalId());
                        log.error(e.getMessage(),e);
                    }
                }
            }
        }

        return false;
    }

//    private List<Pairs> getPairs(String marketName, String currency) {
//        PairsCriteria cond = new PairsCriteria();
//        cond.createCriteria()
//                .andMarketEqualTo(marketName)
//                .andCurrencyEqualTo(currency)
//                .andHasKilneEqualTo("1");
//
//        return pairsMapper.selectByExample(cond);
//    }

    /**
     * 发送上涨提醒
     */
    private void sendStart(List<AlertUser> alertUsers, Tickers tickers, CoinInfo coinInfo, CommonInterval period,double price){
        WxSendBean wxSendBean = new WxSendBean();
        wxSendBean.setExchange(tickers.getExchangeName());
        wxSendBean.setSymbol(tickers.getDisplayPairName());
        wxSendBean.setPeriod(period.getIntervalId());
        wxSendBean.setPrice(price);
        for(AlertUser alertUser : alertUsers) {
            wxSendBean.setSckey(alertUser.getSckey());
            wxSendUtils.sendStart(wxSendBean, coinInfo);
        }
    }


    private double getUsdRate(BlockccClient blockccClient){
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

//    private List<Price> getLastPrice(BlockccClient blockccClient,String symbol) {
//        // 通过api获取新币价格信息
//        List<Price> listPrice = null;
//        try{
//            listPrice = blockccClient.getPublicApi().getPrice(symbol.split("_")[0]).unwrap();
//        }
//        catch (Exception e){
//            log.error(e.getMessage());
//        }
//        return  listPrice;
//    }

    private CoinInfo getCoinInfo(BlockccInfoClient blockccInfoClient, String coinId) {
        // 通过api获取币种信息
        CoinInfo coinInfo = null;
        try{
            coinInfo = blockccInfoClient.getInfoApi().getCoinInfo(coinId).unwrap();
        }
        catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return  coinInfo;
    }



}