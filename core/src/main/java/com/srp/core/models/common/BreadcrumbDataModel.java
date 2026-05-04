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
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = BreadcrumbDataModel.RESOURCETYPE)
public class BreadcrumbDataModel extends TagsDataModel implements GenericDataModel {

	protected static final String RESOURCETYPE = "srp/components/common/breadcrumb";


	@ChildResource
	private List<LabelsDataModel> labels;

	public List<LabelsDataModel> getLabels() {
		if (null == this.labels) {
			return new ArrayList<>();
		}
		return new ArrayList<>(labels);
	}

	@Override
	public String toString() {
		return "BreadcrumbDataModel [getLabels()=" + getLabels() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub

	}
}
