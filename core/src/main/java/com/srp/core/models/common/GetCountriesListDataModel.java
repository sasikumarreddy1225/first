package com.srp.core.models.common;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.srp.core.constants.CommonConstants;
import com.srp.core.services.GenericDataModel;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GetCountriesListDataModel implements GenericDataModel {

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String countryCode;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String label;

	private String code;

	private String name;

	@PostConstruct
	public void init() {
		String[] labels = countryCode.split(CommonConstants.BACKSLASH_PIPE);
		code = labels[0];
		name = labels[1];
	}

	public String getLabel() {
		return label;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "GetCountriesListDataModel [getLabel()=" + getLabel() + ", getCode()=" + getCode() + ", getName()="
				+ getName() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub

	}

}
