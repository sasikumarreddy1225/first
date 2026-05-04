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
class OrderDetailsDataModelTest {

private final AemContext context = new AemContext();
	
	OrderDetailsDataModel orderDetailsDataModel;
	
	@BeforeEach
	void setUp() {
		context.addModelsForClasses(OrderDetailsDataModel.class);
		context.load().json("/com/srp/core/models/common/GridComponentsTest.json", "/json");	
	}
	
	@Test
	void testToString() {
		context.currentResource("/json/job_cards");
		orderDetailsDataModel = context.request().adaptTo(OrderDetailsDataModel.class);
		String expRespString = "OrderDetailsDataModel [getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]]]";
		String actuRespString = orderDetailsDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}
	
	@Test
	void testLabelsNullToString() {
	    orderDetailsDataModel = new OrderDetailsDataModel();
		List<LabelsDataModel> result = orderDetailsDataModel.getLabels();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

}
