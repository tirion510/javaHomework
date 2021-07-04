package com.cy.mc;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import java.io.IOException;

public class HttpClientTest {

	private static HttpClient httpClient;

	private static final String CHARSET = "UTF-8";

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

	public static void main(String[] args){
		HttpMethod method = new GetMethod("http://127.0.0.1:8801");
		try {
			method.getParams().setCredentialCharset("UTF-8");
			httpClient.executeMethod(method);
			String result = new String(method.getResponseBody(), CHARSET);
			System.out.println("-------result----------" + result);
		}catch (Exception ex){
			ex.printStackTrace();
		}finally {
			method.releaseConnection();
		}
	}

}
