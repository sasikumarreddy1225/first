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
class ProductDetailsDataModelTest {

	private final AemContext context = new AemContext();
	
	ProductDetailsDataModel productDetailsDataModel;
	
	@BeforeEach
	void setUp() {
		context.addModelsForClasses(ProductDetailsDataModel.class);
		context.load().json("/com/srp/core/models/common/MyFavouritesProductListTest.json", "/json");
	}

	@Test
	void testToString() {
		context.currentResource("/json/fav_list");
		productDetailsDataModel = context.request().adaptTo(ProductDetailsDataModel.class);
		String expRespString = "ProductDetailsDataModel [getExperienceFragments()=[ExperienceFragmentsDataModel [getXfPath()=/content/experience-fragments/srp/ee/it/en/banner/master]], getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]]]";
		String actuRespString = productDetailsDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}

	@Test
	void testLabelsNullToString() {
		productDetailsDataModel = new ProductDetailsDataModel();
		List<LabelsDataModel> result = productDetailsDataModel.getLabels();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}
	

}
