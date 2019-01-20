package wang.raye.springboot.server;

import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.aicoin.Kline;
import wang.raye.springboot.bean.PointListBean;
import wang.raye.springboot.bean.QuotaListBean;
import wang.raye.springboot.model.AlertSetting;
import wang.raye.springboot.model.Tickers;

import java.util.List;

/**
 * 交易对
 * @author Raye
 * @since 2016年9月21日20:57:39
 */
public interface PageServer {

	/**
	 * 更新交易对
	 * @since 2016年9月21日20:58:17
	 * @return 是否添加成功
	 */
	public List<Tickers> getTickersByExchange(String exchange, String baseSymbol);

	public List<Kline> getLowList(List<Kline> candlestickList, QuotaListBean quotaListBean, List<AlertSetting> alertSettingList);

	public PointListBean getPointListByRSI(List<Kline> candlestickList, QuotaListBean quotaListBean, List<AlertSetting> alertSettingList);




}
