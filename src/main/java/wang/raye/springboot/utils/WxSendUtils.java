package wang.raye.springboot.utils;

import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.blockcc.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import wang.raye.springboot.bean.PositionBean;
import wang.raye.springboot.bean.WxSendBean;
import wang.raye.springboot.bean.WxSendQuotaBean;
import wang.raye.springboot.bean.WxSendQuotaPeriodBean;
import wang.raye.springboot.model.Tickers;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class WxSendUtils {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpRequestUtils httpRequestUtils;

    /**
     * 新币发送提示
     * @param sendBean 微信发送bean
     */
    public void sendNew(WxSendBean sendBean){
//        String url = "https://sc.ftqq.com/"+SCU20744Tcae3e700d2845416dcf4b7d48fefd4fc5a68472d8b5e0+".send";
//        Map<String, Object> uriVariables = new HashMap<String, Object>();
//        uriVariables.put("text", exchange+"上新币["+symbol+"]");
//        uriVariables.put("desp", "新币["+symbol+"]目前价格:"+price);
//        try {
//            String result = restOperations.getForObject(url, String.class, uriVariables);
//        } catch (RestClientException e){
//            log.error("发新币微信时异常:"+e.getStackTrace());
//        }

        String url = "https://sc.ftqq.com/"+sendBean.getSckey()+".send";
        String text=sendBean.getExchange()+"上新币["+sendBean.getSymbol()+"]";
        String desp="新币["+sendBean.getSymbol()+"]目前价格:"+sendBean.getPrice()+ "\n";
        desp=desp+DateUtils.getToday();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("text", text);
        params.add("desp", desp);
//        Object result = httpRequestUtils.request(url,HttpMethod.GET, params);
        String result = httpRequestUtils.get(url, params);
//        log.info("微信提醒[新币]发送结果:"+result);
        log.info("上新币微信发送提醒:"+text);
    }

    /**
     * 新币发送提示
     */
    public void sendNew(String sckey, ExchangeTickersEntry exchangeTicker, CoinInfo coinInfo){

        String url = "https://sc.ftqq.com/"+sckey+".send";
        String text=exchangeTicker.getExchange_name()+"上新币["+exchangeTicker.getDisplay_pair_name()+"]";
//        String desp="新币["+newPair.getSymbol()+"]目前状态:"+newPair.getStatus()+ "\n";
//        desp=desp+"[listat:]"+newPair.getListAt()+ "\n";
//        desp=desp+"[tradeat:]"+newPair.getTradedAt()+ "\n";

        String desp = this.getMarkNewCoin(exchangeTicker, coinInfo);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("text", text);
        params.add("desp", desp);
//        Object result = httpRequestUtils.request(url,HttpMethod.GET, params);
        String result = httpRequestUtils.get(url, params);
//        log.info("微信提醒[新币]发送结果:"+result);
        log.info("上新币微信发送提醒:"+text);

    }

    /**
     * 新币发送提示
     */
    public void sendNew(String sckey, NewPairsListEntry newPair, CoinInfo coinInfo){

        String url = "https://sc.ftqq.com/"+sckey+".send";
        String text=newPair.get_id().getMarket_display_name()+"的新币["+newPair.get_id().getExchange_symbol()+"]开始交易了";

        String desp = this.getMarkNew(newPair, coinInfo);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("text", text);
        params.add("desp", desp);
//        Object result = httpRequestUtils.request(url,HttpMethod.GET, params);
        String result = httpRequestUtils.get(url, params);
//        log.info("微信提醒[新币]发送结果:"+result);
        log.info("上新币微信发送提醒:"+text);

    }

    /**
     * 开启上涨发送提示
     */
    public void sendStart(WxSendBean wxSendBean, CoinInfo coinInfo){

        String url = "https://sc.ftqq.com/"+wxSendBean.getSckey()+".send";
        String text= wxSendBean.getExchange()+"["+wxSendBean.getSymbol()+"]" +" ["+wxSendBean.getPeriod()+"]"+" ["+"上涨"+"]";// + "📈";

        String desp = this.getMarkStart(wxSendBean, coinInfo);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("text", text);
        params.add("desp", desp);

        String result = httpRequestUtils.get(url, params);

        log.info("开启上涨微信发送提醒:"+text);

    }

    /**
     * 公告发送提示
     */
    public void sendNotice(String sckey, NoticesInfoEntry noticesInfoEntry, String infoUrl){

        String url = "https://sc.ftqq.com/"+sckey+".send";
        String market = noticesInfoEntry.getFrom();
        if (StringUtils.isNotEmpty(noticesInfoEntry.getZh_name())) {
            market = noticesInfoEntry.getZh_name();
        }
        String text= "[公告提醒]"+"["+market+"]" +" ["+StringUtils.trim(noticesInfoEntry.getTitle())+"]";

        StringBuffer desp = new StringBuffer();
        desp.append(DateUtils.format(noticesInfoEntry.getTimestamp())).append("\n");
        desp.append("[原文链接]("+ infoUrl + noticesInfoEntry.get_id() +")").append("\n");

//        StringBuffer desp = new StringBuffer();
//        desp.append(" <table> ").append("\n");
//        desp.append("  <tr> ").append("\n");
//        desp.append("    <th>"+DateUtils.format(noticesInfoEntry.getTimestamp())+"   </th> ").append("\n");
//        desp.append("  </tr> ").append("\n");
//        desp.append("  <tr> ").append("\n");
//        desp.append("   <td>   "+"[原文链接]("+ infoUrl + noticesInfoEntry.get_id() +")  </td> ").append("\n");
//        desp.append("  </tr> ").append("\n");
//        desp.append(" </table> ").append("\n");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("text", text);
        params.add("desp", desp.toString());

        String result = httpRequestUtils.get(url, params);

        log.info("公告发送微信提醒:"+text);

    }

    /**
     * 指标交叉发送提示
     * @param wxSendBean 微信发送bean
     */
    public void sendCross(WxSendBean wxSendBean, CoinInfo coinInfo){
        String url = "https://sc.ftqq.com/"+wxSendBean.getSckey()+".send";
        String text= wxSendBean.getExchange()+"["+wxSendBean.getSymbol()+"]" +" ["+ParseUtils.parseCrossType(wxSendBean.getType())+"]";
        text = text+ "["+wxSendBean.getPeriod()+"]" +" ["+ParseUtils.parseCrossStatus(wxSendBean.getStatus())+"]";

        String desp = this.getMarkCross(wxSendBean, coinInfo);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("text", text);
        params.add("desp", desp);

        String result = httpRequestUtils.get(url, params);

        log.info("微信发送提醒:"+text);
    }

    private String getMarkdownDesp (WxSendBean sendBean) {
        double volume = Double.valueOf(sendBean.getPrice())*Double.valueOf(sendBean.getVolume());
        StringBuffer desp = new StringBuffer();
        desp.append(" <table> ").append("\n");
        desp.append("  <tr> ").append("\n");
        desp.append("    <th>"+sendBean.getExchange()+"   </th> ").append("\n");
        desp.append("    <th> "+sendBean.getSymbol()+"["+sendBean.getPrice()+"][量:"+ParseUtils.decimalFormat(volume,ParseUtils.TwoPattern)+"] </th> ").append("\n");
        desp.append("    <th>   "+sendBean.getType()+"  "+sendBean.getPeriod()+" ["+ParseUtils.parseCrossStatus(sendBean.getStatus())+"] *"+sendBean.getTime()+"* </th> ").append("\n");
        desp.append("  </tr> ").append("\n");

        desp.append("  <tr> ").append("\n");
        desp.append("   <td>   "+"支撑阻力位:----------"+"  </td> ").append("\n");
        for(PositionBean position :sendBean.getFibonacciList()) {
            desp.append("   <td>    "+position.getPostion()+" ["+position.getPrice()+"] </td> ").append("\n");
        }
        desp.append("  </tr> ").append("\n");

        for (WxSendQuotaBean quotaBean :sendBean.getQuotaList()) {
            desp.append("  <tr> ").append("\n");
            desp.append("   <td>   "+quotaBean.getType()+"  </td> ").append("\n");
            for(WxSendQuotaPeriodBean periodBean :quotaBean.getPeriodList()) {
                desp.append("   <td>     "+periodBean.getPeriod()+"  ["+ParseUtils.parseCrossStatus(periodBean.getStatus())+"]    </td> ").append("\n");
            }
            desp.append("  </tr> ").append("\n");
        }
        desp.append(" </table> ").append("\n");
        return  desp.toString();
    }

    private String getMarkdownDesp () {
        StringBuffer desp = new StringBuffer();
        desp.append("<table>").append("\n");
        desp.append("  <tr>").append("\n");
        desp.append("    <th >binance   </th>").append("\n");
        desp.append("    <th >    BNBBTC  [MACD]</th>").append("\n");
        desp.append("    <th >    1H  [金叉]    *2018/01/26 22:10:22*</th>").append("\n");
        desp.append("  </tr>").append("\n");
        desp.append("  <tr>").append("\n");
        desp.append("   <td>  MACD  </td>").append("\n");
        desp.append("   <td>     1H  [金叉]    *2018/01/26 22:10:22*</td>").append("\n");
        desp.append("   <td>     4H  [金叉]    *2018/01/26 22:10:22*</td>").append("\n");
        desp.append("  </tr>").append("\n");
        desp.append("  <tr>").append("\n");
        desp.append("    <td>  KDJ  </td>").append("\n");
        desp.append("    <td>    1H  [金叉]    *2018/01/26 22:10:22*</td>").append("\n");
        desp.append("    <td>    4H  [金叉]    *2018/01/26 22:10:22*</td>").append("\n");
        desp.append("  </tr>").append("\n");
        desp.append("  <tr>").append("\n");
        desp.append("    <td>  RSI  </td>").append("\n");
        desp.append("    <td>    1H  [金叉]    *2018/01/26 22:10:22*</td>").append("\n");
        desp.append("    <td>    4H  [金叉]    *2018/01/26 22:10:22*</td>").append("\n");
        desp.append("  </tr>").append("\n");
        desp.append("</table>").append("\n");
        return  desp.toString();
    }

    private String getMarkNewCoin (NewPairsEntry newPair, String[] exchangeCoins, List<Price> listPrice, double usdRate) {
        StringBuffer desp = new StringBuffer();
        desp.append(" <table> ").append("\n");
        desp.append("  <tr> ").append("\n");
        desp.append("    <th>"+newPair.getMarket_name()+"   </th> ").append("\n");
        desp.append("    <th> "+newPair.getSymbol()+" [currency]:"+newPair.getCurrency()+" [状态]:"+newPair.getStatus()+" </th> ").append("\n");
        desp.append("    <th>   [listAt]:*"+newPair.getListAt()+"*  "+" [tradedAt]:*"+newPair.getTradedAt()+"* </th> ").append("\n");
        desp.append("  </tr> ").append("\n");

        desp.append(this.getMarkPrice(listPrice, usdRate));
        desp.append(this.getMarkExchange(exchangeCoins));

        desp.append(" </table> ").append("\n");
        return  desp.toString();
    }

    private String getMarkNewCoin (ExchangeTickersEntry exchangeTicker, CoinInfo coinInfo) {
        StringBuffer desp = new StringBuffer();
        desp.append(" <table> ").append("\n");
        desp.append("  <tr> ").append("\n");
        desp.append("    <th>"+exchangeTicker.getExchange_name()+"   </th> ").append("\n");
        desp.append("    <th> "+exchangeTicker.getDisplay_pair_name()+" [状态]:"+exchangeTicker.getStatus()+" </th> ").append("\n");
        desp.append("  </tr> ").append("\n");

        desp.append(this.getMarkCoinInfo(coinInfo));

        desp.append(" </table> ").append("\n");
        return  desp.toString();
    }

    private String getMarkCross (WxSendBean wxSendBean, CoinInfo coinInfo) {
        StringBuffer desp = new StringBuffer();
        desp.append(" <table> ").append("\n");
        desp.append("  <tr> ").append("\n");
        desp.append("    <th>"+wxSendBean.getExchange() +"  </th> ").append("\n");
        desp.append("    <th> "+wxSendBean.getSymbol()+" 当前交易所价格 : " + " [" + ParseUtils.showDecimalFormat(wxSendBean.getPrice()) + "] "+" </th> ").append("\n");
        desp.append("  </tr> ").append("\n");

        desp.append(this.getMarkCoinInfo(coinInfo));

        desp.append(" </table> ").append("\n");
        return  desp.toString();
    }

    private String getMarkNew (NewPairsListEntry newPair, CoinInfo coinInfo) {
        StringBuffer desp = new StringBuffer();
        desp.append(" <table> ").append("\n");
        desp.append("  <tr> ").append("\n");
        desp.append("    <th>"+newPair.get_id().getMarket_display_name()+" ["+newPair.get_id().getExchange_symbol() +"] 交易对："+newPair.getSymbol()+"  </th> ").append("\n");
        desp.append("    <th> 交易时间："+newPair.getTrade_at()+" </th> ").append("\n");
        desp.append("    <th> 简介："+newPair.getCoin_info()+" </th> ").append("\n");
        desp.append("  </tr> ").append("\n");

        desp.append(this.getMarkCoinInfo(coinInfo));

        desp.append(" </table> ").append("\n");
        return  desp.toString();
    }

    private String getMarkStart (WxSendBean wxSendBean, CoinInfo coinInfo) {
        StringBuffer desp = new StringBuffer();
        desp.append(" <table> ").append("\n");
        desp.append("  <tr> ").append("\n");
        desp.append("    <th>"+wxSendBean.getExchange() +"  </th> ").append("\n");
        desp.append("    <th> "+wxSendBean.getSymbol()+" 当前交易所价格 : " + " [" + ParseUtils.showDecimalFormat(wxSendBean.getPrice()) + "] "+" </th> ").append("\n");
        desp.append("  </tr> ").append("\n");

        desp.append(this.getMarkCoinInfo(coinInfo));

        desp.append(" </table> ").append("\n");
        return  desp.toString();
    }

    private String getMarkPrice (List<Price> listPrice, double usdRate) {
        StringBuffer desp = new StringBuffer();
        desp.append("  <tr> ").append("\n");
        desp.append("   <td>   "+"价格信息:==="+DateUtils.getToday()+"===  </td> ").append("\n");
        if (null != listPrice && listPrice.size() > 0) {
            Price price = listPrice.get(0);
            desp.append("   <td>    name : "  +" [" + price.getName() + "] </td> ").append("\n");
            desp.append("   <td>    symbol : " + " [" + price.getSymbol() + "] </td> ").append("\n");
            DecimalFormat dfPrice  = new DecimalFormat("######0.00000");
            desp.append("   <td>    当前价格 : " + " [¥" + ParseUtils.priceDecimalFormat(price.getPrice()*usdRate) + "]  [$" + price.getPrice() + "] </td> ").append("\n");
            desp.append("   <td>    最高 : " + " [¥" + ParseUtils.priceDecimalFormat(price.getHigh()*usdRate) + "]  [$" + price.getHigh()  + "] </td> ").append("\n");
            desp.append("   <td>    最低 : " + " [¥" + ParseUtils.priceDecimalFormat(price.getLow()*usdRate) + "]  [$" + price.getLow()  + "] </td> ").append("\n");
            desp.append("   <td>    历史最高 : " + " [¥" + ParseUtils.priceDecimalFormat(price.getHist_high()*usdRate) + "]  [$" + price.getHist_high()  + "] </td> ").append("\n");
            desp.append("   <td>    历史最低 : " + " [¥" + ParseUtils.priceDecimalFormat(price.getHist_low()*usdRate) + "]  [$" + price.getHist_low()  + "] </td> ").append("\n");
            double volume = price.getVolume() * price.getPrice() / 10000;
            DecimalFormat df  = new DecimalFormat("######0.00");
            desp.append("   <td>    成交额 : " + " [$" + df.format(volume) + "万] </td> ").append("\n");
            desp.append("   <td>    小时涨幅 : " + " [" + df.format(price.getChange_hourly()) + "%] </td> ").append("\n");
            desp.append("   <td>    日涨幅 : " + " [" + df.format(price.getChange_daily()) + "%] </td> ").append("\n");
            desp.append("   <td>    周涨幅 : " + " [" + df.format(price.getChange_weekly()) + "%] </td> ").append("\n");
            desp.append("   <td>    月涨幅 : " + " [" + df.format(price.getChange_monthly()) + "%] </td> ").append("\n");
        }
        desp.append("  </tr> ").append("\n");
        return  desp.toString();
    }

    private String getMarkCoinInfo(CoinInfo coinInfo) {
        StringBuffer desp = new StringBuffer();
        desp.append("  <tr> ").append("\n");
        desp.append("   <td>   "+"价格信息:==="+DateUtils.getToday()+"===  </td> ").append("\n");
        try {
            if (null != coinInfo) {
                desp.append("   <td>    coin_id : "  +" [" + coinInfo.getId() + "] </td> ").append("\n");
                desp.append("   <td>    coin_name : "  +" [" + coinInfo.getName() + "] </td> ").append("\n");
                desp.append("   <td>    symbol : " + " [" + coinInfo.getSymbol() + "] </td> ").append("\n");
                desp.append("   <td>    当前价格 : " + " [¥" + ParseUtils.priceDecimalFormat(coinInfo.getPrice()*coinInfo.getCNY_RATE()) + "]  [$" + coinInfo.getPrice() + "] </td> ").append("\n");
                desp.append("   <td>    最高 : " + " [¥" + ParseUtils.priceDecimalFormat(coinInfo.getHigh1d()*coinInfo.getCNY_RATE())+ "]  [$" + coinInfo.getHigh1d()  + "] </td> ").append("\n");
                desp.append("   <td>    最低 : " + " [¥" + ParseUtils.priceDecimalFormat(coinInfo.getLow1d()*coinInfo.getCNY_RATE()) + "]  [$" + coinInfo.getLow1d()  + "] </td> ").append("\n");
                desp.append("   <td>    ------------------------------------- </td> ").append("\n");
                desp.append("   <td>    小时涨幅 : " + " [" + ParseUtils.normalDecimalFormat(coinInfo.getChange1h()) + "%] </td> ").append("\n");
                desp.append("   <td>    日涨幅 : " + " [" + ParseUtils.normalDecimalFormat(coinInfo.getChange1d()) + "%] </td> ").append("\n");
                desp.append("   <td>    周涨幅 : " + " [" + ParseUtils.normalDecimalFormat(coinInfo.getChange7d()) + "%] </td> ").append("\n");
                desp.append("   <td>    月涨幅 : " + " [" + ParseUtils.normalDecimalFormat(coinInfo.getChange30d()) + "%] </td> ").append("\n");
                desp.append("   <td>    ------------------------------------- </td> ").append("\n");
                desp.append("   <td>    成交额 : " + " [¥" + ParseUtils.normalDecimalFormat(coinInfo.getVolume_ex() * coinInfo.getCNY_RATE() / 10000) + "万] </td> ").append("\n");
                desp.append("   <td>    成交额排名 : " + " [" + coinInfo.getVolume_level() + "] </td> ").append("\n");
                desp.append("   <td>    流通数量 : " + " [" + ParseUtils.normalDecimalFormat(coinInfo.getAvailable_supply() / 100000000) + "亿] </td> ").append("\n");
                desp.append("   <td>    发行总量 : " + " [" + ParseUtils.normalDecimalFormat(coinInfo.getSupple() / 100000000) + "亿] </td> ").append("\n");
                desp.append("   <td>    市值 : " + " [¥" + ParseUtils.normalDecimalFormat(coinInfo.getMarketCap() * coinInfo.getCNY_RATE() / 100000000) + "亿] </td> ").append("\n");
                desp.append("   <td>    市值排名 : " + " [" + coinInfo.getLevel() + "] </td> ").append("\n\n");
                desp.append("   <td>    --***众筹价格------------------------- </td> ").append("\n");
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
                            desp.append("   <td>      "+icoPrice.getSymbol() + " : " + " [" + ParseUtils.priceDecimalFormat(icoPrice.getPrice()) + "] "+ "[" + ParseUtils.normalDecimalFormat(times) + " 倍] </td> ").append("\n");
                        }
                    }
                }
                desp.append("   <td>    --***交易所成交量占比------------------ </td> ").append("\n");
                List<CoinInfoExData> coinInfoExDataList = coinInfo.getExData();
                if (null != coinInfoExDataList && coinInfoExDataList.size() > 0) {
                    for (CoinInfoExData exData:coinInfoExDataList) {
                        desp.append("   <td>      "+exData.getName() + " : [¥" + ParseUtils.normalDecimalFormat(exData.getY() * coinInfo.getCNY_RATE() / 10000) + "万] [" + ParseUtils.normalDecimalFormat(exData.getY()/coinInfo.getVolume_ex() * 100) + "%] </td> ").append("\n");
                    }
                }
            }
        } catch (Exception e) {
            log.error("线程 编辑币种信息出错 :["+Thread.currentThread().getName()+"]marketName：" + ",symbol:" +coinInfo.getSymbol());
            log.error(e.getMessage(),e);
            e.printStackTrace();
        }

        desp.append("  </tr> ").append("\n");
        return  desp.toString();
    }

    private String getMarkExchange(String[] exchangeCoins) {
        StringBuffer desp = new StringBuffer();
        desp.append("  <tr> ").append("\n");
        desp.append("   <td>   "+"交易所信息:==="+DateUtils.getToday()+"===  </td> ").append("\n");
        if (null != exchangeCoins && exchangeCoins.length > 0) {
            for (String exchangeCoin :exchangeCoins) {
                desp.append("   <td>   "+exchangeCoin+"  </td> ").append("\n");
            }
        }
        desp.append("  </tr> ").append("\n");
        return  desp.toString();
    }


}
