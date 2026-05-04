package com.srp.core.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TagsDataModel {

	@ValueMapValue
	private List<String> geoTags;

	@ValueMapValue
	private List<String> roleTags;

	@ValueMapValue
	private List<String> brandTags;

	public List<String> getGeoTags() {
		if (null == this.geoTags) {
			return new ArrayList<>();
		}
		return new ArrayList<>(geoTags);
	}

	public List<String> getRoleTags() {
		if (null == this.roleTags) {
			return new ArrayList<>();
		}
		return new ArrayList<>(roleTags);
	}

	public List<String> getBrandTags() {
		if (null == this.brandTags) {
			return new ArrayList<>();
		}
		return new ArrayList<>(brandTags);
	}

	@Override
	public String toString() {
		return "TagsDataModel [getGeoTags()=" + getGeoTags() + ", getRoleTags()=" + getRoleTags() + ", getBrandTags()="
				+ getBrandTags() + "]";
	}
}
