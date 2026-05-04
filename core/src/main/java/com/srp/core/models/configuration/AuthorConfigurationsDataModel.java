package com.srp.core.models.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.srp.core.constants.CommonConstants;

import org.apache.sling.models.annotations.DefaultInjectionStrategy;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AuthorConfigurationsDataModel {


	@ValueMapValue
	private String group;

	private String groupId;

	private String groupName;

	@ValueMapValue
	private List<String> geoTags;

	@ValueMapValue
	private List<String> roleTags;

	@ValueMapValue
	private List<String> brandTags;

	@PostConstruct
	public void init() {
		if (null != group) {
			String[] groupsList = group.split(CommonConstants.BACKSLASH_PIPE);
			groupId = groupsList[1];
			groupName = groupsList[0];
		}
	}

	public List<String> getGeoTags() {
		if (null == this.geoTags) {
			return new ArrayList<>();
		}
		return new ArrayList<>(geoTags);
	}

	public List<String> getRoleTags() {
		if (null == this.roleTags) {
			return new ArrayList<>();
		}
		return new ArrayList<>(roleTags);
	}

	public List<String> getBrandTags() {
		if (null == this.brandTags) {
			return new ArrayList<>();
		}
		return new ArrayList<>(brandTags);
	}

	public String getGroupId() {
		return groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	@Override
	public String toString() {
		return "AuthorConfigurationsDataModel [getGeoTags()=" + getGeoTags() + ", getRoleTags()=" + getRoleTags()
				+ ", getBrandTags()=" + getBrandTags() + ", getGroupId()=" + getGroupId() + ", getGroupName()="
				+ getGroupName() + "]";
	}

}
