package com.srp.core.models.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.srp.core.models.LabelsDataModel;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({AemContextExtension.class, MockitoExtension.class })
class BreadCrumbDataModelTest {

private final AemContext context = new AemContext();
	
    BreadcrumbDataModel  breadcrumbdatamodel;
	
	@BeforeEach
	void setUp() {
		context.addModelsForClasses(BackOrdersDataModel.class);
		context.load().json("/com/srp/core/models/common/GridComponentsTest.json", "/json");	
	}
	
	@Test
	void testToString() {
		context.currentResource("/json/job_cards");
		breadcrumbdatamodel = context.request().adaptTo(BreadcrumbDataModel.class);
		String expRespString = "BreadcrumbDataModel [getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]]]";
		String actuRespString = breadcrumbdatamodel.toString();
		assertEquals(expRespString, actuRespString);
	}
	
	@Test
	void testLabelsNullToString() {
		breadcrumbdatamodel = new BreadcrumbDataModel();
		List<LabelsDataModel> result = breadcrumbdatamodel.getLabels();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

}
