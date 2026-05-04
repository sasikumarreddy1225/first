package com.srp.core.models;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.srp.core.services.GenericDataModel;

import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = PageDataModel.RESOURCE)
public class PageDataModel extends TagsDataModel implements GenericDataModel{
	
	protected static final String RESOURCE = "srp/components/page";

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String id;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String title;

	private String label;

	@PostConstruct
	public void init() {
		label = title;
	}

	public String getTitle() {
		return title;
	}

	public String getLabel() {
		return label;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "PageDataModel [getTitle()=" + getTitle() + ", getLabel()=" + getLabel() + ", getId()=" + getId() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}



