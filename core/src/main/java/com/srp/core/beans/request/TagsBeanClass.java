package com.srp.core.beans.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TagsBeanClass {


	private List<String> geoTags;

	private List<String> roleTags;

	private List<String> brandTags;

	public List<String> getGeoTags() {
		if (null == this.geoTags) {
			return new ArrayList<>();
		}
		return new ArrayList<>(geoTags);
	}

	public void setGeoTags(List<String> geoTags) {
		if (null == geoTags) {
			geoTags = new ArrayList<>();
		}
		this.geoTags = Collections.unmodifiableList(geoTags);
	}

	public List<String> getRoleTags() {
		if (null == this.roleTags) {
			return new ArrayList<>();
		}
		return new ArrayList<>(roleTags);
	}

	public void setRoleTags(List<String> roleTags) {
		if (null == roleTags) {
			roleTags = new ArrayList<>();
		}
		this.roleTags = Collections.unmodifiableList(roleTags);
	}

	public List<String> getBrandTags() {
		if (null == this.brandTags) {
			return new ArrayList<>();
		}
		return new ArrayList<>(brandTags);
	}

	public void setBrandTags(List<String> brandTags) {
		if (null == brandTags) {
			brandTags = new ArrayList<>();
		}
		this.brandTags = Collections.unmodifiableList(brandTags);
	}

	@Override
	public String toString() {
		return "TagsBeanClass [getGeoTags()=" + getGeoTags() + ", getRoleTags()=" + getRoleTags() + ", getBrandTags()="
				+ getBrandTags() + "]";
	}
}
