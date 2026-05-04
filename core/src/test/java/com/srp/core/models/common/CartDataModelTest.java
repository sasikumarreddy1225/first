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
class CartDataModelTest {

	private final AemContext context = new AemContext();
	
	CartDataModel cartDataModel;
	@BeforeEach
	void setUp() {
		context.addModelsForClasses(CartDataModel.class);
		context.load().json("/com/srp/core/models/common/CartDataModelTest.json", "/json");	
	}
	
	@Test
	void testToString() {
		context.currentResource("/json/cart");
		cartDataModel = context.request().adaptTo(CartDataModel.class);
		String expRespString = "CartDataModel [getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]]]";
		String actuRespString = cartDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}
	
	@Test
	void testLabelsNullToString() {
		cartDataModel = new CartDataModel();
		List<LabelsDataModel> result = cartDataModel.getLabels();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

}


	


