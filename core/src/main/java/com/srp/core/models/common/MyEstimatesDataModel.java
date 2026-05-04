package com.srp.core.models.common;

import com.srp.core.models.LabelsDataModel;
import com.srp.core.models.TagsDataModel;
import com.srp.core.services.GenericDataModel;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = MyEstimatesDataModel.RESOURCETYPE)
public class MyEstimatesDataModel extends TagsDataModel implements GenericDataModel {

	protected static final String RESOURCETYPE = "srp/components/common/my_estimates";

	@ChildResource
	private List<LabelsDataModel> labels;
	
	@ValueMapValue
	private Boolean isDisabled;

	public List<LabelsDataModel> getLabels() {
		if (null == this.labels) {
			return new ArrayList<>();
		}
		return new ArrayList<>(labels);
	}

	public boolean getIsGrid() {
		return true;
	}
	
	public boolean getIsDisabled() {
		return isDisabled;
	}

	@Override
	public String toString() {
		return "MyEstimatesDataModel [getLabels()=" + getLabels() + ", getIsGrid()=" + getIsGrid()
				+ ", getIsDisabled()=" + getIsDisabled() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
