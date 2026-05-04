package com.srp.core.models.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.srp.core.services.GenericDataModel;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SubCategroyDataModel implements GenericDataModel {

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String name;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String label;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String iconPath;

	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

	public String getIconPath() {
		return iconPath;
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		return "SubCategroyDataModel [getName()=" + getName() + ", getLabel()=" + getLabel() + ", getIconPath()="
				+ getIconPath() + "]";
	}

}
