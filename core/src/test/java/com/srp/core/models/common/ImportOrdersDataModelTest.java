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
class ImportOrdersDataModelTest {

private final AemContext context = new AemContext();
	
	ImportOrdersDataModel importOrdersDataModel;
	
	@BeforeEach
	void setUp() {
		context.addModelsForClasses(UserAccountDataModel.class);
		context.load().json("/com/srp/core/models/common/GridComponentsTest.json", "/json");	
	}
	
	@Test
	void testToString() {
		context.currentResource("/json/job_cards");
		importOrdersDataModel = context.request().adaptTo(ImportOrdersDataModel.class);
		String expRespString = "ImportOrdersDataModel [getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]]]";
		String actuRespString = importOrdersDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}
	
	@Test
	void testLabelsNullToString() {
		importOrdersDataModel = new ImportOrdersDataModel();
		List<LabelsDataModel> result = importOrdersDataModel.getLabels();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

}