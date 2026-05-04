package com.srp.core.models.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import com.srp.core.models.TagsDataModel;
import com.srp.core.services.GenericDataModel;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = GetCountriesDataModel.RESOURCETYPE)
public class GetCountriesDataModel extends TagsDataModel implements GenericDataModel {

	protected static final String RESOURCETYPE = "srp/components/common/get-countries";

	@ChildResource
	private List<GetCountriesListDataModel> countryList;

	public List<GetCountriesListDataModel> getCountryList() {
		if (this.countryList == null) {
			return new ArrayList<>();
		}
		return new ArrayList<>(countryList);
	}

	@Override
	public String toString() {
		return "GetCountriesDataModel [getCountryList()=" + getCountryList() + "]";
	}

	@Override
	public void setRequest(SlingHttpServletRequest request) {
		// TODO Auto-generated method stub

	}

}
