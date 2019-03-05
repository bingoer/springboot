package wang.raye.springboot.utils;

import com.binance.api.client.domain.market.Candlestick;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.Candle;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.aicoin.Kline;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.blockcc.ExchangeTickersEntry;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import org.springframework.util.StringUtils;
import wang.raye.springboot.model.AlertExchange;
import wang.raye.springboot.model.Tickers;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Component
public final class ParseUtils {
    private static String wxShowPattern = "#0.0###############";

    public static String TwoPattern = "#0.0#";

    public static String pricePattern = "######0.0000000";

    public static String normalPattern = "######0.00";

    public ParseUtils() {
    }

//    public static String getDatePattern() {
//        return defaultDatePattern;
//    }

    public static String parseCrossStatus(String statusCode) {
        String status = "";
        if("11".equals(statusCode)) {
            status = "低点";
        } else if("12".equals(statusCode)) {
            status = "金叉";
        } else if("13".equals(statusCode)) {
            status = "暴涨";
        } else if("21".equals(statusCode)) {
            status = "高点";
        } else if("22".equals(statusCode)) {
            status = "死叉";
        } else if("23".equals(statusCode)) {
            status = "暴跌";
        } else if("30".equals(statusCode)) {
            status = "止盈止损";
        } else if("31".equals(statusCode)) {
            status = "反弹";
        }
        return status;
    }

    public static String parseCrossType(String typeCode) {
        String type = "";
        if("BUY".equals(typeCode)) {
            type = "指标买入";
        } else if("SELL".equals(typeCode)) {
            type = "指标卖出";
        } else if("VOLATILE".equals(typeCode)) {
            type = "波动";
        } else if("LOW".equals(typeCode)) {
            type = "低点";
        } else if("HIGH".equals(typeCode)) {
            type = "高点";
        } else if("STOP".equals(typeCode)) {
            type = "止盈止损";
        }
        return type;
    }

    /**
     * 去掉douelbe的科学计数法E
     * @param price
     * @return
     */
    public static String parsePrice(String price) {
        BigDecimal result=new BigDecimal(price+"");
        BigDecimal setScale = result.setScale(12,BigDecimal.ROUND_HALF_DOWN);
        DecimalFormat df = new DecimalFormat(wxShowPattern);
        return  df.format(setScale);
    }

    /**
     * 去掉douelbe的科学计数法E
     * @param price
     * @return
     */
    public static String parsePrice(double price) {
        BigDecimal result=new BigDecimal(price+"");
        BigDecimal setScale = result.setScale(12,BigDecimal.ROUND_HALF_DOWN);
        DecimalFormat df = new DecimalFormat(wxShowPattern);
        return  df.format(setScale);
    }

    /**
     * 去掉N/A的
     * @param price
     * @return
     */
    public static Double parseDoublePrice(String price) {
        Double result = 0.0;
        if (NumberUtils.isParsable(price)) {
            result = Double.parseDouble(price);
        }
        return  result;
    }

    /**
     * 根据小数精度换算总量
     * @param tokenSupply
     * @param decimals
     * @return
     */
    public static Double parseDoubleTokenSupply(String tokenSupply,int decimals) {
        Double result = 0.0;
        if (NumberUtils.isParsable(tokenSupply)) {
            tokenSupply = tokenSupply.substring(0,tokenSupply.length()-decimals);
            if (!StringUtils.isEmpty(tokenSupply)) {
                result = Double.parseDouble(tokenSupply);
            }
        }
        return  result;
    }

    /**
     *
     * @param value
     * @param pattern
     * @return
     */
    public static String decimalFormat(double value, String pattern) {
        Double tValue = Double.valueOf(value);
        if (null == tValue) {
            tValue = 0.0;
        }
        return  new DecimalFormat(pattern).format(new BigDecimal(tValue));
    }

    /**
     *
     * @param value
     * @return
     */
    public static String priceDecimalFormat(Double value) {
        if (null == value) {
            return  "0";
        }
        return  ParseUtils.decimalFormat(value,pricePattern);
    }

    /**
     *
     * @param value
     * @return
     */
    public static String priceDecimalFormat(double value) {
        return  ParseUtils.decimalFormat(value,pricePattern);
    }

    /**
     *
     * @param value
     * @return
     */
    public static String normalDecimalFormat(Double value) {
        if (null == value) {
            return  "0";
        }
        return  ParseUtils.decimalFormat(value,normalPattern);
    }

    /**
     *
     * @param value
     * @return
     */
    public static String normalDecimalFormat(double value) {
        return  ParseUtils.decimalFormat(value,normalPattern);
    }

    /**
     *
     * @param value
     * @return
     */
    public static String showDecimalFormat(double value) {
        return  ParseUtils.decimalFormat(value,wxShowPattern);
    }

