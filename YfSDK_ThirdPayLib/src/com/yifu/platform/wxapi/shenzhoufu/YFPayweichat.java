package com.yifu.platform.wxapi.shenzhoufu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ipaynow.wechatpay.plugin.manager.route.dto.ResponseParams;
import com.ipaynow.wechatpay.plugin.manager.route.impl.ReceivePayResult;
import com.yifu.platform.single.YFErrorCode;
import com.yifu.platform.single.callback.IYFSDKCallBack;
import com.yifu.platform.single.control.PayChannel;
import com.yifu.platform.single.control.pay.YFPayController;
import com.yifu.platform.single.internal.YFPlatformInternal;
import com.yifu.platform.single.item.YFOrderInfoData;
import com.yifu.platform.single.item.YFOrderStatus;
import com.yifu.platform.single.json.JSONManager;
import com.yifu.platform.single.net.ConnectManagerHelper;
import com.yifu.platform.single.net.INetListener;
import com.yifu.platform.single.net.NetManager;
import com.yifu.platform.single.net.response.BaseResult;
import com.yifu.platform.single.net.response.QueryOrderStatusResult;
import com.yifu.platform.single.net.response.UploadOrderInfoResult;
import com.yifu.platform.single.order.Order;
import com.yifu.platform.single.util.Constants;
import com.yifu.platform.single.util.PayOrderChannel;
import com.yifu.platform.single.util.SMSOrderIDGenerator;
import com.yifu.platform.single.util.SharePreferenceUtil;
import com.yifu.platform.single.util.YFUtil;
import com.yifu.platform.wxapi.Md5Util;

/**
 * 神州付微信支付
 * @author lijilei
 *
 */

