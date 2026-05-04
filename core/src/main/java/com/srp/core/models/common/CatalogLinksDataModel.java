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
public class CatalogLinksDataModel implements GenericDataModel {

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
	private String linkType;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String icon;
	
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String categoryId;
	
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String catalogId;
	
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String authField;

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

	public String getLinkType() {
		return linkType;
	}

	public String getIcon() {
		if (StringUtils.isNotEmpty(icon) && srpDomainService.getDomainUrl() != null
				&& icon.startsWith("/" + CommonConstants.DAM_ROOT)) {
			icon = srpDomainService.getDomainUrl().concat(icon);
		}
		return icon;
	}
	
	public String getCategoryId() {
		return categoryId;
	}

	
	public String getCatalogId() {
		return catalogId;
	}
	
	public String getAuthField() {
		return authField;
	}

	@Override
	public String toString() {
		return "CatalogLinksDataModel [getLabel()=" + getLabel() + ", getCategory()=" + getCategory() + ", getLink()="
				+ getLink() + ", getLinkType()=" + getLinkType() + ", getIcon()=" + getIcon() + ", getCategoryId()="
				+ getCategoryId() + ", getCatalogId()=" + getCatalogId() + ", getAuthField()=" + getAuthField() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