    /**
     *
     * @param list
     * @return
     */
    public static List<Candlestick> parseCandlestick(List<Candle> list) {
        List<Candlestick> candlestickList = new ArrayList<>();
        for (Candle candle : list){
            Candlestick candlestick = new Candlestick();
            candlestick.setOpenTime(candle.getT().toLocalTime().toNanoOfDay());
            candlestick.setOpen(String.valueOf(candle.getO()));
            candlestick.setHigh(String.valueOf(candle.getH()));
            candlestick.setLow(String.valueOf(candle.getL()));
            candlestick.setClose(String.valueOf(candle.getC()));
            candlestick.setVolume(String.valueOf(candle.getV()));
            candlestick.setCloseTime(candle.getT().toLocalTime().toNanoOfDay());
//            private String quoteAssetVolume;
//            private Long numberOfTrades;
            candlestick.setTakerBuyBaseAssetVolume(String.valueOf(candle.getBV()));
//            private String takerBuyQuoteAssetVolume;

            candlestickList.add(candlestick);
        }
        return candlestickList;
    }

    /**
     *
     * @param list
     * @return
     */
    public static List<Candlestick> parseGateCandlestick(List<de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.gate.Candle> list) {
        List<Candlestick> candlestickList = new ArrayList<>();
        for (de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.gate.Candle candle : list){
            Candlestick candlestick = new Candlestick();
            candlestick.setOpenTime(candle.getTime());
            candlestick.setOpen(String.valueOf(candle.getOpen()));
            candlestick.setHigh(String.valueOf(candle.getHigh()));
            candlestick.setLow(String.valueOf(candle.getLow()));
            candlestick.setClose(String.valueOf(candle.getClose()));
            candlestick.setVolume(String.valueOf(candle.getVolume()));
            candlestick.setCloseTime(candle.getTime());
//            private String quoteAssetVolume;
//            private Long numberOfTrades;
//            candlestick.setTakerBuyBaseAssetVolume(String.valueOf(candle.getBV()));
//            private String takerBuyQuoteAssetVolume;

            candlestickList.add(candlestick);
        }
        return candlestickList;
    }

    /**
     *
     * @param list
     * @return
     */
    public static List<Kline> parseAicoinCandlestick(List<Candlestick> list) {
        List<Kline> candlestickList = new ArrayList<>();
        for (Candlestick candle : list){
            Kline kline = new Kline();
            kline.setTime(candle.getOpenTime()/1000);
            kline.setOpen(Double.valueOf(candle.getOpen()));
            kline.setClose(Double.valueOf(candle.getClose()));
            kline.setLow(Double.valueOf(candle.getLow()));
            kline.setHigh(Double.valueOf(candle.getHigh()));
            kline.setVolume(Double.valueOf(candle.getVolume()));
            candlestickList.add(kline);
        }
        return candlestickList;
    }

    public static List<Tickers> parseDbTickers(List<ExchangeTickersEntry> list) {
        List<Tickers> tickersList = new ArrayList<>();
        for (ExchangeTickersEntry entry : list){
            Tickers tickers = new Tickers();
            tickers.setTickerId(entry.get_id());
            tickers.setDisplayPairName(entry.getDisplay_pair_name());
            tickers.setCoinSymbol(entry.getCoin_symbol());
            tickers.setCoinName(entry.getCoin_name());
            tickers.setCoinId(entry.getCoin_id());
            tickers.setBaseSymbol(entry.getBase_symbol());
            tickers.setChange1d(entry.getChange1d());
            tickers.setType(entry.getType());
            tickers.setBid(entry.getBid());
            tickers.setAsk(entry.getAsk());
            tickers.setNativePrice(entry.getNative_price());
            tickers.setLow1d(entry.getLow1d());
            tickers.setHigh1d(entry.getHigh1d());
            tickers.setStatus(entry.getStatus());
            tickers.setExchangeDisplayName(entry.getExchange_display_name());
            tickers.setExchangeZhName(entry.getExchange_zh_name());
            tickers.setExchangeName(entry.getExchange_name());
            tickers.setUrl(entry.getUrl());
            tickers.setDatacenterPairName(entry.getDataCenter_pair_name());
            tickers.setTimestamps(DateUtils.format(entry.getTimestamps()));
            tickers.setEnablekline(entry.isEnableKline()?"1":"0");
            tickers.setVolume(entry.getVolume());
            tickers.setPrice(entry.getPrice());
            tickers.setPercent(entry.getPercent());
            tickersList.add(tickers);
        }
        return tickersList;
    }

    /**
     *  普通的字符串或者表情都是占位3个字节，所以utf8足够用了，
     *  但是移动端的表情符号占位是4个字节，普通的utf8就不够用了，
     *  为了应对无线互联网的机遇和挑战、避免 emoji 表情符号带来的问题、
     *  涉及无线相关的 MySQL 数据库建议都提前采用 utf8mb4 字符集，这必须要作为移动互联网行业的一个技术选型的要点。
     * @param content
     * @return
     */
    public static String emojiFormat(String content) {
        if(null == content){
            content ="";
        }
        return  content.replaceAll("[\\x{10000}-\\x{10FFFF}]", "");
    }

