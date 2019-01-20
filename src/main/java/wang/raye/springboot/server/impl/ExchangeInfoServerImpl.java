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
import de.elbatya.cryptocoins.bittrexclient.EthClient;
import de.elbatya.cryptocoins.bittrexclient.IdexClient;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.blockcc.ExchangeTickers;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.blockcc.ExchangeTickersEntry;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.idex.Currencies;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.idex.Market;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import wang.raye.springboot.model.Tickers;
import wang.raye.springboot.server.ExchangeInfoServer;
import wang.raye.springboot.server.MapperServer;
import wang.raye.springboot.utils.ParseUtils;
import wang.raye.springboot.utils.WxSendUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ExchangeInfoServerImpl implements ExchangeInfoServer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxSendUtils wxSendUtils;
    @Autowired
    private MapperServer mapperServer;

    @Override
    public boolean updateTickers(String exchange){
        BlockccClient blockccClient = new BlockccClient();
        // 调用api获取汇率
//        double usdRate=this.getUsdRate(blockccClient);

        //交易所名转换
//        exchange = ParseUtils.parseMarket(exchange);

        BlockccInfoClient blockccInfoClient = new BlockccInfoClient();
        int size = 200;
        // 调用api取得交易对信息
        ExchangeTickers exchangeInfo = blockccInfoClient.getInfoApi().getExchangeTickers(ParseUtils.parseInfoMarket(exchange),0,size).unwrap();
        int pageCount = exchangeInfo.getPageCount();
        for (int i = 0; i < pageCount; i++) {
            List<ExchangeTickersEntry> exchangeInfoList = exchangeInfo.getTickers();

            // 从DB取得已经存在的交易对
            List<Tickers> tickersList= mapperServer.getTickersListByExchange(exchange);

            List<ExchangeTickersEntry> existList = new ArrayList<>();
            if (null != exchangeInfoList && exchangeInfoList.size()> 0) {
                for (ExchangeTickersEntry exchangeInfoEntry: exchangeInfoList) {
                    if (null != tickersList && tickersList.size()> 0) {
                        for (Tickers tickers: tickersList) {
                            if (exchangeInfoEntry.getDisplay_pair_name().replace("/","_")
                                    .equals(tickers.getDisplayPairName())) {

                                existList.add(exchangeInfoEntry);
                                break;
                            }
                        }
                    }
                }
            }

            if (existList.size() > 0) {
                mapperServer.updateTickers(existList, exchange);
            }

            exchangeInfoList.removeAll(existList);

            if (null != exchangeInfoList && exchangeInfoList.size()> 0) {
                for (ExchangeTickersEntry exchangeInfoEntry: exchangeInfoList) {
                    mapperServer.addTickers(exchangeInfoEntry);
                }

            }

            if (i+1 < pageCount) {
                exchangeInfo = blockccInfoClient.getInfoApi().getExchangeTickers(ParseUtils.parseInfoMarket(exchange),i+1,size).unwrap();
            }
        }



        return false;
    }

    @Override
    public boolean updateIdexTickersByApi(String exchange) {


        IdexClient idexClient = new IdexClient();
		Map<String, Market> exchangeInfoList= idexClient.getPublicApi().getMarket();
//		for(Map.Entry<String, Market> entry : exchangeInfoList.entrySet()){
//			System.out.println("键 key ："+entry.getKey()+" 值value ："+entry.getValue());
////			TestStock.testReflect(entry.getValue());
//		}

        // 从DB取得已经存在的交易对
        List<Tickers> TickersList= mapperServer.getTickersListByExchange(exchange);

        Map<String, Market> existList = new HashMap<>();
        if (null != exchangeInfoList && exchangeInfoList.size()> 0) {
            for(Map.Entry<String, Market> entry : exchangeInfoList.entrySet()) {
                if (null != TickersList && TickersList.size() > 0) {
                    for (Tickers tickers : TickersList) {
                        // "ETH_QNT", "QNT_ETH", api返回的symbol跟数据库的相反的
                        String symbol = "";
                        if (null != entry.getKey()) {
                            String[] symbols = entry.getKey().split("_");
                            symbol = symbols[1] + "_" + symbols[0];
                        }

                        if (symbol.equals(tickers.getDisplayPairName())) {
                            existList.put(symbol, entry.getValue());
                            break;
                        }
                    }
                }
            }
        }

        if (existList.size() > 0) {
            mapperServer.updateTickersByIdexApi(existList,exchange);
        }


//        if (null != exchangeInfoList && exchangeInfoList.size()> 0) {
//            List<AlertUser> alertUsers= mapperServer.getAlertUsers();
//            for (ExchangeTickersEntry exchangeInfoEntry: exchangeInfoList) {
//                mapperServer.addTickers(exchangeInfoEntry);
//            }
//
//        }

        return false;
    }

    @Override
    public boolean updateContractAddress(String exchange){

        IdexClient idexClient = new IdexClient();
        Map<String, Currencies> currenciesList= idexClient.getPublicApi().getCurrencies();

        // 从DB取得已经存在的交易对
        List<Tickers> TickersList= mapperServer.getTickersListByExchange(exchange);

        Map<String, Currencies> existList = new HashMap<>();
        if (null != currenciesList && currenciesList.size()> 0) {
            for(Map.Entry<String, Currencies> entry : currenciesList.entrySet()) {
                if (null != TickersList && TickersList.size() > 0) {
                    for (Tickers ticker : TickersList) {
                        // CoinSymbol 和CoinName 都匹配的情况下
                        if (entry.getKey().equals(ticker.getCoinSymbol()) && entry.getValue().getName().trim().equals(ticker.getCoinName())) {
                            existList.put(ticker.getDisplayPairName(), entry.getValue());
                            break;
                            //有可能CoinName 不匹配的情况下，去做模糊匹配
                        } else if (entry.getKey().equals(ticker.getCoinSymbol())
                                && !entry.getValue().getName().equals(ticker.getCoinName())
                                && StringUtils.isEmpty(ticker.getContractAddress())) {
                            existList.put(ticker.getDisplayPairName(), entry.getValue());
                            break;
                        }
                    }
                }
            }
        }

        if (existList.size() > 0) {
            mapperServer.updateTickersContractAddress(existList,exchange);
        }

        return false;
    }

    @Override
    public boolean updateTotalSupply(String exchange){

        EthClient ethClient = new EthClient();

        // 从DB取得已经存在的交易对
        List<Tickers> TickersList= mapperServer.getTickersListByExchange(exchange);

        Map<String, Double> existList = new HashMap<>();

        if (null != TickersList && TickersList.size()> 0) {
            for (Tickers ticker: TickersList) {
                // 合约地址存在 总量为空的的情况下
//                if (StringUtils.isNotEmpty(ticker.getContractAddress()) && ticker.getTotalSupply() == null) {
                if (StringUtils.isNotEmpty(ticker.getContractAddress())) {
                    String tokenSupply = ethClient.getPublicApi().getTokenSupply(ticker.getContractAddress()).unwrap();

                    Double totalsupply = 0.0;
                    try {
                        totalsupply  = ParseUtils.parseDoubleTokenSupply(tokenSupply, ticker.getDecimals());
                    }catch (Exception e){
                        System.out.println("交易对 ："+ticker.getDisplayPairName()+" 总量为 ："+tokenSupply+" 精度 ："+ticker.getDecimals());
                    }
                    existList.put(ticker.getDisplayPairName(),totalsupply);
                }
            }
        }

        if (existList.size() > 0) {
            mapperServer.updateTickersTotalSupply(existList,exchange);
        }

        return false;
    }

}