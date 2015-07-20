package com.metrics.daemon.clients;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metrics.daemon.pojo.BulkUploadRequest;
import com.metrics.daemon.pojo.CreateMonitorRequest;
import com.metrics.daemon.pojo.StagedMetric;
import com.metrics.daemon.pojo.Metric;

import lombok.Setter;

public class MetricClient {

	@Setter
	private HttpClient client;
	private ObjectMapper mapper = new ObjectMapper();

	public void init() {
		HttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		client = HttpClientBuilder.create()
				.setConnectionManager(connectionManager).build();
	}

	public int createMetric(List<StagedMetric> metricList) {
		HttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		client = HttpClientBuilder.create()
				.setConnectionManager(connectionManager).build();
		URIBuilder builder = new URIBuilder();
		builder.setScheme("http")
				.setHost("ec2-52-24-132-32.us-west-2.compute.amazonaws.com")
				.setPort(8080).setPath("/MetricsService/processing/upload");
		BulkUploadRequest req = new BulkUploadRequest();
		req.setApiKey("mahKeyy");
		req.setMetrics(metricList);

		try {
			HttpPost post = new HttpPost(builder.build());
			post.setHeader("Content-Type", "application/json");
			post.setEntity(EntityBuilder.create()
					.setBinary(mapper.writeValueAsBytes(req)).build());
			HttpResponse r = client.execute(post);
			return r.getStatusLine().getStatusCode();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Metric> getMetricByNameAndApp(String name, String appName) {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("http")
				.setHost("ec2-52-24-132-32.us-west-2.compute.amazonaws.com")
				.setPort(8080).setPath("/MetricsService/metric/search")
				.setParameter("metricName", name)
				.setParameter("applicationName", appName);

		try {
			HttpGet get = new HttpGet(builder.build());
			HttpResponse response = client.execute(get);
			List<Metric> returnMetrics = mapper.readValue(response.getEntity()
					.getContent(), mapper.getTypeFactory()
					.constructCollectionType(ArrayList.class, Metric.class));
			return returnMetrics;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public void createMonitor(CreateMonitorRequest req) throws Exception {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("http")
				.setHost("ec2-52-24-132-32.us-west-2.compute.amazonaws.com")
				.setPort(8080).setPath("/MetricsService/monitor");

		HttpPost post = new HttpPost(builder.build());
		post.setHeader("Content-Type", "application/json");
		post.setEntity(EntityBuilder.create()
				.setBinary(mapper.writeValueAsBytes(req)).build());
		HttpResponse r = client.execute(post);
		if(r.getStatusLine().getStatusCode() != 200){
			throw new Exception("Did not create monitor");
		}
	}
}
