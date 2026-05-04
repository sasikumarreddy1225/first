package com.srp.core.beans.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LabelsResponseBean {

	private List<LabelsDataBean> results;

	public List<LabelsDataBean> getResults() {
		if (null == this.results) {
			return new ArrayList<>();
		}
		return new ArrayList<>(results);
	}
}
