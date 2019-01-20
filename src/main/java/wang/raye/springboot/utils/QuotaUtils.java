package wang.raye.springboot.utils;

import com.binance.api.client.domain.market.Candlestick;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.aicoin.Kline;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import wang.raye.springboot.bean.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 指标计算工具类
 * @author Raye
 * @since 2016年10月11日19:29:02
 */

@Component
public class QuotaUtils {


	/**
	 * 	 MACD的图表中包含了dif，dea以及macd三个数据，要计算macd，首先要计算ema，ema表示的是n日的移动平均。
	 * 	 例如：12日EMA的计算：EMA（12） = MA（close，12）
	 * 	 以下以12为快速移动平均值，26为慢速平均值，则 差离值（DIF）的计算：
	 * 	 DIF = EMA（12） - EMA（26） 。
	 * 	 根据差离值计算其9日的EMA，即离差平均值，是所求的DEA值。
	 * 	 DEA = （前一日DEA X 8/10 + 今日DIF X 2/10）
	 * 	 MACD = （DIF-DEA）*2。
	 * @param shortValue 12为快速移动平均值
	 * @param longValue 26为慢速平均值
	 * @param mValue
	 * @param list k线数据
	 * @return
	 */
	public List<MacdBean> getMACD(int shortValue, int longValue, int mValue, List<Kline> list){
		List<MacdBean> resultList = new ArrayList<>();

		double emaShort = 0.0;
		double emaLong = 0.0;
		double bar;
		double dif;
		double dea = 0.0;
		for (int i = 0; i < list.size(); i++) {
			MacdBean bean = new MacdBean();
			double close = Double.valueOf(list.get(i).getClose());
			if (i == 0) {
				emaShort = close;
				emaLong = close;
				dif = 0;
				dea = 0;
				bar = 0;
			} else {
				emaShort = (emaShort * (shortValue - 1) + close * 2) / (shortValue + 1);
				emaLong = (emaLong * (longValue - 1) + close * 2) / (longValue + 1);
				dif = emaShort - emaLong;
				dea = (dea * (mValue - 1) + dif * 2) / (mValue + 1);
				bar = (dif - dea) * 2;
			}
			bean.setDif(dif);
			bean.setDea(dea);
			bean.setBar(bar);
			bean.setTime(list.get(i).getTime());
			resultList.add(bean);
		}
		return resultList;
	}

	/**
	 *	  以KDJ（9，3，3）为例，括号内为传入的参数
	 *	 （1）计算周期的RSV值
	 *	 RSV = （C（9）－L（9））/（H（9）－L（9））×100
	 *	 公式中，C（9）为第9日收盘价；L（9）为9日内的最低价；（H（9）为9日内的最高价。
	 *	 （2）计算K值，D值，J值
	 *	 当日K值=2/3×前一日K值+1/3×当日RSV
	 *	 当日D值=2/3×前一日D值+1/3×当日K值
	 *	 J值=3*当日K值-2*当日D值
	 *	 （注：第一天的K值我取的是33.33，D值我取的是11.11，J值我取的是77.78，当然它们要取50也可以）
	 *  （注：KDJ的值均须在0~100内）
	 * @param n 9为K值的时间周期，即9天的强弱指标
	 * @param m1 3为D值的时间周期，即为3日的K值
	 * @param m2 3是J值的时间周期，即为3日的D
	 * @param list k线数据
	 */
	public List<KdjBean> getKDJ (int n, int m1, int m2,List<Kline> list) {
		List<KdjBean> resultList = new ArrayList<>();

		if (list==null || list.size() < 1){
			return null;
		}
		double[] mK = new double[list.size()];  //K值
		double[] mD = new double[list.size()];  //D值
		double jValue;                                      //J值
		double highValue = Double.valueOf(list.get(0).getHigh());
		double lowValue = Double.valueOf(list.get(0).getLow());
		int highPosition = 0;       //记录最高价的位置
		int lowPosition = 0;        //记录最低价位置
		double rSV = 0.0;
		for (int i = 0; i < list.size(); i++) {
			KdjBean kdjBean = new KdjBean();
			if (i == 0) {
				mK[0] = 33.33;
				mD[0] = 11.11;
				jValue = 77.78;
			} else {
				//对最高价和最低价赋值
				if (highValue <= Double.valueOf(list.get(i).getHigh())) {
					highValue = Double.valueOf(list.get(i).getHigh());
					highPosition = i;
				}
				if (lowValue >= Double.valueOf(list.get(i).getLow())) {
					lowValue = Double.valueOf(list.get(i).getLow());
					lowPosition = i;
				}
				if (i > (n - 1)) {
					//判断存储的最高价是否高于当前最高价
					if (highValue > Double.valueOf(list.get(i).getHigh())) {
						//判断最高价是不是在最近n天内，若不在最近n天内，则从最近n天找出最高价并赋值
						if (highPosition < (i - (n - 1))) {
							highValue = Double.valueOf(list.get(i - (n - 1)).getHigh());
							for (int j = (i - (n - 2)); j <= i; j++) {
								if (highValue <= Double.valueOf(list.get(j).getHigh())) {
									highValue = Double.valueOf(list.get(j).getHigh());
									highPosition = j;
								}
							}
						}
					}
					if ((lowValue < Double.valueOf(list.get(i).getLow()))) {
						if (lowPosition < i - (n - 1)) {
							lowValue = Double.valueOf(list.get(i - (n - 1)).getLow());
							for (int k = i - (n - 2); k <= i; k++) {
								if (lowValue >= Double.valueOf(list.get(k).getLow())) {
									lowValue = Double.valueOf(list.get(k).getLow());
									lowPosition = k;
								}
							}
						}
					}
				}
				if (highValue != lowValue) {
					rSV = (Double.valueOf(list.get(i).getClose()) - lowValue) / (highValue - lowValue) * 100;
				}
				mK[i] = (mK[i - 1] * (m1 - 1) + rSV) / m1;
				mD[i] = (mD[i - 1] * (m2 - 1) + mK[i]) / m2;
				jValue = 3 * mK[i] - 2 * mD[i];
			}
			kdjBean.setK(this.getLimitKdj(mK[i]));
			kdjBean.setD(this.getLimitKdj(mD[i]));
			kdjBean.setJ(this.getLimitKdj(jValue));
			kdjBean.setTime(list.get(i).getTime());
			resultList.add(kdjBean);
		}
		return  resultList;
	}

