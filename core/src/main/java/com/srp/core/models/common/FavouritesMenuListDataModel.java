package com.srp.core.models.common;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.WCMMode;
import com.srp.core.constants.CommonConstants;
import com.srp.core.services.GenericDataModel;
import com.srp.core.services.SrpDomainService;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = FavouritesMenuListDataModel.RESOURCETYPE)
public class FavouritesMenuListDataModel implements GenericDataModel{
	
	protected static final String RESOURCETYPE = "srp/components/common/favouritesmenulist";
	
	private final Logger logger = LoggerFactory.getLogger(FavouritesMenuListDataModel.class);

	@SlingObject
	private Resource resource;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String name;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String label;
	
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String type;
	
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String link;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String documentLink;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String linkType;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String linkDescription;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String category;
	
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String authField;
	
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String catalogId;
	
	@OSGiService
	SrpDomainService srpDomainService;

	@Inject
	SlingHttpServletRequest request;
	
	@PostConstruct
	public void init() {
		logger.error("SRP-Entered into FavouritesMenuListDataModel");
		try {
			if (null != request) {
				WCMMode wcmmode = WCMMode.fromRequest(request);

				if (wcmmode.name().equalsIgnoreCase("edit") || wcmmode.name().equalsIgnoreCase("DISABLED")) {
					if (StringUtils.isEmpty(name)) {
						name = UUID.randomUUID().toString().substring(0, 8);
						logger.error("SRP-Created id {}", name);
					}
					ModifiableValueMap modifiableValueMap = resource.adaptTo(ModifiableValueMap.class);
					if (modifiableValueMap != null) {
						modifiableValueMap.put("name", name);
						resource.getResourceResolver().commit();
					}
				}
			}
		} catch (PersistenceException e) {
			logger.error("SRP-Exception occured in FavouritesMenuListDataModel", e);
		}
		logger.error("SRP-Exiting from FavouritesMenuListModel");
	}
	
	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}
	
	public String getLinkType() {
		return linkType;
	}
	
	public String getType() {
		return type;
	}

	public String getLink() {
		return link;
	}

	public String getDocumentLink() {
		if (StringUtils.isNotEmpty(documentLink) && srpDomainService.getDomainUrl() != null
				&& documentLink.startsWith("/" + CommonConstants.DAM_ROOT)) {
			documentLink = srpDomainService.getDomainUrl().concat(documentLink);
		}
		return documentLink;
	}

	public String getLinkDescription() {
		return linkDescription;
	}

	public String getCategory() {
		return category;
	}

	public String getAuthField() {
		return authField;
	}

	public String getCatalogId() {
		return catalogId;
	}


	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		if (this.request == null) {
			this.request = request;
		}
		
	}

	@Override
	public String toString() {
		return "FavouritesMenuListDataModel [getLabel()=" + getLabel() + ", getLinkType()="
				+ getLinkType() + ", getType()=" + getType() + ", getLink()=" + getLink() + ", getDocumentLink()="
				+ getDocumentLink() + ", getLinkDescription()=" + getLinkDescription() + ", getCategory()="
				+ getCategory() + ", getAuthField()=" + getAuthField() + ", getCatalogId()=" + getCatalogId() + "]";
	}
	
	


}
