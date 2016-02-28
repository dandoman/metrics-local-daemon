package com.metrics.daemon.pojo;

import java.util.List;

import com.metrics.daemon.pojo.StagedMetric;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BulkUploadRequest {
	private String apiKey;
	private List<StagedMetric> metrics;
}
