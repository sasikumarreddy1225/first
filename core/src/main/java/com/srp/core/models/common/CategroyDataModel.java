package com.srp.core.models.common;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.srp.core.services.GenericDataModel;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CategroyDataModel implements GenericDataModel {

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String name;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String label;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String iconPath;

	@Inject
	@ChildResource
	@Named("subcategories/.")
	private List<SubCategroyDataModel> subcategories;

	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

	public String getIconPath() {
		return iconPath;
	}

	public List<SubCategroyDataModel> getSubcategories() {
		if (null == this.subcategories) {
			return new ArrayList<>();
		}
		return new ArrayList<>(subcategories);
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		return "CategroyDataModel [getName()=" + getName() + ", getLabel()=" + getLabel() + ", getIconPath()="
				+ getIconPath() + ", getSubcategories()=" + getSubcategories() + "]";
	}

}
