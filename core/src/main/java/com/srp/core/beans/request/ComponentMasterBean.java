package com.srp.core.beans.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComponentMasterBean {
	
	private String componentType;
	private String componentVariant;
	
	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	public String getComponentVariant() {
		return componentVariant;
	}

	public void setComponentVariant(String componentVariant) {
		this.componentVariant = componentVariant;
	}

	@Override
	public String toString() {
		return "ComponentMasterBean [getComponentType()=" + getComponentType() + ", getComponentVariant()="
				+ getComponentVariant() + "]";
	}
	
	


}