	public List<DojiBean> getDoji(List<Kline> list, List<BollBean> bollBeanList) {
		List<DojiBean> resultList = new ArrayList<>();

		if (list==null || list.size() < 1){
			return null;
		}
		List<Double> maxs = HHV(list, 9);
//		List<KdjBean> kdjList = this.getKDJ(n, m1, m2,list);
		for (int i = 0; i < list.size(); i++) {
			DojiBean dojiBean = new DojiBean();

			//出现十字星交叉
			double doji = Math.abs(list.get(i).getOpen()/list.get(i).getClose() - 1);
			dojiBean.setDoji(doji);

			//缩量
			double hhv = maxs.get(i) * 0.5;
			dojiBean.setHhv(hhv);

			int j = i;
			while (list.get(j).getClose() < bollBeanList.get(j).getBoll() && j > 1) {
				j--;
			}
			dojiBean.setDownCount(i-j);

			if (i > 10) {
				double downChange1 = list.get(i-1).getOpen()/list.get(i-1).getClose() - 1;
				double downChange2 = list.get(i-2).getOpen()/list.get(i-2).getClose() - 1;
				double downChange3 = list.get(i-3).getOpen()/list.get(i-3).getClose() - 1;
				double downChange4 = list.get(i-3).getOpen()/list.get(i-3).getClose() - 1;
				double downChange =  Math.max(downChange1,downChange2);
				downChange =  Math.max(downChange,downChange3);
				downChange =  Math.max(downChange,downChange4);
				dojiBean.setDownChange(downChange);
			}

//			double j = kdjList.get(i).getJ();
//			dojiBean.setJ(j);
//			dojiBean.setTime(list.get(i).getTime());

			resultList.add(dojiBean);
		}
		return  resultList;
	}

	public List<DojiPriceBean> getDojiPrice(List<Kline> list, List<BollBean> bollBeanList) {
		List<DojiPriceBean> resultList = new ArrayList<>();

		if (list==null || list.size() < 1){
			return null;
		}
//		List<Double> maxs = HHV(list, n);
//		List<KdjBean> kdjList = this.getKDJ(n, m1, m2,list);
		for (int i = 0; i < list.size(); i++) {
			DojiPriceBean dojiBean = new DojiPriceBean();
			dojiBean.setLow(list.get(i).getLow());
			if (i< 10 || i == list.size()-1) {
				dojiBean.setDoji(0);
			} else {
				//出现十字星交叉
				double dif = Math.abs(list.get(i).getOpen()/list.get(i).getClose() - 1);
				if (dif < 0.002
						&& list.get(i+1).getClose() > list.get(i).getClose()) {

					dojiBean.setDoji(list.get(i).getClose());

					int j = i;
					while (list.get(j).getClose() < bollBeanList.get(j).getBoll() && j > 1) {
						j--;
					}
					dojiBean.setDownCount(i-j);

//					int offset = 0;
//					if(list.get(i-2).getClose() >= list.get(i-2).getOpen()) {
//						double difOffset = list.get(i-2).getClose()/list.get(i-2).getOpen() - 1;
//						if (difOffset > 0.02) {
//							offset = -1;
//						} else {
//							offset = 1;
//						}
//
//					}
//					if (offset >= 0) {
//						if (list.get(i-2-offset).getClose() < list.get(i-3-offset).getClose()
//								&& list.get(i-3-offset).getClose() < list.get(i-4-offset).getClose()
//								&& list.get(i-4-offset).getClose() < list.get(i-5-offset).getOpen()
//								&& list.get(i-3-offset).getClose() < list.get(i-3-offset).getOpen()
//								&& list.get(i-2-offset).getClose() < list.get(i-2-offset).getOpen()) {
//							dojiBean.setDoji(list.get(i).getClose());
//
//							int j = i;
//							while (list.get(j).getClose() < bollBeanList.get(j).getBoll() && j > 1) {
//								j--;
//							}
//							dojiBean.setDownCount(i-j);
//						}
//					}
				}
			}
			resultList.add(dojiBean);
		}
		return  resultList;
	}

