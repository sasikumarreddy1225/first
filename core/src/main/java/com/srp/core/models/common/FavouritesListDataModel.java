package com.srp.core.models.common;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import com.srp.core.models.LabelsDataModel;
import com.srp.core.models.TagsDataModel;
import com.srp.core.services.GenericDataModel;

import java.util.ArrayList;
import java.util.List;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = FavouritesListDataModel.RESOURCE)
public class FavouritesListDataModel extends TagsDataModel implements GenericDataModel {

	protected static final String RESOURCE = "srp/components/common/favourites";


	@ChildResource
	private List<LabelsDataModel> labels;


	@ChildResource(name = "widgetLinks")
	private List<FooterLinksEntryModel> srpLinks;

	public List<FooterLinksEntryModel> getLinks() {
		if (this.srpLinks == null) {
			return new ArrayList<>();
		}
		return new ArrayList<>(srpLinks);
	}

	public List<LabelsDataModel> getLabels() {
		if (this.labels == null) {
			return new ArrayList<>();
		}
		return new ArrayList<>(labels);
	}

	@Override
	public String toString() {
		return "FavouritesListDataModel [getLinks()=" + getLinks() + ", getLabels()=" + getLabels() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}
}
