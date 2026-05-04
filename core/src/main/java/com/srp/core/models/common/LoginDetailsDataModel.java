package com.srp.core.models.common;


import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import org.apache.commons.lang3.StringUtils;

import com.srp.core.services.GenericDataModel;
import com.srp.core.services.SrpDomainService;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.api.SlingHttpServletRequest;

import com.srp.core.constants.CommonConstants;
import com.srp.core.models.LabelsDataModel;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = LoginDetailsDataModel.RESOURCE)
public class LoginDetailsDataModel implements GenericDataModel {

	protected static final String RESOURCE = "srp/components/common/login-details";

	// Logos & Images Tab fields

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String leftHeaderLogo;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String rightHeaderLogo;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String loginLogo;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String desktopImage;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String mobileImage;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String leftHeaderLogoAlt;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String rightHeaderLogoAlt;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String loginLogoAlt;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String desktopImageAlt;

	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String mobileImageAlt;
	
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String registrationLink;
	
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String registrationLabel;

	@ChildResource
	private List<DocumentationDataModel> onBoardDocumentLinks;

	@ChildResource
	private List<ReachLinksDataModel> reachLinks;

	@ChildResource
	private List<LabelsDataModel> labels;

	@OSGiService
	SrpDomainService srpDomainService;

	public JsonObject getLoginFields() {

		if (StringUtils.isNotEmpty(leftHeaderLogo) && srpDomainService.getDomainUrl() != null
				&& leftHeaderLogo.startsWith("/" + CommonConstants.DAM_ROOT)) {
			leftHeaderLogo = srpDomainService.getDomainUrl().concat(leftHeaderLogo);
		}

		if (StringUtils.isNotEmpty(rightHeaderLogo) && srpDomainService.getDomainUrl() != null
				&& rightHeaderLogo.startsWith("/" + CommonConstants.DAM_ROOT)) {
			rightHeaderLogo = srpDomainService.getDomainUrl().concat(rightHeaderLogo);
		}

		if (StringUtils.isNotEmpty(loginLogo) && srpDomainService.getDomainUrl() != null
				&& loginLogo.startsWith("/" + CommonConstants.DAM_ROOT)) {
			loginLogo = srpDomainService.getDomainUrl().concat(loginLogo);
		}

		if (StringUtils.isNotEmpty(desktopImage) && srpDomainService.getDomainUrl() != null
				&& desktopImage.startsWith("/" + CommonConstants.DAM_ROOT)) {
			desktopImage = srpDomainService.getDomainUrl().concat(desktopImage);
		}

		if (StringUtils.isNotEmpty(mobileImage) && srpDomainService.getDomainUrl() != null
				&& mobileImage.startsWith("/" + CommonConstants.DAM_ROOT)) {
			mobileImage = srpDomainService.getDomainUrl().concat(mobileImage);
		}

		JsonObject loginFields = new JsonObject();
		loginFields.addProperty("leftHeaderLogo", leftHeaderLogo);
		loginFields.addProperty("rightHeaderLogo", rightHeaderLogo);
		loginFields.addProperty("loginLogo", loginLogo);
		loginFields.addProperty("desktopImage", desktopImage);
		loginFields.addProperty("mobileImage", mobileImage);
		loginFields.addProperty("leftHeaderLogoAlt", leftHeaderLogoAlt);
		loginFields.addProperty("rightHeaderLogoAlt", rightHeaderLogoAlt);
		loginFields.addProperty("loginLogoAlt", loginLogoAlt);
		loginFields.addProperty("desktopImageAlt", desktopImageAlt);
		loginFields.addProperty("mobileImageAlt", mobileImageAlt);
		return loginFields;
	}

	public List<DocumentationDataModel> getOnBoardDocumentLinks() {
		if (null == this.onBoardDocumentLinks) {
			return new ArrayList<>();
		}
		return new ArrayList<>(onBoardDocumentLinks);
	}

	public List<ReachLinksDataModel> getReachLinks() {
		if (null == this.reachLinks) {
			return new ArrayList<>();
		}
		return new ArrayList<>(reachLinks);
	}

	public List<LabelsDataModel> getLabels() {
		if (null == this.labels) {
			return new ArrayList<>();
		}
		return new ArrayList<>(labels);
	}

	public String getRegistrationLink() {
		return registrationLink;
	}

	public String getRegistrationLabel() {
		return registrationLabel;
	}

	@Override
	public String toString() {
		return "LoginDetailsDataModel [getLoginFields()=" + getLoginFields() + ", getOnBoardDocumentLinks()="
				+ getOnBoardDocumentLinks() + ", getReachLinks()=" + getReachLinks() + ", getLabels()=" + getLabels()
				+ ", getRegistrationLink()=" + getRegistrationLink() + ", getRegistrationLabel()=" + getRegistrationLabel() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}	

}