    /**
     *  blockcc交易所名转换
     * @param marketName
     * @return
     */
    public static String parseInfoMarket(String marketName) {
        if("gate".equals(marketName)){
            marketName = "gate-io";
        } else if("bitz".equals(marketName)){
            marketName = "bit-z";
        } else if("syex".equals(marketName)){
            marketName = "syex-io";
        }
        return  marketName;
    }

    /**
     *  blockcc交易所名转换
     * @param marketName
     * @return
     */
    public static String parseKlineMarket(String marketName) {
        if("gate-io".equals(marketName)){
            marketName = "gate";
        } else if("bit-z".equals(marketName)){
            marketName = "bitz";
        } else if("syex-io".equals(marketName)){
            marketName = "syex";
        }
        return  marketName;
    }

    /**
     * 拆分集合
     * @param <T>
     * @param resList  要拆分的集合
     * @param count    每个集合的元素个数
     * @return  返回拆分后的各个集合
     */
    public static  <T> List<List<T>> splitList(List<T> resList,int count){

        if(resList==null ||count<1)
            return  null ;
        List<List<T>> ret=new ArrayList<List<T>>();
        int size=resList.size();
        if(size<=count){ //数据量不足count指定的大小
            ret.add(resList);
        }else{
            int pre=size/count;
            int last=size%count;
            //前面pre个集合，每个大小都是count个元素
            for(int i=0;i<pre;i++){
                List<T> itemList=new ArrayList<T>();
                for(int j=0;j<count;j++){
                    itemList.add(resList.get(i*count+j));
                }
                ret.add(itemList);
            }
            //last的进行处理
            if(last>0){
                List<T> itemList=new ArrayList<T>();
                for(int i=0;i<last;i++){
                    itemList.add(resList.get(pre*count+i));
                }
                ret.add(itemList);
            }
        }
        return ret;

    }

    public static String listToString(List<String> list){
        if(list==null){
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for(String string :list) {
            if(first) {
                first=false;
            }else{
                result.append(",");
            }
            result.append(string);
        }
        return result.toString();
    }
    /**
     *  提醒消息中的跳转url
     * @param alertExchangeList
     * @param marketName
     * @param symbol
     * @return
     */
    public static String parseAlertUrl(List<AlertExchange> alertExchangeList, String marketName, String symbol) {

        String alertUrl = null;
        if (null != alertExchangeList) {
            for (AlertExchange alertExchange:alertExchangeList) {
                if (alertExchange.getUrlflg().equals("1") && alertExchange.getUrl() != null) {
                    alertUrl = alertExchange.getUrl();
                    break;
                }
            }
        }

        String result = "";
        if (null != alertUrl) {
            if ("binance".equals(marketName)) {
                result = alertUrl+symbol;
            } else if ("huobipro".equals(marketName)) {
                result = alertUrl+symbol;
            } else if ("hadax".equals(marketName)) {
                result = alertUrl+symbol;
            } else if ("gate".equals(marketName) || "gate-io".equals(marketName)) {
                result = alertUrl+symbol;
            } else if ("bittrex".equals(marketName)) {
                String[] symbolTmps = symbol.split("_");
                result = alertUrl+symbolTmps[1] + "-" + symbolTmps[0];
            } else if ("okex".equals(marketName)) {
                result = alertUrl+symbol;
            }
        }

        if (result.equals("")) {
            result = "https://www.aicoin.net.cn/chart/"+marketName+"-"+symbol.replace("_","").toLowerCase();
        }

        return  result;
    }

    /**
     * 从最新的k线数据中取得价格最低值
     * @param candlestickList
     * @param num
     * @return
     */
    public static double getMinLowPriceByLastCandles(List<Kline> candlestickList, int num) {
        double maxLow = 0.0;
        if (null!= candlestickList && candlestickList.size() > num ) {
            List<Double> lastLowList = new ArrayList<>();
            for (int i = candlestickList.size() - num; i < candlestickList.size(); i++) {
                lastLowList.add(candlestickList.get(i).getLow());
            }
            maxLow = Collections.min(lastLowList);
        }
        return maxLow;
    }

    /**
     * 从最新的k线数据中取得价格最高值
     * @param candlestickList
     * @param num
     * @return
     */
    public static double getMaxHighPriceByLastCandles(List<Kline> candlestickList, int num) {
        double maxHigh = 0.0;
        if (null!= candlestickList && candlestickList.size() > num ) {
            List<Double> lastHighList = new ArrayList<>();
            for (int i = candlestickList.size() - num; i < candlestickList.size(); i++) {
                lastHighList.add(candlestickList.get(i).getHigh());
            }
            maxHigh = Collections.max(lastHighList);
        }
        return maxHigh;
    }




}