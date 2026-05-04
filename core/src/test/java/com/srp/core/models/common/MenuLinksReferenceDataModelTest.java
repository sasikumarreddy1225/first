package com.srp.core.models.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class MenuLinksReferenceDataModelTest {

	private final AemContext ctx = new AemContext();

	MenuLinksReferenceDataModel menuLinksReferenceDataModel;

	@BeforeEach
	void setUp() {
		ctx.addModelsForClasses(MenuLinksReferenceDataModel.class);
		ctx.load().json("/com/srp/core/models/common/MenuLinksReferenceDataModelTest.json", "/json");
		ctx.currentResource("/json/menulinks_reference");
		menuLinksReferenceDataModel = ctx.request().adaptTo(MenuLinksReferenceDataModel.class);
	}

	@Test
	void testToString() {
		String expectedToString = "MenuLinksReferenceDataModel [getMenulinkPath()=/content/srp/menulinks/global/en]";
		String actualToString = menuLinksReferenceDataModel.toString();
		assertEquals(expectedToString, actualToString);
	}

}
