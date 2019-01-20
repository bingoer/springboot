/**
 * model业务实现类
 */
/**
 * @author Raye
 * @since 2016年9月21日20:58:46
 */
package wang.raye.springboot.server.impl;

import de.elbatya.cryptocoins.bittrexclient.BlockccClient;
import de.elbatya.cryptocoins.bittrexclient.BlockccInfoClient;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.blockcc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import wang.raye.springboot.model.AlertUser;
import wang.raye.springboot.model.AlertUserCriteria;
import wang.raye.springboot.model.Tickers;
import wang.raye.springboot.model.TickersCriteria;
import wang.raye.springboot.model.mapper.AlertUserMapper;
import wang.raye.springboot.model.mapper.TickersMapper;
import wang.raye.springboot.model.mapper.TickersMapper;
import wang.raye.springboot.server.MapperServer;
import wang.raye.springboot.server.TickersServer;
import wang.raye.springboot.server.TickersServer;
import wang.raye.springboot.utils.DateUtils;
import wang.raye.springboot.utils.ParseUtils;
import wang.raye.springboot.utils.WxSendUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class TickersServerImpl implements TickersServer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxSendUtils wxSendUtils;
    @Autowired
    private MapperServer mapperServer;

    @Override
    public boolean exchangeTickers(String exchange) {
        BlockccClient blockccClient = new BlockccClient();
        // 调用api获取汇率
//        double usdRate=this.getUsdRate(blockccClient);

        //交易所名转换
//        exchange = ParseUtils.parseMarket(exchange);

        BlockccInfoClient blockccInfoClient = new BlockccInfoClient();
        // 调用api取得交易对信息
        ExchangeTickers exchangeInfo = blockccInfoClient.getInfoApi().getExchangeTickers(ParseUtils.parseInfoMarket(exchange),0,700).unwrap();

        List<ExchangeTickersEntry> exchangeInfoList = exchangeInfo.getTickers();

        // 从DB取得已经存在的交易对
        List<Tickers> TickersList= mapperServer.getTickersListByExchange(exchange);

        List<ExchangeTickersEntry> existList = new ArrayList<>();
        if (null != exchangeInfoList && exchangeInfoList.size()> 0) {
            for (ExchangeTickersEntry exchangeInfoEntry: exchangeInfoList) {
                if (null != TickersList && TickersList.size()> 0) {
                    for (Tickers tickers: TickersList) {
                        if (exchangeInfoEntry.getDisplay_pair_name().replace("/","_")
                                .equals(tickers.getDisplayPairName())) {
                            existList.add(exchangeInfoEntry);
                            break;
                        }
                    }
                }
            }
        }

//        if (null != existList && existList.size() > 0) {
//            if (!exchange.equals("idex")) {
//                mapperServer.updateTickers(existList, exchange);
//            }
//        }

        exchangeInfoList.removeAll(existList);

        if (null != exchangeInfoList && exchangeInfoList.size()> 0) {
            List<AlertUser> alertUsers= mapperServer.getAlertUsers();
            for (ExchangeTickersEntry exchangeInfoEntry: exchangeInfoList) {
                mapperServer.addTickers(exchangeInfoEntry);

//                String symbol = exchangeInfoEntry.getDisplay_pair_name().split("/")[0];

//                // 通过api获取新币价格信息
//                List<Price> listPrice = null;
//                // 通过api获取已有交易所信息
//                String[] exchangeCoins = null;
//                try{
//                    listPrice = blockccClient.getPublicApi().getPrice(symbol).unwrap();
//                }
//                catch (Exception e){
//                    log.error(e.getMessage());
//                }
//                try{
//                    exchangeCoins = blockccInfoClient.getInfoApi().getExchangeCoin(exchangeInfoEntry.getCoin_id()).unwrap();
//                }
//                catch (Exception e){
//                    log.error(e.getMessage());
//                }
                CoinInfo CoinInfo = this.getCoinInfo(blockccInfoClient,exchangeInfoEntry.getCoin_id());
                this.sendNew(alertUsers, exchangeInfoEntry, CoinInfo);
            }

        }

        return false;
    }

    @Override
    public boolean updateExchangeTickers(String exchange) {
        BlockccClient blockccClient = new BlockccClient();
        // 调用api获取汇率
//        double usdRate=this.getUsdRate(blockccClient);

        //交易所名转换
//        exchange = ParseUtils.parseMarket(exchange);

        BlockccInfoClient blockccInfoClient = new BlockccInfoClient();
        // 调用api取得交易对信息
        ExchangeTickers exchangeInfo = blockccInfoClient.getInfoApi().getExchangeTickers(ParseUtils.parseInfoMarket(exchange),0,700).unwrap();

        List<ExchangeTickersEntry> exchangeInfoList = exchangeInfo.getTickers();

        // 从DB取得已经存在的交易对
        List<Tickers> TickersList= mapperServer.getTickersListByExchange(exchange);

        List<ExchangeTickersEntry> existList = new ArrayList<>();
        if (null != exchangeInfoList && exchangeInfoList.size()> 0) {
            for (ExchangeTickersEntry exchangeInfoEntry: exchangeInfoList) {
                if (null != TickersList && TickersList.size()> 0) {
                    for (Tickers tickers: TickersList) {
                        if (exchangeInfoEntry.getDisplay_pair_name().replace("/","_")
                                .equals(tickers.getDisplayPairName())) {
                            existList.add(exchangeInfoEntry);
                            break;
                        }
                    }
                }
            }
        }

        if (null != existList && existList.size() > 0) {
            mapperServer.updateTickers(existList, exchange);
        }

        return false;
    }


    /**
     * 发送上线新币提醒

     */
    private void sendNew(List<AlertUser> alertUsers, ExchangeTickersEntry exchangeTicker, CoinInfo coinInfo){
        for(AlertUser alertUser : alertUsers) {
            wxSendUtils.sendNew(alertUser.getSckey(),exchangeTicker, coinInfo);
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