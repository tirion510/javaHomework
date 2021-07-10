package io.github.kimmking.gateway.outbound.myhttpclient;

import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.github.kimmking.gateway.filter.HttpResponseFilter;
import io.github.kimmking.gateway.filter.MyHttpResponseFilter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class MyHttpClientOutboundHandler {

	private static HttpClient httpClient;

	private static final String CHARSET = "UTF-8";

	HttpResponseFilter myFilter = new MyHttpResponseFilter();

	static {
		HttpClientParams httpClientParams = new HttpClientParams();
		httpClientParams.setConnectionManagerTimeout(1000);
		httpClientParams.setVersion(HttpVersion.HTTP_1_1);
		httpClientParams.setContentCharset(CHARSET);
		httpClientParams.setCredentialCharset(CHARSET);
		HttpConnectionManagerParams connectionManagerParams = new HttpConnectionManagerParams();
		connectionManagerParams.setConnectionTimeout(3000);
		connectionManagerParams.setSoTimeout(300);
		connectionManagerParams.setMaxTotalConnections(200);
		connectionManagerParams.setDefaultMaxConnectionsPerHost(50);
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		connectionManager.setParams(connectionManagerParams);
		httpClient = new HttpClient(httpClientParams, connectionManager);
	}

	public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, HttpRequestFilter filter) {
		String backendUrl = "http://127.0.0.1:8801";
		final String url = backendUrl + fullRequest.uri();
		filter.filter(fullRequest, ctx);
		HttpMethod method = new GetMethod(url);
		System.out.println("start...");
		try {
			method.getParams().setCredentialCharset("UTF-8");
			httpClient.executeMethod(method);
			String result = new String(method.getResponseBody(), CHARSET);
			System.out.println("-------result----------" + result);
			writeResponse(fullRequest, ctx, filter, result);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		System.out.println("finished.");
	}

	private void writeResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, HttpRequestFilter filter, String result) {
		FullHttpResponse response = null;
		try {
			response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(result.getBytes()));
			response.headers().set("Content-Type", "application/json");
			response.headers().setInt("Content-Length", result.getBytes().length);
			myFilter.filter(response);
		} catch (Exception e) {
			e.printStackTrace();
			response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
		} finally {
			if (fullRequest != null) {
				if (!HttpUtil.isKeepAlive(fullRequest)) {
					ctx.write(response).addListener(ChannelFutureListener.CLOSE);
				} else {
					ctx.write(response);
				}
			}
			ctx.flush();
			ctx.close();
		}
	}
}