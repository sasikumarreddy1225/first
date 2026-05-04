package com.srp.core.models;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

import com.srp.core.constants.CommonConstants;
import com.srp.core.services.GenericDataModel;
import org.apache.sling.api.resource.Resource;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LabelsDataModel implements GenericDataModel {

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String fieldType;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String label;

	private String labelId;

	private String labelType;

	@PostConstruct
	public void init() {
		String[] labels = fieldType.split(CommonConstants.BACKSLASH_PIPE);
		labelType = labels[0];
		labelId = labels[1];
	}

	public String getLabel() {
		return label;
	}

	public String getLabelId() {
		return labelId;
	}

	public String getLabelType() {
		return labelType;
	}

	@Override
	public String toString() {
		return "LabelsDataModel [getLabel()=" + getLabel() + ", getLabelId()=" + getLabelId() + ", getLabelType()="
				+ getLabelType() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
