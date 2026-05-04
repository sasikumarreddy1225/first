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

class FrequentlyPurchasedDataModelTest {

private final AemContext context = new AemContext();
	
FrequentlyPurchasedDataModel frequentlyPurchasedDataModel;
	
	@BeforeEach
	void setUp() {
		context.addModelsForClasses(FrequentlyPurchasedDataModel.class);
		context.load().json("/com/srp/core/models/common/MyFavouritesProductListTest.json", "/json");
	}

	@Test
	void testToString() {
		context.currentResource("/json/fav_list");
		frequentlyPurchasedDataModel = context.request().adaptTo(FrequentlyPurchasedDataModel.class);
		String expRespString = "FrequentlyPurchasedDataModel [getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]], getExperienceFragments()=[ExperienceFragmentsDataModel [getXfPath()=/content/experience-fragments/srp/ee/it/en/banner/master]]]";
		String actuRespString = frequentlyPurchasedDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}

	@Test
	void testLabelsNullToString() {
		frequentlyPurchasedDataModel = new FrequentlyPurchasedDataModel();
		List<LabelsDataModel> result = frequentlyPurchasedDataModel.getLabels();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}
}
