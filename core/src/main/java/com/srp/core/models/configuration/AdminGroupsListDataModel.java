package com.srp.core.models.configuration;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.srp.core.constants.CommonConstants;

import org.apache.sling.models.annotations.DefaultInjectionStrategy;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AdminGroupsListDataModel {
	
	@ValueMapValue
	private String group;

	private String groupId;
	private String groupName;
	
	@PostConstruct
	public void init() {
		if (null != group) {
			String[] groupsList = group.split(CommonConstants.BACKSLASH_PIPE);
			groupId = groupsList[1];
			groupName = groupsList[0];
		}
	}
	
	public String getGroup() {
		return group;
	}

	public String getGroupId() {
		return groupId;
	}

	
	public String getGroupName() {
		return groupName;
	}

	@Override
	public String toString() {
		return "AdminGroupsListDataModel [getGroup()=" + getGroup() + ", getGroupId()=" + getGroupId()
				+ ", getGroupName()=" + getGroupName() + "]";
	}

    
}
