package com.srp.core.models.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.srp.core.constants.CommonConstants;
import com.srp.core.models.LabelsDataModel;
import com.srp.core.models.TagsDataModel;
import com.srp.core.services.GenericDataModel;
import com.srp.core.services.SrpDomainService;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = HeaderDataModel.RESOURCETYPE)
public class HeaderDataModel extends TagsDataModel implements GenericDataModel {

	protected static final String RESOURCETYPE = "srp/components/common/header";

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String desktopBlueLogo;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String desktopCatalogLogo;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String showNotificationIcon;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String desktopBlueLogoAlt;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String desktopCatalogLogoAlt;
	
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String desktopErcsBlueLogo;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String desktopErcsCatalogLogo;
	
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String desktopErcsBlueLogoAlt;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String desktopErcsCatalogLogoAlt;

	@OSGiService
	SrpDomainService srpDomainService;

	@ChildResource(name = "ApplicationLinks")
	private List<HeaderApplicationLogoDataModel> applicationLinks;

	@ChildResource
	private List<LabelsDataModel> labels;

	public String getDesktopErcsBlueLogo() {
		if (StringUtils.isNotEmpty(desktopErcsBlueLogo) && srpDomainService.getDomainUrl() != null
				&& desktopErcsBlueLogo.startsWith("/" + CommonConstants.DAM_ROOT)) {
			desktopErcsBlueLogo = srpDomainService.getDomainUrl().concat(desktopErcsBlueLogo);
		}
		return desktopErcsBlueLogo;
	}

	public String getDesktopCatalogLogo() {
		if (StringUtils.isNotEmpty(desktopCatalogLogo) && srpDomainService.getDomainUrl() != null
				&& desktopCatalogLogo.startsWith("/" + CommonConstants.DAM_ROOT)) {
			desktopCatalogLogo = srpDomainService.getDomainUrl().concat(desktopCatalogLogo);
		}
		return desktopCatalogLogo;
	}
	
	public String getDesktopBlueLogo() {
		if (StringUtils.isNotEmpty(desktopBlueLogo) && srpDomainService.getDomainUrl() != null
				&& desktopBlueLogo.startsWith("/" + CommonConstants.DAM_ROOT)) {
			desktopBlueLogo = srpDomainService.getDomainUrl().concat(desktopBlueLogo);
		}
		return desktopBlueLogo;
	}

	public String getDesktopErcsCatalogLogo() {
		if (StringUtils.isNotEmpty(desktopErcsCatalogLogo) && srpDomainService.getDomainUrl() != null
				&& desktopErcsCatalogLogo.startsWith("/" + CommonConstants.DAM_ROOT)) {
			desktopErcsCatalogLogo = srpDomainService.getDomainUrl().concat(desktopErcsCatalogLogo);
		}
		return desktopErcsCatalogLogo;
	}


	public String getShowNotificationIcon() {
		return showNotificationIcon;
	}

	public String getDesktopBlueLogoAlt() {
		return desktopBlueLogoAlt;
	}

	public String getDesktopCatalogLogoAlt() {
		return desktopCatalogLogoAlt;
	}
	
	public String getDesktopErcsBlueLogoAlt() {
		return desktopErcsBlueLogoAlt;
	}

	public String getDesktopErcsCatalogLogoAlt() {
		return desktopErcsCatalogLogoAlt;
	}

	public List<HeaderApplicationLogoDataModel> getApplicationLinks() {
		if (this.applicationLinks == null) {
			return new ArrayList<>();
		}
		return new ArrayList<>(applicationLinks);
	}
	
	public List<LabelsDataModel> getLabels() {
		if (null == this.labels) {
			return new ArrayList<>();
		}
		return new ArrayList<>(labels);
	}

	@Override
	public String toString() {
		return "HeaderDataModel [getDesktopErcsBlueLogo()=" + getDesktopErcsBlueLogo() + ", getDesktopCatalogLogo()="
				+ getDesktopCatalogLogo() + ", getDesktopBlueLogo()=" + getDesktopBlueLogo()
				+ ", getDesktopErcsCatalogLogo()=" + getDesktopErcsCatalogLogo() + ", getShowNotificationIcon()="
				+ getShowNotificationIcon() + ", getDesktopBlueLogoAlt()=" + getDesktopBlueLogoAlt()
				+ ", getDesktopCatalogLogoAlt()=" + getDesktopCatalogLogoAlt() + ", getDesktopErcsBlueLogoAlt()="
				+ getDesktopErcsBlueLogoAlt() + ", getDesktopErcsCatalogLogoAlt()=" + getDesktopErcsCatalogLogoAlt()
				+ ", getApplicationLinks()=" + getApplicationLinks() + ", getLabels()=" + getLabels() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
