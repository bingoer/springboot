package wang.raye.springboot.utils;

import com.binance.api.client.domain.market.Candlestick;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.aicoin.Kline;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wang.raye.springboot.bean.*;
import wang.raye.springboot.model.AlertSetting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 指标计算交叉工具类
 * @author Raye
 * @since 2016年10月11日19:29:02
 */

@Component
public class QuotaCrossUtils {

	@Value("${self.ratio.doji}")
	private double DOJI;
	@Value("${self.ratio.changes}")
	private double CHANGE;



	public String getMACDCross(List<MacdBean> macdBeanList, int len){
		String result = "1";//空仓等待';
//		String result = "4";//空仓等待'状态改为死叉;
		// var macd = TA.MACD(records,12,26,9);//调用指标函数， 参数为MACD 默认的参数。
		// var output = kline.chartMgr._indic._outputs;
//		double dif = macdBean.dif; //dif线
//		var dea = macdBean.dea; //dea线
//		var column = macdBean.column; // MACD柱
//		int len = candlestickList.size(); //K线周期长度
		MacdBean last = macdBeanList.get(len - 1);
		MacdBean second  = macdBeanList.get(len - 2);

		// if( (dif[len-1] > 0 && dea[len-1] > 0) && dif[len-1] > dea[len-1] && dif[len-2] < dea[len-2] && column[len-1] > 0.2 ){
		if (last.getDif() > last.getDea() && second.getDif() < second.getDea()) {
			//判断金叉条件：dif 与 dea 此刻均大于0 ， 且dif由下上穿dea ， 且 MACD量柱大于0.2
			//            return 1; //返回1 代表 金叉信号。
			result = "2";//'金叉';
		}
		// if( (dif[len-1] < 0 && dea[len-1] < 0) && dif[len-1] < dea[len-1] && dif[len-2] > dea[len-2] && column[len-1] < -0.2 ){
		else if (last.getDif() < last.getDea() && second.getDif() > second.getDea()) {
			//判断死叉条件：
			//            return 2;//返回2 代表 死叉信号。
			result = "4";//'死叉';
		} else if (last.getDif() > last.getDea() && second.getDif() > second.getDea()) {
			//判断死叉条件：
			//            return 2;//返回2 代表 死叉信号。
			result = "3";//'继续持有';
//			result = "2";//'继续持有'状态改为金叉;
		}
		return result;
	}

	public String getKdjCross(List<KdjBean> kdjBeanList, int len){
		String result = "1";//空仓等待';
//		String result = "4";//空仓等待'状态改为死叉;
//		int len = candlestickList.size(); //K线周期长度
		KdjBean last = kdjBeanList.get(len - 1);
		KdjBean second  = kdjBeanList.get(len - 2);

		// CROSS(K,D) AND CROSS(J,D);
		// CROSS(KDJ.K,KDJ.D) AND BETWEEN(KDJ.K,45,50) AND BETWEEN(KDJ.D,45,50);
		if ((last.getK() > last.getD() && second.getK() < second.getD())
				&& (last.getJ() > last.getD() && second.getJ() < second.getD())) {
			result = "2";//'金叉';
		}
		else if ((last.getK() < last.getD() && second.getK() > second.getD())
				&& (last.getJ() < last.getD() && second.getJ() > second.getD())) {
			//判断死叉条件：
			//            return 2;//返回2 代表 死叉信号。
			result = "4";//'死叉';
		} else if ((last.getK() > last.getD() && second.getK() > second.getD())
				&& (last.getJ() > last.getD() && second.getJ() > second.getD())) {
			result = "3";//'继续持有';
		} else if (second.getJ() > 101 && second.getJ() > last.getJ()) {
			//判断超买条件：
			//  超买 and 顶背离
			result = "5";//'超买';
		} else if (second.getJ() < 0 && second.getJ() < last.getJ()) {
			//判断超卖条件：
			//  超卖 and 底背离
			result = "6";//'超卖';
		}
		return result;
	}

