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

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class UserMenuEntryDataModel implements GenericDataModel {

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String label;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String category;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String link;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String linkDescription;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String linkType;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String authField;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String type;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String documentLink;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String catalogId;

	@OSGiService
	SrpDomainService srpDomainService;

	public String getLabel() {
		return label;
	}

	public String getCategory() {
		return category;
	}

	public String getLink() {
		return link;
	}

	public String getLinkDescription() {
		return linkDescription;
	}

	public String getLinkType() {
		return linkType;
	}

	public String getAuthField() {
		return authField;
	}

	public String getType() {
		return type;
	}

	public String getDocumentLink() {
		if (StringUtils.isNotEmpty(documentLink) && srpDomainService.getDomainUrl() != null
				&& documentLink.startsWith("/" + CommonConstants.DAM_ROOT)) {
			documentLink = srpDomainService.getDomainUrl().concat(documentLink);
		}
		return documentLink;
	}

	public String getCatalogId() {
		return catalogId;
	}

	@Override
	public String toString() {
		return "UserMenuEntryDataModel [getLabel()=" + getLabel() + ", getCategory()=" + getCategory() + ", getLink()="
				+ getLink() + ", getLinkDescription()=" + getLinkDescription() + ", getLinkType()=" + getLinkType()
				+ ", getAuthField()=" + getAuthField() + ", getType()=" + getType() + ", getDocumentLink()="
				+ getDocumentLink() + ", getCatalogId()=" + getCatalogId() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub

	}

}


