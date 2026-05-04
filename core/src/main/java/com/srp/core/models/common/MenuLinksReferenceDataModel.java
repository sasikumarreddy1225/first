package com.srp.core.models.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

import com.srp.core.services.GenericDataModel;
import com.srp.core.constants.CommonConstants;
import com.srp.core.models.TagsDataModel;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = CommonConstants.MENULINK_REFERENCE)
public class MenuLinksReferenceDataModel extends TagsDataModel implements GenericDataModel {

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String menulinkPath;

	public String getMenulinkPath() {
		return menulinkPath;
	}

	@Override
	public String toString() {
		return "MenuLinksReferenceDataModel [getMenulinkPath()=" + getMenulinkPath() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
