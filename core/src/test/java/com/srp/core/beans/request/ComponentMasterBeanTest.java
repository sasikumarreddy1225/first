package com.srp.core.beans.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComponentMasterBeanTest {

	ComponentMasterBean componentMasterBean = new ComponentMasterBean();

	String componentType = "top-banner";
	String componentVariant = "topBanner";

	@Test
	void testToString() {
		componentMasterBean.setComponentType(componentType);
		componentMasterBean.setComponentVariant(componentVariant);

		String expValue = "ComponentMasterBean [getComponentType()=top-banner, getComponentVariant()=topBanner]";
		String actValue = componentMasterBean.toString();
		assertEquals(expValue, actValue);
	}

}