	public List<VolumeBean> getVolume(int fast, int slow, List<Kline> list) {
		List<VolumeBean> volList = new ArrayList<>();

		if (list==null || list.size() < 1){
			return null;
		}
		List<Double> fastList = this.volumeMA(list, fast);
		List<Double> slowList = this.volumeMA(list, slow);

		for (int i = 0; i < list.size(); i++) {
			VolumeBean volBean = new VolumeBean();
			volBean.setFast(fastList.get(i));
			volBean.setSlow(slowList.get(i));
			volBean.setVol(list.get(i).getVolume());
			volBean.setTime(list.get(i).getTime());
			volList.add(volBean);
		}
		return  volList;
	}

	/**
	 * @param list 传入的数据集合，要求是升序排列
	 * @param t     boll T 一般默认为20
	 * @param k     boll K 一般默认为2
	 * @return
	 */
	public  List<BollBean> getBoll(int t, int k, List<Kline> list) {
		if (t < 1 || k < 1)
			return null;
		if (list == null || list.size() == 0)
			return null;

		List<BollBean> bollList = new ArrayList<>();
//		//存储上轨数据
//		List zhongList = new ArrayList();
//		//存储中轨数据
//		List shangList = new ArrayList();
//		//存储下轨数据
//		List xiaList = new ArrayList();
//		//上轨
//		KCandleObj shangEntity;
//		//中轨
//		KCandleObj zhongEntity;
//		//下轨
//		KCandleObj xiaEntity;

		double standtard = 0;
		double squarSum = 0;

		List<Double> smaList = countMA(t, list);
		if (smaList == null || smaList.size() == 0)
			return null;
		for (int i = 0; i < list.size(); i++) {
			BollBean bean = new BollBean();
			if (i < t-1) {
				bean.setBoll(0);
				bean.setUb(0);
				bean.setLb(0);
				bollList.add(bean);
				continue;
			}
			double smaValue = smaList.get(i);

			standtard = 0;

			for (int j = i - t + 1; j <= i; j++) {
				standtard += (list.get(j).getClose() - smaValue)
						* (list.get(j).getClose() - smaValue);
			}
			squarSum = Math.sqrt(standtard / t);

			bean.setBoll(smaValue);
			bean.setUb(smaValue + squarSum * k);
			bean.setLb(smaValue - squarSum * k);
			bean.setTime(list.get(i).getTime());
			bollList.add(bean);
		}
		return bollList;
	}

	private double getLimitKdj(double value) {
//		if (value > 100) {
//			value = 100;
//		} else if (value < 0) {
//			value = 0;
//		}
		return value;
	}

	/**
	 * SMA(C,N,M) = (M*C+(N-M)*Y')/N
	 * C=今天收盘价－昨天收盘价    N＝就是周期比如 6或者12或者24， M＝权重，其实就是1
	 *
	 * @param c   今天收盘价－昨天收盘价
	 * @param n   周期
	 * @param m   1
	 * @param sma 上一个周期的sma
	 * @return
	 */
	private double countSMA(double c, double n, double m, double sma) {
		return (m * c + (n - m) * sma) / n;
	}

	/**
	 * 算出ma的值 需要确保和传入的list size一致
	 * @param dayCount 周期
	 * @param list 数据集合
	 * @return   集合数据  MA=N日内的收盘价之和÷N
	 */
	public List<Double> countMA(int dayCount, List<Kline> list) {
		if (dayCount < 1) {
			return null;
		}
		if (list == null || list.size() == 0)
			return null;

		List<Double> ma5Values = new ArrayList<Double>();
		for (int i = 0; i < list.size(); i++) {
			double sum = 0;
			if (i < dayCount) {
				ma5Values.add(sum);
				continue;
			}
			for (int j = 0; j < dayCount; j++) {
				sum += list.get(i-j).getClose();
			}
			ma5Values.add(sum / dayCount);
		}
		return ma5Values;
	}

