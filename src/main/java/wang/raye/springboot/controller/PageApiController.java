package wang.raye.springboot.controller;

import de.elbatya.cryptocoins.bittrexclient.AicoinClient;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.CommonInterval;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.CommonIntervalMap;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.aicoin.Kline;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.raye.springboot.bean.*;
import wang.raye.springboot.model.MacdCross;
import wang.raye.springboot.model.TradePoint;
import wang.raye.springboot.param.PointParam;
import wang.raye.springboot.server.CrossApiServer;
import wang.raye.springboot.server.MapperServer;
import wang.raye.springboot.utils.DateUtils;
import wang.raye.springboot.utils.QuotaUtils;

import java.util.ArrayList;
import java.util.List;

@Api(value="页面相关操作的接口")
@RestController
@RequestMapping("/page")
public class PageApiController {

	@Autowired
	private QuotaUtils quotaUtils;

	@Autowired
	private MapperServer mapperServer;

	/**
	 * 根据id查询k线信息
	 * @since 2016年9月22日20:32:43
	 * @return
	 */
	@RequestMapping("/addpoint")
	@ApiOperation(notes="添加买卖点信息",value="买卖点信息列表",httpMethod="POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name="pointParam",value = "买卖点信息集合",dataType="PointParam")
	})
	public boolean addTradePoint(@RequestBody PointParam pointParam){

		String aicoin_symbol = (pointParam.getExchange() + pointParam.getSymbol() + pointParam.getBase()).toLowerCase();
		// aicoin的周线
		int step = CommonIntervalMap.ENUMMAP.get(CommonInterval.valueOf(pointParam.getPeriod()));
//		AicoinClient aicoinClient = new AicoinClient();
//		List<Kline> candlestickList = candlestickList = aicoinClient.getPublicApi().getKline(aicoin_symbol, step).unwrap();
//
//		List<MacdBean> macdBeanList =  quotaUtils.getMACD(12,26,9,candlestickList);
//		List<BollBean> bollBeanList =  quotaUtils.getBoll(20,2,candlestickList);
//		List<KdjBean> kdjBeanList = quotaUtils.getKDJ(9,3,3,candlestickList);
//		List<VolumeBean> volBeanList = quotaUtils.getVolume(5,10,candlestickList);
//
//		List<MaBean> maBeanList = quotaUtils.getMa(5,10,30,120,candlestickList);
//
//		List<RsiBean> rsiBeanList = quotaUtils.getRsi(6, 12, 24, candlestickList);
//
//		List<DmiBean> dmiBeanList = quotaUtils.getDmi(14, 6, candlestickList);
//
//		List<VrBean> vrBeanList = quotaUtils.getVr(26, 6, candlestickList);
//		List<SarBean> sarBeanList = quotaUtils.getSar(candlestickList);

		List<String> timeList = pointParam.getTimestamp();

		TradePoint tradePoint = new TradePoint();
		tradePoint.setExchange(pointParam.getExchange());
		tradePoint.setSymbol(pointParam.getSymbol());
		tradePoint.setBase(pointParam.getBase());
		tradePoint.setPeriod(CommonInterval.valueOf(pointParam.getPeriod()).getIntervalId());
		tradePoint.setStatus(pointParam.getStatus());
		tradePoint.setQuantType(pointParam.getQuantType());

		for (String time:timeList) {
			tradePoint.setKlineTime(DateUtils.format(Long.valueOf(time)));
			List<TradePoint> existTradePoint = mapperServer.getTradePoint(tradePoint);
			if (null == existTradePoint || existTradePoint.size() == 0) {
				mapperServer.addTradePoint(tradePoint);
			}
		}

		return true;
	}
}
