package com.best.java.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Author: xjxu3
 * @Date: 2020/6/14 21:17
 * @Description:
 */
public class EsAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

	private static final ThreadLocal<DateFormat> TIME_FORMAT = new ThreadLocal<DateFormat> () {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		}
	};

	private String host;

	private int port;

	private String indexName;

//	private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(20,20,200,
//													TimeUnit.SECONDS,
//													new LinkedBlockingDeque<>(30),
//													Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
//
	@Override
	public void stop() {
		super.stop();
		// 关闭es客户端
		try {
			if (ElasticClient.getClient() != null) {
				ElasticClient.getClient().getHighClient().close();
			}
		} catch (IOException e) {

		}
	}

	@Override
	protected void append(ILoggingEvent eo) {
		RestHighLevelClient  client = ElasticClient.getClient(host, port).getHighClient();
		IndexRequest indexRequest = new IndexRequest(indexName)
//				.id("")
				.source("postDate", TIME_FORMAT.get().format(new Date(eo.getTimeStamp())),
						"thread", eo.getThreadName(),
						"level", eo.getLevel().toString(),
						"classInfo", eo.getLoggerName(),
						"message", eo.getFormattedMessage(),
						"localIP", eo.getMDCPropertyMap().get("localIP"),
						"requestId", eo.getMDCPropertyMap().get("requestId"),
						"exception", eo.getThrowableProxy() == null ? "" : eo.getThrowableProxy().getMessage()
				);
		try {
			ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>(){
				@Override
				public void onResponse(IndexResponse indexResponse) {
					System.out.println("okok");
				}

				@Override
				public void onFailure(Exception e) {
					System.out.println(e);
				}
			};
			client.indexAsync(indexRequest, RequestOptions.DEFAULT,listener );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
}
