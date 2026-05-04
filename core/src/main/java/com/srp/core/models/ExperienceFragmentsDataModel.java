package com.srp.core.models;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.srp.core.services.GenericDataModel;

import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.api.resource.Resource;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ExperienceFragmentsDataModel implements GenericDataModel{
	
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	String xfPath;

	public String getXfPath() {
		return xfPath;
	}

	@Override
	public String toString() {
		return "ExperienceFragmentsDataModel [getXfPath()=" + getXfPath() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
