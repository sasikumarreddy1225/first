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
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = CatalogDataModel.RESOURCE)
public class CatalogDataModel extends TagsDataModel implements GenericDataModel {

	protected static final String RESOURCE = "srp/components/common/catalog";


	@ChildResource
	private List<LabelsDataModel> labels;

	@ChildResource
	private List<CatalogCategoryDataModel> categories;
	
	@ChildResource
	private List<CatalogLinksDataModel> catalogs;

	public List<LabelsDataModel> getLabels() {
		if (this.labels == null) {
			return new ArrayList<>();
		}
		return new ArrayList<>(labels);
	}

	public List<CatalogCategoryDataModel> getCategories() {
		if (this.categories == null) {
			return new ArrayList<>();
		}
		return new ArrayList<>(categories);
	}
	
	public List<CatalogLinksDataModel> getCatalogs() {
		if (this.catalogs == null) {
			return new ArrayList<>();
		}
		return new ArrayList<>(catalogs);
	}


	@Override
	public String toString() {
		return "CatalogDataModel [getLabels()=" + getLabels() + ", getCategories()=" + getCategories()
				+ ", getCatalogs()=" + getCatalogs() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
