package com.srp.core.beans.request;

import java.util.Arrays;

public class TagsListBean {

	private String[] roleTag;
	private String[] geoTag;
	private String[] brandTag;

	public String[] getRoleTag() {
		if (null == this.roleTag) {
			return new String[0];
		}
		return roleTag.clone();
	}

	public String[] getGeoTag() {
		if (null == this.geoTag) {
			return new String[0];
		}
		return geoTag.clone();
	}

	public String[] getBrandTag() {
		if (null == this.brandTag) {
			return new String[0];
		}
		return brandTag.clone();
	}

	public void setRoleTag(String[] roleTag) {
		if (roleTag == null)
			this.roleTag = new String[0];
		else
			this.roleTag = roleTag.clone();
	}

	public void setGeoTag(String[] geoTag) {
		if (geoTag == null)
			this.geoTag = new String[0];
		else
			this.geoTag = geoTag.clone();
	}

	public void setBrandTag(String[] brandTag) {
		if (brandTag == null)
			this.brandTag = new String[0];
		else
			this.brandTag = brandTag.clone();
	}

	@Override
	public String toString() {
		return "TagsListBean [getRoleTag()=" + Arrays.toString(getRoleTag()) + ", getGeoTag()="
				+ Arrays.toString(getGeoTag()) + ", getBrandTag()=" + Arrays.toString(getBrandTag()) + "]";
	}
}
