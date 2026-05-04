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
public class CatalogCategoryDataModel implements GenericDataModel {

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String categoryId;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String categoryLabel;


	public String getCategoryId() {
		return categoryId;
	}

	public String getCategoryLabel() {
		return categoryLabel;
	}

	@Override
	public String toString() {
		return "CatalogCategoryDataModel [getCategoryId()=" + getCategoryId() + ", getCategoryLabel()="
				+ getCategoryLabel() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
