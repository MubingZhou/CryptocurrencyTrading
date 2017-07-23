package algoTrading;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class BTCChina {
	public static void main(String[] args) {
		// to cal running time
		long startTime1 = System.currentTimeMillis();  
		
		String currency = "btc_cny";
		double price = 1000;
		double amount = 0.01;
		int tradeType = 1;	// 1 - buy; 0 - sell
		
		// trust all certificates
		//UtilFunc.trustAllCertificates(); 
		
		try{
			JSONObject placingOrderRes = placeOrder(price, amount, tradeType, currency);
			String orderId = placingOrderRes.getString("id");
			String message = placingOrderRes.getString("message");
			if(orderId != null){
				System.out.println("Placing order successful! id = " + orderId);
			}else{
				System.out.println("Placing order failed! message = " + message);
			}
			
			JSONObject cancelOrderRes = cancelOrder(orderId, currency);
			System.out.println("Cancelling order res = " + cancelOrderRes.getString("message"));
			

		}catch(Exception e){
			e.printStackTrace();
		}
		
		long endTime2 = System.currentTimeMillis();    //
		System.out.println("========== Total time: " + ((double) endTime2 - startTime1)/1000 + "s ==========");    //

	}

	/**
	 * To place order. Response message stored in a JSONObject
	 * e.g. { "code" : "1000", "message" : "success", "id" : "20131228361867"}	//successful
			{ "code" : "1002", "message" : "Internal Error"}	//failed
	 * @param price
	 * @param amount
	 * @param tradeType
	 * @param currency
	 * @return
	 */
	public static JSONObject placeOrder(double price, double amount, int tradeType, String currency){
		System.out.println("Placing order...");
		
		String params = "method=" + "order" + "&accesskey=" + UtilFunc.accessKey
				+"&price=" + String.valueOf(price) + "&amount=" + String.valueOf(amount)
				+ "&tradeType=" + String.valueOf(tradeType) + "&currency=" + currency;
	    String secret = EncryDigestUtil.digest(UtilFunc.secretKey);
	    String sign = EncryDigestUtil.hmacSign(params, secret);
		String url = UtilFunc.baseURL + "/order?" + params + "&sign=" + sign + "&reqTime=" + System. currentTimeMillis();
		
		return doRequest(url);
	}
	
	/**
	 * to cancel an order
	 *  { "code" : "1000", "message" : "success"}	//successful
		{ "code" : "1002", "message" : "Internal Error"}	//failed

	 * @param orderId
	 * @param currency
	 * @return
	 */
	public static JSONObject cancelOrder(String orderId, String currency){
		System.out.println("Cancelling order...");
		
		String params = "method=" + "cancelorder" + "&accesskey=" + UtilFunc.accessKey
				+"&id=" + orderId + "&currency=" + currency;
	    String secret = EncryDigestUtil.digest(UtilFunc.secretKey);
	    String sign = EncryDigestUtil.hmacSign(params, secret);
		String url = UtilFunc.baseURL + "/cancelOrder?" + params + "&sign=" + sign + "&reqTime=" + System. currentTimeMillis();
		
		//System.out.println(url);
		return doRequest(url);
	}
	
	/**
	 * sample response:
	 * {
		"id" : 123456,
		"type" : 1,
		"price" : 1024,
		"currency": "btc_cny",
		"trade_amount" : 1.5,
		"total_amount" : 3,
		"trade_date" : 1387449019316,
		"status": 3,
		¡°fees¡± : 0
		}
	 * @param orderId
	 * @param currency
	 * @return
	 */
	public static JSONObject getOrder(String orderId, String currency){
		System.out.println("getting order...");
		
		String params = "method=" + "getOrder" + "&accesskey=" + UtilFunc.accessKey
				+"&id=" + orderId + "&currency=" + currency;
	    String secret = EncryDigestUtil.digest(UtilFunc.secretKey);
	    String sign = EncryDigestUtil.hmacSign(params, secret);
		String url = UtilFunc.baseURL + "/getOrder?" + params + "&sign=" + sign + "&reqTime=" + System. currentTimeMillis();
		
		return doRequest(url);
	}
	
	/**
{
	"result"                 : {
		"base"                   : {
			"auth_google_enabled"    : true,
			"auth_mobile_enabled"    : true,
			"trade_password_enabled" : true,
			"username"               : "karmentest"
		},
		"totalAssets"            : 695.4195,
		"netAssets"              : 695.4195,
		"balance"                : {
			"CNY"                    : {
				"amount"                 : 153.1962,
				"currency"               : "CNY",
				"symbol"                 : "%EF%BF%A5"
			},
			"BTC"                    : {
				"amount"                 : 3.8309E-4,
				"currency"               : "BTC",
				"symbol"                 : "%E0%B8%BF"
			},
			"LTC"                    : {
				"amount"                 : 1.0007635,
				"currency"               : "LTC",
				"symbol"                 : "%C5%81"
			},
			"ETH"                    : {
				"amount"                 : 0.16,
				"currency"               : "ETH",
				"symbol"                 : "E"
			},
			"ETC"                    : {
				"amount"                 : 0.0883129,
				"currency"               : "ETC",
				"symbol"                 : "e"
			},
			"BTS"                    : {
				"amount"                 : 2.86725,
				"currency"               : "BTS",
				"symbol"                 : "bts"
			},
			"EOS"                    : {
				"amount"                 : 0.1211363,
				"currency"               : "EOS",
				"symbol"                 : "eos"
			}
		},
		"frozen"                 : {
			"CNY"                    : {
				"amount"                 : 0,
				"currency"               : "CNY",
				"symbol"                 : "%EF%BF%A5"
			},
			"BTC"                    : {
				"amount"                 : 0,
				"currency"               : "BTC",
				"symbol"                 : "%E0%B8%BF"
			},
			"LTC"                    : {
				"amount"                 : 0,
				"currency"               : "LTC",
				"symbol"                 : "%C5%81"
			},
			"ETH"                    : {
				"amount"                 : 0,
				"currency"               : "ETH",
				"symbol"                 : "E"
			},
			"ETC"                    : {
				"amount"                 : 0,
				"currency"               : "ETC",
				"symbol"                 : "e"
			},
			"BTS"                    : {
				"amount"                 : 0,
				"currency"               : "BTS",
				"symbol"                 : "bts"
			},
			"EOS"                    : {
				"amount"                 : 0,
				"currency"               : "EOS",
				"symbol"                 : "eos"
			}
		},
		"p2p"                    : {
			"inCNY"                  : 0,
			"inBTC"                  : 0,
			"inLTC"                  : 0,
			"inETH"                  : 0,
			"inETC"                  : 0,
			"inBTS"                  : 0,
			"inEOS"                  : 0,
			"outCNY"                 : 0,
			"outBTC"                 : 0,
			"outLTC"                 : 0,
			"outETH"                 : 0,
			"outETC"                 : 0,
			"outBTS"                 : 0,
			"outEOS"                 : 0
		}
	}
} 

	 * @return
	 */
	public static JSONObject getAccountInfo(){
		System.out.println("getting account info...");
		
		String params = "method=" + "getAccountInfo" + "&accesskey=" + UtilFunc.accessKey;
	    String secret = EncryDigestUtil.digest(UtilFunc.secretKey);
	    String sign = EncryDigestUtil.hmacSign(params, secret);
		String url = UtilFunc.baseURL + "/getAccountInfo?" + params + "&sign=" + sign + "&reqTime=" + System. currentTimeMillis();
		
		return doRequest(url);
	}
	
	public static JSONObject getKLine(){
		// no need to set access key and secret key
		String url = API_DOMAIN+"/data/v1/kline?currency="+currency+"&times=1min";
	}
	
	/**
	 * get response from url
	 * @param url
	 * @return JSONObject
	 */
	public static JSONObject doRequest(String url){
		// to return
		JSONObject json = null;
		
		long startTime2 = System.currentTimeMillis();  
		
		// build connections
		CloseableHttpClient httpClient = HttpClients.createDefault(); 
		
		HttpGet httpGet = new HttpGet(url);
		//System.out.println("========= executing request: " + httpGet.getURI() +" =========");
		
		try{
			// get response
			CloseableHttpResponse  response = httpClient.execute(httpGet);
			
			HttpEntity responseEntity = response.getEntity();
			StatusLine statusLine = response.getStatusLine();  //HTTP/1.1 OK 200

			 if (responseEntity != null) {  
                 String content = EntityUtils.toString(responseEntity);
                 //System.out.println("Response content: " + content); 
                 
                 json = JSONObject.parseObject(content);
             }  
             //closing connection
             response.close();
             httpClient.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		long endTime2 = System.currentTimeMillis();    //
		System.out.println("URL requesting finished! executing time: " + ((double) endTime2 - startTime2)/1000 + "s");
		return json;
	}
}
