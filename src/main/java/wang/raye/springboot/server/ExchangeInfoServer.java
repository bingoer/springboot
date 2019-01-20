package wang.raye.springboot.server;

/**
 * 交易对
 * @author Raye
 * @since 2016年9月21日20:57:39
 */
public interface ExchangeInfoServer {

	/**
	 * 更新交易对
	 * @since 2016年9月21日20:58:17
	 * @return 是否添加成功
	 */
	public boolean updateTickers(String exchange);


	/**
	 * 更新交易对
	 * @since 2016年9月21日20:58:17
	 * @return 是否添加成功
	 */
	public boolean updateIdexTickersByApi(String exchange);

	/**
	 * 更新合约地址
	 * @since 2016年9月21日20:58:17
	 * @return 是否添加成功
	 */
	public boolean updateContractAddress(String exchange);

	/**
	 * 更新总量
	 * @since 2016年9月21日20:58:17
	 * @return 是否添加成功
	 */
	public boolean updateTotalSupply(String exchange);


}
