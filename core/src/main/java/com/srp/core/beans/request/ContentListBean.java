package com.srp.core.beans.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentListBean {

	private String contentPath;

	private String selector;

	private String type;

	private String eventType;

	private String id;

	public String getContentPath() {
		return contentPath;
	}

	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ContentListBean [getContentPath()=" + getContentPath() + ", getSelector()=" + getSelector()
				+ ", getType()=" + getType() + ", getEventType()=" + getEventType() + ", getId()=" + getId() + "]";
	}

}