	public String getRsiCross(List<RsiBean> rsiBeanList){
		String result = "1";//空仓等待';
		double rsi1 = rsiBeanList.get(rsiBeanList.size()-1).getRsi1();
		double rsi2 = rsiBeanList.get(rsiBeanList.size()-1).getRsi2();
		double rsi3 = rsiBeanList.get(rsiBeanList.size()-1).getRsi3();

		double rsi1Pre = rsiBeanList.get(rsiBeanList.size()-2).getRsi1();
		double rsi2Pre = rsiBeanList.get(rsiBeanList.size()-2).getRsi2();
		double rsi3Pre = rsiBeanList.get(rsiBeanList.size()-2).getRsi3();

		if (rsi1 > rsi2 && rsi1Pre < rsi2Pre) {
			result = "2";//'金叉';
		}
		else if (rsi1 < rsi2 && rsi1Pre > rsi2Pre) {
			//判断死叉条件：
			//            return 2;//返回2 代表 死叉信号。
			result = "4";//'死叉';
		} else if (rsi1 > rsi2 && rsi2 > rsi3) {
			result = "3";//'继续持有';
		} else if (rsi1 > 70) {
			//判断超买条件：
			//  超买 and 顶背离
			result = "5";//'超买';
		} else if (rsi1 < 20) {
			//判断超卖条件：
			//  超卖 and 底背离
			result = "6";//'超卖';
		}
		return result;
	}

	public String getMaCross(List<MaBean> maBeanList){
		String result = "1";//空仓等待';
		double ma5 = maBeanList.get(maBeanList.size()-1).getMa5();
		double ma10 = maBeanList.get(maBeanList.size()-1).getMa10();
		double ma30 = maBeanList.get(maBeanList.size()-1).getMa30();

		double ma5Pre = maBeanList.get(maBeanList.size()-2).getMa5();
		double ma10Pre = maBeanList.get(maBeanList.size()-2).getMa10();
		double ma30Pre = maBeanList.get(maBeanList.size()-2).getMa30();

		if (ma5 > ma10 && ma5Pre < ma10Pre) {
			result = "2";//'金叉';
		}
		else if (ma5 < ma10 && ma5Pre > ma10Pre) {
			//判断死叉条件：
			//            return 2;//返回2 代表 死叉信号。
			result = "4";//'死叉';
		} else if (ma5 > ma10 && ma5Pre > ma10Pre) {
			result = "3";//'继续持有';
		}
		return result;
	}

	public String getVolumeCross(List<VolumeBean> volumeBeanList, int len){
		String result = "1";//空仓等待';
		VolumeBean last = volumeBeanList.get(len - 1);
		VolumeBean second  = volumeBeanList.get(len - 2);

		if (last.getFast() > last.getSlow() && second.getFast() < second.getSlow()) {
			//判断金叉条件：dif 与 dea 此刻均大于0 ， 且dif由下上穿dea ， 且 MACD量柱大于0.2
			//            return 1; //返回1 代表 金叉信号。
			result = "2";//'金叉';
		}

		else if (last.getFast() < last.getSlow() && second.getFast() > second.getSlow()) {
			//判断死叉条件：
			//            return 2;//返回2 代表 死叉信号。
			result = "4";//'死叉';
		} else if (last.getFast() > last.getSlow() && second.getFast() > second.getSlow()) {
			//判断死叉条件：
			//            return 2;//返回2 代表 死叉信号。
			result = "3";//'继续持有';
		}
		return result;
	}