	/**
	 * 算出ma的值 需要确保和传入的list size一致
	 * @param day5 周期
	 * @param list 数据集合
	 * @return   集合数据  MA=N日内的收盘价之和÷N
	 */
	public List<MaBean> getMa(int day5, int day10, int day30, int day120, List<Kline> list) {
		if (day5 < 1) {
			return null;
		}
		if (list == null || list.size() == 0)
			return null;

		List<MaBean> resultList = new ArrayList<MaBean>();
		for (int i = 0; i < list.size(); i++) {
			MaBean maBean= new MaBean();
			double sum5 = 0;
			double sum10 = 0;
			double sum30 = 0;
			double sum120 = 0;
			if (i < day5) {
				maBean.setMa5(sum5);
			} else {
				for (int j = 0; j < day5; j++) {
					sum5 += list.get(i-j).getClose();
				}
				maBean.setMa5(sum5 / day5);
			}
			if (i < day10) {
				maBean.setMa10(sum10);
			} else {
				for (int j = 0; j < day10; j++) {
					sum10 += list.get(i-j).getClose();
				}
				maBean.setMa10(sum10 / day10);
			}
			if (i < day30) {
				maBean.setMa30(sum30);
			} else {
				for (int j = 0; j < day30; j++) {
					sum30 += list.get(i-j).getClose();
				}
				maBean.setMa30(sum30 / day30);
			}
			if (i < day120) {
				maBean.setMa120(sum120);
			} else {
				for (int j = 0; j < day120; j++) {
					sum120 += list.get(i-j).getClose();
				}
				maBean.setMa120(sum120 / day120);
			}
			maBean.setTime(list.get(i).getTime());
			resultList.add(maBean);
		}
		return resultList;
	}

    public List<VrBean> getVr(int n, int m, List<Kline> list) {
//        var REF_CLOSE_1 = new exprs.AssignExpr("REF_CLOSE_1", new exprs.RefExpr(new exprs.CloseExpr(), new exprs.ConstExpr(1)));
//        var TH = new exprs.AssignExpr("TH", new exprs.SumExpr(
//                new exprs.IfExpr(new exprs.GtExpr(new exprs.CloseExpr(), REF_CLOSE_1), new exprs.VolumeExpr(), new exprs.ConstExpr(0)), N));
//        var TL = new exprs.AssignExpr("TL", new exprs.SumExpr(
//                new exprs.IfExpr(new exprs.LtExpr(new exprs.CloseExpr(), REF_CLOSE_1), new exprs.VolumeExpr(), new exprs.ConstExpr(0)), N));
//        var TQ = new exprs.AssignExpr("TQ", new exprs.SumExpr(
//                new exprs.IfExpr(new exprs.EqExpr(new exprs.CloseExpr(), REF_CLOSE_1), new exprs.VolumeExpr(), new exprs.ConstExpr(0)), N));
//        var VR = new exprs.OutputExpr("VR", new exprs.MulExpr(
//                new exprs.DivExpr(
//                        new exprs.AddExpr(
//                                new exprs.MulExpr(TH, new exprs.ConstExpr(2)), TQ),
//                        new exprs.AddExpr(new exprs.MulExpr(TL, new exprs.ConstExpr(2)), TQ)), new exprs.ConstExpr(100)));
//        var MAVR = new exprs.OutputExpr("MAVR", new exprs.MaExpr(VR, M));
        if (list == null || list.size() == 0)
            return null;

        List<VrBean> resultList = new ArrayList<VrBean>();

        double[] tmpTHs = new double[list.size()];
        double[] tmpTLs = new double[list.size()];
        double[] tmpTQs = new double[list.size()];
        double[] tmpVrs = new double[list.size()];

        double sumTH = 0;
        double sumTL = 0;
        double sumTQ = 0;
        double sumVr = 0;

        tmpTHs[0] = 0;
        tmpTLs[0] = 0;
        tmpTQs[0] = 0;
        tmpVrs[0] = 0;
        VrBean firstBean= new VrBean();
        firstBean.setVr(0);
        firstBean.setMavr(0);
        resultList.add(firstBean);

        for (int i = 1; i < list.size(); i++) {
            VrBean vrBean= new VrBean();
            double close = list.get(i).getClose();
            double refClose = list.get(i-1).getClose();
            if (close > refClose) {
                sumTH += list.get(i).getVolume();
                tmpTHs[i] = list.get(i).getVolume();
            } else if (close < refClose) {
                sumTL += list.get(i).getVolume();
                tmpTLs[i] = list.get(i).getVolume();
            } else {
                sumTQ += list.get(i).getVolume();
                tmpTQs[i] = list.get(i).getVolume();
            }

            if (i <= n) {
                vrBean.setVr(0);
                vrBean.setMavr(0);
            } else {

                sumTH -= tmpTHs[i-n];
                sumTL -= tmpTLs[i-n];
                sumTQ -= tmpTQs[i-n];

//                for (int j = 0; j < n; j++) {
//                    double closeDm = list.get(i-j).getClose();
//                    double refCloseDm = list.get(i-j-1).getClose();
//                    if (close > refClose) {
//                        sumTH += list.get(i-j).getVolume();
//                    } else if (close < refClose) {
//                        sumTL += list.get(i-j).getVolume();
//                    } else {
//                        sumTQ += list.get(i-j).getVolume();
//                    }
//                }
                double vr = 0;
                if ((sumTL * 2 + sumTQ) != 0) {
                    vr = (sumTH * 2 + sumTQ) / (sumTL * 2 + sumTQ) * 100;
                }
                vrBean.setVr(vr);
                sumVr += vr;
                tmpVrs[i] = vr;
                sumVr -= tmpVrs[i-m];
//                double sumVr = vr;
//                for (int k = 1; k < m; k++) {
//                    sumVr += resultList.get(i-k).getVr();
//                }
                vrBean.setMavr(sumVr / m);
            }
			vrBean.setTime(list.get(i).getTime());
            resultList.add(vrBean);
        }
        return resultList;
    }

