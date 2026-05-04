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

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = SubscriptionDataModel.RESOURCETYPE)
public class SubscriptionDataModel extends TagsDataModel implements GenericDataModel {

	protected static final String RESOURCETYPE = "srp/components/common/subscription";

	@ChildResource
	private List<LabelsDataModel> labels;

	@ChildResource(name = "subscriptionLogos")
	private List<SubscriptionBrandLogoDataModel> subscriptionLogos;

	public List<LabelsDataModel> getLabels() {
		if (null == this.labels) {
			return new ArrayList<>();
		}
		return new ArrayList<>(labels);
	}

	public List<SubscriptionBrandLogoDataModel> getApplicationLinks() {
		if (this.subscriptionLogos == null) {
			return new ArrayList<>();
		}
		return new ArrayList<>(subscriptionLogos);
	}

	@Override
	public String toString() {
		return "SubscriptionDataModel [getLabels()=" + getLabels() + ", getApplicationLinks()=" + getApplicationLinks()
				+ "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
