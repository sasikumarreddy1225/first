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
class OrdersDataModelTest {

private final AemContext context = new AemContext();
	
	OrdersDataModel ordersDataModel;
	
	@BeforeEach
	void setUp() {
		context.addModelsForClasses(OrdersDataModel.class);
		context.load().json("/com/srp/core/models/common/GridComponentsTest.json", "/json");	
	}
	
	@Test
	void testToString() {
		context.currentResource("/json/job_cards");
		ordersDataModel = context.request().adaptTo(OrdersDataModel.class);
		String expRespString = "OrdersDataModel [getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]]]";
		String actuRespString = ordersDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}
	
	@Test
	void testLabelsNullToString() {
	    ordersDataModel = new OrdersDataModel();
		List<LabelsDataModel> result = ordersDataModel.getLabels();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}
	}