	public List<DmiBean> getDmi(int m1, int m2, List<Kline> list) {
		List<DmiBean> resultList = new ArrayList<>();

		DmiBean firstBean = new DmiBean();
		firstBean.setPdi(0);
		firstBean.setMdi(0);
		firstBean.setAdx(0);
		firstBean.setAdxr(0);
		resultList.add(firstBean);

        double emaDmp = 0;
        double emaDmm = 0;
        double emaMtr = list.get(0).getHigh() - list.get(0).getLow();
        double emaAdx = 0;
        double emaAdxr = 0;

		for (int i = 1; i < list.size(); i++) {

			DmiBean bean = new DmiBean();

			double plusDm = list.get(i).getHigh() - list.get(i-1).getHigh();
			double minusDm = list.get(i-1).getLow() - list.get(i).getLow();
			if (!(plusDm > 0 && plusDm > minusDm)) {
				plusDm = 0;
			}
			if (!(minusDm > 0 && minusDm > plusDm)) {
				minusDm = 0;
			}
			double tr = getAbsMax(list.get(i).getHigh() - list.get(i).getLow(),
					list.get(i).getHigh() - list.get(i-1).getClose(),
					list.get(i-1).getClose() - list.get(i).getLow());

			emaDmp = this.getExpmema(emaDmp,plusDm,m1);
			emaDmm = this.getExpmema(emaDmm,minusDm,m1);
			emaMtr = this.getExpmema(emaMtr,tr,m1);

            double pdi = 0;
            double mdi = 0;
            if (emaMtr != 0) {
                pdi = 100.0 * emaDmp / emaMtr;
                mdi = 100.0 * emaDmm / emaMtr;
            }

			bean.setPdi(pdi);
			bean.setMdi(mdi);

            double adxValue = 0;
			if ((mdi + pdi) != 0) {
               adxValue = Math.abs(mdi - pdi) / (mdi + pdi) * 100.0;
            }

            emaAdx = this.getExpmema(emaAdx,adxValue,m2);
            emaAdxr = this.getExpmema(emaAdxr,emaAdx,m2);

            bean.setAdx(emaAdx);
            bean.setAdxr(emaAdxr);

			bean.setTime(list.get(i).getTime());

            resultList.add(bean);
		}

		return  resultList;
	}

	/**
	 * 获取sar指标参数值
	 * @param list 数据集合
//	 * @param step 参数step 0.02
//	 * @param maxStep 参数max 0.2
	 * @return
	 */
	public List<SarBean> getSarDatas(List<Kline> list) {
		List<SarBean> lineDatas = new ArrayList<SarBean>();

		double step = 0.02;
		double maxStep = 0.2;

//		List sarValue = new ArrayList();
		//记录是否初始化过
		double INIT_VALUE = -100;
		//加速因子
		double af = 0;
		//极值
		double ep = INIT_VALUE;
		//判断是上涨还是下跌  false：下跌
		boolean lasttrend = false;
		double sar = 0;

		for (int i = 0; i < list.size() - 1; i++) {
			//上一个周期的sar
			double priorSAR = sar;
			Kline item = list.get(i);
			if (lasttrend) {
				//上涨
				if (ep == INIT_VALUE || ep < item.getHigh()) {
					//重新初始化值
					ep = item.getHigh();
					af = Math.min(af + step, maxStep);
				}
				sar = priorSAR + af * (ep - priorSAR);
				double lowestPrior2Lows = Math.min(list.get(Math.max(1, i) - 1).getLow(), list.get(i).getLow());
				if (sar > list.get(i + 1).getLow()) {
					sar = ep;
					//重新初始化值
					af = 0;
					ep = INIT_VALUE;
					lasttrend = !lasttrend;

				} else if (sar > lowestPrior2Lows) {
					sar = lowestPrior2Lows;
				}
			} else {
				if (ep == INIT_VALUE || ep > list.get(i).getLow()) {
					//重新初始化值
					ep = list.get(i).getLow();
					af = Math.min(af + step, maxStep);
				}
				sar = priorSAR + af * (ep - priorSAR);
				double highestPrior2Highs = Math.max(list.get(Math.max(1, i) - 1).getHigh(), list.get(i).getHigh());
				if (sar < list.get(i + 1).getHigh()) {
					sar = ep;
					//重新初始化值
					af = 0;
					ep = INIT_VALUE;
					lasttrend = !lasttrend;

				} else if (sar < highestPrior2Highs) {
					sar = highestPrior2Highs;
				}
			}
			SarBean bean = new SarBean();
			bean.setSar(sar);
			lineDatas.add(bean);
		}
		SarBean bean = new SarBean();
		bean.setSar(sar);
		lineDatas.add(bean);

		return lineDatas;
	}

