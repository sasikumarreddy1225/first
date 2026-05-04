package com.srp.core.models.common;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.srp.core.models.LabelsDataModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class LocalLabourManagementDataModelTest {

	private final AemContext context = new AemContext();
	LocalLabourManagementDataModel localLabourManagementDataModel;
	
	
	@BeforeEach
	void setUp() {
		context.addModelsForClasses(LocalLabourManagementDataModel.class);
		context.load().json("/com/srp/core/models/common/GridComponentsTest.json", "/json");	
	}
	
	@Test
	void testToString() {
		context.currentResource("/json/job_cards");
		localLabourManagementDataModel = context.request().adaptTo(LocalLabourManagementDataModel.class);
		String expRespString = "LocalLabourManagementDataModel [getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]]]";
		String actuRespString = localLabourManagementDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}
	
	@Test
	void testLabelsNullToString() {
		localLabourManagementDataModel = new LocalLabourManagementDataModel();
		List<LabelsDataModel> result = localLabourManagementDataModel.getLabels();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

}
