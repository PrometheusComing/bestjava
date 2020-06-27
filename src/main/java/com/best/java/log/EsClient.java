//package com.best.java.log;
//
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @Author: xjxu3
// * @Date: 2020/6/14 21:18
// * @Description:
// */
//@Configuration
//public class EsClient {
//
//	private static RestHighLevelClient client;
//
//	@Value(value = "${es.host.ip:127.0.0.1}")
//	private String host;
//	@Value(value = "${es.port:9200}")
//	private int port;
//
//	@Bean
//	public EsClient esClient() {
//		client = new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, "http")));
//		EsClient esClient = new EsClient();
//		setClient(client);
//		return esClient;
//	}
//
//	public static RestHighLevelClient getClient() {
//		return client;
//	}
//
//	public static void setClient(RestHighLevelClient client) {
//		EsClient.client = client;
//	}
//}
