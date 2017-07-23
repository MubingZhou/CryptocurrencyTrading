package com.world.controller.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

public class RestTest {
	
	private static Logger log = Logger.getLogger(RestTest.class);
	
	//正式
	public static String ACCESS_KEY = "826afc09-xxxx-4ebc-b31c-64956082c705";
	public static String SECRET_KEY = "ab391be1-xxxx-4252-83fc-8b358bd72252";
	public static String URL_PREFIX = "https://trade.chbtc.com/api/";
	public static String PAY_PASS = "xxxx";

	/**
	 * 委托下单
	 * tradeType 1买，0�?
	 */
	@Test
	public void testOrder(){
		try{
			String SECRET_KEY = EncryDigestUtil.digest(this.SECRET_KEY);
			//�?加密的请求参数， tradeType=0卖单
			String params = "method=order&accesskey="+ACCESS_KEY+"&price=1&amount=1.51234&tradeType=1&currency=btc_cny";
			System.out.println("params=" + params);
			//参数执行加密
			String hash = EncryDigestUtil.hmacSign(params, SECRET_KEY);
			//请求地址
			String url = URL_PREFIX+"order?" + params + "&sign=" + hash + "&reqTime=" + System.currentTimeMillis();
			System.out.println("testOrder url: " + url);
			//请求测试
			String callback = get(url, "UTF-8");
			System.out.println("testOrder 结果: " + callback);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 取消下单
	 */
	@Test
	public void testCancelOrder(){
		String orderId = "2017032816xx";
		try{
			String SECRET_KEY = EncryDigestUtil.digest(this.SECRET_KEY);
			//�?加密的请求参�?
			String params = "method=cancelOrder&accesskey="+ACCESS_KEY + "&id=" + orderId + "&currency=btc_cny";
			//参数执行加密
			String hash = EncryDigestUtil.hmacSign(params, SECRET_KEY);
			//请求地址
			String url = URL_PREFIX+"cancelOrder?" + params + "&sign=" + hash + "&reqTime=" + System.currentTimeMillis();
			System.out.println("testGetOrder url: " + url);
			//请求测试
			String callback = get(url, "UTF-8");
			System.out.println("testGetOrder 结果: " + callback);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 获取订单信息
	 */
	@Test
	public void testGetOrder(){
		String orderId = "2017040315519xxx";
		try{
			String SECRET_KEY = EncryDigestUtil.digest(this.SECRET_KEY);
			//�?加密的请求参�?
			String params = "method=getOrder&accesskey="+ACCESS_KEY + "&id=" + orderId + "&currency=btc_cny";
			//参数执行加密
			String hash = EncryDigestUtil.hmacSign(params, SECRET_KEY);
			//请求地址
			String url = URL_PREFIX+"getOrder?" + params + "&sign=" + hash + "&reqTime=" + System.currentTimeMillis();
			System.out.println("testGetOrder url: " + url);
			//请求测试
			String callback = get(url, "UTF-8");
			System.out.println("testGetOrder 结果: " + callback);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 获取多个委托买单或卖单，每次请求返回10条记�?
	 */
	@Test
	public void testGetOrders(){
		try{
			String[] currencyArr = new String[]{"btc_cny", "ltc_cny", "eth_cny", "etc_cny"};
			for(String currency : currencyArr){
				String SECRET_KEY = EncryDigestUtil.digest(this.SECRET_KEY);
				//�?加密的请求参�?
				String params = "method=getOrders&accesskey="+ACCESS_KEY + "&tradeType=1&currency=" + currency + "&pageIndex=1";
				//参数执行加密
				String hash = EncryDigestUtil.hmacSign(params, SECRET_KEY);
				//请求地址
				String url = URL_PREFIX+"getOrders?" + params + "&sign=" + hash + "&reqTime=" + System.currentTimeMillis();
				log.info("testGetOrders url: " + url);
				//请求测试
				String callback = get(url, "UTF-8");
				log.info("testGetOrders 结果: " + callback);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * (�?)获取多个委托买单或卖单，每次请求返回pageSize<=100条记�?
	 */
	@Test
	public void testGetOrdersNew(){
		try{
			String[] currencyArr = new String[]{"btc_cny", "ltc_cny", "eth_cny", "etc_cny"};
			for(String currency : currencyArr){
				String SECRET_KEY = EncryDigestUtil.digest(this.SECRET_KEY);	
				//�?加密的请求参�?
				String params = "method=getOrdersNew&accesskey="+ACCESS_KEY + "&tradeType=1&currency=" + currency + "&pageIndex=1&pageSize=1";
				//参数执行加密
				String hash = EncryDigestUtil.hmacSign(params, SECRET_KEY);
				//请求地址
				String url = URL_PREFIX+"getOrdersNew?" + params + "&sign=" + hash + "&reqTime=" + System.currentTimeMillis();
				log.info("testGetOrdersNew url: " + url);
				//请求测试
				String callback = get(url, "UTF-8");
				log.info("testGetOrdersNew 结果: " + callback);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * 与getOrdersNew的区别是取消tradeType字段过滤，可同时获取买单和卖单，每次请求返回pageSize<=100条记�?
	 */
	@Test
	public void getOrdersIgnoreTradeType(){
		try{
			String[] currencyArr = new String[]{"btc_cny", "ltc_cny", "eth_cny", "etc_cny"};
			for(String currency : currencyArr){
				String SECRET_KEY = EncryDigestUtil.digest(this.SECRET_KEY);	
				//�?加密的请求参�?
				String params = "method=getOrdersIgnoreTradeType&accesskey="+ACCESS_KEY + "&currency=" + currency + "&pageIndex=1&pageSize=1";
				//参数执行加密
				String hash = EncryDigestUtil.hmacSign(params, SECRET_KEY);
				//请求地址
				String url = URL_PREFIX+"getOrdersIgnoreTradeType?" + params + "&sign=" + hash + "&reqTime=" + System.currentTimeMillis();
				log.info("getOrdersIgnoreTradeType url: " + url);
				//请求测试
				String callback = get(url, "UTF-8");
				log.info("getOrdersIgnoreTradeType 结果: " + callback);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * 获取未成交或部份成交的买单和卖单，每次请求返回pageSize<=100条记�?
	 */
	@Test
	public void getUnfinishedOrdersIgnoreTradeType(){
		try{
			String[] currencyArr = new String[]{"btc_cny", "ltc_cny", "eth_cny", "etc_cny"};
			for(String currency : currencyArr){
				String SECRET_KEY = EncryDigestUtil.digest(this.SECRET_KEY);	
				//�?加密的请求参�?
				String params = "method=getUnfinishedOrdersIgnoreTradeType&accesskey="+ACCESS_KEY + "&currency=" + currency + "&pageIndex=1&pageSize=20";
				//参数执行加密
				String hash = EncryDigestUtil.hmacSign(params, SECRET_KEY);
				//请求地址
				String url = URL_PREFIX+"getUnfinishedOrdersIgnoreTradeType?" + params + "&sign=" + hash + "&reqTime=" + System.currentTimeMillis();
				log.info("getUnfinishedOrdersIgnoreTradeType url: " + url);
				//请求测试
				String callback = get(url, "UTF-8");
				log.info("getUnfinishedOrdersIgnoreTradeType 结果: " + callback);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 获取个人信息
	 */
	@Test
	public void testGetAccountInfo(){
        try{
			String SECRET_KEY = EncryDigestUtil.digest(this.SECRET_KEY);	
			//�?加密的请求参�?
			String params = "method=getAccountInfo&accesskey="+ACCESS_KEY;
			//参数执行加密
			String hash = EncryDigestUtil.hmacSign(params, SECRET_KEY);
			//请求地址
			String url = URL_PREFIX+"getAccountInfo?" + params + "&sign=" + hash + "&reqTime=" + System.currentTimeMillis();
			log.info("testGetAccountInfo url: " + url);
			//请求测试
			String callback = get(url, "UTF-8");
			log.info("testGetAccountInfo 结果: " + callback);
			Assert.assertNotSame(callback, "{}");
		}catch(Exception ex){
			ex.printStackTrace();
		}
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();
	}
	
	/**
	 * 获取个人的充值地�?
	 */
	@Test
	public void testGetUserAddress(){
		try{
			String SECRET_KEY = EncryDigestUtil.digest(this.SECRET_KEY);	
			//�?加密的请求参�?
			String params = "method=getUserAddress&accesskey="+ACCESS_KEY+"&currency=btc";
			//参数执行加密
			String hash = EncryDigestUtil.hmacSign(params, SECRET_KEY);
			//请求地址
			String url = URL_PREFIX+"getUserAddress?" + params + "&sign=" + hash + "&reqTime=" + System.currentTimeMillis();
			System.out.println("getUserAddress url: " + url);
			//请求测试
			String callback = get(url, "UTF-8");
			System.out.println("getUserAddress 结果: " + callback);
			Assert.assertNotSame(callback, "{}");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 获取认证的提现地�?
	 */
	@Test
	public void testGetWithdrawAddress(){
		try{
			String SECRET_KEY = EncryDigestUtil.digest(this.SECRET_KEY);	
			//�?加密的请求参�?
			String params = "method=getWithdrawAddress&accesskey="+ACCESS_KEY+"&currency=etc";
			//参数执行加密
			String hash = EncryDigestUtil.hmacSign(params, SECRET_KEY);
			//请求地址
			String url = URL_PREFIX+"getWithdrawAddress?" + params + "&sign=" + hash + "&reqTime=" + System.currentTimeMillis();
			System.out.println("getWithdrawAddress url: " + url);
			//请求测试
			String callback = get(url, "UTF-8");
			System.out.println("getWithdrawAddress 结果: " + callback);
			Assert.assertNotSame(callback, "{}");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 获取提现记录
	 */
	@Test
	public void testGetWithdrawRecord(){
		try{
			String SECRET_KEY = EncryDigestUtil.digest(this.SECRET_KEY);	
			//�?加密的请求参�?
			String params = "method=getWithdrawRecord&accesskey="+ACCESS_KEY+"&currency=eth&pageIndex=1&pageSize=10";
			//参数执行加密
			String hash = EncryDigestUtil.hmacSign(params, SECRET_KEY);
			//请求地址
			String url = URL_PREFIX+"getWithdrawRecord?" + params + "&sign=" + hash + "&reqTime=" + System.currentTimeMillis();
			System.out.println("getWithdrawRecord url: " + url);
			//请求测试
			String callback = get(url, "UTF-8");
			System.out.println("getWithdrawRecord 结果: " + callback);
			Assert.assertNotSame(callback, "{}");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 获取虚拟货币充�?�记�?
	 */
	@Test
	public void testGetChargeRecord(){
		try{
			String SECRET_KEY = EncryDigestUtil.digest(this.SECRET_KEY);	
			//�?加密的请求参�?
			String params = "method=getChargeRecord&accesskey="+ACCESS_KEY+"&currency=btc&pageIndex=2&pageSize=10";
			//参数执行加密
			String hash = EncryDigestUtil.hmacSign(params, SECRET_KEY);
			//请求地址
			String url = URL_PREFIX+"getChargeRecord?" + params + "&sign=" + hash + "&reqTime=" + System.currentTimeMillis();
			System.out.println("getChargeRecord url: " + url);
			//请求测试
			String callback = get(url, "UTF-8");
			System.out.println("getChargeRecord 结果: " + JsonFormatTool.formatJson(callback, "\t") );
			Assert.assertNotSame(callback, "{}");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 获取人民币充值记�?
	 */
	@Test
	public void testGetCnyChargeRecord(){
		try{
			String SECRET_KEY = EncryDigestUtil.digest(this.SECRET_KEY);	
			//�?加密的请求参�?
			String params = "method=getCnyChargeRecord&accesskey="+ACCESS_KEY+"&pageIndex=1&pageSize=10";
			//参数执行加密
			String hash = EncryDigestUtil.hmacSign(params, SECRET_KEY);
			//请求地址
			String url = URL_PREFIX+"getCnyChargeRecord?" + params + "&sign=" + hash + "&reqTime=" + System.currentTimeMillis();
			System.out.println("getCnyChargeRecord url: " + url);
			//请求测试
			String callback = get(url, "UTF-8");
			System.out.println("getCnyChargeRecord 结果: " + JsonFormatTool.formatJson(callback, "\t") );
			Assert.assertNotSame(callback, "{}");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 获取人民币提现记�?
	 */
	@Test
	public void testGetCnyWithdrawRecord(){
		try{
			String SECRET_KEY = EncryDigestUtil.digest(this.SECRET_KEY);	
			//�?加密的请求参�?
			String params = "method=getCnyWithdrawRecord&accesskey="+ACCESS_KEY+"&pageIndex=1&pageSize=10";
			//参数执行加密
			String hash = EncryDigestUtil.hmacSign(params, SECRET_KEY);
			//请求地址
			String url = URL_PREFIX+"getCnyWithdrawRecord?" + params + "&sign=" + hash + "&reqTime=" + System.currentTimeMillis();
			System.out.println("getCnyWithdrawRecord url: " + url);
			//请求测试
			String callback = get(url, "UTF-8");
			System.out.println("getCnyWithdrawRecord 结果: " + JsonFormatTool.formatJson(callback, "\t") );
			Assert.assertNotSame(callback, "{}");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 取消提现操作
	 */
	@Test
	public void testCancelWithdraw(){
		try{
			String SECRET_KEY = EncryDigestUtil.digest(this.SECRET_KEY);	
			//�?加密的请求参�?
			String params = "method=cancelWithdraw&accesskey="+ACCESS_KEY+"&currency=etc&downloadId=2016090xxx&safePwd="+PAY_PASS;
			//参数执行加密
			String hash = EncryDigestUtil.hmacSign(params, SECRET_KEY);
			//请求地址
			String url = URL_PREFIX+"cancelWithdraw?" + params + "&sign=" + hash + "&reqTime=" + System.currentTimeMillis();
			System.out.println("cancelWithdraw url: " + url);
			//请求测试
			String callback = get(url, "UTF-8");
			System.out.println("cancelWithdraw 结果: " + callback);
			Assert.assertNotSame(callback, "{}");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 提现操作
	 */
	@Test
	public void withdraw(){
		try{
			String SECRET_KEY = EncryDigestUtil.digest(this.SECRET_KEY);	
			String addr = "143GwqgnjNi5DqGv4xzwqeGTi7BGxxxxxx";
			String fees = "0.0003";
			String currency = "etc";
			String amount = "0.0004";
			String itransfer = "0";	 
//			String params = "method=withdraw" +"&accesskey=" + ACCESS_KEY + "&amount=" + amount + "&currency=" + currency
//					+ "&fees=" + fees + "&receiveAddr=" + addr + "&safePwd=" + PAY_PASS;
			String params = "accesskey=" + ACCESS_KEY + "&amount=" + amount + "&currency=" + currency + "&fees=" + fees
					+ "&itransfer=" + itransfer + "&method=withdraw&receiveAddr=" + addr + "&safePwd=" + PAY_PASS;
			System.out.println(params);
			//参数执行加密
			String hash = EncryDigestUtil.hmacSign(params, SECRET_KEY);
			//请求地址
			String url = URL_PREFIX+"withdraw?" + params + "&sign=" + hash + "&reqTime=" + System.currentTimeMillis();
			System.out.println("withdraw url: " + url);
			//请求测试
			String callback = get(url, "UTF-8");
			System.out.println("withdraw 结果: " + callback);
			Assert.assertNotSame(callback, "{}");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static String API_DOMAIN = "http://api.chbtc.com";
	
	/**
	 * 测试获取行情
	 */
	@Test
	public void testTicker() {
		try {
			String currency = "etc_cny";
			// 请求地址
			String url = API_DOMAIN+"/data/v1/ticker?currency="+currency;
			log.info(currency + "-testTicker url: " + url);
			// 请求测试
			String callback = get(url, "UTF-8");
			log.info(currency + "-testTicker 结果: " + callback);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 测试获取深度
	 */
	@Test
	public void testDepth() {
		try {
			String currency = "etc_cny";
			String merge = "0.1";
			// 请求地址
			String url = API_DOMAIN+"/data/v1/depth?currency=" + currency;
//			String url = API_DOMAIN+"/data/v1/depth?currency=" + currency + "&size=3&merge=" + merge;
//			String url = API_DOMAIN+"/data/v1/depth?currency=" + currency + "&size=3";
			log.info(currency + "-testDepth url: " + url);
			// 请求测试
			String callback = get(url, "UTF-8");
			log.info(currency + "-testDepth 结果: " + callback);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 测试获取�?近交易记�?
	 */
	@Test
	public void testTrades() {
		try {
			String currency = "etc_cny";
			// 请求地址
			String url = API_DOMAIN+"/data/v1/trades?currency="+currency;
			log.info(currency + "-testTrades url: " + url);
			// 请求测试
			String callback = get(url, "UTF-8");
			log.info(currency + "-testTrades 结果: " + callback);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 测试获取K线数�?
	 */
	@Test
	public void testKline() {
		try {
			String currency = "etc_cny";
			// 请求地址
			String url = API_DOMAIN+"/data/v1/kline?currency="+currency+"&times=1min";
			log.info(currency + "-testKline url: " + url);
			// 请求测试
			String callback = get(url, "UTF-8");
			JSONObject json = JSONObject.parseObject(callback);
			log.info(currency + "-testKline 结果: " + json.toJSONString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param urlAll
	 *            :请求接口
	 * @param charset
	 *            :字符编码
	 * @return 返回json结果
	 */
	public String get(String urlAll, String charset) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";// 模拟浏览�?
		try {
			URL url = new URL(urlAll);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(30000);
			connection.setConnectTimeout(30000);
			connection.setRequestProperty("User-agent", userAgent);
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, charset));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