public class YFPayweichat extends PayChannel implements INetListener,
		ReceivePayResult {
	private Order order;
	private String orderId;
	private Context context;
	private int times = 0;// 查询订单的次数
	IYFSDKCallBack mSDKCallBack = null;
	// 神州付请求地址
	private static final String requsetUrl = "http://pay3.shenzhoufu.com/interface/version3/entry.aspx";
	// 商户id
	private static final String merId = "122713";
	// 服务端回调地址
	// private static final String severReturnUrl =
	// "http://123.57.237.83/sdkapi/weixincborder";
	private static final String severReturnUrl = "http://123.57.237.83/weixincborder";
	// 订单地址
	private static final String productUrl = "www.shenzhoufusd.com";
	// 密钥
	private static final String privateKey = "382FWOHF8HS023ET";
	// 页面返回地址
	private static final String pageReturnUrl = "www.hlyf.com";

	@Override
	public void doPay(Context context, IYFSDKCallBack mSDKCallBack, Order order) {

		// System.out.println(context);
		if (!YFUtil.isAppAvilible(context, "com.tencent.mm")) {
			Toast.makeText(context, "请确认您的手机安装微信客户端后重试！", Toast.LENGTH_LONG)
					.show();
			return;
		}
		this.context = context;
		this.mSDKCallBack = mSDKCallBack;
		// 获取订单
		this.order = order;
		if (order == null) {
			throw new NullPointerException("orderValue can not be null.");
		}

		// 更新第三方支付方式sp
		SharePreferenceUtil.getInstance(context).saveString(
				Constants.SHARE_LAST_THIRDPAY, Constants.CHANNEL_WEIXIN);
		// 生成订单信息
		StringBuilder orderid = new StringBuilder("");
		orderid.append("YF_").append(SMSOrderIDGenerator.getOrderID(8));

		order.setOrder_id(orderid.toString());

		if (ConnectManagerHelper.isNetConnect()) {
			uploadOrderInfo(orderid);
		} else {
			Toast.makeText(context, "网络连接错误，请检查网络", Toast.LENGTH_SHORT).show();
			return;
		}
	}

	private void startPay() {
		final String url = preRequestparam();
		/*
		 * HttpClient client = new DefaultHttpClient(); HttpGet get = new
		 * HttpGet(url); StringBuilder result = new StringBuilder(); try {
		 * HttpResponse res = client.execute(get); if
		 * (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
		 * HttpEntity entity = res.getEntity(); InputStreamReader isr = new
		 * InputStreamReader(entity.getContent(), HTTP.UTF_8);
		 * 
		 * BufferedReader bufferedReader = new BufferedReader(isr);
		 * 
		 * String line = null;
		 * 
		 * while ((line = bufferedReader.readLine()) != null) {
		 * result.append(line + "\n"); } } } catch (Exception e) { throw new
		 * RuntimeException(e);
		 * 
		 * } finally { // 关闭连接 ,释放资源 client.getConnectionManager().shutdown(); }
		 */

		// Intent intent = new Intent(Intent.ACTION_VIEW,
		// Uri.parse(url));
		// context.startActivity(intent);

		final WebView webView = new WebView(context);

		webView.setWebChromeClient(new WebChromeClient());

		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view,
					String url_Turntable) {
				// System.out.println("跳转url==" + url_Turntable);
				if (url_Turntable.startsWith("http:")
						|| url_Turntable.startsWith("https:")) {
					// webView.loadUrl(url_Turntable);
					return false;
				}
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(url_Turntable));
				context.startActivity(intent);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// System.out.println("开始加载=====" + url);
			}

			@Override
			public void onPageFinished(WebView view, String finish_url) {
				// System.out.println("完成加载==" + finish_url);
				if (finish_url.contains(pageReturnUrl)
						&& !finish_url.equals(url)) {
					queryOrderInfo(order.order_id);
					webView.removeAllViews();
					webView.destroy();
				}
			}

		});

		WebSettings s = webView.getSettings();
		s.setJavaScriptEnabled(true);
		webView.loadUrl(url);

	}

	/**
	 * privateField要以商户号开头
	 * 
	 * @return
	 */
	private String preRequestparam() {
		Map<String, String> map = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new Date());

		String version = "3";
		String payMoney = order.price;
		String orderId = date + "-" + merId + "-" + order.order_id;

		String privateField = order.mchNo + order.order_id;
		String verifyType = "1";
		String returnType = "3";
		String isDebug = "0";

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("version", version));
		params.add(new BasicNameValuePair("merId", merId));
		params.add(new BasicNameValuePair("payMoney", payMoney));
		params.add(new BasicNameValuePair("orderId", orderId));
		params.add(new BasicNameValuePair("pageReturnUrl", pageReturnUrl));
		params.add(new BasicNameValuePair("serverReturnUrl", severReturnUrl));
		params.add(new BasicNameValuePair("merUserName", order.mchNo));
		params.add(new BasicNameValuePair("productUrl", productUrl));
		params.add(new BasicNameValuePair("itemName", order.desc));
		params.add(new BasicNameValuePair("gatewayId", "14"));
		params.add(new BasicNameValuePair("privateField", privateField));
		params.add(new BasicNameValuePair("verifyType", verifyType));
		params.add(new BasicNameValuePair("returnType", returnType));
		params.add(new BasicNameValuePair("isDebug", isDebug));
		String str = version + "|" + merId + "|" + payMoney + "|" + orderId
				+ "|" + pageReturnUrl + "|" + severReturnUrl + "|"
				+ privateField + "|" + privateKey + "|" + verifyType + "|"
				+ returnType + "|" + isDebug;
		String md5String = Md5Util.md5(str);

		params.add(new BasicNameValuePair("md5String", md5String));
		String url = requsetUrl + "?"
				+ URLEncodedUtils.format(params, HTTP.UTF_8);

		return url;
	}

	/**
	 * 上传订单信息
	 * 
	 * @param orderid
	 */
	private void uploadOrderInfo(StringBuilder orderid) {
		String content = order.merchant_id;
		// tag3,上传订单信息
		String request = JSONManager.getJsonBuilder()
				.buildSmsOrderInfoUploadString(order.channel,
						orderid.toString(), order.price, order.item_id,
						content, order.desc, order.userdata);
		NetManager.getHttpConnect().sendRequest(
				Constants.URL_UPLOAD_ORDER_INFO,
				Constants.TAGS_UPLOAD_ORDER_INFO, request, this);
	}

	@Override
	public void onIpaynowTransResult(ResponseParams arg0) {

	}

	@Override
	public void onNetResponse(int requestTag, BaseResult responseData,
			int requestId) {
		if (Constants.TAGS_UPLOAD_ORDER_INFO == requestTag) {
			UploadOrderInfoResult result = (UploadOrderInfoResult) responseData;
			if (null != result) {
				if (Constants.DC_OK == result.getErrorCode()) {
					if (result.getOrder_status() == 4) {// 订单号重复
						StringBuilder orderid = new StringBuilder("");
						orderid.append("YF_").append(
								SMSOrderIDGenerator.getOrderID(8));

						order.setOrder_id(orderid.toString());

						uploadOrderInfo(orderid);
						return;
					}
					if (result.getOrder_status() == 3) {// 交易成功
						return;
					}
					// Save the info of order.
					startPay();
				} else {
					Toast.makeText(context, "订单创建失败", Toast.LENGTH_SHORT)
							.show();
				}
			}
		} else if (Constants.TAGS_QUERY_ORDER_STATUS == requestTag) {// 查询订单状态
			if (requestTag == Constants.TAGS_QUERY_ORDER_STATUS) {
				final QueryOrderStatusResult qosr = (QueryOrderStatusResult) responseData;

				int status = qosr.getOrderstatus();
				YFOrderInfoData orderInfo = new YFOrderInfoData();
				orderInfo.setyfOrderPayChannel(PayOrderChannel.CHANNEL_WEIXIN);
				orderInfo.setyfOrderPrice(order.price);
				orderInfo.setyfOrderProductId(order.item_id);
				orderInfo.setyfOrderId(order.order_id);

				System.out.println("订单状态====" + status);
				if (YFOrderStatus.YF_ORDER_STATUS_SUCCESS.getValue() == status) {// 成功
					orderInfo
							.setyfOrderStatus(YFOrderStatus.YF_ORDER_STATUS_SUCCESS);
					SharePreferenceUtil.getInstance(context).saveString(
							Constants.SHARE_LAST_THIRDPAY_SUCCESS,
							Constants.CHANNEL_WEIXIN);
					YFPayController.getInstance().onPlatformRechargeResult(
							YFErrorCode.BDG_RECHARGE_SUCCESS, mSDKCallBack,
							orderInfo);
				} else {
					if (times < 20) {
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								queryOrderInfo(qosr.getOrderid());
							}
						}, 50);

					} else {

						if (YFOrderStatus.YF_ORDER_STATUS_DEALING.getValue() == status) {// 处理中
							orderInfo
									.setyfOrderStatus(YFOrderStatus.YF_ORDER_STATUS_FAIL);
							YFPayController.getInstance()
									.onPlatformRechargeResult(
											YFErrorCode.BDG_RECHARGE_FAIL,
											mSDKCallBack, orderInfo);

						} else {// 失败
							orderInfo
									.setyfOrderStatus(YFOrderStatus.YF_ORDER_STATUS_FAIL);
							YFPayController.getInstance()
									.onPlatformRechargeResult(
											YFErrorCode.BDG_RECHARGE_FAIL,
											mSDKCallBack, orderInfo);
						}
					}
					defaultWeChatExit(context);
				}

			}
		}
	}

	@Override
	public void onDownLoadStatus(DownLoadStatus status, int requestId) {
	}

	@Override
	public void onDownLoadProgressCurSize(long curSize, long totalSize,
			int requestId) {
	}

	@Override
	public void onNetResponseErr(int requestTag, int requestId, int errorCode,
			String msg) {
	}

	/**
	 * 查询订单
	 */
	public void queryOrderInfo(String orderId) {
		times++;
		String data = JSONManager.getJsonBuilder()
				.buildQueryStatusOfOrderString(orderId);
		NetManager.getHttpConnect().sendRequest(
				Constants.URL_QUERY_ORDER_STATUS,
				Constants.TAGS_QUERY_ORDER_STATUS, data, this);

	}

	/**
	 * 支付失败的处理
	 * 
	 * @param context
	 */
	private void defaultWeChatExit(Context context) {
		String lastSuccess = SharePreferenceUtil.getInstance(context)
				.getString(Constants.SHARE_LAST_THIRDPAY_SUCCESS);
		// 上一次支付成功微信
		if (lastSuccess.equals(Constants.CHANNEL_WEIXIN)) {
			SharePreferenceUtil.getInstance(context).saveString(
					Constants.SHARE_LAST_THIRDPAY_SUCCESS, "");

		}
	}

}
