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

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FooterLinksEntryModel implements GenericDataModel {

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String type;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String label;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String linkType;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String category;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String document;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String webLink;

	@OSGiService
	SrpDomainService srpDomainService;

	public String getLabel() {
		return label;
	}

	public String getLinkType() {
		return linkType;
	}

	public String getType() {
		return type;
	}

	public String getDocument() {
		if (StringUtils.isNotEmpty(document) && srpDomainService.getDomainUrl() != null
				&& document.startsWith("/" + CommonConstants.DAM_ROOT)) {
			document = srpDomainService.getDomainUrl().concat(document);
		}
		return document;
	}

	public String getCategory() {
		return category;
	}

	public String getWebLink() {
		return webLink;
	}

	@Override
	public String toString() {
		return "FooterLinksEntryModel [getLabel()=" + getLabel() + ", getLinkType()="
				+ getLinkType() + ", getType()=" + getType() + ", getDocument()=" + getDocument() + ", getCategory()="
				+ getCategory() + ", getWebLink()=" + getWebLink() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}
}
