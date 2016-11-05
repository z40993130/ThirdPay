package com.yifu.platform.wxapi.yibao;

import java.net.URLEncoder;
import java.util.TreeMap;

import org.json.JSONObject;





import android.content.Intent;
import android.net.Uri;

import com.yifu.platform.single.util.Constants;
import com.yifu.platform.single.util.PhoneUtil;
import com.yifu.platform.wxapi.CommonThirdPayWap;

public class YFPayYiBao extends CommonThirdPayWap{

@Override
protected void startPay() {
	super.startPay();
//	String url = preRequestparam();
//	context.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url)));
}

	@Override
	public String preRequestparam() {
		String imeiString = PhoneUtil.getIMEI(context.getApplicationContext());
		int directpaytype=0;
		if (Constants.CHANNEL_WEIXIN == order.channel) {
			directpaytype =1;
		} else if (Constants.CHANNEL_ALIPAY == order.channel) {
			directpaytype = 2;
		}
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		map.put("merchantaccount", YiBaoConfig.MERCHANTACCOUNT);
		map.put("orderid", order.mchNo + order.order_id);
		map.put("transtime", System.currentTimeMillis() / 1000);
		map.put("amount", Integer.valueOf(order.price));
		map.put("productcatalog", "1");
		map.put("productname", order.item_name);
		map.put("identitytype", 0);
		map.put("terminaltype", 0);
		map.put("terminalid", imeiString);
		map.put("userip", Constants.ipAdress);
		map.put("directpaytype", directpaytype);
		map.put("identityid", imeiString);
		map.put("callbackurl", YiBaoConfig.NOTIFY_URL);
		String sign = EncryUtil.handleRSA(map, YiBaoConfig.RSA_PRIVATE_KEY);
		map.put("sign", sign);
		String data = "";
		String encryptkey = "";
		try {
			String jsonStr = new JSONObject(map).toString();
			String aeskey = EncryUtil.getRandom(16);
			data = AES.encryptToBase64(jsonStr, aeskey);
			encryptkey = RSA.encrypt(aeskey, YiBaoConfig.YIBAO_RSA_PUBLIC_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = YiBaoConfig.REQUEST_URL + "?" + "data=" + URLEncoder.encode(data) + "&encryptkey=" + URLEncoder.encode(encryptkey) + "&merchantaccount="
				+ YiBaoConfig.MERCHANTACCOUNT;
		return url;
	}

}
