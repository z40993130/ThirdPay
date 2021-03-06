package com.yifu.platform.single.net;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpHost;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;



import com.yifu.platform.single.internal.YFPlatformInternal;

import android.content.Context;

/**
 * @ClassName: HttpClientHelper
 */
public class HttpClientHelper {
	private static HttpClient httpClient;
	private static HttpParams params = null;

	private HttpClientHelper() {

	}

	// --------------------------Http相关-----------------------
	public static synchronized HttpClient getHttpClient() {
		if (null == httpClient) {
			params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
			HttpProtocolParams.setUseExpectContinue(params, true);

			ConnManagerParams.setTimeout(params, 10 * 1000);
			HttpConnectionParams.setConnectionTimeout(params, 10 * 1000);
			HttpConnectionParams.setSoTimeout(params, 30 * 1000);

			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			// schReg.register(new Scheme("https",
			// SSLSocketFactory.getSocketFactory(), 80));

			ClientConnectionManager connManager = new ThreadSafeClientConnManager(params, schReg);

			httpClient = new DefaultHttpClient(connManager, params);

		}
		Context _con = (Context) YFPlatformInternal.getInstance().getmContext();
		if(_con == null){
			return null;
		}		ConnectManager conn = new ConnectManager(_con);

		if (conn.isWapNetwork()) {
			if (params.getParameter(ConnRouteParams.DEFAULT_PROXY) != null) {
				// 此处需要先删除以前的参数，host、port有可能发生变化
				params.removeParameter(ConnRouteParams.DEFAULT_PROXY);
			}

			String _host = conn.getProxy();
			int _port = Integer.valueOf(conn.getProxyPort());

			httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
			// end
			HttpHost httpHost = new HttpHost(_host, _port);
			params.setParameter(ConnRouteParams.DEFAULT_PROXY, httpHost);
		} else {
			if (params.getParameter(ConnRouteParams.DEFAULT_PROXY) != null) {
				params.removeParameter(ConnRouteParams.DEFAULT_PROXY);
			}
		}

		return httpClient;
	}

	public static HttpGet getHttpGetRequest(String uri) {
		HttpGet httpGet = new HttpGet(uri);
		return httpGet;
	}

	public static HttpPost getHttpPostRequest(String uri) {
		HttpPost httpPost = new HttpPost(uri);

		httpPost.setHeader("Accept", "*/*");
		// httpPost.setHeader("Content-Type",
		// "application/x-www-form-urlencoded");
		httpPost.setHeader("Content-Type", "text/plain");
		httpPost.setHeader("Accept-Encoding", "\"\"");
		httpPost.setHeader("encrypttype", "1");
//		httpPost.setHeader("Connection", "close");
		return httpPost;
	}

	// --------------------------线程相关-----------------------
	private static ThreadPoolExecutor threadPoolExecutor;
	private static int CORE_POOL_SIZE = 4;
	private static int MAX_POOL_SIZE = 128;
	private static int KEEP_ALIVE_TIME = 1;
	private static BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(12);

	private static ThreadFactory threadFactory = new ThreadFactory() {
		private final AtomicInteger integer = new AtomicInteger();

		public Thread newThread(Runnable r) {
			// TODO Auto-generated method stub
			return new Thread(r, "thread  no" + integer.getAndIncrement());
		}
	};

	static {
		threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, workQueue, threadFactory,
				new ThreadPoolExecutor.CallerRunsPolicy());
	}

	public static void execute(Runnable task) {

		threadPoolExecutor.execute(task);
	}
	
	

	public static void shutdownHttpConnection() {
		if (httpClient != null) {
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	public static void shutdownNow(){
		threadPoolExecutor.shutdownNow();
	}
}
