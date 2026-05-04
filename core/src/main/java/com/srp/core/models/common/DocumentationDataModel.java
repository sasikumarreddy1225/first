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
import com.srp.core.models.TagsDataModel;
import com.srp.core.services.GenericDataModel;
import com.srp.core.services.SrpDomainService;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DocumentationDataModel extends TagsDataModel implements GenericDataModel {

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String label;
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String type;
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String linkType;
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String link;
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String path;

	@OSGiService
	SrpDomainService srpDomainService;

	public String getLabel() {
		return label;
	}

	public String getType() {
		return type;
	}

	public String getLinkType() {
		return linkType;
	}

	public String getLink() {
		return link;
	}

	public String getPath() {
		if (StringUtils.isNotEmpty(path) && srpDomainService.getDomainUrl() != null
				&& path.startsWith("/" + CommonConstants.DAM_ROOT)) {
			path = srpDomainService.getDomainUrl().concat(path);
		}
		return path;
	}

	@Override
	public String toString() {
		return "DocumentationDataModel [getLabel()=" + getLabel() + ", getType()=" + getType() + ", getLinkType()="
				+ getLinkType() + ", getLink()=" + getLink() + ", getDocument()=" + getPath() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}
}
