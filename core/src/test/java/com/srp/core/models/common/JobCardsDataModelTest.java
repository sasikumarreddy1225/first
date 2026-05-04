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
class JobCardsDataModelTest {

	private final AemContext context = new AemContext();
	
	JobCardsDataModel jobCardsDataModel;
	
	@BeforeEach
	void setUp() {
		context.addModelsForClasses(JobCardsDataModel.class);
		context.load().json("/com/srp/core/models/common/GridComponentsTest.json", "/json");	
	}
	
	@Test
	void testToString() {
		context.currentResource("/json/job_cards");
		jobCardsDataModel = context.request().adaptTo(JobCardsDataModel.class);
		String expRespString = "JobCardsDataModel [getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]], getIsGrid()=true, getIsDisabled()=true]";
		String actuRespString = jobCardsDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}
	
	@Test
	void testLabelsNullToString() {
		jobCardsDataModel = new JobCardsDataModel();
		List<LabelsDataModel> result = jobCardsDataModel.getLabels();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

}
