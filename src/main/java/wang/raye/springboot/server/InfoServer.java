package wang.raye.springboot.server;

import de.elbatya.cryptocoins.bittrexclient.BlockccClient;
import de.elbatya.cryptocoins.bittrexclient.BlockccInfoClient;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.blockcc.CoinInfo;

/**
 * 交易对
 * @author Raye
 * @since 2016年9月21日20:57:39
 */
public interface InfoServer {

	/**
	 * 更新交易对
	 * @since 2016年9月21日20:58:17
	 * @return 是否添加成功
	 */
	public double getUsdRate();

	public CoinInfo getCoinInfo(BlockccInfoClient blockccInfoClient, String coinId);



}
