package wang.raye.springboot.server;

import wang.raye.springboot.bean.CrossQuotaBean;
import wang.raye.springboot.bean.QuotaBean;
import wang.raye.springboot.bean.RetraceBean;
import wang.raye.springboot.bean.VolatileBean;
import wang.raye.springboot.model.AlertSetting;

import java.util.List;

/**
 * 指标分析
 * @author Raye
 * @since 2016年9月21日20:57:39
 */
public interface AnalysisServer {

	/**
	 *  分析rsi指标得出低点
	 * @param alertSettingList
	 * @param crossQuotaBean
	 * @return
	 */
	public VolatileBean getLowByRSI(List<AlertSetting> alertSettingList, CrossQuotaBean crossQuotaBean);

	/**
	 * 分析rsi指标得出高点
	 * @param alertSettingList
	 * @param crossQuotaBean
	 * @return
	 */
	public VolatileBean getHighByRSI(List<AlertSetting> alertSettingList, CrossQuotaBean crossQuotaBean);

	/**
	 * 分析rsi指标得出 在低点买入后的止盈止损点
	 * @param alertSettingList
	 * @param crossQuotaBean
	 * @return
	 */
	public VolatileBean getStopLimitByRSI(List<AlertSetting> alertSettingList, CrossQuotaBean crossQuotaBean);

	/**
	 * 分析rsi指标得出 双底后的买入点
	 * @param alertSettingList
	 * @param crossQuotaBean
	 * @return
	 */
	public boolean getDoubleByRSI(List<AlertSetting> alertSettingList, CrossQuotaBean crossQuotaBean);

	/**
	 * 分析rsi指标得出 开启上涨后的回调买入点
	 * @param alertSettingList
	 * @param crossQuotaBean
	 * @return
	 */
	public VolatileBean getRetraceByRSI(List<AlertSetting> alertSettingList, CrossQuotaBean crossQuotaBean, String preStatus);

	public boolean getCross(List<AlertSetting> alertSettingList, QuotaBean quotaBean, double price);

	public boolean getDoji(List<AlertSetting> alertSettingList, QuotaBean quotaBean, double dojiPrice, double price);

	public boolean getDojiSell(List<AlertSetting> alertSettingList, QuotaBean quotaBean, double dojiPrice, double close,double high);

	public boolean getCrossNew(List<AlertSetting> alertSettingList, CrossQuotaBean crossQuotaBean);

	public boolean getCrossSellNew(List<AlertSetting> alertSettingList, CrossQuotaBean crossBean);



}
