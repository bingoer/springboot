package wang.raye.springboot.controller;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import de.elbatya.cryptocoins.bittrexclient.AicoinClient;
import de.elbatya.cryptocoins.bittrexclient.BinanceClient;
import de.elbatya.cryptocoins.bittrexclient.BlockccClient;
import de.elbatya.cryptocoins.bittrexclient.BlockccInfoClient;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.CommonInterval;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.CommonIntervalMap;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.aicoin.Kline;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.binance.TickerStatistics;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.blockcc.ExchangeTickers;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.blockcc.ExchangeTickersEntry;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.blockcc.Market;
import net.minidev.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import wang.raye.springboot.bean.*;
import wang.raye.springboot.model.*;
import wang.raye.springboot.server.*;
import wang.raye.springboot.utils.DateUtils;
import wang.raye.springboot.utils.ParseUtils;
import wang.raye.springboot.utils.QuotaUtils;

import java.util.*;

/**
 * @author xiaodongdong
 * @description: 测试controller跳转到jsp页面
 * @create 2017-11-13 11:36
 **/

@Controller
public class PageController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // 从 application.yml 中读取配置，如取不到默认值为Hello Jsp
    @Value("${application.hello:Hello Jsp}")
    private String hello = "Hello Jsp";

    @Autowired
    private QuotaUtils quotaUtils;
    @Autowired
    private PageServer pageServer;
    @Autowired
    private CrossServer crossServer;
    @Autowired
    private MapperServer mapperServer;
    @Autowired
    private InfoServer infoServer;
    @Autowired
    private ExchangeInfoServer exchangeInfoServer;
    @Autowired
    private QuotaInfoServer quotaInfoServer;

    /**
     * 默认页<br/>
     * @RequestMapping("/") 和 @RequestMapping 是有区别的
     * 如果不写参数，则为全局默认页，加入输入404页面，也会自动访问到这个页面。
     * 如果加了参数“/”，则只认为是根页面。
     * 可以通过localhost:8080或者localhost:8080/index访问该方法
     */
    @RequestMapping(value = {"/","/index"})
    public String index(ModelMap model){
        // 直接返回字符串，框架默认会去 spring.view.prefix 目录下的 （index拼接spring.view.suffix）页面
        // 本例为 /WEB-INF/jsp/index.html
        model.addAttribute("time", new Date());
        model.addAttribute("message", this.hello);
        return "index";
    }

    @RequestMapping(value = {"/member_list"})
    public String memberList(ModelMap model){
        return "member-list";
    }

    @RequestMapping(value = {"/article_add"})
    public String articleAdd(ModelMap model){
        return "article-add";
    }

    @RequestMapping(value = {"/article_list"})
    public String articleList(ModelMap model){
        return "article-list";
    }

    @RequestMapping(value = {"/picture_add"})
    public String pictureAdd(ModelMap model){
        return "picture-add";
    }

    @RequestMapping(value = {"/picture_list"})
    public String pictureList(ModelMap model){
        return "picture-list";
    }

    @RequestMapping(value = {"/product_add"})
    public String productAdd(ModelMap model){
        return "product-add";
    }

    @RequestMapping(value = {"/product_brand"})
    public String productBrand(ModelMap model){
        return "product-brand";
    }

    @RequestMapping(value = {"/product_category"})
    public String productCategory(ModelMap model){
        return "product-category";
    }

    @RequestMapping(value = {"/product_list"})
    public String productList(ModelMap model){
        return "product-list";
    }

    @RequestMapping(value = {"/feedback_list"})
    public String feedbackList(ModelMap model){
        return "feedback-list";
    }

    @RequestMapping(value = {"/member_add"})
    public String memberAdd(ModelMap model){
        return "member-add";
    }

    @RequestMapping(value = {"/member_del"})
    public String memberDel(ModelMap model){
        return "member-del";
    }

    @RequestMapping(value = {"/member_level"})
    public String memberLevel(ModelMap model){
        return "member-level";
    }

    @RequestMapping(value = {"/member_scoreoperation"})
    public String memberScoreoperation(ModelMap model){
        return "member-scoreoperation";
    }

    @RequestMapping(value = {"/member_record_browse"})
    public String memberRecordBrowse(ModelMap model){
        return "member-record-browse";
    }

    @RequestMapping(value = {"/member_record_download"})
    public String memberRecordDownload(ModelMap model){
        return "member-record-download";
    }

    @RequestMapping(value = {"/member_record_share"})
    public String memberRecordShare(ModelMap model){
        return "member-record-share";
    }

    @RequestMapping(value = {"/welcome"})
    public String welcome(ModelMap model){
        model.addAttribute("time", new Date());
        model.addAttribute("message", this.hello);
        return "welcome";
    }

    @RequestMapping(value = {"/admin_list"})
    public String adminList(ModelMap model){
        return "admin-list";
    }

    @RequestMapping(value = {"/admin_permission"})
    public String adminPermission(ModelMap model){
        return "admin-permission";
    }

    @RequestMapping(value = {"/admin_role"})
    public String adminRole(ModelMap model){
        return "admin-role";
    }

    @RequestMapping(value = {"/echarts_1"})
    public String echarts1(ModelMap model, @RequestParam(value = "exchange", required = false, defaultValue = "binance") String exchange,
                           @RequestParam(value = "symbol", required = false, defaultValue = "DLT") String symbol,
                           @RequestParam(value = "base", required = false, defaultValue = "BTC") String base,
                           @RequestParam(value = "period", required = false, defaultValue = "TWELVE_HOURLY") String period){

        List<Kline> candlestickList = null;
        if (exchange.equals("binance1")) {
            BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("API-KEY", "SECRET");
            BinanceApiRestClient client = factory.newRestClient();
            //调api获取k线
//        List<Candlestick> binanceList = client.getCandlestickBars("BNBBTC", CandlestickInterval.TWO_HOURLY);
            List<Candlestick> binanceList = client.getCandlestickBars(symbol+base, CandlestickInterval.valueOf(period));
            candlestickList = ParseUtils.parseAicoinCandlestick(binanceList);
        } else {
            String aicoin_symbol = (exchange + symbol + base).toLowerCase();
            // aicoin的周线
            int step = CommonIntervalMap.ENUMMAP.get(CommonInterval.valueOf(period));
            AicoinClient aicoinClient = new AicoinClient();
            candlestickList = aicoinClient.getPublicApi().getKline(aicoin_symbol, step).unwrap();
        }

        List<MacdBean> macdBeanList =  quotaUtils.getMACD(12,26,9,candlestickList);
        List<BollBean> bollBeanList =  quotaUtils.getBoll(20,2,candlestickList);
        List<KdjBean> kdjBeanList = quotaUtils.getKDJ(9,3,3,candlestickList);
        List<VolumeBean> volBeanList = quotaUtils.getVolume(5,10,candlestickList);
        List<MaBean> maBeanList = quotaUtils.getMa(5,10,30,120,candlestickList);
        List<RsiBean> rsiBeanList = quotaUtils.getRsi(6, 12, 24, candlestickList);
        List<DmiBean> dmiBeanList = quotaUtils.getDmi(14, 6, candlestickList);
        List<VrBean> vrBeanList = quotaUtils.getVr(26, 6, candlestickList);
        List<SarBean> sarBeanList = quotaUtils.getSar(candlestickList);
        List<DojiBean> dojiBeanList = quotaUtils.getDoji(candlestickList,bollBeanList);

        QuotaListBean quotaListBean = new QuotaListBean();
        quotaListBean.setMacdBeanList(macdBeanList);
        quotaListBean.setBollBeanList(bollBeanList);
        quotaListBean.setKdjBeanList(kdjBeanList);
        quotaListBean.setVolBeanList(volBeanList);
        quotaListBean.setMaBeanList(maBeanList);
        quotaListBean.setRsiBeanList(rsiBeanList);
        quotaListBean.setDmiBeanList(dmiBeanList);
        quotaListBean.setVrBeanList(vrBeanList);
        quotaListBean.setSarBeanList(sarBeanList);
        quotaListBean.setDojiBeanList(dojiBeanList);

        List<AlertSetting> alertSettingList = mapperServer.getAlertSettingByPeriod(CommonInterval.valueOf(period).getIntervalId());

        PointListBean pointListBean = pageServer.getPointListByRSI(candlestickList, quotaListBean, alertSettingList);

        List<Tickers> tickers = pageServer.getTickersByExchange(exchange,base);

// TODO       this.findMissCross (tickers, client,base,period,alertSettingList);

        model.addAttribute("tickers", tickers);

        model.addAttribute("kline", candlestickList);
        model.addAttribute("low", pointListBean.getLowList());
        model.addAttribute("high", pointListBean.getHighList());
        model.addAttribute("stopLimit", pointListBean.getStopLimitList());
        model.addAttribute("retrace", pointListBean.getRetraceList());
        model.addAttribute("vol", volBeanList);
        model.addAttribute("macd", macdBeanList);
//        model.addAttribute("kdj", kdjList);
        model.addAttribute("kdj", kdjBeanList);
        model.addAttribute("rsi", rsiBeanList);

        model.addAttribute("dmi", dmiBeanList);
        model.addAttribute("vr", vrBeanList);
        model.addAttribute("sar", sarBeanList);

        model.addAttribute("exchange", exchange);
        model.addAttribute("symbol", symbol);
        model.addAttribute("base", base);
        model.addAttribute("period", period);

        return "echarts-1";
    }

    @RequestMapping(value = {"/point_list"})
    public String pointList(ModelMap model, @RequestParam(value = "exchange", required = false, defaultValue = "binance") String exchange,
                            @RequestParam(value = "symbol", required = false, defaultValue = "MCO") String symbol,
                            @RequestParam(value = "base", required = false, defaultValue = "BTC") String base,
                            @RequestParam(value = "period", required = false, defaultValue = "TWO_HOURLY") String period,
                            @RequestParam(value = "status", required = false, defaultValue = "11") String status,
                            @RequestParam(value = "quantType", required = false, defaultValue = "FREE") String quantType){

        TradePoint tradePoint = new TradePoint();
        tradePoint.setExchange(exchange);
        tradePoint.setSymbol(symbol);
        tradePoint.setBase(base);
        if (!StringUtils.isNoneEmpty(period)) {
            tradePoint.setPeriod(CommonInterval.valueOf(period).getIntervalId());
        }
        tradePoint.setStatus(status);
        tradePoint.setQuantType(quantType);

        List<TradePoint> tradePointList =  mapperServer.getTradePoint(tradePoint);

        model.addAttribute("tradePointList", tradePointList);

        model.addAttribute("exchange", exchange);
        model.addAttribute("symbol", symbol);
        model.addAttribute("base", base);
        model.addAttribute("period", period);
        model.addAttribute("status", status);
        model.addAttribute("quantType", quantType);

        return "point-list";
    }

    @RequestMapping(value = {"/point_update_quota"})
    public String pointUpdateQuota(ModelMap model, @RequestParam(value = "exchange", required = false, defaultValue = "binance") String exchange,
                                   @RequestParam(value = "symbol", required = false, defaultValue = "MCO") String symbol,
                                   @RequestParam(value = "base", required = false, defaultValue = "BTC") String base,
                                   @RequestParam(value = "period", required = false, defaultValue = "TWO_HOURLY") String period,
                                   @RequestParam(value = "status", required = false, defaultValue = "11") String status,
                                   @RequestParam(value = "quantType", required = false, defaultValue = "FREE") String quantType){

        quotaInfoServer.updateTradePointQuota(exchange, symbol, base, period, status,quantType);

        return "redirect:/point_list";
    }

    @RequestMapping(value = {"/exchange_info"})
    public String exchangeInfo(ModelMap model, @RequestParam(value = "exchange", required = false, defaultValue = "idex") String exchange){

        // 从DB取得已经存在的交易对
        List<Tickers> tickers= mapperServer.getTickersListByExchange(exchange);
        for (Tickers ticker:tickers) {
            if (null != ticker.getNativePrice() && null != ticker.getTotalSupply()) {
                ticker.setMarketcap(ticker.getNativePrice() * ticker.getTotalSupply());
            }
            if (null != ticker.getBid() && null != ticker.getTotalSupply()) {
                ticker.setChange7d(ticker.getBid() * ticker.getTotalSupply());
            }
        }

        model.addAttribute("tickers", tickers);
        model.addAttribute("exchange", exchange);
        return "exchange-info";
    }

    @RequestMapping(value = {"/exchange_update"})
    public String exchangeUpdate(ModelMap model, @RequestParam(value = "exchange", required = false, defaultValue = "idex") String exchange){

        exchangeInfoServer.updateTickers(exchange);

        return "redirect:/exchange_info";
    }

    @RequestMapping(value = {"/exchange_update_api_idex"})
    public String exchangeUpdateApiIdex(ModelMap model, @RequestParam(value = "exchange", required = false, defaultValue = "idex") String exchange){

        exchangeInfoServer.updateIdexTickersByApi(exchange);

        return "redirect:/exchange_info";
    }

    @RequestMapping(value = {"/exchange_update_contract_address"})
    public String exchangeUpdateContractAddress(ModelMap model, @RequestParam(value = "exchange", required = false, defaultValue = "idex") String exchange){

        exchangeInfoServer.updateContractAddress(exchange);

        return "redirect:/exchange_info";
    }

    @RequestMapping(value = {"/exchange_update_total_supply"})
    public String exchangeUpdateTotalSupply(ModelMap model, @RequestParam(value = "exchange", required = false, defaultValue = "idex") String exchange){

        exchangeInfoServer.updateTotalSupply(exchange);

        return "redirect:/exchange_info";
    }

    @RequestMapping(value = {"/charts_1"})
    public String charts1(ModelMap model){
        return "charts-1";
    }

    @RequestMapping(value = {"/charts_2"})
    public String charts2(ModelMap model){
        return "charts-2";
    }

    @RequestMapping(value = {"/charts_3"})
    public String charts3(ModelMap model){
        return "charts-3";
    }

    @RequestMapping(value = {"/charts_4"})
    public String charts4(ModelMap model){
        return "charts-4";
    }

    @RequestMapping(value = {"/charts_5"})
    public String charts5(ModelMap model){
        return "charts-5";
    }

    @RequestMapping(value = {"/charts_6"})
    public String charts6(ModelMap model){
        return "charts-6";
    }

    @RequestMapping(value = {"/charts_7"})
    public String charts7(ModelMap model){
        return "charts-7";
    }

    @RequestMapping(value = {"/charts_kline"})
    public String chartsKline(ModelMap model){
        return "charts-kline";
    }

    @RequestMapping(value = {"/system_base"})
    public String systemBase(ModelMap model){
        return "system-base";
    }

    @RequestMapping(value = {"/system_category"})
    public String systemCategory(ModelMap model){
        return "system-category";
    }

    @RequestMapping(value = {"/system_data"})
    public String systemData(ModelMap model){
        return "system-data";
    }

    @RequestMapping(value = {"/system_shielding"})
    public String systemShielding(ModelMap model){
        return "system-shielding";
    }

    @RequestMapping(value = {"/system_log"})
    public String systemLog(ModelMap model){
        return "system-log";
    }

    @RequestMapping(value = {"/system_tools"})
    public String systemTools(ModelMap model){
        return "system-tools";
    }


    @RequestMapping(value = {"/cross_analysis"})
    public String crossList(ModelMap model, @RequestParam(value = "exchange", required = false, defaultValue = "") String exchange){
        model.addAttribute("exchange", exchange);
        return "cross-analysis";
    }

    @RequestMapping(value = {"/cross_list"})
    public String crossAnalysis(ModelMap model, @RequestParam(value = "exchange", required = false, defaultValue = "binance") String exchange,
                                @RequestParam(value = "status", required = false, defaultValue = "2") String status,
                                @RequestParam(value = "period", required = false, defaultValue = "TWO_HOURLY") String period){

        List<AnalysisBean> infolist = new ArrayList<>();

        String comomPeriod = period;
        if (!"-".equals(period)) {
            comomPeriod = CommonInterval.valueOf(period).getIntervalId();
        }

        List<Analysis> analysisList = mapperServer.searchAnalysis(exchange, comomPeriod, status);


        int len = analysisList.size();
//        if (len > 15) {
//            len = 15;
//        }

        List<MacdCross> maList = mapperServer.getMacdCross(exchange, "", comomPeriod, "MA");

        List<TickerStatistics> tickerStatisticsList = null;
        List<Tickers> tickersList = null;
//        List<ExchangeTickersEntry> tickersList = null;
        double btcUsdtPrice = 0;
//        double usdtRate = infoServer.getUsdRate();
        double usdtRate = 6.9;
        if ("binance".equals(exchange)) {
            try {
                BinanceClient binanceClient = new BinanceClient();
                tickerStatisticsList = binanceClient.getPublicApi().get24HrPriceStatistics();
                for (TickerStatistics tickerStatistics: tickerStatisticsList) {
                    if (tickerStatistics.getSymbol().equals("BTCUSDT")) {
                        btcUsdtPrice = tickerStatistics.getLastPrice();
                        break;
                    }
                }
            }catch (Exception e){
                log.error(e.getMessage(),e);
            }
        } else {
//            if ("hadax".equals(exchange)) {
//                exchange = "binance";
//            }
            BlockccInfoClient blockccInfoClient = new BlockccInfoClient();
            // 调用api取得交易对信息
            ExchangeTickers exchangeInfo = blockccInfoClient.getInfoApi().getExchangeTickers(ParseUtils.parseInfoMarket(exchange),0,700).unwrap();

            tickersList = ParseUtils.parseDbTickers(exchangeInfo.getTickers());

//            tickersList = mapperServer.getTickersListByExchange(exchange);
        }


        for (int i = 0; i < len; i++) {
            AnalysisBean bean = new AnalysisBean();
            bean.setId(analysisList.get(i).getId());
            bean.setExchange(analysisList.get(i).getExchange());
            bean.setSymbol(analysisList.get(i).getSymbol());
//            bean.setType(analysisList.get(i).getType());
//            if ("2".equals(analysisList.get(i).getStatus())) {
//                bean.setStatus("金叉");
//            } else if ("4".equals(analysisList.get(i).getStatus())){
//                bean.setStatus("死叉");
//            }
            bean.setStatus(analysisList.get(i).getStatus());
            bean.setPrice(analysisList.get(i).getPrice());
            bean.setPeriod(analysisList.get(i).getPeriod());
            bean.setKlineTime(analysisList.get(i).getKlineTime());
            bean.setCreateTime(DateUtils.format(analysisList.get(i).getCreateTime()));

            String klineUrl = "https://www.aicoin.net.cn/symbols/";
            klineUrl = klineUrl + bean.getExchange() + bean.getSymbol().replace("_","").toLowerCase();
            bean.setKlineUrl(klineUrl);

            if ("binance".equals(exchange) && null != tickerStatisticsList && tickerStatisticsList.size() > 0) {
                for (TickerStatistics tickerStatistics:tickerStatisticsList) {
                    if (tickerStatistics.getSymbol().equals(bean.getSymbol().replace("_",""))) {
                        double change = (tickerStatistics.getLastPrice() / analysisList.get(i).getPrice() - 1) * 100;
                        bean.setChange(change);
                        bean.setVolume(tickerStatistics.getQuoteVolume());
                        bean.setCurPrice(tickerStatistics.getLastPrice());
                        bean.setHighPrice(tickerStatistics.getHighPrice());
                        bean.setLowPrice(tickerStatistics.getLowPrice());
                        bean.setPriceChangePercent(tickerStatistics.getPriceChangePercent());
                        if (tickerStatistics.getSymbol().endsWith("USDT")) {
                            bean.setPriceRmb(bean.getCurPrice() * usdtRate);
                        } else if (tickerStatistics.getSymbol().endsWith("BTC")) {
                            bean.setPriceRmb(bean.getCurPrice() * usdtRate * btcUsdtPrice);
                        }

                        break;
                    }
                }
            } else {
                if (null != tickersList && tickersList.size() > 0) {
                    for (Tickers tickers : tickersList) {
                        if (tickers.getDisplayPairName().equals(bean.getSymbol().replace("_","/"))) {
                            double change = (tickers.getNativePrice() / analysisList.get(i).getPrice() - 1) * 100;
                            bean.setChange(change);
                            bean.setVolume(tickers.getVolume());
                            bean.setCurPrice(tickers.getNativePrice());
                            bean.setHighPrice(tickers.getHigh1d());
                            bean.setLowPrice(tickers.getLow1d());
                            bean.setPriceChangePercent(tickers.getChange1d());
                            bean.setPriceRmb(tickers.getPrice() * usdtRate);
                            break;
                        }
                    }
                }
            }

            if (null != maList && maList.size() > 0) {
                for (MacdCross ma:maList) {
                    if (ma.getSymbol().equals(bean.getSymbol())) {
                        bean.setMa10(ma.getQuota2());
                        break;
                    }
                }
            }

            infolist.add(bean);
        }


        model.addAttribute("exchange", exchange);
        model.addAttribute("status", status);
        model.addAttribute("period", period);
        model.addAttribute("infolist", infolist);
        return "cross-list";
    }

    private void findMissCross (List<Tickers> tickers, BinanceApiRestClient client,String base,String period,List<AlertSetting> alertSettingList) {
        for (Tickers ticker:tickers) {
            try {
            if (!ticker.getBaseSymbol().equals(base)) {
                continue;
            }
            List<Candlestick> binanceList = client.getCandlestickBars(ticker.getDisplayPairName().replace("_",""), CandlestickInterval.valueOf(period));
            List<Kline> candlestickList = ParseUtils.parseAicoinCandlestick(binanceList);

            List<MacdBean> macdBeanList =  quotaUtils.getMACD(12,26,9,candlestickList);
            List<BollBean> bollBeanList =  quotaUtils.getBoll(20,2,candlestickList);
            List<KdjBean> kdjBeanList = quotaUtils.getKDJ(9,3,3,candlestickList);
            List<VolumeBean> volBeanList = quotaUtils.getVolume(5,10,candlestickList);
//        List<Double> ma5List = quotaUtils.countMA(5, candlestickList);
//        List<Double> ma10List = quotaUtils.countMA(10, candlestickList);
//        List<Double> ma30List = quotaUtils.countMA(30, candlestickList);
            List<MaBean> maBeanList = quotaUtils.getMa(5,10,30,120,candlestickList);

//        List<Double> rsi1= quotaUtils.getRsi(6,candlestickList);
//        List<Double> rsi2= quotaUtils.getRsi(12,candlestickList);
//        List<Double> rsi3= quotaUtils.getRsi(24,candlestickList);
            List<RsiBean> rsiBeanList = quotaUtils.getRsi(6, 12, 24, candlestickList);

            List<DmiBean> dmiBeanList = quotaUtils.getDmi(14, 6, candlestickList);

            List<VrBean> vrBeanList = quotaUtils.getVr(26, 6, candlestickList);
            List<SarBean> sarBeanList = quotaUtils.getSar(candlestickList);
            for (int i = 0; i < candlestickList.size(); i++) {
                if (i < 10) {
                    continue;
                }
                //===================交叉判断====================
                boolean isCross = false;
                MacdBean macdBean = macdBeanList.get(i);
                macdBean.setDifPre(macdBeanList.get(i-1).getDif());
                KdjBean KdjBean = kdjBeanList.get(i);
                KdjBean.setJPre(kdjBeanList.get(i-1).getJ());

                BollBean bollBean = bollBeanList.get(i);
                bollBean.setLbPre(bollBeanList.get(i-1).getLb());

                DmiBean dmiBean = dmiBeanList.get(i);
                dmiBean.setAdxPre(dmiBeanList.get(i-1).getAdx());

                QuotaBean quotaBean = new QuotaBean();
                quotaBean.setMacdBean(macdBeanList.get(i));
                quotaBean.setKdjBean(kdjBeanList.get(i));
                quotaBean.setBollBean(bollBeanList.get(i));
                quotaBean.setRsiBean(rsiBeanList.get(i));
//							quotaBean.setDojiBean();
                quotaBean.setVolumeBean(volBeanList.get(i));
                quotaBean.setMaBean(maBeanList.get(i));
                quotaBean.setVrBean(vrBeanList.get(i));
                quotaBean.setDmiBean(dmiBeanList.get(i));
                quotaBean.setSarBean(sarBeanList.get(i));
//                isCross = crossServer.getCross(alertSettingList, quotaBean, candlestickList.get(i).getClose());
//                boolean isHighCross = crossServer.getCross(alertSettingList, quotaBean, candlestickList.get(i).getHigh());
                boolean isHighCross = false;
                if (isHighCross && !isCross) {
                    System.out.print("[MISS]  SYMBOL:"+ticker.getCoinSymbol()+ "   " + DateUtils.format(candlestickList.get(i).getTime()));
                    System.out.print("  HIGH:"+candlestickList.get(i).getHigh());
                    System.out.print("  CLOSE:"+candlestickList.get(i).getClose());
                    double changeHigh = candlestickList.get(i).getHigh()/candlestickList.get(i).getClose()-1;
                    System.out.println("  CHANGE:"+changeHigh);
                }

            }


                Thread.sleep(300);
            } catch (Exception e) {
                log.error("线程sleep 出错 :["+Thread.currentThread().getName()+" symbol:" +ticker.getDisplayPairName());
                log.error(e.getMessage(),e);
            }
        }
    }




}