package com.srp.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class TagsDataModelTest {

	private final AemContext context = new AemContext();

	@BeforeEach
	void setUp() {
		context.addModelsForClasses(TagsDataModel.class);
		context.load().json("/com/srp/core/models/TagsDataModelTest.json", "/json");
		context.currentResource("/json/tags");
	}

	@Test
	void toStringTestCase() {
		TagsDataModel tags = context.request().adaptTo(TagsDataModel.class);
		String actualResp = tags.toString();
		String expectedResp = "TagsDataModel [getGeoTags()=[srp:geography/na/usa/grate-lakes], getRoleTags()=[srp:position/13-service-advisor], getBrandTags()=[srp:brand/jeep, srp:brand/fiat]]";
		assertEquals(expectedResp, actualResp);
	}

	@Test
	void toStringNullTestCase() {
		context.currentResource("/json/tags_ null");
		TagsDataModel tags = context.request().adaptTo(TagsDataModel.class);
		String actualResp = tags.toString();
		String expectedResp = "TagsDataModel [getGeoTags()=[], getRoleTags()=[], getBrandTags()=[]]";
		assertEquals(expectedResp, actualResp);
	}

}
