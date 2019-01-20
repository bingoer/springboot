package com.coin.otaku.utils;

import de.elbatya.cryptocoins.bittrexclient.AicoinClient;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.CommonInterval;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.CommonIntervalMap;
import de.elbatya.cryptocoins.bittrexclient.api.model.publicapi.aicoin.Kline;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import wang.raye.springboot.ApiApplication;
import wang.raye.springboot.model.Symbols;
import wang.raye.springboot.model.SymbolsCriteria;
import wang.raye.springboot.model.mapper.SymbolsMapper;
import wang.raye.springboot.server.PairsServer;
import wang.raye.springboot.utils.ParseUtils;
import wang.raye.springboot.utils.QuotaCrossUtils;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApiApplication.class)
@WebAppConfiguration
public class QuotaCrossUtilsTests {

	@Autowired
	private QuotaCrossUtils quotaCrossUtils;
	@Autowired
	private PairsServer pairsServer;


	@Test
	public void getStart() {

//		AicoinClient aicoinClient = new AicoinClient();
//		// aicoin的symbol
//		String symbol = "binancebtcusdt";
//		// aicoin的周线
//		int step = CommonIntervalMap.ENUMMAP.get(CommonInterval.DAILY);
//		// 调用aicoin的k线api
//		List<Kline> listKline = aicoinClient.getPublicApi().getKline(symbol, step).unwrap();
//		listKline.remove(listKline.size()-1);

//		String status = quotaCrossUtils.getStart(listKline,3,1,10, 30);
//		System.out.println("状态:"+ status);

//		pairsServer.newPairs();

	}

}