	/**
	 * 缩量十字星擒牛法
	 * 五线上:=C>=MAX(MA(C,5),MAX(MA(C,10),MAX(MA(C,20),MAX(MA(C,60),MA(C,120)))));
	 *
	 * 缩量:=V<=HHV(V,10)*0.5;
	 *
	 * 十字星:=ABS(C-O)/C<=0.002 AND C/REF(C,1)<1.02;
	 *
	 * 五线上 AND 缩量 AND 十字星;
	 * @param dojiBeanList
	 * @param candlestickList
	 * @return
	 */
	public String getDojiCross(List<DojiBean> dojiBeanList, List<Kline> candlestickList){

		if(null == candlestickList ||candlestickList.size() < 10) {
			return "1";//空仓等待';
		}
		String result = "1";//空仓等待';
		int len = candlestickList.size(); //K线周期长度
		DojiBean last = dojiBeanList.get(len - 1);
		DojiBean second  = dojiBeanList.get(len - 2);
		Kline lastCandle = candlestickList.get(len - 1);
		Kline secondCandle  = candlestickList.get(len - 2);

		// C/REF(C,1)<1.02
		double change = Double.valueOf(lastCandle.getClose())/Double.valueOf(secondCandle.getClose()) - 1;

		//倒数第二根k线收盘价
		double secondClose = Double.valueOf(secondCandle.getClose());

		// 连续下跌3次，
		int every = 3;
		// 8次收盘价最低
		int between = 8;

		// 缩量 AND 十字星 AND (连续下跌或者上涨)
		if (second.getDoji() <= DOJI && Double.valueOf(secondCandle.getVolume()) <= second.getHhv()) {
			// 连续下跌3次，
			boolean everyFlg = true;
			// 8次收盘价最低
			boolean betweenFlg = true;
			if(change >= CHANGE) {
				// 8次收盘价最低
				for (int i = len - 3; i > len - 3 - between; i--) {
					if (secondClose > Double.valueOf(candlestickList.get(i).getClose())) {
						betweenFlg = false;
						break;
					}
				}
				// 连续下跌3次
				double tempClose = secondClose;
				for (int i = len - 3; i > len - 3 -every; i--) {
					double tClose = Double.valueOf(candlestickList.get(i).getClose());
					if (tempClose > tClose) {
						everyFlg = false;
						break;
					}
					tempClose = tClose;
				}

				if (everyFlg && betweenFlg) {
					result = "2";//'金叉';
				}

			} else if(change <= CHANGE * -1) {
				// 8次收盘价最高
				for (int i = len - 3; i > len - 3 - between; i--) {
					if (secondClose < Double.valueOf(candlestickList.get(i).getClose())) {
						betweenFlg = false;
						break;
					}
				}
				// 连续上涨3次
				double tempClose = secondClose;
				for (int i = len - 3; i > len - 3 -every; i--) {
					double tClose = Double.valueOf(candlestickList.get(i).getClose());
					if (tempClose < tClose) {
						everyFlg = false;
						break;
					}
					tempClose = tClose;
				}

				if (everyFlg && betweenFlg) {
					result = "4";//'死叉';
				}
			}
		}
		return result;
	}

	public String getVolatile(List<Kline> candlestickList, List<AlertSetting> alertSettingList) {
		String result = "";
		Kline lastCandle = candlestickList.get(candlestickList.size()-1);
		Kline secondCandle = candlestickList.get(candlestickList.size()-2);
		Kline thrCandle = candlestickList.get(candlestickList.size()-3);

		double upChange = alertSettingList.get(0).getRatio();
		double lowChange = alertSettingList.get(0).getRatio2();
		if (lastCandle.getClose() > secondCandle.getClose()) {
			double low = Math.max(lastCandle.getLow(), secondCandle.getLow());
			double difHigh = lastCandle.getHigh() / low - 1;
			if (difHigh >= upChange) {
				double close = Math.max(lastCandle.getClose(), secondCandle.getClose());
				if (thrCandle.getHigh() > close) {
					result = "31";//反弹
				}else {
					result = "13";//暴涨
				}
			}
		} else {
			double difLow = lastCandle.getLow() / secondCandle.getHigh() - 1;
			if (difLow <= lowChange * -1) {
				result = "23";//暴跌
			}
		}
		return result;
	}

