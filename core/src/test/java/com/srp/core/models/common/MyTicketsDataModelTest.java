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
class MyTicketsDataModelTest {

	private final AemContext context = new AemContext();
	
	MyTicketsDataModel myTicketsDataModel;
	
	@BeforeEach
	void setUp() {
		context.addModelsForClasses(MyTicketsDataModel.class);
		context.load().json("/com/srp/core/models/common/GridComponentsTest.json", "/json");	
	}
	
	@Test
	void testToString() {
		context.currentResource("/json/job_cards");
		myTicketsDataModel = context.request().adaptTo(MyTicketsDataModel.class);
		String expRespString = "MyTicketsDataModel [getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]], getIsGrid()=true, getIsDisabled()=true]";
		String actuRespString = myTicketsDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}
	
	@Test
	void testLabelsNullToString() {
		myTicketsDataModel = new MyTicketsDataModel();
		List<LabelsDataModel> result = myTicketsDataModel.getLabels();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

}

