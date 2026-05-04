package com.srp.core.models.common;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import com.srp.core.models.ExperienceFragmentsDataModel;
import com.srp.core.models.LabelsDataModel;
import com.srp.core.models.TagsDataModel;
import com.srp.core.services.GenericDataModel;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = ProductCategoriesDataModel.RESOURCETYPE)
public class ProductCategoriesDataModel extends TagsDataModel implements GenericDataModel {

	protected static final String RESOURCETYPE = "srp/components/common/wsp/product-categories";

	@Inject
	@ChildResource
	private List<LabelsDataModel> labels;

	@Inject
	@ChildResource
	private List<ExperienceFragmentsDataModel> experienceFragments;

	@Inject
	@ChildResource
	private List<CategroyDataModel> categories;

	public List<LabelsDataModel> getLabels() {
		if (null == this.labels) {
			return new ArrayList<>();
		}
		return new ArrayList<>(labels);
	}

	public List<ExperienceFragmentsDataModel> getExperienceFragments() {
		if (null == this.experienceFragments) {
			return new ArrayList<>();
		}
		return new ArrayList<>(experienceFragments);
	}

	public List<CategroyDataModel> getCategories() {
		if (null == this.categories) {
			return new ArrayList<>();
		}
		return new ArrayList<>(categories);
	}

	@Override
	public String toString() {
		return "ProductCategoriesDataModel [getLabels()=" + getLabels() + ", getExperienceFragments()="
				+ getExperienceFragments() + ", getCategories()=" + getCategories() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub

	}

}
