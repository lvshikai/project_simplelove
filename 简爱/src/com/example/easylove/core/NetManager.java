package com.example.easylove.core;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.example.easylove.config.AppConfig;

/**
 * 网络连接管理类
 * 
 * (预留)
 * 
 */
public class NetManager {

	/**
	 * 最大连接数
	 */
	public final static int MAX_TOTAL_CONNECTIONS = 50;

	/**
	 * 获取连接的最大等待时间
	 */
	public final static int WAIT_TIMEOUT = 10000;

	/**
	 * 每个路由最大连接数
	 */
	public final static int MAX_ROUTE_CONNECTIONS = 50;

	/**
	 * 连接超时时间
	 */
	public final static int CONNECT_TIMEOUT = 30000;

	/**
	 * 读取超时时间
	 */
	public final static int READ_TIMEOUT = 30000;

	/**
	 * 字符编码
	 */
	public final static String CHARSET = "utf-8";

	private static NetManager instance;
	private  List<Cookie> cookies;

	/**
	 * 返回实例
	 * 
	 * @return
	 */
	public synchronized static NetManager getInstance() {
		if (instance == null) {
			instance = new NetManager();
		}
		return instance;
	}

	/**
	 * 发送get请求
	 * 
	 * @param url
	 *            发送请求url
	 * @param content
	 *            请求内容
	 * @return result 内容 <null表示连接失败>
	 */
	public String sendGetRequest(String url, Map<String, String> parmMap) {
		String result = null;
		String parms = "?";
		try {
			if (parmMap != null && !parmMap.isEmpty()) {
				Iterator<Entry<String, String>> iterator = parmMap.entrySet()
						.iterator();
				while (iterator.hasNext()) {
					Entry<String, String> entry = iterator.next();
					parms += entry.getKey() + "="
							+ URLEncoder.encode(entry.getValue(), CHARSET)
							+ "&";
				}

			}
			HttpGet get = getHttpGet(url + parms);
			get.setHeader("Cookie", getCookie());
			HttpClient client = getHttpClient();
			HttpResponse response = client.execute(get);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				if (url.endsWith(AppConfig.LOGIN_URL)) {
					this.cookies = ((AbstractHttpClient) client)
							.getCookieStore().getCookies();
				}
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, CHARSET);
					resEntity.consumeContent();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 发送post请求
	 * 
	 * @param url
	 *            发送请求url
	 * @param parmMap
	 *            请求内容
	 * @return result 内容 <null表示连接失败>
	 */
	public String sendPostRequest(String url, Map<String, String> parmMap) {
		String result = null;
		HttpPost post = getHttpPost(url);

		List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
		try {
			if (parmMap != null && !parmMap.isEmpty()) {
				Iterator<Entry<String, String>> iterator = parmMap.entrySet()
						.iterator();
				while (iterator.hasNext()) {
					Entry<String, String> entry = iterator.next();
					nameValuePairs.add(new BasicNameValuePair(entry.getKey(),
							(String) entry.getValue()));
				}

			}
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs, CHARSET));
			post.setHeader("Cookie", getCookie());
			HttpClient client = getHttpClient();
			HttpResponse response = getHttpClient().execute(post);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				if (url.endsWith(AppConfig.LOGIN_URL)) {
					this.cookies = ((AbstractHttpClient) client)
							.getCookieStore().getCookies();
					// // Editor editor = sp.edit();
					// for (int i = 0; i < cookies.size(); i++) {
					// // 如果cookies头和"JSESSIONID" 就记录sessionID
					// Cookie cookie = cookies.get(i); //(cookie.getName(),
					// cookie.getValue()); }
					// }
				}
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, CHARSET);
					resEntity.consumeContent();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取cookie
	 * 
	 * @return
	 */
	public  String getCookie() {
		if (cookies == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (Cookie ck : cookies) {
			sb.append(ck.getName()).append('=').append(ck.getValue())
					.append(";");
		}
		return sb.toString();

	}
	/**
	 * 清除cookie
	 * 
	 * @return
	 */
	public  void clearCookie() {
		cookies = null;
	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 *            发送请求url
	 * @return HttpEntity 下载内容
	 */
	public HttpEntity doDownloadFile(String url) {
		HttpEntity httpEntity = null;
		HttpPost post = getHttpPost(url);

		try {
			HttpResponse response = getHttpClient().execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// response.getEntity().getContent();
				httpEntity = response.getEntity();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return httpEntity;
	}

	/**
	 * 创建一个HttpPost并返回
	 * 
	 * @param url
	 * @return
	 */
	private HttpPost getHttpPost(String url) {
		HttpPost post = new HttpPost(url);
		post.addHeader("Content-Type", "application/json");
		return post;
	}

	/**
	 * 创建一个HttpPost并返回
	 * 
	 * @param url
	 * @return
	 */
	private HttpGet getHttpGet(String url) {
		HttpGet get = new HttpGet(url);
		get.addHeader("Content-Type", "text/html");

		return get;
	}

	/**
	 * 创建一个HttpClient
	 * 
	 * @return
	 */
	private HttpClient getHttpClient() {
		try {
			HttpParams httpParams = new BasicHttpParams();
			// 设置通信协议版本
			httpParams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
					HttpVersion.HTTP_1_1);
			// 设置最大连接数
			ConnManagerParams.setMaxTotalConnections(httpParams,
					MAX_TOTAL_CONNECTIONS);
			// 设置获取连接的最大等待时间
			ConnManagerParams.setTimeout(httpParams, WAIT_TIMEOUT);
			// 设置每个路由最大连接数
			ConnPerRouteBean connPerRoute = new ConnPerRouteBean(
					MAX_ROUTE_CONNECTIONS);
			ConnManagerParams.setMaxConnectionsPerRoute(httpParams,
					connPerRoute);
			// 设置连接超时时间
			HttpConnectionParams.setConnectionTimeout(httpParams,
					CONNECT_TIMEOUT);
			// 设置读取超时时间
			HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));

			ClientConnectionManager connectionManager = new ThreadSafeClientConnManager(
					httpParams, registry);
			return new DefaultHttpClient(connectionManager, httpParams);
		} catch (Exception e) {
			e.printStackTrace();
			return new DefaultHttpClient();
		}
	}

}
