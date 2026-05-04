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

class ProductCardDataModelTest {

	private final AemContext context = new AemContext();
	
	ProductCardDataModel productCardDataModel;
	
	@BeforeEach
	void setUp() {
		context.addModelsForClasses(ProductCardDataModel.class);
		context.load().json("/com/srp/core/models/common/MyFavouritesProductListTest.json", "/json");
	}

	@Test
	void testToString() {
		context.currentResource("/json/fav_list");
		productCardDataModel = context.request().adaptTo(ProductCardDataModel.class);
		String expRespString = "ProductCardDataModel [getExperienceFragments()=[ExperienceFragmentsDataModel [getXfPath()=/content/experience-fragments/srp/ee/it/en/banner/master]], getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]]]";
		String actuRespString = productCardDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}

	@Test
	void testLabelsNullToString() {
		productCardDataModel = new ProductCardDataModel();
		List<LabelsDataModel> result = productCardDataModel.getLabels();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

}
