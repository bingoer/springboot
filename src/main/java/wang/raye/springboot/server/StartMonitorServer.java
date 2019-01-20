package wang.raye.springboot.server;

import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.CommonInterval;

/**
 * 启动监测
 * @author Raye
 * @since 2016年9月21日20:57:39
 */
public interface StartMonitorServer {

	/**
	 * 启动点监测
	 * @since 2016年9月21日20:58:17
	 * @return 是否添加成功
	 */
	public boolean startPoint(String exchange, CommonInterval period);


}