	/**
	 * 获取sar指标参数值
	 * @param list 数据集合
	//	 * @param step 参数step 0.02
	//	 * @param maxStep 参数max 0.2
	 * @return
	 */
	public List<SarBean> getSar(List<Kline> list) {
		List<SarBean> lineDatas = new ArrayList<SarBean>();

		int RANGE = 4;
		double MIN = 2 / 100.0;
		double STEP = 2 / 100.0;
		double MAX = 20 / 100.0;


//		var data = this._buf[index];
//		var exprEnv = ExprEnv.get();
//		var first = exprEnv._firstIndex;
		for (int i = 0; i < list.size(); i++) {
			SarBean bean = new SarBean();
			if (i == 0) {
				bean.setLongPos(true);
				bean.setSar(list.get(i).getLow());
				bean.setEp(list.get(i).getHigh());
				bean.setAf(0.02);
//				data.longPos = true;
//				data.sar = exprEnv._ds.getDataAt(index).low;
//				data.ep = exprEnv._ds.getDataAt(index).high;
//				data.af = 0.02;
			} else {
				double high = list.get(i).getHigh();

				double low = list.get(i).getLow();

				SarBean prev = lineDatas.get(i-1);
				double sar = prev.getSar() + prev.getAf() * (prev.getEp() - prev.getSar());

				boolean longPos = true;
				double ep = 0;
				double af = 0;


				if (prev.isLongPos()) {
					longPos = true;

					if (high > prev.getEp()) {
						ep = high;
						af = Math.min(prev.getAf() + STEP, MAX);
					} else {
						ep = prev.getEp();
						af = prev.getAf();
					}

					if (sar > low) {
						longPos = false;
						int j = i - RANGE + 1;

						for (j = Math.max(j, 0); j < i; j++) {
							double h = list.get(j).getHigh();

							if (high < h) high = h;
						}

						sar = high;
						ep = low;
						af = 0.02;
					}
				} else {
					longPos = false;


					if (low < prev.getEp()) {
						ep = low;
						af = Math.min(prev.getAf() + STEP, MAX);
					} else {
						ep = prev.getEp();
						af = prev.getAf();
					}

					if (sar < high) {
						longPos = true;

						int j = i - RANGE + 1;

						for (j = Math.max(j, 0); j < i; j++) {
							double l = list.get(j).getLow();

							if (low > l) low = l;
						}
						sar = low;
						ep = high;
						af = 0.02;
					}
				}
				bean.setLongPos(longPos);
				bean.setSar(sar);
				bean.setEp(ep);
				bean.setAf(af);
			}
			bean.setTime(list.get(i).getTime());
			lineDatas.add(bean);
		}

		return lineDatas;
	}

	private double getAbsMax(double a, double b, double c) {
		a = Math.abs(a);
		b = Math.abs(b);
		c = Math.abs(c);
		double temp = Math.max(a, b);
		return Math.max(temp, c);
     }

    private double getEXPMA(double[] values, int range, int index) {
        // 开始计算EMA值，
        Double k = 2.0 / (range + 1.0);// 计算出序数
        int first = index - range + 1;
        if (first < 0) {
            first = 0;
        }
        Double ema = values[first];// 第一天ema等于当天收盘价
        Double preExpma = values[first];// 第一天ema等于当天收盘价
        for (int i = first; i <= index; i++) {
            // 第二天以后，当天收盘 收盘价乘以系数再加上昨天EMA乘以系数-1
            ema = values[i] * k + preExpma * (1 - k);
            preExpma = ema;
        }
        return ema;
    }

    private double getExpmema(double preExpma, double value, int range) {
        double alpha = 1.0 / range;

		double expma = alpha * (value - preExpma) + preExpma;
        return expma;
    }

//	private double getExpmema(double preExpma, double value, int range) {
//		double a = 1.0 * 2 / (range + 1);
//
//		double expma = (value * 2 + preExpma * (range - 1))/(range + 1);
//
//		return expma;
//	}

