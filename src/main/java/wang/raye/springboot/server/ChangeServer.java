package wang.raye.springboot.server;

import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.CommonInterval;

/**
 * 涨跌幅监测提醒
 * @author Raye
 * @since 2016年9月21日20:57:39
 */
public interface ChangeServer {
	/**
	 * 涨跌幅监测提醒
	 * @param marketName 交易所
	 * @param period k线周期
	 * @since 2016年9月21日20:58:17
	 * @return 是否添加成功
	 */
	public boolean netChange(String marketName, CommonInterval period);

	/**
	 * idex涨跌幅监测提醒
	 * @since 2016年9月21日20:58:17
	 * @return 是否添加成功
	 */
	public boolean idexNetChange();



}
