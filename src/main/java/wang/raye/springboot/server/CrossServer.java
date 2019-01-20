package wang.raye.springboot.server;

import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.CommonInterval;
import wang.raye.springboot.bean.*;
import wang.raye.springboot.model.AlertSetting;
import wang.raye.springboot.model.Analysis;
import wang.raye.springboot.model.MacdCross;

import java.util.List;

/**
 * 交叉
 * @author Raye
 * @since 2016年9月21日20:57:39
 */
public interface CrossServer {
	/**
	 * 保持交叉数据
	 * @param period k线周期
	 * @since 2016年9月21日20:58:17
	 * @return 是否添加成功
	 */
	public boolean saveCross(String exchange, CommonInterval period);

	/**
	 * 交叉数据监测
	 * @param period k线周期
	 * @since 2016年9月21日20:58:17
	 * @return 是否添加成功
	 */
	public boolean crossMonitor(String exchange, CommonInterval period);

	/**
	 * 交叉数据监测
	 * @param period k线周期
	 * @since 2016年9月21日20:58:17
	 * @return 是否添加成功
	 */
	public boolean dojiMonitor(String exchange, CommonInterval period);

    public boolean lowMonitor(String exchange, CommonInterval period);

	public boolean retraceMonitor(String exchange, CommonInterval period);




}
