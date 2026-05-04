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
class ProductListingDataModelTest {

	private final AemContext context = new AemContext();

	ProductListingDataModel productListingDataModel;

	@BeforeEach
	void setUp() {
		context.addModelsForClasses(ProductListingDataModel.class);
		context.load().json("/com/srp/core/models/common/GridComponentsTest.json", "/json");
	}

	@Test
	void testToString() {
		context.currentResource("/json/job_cards");
		productListingDataModel = context.request().adaptTo(ProductListingDataModel.class);
		String expRespString = "ProductListingDataModel [getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]], getExperienceFragments()=[]]";
		String actuRespString = productListingDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}

	@Test
	void testLabelsNullToString() {
		productListingDataModel = new ProductListingDataModel();
		List<LabelsDataModel> result = productListingDataModel.getLabels();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

}
