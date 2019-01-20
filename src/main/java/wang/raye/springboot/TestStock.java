package wang.raye.springboot;

import de.elbatya.cryptocoins.bittrexclient.BlockccClient;
import org.springframework.beans.factory.annotation.Autowired;
import wang.raye.springboot.model.AlertExchange;
import wang.raye.springboot.server.MapperServer;
import wang.raye.springboot.server.PairsServer;
import wang.raye.springboot.server.impl.PairsServerImpl;
import wang.raye.springboot.utils.DateUtils;
import wang.raye.springboot.utils.ParseUtils;
import wang.raye.springboot.utils.send.DtalkSendMessage;
import wang.raye.springboot.utils.send.MessageModel;
import wang.raye.springboot.utils.send.WxSendMessageUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestStock {


    public static void main(String[] args) {
//        String strTime  = "1528197000";
////        Long timeLong = Long.valueOf(strTime);
////        String dfDate = DateUtils.format(timeLong);
////        System.out.println(dfDate);
////
////        long longTime = new Date().getTime();
////
////        System.out.println("字符串类型的Long日期转换成日期:");
////        String str = "1498457677473";
////        Long dateLong = Long.valueOf(str);
////
////        System.out.println("longToDate："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timeLong)));
////
////        System.out.println("============================");
////
////        System.out.println("Date和Long互转:");
////        System.out.println("日期转换成Long："+longTime);
////        System.out.println("Long转换成日期："+new Date(longTime));

//        DtalkSendMessage dtalkSendMessage = new DtalkSendMessage();
//        MessageModel messageModel = new MessageModel();
//        messageModel.setContent("测试用的");
//        messageModel.setText("测试aaaa");
//        messageModel.setTitle("测试标题");
//        dtalkSendMessage.sendMessage("13861741339",messageModel);

//        WxSendMessageUtil wxSendMessageUtil = new WxSendMessageUtil();
//        String secretIdNotice = "S2y51Sze2Wb5R53-e74b8x9E2qRbsMzhBYd_oL1rKBo";
//        wxSendMessageUtil.sendMessageByMobile("","测试用的啊");

//        String now = DateUtils.getToday();
//
//        String title = "标题";
//        String description = "内容";
//        String url = "https://www.huobi.br.com/zh-cn/coin_coin/exchange/#s=";
//        String mobile = "13861741339/17834550916";
//
//        url = ParseUtils.parseAlertUrl("hadax",url,"RVN_USDT");

//        //公告
//        title = "【公告】Bibox已恢复AC3（AC3）充值、提现功能";
//        description = "<div class=\\\"gray\\\">"+now+"</div> <div class=\\\"normal\\\">恭喜你抽中iPhone 7一台，领奖码：xxxx</div><div class=\\\"highlight\\\">【公告】Bibox已恢复AC3（AC3）充值、提现功能</div>";
//        url = "https://block.cc/notice/" + "5bd708f522285b8f5a6c387c";
//        wxSendMessageUtil.sendMessageCard(title, description, url, WxSendMessageUtil.SECRETID_NOTICE, WxSendMessageUtil.AGENTID_NOTICE, mobile);

//        //币安金叉
//        title = "【DLT_BTC】价格[0.00001725]｜指标买入｜2h｜金叉｜   ";
//        description = "<div class=\\\"gray\\\">"+now+"</div> <div class=\\\"normal\\\">成交额:[¥6661.89万]｜市值:[¥0.64亿]｜市值排名:[421]</div><div class=\\\"highlight\\\">时涨幅:[6.71%]｜日涨幅:[20.28%]｜<br>周涨幅:[13.62%]｜月涨幅:[61.85%] </div>";
//        wxSendMessageUtil.sendMessageCard(title, description, url, WxSendMessageUtil.SECRETID_BINANCE_BUY, WxSendMessageUtil.AGENTID_BINANCE_BUY, mobile);

//        //币安卖出
//        title = "【MOD_BTC】价格[0.0001343]｜指标卖出｜2h｜死叉｜   ";
//        description = "<div class=\\\"gray\\\">"+now+"</div> <div class=\\\"normal\\\">成交额:[¥1200.02万]｜市值:[¥1.26亿]｜<br>市值排名:[248]</div><div class=\\\"highlight\\\">时涨幅:[-2.06%]｜日涨幅:[2.52%]｜<br>周涨幅:[-6.84%]｜月涨幅:[23.70%] </div>";
//        wxSendMessageUtil.sendMessageCard(title, description, url, WxSendMessageUtil.SECRETID_BINANCE_SELL, WxSendMessageUtil.AGENTID_BINANCE_SELL, mobile);

//        //币安涨跌
//        title = "【WPR_BTC】价格[0.00000677]｜暴涨9.46%｜30m｜波动";
//        description = "<div class=\\\"gray\\\">"+now+"</div> <div class=\\\"normal\\\">成交额:[¥9471.02万]｜市值:[¥1.49亿]｜<br>市值排名:[199]</div><div class=\\\"highlight\\\">时涨幅:[-2.06%]｜日涨幅:[2.52%]｜<br>周涨幅:[-6.84%]｜月涨幅:[23.70%] </div>";
//        wxSendMessageUtil.sendMessageCard(title, description, url, WxSendMessageUtil.SECRETID_BINANCE_CHANGE, WxSendMessageUtil.AGENTID_BINANCE_CHANGE, mobile);
//        wxSendMessageUtil.sendMessageCard(title, description, url, WxSendMessageUtil.SECRETID_HUOBI_CHANGE, WxSendMessageUtil.AGENTID_HUOBI_CHANGE, mobile);
//        wxSendMessageUtil.sendMessageCard(title, description, url, WxSendMessageUtil.SECRETID_GATE_CHANGE, WxSendMessageUtil.AGENTID_GATE_CHANGE, mobile);
//        wxSendMessageUtil.sendMessageCard(title, description, url, WxSendMessageUtil.SECRETID_BITTREX_CHANGE, WxSendMessageUtil.AGENTID_BITTREX_CHANGE, mobile);
//        wxSendMessageUtil.sendMessageCard(title, description, url, WxSendMessageUtil.SECRETID_OKEX_CHANGE, WxSendMessageUtil.AGENTID_OKEX_CHANGE, mobile);

        //新币
//        title = "【Huobi.pro】新币[NANO]｜交易时间：2018-11-01T09:00";
//        description = "<div class=\\\"gray\\\">"+now+"｜<br>交易对：[ETH, BTC]</div><div class=\\\"normal\\\">成交额:[¥1200.02万]｜市值:[¥1.26亿]｜<br>市值排名:[248]</div>";
//        description +="<div class=\\\"highlight\\\">时涨幅:[-2.06%]｜日涨幅:[2.52%]｜<br>周涨幅:[-6.84%]｜月涨幅:[23.70%] ｜<br>当前价格 :  [¥13.8170829]  [$1.9820236]</div>";
//        description +="<div class=\\\"gray\\\">【众筹价格】:<br>USD :  [0.2000000] [9.91 倍]<br>ETH :  [0.2000000] [9.91 倍]   </div>";
//        description +="2简介：[Nano是由Colin LeMahieu于2015年推出的RaiBlocks，是一款低延迟支付平台，所需资源极少;使Nano成为点对点交易的理想选择。] ";
//        wxSendMessageUtil.sendMessageCard(title, description, url, WxSendMessageUtil.SECRETID_NEWCOIN, WxSendMessageUtil.AGENTID_NEWCOIN, mobile);

//        PairsServer pairsServer = new PairsServerImpl();
//        pairsServer.newPairs();




    }
}
