package com.srp.core.models.common;

import javax.inject.Inject;

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

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TopBannerEntryDataModel implements GenericDataModel {

	@Inject
	SlingHttpServletRequest request;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String ctaLabel;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String image;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String link;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String linkType;
	
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String startDate;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String endDate;

	@OSGiService
	SrpDomainService srpDomainService;

	public String getCtaLabel() {
		return ctaLabel;
	}

	public String getImage() {
		if (StringUtils.isNotEmpty(image) && srpDomainService.getDomainUrl() != null
				&& image.startsWith("/" + CommonConstants.DAM_ROOT)) {
			image = srpDomainService.getDomainUrl().concat(image);
		}
		return image;
	}

	public String getLink() {
		return link;
	}

	public String getLinkType() {
		return linkType;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	@Override
	public String toString() {
		return "TopBannerEntryDataModel [getCtaLabel()=" + getCtaLabel() + ", getImage()=" + getImage() + ", getLink()="
				+ getLink() + ", getLinkType()=" + getLinkType() + ", getStartDate()=" + getStartDate()
				+ ", getEndDate()=" + getEndDate() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}