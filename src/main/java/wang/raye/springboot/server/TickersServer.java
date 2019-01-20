package wang.raye.springboot.server;

/**
 * 交易对
 * @author Raye
 * @since 2016年9月21日20:57:39
 */
public interface TickersServer {

	/**
	 * 更新交易对
	 * @since 2016年9月21日20:58:17
	 * @return 是否添加成功
	 */
	public boolean exchangeTickers(String exchange);

	/**
	 * 更新交易对
	 * @since 2016年9月21日20:58:17
	 * @return 是否添加成功
	 */
	public boolean updateExchangeTickers(String exchange);



}
