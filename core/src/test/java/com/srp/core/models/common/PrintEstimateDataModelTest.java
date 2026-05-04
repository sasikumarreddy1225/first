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
class PrintEstimateDataModelTest {

	private final AemContext context = new AemContext();
	PrintEstimateDataModel printEstimateDataModel;
	
	
	@BeforeEach
	void setUp() {
		context.addModelsForClasses(PrintEstimateDataModel.class);
		context.load().json("/com/srp/core/models/common/GridComponentsTest.json", "/json");	
	}
	
	@Test
	void testToString() {
		context.currentResource("/json/job_cards");
		printEstimateDataModel = context.request().adaptTo(PrintEstimateDataModel.class);
		String expRespString = "PrintEstimateDataModel [getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]]]";
		String actuRespString = printEstimateDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}
	
	@Test
	void testLabelsNullToString() {
		printEstimateDataModel = new PrintEstimateDataModel();
	    List<LabelsDataModel> result = printEstimateDataModel.getLabels();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

}