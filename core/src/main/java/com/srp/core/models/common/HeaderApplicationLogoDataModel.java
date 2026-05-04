package com.srp.core.models.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.srp.core.constants.CommonConstants;
import com.srp.core.services.GenericDataModel;
import com.srp.core.services.SrpDomainService;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeaderApplicationLogoDataModel implements GenericDataModel {

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String name;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String link;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String appcategory;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String icon;

	@OSGiService
	SrpDomainService srpDomainService;

	public String getName() {
		return name;
	}

	public String getLink() {
		return link;
	}

	public String getIcon() {
		if (StringUtils.isNotEmpty(icon) && srpDomainService.getDomainUrl() != null
				&& icon.startsWith("/" + CommonConstants.DAM_ROOT)) {
			icon = srpDomainService.getDomainUrl().concat(icon);
		}
		return icon;
	}

	@Override
	public String toString() {
		return "HeaderApplicationLogoDataModel [getName()=" + getName() + ", getLink()=" + getLink() + ", getIcon()="
				+ getIcon() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