    private double[] getExpma(double[] values, int range) {
	    double[] result = new double[values.length];
        double expma;
        double preExpma = values[0];
        double a = 1.0 * 2 / (range + 1);
        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                expma = values[0];
            } else {
                expma = values[i] * a + (1 - a) * preExpma;
            }
            preExpma = expma;
            result[i] = expma;
        }
        return result;
    }


	/**
	 * 算出ma的值 需要确保和传入的list size一致
	 * @param list 数据集合
	 * @param days 周期
	 * @return   集合数据  MA=N日内的收盘价之和÷N
	 */
	public List<Double> volumeMA(List<Kline> list, int days) {
		if (days < 1) {
			return null;
		}
		if (list == null || list.size() == 0)
			return null;
		int cycle = days;

		if (cycle > list.size()) {
			//设置的指标参数大于数据集合 不用计算
			return null;
		}

		double sum = 0;

		List<Double> ma5Values = new ArrayList<Double>();

		for (int i = 0; i < list.size(); i++) {
			if (i == cycle - 1) {
				for (int j = i - days + 1; j <= i; j++) {
					sum += Double.valueOf(list.get(j).getVolume());
				}
			} else if (i > cycle - 1) {
				sum = sum + Double.valueOf(list.get(i).getVolume())
						- Double.valueOf(list.get(i - days).getVolume());
			}
			ma5Values.add(sum / days);
		}
		return ma5Values;
	}

	/**
	 * LLV  n周期内的最低值 取low字段
	 *
	 * @param list 数据集合
	 * @param n 周期
	 * @return
	 */
	public List<Double> LLV(List<Kline> list, int n) {
		if (list == null || list.size() == 0)
			return null;
		List<Double> datas = new ArrayList<>();

		double minValue = 0;
		for (int i = n - 1; i < list.size(); i++) {
			for (int j = i - n + 1; j <= i; j++) {
				if (j == i - n + 1) {
					minValue = Double.valueOf(list.get(j).getLow());
				} else {
					minValue = Math.min(minValue, Double.valueOf(list.get(j).getLow()));
				}
			}
			datas.add(minValue);
		}

		return datas;
	}

	/**
	 * HHV n周期内的最大值 取high字段
	 *
	 * @param list 数据集合
	 * @param n 周期
	 * @return
	 */
	public List<Double> HHV(List<Kline> list, int n) {
		if (list == null || list.size() == 0)
			return null;
		List<Double> datas = new ArrayList<>();

		double maxValue = Double.valueOf(list.get(0).getVolume());
		for (int i = 0; i < list.size(); i++) {
			if (i < n - 1) {
				maxValue = Double.valueOf(list.get(i).getVolume());
			} else {
				for (int j = i - n + 1; j <= i; j++) {
					if (j == i - n + 1) {
						maxValue = Double.valueOf(list.get(j).getVolume());
					} else {
						maxValue = Math.max(maxValue, Double.valueOf(list.get(j).getVolume()));
					}
				}
			}
			datas.add(maxValue);
		}
		return datas;
	}


	/**
	 * kdj 9,3,3
	 * N:=9; P1:=3; P2:=3;
	 * RSV:=(CLOSE-LLV(LOW,N))/(HHV(HIGH,N)-LLV(LOW,N))*100;
	 * K:SMA(RSV,P1,1);
	 * D:SMA(K,P2,1);
	 * J:3*K-2*D;
	 * @param list 数据集合
	 * @param n 指标周期 9
	 * @param P1 参数值为3
	 * @param P2 参数值为3
	 * @return
	 */
	public List<KdjBean> getKDJLinesDatas(
			List<Kline> list, int n, int P1, int P2) {
		if (list == null || list.size() == 0)
			return null;
		int cycle = n;
		if (n > list.size()) {
			return null;
		}
		List<KdjBean> resultList = new ArrayList<>();

//		List kValue = new ArrayList();
//		List dValue = new ArrayList();
//		List jValue = new ArrayList();

		List<Double> maxs = HHV(list, n);
		List<Double> mins = LLV(list, n);
		if (maxs == null || mins == null)
			return null;
		//确保和 传入的list size一致，
//		int size = list.size() - maxs.size();
//		for (int i = 0; i < size; i++) {
//			maxs.add(0, new KCandleObj());
//			mins.add(0, new KCandleObj());
//		}
		double rsv = 0;
		double lastK = 50;
		double lastD = 50;
		for (int i = cycle - 1; i < list.size(); i++) {
			if (i >= maxs.size())
				break;
			if (i >= mins.size())
				break;
			double div = maxs.get(i) - mins.get(i);
			if (div == 0) {
				//使用上一次的
			} else {
				rsv = ((Double.valueOf(list.get(i).getClose()) - mins.get(i))
						/ (div)) * 100;
			}

			double k = countSMA(rsv, P1, 1, lastK);

//			try {
//				BigDecimal big = new BigDecimal(k);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			double d = countSMA(k, P2, 1, lastD);
			double j = 3 * k - 2 * d;
			lastK = k;
			lastD = d;
			KdjBean kdjBean = new KdjBean();
			kdjBean.setK(k);
			kdjBean.setD(d);
			kdjBean.setJ(j);
			resultList.add(kdjBean);
//			kValue.add(new KCandleObj(k));
//			dValue.add(new KCandleObj(d));
//			jValue.add(new KCandleObj(j));
		}


		//确保和 传入的list size一致，
//		size = list.size() - kValue.size();
//		for (int i = 0; i < size; i++) {
//			kValue.add(0, new KCandleObj());
//			dValue.add(0, new KCandleObj());
//			jValue.add(0, new KCandleObj());
//		}
//
//		List<KLineObj> lineDatas = new ArrayList<KLineObj>();
//
//		KLineObj kLine = new KLineObj();
//		kLine.setLineData(kValue);
//		kLine.setTitle("K");
//		kLine.setLineColor(KParamConfig.COLOR_KDJ_K);
//		lineDatas.add(kLine);
//
//		KLineObj dLine = new KLineObj();
//		dLine.setLineData(dValue);
//		dLine.setTitle("D");
//		dLine.setLineColor(KParamConfig.COLOR_KDJ_D);
//		lineDatas.add(dLine);

//        KLineObj jLine = new KLineObj();
//        jLine.setLineData(jValue);
//        jLine.setTitle("J");
//        jLine.setLineColor(KParamConfig.COLOR_KDJ_J);
//        lineDatas.add(jLine);

//		//加两条固定线 30  70
//		KLineObj line30 = new KLineObj();
//		line30.setLineColor(KParamConfig.COLOR_KDJ_30);
//		line30.setLineValue(KParamConfig.VALUE_KDJ_01);
//		lineDatas.add(line30);
//
//		KLineObj line70 = new KLineObj();
//		line70.setLineColor(KParamConfig.COLOR_KDJ_70);
//		line70.setLineValue(KParamConfig.VALUE_KDJ_02);
//		lineDatas.add(line70);

		return resultList;
	}


	/**
	 * SMA(C,N,M) = (M*C+(N-M)*Y')/N
	 * LC := REF(CLOSE,1);
	 * RSI$1:SMA(MAX(CLOSE-LC,0),N1,1)/SMA(ABS(CLOSE-LC),N1,1)*100;
	 */
	public  List<RsiBean> getRsi(int day1, int day2, int day3, List<Kline> list) {
		if (list == null)
			return null;
//		if (day1 > list.size() || day2 > list.size() || day3 > list.size())
//			return null;

		List<RsiBean> resultList = new ArrayList<>();

		double smaMax1 = 0, smaAbs1 = 0;//默认0
		double lc1 = 0;//默认0
		double close1 = 0;
		double rsi1 = 0;

		double smaMax2 = 0, smaAbs2 = 0;//默认0
		double lc2 = 0;//默认0
		double close2 = 0;
		double rsi2 = 0;

		double smaMax3 = 0, smaAbs3 = 0;//默认0
		double lc3 = 0;//默认0
		double close3 = 0;
		double rsi3 = 0;
		for (int i = 0; i < list.size(); i++) {
			if (i < day3) {
				rsi1 = 0;
				rsi2 = 0;
				rsi3 = 0;
			} else {
				lc1 = list.get(i - 1).getClose();
				close1 = list.get(i).getClose();
				smaMax1 = countSMA(Math.max(close1 - lc1, 0d), day1, 1, smaMax1);
				smaAbs1 = countSMA(Math.abs(close1 - lc1), day1, 1, smaAbs1);
				rsi1 = smaMax1 / smaAbs1 * 100;

				lc2 = list.get(i - 1).getClose();
				close2 = list.get(i).getClose();
				smaMax2 = countSMA(Math.max(close2 - lc2, 0d), day2, 1, smaMax2);
				smaAbs2 = countSMA(Math.abs(close2 - lc2), day2, 1, smaAbs2);
				rsi2 = smaMax2 / smaAbs2 * 100;

				lc3 = list.get(i - 1).getClose();
				close3 = list.get(i).getClose();
				smaMax3 = countSMA(Math.max(close3 - lc3, 0d), day3, 1, smaMax3);
				smaAbs3 = countSMA(Math.abs(close3 - lc3), day3, 1, smaAbs3);
				rsi3 = smaMax3 / smaAbs3 * 100;
			}

			RsiBean rsiBean = new RsiBean();
			rsiBean.setRsi1(rsi1);
			rsiBean.setRsi2(rsi2);
			rsiBean.setRsi3(rsi3);
			rsiBean.setTime(list.get(i).getTime());
			resultList.add(rsiBean);
		}

		return resultList;
	}



}
