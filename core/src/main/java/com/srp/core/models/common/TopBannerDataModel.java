package com.srp.core.models.common;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import com.srp.core.models.TagsDataModel;
import com.srp.core.services.GenericDataModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = TopBannerDataModel.RESOURCE)
public class TopBannerDataModel extends TagsDataModel implements GenericDataModel{

	protected static final String RESOURCE = "srp/components/common/top-banner";

	@Inject
	SlingHttpServletRequest request;

	@ChildResource
	private List<TopBannerEntryDataModel> banners;

	public List<TopBannerEntryDataModel> getBanners() {
		if (this.banners == null) {
			return new ArrayList<>();
		}
		return new ArrayList<>(banners);
	}

	@Override
	public String toString() {
		return "TopBannerDataModel [getBanners()=" + getBanners() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
