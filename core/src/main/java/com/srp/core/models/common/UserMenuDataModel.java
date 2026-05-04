package com.srp.core.models.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import com.srp.core.models.LabelsDataModel;
import com.srp.core.models.TagsDataModel;
import com.srp.core.services.GenericDataModel;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = UserMenuDataModel.RESOURCETYPE)
public class UserMenuDataModel extends TagsDataModel implements GenericDataModel {

	protected static final String RESOURCETYPE = "srp/components/common/userMenu";

	@ChildResource
	private List<UserMenuEntryDataModel> userMenuLinks;

	@ChildResource
	private List<LabelsDataModel> labels;

	public List<UserMenuEntryDataModel> getUserMenuLinks() {
		return userMenuLinks;
	}

	public List<LabelsDataModel> getLabels() {
		if (null == this.labels) {
			return new ArrayList<>();
		}
		return new ArrayList<>(labels);
	}

	@Override
	public String toString() {
		return "UserMenuDataModel [getUserMenuLinks()=" + getUserMenuLinks() + ", getLabels()=" + getLabels() + "]";
	}

	public static String getResourcetype() {
		return RESOURCETYPE;
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub

	}

}

