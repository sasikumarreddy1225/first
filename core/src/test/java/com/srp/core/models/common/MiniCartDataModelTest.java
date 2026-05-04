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


@ExtendWith({AemContextExtension.class, MockitoExtension.class })
class MiniCartDataModelTest {

	private final AemContext context = new AemContext();
	
	MiniCartDataModel miniCartDataModel;
	@BeforeEach
	void setUp() {
		context.addModelsForClasses(MiniCartDataModel.class);
		context.load().json("/com/srp/core/models/common/MiniCartDataModelTest.json", "/json");	
	}
	
	@Test
	void testToString() {
		context.currentResource("/json/miniCart");
		miniCartDataModel = context.request().adaptTo(MiniCartDataModel.class);
		String expRespString = "MiniCartDataModel [getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]]]";
		String actuRespString = miniCartDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}
	
	@Test
	void testLabelsNullToString() {
		miniCartDataModel = new MiniCartDataModel();
		List<LabelsDataModel> result = miniCartDataModel.getLabels();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

}

