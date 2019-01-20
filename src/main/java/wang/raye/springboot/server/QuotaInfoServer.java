package wang.raye.springboot.server;

/**
 * 交易对
 * @author Raye
 * @since 2016年9月21日20:57:39
 */
public interface QuotaInfoServer {

	/**
	 * 更新交易点的指标数据
	 * @since 2016年9月21日20:58:17
	 * @return 是否添加成功
	 */
	public boolean updateTradePointQuota(String exchange, String symbol, String base, String period, String status,String quantType);



}
