package com.srp.core.beans.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Collections;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PublishContentRequestBean {

	private List<ContentListBean> content;

	private boolean isExternal;

	public List<ContentListBean> getContent() {
		if (null == this.content) {
			return new ArrayList<>();
		}
		return new ArrayList<>(content);
	}

	public void setContentBean(List<ContentListBean> content) {
		if (null == content) {
			content = new ArrayList<>();
		}
		this.content = Collections.unmodifiableList(content);
	}

	public boolean isExternal() {
		return isExternal;
	}

	public void setExternal(boolean isExternal) {
		this.isExternal = isExternal;
	}

	@Override
	public String toString() {
		return "PublishContentRequestBean [getContent()=" + getContent() + "]";
	}

}
