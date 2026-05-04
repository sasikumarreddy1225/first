package com.srp.core.models.common;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.srp.core.models.LabelsDataModel;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ErrorOverlayDataModelTest {
	
	private final AemContext context = new AemContext();
	
	ErrorOverlayDataModel errorOverlayDataModel;

	@BeforeEach
	void setUp() {
		context.addModelsForClasses(ErrorOverlayDataModel.class);
		context.load().json("/com/srp/core/models/common/GridComponentsTest.json", "/json");
	}

	@Test
	void testToString() {
		context.currentResource("/json/job_cards");
		errorOverlayDataModel = context.request().adaptTo(ErrorOverlayDataModel.class);
		String expRespString = "ErrorOverlayDataModel [getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]]]";
		String actuRespString = errorOverlayDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}

	@Test
	void testLabelsNullToString() {
		errorOverlayDataModel = new ErrorOverlayDataModel();
		List<LabelsDataModel> result = errorOverlayDataModel.getLabels();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}


}
