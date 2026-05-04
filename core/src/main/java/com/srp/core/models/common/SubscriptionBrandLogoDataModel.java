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
public class SubscriptionBrandLogoDataModel implements GenericDataModel {

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String brandLink;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String brandLogo;
	
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String authField;
	
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String category;
	
	@OSGiService
	SrpDomainService srpDomainService;

	public String getBrandLink() {
		return brandLink;
	}

	public String getBrandLogo() {
		if(StringUtils.isNotEmpty(brandLogo) && srpDomainService.getDomainUrl() != null
				&& brandLogo.startsWith("/"+ CommonConstants.DAM_ROOT)){
		            brandLogo = srpDomainService.getDomainUrl().concat(brandLogo);
		}
		return brandLogo;
	}

	public String getAuthField() {
		return authField;
	}
	
	public String getCategory() {
		return category;
	}

	@Override
	public String toString() {
		return "SubscriptionBrandLogoDataModel [getBrandLink()=" + getBrandLink() + ", getBrandLogo()=" + getBrandLogo()
				+ ", getAuthField()=" + getAuthField() + ", getCategory()=" + getCategory() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
