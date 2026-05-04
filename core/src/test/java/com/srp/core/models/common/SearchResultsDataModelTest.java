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
class SearchResultsDataModelTest {

	private final AemContext context = new AemContext();

	SearchResultsDataModel searchResultsDataModel;

	@BeforeEach
	void setUp() {
		context.addModelsForClasses(SearchResultsDataModel.class);
		context.load().json("/com/srp/core/models/common/GridComponentsTest.json", "/json");
	}

	@Test
	void testToString() {
		context.currentResource("/json/job_cards");
		searchResultsDataModel = context.request().adaptTo(SearchResultsDataModel.class);
		String expRespString = "SearchResultsDataModel [getLabels()=[LabelsDataModel [getLabel()=, getLabelId()=1234, getLabelType()=nt:unstructured]], getExperienceFragments()=[]]";
		String actuRespString = searchResultsDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}

	@Test
	void testLabelsNullToString() {
		searchResultsDataModel = new SearchResultsDataModel();
		List<LabelsDataModel> result = searchResultsDataModel.getLabels();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}
}
