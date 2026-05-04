package com.srp.core.beans.response;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LabelsDataBeanTest {

	@Test
	void testLabelsDataBean() {
		LabelsDataBean labelsDataBean = new LabelsDataBean(); 
		
		labelsDataBean.setLabelId("123");
		labelsDataBean.setLabelName("guest");
		
		assertEquals("123",labelsDataBean.getLabelId());
		assertEquals("guest", labelsDataBean.getLabelName());
	}

}