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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import wang.raye.springboot.bean.WxSendBean;
import wang.raye.springboot.model.*;
import wang.raye.springboot.model.Tickers;
import wang.raye.springboot.model.mapper.*;
import wang.raye.springboot.server.CrossApiServer;
import wang.raye.springboot.server.InfoServer;
import wang.raye.springboot.server.MapperServer;
import wang.raye.springboot.server.PairsServer;
import wang.raye.springboot.utils.DateUtils;
import wang.raye.springboot.utils.ParseUtils;
import wang.raye.springboot.utils.WxSendUtils;
import wang.raye.springboot.utils.send.WxSendMessageUtil;

import java.util.*;

@Repository
public class PairsServerImpl implements PairsServer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PairsMapper pairsMapper;
    @Autowired
    private MapperServer mapperServer;
    @Autowired
    private InfoServer infoServer;


    @Autowired
    private WxSendUtils wxSendUtils;
    @Autowired
    private WxSendMessageUtil wxSendMessageUtil;
    @Autowired
    private AlertUserMapper alertUserMapper;



    @Override
    public boolean marketPairs() {
//        BlockccClient blockccClient = new BlockccClient();
//        List<Markets> listMarkets = blockccClient.getPublicApi().getMarkets().unwrap();
//
////        List<MarketPairs> listMarketPairs = blockccClient.getPublicApi().getMarketPairs().unwrap();
//
//    //TODO
//
//        if (null != listMarkets && listMarkets.size() > 0) {
//            for (Markets market: listMarkets) {
//                Market listMarket = blockccClient.getPublicApi().getMarket(ParseUtils.parseMarket(market.getName())).unwrap();
//                String[] symbolPairs = listMarket.getSymbol_pairs();
//                if (null != symbolPairs && symbolPairs.length > 0) {
//                    for (String symbolPair : symbolPairs) {
//                        if (this.getPairs(market.getName()).size() < 1) {
//                            this.addPairs(market.getName(), symbolPair);
////                            this.sendNew(marketPairs.getName(), symbolPair, "");
//                        }
//                    }
//                }
//            }
//        }

        return false;
    }

    @Override
    public boolean marketPairs(String marketName) {
        BlockccClient blockccClient = new BlockccClient();
        // 调用api取得交易对信息
        Market market = blockccClient.getPublicApi().getMarket(ParseUtils.parseInfoMarket(marketName)).unwrap();
        // 调用api获取汇率
        double usdRate=this.getUsdRate(blockccClient);
        // 交易对
        String[] symbolPairs = market.getSymbol_pairs();
        // 交易对转成list
        List<String> symbolList = new ArrayList(Arrays.asList(symbolPairs));
        // 从DB取得已经存在的交易对
        List<Pairs> pairsList= this.getPairs(marketName);
        // DB交易对转换stringList
        List<String> pairList = new ArrayList<>();
        for (Pairs pair:pairsList) {
            pairList.add(pair.getSymbol());
        }
        if (null != symbolPairs && symbolPairs.length > 0) {
            //取得api交易对和DB交易对的差集
            symbolList.removeAll(pairList);
            for (String symbol:symbolList) {
                this.addPairs(marketName, symbol);

                // 通过api获取新币价格信息
                List<Price> listPrice = null;
                try{
                    listPrice = blockccClient.getPublicApi().getPrice(symbol.split("_")[0]).unwrap();
                }
                catch (Exception e){
                    log.error(e.getMessage(),e);
                }

                NewPairsEntry newPair = new NewPairsEntry();
                newPair.setMarket_name(market.getName());
                newPair.setSymbol(symbol);
                newPair.setStatus("list");
                newPair.setListAt(DateUtils.getToday());
//                this.sendNew(newPair, listPrice, usdRate);
            }
        }
        return false;
    }

    @Override
    public boolean newPairs() {
        BlockccClient blockccClient = new BlockccClient();
        BlockccInfoClient blockccInfoClient = new BlockccInfoClient();
        // 调用api获取新币信息
//        List<NewPairsEntry> newPairList = blockccClient.getPublicApi().getNewPairs().unwrap().getList();
        List<NewPairsListEntry> newPairsList= blockccInfoClient.getInfoApi().getNewPairsList(10).unwrap().getList();

        // 调用api获取汇率
//        double usdRate=this.getUsdRate(blockccClient);

        if (null != newPairsList && newPairsList.size() > 0) {

            List<AlertUser> alertUsers= mapperServer.getAlertUsers();
            List<AlertUser> alertWorkWxUsers= mapperServer.getAlertWorkWxUsers();

            for (NewPairsListEntry newPair : newPairsList) {


                List<PairsNew>  pairList = mapperServer.getPairsNew(newPair.get_id().getMarket_display_name(),newPair.get_id().getExchange_symbol_id());

                boolean isNew = false;
                if (null == pairList || pairList.size() < 1) {
                    if (null != newPair.getTrade_at()) {
                        isNew = true;
                    }
                }

                if (isNew) {
                    mapperServer.addPairsNew(newPair);
                    try {
                        this.sendNew(alertWorkWxUsers,newPair, infoServer.getCoinInfo(blockccInfoClient,newPair.get_id().getExchange_symbol_id()));
                        this.sendNew2(alertUsers,newPair, infoServer.getCoinInfo(blockccInfoClient,newPair.get_id().getExchange_symbol_id()));
                    } catch (Exception e) {
                        log.error("线程 发送提醒信息出错 :["+Thread.currentThread().getName()+"]marketName：" + ",symbol:" +newPair.get_id().getExchange_symbol());
                        log.error(e.getMessage(),e);
                        e.printStackTrace();
                    }
                }

            }
        }
        return false;
    }

    private List<Pairs> getPairs(String market) {
        PairsCriteria cond = new PairsCriteria();
        cond.createCriteria()
                .andMarketEqualTo(market);
//                .andSymbolEqualTo(symbol);
        return pairsMapper.selectByExample(cond);
    }

    private List<Pairs> getPairs(String market, String symbol) {
        PairsCriteria cond = new PairsCriteria();
        cond.createCriteria()
                .andMarketEqualTo(market)
                .andSymbolEqualTo(symbol);
        return pairsMapper.selectByExample(cond);
    }

    /**
     * 添加新交易对
     * @param market
     * @param symbol
     */
    private void addPairs(String market, String symbol) {
        Pairs pairs = new Pairs();
        pairs.setMarket(market);
        if(market.equals("gate")) {
            pairs.setMarketAlias("gate-io");
        } else {
            pairs.setMarketAlias(market);
        }

        pairs.setSymbol(symbol);
        pairs.setSymbolAlias(symbol.toLowerCase().replace("_",""));

        String[] symbols =  symbol.split("_");
        if(symbols.length > 1){
            pairs.setCurrency(symbols[1]);
        }
        pairs.setHasKilne("0");
        pairs.setListat(DateUtils.getToday());
        pairsMapper.insert(pairs);
    }

    /**
     * 添加新交易对
     */
    private void addPairs(NewPairsEntry newPair) {
        Pairs pairs = new Pairs();
        pairs.setMarket(ParseUtils.parseInfoMarket(newPair.getMarket_name()));
        pairs.setMarketAlias(newPair.getMarket_name());

        pairs.setSymbol(newPair.getSymbol());
        pairs.setSymbolAlias(newPair.getSymbol().toLowerCase().replace("_",""));

        String[] symbols =  newPair.getSymbol().split("_");
        if(symbols.length > 1){
            pairs.setCurrency(symbols[1]);
        }
        pairs.setHasKilne("0");
        pairs.setStatus(newPair.getStatus());
        pairs.setListat(newPair.getListAt());
        pairs.setTradedat(newPair.getTradedAt().toString());
        pairsMapper.insert(pairs);
    }

    /**
     * 发送上线新币提醒

     */
    private void sendNew2(List<AlertUser> alertUsers, NewPairsListEntry newPair, CoinInfo coinInfo){
        //发微信
//        WxSendBean sendBean = new WxSendBean();
//
//        sendBean.setExchange(market);
//        sendBean.setSymbol(symbol);
//        sendBean.setPrice(price);

        for(AlertUser alertUser : alertUsers) {
            wxSendUtils.sendNew(alertUser.getSckey(), newPair, coinInfo);
        }
    }

    /**
     * 发送上线新币提醒

     */
    private void sendNew(List<AlertUser> alertUsers, NewPairsListEntry newPair, CoinInfo coinInfo){
        String mobile = "";
        for (int i = 0; i < alertUsers.size(); i++) {
            if (i == 0) {
                mobile = alertUsers.get(i).getMobile();
            } else {
                mobile += "/" + alertUsers.get(i).getMobile();
            }
        }

        String now = DateUtils.getToday();
        String title = "【"+newPair.get_id().getMarket_display_name()+"】新币["+newPair.get_id().getExchange_symbol()+"]｜交易时间："+newPair.getTrade_at();

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
        desp.append("<div class=\\\"gray\\\">");
        desp.append("【众筹价格】:<br>");

        CoinInfoIco coinInfoIco = coinInfo.getIco();
        if (null != coinInfoIco) {
            List<CoinInfoIcoPrice> icoPriceList = coinInfoIco.getIcoPrice();
            if (null != icoPriceList && icoPriceList.size() > 0) {
                for (CoinInfoIcoPrice icoPrice:icoPriceList) {
                    double times = 0;
                    if (icoPrice.getPrice() != null) {
                        if ("USD".equals(icoPrice.getSymbol())) {
                            times = coinInfo.getPrice() / icoPrice.getPrice();
                        } else if ("BTC".equals(icoPrice.getSymbol())) {
                            times = coinInfo.getPrice() * coinInfo.getBTC_RATE() / icoPrice.getPrice();
                        } else if ("ETH".equals(icoPrice.getSymbol())) {
                            times = coinInfo.getPrice() * coinInfo.getETH_RATE() / icoPrice.getPrice();
                        }
                    }
                    desp.append(""+icoPrice.getSymbol() + " : " + " [" + ParseUtils.priceDecimalFormat(icoPrice.getPrice()) + "] "+ "[" + ParseUtils.normalDecimalFormat(times) + " 倍]<br>");
                }
            }
        }
        desp.append("</div>");
        desp.append("简介："+newPair.getCoin_info());

        String url="https://www.baidu.com";
        String secretId = WxSendMessageUtil.SECRETID_NEWCOIN;
        String agentId = WxSendMessageUtil.AGENTID_NEWCOIN;

        wxSendMessageUtil.sendMessageCard(title, desp.toString(), url, secretId, agentId, mobile);
    }

    private List<AlertUser> getAlertUsers() {
        AlertUserCriteria cond = new AlertUserCriteria();
        cond.createCriteria()
                .andOpenflgEqualTo("1");
        return alertUserMapper.selectByExample(cond);
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
}