package com.minsait.onesait.platform.spring.boot.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class StatusAggregationResults {

	private int count;

	private String statusMessage;

	private String percentage;

	public String getPercentage() {
		return percentage + "%";
	}
}
