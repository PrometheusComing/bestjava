package com.best.java.log;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: xjxu3
 * @Date: 2020/6/14 22:16
 * @Description:
 *
 * 单例es客户端
 */
public class ElasticClient {

	private  RestHighLevelClient highClient;

	private ElasticClient(String host,int port) {
		highClient = new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, "http")));
	}

	public static final AtomicReference<ElasticClient> INSTANCE = new AtomicReference<>();

	/**
	 * 附带初始化内部client
	 */
	public static ElasticClient getClient(String host,int port) {
		for (;;) {
			ElasticClient elasticClient = INSTANCE.get();
			if (elasticClient != null) {
				return elasticClient;
			}
			elasticClient = new ElasticClient(host, port);
			if (INSTANCE.compareAndSet(null, elasticClient)) {
				return elasticClient;
			}
		}
	}

	// 返回有可能为空
	public static ElasticClient getClient() {
		return INSTANCE.get();
	}

	public RestHighLevelClient getHighClient() {
		return highClient;
	}

	public void setHighClient(RestHighLevelClient highClient) {
		this.highClient = highClient;
	}
}