	/**
	 *
	 * @param listKline
	 * @param alertSetting ratio 系数
	 * @return
	 */
	public String getStart(List<Kline> listKline, AlertSetting alertSetting) {

		String result = "";
		// 	 * @param riseNum ratio 系数 连续上涨阳线
		//	 * @param volumeNum ratio2 系数2 放量判断周期
		//	 * @param periodNum ratio3 系数3 突破判断周期
		//	 * @param marginNum ratio4 系数4 末位k线偏移
		int riseNum = alertSetting.getRatio().intValue();
		int volumeNum = alertSetting.getRatio2().intValue();
		int periodNum = alertSetting.getRatio3().intValue();
		int marginNum = alertSetting.getRatio4().intValue();

		int maxNum = riseNum;
		if (volumeNum > maxNum) {
			maxNum = volumeNum;
		}
		if (periodNum > maxNum) {
			maxNum = periodNum;
		}
		if (marginNum > maxNum) {
			maxNum = marginNum;
		}
		// k线周期长度不足时不做处理直接返回
		if (listKline.size() < maxNum) {
			return  result;
		}

		int length = listKline.size() - 1 - marginNum;
		int index = length - riseNum;
		boolean isStart = true;

		for (int i = index; i < length; i++) {
			// 连续上涨阳线
			if (listKline.get(i).getClose() >= listKline.get(i + 1).getClose()) {
				isStart = false;
				break;
			}
		}
		if (isStart) {
			if (volumeNum > 0) {
				// 放量
				double maxVolume = this.getPeriodMax(listKline, volumeNum, "volume");
				double maxRiseVolume = this.getPeriodMax(listKline, riseNum + marginNum, "volume");
				if (maxRiseVolume < maxVolume) {
					isStart = false;
				}
			}

			if (periodNum > 0) {
				// 突破前高
				double maxHigh = this.getPeriodMax(listKline, periodNum, "high");
				if (listKline.get(length).getClose() < maxHigh) {
					isStart = false;
				}
			}

			if (marginNum > 0) {
				// 末位k线偏移
				if (listKline.get(length).getHigh() >= listKline.get(length + marginNum).getClose()) {
					isStart = false;
				}
			}
		}
		if (isStart) {
			result = "2";//上涨
		}

		return result;
	}

	private double getPeriodMax(List<Kline> listKline, int periodNum, String item) {
		List<Double> list = new ArrayList<>();
		int index = listKline.size() - 1 - periodNum;
		if (listKline.size() - 1 < periodNum) {
			index = 0;
		}
		int size = listKline.size() - 1;
		for (int i = index; i < size; i++) {
			if ("volume".equals(item)) {
				list.add(listKline.get(i).getVolume());
			} else if ("high".equals(item)) {
				list.add(listKline.get(i).getHigh());
			} else if ("close".equals(item)) {
				list.add(listKline.get(i).getClose());
			} else if ("low".equals(item)) {
				list.add(listKline.get(i).getLow());
			} else if ("open".equals(item)) {
				list.add(listKline.get(i).getOpen());
			}
		}
		return Collections.max(list);
	}


	//箱顶:=PEAK(CLOSE,13,1)*0.98 ;
	//箱底:=TROUGH(CLOSE,8,1)*1.02;
	//AA:=CROSS(REF(C,1),箱顶);
	//BB:=CROSS(OPEN,箱顶);
	//DD:=C>O;
	//突破:AA AND BB AND DD;


//	N:=60;
//	V1:=MA(VOL,5);V2:=MA(VOL,10);V3:=MA(VOL,20); V4:=MA(VOL,30);
//	VV:=V1>V2 AND V2>V3 {AND V3>V4};
//	A1:=MA(C,5);A2:=MA(C,10);A3:=MA(C,20);A4:=MA(C,30);A5:=MA(C,60);A6:=MA(C,120);
//	AA:=A1>A2 AND A2>A3 AND A3>A4 AND A4>A5 AND A5>A6;
//	EE:=(HHV(C,N)-LLV(C,N))/LLV(C,N);
//  BB:=REF(EE,1)<=0.30;
//	CC:=C>O AND C>=REF(HHV(C,N),1)
//	DD:=(C-REF(C,1))/REF(C,1)>=0.03;
//	横盘突破:VV  AND  AA  AND BB AND CC AND DD;
}
