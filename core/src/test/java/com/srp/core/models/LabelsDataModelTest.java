package com.srp.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class LabelsDataModelTest {

	private final AemContext context = new AemContext();

	@BeforeEach
	void setUp() {
		context.addModelsForClasses(LabelsDataModel.class);
		context.load().json("/com/srp/core/models/LabelsDataModelTest.json", "/json");
		context.currentResource("/json/labels");
	}

	@Test
	void toStringTestCase() {
		LabelsDataModel labels = context.request().adaptTo(LabelsDataModel.class);
		String actualResp = labels.toString();
		String expectedResp = "LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]";
		assertEquals(expectedResp, actualResp);
	}

	@Test
	void toStringNullTestCase() {
		context.currentResource("/json/labels_null");
		LabelsDataModel labels = context.request().adaptTo(LabelsDataModel.class);
		String actualResp = labels.toString();
		String expectedResp = "LabelsDataModel [getLabel()=admin, getLabelId()=1234, getLabelType()=nt:unstructured]";
		assertEquals(expectedResp, actualResp);
	}

}
