package com.srp.core.models.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AdminConfigDataModel {

	@Inject
	@ChildResource
	private List<AdminGroupsListDataModel> adminGroups;

	public List<AdminGroupsListDataModel> getAdminGroups() {
		if (null == adminGroups)
			return new ArrayList<>();
		return new ArrayList<>(adminGroups);
	}

	@Override
	public String toString() {
		return "AdminConfigDataModel [getAdminGroups()=" + getAdminGroups() + "]";
	}

}
